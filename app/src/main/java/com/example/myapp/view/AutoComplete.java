package com.example.myapp.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-1-8
 * Time: 下午7:00
 * Email: yanru.zhang@renren-inc.com
 */
public class AutoComplete extends AutoCompleteTextView {
    private static final String[] emailSuffix = { "@qq.com", "@163.com", "@126.com", "@gmail.com" };
    private Context context;
    public AutoComplete(Context context){
        super(context);
        this.context = context;
        init(context);
    }
    public AutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context);
    }

    private void init(Context context){
        final MyAdatper adapter = new MyAdatper(context);
        setAdapter(adapter);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                adapter.mList.clear();
                if (input.length() > 0) {
                    for (int i = 0; i < emailSuffix.length; ++i) {
                        adapter.mList.add(input + emailSuffix[i]);
                    }
                }
                adapter.notifyDataSetChanged();
                showDropDown();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });
        // default=2 当输入一个字符的时候就开始检测
        setThreshold(1);
    }

    class ViewHolder{
        TextView textView;
        public ViewHolder(View view){
            textView = (TextView)view.findViewById(R.id.text);
        }
    }

    class MyAdatper extends BaseAdapter implements Filterable {
        List<String> mList;
        private Context mContext;
        private MyFilter mFilter;

        public MyAdatper(Context context) {
            mContext = context;
            mList = new ArrayList<String>();
        }

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList == null ? null : mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.auto_complete_textview_item,null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.textView.setText(mList.get(position));
            return convertView;
        }

        @Override
        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new MyFilter();
            }
            return mFilter;
        }

        private class MyFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (mList == null) {
                    mList = new ArrayList<String>();
                }
                results.values = mList;
                results.count = mList.size();
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
    }
}
