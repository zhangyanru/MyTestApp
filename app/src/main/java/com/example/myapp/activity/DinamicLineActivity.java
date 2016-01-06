package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.myapp.R;
import com.example.myapp.view.CustomDinamicLineView;

/**
 * Created by zyr
 * DATE: 15-12-17
 * Time: 下午7:42
 * Email: yanru.zhang@renren-inc.com
 */
public class DinamicLineActivity extends Activity{
    private CustomDinamicLineView customDinamicLineView;
    private int mX = 0;
    private Handler mHandler;
    private static final int MSG_DATA_CHANGE = 0x11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinamic_layout);
        customDinamicLineView = (CustomDinamicLineView)findViewById(R.id.view_dinamic_line);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case MSG_DATA_CHANGE:
                        customDinamicLineView.addPoint(msg.arg1, msg.arg2);
                        break;

                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<15;i++){
                    Message message = new Message();
                    message.what = MSG_DATA_CHANGE;
                    message.arg1 = mX;
                    message.arg2 = (int)(Math.random()*500);;
                    mHandler.sendMessage(message);
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    mX += 60;
                }
            }
        }).start();


    }
}
