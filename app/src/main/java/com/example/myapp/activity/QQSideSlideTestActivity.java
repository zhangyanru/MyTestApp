package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter2;
import com.example.myapp.adapter.CustomSlideDeleteListViewAdapter;
import com.example.myapp.view.CustomSlideDeleteListView;

import java.util.ArrayList;

/**
 * Created by yanru.zhang on 16/7/18.
 * Email:yanru.zhang@renren-inc.com
 */
public class QQSideSlideTestActivity extends Activity {
    private CustomSlideDeleteListView listView;
    private CustomSlideDeleteListViewAdapter adapter;
    private ArrayList<String> strings = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qq_side_slide);
        listView = (CustomSlideDeleteListView) findViewById(R.id.lv);
        for(int i=0;i<20;i++){
            strings.add("zyr" + i);
        }
        adapter = new CustomSlideDeleteListViewAdapter(this,strings);
        listView.setAdapter(adapter);
    }
}
