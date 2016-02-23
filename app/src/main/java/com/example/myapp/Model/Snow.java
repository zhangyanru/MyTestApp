package com.example.myapp.Model;

import android.graphics.Canvas;
import android.graphics.Paint;

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

    Random random = new Random();

    public final static float SNOW_R_MAX = 10f;
    public final static float SNOW_R_MIN = 2f;
    public final static float SNOW_ANGLE_MAX = 110f;
    public final static float SNOW_ANGLE_MIN = 80f;
    public final static float SNOW_MOVE_MAX = 10f;
    public final static float SNOW_MOVE_MIN = 2f;
    public final static float SNOW_MOVE_PRE_Y = 100f;


    public Snow(int w,int h,Paint paint){
        this.w = w;
        this.h = h+1;
        this.paint = paint;
        
        x = random.nextInt(w);
        y = random.nextInt(h);
        move = random.nextFloat() * (SNOW_MOVE_MAX-SNOW_MOVE_MIN) + SNOW_MOVE_MIN;
        angle = random.nextFloat() * (SNOW_ANGLE_MAX-SNOW_ANGLE_MIN) + SNOW_ANGLE_MIN;
        r = random.nextFloat() * (SNOW_R_MAX - SNOW_R_MIN) + SNOW_R_MIN;
    }

    public void draw(Canvas canvas) {
        move();
        canvas.drawCircle(x, y, r,paint);
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
        x = random.nextInt(w);
        y = -random.nextFloat() * SNOW_MOVE_PRE_Y;
        move = random.nextFloat() * (SNOW_MOVE_MAX-SNOW_MOVE_MIN) + SNOW_MOVE_MIN;
        angle = random.nextFloat() * (SNOW_ANGLE_MAX-SNOW_ANGLE_MIN) + SNOW_ANGLE_MIN;
        r = random.nextFloat() * (SNOW_R_MAX - SNOW_R_MIN) + SNOW_R_MIN;
    }
}
