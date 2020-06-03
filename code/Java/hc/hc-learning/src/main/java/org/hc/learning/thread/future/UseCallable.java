package org.hc.learning.thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class UseCallable implements Callable<Integer> {

    private Integer var = 1024;

    @Override
    public Integer call() throws Exception {
        Thread.interrupted();
        System.out.println("Thread.interrupted:" + Thread.interrupted());
        return var;
    }

    public static void main(String[] args) throws Exception {
        UseCallable useCallable = new UseCallable();
        FutureTask<Integer> integerFutureTask = new FutureTask<>(useCallable);
        Thread thread = new Thread(integerFutureTask);
        thread.start();

        // System.out.println(useCallable.call());
        System.out.println(integerFutureTask.get());
        System.out.println(integerFutureTask.isDone());
        System.out.println(integerFutureTask.isCancelled());
        System.out.println(thread.isInterrupted());
    }
}
