package com.example.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 15-11-26
 * Time: 下午2:52
 * Email: yanru.zhang@renren-inc.com
 */
public class CommonAdapter2 extends BaseAdapter implements Filterable{
    public ArrayList<String> arrayList = new ArrayList<String>();
    private Context context;
    private MyFilter mFilter;

    public CommonAdapter2(Context context){
        this.context = context;
    }
    public CommonAdapter2(Context context, ArrayList<String> arrayList){
        this.context = context;
        this.arrayList = new ArrayList<String>(arrayList);
    }

    public void setData(ArrayList<String> array){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView ==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_item2,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.textView.setText(arrayList.get(position));
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
        TextView textView;
        public ViewHolder(View view){
            textView = (TextView)view.findViewById(R.id.list_view_text);
        }
    }
}
