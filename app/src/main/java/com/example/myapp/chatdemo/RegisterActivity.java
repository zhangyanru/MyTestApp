package com.example.myapp.chatdemo;

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
        getSocketServiceBinder().getService().sendMsg(chatMessage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.listener = new SocketService.SocketListener() {
            @Override
            public void onGetChatMsgSuccess(final JSONObject chatMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(chatMessage!=null){
                            Log.e("zyr","RegisterActivity onGetChatMsg");
                            if(chatMessage.getIntValue("type") == ChatMessage.REGISTER){
                                Toast.makeText(RegisterActivity.this,chatMessage.getString("status_msg"),Toast.LENGTH_SHORT).show();
                                if(chatMessage.getIntValue("status") == ChatMessage.STATUS_SUCCESS){

                                }
                            }
                        }
                    }
                });
            }
        };
    }

}
