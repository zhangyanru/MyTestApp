package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

/**
 * Created by zyr
 * DATE: 16-3-2
 * Time: 上午10:39
 * Email: yanru.zhang@renren-inc.com
 */
public class MyShimmerButton extends Button {
    private Paint mPaint;
    private int mWdith,mHeight,mTranslateX,deltaX;
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private boolean isShimmer = false;


    public MyShimmerButton(Context context) {
        super(context);
    }

    public MyShimmerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyShimmerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("zyr","onFinishInflate");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("zyr", "onMeasure");

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("zyr", "onLayout");

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("zyr", "onSizeChanged");

        if(w!=oldw || h!=oldh){
            isShimmer = true;
            mWdith = w;
            mHeight = h;
            mPaint = getPaint();
            mLinearGradient = new LinearGradient(0,0,mWdith,mHeight,new int[]{0x33ffffff, 0xffffffff, 0x33ffffff} ,  new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP);
            mMatrix = new Matrix();
            mLinearGradient.setLocalMatrix(mMatrix);
            deltaX = mWdith / 10;
            mPaint.setShader(mLinearGradient);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isShimmer){
            if(mTranslateX > mWdith*2){
                mTranslateX = -mWdith;
            } else{
                mTranslateX = mTranslateX + deltaX;
            }

            mMatrix.setTranslate(mTranslateX,0);
            mLinearGradient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(50);
        }
    }
}
