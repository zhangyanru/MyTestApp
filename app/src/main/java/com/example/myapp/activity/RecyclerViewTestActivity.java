package com.example.myapp.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonRecyclerViewAdapter;
import com.example.myapp.util.Methods;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-4-14
 * Time: 下午3:45
 * Email: yanru.zhang@renren-inc.com
 */
public class RecyclerViewTestActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private CommonRecyclerViewAdapter adapter;

    private List<String> strings = new ArrayList<>();

    private TextView insertTv;

    private TextView deleteTv;

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        insertTv = (TextView) findViewById(R.id.insert_btn);
        deleteTv = (TextView) findViewById(R.id.delete_btn);

        //设置布局管理器

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        //设置adapter

        for(int i=0;i<30;i++){
            strings.add("zyr" + i);
        }
        adapter = new CommonRecyclerViewAdapter(this,strings);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CommonRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Methods.toast(RecyclerViewTestActivity.this,position+"");
            }
        });

        //设置Item增加、移除动画

        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_recycler_view_test;
    }

    @Override
    public void initListener() {
        insertTv.setOnClickListener(this);
        deleteTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insert_btn:
                strings.add("zyr"+strings.size());
                adapter.insertData(2,"zyr");
                break;
            case R.id.delete_btn:
                strings.remove(2);
                adapter.deleteData(2);
                break;
        }

    }
}
