package com.example.myapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-4-19
 * Time: 下午6:33
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomExpandableLayoutItem extends RelativeLayout{

    private Context mContext;

    private int menuViewId, itemViewId;

    private View menuView, itemView;

    private FrameLayout menuLayout, itemLayout;

    private boolean isAnimating = false;

    private boolean isOpen = false;

    private int menuLayoutHeight;

    private static final int DURATION = 500;

    public CustomExpandableLayoutItem(Context context) {
        this(context, null);
    }

    public CustomExpandableLayoutItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomExpandableLayoutItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomExpandableLayoutItem);
        for(int i=0;i<typedArray.length();i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CustomExpandableLayoutItem_itemLayout:
                    itemViewId = typedArray.getResourceId(R.styleable.CustomExpandableLayoutItem_itemLayout,0);
                    break;
                case R.styleable.CustomExpandableLayoutItem_menuLayout:
                    menuViewId = typedArray.getResourceId(R.styleable.CustomExpandableLayoutItem_menuLayout,0);
                    break;
            }
        }
        typedArray.recycle();
//        Log.d("zyr", "itemViewId :" + itemViewId + "     menuViewId :" + menuViewId);
        getItemView();
        getMenuView();
//        Log.d("zyr", "itemView :" + (itemView == null) + "     menuView :" + (menuView == null));
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.expandable_layout_root_view, this);
        itemLayout = (FrameLayout)rootView.findViewById(R.id.expandable_header_layout);
        menuLayout = (FrameLayout)rootView.findViewById(R.id.expandable_content_layout);
        if(itemView !=null){
            itemLayout.addView(itemView);
        }
        if(menuView !=null){
            menuLayout.addView(menuView);
        }
        menuLayout.measure(0, 0);
        menuLayoutHeight = menuLayout.getMeasuredHeight();
        menuLayout.setVisibility(GONE);
    }

    public void hide() {
        if(isAnimating || !isOpen){
            return;
        }

        ValueAnimator valueAnimator = ValueAnimator.ofInt(menuLayoutHeight,0);
        valueAnimator.setDuration(DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                if (value == 0) {
                    menuLayout.setVisibility(GONE);
                    isOpen = false;
                }
                menuLayout.getLayoutParams().height = value;
                menuLayout.setLayoutParams(menuLayout.getLayoutParams());
            }
        });
        valueAnimator.start();
    }

    public void show() {
        if(isAnimating){
            return;
        }

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, menuLayoutHeight);
        valueAnimator.setDuration(DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                if (value == 0) {
                    menuLayout.setVisibility(VISIBLE);
                }
                if (value == menuLayoutHeight) {
                    isOpen = true;
                }
                menuLayout.getLayoutParams().height = value;
                menuLayout.setLayoutParams(menuLayout.getLayoutParams());
            }
        });
        valueAnimator.start();
    }

    public void showRightNow(){
        if(menuLayout.getLayoutParams().height == menuLayoutHeight){
            return;
        }
        menuLayout.setVisibility(VISIBLE);
        menuLayout.getLayoutParams().height = menuLayoutHeight;
        menuLayout.setLayoutParams(menuLayout.getLayoutParams());
        isOpen = true;
        invalidate();
    }

    public void hideRightNow(){
        if(menuLayout.getLayoutParams().height == 0){
            return;
        }
        menuLayout.setVisibility(GONE);
        menuLayout.getLayoutParams().height = 0;
        menuLayout.setLayoutParams(menuLayout.getLayoutParams());
        isOpen = false;
        invalidate();
    }

    public View getMenuView() {
        if(menuView == null){
            if(menuViewId !=0){
                menuView = View.inflate(mContext, menuViewId,null);
            }
        }
        return menuView;
    }

    public View getItemView() {
        if(itemView == null){
            if(itemViewId !=0){
                itemView = View.inflate(mContext, itemViewId,null);
            }
        }
        return itemView;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
