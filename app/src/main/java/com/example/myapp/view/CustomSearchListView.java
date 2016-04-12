package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-4-8
 * Time: 下午7:09
 * Email: yanru.zhang@renren-inc.com
 *
 * 通过listview addHeaderView来实现下拉刷新
 */
public class CustomSearchListView extends ListView implements AbsListView.OnScrollListener {
    private Context mContext;

    private View headerView;

    private TextView headerTv;

    private View searchView;

    private RelativeLayout searchContainer;

    private int headerHeight ;

    private int refreshHeight ;

    private int searchContainerHeight;

    private Scroller mScroller;

    public static final int STATE_NONE  = 0;
    public static final int STATE_PULL_TO_SHOW_SEARCH_VIEW  = 1; // 下拉去展示search_view
    public static final int STATE_PULL_TO_REFRESH = 2; //下拉去刷新
    public static final int STATE_RELEASE_TO_REFRESH = 3; // 释放进行刷新
    public static final int STATE_REFRESHING = 4; // 正在刷新

    private int state = STATE_NONE;
    private int downX , downY , moveY , lastMoveY , upY , upX;
    private int diff , moveDiff;

    public static final int DURATION = 500;
    private OnRefreshListener mOnRefreshListener;

    public interface OnRefreshListener{
        void onDownPullRefresh();
    }

    /************************   构造****************************************/

    public CustomSearchListView(Context context) {
        this(context, null);
    }

