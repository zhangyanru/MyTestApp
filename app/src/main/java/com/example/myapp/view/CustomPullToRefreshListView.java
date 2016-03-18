package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-3-16
 * Time: 下午4:12
 * Email: yanru.zhang@renren-inc.com
 * 关于下拉刷新，我目前看到的办法是将刷新的部分和ListView包含在LinearlyLayout中，滑动的时候改变LinearlyLayout的padding
 * 感觉用header，改变ListView的header的高度应该也可行把，没有试过
 *
 *
 *
 * 下拉刷新
 * 1.往下拉，而且已经拉到最顶部
 * 2.超过一定的距离
 * 3.
 */
public class CustomPullToRefreshListView extends FrameLayout{
    private Context mContext;
    private View refreshView;
    private ListView listView;
    private TextView textView;
    private int mWidth,mHeight,mRefreshWidth,mRefreshHeight;
    private FrameLayout.LayoutParams refreshViewLayoutParams,listViewLayoutParams;
    private int downX,downY,touchX,touchY,deltaX,deltaY,startX,startY,headMoveX,headMoveY;
    private boolean isScrollDown = false;
    private int currentStatus = STATUS_REFRESH_FINISHED;
    /**
     * 下拉状态
     */
    public static final int STATUS_PULL_TO_REFRESH = 0;
    /**
     * 释放立即刷新状态
     */
    public static final int STATUS_RELEASE_TO_REFRESH = 1;
    /**
     * 正在刷新状态
     */
    public static final int STATUS_REFRESHING = 2;
    /**
     * 刷新完成或未刷新状态
     */
    public static final int STATUS_REFRESH_FINISHED = 3;
    /**
     * 下拉超过这个高度释放就可以刷新
     */
    public static final int REFRESH_HEIGHT = 200;
    /***************************    OnRefreshListener   *************************/
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    private OnRefreshListener onRefreshListener;
    public interface OnRefreshListener{
        void onRefresh();
    }

    public CustomPullToRefreshListView(Context context) {
        this(context, null);
    }

    public CustomPullToRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_custom_pull_to_refresh_listview, this);
        refreshView = findViewById(R.id.refresh_view);
        refreshViewLayoutParams = (FrameLayout.LayoutParams)refreshView.getLayoutParams();
        textView = (TextView)refreshView.findViewById(R.id.text_view);
        listView = (ListView)findViewById(R.id.list_view);
        listViewLayoutParams = (FrameLayout.LayoutParams)listView.getLayoutParams();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=oldw || h!=oldh){
            mWidth = w;
            mHeight = h;
            mRefreshWidth = refreshView.getMeasuredWidth();
            mRefreshHeight = refreshView.getMeasuredHeight();
            if(mRefreshHeight!=0){
                refreshViewLayoutParams.setMargins(0,-mRefreshHeight,0,0);
                refreshView.setLayoutParams(refreshViewLayoutParams);
            }
        }
    }

    /**************************     ListView    ************************/
    public void setAdapter(ListAdapter adapter){
        listView.setAdapter(adapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        listView.setOnItemClickListener(onItemClickListener);
    }

    public void setSelection(int position){
        listView.setSelection(position);
    }

    /**************************     Touch    ************************/
    /**
     * 如果dispatchTouchEvent返回true ，则交给这个view的onTouchEvent处理，
     * 如果dispatchTouchEvent返回 false ，则交给这个 view 的 interceptTouchEvent 方法来决定是否要拦截这个事件
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX =(int) ev.getX();
                downY =(int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                touchX =(int) ev.getX();
                touchY =(int) ev.getY();
                deltaX = touchX-downX;
                deltaY = touchY-downY;
                if(deltaY>=0){
                    isScrollDown = true;
                }else{
                    isScrollDown = false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                downX = 0;
                downY = 0;
                touchX = 0;
                touchY = 0;
                deltaX = 0;
                deltaY = 0;
                startX = 0;
                startY = 0;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(isScrollDown && listView.getFirstVisiblePosition() ==0 && listView.getScrollY() == 0){
                    currentStatus = STATUS_PULL_TO_REFRESH;
                    textView.setText("下拉去刷新");
                    startX =(int) ev.getX();
                    startY =(int) ev.getY();
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                headMoveY = (int) event.getY() - startY - mRefreshHeight;
                refreshViewLayoutParams.setMargins(0, headMoveY, 0, 0);
                refreshView.setLayoutParams(refreshViewLayoutParams);
                listViewLayoutParams.setMargins(0, headMoveY + mRefreshHeight, 0, 0);
                listView.setLayoutParams(listViewLayoutParams);
                if(headMoveY > REFRESH_HEIGHT){
                    currentStatus = STATUS_RELEASE_TO_REFRESH;
                    textView.setText("释放立即刷新");
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(currentStatus == STATUS_RELEASE_TO_REFRESH){
                    currentStatus = STATUS_REFRESHING;
                    textView.setText("正在刷新。。。");
                    onRefreshListener.onRefresh();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void onRefreshComplete(){
        if(currentStatus == STATUS_REFRESHING){
            currentStatus = STATUS_REFRESH_FINISHED;
            textView.setText("刷新成功！");
        }
        refreshViewLayoutParams.setMargins(0, -mRefreshHeight, 0, 0);
        refreshView.setLayoutParams(refreshViewLayoutParams);
        listViewLayoutParams.setMargins(0,0 , 0,0);
        listView.setLayoutParams(listViewLayoutParams);
    }
}
