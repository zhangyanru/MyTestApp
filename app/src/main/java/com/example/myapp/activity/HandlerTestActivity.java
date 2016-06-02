package com.example.myapp.activity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;

/**
 * Created by yanru.zhang on 16/6/1.
 *
 * http://mobile.51cto.com/aprogram-442833.htm
 */
public class HandlerTestActivity extends BaseActivity {

    private TextView textView;

    private MyMainThradHandler myMainThreadHandler = new MyMainThradHandler();

    private MyThread thread;

    @Override
    protected void initView() {
        textView = (TextView) findViewById(R.id.tv);

        thread = new MyThread();
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();

        //子线程向主线程发消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myMainThreadHandler.sendEmptyMessage(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myMainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(2 + ""); //在主线程执行了，因为myHandler是在主线程的，把runnable传到主线程
                    }
                });
            }
        }).start();


    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        //主线程向子线程发消息
        thread.myThreadHandler.sendEmptyMessage(1);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_handler_test;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    class MyMainThradHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    System.out.println(Thread.currentThread().getName() + " handleMessage");
                    textView.setText(1 + "");
                    //主线程向子线程发消息
                    break;
            }
        }
    }

    class MyThreadHandler extends Handler{
        public MyThreadHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    System.out.println(Thread.currentThread().getName() + " handleMessage");
//                    textView.setText("thread");
                    break;
            }
        }
    }

    class MyThread extends Thread{

        public MyThreadHandler myThreadHandler ;

        @Override
        public void run() {
            super.run();
            Looper.prepare();

            myThreadHandler = new MyThreadHandler(Looper.myLooper());

            Looper.loop();
        }
    }
}
