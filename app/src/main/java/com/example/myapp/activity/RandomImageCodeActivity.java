package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapp.R;
import com.example.myapp.util.Code;

/**
 * Created by zyr
 * DATE: 15-12-18
 * Time: 下午4:04
 * Email: yanru.zhang@renren-inc.com
 */
public class RandomImageCodeActivity extends Activity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_random_text_code);
        imageView = (ImageView)findViewById(R.id.random_iamge_code_iamge);
        imageView.setImageBitmap(Code.getInstance().createBitmap());
    }
}
