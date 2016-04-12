package com.example.myapp.github.pinnedheaderlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class PinnedHeaderListView extends ListView implements OnScrollListener , AdapterView.OnItemClickListener{

    private OnScrollListener mOnScrollListener;

    private OnItemClickListener mOnItemClickListener;


    public static interface PinnedSectionedHeaderAdapter {
        public boolean isSectionHeader(int position); //是否是组的头部

        public int getSectionForPosition(int position); //根据位置判断对应的组

        public View getSectionHeaderView(int section, View convertView, ViewGroup parent); // 得到组的头部view

        public int getSectionHeaderViewType(int section); //

        public int getCount();

    }

    private PinnedSectionedHeaderAdapter mAdapter;
    private View mCurrentHeader;
    private int mCurrentHeaderViewType = 0;
    private float mHeaderOffset; //吸顶的header的offset
    private boolean mShouldPin = true; //是否把组的头部固定在顶部位置
    private int mCurrentSection = 0; //现在的组
    private int mWidthMode;
    private int mHeightMode;
    /*************  add by yanru************/
    private int headerCount;

    public PinnedHeaderListView(Context context) {
        super(context);
        super.setOnScrollListener(this);
        super.setOnItemClickListener(this);
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOnScrollListener(this);
        super.setOnItemClickListener(this);

    }

    public PinnedHeaderListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        super.setOnScrollListener(this);
        super.setOnItemClickListener(this);

    }

    /**
     * 如果设置为false,就没有组的头部固定的效果，等于普通的List View了
     * @param shouldPin
     */
    public void setPinHeaders(boolean shouldPin) {
        mShouldPin = shouldPin;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        mCurrentHeader = null;
        mAdapter = (PinnedSectionedHeaderAdapter) adapter;
        super.setAdapter(adapter);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

        headerCount = getHeaderViewsCount();
        if (mAdapter == null || mAdapter.getCount() == 0 || !mShouldPin || (firstVisibleItem < headerCount)) {
            mCurrentHeader = null;
            mHeaderOffset = 0.0f;
            for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
                View header = getChildAt(i);
                if (header != null) {
                    header.setVisibility(VISIBLE);
                }
            }
            return;
        }

        firstVisibleItem -= getHeaderViewsCount();//去掉header view的影响

        int section = mAdapter.getSectionForPosition(firstVisibleItem); //得到组号
        int viewType = mAdapter.getSectionHeaderViewType(section);
        mCurrentHeader = getSectionHeaderView(section, mCurrentHeaderViewType != viewType ? null : mCurrentHeader);
        //layout header,使它在最顶端
        ensurePinnedHeaderLayout(mCurrentHeader);
        mCurrentHeaderViewType = viewType;

        mHeaderOffset = 0.0f;

        for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
            if (mAdapter.isSectionHeader(i)) {
                Log.d("zyr","-----------------i :" + i);
                View header = getChildAt(i - firstVisibleItem);
                float headerTop = header.getTop();
                Log.d("zyr","-----------------headerTop :" + headerTop);
                float pinnedHeaderHeight = mCurrentHeader.getMeasuredHeight();
                Log.d("zyr","-----------------pinnedHeaderHeight :" + pinnedHeaderHeight);
                header.setVisibility(VISIBLE);
                if (pinnedHeaderHeight >= headerTop && headerTop > 0) { // 下一个组的头部快滑动到顶部，距离顶部的距离小于现在在顶部悬浮的head的高度了
                    mHeaderOffset = headerTop - header.getHeight(); //MheaderOffset是小于0的
                } else if (headerTop <= 0) { //下一个组的头部滑动到了顶部了
                    header.setVisibility(INVISIBLE);
                }
            }
        }

        invalidate();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    /**
     *
     * @param section 组号
     * @param oldView 旧的固定在头部的组view
     * @return
     */
    private View getSectionHeaderView(int section, View oldView) {
        boolean shouldLayout = section != mCurrentSection || oldView == null;//如果组号和现在的组号不一致，或者oldView为空，要layout

        View view = mAdapter.getSectionHeaderView(section, oldView, this); //根据组号，得到组对应的头部view
        if (shouldLayout) {
            // a new section, thus a new header. We should lay it out again
            ensurePinnedHeaderLayout(view);
            mCurrentSection = section;
        }
        return view;
    }

    /**
     * layout header,使它在最顶端
     * @param header 组对应的头部view
     */
    private void ensurePinnedHeaderLayout(View header) {
        if (header.isLayoutRequested()) {
            int widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), mWidthMode);
            
            int heightSpec;
            ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
            if (layoutParams != null && layoutParams.height > 0) {
                heightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
            } else {
                heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            }
            header.measure(widthSpec, heightSpec);
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mAdapter == null || !mShouldPin || mCurrentHeader == null )
            return;
        int saveCount = canvas.save();
        //沿y轴向下移动mHeaderOffset距离,把画布移动到(0,mHeaderOffset)
        //注意，此处mHeaderOffset是<=0的，所以等于说是把画布往上移动了一段距离
        canvas.translate(0, mHeaderOffset);
        canvas.clipRect(0, 0, getWidth(), mCurrentHeader.getMeasuredHeight()); // needed
        // for
        // <
        // HONEYCOMB
        mCurrentHeader.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mOnScrollListener = l;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
    }

    /********************** ItemClick*************************************/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position < headerCount){
            if(mOnItemClickListener  !=null){
                mOnItemClickListener.onHeaderClick(parent, view, position, id);
            }
            return;
        }
        if(mAdapter!= null && position >= headerCount + mAdapter.getCount()){
            if(mOnItemClickListener  !=null){
                mOnItemClickListener.onFooterClick(parent, view, position - headerCount - mAdapter.getCount(), id);
            }
            return;
        }
        position = position - headerCount;
        SectionedBaseAdapter adapter;
        if (parent.getAdapter().getClass().equals(HeaderViewListAdapter.class)) {
            HeaderViewListAdapter wrapperAdapter = (HeaderViewListAdapter) parent.getAdapter();
            adapter = (SectionedBaseAdapter) wrapperAdapter.getWrappedAdapter();
        } else {
            adapter = (SectionedBaseAdapter) parent.getAdapter();
        }
        int section = adapter.getSectionForPosition(position);
        int p = adapter.getPositionInSectionForPosition(position);

        if (p == -1) {
            if( mOnItemClickListener != null){
                mOnItemClickListener.onSectionClick(parent, view, section, id);
            }
        } else {
            if( mOnItemClickListener != null){
                mOnItemClickListener.onSectionItemClick(parent, view, section, p, id);
            }

        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {

        void onSectionItemClick(AdapterView<?> adapterView, View view, int section, int position, long id);

        void onSectionClick(AdapterView<?> adapterView, View view, int section, long id);

        void onHeaderClick(AdapterView<?> adapterView, View view, int position, long id);

        void onFooterClick(AdapterView<?> adapterView, View view, int position, long id);

    }
}
