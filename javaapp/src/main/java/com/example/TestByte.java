package com.example;

/**
 * Created by zhangyanru on 2017/3/2.
 * 功能描述：
 */

public class TestByte {

    public static void main(String[] args){
        String s = "fs123fdsa";//String变量

        byte b[] = s.getBytes();//String转换为byte[]

        String t = new String(b);//bytep[]转换为String

        System.out.println(t);
    }
}