package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by zyr
 * DATE: 15-12-10
 * Time: 下午4:46
 * Email: yanru.zhang@renren-inc.com
 */
public class MyLinearLayout extends LinearLayout {

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 第一步，dispatchTouchEvent，
     *          false：结束
     *          true：到onInterceptTouchEvent
     *              第二步：到onInterceptTouchEvent，
     *                  true：到viewGroup的onTouchEvent
     *                  false：到View dispatchTouchEvent
     *                      第三步：View dispatchTouchEvent
     *                          第四步：View onTouchEvent
     *                                true：view消费touch事件，viewGroup不能消费
     *                                false：viewGroup决定是否消费touch事件
     *
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("zyr", "LinearLayout dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("zyr","LinearLayout dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
//                Log.d("zyr","LinearLayout dispatchTouchEvent ACTION_UP");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("zyr", "LinearLayout onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("zyr","LinearLayout onInterceptTouchEvent ACTION_MOVE");
                return  true;
            case MotionEvent.ACTION_UP:
//                Log.d("zyr","LinearLayout onInterceptTouchEvent ACTION_UP");
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("zyr", "LinearLayout onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("zyr","LinearLayout onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
//                Log.d("zyr","LinearLayout onTouchEvent ACTION_UP");
                break;
            default:
                break;
        }
        return true;
    }
}
