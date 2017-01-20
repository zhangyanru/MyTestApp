package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-3-16
 * Time: 下午4:12
 * 下拉刷新
 * 1.往下拉，而且已经拉到最顶部
 * 2.超过一定的距离
 * 3.
 */
public class CustomPullToRefreshWebView extends LinearLayout {
    private Context mContext;
    private View headView;
    private WebView webView;
    private TextView headTextView;
    private int mWidth, mHeight, headWidth, headHeight;
    private LayoutParams headViewLayoutParams;
    private int downX, downY, touchX, touchY, deltaX, deltaY, startX, startY, headMoveX, headMoveY;
    private boolean isScrollDown = false;
    private int currentStatus = STATUS_REFRESH_FINISHED;
    public static final int DURATION = 500;
    private Scroller mScroller;


    /**
     * 下拉状态
     */
    public static final int STATUS_PULL_TO_REFRESH = 0;
    /**
     * 释放立即刷新状态
     */
    public static final int STATUS_RELEASE_TO_REFRESH = 1;
    /**
     * 正在刷新状态
     */
    public static final int STATUS_REFRESHING = 2;
    /**
     * 刷新完成或未刷新状态
     */
    public static final int STATUS_REFRESH_FINISHED = 3;
    /**
     * 下拉超过这个高度释放就可以刷新
     */
    public static final int START_REFRESH_MIN_HEIGHT = 200;
    public static final int PULL_MAX_HEIGHT = 600;

    /***************************
     * OnRefreshListener
     *************************/
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    private OnRefreshListener onRefreshListener;

    public interface OnRefreshListener {
        void onRefresh();
    }

    public CustomPullToRefreshWebView(Context context) {
        this(context, null);
    }

    public CustomPullToRefreshWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPullToRefreshWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
        initScroller();
    }

    private void initScroller() {
        mScroller = new Scroller(getContext(), new DecelerateInterpolator());
    }

    @Override
    public void computeScroll() {
        Log.d("zyr", "computeScroll");
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            Log.d("zyr", "computeScroll" + mScroller.getCurrY());
            headViewLayoutParams.setMargins(0, mScroller.getCurrY(), 0, 0);
            headView.setLayoutParams(headViewLayoutParams);
        }
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_custom_pull_to_refresh_webview, this);
        headView = findViewById(R.id.refresh_view);
        headViewLayoutParams = (LayoutParams) headView.getLayoutParams();
        headTextView = (TextView) headView.findViewById(R.id.text_view);
        webView = (WebView) findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            mWidth = w;
            mHeight = h;
            headWidth = headView.getMeasuredWidth();
            headHeight = headView.getMeasuredHeight();
            if (headHeight != 0) {
                headViewLayoutParams.setMargins(0, -headHeight, 0, 0);
                headView.setLayoutParams(headViewLayoutParams);
            }
        }
    }

    /**************************
     * ListView
     ************************/

    public void load(String url) {
        webView.loadUrl(url);
    }
    /**************************     Touch    ************************/
    /**
     * 如果dispatchTouchEvent返回true ，则交给这个view的onTouchEvent处理，
     * 如果dispatchTouchEvent返回 false ，则交给这个 view 的 interceptTouchEvent 方法来决定是否要拦截这个事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                touchX = (int) ev.getX();
                touchY = (int) ev.getY();
                deltaX = touchX - downX;
                deltaY = touchY - downY;
                if (deltaY >= 0) {
                    isScrollDown = true;
                } else {
                    isScrollDown = false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                downX = 0;
                downY = 0;
                touchX = 0;
                touchY = 0;
                deltaX = 0;
                deltaY = 0;
                startX = 0;
                startY = 0;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isScrollDown && webView.getScrollY() == 0) {
                    currentStatus = STATUS_PULL_TO_REFRESH;
                    headTextView.setText("下拉可以刷新");
                    startX = (int) ev.getX();
                    startY = (int) ev.getY();
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                headMoveY = (int) event.getY() - startY - headHeight;
                if (headViewLayoutParams.topMargin < PULL_MAX_HEIGHT) {
                    headViewLayoutParams.setMargins(0, headMoveY, 0, 0);
                    headView.setLayoutParams(headViewLayoutParams);
                }
                if (headMoveY > START_REFRESH_MIN_HEIGHT) {
                    currentStatus = STATUS_RELEASE_TO_REFRESH;
                    headTextView.setText("释放立即刷新");
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
                    currentStatus = STATUS_REFRESHING;
                    headTextView.setText("正在刷新。。。");
                    onRefreshListener.onRefresh();
                } else {
                    int currentMarginTop = headViewLayoutParams.topMargin;
                    mScroller.startScroll(0, currentMarginTop, 0, -headHeight - currentMarginTop);
                    postInvalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void onRefreshComplete() {
        if (currentStatus == STATUS_REFRESHING) {
            currentStatus = STATUS_REFRESH_FINISHED;
            headTextView.setText("刷新成功！");
        }
        int currentMarginTop = headViewLayoutParams.topMargin;
        mScroller.startScroll(0, currentMarginTop, 0, -headHeight - currentMarginTop);
        postInvalidate();
    }
}
