package com.example.myapp.activity;

import android.graphics.Path;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eftimoff.androipathview.PathView;
import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-3-3
 * Time: 下午5:40
 * Email: yanru.zhang@renren-inc.com
 */
public class QuXianAnimatorActivity extends BaseActivity {
    private PathView pathView ;
    private Path path;
    private int length = 400;
    private int height = 400;


    @Override
    protected void initView() {
        pathView = (PathView)findViewById(R.id.pathView);
        path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(length / 4f, 0.0f);
        path.lineTo(length, height / 2.0f);
        path.lineTo(length / 4f, height);
        path.lineTo(0.0f, height);
        path.lineTo(length * 3f / 4f, height / 2f);
        path.lineTo(0.0f, 0.0f);
        path.close();
        pathView.setPath(path);
//        pathView.getPathAnimator()
//                .delay(100)
//                .duration(500)
//                .listenerStart(null)
//                .listenerEnd(null)
//                .interpolator(new AccelerateDecelerateInterpolator())
//                .start();

        pathView.getSequentialPathAnimator()
                .delay(100)
                .duration(500)
                .listenerStart(null)
                .listenerEnd(null)
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();

    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_quxian_animator_layout;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
