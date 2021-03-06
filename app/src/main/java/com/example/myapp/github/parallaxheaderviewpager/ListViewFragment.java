package com.example.myapp.github.parallaxheaderviewpager;

import android.widget.AbsListView;
import android.widget.ListView;

import com.example.myapp.github.pullzoomview.PullToZoomBase;
import com.example.myapp.github.pullzoomview.PullToZoomListViewEx;

/**
 * Created by desmond on 1/6/15.
 */
public abstract class ListViewFragment extends ScrollTabHolderFragment {
    protected static final String ARG_POSITION = "position";

    protected ListView mListView;
    protected int mPosition;

    protected PullToZoomListViewEx mZoomParent;


    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {
        if (mListView == null) return;

        if (scrollHeight == 0 && mListView.getFirstVisiblePosition() >= 1) {
            return;
        }

        mListView.setSelectionFromTop(1, scrollHeight);
    }

    protected void setListViewOnScrollListener() {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mScrollTabHolder != null) {
                    mScrollTabHolder.onListViewScroll(
                            view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
                }
            }
        });
    }

    protected void setPullZoomListener(){
        mZoomParent.setOnPullZoomListener(new PullToZoomBase.OnPullZoomListener() {
            @Override
            public void onPullZooming(int newScrollValue) {
                if (mScrollTabHolder != null) {
                    mScrollTabHolder.onHeaderZoom(newScrollValue, mPosition);
                }
            }

            @Override
            public void onPullZoomEnd() {
                if (mScrollTabHolder != null) {
                    mScrollTabHolder.onHeaderZoomEnd(mPosition);
                }
            }
        });
    }
}
