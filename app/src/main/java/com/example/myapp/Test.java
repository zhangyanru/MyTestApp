package com.example.myapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanru.zhang on 16/7/15.
 * Email:yanru.zhang@renren-inc.com
 */
public class Test implements Serializable {
    public String name = "test";
    public String city = "city";

    public List<NormalInterClass> list = new ArrayList<NormalInterClass>();
    public StaticInnerClass staticInnerClass;
//    序列化会忽略静态变量，即序列化不保存静态变量的状态
    public static String age = "100";

    public Test(String name,String city,String age){
        for(int i=0;i<5;i++){
            list.add(new NormalInterClass("class" + i));
        }
        staticInnerClass = new StaticInnerClass("staticInnerClass");
        this.name = name;
        this.city = city;
        this.age = age;
    }

    //非静态内部类一定要序列化，不然报错
    public class NormalInterClass implements Serializable{
        private String name;

        public NormalInterClass(String s){
            name = s;
        }

        @Override
        public String toString() {
            return "NormalInterClass name:" + name;
        }
    }
    //静态内部类一定要序列化，不然报错
    public static class StaticInnerClass implements Serializable{
        private String name;

        public StaticInnerClass(String s){
            name = s;
        }

        @Override
        public String toString() {
            return "StaticInnerClass name:" + name;
        }
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\nname:" + name);
        stringBuffer.append("\ncity:" + city);
        stringBuffer.append("\nage:" + age);
        stringBuffer.append("\nnormal inter class list:" + list.toString());
        if(staticInnerClass!=null){
            stringBuffer.append("\nstatic inner class:" + staticInnerClass.toString());
        }
        return stringBuffer.toString();
    }
}
