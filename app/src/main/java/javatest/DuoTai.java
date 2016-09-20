package javatest;

/**
 * Created by yanru.zhang on 16/8/10.
 * Email:yanru.zhang@renren-inc.com
 */
public class DuoTai {



    public static void main(String args[]){

        ParentAClass a1 = new ParentAClass();//showA,showD
        ParentAClass a2 = new ChildBClass();//showA showD showB
        ChildBClass b = new ChildBClass();//showA showD showB
        ChildCClass c = new ChildCClass();
        ChildDClass d = new ChildDClass();
        System.out.println(a1.show(b));//A and A
        System.out.println(a1.show(c));//A and A
        System.out.println(a1.show(d));//A and D

        System.out.println(a2.show(b));//B and A ? 感觉该是B and B，原因:多态只能调用父类中有子类重写的方法(show(A)或者show(D))，这里调用的是B的show(A)方法
        System.out.println(a2.show(c));//B and A ? 感觉该是B and B,同上。要想调用子类独有的方法，需要强制把超类转换为子类
        System.out.println(a2.show(d));//A and D

        System.out.println(b.show(b));//B and B
        System.out.println(b.show(c));//B and B 调用的show(ChildBClass)
        System.out.println(b.show(d));//A and D show(ChildDClass)是父类的方法
    }

    //A a2 = new B();
    //栈中的引用变量是A，堆中的实例变量是B。
    //将子类的实例，赋值给父类的引用。就是向上转型。
    //向上转型，在运行时，会遗忘子类对象中与父类对象中不同的方法。也会覆盖与父类中相同的方法--重写。（方法名，参数都相同）
    //所以a2,可以调用的方法就是，A中有的，但是B中没有的方法，和B中的重写A的方法。
}
