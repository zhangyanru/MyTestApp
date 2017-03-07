package com.example.myapp.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by zhangyanru on 2017/2/28.
 * https://my.oschina.net/fengheju/blog/196266
 * 功能描述：
 */

public class CustomVerticalLayout extends ViewGroup {

    public CustomVerticalLayout(Context context) {
        super(context);
    }

    public CustomVerticalLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVerticalLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 计算所有ChildView的宽度和高度 然后根据ChildView的计算结果，设置自己的宽和高
     * <p>
     * View 中对 子视图 进行 measure() 操作:
     * 1.measureChildren() : 内部调用 measureChild() 对每一个子视图进行 measure 操作
     * 2.measureChild() : 为指定的子视图进行measure操作
     * 3.measureChildWithMargins() : measure 时考虑把margin及padding
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 计算所有child view 要占用的空间
        int desireWidth = 0;
        int desireHeight = 0;
        int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                MarginLayoutParams childLp = (MarginLayoutParams) childView.getLayoutParams();
                //将measureChild改为measureChildWithMargin
                measureChildWithMargins(childView, widthMeasureSpec, 0,
                        heightMeasureSpec, 0);
                //这里在计算宽度时加上margin
                desireHeight += childView.getMeasuredHeight() + childLp.topMargin + childLp.bottomMargin;
                desireWidth = Math
                        .max(desireWidth, childView.getMeasuredWidth() + childLp.leftMargin + childLp.rightMargin);
            }
        }

        // count with padding
        desireWidth += getPaddingLeft() + getPaddingRight();
        desireHeight += getPaddingTop() + getPaddingBottom();

        // see if the size is big enough
        desireWidth = Math.max(desireWidth, getSuggestedMinimumWidth());
        desireHeight = Math.max(desireHeight, getSuggestedMinimumHeight());

        setMeasuredDimension(resolveSize(desireWidth, widthMeasureSpec),
                resolveSize(desireHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int tempHeight = getPaddingTop();
            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);
                int cWidth = childView.getMeasuredWidth();
                int cHeight = childView.getMeasuredHeight();
                MarginLayoutParams cParams = (MarginLayoutParams) childView.getLayoutParams();
                childView.layout(cParams.leftMargin + getPaddingLeft(),
                        tempHeight + cParams.topMargin,
                        Math.min(cParams.leftMargin + getPaddingLeft() + cWidth, getMeasuredWidth() - cParams.rightMargin),
                        tempHeight + cParams.topMargin + cHeight);
                tempHeight = tempHeight + cParams.topMargin + cParams.bottomMargin + cHeight;
            }
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        //为我们的ViewGroup指定了其LayoutParams为MarginLayoutParams
        return new MarginLayoutParams(getContext(), attrs);
    }
}
