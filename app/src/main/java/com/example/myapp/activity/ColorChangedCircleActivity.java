package com.example.myapp.activity;

import android.os.Bundle;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.view.RoundProgressBar;

/**
 * Created by admin on 15/10/20.
 */
public class ColorChangedCircleActivity extends BaseActivity {
    RoundProgressBar roundProgressBar;
    int progress =0;
    @Override
    protected int onSetContainerViewId() {
        return R.layout.colorchangedcirclelayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roundProgressBar = (RoundProgressBar)findViewById(R.id.round_progress_bar);
        roundProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            roundProgressBar.setProgress(progress);
                            if (progress == 100) {
                                progress = 0;
                            } else {
                                progress++;
                            }
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }
}
