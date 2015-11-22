package com.example.myapp.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zyr
 * DATE: 15-11-22
 * Time: 下午7:00
 * Email: yanru.zhang@renren-inc.com
 */
public class DragView extends ViewGroup{

    private ViewDragHelper viewDragHelper;
    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViewDragHelper();
    }

    public void initViewDragHelper(){
        viewDragHelper = ViewDragHelper.create(DragView.this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return 300;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return 300;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            // mesure child
            getChildAt(i).measure(MeasureSpec.UNSPECIFIED,
                    MeasureSpec.UNSPECIFIED);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            int childCount = getChildCount();
            for(int i=0;i<childCount;i++){
                View childView = getChildAt(i);
                int width  = childView.getMeasuredWidth();
                int height = childView.getMeasuredHeight();
                int x = (int) childView.getX();
                int y = (int)childView.getY();
                childView.layout(x,y,x+width,y+height);
            }
        }
    }
}
