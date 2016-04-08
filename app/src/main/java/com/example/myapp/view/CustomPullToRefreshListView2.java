package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-4-6
 * Time: 下午4:02
 * Email: yanru.zhang@renren-inc.com
 *
 * 通过listview addHeaderView来实现下拉刷新
 */
public class CustomPullToRefreshListView2 extends ListView implements AbsListView.OnScrollListener {
    private Context mContext;

    private View headerView;

    private TextView headerTv;

    private int headerHeight;

    public static final int STATE_NONE  = 0;
    public static final int STATE_PULL_TO_REFRESH = 1;
    public static final int STATE_RELEASE_TO_REFRESH = 2;
    public static final int STATE_REFRESHING = 3;

    private int state = STATE_NONE;
    private int downY;
    private OnRefreshListener mOnRefreshListener;

    public interface OnRefreshListener{
        void onDownPullRefresh();
    }

    /************************   构造****************************************/

    public CustomPullToRefreshListView2(Context context) {
        this(context, null);
    }

    public CustomPullToRefreshListView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPullToRefreshListView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initHeaderView();
    }

    private void initHeaderView() {
        headerView =  View.inflate(getContext(), R.layout.header_listview, null);
//        headerView = LayoutInflater.from(mContext).inflate(R.layout.header_listview,this,false);
        headerTv = (TextView) headerView.findViewById(R.id.header_tv);
        headerView.measure(0, 0); // 系统会帮我们测量出headerView的高度
        headerHeight = headerView.getMeasuredHeight();
        Log.d("zyr","--------------------headerHeight :" + headerHeight);
        headerView.setPadding(0, -headerHeight, 0, 0);
        Log.d("zyr", "----------------------headerPaddingTop :" + headerView.getPaddingTop());
        this.addHeaderView(headerView); // 向ListView的顶部添加一个view对象
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
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE :
                int moveY = (int) ev.getY();
                // 移动中的y - 按下的y = 间距.
                int diff = (moveY - downY) / 2;
                // -头布局的高度 + 间距 = paddingTop
                int paddingTop = -headerHeight + diff;
                // 如果: 第一个可见，向下拉
                if (getFirstVisiblePosition() == 0
                        &&  diff > 0) {
                    if (paddingTop > 0 ) { // 完全显示了.
                        Log.i("zyr", "松开刷新");
                        state = STATE_RELEASE_TO_REFRESH;
                        refreshHeaderView();
                    } else if (paddingTop < 0 ) { // 没有显示完全
                        Log.i("zyr", "下拉刷新");
                        state = STATE_PULL_TO_REFRESH;
                        refreshHeaderView();
                    }
                    // 下拉头布局
                    Log.d("zyr","--------------paddingTop:" + paddingTop);
                    headerView.setPadding(0, paddingTop, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP :
                // 判断当前的状态是松开刷新还是下拉刷新
                if (state == STATE_RELEASE_TO_REFRESH) {
                    Log.i("zyr", "刷新数据.");
                    // 把头布局设置为完全显示状态
                    headerView.setPadding(0, 0, 0, 0);
                    // 进入到正在刷新中状态
                    state = STATE_REFRESHING;
                    refreshHeaderView();

                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onDownPullRefresh(); // 调用使用者的监听方法
                    }
                } else if (state == STATE_PULL_TO_REFRESH) {
                    // 隐藏头布局
                    headerView.setPadding(0, -headerHeight, 0, 0);
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
                headerView.setPadding(0, -headerHeight, 0, 0);
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
