package org.hc.learning.thread.queue.boundebuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 需要注意在调用signal后
 * 下一个获得锁的线程不一定时等待队列上的线程
 * 也有可能时同步队列上的线程
 *
 * 对比一下 SyncBufferImpl, ConditionBufferImpl发生CPU上下文切换更多
 *
 * @param <T>
 */
public class ConditionBufferImpl<T> implements IBoundBuffer<T> {
    private static final Logger logger = LoggerFactory.getLogger(SyncBufferImpl.class);
    private LinkedList<T> queue = new LinkedList<>();
    private int capacity = DEFAULT_CAPACITY;
    private final static int DEFAULT_CAPACITY = 6;

    private ReentrantLock lock = new ReentrantLock();
    /**
     * 生产者Condition
     */
    private Condition putCondition = lock.newCondition();
    /**
     * 消费者Condition
     */
    private Condition takeCondition = lock.newCondition();

    @Override
    public void put(T element) throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        lock.lock();
        /**
         * 在put方法中使用if
         * 可能导致queue中数量超出限制capacity的逻辑限制
         */
        while (queue.size() >= capacity) {
            /*logger.debug("{} 进入Put等待队列", threadName);
            takeCondition.signal();*/ // 唤醒take等待队列上的一个线程
            putCondition.await(); // 当前线程进入阻塞
            /*logger.debug("{} 被唤醒 执行Put", threadName);*/
        }
        /*logger.debug("{} 通知 Take", threadName);*/
        takeCondition.signal(); // 唤醒take等待队列上的一个线程
        queue.addFirst(element);
        /*logger.debug("{} put {} into queue. 剩余元素:{}", Thread.currentThread().getName(), element, queue.size());*/
        lock.unlock();
    }

    @Override
    public T take() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        lock.lock();
        /**
         * 在take()方法中使用if可能导致线程中断
         * 因为在调用removeLast()方法时, 队列中可能已经没有元素了
         * 这将抛出NoSuchElementException异常
         */
        while (queue.size() <= 0) {
          /*  logger.debug("{} 进入Take等待队列", threadName);*/
            putCondition.signal();
            takeCondition.await();
            /*logger.debug("{} 被唤醒, 执行Take Task", threadName);*/
        }
        putCondition.signal();
        /*logger.debug("{} 通知 Put队列", threadName);*/
        T element = queue.removeLast();
        /*logger.debug("{} take {} from queue. 剩余元素:{}", Thread.currentThread().getName(), element, queue.size());*/
        lock.unlock();
        return element;
    }
}
