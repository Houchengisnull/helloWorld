package org.hc.learning.thread.queue;

import org.junit.Test;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueApplication {

    private static final int CAPACITY = 10;

    @Test
    public void a() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue(CAPACITY, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        queue.add("hello");
        queue.add("java");
        queue.add("C++");
        System.out.println(queue.peek()); // 按照FIFO原则应该打印Hello, 但由于我们重写了Comparator其内部将根据我们的比较器进行排序，最终打印C++
    }

}
