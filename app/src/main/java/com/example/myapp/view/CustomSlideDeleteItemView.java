package com.example.myapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.util.Methods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-3-19
 * Time: 下午3:54
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomSlideDeleteItemView extends FrameLayout{
    private Context mContext;
    private List<View> menuViews = new ArrayList<>();
    private View contentView;
    private LinearLayout menuViewContainer;
    private int contentViewId;
    private int contentViewWidth,contentViewHeight,menuWidth,menuHeight;

    public boolean isMenuOpen() {
        return isMenuOpen;
    }

    private boolean isMenuOpen = false;

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }
    private OnMenuClickListener onMenuClickListener;

    public interface OnMenuClickListener{
        void onMenuClick(int i,int counts);
    }
    public CustomSlideDeleteItemView(Context context) {
        this(context, null);
    }

    public CustomSlideDeleteItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSlideDeleteItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        menuWidth = Methods.computePixelsWithDensity(mContext,100);
        Log.d("zyr","menuWidth:" + menuWidth);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSlideDeleteItemView);
        for(int i=0;i<typedArray.length();i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CustomSlideDeleteItemView_contentViewId:
                    contentViewId = typedArray.getResourceId(R.styleable.CustomSlideDeleteItemView_contentViewId,0);
                    break;
            }
        }
        if(contentViewId!=0){
            contentView = findViewById(contentViewId);
        }
        menuViewContainer = new LinearLayout(mContext);
        menuViewContainer.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(menuViewContainer, layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(contentView!=null){
            contentView.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }
        layoutMenu();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=oldw || h!=oldh){
            contentViewHeight = menuHeight = h;
            contentViewWidth = w;
//            Log.d("zyr","contentViewWidth:" + contentViewWidth);
        }
    }

    public void addMenu(String s,int color){
        TextView textView = new TextView(mContext);
        textView.setText(s);
        textView.setBackgroundColor(color);
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(menuWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        menuViews.add(textView);
        menuViewContainer.addView(textView, layoutParams);
        invalidate();
        for(int i=0;i<menuViews.size();i++){
            final int position = i;
            View view = menuViews.get(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onMenuClickListener!=null){
                        onMenuClickListener.onMenuClick(position,menuViews.size());
                        scrollBack();
                    }
                }
            });
        }
    }

    public void clearAllMenu(){
        menuViews.clear();
        menuViewContainer.removeAllViews();
        invalidate();
    }

    private void layoutMenu() {
        menuViewContainer.layout(contentViewWidth, 0, contentViewWidth + menuWidth * menuViews.size(), contentViewHeight);
    }

    public void scroll(int deltaX,int lastDeltaX) {
        if(deltaX < 0){
            Log.d("zyr","deltaX:" + deltaX);
            Log.e("zyr","menuWidth * menuViews.size():" + menuWidth * menuViews.size());

            if(Math.abs(deltaX) > menuWidth * menuViews.size() ){
                scrollTo(menuWidth * menuViews.size(), 0);
                isMenuOpen = true;
            }else{
                scrollBy(-lastDeltaX, 0);
            }
        }else{
            if(isMenuOpen()){
                Log.d("zyr","deltaX:" + deltaX);
                Log.e("zyr", "menuWidth * menuViews.size():" + menuWidth * menuViews.size());

                if(Math.abs(deltaX) > menuWidth * menuViews.size()){
                    scrollTo(0,0);
                    isMenuOpen = false;
                }else{
                    scrollBy(-lastDeltaX, 0);
                }
            }
        }
    }

    public void autoScroll(int deltaX){
        if(deltaX < 0){
            if(Math.abs(deltaX) < menuWidth){
                scrollTo(0, 0);
                isMenuOpen = false;
            }else{
                scrollTo(menuWidth * menuViews.size(), 0);
                isMenuOpen = true;
            }
        }else{
            if(isMenuOpen()){
                if(Math.abs(deltaX) < menuWidth){
                    scrollTo(menuWidth * menuViews.size(), 0);
                    isMenuOpen = true;
                }else{
                    scrollTo(0,0);
                    isMenuOpen = false;
                }
            }
        }
    }

    public void scrollBack(){
        scrollTo(0,0);
        isMenuOpen = false;
    }
}
