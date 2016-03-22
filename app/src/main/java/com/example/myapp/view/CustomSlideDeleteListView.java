package com.example.myapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
    private int downX,downY,moveX,moveY,deltaX,deltaY,upX,upY,lastX,lastDeltaX;
    public final static int MIN_SCROLL_DIS = 100;
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
                /**还要区分上下滑动listview的时候**/
                moveX = (int) ev.getX();
                moveY = (int) ev.getY();
                deltaX = moveX - downX;
                deltaY = moveY - downY;
                if(Math.abs(deltaX) > Math.abs(deltaY)){
                    lastDeltaX = moveX - lastX;
                    lastX = moveX;
//                Log.d("zyr","deltaX:" + deltaX);
//                Log.e("zyr","canShowRightView " + canShowRightView);
                    if(Math.abs(deltaX) > MIN_SCROLL_DIS && mCurrentView!=null){
                        mCurrentView.scroll(deltaX,lastDeltaX);
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                upX = (int)ev.getX();
                upY = (int)ev.getY();
                if(mCurrentView!=null && Math.abs(upX-downX) > MIN_SCROLL_DIS){
                    mCurrentView.autoScroll(upX-downX);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
