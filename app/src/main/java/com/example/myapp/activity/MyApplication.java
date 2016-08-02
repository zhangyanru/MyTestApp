package com.example.myapp.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

import com.example.myapp.chatdemo.SocketService;


/**
 * Created by yanru.zhang on 16/6/15.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static Intent socketServiceIntent;
    private static Application mContext;
    public  String APP_KEY;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        socketServiceIntent = new Intent(MyApplication.this,SocketService.class);
        startService(socketServiceIntent);
    }

    public static Intent getSocketServiceIntent() {
        return socketServiceIntent;
    }

    public static Context getContext(){
        return mContext;
    }

}
