package org.hc.learning.thread.题目.顺序执行;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
@Setter
@Getter
public class PrintTask implements Runnable{
    private int times = 30;
    public PrintTask(){}

    private BlockingQueue<String> tasks;

    @SneakyThrows
    @Override
    public void run() {
        while (times > 0) {
            synchronized (tasks) {
                String peek = tasks.peek();
                if (Thread.currentThread().getName().equals(peek)) {
                    String take = tasks.take();
                    System.out.print(Thread.currentThread().getName());
                    times--;
                } else {
                    Thread.sleep(10);
                }
            }
        }
    }
}
