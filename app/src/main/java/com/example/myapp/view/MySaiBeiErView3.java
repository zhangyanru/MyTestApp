package com.example.myapp.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
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
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by yanru.zhang on 16/7/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class MySaiBeiErView3 extends View {
    //二次赛贝尔曲线方程
    // x = (1-t) * (1-t) * p0x + 2 * t * (1-t) * p1x + t * t * p2x;
    // y = (1-t) * (1-t) * p0y + 2 * t * (1-t) * p1y + t * t * p2y;
    private Context mContext;
    private Paint myPaint ;
    private Point p0 ; //二次赛贝尔曲线的起点
    private Point p1 ; //二次赛贝尔曲线的控制点
    private Point p2 ; //二次赛贝尔曲线的终点
    private Point p ; //曲线上的一个点
    private Point bitmapP ; //图片的位置点
    private Point centerP;

    private Path path;
    private Path shootPath;
    private int downX,downY,moveX,moveY,upX,upY;
    private Bitmap normalAnzai , moveAnzai ;
    private Bitmap paintBitmap;
    private int width,height;
    private boolean isShootAnzai = false , isResetLine = false;
    private BitmapShader bitmapShader;
    private float t = 0.5f; //二次赛贝尔曲线的参数t

    public MySaiBeiErView3(Context context) {
        this(context,null);
    }

    public MySaiBeiErView3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySaiBeiErView3(Context context, AttributeSet attrs, int defStyleAttr) {
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
        shootPath = new Path();

        p0 = new Point(300,height-600);
        p1 = new Point( );
        p2 = new Point(width-300,height-600);
        p = new Point(width/2,height - 500);
        bitmapP = new Point();
        centerP = new Point(width/2,height - 600);
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

        //根据二次赛贝尔曲线方程计算控制点
        p1.x = (int) ( (p.x - (1-t) * (1-t) * p0.x - t * t * p2.x)/( 2 * t * ( 1-t )) );
        p1.y = (int) ( (p.y - (1-t) * (1-t) * p0.y - t * t * p2.y)/( 2 * t * ( 1-t )) );
        //根据起点，控制点，终点，绘制二次赛贝尔曲线
        path.reset();
        path.moveTo(p0.x,p0.y);
        path.quadTo(p1.x,p1.y,p2.x,p2.y);
        canvas.drawPath(path,myPaint);

        canvas.drawPoint(p1.x,p1.y,myPaint);

        if(!isShootAnzai && !isResetLine){
            //安仔的位置和p的位置一致
            bitmapP.x = p.x - normalAnzai.getWidth()/2;
            bitmapP.y = p.y - normalAnzai.getHeight()/2;
        }

        //画icon
        if(normalAnzai!=null && moveAnzai!=null){
            if(isShootAnzai){
                canvas.drawBitmap(moveAnzai,
                        bitmapP.x,
                        bitmapP.y,
                        myPaint);
            }else{
                canvas.drawBitmap(normalAnzai,
                        bitmapP.x,
                        bitmapP.y,
                        myPaint);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("zyr","ACTION_DOWN");
                downX =(int)event.getRawX();
                downY =(int)event.getRawY();
                if(isShootAnzai || isResetLine){
                    return false;
                }
                p.x = downX;
                p.y = downY;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d("zyr","ACTION_MOVE");

                moveX =(int)event.getRawX();
                moveY =(int)event.getRawY();
                p.x = moveX;
                p.y = moveY;
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

    public void setBitmap(Bitmap bitmap,Bitmap bitmap2){
        if(bitmap == null || bitmap2 == null) return;
        this.normalAnzai = bitmap;
        this.moveAnzai = bitmap2;
        invalidate();
    }

    private void bitmapMove(){
        if(moveAnzai == null) return;
        //得到p1在p0的上方还是下方

        if(bitmapP.y == centerP.y){
            return;
        }

        if(bitmapP.x < centerP.x){
            shootPath.reset();
            shootPath.moveTo(bitmapP.x,bitmapP.y);
            shootPath.lineTo( width, (width - bitmapP.x) * (centerP.y - bitmapP.y) / (centerP.x - bitmapP.x) + bitmapP.y);
        }else if(bitmapP.x > centerP.x){
            shootPath.reset();
            shootPath.moveTo(bitmapP.x,bitmapP.y);
            shootPath.lineTo(0, (0 - bitmapP.x) * (centerP.y - bitmapP.y) / (centerP.x - bitmapP.x) + bitmapP.y);
        }else{
            shootPath.reset();
            shootPath.moveTo(bitmapP.x,bitmapP.y);
            if(bitmapP.y > centerP.y){
                shootPath.lineTo(width/2,0);
            }else if(bitmapP.y < centerP.y){
                shootPath.lineTo(width/2,height);
            }
        }
        bitmapAnim();

    }

    private void bitmapAnim( ){
        final PathMeasure pathMeasure = new PathMeasure(shootPath,false);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,pathMeasure.getLength());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();

                float[] tempPoint = new float[2];
                pathMeasure.getPosTan(value,tempPoint,null);
                bitmapP.y = (int)tempPoint[1];
                bitmapP.x = (int) tempPoint[0];
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isShootAnzai = true;
                resetLineAnim();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isShootAnzai = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isShootAnzai = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
    }

    public void resetLineAnim(){
        ValueAnimator valueAnimatorX = ValueAnimator.ofInt(p.x,width/2);
        valueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                p.x = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimatorX.setDuration(200);
        valueAnimatorX.setInterpolator(new BounceInterpolator());

        ValueAnimator valueAnimatorY = ValueAnimator.ofInt(p.y,height - 500);
        valueAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                p.y = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimatorY.setDuration(200);
        valueAnimatorY.setInterpolator(new BounceInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isResetLine = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isResetLine = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isResetLine = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.playTogether(valueAnimatorX,valueAnimatorY);
        animatorSet.start();
    }
}
