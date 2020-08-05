package org.hc.learning.thread.lock.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Semaphore
 * 具在同一时刻，只允许至多 3 个线程同时访问，超 过 3 个线程的访问将被阻塞。
 */
public class TrinityLock implements Lock {

    Sync sync = new Sync(3);

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, time);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    static class Sync extends AbstractQueuedSynchronizer {
        final ConditionObject newCondition() {
            return new ConditionObject();
        }

        Sync(int count) {
            if (count <= 0)
                throw new IllegalArgumentException("count must large than zero.");
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            for (;;) {
                int state = getState();
                int expect = state - arg;
                if (expect < 0 || compareAndSetState(state, expect)) {
                    if (expect < 0) {
                        System.out.println(Thread.currentThread().getId() + " get lock failed");
                    }
                    return expect;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;) {
                int state = getState();
                int expect = state + arg;
                if (compareAndSetState(state, expect)){
                    return true;
                }
            }
        }
    }

    public static void main(String[] args) {
        TrinityLock trinityLock = new TrinityLock();
        for (int i = 0; i < 4; i++) {
            new Thread(new TrinityLockRunnable(trinityLock)).start();
        }
    }

    static class TrinityLockRunnable implements Runnable {
        Lock lock;

        TrinityLockRunnable(Lock lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getId() + " get TrinityLock");
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                /*lock.unlock();*/
            }
        }
    }
}
