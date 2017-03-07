package com.example.myapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 15-11-16
 * Time: 下午8:47
 * Email: yanru.zhang@renren-inc.com
 */
public class RoundProgressBar extends View {
    int mNegativeColor;
    int mPositiveColor;
    float mRingWidth;
    int mTextColor;
    float mTextSize;
    float mProgress;
    Paint mNegativePaint, mPositivePaint, mTextPaint;
    float mWidth, mHeight, mCenterX, mCenterY, mRadius, mTextWidth;
    RectF mRectF;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        mNegativeColor = getResources().getColor(R.color.gray_cccccc);
        mPositiveColor = getResources().getColor(R.color.blue_light);
        mRingWidth = getResources().getDimension(R.dimen.round_progress_bar_circle_width);
        mTextColor = getResources().getColor(R.color.gray_cccccc);
        mTextSize = 40;
        mProgress = 20;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        for (int i = 0; i < array.length(); i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.RoundProgressBar_p_negativeColor:
                    mNegativeColor = array.getColor(R.styleable.RoundProgressBar_p_negativeColor, getResources().getColor(R.color.gray_cccccc));
                    break;
                case R.styleable.RoundProgressBar_p_positiveColor:
                    mPositiveColor = array.getColor(R.styleable.RoundProgressBar_p_positiveColor, getResources().getColor(R.color.blue_light));
                    break;
                case R.styleable.RoundProgressBar_p_ringWidth:
                    mRingWidth = array.getDimension(R.styleable.RoundProgressBar_p_ringWidth, getResources().getDimension(R.dimen.round_progress_bar_circle_width));
                    break;
                case R.styleable.RoundProgressBar_p_textColor:
                    mTextColor = array.getColor(R.styleable.RoundProgressBar_p_textColor, getResources().getColor(R.color.gray_cccccc));
                    break;
                case R.styleable.RoundProgressBar_p_textSize:
                    mTextSize = array.getDimension(R.styleable.RoundProgressBar_p_textSize, 40);
                    break;
                case R.styleable.RoundProgressBar_p_progress:
                    mProgress = array.getDimension(R.styleable.RoundProgressBar_p_progress, 20);
                    break;
            }
        }
        array.recycle();
        mNegativePaint = new Paint();
        mNegativePaint.setStyle(Paint.Style.STROKE);
        mNegativePaint.setAntiAlias(true);
        mNegativePaint.setColor(mNegativeColor);
        mNegativePaint.setStrokeWidth(mRingWidth);

        mPositivePaint = new Paint();
        mPositivePaint.setStyle(Paint.Style.STROKE);
        mPositivePaint.setAntiAlias(true);
        mPositivePaint.setColor(mPositiveColor);
        mPositivePaint.setStrokeWidth(mRingWidth);

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int defaultWidth = (int) mRingWidth * 10 + getPaddingLeft() + getPaddingRight();
        int defaultHeight = (int) mRingWidth * 10 + getPaddingTop() + getPaddingBottom();
        //支持wrap_content
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultWidth, defaultHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, defaultHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            mWidth = getMeasuredWidth();
            mHeight = getMeasuredHeight();
            //做padding的处理，使支持padding
            mCenterY = (mHeight - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop();
            mCenterX = (mWidth - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
            mRadius = Math.min(mWidth - getPaddingLeft() - getPaddingRight(), mHeight - getPaddingTop() - getPaddingBottom()) / 2 - mRingWidth / 2;
            mRectF = new RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆环
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mNegativePaint);
        //绘制psitive部分的圆弧,canvas.drawArc参数信息：http://www.2cto.com/kf/201503/385759.html
        canvas.drawArc(mRectF, -90, mProgress / 100 * 360, false, mPositivePaint);
        //draw process text
        canvas.drawText(mProgress + "%", mCenterX - mTextWidth / 2, mCenterY, mTextPaint);
    }

    public void setProgress(float mProgress) {
        this.mProgress = mProgress;
        mTextWidth = mTextPaint.measureText(mProgress + "%");
        postInvalidate();
    }
}
