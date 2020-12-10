package org.hc.learning.thread.queue.boundebuffer;

import org.hc.learning.test.TestCase;
import org.junit.Test;
import org.slf4j.*;
import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * 测试结果
 * JDK胜出
 */
public class BoundBufferTest extends TestCase {
    private static final int DEFAULT_THREAD_NUM = 4;
    private static final String FINISH = "finish";
    private static int taskNum = 20000;
    private static ExecutorService producer = Executors.newFixedThreadPool(DEFAULT_THREAD_NUM);
    private static ExecutorService consumer = Executors.newFixedThreadPool(DEFAULT_THREAD_NUM);
    private static final Logger logger = LoggerFactory.getLogger(BoundBufferTest.class);

    @Test
    public void SyncTest() throws InterruptedException, ExecutionException {
        IBoundBuffer<Integer> queue = new SyncBufferImpl<>();
        Future<String>[] giveResult = new Future[DEFAULT_THREAD_NUM];
        Future<String>[] takeResult = new Future[DEFAULT_THREAD_NUM];
        /**
         * 生产线程
         */
        for (int i = 0; i < DEFAULT_THREAD_NUM; i++) {
            giveResult[i] = producer.submit(new GiveTask(queue));
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

    @Test
    public void ConditionTest() throws ExecutionException, InterruptedException {
        IBoundBuffer<Integer> queue = new ConditionBufferImpl<>();
        Future<String>[] giveResult = new Future[DEFAULT_THREAD_NUM];
        Future<String>[] takeResult = new Future[DEFAULT_THREAD_NUM];
        /**
         * 生产线程
         */
        for (int i = 0; i < DEFAULT_THREAD_NUM; i++) {
            giveResult[i] = producer.submit(new GiveTask(queue));
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

    @Test
    public void JDKBlockingQueueTest() throws ExecutionException, InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(6);
        Future<String>[] giveResult = new Future[DEFAULT_THREAD_NUM];
        Future<String>[] takeResult = new Future[DEFAULT_THREAD_NUM];
        /**
         * 生产线程
         */
        for (int i = 0; i < DEFAULT_THREAD_NUM; i++) {
            giveResult[i] = producer.submit(new JDKGiveTask(queue));
        }
        /**
         * 消费线程
         */
        for (int i = 0; i < DEFAULT_THREAD_NUM; i++) {
            takeResult[i] = consumer.submit(new JDKTakeTask(queue));
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

        IBoundBuffer queue;

        GiveTask(IBoundBuffer queue){
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

    static class JDKGiveTask implements Callable<String> {

        BlockingQueue queue;

        JDKGiveTask(BlockingQueue queue){
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

        IBoundBuffer queue;

        TakeTask(IBoundBuffer queue){
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

    static class JDKTakeTask implements Callable<String> {

        BlockingQueue queue;

        JDKTakeTask(BlockingQueue queue){
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
