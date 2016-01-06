package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by zyr
 * DATE: 15-12-15
 * Time: 下午3:39
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomViewGroup extends ViewGroup {
    private Context mContext;
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        setBackgroundColor(Color.parseColor(color));
        invalidate();
    }
    public CustomViewGroup(Context context) {
        this(context,null);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zyr", "view group click!!!");
            }
        });

    }

    /*
    * 计算childView的测量值以及模式，以及设置自己的宽和高：
    * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*
        * EXACTLY：表示设置了精确的值，一般当childView设置其宽、高为精确值、match_parent时，ViewGroup会将其设置为EXACTLY；
        * AT_MOST：表示子布局被限制在一个最大值内，一般当childView设置其宽、高为wrap_content时，ViewGroup会将其设置为AT_MOST；
        * UNSPECIFIED：表示子布局想要多大就多大，一般出现在AadapterView的item的heightMode中、ScrollView的childView的heightMode中；此种模式比较少见
        * */

        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        // 计算出所有的childView的宽和高
//        measureChildren(widthMeasureSpec, heightMeasureSpec);
        final int size = getChildCount();
        for (int i = 0; i < size; ++i) {
            View child = getChildAt(i);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidthMeasureSpec;
            int childHeightMeasureSpec;

            if (lp.width == LayoutParams.MATCH_PARENT) {
                childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth() -
                                getPaddingLeft() - getPaddingRight() -
                                lp.leftMargin - lp.rightMargin,
                        MeasureSpec.EXACTLY);
            } else {
                childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                        getPaddingLeft() + getPaddingRight() +
                                lp.leftMargin + lp.rightMargin,
                        lp.width);
            }

            if (lp.height == LayoutParams.MATCH_PARENT) {
                childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() -
                                getPaddingTop() - getPaddingBottom() -
                                lp.topMargin - lp.bottomMargin,
                        MeasureSpec.EXACTLY);
            } else {
                childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                        getPaddingTop() + getPaddingBottom() +
                                lp.topMargin + lp.bottomMargin,
                        lp.height);
            }

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);


//            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }

        /*
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接设置为父容器计算的值
         */

        /*
        * 如果是一个View，重写onMeasure时要注意：
        * 如果在使用自定义view时，用了wrap_content。那么在onMeasure中就要调用setMeasuredDimension，
        * 来指定view的宽高。如果使用的fill_parent或者一个具体的dp值。那么直接使用super.onMeasure即可。
        * */
//        setMeasuredDimension(sizeWidth,sizeHeight);
        Log.d("zyr","\n sizeWidth:" + sizeWidth + "\n sizeHeight:" + sizeHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("zyr","\nl:" + l +"\nt:" + t +"\nr:"+r+"\nb:"+b);
        l = l + getPaddingLeft();
        t = t + getPaddingTop();
        r = r - getPaddingRight();
        b = b - getPaddingBottom();
        Log.d("zyr","\nl:" + l +"\nt:" + t +"\nr:"+r+"\nb:"+b);

        Log.d("zyr","\npaddingLeft:" + getPaddingLeft()
        + "\n paddingTop:" + getPaddingTop()
        + "\n paddingRight:" + getPaddingRight()
        + "\n paddingBottom:" + getPaddingBottom());

        final int parentLeft = getPaddingLeft();
        final int parentRight = r - l - getPaddingRight();

        final int parentTop = getPaddingTop();
        final int parentBottom = b - t - getPaddingBottom();

        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        /**
         * 遍历所有childView根据其宽和高，以及margin进行布局
         */
        for (int i = 0; i < cCount; i++)
        {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            Log.d("zyr","\ncMarginLeft:" + cParams.leftMargin +"\ncMarginTop:" + cParams.topMargin
            +"\n cMarginRight:" + cParams.rightMargin + "\n cMarginBottom:" + cParams.bottomMargin);
            Log.d("zyr","\ncWidth:" + cWidth +"\ncHeight:" + cHeight);

            int cl = 0, ct = 0, cr = 0, cb = 0;
            cl = parentLeft + cParams.leftMargin;
            ct = parentTop + cParams.topMargin;
//            cr = cWidth + parentLeft - cParams.rightMargin;
//            cb = cHeight + parentTop - cParams.bottomMargin;

            cr = cWidth + cl;
            cb = cHeight + ct;
            Log.d("zyr","\ncl:" + cl +"\nct:" + ct +"\ncr:"+ cr +"\ncb:"+ cb);
            childView.layout(cl,ct,cr,cb);
        }
    }

    /**
     * 某一个子view，多宽，多高, 内部加上了viewGroup的padding值、margin值和传入的宽高wUsed、hUsed
     */
    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
    }

    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        super.measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    /*
                    * 决定该ViewGroup的LayoutParams
                    * */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mContext,attrs);
        return layoutParams;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("zyr","measureWidth:" + getMeasuredWidth());
        Log.d("zyr","measureHeight:" + getMeasuredHeight());
        Log.d("zyr","width:" + getWidth());
        Log.d("zyr", "height:" + getHeight());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


}
