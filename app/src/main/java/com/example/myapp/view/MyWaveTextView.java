package com.example.myapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-3-2
 * Time: 下午4:33
 * Email: yanru.zhang@renren-inc.com
 */
public class MyWaveTextView extends TextView {
    private Paint mPaint;
    private int mWidth,mHeight,translateY,translateX;
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private Matrix matrix;
    private Drawable wave;
    private boolean isWave = false;

    public MyWaveTextView(Context context) {
        this(context, null);
    }

    public MyWaveTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWaveTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=oldw || h!=oldh){
            isWave = true;
            mPaint = getPaint();
            mPaint.setColor(getCurrentTextColor());
            mWidth = w;
            mHeight = h;
            translateX = 0;
            translateY = 0;
            matrix = new Matrix();
            if (wave == null) {
                wave = getResources().getDrawable(R.drawable.wave);
            }

            int waveW = wave.getIntrinsicWidth();
            int waveH = wave.getIntrinsicHeight();

            Bitmap b = Bitmap.createBitmap(waveW, waveH, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);

            c.drawColor(getCurrentTextColor());

            wave.setBounds(0, 0, waveW, waveH);
            wave.draw(c);

            mBitmapShader = new BitmapShader(b, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
            getPaint().setShader(mBitmapShader);

            mPaint.setShader(mBitmapShader);
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(isWave){
            if(translateY < -mHeight){
                translateY = 0;
            }else{
                translateY = translateY - mHeight/200;
            }

            translateX = translateX + mWidth/100;

            matrix.setTranslate(translateX,translateY);

            mBitmapShader.setLocalMatrix(matrix);
            postInvalidateDelayed(1000);
        }

    }
}
