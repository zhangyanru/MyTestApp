package com.example.myapp.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonRecyclerViewAdapter;
import com.example.myapp.util.Methods;
import com.example.myapp.view.CustomRecyclerViewGalleryView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-4-15
 * Time: 下午2:02
 * Email: yanru.zhang@renren-inc.com
 */
public class RecyclerViewGalleryTestActivity extends BaseActivity {

    private CustomRecyclerViewGalleryView recyclerViewGalleryView;

    private TextView tv;

    private CommonRecyclerViewAdapter adapter;

    private List<String> strings = new ArrayList<>();

    @Override
    protected void initView() {
        recyclerViewGalleryView = (CustomRecyclerViewGalleryView) findViewById(R.id.recycler_view);
        tv = (TextView) findViewById(R.id.tv);

        //设置布局管理器
        recyclerViewGalleryView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //设置adapter
        for(int i=0;i<30;i++){
            strings.add("zyr" + i);
        }
        adapter = new CommonRecyclerViewAdapter(this,strings);
        recyclerViewGalleryView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CommonRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Methods.toast(RecyclerViewGalleryTestActivity.this, position + "");
            }
        });
        //
        recyclerViewGalleryView.setFirstItemChangedListener(new CustomRecyclerViewGalleryView.OnFirstItemChangedListener() {
            @Override
            public void onFirstItemChanged(View view, int position) {
                tv.setText(adapter.getStrings().get(position));
            }
        });
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_recycler_view_gallery_test_layout;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
