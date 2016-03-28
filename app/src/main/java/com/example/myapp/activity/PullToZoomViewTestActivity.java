package com.example.myapp.activity;

import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter;
import com.example.myapp.util.Methods;
import com.example.myapp.view.CustomPullToRefreshListView;
import com.example.myapp.view.CustomPullToZoomListView;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-3-25
 * Time: 下午5:24
 * Email: yanru.zhang@renren-inc.com
 */
public class PullToZoomViewTestActivity extends BaseActivity {
    private CustomPullToZoomListView customPullToZoomListView;
    private TextView signUpBtn;
    private TextView loginBtn;
    private CommonAdapter commonAdapter;
    private ArrayList<String> strings  = new ArrayList<>();
    @Override
    protected void initView() {
        customPullToZoomListView = (CustomPullToZoomListView)findViewById(R.id.pull_to_zoom);
        for(int i=0;i<20;i++){
            strings.add("zyr" + i);
        }
        commonAdapter = new CommonAdapter(this,strings);
        customPullToZoomListView.setAdapter(commonAdapter);

        signUpBtn = (TextView) findViewById(R.id.sign_up);
        loginBtn = (TextView) findViewById(R.id.login);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_pull_to_zoom_view_layout;
    }

    @Override
    public void initListener() {
        signUpBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up:
                Methods.toast(mContext,"sign up");
                break;
            case R.id.login:
                Methods.toast(mContext,"login");
                break;
        }
    }
}
