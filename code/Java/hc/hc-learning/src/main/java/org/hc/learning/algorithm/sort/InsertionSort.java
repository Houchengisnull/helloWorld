package org.hc.learning.algorithm.sort;

import org.hc.learning.thread.forkjoin.learning.sum.MakeArray;


/**
 * 插入排序
 * 插入排序是一种稳定的排序
 */
public class InsertionSort extends Sort<Integer>{
    /**
     * 迭代次数
     */
    private int iterateCount = 0;
    private int count = 0 ;

    public InsertionSort(Integer[] array) {
        super(array);
    }

    /**
     * 仅针对从left 到 right的数据进行排序
     * @param left >= 0
     * @param right right <= array.length -1
     */
    public void sort(Integer left, Integer right) {
        // 每次输入数据中 移除一个元素并将其插入已排序序列的正确位置
        for (int i = left; i < right; i++) {
            int value = array[i];
            int j = i;
            while (j>=left+1 && array[j-1] > value) {
                iterateCount++;
                array[j] = array[j-1]; // 元素往后移动
                j--;
            }
            array[j] = value;
            // output(array);
        }
    }

    @Override
    public void sort() {
        sort(0, array.length );
    }

    @Override
    public void swap(int posFront, int posBack) {
        count++;
        int temp = array[posFront];
        array[posFront] = array[posBack];
        array[posBack] = temp;
    }

    public static void main(String[] args) {
        System.out.println("插入排序");
        long l = System.currentTimeMillis();
        Integer[] array = MakeArray.makeArray();
        InsertionSort sort = new InsertionSort(array);
        // sort.output(array);
        sort.sort();
        // sort.output(array);
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
