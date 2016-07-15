package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import java.lang.reflect.Method;

/**
 * Created by yanru.zhang on 16/7/12.
 * Email:yanru.zhang@renren-inc.com
 */
public class CustomWebView extends WebView{
    private WebSettings webSetting;

    private OnScrollChangedCallBack onScrollChangedCallBack;

    public CustomWebView(Context context) {
        this(context,null);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        webSetting = getSettings();
        webSetting.setJavaScriptEnabled(true); //在webView中使用JavaScript
        webSetting.setSupportZoom(true); // 允许缩放
//        webSetting.setUseWideViewPort(true); //任意比例缩放
        webSetting.setBuiltInZoomControls(true);//原网页基础上缩放
        if(android.os.Build.VERSION.SDK_INT>=11){
            webSetting.setDisplayZoomControls(false); //隐藏缩放控件
        }else{
//            try {
//                Class webview = Class.forName("android.webkit.WebView");
//                Method method = webview.getMethod("getZoomButtonsController");
//                ZoomButtonsController zoomController = (ZoomButtonsController) method.invoke(this, null);
//                zoomController.setVisible(false);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
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
