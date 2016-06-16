package com.example.myapp.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.example.myapp.chatdemo.SocketService;

/**
 * Created by admin on 15/9/16.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener{
    public View containerView;
    public Activity mContext;

    public SocketService.SocketServiceBinder socketServiceBinder;
    private ServiceConnection connection;
    public SocketService.SocketListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        init();
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        Log.d("zyr","BaseActivity onResume");
        super.onResume();
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("zyr","onServiceConnected");
                socketServiceBinder = (SocketService.SocketServiceBinder)service;
                socketServiceBinder.getService().setSocketListener(listener);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("zyr","onServiceDisconnected");
            }
        };
        bindService(MyApplication.getSocketServiceIntent(), connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    protected abstract void initView();

    protected abstract int onSetContainerViewId();

    public void init(){
        containerView = LayoutInflater.from(getApplication()).inflate(onSetContainerViewId(),null);
        setContentView(containerView);
    }

    public abstract void initListener();

    @Override
    public abstract void onClick(View v);

    public void show(Context context,Class<? extends Activity> activity){
        Intent intent = new Intent();
        intent.setClass(context, activity);
        this.startActivity(intent);
    }
    public void showActivityForResult(Context context,Class<? extends Activity> activity,int requestCode){
        Intent intent = new Intent();
        intent.setClass(context,activity);
        this.startActivityForResult(intent,requestCode);
    }

    public SocketService.SocketServiceBinder getSocketServiceBinder() {
        return socketServiceBinder;
    }
}
