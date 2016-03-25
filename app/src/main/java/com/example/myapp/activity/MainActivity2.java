package com.example.myapp.activity;

import android.view.View;
import android.widget.Button;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-2-24
 * Time: 下午7:17
 * Email: yanru.zhang@renren-inc.com
 */
public class MainActivity2 extends BaseActivity {
    private Button scrollerTest;
    private Button shimmerText;
    private Button quxianAnimator;
    private Button pathAnimator;
    private Button customPullToRefreshListViewTest;
    private Button customSlideDeleteListViewTest;
    private Button popWindowTest;
    @Override
    protected void initView() {
        scrollerTest = (Button)findViewById(R.id.scroller_test);
        shimmerText = (Button)findViewById(R.id.shimmer_text);
        quxianAnimator = (Button)findViewById(R.id.quxian_animator);
        pathAnimator = (Button)findViewById(R.id.path_animator);
        customPullToRefreshListViewTest = (Button) findViewById(R.id.custom_pull_to_refresh_listview);
        customSlideDeleteListViewTest = (Button) findViewById(R.id.custom_slid_delete_list_view);
        popWindowTest = (Button) findViewById(R.id.popwindow_test);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_main2;
    }

    @Override
    public void initListener() {
        scrollerTest.setOnClickListener(this);
        shimmerText.setOnClickListener(this);
        quxianAnimator.setOnClickListener(this);
        pathAnimator.setOnClickListener(this);
        customPullToRefreshListViewTest.setOnClickListener(this);
        customSlideDeleteListViewTest.setOnClickListener(this);
        popWindowTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.scroller_test:
                show(this,ScrollerTestActivity.class);
                break;
            case R.id.shimmer_text:
                show(this, ShimmerTextViewTestActivity.class);
                break;
            case R.id.quxian_animator:
                show(this,QuXianAnimatorActivity.class);
                break;
            case R.id.path_animator:
                show(this,PathAnimatorActivity.class);
                break;
            case R.id.custom_pull_to_refresh_listview:
                show(this,CustomPullToRefreshListViewActivity.class);
                break;
            case R.id.custom_slid_delete_list_view:
                show(this,CustomSlideDeleteListViewTestActivity.class);
                break;
            case R.id.popwindow_test:
                show(this,PopupWindowTestActivity.class);
                break;
        }

    }
}
