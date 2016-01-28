package com.example.myapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myapp.R;
import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by zyr
 * DATE: 16-1-26
 * Time: 下午7:32
 * Email: yanru.zhang@renren-inc.com
 *
 * 如果无效的话，可能是需要在xml中设置clickable为true
 */
public class PullToRefreshLinearLayout extends PullToRefreshBase<LinearLayout> {

    public PullToRefreshLinearLayout(Context context) {
        super(context);
    }

    public PullToRefreshLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected LinearLayout createRefreshableView(Context context, AttributeSet attrs) {
        LinearLayout linearLayout;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            linearLayout = new InternalLinearLayoutSDK9(context, attrs);
        } else {
            linearLayout = new LinearLayout(context, attrs);
        }
        linearLayout.setId(R.id.llll);
        linearLayout.setClickable(true);
        Log.d("zyr", "linearLayout.getScrollY():" + linearLayout.getScrollY());
        return linearLayout;
    }

    @Override
    protected boolean isReadyForPullStart() {
        LinearLayout refreshableView = getRefreshableView();
        int childCount = refreshableView.getChildCount();
        for (int i=0;i<childCount;i++){
            View child = refreshableView.getChildAt(i);
            Log.d("zyr","child" + i + "scrollY:" + child.getScrollY());
        }

        Log.d("zyr","isReadyForPullStart:" +(refreshableView.getScrollY() == 0) );
        Log.d("zyr","refreshableView.getScrollY():" + refreshableView.getScrollY() );
        return refreshableView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        LinearLayout refreshableView = getRefreshableView();

        View child = refreshableView.getChildAt(0);
        if (null != child) {
            Log.d("zyr","isReadyForPullEnd:" +(refreshableView.getScrollY() >= (child.getHeight() - getHeight())) );

            return refreshableView.getScrollY() >= (child.getHeight() - getHeight());
        }
        Log.d("zyr","isReadyForPullEnd:false");
        return false;
    }

    @TargetApi(9)
    final class InternalLinearLayoutSDK9 extends LinearLayout {

        public InternalLinearLayoutSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                    scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

            // Does all of the hard work...
            OverscrollHelper.overScrollBy(PullToRefreshLinearLayout.this, deltaX, scrollX, deltaY, scrollY,
                    getScrollRange(), isTouchEvent);

            return returnValue;
        }

        /**
         * Taken from the AOSP ScrollView source
         */
        private int getScrollRange() {
            int scrollRange = 0;
            if (getChildCount() > 0) {
                View child = getChildAt(0);
                scrollRange = Math.max(0, child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
            }
            return scrollRange;
        }
    }

}
