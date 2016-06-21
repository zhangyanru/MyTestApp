package com.example.myapp.chatdemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yanru.zhang on 16/6/15.
 * Email:yanru.zhang@renren-inc.com
 */
public class SocketService extends Service {

    //activity和service的通信方式
    //1.通过bind得到service,这样activity可以访问service的方法;activity注册service中的listener得到回调
    //2.通过broadcast receiver,activity向service发送广播，从而进行一些操作

    private Socket clientSocket;
    private SocketReadThread socketReadThread;
    private SocketWriteThread socketWriteThread;

    public final static String SEND_ACTION = "send_msg";
    public final static String RECEIVE_ACTION = "receive_msg";

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            sendMsg(data);
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        return new SocketServiceBinder();
    }

    public class SocketServiceBinder extends Binder{
        public SocketService getService(){
            return SocketService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("zyr","socket service created");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new Socket("10.2.52.54",9000);
                    clientSocket.setKeepAlive(true);
                    clientSocket.setOOBInline(true);
                    clientSocket.setSoTimeout(60000); //超read时时间
                    Log.d("zyr","connect server success");
                    socketReadThread = new SocketReadThread(clientSocket);
                    socketReadThread.start();
                    socketWriteThread = new SocketWriteThread(clientSocket);
                    socketWriteThread.start();
                    registerReceiver(receiver,new IntentFilter(SEND_ACTION));
                } catch (Exception e) {
                    Log.d("zyr","connect server failed");
                    Log.d("zyr",e.toString());
                }
            }
        }).start();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d("zyr","socket service started");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("zyr","socket service destroy");
        unregisterReceiver(receiver);
    }


    public interface SocketListener{
        void onGetChatMsgSuccess(JSONObject chatMessage);
    }

    public void sendMsg(String chatMessage){
        socketWriteThread.sendMsg(chatMessage);
    }

    public void startHeartBeatTimerTask(){
        socketWriteThread.startHeartBeatTimerTask();
    }
    /**********************************************************************************************/

    public class SocketReadThread extends Thread {
        //定义当前线程所处理的Socket
        private Socket socket = null;
        private DataInputStream dataInputStream = null;
        private Timer heartBeatTimer;

        public SocketReadThread(Socket clientSocket){
            this.socket = clientSocket;
        }

        public void run() {

            while (socket.isConnected()){
                try {
                    Log.d("zyr","socket.isConnected() is true");
                    //读取服务端的消息
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    String serverMessage =  dataInputStream.readUTF();
                    Log.e("zyr","serverMsg:" + (serverMessage==null?"null":serverMessage) );
                    if(serverMessage!=null){
                        Intent intent = new Intent(RECEIVE_ACTION);
                        intent.putExtra("data",serverMessage);
                        sendBroadcast(intent);

                        JSONObject jsonObject = JSON.parseObject(serverMessage);
                        if(jsonObject.getIntValue("type") == MessageType.LOGIN
                                && jsonObject.getIntValue("status") == MessageType.STATUS_SUCCESS){
                            startHeartBeatTimerTask();
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
            socketWriteThread.cancelHeadrtBeat();

        }

    }

    public class SocketWriteThread extends Thread{
        private DataOutputStream dataOutputStream;
        private Socket socket;
        private Timer heartBeatTimer;

        public SocketWriteThread(Socket clientSocket){
            this.socket = clientSocket;
        }

        public void cancelHeadrtBeat() {
            heartBeatTimer.cancel();
        }

        @Override
        public void run() {
            super.run();

        }

        /**
         * 心跳
         */
        public void startHeartBeatTimerTask() {
            // 启动心跳线程
            heartBeatTimer = new Timer();
            TimerTask heartBeatTask = new TimerTask() {

                @Override
                public void run() {
                    try {
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        if(dataOutputStream!=null){
                            JSONObject jsonObject = new JSONObject();
                            dataOutputStream.writeUTF(jsonObject.toJSONString());
                            dataOutputStream.flush();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            heartBeatTimer.schedule(heartBeatTask, 10000, 10000);
        }

        public void sendMsg(String chatMessage){
            //给服务端发消息
            if(socket.isConnected()){
                try {
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF(chatMessage);
                    dataOutputStream.flush();

                    Log.e("zyr","clientMsg:" + chatMessage.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
