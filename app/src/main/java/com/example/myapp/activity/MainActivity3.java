package com.example.myapp.activity;

import android.view.View;
import android.widget.Button;

import com.example.myapp.R;

/**
 * Created by yanru.zhang on 16/7/19.
 * Email:yanru.zhang@renren-inc.com
 */
public class MainActivity3 extends BaseActivity {
    private Button colorMatrixTest;
    private Button guaGuaLeTest;

    @Override
    protected void initView() {
        colorMatrixTest = (Button) findViewById(R.id.color_matrix_test);
        guaGuaLeTest = (Button) findViewById(R.id.gua_gua_le_test);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_main3;
    }

    @Override
    public void initListener() {
        colorMatrixTest.setOnClickListener(this);
        guaGuaLeTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.color_matrix_test:
//                    show(this,ColorMatrixTestActivity.class);
                show(this,ColorMatrixTestActivity2.class);

                break;
            case R.id.gua_gua_le_test:
                show(this,GuaGuaLeTestActivity.class);
                break;
        }

    }
}
