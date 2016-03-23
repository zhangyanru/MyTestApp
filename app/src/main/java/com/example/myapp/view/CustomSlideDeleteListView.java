package com.example.myapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

import com.example.myapp.R;
import com.example.myapp.util.Methods;

/**
 * Created by zyr
 * DATE: 16-3-18
 * Time: 下午5:42
 * Email: yanru.zhang@renren-inc.com
 * 参照：https://github.com/baoyongzhang/SwipeMenuListView
 * 还有一些没有解决的问题，
 * 1.触摸拦截
 */
public class CustomSlideDeleteListView extends ListView {
    private Context mContext;
    private CustomSlideDeleteItemView mCurrentView,mLastTouchItemView;
    private int currentItemId,lastItemId,touchItemId;
    private int downX,downY,moveX,moveY,deltaX,deltaY,upX,upY,lastX,lastDeltaX;
    public final static int MIN_SCROLL_DIS = 100;

    private int touchState=TOUCH_STATE_NONE;
    public final static int TOUCH_STATE_X = 1;
    public final static int TOUCH_STATE_Y = 2;
    public final static int TOUCH_STATE_NONE = 0;
    private int  direction = DIRECTION_NONE;
    public final static int DIRECTION_LEFT = 1;//左滑
    public final static int DIRECTION_RIGHT = 2;//右滑
    public final static int DIRECTION_TOP = 3;//左滑
    public final static int DIRECTION_BOTTOM = 4;//右滑
    public final static int DIRECTION_NONE = 0;//
    public CustomSlideDeleteListView(Context context) {
        this(context, null);
    }

    public CustomSlideDeleteListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSlideDeleteListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setSelector(new ColorDrawable(Color.TRANSPARENT));
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSlideDeleteListView);
        for(int i=0;i<typedArray.length();i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CustomSlideDeleteListView_rightViewId:

                    break;
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = downX = (int) ev.getX();
                downY = (int) ev.getY();
                touchItemId = pointToPosition(downX,downY);
                touchState = TOUCH_STATE_NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) ev.getX();
                moveY = (int) ev.getY();
                deltaX = moveX - downX;
                deltaY = moveY - downY;
                lastDeltaX = moveX - lastX;
                lastX = moveX;
                if(Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > MIN_SCROLL_DIS){
                    touchState = TOUCH_STATE_X;
                    if(deltaX > 0){
                        direction = DIRECTION_RIGHT;
                    }else{
                        direction = DIRECTION_LEFT;
                    }
                }else if(Math.abs(deltaX) <= Math.abs(deltaY) && Math.abs(deltaY) > MIN_SCROLL_DIS){
                    touchState = TOUCH_STATE_Y;
                    if(deltaY > 0){
                        direction = DIRECTION_BOTTOM;
                    }else{
                        direction = DIRECTION_TOP;
                    }
                }else {
                    touchState = TOUCH_STATE_NONE;
                    direction = DIRECTION_NONE;
                }
//                Log.e("zyr","touchState:" + touchState);
//                Log.e("zyr","direction"+ direction);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //按下的位置不是上次按下的位置
                //上次的iew不是空，而且是打开的，拦截
                if(touchItemId != lastItemId
                        && mLastTouchItemView!=null
                        && !mLastTouchItemView.isClosed()){
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //if 向距离大于纵向距离，拦截
                if(touchState == TOUCH_STATE_X ){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //只是点击了一下
                //有处于Open状态的menu
                //没有点击在menu上面,拦截
                if(touchState==TOUCH_STATE_NONE
                        && mLastTouchItemView!=null
                        && !mLastTouchItemView.isClosed()
                        && !Methods.inRangeOfView(mLastTouchItemView.getMenuViewContainer(),ev)){
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(touchItemId != lastItemId
                        && mLastTouchItemView!=null
                        && !mLastTouchItemView.isClosed()){
                    mLastTouchItemView.scrollClose();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(touchState == TOUCH_STATE_X ){

                    lastItemId = currentItemId;
                    currentItemId = pointToPosition(downX,downY);
                    mLastTouchItemView = mCurrentView;
                    mCurrentView = (CustomSlideDeleteItemView)getChildAt(currentItemId - getFirstVisiblePosition());
                    mCurrentView.scroll(deltaX,lastDeltaX);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                upX = (int)ev.getX();
                upY = (int)ev.getY();
                if(touchState == TOUCH_STATE_X && mCurrentView!=null){
                    mCurrentView.autoScroll(upX - downX);
                    return true;
                }
                if(touchState==TOUCH_STATE_NONE
                        && mLastTouchItemView!=null
                        && !mLastTouchItemView.isClosed()
                        && !Methods.inRangeOfView(mLastTouchItemView.getMenuViewContainer(),ev)){
                    mLastTouchItemView.scrollClose();
                    return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
