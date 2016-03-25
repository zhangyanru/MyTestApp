package com.example.myapp.activity;

import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.util.Methods;

/**
 * Created by zyr
 * DATE: 16-3-23
 * Time: 下午7:29
 * Email: yanru.zhang@renren-inc.com
 *
 * 参考的连接：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/0702/1627.html
 * 源码解析：http://www.hainter.com/android-popup-window
 */
public class PopupWindowTestActivity extends BaseActivity {
    private PopupWindow popupWindow;
    private View popContentView;

    private TextView showTextView;
    private TextView dismissTextView;
    private TextView popContentShowBtn;
    private TextView popContentDismissBtn;

    @Override
    protected void initView() {
        showTextView = (TextView)findViewById(R.id.show_text);
        dismissTextView = (TextView)findViewById(R.id.dismiss_text);

        popContentView = LayoutInflater.from(mContext).inflate(R.layout.pop_window_layout,null);
        popContentShowBtn = (TextView)popContentView.findViewById(R.id.pop_content_show_btn);
        popContentDismissBtn = (TextView)popContentView.findViewById(R.id.pop_content_dismiss_btn);
        popupWindow = new PopupWindow(popContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);//只设置这一个，点击popWindow以外的任何位置没反应，按返回键没反应,PopupWindow弹出后，所有的触屏和物理按键都有PopupWindows处理

//        popupWindow.setOutsideTouchable(true);//如果你以为setOutsideTouchable(true)则点击PopupWindow之外的地方PopupWindow会消失，其实这玩意儿好像一点用都没有

        //要让点击PopupWindow之外的地方PopupWindow消失你需要调用setBackgroundDrawable(new BitmapDrawable());
        //如果有背景，则会在contentView外面包一层PopupViewContainer之后作为mPopupView，如果没有背景，则直接用contentView作为mPopupView。
        //而这个PopupViewContainer是一个内部私有类，它继承了FrameLayout，在其中重写了Key和Touch事件的分发处理
        //详情见：http://www.cnblogs.com/mengdd/p/3569127.html
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

        //阻止屏幕上的其他View事件，且点击PopupWindow外面不会消失，但是能响应back事件（点击back消失）,怎么办呢
        //不能调用setBackgroundDrawable，因为setBackgroundDrawable会让点击PopupWindow外面PopupWindow消失
        popupWindow.getContentView().setFocusable(true);
        popupWindow.getContentView()
                .setFocusableInTouchMode(true);
        popupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //参考了这篇文章：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2013/0304/963.html
        //要在控件都渲染完毕的情况下再show，不然会报错。
//        if(popupWindow.isShowing()){
//            popupWindow.dismiss();
//        }else{
////            popupWindow.setFocusable(true);
//            popupWindow.showAsDropDown(containerView);
//        }
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_popup_window_test_layout;
    }

    @Override
    public void initListener() {
        showTextView.setOnClickListener(this);
        dismissTextView.setOnClickListener(this);
        popContentShowBtn.setOnClickListener(this);
        popContentDismissBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_text:
                if(!popupWindow.isShowing()){
                    popupWindow.showAsDropDown(dismissTextView);
                }
                break;
            case R.id.dismiss_text:
                if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                break;
            case R.id.pop_content_show_btn:
                if(!popupWindow.isShowing()){
                    popupWindow.showAsDropDown(dismissTextView);
                }
                break;
            case R.id.pop_content_dismiss_btn:
                if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                break;
        }
    }
}
