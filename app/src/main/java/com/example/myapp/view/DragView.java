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
 * http://m.blog.csdn.net/blog/coder_nice/44678153
 * http://blog.csdn.net/coder_nice/article/details/44592989
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

            /**
             * 处理水平方向上的拖动
             * @param child 被拖动到view
             * @param left 移动到达的x轴的距离
             * @param dx 建议的移动的x距离
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {

                //取得左边界的坐标
                final int leftBound = getPaddingLeft();
                //取得右边界的坐标
                final int rightBound = getWidth() - child.getWidth() - leftBound;
                //这个地方的含义就是 如果left的值 在leftbound和rightBound之间 那么就返回left
                //如果left的值 比 leftbound还要小 那么就说明 超过了左边界 那我们只能返回给他左边界的值
                //如果left的值 比rightbound还要大 那么就说明 超过了右边界，那我们只能返回给他右边界的值

                return Math.min(Math.max(left, leftBound), rightBound);

            }
            /**
             *  处理竖直方向上的拖动
             * @param  child 被拖动到view
             * @param  top 移动到达的y轴的距离
             * @param  dy 建议的移动的y距离
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                // 两个if主要是为了让viewViewGroup里
                if(getPaddingTop() > top) {
                    return getPaddingTop();
                }

                if(getHeight() - child.getHeight() < top) {
                    return getHeight() - child.getHeight();
                }

                return top;
            }

            /**
             * 当拖拽到状态改变时回调
             * @params 新的状态
             */
            @Override
            public void onViewDragStateChanged(int state) {
                switch (state) {
                    case ViewDragHelper.STATE_DRAGGING:  // 正在被拖动
                        break;
                    case ViewDragHelper.STATE_IDLE:  // view没有被拖拽或者 正在进行fling/snap
                        break;
                    case ViewDragHelper.STATE_SETTLING: // fling完毕后被放置到一个位置
                        break;
                }
                super.onViewDragStateChanged(state);
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
