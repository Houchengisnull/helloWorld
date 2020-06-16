package org.hc.learning.thread.lock.readwrite;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *类说明：对商品进行业务的应用
 */
public class BusiApp {
    static final int readWriteRatio = 10;//读写线程的比例
    static final int minthreadCount = 3;//最少线程数
    //读操作
    private static class GetThread implements Runnable{

        private GoodsService goodsService;
        public GetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for(int i=0;i<100;i++){//操作100次
                try {
                    goodsService.getNum();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+"读取商品数据耗时："
             +(System.currentTimeMillis()-start)+"ms");

        }
    }

    //写操做
    private static class SetThread implements Runnable{

        private GoodsService goodsService;
        public SetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Random r = new Random();
            for(int i=0;i<10;i++){//操作10次
                try {
                    TimeUnit.MICROSECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    goodsService.setNum(r.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()
            		+"写商品数据耗时："+(System.currentTimeMillis()-start)+"ms---------");

        }
    }

    public static void main(String[] args) throws InterruptedException {
        GoodsInfo goodsInfo = new GoodsInfo("Cup",100000,10000);
        /*GoodsService goodsService = new UseSyn(goodsInfo);*/
        GoodsService goodsService = new UseRwLock(goodsInfo);
        for(int i = 0;i<minthreadCount;i++){
            Thread setT = new Thread(new SetThread(goodsService));
            for(int j=0;j<readWriteRatio;j++) {
                Thread getT = new Thread(new GetThread(goodsService));
                getT.start();           	
            }
            Thread.sleep(5);
            setT.start();
        }
    }
}
