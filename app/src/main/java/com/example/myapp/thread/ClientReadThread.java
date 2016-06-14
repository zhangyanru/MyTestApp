package com.example.myapp.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.myapp.Model.ListData;
import com.example.myapp.Model.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;

/**
 * Created by yanru.zhang on 16/6/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class ClientReadThread extends Thread {
    //定义当前线程所处理的Socket
    private Socket socket = null;
    private ObjectInputStream objectInputStream = null;
    private Timer heartBeatTimer;
    private boolean isConnect;
    private Handler handler;

    public ClientReadThread(Socket clientSocket,Handler handler){
        this.socket = clientSocket;
        this.handler = handler;
        isConnect = true;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public void run() {
        try {
            while (isConnect){

                Log.d("zyr","isConnect is true");
                //读取服务端的消息
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                ChatMessage serverMessage = (ChatMessage) objectInputStream.readObject();
                Log.e("zyr","serverMsg:" + serverMessage.toString());
                if( serverMessage != null && (serverMessage.getChatType() == ChatMessage.SINGLE_CHAT || serverMessage.getChatType() == ChatMessage.MUTIL_CHATE)){
                    Message message = new Message();
                    message.what = ListData.SERVER_MSG;
                    Bundle bundle = new Bundle();
                    bundle.putString("serverMsg",serverMessage.getBody());
                    message.setData(bundle);
                    handler.sendMessage(message);
                } else if(serverMessage!=null && serverMessage.getChatType() == ChatMessage.FRIEND_LIST){
                    Message message = new Message();
                    message.what = ListData.TIP_MSG;
                    Bundle bundle = new Bundle();
                    bundle.putString("tipMsg",serverMessage.getBody());
                    message.setData(bundle);
                    handler.sendMessage(message);
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

}
