package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 15/9/15.
 */
public class GridPasswordView extends View{
    private Point[] passedPoints = new Point[9]; //选择的点
    private Point[] nativePaoints = new Point[9]; //默认有的九个点
    private int [] correctPwd = new int[9];//正确的密码
    private int [] inputPwc= new int[9];//输入的密码
    private Paint whitePaint , redPaint;
    private int mWidth,mHeight;
    public GridPasswordView(Context context) {
        this(context, null);
    }

    public GridPasswordView(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public GridPasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=oldw || h!=oldh){
            mWidth = getMeasuredWidth();
            mHeight = getMeasuredHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
