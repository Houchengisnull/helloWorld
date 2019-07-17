package org.hc.learning.thread.forkjoin.learning.sum;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class SumTest {
    /**
     * 单线程累加
     */
    @Test
    public void singleSum () {
        int count = 0;
        Integer[] src = MakeArray.makeArray();

        long start = System.currentTimeMillis();
        for (int i : src) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count += i;
        }
        System.out.println("The count is " + count);
        System.out.println("spend time:" + (System.currentTimeMillis()-start) + "ms");
    }

    @Test
    public void forkJoinSum () {
        ForkJoinPool pool = new ForkJoinPool();
        Integer[] src = MakeArray.makeArray();
        SumTask innerFind = new SumTask(src, 0, src.length - 1);

        long start = System.currentTimeMillis();

        // 同步
        pool.invoke(innerFind);
        System.out.println("The count is " + innerFind.join());
        System.out.println("spend time:" + (System.currentTimeMillis() - start) + "ms");
    }
}
