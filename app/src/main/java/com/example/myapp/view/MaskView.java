package com.example.myapp.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by zyr on 15/9/8.
 */
public class MaskView extends View {
    private Paint paint;

    public MaskView(Context context) {
        super(context);
        init();
    }

    public MaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaskView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init(){
        paint = new Paint();
        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);//设置空心
        paint.setStrokeWidth(3);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //DIFFERENCE是第一次不同于第二次的部分显示出来A-B-------
        //REPLACE是显示第二次的B******
        //REVERSE_DIFFERENCE 是第二次不同于第一次的部分显示--------
        //INTERSECT交集显示A-(A-B)*******
        //UNION全部显示A+B******
        //XOR补集 就是全集的减去交集生育部分显示--------
        canvas.save();
        canvas.clipRect(-10,-10,canvas.getWidth(),canvas.getHeight());
        canvas.clipRect(0, 200, canvas.getWidth(), 400, Region.Op.XOR);
        canvas.drawARGB(80,0,0,0);
        canvas.restore();
        invalidate();
    }
}
