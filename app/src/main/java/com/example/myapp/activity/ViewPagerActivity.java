package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapp.R;
import com.sina.weibo.sdk.api.share.Base;

import java.util.ArrayList;

/**
 * Created by admin on 15/9/16.
 */
public class ViewPagerActivity extends BaseActivity {
    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    private PagerTitleStrip pagerTitleStrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<View> arrayList = new ArrayList<View>();
        View view1 = View.inflate(this,R.layout.viewpager_item1,null);
        View view2 = View.inflate(this,R.layout.viewpager_item2,null);
        View view3 = View.inflate(this,R.layout.viewpager_item3,null);
        arrayList.add(view1);
        arrayList.add(view2);
        arrayList.add(view3);

        ArrayList<String> titleList = new ArrayList<String>();
        titleList.add("page1");
        titleList.add("page2");
        titleList.add("page3");

        MyAdapter myAdapter = new MyAdapter(arrayList,titleList);
        viewPager.setAdapter(myAdapter);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.viewpager;
    }

    @Override
    public void initViews() {
        super.initViews();
        viewPager = (ViewPager)containerView.findViewById(R.id.myviewpager);
        pagerTabStrip = (PagerTabStrip)containerView.findViewById(R.id.pagertab);
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    class MyAdapter extends PagerAdapter{
        private ArrayList<View> arrayList;
        private ArrayList<String> titleList;

        public MyAdapter(ArrayList<View> a,ArrayList<String> t){
            arrayList = a;
            titleList = t;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(arrayList.get(position));
            return arrayList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(arrayList.get(position));
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
