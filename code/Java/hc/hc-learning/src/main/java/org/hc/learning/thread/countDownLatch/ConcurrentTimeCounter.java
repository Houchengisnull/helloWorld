package org.hc.learning.thread.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并发场景下多线程计时器
 */
public class ConcurrentTimeCounter {

    private static final int DEFAULT_THREADS = 5;
    private static final CountDownLatch latch = new CountDownLatch(DEFAULT_THREADS);
    private static final long last = System.currentTimeMillis();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(DEFAULT_THREADS);
        Runnable task = new ConcurrentTimeCounterRunner(latch);
        for (int i = 0; i < DEFAULT_THREADS; i++) {
            service.execute(task);
        }
        latch.await();
        System.out.println("共耗时:" + ((System.currentTimeMillis() - last)/1000) + "秒");
        service.shutdownNow();
    }

}
