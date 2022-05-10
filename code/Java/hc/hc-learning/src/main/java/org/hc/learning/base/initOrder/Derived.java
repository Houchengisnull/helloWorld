package org.hc.learning.base.initOrder;

public class Derived extends Base {

    public Derived() {
        System.out.println("派生类构造方法");
    }

    {
        System.out.println("派生类普通代码块-2");
    }

    private String value = InitOrderMain.log("派生类成员变量-1");

    static {
        System.out.println("派生类静态代码块-2");
    }

    static {
        System.out.println("派生类静态代码块-1");
    }

    private static String staticValue = InitOrderMain.log("派生类静态变量");



}
