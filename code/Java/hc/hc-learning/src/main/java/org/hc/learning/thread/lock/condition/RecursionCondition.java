package org.hc.learning.thread.lock.condition;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一次错误的Condition应用:
 * 在递归中使用Condition
 *
 * 这是一个实际应用的bug(不是我写的), 原项目使用commons-pool2来实现连接池
 * 后来重构之后有人动手自己实现了一个连接池。在一般情况下并不会出现bug，但是
 * 在线程重入时（方法递归调用），尽管当前线程持有锁，但执行到await时线程放弃
 * 锁并进入等待队列，一直阻塞导致程序不能正常运行。
 *
 * 虽然有其他地方调用signal唤醒，但是在单线程情况下还是会阻塞。
 * 如果线程够多也许不会如此，但是这是不合理的，线程开辟是需要消耗系统资源的。
 *
 * 在实际限流业务中我们应该用Semaphore, 它已经帮助我们实现了控制资源数量的模型
 * （等待通知模型）。但在递归调用时依然有可能出现这种问题。
 *
 */
public class RecursionCondition {

    static Lock lock = new ReentrantLock();
    static Condition full = lock.newCondition();
    static Condition empty = lock.newCondition();
    volatile static int resource = 1;

    public static void main(String[] args) {
        new Thread(new GetResourceTask()).start();
    }

    static class GetResourceTask implements Runnable{

        @Override
        public void run() {
            getResource();
        }

        @SneakyThrows
        private void getResource() {
            lock.lock();
            if (resource-- > 0) {
                System.out.println("get resource, 剩余:" + resource);
                getResource();
            } else {
                empty.signal();
                full.await();
            }
            lock.unlock();
        }
    }

}
