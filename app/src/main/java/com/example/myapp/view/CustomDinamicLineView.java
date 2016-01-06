package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 15-12-17
 * Time: 下午4:32
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomDinamicLineView extends View {
    private Context mContext;
    private Paint mPaint;
    private ArrayList<PointF> mPointFs = new ArrayList<PointF>();
    public CustomDinamicLineView(Context context) {
        this(context, null);
        mContext = context;
    }

    public CustomDinamicLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;

    }

    public CustomDinamicLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mPointFs.size()>0){
            Path path = new Path();
            for(int i=0;i<mPointFs.size();i++){
                PointF pointF = mPointFs.get(i);
                canvas.drawPoint(pointF.x,pointF.y,mPaint);
                if(i ==0){
                    path.moveTo(pointF.x,pointF.y);
                }else{
                    path.lineTo(pointF.x,pointF.y);
                }
            }
            canvas.drawPath(path,mPaint);
        }
    }

    public void addPoint(int x,int y){
        PointF pointF = new PointF(x,y);
        mPointFs.add(pointF);
        postInvalidate();
    }
}