    public CustomSearchListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSearchListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initScroller();
        initHeaderView();
        addSearchView();
        setOnScrollListener(this);
    }

    private void initScroller() {
        mScroller = new Scroller(getContext(), new DecelerateInterpolator());
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            Log.d("zyr" , "-----------computeScroll mScroller.getCurrY():" + mScroller.getCurrY());
            headerView.setPadding(0, mScroller.getCurrY(), 0, 0);
        }
    }

    private void initHeaderView() {
        headerView =  View.inflate(getContext(), R.layout.search_header_listview, null);
        searchContainer = (RelativeLayout) headerView.findViewById(R.id.search_container);
        headerTv = (TextView) headerView.findViewById(R.id.tvHead);
        headerView.measure(0, 0); // 系统会帮我们测量出headerView的高度
        headerHeight = refreshHeight = headerView.getMeasuredHeight();
        Log.d("zyr", "--------------------headerHeight :" + headerHeight);
        headerView.setPadding(0, -headerHeight, 0, 0);
        invalidate();
        Log.d("zyr", "----------------------headerPaddingTop :" + headerView.getPaddingTop());
        super.addHeaderView(headerView, null, false);
    }

    private void addSearchView() {
        searchView = LayoutInflater.from(getContext()).inflate(R.layout.search_view, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        searchContainer.addView(searchView , layoutParams);
        searchContainer.measure(0, 0);
        searchContainerHeight = searchContainer.getMeasuredHeight();
        Log.d("zyr", "--------------------searchContainerHeight :" + searchContainerHeight);
        headerHeight = searchContainerHeight + headerHeight ;
        headerView.setPadding(0, - headerHeight, 0, 0);
        invalidate();
        Log.d("zyr", "--------------------headerHeight :" + headerHeight);
        Log.d("zyr", "----------------------headerPaddingTop :" + headerView.getPaddingTop());
    }

    /**************************     Scroll******************************/
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    /***************************    Touch***************************************/
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                downY = lastMoveY = (int) ev.getY();
                downX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE :
                moveY = (int) ev.getY();
                moveDiff = (moveY - lastMoveY)/2 ;
                lastMoveY = moveY ;
                diff = moveY - downY;
                //
                int paddingTop =   headerView.getPaddingTop() + moveDiff ;
                // 如果: 第一个可见
                if(getFirstVisiblePosition() == 0 && Math.abs(diff) > 50){
                    switch (state){
                        case STATE_NONE :
                            if(diff > 0){
                                state = STATE_PULL_TO_SHOW_SEARCH_VIEW;
                                headerView.setPadding(0, paddingTop, 0, 0);
                                Log.d("zyr", "--------------paddingTop:" + paddingTop);
                                return true;
                            }
                            break;
                        case STATE_PULL_TO_SHOW_SEARCH_VIEW:
                        case STATE_PULL_TO_REFRESH:
                        case STATE_RELEASE_TO_REFRESH:
                            if (paddingTop >= 0 ) { // 完全显示了.
                                Log.i("zyr", "ACTION_MOVE 松开刷新");
                                state = STATE_RELEASE_TO_REFRESH;
                                refreshHeaderView();
                            } else if (paddingTop < 0 && Math.abs(paddingTop) <= refreshHeight) {
                                Log.i("zyr", "ACTION_MOVE 下拉刷新");
                                state = STATE_PULL_TO_REFRESH;
                                refreshHeaderView();
                            }  else if(paddingTop < 0 && Math.abs(paddingTop) > refreshHeight) {
                                Log.i("zyr", "ACTION_MOVE 下拉显示Search View");
                                state = STATE_PULL_TO_SHOW_SEARCH_VIEW;
                                refreshHeaderView();
                            }  else if ( paddingTop == 0){
                                state = STATE_NONE;
                                break;
                            }
                            // 下拉头布局
                            Log.d("zyr","--------------paddingTop:" + paddingTop);
                            headerView.setPadding(0, paddingTop, 0, 0);
                            return true;
                        default:
                            break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP :
                upY = (int)ev.getY();
                upX = (int)ev.getX();
                // 判读是不是点击事件
                if(Math.abs(upY - downY) < 50 && Math.abs(upX - downX) < 50){
                    return super.onTouchEvent(ev);
                }
                // 判断当前的状态
                int headPaddingTop = headerView.getPaddingTop();
                Log.d("zyr","--------------MotionEvent.ACTION_UP paddingTop:" + headPaddingTop);
                Log.d("zyr","--------------MotionEvent.ACTION_UP state:" + state);
                switch (state){
                    case STATE_PULL_TO_SHOW_SEARCH_VIEW:
                        if ( headerHeight - Math.abs(headPaddingTop) <= searchContainerHeight * 0.25){
                            Log.i("zyr", "下拉高度小于Search View * 0.25,隐藏search view");
                            // 隐藏头布局
                            mScroller.startScroll(0,headPaddingTop,0,- headerHeight - headPaddingTop , DURATION);
                            postInvalidate();
                            state = STATE_NONE;
                        } else if( headerHeight - Math.abs(headPaddingTop) >= searchContainerHeight * 0.75) {
                            Log.i("zyr", "下拉高度大于Search View * 0.75,显示search view");
                            // 显示 search view
                            mScroller.startScroll(0,headPaddingTop,0,- refreshHeight - headPaddingTop , DURATION);
                            postInvalidate();
                            state = STATE_PULL_TO_REFRESH;
                        } else if ( moveDiff > 0){
                            Log.i("zyr", "下拉高度在Search View * 0.25 - 0.75,下拉，显示search view");
                            // 显示 search view
                            mScroller.startScroll(0,headPaddingTop,0,- refreshHeight - headPaddingTop , DURATION);
                            postInvalidate();
                            state = STATE_PULL_TO_REFRESH;
                        } else {
                            Log.i("zyr", "下拉高度在Search View * 0.25 - 0.75,上滑，隐藏search view");
                            // 隐藏头布局
                            mScroller.startScroll(0,headPaddingTop,0,- headerHeight - headPaddingTop , DURATION);
                            postInvalidate();
                            state = STATE_NONE;
                        }
                        return true;
                    case STATE_PULL_TO_REFRESH:
                        Log.i("zyr", "ACTION_UP 下拉刷新数据");
                        // 显示search view
                        mScroller.startScroll(0,headerView.getPaddingTop(),0,- refreshHeight - headerView.getPaddingTop(),DURATION);
                        postInvalidate();
                        return true;
                    case STATE_RELEASE_TO_REFRESH:
                        Log.i("zyr", "ACTION_UP 释放刷新数据");
                        // 把头布局设置为完全显示状态
                        mScroller.startScroll(0,headerView.getPaddingTop(),0,-headerView.getPaddingTop(),DURATION);
                        postInvalidate();
                        // 进入到正在刷新中状态
                        state = STATE_REFRESHING;
                        refreshHeaderView();

                        if (mOnRefreshListener != null) {
                            mOnRefreshListener.onDownPullRefresh(); // 调用使用者的监听方法
                        }
                        return true;
                }
                break;
            default :
                break;
        }
        return super.onTouchEvent(ev);
    }
    /**
     * 根据currentState刷新头布局的状态
     */
    private void refreshHeaderView() {
        switch (state) {
            case STATE_PULL_TO_REFRESH : // 下拉刷新状态
            case STATE_PULL_TO_SHOW_SEARCH_VIEW:
                headerTv.setText("下拉刷新");
                break;
            case STATE_RELEASE_TO_REFRESH : // 松开刷新状态
                headerTv.setText("松开刷新");
                break;
            case STATE_REFRESHING : // 正在刷新中状态
                headerTv.setText("正在刷新中...");
                break;
            default :
                break;
        }
    }

    /**
     * 隐藏头布局
     */
    public void hideHeaderView() {
        post(new Runnable() {
            @Override
            public void run() {
                mScroller.startScroll(0,headerView.getPaddingTop(),0,- headerHeight -headerView.getPaddingTop(),DURATION);
                postInvalidate();
                headerTv.setText("下拉刷新");
                state = STATE_NONE;
            }
        });
    }

    /**
     * 设置刷新监听事件
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

}
