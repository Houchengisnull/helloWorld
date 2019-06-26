package org.hc.learning.thread.notify.bure;

/**
 * 枪膛
 */
public class Bure {
	/**
	 * 最大子弹容量
	 */
	public static int MAX_BULLET_CAPACITY = 20;
	private Integer bulletCapacity = 0;
	
	public static void main(String[] args) {
		Bure bure = new Bure();
		new Thread(new PushBullet(bure), "上膛线程").start();
		new Thread(new PopBullet(bure), "射击线程").start();
	}

	public Integer getBulletCapacity() {
		return bulletCapacity;
	}

	public void setBulletCapacity(Integer bulletCapacity) {
		this.bulletCapacity = bulletCapacity;
	}
}
