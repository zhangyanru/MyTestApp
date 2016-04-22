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

        viewHolder.itemTv.setText(arrayList.get(position));
        viewHolder.menuTv.setText("menu " + position + "!!!!") ;
        viewHolder.menuLy.measure(0, 0);
        final int height = viewHolder.menuLy.getMeasuredHeight();
        viewHolder.itemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.menuLy.getVisibility() == View.GONE) {
                    show(viewHolder.menuLy, height);
                } else {
                    dismiss(viewHolder.menuLy, height);
                }
            }
        });

        return convertView;
    }

    public void show(final View v ,int height){
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

    public void dismiss(final View v ,int height){

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
        TextView itemTv;
        TextView menuTv;
        FrameLayout menuLy;
        public ViewHolder(View view){
            rootView = view;
            itemTv = (TextView)view.findViewById(R.id.item_layout);
            menuTv = (TextView) view.findViewById(R.id.menu_tv);
            menuLy = (FrameLayout) view.findViewById(R.id.menu_layout);
        }
    }
}
