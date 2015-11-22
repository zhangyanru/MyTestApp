package com.example.myapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapp.AccessTokenKeeper;
import com.example.myapp.R;
//import com.sina.weibo.sdk.api.TextObject;
//import com.sina.weibo.sdk.api.WeiboMultiMessage;
//import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
//import com.sina.weibo.sdk.auth.AuthInfo;
//import com.sina.weibo.sdk.auth.Oauth2AccessToken;
//import com.sina.weibo.sdk.auth.WeiboAuthListener;
//import com.sina.weibo.sdk.auth.sso.SsoHandler;
//import com.sina.weibo.sdk.exception.WeiboException;

public class WeiboSsoAuthActivity extends Activity {
//
//    private TextView textView;
//    private Button button;
//    private final String APPKYE = "2803014081";
//    private final String APPSECRET = "6c675fc63c0b4d5c1d5087b900be856d";
//    private final String REDIRECT_URL = "http://www.sofund.com";
//    public IWeiboShareAPI mWeiboShareAPI;
////    private SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//    public SsoHandler mSsoHandler;
//    public  Oauth2AccessToken mAccessToken;
//
//    /**
//     * Called when the activity is first created.
//     */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//
//        AuthInfo authInfo = new AuthInfo(WeiboSsoAuthActivity.this, APPKYE, REDIRECT_URL, APPSECRET);
//        mSsoHandler = new SsoHandler(WeiboSsoAuthActivity.this,authInfo);
//
//        textView = (TextView)findViewById(R.id.textview);
//        TextObject textObject = new TextObject();
//        textObject.text = textView.getText()+"";
//        WeiboMultiMessage message = new WeiboMultiMessage();
//        message.textObject = textObject;
//        button = (Button)findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSsoHandler.authorize(new AuthListener());
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // SSO 授权回调
//        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
//        if (mSsoHandler != null) {
//            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
//
//    }
//
//    class AuthListener implements WeiboAuthListener{
//        @Override
//        public void onCancel() {
//            Toast.makeText(WeiboSsoAuthActivity.this,
//                   "auth_canceled", Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onComplete(Bundle bundle) {
//            Toast.makeText(WeiboSsoAuthActivity.this,"onComplete",Toast.LENGTH_SHORT).show();
//            Log.i("mSsoHandler.authorize", "onComplete");
//            // 从 Bundle 中解析 Token
//            mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
//            textView.setText(mAccessToken.getToken());
//            if (mAccessToken.isSessionValid()){
//                AccessTokenKeeper.writeAccessToken(WeiboSsoAuthActivity.this, mAccessToken);
//                Toast.makeText(WeiboSsoAuthActivity.this,
//                        "weibosdk_demo_toast_auth_success", Toast.LENGTH_SHORT).show();
//                Log.i("mSsoHandler.authorize","weibosdk_demo_toast_auth_success");
//            }else {
//                String code = bundle.getString("code");
//                Toast.makeText(WeiboSsoAuthActivity.this, code, Toast.LENGTH_LONG).show();
//                Log.i("code",code+"---------------------------");
//            }
//        }
//
//        @Override
//        public void onWeiboException(WeiboException e) {
//            Toast.makeText(WeiboSsoAuthActivity.this,
//                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
}
