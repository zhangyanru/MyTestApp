package com.example.myapp.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter;
import com.example.myapp.util.Methods;
import com.example.myapp.view.CustomPullToRefreshListView2;
import com.example.myapp.view.CustomSearchListView;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-4-6
 * Time: 下午4:05
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomSearchListViewTestActivity extends BaseActivity {
    private FrameLayout headRoot;
    private FrameLayout headContainer;
    private CustomSearchListView listView;
//    private PullToRefreshListView listView;

    private CommonAdapter commonAdapter;
    private ArrayList<String> strings  = new ArrayList<>();

    @Override
    protected void initView() {
        listView = (CustomSearchListView) findViewById(R.id.listview);
        for(int i=0;i<20;i++){
            strings.add("zyr" + i);
        }
        commonAdapter = new CommonAdapter(this,strings);
        listView.setAdapter(commonAdapter);
        listView.setOnRefreshListener(new CustomSearchListView.OnRefreshListener() {
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Methods.toast(CustomSearchListViewTestActivity.this,id+"");
            }
        });
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_custom_search_listview;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
