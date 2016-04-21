package com.example.myapp.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.util.Methods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 15-11-26
 * Time: 下午2:52
 * Email: yanru.zhang@renren-inc.com
 */
public class ExpandableLayoutAdapter extends BaseAdapter{
    public List<String> arrayList = new ArrayList<String>();
    private Context context;

    private int height;
    public ExpandableLayoutAdapter(Context context){
        this.context = context;
    }
    public ExpandableLayoutAdapter(Context context, List<String> arrayList){
        this.context = context;
        this.arrayList = new ArrayList<String>(arrayList);
    }

    public void setData(List<String> array){
        if(array ==null){
            return;
        }
        arrayList = new ArrayList<String>(array);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView ==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_layout_item_layout,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.showTv.setText(arrayList.get(position));
        viewHolder.clickShowTv.setText("I am showing by click item " + position + "!!!!") ;
        viewHolder.clickShowly.measure(0, 0);
        height = viewHolder.clickShowly.getMeasuredHeight();
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.clickShowly.getVisibility() == View.GONE){
                    show(viewHolder.clickShowly);
                }else{
                    dismiss(viewHolder.clickShowly);
                }
            }
        });

        viewHolder.clickShowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Methods.toast(context,"click green layout " + position);
            }
        });
        return convertView;
    }

    public void show(final View v){
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = ValueAnimator.ofInt(0,height);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                v.getLayoutParams().height = value;
                v.setLayoutParams(v.getLayoutParams());
            }
        });
        animator.start();
    }

    public void dismiss(final View v){

        ValueAnimator animator = ValueAnimator.ofInt(height,0);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                if (value == 0) {
                    v.setVisibility(View.GONE);
                }
                v.getLayoutParams().height = value;
                v.setLayoutParams(v.getLayoutParams());
            }
        });
        animator.start();
    }

    class ViewHolder{
        View rootView;
        TextView showTv;
        TextView clickShowTv;
        FrameLayout clickShowly;
        public ViewHolder(View view){
            rootView = view;
            showTv = (TextView)view.findViewById(R.id.show_layout);
            clickShowTv = (TextView) view.findViewById(R.id.click_show_tv);
            clickShowly = (FrameLayout) view.findViewById(R.id.click_show_layout);
        }
    }
}
