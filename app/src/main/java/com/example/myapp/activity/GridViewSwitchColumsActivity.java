package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter;
import com.example.myapp.adapter.CommonAdapter2;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-2-22
 * Time: 下午2:23
 * Email: yanru.zhang@renren-inc.com
 */
public class GridViewSwitchColumsActivity extends Activity implements View.OnClickListener{
    private GridView gridView;
    private CommonAdapter commonAdapter;
    private CommonAdapter2 commonAdapter2;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private Button gridMode;
    private Button listMode;
    private boolean isScroll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_grid_switch_colums_layout);
        gridView = (GridView)findViewById(R.id.grid_view);
        gridMode = (Button)findViewById(R.id.grid);
        listMode = (Button)findViewById(R.id.list);
        gridMode.setOnClickListener(this);
        listMode.setOnClickListener(this);
        for(int i=0;i<50;i++){
            arrayList.add("zyr" + i);
        }
        commonAdapter = new CommonAdapter(this,arrayList);
        commonAdapter2 = new CommonAdapter2(this,arrayList);

        gridView.setAdapter(commonAdapter);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState ==0){
                    isScroll = false;
                }else{
                    isScroll = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.grid:
                if(isScroll){

                    return;
                }
                gridView.setNumColumns(2);
                gridView.setAdapter(commonAdapter2);
                break;
            case R.id.list:
                if(isScroll){
                    return;
                }
                gridView.setNumColumns(1);
                gridView.setAdapter(commonAdapter);
                break;
        }
    }
}
