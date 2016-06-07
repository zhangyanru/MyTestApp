package com.example.myapp.activity;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.myapp.R;
import com.example.myapp.util.AsyncImageLoader3;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yanru.zhang on 16/6/2.
 * Email:yanru.zhang@renren-inc.com
 *
 * http://www.cnblogs.com/toosuo/archive/2013/02/01/2888971.html
 */
public class ThreadPoolTestActivity extends BaseActivity {

    private ImageView imageView;

    private Handler handler = new Handler();

    private ExecutorService executorService = Executors.newFixedThreadPool(5);//创建一个可重用固定线程数的线程池

    private AsyncImageLoader3 asyncImageLoader3  = new AsyncImageLoader3();

    private AsyncImageLoader3.ImageCallback callBack;

    @Override
    protected void initView() {
        imageView = (ImageView) findViewById(R.id.image_v);

        //Handler+Thread+Message模式
        //Handler+ExecutorService(线程池)+MessageQueue模式
        /*
        loadImage("http://www.baidu.com/img/baidu_logo.gif",imageView);
        loadImage("https://s3-us-west-1.amazonaws.com/chime-static-cloud/home/images/logo/72.png",imageView);
        loadImage("http://www.csdn.net/css/logo.png",imageView);
        loadImage("http://www.csdn.net/css/x.png",imageView);
        loadImage("http://c.csdnimg.cn/www/images/ali.png",imageView);

        loadImage("http://images.csdn.net/20160530/a1.jpg",imageView);
        loadImage("http://img.knowledge.csdn.net/upload/base/1464683375884_884.jpg",imageView);
        loadImage("https://s3-us-west-1.amazonaws.com/chime-static-cloud/home/images/logo/72.png",imageView);
        loadImage("http://www.baidu.com/img/baidu_logo.gif",imageView);
        loadImage("https://s3-us-west-1.amazonaws.com/chime-static-cloud/home/images/logo/72.png",imageView);
        */
        //Handler+ExecutorService(线程池)+MessageQueue+缓存模式
        callBack = new AsyncImageLoader3.ImageCallback() {
            @Override
            public void imageLoaded(Drawable imageDrawable) {
                imageView.setImageDrawable(imageDrawable);
            }
        };
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncImageLoader3.loadDrawable("http://www.baidu.com/img/baidu_logo.gif",callBack);
            }
        });


        //阅读 : http://blog.sina.com.cn/s/blog_5da93c8f0101ofdq.html

    }

    private void loadImage(final String s, final ImageView imageView) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " load...");
                    final Drawable drawable = Drawable.createFromStream(new URL(s).openStream(),"image.png");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageDrawable(drawable);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_thread_pool_test;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
