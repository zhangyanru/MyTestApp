package umengchatdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.myapp.Model.ChatMessage;
import com.example.myapp.R;
import com.example.myapp.activity.BaseActivity;
import com.example.myapp.chatdemo.FriendListActivity;
import com.example.myapp.chatdemo.SocketService;
import com.example.myapp.util.PrefrenceManager;

/**
 * Created by yanru.zhang on 16/6/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class UMRegisterActivity extends BaseActivity {
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
                    Toast.makeText(UMRegisterActivity.this,"account cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordEditTv.getText().toString())){
                    Toast.makeText(UMRegisterActivity.this,"password cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(accountEditTv.getText().toString())){
                    Toast.makeText(UMRegisterActivity.this,"please input password again",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!passwordEditTv.getText().toString().equals(password2EditTv.getText().toString())){
                    Toast.makeText(UMRegisterActivity.this,"password not match",Toast.LENGTH_SHORT).show();
                    return;
                }
                register();
                break;
        }
    }

    private void register() {
        String account = accountEditTv.getText().toString();
        String password = passwordEditTv.getText().toString();

        //volley使用：http://www.apihome.cn/view-detail-70211.html
        RequestQueue mQueue = Volley.newRequestQueue(UMRegisterActivity.this);
    }

}
