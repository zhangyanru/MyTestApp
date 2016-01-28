package com.example.myapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.myapp.R;
import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.internal.EmptyViewMethodAccessor;

import org.askerov.dynamicgrid.DynamicGridView;

/**
 * Created by zyr
 * DATE: 16-1-27
 * Time: 下午3:16
 * Email: yanru.zhang@renren-inc.com
 */
public class PullToRefreshDynamicGrid extends PullToRefreshAdapterViewBase<DynamicGridView> {
    public PullToRefreshDynamicGrid(Context context) {
        super(context);
    }

    public PullToRefreshDynamicGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshDynamicGrid(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshDynamicGrid(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected DynamicGridView createRefreshableView(Context context, AttributeSet attrs) {
        final DynamicGridView gv;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            gv = new InternalDynamicGridSDK9(context, attrs);
        } else {
            gv = new InternalDynamicGridView(context, attrs);
        }

        // Use Generated ID (from res/values/ids.xml)
        gv.setId(R.id.dygrid);
        return gv;
    }

    class InternalDynamicGridView extends DynamicGridView implements EmptyViewMethodAccessor {

        public InternalDynamicGridView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void setEmptyView(View emptyView) {
            PullToRefreshDynamicGrid.this.setEmptyView(emptyView);
        }

        @Override
        public void setEmptyViewInternal(View emptyView) {
            super.setEmptyView(emptyView);
        }
    }
    @TargetApi(9)
    final class InternalDynamicGridSDK9 extends InternalDynamicGridView {

        public InternalDynamicGridSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                    scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

            // Does all of the hard work...
            OverscrollHelper.overScrollBy(PullToRefreshDynamicGrid.this, deltaX, scrollX, deltaY, scrollY, isTouchEvent);

            return returnValue;
        }
    }
}
