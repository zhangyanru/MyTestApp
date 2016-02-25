package com.example.myapp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.example.myapp.R;

/**
 * Created by admin on 15/10/14.
 */
public class CActivity extends BaseActivity {
    private Button btn;

    @Override
    protected void initView() {
        btn = (Button)containerView.findViewById(R.id.btn);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.c_layout;
    }

    @Override
    public void initListener() {
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                Intent intent = new Intent();
                intent.putExtra("haha","Already finish C Activity!");
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
