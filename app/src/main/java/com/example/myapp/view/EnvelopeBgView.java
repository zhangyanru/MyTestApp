package com.example.myapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 15-11-13
 * Time: 下午5:12
 * Email: yanru.zhang@renren-inc.com
 */
public class EnvelopeBgView extends View {
    Context context;
    int sizeHeight;
    int sizeWidth;
    Paint paint;

    public EnvelopeBgView(Context context) {
        this(context, null);
    }

    public EnvelopeBgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnvelopeBgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(sizeWidth ,
                sizeHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap srcBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.emai_bg1);
        Bitmap srcBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.email_bg2);
        Bitmap srcBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        Bitmap srcBitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.right);
        Bitmap resultBitmap = addBitmap(sizeWidth, 5, srcBitmap1, srcBitmap2);
        Bitmap resultBitmap2 = addBitmap2(5, sizeHeight, srcBitmap3, srcBitmap4);

        canvas.drawBitmap(resultBitmap2, 0, 0, paint);
        canvas.drawBitmap(resultBitmap2,  sizeWidth-5,0, paint);
        canvas.drawBitmap(resultBitmap,0,0,paint);
        canvas.drawBitmap(resultBitmap, 0, sizeHeight - 5, paint);
        super.onDraw(canvas);
    }

    public Bitmap addBitmap(int width,int height,Bitmap bitmap1,Bitmap bitmap2){
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        int srcWidth = bitmap1.getWidth()+bitmap2.getWidth();
        if(width<srcWidth){
            return result;
        }
        int count = width/srcWidth;
        int nowWidth = 0;
        for(int i=0;i<count-1;i++){
            canvas.drawBitmap(bitmap1, nowWidth, 0, null);
            canvas.drawBitmap(bitmap2,nowWidth+bitmap1.getWidth(),0,null);
            nowWidth = nowWidth + srcWidth;
        }
        if(nowWidth<width){
            canvas.drawBitmap(bitmap1, nowWidth, 0, null);
            nowWidth = nowWidth + bitmap1.getWidth();
        }
        if(nowWidth<width){
            canvas.drawBitmap(bitmap2, nowWidth, 0, null);
            nowWidth = nowWidth + bitmap2.getWidth();
        }
        return result;
    }
    public Bitmap addBitmap2(int width,int height,Bitmap bitmap1,Bitmap bitmap2){
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        int srcHeight = bitmap1.getHeight()+bitmap2.getHeight();
        if(height<srcHeight){
            return result;
        }
        int count = height/srcHeight;
        int nowHeight = 0;
        for(int i=0;i<count;i++){
            canvas.drawBitmap(bitmap1, 0,nowHeight, null);
            canvas.drawBitmap(bitmap2,0,nowHeight+bitmap1.getHeight(),null);
            nowHeight = nowHeight + srcHeight;
        }
        canvas.drawBitmap(bitmap1, 0,nowHeight, null);
        return result;
    }
}
