package com.example.myapp.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.example.myapp.R;

/**
 * Created by zyr
 * DATE: 16-1-28
 * Time: 下午2:44
 * Email: yanru.zhang@renren-inc.com
 */
public class ImageCopyTestActivity extends Activity {
    BitmapDrawable bitmapDrawable;

    private ImageView imageView;
    private ImageView imageCopyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_copy_layout);
        imageView = (ImageView)findViewById(R.id.image);
        imageCopyView = (ImageView)findViewById(R.id.image_copy);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapDrawable = new BitmapDrawable(getResources(), getBitmapFromView(imageView));
                imageCopyView.setImageDrawable(bitmapDrawable);

//                imageView.setDrawingCacheEnabled(true);
//                imageCopyView.setImageBitmap(Bitmap.createBitmap(imageView.getDrawingCache()));
//                imageView.setDrawingCacheEnabled(false);
//                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_album));
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
