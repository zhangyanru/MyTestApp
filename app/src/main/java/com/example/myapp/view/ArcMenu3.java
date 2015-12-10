package com.example.myapp.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.example.myapp.R;
import com.example.myapp.activity.ArcMenu2Activity;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 15-11-25
 * Time: 下午1:49
 * Email: yanru.zhang@renren-inc.com
 */
public class ArcMenu3 extends ViewGroup {
    private Context context;
    private float mR = 500;//半径
    private Position mPosition = Position.RIGHT_BOTTOM;
    private int pointX;
    private int pointY;
    private boolean isPointById = false;//是第一个子iew旋转还是子iew中的一个特定的id的view旋转
    private int pointId;
    private View pointView;
    private ArrayList<Float> childX = new ArrayList<Float>();
    private ArrayList<Float> childY = new ArrayList<Float>();
    /**
     * 用户点击的按钮
     */
    private View pointBtn;
    /**
     * 当前ArcMenu的状态
     */
    private Status mCurrentStatus = Status.CLOSE;
    /**
     * 设置菜单现实的位置，四选1，默认右下
     */
    public enum Position
    {
        LEFT_TOP, RIGHT_TOP, RIGHT_BOTTOM, LEFT_BOTTOM;
    }
    /**
     * 状态的枚举类
     *
     */
    public enum Status
    {
        OPEN, CLOSE
    }
    /**
     * 回调接口
     */
    private OnChildClickListener iOnChildClickListener = null;
    public interface OnChildClickListener{
        public void OnChildClick(View view,int position);
    }

    int screenWidth;
    int screenHeigh;

    public ArcMenu3(Context context) {
        this(context, null);
    }

