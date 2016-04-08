package com.example.myapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapp.R;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;


/**
 * Created by zyr
 * DATE: 16-4-7
 * Time: 下午6:55
 * Email: yanru.zhang@renren-inc.com
 */
public class PullToRefreshListView extends ListView implements OnScrollListener {

    private static final int TAP_TO_REFRESH = 1;            //（未刷新）
    private static final int PULL_TO_REFRESH = 2;           // 下拉刷新
    private static final int RELEASE_TO_REFRESH = 3;        // 释放刷新
    private static final int REFRESHING = 4;                // 正在刷新
    private static final int TAP_TO_LOADMORE = 5;           // 未加载更多
    private static final int LOADING = 6;                   // 正在加载


    private static final String TAG = "PullToRefreshListView";

    private OnRefreshListener mOnRefreshListener;           // 刷新监听器

    /**
     * Listener that will receive notifications every time the list scrolls.
     */
    private OnScrollListener mOnScrollListener;             // 列表滚动监听器
    private LayoutInflater mInflater;                       // 用于加载布局文件

    private RelativeLayout mRefreshHeaderView;              // 刷新视图(也就是头部那部分)
    private TextView mRefreshViewText;                      // 刷新提示文本
    private ImageView mRefreshViewImage;                    // 刷新向上向下的那个图片
    private ProgressBar mRefreshViewProgress;               // 这里是圆形进度条
    private TextView mRefreshViewLastUpdated;               // 最近更新的文本

    private RelativeLayout mLoadMoreFooterView;             // 加载更多
    private TextView mLoadMoreText;                         // 提示文本
    private ProgressBar mLoadMoreProgress;                  // 加载更多进度条


    private int mCurrentScrollState;                        // 当前滚动位置
    private int mRefreshState;                              // 刷新状态
    private int mLoadState;                                 // 加载状态

    private RotateAnimation mFlipAnimation;                 // 下拉动画
    private RotateAnimation mReverseFlipAnimation;          // 恢复动画

    private int mRefreshViewHeight;                         // 刷新视图高度
    private int mRefreshOriginalTopPadding;                 // 原始上部间隙
    private int mLastMotionY;                               // 记录点击位置

    public PullToRefreshListView(Context context) {
        super(context);
        init(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        // Load all of the animations we need in code rather than through XML
        /** 定义旋转动画**/
        // 参数：1.旋转开始的角度 2.旋转结束的角度 3. X轴伸缩模式 4.X坐标的伸缩值 5.Y轴的伸缩模式 6.Y坐标的伸缩值
        mFlipAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);                // 设置持续时间
        mFlipAnimation.setFillAfter(true);              // 动画执行完是否停留在执行完的状态
        mReverseFlipAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(250);
        mReverseFlipAnimation.setFillAfter(true);

        // 获取LayoutInflater实例对象
        mInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        // 加载下拉刷新的头部视图
        mRefreshHeaderView = (RelativeLayout) mInflater.inflate(
                R.layout.pull_to_refresh_header, this, false);
        mRefreshViewText =
                (TextView) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_text);
        mRefreshViewImage =
                (ImageView) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_image);
        mRefreshViewProgress =
                (ProgressBar) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_progress);
        mRefreshViewLastUpdated =
                (TextView) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_updated_at);
        mLoadMoreFooterView = (RelativeLayout) mInflater.inflate(
                R.layout.loadmore_footer, this, false);
        mLoadMoreText = (TextView) mLoadMoreFooterView.findViewById(R.id.loadmore_text);
        mLoadMoreProgress = (ProgressBar) mLoadMoreFooterView.findViewById(R.id.loadmore_progress);


        mRefreshViewImage.setMinimumHeight(50);     // 设置图片最小高度
        mRefreshHeaderView.setOnClickListener(new OnClickRefreshListener());
        mRefreshOriginalTopPadding = mRefreshHeaderView.getPaddingTop();
        mLoadMoreFooterView.setOnClickListener(new OnClickLoadMoreListener());

        mRefreshState = TAP_TO_REFRESH;             // 初始刷新状态
        mLoadState = TAP_TO_LOADMORE;

        addHeaderView(mRefreshHeaderView);          // 增加头部视图
        addFooterView(mLoadMoreFooterView);         // 增加尾部视图

        super.setOnScrollListener(this);

