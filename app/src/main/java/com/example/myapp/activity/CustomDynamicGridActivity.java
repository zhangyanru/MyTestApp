package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter;
import com.example.myapp.adapter.CustomDynamicGridAdapter;
import com.example.myapp.view.CustomDynamicGrid;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-2-2
 * Time: 下午3:05
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomDynamicGridActivity extends Activity {
    private CustomDynamicGrid customDynamicGrid;
    private CustomDynamicGridAdapter customDynamicGridAdapter;
    private ArrayList<String> arrayList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_dynamic_grid);
        customDynamicGrid = (CustomDynamicGrid)findViewById(R.id.custom_dynamic_grid);
        for(int i=0;i<100;i++){
            arrayList.add("zyr" + i);
        }
        customDynamicGridAdapter = new CustomDynamicGridAdapter(this,arrayList);
        customDynamicGrid.setAdapter(customDynamicGridAdapter);
        customDynamicGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                customDynamicGrid.startEditMode(position);
                return true;
            }
        });

        customDynamicGrid.setActionUpListener(new CustomDynamicGrid.ActionUpListener() {
            @Override
            public void onActionUp(int downPosition, int upPosition) {
            }
        });

        customDynamicGrid.setSwapListener(new CustomDynamicGrid.SwapListener() {
            @Override
            public void onSwap(int originPosition, int targetPosition) {
                String originItem = arrayList.get(originPosition);
                String targetItem = arrayList.get(targetPosition);
                arrayList.remove(originPosition);
                arrayList.add(originPosition, targetItem);
                arrayList.remove(targetPosition);
                arrayList.add(targetPosition, originItem);
                customDynamicGridAdapter.setData(arrayList);
            }
        });
    }
}
