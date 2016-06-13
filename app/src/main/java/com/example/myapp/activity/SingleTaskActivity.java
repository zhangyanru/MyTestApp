package com.example.myapp.activity;

import android.view.View;
import android.widget.Button;

import com.example.myapp.R;

/**
 * Created by yanru.zhang on 16/6/13.
 * Email:yanru.zhang@renren-inc.com
 */
public class SingleTaskActivity extends BaseActivity {
    private Button button;
    @Override
    protected void initView() {
        button = (Button) findViewById(R.id.btn);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.acitivity_single_task;
    }

    @Override
    public void initListener() {
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                finish();
                break;
        }
    }
}
