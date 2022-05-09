package org.hc.learning.extend.initOrder;

/**
 * Java类初始化顺序
 *
 * 类加载阶段：
 *  基类静态代码块与静态字段
 *  派生类静态代码块与静态字段
 * 类初始化:
 *  （类初始化指的是new）
 *   比如在派生类中使用super关键字, 那么就一定要求基类已初始化完成
 *   所以基类初始化在派生类之前
 */
public class InitOrderMain {

    public static String log(String log) {
        System.out.println(log);
        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("----------------- 类加载 -----------------");
        Class.forName(Derived.class.getName());
        System.out.println("----------------- 实例化1 -----------------");
        new Derived();
        System.out.println("----------------- 实例化2 -----------------");
        new Derived();
    }
}
