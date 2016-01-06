package com.example.myapp.activity;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.util.ColorEvaluator;
import com.example.myapp.view.CustomViewGroup;

/**
 * Created by zyr
 * DATE: 15-12-15
 * Time: 下午3:37
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomViewGroupActivity extends Activity implements View.OnClickListener{
    private CustomViewGroup viewGroup;
    private TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_view_group);
        viewGroup = (CustomViewGroup)findViewById(R.id.view_group);
        view = (TextView)findViewById(R.id.view);
        viewGroup.setOnClickListener(this);
        view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_group:
                Log.d("zyr", "view group click");
                view.setText("view group click");
                ObjectAnimator anim = ObjectAnimator.ofObject(viewGroup, "color", new ColorEvaluator(),
                        "#0000FF", "#FF0000");
                anim.setDuration(1000);
                anim.start();
//                ValueAnimator valueAnimator = ValueAnimator.ofObject(new ColorEvaluator(),
//                        "#FFFFFFFF", "#00FFFFFF" );
//                valueAnimator.setTarget(viewGroup);
//                valueAnimator.setDuration(2000);
//                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        viewGroup.setBackgroundColor((Integer) animation.getAnimatedValue());
//                    }
//                });
//                valueAnimator.start();
                break;
            case R.id.view:
                Log.d("zyr", "view  click");
                view.setText("view  click");
                ValueAnimator valueAnimator = ValueAnimator.ofObject(new ColorEvaluator(),
                      "#FFFFFFFF", "#00FFFFFF" );
                valueAnimator.setTarget(view);
                valueAnimator.setDuration(2000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        view.setBackgroundColor(Color.parseColor(animation.getAnimatedValue().toString()));
                    }
                });
                valueAnimator.start();
                break;
        }
    }
}
