package youmengchatdemo;

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
import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.example.myapp.Model.ChatMessage;
import com.example.myapp.R;
import com.example.myapp.activity.BaseActivity;
import com.example.myapp.activity.MyApplication;
import com.example.myapp.chatdemo.FriendListActivity;
import com.example.myapp.chatdemo.MessageType;
import com.example.myapp.chatdemo.RegisterActivity;
import com.example.myapp.chatdemo.SocketService;
import com.example.myapp.util.Methods;
import com.example.myapp.util.PrefrenceManager;


/**
 * Created by yanru.zhang on 16/6/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class YMLoginActivity extends BaseActivity{
    private static final String TAG = "YMLoginActivity";
    private EditText accountEditTv,passwordEditTv;
    private TextView loginBtn,registerBtn;

    @Override
    protected void initView() {
        accountEditTv = (EditText) findViewById(R.id.account);
        accountEditTv.setText("zyr");
        passwordEditTv = (EditText) findViewById(R.id.password);
        passwordEditTv.setText("123456");
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
                    Toast.makeText(YMLoginActivity.this,"account cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordEditTv.getText().toString())){
                    Toast.makeText(YMLoginActivity.this,"password cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                login();
                break;
            case R.id.register_btn:
                show(YMLoginActivity.this,RegisterActivity.class);
                break;
        }
    }

    private void login() {
        //开始登录
        String userid = "zyr";
        String password = "123456";

        YWIMKit mIMKit = YWAPI.getIMKitInstance(userid,myApplication.APP_KEY );
        Log.d(TAG," myApplication.APP_KEY :" +  myApplication.APP_KEY);
        Log.d(TAG,"mIMKit:" + (mIMKit==null?null:mIMKit));
        if(mIMKit!=null){
            IYWLoginService loginService = mIMKit.getLoginService();
            YWLoginParam loginParam = YWLoginParam.createLoginParam(userid, password);
            loginService.login(loginParam, new IWxCallback() {

                @Override
                public void onSuccess(Object... arg0) {
                    Methods.toast(YMLoginActivity.this,"login success");
                }

                @Override
                public void onProgress(int arg0) {
                    // TODO Auto-generated method stub
                    Methods.toast(YMLoginActivity.this,"login ing....");

                }

                @Override
                public void onError(int errCode, String description) {
                    //如果登录失败，errCode为错误码,description是错误的具体描述信息
                    Log.d(TAG,"loginService onError description:" + description);
                    Methods.toast(YMLoginActivity.this,"login failed " + description);

                }
            });
        }

    }
}
