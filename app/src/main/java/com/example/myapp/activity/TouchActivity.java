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
    private MyView button;
    private TextView textView;
    private MyLinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        button =(MyView) findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.text);
        linearLayout = (MyLinearLayout)findViewById(R.id.linear_layout);


//        linearLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        textView.append("linearLayout ACTION_DOWN!\n");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        textView.append("linearLayout ACTION_UP!\n");
//
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        textView.append("linearLayout ACTION_MOVE!\n");
//                        break;
//                }
//                return false;
//            }
//        });
//
//
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("zyr","button ACTION_DOWN!");

                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("zyr", "button ACTION_UP!");

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("zyr", "button ACTION_MOVE!");

                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }
}
