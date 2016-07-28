package com.example.myapp.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.myapp.R;

/**
 * Created by yanru.zhang on 16/7/27.
 * Email:yanru.zhang@renren-inc.com
 *
 * svg动画的操作方法：
 * 1.在drawable下建立静态的vector图片（用vector,group,path等标签）
 * 2.在drawable下建立动态的vector图片（用animated-vector标签，需要animation）
 * 3.在animator下建立objectorAnimator动画，并且勇于步骤2中animation的引用
 *
 * svg动画的几种类型：
 * 1.位移，缩放，透明度变化等基本操作（注意，这个应该作用于group标签的name）
 * 2.改变路径(必须作用于path标签的name)（animator的标签,android:propertyName="pathData",android:valueType="pathType"）
 * 3.沿路径运动（必须作用于path标签的name）(animator的标签,android:propertyName="trimPathEnd或者trimPathStart")
 */
public class SVGAnimTestActivity extends Activity {
    private ImageView normalAnimImageView;
    private ImageView svgAnimImageView;
    private ImageView fiveStartIv;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_svg_anim);
        normalAnimImageView = (ImageView) findViewById(R.id.normal_xml_anim_drawable);
        ((Animatable)normalAnimImageView.getDrawable()).start();
        svgAnimImageView = (ImageView) findViewById(R.id.svg_xml_anim_drawable);
        svgAnimImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AnimatedVectorDrawable)svgAnimImageView.getDrawable()).start();
            }
        });

        fiveStartIv = (ImageView) findViewById(R.id.five_start_image_view);
        fiveStartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AnimatedVectorDrawable)fiveStartIv.getDrawable()).start();
            }
        });
    }
}
