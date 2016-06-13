package com.example.myapp.util;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by yanru.zhang on 16/6/13.
 * Email:yanru.zhang@renren-inc.com
 */
public class ChatMessage implements Serializable{
    private InetAddress toAddress;
    private String body;

    public void setBody(String s){
        body = s;
    }
    public void setToAddress(InetAddress s){
        toAddress = s;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("toAddress : " + toAddress);
        buffer.append("body : " + body);
        return buffer.toString();
    }
}
