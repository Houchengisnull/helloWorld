package org.hc.learning.thread.exception;

import lombok.SneakyThrows;

public class ThreadException {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(100);
                    System.out.println(1/0);
                }
            }
        });
        thread.start();

        Thread oomThread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new OutOfMemoryError();
            }
        });

        oomThread.start();

        System.out.println("------- 启动完毕 ------");
        System.out.println(oomThread.isAlive());
        System.out.println(thread.isAlive());
        Thread.sleep(1000);
        System.out.println("------- 休眠1s ------");
        System.out.println(oomThread.isAlive());
        System.out.println(thread.isAlive());
    }

}
