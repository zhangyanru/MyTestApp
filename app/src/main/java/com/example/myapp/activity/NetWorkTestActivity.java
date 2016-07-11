package com.example.myapp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.util.Methods;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-4-26
 * Time: 上午10:52
 * Email: yanru.zhang@renren-inc.com
 *
 * tcp,udp,http,socket,tcp/ip的区别是什么？链接：http://zyc-to.blog.163.com/blog/static/17152400201338354067/
 * http,socket的区别：http://blog.csdn.net/zeng622peng/article/details/5546384
 */
public class NetWorkTestActivity extends BaseActivity {

    private TextView textView;
    private Button httpClientGetBtn,httpClientPostBtn,urlConnectionBtn,socketBtn,okHttpBtn,imageLoaderBtn;
    private ImageView imageView;
    private ScrollView scrollView;
    private Handler handler;

    private final static int HTTP_CLIENT = 1;
    private final static int URL_CONNECTION = 2;
    private final static int OK_HTTP = 3;
    private final static int IMAGE_LOADER = 4;

    Bitmap bitmap;

    @Override
    protected void initView() {
        textView = (TextView) findViewById(R.id.tv);
        textView.setText("参考 : http://www.open-open.com/lib/view/open1453008154245.html");
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        imageView = (ImageView) findViewById(R.id.image_view);
        httpClientGetBtn = (Button) findViewById(R.id.http_client_get_btn);
        httpClientPostBtn = (Button) findViewById(R.id.http_client_post_btn);
        urlConnectionBtn = (Button) findViewById(R.id.url_connection_btn);
        socketBtn = (Button) findViewById(R.id.socket_btn);
        okHttpBtn = (Button) findViewById(R.id.ok_http);
        imageLoaderBtn = (Button) findViewById(R.id.image_loader);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int i = msg.getData().getInt("result");
                String s = msg.getData().getString("response");
                textView.setText(s);
                scrollView.scrollTo(0,0);
                switch (i){
                    case HTTP_CLIENT:
                        Methods.toast(NetWorkTestActivity.this,"http client success!");
                        break;
                    case URL_CONNECTION:
                        Methods.toast(NetWorkTestActivity.this,"http url connection success!");
                        break;

                }
            }
        };
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_net_work_layout;
    }

    @Override
    public void initListener() {
        httpClientGetBtn.setOnClickListener(this);
        httpClientPostBtn.setOnClickListener(this);
        urlConnectionBtn.setOnClickListener(this);
        socketBtn.setOnClickListener(this);
        okHttpBtn.setOnClickListener(this);
        imageLoaderBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.http_client_get_btn:
                doHttpClientGet();
                doHttpClientDownImage();
                break;
            case R.id.http_client_post_btn:
                doHttpClientPost();
                break;
            case R.id.url_connection_btn:
                doUrlConnection();
                break;
            case R.id.socket_btn:
                doSocket();
                break;
            case R.id.ok_http:
                break;
            case R.id.image_loader:
                break;
        }
    }

    private void doSocket() {
        show(this,SocketTestActivity2.class);
    }

    private void doUrlConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.zcool.com.cn");
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    urlConnection.connect();
                    //结果
                    InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
                    //使用缓冲一行行的读入，加速InputStreamReader的速度
                    BufferedReader buffer = new BufferedReader(isr);
                    String inputLine;
                    StringBuffer resultData = new StringBuffer();

                    while((inputLine = buffer.readLine()) != null){
                        resultData.append(inputLine);
                        resultData.append("\n");
                    }
                    buffer.close();
                    isr.close();
                    urlConnection.disconnect();

                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("response", resultData.toString());
                    bundle.putInt("result", URL_CONNECTION);
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }
            }
        }).start();
    }

    private void doHttpClientGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpGet httpGet = new HttpGet("http://www.hao163.com");
                // 创建默认的httpClient实例.
                HttpClient httpclient = new DefaultHttpClient();
                try {
                    HttpResponse httpResponse = httpclient.execute(httpGet);
                    HttpEntity httpEntity = httpResponse.getEntity();

                    InputStreamReader isr = new InputStreamReader(httpEntity.getContent());
                    //使用缓冲一行行的读入，加速InputStreamReader的速度
                    BufferedReader buffer = new BufferedReader(isr);
                    String inputLine;
                    StringBuffer resultData = new StringBuffer();

                    while((inputLine = buffer.readLine()) != null){
                        resultData.append(inputLine);
                        resultData.append("\n");
                    }
                    buffer.close();
                    isr.close();

                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("response", resultData.toString());
                    bundle.putInt("result", HTTP_CLIENT);
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }
            }
        }).start();

    }

    private void doHttpClientPost() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 创建默认的httpClient实例.
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://10.4.24.116:8080/transaction/count");
                    httpPost.setHeader("Accept-Encoding","identity");
                    httpPost.setHeader("content-type","application/x-www-form-urlencoded; charset=UTF-8");
                    httpPost.setHeader("User-Agent","Dalvik/2.1.0(Linux; U; Android 5.0.1; GT-I9500 Build/LRX22C)");
                    httpPost.setHeader("Accept","*/*");
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("sessionKey","6664cda0cf87a13eec12b6860128d302"));
                    nameValuePairs.add(new BasicNameValuePair("client_info","%7B%22deviceId%22%3A%22357747050997167%22%2C%22mac%22%3A%2240%3A0E%3A85%3A5B%3A63%3A13%22%2C%22version%22%3A%221.3.0%22%2C%22fromId%22%3A0%2C%22appId%22%3A481559%2C%22timezone%22%3A28800%2C%22os%22%3A%2221_5.0.1%22%2C%22model%22%3A%22GT-I9500%22%7D"));
                    nameValuePairs.add(new BasicNameValuePair("sig","406cb5cea65e7611b60eec19f383eaa4"));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

                    HttpResponse httpResponse = httpclient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();

                    InputStreamReader isr = new InputStreamReader(httpEntity.getContent());
                    //使用缓冲一行行的读入，加速InputStreamReader的速度
                    BufferedReader buffer = new BufferedReader(isr);
                    String inputLine;
                    StringBuffer resultData = new StringBuffer();

                    while((inputLine = buffer.readLine()) != null){
                        resultData.append(inputLine);
                        resultData.append("\n");
                    }
                    buffer.close();
                    isr.close();

                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("response", resultData.toString());
                    bundle.putInt("result", HTTP_CLIENT);
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }
            }
        }).start();

    }

    private void doHttpClientDownImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet get = new HttpGet("http://images.sohu.com/uiue/sohu_logo/beijing2008/2008sohu.gif");
                    HttpResponse httpResponse = client.execute(get);
                    // 如果服务器响应的是OK的话！
                    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        InputStream is = httpResponse.getEntity().getContent();
                        bitmap = BitmapFactory.decodeStream(is);
                        is.close();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
