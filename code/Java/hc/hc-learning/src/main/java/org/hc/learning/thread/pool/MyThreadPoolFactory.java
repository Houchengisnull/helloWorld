package org.hc.learning.thread.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadPoolFactory  implements ThreadFactory {

    private final Logger logger = LoggerFactory.getLogger(MyThreadPoolFactory.class);
    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "PDP - SIX - " + count.addAndGet(1));
        thread.setDaemon(true); // 设置为守护线程
        logger.debug(thread.getName());
        return thread;
    }
}
