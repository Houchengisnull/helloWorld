package org.hc.learning.thread.queue.boundebuffer;

import org.apache.commons.io.input.Tailer;
import org.hc.learning.test.TestCase;
import org.junit.Test;
import org.slf4j.*;
import java.time.LocalDateTime;
import java.util.concurrent.*;

public class BoundeBufferTest extends TestCase {
    private static final int DEFAULT_THREAD_NUM = 5;
    private static final String FINISH = "finish";
    private static int taskNum = 4096;
    private static ExecutorService productor = Executors.newFixedThreadPool(DEFAULT_THREAD_NUM);
    private static ExecutorService consumer = Executors.newFixedThreadPool(DEFAULT_THREAD_NUM);
    private static final Logger logger = LoggerFactory.getLogger(BoundeBufferTest.class);

    @Test
    public void SyncTest() throws InterruptedException, ExecutionException {
        IBoundeBuffer<Integer> queue = new SyncBufferImpl<>();
        Future<String>[] giveResult = new Future[DEFAULT_THREAD_NUM];
        Future<String>[] takeResult = new Future[DEFAULT_THREAD_NUM];
        /**
         * 生产线程
         */
        for (int i = 0; i < DEFAULT_THREAD_NUM; i++) {
            giveResult[i] = productor.submit(new GiveTask(queue));
        }
        /**
         * 消费线程
         */
        for (int i = 0; i < DEFAULT_THREAD_NUM; i++) {
            takeResult[i] = consumer.submit(new TakeTask(queue));
        }
        for (int i = 0; i < DEFAULT_THREAD_NUM; i++) {
            String result = giveResult[i].get();
            logger.debug("Give Task[{}] {}", i, result);
        }
        for (int i = 0; i < DEFAULT_THREAD_NUM; i++) {
            String result = takeResult[i].get();
            logger.debug("Take Task[{}] {}", i, result);
        }
    }

    static class GiveTask implements Callable<String> {

        IBoundeBuffer queue;

        GiveTask(IBoundeBuffer queue){
            this.queue = queue;
        }

        @Override
        public String call() throws Exception {
            for (int i = 0; i < taskNum; i++) {
                queue.put(i);
            }
            return FINISH + ":" + LocalDateTime.now().format(formatter);
        }
    }

    static class TakeTask implements Callable<String> {

        IBoundeBuffer queue;

        TakeTask(IBoundeBuffer queue){
            this.queue = queue;
        }

        @Override
        public String call() throws Exception {
            for (int i = 0; i < taskNum; i++) {
                queue.take();
            }
            return FINISH + ":" + LocalDateTime.now().format(formatter);
        }
    }
}
