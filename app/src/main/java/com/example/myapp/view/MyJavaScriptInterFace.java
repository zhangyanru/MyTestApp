package com.example.myapp.view;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by yanru.zhang on 16/7/11.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyJavaScriptInterFace {
    Context mContext;

    public MyJavaScriptInterFace(Context context){
        mContext = context;
    }

    public void showToast(String toast){
        Toast.makeText(mContext,toast,Toast.LENGTH_SHORT).show();
    }
}
