package org.hc.learning.thread.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 扩展线程池
 */
public class ThreadPoolExtend extends ThreadPoolExecutor {

    private final static Logger logger = LoggerFactory.getLogger(ThreadPoolExtend.class);

    public ThreadPoolExtend(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        logger.debug("before execute : {}", t.getName());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        logger.debug("after execute : {}", Thread.currentThread().getName());
    }

    @Override
    protected void terminated() {
        super.terminated();
        logger.debug("Thread pool terminate");
    }
}
