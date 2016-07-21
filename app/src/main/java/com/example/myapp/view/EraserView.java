package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yanru.zhang on 16/7/21.
 * Email:yanru.zhang@renren-inc.com
 */
public class EraserView extends View {
    private int width,height;
    private Paint mPaint;
    private PorterDuffXfermode porterDuffXfermode;
    private Path mPath;
    private int downX,downY,moveX,moveY,lastX,lastY,upX,upY;
    private int bgColor = Color.GRAY;
    private int paintWidth = 50;

    public EraserView(Context context) {
        this(context,null);
    }

    public EraserView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EraserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(paintWidth);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=oldw || h!=oldh){
            width = w;
            height = h;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(bgColor);
        mPaint.setXfermode(porterDuffXfermode);
        canvas.drawPath(mPath,mPaint);
        mPaint.setXfermode(null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = downX = (int)event.getX();
                lastY = downY = (int)event.getY();
                mPath.moveTo(downX,downY);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int)event.getX();
                moveY = (int)event.getY();
                mPath.quadTo((lastX+moveX)/2,(lastY+moveY)/2,moveX,moveY);
                invalidate();
                lastX = moveX;
                lastY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                upX = (int)event.getX();
                upY = (int)event.getY();
                mPath.quadTo(lastX,lastY,upX,upY);
                invalidate();
                break;
        }
        return true;
    }

    public void setPaintWidth(int width){
        this.paintWidth = width;
        mPaint.setStrokeWidth(width);
    }

    public void setBgColor(int color){
        bgColor = color;
    }
}
