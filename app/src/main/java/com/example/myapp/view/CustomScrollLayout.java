package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zyr
 * DATE: 16-2-25
 * Time: 下午3:23
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomScrollLayout extends ViewGroup {
    private Context mContext;
    private int touchMinDx = 4;//确定是拖动的x轴的最小距离
    private int moveMinDx = 50;//触发自动滑动的拖动的最小距离
    private int downX,moveX, lastMoveX, moveDeltaX,deltaX;
    public CustomScrollLayout(Context context) {
        this(context, null);
    }

    public CustomScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScrollLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for(int i=0;i<getChildCount();i++){
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            moveMinDx = getMeasuredWidth()/2;
            for(int i=0;i<getChildCount();i++){
                View childView = getChildAt(i);
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int)ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                lastMoveX = moveX = (int)ev.getX();
                if(Math.abs(moveX - downX) > touchMinDx){
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
                downX = (int)event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int)event.getX();
                moveDeltaX = lastMoveX - moveX;
                deltaX = downX - moveX;
                lastMoveX = moveX;
                scrollBy(moveDeltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                if(deltaX < 0 && getScrollX() < getMeasuredWidth()){//第一个子view
                    scrollTo(0,0);
                }else if( deltaX > 0 && getScrollX() > (getChildCount()-1)*getMeasuredWidth() && getScrollX() < getChildCount() * getMeasuredWidth()){//最后一个子view
                    scrollTo((getChildCount()-1) * getMeasuredWidth(),0);
                }else if(Math.abs(deltaX) > moveMinDx){
                    int autoMoveX = Math.abs(getMeasuredWidth() - deltaX);
                    if(deltaX > 0){//left
                        scrollBy(autoMoveX,0);
                    }else{
                        scrollBy(-autoMoveX,0);
                    }
                }else{
                    scrollTo(0,0);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                scrollTo(0,0);
                break;
        }
        return super.onTouchEvent(event);
    }
}
