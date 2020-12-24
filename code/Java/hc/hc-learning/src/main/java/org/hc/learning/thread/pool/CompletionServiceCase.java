package org.hc.learning.thread.pool;

import lombok.extern.slf4j.Slf4j;
import org.hc.learning.test.TestCase;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CompletionServiceCase extends TestCase {

    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final int TOTAL_TASK = Runtime.getRuntime().availableProcessors() * 10;
    private final long sleepTime = 100L;
    private static AtomicInteger count = new AtomicInteger(0);

    @Test
    public void 对比() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        List<Future<Integer>> tasks = new LinkedList<>();
        for (int i = 0; i < TOTAL_TASK; i++) {
            Future<Integer> submit = pool.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(sleepTime);
                    return count.addAndGet(1);
                }
            });
            tasks.add(submit);
        }

        for (int i = 0; i < tasks.size(); i++) {
            Integer integer = tasks.get(i).get();
            log.debug("Task {} execute end", integer);
        }
    }


    /**
     * 先完成的任务先进入队列
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void completeService() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        ExecutorCompletionService<Integer> service = new ExecutorCompletionService<>(pool);

        for (int i = 0; i < TOTAL_TASK; i++) {
            service.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(sleepTime);
                    return count.addAndGet(1);
                }
            });
        }

        for (int i = 0; i < TOTAL_TASK; i++) {
            Integer integer = service.take().get();
            log.debug("Task {} execute end", integer);
        }
    }

}
