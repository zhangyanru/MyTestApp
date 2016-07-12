package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.view.MyWebView;

/**
 * Created by yanru.zhang on 16/7/11.
 * Email:yanru.zhang@renren-inc.com
 */
public class WebViewTestActivity extends Activity {
    private MyWebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        myWebView = (MyWebView) findViewById(R.id.my_web_view);
        myWebView.load("https://www.baidu.com/");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            myWebView.back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
