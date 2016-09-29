package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yanru.zhang on 16/9/20.
 * Email:yanru.zhang@renren-inc.com
 */
public class DanMuVIew2 extends SurfaceView implements SurfaceHolder.Callback{
    private SurfaceHolder surfaceHolder;
    public boolean isRun;
    private int width,height;
    private Paint paint = new Paint();
    private Random random = new Random();
    private Thread thread = new Thread(){
        @Override
        public void run() {
            while (isRun&&width!=0&&height!=0){
                Log.d("zyr","danm run" + Thread.currentThread());

                Canvas canvas = surfaceHolder.lockCanvas();
                if(canvas == null)return;
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                synchronized (infoList) {
                    for(int i=infoList.size()-1;i>=0;i--){
                        DanMuInfo danMuInfo = infoList.get(i);
                        danMuInfo.x -= danMuInfo.v;
                        //如果滚动到屏幕外，就把改弹幕删除
                        if(danMuInfo.x <=0){
                            infoList.remove(i);
                            continue;
                        }
                        paint.setTextSize(danMuInfo.textSize);
                        canvas.drawText(danMuInfo.data,danMuInfo.x,danMuInfo.y,paint);
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    };
    private List<DanMuInfo> infoList = new ArrayList<>();

    public void addInfoList(List<DanMuInfo> infoList) {
        synchronized (this.infoList){
            for(int i=0;i<infoList.size();i++){
                DanMuInfo info = infoList.get(i);
                info.x = width + random.nextInt(40) + 10;
                info.y = random.nextInt(height - 10) + 10;
                info.v = random.nextInt(20) + 10;
                info.textSize = random.nextInt(20) + 10;
                this.infoList.add(info);
            }
        }
    }

    public DanMuVIew2(Context context) {
        this(context,null);
    }

    public DanMuVIew2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DanMuVIew2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setColor(Color.GRAY);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("zyr","surfaceCreated");

        isRun = true;
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("zyr","surfaceChanged");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("zyr","surfaceDestroyed");

        isRun = false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d("zyr","onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=oldw || h!=oldh){
            width = w;
            height = h;
        }
    }
}
