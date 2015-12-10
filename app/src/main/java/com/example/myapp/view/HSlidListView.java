package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by zyr
 * DATE: 15-11-25
 * Time: 下午7:20
 * Email: yanru.zhang@renren-inc.com
 * dispatchTouchEvent是处理触摸事件分发,事件(多数情况)是从Activity的dispatchTouchEvent开始的。执行
   super.dispatchTouchEvent(ev)，事件向下分发。
   onInterceptTouchEvent是ViewGroup提供的方法，默认返回false，返回true表示拦截。
   onTouchEvent是View中提供的方法，ViewGroup也有这个方法，view中不提供onInterceptTouchEvent。view中默认返回true，表示消费了这个事件。
 http://blog.csdn.net/xyz_lmn/article/details/12517911
 http://blog.csdn.net/xiaanming/article/details/17539199
 */
public class HSlidListView extends ListView {
    private Scroller scroller;
    private int screenWidth;
    /**
     * 手指按下X的坐标
     */
    private int downY;
    /**
     * 手指按下Y的坐标
     */
    private int downX;
    /**
     * 是否响应滑动，默认为不响应
     */
    private boolean isSlide = false;
    /**
     * 认为是用户滑动的最小距离
     */
    private int mTouchSlop = 50;
    /**
     * 当前滑动的ListView　position
     */
    private int slidePosition;
    /**
     * ListView的item
     */
    private View itemView;

    public HSlidListView(Context context) {
        this(context, null);
    }

    public HSlidListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HSlidListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        scroller = new Scroller(context);
    }
    /**
     * 分发事件，主要做的是判断点击的是那个item, 以及通过postDelayed来设置响应左右滑动事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.d("zyr","ACTION_DOWN");
                Log.d("zyr","ACTION_DOWN x:" + ev.getX());
                Log.d("zyr","ACTION_DOWN y:" + ev.getY());
                // 假如scroller滚动还没有结束，我们直接返回
                if (!scroller.isFinished()) {
                    return super.dispatchTouchEvent(ev);
                }
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                slidePosition = pointToPosition(downX, downY);
                // 获取我们点击的item view
                itemView = getChildAt(slidePosition - getFirstVisiblePosition());
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.d("zyr","ACTION_MOVE");
                Log.d("zyr","ACTION_MOVE x:" + ev.getX());
                Log.d("zyr","ACTION_MOVE y:" + ev.getY());
                if ((Math.abs(ev.getX() - downX) > mTouchSlop &&
                        Math.abs(ev.getY() - downY) < mTouchSlop)) {
                    isSlide = true;
                }else{
                    isSlide = false;
                }
                Log.d("zyr","ACTION_MOVE isSlide:" + isSlide);
                break;
            }
            case MotionEvent.ACTION_UP:
                Log.d("zyr","ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    /**
     * 处理我们拖动ListView item的逻辑
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSlide && slidePosition != AdapterView.INVALID_POSITION) {
            requestDisallowInterceptTouchEvent(true);
            final int action = ev.getAction();
            int x = (int) ev.getX();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:

                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (ev.getActionIndex()<< MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);

                    int deltaX = downX - x;
                    downX = x;

                    // 手指拖动itemView滚动, deltaX大于0向左滚动，小于0向右滚
                    itemView.scrollBy(deltaX, 0);

                    return true;  //拖动的时候ListView不滚动
                case MotionEvent.ACTION_UP:
                    // 手指离开的时候就不响应左右滚动
                    isSlide = false;
                    break;
            }
        }

        return super.onTouchEvent(ev);
    }
}
