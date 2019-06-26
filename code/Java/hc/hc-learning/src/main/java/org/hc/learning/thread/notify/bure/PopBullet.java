package org.hc.learning.thread.notify.bure;

/**
 * 消费者
 */
public class PopBullet implements Runnable{
	
	Bure bure;
	public PopBullet(Bure bure) {
		this.bure = bure;
	}
	
	@Override
	public void run() {
		while (true) {
			// 对象锁
			synchronized (bure) {
				if (bure.getBulletCapacity() > 0) {
					bure.setBulletCapacity(bure.getBulletCapacity() - 1);
					System.out.println(Thread.currentThread().getName() + ":射击, 当前子弹:" + bure.getBulletCapacity());
					bure.notifyAll();
				} else {
					System.out.println("枪膛已空");
					try {
						bure.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
