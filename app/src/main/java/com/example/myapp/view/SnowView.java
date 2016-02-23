package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.myapp.Model.Snow;
import com.example.myapp.Model.SnowFlake;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-2-23
 * Time: 下午3:07
 * Email: yanru.zhang@renren-inc.com
 */
public class SnowView extends View {
    private Paint mPaint;
    private Context mContext;
    private Snow[] snows = new Snow[SNOW_COUNT];

    public final static int SNOW_COUNT = 300;
    public final static long DELAY = 5;

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

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=oldw || h!=oldh){
            resize(w,h);
        }
    }

    private void resize(int w, int h) {
        snows = new Snow[SNOW_COUNT];
        for(int i=0;i<SNOW_COUNT;i++){
            Snow snow = new Snow(w,h,mPaint);
            snows[i] = snow;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0;i<snows.length;i++){
            snows[i].draw(canvas);
        }
        getHandler().postDelayed(runnable, DELAY);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };
}
