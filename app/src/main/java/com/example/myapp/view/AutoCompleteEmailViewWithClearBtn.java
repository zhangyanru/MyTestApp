package com.example.myapp.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-1-18
 * Time: 下午7:38
 * Email: yanru.zhang@renren-inc.com
 */
public class AutoCompleteEmailViewWithClearBtn extends AutoCompleteEmailView{
    private Context mContext;
    private Drawable imgDel;
    public AutoCompleteEmailViewWithClearBtn(Context context) {
        this(context, null);
    }

    public AutoCompleteEmailViewWithClearBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        imgDel = mContext.getResources().getDrawable(R.drawable.icon_album);
        setDrawable();
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setDrawable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //设置删除图片
    private void setDrawable() {
        if(length() >0){
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgDel, null);
        }else{
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgDel != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getX();
            if(isOnClearButton(eventX))
                setText("");
        }
        return super.onTouchEvent(event);
    }

    private final boolean isOnClearButton(float x) {
        Rect bounds = imgDel.getBounds();
        return x > getWidth() - getPaddingRight() - bounds.width() + bounds.left;
    }

}
