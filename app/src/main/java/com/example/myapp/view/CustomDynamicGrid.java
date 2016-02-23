package com.example.myapp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-2-2
 * Time: 下午3:09
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomDynamicGrid extends GridView {
    private static final int INVALID_ID = -1;
    private float originX,originY,currentX,currentY,deltaX,deltaY;
    private int clickPosition,upPosition,originPosition,targetPosition;
    private BitmapDrawable bitmapDrawable;
    private Rect originRect,currentRect;
    private boolean isEditMode = false;
    private long mMobileItemId = INVALID_ID;
    private View clickView;
    private boolean mAnimationEnd = true;

    public void setActionUpListener(ActionUpListener actionUpListener) {
        this.actionUpListener = actionUpListener;
    }

    private ActionUpListener actionUpListener;
    public interface ActionUpListener{
        public void onActionUp(int downPosition,int upPosition);
    }

    public void setSwapListener(SwapListener swapListener) {
        this.swapListener = swapListener;
    }

    private SwapListener swapListener;
    public interface SwapListener{
        public void onSwap(int originPosition,int targetPosition);
    }

    public CustomDynamicGrid(Context context) {
        this(context, null);
    }

    public CustomDynamicGrid(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomDynamicGrid(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setIsEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("zyr","onTouchEvent ACTION_DOWN");
                originX = ev.getX();
                originY = ev.getY();
                if(isEditMode){
                    clickPosition = pointToPosition((int) originX, (int) originY);
                    Log.d("zyr","clickPosition:" + clickPosition);
                    layoutChildren();
                    startDragAtPosition();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("zyr","onTouchEvent ACTION_MOVE");
                currentX = ev.getX();
                currentY = ev.getY();
                deltaX = currentX - originX;
                deltaY = currentY - originY;
                if(isEditMode){
                    /*********************************镜像随手指移动**********************************/
                    currentRect.offsetTo(originRect.left + (int) deltaX, originRect.top + (int) deltaY);
                    bitmapDrawable.setBounds(currentRect);
                    invalidate();
                    /*********************************实时交换动画**********************************/
                    targetPosition = pointToPosition((int) currentX, (int) currentY);
                    if(this.swapListener !=null && isEditMode && targetPosition !=-1 && originPosition!=targetPosition){
                        Log.d("zyrr", "originPosition:" + originPosition + "  targetPosition:" + targetPosition);
                        swapListener.onSwap(originPosition, targetPosition);
                        getChildAt(originPosition-getFirstVisiblePosition()).setVisibility(VISIBLE);
                        getChildAt(targetPosition-getFirstVisiblePosition()).setVisibility(INVISIBLE);
                        animateReorder(targetPosition, originPosition);
                        originPosition = targetPosition;
                    }
                    /*********************************到边界向上滚动或者向下滚动**********************************/
                    int offset = computeVerticalScrollOffset();
                    int height = getHeight();
                    int extent = computeVerticalScrollExtent();
                    int range = computeVerticalScrollRange();
                    int hoverViewTop = currentRect.top;
                    int hoverHeight = currentRect.height();

                    if (hoverViewTop <= 0 && offset > 0) {
                        smoothScrollBy(-24, 0);
                        return true;
                    }

                    if (hoverViewTop + hoverHeight >= height && (offset + extent) < range) {
                        smoothScrollBy(24, 0);
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d("zyr", "onTouchEvent ACTION_UP");
                bitmapDrawable = null;
                if(clickView!=null){
                    clickView.setVisibility(VISIBLE);
                }
                if(getChildAt(originPosition-getFirstVisiblePosition())!=null){
                    getChildAt(originPosition-getFirstVisiblePosition()).setVisibility(VISIBLE);
                }
                upPosition = pointToPosition((int) currentX, (int) currentY);
                if(this.actionUpListener !=null && isEditMode && upPosition !=-1 ){
                    actionUpListener.onActionUp(clickPosition,upPosition);
                }
                isEditMode = false;
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * item的交换动画效果
     * @param oldPosition
     * @param newPosition
     */
    private void animateReorder(final int oldPosition, final int newPosition) {
        boolean isForward = newPosition > oldPosition;
        List<Animator> resultList = new LinkedList<Animator>();
        if (isForward) {
            for (int pos = oldPosition; pos < newPosition; pos++) {
                View view = getChildAt(pos - getFirstVisiblePosition());
                System.out.println(pos);

                if ((pos + 1) % getNumColumns() == 0) {
                    resultList.add(createTranslationAnimations(view,
                            - view.getWidth() * (getNumColumns() - 1), 0,
                            view.getHeight(), 0));
                } else {
                    resultList.add(createTranslationAnimations(view,
                            view.getWidth(), 0, 0, 0));
                }
            }
        } else {
            for (int pos = oldPosition; pos > newPosition; pos--) {
                View view = getChildAt(pos - getFirstVisiblePosition());
                if ((pos + getNumColumns()) % getNumColumns() == 0) {
                    resultList.add(createTranslationAnimations(view,
                            view.getWidth() * (getNumColumns() - 1), 0,
                            -view.getHeight(), 0));
                } else {
                    resultList.add(createTranslationAnimations(view,
                            -view.getWidth(), 0, 0, 0));
                }
            }
        }

        AnimatorSet resultSet = new AnimatorSet();
        resultSet.playTogether(resultList);
        resultSet.setDuration(300);
        resultSet.setInterpolator(new AccelerateDecelerateInterpolator());
        resultSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mAnimationEnd = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimationEnd = true;
            }
        });
        resultSet.start();
    }
    /**
     * 创建移动动画
     * @param view
     * @param startX
     * @param endX
     * @param startY
     * @param endY
     * @return
     */
    private AnimatorSet createTranslationAnimations(View view, float startX,
                                                    float endX, float startY, float endY) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX",
                startX, endX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY",
                startY, endY);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        return animSetXY;
    }
    private void startDragAtPosition(){
        startDragAtPosition(-1);
    }
    private void startDragAtPosition(int position) {
        if(position!=-1){
            clickPosition = position;
            originPosition = position;
        }
        clickView = getChildAt(position - getFirstVisiblePosition());//任何情况下，getChildAt（int position)返回的item都是指的可视区域内的第position个元素
        if(clickView!=null){
            mMobileItemId = getAdapter().getItemId(position);
            int w = clickView.getWidth() + 100;
            int h = clickView.getHeight() + 100;
            int left = clickView.getLeft() - 50;
            int top = clickView.getTop() - 50;
            Bitmap bitmap = getBitmapFromView(clickView);
            bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            bitmapDrawable.setAlpha(150);
            originRect = new Rect(left ,top, left + w,top + h );
            currentRect = new Rect(originRect);
            bitmapDrawable.setBounds(originRect);
            clickView.setVisibility(View.INVISIBLE);
            isEditMode = true;
        }
    }

    public void startEditMode(int position){
        startDragAtPosition(position);
    }

    private Bitmap getBitmapFromView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(bitmapDrawable!=null){
            Log.d("zyr","dispatchDraw");
            bitmapDrawable.draw(canvas);
        }
    }
}
