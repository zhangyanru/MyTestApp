package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.example.myapp.R;
import com.example.myapp.view.XListView;

import java.util.ArrayList;

/**
 * Created by admin on 15/9/10.
 */
public class XListViewActivity extends Activity {
    private XListView xListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xlistview);
        xListView = (XListView)findViewById(R.id.xListView);
        ArrayList<String> data = new ArrayList<String>();
        for(int i=0;i<100;i++){
            data.add(i,"item"+i);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.xlistview_item,R.id.xListView_item,data);
        xListView.setAdapter(arrayAdapter);
    }
}
