package com.example.myapp.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.example.myapp.R;
import com.example.myapp.view.PorterDuffXfermodeTestView;

/**
 * Created by yanru.zhang on 16/7/20.
 * Email:yanru.zhang@renren-inc.com
 */
public class GuaGuaLeTestActivity extends Activity {
    private PorterDuffXfermodeTestView clear,src,dst,srcover,
            dstover,srcin,dstin,srcout,
            dstout,srcatop,dstatop,xor,
            darken,lighten,multiply,screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gua_gua_le);
        clear = (PorterDuffXfermodeTestView) findViewById(R.id.clear);
        clear.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        clear.setMode(PorterDuff.Mode.CLEAR);

        src = (PorterDuffXfermodeTestView) findViewById(R.id.src);
        src.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        src.setMode(PorterDuff.Mode.SRC);

        dst = (PorterDuffXfermodeTestView) findViewById(R.id.dst);
        dst.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        dst.setMode(PorterDuff.Mode.DST);

        srcover = (PorterDuffXfermodeTestView) findViewById(R.id.srcover);
        srcover.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        srcover.setMode(PorterDuff.Mode.SRC_OVER);

        dstover = (PorterDuffXfermodeTestView) findViewById(R.id.dstover);
        dstover.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        dstover.setMode(PorterDuff.Mode.DST_OVER);

        srcin = (PorterDuffXfermodeTestView) findViewById(R.id.srcin);
        srcin.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        srcin.setMode(PorterDuff.Mode.SRC_IN);

        dstin = (PorterDuffXfermodeTestView) findViewById(R.id.dstin);
        dstin.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        dstin.setMode(PorterDuff.Mode.DST_IN);

        srcout = (PorterDuffXfermodeTestView) findViewById(R.id.srcout);
        srcout.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        srcout.setMode(PorterDuff.Mode.SRC_OUT);

        dstout = (PorterDuffXfermodeTestView) findViewById(R.id.dstout);
        dstout.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        dstout.setMode(PorterDuff.Mode.DST_OUT);

        srcatop = (PorterDuffXfermodeTestView) findViewById(R.id.srcatop);
        srcatop.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        srcatop.setMode(PorterDuff.Mode.SRC_ATOP);

        dstatop = (PorterDuffXfermodeTestView) findViewById(R.id.dstatop);
        dstatop.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        dstatop.setMode(PorterDuff.Mode.DST_ATOP);

        xor = (PorterDuffXfermodeTestView) findViewById(R.id.xor);
        xor.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        xor.setMode(PorterDuff.Mode.XOR);

        darken = (PorterDuffXfermodeTestView) findViewById(R.id.darken);
        darken.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        darken.setMode(PorterDuff.Mode.DARKEN);

        lighten = (PorterDuffXfermodeTestView) findViewById(R.id.lighten);
        lighten.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        lighten.setMode(PorterDuff.Mode.LIGHTEN);

        multiply = (PorterDuffXfermodeTestView) findViewById(R.id.multiply);
        multiply.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        multiply.setMode(PorterDuff.Mode.MULTIPLY);

        screen = (PorterDuffXfermodeTestView) findViewById(R.id.screen);
        screen.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.des),
                BitmapFactory.decodeResource(getResources(),R.drawable.src));
        screen.setMode(PorterDuff.Mode.SCREEN);
    }
}
