package com.example.myapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by yanru.zhang on 16/7/19.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyColorMatrixView extends View {
    private Context mContext;
    private Paint mPaint;
    private ColorMatrix colorMatrix;
    private ColorMatrixColorFilter colorMatrixColorFilter;
    private Bitmap bitmap;

    public MyColorMatrixView(Context context) {
        this(context,null);
    }

    public MyColorMatrixView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyColorMatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint();
        colorMatrix = new ColorMatrix();
        colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        mPaint.setColorFilter(colorMatrixColorFilter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureSize(widthMeasureSpec,bitmap == null ? 0 : bitmap.getWidth()),
                measureSize(heightMeasureSpec,bitmap == null ? 0 : bitmap.getHeight()));
    }

    private int measureSize(int measureSpec,int bitmapSize){
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        int mSize = 0;
        if(mode == MeasureSpec.EXACTLY){
            mSize =  size;
        }else if(mode == MeasureSpec.AT_MOST){
            mSize = Math.min(size,bitmapSize);
        }else if(mode == MeasureSpec.UNSPECIFIED){
            mSize = bitmapSize;
        }
        return mSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmap != null){
            canvas.drawBitmap(bitmap,0,0,mPaint);
        }
    }

    public void setBitmap(Bitmap bitmap){
        if(bitmap == null)return;
        this.bitmap = bitmap;
        requestLayout();
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void resetColorMatrix(){
        this.colorMatrix.reset();
        colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        mPaint.setColorFilter(colorMatrixColorFilter);
        invalidate();
    }

    public void setColorMatrix(ColorMatrix colorMatrix){
        this.colorMatrix.set(colorMatrix);
        colorMatrixColorFilter = new ColorMatrixColorFilter(this.colorMatrix);
        mPaint.setColorFilter(colorMatrixColorFilter);
        invalidate();
    }

}
