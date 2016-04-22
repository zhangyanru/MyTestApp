package com.example.myapp.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.util.Methods;
import com.example.myapp.view.CustomExpandableLayoutItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 15-11-26
 * Time: 下午2:52
 * Email: yanru.zhang@renren-inc.com
 */
public class ExpandableLayoutAdapter2 extends BaseAdapter{
    public List<String> arrayList = new ArrayList<String>();
    private Context context;

    public ExpandableLayoutAdapter2(Context context){
        this.context = context;
    }
    public ExpandableLayoutAdapter2(Context context, List<String> arrayList){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_layout_item_layout2,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.itemTv.setText("item " + position);
        viewHolder.menuTv.setText("menu" + position + "...");

        viewHolder.menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Methods.toast(context,"menu" + position);
            }
        });

        return convertView;
    }

    class ViewHolder{
        CustomExpandableLayoutItem expandableLayoutItem;
        View itemView;
        View menuView;
        TextView itemTv;
        TextView menuTv;
        public ViewHolder(View view){
            expandableLayoutItem = (CustomExpandableLayoutItem) view.findViewById(R.id.custom_expandable_layout);
            itemView = expandableLayoutItem.getItemView();
            menuView = expandableLayoutItem.getMenuView();
            itemTv = (TextView) itemView.findViewById(R.id.item_tv);
            menuTv = (TextView) menuView.findViewById(R.id.menu_tv);
        }
    }
}
