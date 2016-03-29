package com.example.myapp.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.myapp.R;
import com.example.myapp.util.Methods;

/**
 * Created by zyr
 * DATE: 16-3-28
 * Time: 下午2:58
 * Email: yanru.zhang@renren-inc.com
 * github：
 */
public class CustomPullToZoomListView extends LinearLayout {
    private Context mContext;
    /***************** View*********************/
    private FrameLayout mHeaderContainer;
    private View mHeadView;//不可拉伸的那部分
    private View mZoomView;//可拉伸的那个view
    private ListView mListView;
    private int mHeadViewId,mZoomViewId;
    ViewGroup.LayoutParams layoutParams;
    private int mHeaderContainerOriHeight;
    /***************** 状态*********************/
    private boolean isBeingDragged;
    private boolean isZooming;

    private int mDownX,mDownY,mMoveX,mMoveY,deltaX,deltaY;
    private int mScreenHeight,mScreenWidth;
    public final static int MIN_MOVE_Y = 50;

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

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        mScreenHeight = localDisplayMetrics.heightPixels;
        mScreenWidth = localDisplayMetrics.widthPixels;
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        mHeaderContainer.addView(mZoomView);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        mHeaderContainer.addView(mHeadView, lp);
        mHeaderContainer.setMinimumHeight(Methods.computePixelsWithDensity(mContext,200));
        addView(mHeaderContainer);
        addView(mListView);
        layoutParams = mHeaderContainer.getLayoutParams();

        mHeaderContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeaderContainerOriHeight = mHeaderContainer.getHeight();
                Log.d("zyr", "mHeaderContainerOriHeight :" + mHeaderContainerOriHeight);
                mHeaderContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX = (int)ev.getX();
                mDownY = (int)ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = (int)ev.getX();
                mMoveY = (int)ev.getY();
                deltaX = mMoveX - mDownX;
                deltaY = mMoveY - mDownY;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                    break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("zyr","INT ACTION_DOWN");
                if(isReadyForPullStart()){
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("zyr","INT ACTION_MOVE");
                if(isReadyForPullStart()){
                    //y向滑动
                    //y向滑动超过一定距离
                    if(Math.abs(deltaY) > Math.abs(deltaX)
                            && Math.abs(deltaY) > MIN_MOVE_Y
                            && deltaY > 0){
                        isBeingDragged = true;
                        return true;
                    }else{
                        isBeingDragged = false;
                    }
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("zyr","TOU ACTION_DOWN");
                if(isReadyForPullStart()){
                    isBeingDragged = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("zyr","TOU ACTION_MOVE");
                if(isBeingDragged && deltaY > 0){
                    pullEvent();
                    isZooming = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(isBeingDragged && isZooming){
                    autoScrollToOrig();
                }
                isBeingDragged = false;
                isZooming = false;
                break;
        }
        return super.onTouchEvent(event);
    }

    private void autoScrollToOrig() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.height,mHeaderContainerOriHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer)animation.getAnimatedValue();
                Log.d("zyr","value:" + value);
                layoutParams.height = value;
                mHeaderContainer.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.setDuration(100);
        valueAnimator.start();
    }

    private void pullEvent() {
        Log.d("zyr","pullEvent deltaY:" + deltaY);
        layoutParams.height = mHeaderContainerOriHeight + deltaY > mScreenHeight*3/4 ? mScreenHeight*3/4 : mHeaderContainerOriHeight + deltaY;
        mHeaderContainer.setLayoutParams(layoutParams);
    }

    private boolean isReadyForPullStart() {
        if(mListView.getFirstVisiblePosition() == 0){
            return true;
        }else{
            return false;
        }
    }

    public void setAdapter(ListAdapter adapter){
        mListView.setAdapter(adapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        mListView.setOnItemClickListener(onItemClickListener);
    }
}
