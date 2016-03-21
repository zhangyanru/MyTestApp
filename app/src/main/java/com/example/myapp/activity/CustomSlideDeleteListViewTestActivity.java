package com.example.myapp.activity;

import android.view.View;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter;
import com.example.myapp.adapter.CustomSlideDeleteListViewAdapter;
import com.example.myapp.view.CustomSlideDeleteListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-3-18
 * Time: 下午5:40
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomSlideDeleteListViewTestActivity extends BaseActivity {
    private CustomSlideDeleteListView customSlideDeleteListView;
    private CustomSlideDeleteListViewAdapter customSlideDeleteListViewAdapter;
    private List<String> stringList = new ArrayList<>();
    @Override
    protected void initView() {
        customSlideDeleteListView = (CustomSlideDeleteListView) findViewById(R.id.custom_slid_delete_list_view);
        for(int i=0;i<100;i++){
            stringList.add("zyr" + i);
        }
        customSlideDeleteListViewAdapter = new CustomSlideDeleteListViewAdapter(this,stringList);
        customSlideDeleteListView.setAdapter(customSlideDeleteListViewAdapter);


    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_custom_slide_delete_list_view;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
