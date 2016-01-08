package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapp.R;
import com.example.myapp.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-1-8
 * Time: 下午4:26
 * Email: yanru.zhang@renren-inc.com
 */
public class YindaoActivity extends Activity {
//    private ViewPager viewPager;
//    private LinearLayout circleLayout;
//    private ViewPagerAdapter vpAdapter;
//    private List<View> views;
//    // 引导图片资源
//    private static final int[] pics = { R.drawable.guide1, R.drawable.guide2,
//            R.drawable.guide3, R.drawable.guide4 };
//
//    // 底部小店图片
//    private ImageView[] dots;
//
//    // 记录当前选中位置
//    private int currentIndex;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_yindao_layout);
//        viewPager = (ViewPager)findViewById(R.id.viewpager);
//        circleLayout = (LinearLayout)findViewById(R.id.circle_layout);
//
//        // 初始化底部小圆点
//        initDots();
//
//        views = new ArrayList<View>();
//        // 为引导图片提供布局参数
//        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        // 初始化引导点图片列表
//        for (int i = 0; i < pics.length; i++) {
//            ImageView iv = new ImageView(this);
//            iv.setLayoutParams(mParams);
//            iv.setImageResource(pics[i]);
//            views.add(iv);
//        }
//        // 初始化Adapter
//        vpAdapter = new ViewPagerAdapter(views);
//        viewPager.setAdapter(vpAdapter);
//        // 绑定回调
//        viewPager.setOnPageChangeListener(this);
//    }
//    private void initDots() {
//
//        dots = new ImageView[pics.length];
//
//        // 循环取得小点图片
//        for(int i=0;i<pics.length;i++){
//            dots[i] = new ImageView(YindaoActivity.this);
//            dots[i].setImageResource(R.drawable.dots);
//            dots[i].setEnabled(false);
//            circleLayout.addView(dots[i]);
//        }
//
//        currentIndex = 0;
//        dots[currentIndex].setEnabled(true);// 设置为选中状态
//    }

}
