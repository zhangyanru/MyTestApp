package com.example.myapp.activity;

import android.view.View;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter;
import com.example.myapp.view.CustomPullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-3-16
 * Time: 下午4:08
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomPullToRefreshListViewActivity extends BaseActivity {
    private CustomPullToRefreshListView customPullToRefreshListView;
    private CommonAdapter commonAdapter;
    private List<String> strings = new ArrayList<>();
    @Override
    protected void initView() {
        customPullToRefreshListView = (CustomPullToRefreshListView)findViewById(R.id.custom_pull_to_refresh_listview);
        for(int i=0;i<20;i++){
            strings.add("zyr" + i);
        }
        commonAdapter = new CommonAdapter(this,strings);
        customPullToRefreshListView.setAdapter(commonAdapter);
        customPullToRefreshListView.setOnRefreshListener(new CustomPullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    public void run() {
                        try {
                            sleep(500);     //等待三秒,自动进入软件主窗口
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    customPullToRefreshListView.onRefreshComplete();
                                }
                            });
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_custom_pull_to_refresh_listview_layout;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
