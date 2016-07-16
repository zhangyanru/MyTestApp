package com.example.myapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by yanru.zhang on 16/7/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class MySaiBeiErView2 extends View {
    private Context mContext;
    private Paint myPaint;
    private Point p0 , p1, p2 ;
    private Path path;
    private int downX,downY,moveX,moveY,upX,upY;
    private Bitmap bitmap;
    private Bitmap paintBitmap;
    private int width,height;
    private PathMeasure pathMeasure;
    private float[] bitmapPosition = new float[2]; //触摸事件操作的时候bitmap的位置
    private boolean isAmin = false;
    private BitmapShader bitmapShader;

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
        myPaint.setStrokeWidth(10);
        myPaint.setColor(Color.WHITE);

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
        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setShader(null);
        canvas.drawRect(0,0,width,height,myPaint);

        //画曲线
        if(paintBitmap != null){
            bitmapShader = new BitmapShader(paintBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            myPaint.setShader(bitmapShader);
        }
        myPaint.setStyle(Paint.Style.STROKE);
        path.reset();
        path.moveTo(p0.x,p0.y);
        path.quadTo(p1.x,p1.y,p2.x,p2.y);
        canvas.drawPath(path,myPaint);

        if(!isAmin){
            pathMeasure = new PathMeasure(path,false);
            pathMeasure.getPosTan( pathMeasure.getLength()/2 , bitmapPosition,null);
        }

        //画icon
        if(bitmap!=null){
            canvas.drawBitmap(bitmap,
                    bitmapPosition[0] - bitmap.getWidth()/2,
                    bitmapPosition[1] - bitmap.getHeight()/2,
                    myPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("zyr","ACTION_DOWN");
                downX =(int)event.getRawX();
                downY =(int)event.getRawY();
                if(isAmin){
                    return false;
                }
                bitmapPosition[0] = downX;
                p1.x = downX;
                bitmapPosition[1] = downY;
                p1.y = downY;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d("zyr","ACTION_MOVE");

                moveX =(int)event.getRawX();
                moveY =(int)event.getRawY();
                bitmapPosition[0] = moveX;
                p1.x = moveX;
                bitmapPosition[1] = moveY;
                p1.y = moveY;
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.d("zyr","ACTION_CANCEL ACTION_UP");

                upX =(int)event.getRawX();
                upY =(int)event.getRawY();
                bitmapMove();

                break;
        }
        return super.onTouchEvent(event);
    }

    public void setPaintBitmap(Bitmap bitmap){
        if(bitmap == null) return;
        this.paintBitmap = bitmap;
        invalidate();
    }

    public void setBitmap(Bitmap bitmap){
        if(bitmap == null) return;
        this.bitmap = bitmap;
        invalidate();
    }

    private void bitmapMove(){
        if(bitmap == null) return;
        //得到p1在p0的上方还是下方

        if(p1.y - p0.y >=0){ //向上飞
            bitmapAnim((int) bitmapPosition[1],0);
        }else{//向下飞
            bitmapAnim((int) bitmapPosition[1],height);
        }
    }

    private void bitmapAnim(final int startY, final int endY){
        //计算斜率
        final float xBy = (float) Math.abs( width/2 - p1.x) / (float) Math.abs(p0.y - p1.y) ;
        Log.d("zyr","xBy :" + xBy);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startY,endY);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                if(value == startY){
                    isAmin = true;
                }
                if(value == endY){
                    isAmin = false;
                }
                bitmapPosition[1] = value;
                if( p1.x < width/2 ){
                    bitmapPosition[0] = p1.x + Math.abs(p1.y - value) * xBy;
                }else if( p1.x > width/2 ){
                    bitmapPosition[0] = p1.x - Math.abs(p1.y - value) * xBy;
                }else{
                    bitmapPosition[0] = (p0.x+p2.x)/2 ;
                }
                invalidate();
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
    }
}
