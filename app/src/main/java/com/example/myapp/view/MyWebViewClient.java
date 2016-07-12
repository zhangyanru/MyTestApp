package com.example.myapp.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by yanru.zhang on 16/7/11.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyWebViewClient extends WebViewClient {

    private Context mContext;

    private OnPageUpdateListener onPageUpdateListener;

    public interface OnPageUpdateListener{
        void onProgressUpdate(int newProgress);
        void onGetTitle(String title);
    }

    public MyWebViewClient(Context context){
        mContext = context;
    }

    /**
     * 控制加载链接的位置,false仍然在webView中加载
     * @param view
     * @param url
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(url.contains("baidu.com")){ //访问百度的站点都在webView中访问
            return false;
        }

        //非百度站点打开浏览器访问
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mContext.startActivity(intent);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if(onPageUpdateListener!=null){
            onPageUpdateListener.onGetTitle(view.getTitle());
        }
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        Log.d("zyr","onLoadResource url:" + url + " progress:" + view.getProgress());
        if(onPageUpdateListener!=null){
            onPageUpdateListener.onProgressUpdate(view.getProgress());
            if(view.getTitle()!=null){
                onPageUpdateListener.onGetTitle(view.getTitle());
            }
        }
    }

    public void setOnPageUpdateListener(OnPageUpdateListener onPageUpdateListener) {
        this.onPageUpdateListener = onPageUpdateListener;
    }
}
