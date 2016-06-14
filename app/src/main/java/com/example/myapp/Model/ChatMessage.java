package com.example.myapp.Model;

import org.w3c.dom.ProcessingInstruction;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by yanru.zhang on 16/6/13.
 * Email:yanru.zhang@renren-inc.com
 */
public class ChatMessage implements Serializable{
    private InetAddress toAddress;
    private InetAddress fromAddress;
    private String body;
    private int chatType; // 0-单聊，1-群聊，2-好友列表
    public final static int SINGLE_CHAT = 0;
    public final static int MUTIL_CHATE = 1;
    public final static int FRIEND_LIST = 2;

    private static final long serialVersionUID = 7415955052712095370l;

    public void setBody(String s){
        body = s;
    }
    public void setToAddress(InetAddress s){
        toAddress = s;
    }

    public void setChatType(int c){
        chatType = c;
    }

    public InetAddress getToAddress() {
        return toAddress;
    }

    public InetAddress getFromAddress() {
        return fromAddress;
    }

    public String getBody() {
        return body;
    }

    public int getChatType() {
        return chatType;
    }

    public void setFromAddress(InetAddress i){
        fromAddress = i;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" chatType : " + (chatType == 0 ? "单聊" : "群聊"));
        buffer.append(" toAddress : " + (toAddress== null ? "null" : toAddress.toString()) );
        buffer.append(" body : " + body);
        return buffer.toString();
    }
}
