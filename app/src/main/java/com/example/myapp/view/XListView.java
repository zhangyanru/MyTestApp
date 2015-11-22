package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.myapp.R;
import android.widget.AbsListView.OnScrollListener;
/**
 * Created by admin on 15/9/10.
 */
public class XListView extends ListView implements OnScrollListener {
    private XListViewHeaderView headerView;
    private RelativeLayout headViewContainter;
    private OnScrollListener mScrollListener;
    private float lastY = -1;
    private final static float OFFSET_RADIO = 1.8f;
    private int mHeaderViewHeight;

    private final int XlISTVIEW_NORMAL = 0;
    private final int XlISTVIEW_READY = 1;
    private final int XlISTVIEW_REFRESHING = 2;

    public XListView(Context context) {
        super(context);
        initView(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public void initView(Context context){
        headerView = new XListViewHeaderView(context);
        headerView.setVisibility(VISIBLE);
        headViewContainter = (RelativeLayout)headerView.findViewById(R.id.xlistview_header_content);
        addHeaderView(headerView);
        headerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeaderViewHeight = headViewContainter.getHeight();
                Log.i("mHeaderViewHeight", mHeaderViewHeight + "");
                getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (lastY == -1) {
            lastY = ev.getRawY();
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = ev.getY()-lastY;
                lastY = ev.getY();
                if (getFirstVisiblePosition() == 0
                        && (headerView.getVisiableHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeadViewHeight(deltaY / OFFSET_RADIO);
                }
                break;
            default:
                lastY = -1; // reset
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    Log.i("VisiableHeight()", headerView.getVisiableHeight() + "");
                    Log.i("mHeaderViewHeight",mHeaderViewHeight+"");
                    if (headerView.getVisiableHeight() > mHeaderViewHeight) {
                        headerView.setState(XlISTVIEW_REFRESHING);
                    }
                    resetHeaderHeight();
                }

        }
        return super.onTouchEvent(ev);
    }

    public void updateHeadViewHeight(float deltaY){
        headerView.setVisibleHeight(deltaY + headerView.getVisiableHeight());
        if(headerView.getVisiableHeight() > mHeaderViewHeight){
            headerView.setState(XlISTVIEW_READY);
        }


    }

    public void resetHeaderHeight(){
        int height = headerView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        // trigger computeScroll
        invalidate();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
