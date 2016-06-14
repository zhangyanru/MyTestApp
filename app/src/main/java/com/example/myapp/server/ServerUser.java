package com.example.myapp.server;

import java.net.Socket;

/**
 * Created by yanru.zhang on 16/6/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class ServerUser {
    public Socket socket;
    public int count;

    public ServerUser(Socket socket){
        this.socket = socket;
        this.count = 0;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(socket.getInetAddress().toString());
        return stringBuffer.toString();
    }
}
