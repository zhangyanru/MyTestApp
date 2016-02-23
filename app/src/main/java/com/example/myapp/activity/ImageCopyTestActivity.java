package com.example.myapp.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.myapp.R;
import com.example.myapp.view.MyImageView;

import java.lang.reflect.Method;

/**
 * Created by zyr
 * DATE: 16-1-28
 * Time: 下午2:44
 * Email: yanru.zhang@renren-inc.com
 */
public class ImageCopyTestActivity extends Activity {
    BitmapDrawable bitmapDrawable;
    BitmapDrawable bitmapDrawable2;
    Rect originalRect;
    Rect currentRect;
    MyImageView myImageView;
    private ImageView imageView;
    private ImageView imageCopyView;

    private boolean isEditMode = false;
    private float originalX,originalY,currentX,currentY,deltaX,deltaY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_copy_layout);
        imageView = (ImageView)findViewById(R.id.image);
        imageCopyView = (ImageView)findViewById(R.id.image_copy);
        myImageView = (MyImageView)findViewById(R.id.my_image);

//        imageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Log.d("zyr","onLongClick");
//                isEditMode = false;
//                bitmapDrawable = new BitmapDrawable(getResources(), getBitmapFromView(imageView));
//                imageCopyView.setImageDrawable(bitmapDrawable);

//                imageView.setDrawingCacheEnabled(true);
//                imageCopyView.setImageBitmap(Bitmap.createBitmap(imageView.getDrawingCache()));
//                imageView.setDrawingCacheEnabled(false);
//                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_album));
//
//                return true;
//            }
//        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("zyr", "onTouch ACTION_DOWN");
                        originalX = event.getX();
                        originalY = event.getY();
                        bitmapDrawable2 = new BitmapDrawable(getResources(), getBitmapFromView(imageView));
                        originalRect = new Rect((int)originalX,(int)originalY,imageView.getWidth()+100,imageView.getHeight()+100);
                        currentRect = new Rect(originalRect);
                        bitmapDrawable2.setBounds(currentRect);
                        myImageView.setBitmapDrawable(bitmapDrawable2);
                        myImageView.postInvalidate();
                        Log.d("zyr", "originalX:" + originalX + "    originalY:" + originalY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("zyr", "onTouch ACTION_MOVE");
                        currentX = event.getX();
                        currentY = event.getY();
                        deltaX = currentX - originalX;
                        deltaY = currentY - originalY;
                        currentRect.offsetTo(originalRect.left + (int)deltaX, originalRect.top + (int)deltaY);
                        bitmapDrawable2.setBounds(currentRect);
                        myImageView.setBitmapDrawable(bitmapDrawable2);
                        myImageView.postInvalidate();
                        if (isEditMode) {
                            imageCopyView.setTranslationX(deltaX);
                            imageCopyView.setTranslationY(deltaY);
                            Log.d("zyr", "deltaX:" + deltaX + "    deltaY:" + deltaY);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("zyr", "onTouch ACTION_UP");
                        isEditMode = false;
                        break;
                }
                return false;
            }
        });


        imageCopyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zyr", "click imageCopyView");
            }
        });

    }

    /***
     * 也可以用getDrawingCache()，但是要注意
     * 1.在调用getDrawingCache()方法从ImageView对象获取图像之前，一定要调用setDrawingCacheEnabled(true)方法，否则，无法从ImageView中获取图像；
     * 2.在调用getDrawingCache()方法从ImageView对象获取图像之后，一定要调用setDrawingCacheEnabled(false)方法，以清空画图缓冲区，否则，下一次从ImageView对象iv_photo中获取的图像，还是原来的图像。
     * @param v
     * @return
     */
    private Bitmap getBitmapFromView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
