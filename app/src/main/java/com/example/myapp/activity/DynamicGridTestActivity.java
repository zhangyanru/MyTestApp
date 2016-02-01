package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter;
import com.example.myapp.adapter.DynamicGridTestAdapter;

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-1-26
 * Time: 上午11:38
 * Email: yanru.zhang@renren-inc.com
 */
public class DynamicGridTestActivity extends Activity {
    private DynamicGridView dynamicGridView;
    private DynamicGridTestAdapter adapter;
    private CommonAdapter commonAdapter;
    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dynamic_grid_test_layout);
        dynamicGridView = (DynamicGridView)findViewById(R.id.dynamic_grid);
        for(int i=0;i<10;i++){
            arrayList.add("zyr" + i);
        }
//        adapter = new DynamicGridTestAdapter(this,arrayList,3);
//        dynamicGridView.setAdapter(adapter);

        commonAdapter = new CommonAdapter(this,arrayList);
        dynamicGridView.setAdapter(commonAdapter);
        dynamicGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dynamicGridView.startEditMode(position);
                return false;
            }
        });

        dynamicGridView.setOnDropListener(new DynamicGridView.OnDropListener() {
            @Override
            public void onActionDrop() {
                if (dynamicGridView.isEditMode()) {
                    dynamicGridView.stopEditMode();
                }
                Log.d("zyr", adapter.getItems().toString());
            }
        });
    }
}
