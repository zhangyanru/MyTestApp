package com.example.myapp.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class AutoCompleteEmailView extends AutoCompleteTextView {
    private static final String[] emailSuffix = { "@qq.com", "@163.com", "@126.com", "@gmail.com" };
    private Context context;
    public AutoCompleteEmailView(Context context){
        super(context);
        this.context = context;
        init(context);
    }
    public AutoCompleteEmailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context);
    }

    private void init(Context context){
        final MyAdatper adapter = new MyAdatper(context);
        setAdapter(adapter);
        // default=2 当输入一个字符的时候就开始检测
        setThreshold(1);
    }

    @Override
    protected void replaceText(CharSequence text) {
        String string = getText().toString();
        if(string.contains("@")){
            int index = string.indexOf("@");
            String s = string.substring(0,index);
            setText(s + text);
        }else{
            setText(string + text);
        }
        setSelection(getText().length());
    }

    @Override
    public void performCompletion() {
        super.performCompletion();
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        //该方法会在用户输入文本之后调用，将已输入的文本与adapter中的数据对比，若它匹配
        //adapter中数据的前半部分，那么adapter中的这条数据将会在下拉框中出现
        String t = text.toString();
        //因为用户输入邮箱时，都是以字母，数字开始，而我们的adapter中只会提供以类似于"@163.com"
        //的邮箱后缀，因此在调用super.performFiltering时，传入的一定是以"@"开头的字符串
        if(t.contains("@")) {
            int index = t.indexOf("@");
            String filterString = t.substring(index,t.length());
            Log.d("zyr","filterString:" + filterString);
            super.performFiltering(filterString, keyCode);
        } else {
           dismissDropDown();
        }
    }

    class ViewHolder{
        TextView textView;
        public ViewHolder(View view){
            textView = (TextView)view.findViewById(R.id.text);
        }
    }

    class MyAdatper extends BaseAdapter implements Filterable {
        List<String> mList = new ArrayList<String>();
        private Context mContext;
        private MyFilter mFilter;
        private String myConstraint = "";//匹配的文字

        public MyAdatper(Context context) {
            mContext = context;
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
            SpannableString spanText = new SpannableString(mList.get(position));
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, myConstraint.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            viewHolder.textView.setText(spanText);
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
                myConstraint = constraint.toString();
                if (mList == null) {
                    mList = new ArrayList<String>();
                }else{
                    mList.clear();
                }

                for(int i=0;i<emailSuffix.length;i++){
                    if(emailSuffix[i].contains(constraint)){
                        mList.add(emailSuffix[i]);
                    }
                }
                results.values = mList;
                results.count = mList.size();
                Log.d("zyr","results.values:" + mList.toString());
                Log.d("zyr","results.count:" + mList.size());
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        }
    }
}
