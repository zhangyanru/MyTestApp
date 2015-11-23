package com.example.myapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapp.R;

/**
 * Created by admin on 15/10/14.
 */
public class AActivity extends BaseActivity{
    private Button btn;
    private TextView tv;
    public final static int TO_B_ACTIVITY = 0;
    @Override
    protected int onSetContainerViewId() {
        return R.layout.a_layout;
    }

    @Override
    public void initViews() {
        super.initViews();
        btn= (Button)containerView.findViewById(R.id.btn);
        tv = (TextView)containerView.findViewById(R.id.tv);
    }

    @Override
    public void initListener() {
        super.initListener();
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn:
                showActivityForResult(this,BActivity.class,TO_B_ACTIVITY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK){
            switch (requestCode){
                case TO_B_ACTIVITY :
                    String haha = data.getStringExtra("haha");
                    String ee = data.getStringExtra("ee");
                    tv.setText(haha+ee);
                break;
            }
        }
    }
}
