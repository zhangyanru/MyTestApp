package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zyr
 * DATE: 16-1-20
 * Time: 下午3:57
 * Email: yanru.zhang@renren-inc.com
 */
public class DragView2 extends View {
    private Context mContext;
    private int downX;
    private int downY;
    private int moveX;
    private int moveY;
    private int offsetX;
    private int offsetY;
    public DragView2(Context context) {
        this(context, null);
    }

    public DragView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        downX = (int)event.getX();
                        downY = (int)event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX = (int)event.getX();
                        moveY = (int)event.getY();
                        offsetX = moveX - downX;
                        offsetY = moveY - downY;
                        setX(getX() + offsetX);
                        setY(getY() + offsetY);
                        invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }
}
