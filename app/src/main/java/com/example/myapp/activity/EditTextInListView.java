package com.example.myapp.activity;

import android.view.View;
import android.widget.ListView;

import com.example.myapp.R;
import com.example.myapp.adapter.EditTextInListViewAdapter;

import java.util.ArrayList;

/**
 * Created by zhangyanru on 2016/12/19.
 * 功能描述：
 */

public class EditTextInListView extends BaseActivity {
    ListView listView;
    EditTextInListViewAdapter adapter;
    ArrayList<String> strings = new ArrayList<String>();

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new EditTextInListViewAdapter(this,listView);
        listView.setAdapter(adapter);
        for (int i = 0; i < 50; i++) {
            strings.add("s" + i);
        }
        adapter.setData(strings);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.edit_text_in_list_view;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
