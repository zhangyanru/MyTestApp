package com.example.myapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by zyr
 * DATE: 16-1-11
 * Time: 下午3:59
 * Email: yanru.zhang@renren-inc.com
 */
public class GroupHeadView extends View{
    private Context mContext;
    private Paint mBitmapPaint;
    private static Bitmap headDefaultBitmap;

    private final static int MAXCOUNT = 3;
    private int headCount = MAXCOUNT;//默认3个
    private ArrayList<RectF> headRectFs = new ArrayList<RectF>(headCount);
    private ArrayList<Bitmap> bitmaps =new ArrayList<Bitmap>(headCount);
    private ArrayList<Paint> paints = new ArrayList<Paint>(headCount);
    private ArrayList<String> headUrls = new ArrayList<String>();
    private ArrayList<Future<?>> taskFutures = new ArrayList<Future<?>>();
    private static float[] headSize;
    private static int overLapWidth;

    static {
        headSize = new float[]{200, 200, 200};
        overLapWidth = 50;
    }

    public GroupHeadView(Context context) {
        this(context,null);
        this.mContext = context;

    }

    public GroupHeadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        this.mContext = context;

    }

    public GroupHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        mBitmapPaint = new Paint();
        if(headDefaultBitmap == null){
            headDefaultBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_album);
        }
        for (int i = 0; i < MAXCOUNT; i++) {
            headRectFs.add(new RectF());
            if(i==0){
                headRectFs.get(0).set(0, 0, headSize[0], headSize[0]);
            }else{
                headRectFs.get(i).set(headRectFs.get(i-1).right-overLapWidth,0,headRectFs.get(i-1).right-overLapWidth+headSize[i],headSize[i]);

            }
        }
        for (int i = 0; i < MAXCOUNT; i++) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paints.add(paint);

            if (i >= paints.size() ||  i >= headRectFs.size()) {
                return;
            }
            setShader(paints.get(i), headDefaultBitmap, headRectFs.get(i));
        }
    }
    private void setShader(Paint paint, Bitmap bitmap, RectF headRect) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = headSize[headCount - 1] * 1.0f / bitmap.getWidth();
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        int top = 0;
        int bottom = bitmap.getWidth();
        int left = 0;
        int right = bitmap.getWidth();
        if(bitmap.getHeight() > bitmap.getWidth()){
            top = (bitmap.getHeight() - bitmap.getWidth()) / 2;
            bottom = bitmap.getHeight() - (bitmap.getHeight() - bitmap.getWidth()) / 2;
        }else if(bitmap.getWidth() > bitmap.getHeight()){
            left = (bitmap.getWidth() - bitmap.getHeight()) / 2;
            right = left + bitmap.getHeight();
            top = 0;
            bottom = bitmap.getHeight();
        }
        RectF bitmapRect = new RectF(left, top, right, bottom);
        matrix.setRectToRect(bitmapRect, headRect, Matrix.ScaleToFit.FILL);
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
    }
    private void setDefaultHead(){
        for (int i = 0; i < headCount; i++) {
            headRectFs.add(new RectF());
        }
        for (int i = 0; i < headCount; i++) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paints.add(paint);

            if (i >= paints.size() ||  i >= headRectFs.size()) {
                return;
            }
            setShader(paints.get(i), headDefaultBitmap, headRectFs.get(i));
        }
    }

    public void setUrls(List<String> urls) {
        if (urls == null || urls != null && urls.size() == 0) {
            headCount = 1;
            return;
        }

        if (headUrls != null) {
            headUrls.clear();
        }
        headUrls.addAll(urls);
        if(headRectFs != null){
            headRectFs.clear();
        }
        if(bitmaps != null){
            bitmaps.clear();
        }
        if(paints != null){
            paints.clear();
        }
        taskFutures.clear();

        headCount = urls.size() >= 3 ? 3 : urls.size();
        setDefaultHead();

//        for (int i = 0; i < headCount; i++) {
//            final String headUrl = headUrls.get(i);
//            headRectFs.add(new RectF());
//            Paint paint = new Paint();
//            paint.setAntiAlias(true);
//            paints.add(paint);
//            Future<?> taskFuture = RecyclingImageLoader.loadImage(null, urls.get(i), null, new BaseImageLoadingListener() {
//                @Override
//                public void onLoadingComplete(String imageUri, RecyclingImageView imageView, LoadOptions options,
//                                              Drawable loadedImage, boolean sync) {
//                    if (imageUri != null && imageUri.equals(headUrl)) {
//                        Bitmap headImage = ((BitmapDrawable) loadedImage).getBitmap();
//                        bitmaps.add(headImage);
//                        headRectFs();
//                        setHeadShaders();
//                        invalidate();
//                    }
//                }
//            });
//            taskFutures.add(taskFuture);
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (headRectFs == null) {
//            return;
//        }
//        for (int i = 0; i <headCount; i++) {
//            Log.d("zyr","draw circle");
//            canvas.drawCircle(headRectFs.get(i).left + headRectFs.get(i).width() / 2, headRectFs.get(i).top + headRectFs.get(i).height() / 2, headSize[i] / 2, paints.get(i));
//        }
    }
}
