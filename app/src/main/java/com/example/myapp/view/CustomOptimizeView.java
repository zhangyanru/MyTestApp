package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by yanru.zhang on 16/7/18.
 * Email:yanru.zhang@renren-inc.com
 */
public class CustomOptimizeView extends View implements View.OnClickListener{
    private Context mContext;
    private Paint mPaint;
    private Path mPath;
    private Matrix matrix;
    private int width,height;

    public CustomOptimizeView(Context context) {
        this(context,null);
    }

    public CustomOptimizeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomOptimizeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
        matrix = new Matrix();
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //优化onDraw的几种方法：
        //1.减少invalidate()方法的调用
        //2.尽量调用invalidate(Rect dirty),invalidate(int l,int t,int r,int b)方法
        //3.减少onDraw中的内存分配
        mPaint.setColor(Color.RED);
        mPaint.setAlpha(new Random().nextInt(255));
        canvas.drawRect(0,0,width,height,mPaint);
        mPaint.setColor(Color.CYAN);
        canvas.drawPath(mPath,mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w != oldw || h!= oldh){
            mPath.addCircle(w/2,h/2,200, Path.Direction.CCW);
            width = w;
            height = h;
        }
    }

    @Override
    public void onClick(View v) {
        matrix.postTranslate(20,20);
        mPath.transform(matrix);
        invalidate(width/2 - 200,height/2-200,width/2+200,height/2+200);
    }
}
