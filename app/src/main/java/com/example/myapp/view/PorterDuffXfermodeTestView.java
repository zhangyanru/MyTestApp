package com.example.myapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
/**
 * Created by yanru.zhang on 16/7/20.
 * Email:yanru.zhang@renren-inc.com
 */
public class PorterDuffXfermodeTestView extends View {
    private Context mContext;
    private int width,height;
    private Paint mPaint;
    private Bitmap desBitmap,srcBitmap;
    private PorterDuff.Mode mode;

    public PorterDuffXfermodeTestView(Context context) {
        this(context, null);
    }

    public PorterDuffXfermodeTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PorterDuffXfermodeTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(20);
        mPaint.setColor(Color.BLACK);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureW(widthMeasureSpec),measureH(heightMeasureSpec));
    }

    private int measureW(int measureSpec){
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode){
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.AT_MOST:
                return Math.min(size,width);
            case MeasureSpec.UNSPECIFIED:
                return width;
        }
        return 0;
    }

    private int measureH(int measureSpec){
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode){
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.AT_MOST:
                return Math.min(size,height);
            case MeasureSpec.UNSPECIFIED:
                return height;
        }
        return 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=oldw || h!=oldh){
            width = w;
            height = h;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mode==null || desBitmap == null || srcBitmap == null)return;

        canvas.drawText(mode.toString(),50,50,mPaint);
        //将绘制操作保存到新的图层（更官方的说法应该是离屏缓存）
        int sc = canvas.saveLayer(0,0,width,height,null,Canvas.ALL_SAVE_FLAG);
        // 先绘制目的图层Des
        canvas.drawBitmap(desBitmap,0,0,mPaint);
        // 设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(mode));
        // 再绘制src源图
        canvas.drawBitmap(srcBitmap,0,0,mPaint);
        // 还原混合模式
        mPaint.setXfermode(null);
        // 还原画布
        canvas.restoreToCount(sc);
    }

    public void setBitmap(Bitmap desB,Bitmap srcB){
        desBitmap = desB;
        srcBitmap = srcB;
        if(desBitmap!=null){
            width = desBitmap.getWidth();
            height = desBitmap.getHeight();
        }
        requestLayout();
    }

    public void setMode(PorterDuff.Mode mode){
        this.mode = mode;
    }

}
