package com.example.myapp.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.LinkedHashMap;

/**
 * Created by yanru.zhang on 16/7/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class FrameAnimTestActivity extends Activity implements View.OnClickListener{
    private ImageView imageView;
    private AnimationDrawable animationDrawable;
    private Handler mHandler;
    private TextView tv;
    private Button write1,write2,read1,read2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();

        setContentView(R.layout.activity_frame_anim);
        imageView = (ImageView) findViewById(R.id.image_view);
        tv = (TextView) findViewById(R.id.tv);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();

        LinkedHashMap<Integer,Integer> linkedHashMap = new LinkedHashMap<>(0,1,true);
        linkedHashMap.put(1,1);
        linkedHashMap.put(2,10);
        linkedHashMap.put(3,11);
        linkedHashMap.put(4,7);
        linkedHashMap.put(5,5);
        linkedHashMap.put(2,10);
        Log.d("zyr",linkedHashMap.toString());

        write1 = (Button) findViewById(R.id.btn1_write);
        write2 = (Button) findViewById(R.id.btn2_write);
        read1 = (Button) findViewById(R.id.btn1_read);
        read2 = (Button) findViewById(R.id.btn2_read);
        write1.setOnClickListener(this);
        write2.setOnClickListener(this);
        read1.setOnClickListener(this);
        read2.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1_write:
                Test test1 = new Test("zhang","beijing","23");
                write(test1,getApplicationContext().getFilesDir().getAbsolutePath()+"/test");
                break;
            case R.id.btn2_write:
                Test test2 = new Test("ren","wuhan","60");
                write(test2,getApplicationContext().getFilesDir().getAbsolutePath()+"/haha");
                break;
            case R.id.btn1_read:
                read(getApplicationContext().getFilesDir().getAbsolutePath()+"/test");
                break;
            case R.id.btn2_read:
                read(getApplicationContext().getFilesDir().getAbsolutePath()+"/haha");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
    }


    public void write(final Test test,final String s){
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(s);
                try {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    System.out.print(test.toString());
                    objectOutputStream.writeObject(test);
                    objectOutputStream.flush();
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void read(final String s){
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(s);
                if(file.exists()){
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                        Test test = (Test)objectInputStream.readObject();
                        System.out.print(test.toString());
                        objectInputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (StreamCorruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}
