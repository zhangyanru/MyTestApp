package com.example.myapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.example.myapp.R;

/**
 * Created by admin on 15/10/20.
 */
public class ColorChangedCircleView extends View {
    private int firstColor;
    private int secondColor;
    private int speed;
    private float circleWidth;
    private Paint paint;
    private float progress;

    private final static int STATE_ONE = 0;
    private final static int STATE_TWO = 1;

    private int state;
    public ColorChangedCircleView(Context context) {
        this(context, null);
    }

    public ColorChangedCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorChangedCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        state = STATE_ONE;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorChangedCircleView);
        for(int i=0;i<array.length();i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.ColorChangedCircleView_firstColor:
                    firstColor = array.getColor(R.styleable.ColorChangedCircleView_firstColor,Color.RED);
                    break;
                case R.styleable.ColorChangedCircleView_secondColor:
                    secondColor = array.getColor(R.styleable.ColorChangedCircleView_secondColor, Color.BLUE);
                    break;
                case R.styleable.ColorChangedCircleView_speed:
                    speed = array.getInt(R.styleable.ColorChangedCircleView_speed,20);
                    break;
                case R.styleable.ColorChangedCircleView_circleWidth:
                    circleWidth = array.getDimension(R.styleable.ColorChangedCircleView_circleWidth,20);
                    break;
            }
        }
        array.recycle();
        new Thread(){
            @Override
            public void run() {
                while (true){
                    progress++;
                    if(progress ==360){
                        progress =0;
                        if(state ==STATE_ONE){
                            state = STATE_TWO;
                        }else if(state ==STATE_TWO){
                            state = STATE_ONE;
                        }
                    }
                    Log.i("me",progress+"");
                    System.out.println(progress+"******************");
                    postInvalidate();
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void initPaint(){
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = getWidth()/2;
        float y = getHeight()/2;
        float r = getWidth()/2-circleWidth/2;
        paint.setStrokeWidth(circleWidth);
        RectF oval = new RectF(0+circleWidth/2, y-r, getWidth()-circleWidth/2, y+r);
        if(state ==STATE_ONE){
            paint.setColor(firstColor);
            canvas.drawCircle(x, y, r, paint);
            paint.setColor(secondColor);
            canvas.drawArc(oval, -90, progress, false, paint);
        }else if(state ==STATE_TWO){
            paint.setColor(secondColor);
            canvas.drawCircle(x, y, r, paint);
            paint.setColor(firstColor);
            canvas.drawArc(oval,-90,progress,false,paint);
        }
    }
}
