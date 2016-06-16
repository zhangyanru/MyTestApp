package com.example.myapp.chatdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

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
    }


    public interface SocketListener{
        void onGetChatMsgSuccess(JSONObject chatMessage);
    }

    private SocketListener socketListener;

    public void setSocketListener(SocketListener listener){
        this.socketListener = listener;
    }

    public void sendMsg(JSONObject chatMessage){
        socketWriteThread.sendMsg(chatMessage);
    }

    public void startHeartBeatTimerTask(){
        socketWriteThread.startHeartBeatTimerTask();
    }
    /**********************************************************************************************/

    public class SocketReadThread extends Thread {
        //定义当前线程所处理的Socket
        private Socket socket = null;
        private ObjectInputStream objectInputStream = null;
        private Timer heartBeatTimer;

        public SocketReadThread(Socket clientSocket){
            this.socket = clientSocket;
        }

        public void run() {

            while (socket.isConnected()){
                try {
                    Log.d("zyr","socket.isConnected() is true");
                    //读取服务端的消息
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    JSONObject serverMessage = (JSONObject) objectInputStream.readObject();
                    Log.e("zyr","serverMsg:" + (serverMessage==null?"null":serverMessage.toString()) );
                    if(serverMessage!=null){
                        socketListener.onGetChatMsgSuccess(serverMessage);
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
        private ObjectOutputStream objectOutputStream;
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
                        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        if(objectOutputStream!=null){
                            objectOutputStream.writeObject(new JSONObject());
                            objectOutputStream.flush();
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

        public void sendMsg(JSONObject chatMessage){
            //给服务端发消息
            if(socket.isConnected()){
                try {
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(chatMessage);
                    objectOutputStream.flush();

                    Log.d("zyr","clientMsg:" + chatMessage.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
