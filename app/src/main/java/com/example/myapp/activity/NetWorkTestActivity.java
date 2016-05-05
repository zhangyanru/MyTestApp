package com.example.myapp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
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
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

/**
 * Created by zyr
 * DATE: 16-4-26
 * Time: 上午10:52
 * Email: yanru.zhang@renren-inc.com
 */
public class NetWorkTestActivity extends BaseActivity {

    private TextView textView;
    private Button httpClientBtn,urlConnectionBtn,okHttpBtn,imageLoaderBtn;
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
        httpClientBtn = (Button) findViewById(R.id.http_client_btn);
        urlConnectionBtn = (Button) findViewById(R.id.url_connection_btn);
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
        httpClientBtn.setOnClickListener(this);
        urlConnectionBtn.setOnClickListener(this);
        okHttpBtn.setOnClickListener(this);
        imageLoaderBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.http_client_btn:
                doHttpClient();
                doHttpClientDownImage();
                break;
            case R.id.url_connection_btn:
                doUrlConnection();
                break;
            case R.id.ok_http:
                break;
            case R.id.image_loader:
                break;
        }
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

    private void doHttpClient() {
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
