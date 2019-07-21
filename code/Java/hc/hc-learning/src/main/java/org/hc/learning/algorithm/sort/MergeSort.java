package org.hc.learning.algorithm.sort;

import org.hc.learning.thread.forkjoin.learning.sum.MakeArray;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 该种实现方式效率依然不够高的原因时
 * 插入排序无法充分利用已经排好序的两个数组
 * 在合并时使用快速排序更为妥当
 */
public class MergeSort extends RecursiveAction {

    private Integer[] array;
    private int left;
    private int right;

    public MergeSort(Integer[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        /*long l = System.currentTimeMillis();*/
        if (right - left <= 2) {
            new InsertionSort(array).sort(left, right);
        } else {
            Integer mid = (left + right)/ 2;
            MergeSort leftTask = new MergeSort(array, left, mid);
            MergeSort rightTask = new MergeSort(array, mid, right);
            invokeAll(leftTask, rightTask);
            leftTask.join();
            rightTask.join();
            Integer[] leftArray = Arrays.copyOfRange(array, left, mid);
            Integer[] rightArray = Arrays.copyOfRange(array, mid, right);

            for (int i = left, leftIndex = 0, rightIndex = 0; i < right; i++) {
                if (leftIndex >= leftArray.length)/*左边数组已经取完，完全取右边数组的值即可*/ {
                    array[i] = rightArray[rightIndex++];
                }else if (rightIndex >= rightArray.length)/*右边数组已经取完，完全取左边数组的值即可*/
                    array[i] = leftArray[leftIndex++];
                else if (leftArray[leftIndex] > rightArray[rightIndex])/*左边数组的元素值大于右边数组，取右边数组的值*/
                    array[i] = rightArray[rightIndex++];
                else/*右边数组的元素值大于左边数组，取左边数组的值*/
                    array[i] = leftArray[leftIndex++];
            }
        }
    }

    /**
     * 在数据量在大于等于十万时效率高于单线程
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("归并排序");
        Integer[] array = MakeArray.makeArray();
        long l = System.currentTimeMillis();
        // output(array);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MergeSort(array, 0, array.length));
        System.out.println("\t耗时" + (System.currentTimeMillis() - l) + "ms");
        // sorted
        // output(array);
    }

    private static void output(Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            if (i%10 == 0) {
                System.out.println();
            }
            System.out.print(array[i].toString());
            System.out.print("\t");
        }
        System.out.println();
    }
}
