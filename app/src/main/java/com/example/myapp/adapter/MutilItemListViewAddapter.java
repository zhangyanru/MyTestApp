package com.example.myapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-1-7
 * Time: 下午5:08
 * Email: yanru.zhang@renren-inc.com
 */
public class MutilItemListViewAddapter extends BaseAdapter {
    private ArrayList<String> strings = new ArrayList<String>();
    private Context context;

    public MutilItemListViewAddapter(ArrayList<String> strings,Context context) {
        this.strings = strings;
        this.context = context;
    }

    @Override
    public int getCount() {
        Log.d("zyr", "string s size:"+strings.size());
        Log.d("zyr", "list s size:"+(Math.ceil(strings.size() / 2.0)) + ";;;;");
        return (int)Math.ceil(strings.size() / 2.0);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.mutil_item_listview,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final int leftId = position *2;
        final int rightId = position *2 +1;
        Log.d("zyr", "position:" + position + "   position *2 :"+ leftId);
        Log.d("zyr", "position:" + position + "   position *2 + 1 :"+ rightId);


        if(leftId < strings.size()){
            viewHolder.layout1.setVisibility(View.VISIBLE);
            viewHolder.textView1.setText(strings.get(leftId));
            viewHolder.layout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,strings.get(leftId),Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            viewHolder.layout1.setVisibility(View.INVISIBLE);
        }

        if(rightId < strings.size()){
            viewHolder.layout2.setVisibility(View.VISIBLE);
            viewHolder.textView2.setText(strings.get(rightId));
            viewHolder.layout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, strings.get(rightId), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            viewHolder.layout2.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    class ViewHolder{
        LinearLayout layout1;
        LinearLayout layout2;
        TextView textView1;
        TextView textView2;

        public ViewHolder(View view){
            layout1 = (LinearLayout)view.findViewById(R.id.layout1);
            layout2 = (LinearLayout)view.findViewById(R.id.layout2);

            textView1 = (TextView)layout1.findViewById(R.id.text_view);
            textView2 = (TextView)layout2.findViewById(R.id.text_view);
        }
    }



}
