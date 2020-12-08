package org.hc.learning.thread.queue;

import org.junit.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueApplication {

    @Test
    public void 测试add顺序() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("1");
        queue.add("2");
        for (String s : queue) {
            System.out.println(s);
        }
    }

    @Test
    public void 测试put顺序() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.offer(1);
        queue.offer(2);
        for (Integer s : queue) {
            System.out.println(s);
        }
    }

}
