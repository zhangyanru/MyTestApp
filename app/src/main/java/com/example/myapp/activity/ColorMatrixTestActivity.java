package com.example.myapp.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.example.myapp.R;
import com.example.myapp.view.MyColorMatrixView;

/**
 * Created by yanru.zhang on 16/7/19.
 * Email:yanru.zhang@renren-inc.com
 */
public class ColorMatrixTestActivity extends Activity implements SeekBar.OnSeekBarChangeListener{
    private MyColorMatrixView imageView;
    private SeekBar hueSeekBar,saturationSeekBar,brightnessSeekBar;
    private RadioGroup hueRadioGroup;
    private ColorMatrix colorMatrix;
    private int[] hues = new int[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_color_matrix);
        imageView = (MyColorMatrixView) findViewById(R.id.image_view);
        imageView.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.flower));
        hueRadioGroup = (RadioGroup) findViewById(R.id.hue_radio_group);
        hueRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("zyr","checkedId:" + checkedId);
                colorMatrix.setRotate(checkedId-1,0);
                imageView.setColorMatrix(colorMatrix);
                hueSeekBar.setProgress(hues[checkedId-1]);
            }
        });
        hueSeekBar = (SeekBar) findViewById(R.id.hue);
        saturationSeekBar = (SeekBar) findViewById(R.id.saturation);
        brightnessSeekBar = (SeekBar) findViewById(R.id.brightness);

        colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{1,0,0,0,0,
                                    0,1,0,0,0,
                                    0,0,1,0,0,
                                    0,0,0,1,0});

        hueSeekBar.setOnSeekBarChangeListener(this);
        saturationSeekBar.setOnSeekBarChangeListener(this);
        brightnessSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.hue:
                //axis:0-red,1-green-2-blue
                //degree:
                Log.d("zyr","hue:" + hueRadioGroup.getCheckedRadioButtonId());
                hues[hueRadioGroup.getCheckedRadioButtonId()-1] = progress;
                colorMatrix.setRotate(hueRadioGroup.getCheckedRadioButtonId()-1,progress);
                imageView.setColorMatrix(colorMatrix);
                break;
            case R.id.saturation:
                colorMatrix.setSaturation(progress);
                imageView.setColorMatrix(colorMatrix);
                break;
            case R.id.brightness:
                colorMatrix.setScale(progress,progress,progress,1);
                imageView.setColorMatrix(colorMatrix);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
