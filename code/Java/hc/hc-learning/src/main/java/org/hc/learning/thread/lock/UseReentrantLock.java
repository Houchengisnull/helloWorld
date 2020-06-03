package org.hc.learning.thread.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

/**
 * 可重入锁
 */
public class UseReentrantLock {

    List arrayList = new ArrayList<Integer>();
    Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        UseReentrantLock useReentrantLock = new UseReentrantLock();
        /*new Thread(){
            public void run() {
                // useReentrantLock.insert();
                useReentrantLock.insertTryLock();
            };
        }.start();

        new Thread(){
            public void run() {
                // useReentrantLock.insert();
                useReentrantLock.insertTryLock();
            };
        }.start();*/
        UseReentrantLock lock = new UseReentrantLock();
        LockInterruptiblyThread thread1 = new LockInterruptiblyThread(lock);
        LockInterruptiblyThread thread2 = new LockInterruptiblyThread(lock);
        thread1.start();
        thread2.start();

        thread2.interrupt();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread1.getState());
        System.out.println(thread2.getState());
    }

    public void insert() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"得到了锁");
            for(int i=0;i<5;i++) {
                arrayList.add(i);
            }
        } catch (Exception e) {}finally {
            System.out.println(Thread.currentThread().getName()+"释放了锁");
            lock.unlock();
        }
    }

    public void insertTryLock() {
        Thread thread = Thread.currentThread();
        if(lock.tryLock()) {
            try {
                Thread.sleep(100); // 造成等待
                System.out.println(thread.getName()+"得到了锁");
                for(int i=0;i<5;i++) {
                    arrayList.add(i);
                }
            } catch (Exception e) {}finally {
                System.out.println(thread.getName()+"释放了锁; arrayList长度:" + arrayList.size());
                lock.unlock();
            }
        } else {
            System.out.println(thread.getName()+"获取锁失败");
        }
    }

    public void lockInterruptibly() throws InterruptedException {
        lock.lockInterruptibly();   //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
        Thread thread = Thread.currentThread();
        try {
            System.out.println(thread.getName()+"得到了锁");
            long startTime = System.currentTimeMillis();
            for(    ;     ;) {
                if(System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
                    break;
                //插入数据
            }
        }
        finally {
            System.out.println(Thread.currentThread().getName()+"执行finally");
            lock.unlock();
            System.out.println(thread.getName()+"释放了锁");
        }
    }

    static class LockInterruptiblyThread extends Thread {
        private UseReentrantLock useLock;

        LockInterruptiblyThread(UseReentrantLock useLock){
            this.useLock = useLock;
        }

        @Override
        public void run() {
            super.run();
            try {
                useLock.lockInterruptibly();
            } catch (InterruptedException e) {
                interrupt();
                System.out.println(Thread.currentThread().getName()+"被中断");
                /*e.printStackTrace();*/
            } finally {
                System.out.println(Thread.currentThread().getName() + "中断状态:" + Thread.currentThread().isInterrupted());
            }
        }
    }
}
