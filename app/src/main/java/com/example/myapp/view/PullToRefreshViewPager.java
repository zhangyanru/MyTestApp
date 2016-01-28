package com.example.myapp.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import com.example.myapp.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by zyr
 * DATE: 16-1-26
 * Time: 下午7:38
 * Email: yanru.zhang@renren-inc.com
 */
public class PullToRefreshViewPager extends PullToRefreshBase<ViewPager> {

    public PullToRefreshViewPager(Context context) {
        super(context);
    }

    public PullToRefreshViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.HORIZONTAL;
    }

    @Override
    protected ViewPager createRefreshableView(Context context, AttributeSet attrs) {
        ViewPager viewPager = new ViewPager(context, attrs);
        viewPager.setId(R.id.viewpager);
        return viewPager;
    }

    @Override
    protected boolean isReadyForPullStart() {
        ViewPager refreshableView = getRefreshableView();

        PagerAdapter adapter = refreshableView.getAdapter();
        if (null != adapter) {
            Log.d("zyr", "isReadyForPullStart:" + (refreshableView.getCurrentItem() == 0));
            return refreshableView.getCurrentItem() == 0;
        }
        Log.d("zyr", "isReadyForPullStart:false");
        return false;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        ViewPager refreshableView = getRefreshableView();

        PagerAdapter adapter = refreshableView.getAdapter();
        if (null != adapter) {
            Log.d("zyr", "isReadyForPullEnd:" + (refreshableView.getCurrentItem() == adapter.getCount() - 1) );

            return refreshableView.getCurrentItem() == adapter.getCount() - 1;
        }
        Log.d("zyr", "isReadyForPullEnd:false");
        return false;
    }
}
