package com.example.myapp.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by yanru.zhang on 16/7/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class MySaiBeiErView2 extends View {
    private Context mContext;
    private Paint myPaint;
    private Point p0 , p1, p2 ,bitmapPoint;
    private Path path;
    private int downX,downY,moveX,moveY,upX,upY;
    private Bitmap bitmap;
    private int width,height;
    public MySaiBeiErView2(Context context) {
        this(context,null);
    }

    public MySaiBeiErView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySaiBeiErView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext =  context;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        myPaint = new Paint();
        myPaint.setAntiAlias(true);
        myPaint.setStrokeWidth(5);

        path = new Path();

        p0 = new Point(300,height-600);
        p1 = new Point(width/2,height-300);
        p2 = new Point(width-300,height-600);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // MeasureSpec有3种模式分别是UNSPECIFIED, EXACTLY和AT_MOST,
        // 那么这些模式和我们平时设置的layout参数fill_parent, wrap_content有什么关系呢。经过代码测试就知道，
        // 当我们设置width或height为fill_parent时，容器在布局时调用子 view的measure方法传入的模式是EXACTLY，因为子view会占据剩余容器的空间，所以它大小是确定的。
        // 而当设置为 wrap_content时，容器传进去的是AT_MOST, 表示子view的大小最多是多少，这样子view会根据这个上限来设置自己的尺寸。
        // 当子view的大小设置为精确值时，容器传入的是EXACTLY, 而MeasureSpec的UNSPECIFIED模式目前还没有发现在什么情况下使用。
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("zyr","onDraw");

//      Android在API=1的时候就提供了贝塞尔曲线的画法，只是隐藏在Path#quadTo()和Path#cubicTo()方法中，一个是二阶贝塞尔曲线，一个是三阶贝塞尔曲线。

        //画背景
        myPaint.setColor(Color.WHITE);
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,0,width,height,myPaint);

        myPaint.setStyle(Paint.Style.STROKE);

        //画点组成的线
        myPaint.setColor(Color.LTGRAY);
        canvas.drawLine(p0.x,p0.y,p1.x,p1.y,myPaint);
        canvas.drawLine(p1.x,p1.y,p2.x,p2.y,myPaint);
        canvas.drawLine(p2.x,p2.y,p0.x,p0.y,myPaint);

        //画曲线
        myPaint.setColor(Color.RED);
        path.reset();
        path.moveTo(p0.x,p0.y);
        path.quadTo(p1.x,p1.y,p2.x,p2.y);
        canvas.drawPath(path,myPaint);

        //画icon
        if(bitmap!=null){
            canvas.drawBitmap(bitmap,bitmapPoint.x,bitmapPoint.y,myPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("zyr","ACTION_DOWN");
                downX =(int)event.getRawX();
                downY =(int)event.getRawY();
                p1.x = bitmapPoint.x = downX;
                p1.y = bitmapPoint.y = downY;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d("zyr","ACTION_MOVE");

                moveX =(int)event.getRawX();
                moveY =(int)event.getRawY();
                p1.x = bitmapPoint.x = moveX;
                p1.y = bitmapPoint.y = moveY;
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.d("zyr","ACTION_CANCEL ACTION_UP");

                upX =(int)event.getRawX();
                upY =(int)event.getRawY();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setBitmap(Bitmap bitmap){
        if(bitmap == null) return;
        this.bitmap = bitmap;
        bitmapPoint = new Point(width-bitmap.getWidth()/2,height/2);
        invalidate();
    }
}
