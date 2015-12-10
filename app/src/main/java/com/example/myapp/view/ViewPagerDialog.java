package com.example.myapp.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 15-11-27
 * Time: 下午2:01
 * Email: yanru.zhang@renren-inc.com
 */
public class ViewPagerDialog extends Dialog {
    private Context context;
    private View rootView;
    private ViewPager viewPager;
    private DialogPagerAdapter adapter;
    public ViewPagerDialog(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public ViewPagerDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        initView();
    }

    protected ViewPagerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        initView();
    }

    public void initView(){
        rootView = LayoutInflater.from(context).inflate(R.layout.view_pager_layout,null);
        viewPager =(ViewPager) rootView.findViewById(R.id.big_image_viewpager);
        adapter = new DialogPagerAdapter(context);
        viewPager.setAdapter(adapter);

        ArrayList<View> arrayList = new ArrayList<View>();
        ArrayList<Integer> urls = new ArrayList<Integer>();
        for(int i=0;i<9;i++){
            urls.add(R.drawable.icon_album);
        }

        for(int i=0;i<9;i++){
            View view = LayoutInflater.from(context).inflate(R.layout.view_pager_item,null);
            arrayList.add(view);
        }

        adapter.setData(arrayList,urls);
    }

    class DialogPagerAdapter extends PagerAdapter {
        ArrayList<View> arrayList = new ArrayList<View>();
        ArrayList<Integer> urls = new ArrayList<Integer>();
        private Context context;

        public DialogPagerAdapter(Context context){
            this.context = context;
        }

        public void setData(ArrayList<View> views,ArrayList<Integer> urls){
            if(views == null||urls==null){
                return;
            }
            arrayList = new ArrayList<View>(views);
            this.urls = new ArrayList<Integer>(urls);
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 这个方法，是从ViewGroup中移出当前View
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(arrayList.get(position));
        }

        /**这个方法，return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = arrayList.get(position);
            container.addView(view);
            ImageView imageView = (ImageView)view.findViewById(R.id.view_pager_image);
            imageView.setImageResource(urls.get(position));
            TextView textView = (TextView)view.findViewById(R.id.view_pager_text);
            textView.setText(position+"--");
            return view;
        }
    }

}
