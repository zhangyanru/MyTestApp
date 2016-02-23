package com.example.myapp.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.example.myapp.Model.Ball;
import com.example.myapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by zyr
 * DATE: 16-2-19
 * Time: 上午10:51
 * Email: yanru.zhang@renren-inc.com
 */
public class LiziCopyView extends View {
    private Paint mPaint;
    private static final Canvas mCanvas = new Canvas();
    private Bitmap mBitmap;
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private AnimatorSet animAnimatorSet ;
    private ArrayList<Animator> animatorArrayList = new ArrayList<Animator>();

    private View mView;
    public static final int ANIMA_DURA = 500; //动画持续时间
    public static final int ANIMA_START_DELAY = 60; //动画延迟播放时间
    public static final int PART_WH = 20; //默认小球宽高
    private int counts,wCount,hCount;

    public boolean isAnimRunning() {
        return isAnimRunning;
    }

    private boolean isAnimRunning = false;
    Random random = new Random();

    public void setAnimListener(AnimListener animListener) {
        this.animListener = animListener;
    }

    private AnimListener animListener;
    public interface AnimListener{
        public void onAnimStart();
        public void onAnimEnded();

    }

    public void setmView(View mView) {
        this.mView = mView;
        requestLayout();
        doExplode();
    }
    public LiziCopyView(Context context) {
        this(context, null);
    }

    public LiziCopyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiziCopyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    private void doExplode(){
        mBitmap = createBitmapFromView(mView);
        Rect rect = new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight());
        int[] countss = getBallCounts(rect);
        wCount = countss[0];
        hCount = countss[1];
        counts = countss[2];
        Log.e("zyr", "width:" + mBitmap.getWidth() + " height:" + mBitmap.getHeight());
        Log.e("zyr", "counts:" + counts + " wCount:" + wCount + "   hCount:" + hCount);
        for(int i=0;i<hCount;i++){
            for(int j=0;j<wCount;j++){
                Ball ball = new Ball();
                //取得当前粒子所在位置的颜色
//                Log.e("zyr", "j * PART_WH:" + j * PART_WH + " i * PART_WH:" + i * PART_WH);
                ball.color = mBitmap.getPixel(j * PART_WH , i * PART_WH );

                ball.left = PART_WH * j;
                ball.top = PART_WH * i;
                ball.right = PART_WH + ball.left;
                ball.bottom = PART_WH + ball.top;
                ball.cx = PART_WH/2 + ball.left;
                ball.cy = PART_WH/2 + ball.top;
                ball.radius = PART_WH/2;
                ball.alpha = 255;
                balls.add(ball);
            }
        }
        invalidate();

        animAnimatorSet = new AnimatorSet();
        animatorArrayList.clear();
        for(int i=0;i<balls.size();i++){
            final Ball ball = balls.get(i);

            ValueAnimator valueAnimator = ValueAnimator.ofInt(255, 0).setDuration(ANIMA_DURA);
            valueAnimator.setInterpolator(new AccelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
                    ball.alpha = value;
                    ball.cx = ball.cx + Math.abs(random.nextInt()) % 20;
                    ball.cy = ball.cy + Math.abs(random.nextInt()) % 100;
                    invalidate();
                }
            });

            animatorArrayList.add(valueAnimator);
        }
        animAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimRunning = true;
                if (animListener != null) {
                    animListener.onAnimStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("zyr","onAnimationEnd");
                balls.clear();
                animatorArrayList.clear();
                isAnimRunning = false;
                if (animListener != null) {
                    animListener.onAnimEnded();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimRunning = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animAnimatorSet.setStartDelay(ANIMA_START_DELAY);
        animAnimatorSet.playTogether(animatorArrayList);
        animAnimatorSet.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mView ==null){
            setMeasuredDimension(0, 0);
        }else{
            setMeasuredDimension(mView.getWidth()*2, mView.getHeight()*3);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0;i<balls.size();i++){
//            Log.d("zyr", "onDraw i :" + i);
            Ball ball = balls.get(i);
            mPaint.setColor(ball.color);
            mPaint.setAlpha(ball.alpha);
            canvas.drawCircle(ball.cx,ball.cy,ball.radius,mPaint);
        }
    }

    private Bitmap createBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        if (bitmap != null) {
            synchronized (mCanvas) {
                mCanvas.setBitmap(bitmap);
                view.draw(mCanvas);
                mCanvas.setBitmap(null); //清除引用
            }
        }
        return bitmap;
    }
    /**
     * 根据view的宽高，算出横竖粒子的个数
     * @return
     */
    private int[] getBallCounts(Rect bound){
        int w = bound.width();
        int h = bound.height();

        int partW_Count = w / PART_WH; //横向个数
        int partH_Count = h / PART_WH; //竖向个数
        return new int[]{partW_Count,partH_Count,partW_Count * partH_Count};
    }
}
