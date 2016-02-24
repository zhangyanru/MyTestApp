package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by zyr
 * DATE: 16-2-23
 * Time: 下午6:41
 * Email: yanru.zhang@renren-inc.com
 */
public class DanMuView extends TextView{
    private Context mContext;
    private Paint mPaint;
    private int mColor;
    private int mTextSize;
    private int moveX;
    private int x,y;
    private int w,h;

    public final static int TEXT_SIZE_MAX = 60;
    public final static int TEXT_SIZE_MIN = 30;
    public final static int TEXT_MOVE_X_MAX = 20;
    public final static int TEXT_MOVE_X_MIN = 5;

    Random random = new Random();

    public DanMuView(Context context) {
        this(context, null);
    }

    public DanMuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DanMuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w!=oldw || h!=oldh){
            this.w = w;
            this.h = h;
            init();
        }
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mTextSize = random.nextInt(TEXT_SIZE_MAX-TEXT_SIZE_MIN) + TEXT_SIZE_MIN;
        mPaint.setTextSize(mTextSize);

        mColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        mPaint.setColor(mColor);

        x = random.nextInt(w);
        y = random.nextInt(h);
        moveX =(int) (random.nextFloat() * (TEXT_MOVE_X_MAX - TEXT_MOVE_X_MIN) + TEXT_MOVE_X_MIN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        move();
        canvas.drawText(getText().toString(),x,y,mPaint);

        postInvalidateDelayed(10);
    }

    private void move() {
        if(isInSide()){
            x = x - moveX;
        }else{
            reset();
        }

    }

    private void reset(){
        init();
    }

    private boolean isInSide(){
        if(x + mPaint.measureText(getText().toString())<0 || x > w ){
            return false;
        }else{
            return true;
        }
    }
}
