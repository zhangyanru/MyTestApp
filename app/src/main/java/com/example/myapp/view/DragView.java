package com.example.myapp.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
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
                int parentLeft = (int) (getX() + getPaddingLeft());
                int parentRight =(int) (getY() - getPaddingRight());

                if(left > (parentRight-child.getWidth())){
                    return parentRight-child.getWidth();
                }else if(left <parentLeft){
                    return parentLeft;
                }
                return left;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            viewDragHelper.cancel();
            return false;
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 有三种可能的模式：
     UNSPECIFIED：父布局没有给子布局任何限制，子布局可以任意大小。
     EXACTLY：父布局决定子布局的确切大小。不论子布局多大，它都必须限制在这个界限里。
     AT_MOST：子布局可以根据自己的大小选择任意大小。
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            // mesure child
            View child = getChildAt(i);
            LayoutParams lp = child.getLayoutParams();
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                    getPaddingLeft()+getPaddingRight(), lp.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                    getPaddingTop()+getPaddingBottom(), lp.height);
            child.measure(childWidthMeasureSpec,
                    childHeightMeasureSpec);
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
