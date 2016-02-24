package com.example.myapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.myapp.Model.Snow;

/**
 * Created by zyr
 * DATE: 16-2-23
 * Time: 下午3:07
 * Email: yanru.zhang@renren-inc.com
 * http://ndquangr.blogspot.jp/2013/04/android-view-lifecycle.html
 * android view有以下14个周期：
 * View 的关键生命周期为
 * [改变可见性]
 * --> 构造View
 * -->  onFinishInflate
 * -->  onAttachedToWindow
 * -->  onMeasure
 * -->  onSizeChanged
 * -->  onLayout
 * -->  onDraw
 * -->  onDetackedFromWindow
 *
 *    
 *
     1、onFinishInflate() 当View中所有的子控件均被映射成xml后触发 。
     2、onMeasure( int , int ) 确定所有子元素的大小 。
     3、onLayout( boolean , int , int , int , int ) 当View分配所有的子元素的大小和位置时触发 。
     4、onSizeChanged( int , int , int , int ) 当view的大小发生变化时触发 。
     5、onDraw(Canvas) view渲染内容的细节。
     6、onKeyDown( int , KeyEvent) 有按键按下后触发 。
     7、onKeyUp( int , KeyEvent) 有按键按下后弹起时触发 。
     8、onTrackballEvent(MotionEvent) 轨迹球事件 。
     9、onTouchEvent(MotionEvent) 触屏事件 。
     10、onFocusChanged( boolean , int , Rect) 当View获取或失去焦点时触发 。
     11、onWindowFocusChanged( boolean ) 当窗口包含的view获取或失去焦点时触发 。
     12、onAttachedToWindow() 当view被附着到一个窗口时触发 。
     13、onDetachedFromWindow() 当view离开附着的窗口时触发，Android123提示该方法和 onAttachedToWindow() 是相反的。
     14、onWindowVisibilityChanged( int ) 当窗口中包含的可见的view发生变化时触发。
 */
public class SnowView extends View {
    private AnimThread animThread;
    private Paint mPaint;
    private Context mContext;
    private Snow[] snows = new Snow[SNOW_COUNT];
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int w, h;

    public final static int SNOW_COUNT = 300;
    public final static long DELAY = 30;

    public SnowView(Context context) {
        this(context, null);
    }

    public SnowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public void setBitmap(Bitmap bitmap){
        mBitmap = null;
        if(bitmap != null){
            mBitmap = bitmap;
        }
        init();
        resize(w,h);
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        if(mBitmap!=null){
            mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(mBitmapShader);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=oldw || h!=oldh){
            this.w = w;
            this.h = h;
            resize(w,h);
        }
    }

    private void resize(int w, int h) {
        Log.e("zyr","w:" + w + "    h:" + h);
        snows = new Snow[SNOW_COUNT];
        for(int i=0;i<SNOW_COUNT;i++){
            Snow snow = new Snow(w,h,mPaint,mBitmap);
            snows[i] = snow;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0;i<snows.length;i++){
            snows[i].draw(canvas);
        }
        if (animThread == null) {
            animThread = new AnimThread();
            animThread.start();
        }
    }

    class AnimThread extends Thread {
        @Override
        public void run() {
            while(true) {
                //延迟
                try {
                    Thread.sleep(DELAY);
                    postInvalidate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
