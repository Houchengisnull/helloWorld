package org.hc.learning.thread.queue.boundebuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * 每次插入或者移除元素将唤醒所有线程
 * @param <T>
 */
public class SyncBufferImpl<T> implements IBoundBuffer<T>{
    private static final Logger logger = LoggerFactory.getLogger(SyncBufferImpl.class);
    private LinkedList<T> queue = new LinkedList<>();
    private int capacity = DEFAULT_CAPACITY;
    private final static int DEFAULT_CAPACITY = 10;

    public SyncBufferImpl() {
        this(DEFAULT_CAPACITY);
    }

    public SyncBufferImpl(int capacity){
        this.capacity = capacity;
    }

    @Override
    public synchronized void put(T element) throws InterruptedException {
        while (queue.size() >= capacity) {
            // 阻塞
            wait();
        }
        /*logger.debug("{} put {} into queue.", Thread.currentThread().getName(), element);*/
        queue.addLast(element);
        notifyAll();
    }

    @Override
    public synchronized T take() throws InterruptedException {
        while (queue.size() <= 0) {
            wait();
        }
        T element = queue.removeFirst(); // 应该先获取元素再唤醒
        /*logger.debug("{} take {} from queue.", Thread.currentThread().getName(), element);*/
        notifyAll();
        return element;
    }

}
