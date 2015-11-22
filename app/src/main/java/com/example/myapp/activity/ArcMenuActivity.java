package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.view.ArcMenu;

/**
 * Created by zyr
 * DATE: 15-11-18
 * Time: 下午8:38
 * Email: yanru.zhang@renren-inc.com
 */
public class ArcMenuActivity extends Activity {

    private ArcMenu arcMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_menu);

        arcMenu = (ArcMenu)findViewById(R.id.arc_menu);
        arcMenu.setOnChildClickListener(new ArcMenu.OnChildClickListener() {
            @Override
            public void OnChildClick(int position) {
                Toast.makeText(ArcMenuActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });
        arcMenu.setOnPointClickListener(new ArcMenu.OnPointClickListener() {
            @Override
            public void onPointClick() {
                Toast.makeText(ArcMenuActivity.this,"Point Click",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
