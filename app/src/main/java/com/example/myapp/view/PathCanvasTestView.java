package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zyr
 * DATE: 16-3-7
 * Time: 上午10:56
 * Email: yanru.zhang@renren-inc.com
 */
public class PathCanvasTestView extends View {
    private Paint mPaint;
    private Path mPath;
    public PathCanvasTestView(Context context) {
        this(context, null);
    }

    public PathCanvasTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathCanvasTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(40);
        mPath = new Path();

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭硬件加速

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(800,800);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        // save：用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
        //restore：用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
        //save和restore要配对使用(restore可以比save少，但不能多)，如果restore调用次数比save多，会引发Error。save和restore之间，往往夹杂的是对Canvas的特殊操作。
        canvas.save();
        canvas.clipRect(0, 0, 300, 400);
        mPath.addCircle(100, 100, 100, Path.Direction.CW);
        mPath.addRect(new RectF(100, 100, 300, 200), Path.Direction.CW);
        //WINDING            (0),默认
        //EVEN_ODD           (1),
        //INVERSE_WINDING    (2),
        //INVERSE_EVEN_ODD   (3),
        mPath.setFillType(Path.FillType.WINDING);
        canvas.drawPath(mPath, mPaint);
        canvas.drawText("WINDING", 50, 300, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(400, 0);
        canvas.clipRect(0, 0, 300, 400);
        mPath.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawPath(mPath, mPaint);
        canvas.drawText("EVEN_ODD", 50, 300, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 400);
        canvas.clipRect(0, 0, 300, 400);
        mPath.setFillType(Path.FillType.INVERSE_WINDING);
        canvas.drawPath(mPath, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawText("INVERSE_WINDING", 0, 300, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(400, 400);
        canvas.clipRect(0, 0, 300, 400);
        mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        mPaint.setColor(Color.RED);
        canvas.drawPath(mPath, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawText("INVERSE_EVEN_ODD", 0, 300, mPaint);
        canvas.restore();

        mPaint.setColor(Color.RED);
    }
}
