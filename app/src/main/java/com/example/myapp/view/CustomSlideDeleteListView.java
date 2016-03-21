package com.example.myapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-3-18
 * Time: 下午5:42
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomSlideDeleteListView extends ListView {
    private Context mContext;
    private CustomSlideDeleteItemView mCurrentView;
    private int currentItemId;
    private int downX,downY,moveX,moveY,deltaX,upX,upY,lastX,lastDeltaX;
    private boolean isSlideLeft = false;
    private boolean canShowRightView = false;
    private boolean isRightOpen = false;
    public final static int SLIDE_MIN_Y = 200;
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
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = downX = (int) ev.getX();
                downY = (int) ev.getY();
                currentItemId = pointToPosition(downX, downY);
                mCurrentView = (CustomSlideDeleteItemView)getChildAt(currentItemId - getFirstVisiblePosition());
//                Log.e("zyr","currentItemId:" + currentItemId);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) ev.getX();
                moveY = (int) ev.getY();
                deltaX = moveX - downX;
                lastDeltaX = moveX - lastX;
                lastX = moveX;
//                Log.d("zyr","deltaX:" + deltaX);
                /***判断左滑还是右滑**/
                if(lastDeltaX > 0){
                    isSlideLeft = false;
                }else{
                    isSlideLeft = true;
                }
                /***判断左滑还是右滑**/
                if(Math.abs(deltaX) > SLIDE_MIN_Y && isSlideLeft){
                    canShowRightView = true;
                }else{
                    canShowRightView = false;
                }
//                Log.e("zyr","canShowRightView " + canShowRightView);
                if(canShowRightView && mCurrentView!=null){
                    isRightOpen = true;
                    mCurrentView.scroll(deltaX);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                upX = (int)ev.getX();
                upY = (int)ev.getY();
                if(mCurrentView!=null && isRightOpen){
                    mCurrentView.autoScrollBack(upX-downX);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
