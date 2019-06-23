package org.hc.learning.thread.threadlocal;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalOOM {

    private static final int TASK_LOOP_SIZE = 500;
    final static ThreadPoolExecutor executor
            = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue());

    static class LocalVariable {
        private byte[] a = new byte[1024*1024*5];/*5M大小的数组*/
    }

    final static ThreadLocal<LocalVariable> localVariable = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        for (int i = 0; i < TASK_LOOP_SIZE; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    new LocalVariable();
                    System.out.println("use local var");
                    // localVariable.remove();
                }
            });

            Thread.sleep(100);
        }
        System.out.println("pool execute over");
    }
}
