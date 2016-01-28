package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.myapp.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by zyr
 * DATE: 16-1-26
 * Time: 下午8:13
 * Email: yanru.zhang@renren-inc.com
 */
public class PullToRefreshFrameLayout extends PullToRefreshBase<FrameLayout> {

    public PullToRefreshFrameLayout(Context context) {
        super(context);
    }

    public PullToRefreshFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected FrameLayout createRefreshableView(Context context, AttributeSet attrs) {
        FrameLayout frameLayout = new FrameLayout(context, attrs);
        frameLayout.setId(R.id.framelayout);
        frameLayout.setClickable(true);
        return frameLayout;
    }

    @Override
    protected boolean isReadyForPullStart() {
        FrameLayout refreshableView = getRefreshableView();
        Log.d("zyr", "isReadyForPullStart:" + (refreshableView.getScrollY() == 0));
        return refreshableView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        FrameLayout refreshableView = getRefreshableView();

        View child = refreshableView.getChildAt(0);
        if (null != child) {
            Log.d("zyr","isReadyForPullEnd:" +(refreshableView.getScrollY() >= (child.getHeight() - getHeight())) );

            return refreshableView.getScrollY() >= (child.getHeight() - getHeight());
        }
        Log.d("zyr","isReadyForPullEnd:false");
        return false;
    }
}
