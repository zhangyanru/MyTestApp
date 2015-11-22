package com.example.myapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.myapp.R;

/**
 * Created by admin on 15/9/8.
 */
public class MainActivity extends Activity implements View.OnClickListener{
    private Button shareweibo;
    private Button drawpath;
    private Button animation;
    private Button xListView;
    private Button gridPasswardView;
    private Button viewpager;
    private Button viewpager_fragment;
    private Button topbar;
    private Button flowlayout;
    private Button startForResult;
    private Button colorChangedCircle;

    private final static int TO_A_ACTIVITY = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        initViews();
        initListener();
    }

    public void initViews(){
        shareweibo = (Button)findViewById(R.id.shareweibo);
        drawpath = (Button)findViewById(R.id.drawpath);
        animation = (Button)findViewById(R.id.animation);
        xListView = (Button)findViewById(R.id.xListView);
        gridPasswardView = (Button)findViewById(R.id.gridPasswardView);
        viewpager = (Button)findViewById(R.id.viewpager);
        viewpager_fragment = (Button)findViewById(R.id.viewpager_fragment);
        topbar = (Button)findViewById(R.id.topbar);
        flowlayout = (Button)findViewById(R.id.flowlayout);
        startForResult = (Button)findViewById(R.id.startAcyivityForResult);
        colorChangedCircle = (Button)findViewById(R.id.color_changed_circle);
    }

    public void initListener(){
        shareweibo.setOnClickListener(this);
        drawpath.setOnClickListener(this);
        animation.setOnClickListener(this);
        xListView.setOnClickListener(this);
        gridPasswardView.setOnClickListener(this);
        viewpager.setOnClickListener(this);
        viewpager_fragment.setOnClickListener(this);
        topbar.setOnClickListener(this);
        flowlayout.setOnClickListener(this);
        startForResult.setOnClickListener(this);
        colorChangedCircle.setOnClickListener(this);
    }

    public void show(Class<? extends Activity> activity){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, activity);
        this.startActivity(intent);
    }
    public void showActivityForResult(Class<? extends Activity> activity,int requestCode){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,activity);
        this.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case TO_A_ACTIVITY:
                    Toast.makeText(this,"to c activity result",Toast.LENGTH_LONG).show();
                    break;

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shareweibo:
                show(WeiboSsoAuthActivity.class);
                break;
            case R.id.drawpath:
                show(DrawPathActivity.class);
                break;
            case R.id.animation:
                show(AnimationActivity.class);
                break;
            case R.id.xListView:
                show(XListViewActivity.class);
                break;
            case R.id.gridPasswardView:
                show(GridPasswardActivity.class);
                break;
            case R.id.viewpager:
                show(ViewPagerActivity.class);
                break;
            case R.id.viewpager_fragment:
                show(ViewPagerFragmentActivity.class);
                break;
            case R.id.topbar:
                show(TopBarActivity.class);
                break;
            case R.id.flowlayout:
                show(FlowLayoutActivity.class);
                break;
            case R.id.startAcyivityForResult:
                showActivityForResult(AActivity.class,TO_A_ACTIVITY);
                break;
            case R.id.color_changed_circle:
                show(ColorChangedCircleActivity.class);
                break;
            default:
                break;
        }
    }
}
