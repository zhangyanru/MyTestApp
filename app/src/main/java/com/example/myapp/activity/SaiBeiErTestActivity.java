package com.example.myapp.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.example.myapp.R;
import com.example.myapp.view.MySaiBeiErView2;
import com.example.myapp.view.MySaiBeiErView3;

/**
 * Created by yanru.zhang on 16/7/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class SaiBeiErTestActivity extends Activity {
    private MySaiBeiErView3 mySaiBeiErView3;
    Bitmap bitmap , bitmap2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_saibeier);
        mySaiBeiErView3 = (MySaiBeiErView3) findViewById(R.id.my_saibeier3);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.xiaoanzai);
        bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.anzaimove);
        mySaiBeiErView3.setBitmap(bitmap,bitmap2);
        mySaiBeiErView3.setPaintBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.lineimage));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bitmap.recycle();
        bitmap2.recycle();
    }
}