    public ArcMenu3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu3(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ArcMenu3);
        for(int i=0;i<array.length();i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.ArcMenu3_arc3_r:
                    mR = array.getDimension(R.styleable.ArcMenu3_arc3_r,500);
                    break;
                case R.styleable.ArcMenu3_pointId3:
                    pointId = array.getResourceId(R.styleable.ArcMenu3_pointId3,0);
                    break;
                case R.styleable.ArcMenu3_isPointById3:
                    isPointById = array.getBoolean(R.styleable.ArcMenu3_isPointById3,false);
                    break;
                case R.styleable.ArcMenu3_position_arc3:
                    int val = array.getInteger(R.styleable.ArcMenu3_position_arc3, 3);
                    Log.d("zyr","position" + val);
                    switch (val)
                    {
                        case 0:
                            mPosition = Position.LEFT_TOP;
                            break;
                        case 1:
                            mPosition = Position.LEFT_BOTTOM;
                            break;
                        case 2:
                            mPosition = Position.RIGHT_TOP;
                            break;
                        case 3:
                            mPosition = Position.RIGHT_BOTTOM;
                            break;
                    }
                    break;
            }
        }
        array.recycle();
        initView();
    }
    private void initView() {
        // 获取屏幕宽高（方法1）
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 1; i < count; i++)
        {
            // mesure child
            getChildAt(i).measure(MeasureSpec.UNSPECIFIED,
                    MeasureSpec.UNSPECIFIED);
        }
        getChildAt(0).measure(screenWidth,
                screenHeigh);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed)
        {
            layoutWhiteView();
            layoutPointButton(l, t, r, b);
            int count = getChildCount();
            for (int i = 2; i < count; i++)
            {
                View child = getChildAt(i);
                child.setVisibility(GONE);

                int cx = (int) (mR *  Math.sin(Math.PI / 2 / (count - 3) * (i-2))  );
                int cy = (int) (mR *  Math.cos(Math.PI / 2 / (count - 3) * (i-2))  );
                // childview width
                int cWidth = child.getMeasuredWidth();
                // childview height
                int cHeight = child.getMeasuredHeight();

                if(mPosition ==Position.LEFT_TOP){
                    cx = pointX + cx - cWidth/2;
                    cy = pointY + cy - cHeight/2;
                }

                if(mPosition ==Position.LEFT_BOTTOM){
                    cx = pointX + cx - cWidth/2;
                    cy = pointY - cy - cHeight/2;
                }

                if(mPosition ==Position.RIGHT_TOP){
                    cx = pointX - cx - cWidth/2;
                    cy = pointY + cy - cHeight/2;
                }

                if(mPosition ==Position.RIGHT_BOTTOM){
                    cx = pointX - cx - cWidth/2;
                    cy = pointY - cy - cHeight/2;
                }

                childX.add(cx + 0.0f);
                childY.add(cy + 0.0f);

                Log.e("zyr", "child " + i + ":" + cx + " , " + cy);
//                child.layout(cx, cy, cx + cWidth, cy + cHeight);
                child.layout(childX.get(1).intValue(),childY.get(1).intValue(), childX.get(1).intValue() + cWidth, childY.get(1).intValue() + cHeight);
            }

        }
    }

    private void layoutWhiteView() {
        getChildAt(0).layout(0,0, screenWidth, screenHeigh);
        getChildAt(0).setVisibility(GONE);
        childX.add(0.0f);
        childY.add(0.0f);
        getChildAt(0).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentStatus ==Status.CLOSE){

                }else{
                    alphaView(300);
                    toggleMenu(300);
                }

            }
        });
    }

    private void layoutPointButton(int l,int t,int r,int b) {
        pointBtn = getChildAt(1);
        if(isPointById){
            pointView = pointBtn.findViewById(pointId);
        }
        pointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPointById){
                    rotateView(pointView,315,300);
                }else{
                    rotateView(pointBtn,315,300);
                }
                toggleMenu(300);
            }
        });

        int x = l;
        int y = t;
        int width = pointBtn.getMeasuredWidth();
        int height = pointBtn.getMeasuredHeight();
        switch (mPosition)
        {
            case LEFT_TOP:
                break;
            case LEFT_BOTTOM:
                x = l;
                y = b -height;
                break;
            case RIGHT_TOP:
                x = r - width;
                y = t;

                break;
            case RIGHT_BOTTOM:
                x = r - width;
                y = b - height;

                break;

        }
        pointX = x + width/2;
        pointY = y + height/2;
        childX.add(x + 0.0f);
        childY.add(y + 0.0f);
        Log.e("zyr", "l,t:" + l + " , " + t);
        Log.e("zyr", "child 0:" + x + " , " + y);
        pointBtn.layout(x, y, x + width, y + height);
    }
    /**
     * 按钮的旋转动画
     *
     * @param view
     * @param toDegrees
     * @param durationMillis
     */
    public void rotateView(View view, float toDegrees, int durationMillis)
    {
        if(mCurrentStatus == Status.CLOSE){//to open
           alphaView(durationMillis);

            ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(view,"rotation",0,toDegrees).setDuration(durationMillis);
            rotateAnim.start();
        }else if(mCurrentStatus == Status.OPEN){// to close
            alphaView(durationMillis);

            ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(view,"rotation",toDegrees,0).setDuration(durationMillis);
            rotateAnim.start();
        }

    }

    public void alphaView(int durationMillis){
        if(mCurrentStatus == Status.CLOSE){//to open
            getChildAt(0).setVisibility(VISIBLE);
            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(getChildAt(0),"alpha",0.0f,0.8f).setDuration(durationMillis);
            alphaAnim.start();
        } else if(mCurrentStatus == Status.OPEN){// to close
            getChildAt(0).setVisibility(VISIBLE);
            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(getChildAt(0),"alpha",0.8f,0.0f).setDuration(durationMillis);
            alphaAnim.start();
        }
    }

    public void toggleMenu(int durationMillis)
    {
        int count = getChildCount();
        for (int i = 2; i < count; i++)
        {
            final View childView = getChildAt(i);
            childView.setVisibility(View.VISIBLE);

            if (mCurrentStatus == Status.CLOSE)
            {// to open

                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator transXAnim =  ObjectAnimator.ofFloat(childView, "x", childX.get(i)).setDuration(durationMillis);
                ObjectAnimator transYAnim =  ObjectAnimator.ofFloat(childView, "y", childY.get(i)).setDuration(durationMillis);
                ObjectAnimator alphaAnim =   ObjectAnimator.ofFloat(childView, "alpha", 0.0f, 1.0f).setDuration(durationMillis);
                ObjectAnimator rotateAnim =  ObjectAnimator.ofFloat(childView,"rotation",0.0f,720f).setDuration(durationMillis);

                animatorSet.setDuration(durationMillis);
                animatorSet.playTogether(transXAnim, transYAnim, alphaAnim, rotateAnim);
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mCurrentStatus = Status.OPEN;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorSet.start();
            } else {// to close

                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator transXAnim = ObjectAnimator.ofFloat(childView, "x",pointX).setDuration(durationMillis);

                ObjectAnimator transYAnim = ObjectAnimator.ofFloat(childView, "y",pointY).setDuration(durationMillis);

                ObjectAnimator alphaAnim =   ObjectAnimator.ofFloat(childView, "alpha", 1.0f,0.0f).setDuration(durationMillis);

                ObjectAnimator rotateAnim =  ObjectAnimator.ofFloat(childView,"rotation",0.0f,720f).setDuration(durationMillis);

                animatorSet.setDuration(durationMillis);
                animatorSet.playTogether(transXAnim, transYAnim, alphaAnim, rotateAnim);
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mCurrentStatus = Status.CLOSE;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorSet.start();

            }

            final int index = i -1;
            childView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    menuItemAnin(index + 1);
                    mCurrentStatus = Status.CLOSE;

                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (iOnChildClickListener != null){
                                iOnChildClickListener.OnChildClick(childView,index);
                            }
                        }
                    },300);
                }
            });

        }


        Log.e("zyr", mCurrentStatus.name() + "");
    }
    /**
     * 开始菜单动画，点击的MenuItem放大消失，其他的缩小消失
     * @param item
     */
    private void menuItemAnin(int item)
    {
        for (int i = 2; i < getChildCount(); i++)
        {
            View childView = getChildAt(i);
            if (i == item)
            {
                scaleBigAnim(childView, 300);
            } else
            {
                scaleSmallAnim(childView,300);
            }
        }
    }
    /**
     * 放大，透明度降低
     * @param durationMillis
     * @return
     */
    private void scaleBigAnim(final View childView,int durationMillis)
    {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(childView,"alpha",0.0f).setDuration(durationMillis);
        ObjectAnimator scalXAnim = ObjectAnimator.ofFloat(childView,"scaleX",4.0f).setDuration(durationMillis);
        ObjectAnimator scalYAnim = ObjectAnimator.ofFloat(childView,"scaleY",4.0f).setDuration(durationMillis);
        animatorSet.setDuration(durationMillis);
        animatorSet.playTogether(alphaAnim, scalXAnim, scalYAnim);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                childView.setScaleX(1.0f);
                childView.setScaleY(1.0f);
                childView.setX(pointX);
                childView.setY(pointY);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }
    /**
     * 缩小消失
     * @param durationMillis
     * @return
     */
    private void scaleSmallAnim(final View childView,int durationMillis)
    {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(childView,"alpha",0.0f).setDuration(durationMillis);
        ObjectAnimator scalXAnim = ObjectAnimator.ofFloat(childView,"scaleX",0.0f).setDuration(durationMillis);
        ObjectAnimator scalYAnim = ObjectAnimator.ofFloat(childView,"scaleY",0.0f).setDuration(durationMillis);
        animatorSet.setDuration(durationMillis);
        animatorSet.playTogether(alphaAnim, scalXAnim, scalYAnim);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                childView.setScaleX(1.0f);
                childView.setScaleY(1.0f);
                childView.setX(pointX);
                childView.setY(pointY);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    public void setOnChildClickListener(final OnChildClickListener onChildClickListener){
        this.iOnChildClickListener = onChildClickListener;
    }
}

