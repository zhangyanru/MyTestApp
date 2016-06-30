package com.example.myapp.activity;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.ui.chat.widget.YWSmilyMgr;
import com.alibaba.mobileim.utility.IMAutoLoginInfoStoreUtil;
import com.alibaba.wxlib.util.SysUtil;
import com.example.myapp.Model.ChatMessage;
import com.example.myapp.chatdemo.SocketService;
import com.umeng.openim.OpenIMAgent;

import umengchatdemo.customui.CustomHelper;

/**
 * Created by yanru.zhang on 16/6/15.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static Intent socketServiceIntent;
    private static Application mContext;
    public  String APP_KEY;
    public  YWIMKit mIMKit;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        /**
         * 以下，友盟相关代码
         */
        //必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
        SysUtil.setApplication(this);
        if(SysUtil.isTCMSServiceProcess(this)){
            return;
        }
        //第一个参数是Application Context
        //这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if(SysUtil.isMainProcess(this)){
            //TODO 注意：--------------------------------------
            //  以下步骤调用顺序有严格要求，请按照示例的步骤（todo step）
            // 的顺序调用！
            //TODO --------------------------------------------

            // ------[todo step1]-------------
            //［IM定制初始化］，如果不需要定制化，可以去掉此方法的调用
            //todo 注意：由于增加全局初始化，该配置需最先执行！

            CustomHelper.initCustom();

            // ------[todo step2]-------------
            //SDK初始化

            //后期将使用Override的方式进行集中配置，请参照YWSDKGlobalConfigSample
            YWAPI.enableSDKLogOutput(true);
            OpenIMAgent im = OpenIMAgent.getInstance(this);
            im.init();
            APP_KEY = im.getMessageBCAppkey();
            Log.d(TAG,"APP_KEY:" + APP_KEY);
        }
        /**
         * 以上，友盟相关代码
         */
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
