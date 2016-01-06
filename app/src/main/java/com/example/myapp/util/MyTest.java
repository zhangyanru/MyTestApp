package com.example.myapp.util;

/**
 * Created by zyr
 * DATE: 15-12-24
 * Time: 下午7:39
 * Email: yanru.zhang@renren-inc.com
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *本类用于测试反射API，利用用户输入类的全路径，
 *找到该类所有的成员方法和成员属性
 */
public class MyTest {
    /**
     *构造方法
     */
    public MyTest(){
        String classInfo= "com.example.myapp.view.CustomViewGroup";//要求用户输入类的全路径
        try {
            Class cla=Class.forName(classInfo);//根据类的全路径进行类加载，返回该类的Class对象

            Method[] method=cla.getDeclaredMethods();//利用得到的Class对象的自审，返回方法对象集合

            for(Method me:method){//遍历该类方法的集合
                System.out.println(me.toString());//打印方法信息
            }

            System.out.println("********");

            Field[] field=cla.getDeclaredFields();//利用得到的Class对象的自审，返回属性对象集合
            for(Field me:field){ //遍历该类属性的集合
                System.out.println(me.toString());//打印属性信息
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
//        new MyTest();
        TestOne  one=null;
        try{
            Class  cla=Class.forName("com.example.myapp.util.TestOne");//进行com.TestOne类加载，返回一个Class对象
            System.out.println("********");
            one=(TestOne)cla.newInstance();//产生这个Class类对象的一个实例，调用该类无参的构造方法，作用等同于new TestOne()
        }catch(Exception e){
            e.printStackTrace();
        }
        TestOne two=new TestOne();
        System.out.println(one.getClass() == two.getClass());//比较两个TestOne对象的Class对象是否是同一个对象，在这里结果是true。说明如果两个对象的类型相同，那么它们会有相同的Class对象

    }
}

