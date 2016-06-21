package com.example.myapp.chatdemo;

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
import java.util.List;

/**
 * Created by zyr
 * DATE: 15-11-26
 * Time: 下午2:52
 * Email: yanru.zhang@renren-inc.com
 */
public class FriendListAdapter extends BaseAdapter{
    public List<String> arrayList = new ArrayList<String>();
    private Context context;

    public FriendListAdapter(Context context){
        this.context = context;
    }
    public FriendListAdapter(Context context, List<String> arrayList){
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

    class ViewHolder{
        TextView textView;
        public ViewHolder(View view){
            textView = (TextView)view.findViewById(R.id.list_view_text);
        }
    }
}
