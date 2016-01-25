package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zyr
 * DATE: 15-12-10
 * Time: 下午4:47
 * Email: yanru.zhang@renren-inc.com
 */
public class MyView extends View {

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("zyr","View dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("zyr","View dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
//                Log.d("zyr","View dispatchTouchEvent ACTION_UP");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("zyr","View onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("zyr","View onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
//                Log.d("zyr","View onTouchEvent ACTION_UP");
                break;
            default:
                break;
        }
        return false;
    }
}
