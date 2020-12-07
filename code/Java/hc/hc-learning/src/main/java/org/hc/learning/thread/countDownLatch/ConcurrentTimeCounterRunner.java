package org.hc.learning.thread.countDownLatch;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 随机耗时线程
 * 相当于我们的业务代码
 *
 * 2020.12.7 突然感觉通过集成CycleBarrier实现并发场景下的计时更为合理
 */
public class ConcurrentTimeCounterRunner implements Runnable{

    private CountDownLatch latch;

    public ConcurrentTimeCounterRunner(CountDownLatch latch) {
        this.latch = latch;
    }

    @SneakyThrows
    @Override
    public void run() {
        Random rand = new Random();
        int spend = rand.nextInt(10);
        TimeUnit.SECONDS.sleep(spend);
        System.out.println(Thread.currentThread().getName() + "完成任务, 耗时:" + spend + "秒");
        latch.countDown();
    }
}
