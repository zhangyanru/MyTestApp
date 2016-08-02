package com.example.myapp.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;

import java.io.UnsupportedEncodingException;


/**
 * Created by yanru.zhang on 16/7/25.
 * Email:yanru.zhang@renren-inc.com
 */
public class LimitLengthEditText extends RelativeLayout implements TextWatcher, View.OnClickListener {
    public EditText mEditText;
    public TextView mTextView;
    private ImageView mDeleteBtn;
    private RelativeLayout root;
    private final static int MAX_LENGTH = 160;
    private final static String SEPARATOR = "/";
    private String limitToastS = "Maximum number of characters allowed is 140";
    private int nowLength;
    private boolean allowCN = false; //是否允许输入中文
    public final static int OVER_LENGTH_SHOW_OVER_NUMBER = 1;//超过长度之后可以继续输入，但是会用红色的数字标出超过的个数
    public final static int OVER_LENGTH_LIMIT_INPUT = 2;//超过长度之后不能继续输入，弹toast提示超过了长度
    private int overLengthHintType = OVER_LENGTH_SHOW_OVER_NUMBER;
    private TextWatcher textWatcher;

    public LimitLengthEditText(Context context) {
        this(context, null);
    }

    public LimitLengthEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LimitLengthEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        root = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.limit_length_edit_text, this);
        mEditText = (EditText) root.findViewById(R.id.limit_length_edit_text);
        mTextView = (TextView) root.findViewById(R.id.limit_length_tv);
        mDeleteBtn = (ImageView) root.findViewById(R.id.limit_length_delete_btn);
        mDeleteBtn.setOnClickListener(this);
        mEditText.addTextChangedListener(this);
        if(!allowCN){
            mEditText.setFilters(new InputFilter[]{
                    new InputFilter(){

                        @Override
                        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                            //source,现在正输入的字符
                            //dest,之前已经在ediText中的字符
                            Log.d("zyr","source:" + source.toString() + " start:" + start + " end:" + end);
                            Log.d("zyr","dest:" + dest.toString() + " dstart:" + dstart + " dend:" + dend);
                            if(isCN(source.toString())){
                                return "";
                            }else{
                                return source;
                            }
                        }
                    }
            });
        }
        nowLength = mEditText.getText().length();
        mTextView.setText(nowLength + SEPARATOR + MAX_LENGTH);
    }

    public boolean isCN(String str){
        try {
            byte [] bytes = str.getBytes("UTF-8");
            if(bytes.length == str.length()){
                return false;
            }else{
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (textWatcher != null) {
            textWatcher.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        nowLength = mEditText.getText().length();
        if (nowLength > MAX_LENGTH) {
            switch (overLengthHintType) {
                case OVER_LENGTH_LIMIT_INPUT:
                    if (!TextUtils.isEmpty(limitToastS)) {
                        Toast.makeText(getContext(), limitToastS, Toast.LENGTH_SHORT).show();
                    }
                    mEditText.setText(mEditText.getText().subSequence(0, MAX_LENGTH));
                    mEditText.setSelection(mEditText.getText().length());
                    mTextView.setText(MAX_LENGTH + SEPARATOR + MAX_LENGTH);
                    nowLength = MAX_LENGTH;
                    break;
                case OVER_LENGTH_SHOW_OVER_NUMBER:
                    mEditText.setSelection(nowLength);
                    mTextView.setText((MAX_LENGTH - nowLength) + "");
                    mTextView.setTextColor(getContext().getResources().getColor(R.color.common_color_e94f4f));
                    break;
            }
        } else {
            mEditText.setSelection(nowLength);
            mTextView.setText(nowLength + SEPARATOR + MAX_LENGTH);
            mTextView.setTextColor(getContext().getResources().getColor(R.color.common_color_787878));
        }
        if (textWatcher != null) {
            textWatcher.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (textWatcher != null) {
            textWatcher.afterTextChanged(s);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.limit_length_delete_btn:
                mEditText.setText("");
                break;
        }
    }

    /**
     * 设置超过长度之后的toast的字符串
     * @param s
     */
    public void setLimitToastS(String s) {
        limitToastS = s;
    }

    /**
     * 设置长度超过限制长度之后的操作类型
     * 1.OVER_LENGTH_LIMIT_INPUT 长度超过之后就不管输入什么都显示140个字符，看上去就是不能再输入进去了，并且弹toast提示
     * 2.OVER_LENGTH_SHOW_OVER_NUMBER 超过限制长度之后仍然照常输入，但是显示超过的字符数，比如最多140个，现在长度为150，则显示-10，代表超过了10个字符
     * @param type
     */
    public void setOverLengthHintType(int type) {
        if (type == OVER_LENGTH_LIMIT_INPUT || type == OVER_LENGTH_SHOW_OVER_NUMBER) {
            overLengthHintType = type;
        }
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    /**
     * 判断现在是否超过了最大长度
     * @return
     */
    public boolean isOverLength() {
        if (nowLength > MAX_LENGTH) {
            return true;
        } else {
            return false;
        }
    }
}
