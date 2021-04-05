package org.hc.learning.thread.safe;

/**
 * 饿汉式
 */
public class SingleEhan {

    private static SingleEhan instance = new SingleEhan();

    public static SingleEhan getInstance() {
        return instance;
    }

}
