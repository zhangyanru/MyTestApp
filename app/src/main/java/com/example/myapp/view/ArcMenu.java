package com.example.myapp.view;

import android.content.Context;
import android.content.Loader;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.example.myapp.R;

import java.lang.reflect.Method;

/**
 * Created by zyr
 * DATE: 15-11-18
 * Time: 下午8:21
 * Email: yanru.zhang@renren-inc.com
 *
 * 在继承ViewGroup时有三个重要的方法，下面我们就来看看：
 1、onLayout方法
 protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
 }
 在我们继承ViewGroup时会在除了构造函数之外提供这个方法，我们可以看到，在ViewGroup的源代码中方法是这样定义的，也就是父类没有提供方法的内容，需要我们自己实现。
 当View要为所有子对象分配大小和位置时，调用此方法
 2、addView方法
 public void addView(View child) {
 addView(child, -1);
 }
 这个方法是用来想View容器中添加组件用的。我们可以使用这个方法想这个ViewGroup中添加组件。
 3、getChildAt方法
 public View getChildAt(int index) {
 try {
 return mChildren[index];
 } catch (IndexOutOfBoundsException ex) {
 return null;
 }
 }
 这个方法用来返回指定位置的View。
 */
public class ArcMenu extends ViewGroup{
    private Context context;
    private View root;
    OnChildClickListener iOnChildClickListener = null;
    OnPointClickListener iOnPointClickListener = null;
    private int pointX;
    private int pointY;
    boolean flag = false;
    private float rr = 500;//半径
    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorChangedCircleView);
        for(int i=0;i<array.length();i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.ArcMenu_arc_r:
                    rr = array.getDimension(R.styleable.ArcMenu_arc_r,500);
                    break;
            }
        }
        array.recycle();
        initView();
    }

    private void initView() {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            int count = getChildCount();
            Log.d("zyr",count+"ArcMenu:getChildCount");
            int mViewGroupWidth  = getMeasuredWidth();  //当前ViewGroup的总宽度
            int childCount = getChildCount();
            int x = l;  //当前绘图光标横坐标位置
            int y = t;  //当前绘图光标纵坐标位置
            float aa = 0;
            if(childCount>0){
                View firstChild = getChildAt(0);
                int firstChildWidth  = firstChild.getMeasuredWidth();
                int firstChildHeight = firstChild.getMeasuredHeight();
                x = l;
                y = (int)(t + rr);
                pointX = x;
                pointY = y;
                //执行ChildView的绘制
                firstChild.layout(x,y,x+firstChildWidth, y+firstChildHeight);
            }


            for ( int i = 1; i < childCount; i++ ) {
                aa = 180/(childCount+1) * (i+1);
                x = (int)(l + sinA(aa) * rr);
                y = (int)(rr - cosA(aa) * rr + t);
                Log.d("zyr","sinA:"+sinA(aa)+"  aa："+aa);
                Log.d("zyr","cosA："+cosA(aa)+"  aa："+aa);
                Log.d("zyr",x+":x");
                Log.d("zyr", y + ":y");
                View childView = getChildAt(i);
                childView.setVisibility(GONE);
                int width  = childView.getMeasuredWidth();
                int height = childView.getMeasuredHeight();
                //执行ChildView的绘制
                childView.layout(x,y,x+width, y+height);
            }
        }

    }

    private double cosA(float aa) {
        return Math.cos(Math.toRadians(aa));
    }

    private double sinA(float aa) {
        return Math.sin(Math.PI * aa / 180);
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

    public interface OnChildClickListener{
        public void OnChildClick(int position);
    }

    public interface OnPointClickListener{
        public void onPointClick();
    }


    public void setOnPointClickListener(final OnPointClickListener onPointClickListener){
        iOnPointClickListener = onPointClickListener;
        final int childCount = getChildCount();
        if(childCount>0){
            getChildAt(0).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    iOnPointClickListener.onPointClick();
                    for(int i=1;i<childCount;i++){
                        final View child = getChildAt(i);
                        if(child!=null){
                            if(flag){//visible
                                Log.d("zyr", "hide anim");
                                Log.d("zyr", "view.getAlpha():"+child.getAlpha());
                                child.clearAnimation();
                                AnimationSet animationSet = new AnimationSet(true);
                                TranslateAnimation translateAnimation = new TranslateAnimation(
                                        0,
                                        pointX - child.getX(),
                                        0,
                                        pointY - child.getY());
                                translateAnimation.setDuration(500);
                                translateAnimation.setFillAfter(true);

                                AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
                                alphaAnimation.setDuration(500);
                                alphaAnimation.setFillAfter(true);
                                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        Log.d("zyr", "hide anim end");
                                        child.setVisibility(GONE);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });

                                animationSet.addAnimation(translateAnimation);
                                animationSet.addAnimation(alphaAnimation);
                                child.startAnimation(animationSet);
                            }else{//gone
                                child.setVisibility(VISIBLE);
                                Log.d("zyr", "show anim");
                                child.clearAnimation();
                                AnimationSet animationSet = new AnimationSet(true);
                                TranslateAnimation translateAnimation = new TranslateAnimation(
                                        pointX - child.getX(),
                                        0,
                                        pointY - child.getY(),
                                        0);
                                translateAnimation.setDuration(500);
                                translateAnimation.setFillAfter(true);
                                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        Log.d("zyr", "show anim end");
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });

                                AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
                                alphaAnimation.setDuration(500);
                                alphaAnimation.setFillAfter(true);

                                animationSet.addAnimation(translateAnimation);
                                animationSet.addAnimation(alphaAnimation);
                                child.startAnimation(animationSet);
                            }
                        }
                    }
                    flag = !flag;
                }
            });
        }
    }
    public void setOnChildClickListener(final OnChildClickListener onChildClickListener){
        iOnChildClickListener = onChildClickListener;
        int childCount = getChildCount();
        for(int i=1;i<childCount;i++){
            final int position = i;
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    iOnChildClickListener.OnChildClick(position);
                }
            });
        }
    }
}
