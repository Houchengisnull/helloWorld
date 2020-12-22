package org.hc.learning.thread.pool;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoolTestTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TestMyThreadPool.class);
    private int taskSeq;

    public PoolTestTask(int taskSeq) {
        this.taskSeq = taskSeq;
    }

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(50);
        logger.debug("Task {} has been finished", taskSeq);
    }
}
