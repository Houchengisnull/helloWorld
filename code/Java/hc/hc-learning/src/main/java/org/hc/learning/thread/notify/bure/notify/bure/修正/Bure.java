package org.hc.learning.thread.notify.bure.notify.bure.修正;

/**
 * 枪膛
 * 20190630
 * 原来的写法是耦合度较高的，依赖线程本身为射击线程或上膛线程
 * 而“射击”与“上膛”本该为枪膛的某种动作，
 * 不用关心是哪一个线程执行了射击或上膛操作
 * 而是任何*使用枪膛从而进行射击或上膛操作*的**线程**都要遵循**枪膛有子弹才能射击规则**
 *
 * 即任意线程都能使用枪膛进行射击或上膛
 * 而不需要自己动手写射击或上膛的方法，将这两个过程封装在实体中
 */
public class Bure {
    /**
     * 最大子弹容量
     */
    public static int MAX_BULLET_CAPACITY = 20;
    private Integer bulletCapacity = 0;
    public Integer getBulletCapacity() {
        return bulletCapacity;
    }

    public void setBulletCapacity(Integer bulletCapacity) {
        this.bulletCapacity = bulletCapacity;
    }

    /**
     * 射击
     */
    public synchronized void pop() {
        while (bulletCapacity <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        bulletCapacity -= 1;
        System.out.println(Thread.currentThread().getName() + "射击, 枪膛当前子弹容量: " + bulletCapacity);
        notifyAll();
    }

    /**
     * 上膛
     */
    public synchronized void push() {
        while(bulletCapacity >= MAX_BULLET_CAPACITY) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 业务逻辑
        bulletCapacity += 1;
        System.out.println(Thread.currentThread().getName() + "上膛, 枪膛当前子弹容量: " + bulletCapacity);
        notifyAll();
    }

}
