package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zyr
 * DATE: 16-2-23
 * Time: 下午6:42
 * Email: yanru.zhang@renren-inc.com
 */
public class DanMuViewGroup extends RelativeLayout {
    private Context mContext;
    private ArrayList<String> stringArrayList = new ArrayList<String>();
    private int w,h;
    Random random = new Random();

    public DanMuViewGroup(Context context) {
        this(context, null);
    }

    public DanMuViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DanMuViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w!=oldw || h!=oldh){
            this.w = w;
            this.h = h;
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    public void setTexts(ArrayList<String> arrayList){
        this.stringArrayList.clear();
        this.stringArrayList = new ArrayList<String>(arrayList);

        RelativeLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i=0;i<arrayList.size();i++){
            DanMuView danMuView = new DanMuView(mContext);
            danMuView.setText(arrayList.get(i));
            addView(danMuView, lp);
        }
    }

}
