package com.example.myapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.myapp.adapter.DragGridAdapter;

/**http://zhangcb666.blog.163.com/blog/static/4696352920124221043837/
 * Created by zyr
 * DATE: 16-1-20
 * Time: 下午3:04
 * Email: yanru.zhang@renren-inc.com
 */
public class DragGridView extends GridView {
    private static final String TAG = "GragGridView";
    //定义基本的成员变量
    private ImageView dragImageView; //拖动的影像
    private int dragSrcPosition;//原始对应位置
    private int dragPosition;//拖动到坐标对应的位置

    //x,y坐标的计算
    private int dragPointX; //按下坐标相对于当前项位置--  相对
    private int dragPointY;
    private int dragOffsetX;//当前窗体和屏幕的距离 --绝对
    private int dragOffsetY;

    private WindowManager windowManager;//窗口控制类
    private WindowManager.LayoutParams windowParams; //用于控制拖动项的显示参数

    //  private int scaledTouchSlop; //判断滑动的距离
    private int upScrollBounce; //拖动时候,开始向上滚动的边界
    private int downScrollBounce;//拖动时候,开始向下滚动的边界

    public DragGridView(Context context) {
        super(context);
    }
    public DragGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public DragGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    //触控拦截事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            int x = (int)ev.getX();
            int y = (int)ev.getY();

            //选中数据项位置 ,
            dragSrcPosition = dragPosition = pointToPosition(x, y);
            if(dragPosition == AdapterView.INVALID_POSITION){//无效位置(超出边蛸,分割线)
                return super.onInterceptTouchEvent(ev);
            }
            Log.i(TAG, "[onInterceptTouchEvent] dragSrcPosition:" + dragSrcPosition + ",getFirstVisiblePosition():" + getFirstVisiblePosition());
            //getFirstVisiblePosition()返回第一个dispaly在界面的view在adapter的位置  可能是0,也可能是4
            ViewGroup itemView = (ViewGroup)getChildAt(dragPosition - getFirstVisiblePosition());
            //计算按下的坐标相对当前项的位置
            dragPointX = x - itemView.getLeft();//在当前项的X位置
            dragPointY = y - itemView.getTop();
            //当前窗体和屏幕的距离
            dragOffsetX = (int) (ev.getRawX()-x);
            dragOffsetY = (int) (ev.getRawY() -y);
            Log.i(TAG, "[onInterceptTouchEvent] [x:"+x+",y:"+y+"],[rawX:"+ev.getRawX()+",rawY:"+ev.getRawY()+"]");
            Log.i(TAG, "[onInterceptTouchEvent] [dragPointX:"+dragPointX+",dragPointY:"+dragPointY+"],[dragOffsetX:"+dragOffsetX+",dragOffsetY:"+dragOffsetY+"]");
//
//         upScrollBounce = Math.min(y-scaledTouchSlop, getHeight()/4);
//         downScrollBounce = Math.max(y+scaledTouchSlop, getHeight()*3/4);

            upScrollBounce = Math.min(y, getHeight()/4);//向上可以滚动的距离
            downScrollBounce = Math.max(y, getHeight()*3/4);//向下可以滚动的距离

            itemView.setDrawingCacheEnabled(true);
            Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache());
            startDrag(bm,x,y);
        }

        return super.onInterceptTouchEvent(ev);

    }

    //开始拖动
    public void startDrag(Bitmap bm,int x,int y){
        stopDrag();
        windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.TOP|Gravity.LEFT;
        windowParams.x = x - dragPointX + dragOffsetX;//计算当前项Left离窗体的距离
        windowParams.y = y - dragPointY + dragOffsetY;//计算当前项Top离窗体的距离

        windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;

        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        windowParams.format = PixelFormat.TRANSLUCENT;

        windowParams.windowAnimations = 0;

        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bm);
        windowManager = (WindowManager)getContext().getSystemService(getContext().WINDOW_SERVICE);
        windowManager.addView(imageView, windowParams);
        dragImageView = imageView;
    }
    //停止拖到
    public void stopDrag(){
        if(dragImageView != null){
            windowManager.removeView(dragImageView);
            dragImageView = null;
        }
    }

    //拖动
    public void onDrag(int x,int y){
        if(dragImageView != null){
            windowParams.alpha = 0.9f;
            windowParams.x = x - dragPointX + dragOffsetX;
            windowParams.y = y - dragPointY + dragOffsetY;
            windowManager.updateViewLayout(dragImageView, windowParams);

        }
        int tempPosition = pointToPosition(x, y);
        if(tempPosition != INVALID_POSITION){
            dragPosition = tempPosition;
        }

        if(y < upScrollBounce || y > downScrollBounce){
            setSelection(dragPosition);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(dragImageView != null && dragPosition != INVALID_POSITION){
            int action = ev.getAction();
            switch(action){
                case MotionEvent.ACTION_UP:
                    int upx = (int)ev.getX();
                    int upY = (int)ev.getY();
                    stopDrag();
                    onDrop(upx,upY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveX = (int)ev.getX();
                    int moveY = (int)ev.getY();
                    onDrag(moveX, moveY);
                    break;
            }
            return true;
        }


        return super.onTouchEvent(ev);
    }

    //拖到落下
    public void onDrop(int x,int y){
        int tempPosition = pointToPosition(x,y);
        if(tempPosition != INVALID_POSITION){
            dragPosition  = tempPosition;
        }
        //超出边界
        if(y < getChildAt(0).getTop()){
            dragPosition = 0;
        }else if(y > getChildAt(getChildCount()-1).getBottom() ||
                (y > getChildAt(getChildCount()-1).getTop() &&
                        x > getChildAt(getChildCount()-1).getRight())){
            //超出下边界
            dragPosition = getAdapter().getCount() -1;
        }
        //数据交换当前拖动的于拖到到位置上的图片交换
        if(dragPosition != dragSrcPosition && dragPosition > -1 && dragPosition < getAdapter().getCount()){
            DragGridAdapter adapter = (DragGridAdapter)getAdapter();
            String dragSrcItem = adapter.getItem(dragSrcPosition);
            String dragTargetItem = adapter.getItem(dragPosition);

            adapter.remove(dragSrcItem);
            adapter.insert(dragSrcItem, dragPosition);

            adapter.remove(dragTargetItem);
            adapter.insert(dragTargetItem, dragSrcPosition);

            System.out.println("srcPosition="+dragSrcPosition+"  dragPosition="+dragPosition);
            //Toast.makeText(getContext(), adapter.getList().toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