//        measureView(mRefreshHeaderView);                // 测量视图
//        mRefreshViewHeight = mRefreshHeaderView.getMeasuredHeight();    // 得到视图的高度

        mRefreshHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRefreshViewHeight = mRefreshHeaderView.getMeasuredHeight();    // 得到视图的高度
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setSelection(1);        // 设置当前选中的项
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);

        setSelection(1);
    }

    /**
     * Set the listener that will receive notifications every time the list
     * scrolls.
     *
     * @param l The scroll listener.
     */
    @Override
    public void setOnScrollListener(AbsListView.OnScrollListener l) {
        mOnScrollListener = l;
    }

    /**
     * Register a callback to be invoked when this list should be refreshed.
     * 注册监听器
     * @param onRefreshListener The callback to run.
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    /**
     * Set a text to represent when the list was last updated.
     * 设置一个文本来表示最近更新的列表，显示的是最近更新列表的时间
     * @param lastUpdated Last updated at.
     */
    public void setLastUpdated(CharSequence lastUpdated) {
        if (lastUpdated != null) {
            mRefreshViewLastUpdated.setVisibility(View.VISIBLE);
            mRefreshViewLastUpdated.setText("更新于: " + lastUpdated);
        } else {
            mRefreshViewLastUpdated.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int y = (int) event.getY();    // 获取点击位置的Y坐标

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:     // 手指抬起
                if (!isVerticalScrollBarEnabled()) {
                    setVerticalScrollBarEnabled(true);
                }
                if (getFirstVisiblePosition() == 0 && mRefreshState != REFRESHING) {
                    if ((mRefreshHeaderView.getBottom() > mRefreshViewHeight
                            || mRefreshHeaderView.getTop() >= 0)
                            && mRefreshState == RELEASE_TO_REFRESH) {
                        // Initiate the refresh
                        mRefreshState = REFRESHING;     // 刷新状态
                        prepareForRefresh();
                        onRefresh();
                    } else if (mRefreshHeaderView.getBottom() < mRefreshViewHeight
                            || mRefreshHeaderView.getTop() < 0) {
                        // Abort refresh and scroll down below the refresh view
                        resetHeader();
                        setSelection(1);
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                applyHeaderPadding(event);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void applyHeaderPadding(MotionEvent ev) {
        final int historySize = ev.getHistorySize();

        // Workaround for getPointerCount() which is unavailable in 1.5
        // (it's always 1 in 1.5)
        int pointerCount = 1;
        try {
            Method method = MotionEvent.class.getMethod("getPointerCount");
            pointerCount = (Integer)method.invoke(ev);
        } catch (NoSuchMethodException e) {
            pointerCount = 1;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalAccessException e) {
            System.err.println("unexpected " + e);
        } catch (InvocationTargetException e) {
            System.err.println("unexpected " + e);
        }

        for (int h = 0; h < historySize; h++) {
            for (int p = 0; p < pointerCount; p++) {
                if (mRefreshState == RELEASE_TO_REFRESH) {
                    if (isVerticalFadingEdgeEnabled()) {
                        setVerticalScrollBarEnabled(false);
                    }

                    int historicalY = 0;
                    try {
                        // For Android > 2.0
                        Method method = MotionEvent.class.getMethod(
                                "getHistoricalY", Integer.TYPE, Integer.TYPE);
                        historicalY = ((Float) method.invoke(ev, p, h)).intValue();
                    } catch (NoSuchMethodException e) {
                        // For Android < 2.0
                        historicalY = (int) (ev.getHistoricalY(h));
                    } catch (IllegalArgumentException e) {
                        throw e;
                    } catch (IllegalAccessException e) {
                        System.err.println("unexpected " + e);
                    } catch (InvocationTargetException e) {
                        System.err.println("unexpected " + e);
                    }

                    // Calculate the padding to apply, we divide by 1.7 to
                    // simulate a more resistant effect during pull.
                    int topPadding = (int) (((historicalY - mLastMotionY)
                            - mRefreshViewHeight) / 1.7);

                    Log.d("zyr","--------------------------topPadding :" + topPadding);
                    // 设置上、下、左、右四个位置的间隙间隙
                    mRefreshHeaderView.setPadding(
                            mRefreshHeaderView.getPaddingLeft(),
                            topPadding,
                            mRefreshHeaderView.getPaddingRight(),
                            mRefreshHeaderView.getPaddingBottom());
                }
            }
        }
    }

    /**
     * Sets the header padding back to original size.
     * 设置头部填充会原始大小
     */
    private void resetHeaderPadding() {
        mRefreshHeaderView.setPadding(
                mRefreshHeaderView.getPaddingLeft(),
                mRefreshOriginalTopPadding,
                mRefreshHeaderView.getPaddingRight(),
                mRefreshHeaderView.getPaddingBottom());
    }

    /**
     * Resets the header to the original state.
     * 重新设置头部为原始状态
     */
    private void resetHeader() {
        if (mRefreshState != TAP_TO_REFRESH) {
            mRefreshState = TAP_TO_REFRESH;

            resetHeaderPadding();

            // Set refresh view text to the pull label
            mRefreshViewText.setText("pull_to_refresh_tap_label");
            // Replace refresh drawable with arrow drawable
            mRefreshViewImage.setImageResource(R.drawable.indicator_arrow);
            // Clear the full rotation animation
            mRefreshViewImage.clearAnimation();
            // Hide progress bar and arrow.
            mRefreshViewImage.setVisibility(View.GONE);
            mRefreshViewProgress.setVisibility(View.GONE);
        }
    }

    /**
     * 重设ListView尾部视图为初始状态
     */
    private void resetFooter() {
        if(mLoadState != TAP_TO_LOADMORE) {
            mLoadState = TAP_TO_LOADMORE;

            // 进度条设置为不可见
            mLoadMoreProgress.setVisibility(View.GONE);
            // 按钮的文本替换为“加载更多”
            mLoadMoreText.setText("loadmore_label");
        }

    }


    /**
     * 测量视图的大小
     * @param child
     */
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0,
                0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // When the refresh view is completely visible, change the text to say
        // "Release to refresh..." and flip the arrow drawable.
        if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
                && mRefreshState != REFRESHING) {
            if (firstVisibleItem == 0) {        // 如果第一个可见条目为0
                mRefreshViewImage.setVisibility(View.VISIBLE);  // 让指示箭头变得可见
                /**如果头部视图相对与父容器的位置大于其自身高度+20或者头部视图的顶部位置>0,并且要在刷新状态不等于"释放以刷新"**/
                if ((mRefreshHeaderView.getBottom() > mRefreshViewHeight + 20
                        || mRefreshHeaderView.getTop() >= 0)
                        && mRefreshState != RELEASE_TO_REFRESH) {
                    mRefreshViewText.setText(R.string.pull_to_refresh_release_label);// 设置刷新文本为"Release to refresh..."
                    mRefreshViewImage.clearAnimation();                 // 清除动画
                    mRefreshViewImage.startAnimation(mFlipAnimation);   // 启动动画
                    mRefreshState = RELEASE_TO_REFRESH;                 // 更改刷新状态为“释放以刷新"
                } else if (mRefreshHeaderView.getBottom() < mRefreshViewHeight + 20
                        && mRefreshState != PULL_TO_REFRESH) {
                    mRefreshViewText.setText(R.string.pull_to_refresh_pull_label);// 设置刷新文本为"Pull to refresh..."
                    if (mRefreshState != TAP_TO_REFRESH) {
                        mRefreshViewImage.clearAnimation();
                        mRefreshViewImage.startAnimation(mReverseFlipAnimation);
                    }
                    mRefreshState = PULL_TO_REFRESH;
                }
            } else {
                mRefreshViewImage.setVisibility(View.GONE);         // 让刷新箭头不可见
                resetHeader();  // 重新设置头部为原始状态
            }
        } else if (mCurrentScrollState == SCROLL_STATE_FLING
                && firstVisibleItem == 0
                && mRefreshState != REFRESHING) {
            setSelection(1);
        }

        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem,
                    visibleItemCount, totalItemCount);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mCurrentScrollState = scrollState;

        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }


    /**为刷新做准备**/
    public void prepareForRefresh() {
        resetHeaderPadding();

        mRefreshViewImage.setVisibility(View.GONE);         // 去掉刷新的箭头
        // We need this hack, otherwise it will keep the previous drawable.
        mRefreshViewImage.setImageDrawable(null);
        mRefreshViewProgress.setVisibility(View.VISIBLE);   // 圆形进度条变为可见

        // Set refresh view text to the refreshing label
        mRefreshViewText.setText(R.string.pull_to_refresh_refreshing_label);

        mRefreshState = REFRESHING;
    }

    /**为加载更多做准备**/
    public void prepareForLoadMore() {
        mLoadMoreProgress.setVisibility(View.VISIBLE);
        mLoadMoreText.setText("loading_label");
        mLoadState = LOADING;
    }

    public void onRefresh() {
        Log.d(TAG, "onRefresh");

        if (mOnRefreshListener != null) {
            mOnRefreshListener.onRefresh();
        }
    }

    public void OnLoadMore() {
        Log.d(TAG, "onLoadMore");
        if(mOnRefreshListener != null) {
            mOnRefreshListener.onLoadMore();
        }
    }

    /**
     * Resets the list to a normal state after a refresh.
     * @param lastUpdated Last updated at.
     */
    public void onRefreshComplete(CharSequence lastUpdated) {
        setLastUpdated(lastUpdated);    // 显示更新时间
        onRefreshComplete();
    }

    /**
     * Resets the list to a normal state after a refresh.
     */
    public void onRefreshComplete() {
        Log.d(TAG, "onRefreshComplete");

        resetHeader();

        // If refresh view is visible when loading completes, scroll down to
        // the next item.
        if (mRefreshHeaderView.getBottom() > 0) {
            invalidateViews();
            setSelection(1);
        }
    }

    public void onLoadMoreComplete() {
        Log.d(TAG, "onLoadMoreComplete");
        resetFooter();
    }

    /**
     * Invoked when the refresh view is clicked on. This is mainly used when
     * there's only a few items in the list and it's not possible to drag the
     * list.
     * 点击刷新
     */
    private class OnClickRefreshListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (mRefreshState != REFRESHING) {
                prepareForRefresh();
                onRefresh();
            }
        }

    }

    /**
     *
     * @author wwj
     * 加载更多
     */
    private class OnClickLoadMoreListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if(mLoadState != LOADING) {
                prepareForLoadMore();
                OnLoadMore();
            }
        }
    }

    /**
     * Interface definition for a callback to be invoked when list should be
     * refreshed.
     * 接口定义一个回调方法当列表应当被刷新
     */
    public interface OnRefreshListener {
        /**
         * Called when the list should be refreshed.
         * 当列表应当被刷新是调用这个方法
         * <p>
         * A call to {@link PullToRefreshListView #onRefreshComplete()} is
         * expected to indicate that the refresh has completed.
         */
        public void onRefresh();

        public void onLoadMore();
    }
}
