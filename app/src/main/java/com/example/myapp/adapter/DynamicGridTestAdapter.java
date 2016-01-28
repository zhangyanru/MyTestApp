package com.example.myapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapp.R;

import org.askerov.dynamicgrid.AbstractDynamicGridAdapter;
import org.askerov.dynamicgrid.BaseDynamicGridAdapter;
import org.askerov.dynamicgrid.DynamicGridUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-1-26
 * Time: 上午11:40
 * Email: yanru.zhang@renren-inc.com
 */
public class DynamicGridTestAdapter extends BaseDynamicGridAdapter {

    protected DynamicGridTestAdapter(Context context, int columnCount) {
        super(context, columnCount);
    }

    public DynamicGridTestAdapter(Context context, List<?> items, int columnCount) {
        super(context, items, columnCount);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.xlistview_item,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(getItem(position).toString());
        return convertView;
    }

    class ViewHolder{
        TextView textView;
        public ViewHolder(View view){
            textView = (TextView)view.findViewById(R.id.xListView_item);
        }
    }

    @Override
    public void reorderItems(int originalPosition, int newPosition) {
        if (newPosition < getCount()) {
            DynamicGridUtils.swap(getItems(), originalPosition, newPosition);
            Log.d("zyr", "swap.......................................");
            notifyDataSetChanged();
        }
    }
}
