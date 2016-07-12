package com.example.myapp.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;

/**
 * Created by yanru.zhang on 16/7/11.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyWebView extends LinearLayout implements View.OnClickListener , CustomWebView.OnScrollChangedCallBack{
    private WebSettings webSetting;
    private MyWebViewClient webViewClient;
    private final String mimeType = "text/html";
    private final String encoding = "utf-8";

    private RelativeLayout titleLayout;
    private CustomWebView webView;
    private TextView titleTv;
    private ImageView backBtn;
    private ImageView closeBtn;
    private ProgressBar progressBar;
    private View rootView;

    public MyWebView(Context context) {
        this(context,null); 
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rootView = LayoutInflater.from(context).inflate(R.layout.web_view,this);
        titleLayout = (RelativeLayout) rootView.findViewById(R.id.title_layout);
        titleTv = (TextView) rootView.findViewById(R.id.web_view_title);
        backBtn = (ImageView) rootView.findViewById(R.id.web_view_back_btn);
        closeBtn = (ImageView) rootView.findViewById(R.id.web_view_close_btn);
        progressBar = (ProgressBar) rootView.findViewById(R.id.web_view_progress_bar);
        webView = (CustomWebView) rootView.findViewById(R.id.web_view);
        init();
    }

    private void init() {
        webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true); //在webView中使用JavaScript
        webSetting.setSupportZoom(true); // 允许缩放
        webSetting.setBuiltInZoomControls(true); //原网页基础上缩放
        webSetting.setUseWideViewPort(true); //任意比例缩放
        webViewClient = new MyWebViewClient(getContext());
        webViewClient.setOnPageUpdateListener(new MyWebViewClient.OnPageUpdateListener() {
            @Override
            public void onProgressUpdate(int newProgress) {
                if(newProgress == 0){
                    progressBar.setVisibility(VISIBLE);
                }
                if(newProgress == 100){
                    progressBar.setVisibility(GONE);
                }
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onGetTitle(String title) {
                if(title.equals(titleTv.getText())){
                    return;
                }
                titleTv.setText(title);
            }
        });
        webView.setWebViewClient(webViewClient);//单击webView中的链接直接在wenView中加载
        webView.setOnScrollChangedCallBack(this);
        backBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);

    }

    public void load(String url){
//        loadData(url,mimeType,encoding);
        webView.loadUrl(url);
    }

    public WebView getWebView() {
        return webView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.web_view_back_btn:
                back();
                break;
            case R.id.web_view_close_btn:
                close();
                break;
        }
    }

    private void close(){
        if(getContext() instanceof Activity){
            Activity activity = (Activity) getContext();
            activity.finish();
        }
    }

    public void back(){
        if(webView == null){
            return;
        }
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            close();
        }
    }

    public void cleadCache(){
        if(webView == null){
            return;
        }
        webView.clearCache(true);
    }

    public void clearHistory(){
        if(webView == null){
            return;
        }
        webView.clearHistory();
    }

    public void cleadCookies(){
        CookieSyncManager.createInstance(getContext());
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();
    }

    @Override
    public void onWebViewScroll(int dx, int dy) {
        if( dy > 0 && webView.getScrollY() > 2 * getResources().getDimension(R.dimen.web_view_title_height) ){ //下滑，互动超过顶部title高度2个
            titleLayout.setVisibility(INVISIBLE);
        }else{
            titleLayout.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onWebViewScrollToEnd() {
        Toast.makeText(getContext(),"客官，已经滑动到底部啦~", Toast.LENGTH_SHORT).show();
    }
}
