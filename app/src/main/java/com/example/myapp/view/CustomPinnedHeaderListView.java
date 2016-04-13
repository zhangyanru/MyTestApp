package com.example.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.myapp.adapter.CustomPinnedHeaderListViewBaseAdapter;
import com.example.myapp.github.pinnedheaderlistview.SectionedBaseAdapter;

/**
 * Created by zyr
 * DATE: 16-4-12
 * Time: 下午7:58
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomPinnedHeaderListView extends ListView implements AbsListView.OnScrollListener , AdapterView.OnItemClickListener{

    private final static String TAG = "CusPinnedHeaderListView";

    private Context mContext;

    private SectionHeader currentSectionHeader;

    private SectionHeader nextSectionHeader;

    private int currentSectionHeaderOffest ;

    private CustomPinnedHeaderListViewBaseAdapter mAdapter;

    private int mWidthMode;

    private int mHeightMode;

    private MyOnItemClickListener mOnItemClickListener;

    private int headerCount; //header view counts

    /*********************************************************************************************
     *
     * 构造函数
     *
     **********************************************************************************************/
    public CustomPinnedHeaderListView(Context context) {
        this(context, null);
    }

    public CustomPinnedHeaderListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPinnedHeaderListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOnScrollListener(this);
        setOnItemClickListener(this);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        if(adapter instanceof CustomPinnedHeaderListViewBaseAdapter){
            mAdapter = (CustomPinnedHeaderListViewBaseAdapter)adapter;
        }
        Log.d(TAG, "is my adapter null :" + (mAdapter == null));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
    }

    /*********************************************************************************************
     *
     * 实现OnScrollListener
     *
     **********************************************************************************************/

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //重置currentSectionHeaderOffest
        currentSectionHeaderOffest = 0;
        headerCount = getHeaderViewsCount();
        if(mAdapter==null || mAdapter.getCount()<=0 || firstVisibleItem < headerCount){
            currentSectionHeader = null;
            nextSectionHeader = null;
            return;
        }
        firstVisibleItem = firstVisibleItem - headerCount ;
        //遍历所有可见的部分找到nextSectionHeader;
        View nextIsSectionHeaderView = null;
        for(int i=firstVisibleItem;i<firstVisibleItem + visibleItemCount;i++){
            if(mAdapter.isSectionHeader(i)){
                nextIsSectionHeaderView  = getChildAt(i-firstVisibleItem);
                nextSectionHeader = new SectionHeader(mAdapter.getView(i,null,this),
                        i,mAdapter.getSectionId(i));
                break;
            }
        }
        //比较currentSectionHeader == nextSectionHeader ???
        if(currentSectionHeader!=null && nextSectionHeader!=null && currentSectionHeader.position == nextSectionHeader.position){
            return;
        }
        //得到currentSectionHeader
        currentSectionHeader = new SectionHeader(mAdapter.getSectionHeaderView(mAdapter.getSectionId(firstVisibleItem),null,this),
                mAdapter.getSectionPosition(mAdapter.getSectionId(firstVisibleItem)),mAdapter.getSectionId(firstVisibleItem));
        currentSectionHeader.view.layout(0,0,currentSectionHeader.width,currentSectionHeader.height);//!!!!!一定不能少
        //判断nextSectionHeader的位置，如果距离顶部高度小于currentSectionHeader高度，使currentSectionHeader向上推移动
        if(nextIsSectionHeaderView!=null ){

            if(nextIsSectionHeaderView.getTop() >= 0 && currentSectionHeader!=null && nextIsSectionHeaderView.getTop() < currentSectionHeader.height){
                currentSectionHeaderOffest = nextIsSectionHeaderView.getTop() - currentSectionHeader.height; // <0的哦
                Log.d(TAG,"move current section header view");
            }
            invalidate();
        }

    }

    /*********************************************************************************************
     *
     * 实现dispatchDraw,最精髓的部分哦
     *
     **********************************************************************************************/

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
//        Log.d(TAG, "is current section header null :" + (currentSectionHeader == null));
        if(currentSectionHeader !=null){
            canvas.save();
            canvas.translate(0, currentSectionHeaderOffest);
            canvas.clipRect(0, 0, currentSectionHeader.width, currentSectionHeader.height);
//            Log.d(TAG,"is current section header view null :" + (currentSectionHeader.view==null));
            currentSectionHeader.view.draw(canvas);
            canvas.restore();
        }
    }

    /*********************************************************************************************
     *
     * 实现OnItemClickListener
     *
     **********************************************************************************************/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        headerCount = getHeaderViewsCount();
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

        int section = mAdapter.getSectionId(position);
        int positionInSection = mAdapter.getPositionIdInSection(position);

        if (mAdapter.isSectionHeader(position)) {
            if( mOnItemClickListener != null){
                mOnItemClickListener.onSectionClick(parent, view, section, id);
            }
        } else {
            if( mOnItemClickListener != null){
                mOnItemClickListener.onSectionItemClick(parent, view, section, positionInSection, id);
            }

        }
    }

    public void setOnMyItemClickListener(MyOnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface MyOnItemClickListener {

        void onSectionItemClick(AdapterView<?> adapterView, View view, int section, int position, long id);

        void onSectionClick(AdapterView<?> adapterView, View view, int section, long id);

        void onHeaderClick(AdapterView<?> adapterView, View view, int position, long id);

        void onFooterClick(AdapterView<?> adapterView, View view, int position, long id);

    }

    /*********************************************************************************************
     *
     * inner class
     *
     **********************************************************************************************/

    class SectionHeader{
        public View view;
        public int sectionId;
        public int position;
        public int height;
        public int width;

        public SectionHeader(View view,int position,int sectionId){
            this.view = view;
            this.position = position;
            this.sectionId = sectionId;
            int widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), mWidthMode);
            int heightMeasureSpec  ;
            ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
            if (layoutParams != null && layoutParams.height > 0) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
            } else {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            }
            this.view.measure(widthMeasureSpec,heightMeasureSpec);
            this.height = this.view.getMeasuredHeight();
            this.width = this.view.getMeasuredWidth();
            this.view.setBackgroundColor(Color.RED);
//            Log.d(TAG,"width:" + this.width+ "  height:" + this.height);
        }
    }
}
