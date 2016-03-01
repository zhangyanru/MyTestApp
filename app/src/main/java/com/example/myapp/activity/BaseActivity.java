package com.example.myapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by admin on 15/9/16.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener{
    public View containerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
        initListener();
    }

    protected abstract void initView();

    protected abstract int onSetContainerViewId();

    public void init(){
        setContentView(onSetContainerViewId());
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
}
