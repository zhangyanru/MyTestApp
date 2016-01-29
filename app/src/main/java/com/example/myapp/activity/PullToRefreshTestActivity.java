package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-1-25
 * Time: 下午4:18
 * Email: yanru.zhang@renren-inc.com
 */
public class PullToRefreshTestActivity extends Activity {
    private PullToRefreshGridView pullToRefreshListView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pull_to_refresh_test);
        pullToRefreshListView = (PullToRefreshGridView)findViewById(R.id.pull_to_refresh_list_view);
        for(int i=0 ; i <10;i++){
            arrayList.add("zyr" + i);
        }
        arrayAdapter = new ArrayAdapter(this,R.layout.xlistview_item,R.id.xListView_item,arrayList);
        pullToRefreshListView.setAdapter(arrayAdapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pullToRefreshListView.onRefreshComplete();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
