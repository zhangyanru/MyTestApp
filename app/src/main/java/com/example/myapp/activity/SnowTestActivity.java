package com.example.myapp.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapp.R;
import com.example.myapp.view.DanMuViewGroup;
import com.example.myapp.view.SnowView;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-2-23
 * Time: 下午2:55
 * Email: yanru.zhang@renren-inc.com
 */
public class SnowTestActivity extends Activity implements View.OnClickListener{
    private Button snowBtn;
    private Button danmuBtn;
    private Button yuanbaoBtn;
    private ImageView imageView;
    private SnowView snowView;
    private DanMuViewGroup danMuViewGroup;
    private Bitmap bitmap;

    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activty_snow_test_layout);

        snowBtn = (Button)findViewById(R.id.snow_btn);
        danmuBtn = (Button)findViewById(R.id.danmu_btn);
        yuanbaoBtn = (Button)findViewById(R.id.yunbao_btn);
        imageView = (ImageView)findViewById(R.id.image);
        snowView = (SnowView)findViewById(R.id.snow_view);
        danMuViewGroup = (DanMuViewGroup)findViewById(R.id.danmu_viewgroup);
        danMuViewGroup.setVisibility(View.INVISIBLE);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.yuanbao);
        for(int i=0;i<10;i++){
            arrayList.add("zyr" + i);
        }
        danMuViewGroup.setTexts(arrayList);

        snowBtn.setOnClickListener(this);
        danmuBtn.setOnClickListener(this);
        yuanbaoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.snow_btn:
                imageView.setImageResource(R.drawable.tree2);
                snowView.setVisibility(View.VISIBLE);
                snowView.setBitmap(null);
                danMuViewGroup.setVisibility(View.INVISIBLE);
                break;
            case R.id.yunbao_btn:
                imageView.setImageResource(R.drawable.caishen);
                snowView.setVisibility(View.VISIBLE);
                snowView.setBitmap(bitmap);
                danMuViewGroup.setVisibility(View.INVISIBLE);
                break;
            case R.id.danmu_btn:
                snowView.setVisibility(View.INVISIBLE);
                danMuViewGroup.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onDetachedFromWindow() {
        Log.e("zyr","onDetachedFromWindow");
        super.onDetachedFromWindow();
        bitmap.recycle();
    }
}
