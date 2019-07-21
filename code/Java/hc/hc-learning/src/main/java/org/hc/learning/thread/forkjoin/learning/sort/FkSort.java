package org.hc.learning.thread.forkjoin.learning.sort;


import org.hc.learning.thread.forkjoin.learning.sum.MakeArray;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * forkjoin实现的归并排序
 */
public class FkSort {
    private static class SumTask extends RecursiveTask<Integer[]>{

        private final static int THRESHOLD = 2;
        private Integer[] src;

        public SumTask(Integer[] src) {
            this.src = src;
        }

        @Override
        protected Integer[] compute() {
            if(src.length<=THRESHOLD){
                return InsertionSort.sort(src);
            }else{
                //fromIndex....mid.....toIndex
                int mid = src.length / 2;
                SumTask leftTask = new SumTask(Arrays.copyOfRange(src, 0, mid));
                SumTask rightTask = new SumTask(Arrays.copyOfRange(src, mid, src.length));
                invokeAll(leftTask,rightTask);
                Integer[] leftResult = leftTask.join();
                Integer[] rightResult = rightTask.join();
                return MergeSort.merge(leftResult,rightResult);
            }
        }
    }


    public static void main(String[] args) {
        System.out.println("FkSort implement By Mark");
        ForkJoinPool pool = new ForkJoinPool();
        Integer[] src = MakeArray.makeArray();

        SumTask innerFind = new SumTask(src);

        long start = System.currentTimeMillis();

        Integer[] invoke = pool.invoke(innerFind);
        //PrintArray.print(innerFind.join());

        System.out.println("\tspend time:"+(System.currentTimeMillis()-start)+"ms");
        /*for (int i = 0; i < invoke.length; i++) {
            if (i%10 == 0) {
                System.out.println();
            }
            System.out.print(invoke[i].toString());
            System.out.print("\t");
        }
        System.out.println();*/

    }
}
