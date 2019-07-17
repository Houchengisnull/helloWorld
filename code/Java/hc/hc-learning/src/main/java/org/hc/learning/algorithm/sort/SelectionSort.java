package org.hc.learning.algorithm.sort;

import org.hc.learning.thread.forkjoin.learning.sum.MakeArray;

public class SelectionSort extends Sort<Integer>{

    private int iterateCount = 0;
    private int count = 0 ;

    public SelectionSort(Integer[] array) {
        super(array);
    }

    @Override
    public void sort() {
        for (int i = 0; i < array.length; i++) {
            int max = i;
            for (int j = i+1; j < array.length; j++) {
                iterateCount++;
                if (array[max] < array[j]){
                    max = j;
                }
            }
            swap(max, i);
        }
    }

    @Override
    public void swap(int posFront, int posBack) {
        count++ ;
        int temp = array[posFront];
        array[posFront] = array[posBack];
        array[posBack] = temp;
    }

    /**
     * 10万数据耗时16703ms
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("选择排序");
        long l = System.currentTimeMillis();
        Integer[] array = MakeArray.makeArray();
        SelectionSort sort = new SelectionSort(array);
        sort.sort();
        // sort.output(array);
        System.out.println("\t交换" + sort.getCount() + "次");
        System.out.println("\t迭代" + sort.getIterateCount() + "次");
        System.out.println("\t耗时" + (System.currentTimeMillis()-l) + "ms");
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIterateCount() {
        return iterateCount;
    }

    public void setIterateCount(int iterateCount) {
        this.iterateCount = iterateCount;
    }
}
