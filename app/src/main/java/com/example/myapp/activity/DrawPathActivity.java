package com.example.myapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.myapp.R;
import com.example.myapp.view.MaskView;

/**
 * Created by admin on 15/9/8.
 */
public class DrawPathActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawpath);

        LinearLayout root= (LinearLayout)findViewById(R.id.linear);
    }
}
