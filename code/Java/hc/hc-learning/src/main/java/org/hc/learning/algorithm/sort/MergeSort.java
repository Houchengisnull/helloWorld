package org.hc.learning.algorithm.sort;

import org.hc.learning.thread.forkjoin.learning.sum.MakeArray;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 通过Fork-Join实现归并排序
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
        if (right - left < 2 ) {
            // sort
            int temp;
            if (array[right] < array[left]){
                temp = array[right];
                array[right] = array[left];
                array[left] = temp;
            }
        }else {
            int mid = (left + right )/ 2;
            System.out.println("left("+ left + "," + mid + ")" + "\tright(" + (mid+1) + "," +  right + ")");
            MergeSort leftTask = new MergeSort(array, left, mid);
            MergeSort rightTask = new MergeSort(array, mid + 1, right);
            invokeAll(leftTask, rightTask);
            leftTask.join();
            rightTask.join();
            for (int i = left; i <= right ; i++){

            }
        }
    }

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        Integer[] array = MakeArray.makeArray();
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MergeSort(array, 0, array.length-1));
        // System.out.println(System.currentTimeMillis() - l);
        // sorted
    }
}
