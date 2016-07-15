package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yanru.zhang on 16/7/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class MySaiBeiErView extends View {
    private Context mContext;
    private Paint myPaint;
    private Point p0 , p1, p2 , p3;
    private Path path2;
    private Path path3;

    public MySaiBeiErView(Context context) {
        this(context,null);
    }

    public MySaiBeiErView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySaiBeiErView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        myPaint = new Paint();
        myPaint.setAntiAlias(true);
        myPaint.setStrokeWidth(5);

        path2 = new Path();
        path3 = new Path();

        p0 = new Point(200,200);
        p1 = new Point(250,50);
        p2 = new Point(400,30);
        p3 = new Point(500,200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Android在API=1的时候就提供了贝塞尔曲线的画法，只是隐藏在Path#quadTo()和Path#cubicTo()方法中，一个是二阶贝塞尔曲线，一个是三阶贝塞尔曲线。
        myPaint.setColor(Color.WHITE);
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,0,1000,600,myPaint);

        myPaint.setColor(Color.RED);
        myPaint.setStyle(Paint.Style.STROKE);

        path2.reset();
        path2.moveTo(p0.x,p0.y);
        path2.quadTo(p1.x,p1.y,p3.x,p3.y);
        canvas.drawPath(path2,myPaint);

        canvas.save();
        canvas.translate(0,200);
        path3.reset();
        path3.moveTo(p0.x,p0.y);
        path3.cubicTo(p1.x,p1.y,p2.x,p2.y,p3.x,p3.y);
        canvas.drawPath(path3,myPaint);
        canvas.restore();
    }
}
