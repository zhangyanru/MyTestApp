package com.example.myapp.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-2-25
 * Time: 下午12:35
 * Email: yanru.zhang@renren-inc.com
 */
public class ScrollerTestActivity extends BaseActivity {
    private Button button;

    @Override
    protected void initView() {
        button = (Button)findViewById(R.id.t);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_scroller_test;
    }

    @Override
    public void initListener() {
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.t:
                button.scrollBy(20, 0);//zheng,left;fu,right
                break;
        }
    }
}
