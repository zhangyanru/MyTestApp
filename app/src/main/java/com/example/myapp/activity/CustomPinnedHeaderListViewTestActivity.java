package com.example.myapp.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.example.myapp.R;
import com.example.myapp.adapter.CustomPinnedHeaderListViewAdapter;
import com.example.myapp.util.Methods;
import com.example.myapp.view.CustomPinnedHeaderListView;

/**
 * Created by zyr
 * DATE: 16-4-13
 * Time: 下午2:25
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomPinnedHeaderListViewTestActivity extends BaseActivity{

    private CustomPinnedHeaderListView listView;

    private CustomPinnedHeaderListViewAdapter adapter;

    private View headerView;

    private View footerView;

    @Override
    protected void initView() {
        listView = (CustomPinnedHeaderListView)findViewById(R.id.lv);
        headerView = LayoutInflater.from(this).inflate(R.layout.cus_pinned_header_lv_header,null);
        footerView = LayoutInflater.from(this).inflate(R.layout.cus_pinned_header_lv_footer,null);
        listView.addHeaderView(headerView);
        listView.addFooterView(footerView);
        adapter = new CustomPinnedHeaderListViewAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnMyItemClickListener(new CustomPinnedHeaderListView.MyOnItemClickListener() {
            @Override
            public void onSectionItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                Methods.toast(CustomPinnedHeaderListViewTestActivity.this,"s:" + section + "    item:" + position);
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {
                Methods.toast(CustomPinnedHeaderListViewTestActivity.this,"s:" + section );
            }

            @Override
            public void onHeaderClick(AdapterView<?> adapterView, View view, int position, long id) {
                Methods.toast(CustomPinnedHeaderListViewTestActivity.this,"header:" + position );
            }

            @Override
            public void onFooterClick(AdapterView<?> adapterView, View view, int position, long id) {
                Methods.toast(CustomPinnedHeaderListViewTestActivity.this,"footer:" + position);
            }
        });
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_custom_pinned_header_lv;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
