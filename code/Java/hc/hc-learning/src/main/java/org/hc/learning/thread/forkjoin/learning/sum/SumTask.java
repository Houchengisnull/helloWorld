package org.hc.learning.thread.forkjoin.learning.sum;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class SumTask extends RecursiveTask<Integer> {
    /**
     * 阈值 即每份任务量的大小
     */
    private final static int THRESHOLD = MakeArray.ARRAY_LENGTH/10;
    private Integer[] src;
    private int fromIndex;
    private int toIndex;

    public SumTask(Integer[] src, int fromIndex, int toIndex) {
        this.src = src;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    protected Integer compute() {
        /**
         * 任务的大小是否合适
         */
        if (toIndex - fromIndex < THRESHOLD) {
            System.out.println(" from index = " + fromIndex + " toIndex =" + toIndex);
            int count = 0;
            for (int i = fromIndex; i <= toIndex ; i++) {
                /**
                 * 假设每次操作耗时较大时
                 */
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count += src[i];
            }
            return count;
        } else {
            int mid = (fromIndex + toIndex)/2;
            SumTask left = new SumTask(src, fromIndex, mid);// 二分
            SumTask right = new SumTask(src, mid + 1, toIndex);
            invokeAll(left, right);
            // 阻塞方法
            return left.join() + right.join();
        }
    }
}
