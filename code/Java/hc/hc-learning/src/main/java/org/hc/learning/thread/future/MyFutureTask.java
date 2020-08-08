package org.hc.learning.thread.future;

import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 个人以为在此处强行使用AQS略显奇怪
 * 包括在class FutureTask中也未曾使用AQS
 * 这主要是以为我们使用单线程计算Callable.call()
 * 并未类似ForkJoin中使用并发线程计算然后汇总
 * @param <V>
 */
public class MyFutureTask<V> implements Future<V>, Runnable {

    public MyFutureTask(Callable<V> callable) {
        if (callable == null) {
            throw new NullPointerException();
        }
        sync = new Sync(callable);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return this.sync.innerGet();
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.sync.innerGet();
    }

    @Override
    public void run() {
        this.sync.innerRun();
    }

    private final Sync sync;

    class Sync extends AbstractQueuedSynchronizer {

        private static final int RAN = 2;
        private static final int RUNNING = 1;
        /**
         * 执行结果
         */
        private V result;
        private Callable<V> callable;

        Sync(Callable<V> callable){
            super();
            this.callable = callable;
        }

        void innerSet(V v) {
            for (;;) {
                int s = getState(); // 获取任务执行状态。
                if (s == RAN)
                    return; // 如果任务已经执行完毕，退出。
                // 尝试将任务状态设置为执行完成。
                if (compareAndSetState(s, RAN)) {
                    result = v; // 设置执行结果。
                    releaseShared(0); // 释放控制权。
                    return;
                }
            }
        }

        void innerRun() {
            if (this.compareAndSetState(0, RUNNING)) {
                if (this.getState() == RUNNING) {//再检查一次，双重保障
                    try {
                        /*将call()方法的执行结果赋值给Sync中的result*/
                        this.innerSet(this.callable.call());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    /*如果不等于RUNNING，表示被取消或者是抛出了异常。这时候唤醒调用get的线程。*/
                    this.releaseShared(0);
                }
            }
        }

        V innerGet() {
            acquireShared(0);
            return result; // 成功执行完成，返回执行结果。
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return this.getState()==RAN ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            return true;
        }
    }

}
