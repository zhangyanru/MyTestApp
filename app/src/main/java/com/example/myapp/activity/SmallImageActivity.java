package com.example.myapp.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonImageAdapter;
import com.example.myapp.view.ViewPagerDialog;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 15-11-27
 * Time: 上午11:03
 * Email: yanru.zhang@renren-inc.com
 */
public class SmallImageActivity extends Activity {
    private GridView gridView;
    private CommonImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_view_layout);

        gridView = (GridView)findViewById(R.id.grid_view);
        adapter = new CommonImageAdapter(this);
        gridView.setAdapter(adapter);

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for(int i=0;i<9;i++){
            arrayList.add(R.drawable.icon_album);
        }
        adapter.setData(arrayList);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(SmallImageActivity.this,BigImageActivity.class);
                intent.putExtra("id",position);
                startActivity(intent);

//                ViewPagerDialog dialog = new ViewPagerDialog(SmallImageActivity.this);
//                dialog.show();
            }
        });
    }

}
