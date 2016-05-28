package com.example.myapp.github.parallaxheaderviewpager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

/**
 * Created by desmond on 1/6/15.
 * TODO 不要yanru.zhang，修改了切换tab,listview or scrollview之前滑动位置不保存的bug
 */
public abstract class ParallaxViewPagerBaseActivity extends AppCompatActivity implements ScrollTabHolder {

    public static final String TAG = ParallaxViewPagerBaseActivity.class.getSimpleName();

    protected static final String IMAGE_TRANSLATION_Y = "image_translation_y";
    protected static final String HEADER_TRANSLATION_Y = "header_translation_y";

    protected View mHeader;
    protected ViewPager mViewPager;
    protected ParallaxFragmentPagerAdapter mAdapter;

    protected int mMinHeaderHeight;
    protected int mHeaderHeight;//viewpager共享header的高度
    protected int mMinHeaderTranslation;//最小滑动距离，及Math.abs(scrollY)最大
    protected int mNumFragments;//viewpager的滑动fragment数目

    //TODO 继承该activity的需要实现的方法

    //初始化变量，包括mHeaderHeight、mMinHeaderTranslation、mNumFragments（mMinHeaderHeight暂无意义）
    protected abstract void initValues();

    //listview或scrollview滑动时，同步调整header的translationY
    protected abstract void scrollHeader(int scrollY);

    //下拉刷新,header放大的处理
    protected abstract void zoomHeader(int scrollY);

    //下拉刷新结束时的相关操作
    protected abstract void zoomEnd();


    //viewPager、adapter及ParallaxViewPagerChangeListener的设置
    protected abstract void setupAdapter();

    /**
     * 获取Y方向上listview的滚动距离
     */
    protected int getScrollYOfListView(AbsListView view) {
        View child = view.getChildAt(0);
        if (child == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = child.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * child.getHeight() + headerHeight;
    }

    protected ParallaxViewPagerChangeListener getViewPagerChangeListener() {
        return new ParallaxViewPagerChangeListener(mViewPager, mAdapter, mHeader);
    }


    /**
     * 页面切换时，调整header高度
     * @param scrollHeight
     * @param headerHeight
     */
    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {}

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            scrollHeader(getScrollYOfListView(view));
        }
    }

    @Override
    public void onScrollViewScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            scrollHeader(view.getScrollY());
        }
    }

    @Override
    public void onRecyclerViewScroll(RecyclerView view, int dx, int dy, int scrollY, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            scrollHeader(scrollY);
        }
    }
}
