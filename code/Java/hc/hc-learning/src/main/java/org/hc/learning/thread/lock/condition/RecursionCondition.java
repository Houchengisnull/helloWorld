package org.hc.learning.thread.lock.condition;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一次错误的Condition应用:
 * 在递归中使用Condition
 */
public class RecursionCondition {

    static Lock lock = new ReentrantLock();
    static Condition full = lock.newCondition();
    static Condition empty = lock.newCondition();
    volatile static int resource = 1;

    public static void main(String[] args) {
        new Thread(new GetResourceTask()).start();
    }

    static class GetResourceTask implements Runnable{

        @Override
        public void run() {
            getResource();
        }

        @SneakyThrows
        private void getResource() {
            lock.lock();
            if (resource-- > 0) {
                System.out.println("get resource, 剩余:" + resource);
                getResource();
            } else {
                empty.signal();
                full.await();
            }
            lock.unlock();
        }
    }

}
