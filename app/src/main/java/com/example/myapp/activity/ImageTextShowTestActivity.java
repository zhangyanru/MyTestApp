package com.example.myapp.activity;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yanru.zhang on 16/6/22.
 * Email:yanru.zhang@renren-inc.com
 */
public class ImageTextShowTestActivity extends BaseActivity {

    private TextView normalTv;

    @Override
    protected void initView() {
        normalTv = (TextView) findViewById(R.id.normal_image_text_show);
        String string = "一只@和一只&,两只@和两只&";
        SpannableString spannableString = new SpannableString(string);

        Pattern pattern = Pattern.compile("@");
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()){
            spannableString.setSpan(new ImageSpan(this,R.drawable.gou,ImageSpan.ALIGN_BASELINE),matcher.start(),matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        pattern = Pattern.compile("&");
        matcher = pattern.matcher(string);
        while (matcher.find()){
            spannableString.setSpan(new ImageSpan(this,R.drawable.mao),matcher.start(),matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        normalTv.setText(spannableString);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_image_text;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
