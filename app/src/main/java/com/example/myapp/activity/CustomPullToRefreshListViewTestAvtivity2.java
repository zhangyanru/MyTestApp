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
//        View head =  LayoutInflater.from(this).inflate(R.layout.header_listview,null);
        headRoot =  (FrameLayout)LayoutInflater.from(this).inflate(R.layout.header_listview, listView, false);//这样做headRoot不会被忽视
        headContainer = (FrameLayout) headRoot.findViewById(R.id.head_container);
        Log.d("zyr", "-Methods.computePixelsWithDensity(this,100) :" + -Methods.computePixelsWithDensity(this, 100));

//        listView = (PullToRefreshListView) findViewById(R.id.listview);

        listView.addHeaderView(headRoot, null, false);
//
//        View v = LayoutInflater.from(this).inflate(R.layout.header_listview2, null);
//        listView.addHeaderView(v);
//
//
        for(int i=0;i<20;i++){
            strings.add("zyr" + i);
        }
        commonAdapter = new CommonAdapter(this,strings);
        listView.setAdapter(commonAdapter);
//        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        listView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                listView.onRefreshComplete();
//                            }
//                        });
//
//                    }
//                }).start();
//
//            }
//
//            @Override
//            public void onLoadMore() {
//
//            }
//        });
//

//        headRoot.setVisibility(View.GONE);//没做用
//        headContainer.setVisibility(View.GONE);//可以变为不可见
        headRoot.setPadding(0, -Methods.computePixelsWithDensity(this, 100), 0, 0);//没做用，晕死
        listView.requestLayout();
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
