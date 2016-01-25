package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-1-25
 * Time: 下午4:18
 * Email: yanru.zhang@renren-inc.com
 */
public class PullToRefreshTestActivity extends Activity {
    private PullToRefreshListView pullToRefreshListView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pull_to_refresh_test);
        pullToRefreshListView = (PullToRefreshListView)findViewById(R.id.pull_to_refresh_list_view);
        for(int i=0 ; i <10;i++){
            arrayList.add("zyr" + i);
        }
        arrayAdapter = new ArrayAdapter(this,R.layout.xlistview_item,R.id.xListView_item,arrayList);
        pullToRefreshListView.setAdapter(arrayAdapter);
    }
}
