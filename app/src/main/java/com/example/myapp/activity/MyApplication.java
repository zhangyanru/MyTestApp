package com.example.myapp.activity;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.myapp.Model.ChatMessage;
import com.example.myapp.chatdemo.SocketService;

/**
 * Created by yanru.zhang on 16/6/15.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyApplication extends Application {
    private static Intent socketServiceIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        socketServiceIntent = new Intent(MyApplication.this,SocketService.class);
        startService(socketServiceIntent);
    }

    public static Intent getSocketServiceIntent() {
        return socketServiceIntent;
    }

}
