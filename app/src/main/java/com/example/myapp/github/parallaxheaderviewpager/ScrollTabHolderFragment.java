package com.example.myapp.github.parallaxheaderviewpager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.ScrollView;

/**
 * Created by desmond on 12/4/15.
 */
public abstract class ScrollTabHolderFragment extends Fragment implements ScrollTabHolder {

    protected static final String ARG_POSITION = "position";

    protected ScrollTabHolder mScrollTabHolder;
    protected int mPosition;
    //TODO add by yanru,delta 指的是header.height 和 header.translationY之间的差值，及滚动到最顶端时刻的差值
    protected int delta = 100;
    /**
     * 必须设置该值，否则tab切换会导致,每次返回都显示第一条
     * delta = **;
     *
     * */
    //TODO add by yanru
    public abstract void setDelta();

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mScrollTabHolder = (ScrollTabHolder) activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ScrollTabHolder");
        }
    }

    @Override
    public void onDetach() {
        mScrollTabHolder = null;
        super.onDetach();
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {}

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount, int pagePosition) {}

    @Override
    public void onScrollViewScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {}

    @Override
    public void onRecyclerViewScroll(RecyclerView view, int dx, int dy, int scrollY, int pagePosition) {}

    @Override
    public void onHeaderZoom(int scrollY, int pagePosition) {

    }

    @Override
    public void onHeaderZoomEnd(int pagePosition) {

    }
}
