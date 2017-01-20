package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapp.R;
import com.example.myapp.view.CustomPullToRefreshWebView;
import com.example.myapp.view.MyWebView;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;

/**
 * Created by yanru.zhang on 16/7/11.
 * Email:yanru.zhang@renren-inc.com
 */
public class PullToRefreshWebViewTestActivity extends Activity {
    private CustomPullToRefreshWebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh_web_view);

        myWebView = (CustomPullToRefreshWebView) findViewById(R.id.my_web_view);
        myWebView.load("https://www.baidu.com/");

        myWebView.setOnRefreshListener(new CustomPullToRefreshWebView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myWebView.load("http://www.sina.com.cn");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myWebView.onRefreshComplete();
            }
        });
    }

}
