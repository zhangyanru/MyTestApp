package com.example.myapp.util;

/**
 * Created by zyr
 * DATE: 15-12-24
 * Time: 下午7:45
 * Email: yanru.zhang@renren-inc.com
 */

import java.lang.reflect.Constructor;

/**
 *
 * 本类测试反射获得类的构造器对象，
 * 并通过类构造器对象生成该类的实例
 *
 */

public class ConstructorTest {
    public static void main(String[] args) {
        try {
            //获得指定字符串类对象
            Class cla=Class.forName("com.example.myapp.util.Tests");
            //设置Class对象数组，用于指定构造方法类型
            Class[] cl=new Class[]{int.class,int.class};

            //获得Constructor构造器对象。并指定构造方法类型
            Constructor con=cla.getConstructor(cl);

            //给传入参数赋初值
            Object[] x={new Integer(33),new Integer(67)};

            //得到实例
            Object obj=con.newInstance(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
