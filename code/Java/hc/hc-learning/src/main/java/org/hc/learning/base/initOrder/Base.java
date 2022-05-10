package org.hc.learning.base.initOrder;

public class Base {

    static {
        System.out.println("基类静态代码块-1");
    }

    private static String staticValue = InitOrderMain.log("基类静态字段-1");

    static {
        System.out.println("基类静态代码块-2");
    }

    {
        System.out.println("基类普通代码块-1");
    }

    private String value = InitOrderMain.log("基类成员变量-1");

    {
        System.out.println("基类普通代码块-2");
    }

    public Base() {
        System.out.println("基类构造方法");
    }

}
