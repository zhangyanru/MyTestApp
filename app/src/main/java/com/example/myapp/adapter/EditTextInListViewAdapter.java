package com.example.myapp.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.ArrayList;

/**
 * Created by zhangyanru on 2016/12/19.
 * 功能描述：
 * 参考了：http://stackoverflow.com/questions/2679948/focusable-edittext-inside-listview
 */

public class EditTextInListViewAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_TEXT = 0;
    private static final int VIEW_TYPE_EDIT = 1;
    private ArrayList<String> strings = new ArrayList<String>();
    private Context mContext;
    private ListView mListView;

    public EditTextInListViewAdapter(Context context, ListView listView) {
        mContext = context;
        mListView = listView;
    }

    public void setData(ArrayList<String> strings) {
        this.strings.clear();
        this.strings.addAll(strings);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder editViewHolder = null, textViewHolder = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            if (type == VIEW_TYPE_EDIT) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_edit_text_in_listview, null);
                editViewHolder = new ViewHolder(convertView);
                convertView.setTag(editViewHolder);
            } else if (type == VIEW_TYPE_TEXT) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_text_in_listview, null);
                textViewHolder = new ViewHolder(convertView);
                convertView.setTag(textViewHolder);
            }
        } else {
            if (type == VIEW_TYPE_EDIT) {
                editViewHolder = (ViewHolder) convertView.getTag();
            } else if (type == VIEW_TYPE_TEXT) {
                textViewHolder = (ViewHolder) convertView.getTag();
            }
        }
        if (type == VIEW_TYPE_EDIT) {
            final EditText editText = (EditText) editViewHolder.root.findViewById(R.id.edit_text);
            if (editText.getTag() instanceof TextWatcher) {
                editText.removeTextChangedListener((TextWatcher)editText.getTag());
            }
            editText.setText(strings.get(position));
            editText.setFocusable(true);
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                    View focusView = editText.findFocus();
                    if (focusView != null) {
                        focusView.clearFocus();
                    }
                    editText.requestFocus();
                }
            });
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    strings.set(position,s.toString());
                }
            };
            editText.addTextChangedListener(textWatcher);
            editText.setTag(textWatcher);
            mListView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        } else if (type == VIEW_TYPE_TEXT) {
            TextView textView = (TextView) textViewHolder.root.findViewById(R.id.text_view);
            textView.setText(strings.get(position));
            textViewHolder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mListView.isFocused()) {
                        mListView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
                        mListView.requestFocus();
                    }
                }
            });
            mListView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        }
        return convertView;
    }

    private static class ViewHolder {
        public View root;

        public ViewHolder(View view) {
            root = view;
        }
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_EDIT;
    }
}
