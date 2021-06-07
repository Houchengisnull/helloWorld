package org.hc.learning.thread.jstack;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class DeadLockTask implements Runnable{

    private static final ReentrantLock lockOne = new ReentrantLock();
    private static final ReentrantLock lockTwo = new ReentrantLock();

    @SneakyThrows
    @Override
    public void run() {
        int count = 1;
        if (lockOne.tryLock()) {
            Thread.sleep(1000);
        }
        if (lockTwo.tryLock()) {
            Thread.sleep(1000);
        }
        lockOne.lock();
        lockTwo.lock();
        log.info("get 2 locks");
        lockTwo.unlock();
        lockOne.unlock();
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(new DeadLockTask());
        service.execute(new DeadLockTask());
    }
}
