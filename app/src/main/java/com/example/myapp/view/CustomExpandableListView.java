package com.example.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by zyr
 * DATE: 16-4-22
 * Time: 上午11:09
 * Email: yanru.zhang@renren-inc.com
 */
public class CustomExpandableListView extends ListView implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener{
    private int currentOpenId = -1;

    private int scrollState;

    public CustomExpandableListView(Context context) {
        this(context, null);
    }

    public CustomExpandableListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnItemClickListener(this);
        setOnScrollListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //get click view
        if(getChildAt(position - getFirstVisiblePosition()) instanceof CustomExpandableLayoutItem){
            CustomExpandableLayoutItem expandableLayout = (CustomExpandableLayoutItem) getChildAt(position - getFirstVisiblePosition());
            if(expandableLayout.isOpen()){
                expandableLayout.hide();
                currentOpenId = -1;
            }else{
                //close all menu
                for(int i=getFirstVisiblePosition();i<=getLastVisiblePosition();i++){
                    CustomExpandableLayoutItem item = (CustomExpandableLayoutItem) getChildAt(i - getFirstVisiblePosition());
                    item.hide();
                }
                expandableLayout.show();
                currentOpenId = position;
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        if(scrollState == SCROLL_STATE_IDLE){
            Log.d("zyr","------------------idle------------------");
            for(int i=getFirstVisiblePosition();i<getLastVisiblePosition()+1;i++){
                CustomExpandableLayoutItem expandableLayout = (CustomExpandableLayoutItem) getChildAt(i - getFirstVisiblePosition());
                Log.d("zyr","i :" + i + "   isOpen :" + expandableLayout.isOpen());
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(scrollState == SCROLL_STATE_IDLE){
            return;
        }
        Log.d("zyr","--------firstVisibleItem :" + firstVisibleItem);
        if(currentOpenId >= firstVisibleItem && currentOpenId <= firstVisibleItem+visibleItemCount){
            CustomExpandableLayoutItem expandableLayout = (CustomExpandableLayoutItem) getChildAt(currentOpenId - getFirstVisiblePosition());
            if(expandableLayout!=null && !expandableLayout.isOpen()){
                expandableLayout.showRightNow();
                Log.d("zyr", "--------show :" + currentOpenId);
            }
        }else{
            for(int i=firstVisibleItem;i<firstVisibleItem+visibleItemCount;i++){
                CustomExpandableLayoutItem expandableLayout = (CustomExpandableLayoutItem) getChildAt(i-firstVisibleItem);
                if(expandableLayout!=null && expandableLayout.isOpen()){
                    expandableLayout.hideRightNow();
                    Log.d("zyr", "--------hide :" + i);
                }
            }
        }
        invalidate();
    }
}
