package com.example.myapp.thread;

import android.util.Log;

/**
 * Created by zhangyanru on 2016/12/22.
 * 功能描述：
 */

public class CustomUncaugthHandler implements Thread.UncaughtExceptionHandler {
    private static volatile CustomUncaugthHandler instance;

    public static CustomUncaugthHandler getInstance() {
        if (instance == null) {
            synchronized (CustomUncaugthHandler.class) {
                if (instance == null) {
                    instance = new CustomUncaugthHandler();
                }
            }
        }
        return instance;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.d("zyr", "uncaughtException");
    }
}
