package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.adapter.DynamicGridTestAdapter;
import com.example.myapp.view.PullToRefreshDynamicGrid;
import com.example.myapp.view.PullToRefreshLinearLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-1-26
 * Time: 下午3:24
 * Email: yanru.zhang@renren-inc.com
 */
public class PullToRefreshDynamicGridTestActivity extends Activity {
//    private PullToRefreshLinearLayout pullToRefreshLinearLayout;
//    private TextView textView;
//    private DynamicGridView dynamicGridView;
    private PullToRefreshDynamicGrid pullToRefreshDynamicGrid;
    private DynamicGridView dynamicGridView2;
//
    private DynamicGridTestAdapter adapter;
    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pull_to_refresh_dynamic_layout);

//        textView = (TextView)findViewById(R.id.text);
//        pullToRefreshLinearLayout = (PullToRefreshLinearLayout)findViewById(R.id.pull_to_refresh_linearlayout);
//        dynamicGridView = (DynamicGridView)findViewById(R.id.dgrid);
//
//        pullToRefreshLinearLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<LinearLayout>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<LinearLayout> refreshView) {
//                textView.setText("Refreshing....");
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d("zyr", "refreshing.....");
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                pullToRefreshLinearLayout.onRefreshComplete();//要在UI线程调用。不然抛异常
//                                textView.setText("Complete!");
//                            }
//                        });
//                    }
//                }).start();
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<LinearLayout> refreshView) {
//
//            }
//        });
//        for(int i=0;i<100;i++){
//            arrayList.add("zyr" + i);
//        }
//        adapter = new DynamicGridTestAdapter(this,arrayList,3);
//        dynamicGridView.setAdapter(adapter);
//        dynamicGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                dynamicGridView.startEditMode(position);
//                return false;
//            }
//        });
//
//        dynamicGridView.setOnDropListener(new DynamicGridView.OnDropListener() {
//            @Override
//            public void onActionDrop() {
//                if (dynamicGridView.isEditMode()) {
//                    dynamicGridView.stopEditMode();
//                }
//                Log.d("zyr", adapter.getItems().toString());
//            }
//        });



        pullToRefreshDynamicGrid = (PullToRefreshDynamicGrid)findViewById(R.id.pull_dy_grid);
        dynamicGridView2 = pullToRefreshDynamicGrid.getRefreshableView();
        dynamicGridView2.setNumColumns(3);
        dynamicGridView2.setWobbleInEditMode(false);
        for(int i=0;i<100;i++){
            arrayList.add("zyr" + i);
        }
        adapter = new DynamicGridTestAdapter(this,arrayList,3);

        dynamicGridView2.setAdapter(adapter);
        dynamicGridView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dynamicGridView2.startEditMode(position);
                return false;
            }
        });

        dynamicGridView2.setOnDropListener(new DynamicGridView.OnDropListener() {
            @Override
            public void onActionDrop() {
                if (dynamicGridView2.isEditMode()) {
                    dynamicGridView2.stopEditMode();
                }
                Log.d("zyr", adapter.getItems().toString());
            }
        });
    }
}
