package org.hc.learning.thread.lock.readwrite;

/**
 *类说明：商品的服务的接口
 */
public interface GoodsService {

	public GoodsInfo getNum() throws InterruptedException;//获得商品的信息
	public void setNum(int number) throws InterruptedException;//设置商品的数量
}
