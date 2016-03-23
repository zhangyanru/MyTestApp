package com.example.myapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.view.CustomSlideDeleteItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 15-11-26
 * Time: 下午2:52
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomSlideDeleteListViewAdapter extends BaseAdapter implements Filterable{
    public List<String> arrayList = new ArrayList<String>();
    private Context context;
    private MyFilter mFilter;

    public CustomSlideDeleteListViewAdapter(Context context){
        this.context = context;
    }
    public CustomSlideDeleteListViewAdapter(Context context, List<String> arrayList){
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
        ViewHolder viewHolder = null;
        if(convertView ==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_item,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.itemView.clearAllMenu();
        viewHolder.itemView.addMenu("Delete", Color.RED);
        viewHolder.itemView.addMenu("Open", Color.BLUE);
        viewHolder.itemView.scrollClose();
        viewHolder.textView.setText(arrayList.get(position));
        viewHolder.itemView.setOnMenuClickListener(new CustomSlideDeleteItemView.OnMenuClickListener() {
            @Override
            public void onMenuClick(int i, int counts) {
                switch (i){
                    case 0:
                        arrayList.remove(position);
                        notifyDataSetChanged();
                        break;
                    case 1:
                        break;
                }

            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new MyFilter();
        }
        return mFilter;
    }
    class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (arrayList == null) {
                arrayList = new ArrayList<String>();
            }
            results.values = arrayList;
            results.count = arrayList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    class ViewHolder{
        CustomSlideDeleteItemView itemView;
        TextView textView;
        public ViewHolder(View view){
            itemView = (CustomSlideDeleteItemView)view;
            textView = (TextView)view.findViewById(R.id.list_view_text);
        }
    }
}
