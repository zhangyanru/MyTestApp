package com.example.myapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 15-11-16
 * Time: 下午8:47
 * Email: yanru.zhang@renren-inc.com
 */
public class RoundProgressBar extends View {
    int firstColor;
    int secondColor;
    float circleWidth;
    int textColor;
    float textSize;
    float progress;
    Context context;
    Paint paint;
    public RoundProgressBar(Context context) {
        this(context,null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    public void init( AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        for(int i=0;i<array.length();i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.RoundProgressBar_p_firstColor:
                    firstColor = array.getColor(R.styleable.RoundProgressBar_p_firstColor, Color.RED);
                    break;
                case R.styleable.RoundProgressBar_p_secondColor:
                    secondColor = array.getColor(R.styleable.RoundProgressBar_p_secondColor, Color.BLUE);
                    break;
                case R.styleable.RoundProgressBar_p_circleWidth:
                    circleWidth = array.getDimension(R.styleable.RoundProgressBar_p_circleWidth, 100);
                    break;
                case R.styleable.RoundProgressBar_p_textColor:
                    textColor = array.getColor(R.styleable.RoundProgressBar_p_textColor, 100);
                    break;
                case R.styleable.RoundProgressBar_p_textSize:
                    textSize = array.getDimension(R.styleable.RoundProgressBar_p_textSize, 100);
                    break;
                case R.styleable.RoundProgressBar_p_progress:
                    progress = array.getDimension(R.styleable.RoundProgressBar_p_progress,100);
                    break;
            }
        }
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = getWidth()/2;
        float y = getHeight()/2;
        float r = getWidth()/2-circleWidth/2;
        paint.setStrokeWidth(circleWidth);
        RectF oval = new RectF(0+circleWidth/2, y-r, getWidth()-circleWidth/2, y+r);
        paint.setColor(firstColor);
        canvas.drawCircle(x, y, r, paint);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        float textWidth = paint.measureText(progress + "%");
        canvas.drawText(progress + "%", x-textWidth/2, y, paint);
        paint.setColor(secondColor);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, -90, progress/100*360, false, paint);
    }

    public void setProgress(float progress){
        this.progress = progress;
        postInvalidate();
    }
}
