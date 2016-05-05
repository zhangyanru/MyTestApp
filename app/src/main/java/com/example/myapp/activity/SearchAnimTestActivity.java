package com.example.myapp.activity;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-4-25
 * Time: 上午11:35
 * Email: yanru.zhang@renren-inc.com
 */
public class SearchAnimTestActivity extends BaseActivity {

    private LinearLayout searchLy;
    private LinearLayout searchEdtTvLy;
    private Button btn;


    private int searchEditTvLyWidth,searchLyWidth;

    @Override
    protected void initView() {
        searchLy = (LinearLayout) findViewById(R.id.lead_list_search_ly);
        searchEdtTvLy = (LinearLayout) findViewById(R.id.lead_list_search_edit_tv_ly);

        btn = (Button) findViewById(R.id.btn);

        searchEdtTvLy.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                searchEditTvLyWidth = searchEdtTvLy.getMeasuredWidth();
                searchLyWidth = searchLy.getMeasuredWidth();
                searchEdtTvLy.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                startAnim();
            }
        });
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_search_anim_layout;
    }

    @Override
    public void initListener() {
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                startAnim();
                break;
        }
    }

    private void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(searchLyWidth + (searchLyWidth - searchEditTvLyWidth),searchLyWidth);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                Log.d("zyr","sacle X :" + value);
                searchLy.getLayoutParams().width = value;
                searchLy.setLayoutParams(searchLy.getLayoutParams());
            }
        });
        valueAnimator.start();
    }
}
