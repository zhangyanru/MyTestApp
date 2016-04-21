package com.example.myapp.activity;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter;
import com.example.myapp.adapter.ExpandableLayoutAdapter;
import com.example.myapp.view.CustomExpandableLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-4-19
 * Time: 下午4:15
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomExpandableLayoutTestActivity extends BaseActivity {

    private CustomExpandableLayout customExpandableLayout;

    private ListView listView;

    private ExpandableLayoutAdapter adapter;

    private List<String> strings = new ArrayList<>();

    @Override
    protected void initView() {
        customExpandableLayout = (CustomExpandableLayout)findViewById(R.id.expandlayout_ly);
        Log.d("zyr","child count : " + customExpandableLayout.getChildCount());
        listView = (ListView) findViewById(R.id.lv);

        for(int i=0;i<30;i++){
            strings.add("zyr" + i);
        }

        adapter = new ExpandableLayoutAdapter(this,strings);

        listView.setAdapter(adapter);

    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_custom_expandable_layout;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
