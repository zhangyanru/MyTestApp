package com.example.myapp.activity;

import android.view.View;

import com.example.myapp.R;
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
    @Override
    protected void initView() {
        shimmerTextView = (ShimmerTextView)findViewById(R.id.shimmer_text_view);
        shimmer = new Shimmer();
        shimmer.start(shimmerTextView);
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
