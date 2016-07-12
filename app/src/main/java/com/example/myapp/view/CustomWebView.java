package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by yanru.zhang on 16/7/12.
 * Email:yanru.zhang@renren-inc.com
 */
public class CustomWebView extends WebView{

    private OnScrollChangedCallBack onScrollChangedCallBack;

    public CustomWebView(Context context) {
        this(context,null);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollChangedCallBack != null){
            onScrollChangedCallBack.onWebViewScroll(l - oldl, t - oldt);
            if(getContentHeight() * getScale() == getHeight() + getScrollY()){
                onScrollChangedCallBack.onWebViewScrollToEnd();
            }
        }
    }

    public interface OnScrollChangedCallBack{
        void onWebViewScroll(int dx,int dy);
        void onWebViewScrollToEnd();
    }

    public void setOnScrollChangedCallBack(OnScrollChangedCallBack onScrollChangedCallBack) {
        this.onScrollChangedCallBack = onScrollChangedCallBack;
    }


}
