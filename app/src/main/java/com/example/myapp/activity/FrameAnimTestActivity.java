package com.example.myapp.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapp.R;

/**
 * Created by yanru.zhang on 16/7/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class FrameAnimTestActivity extends Activity {
    private ImageView imageView;
    private AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_frame_anim);
        imageView = (ImageView) findViewById(R.id.image_view);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
    }
}
