package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.view.MyLinearLayout;
import com.example.myapp.view.MyView;

/**
 * Created by zyr
 * DATE: 15-12-10
 * Time: 下午3:48
 * Email: yanru.zhang@renren-inc.com
 */
public class TouchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
    }
}
