package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.view.ExpandableTextView;

/**
 * Created by yanru.zhang on 16/7/22.
 * Email:yanru.zhang@renren-inc.com
 */
public class ExpandableTvActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expandable_tv);
    }
}
