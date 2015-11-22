package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.*;
import android.widget.Button;
import android.widget.ImageView;
import com.example.myapp.R;

/**
 * Created by admin on 15/9/9.
 */
public class AnimationActivity extends Activity implements View.OnClickListener{
    private Button alpha;
    private Button traslate;
    private Button rotate;
    private Button scale;
    private Button all;
    private Button xml;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation);
        initviews();
        initListener();
    }

    public void initviews(){
        alpha = (Button)findViewById(R.id.alpha);
        traslate = (Button)findViewById(R.id.traslate);
        rotate = (Button)findViewById(R.id.rotate);
        scale = (Button)findViewById(R.id.scale);
        imageView = (ImageView)findViewById(R.id.image);
        all = (Button)findViewById(R.id.all);
        xml = (Button)findViewById(R.id.xml);

    }

    public void initListener(){
        alpha.setOnClickListener(this);
        traslate.setOnClickListener(this);
        rotate.setOnClickListener(this);
        scale.setOnClickListener(this);
        all.setOnClickListener(this);
        xml.setOnClickListener(this);
    }

    /**
     * Tween Animations的通用方法
     　　1、setDuration(long durationMills)
     　　设置动画持续时间（单位：毫秒）
     　　2、setFillAfter(Boolean fillAfter)
     　　如果fillAfter的值为true,则动画执行后，控件将停留在执行结束的状态
     　　3、setFillBefore(Boolean fillBefore)
     　　如果fillBefore的值为true，则动画执行后，控件将回到动画执行之前的状态
     　　4、setStartOffSet(long startOffSet)
     　　设置动画执行之前的等待时间
     　　5、setRepeatCount(int repeatCount)
     　　设置动画重复执行的次数
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.alpha:
                AnimationSet animationSet = new AnimationSet(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0,0.5f);
                alphaAnimation.setDuration(1000);
                animationSet.addAnimation(alphaAnimation);
                animationSet.setFillAfter(true);
                imageView.startAnimation(animationSet);
                break;
            case R.id.traslate:
                AnimationSet animationSet1 = new AnimationSet(true);
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF,0,
                        Animation.RELATIVE_TO_SELF,1,
                        Animation.RELATIVE_TO_SELF,0,
                        Animation.RELATIVE_TO_SELF,1);
                translateAnimation.setDuration(1000);
                animationSet1.addAnimation(translateAnimation);
                imageView.setAnimation(animationSet1);
                break;
            case R.id.rotate:
                AnimationSet animationSet2 = new AnimationSet(true);
                RotateAnimation rotateAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                rotateAnimation.setDuration(1000);
                rotateAnimation.setRepeatCount(-1);
                animationSet2.addAnimation(rotateAnimation);
                imageView.setAnimation(animationSet2);
                break;
            case R.id.scale:
                AnimationSet animationSet3 = new AnimationSet(true);
                ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                scaleAnimation.setDuration(1000);
                animationSet3.addAnimation(scaleAnimation);
                imageView.setAnimation(animationSet3);
                break;
            case R.id.all:
                AnimationSet animationSet4 = new AnimationSet(true);
                AlphaAnimation alphaAnimation2 = new AlphaAnimation(0,1);
                alphaAnimation2.setDuration(1000);
                animationSet4.addAnimation(alphaAnimation2);

                TranslateAnimation translateAnimation2 = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF,0,
                        Animation.RELATIVE_TO_SELF,0.5f,
                        Animation.RELATIVE_TO_SELF,0,
                        Animation.RELATIVE_TO_SELF,0.5f);
                translateAnimation2.setDuration(1000);
                animationSet4.addAnimation(translateAnimation2);

                RotateAnimation rotateAnimation2 = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                rotateAnimation2.setDuration(1000);
                animationSet4.addAnimation(rotateAnimation2);

                ScaleAnimation scaleAnimation2 = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                scaleAnimation2.setDuration(1000);
                animationSet4.addAnimation(scaleAnimation2);

                imageView.setAnimation(animationSet4);
                break;
            case R.id.xml:
                Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation);
                imageView.startAnimation(animation);
                break;
        }

    }
}
