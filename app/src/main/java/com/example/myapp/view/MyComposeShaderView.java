package com.example.myapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-3-2
 * Time: 上午11:21
 * Email: yanru.zhang@renren-inc.com
 */
public class MyComposeShaderView extends View {
    private Paint mPaint,mPaint2;
    private int mWdith,mHeight,r,d;
    private ComposeShader mComposeShader;
    private BitmapShader mBitmapShader;
    private LinearGradient mLinearGradient;
    private SweepGradient mSweepGradient;
    private Bitmap mBitmap;
    private Matrix matrix;
    public MyComposeShaderView(Context context) {
        this(context, null);
    }

    public MyComposeShaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyComposeShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w != oldw || w!= oldh){
            mWdith = w;
            mHeight = h;
            d = Math.min(mWdith,mHeight);
            r = Math.min(mWdith,mHeight) / 2;

//            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icon_flower);
//            matrix = new Matrix();
//            matrix.postScale(( (float)d )/b.getWidth(),( (float)d )/b.getHeight());
//            mBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(),
//                    matrix, true);
//            b.recycle();
//            Log.d("zyr", "d width:" + d);
//            Log.d("zyr","bitmap width:" + mBitmap.getWidth());


            mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon_flower);

            mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR , Shader.TileMode.MIRROR);
            float scale = 1.0f;
            matrix = new Matrix();
            scale =( (float)d )/mBitmap.getWidth();
            matrix.postScale(scale,scale);
            matrix.postTranslate(0, 0);
            mBitmapShader.setLocalMatrix(matrix);

            mLinearGradient = new LinearGradient(0, 0, 0, 100, new int[] {
                    Color.CYAN, Color.YELLOW }, null,
                    Shader.TileMode.MIRROR);
            mComposeShader = new ComposeShader(mBitmapShader,mLinearGradient, PorterDuff.Mode.DARKEN);
            mSweepGradient = new SweepGradient(mWdith/4 *3,mHeight/2,new int[]{Color.RED,Color.BLUE,Color.WHITE},new float[]{0.0f,0.5f,1.0f});
            mPaint.setShader(mComposeShader);
            mPaint2.setShader(mSweepGradient);
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(mWdith / 2, mHeight / 2, r, mPaint);
        canvas.drawRect(0, 0, mWdith/2, mHeight, mPaint);
        canvas.drawRect(mWdith/2, 0, mWdith,mHeight,mPaint2);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBitmap.recycle();
    }
}
