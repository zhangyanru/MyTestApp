package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 15-11-26
 * Time: 下午3:41
 * Email: yanru.zhang@renren-inc.com
 */
public class AutoSlidActivity extends Activity {
    private ImageView imageView;
    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.auto_slid_layout);
        imageView = (ImageView)findViewById(R.id.image_view);
        root = (LinearLayout)findViewById(R.id.root_view);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("zyr","ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("zyr","ACTION_MOVE");
                        imageView.setX(event.getX()-imageView.getMeasuredWidth());
                        imageView.setY(event.getY()-imageView.getMeasuredHeight());
                        imageView.invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("zyr","ACTION_UP");
                        break;
                }
                return true;
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zyr","iamge view clicked!");
            }
        });
    }
}
