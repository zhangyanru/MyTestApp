package com.example.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 15-11-27
 * Time: 上午11:06
 * Email: yanru.zhang@renren-inc.com
 */
public class CommonImageAdapter extends BaseAdapter {
    ArrayList<Integer> arrayList = new ArrayList<Integer>();
    private Context context;

    public CommonImageAdapter(Context context){
        this.context = context;
    }

    public void setData(ArrayList<Integer> array){
        if(array ==null){
            return;
        }
        arrayList = new ArrayList<Integer>(array);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView ==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_item_image,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.imageView.setImageResource(arrayList.get(position));
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        public ViewHolder(View view){
            imageView = (ImageView)view.findViewById(R.id.iamge_view_item_image);
        }
    }
}
