package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by zyr
 * DATE: 16-4-6
 * Time: 下午4:02
 * Email: yanru.zhang@renren-inc.com
 *
 * 通过listview addHeaderView来实现下拉刷新
 */
public class CustomPullToRefreshListView2 extends ListView implements AbsListView.OnScrollListener{
    private Context mContext;

    /************************   构造****************************************/

    public CustomPullToRefreshListView2(Context context) {
        this(context, null);
    }

    public CustomPullToRefreshListView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPullToRefreshListView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    /**************************     Scroll******************************/
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
