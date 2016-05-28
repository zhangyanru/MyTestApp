package com.example.myapp.github.parallaxheaderviewpager;

import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.ScrollView;

/**
 * Created by desmond on 10/4/15.
 */
public interface ScrollTabHolder {

    void adjustScroll(int scrollHeight, int headerHeight);

    void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                          int totalItemCount, int pagePosition);

    void onScrollViewScroll(ScrollView view, int x, int y,
                            int oldX, int oldY, int pagePosition);

    void onRecyclerViewScroll(RecyclerView view, int dx, int dy, int scrollY, int pagePosition);

    //TODO add by yanru,onHeaderZoom、onHeaderZoomEnd是添加下拉放大刷新加入的，activity中实现响应逻辑
    void onHeaderZoom(int scrollY, int pagePosition);

    void onHeaderZoomEnd(int pagePosition);
}
