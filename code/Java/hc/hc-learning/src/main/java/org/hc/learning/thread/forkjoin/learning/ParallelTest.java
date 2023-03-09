package org.hc.learning.thread.forkjoin.learning;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hc.learning.test.TestCase;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ParallelTest extends TestCase {

    private static final int max = 150;
    private static final long cost = 1000;

    private static final List<Runnable> records = new ArrayList(){
        {
            for (int i = 0; i < max; i++) {
                add(new Runnable() {
                    @Override
                    public void run() {
                        log.info(Thread.currentThread().getName());
                        try {
                            Thread.sleep(cost);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    };



    @Test
    public void parallelTest(){
        /*int commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism();
        System.out.println(commonPoolParallelism);
        System.out.println(Runtime.getRuntime().availableProcessors());*/
        records.parallelStream().forEach(e -> {
            e.run();
        });
    }

    @SneakyThrows
    @Test
    public void fixPoolTest(){
        ExecutorService pool = Executors.newFixedThreadPool(100);
        List<CallTask> tasks = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            tasks.add(new CallTask());
        }
        pool.invokeAll(tasks);

    }


    static class CallTask implements Callable<String>{

        @Override
        public String call() throws Exception {
            String name = Thread.currentThread().getName();
            Thread.sleep(cost);
            log.info(name);
            return name;
        }

    }
}


