package com.example.myapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by yanru.zhang on 16/6/4.
 * Email:yanru.zhang@renren-inc.com
 */
public class SocketTestActivity extends BaseActivity {

    /*
    1.Socket基础知识

　　Socket（套接字）用于描述IP地址和端口，是通信链的句柄，应用程序可以通过Socket向网络发出请求或者应答网络请求。

　　Socket是支持TCP/IP协议的网络通信的基本操作单元，是对网络通信过程中端点的抽象表示，包含了进行网络通信所必需的5种信息：连接所使用的协议、本地主机的IP地址、本地进程的协议端口、远地主机的IP地址以及远地进程的协议端口。

   1.1 Socket的传输模式

　　Socket有两种主要的操作方式：面向连接的和无连接的。

　　面向连接的Socket操作就像一部电话，Socket必须在发送数据之前与目的地的Socket取得连接，一旦连接建立了，Socket就可以使用一个流接口进行打开、读写以及关闭操作。并且，所有发送的数据在另一端都会以相同的顺序被接收。

　　无连接的Socket操作就像一个邮件投递，每一个数据报都是一个独立的单元，它包含了这次投递的所有信息（目的地址和要发送的内容）。在这个模式下的Socket不需要连接目的地Socket，它只是简单的投出数据报。

　　由此可见，无连接的操作是快速高效的，但是数据安全性不佳；面向连接的操作效率较低，但数据的安全性较好。

　　本文主要介绍的是面向连接的Socket操作。

   1.2 Socket的构造方法

　　Java在包java.net中提供了两个类Socket和ServerSocket，分别用来表示双向连接的Socket客户端和服务器端。

　　Socket的构造方法如下：

　　（1）Socket(InetAddress address, int port);

　　（2）Socket(InetAddress address, int port, boolean stream);

　　（3）Socket(String host, int port);

　　（4）Socket(String host, int port, boolean stream);

　　（5）Socket(SocketImpl impl);

　　（6）Socket(String host, int port, InetAddress localAddr, int localPort);

　　（7）Socket(InetAddress address, int port, InetAddrss localAddr, int localPort);

　　 ServerSocket的构造方法如下：

　　（1）ServerSocket(int port);

　　（2）ServerSocket(int port, int backlog);

　　（3）ServerSocket(int port, int backlog, InetAddress bindAddr);

　　 其中，参数address、host和port分别是双向连接中另一方的IP地址、主机名和端口号；参数stream表示Socket是流Socket还是数据报Socket；
     参数localAddr和localPort表示本地主机的IP地址和端口号；SocketImpl是Socket的父类，既可以用来创建ServerSocket，也可以用来创建Socket。
     */

    //http://blog.csdn.net/xiaoweige207/article/details/6211577/

    private TextView clientTv, serverTv, clientSendTv, serverSendTv;

    private Handler myHandler;

    private boolean isConnect;

    private ClientThread clientThread;

    @Override
    protected void initView() {
        clientTv = (TextView) findViewById(R.id.client_tv);
        serverTv = (TextView) findViewById(R.id.server_tv);
        clientSendTv = (TextView) findViewById(R.id.client_send_tv);
        serverSendTv = (TextView) findViewById(R.id.server_send_tv);

        isConnect = true;

        myHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                switch (msg.what){
                    case 0:
                        String serverMsg = bundle.getString("serverMsg");
                        Log.d("zyr","handler server:" + serverMsg);
                        serverTv.append(serverMsg);
                        break;
                    case 1:
                        String clientMsg = bundle.getString("clientMsg");
                        Log.d("zyr","handler client:" + clientMsg);
                        clientTv.append(clientMsg);
                        break;
                    case -1:
                        Toast.makeText(SocketTestActivity.this,"connect failed",Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                clientThread = new ClientThread();
            }
        }).start();


    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_socket_test;
    }

    @Override
    public void initListener() {
        clientSendTv.setOnClickListener(this);
        serverSendTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.client_send_tv:
                Log.d("zyr","click sendMsg ..." );
                if(clientThread!=null){
                    clientThread.sendMsg();
                }
                break;
            case R.id.server_send_tv:
                break;
        }
    }

    public class ClientThread extends Thread {
        //定义当前线程所处理的Socket
        private Socket clientSocket = null;
        private BufferedReader bufferedReader = null;
        private BufferedWriter bufferedWriter = null;



        public ClientThread(){
            try {
                clientSocket = new Socket("10.2.52.54",8000);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                start();
            } catch (IOException e) {
                myHandler.sendEmptyMessage(-1);
                e.printStackTrace();
            }
        }


        public void run() {
            try {
                while (isConnect){

                    Log.d("zyr","isConnect is true");
                    //读取服务端的消息
                    String serverMsg = bufferedReader.readLine();
                    Log.e("zyr","serverMsg:" + serverMsg);
                    if( serverMsg!=null ){
                        Message message = new Message();
                        message.what = 0;
                        Bundle bundle = new Bundle();
                        bundle.putString("serverMsg",serverMsg + "\n");
                        message.setData(bundle);
                        myHandler.sendMessage(message);
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        public void sendMsg(){
            //给服务端发消息
            String clientMsg = "hi server! I'm " + clientSocket.getLocalPort() + "\n";
            try {
                bufferedWriter.write(clientMsg);
                bufferedWriter.flush();
                Log.d("zyr","clientMsg:" + clientMsg);

                Message message2 = new Message();
                message2.what = 1;
                Bundle bundle2 = new Bundle();
                bundle2.putString("clientMsg",clientMsg);
                message2.setData(bundle2);
                myHandler.sendMessage(message2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        isConnect = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isConnect = false;
    }
}
