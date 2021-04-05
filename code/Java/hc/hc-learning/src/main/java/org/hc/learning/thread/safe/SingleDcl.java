package org.hc.learning.thread.safe;

/**
 * 懒汉式-双重检查锁定:
 * 一种线程不安全的单例模式
 */
public class SingleDcl {

    private static /*volatile*/ SingleDcl singleDcl;

    private SingleDcl() {}

    public static SingleDcl getInstance() {
        if (singleDcl == null) {
            synchronized (SingleDcl.class) {
                if (singleDcl == null) {
                    singleDcl = new SingleDcl();
                }
            }
        }
        return singleDcl;
    }
}
