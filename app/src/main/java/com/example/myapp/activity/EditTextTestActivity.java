package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-2-17
 * Time: 上午10:37
 * Email: yanru.zhang@renren-inc.com
 */
public class EditTextTestActivity extends Activity{
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edittext_test_layout);
        relativeLayout = (RelativeLayout)findViewById(R.id.root);
        View view = new View(this);
        view.setFocusable(true);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        relativeLayout.addView(view,new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.requestFocus();
    }
}
