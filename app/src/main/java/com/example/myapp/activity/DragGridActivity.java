package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapp.R;
import com.example.myapp.adapter.DragGridAdapter;
import com.example.myapp.view.DragGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-1-20
 * Time: 下午3:09
 * Email: yanru.zhang@renren-inc.com
 */
public class DragGridActivity extends Activity {

    private static List<String> list = null;
    //  private static List<Integer> res = null;
    //自定义适配器
    private DragGridAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.drag_grid_activity);
        initData();
//
        DragGridView dragView = (DragGridView) findViewById(R.id.drag_grid);
//        System.out.println("dragView"+dragView);
        adapter = new DragGridAdapter(this, list);
        dragView.setAdapter(adapter);
    }

    public void initData() {
        list = new ArrayList<String>();
//     res = new ArrayList<Integer>();
        for (int i = 0; i < 12; i++) {
            list.add("grid_" + i % 12);
            //res.add(0x7f020000+i);
        }
    }
}