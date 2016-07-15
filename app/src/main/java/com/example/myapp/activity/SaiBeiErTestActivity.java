package com.example.myapp.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.example.myapp.R;
import com.example.myapp.view.MySaiBeiErView2;

/**
 * Created by yanru.zhang on 16/7/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class SaiBeiErTestActivity extends Activity {
    private MySaiBeiErView2 mySaiBeiErView2;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_saibeier);
        mySaiBeiErView2 = (MySaiBeiErView2) findViewById(R.id.my_saibeier2);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.zhu);
        Log.d("zyr","bitmap:" + bitmap);
        mySaiBeiErView2.setBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bitmap.recycle();
    }
}
