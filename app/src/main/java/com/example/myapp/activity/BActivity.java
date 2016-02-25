package com.example.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapp.R;

/**
 * Created by admin on 15/10/14.
 */
public class BActivity extends BaseActivity {
    private Button btn;
    public final static int TO_C_ACTIVITY = 0;

    @Override
    protected void initView() {
        setResult(RESULT_OK);
        btn = (Button)containerView.findViewById(R.id.btn);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.b_layout;
    }


    @Override
    public void initListener() {
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                showActivityForResult(this,CActivity.class,TO_C_ACTIVITY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK){
            switch (requestCode){
                case TO_C_ACTIVITY:
                    String string = data.getStringExtra("haha");
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("haha",string);
                    bundle.putString("ee", "\nAlready finish B Activity!");
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
//                    finish();
                    break;
            }
        }
    }
}
