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
public class CustomExpandableLayout extends RelativeLayout{

    private Context mContext;

    private int contentViewId, headerViewId;

    private View contentView,headerView;

    private FrameLayout contentLayout,headerLayout;

    private boolean isAnimating = false;

    private int contentLayoutHeight;

    private static final int DURATION = 500;

    public CustomExpandableLayout(Context context) {
        this(context, null);
    }

    public CustomExpandableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomExpandableLayout);
        for(int i=0;i<typedArray.length();i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CustomExpandableLayout_contentLayout:
                    contentViewId = typedArray.getResourceId(R.styleable.CustomExpandableLayout_contentLayout,0);
                    break;
                case R.styleable.CustomExpandableLayout_headLayout:
                    headerViewId = typedArray.getResourceId(R.styleable.CustomExpandableLayout_headLayout,0);
                    break;
            }
        }
        typedArray.recycle();
        Log.d("zyr","headerViewId :" + headerViewId + "     contentViewId :" + contentViewId);
        if(headerViewId!=0){
            headerView = View.inflate(mContext,headerViewId,null);
        }
        if(contentViewId!=0){
            contentView = View.inflate(mContext,contentViewId,null);
        }
        Log.d("zyr","headerView :" + (headerView==null) + "     contentView :" + (contentView==null));
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.expandable_layout_root_view,this);
        headerLayout = (FrameLayout)rootView.findViewById(R.id.expandable_header_layout);
        contentLayout = (FrameLayout)rootView.findViewById(R.id.expandable_content_layout);
        if(headerView!=null){
            headerLayout.addView(headerView);
        }
        if(contentView!=null){
            contentLayout.addView(contentView);
        }
        contentLayout.measure(0,0);
        contentLayoutHeight = contentLayout.getMeasuredHeight();
        contentLayout.setVisibility(GONE);
        headerLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentLayout.getVisibility() == GONE) {
                    //show
                    show();
                } else {
                    //hide
                    hide();
                }
            }
        });
    }

    private void hide() {
        if(isAnimating){
            return;
        }

        ValueAnimator valueAnimator = ValueAnimator.ofInt(contentLayoutHeight,0);
        valueAnimator.setDuration(DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer)animation.getAnimatedValue();
                if(value == 0){
                    contentLayout.setVisibility(GONE);
                }
                contentLayout.getLayoutParams().height = value;
                contentLayout.setLayoutParams(contentLayout.getLayoutParams());
            }
        });
        valueAnimator.start();
    }

    private void show() {
        if(isAnimating){
            return;
        }

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,contentLayoutHeight);
        valueAnimator.setDuration(DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                if (value == 0) {
                    contentLayout.setVisibility(VISIBLE);
                }
                contentLayout.getLayoutParams().height = value;
                contentLayout.setLayoutParams(contentLayout.getLayoutParams());
            }
        });
        valueAnimator.start();
    }
}
