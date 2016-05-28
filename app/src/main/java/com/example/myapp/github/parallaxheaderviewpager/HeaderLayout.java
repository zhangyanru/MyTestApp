package com.example.myapp.github.parallaxheaderviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * 用于解决Header的点击事件和ListView滑动冲突的问题
 * 解决方法：在滑动时拦截touch事件，并在onTouchEvent中手动分发touch事件给ViewPager
 * 需要注意的是：down事件没有被拦截，所以onTouchEvent中分发时要补充一个Down事件
 * Created by xiaoning.wang on 16/1/21.
 */
public class HeaderLayout extends LinearLayout {

    private static final String TAG = "HeaderLayout";

    private final int mTouchSlop;
    public HeaderLayout(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private View viewPager;

    public void setViewPager(View viewPager){
        this.viewPager = viewPager;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否补充了down事件
     */
    boolean dispatchDownEvent = false;
    /**
     * 是否滑动了
     */
    private boolean mIsBeingDragged;
    private float downY;

    public boolean onTouchEvent(MotionEvent event) {

        if(viewPager == null){
            return super.onTouchEvent(event);
        }
        event.setLocation(event.getRawX(), event.getRawY());
        if (!dispatchDownEvent) {
            event.setAction(MotionEvent.ACTION_DOWN);
            dispatchDownEvent = true;
        }
        viewPager.dispatchTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            dispatchDownEvent = false;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(viewPager == null){
            return super.onInterceptTouchEvent(ev);
        }
        //Log.d(TAG, "onInterceptTouchEvent() called with: " + "ev = [" + ev + "]");
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mIsBeingDragged = false;
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                float y = ev.getY();
                float yDiff = Math.abs(y - downY);
                if (yDiff > mTouchSlop && !mIsBeingDragged) {
                    mIsBeingDragged = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                break;
        }
        return mIsBeingDragged;
    }
}
