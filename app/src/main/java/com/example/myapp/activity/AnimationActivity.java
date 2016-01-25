package com.example.myapp.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapp.R;

import java.security.spec.EllipticCurve;

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
    private Button objAnim;
    private Button btn;
    private Button valueAnim;
    private Button animSet;
    private ImageView imageView;
    private EditText editText;
    private LinearLayout linearLayout;
    private Button transX;
    private Button transX2;
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
        editText = (EditText)findViewById(R.id.edit);
        linearLayout = (LinearLayout)findViewById(R.id.linear);
        all = (Button)findViewById(R.id.all);
        xml = (Button)findViewById(R.id.xml);
        objAnim = (Button)findViewById(R.id.object_anim);
        btn = (Button)findViewById(R.id.button_anim);
        valueAnim = (Button)findViewById(R.id.value_amin);
        animSet = (Button)findViewById(R.id.anim_set);
        transX = (Button)findViewById(R.id.tran_x);
        transX2 = (Button)findViewById(R.id.tran_x2);
    }

    public void initListener(){
        alpha.setOnClickListener(this);
        traslate.setOnClickListener(this);
        rotate.setOnClickListener(this);
        scale.setOnClickListener(this);
        all.setOnClickListener(this);
        xml.setOnClickListener(this);
        objAnim.setOnClickListener(this);
        btn.setOnClickListener(this);
        valueAnim.setOnClickListener(this);
        animSet.setOnClickListener(this);
        transX.setOnClickListener(this);
        transX2.setOnClickListener(this);
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
//                TranslateAnimation translateAnimation = new TranslateAnimation(
//                        Animation.RELATIVE_TO_SELF,0,
//                        Animation.RELATIVE_TO_SELF,1,
//                        Animation.RELATIVE_TO_SELF,0,
//                        Animation.RELATIVE_TO_SELF,1);
                TranslateAnimation translateAnimation = new TranslateAnimation(0,-100,0,-100);
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
            case R.id.object_anim:
                ViewWrapper viewWrapper = new ViewWrapper(btn);
                ObjectAnimator.ofInt(viewWrapper, "width", 1000).setDuration(5000).start();
                break;
            case R.id.value_amin:
                ValueAnimator valueAnimator = new ValueAnimator();
                valueAnimator.setTarget(imageView);
                valueAnimator.setDuration(500);
                valueAnimator.setObjectValues(new PointF(0,0));
                valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
                    @Override
                    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                        PointF point = new PointF();
                        point.x = 200 * fraction * 3;
                        point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                        return point;
                    }
                });
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        PointF point = (PointF) animation.getAnimatedValue();
                        imageView.setX(point.x);
                        imageView.setY(point.y);
                    }
                });
                valueAnimator.start();
                break;
            case R.id.anim_set:
                final AnimatorSet animatorSet = new AnimatorSet();

                ValueAnimator valueAnimator1 = new ValueAnimator();
                valueAnimator1.setTarget(imageView);
                valueAnimator1.setDuration(500);
                valueAnimator1.setObjectValues(new PointF(0, 0));
                valueAnimator1.setEvaluator(new TypeEvaluator<PointF>() {
                    @Override
                    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                        PointF point = new PointF();
                        point.x = 200 * fraction * 3;
                        return point;
                    }
                });
                valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        PointF point = (PointF) animation.getAnimatedValue();
                        imageView.setX(point.x);
                        imageView.setY(point.y);
                    }
                });

                ValueAnimator valueAnimator2 = new ValueAnimator();
                valueAnimator2.setTarget(imageView);
                valueAnimator2.setDuration(500);
                valueAnimator2.setObjectValues(new PointF(0, 0));
                valueAnimator2.setEvaluator(new TypeEvaluator<PointF>() {
                    @Override
                    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                        PointF point = new PointF();
                        point.x = 600 - 200 * fraction * 3;
                        return point;
                    }
                });
                valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        PointF point = (PointF) animation.getAnimatedValue();
                        imageView.setX(point.x);
                        imageView.setY(point.y);
                    }
                });
                animatorSet.play(valueAnimator1).before(valueAnimator2);
                animatorSet.setDuration(1000);
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                     animatorSet.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorSet.start();
                animSet.setEnabled(false);
                break;
            case R.id.tran_x:
                AnimatorSet animatorSet1 = new AnimatorSet();
                ObjectAnimator objectAnimator =ObjectAnimator.ofFloat(imageView, "x", imageView.getX(), imageView.getX() + 300).setDuration(5000);

                Log.d("zyr",imageView.getX()+"imageX");

                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(imageView, "y", imageView.getY(), imageView.getY() + 300).setDuration(5000);
                Log.d("zyr", imageView.getY() + "imageY");

                animatorSet1.setDuration(5000);
//                animatorSet1.playTogether(objectAnimator,objectAnimator2);
                animatorSet1.play(objectAnimator).with(objectAnimator2);
                animatorSet1.start();
                break;
            case R.id.tran_x2:
                AnimatorSet animatorSet2 = new AnimatorSet();
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(linearLayout,"translationX",300).setDuration(5000);
                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(linearLayout,"translationY",300).setDuration(5000);
                animatorSet2.playTogether(objectAnimator3,objectAnimator4);
                animatorSet2.start();
                break;
        }

    }



    class ViewWrapper{
        View view;
        public ViewWrapper(View view){
            this.view = view;
        }
        public void setWidth(int width){
            view.getLayoutParams().width = width;
            view.requestLayout();
        }
        public int getWidth(){
            return view.getLayoutParams().width;
        }
    }
}
