package com.example.myapp.activity;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.view.DanMuInfo;
import com.example.myapp.view.DanMuVIew2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yanru.zhang on 16/9/20.
 * Email:yanru.zhang@renren-inc.com
 */
public class DanMuActivity extends Activity {
    private DanMuVIew2 danMuVIew;
    private boolean isRun;
    private Thread thread;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dan_mu);
        danMuVIew = (DanMuVIew2) findViewById(R.id.dan_mu_view);
        danMuVIew.setZOrderOnTop(true);
        danMuVIew.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        danMuVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zyr","onClick");
                isRun = true;
                thread.run();
            }
        });
        isRun = false;
        thread = new Thread(){
            @Override
            public void run() {
                while (isRun){
                    Log.d("zyr","activity run" + Thread.currentThread());

                    List<DanMuInfo> danMuInfoList = new ArrayList<>();
                    int count = random.nextInt(30);
                    for(int i=0;i<count;i++){
                        danMuInfoList.add(new DanMuInfo(getRandomString(random.nextInt(10) * 2 + 5)));
                    }
                    danMuVIew.addInfoList(danMuInfoList);
                    try {
                        Thread.sleep(random.nextInt(5) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @Override
    protected void onResume() {
        Log.d("zyr","onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRun = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){
            Log.d("zyr","onKeyDown");
            isRun = false;
            danMuVIew.isRun = false;
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
