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
    @Override
    protected void initView() {
        scrollerTest = (Button)findViewById(R.id.scroller_test);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_main2;
    }

    @Override
    public void initListener() {
        scrollerTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.scroller_test:
                show(this,ScrollerTestActivity.class);
                break;
        }
    }
}