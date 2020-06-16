package org.hc.learning.thread.lock.readwrite;

import java.util.concurrent.TimeUnit;

/**
 *类说明：用内置锁来实现商品服务接口
 */
public class UseSyn implements GoodsService {
	
	private GoodsInfo goodsInfo;
	
	public UseSyn(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	@Override
	public synchronized GoodsInfo getNum() throws InterruptedException {
		TimeUnit.MICROSECONDS.sleep(5);
		return this.goodsInfo;
	}

	@Override
	public synchronized void setNum(int number) throws InterruptedException {
		TimeUnit.MICROSECONDS.sleep(5);
		goodsInfo.changeNumber(number);

	}

}
