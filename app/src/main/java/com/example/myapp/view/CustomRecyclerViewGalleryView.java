package com.example.myapp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by zyr
 * DATE: 16-4-15
 * Time: 下午2:08
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomRecyclerViewGalleryView extends RecyclerView{

    private Context mContext;

    private OnFirstItemChangedListener firstItemChangedListener;

    private View currentFirstItemView;

    /*******************************************************************************
     *
     *
     * 构造
     *
     *
     ********************************************************************************/
    public CustomRecyclerViewGalleryView(Context context) {
        this(context, null);
    }

    public CustomRecyclerViewGalleryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecyclerViewGalleryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setOnScrollListener(mOnScrollListener);
    }

    /*******************************************************************************
     *
     *
     * OnScrollListener
     *
     *
     ********************************************************************************/

    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(currentFirstItemView==null || currentFirstItemView != getChildAt(0)){
                currentFirstItemView = getChildAt(0);
                if(firstItemChangedListener !=null){
                    firstItemChangedListener.onFirstItemChanged(currentFirstItemView,getChildPosition(currentFirstItemView));
                }
            }
        }
    };

    /*******************************************************************************
     *
     *
     * interface
     *
     *
     ********************************************************************************/

    public interface OnFirstItemChangedListener{
        public void onFirstItemChanged(View view,int position);
    }

    public void setFirstItemChangedListener(OnFirstItemChangedListener firstItemChangedListener) {
        this.firstItemChangedListener = firstItemChangedListener;
    }

}
