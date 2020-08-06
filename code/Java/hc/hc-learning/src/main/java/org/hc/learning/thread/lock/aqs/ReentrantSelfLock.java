package org.hc.learning.thread.lock.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ReentrantSelfLock implements Lock {

    private Sync sync = new Sync();

    static class Sync extends AbstractQueuedSynchronizer {

        @Override
        public boolean tryAcquire(int arg) {
            Thread current = Thread.currentThread();
            if (compareAndSetState(0, 1)) { // 线程首次获取锁
                setExclusiveOwnerThread(current);
                return true;
                /**
                 * 可重入锁获取时 需要添加判断当前持有锁的线程是否为当前线程, 如果是则对state进行累加
                 */
            } else if (current == getExclusiveOwnerThread()) {
                setState(getState() + 1);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (Thread.currentThread() != getExclusiveOwnerThread()) {
                throw new IllegalMonitorStateException();
            }
            if (getState() == 0)
                throw new IllegalMonitorStateException();
            /**
             * 不论时JDK亦或时Mark老师都是直接-1
             * 这是因为此时只有一个线程可以进行release()
             */
            setState(getState() - 1);
            /**
             * 当可重入锁中state为0则释放锁资源
             */
            if (getState() == 0) {
                setExclusiveOwnerThread(null);
                return true;
            }
            // 返回false不会唤醒其他线程 提高效率
            return false;
        }

        protected final boolean isHeldExclusively() {
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        // 返回一个Condition，每个condition都包含了一个condition队列
        ConditionObject newCondition() {
            return new ConditionObject();
        }
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, time);
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

}
