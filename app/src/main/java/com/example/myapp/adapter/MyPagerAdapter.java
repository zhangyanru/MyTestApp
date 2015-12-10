package com.example.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 15-11-27
 * Time: 上午11:31
 * Email: yanru.zhang@renren-inc.com
 */
public class MyPagerAdapter extends PagerAdapter {
    ArrayList<View> arrayList = new ArrayList<View>();
    ArrayList<Integer> urls = new ArrayList<Integer>();
    private Context context;
    public interface OnDeleteClickListener{
         void onDeleteClick(View v,int position);
    }
    private OnDeleteClickListener onDeleteClickListener;
    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener){
        this.onDeleteClickListener = onDeleteClickListener;
    }
    public MyPagerAdapter(Context context){
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
        container.removeView((View)object);
    }

    /**这个方法，return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container,int position) {
        Log.d("zyr","adapter position : "+ position);
        final int currentPosition = position;
        final View view = arrayList.get(position);
        container.addView(view);
        ImageView imageView = (ImageView)view.findViewById(R.id.view_pager_image);
        imageView.setImageResource(urls.get(position));
        TextView textView = (TextView)view.findViewById(R.id.view_pager_text);
        textView.setText((position)+"/"+arrayList.size());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClickListener.onDeleteClick(view,currentPosition);
            }
        });
        view.setTag(currentPosition);
        return view;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
