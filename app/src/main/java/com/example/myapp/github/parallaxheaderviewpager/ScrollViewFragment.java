package com.example.myapp.github.parallaxheaderviewpager;

import android.util.Log;
import android.widget.ScrollView;

import com.example.myapp.github.pullzoomview.PullToZoomBase;
import com.example.myapp.github.pullzoomview.PullToZoomScrollViewEx;

/**
 * Created by desmond on 1/6/15.
 *
 * TODO add by yanru,如果页面是由scrollview实现的，继承该类即可
 *
 * TODO 需要实现的方法：a、父类中的setDelta()方法（如果不实现该方法，会导致tab切换时，scrollview滑动位置不保存，每次切换，都滑动到第一条）
 *               b、初始化父类中的mPosition（即在viewpager中的index值）。
 *               c、初始化mScrollView（记得要使用NotifyingScrollView）,并设置setScrollViewOnScrollListener。（ 不需要下拉刷新）
 *               如果需要下拉刷新，步骤c应该改为
 *               c、init mZoomParent(支持下拉刷新的view, 写在xml中，有对应的scrollview和listview,详情见pullTOZoomView)
 *                  mScrollView = mZoomParent.getPullRootView();
 *                  mZoomParent.setContentView();
 *                  setScrollViewOnScrollListener和setPullZoomListener
 *                  步骤a、b和不需要下拉刷新的步骤一致
 *下拉刷新mZoomParent简单说明，就是一个可以支持下拉手势处理的linearlayout, 包含下拉刷新zoomHeader和scrollView、listview组成响应布局
 */
public abstract class ScrollViewFragment extends ScrollTabHolderFragment {

    public static final String TAG = ScrollViewFragment.class.getSimpleName();

    protected static final int NO_SCROLL_X = 0;

    protected NotifyingScrollView mScrollView;
    //TODO add by yanru
    protected PullToZoomScrollViewEx mZoomParent;


    protected void setScrollViewOnScrollListener() {
        mScrollView.setOnScrollChangedListener(new NotifyingScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ScrollView view, int l, int t, int oldl, int oldt) {

                Log.d(TAG, "position " + mPosition);
                if (mScrollTabHolder != null) {
                    mScrollTabHolder.onScrollViewScroll(view, l, t, oldl, oldt, mPosition);
                }
            }
        });
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {
        if (mScrollView == null) return;

        mScrollView.scrollTo(NO_SCROLL_X, headerHeight - scrollHeight);

        if (mScrollTabHolder != null) {
            mScrollTabHolder.onScrollViewScroll(mScrollView, 0, 0, 0, 0, mPosition);
        }
    }

    //TODO add by yanru
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
