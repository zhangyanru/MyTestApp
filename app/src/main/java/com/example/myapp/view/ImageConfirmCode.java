package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by admin on 15/10/14.
 */
public class ImageConfirmCode extends TextView {
    public ImageConfirmCode(Context context) {
        super(context);
    }

    public ImageConfirmCode(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageConfirmCode(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    /**
     * 重写之前先了解MeasureSpec的specMode,一共三种类型：
     EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
     AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
     UNSPECIFIED：表示子布局想要多大就多大，很少使用
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
