package com.example.myapp.activity;

import android.view.View;

import com.example.myapp.R;
import com.example.myapp.view.Titanic;
import com.example.myapp.view.TitanicTextView;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

/**
 * Created by zyr
 * DATE: 16-3-1
 * Time: 下午4:01
 * Email: yanru.zhang@renren-inc.com
 */
public class ShimmerTextViewTestActivity extends BaseActivity {
    private Shimmer shimmer;
    private ShimmerTextView shimmerTextView;
    private Titanic titanic;
    private TitanicTextView myTitanicTextView;
    @Override
    protected void initView() {
        shimmerTextView = (ShimmerTextView)findViewById(R.id.shimmer_text_view);
        shimmer = new Shimmer();
        shimmer.start(shimmerTextView);

        myTitanicTextView = (TitanicTextView)findViewById(R.id.titanic_tv);
        titanic = new Titanic();
        titanic.start(myTitanicTextView);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_shimmer_text_test_layout;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmer.cancel();
    }
}
