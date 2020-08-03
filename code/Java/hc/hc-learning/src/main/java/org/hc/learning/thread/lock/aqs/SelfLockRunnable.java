package org.hc.learning.thread.lock.aqs;

public class SelfLockRunnable implements Runnable {
    SelfLock lock = new SelfLock();
    /*ReentrantLock lock = new ReentrantLock();*/
    int count = 0;

    @Override
    public void run() {
        forEachOut();
        // System.out.println(count(5));
    }

    /**
     * 通过该方法测试SelfLock是否现场安全
     * 可通过ReentrantLock作为参照
     */
    public void forEachOut() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            count++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(count);
            lock.unlock();
        }
    }

    /**
     * 通过该方法测试SelfLock不可重入
     * @param i
     * @return
     */
    public int count(int i) {
        lock.lock();
        if (i > 0) {
            i = i + count(--i);
        }
        lock.unlock();
        return i;
    }

    public static void main(String[] args){
        SelfLockRunnable run = new SelfLockRunnable();
        for (int i = 0; i < 1; i++) {
            new Thread(run).start();
        }
    }
}
