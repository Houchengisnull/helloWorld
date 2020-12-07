package org.hc.learning.thread.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *类说明：演示CountDownLatch用法，
 * 共5个初始化子线程，6个闭锁扣除点，扣除完毕后，主线程和业务线程才能继续执行
 */
public class UseCountDownLatch {

    static CountDownLatch latch = new CountDownLatch(6);

    /*初始化线程*/
    private static class InitThread implements Runnable{

        public void run() {
            System.out.println("Thread_"+Thread.currentThread().getId()
                    +" ready init work......");
            latch.countDown();
            for(int i =0;i<2;i++) {
                System.out.println("Thread_"+Thread.currentThread().getId()
                        +" ........continue do its work");
            }
        }
    }

    /*业务线程等待latch的计数器为0完成*/
    private static class BusiThread implements Runnable{

        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i =0;i<3;i++) {
                System.out.println("BusiThread_"+Thread.currentThread().getId()
                        +" do business-----");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread_"+Thread.currentThread().getId()
                        +" ready init work step 1st......");
                latch.countDown();
                System.out.println("begin step 2nd.......");
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread_"+Thread.currentThread().getId()
                        +" ready init work step 2nd......");
                latch.countDown();
            }
        }).start();
        new Thread(new BusiThread()).start();
        for(int i=0;i<=3;i++){
            Thread thread = new Thread(new InitThread());
            thread.start();
        }

        latch.await();
        System.out.println("Main do its work........");
    }
}
