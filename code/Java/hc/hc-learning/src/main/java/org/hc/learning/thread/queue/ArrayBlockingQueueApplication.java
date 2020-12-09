package org.hc.learning.thread.queue;

import org.junit.Test;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayBlockingQueueApplication {

    private final static int CAPACITY = 10;

    @Test
    public void put() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(CAPACITY);
        for (int i = 0; i < 11; i++) {
            // 阻塞方法
            queue.put(i);
            System.out.println("阻塞队列存放元素["+ i +"]成功"); // 线程在插入第十一个元素时阻塞
        }
    }

    @Test
    public void add() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(CAPACITY);
        for (int i = 0; i < 11; i++) {
            queue.add(i);
            System.out.println("阻塞队列存放元素["+ i +"]成功"); // 线程在插入第十一个元素时抛出异常java.lang.IllegalStateException: Queue full
        }
    }

}
