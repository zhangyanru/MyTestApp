package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-1-22
 * Time: 下午5:33
 * Email: yanru.zhang@renren-inc.com
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private Context mContext;
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Paint mPaint;
    private int mText;
    private boolean isRuning = true;
    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        Log.d("zyr","init");
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(80);
    }

    private void draw() {
        try
        {
            synchronized (mSurfaceHolder){
                // 获得canvas
                mCanvas = mSurfaceHolder.lockCanvas();
                if (mCanvas != null)
                {
                    // drawSomething..
                    mCanvas.drawColor(Color.WHITE);
                    mCanvas.drawText(mText+"",100,100,mPaint);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null)
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);//结束锁定画图，并提交改变。
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("zyr","surfaceCreated");
        isRuning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRuning){
                    mText ++;
                    Log.d("zyr","running,mText:" + mText);
                    draw();
                    try {
                        Thread.sleep(500);//睡眠时间为1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("zyr","surfaceChanged");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("zyr","surfaceDestroyed");

        isRuning = false;
    }
}
