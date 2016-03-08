package com.example.myapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by zyr
 * DATE: 16-3-7
 * Time: 下午7:10
 * Email: yanru.zhang@renren-inc.com
 */
public class SinMoveView extends View {
    private Paint mPaint;
    private ValueAnimator valueAnimator;
    private float x,y;
    public SinMoveView(Context context) {
        this(context, null);
    }

    public SinMoveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SinMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(40);
        mPaint.setStyle(Paint.Style.FILL);

        valueAnimator = ValueAnimator.ofFloat(0,1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                x = (Float) animation.getAnimatedValue();
                y = getSinY(x);
                postInvalidate();
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();

    }

    private float getSinY(float x) {
        //y=Asin(ωx+φ）+B
        //A:y的最大值最小值
        //ω:
        //φ:x平移的距离
        //B:y平移的距离
        y = (float) (200 * Math.sin(Math.toRadians(x))) + 300;
        return y;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(1200,600);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);

        canvas.drawLine(0, 0, 1200, 0, mPaint);
        canvas.drawLine(0, 100, 1200, 100, mPaint);
        canvas.drawLine(0, 300, 1200, 300, mPaint);
        canvas.drawLine(0, 500, 1200, 500, mPaint);
        canvas.drawLine(0, 600, 1200, 600, mPaint);
        canvas.drawCircle(x, y, 20, mPaint);
    }
}
