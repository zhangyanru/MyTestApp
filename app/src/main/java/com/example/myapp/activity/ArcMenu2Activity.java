package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.view.ArcMenu2;

import java.lang.reflect.Method;

/**
 * Created by zyr
 * DATE: 15-11-21
 * Time: 下午3:05
 * Email: yanru.zhang@renren-inc.com
 */
public class ArcMenu2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arc_menu3);

//        ArcMenu2 arcMenu1 = (ArcMenu2)findViewById(R.id.arc_menu_1);
//        arcMenu1.setOnChildClickListener(new ArcMenu2.OnChildClickListener() {
//            @Override
//            public void OnChildClick(View view, int position) {
//                Toast.makeText(ArcMenu2Activity.this, position + "", Toast.LENGTH_SHORT).show();
//            }
//        });

        ArcMenu2 arcMenu2 = (ArcMenu2)findViewById(R.id.arc_menu_2);
        arcMenu2.setOnChildClickListener(new ArcMenu2.OnChildClickListener() {
            @Override
            public void OnChildClick(View view, int position) {
                Toast.makeText(ArcMenu2Activity.this,position + "",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
