package com.example.myapp.Model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

/**
 * Created by zyr
 * DATE: 16-2-23
 * Time: 下午3:15
 * Email: yanru.zhang@renren-inc.com
 */
public class Snow {
    public int w;
    public int h;
    public Paint paint;
    public float x;
    public float y;
    public float move;
    public float angle;//45-135
    public float r;
    public boolean isReset = false;
    public Bitmap mBitmap;

    Random random = new Random();

    public final static float SNOW_R_MAX = 10f;
    public final static float SNOW_R_MIN = 2f;
    public final static float IMAGE_WIDTH_MAX = 160F;
    public final static float IMAGE_WIDTH_MIN = 60F;
    public final static float SNOW_ANGLE_MAX = 110f;
    public final static float SNOW_ANGLE_MIN = 80f;
    public final static float SNOW_MOVE_MAX = 20f;
    public final static float SNOW_MOVE_MIN = 2f;
    public final static float SNOW_MOVE_PRE_Y = 100f;


    public Snow(int w,int h,Paint paint){
        this(w, h, paint, null);
    }

    public Snow(int w,int h,Paint paint,Bitmap bitmap){
        this.w = w;
        this.h = h+1;
        this.paint = paint;
        if(bitmap!=null){
            this.mBitmap = bitmap;
        }

        init();
    }

    private void init(){
        x = random.nextInt(w);
        if(isReset){
            y = - random.nextFloat() * SNOW_MOVE_PRE_Y;
        }else{
            y = random.nextInt(h);
        }

        move = random.nextFloat() * (SNOW_MOVE_MAX-SNOW_MOVE_MIN) + SNOW_MOVE_MIN;
        angle = random.nextFloat() * (SNOW_ANGLE_MAX-SNOW_ANGLE_MIN) + SNOW_ANGLE_MIN;
        if(mBitmap !=null){
            r = random.nextFloat() * (IMAGE_WIDTH_MAX - IMAGE_WIDTH_MIN) + IMAGE_WIDTH_MIN;
        }else{
            r = random.nextFloat() * (SNOW_R_MAX - SNOW_R_MIN) + SNOW_R_MIN;
        }
    }

    public void draw(Canvas canvas) {
        move();
        if(mBitmap !=null){
            RectF rectF = new RectF(x,y,x+r,y+r);
            canvas.drawBitmap(mBitmap,null,rectF,paint);
        }else{
            canvas.drawCircle(x, y, r,paint);
        }
    }

    private void move() {
        if(x > w || y > h) {
            reset();
            return;
        }
        x = x + (int) (Math.cos(2*Math.PI/360*angle)*move);
        y = y + (int) (Math.sin(2*Math.PI/360*angle)*move);
    }

    private void reset() {
        isReset = true;
        init();
    }
}
