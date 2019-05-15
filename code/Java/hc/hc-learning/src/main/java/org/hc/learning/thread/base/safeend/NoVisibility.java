package org.hc.learning.thread.base.safeend;

public class NoVisibility {
	static /*volatile*/ boolean flag;
	static int count = 0;
	
	private static class NoVisibilityThread extends Thread {
		@Override
		public void run() {
			System.out.println("进入线程run()");
			while (!flag) {}; // 死循环
			System.out.println(count);
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new NoVisibilityThread().start();
		Thread.sleep(10); // 休眠10ms 保证在flag = true前进入循环
		count = 10;
		flag = true;
		Thread.sleep(10); // 休眠10ms 保证在执行完 flag = true 后进入线程时间片
		System.out.println("主线程执行完毕");
	}
}
