package com.example.myapp.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp.R;

/**
 * Created by yanru.zhang on 16/7/18.
 * Email:yanru.zhang@renren-inc.com
 */
public class QQSideSlideView extends ViewGroup {
    private Context mContext;
    private View mMenuVuew;
    private View mMainView;
    private int menuW,mainW,width,height,menuL,menuR,mainL,mainR;
    private ViewDragHelper mViewDragHelper;
    private ViewDragHelper.Callback mViewDragHelperCallback = new ViewDragHelper.Callback() {
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            //拖动状态变化的回调
            //STATE_IDEL 0
            //STATE_DEAGGING 1
            //STATE_SETTLING 2
            Log.d("zyr","state:" + state);
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            Log.d("zyr","onViewCaptured capturedChild : " + (capturedChild==mMainView));
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //拖动的view
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //left:view水平方向移动的距离
            //dx:与上次在的位置的水平距离,>0为向右，<0为向左
            Log.d("zyr","left:" + left + " dx:" + dx);

            if(child == mMainView){
                Log.d("zyr","child == mMainView");
                if(left >0 && left < menuW){
                    return left;
                }else if(left >= menuW){
                    return menuW;
                }else{
                    return 0;
                }
            }else if(child == mMenuVuew){
                Log.d("zyr","child == mMenuVuew");
                if(left < 0 && left > -menuW){
                    return left;
                }else if(left <= -menuW){
                    return -menuW;
                }else{
                    return 0;
                }
            }
          return 0;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return width;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            //子view被拖拽移动的时候的回调方法
            //left:该view的左边缘的新的x坐标
            if(changedView == mMainView){
                menuL = mMenuVuew.getLeft() + dx;
                menuR = menuL + menuW;
                mMenuVuew.layout(menuL,0,menuR,height);
            }else if(changedView == mMenuVuew){
                mainL = mMainView.getLeft() + dx;
                mainR = mainL + mainW;
                mMainView.layout(mainL,0,mainR,height);
            }
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.d("zyr","onViewReleased:");
            //停止拖拽的时候调用
            //xvel:x坐标的速度
            if(releasedChild == mMainView){
                if(mMainView.getLeft() > width/2){
                    //open
                    openAnim();
                }else{
                    //close
                    closeAnim();
                }
            }else if(releasedChild == mMenuVuew){
                if(mMenuVuew.getRight() > width/2){
                    //open
                    openAnim();
                }else{
                    //close
                    closeAnim();
                }
            }
        }
    };

    public QQSideSlideView(Context context) {
        this(context, null);
    }

    public QQSideSlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQSideSlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mViewDragHelper = ViewDragHelper.create(this,mViewDragHelperCallback);//第一个参数是要监听的view,第二个是回调
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMainView = findViewById(R.id.main);
        mMenuVuew = findViewById(R.id.menu);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d("zyr","onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=oldw || h!=oldh){
            width = w;
            height = h;
            menuW = mMenuVuew.getMeasuredWidth();
            mainW = mMainView.getMeasuredWidth();
            mainL = 0;
            mainR = mainL + mainW;
            menuL =  - menuW;
            menuR = menuL + menuW;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        mMainView.measure(MeasureSpec.makeMeasureSpec(size,MeasureSpec.EXACTLY),heightMeasureSpec);
        mMenuVuew.measure(MeasureSpec.makeMeasureSpec(size/4 * 3,MeasureSpec.EXACTLY),heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("zyr","onLayout");
        if(changed){
            mMainView.layout(0,0,width,height);
            mMenuVuew.layout(menuL,0,menuR,height);
        }
    }

    //viewDragHelper内部是通过scroller实现平滑移动的
    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            Log.d("zyr","computeScroll:");
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("zyr ","mViewDragHelper.shouldInterceptTouchEvent(ev):" + mViewDragHelper.shouldInterceptTouchEvent(ev));
        //状态是DRAGGING才会是true
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件传递给viewDragHelper
        mViewDragHelper.processTouchEvent(event);
        return mViewDragHelper.getViewDragState() == ViewDragHelper.STATE_DRAGGING;
    }

    private void openAnim(){
        int duration = (int) ( (menuW - mMainView.getLeft())/(float)width * 500);
        ValueAnimator mainAnimator = ValueAnimator.ofInt(mMainView.getLeft(),menuW);
        mainAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mainL = value;
                mainR = mainL + mainW;
                mMainView.layout(mainL,0,mainR,height);
            }
        });
        mainAnimator.setDuration(duration);

        ValueAnimator menuAnimator = ValueAnimator.ofInt(mMenuVuew.getLeft(),0);
        menuAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                menuL = value;
                menuR = menuL + menuW;
                mMenuVuew.layout(menuL,0,menuR,height);
            }
        });
        menuAnimator.setDuration(duration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(mainAnimator,menuAnimator);
        animatorSet.start();
    }

    private void closeAnim(){
        int duration = (int) (mMainView.getLeft()/(float)width * 500);
        ValueAnimator mainAnimator = ValueAnimator.ofInt(mMainView.getLeft(),0);
        mainAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mainL = value;
                mainR = mainL + mainW;
                mMainView.layout(mainL,0,mainR,height);
            }
        });
        mainAnimator.setDuration(duration);

        ValueAnimator menuAnimator = ValueAnimator.ofInt(mMenuVuew.getLeft(),-menuW);
        menuAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                menuL = value;
                menuR = menuL + menuW;
                mMenuVuew.layout(menuL,0,menuR,height);
            }
        });
        menuAnimator.setDuration(duration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(mainAnimator,menuAnimator);
        animatorSet.start();
    }
}
