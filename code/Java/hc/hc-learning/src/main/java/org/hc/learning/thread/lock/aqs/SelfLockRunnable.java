package org.hc.learning.thread.lock.aqs;

public class SelfLockRunnable implements Runnable {
    SelfLock lock = new SelfLock();
    /*ReentrantLock lock = new ReentrantLock();*/
    int count = 0;

    @Override
    public void run() {
        lock.lock();
        for (int i = 0; i < 5; i++) {
            count++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(count);
        }
        lock.unlock();
    }

    public static void main(String[] args){
        SelfLockRunnable run = new SelfLockRunnable();
        for (int i = 0; i < 5; i++) {
            new Thread(run).start();
        }
    }
}
