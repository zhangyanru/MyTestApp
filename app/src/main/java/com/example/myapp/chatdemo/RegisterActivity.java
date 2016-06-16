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

import com.alibaba.fastjson.JSONObject;
import com.example.myapp.Model.ChatMessage;
import com.example.myapp.R;
import com.example.myapp.activity.BaseActivity;

/**
 * Created by yanru.zhang on 16/6/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class RegisterActivity extends BaseActivity {
    private EditText accountEditTv,passwordEditTv,password2EditTv;
    private TextView registerBtn;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            JSONObject jsonObject = JSONObject.parseObject(data);
            if(jsonObject.getIntValue("type") == ChatMessage.REGISTER){
                Toast.makeText(RegisterActivity.this,jsonObject.getString("status_msg"),Toast.LENGTH_SHORT).show();
                if(jsonObject.getIntValue("status") == ChatMessage.STATUS_SUCCESS){
                    show(RegisterActivity.this,FriendListActivity.class);
                }
            }
        }
    };

    @Override
    protected void initView() {
        accountEditTv = (EditText) findViewById(R.id.register_account);
        passwordEditTv = (EditText) findViewById(R.id.register_password);
        password2EditTv = (EditText) findViewById(R.id.register_password2);
        registerBtn = (TextView) findViewById(R.id.register);

    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_register;
    }

    @Override
    public void initListener() {
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                if(TextUtils.isEmpty(accountEditTv.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"account cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordEditTv.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"password cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(accountEditTv.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"please input password again",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!passwordEditTv.getText().toString().equals(password2EditTv.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"password not match",Toast.LENGTH_SHORT).show();
                    return;
                }
                register();
                break;
        }
    }

    private void register() {
        String account = accountEditTv.getText().toString();
        String password = passwordEditTv.getText().toString();
        String password2 = password2EditTv.getText().toString();
        JSONObject chatMessage = new JSONObject();
        chatMessage.put("type",ChatMessage.REGISTER);
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
