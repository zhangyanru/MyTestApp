package com.example.myapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-3-28
 * Time: 下午2:58
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomPullToZoomListView extends LinearLayout {
    private Context mContext;
    /***************** View*********************/
    private FrameLayout mHeaderContainer;
    private View mHeadView;//不可拉伸的那部分
    private View mZoomView;//可拉伸的那个view
    private ListView mListView;
    private int mHeadViewId,mZoomViewId;
    /***************** 状态*********************/
    private boolean isBeingDragged;
    private boolean isZooming;

    public CustomPullToZoomListView(Context context) {
        this(context, null);
    }

    public CustomPullToZoomListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPullToZoomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CustomPullToZoomListView);
        for(int i=0;i<typedArray.length();i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CustomPullToZoomListView_headerLayout:
                    mHeadViewId = typedArray.getResourceId(R.styleable.CustomPullToZoomListView_headerLayout,0);
                    break;
                case R.styleable.CustomPullToZoomListView_zoomLayout:
                    mZoomViewId = typedArray.getResourceId(R.styleable.CustomPullToZoomListView_zoomLayout,0);
                    break;
            }
        }
        mHeadView = LayoutInflater.from(mContext).inflate(mHeadViewId,null);
        mZoomView = LayoutInflater.from(mContext).inflate(mZoomViewId, null);
        mHeaderContainer = new FrameLayout(mContext);
        mListView = new ListView(mContext);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        mHeaderContainer.addView(mZoomView);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        mHeaderContainer.addView(mHeadView,layoutParams);
        addView(mHeaderContainer);
        addView(mListView);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(isReadyForPullStart()){

                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    private boolean isReadyForPullStart() {
        if(mListView.getFirstVisiblePosition() == 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setAdapter(ListAdapter adapter){
        mListView.setAdapter(adapter);
    }
}
