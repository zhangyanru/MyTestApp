package com.example.myapp.chatdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.myapp.Model.ChatMessage;
import com.example.myapp.R;
import com.example.myapp.activity.BaseActivity;


/**
 * Created by yanru.zhang on 16/6/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class LoginActivity extends BaseActivity{
    private EditText accountEditTv,passwordEditTv;
    private TextView loginBtn,registerBtn;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            JSONObject jsonObject = JSONObject.parseObject(data);
            if(jsonObject.getIntValue("type") == MessageType.LOGIN){
                Toast.makeText(LoginActivity.this,jsonObject.getString("status_msg"),Toast.LENGTH_SHORT).show();
                if(jsonObject.getIntValue("status") == MessageType.STATUS_SUCCESS){
                    show(LoginActivity.this,FriendListActivity.class);
                }
            }
        }
    };
    @Override
    protected void initView() {
        accountEditTv = (EditText) findViewById(R.id.account);
        passwordEditTv = (EditText) findViewById(R.id.password);
        loginBtn = (TextView) findViewById(R.id.login);
        registerBtn = (TextView) findViewById(R.id.register_btn);

    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                if(TextUtils.isEmpty(accountEditTv.getText().toString())){
                    Toast.makeText(LoginActivity.this,"account cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordEditTv.getText().toString())){
                    Toast.makeText(LoginActivity.this,"password cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                login();
                break;
            case R.id.register_btn:
                show(LoginActivity.this,RegisterActivity.class);
                break;
        }
    }

    private void login() {
        String account = accountEditTv.getText().toString();
        String password = passwordEditTv.getText().toString();
        JSONObject chatMessage = new JSONObject();
        chatMessage.put("type",ChatMessage.LOGIN);
        chatMessage.put("account",account);
        chatMessage.put("password",password);

        Intent intent = new Intent(SocketService.SEND_ACTION);
        intent.putExtra("data",chatMessage.toJSONString());
        sendBroadcast(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(receiver,new IntentFilter(SocketService.RECEIVE_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
