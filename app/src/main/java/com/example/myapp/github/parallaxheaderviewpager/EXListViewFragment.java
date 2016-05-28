package com.example.myapp.github.parallaxheaderviewpager;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.example.myapp.github.pullzoomview.PullToZoomBase;
import com.example.myapp.github.pullzoomview.PullToZoomListViewEx;


/**
 * Created by desmond on 1/6/15.
 *
 * 继承此类实现tab滑动置顶的EXlistFragment
 *
 * Expandlistview，具体使用可参见ScrollViewFragment
 */
public abstract class EXListViewFragment extends ScrollTabHolderFragment {
    private static final String TAG = "ExListViewFragment";

    protected static final String ARG_POSITION = "position";

    protected PullToZoomListViewEx mZoomParent;
    protected ExpandableListView mListView;

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {
        if (mListView == null) return;

        Log.d(TAG, "adjustScroll and first visible position is " + mListView.getFirstVisiblePosition());

        if (scrollHeight == delta && mListView.getFirstVisiblePosition() >= 1) {
            return;
        }
        Log.d(TAG, "setSelection From Top, scrollHeight is " + scrollHeight);
        mListView.setSelectionFromTop(1, scrollHeight);

    }


    protected void setListViewOnScrollListener() {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

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
