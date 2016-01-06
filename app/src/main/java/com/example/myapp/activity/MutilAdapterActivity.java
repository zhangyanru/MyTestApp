package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapp.Model.SearchResult;
import com.example.myapp.Model.Transaction;
import com.example.myapp.R;
import com.example.myapp.adapter.GlobalSearchAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyr
 * DATE: 16-1-4
 * Time: 下午3:14
 * Email: yanru.zhang@renren-inc.com
 */
public class MutilAdapterActivity extends Activity {
    private ListView listview;

    private GlobalSearchAdapter adapter;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private Map<Integer,ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();

    final int TYPE_TRANS = 0;
    final int TYPE_DOCS = 1;
    final int TYPE_TASKS = 2;
    final int TYPE_MEMBERS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mutil_listview_layout);
        listview = (ListView)findViewById(R.id.list_view);

        for(int i=0;i<10;i++){
            arrayList.add("zyr" + i);
        }

        SearchResult searchResult = new SearchResult();
        adapter = new GlobalSearchAdapter(searchResult,MutilAdapterActivity.this);

        listview.setAdapter(adapter);
    }
}
