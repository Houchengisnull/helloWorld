package org.hc.learning.thread.base.safeend;


/**
 *类说明：如何安全中断线程
 */
public class EndThread {
	
	private static class UseThread extends Thread{

		public UseThread(String name) {
			super(name);
		}

		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName+" interrrupt flag ="+isInterrupted());
			while(!isInterrupted()){
				// 检查线程中断标志位并修改位false
				// while(!Thread.interrupted()){
				//while(true) {
				System.out.println(threadName+" is running");
				System.out.println(threadName+"inner interrrupt flag = " +isInterrupted());
			}
			System.out.println(threadName + " interrrupt flag = "+ isInterrupted());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread endThread = new UseThread("endThread");
		endThread.start();
		Thread.sleep(20);
		endThread.interrupt();//中断线程，其实设置线程的标识位true
	}

}