package com.example.myapp.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.example.myapp.R;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 15-11-21
 * Time: 下午12:20
 * Email: yanru.zhang@renren-inc.com
 */
public class ArcMenu2 extends ViewGroup {
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

    public ArcMenu2(Context context) {
        this(context, null);
    }

    public ArcMenu2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ArcMenu2);
        for(int i=0;i<array.length();i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.ArcMenu2_arc2_r:
                    mR = array.getDimension(R.styleable.ArcMenu2_arc2_r,500);
                    break;
                case R.styleable.ArcMenu2_pointId:
                    pointId = array.getResourceId(R.styleable.ArcMenu2_pointId,0);
                    break;
                case R.styleable.ArcMenu2_isPointById:
                    isPointById = array.getBoolean(R.styleable.ArcMenu2_isPointById,false);
                    break;
                case R.styleable.ArcMenu2_position2:
                    int val = array.getInteger(R.styleable.ArcMenu2_position2, 3);
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

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            // mesure child
            getChildAt(i).measure(MeasureSpec.UNSPECIFIED,
                    MeasureSpec.UNSPECIFIED);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed)
        {
            layoutPointButton(l,t,r,b);
            int count = getChildCount();
            for (int i = 1; i < count; i++)
            {
                View child = getChildAt(i);
                child.setVisibility(View.GONE);

                int cx = (int) (mR *  Math.sin(Math.PI / 2 / (count - 2) * (i-1))  );
                int cy = (int) (mR *  Math.cos(Math.PI / 2 / (count - 2) * (i-1))  );
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

                Log.e("zyr", "child "+i+":"+cx + " , " + cy );
//                child.layout(cx, cy, cx + cWidth, cy + cHeight);
                child.layout(childX.get(0).intValue(),childY.get(0).intValue(), childX.get(0).intValue() + cWidth, childY.get(0).intValue() + cHeight);
            }

        }
    }

    private void layoutPointButton(int l,int t,int r,int b) {
        pointBtn = getChildAt(0);
        if(isPointById){
            pointView = pointBtn.findViewById(pointId);
        }
        pointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPointById){
                    rotateView(pointView, 0f, 270f, 300);
                }else{
                    rotateView(pointBtn, 0f, 270f, 300);
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
        childX.add( x + 0.0f);
        childY.add( y + 0.0f);
        Log.e("zyr", "l,t:" + l + " , " + t);
        Log.e("zyr", "child 0:" + x + " , " + y);
        pointBtn.layout(x, y, x + width, y + height);
    }
    /**
     * 按钮的旋转动画
     *
     * @param view
     * @param fromDegrees
     * @param toDegrees
     * @param durationMillis
     */
    public static void rotateView(View view, float fromDegrees,
                                  float toDegrees, int durationMillis)
    {
        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(durationMillis);
        rotate.setFillAfter(true);
        view.startAnimation(rotate);
    }

    public void toggleMenu(int durationMillis)
    {
        int count = getChildCount();
        for (int i = 1; i < count; i++)
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
                    if (iOnChildClickListener != null)
                        iOnChildClickListener.OnChildClick(childView,index);
                    menuItemAnin(index + 1);
                    mCurrentStatus = Status.CLOSE;
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
        for (int i = 1; i < getChildCount(); i++)
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
