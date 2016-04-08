package com.example.myapp.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter;
import com.example.myapp.util.Methods;
import com.example.myapp.view.CustomPullToRefreshListView2;
import com.example.myapp.view.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-4-6
 * Time: 下午4:05
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomPullToRefreshListViewTestAvtivity2 extends BaseActivity {
    private FrameLayout headRoot;
    private FrameLayout headContainer;
    private CustomPullToRefreshListView2 listView;
//    private PullToRefreshListView listView;

    private CommonAdapter commonAdapter;
    private ArrayList<String> strings  = new ArrayList<>();

    @Override
    protected void initView() {
        listView = (CustomPullToRefreshListView2) findViewById(R.id.listview);
        for(int i=0;i<20;i++){
            strings.add("zyr" + i);
        }
        commonAdapter = new CommonAdapter(this,strings);
        listView.setAdapter(commonAdapter);
        listView.setOnRefreshListener(new CustomPullToRefreshListView2.OnRefreshListener() {
            @Override
            public void onDownPullRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        listView.hideHeaderView();
                    }
                }).start();
            }
        });
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_custom_pull_to_refresh_listview2;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
