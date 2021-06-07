package org.hc.learning.thread.jstack;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class NormalTask implements Runnable {

    @SneakyThrows
    @Override
    public void run() {
        int count = 0;
        while (true) {
            log.debug("第{}次执行任务", ++count);
            int sum = 0;
            for (int i = 0; i < 999999999; i++) {
                sum += i;
            }
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) {
        /*Thread thread = new Thread(new NormalTask());
        thread.start();*/
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(new NormalTask());
    }
}
