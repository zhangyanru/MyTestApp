package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zyr
 * DATE: 16-3-1
 * Time: 下午4:49
 * Email: yanru.zhang@renren-inc.com
 */
public class MyLinearGradientView extends View implements Runnable {
    int bitwidth = 0;
    int bitheight = 0;
    Paint mPaint = null;
    // 线性渐变渲染
    Shader mLinearGradient1 = null;
    // 线性渐变渲染
    Shader mLinearGradient2 = null;
    // 线性渐变渲染
    Shader mLinearGradient3 = null;

    public MyLinearGradientView(Context context) {
        this(context, null);
    }

    public MyLinearGradientView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLinearGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 创建LinearGradient并设置渐变颜色数组
        // 第一个,第二个参数表示渐变起点 可以设置起点终点在对角等任意位置
        // 第三个,第四个参数表示渐变终点
        // 第五个参数表示渐变颜色
        // 第六个参数可以为空,表示坐标,值为0-1 new float[] {0.25f, 0.5f, 0.75f, 1 }
        // 如果这是空的，颜色均匀分布，沿梯度线。
        // 第七个表示平铺方式
        // CLAMP重复最后一个颜色至最后
        // MIRROR重复着色的图像水平或垂直方向已镜像方式填充会有翻转效果
        // REPEAT重复着色的图像水平或垂直方向
        mLinearGradient1 = new LinearGradient(0, 0, 0, 100, new int[] {
                Color.RED, Color.GREEN, Color.BLUE, Color.WHITE }, null,
                Shader.TileMode.CLAMP);
        mLinearGradient2 = new LinearGradient(0, 0, 0, 100, new int[] {
                Color.RED, Color.GREEN, Color.BLUE, Color.WHITE }, null,
                Shader.TileMode.MIRROR);
        mLinearGradient3 = new LinearGradient(0, 0, 0, 100, new int[] {
                Color.RED, Color.GREEN, Color.BLUE, Color.WHITE }, null,
                Shader.TileMode.REPEAT);
        mPaint = new Paint();
        new Thread(this).start();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        // LinearGradient的高度只有100而绘制的矩形有200所以才会有重复
        // 如果高度两者相同clamp重复是看不出效果的
        Paint paint = new Paint();
        paint.setTextSize(80);
//        paint.setColor(Color.WHITE);
        // 绘制渐变的矩形
        mPaint.setShader(mLinearGradient1);
        canvas.drawRect(0, 0, 200, 200, mPaint);
        canvas.drawText("CLAMP", 0, 600 + 60, paint);
        // 绘制渐变的矩形
        mPaint.setShader(mLinearGradient2);
        canvas.drawRect(0, 250, 200, 450, mPaint);
        canvas.drawText("MIRROR", 0, 450 + 20, paint);
        // 绘制渐变的矩形
        mPaint.setShader(mLinearGradient3);
        canvas.drawRect(0, 500, 200, 700, mPaint);
        canvas.drawText("REPEAT", 0, 700 + 20, paint);
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(100);
            }
            catch(Exception e) {
                Thread.currentThread().interrupt();
            }
            postInvalidate();
        }
    }
}
