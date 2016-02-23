package com.example.myapp.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.myapp.R;
import com.example.myapp.view.LiziCopyView;

/**
 * Created by zyr
 * DATE: 16-2-18
 * Time: 下午7:20
 * Email: yanru.zhang@renren-inc.com
 */
public class LiziTestActivity extends Activity {
    private ImageView camera;
    private LiziCopyView liziCopyView;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lizi_test);
        camera = (ImageView)findViewById(R.id.camera);

        liziCopyView = (LiziCopyView)findViewById(R.id.copy);
        liziCopyView.setAnimListener(new LiziCopyView.AnimListener() {
            @Override
            public void onAnimStart() {
                camera.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimEnded() {
                Log.d("zyr","onAnimEnded");
                camera.setVisibility(View.VISIBLE);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!liziCopyView.isAnimRunning()) {
                    liziCopyView.setmView(camera);
                }
            }
        });
    }

}
