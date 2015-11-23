package com.example.myapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.example.myapp.R;
import com.example.myapp.fragment.ViewpagerFragment1;
import com.example.myapp.fragment.ViewpagerFragment2;
import com.example.myapp.fragment.ViewpagerFragment3;

import java.util.ArrayList;

/**
 * Created by admin on 15/9/16.
 */
public class ViewPagerFragmentActivity extends FragmentActivity{
    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_fragment);
        initViews();
        initListener();

        ArrayList<Fragment> arrayList = new ArrayList<Fragment>();
        arrayList.add(new ViewpagerFragment1());
        arrayList.add(new ViewpagerFragment2());
        arrayList.add(new ViewpagerFragment3());
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<String> titleList = new ArrayList<String>();
        titleList.add("page1");
        titleList.add("page2");
        titleList.add("page3");
        MyAdapter  myAdapter = new MyAdapter(fragmentManager,arrayList,titleList);
        viewPager.setAdapter(myAdapter);
    }


    public void initViews() {
        viewPager = (ViewPager)findViewById(R.id.viewpager_fragment);
        pagerTabStrip = (PagerTabStrip)findViewById(R.id.pagertab);
        pagerTabStrip.setBackgroundColor(Color.GRAY);
        pagerTabStrip.setTabIndicatorColor(Color.WHITE);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTextColor(Color.BLUE);

    }

    public void initListener() {
    }

    class MyAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> arrayList;
        private ArrayList<String> titleList;
        public MyAdapter(FragmentManager fm,ArrayList<Fragment> fragmentArrayList,ArrayList<String> titleList) {
            super(fm);
            arrayList = fragmentArrayList;
            this.titleList = titleList;
        }

        @Override
        public Fragment getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }

    class MyAdapter2 extends FragmentStatePagerAdapter{
        private ArrayList<Fragment> arrayList;
        private ArrayList<String> titleList;
        public MyAdapter2(FragmentManager fm,ArrayList<Fragment> fragmentArrayList,ArrayList<String> titleList) {
            super(fm);
            arrayList = fragmentArrayList;
            this.titleList = titleList;
        }

        @Override
        public Fragment getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
