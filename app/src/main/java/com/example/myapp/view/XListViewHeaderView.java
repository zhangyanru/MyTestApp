package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.myapp.R;

/**
 * Created by admin on 15/9/10.
 */
public class XListViewHeaderView extends LinearLayout {
    private LinearLayout viewContaner;
    private ImageView arrow;
    private ImageView refresh;

    private final int NORMAL = 0;
    private final int READY = 1;
    private final int REFRESHING = 2;
    private int mstate = 0;
    private Context mcontext;

    public XListViewHeaderView(Context context) {
        super(context);
        init(context);
    }

    public XListViewHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XListViewHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        mcontext = context;
        viewContaner =  (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xlistview_headerview, null);
        arrow = (ImageView)viewContaner.findViewById(R.id.headview_arrow);
        refresh = (ImageView)viewContaner.findViewById(R.id.headview_refresh);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 0);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        addView(viewContaner, layoutParams);
        setGravity(Gravity.BOTTOM);
    }


    public void setState(int state){
        if(mstate!=state){
            mstate = state;
            resetView();
        }

    }

    public void resetView(){
        switch (mstate){
            case NORMAL:
                arrow.setVisibility(VISIBLE);
                arrow.clearAnimation();
                refresh.clearAnimation();
                refresh.setVisibility(GONE);
                break;
            case READY:
                arrow.setVisibility(VISIBLE);
                arrow.clearAnimation();
                AnimationSet animationSet = new AnimationSet(true);
                RotateAnimation rotateAnimation = new RotateAnimation(0,180,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                rotateAnimation.setDuration(500);
                animationSet.addAnimation(rotateAnimation);
                animationSet.setFillAfter(true);
                arrow.setAnimation(animationSet);
                refresh.clearAnimation();
                refresh.setVisibility(GONE);
                break;
            case REFRESHING:
                arrow.clearAnimation();
                arrow.setVisibility(GONE);
                refresh.setVisibility(VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(mcontext,R.anim.animation);
                refresh.setAnimation(animation);
                break;
        }
    }

    public void setVisibleHeight(float height){
        if(height<=0)
            {  height = 0;}
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewContaner.getLayoutParams();
        layoutParams.height =(int) height;
        viewContaner.setLayoutParams(layoutParams);
    }



    public int getVisiableHeight() {
        return viewContaner.getHeight();
    }
}
