package org.hc.learning.thread.base;

public class UseJoin {
    /**
     * Join将使线程执行有序
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            }, i + "");
            thread.start();
            thread.join();
        }
    }

}
