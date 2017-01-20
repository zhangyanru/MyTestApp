package com.example.myapp.activity;

import android.view.View;
import android.widget.Button;

import com.example.myapp.R;

/**
 * Created by zhangyanru on 2016/12/22.
 * 功能描述：
 */

public class TestUncaughtHandlerActivity extends BaseActivity {
    private Button button;

    @Override
    protected void initView() {
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_test_uncaugth_handler;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         button.setText("lll");
                     }
                 }).start();
                break;
        }
    }
}
