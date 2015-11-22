package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.graphics.*;
import android.content.Context;

/**
 * Created by admin on 15/9/8.
 */
public class PictureTestActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(new SampleView(this));
    }
    private static class SampleView extends View {
        private Paint mPaint;
        private Path mPath;
        public SampleView(Context context) {
            super(context);
            setFocusable(true);

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(6);
            mPaint.setTextSize(16);
            mPaint.setTextAlign(Paint.Align.RIGHT);

            mPath = new Path();
        }

        private void drawScene(Canvas canvas) {
            canvas.clipRect(0, 0, 100, 100);

            canvas.drawColor(Color.WHITE);

            mPaint.setColor(Color.RED);
            canvas.drawLine(0, 0, 100, 100, mPaint);

            mPaint.setColor(Color.GREEN);
            canvas.drawCircle(30, 70, 30, mPaint);

            mPaint.setColor(Color.BLUE);
            canvas.drawText("Clipping", 100, 30, mPaint);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.GRAY);
            canvas.save();
            canvas.translate(10, 10);

            canvas.clipRect(0, 0, 100, 100);
            canvas.drawColor(Color.WHITE);
            mPaint.setColor(Color.RED);
            canvas.drawLine(0, 0, 100, 100, mPaint);
            mPaint.setColor(Color.GREEN);
            canvas.drawCircle(30, 70, 30, mPaint);
            mPaint.setColor(Color.BLUE);
            canvas.drawText("Clipping", 100, 30, mPaint);

            canvas.restore();

            canvas.save();
            canvas.translate(160, 10);
            canvas.clipRect(10, 10, 90, 90);
            canvas.clipRect(30, 30, 70, 70, Region.Op.DIFFERENCE);//第一次不同于第二次的部分显示出来

            canvas.clipRect(0, 0, 100, 100);
            canvas.drawColor(Color.WHITE);
            mPaint.setColor(Color.RED);
            canvas.drawLine(0, 0, 100, 100, mPaint);
            mPaint.setColor(Color.GREEN);
            canvas.drawCircle(30, 70, 30, mPaint);
            mPaint.setColor(Color.BLUE);
            canvas.drawText("Clipping", 100, 30, mPaint);

            canvas.restore();

            canvas.save();
            canvas.translate(10, 160);
            mPath.reset();
            canvas.clipPath(mPath); // makes the clip empty
            mPath.addCircle(50, 50, 50, Path.Direction.CCW);
            canvas.clipPath(mPath, Region.Op.REPLACE);//是显示第二次的
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(160, 160);
            canvas.clipRect(0, 0, 60, 60);
            canvas.clipRect(40, 40, 100, 100, Region.Op.UNION);//全部显示
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(10, 310);
            canvas.clipRect(0, 0, 60, 60);
            canvas.clipRect(40, 40, 100, 100, Region.Op.XOR);//补集 就是全集的减去交集生育部分显示
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(160, 310);
            canvas.clipRect(0, 0, 60, 60);
            canvas.clipRect(40, 40, 100, 100, Region.Op.REVERSE_DIFFERENCE);//是第二次不同于第一次的部分显示
            drawScene(canvas);
            canvas.restore();
        }
    }
}