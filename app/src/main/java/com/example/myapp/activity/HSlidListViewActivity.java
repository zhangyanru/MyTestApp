package com.example.myapp.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.myapp.R;
import com.example.myapp.adapter.CommonAdapter;
import com.example.myapp.view.HSlidListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 15-11-25
 * Time: 下午7:19
 * Email: yanru.zhang@renren-inc.com
 */
public class HSlidListViewActivity extends Activity {
    private HSlidListView listView;
    private CommonAdapter adapter;
    private ImageView imageView;
    private ArrayList<String> arrayList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.slid_listview);
        listView = (HSlidListView)findViewById(R.id.slid_list_view);
        adapter = new CommonAdapter(this);
        listView.setAdapter(adapter);
        for(int i=0;i<20;i++){
            arrayList.add("zhangyanru"+i);
        }
        adapter.setData(arrayList);
//        setContentView(R.layout.image);
//        imageView = (ImageView)findViewById(R.id.image);
//        Bitmap bitmap = getLoacalBitmap("/storage/emulated/0/DCIM/Camera/20151126_130457.jpg");
//        imageView.setImageBitmap(bitmap);
    }
    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
