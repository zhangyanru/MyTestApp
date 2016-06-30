package com.example.myapp.view;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

import com.example.myapp.R;
import com.example.myapp.util.Methods;

/**
 * Created by zyr
 * DATE: 16-3-28
 * Time: 下午2:58
 * Email: yanru.zhang@renren-inc.com
 * github：
 */
public class CustomPullToZoomListView extends ListView implements AbsListView.OnScrollListener{
    private Context mContext;
    /***************** View*********************/
    private View mHeaderRootView;
    private FrameLayout mHeaderContainer;
    private View mHeadView;//不可拉伸的那部分
    private View mZoomView;//可拉伸的那个view
    private int mHeadViewId,mZoomViewId;
    FrameLayout.LayoutParams mHeaderContainerLp;
    private int mHeaderContainerOriHeight;
    private Scroller mScroller;
    /***************** 状态*********************/
    private boolean isBeingDragged;
    private boolean isZooming;

    private int mDownX,mDownY,mMoveX,mMoveY,deltaX,deltaY;
    private int mScreenHeight,mScreenWidth;
    public final static int MIN_MOVE_Y = 50;
    public final static int SCROLL_DURATION = 200;


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
                case R.styleable.CustomPullToZoomListView_headerlayout:
                    mHeadViewId = typedArray.getResourceId(R.styleable.CustomPullToZoomListView_headerlayout,0);
                    break;
                case R.styleable.CustomPullToZoomListView_zoomLayout:
                    mZoomViewId = typedArray.getResourceId(R.styleable.CustomPullToZoomListView_zoomLayout,0);
                    break;
            }
        }

        initScroller();

        mHeaderRootView = LayoutInflater.from(mContext).inflate(R.layout.pull_to_zoom_header_layout,null);
        mHeaderContainer = (FrameLayout) mHeaderRootView.findViewById(R.id.pull_to_zoom_header_content_ly);
        mHeaderContainerLp = (FrameLayout.LayoutParams)mHeaderContainer.getLayoutParams();
        if(mHeaderContainerLp == null){
            mHeaderContainerLp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        }
        //给listview添加onScroll监听
        setOnScrollListener(this);
        addHeaderView(mHeaderRootView, null, false);

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        mScreenHeight = localDisplayMetrics.heightPixels;
        mScreenWidth = localDisplayMetrics.widthPixels;
        init();
    }

    private void initScroller() {
        mScroller = new Scroller(mContext,new DecelerateInterpolator());
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            mHeaderContainerLp.height = mScroller.getCurrY();
            mHeaderContainer.setLayoutParams(mHeaderContainerLp);
        }
    }

    private void init() {
        mHeadView = LayoutInflater.from(mContext).inflate(mHeadViewId,null);
        mZoomView = LayoutInflater.from(mContext).inflate(mZoomViewId, null);

        mHeaderContainer.addView(mZoomView);
        FrameLayout.LayoutParams mHeaderViewlp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mHeaderViewlp.gravity = Gravity.BOTTOM;
        mHeaderContainer.addView(mHeadView, mHeaderViewlp);
        mHeaderContainer.setMinimumHeight(Methods.computePixelsWithDensity(mContext, 200));

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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("zyr","TOU ACTION_DOWN");
                mDownX = (int) event.getX();
                mDownY = (int) event.getY();
                if(isReadyForPullStart()){
                    Log.d("zyr","--------------isReadyForPullStart");
                    isBeingDragged = true;
                }else{
                    isBeingDragged = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("zyr","TOU ACTION_MOVE");
                mMoveX = (int) event.getX();
                mMoveY = (int) event.getY();
                deltaX = mMoveX - mDownX;
                deltaY = mMoveY - mDownY;
                if(isReadyForPullStart() && isBeingDragged && deltaY > 0 && deltaY > MIN_MOVE_Y){
                    Log.d("zyr","--------------isReadyForPullStart");
                    pullEvent();
                    isZooming = true;
                }else{
                    isZooming = false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(isBeingDragged && isZooming){
                    autoScrollToOrig();
                    isBeingDragged = false;
                    isZooming = false;
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void autoScrollToOrig() {
        mScroller.startScroll(0,mHeaderContainerLp.height,0,mHeaderContainerOriHeight - mHeaderContainerLp.height,SCROLL_DURATION);
        postInvalidate();
    }

    private void pullEvent() {
        Log.d("zyr","pullEvent deltaY:" + deltaY);
        mHeaderContainerLp.height = mHeaderContainerOriHeight + deltaY > mScreenHeight*3/4 ? mScreenHeight*3/4 : mHeaderContainerOriHeight + deltaY;
        mHeaderContainer.setLayoutParams(mHeaderContainerLp);
    }

    private boolean isReadyForPullStart() {
        Log.e("zyr", "mHeaderContainer.getBottom():" + mHeaderContainer.getBottom());
        if(mHeaderRootView.getBottom() >= mHeaderContainerOriHeight){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.d("zyr", "onScrollStateChanged --> ");
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d("zyr", "onScroll --> ");

    }
}
