package org.hc.learning.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class UseAtomicInt {

    static AtomicInteger ai = new AtomicInteger(10);

    public static void main(String[] args) {
        // ai++
        ai.getAndIncrement();
        // ++ai
        ai.incrementAndGet();
        // ai = ai + 24
        ai.addAndGet(24);
        System.out.println(ai.get());
    }

}
