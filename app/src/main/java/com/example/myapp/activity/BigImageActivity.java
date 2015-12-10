package com.example.myapp.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.adapter.MyPagerAdapter;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 15-11-27
 * Time: 上午11:04
 * Email: yanru.zhang@renren-inc.com
 */
public class BigImageActivity extends Activity {
    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private int count = 0;
    private int indexPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);
        viewPager = (ViewPager)findViewById(R.id.big_image_viewpager);

        final ArrayList<View> arrayList = new ArrayList<View>();
        final ArrayList<Integer> urls = new ArrayList<Integer>();
        urls.add(R.drawable.big0);
        urls.add(R.drawable.big1);
        urls.add(R.drawable.big2);
        urls.add(R.drawable.big3);
        urls.add(R.drawable.big4);
        urls.add(R.drawable.big5);
        urls.add(R.drawable.big6);
        urls.add(R.drawable.big7);

        for(int i=0;i<7;i++){
            View view = LayoutInflater.from(this).inflate(R.layout.view_pager_item,null);
            arrayList.add(view);
        }

        adapter = new MyPagerAdapter(this);
        viewPager.setAdapter(adapter);
        adapter.setData(arrayList,urls);
        count = arrayList.size();
        indexPosition = 0;
        adapter.setOnDeleteClickListener(new MyPagerAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(View v, int position) {
                arrayList.remove(position);
                urls.remove(position);
                viewPager.setCurrentItem(position-1);
                adapter.setData(arrayList,urls);
                if(arrayList.size()==0){
                    finish();
                }
                Log.d("zyr","view pager position : "+ position);
                Log.d("zyr","view pager count : "+ arrayList.size());
            }
        });
//        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
//            @Override
//            public void transformPage(View page, float position) {
//                /**
//                 * position这里是float类型，
//                 * 不是平时理解的int位置，而是当前滑动状态的一个表示，
//                 * 比如当滑动到正全屏时，position是0，而向左滑动，使得右边刚好有一部被进入屏幕时，position是1，
//                 * 如果前一页和下一页基本各在屏幕占一半时，前一页的position是-0.5，后一页的posiotn是0.5，
//                 * 所以根据position的值我们就可以自行设置需要的alpha，x/y信息。
//                 * 通过使用诸如setAlpha()、setTranslationX()、或setScaleY()方法来设置页面的属性，来创建自定义的滑动动画。
//                 */
//                if (position <= 0) {//正在消失的view
//                    page.setPivotX(page.getMeasuredWidth());
//                    page.setPivotY(page.getMeasuredHeight() * 0.5f);
//
//                    //只在Y轴做旋转操作
//                    page.setRotationY(90f * position);
//
//                    page.setAlpha(1+position);
//                } else if (position <= 1) {//正在进入的view
//                    //从左向右滑动为当前View
//                    page.setPivotX(0);
//                    page.setPivotY(page.getMeasuredHeight() * 0.5f);
//                    page.setRotationY(90f * position);
//
//                    page.setAlpha(1-position);
//                }
//            }
//        });



    }
}
