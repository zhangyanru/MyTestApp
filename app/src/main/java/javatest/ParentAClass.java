package javatest;

/**
 * Created by yanru.zhang on 16/8/10.
 * Email:yanru.zhang@renren-inc.com
 */
public class ParentAClass{

    public String show(ChildDClass dClass){
        return "A and D";
    }

    public String show(ParentAClass aClass){
        return "A and A";
    }

}
