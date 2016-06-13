package com.example.myapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.util.ChatMessage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by yanru.zhang on 16/6/4.
 * Email:yanru.zhang@renren-inc.com
 */
public class SocketTestActivity2 extends BaseActivity {

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
                        Toast.makeText(SocketTestActivity2.this,"connect failed",Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                clientThread = new ClientThread();
                clientThread.start();
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

    class ClientThread extends Thread {
        //定义当前线程所处理的Socket
        private Socket clientSocket = null;
        private ObjectInputStream objectInputStream = null;
        private ObjectOutputStream objectOutputStream = null;



        public ClientThread(){
            try {
                clientSocket = new Socket("10.2.52.54",8000);
                Log.d("zyr","new clientThread success");
            } catch (Exception e) {
                myHandler.sendEmptyMessage(-1);
                e.printStackTrace();
                Log.d("zyr",e.toString());
            }
        }


        public void run() {
            try {
                while (isConnect){

                    Log.d("zyr","isConnect is true");
                    //读取服务端的消息
                    objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                    ChatMessage serverMessage = (ChatMessage) objectInputStream.readObject();
                    Log.e("zyr","serverMsg:" + serverMessage.toString());
                    if( serverMessage != null ){
                        Message message = new Message();
                        message.what = 0;
                        Bundle bundle = new Bundle();
                        bundle.putString("serverMsg",serverMessage.toString() + "\n");
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
            ChatMessage clientMessage = new ChatMessage();
            clientMessage.setToAddress(clientSocket.getLocalAddress());
            clientMessage.setBody("hi server,I am " + clientSocket.getLocalPort() );
            try {
                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectOutputStream.writeObject(clientMessage);
                objectOutputStream.flush();

                Log.d("zyr","clientMsg:" + clientMessage.toString());

                Message message2 = new Message();
                message2.what = 1;
                Bundle bundle2 = new Bundle();
                bundle2.putString("clientMsg",clientMessage.toString());
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
