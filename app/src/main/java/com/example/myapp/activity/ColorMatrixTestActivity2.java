package com.example.myapp.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.example.myapp.R;
import com.example.myapp.view.MyColorMatrixView;

/**
 * Created by yanru.zhang on 16/7/19.
 * Email:yanru.zhang@renren-inc.com
 */
public class ColorMatrixTestActivity2 extends Activity implements View.OnClickListener{
    private MyColorMatrixView imageView;
    private ColorMatrix colorMatrix;
    private Button huiDu,fanZhuan,huaiJiu,quSe,gaoBaoHe;
    private Button diPian,oldPicture,fuDiao;

    private static final int TYPE_DIPIAN = 1;
    private static final int TYPE_OLD_PICTURE = 2;
    private static final int TYPE_FU_DIAO = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_color_matrix2);
        imageView = (MyColorMatrixView) findViewById(R.id.image_view);
        imageView.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.flower));
        huiDu = (Button) findViewById(R.id.hui_du);
        fanZhuan = (Button) findViewById(R.id.fan_zhuan);
        huaiJiu = (Button) findViewById(R.id.huai_jiu);
        quSe = (Button) findViewById(R.id.qu_se);
        gaoBaoHe = (Button) findViewById(R.id.gao_bao_he_du);
        diPian = (Button) findViewById(R.id.di_pian);
        oldPicture= (Button) findViewById(R.id.old_picture);
        fuDiao = (Button) findViewById(R.id.fu_diao);


        huiDu.setOnClickListener(this);
        fanZhuan.setOnClickListener(this);
        huaiJiu.setOnClickListener(this);
        quSe.setOnClickListener(this);
        gaoBaoHe.setOnClickListener(this);
        diPian.setOnClickListener(this);
        oldPicture.setOnClickListener(this);
        fuDiao.setOnClickListener(this);

        colorMatrix = new ColorMatrix();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hui_du:
                colorMatrix = new ColorMatrix(new float[]{
                        0.33F,	0.59F,	0.11F,	0,	0,
                        0.33F,	0.59F,	0.11F,	0,	0,
                        0.33F,	0.59F,	0.11F,	0,	0,
                        0,		0,		0,		1,	0
                });
                imageView.setColorMatrix(colorMatrix);
                break;
            case R.id.fan_zhuan:
                colorMatrix = new ColorMatrix(new float[]{
                        -1,	0,	0,	1,	1,
                        0,	-1,	0,	1,	1,
                        0,	0,	-1,	1,	1,
                        0,	0,	0,	1,	0
                });
                imageView.setColorMatrix(colorMatrix);
                break;
            case R.id.huai_jiu:
                colorMatrix = new ColorMatrix(new float[]{
                        0.393F,	0.769F,	0.189F,	0,	0,
                        0.349F,	0.686F,	0.168F,	0,	0,
                        0.272F,	0.534F,	0.131F,	0,	0,
                        0,		0,		0,		1,	0
                });
                imageView.setColorMatrix(colorMatrix);
                break;
            case R.id.qu_se:
                colorMatrix = new ColorMatrix(new float[]{
                        1.5F,	1.5F,	1.5F,		0,	-1,
                        1.5F,	1.5F,	1.5F,		0,	-1,
                        1.5F,	1.5F,	1.5F,		0,	-1,
                        0,		0,		0,		1,	0

                });
                imageView.setColorMatrix(colorMatrix);
                break;
            case R.id.gao_bao_he_du:
                colorMatrix = new ColorMatrix(new float[]{
                        1.438F,	-0.122F,	-0.016F,	0,	-0.03F,
                        -0.062F,	1.378F,	-0.016F,	0,	0.05F,
                        -0.062F,	-0.122F,	1.483F,	0,	-0.02F,
                        0,		0,		0,		1,	0
                });
                imageView.setColorMatrix(colorMatrix);
                break;
            case R.id.di_pian:
                imageView.setBitmap(handlePixels(imageView.getBitmap(),TYPE_DIPIAN));
                imageView.resetColorMatrix();
                break;
            case R.id.old_picture:
                imageView.setBitmap(handlePixels(imageView.getBitmap(),TYPE_OLD_PICTURE));
                imageView.resetColorMatrix();
                break;
            case R.id.fu_diao:
                imageView.setBitmap(handlePixels(imageView.getBitmap(),TYPE_FU_DIAO));
                imageView.resetColorMatrix();
                break;
        }
    }

    private Bitmap handlePixels(Bitmap bitmap,int type){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] oldPixels = new int[width * height];
        bitmap.getPixels(oldPixels,0,width,0,0,width,height);

        int[] newPixels = new int[width * height];
        Bitmap newBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        //处理新像素点
        //获取每个像素的ARGB的值
        for(int i=0;i<oldPixels.length;i++){
            if(type == TYPE_DIPIAN){
                newPixels[i] = diPain(oldPixels[i]);
            }else if(type == TYPE_OLD_PICTURE){
                newPixels[i] = oldPictrue(oldPixels[i]);
            }else if(type == TYPE_FU_DIAO){
                if(i == oldPixels.length - 1){
                    newPixels[i] = fuDiao(oldPixels[i],oldPixels[0]);
                }else{
                    newPixels[i] = fuDiao(oldPixels[i],oldPixels[i+1]);
                }
            }
        }
        newBitmap.setPixels(newPixels,0,width,0,0,width,height);
        return newBitmap;
    }

    /**
     * 算法：
     * B.r = 255 - B.r;
     * B.g = 255 - B.g;
     * B.b = 255 - B.b;
     * @param oldPixel
     * @return
     */
    private int diPain(int oldPixel){
        int oldColor = oldPixel;
        int oldR = Color.red(oldColor);
        int oldG = Color.green(oldColor);
        int oldB = Color.blue(oldColor);
        int oldA = Color.alpha(oldColor);

        int newR = 255 - oldR;
        int newG = 255 - oldG;
        int newB = 255 - oldB;
        int newA = oldA;
        if(newR > 255){
            newR = 255;
        } else if (newR < 0) {
            newR = 0;
        }
        if(newG > 255){
            newG = 255;
        } else if (newG < 0) {
            newG = 0;
        }
        if(newB > 255){
            newB = 255;
        } else if (newB < 0) {
            newB = 0;
        }
        return Color.argb(newA,newR,newG,newB);
    }

    /**
     * r = 0.393*r + 0.769*g + 0.189*b
     * g = 0.349*r + 0.686*g + 0.168*b
     * b = 0.272*r + 0.534*g + 0.131*b
     * @param oldPixel
     * @return
     */
    private int oldPictrue(int oldPixel){
        int oldColor = oldPixel;
        int oldR = Color.red(oldColor);
        int oldG = Color.green(oldColor);
        int oldB = Color.blue(oldColor);
        int oldA = Color.alpha(oldColor);

        int newR = (int)(0.393*oldR + 0.769*oldG+ 0.189*oldB);
        int newG = (int)(0.349*oldR+ 0.686*oldG + 0.168*oldB);
        int newB = (int)(0.272*oldR + 0.534*oldG + 0.131*oldB);
        int newA = oldA;
        return Color.argb(newA,newR,newG,newB);
    }

    /**
     * ABC三点
     * B.r = C.r - B.r + 127;
     * B.g = C.g - B.g + 127;
     * B.b = C.b - B.b + 127;
     * @param oldPixel1
     * @param oldPixel2
     * @return
     */
    private int fuDiao(int oldPixel1,int oldPixel2){
        int oldColor1 = oldPixel1;
        int oldR1 = Color.red(oldColor1);
        int oldG1 = Color.green(oldColor1);
        int oldB1 = Color.blue(oldColor1);
        int oldA1 = Color.alpha(oldColor1);

        int oldColor2 = oldPixel2;
        int oldR2 = Color.red(oldColor2);
        int oldG2 = Color.green(oldColor2);
        int oldB2 = Color.blue(oldColor2);
        int oldA2 = Color.alpha(oldColor2);

        int newR = oldR2 - oldR1 + 127;
        int newG = oldG2 - oldG1 + 127;
        int newB = oldB2 - oldB1 + 127;
        int newA = oldA2 - oldA1 + 127;
        return Color.argb(newA,newR,newG,newB);
    }
}
