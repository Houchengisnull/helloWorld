package org.hc.learning.thread.pool;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;

public class MyThreadPool implements Executor {

    private static final Logger logger = LoggerFactory.getLogger(MyThreadPool.class);

    public static final int DEFAULT_TASK_NUM = 16;
    public static final int DEFAULT_THREAD_NUM = 8;
    /**
     * 保存任务
     */
    private BlockingQueue<Runnable> taskQueue;
    /**
     * 保存线程
     */
    private MyThread[] threads;

    public MyThreadPool() {
        this(DEFAULT_TASK_NUM, DEFAULT_THREAD_NUM);
    }

    public MyThreadPool(int taskNum, int threadNum){
        this.taskQueue = new LinkedBlockingDeque<>(taskNum);
        this.threads = new MyThread[threadNum];
        // 初始化所有线程并且启动
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread();
            threads[i].start();
            logger.debug("my pool {} start", threads[i].getName());
        }
    }

    /**
     * 该方法仅用于将任务保存到任务队列，待线程处理
     * @param command
     */
    @SneakyThrows
    @Override
    public void execute(Runnable command) {
        taskQueue.put(command);
    }

    public void destroy() {
        logger.debug("ready destroy pool");
        for (int i = 0; i < threads.length; i++) {
            threads[i].interrupt();
            threads[i] = null; // help gc
        }
        taskQueue.clear();
    }

    /**
     * 为控制线程的生命周期
     * 我们需要重写Thread
     */
    class MyThread extends Thread {
        /**
         * 阻塞地获取任务
         */
        @SneakyThrows
        @Override
        public void run() {
            while (!isInterrupted()) {
                Runnable task = taskQueue.take();
                if (task != null) {
                    logger.debug("my pool {} run", Thread.currentThread().getName());
                    task.run();
                }
                task = null;
            }
        }
    }
}
