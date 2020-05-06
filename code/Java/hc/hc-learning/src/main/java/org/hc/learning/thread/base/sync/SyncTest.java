package org.hc.learning.thread.base.sync;

/**
 * synchronized关键字
 */
public class SyncTest implements Runnable{

    static Integer i = 50;
    static long sleepTime = 5;
    @Override
    public void run() {
        sayHello();
        // staticSayHello();
    }

    public synchronized static void staticSayHello(){
        for (int j = 0; j < i; j++) {
            System.out.println(Thread.currentThread().getName() + j);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void sayHello(){
        for (int j = 0; j < i; j++) {
            System.out.println(Thread.currentThread().getName() + j);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new SyncTest());
        thread.setName("Thread --- A ---");
        Thread thread1 = new Thread(new SyncTest());
        thread1.setName("Thread === B ===");
        thread.start();
        thread1.start();
    }

}