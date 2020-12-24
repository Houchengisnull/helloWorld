package org.hc.learning.thread.pool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hc.learning.test.TestCase;
import org.junit.Test;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduledCase extends TestCase {

    ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);

    /**
     * 仅执行一次
     */
    @SneakyThrows
    @Test
    public void 延时任务() {
        schedule.schedule(new PoolTestTask(1), 3, TimeUnit.SECONDS);
        Thread.sleep(4000);
    }

    /**
     * 初始延迟1s
     * 固定延迟3s
     * 固定延迟指 上一个任务结束时间到下一个任务的开始时间
     */
    @Test
    public void 固定延迟间隔执行() throws InterruptedException {
        schedule.scheduleWithFixedDelay(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.debug("****** start ******");
                Thread.sleep(3000);
                log.debug("****** end ******");
            }
        }, 1L, 2L, TimeUnit.SECONDS);
        Thread.sleep(10 * 1000);
    }

    /**
     * 由于时间间隔小于线程执行任务的时间
     * 所以上一个任务执行完毕后将立马执行下一个任务
     * @throws InterruptedException
     */
    @Test
    public void 固定时间间隔() throws InterruptedException {
        schedule.scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.debug("****** start ******");
                Thread.sleep(3000);
                log.debug("****** end ******");
            }
        }, 0, 2, TimeUnit.SECONDS );
        Thread.sleep(10 * 1000);
    }

    /**
     * 抛出异常未捕获 next周期将不会运行
     * @throws InterruptedException
     */
    @Test
    public void 固定时间间隔抛出异常() throws InterruptedException {
        schedule.scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.debug("****** start ******");
                Thread.sleep(3000);
                log.debug("抛出异常");
                int i = 1/0;
                log.debug("****** end ******");
            }
        }, 0, 3, TimeUnit.SECONDS );

        Thread.sleep(10 * 1000);
    }

    /**
     * 抛出异常被捕获 next周期将继续运行
     */
    @Test
    public void 固定时间间隔捕获异常() throws InterruptedException {
        schedule.scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.debug("****** start ******");
                Thread.sleep(3000);
                log.debug("抛出异常");
                try {
                    int i = 1/0;
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                } finally {
                    log.debug("****** end ******");
                }
            }
        }, 0, 3, TimeUnit.SECONDS );

        Thread.sleep(10 * 1000);    }

}
