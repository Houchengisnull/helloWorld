package org.hc.learning.thread.queue.boundebuffer;

public interface IBoundeBuffer<T> {
    /**
     * 阻塞存放元素到队列尾部
     * @param element
     * @throws InterruptedException
     */
    void put(T element) throws InterruptedException;

    /**
     * 阻塞获取元素
     * @return
     * @throws InterruptedException
     */
    T take() throws InterruptedException;

}
