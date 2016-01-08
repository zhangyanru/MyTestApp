package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.adapter.MutilItemListViewAddapter;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-1-7
 * Time: 下午5:03
 * Email: yanru.zhang@renren-inc.com
 */
public class MutilItemListViewActivity extends Activity {
    private ListView listView;
    private ArrayList<String> strings = new ArrayList<String>();
    private MutilItemListViewAddapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mutil_item_list_view);
        listView = (ListView)findViewById(R.id.list_view);
        for(int i=0;i<11;i++){
            strings.add("zyr" + i);
        }
        adapter = new MutilItemListViewAddapter(strings,MutilItemListViewActivity.this);
        listView.setAdapter(adapter);

    }


}
