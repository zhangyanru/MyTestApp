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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(new DrawableTestView(this));

    }
    class DrawableTestView extends View {
        public DrawableTestView(Context context) {
            super(context);
            // BitmapDrawable を作成
            bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.icon_album);
        }
        @Override
        public void onDraw(Canvas canvas) {
            // BitmapDrawable の範囲を設定
            bitmapDrawable.setBounds(0, 0, 48, 48);
            // BitmapDrawable の描画
            bitmapDrawable.draw(canvas);

            // BitmapDrawable のアルファ値を設定
            bitmapDrawable.setAlpha(128);
            bitmapDrawable.setBounds(48, 0, 96, 48);
            bitmapDrawable.draw(canvas);
            bitmapDrawable.setAlpha(255);

            // BitmapDrawable にアンチエイリアスを設定
            bitmapDrawable.setAntiAlias(true);
            bitmapDrawable.setBounds(0, 48, 240, 288);
            bitmapDrawable.draw(canvas);

            bitmapDrawable.setAntiAlias(false);
            bitmapDrawable.setBounds(240, 48, 480, 288);
            bitmapDrawable.draw(canvas);

            bitmapDrawable.setBounds(0, 0, getWidth(), getHeight());
            // BitmapDrawable にグラビティを設定
            bitmapDrawable.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            bitmapDrawable.draw(canvas);
        }
    }
    private Bitmap getBitmapFromView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
