package umengchatdemo.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.activity.BaseActivity;

import umengchatdemo.adapter.UMMainAdapter;

/**
 * Created by yanru.zhang on 16/6/28.
 * Email:yanru.zhang@renren-inc.com
 */
public class UMMainActivity extends BaseActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private UMMainAdapter adapter;
    @Override
    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        adapter = new UMMainAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_um_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
