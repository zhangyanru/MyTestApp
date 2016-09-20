package javatest;

/**
 * Created by yanru.zhang on 16/8/10.
 * Email:yanru.zhang@renren-inc.com
 */
public class ChildBClass extends ParentAClass{

    public String show(ChildBClass bClass){
        return "B and B";
    }

    @Override
    public String show(ParentAClass aClass) {
        return "B and A";
    }
}
