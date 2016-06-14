package com.example.myapp.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.myapp.Model.ListData;
import com.example.myapp.Model.ChatMessage;
import com.example.myapp.Model.EmptyMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yanru.zhang on 16/6/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class ClientWriteThread extends Thread{
    private ObjectOutputStream objectOutputStream;
    private Socket socket;
    private boolean isConnect;
    private Handler handler;
    private Timer heartBeatTimer;

    public ClientWriteThread(Socket clientSocket,Handler handler){
        this.socket = clientSocket;
        this.handler = handler;
        isConnect = true;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
        if(!isConnect){
            heartBeatTimer.cancel();
        }
    }

    @Override
    public void run() {
        super.run();

    }

    public void sendMsg(String msg){
        //给服务端发消息
        if(isConnect){
            ChatMessage clientMessage = new ChatMessage();
            clientMessage.setChatType(ChatMessage.SINGLE_CHAT);
            try {
                clientMessage.setToAddress(InetAddress.getByAddress(new byte[]{10,2,(byte) 205,89}));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            clientMessage.setFromAddress(socket.getLocalAddress());
            clientMessage.setBody(msg);
            try {
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(clientMessage);
                objectOutputStream.flush();

                Log.d("zyr","clientMsg:" + clientMessage.toString());

                Message message2 = new Message();
                message2.what = ListData.CLIENT_MSG;
                Bundle bundle2 = new Bundle();
                bundle2.putString("clientMsg",clientMessage.getBody());
                message2.setData(bundle2);
                handler.sendMessage(message2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void getFriendList(){
        //给服务端发消息
        if(isConnect){
            ChatMessage clientMessage = new ChatMessage();
            clientMessage.setChatType(ChatMessage.FRIEND_LIST);
            try {
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(clientMessage);
                objectOutputStream.flush();
                Log.d("zyr","get friend list:" + clientMessage.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
                        objectOutputStream.writeObject(new EmptyMessage());
                        objectOutputStream.flush();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        heartBeatTimer.schedule(heartBeatTask, 5000, 5000);
    }
}
