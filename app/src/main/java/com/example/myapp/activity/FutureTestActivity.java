package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapp.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by yanru.zhang on 16/8/4.
 * Email:yanru.zhang@renren-inc.com
 */
public class FutureTestActivity extends Activity implements View.OnClickListener{
    private Button extendsThreadBtn,runnableThreadBtn,callableFutureTaskThreadBtn,
            executorServiceCallableFutureBtn,executorServiceRunnableFutureBtn,executorServiceFutureTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_future);
        extendsThreadBtn = (Button) findViewById(R.id.extends_thread);
        runnableThreadBtn = (Button) findViewById(R.id.runnable_thread);
        callableFutureTaskThreadBtn = (Button) findViewById(R.id.callable_futuretask_thread);
        executorServiceCallableFutureBtn = (Button) findViewById(R.id.executor_service_callable_future);
        executorServiceRunnableFutureBtn = (Button) findViewById(R.id.executor_service_runnable_future);
        executorServiceFutureTaskBtn = (Button) findViewById(R.id.executor_service_futuretask);

        extendsThreadBtn.setOnClickListener(this);
        runnableThreadBtn.setOnClickListener(this);
        callableFutureTaskThreadBtn.setOnClickListener(this);
        executorServiceCallableFutureBtn.setOnClickListener(this);
        executorServiceRunnableFutureBtn.setOnClickListener(this);
        executorServiceFutureTaskBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.extends_thread:
                extendsThread();
                break;
            case R.id.runnable_thread:
                runnableThread();
                break;
            case R.id.callable_futuretask_thread:
                callableFutureTaskThread();
                break;
            case R.id.executor_service_callable_future:
                executorServiceCallableFuture();
                break;
            case R.id.executor_service_runnable_future:
                executorServiceRunnableFuture();
                break;
            case R.id.executor_service_futuretask:
                executorServiceFutureTask();
                break;
        }
    }

    /**
     * 通过 executorService.submit(futureTask)开启线程
     */
    private void executorServiceFutureTask() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);//创建一个可重用固定线程数的线程池;
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("通过 executorService.submit(futureTask)开启线程");
                return "通过 executorService.submit(futureTask)开启线程";
            }
        });
        executorService.submit(futureTask);
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过 executorService.submit(runnable,T)并且return一个Future开启线程
     */
    private void executorServiceRunnableFuture() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);//创建一个可重用固定线程数的线程池;
        Future<String> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("通过 executorService.submit(runnable,T)并且return一个Future开启线程");
            }
        },"finish 通过 executorService.submit(runnable,T)并且return一个Future开启线程");
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过 executorService.submit(callable)并且return一个Future开启线程
     */
    private void executorServiceCallableFuture() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);//创建一个可重用固定线程数的线程池;
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("通过 executorService.submit(callable)并且return一个Future开启线程");
                return "finish 通过 executorService.submit(callable)并且return一个Future开启线程";
            }
        });
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过new Thread(new FutureTask(callable))开启线程
     */
    private void callableFutureTaskThread() {
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("通过new Thread(new FutureTask(callable))开启线程");
                return "finish 通过new Thread(new FutureTask(callable))开启线程";
            }
        });
        new Thread(futureTask).start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过new Thread(runnable)开启线程
     */
    private void runnableThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("通过new Thread(runnable)开启线程");
            }
        }).start();
    }

    /**
     * 通过继承Thread类开启线程
     */
    private void extendsThread() {
        new MyThread().start();
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();

            System.out.println("通过继承Thread类开启线程");
        }
    }
}
