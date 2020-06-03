package org.hc.learning.thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class UseFuture implements Callable {

    private Integer ran = 0;

    @Override
    public Integer call() throws Exception {
        while (ran++ < 100) {
            System.out.println("useFuture : " + ran);
            Thread.sleep(100);
        }
        return ran;
    }

    public static void main(String[] args) throws Exception {
        UseFuture useFuture = new UseFuture();
        FutureTask futureTask = new FutureTask<>(useFuture);
        Thread thread = new Thread(futureTask);
        thread.start();
    }
}
