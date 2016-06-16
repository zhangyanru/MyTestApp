package com.example.myapp.chatdemo;

import android.os.Bundle;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by yanru.zhang on 16/6/13.
 * Email:yanru.zhang@renren-inc.com
 */
public class MessageType{
    public final static int SINGLE_CHAT = 0;
    public final static int MUTIL_CHATE = 1;
    public final static int FRIEND_LIST = 2;
    public final static int LOGIN = 3;
    public final static int REGISTER = 4;

    public final static int STATUS_SUCCESS = 0;
    public final static int STATUS_FAILED = -1;
}
