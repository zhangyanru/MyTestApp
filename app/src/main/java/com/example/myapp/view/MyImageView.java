package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zyr
 * DATE: 16-2-2
 * Time: 下午2:59
 * Email: yanru.zhang@renren-inc.com
 */
public class MyImageView extends View {
    private BitmapDrawable bitmapDrawable;

    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(bitmapDrawable!=null){
            bitmapDrawable.draw(canvas);
        }
    }

    public void setBitmapDrawable(BitmapDrawable bitmapDrawable){
        this.bitmapDrawable = bitmapDrawable;
    }
}
