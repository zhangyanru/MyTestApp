package com.example.myapp.Model;

/**
 * Created by yanru.zhang on 16/6/14.
 * Email:yanru.zhang@renren-inc.com
 */
public class ListData{
    public String message;
    public int type;
    public final static int SERVER_MSG = 0;
    public final static int CLIENT_MSG = 1;
    public final static int TIP_MSG = 2;

    public ListData(int type,String message){
        this.type = type;
        this.message = message;
    }
}
