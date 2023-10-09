package org.hc.learning.algorithm.sort;

import org.hc.learning.thread.forkjoin.learning.sum.MakeArray;
import org.hc.tool.print.Colorfuls;

import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 错误案例
 * 通过Fork-Join实现归并排序
 */
public class MergeSortError extends RecursiveAction {

    private Integer[] array;
    private int left;
    private int right;
    private InsertionSort sort;

    public MergeSortError(Integer[] array, int left, int right) {
        this(array, left, right, new InsertionSort(array));
    }

    public MergeSortError(Integer[] array, int left, int right, InsertionSort sort) {
        this.array = array;
        this.left = left;
        this.right = right;
        this.sort = Optional.ofNullable(sort).orElse(new InsertionSort(array));
    }

    @Override
    protected void compute() {
        if (right - left > 2) {
            Integer mid = (left + right)/ 2;
            MergeSortError leftTask = new MergeSortError(array, left, mid, sort);
            MergeSortError rightTask = new MergeSortError(array, mid+1, right, sort);
            invokeAll(leftTask, rightTask);
        }
        //效率低下 此处由于时间片切换频繁导致效率低下
        sort.sort(left, right);
        // new InsertionSort(array).sort(left, right);
    }

    /**
     * 在数据量在大于等于十万时效率高于单线程
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Colorfuls.toRed("归并排序"));
        long l = System.currentTimeMillis();
        Integer[] array = MakeArray.makeArray();
        // output(array);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MergeSortError(array, 0, array.length));
        System.out.println("\t耗时" + (System.currentTimeMillis() - l) + "ms");
    }
}
