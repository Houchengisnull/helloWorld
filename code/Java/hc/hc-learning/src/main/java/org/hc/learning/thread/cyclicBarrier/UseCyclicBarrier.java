package org.hc.learning.thread.cyclicBarrier;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 *类说明：演示CyclicBarrier用法,共5个子线程，他们全部完成工作后，交出自己结果，
 *再被统一释放去做自己的事情，而交出的结果被另外的线程拿来拼接字符串
 *
 * 由于PARITES == 4 而启动5个线程故可能出现一次多余的dosomething
 *
 * 注意：由于CyclicBarrier可反复使用，所以调用完4次await后可重复使用
 */
public class UseCyclicBarrier {

    private static int PARITES = 4;
    private static CyclicBarrier barrier = new CyclicBarrier(PARITES, new CollectThread());
    private static ConcurrentHashMap<String,Long> resultMap
            = new ConcurrentHashMap<>();//存放子线程工作结果的容器

    public static void main(String[] args) {
        for(int i=0;i<=PARITES;i++){
            Thread thread = new Thread(new SubThread());
            thread.start();
        }
    }

    /**
     * 汇总工作线程
     */
    private static class CollectThread implements Runnable{

        @Override
        public void run() {
            StringBuilder result = new StringBuilder();
            for(Map.Entry<String,Long> workResult:resultMap.entrySet()){
                result.append("["+workResult.getValue()+"]");
            }
            System.out.println(" the result = "+ result);
            System.out.println("do other business........");
        }
    }

    /**
     * 子任务线程
     */
    private static class SubThread implements Runnable{

        @Override
        public void run() {
            long id = Thread.currentThread().getId();
            resultMap.put(Thread.currentThread().getId()+"",id);
            try {
                Thread.sleep(1000+id);
                System.out.println("Thread_"+id+" ....do something ");
                // 所有线程调用`await`后将唤醒线程
                barrier.await();
                // 唤醒后业务工作
                Thread.sleep(1000+id);
                System.out.println("Thread_"+id+" ....do its business ");
                // barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
