package org.hc.learning.thread.notify.bure;

/**
 * 压进子弹
 * 生产者
 */
public class PushBullet implements Runnable{

	Bure bure;
	public PushBullet(Bure bure) {
		this.bure = bure;
	}
	
	@Override
	public void run() {
		while (true) {
			// 对象锁
			synchronized (bure) {
				if (bure.getBulletCapacity() < Bure.MAX_BULLET_CAPACITY) {
					bure.setBulletCapacity(bure.getBulletCapacity() + 1);
					System.out.println(Thread.currentThread().getName() + ":上膛, 当前子弹:" + bure.getBulletCapacity());
					bure.notifyAll();
				} else {
					System.out.println("枪膛已满");
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
