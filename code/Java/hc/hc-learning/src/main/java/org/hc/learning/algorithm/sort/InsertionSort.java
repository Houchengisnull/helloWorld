package org.hc.learning.algorithm.sort;

import org.hc.learning.thread.forkjoin.learning.sum.MakeArray;

import java.util.LinkedList;

/**
 * 插入排序
 */
public class InsertionSort extends Sort<Integer>{

    private int count = 0 ;

    public InsertionSort(Integer[] array) {
        super(array);
    }

    @Override
    public void sort() {
        // 每次输入数据中 移除一个元素并将其插入已排序序列的正确位置
        for (int i = 0; i < array.length; i++) {
            for (int j = i-1; j > 0; j--) {
                if (array[i] < array[j]) {
                    swap(i, j);
                }
            }
        }
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
        sort.sort();
        sort.output(array);
        System.out.println("交换" + sort.getCount() + "次");
        System.out.println("耗时" + (System.currentTimeMillis()-l) + "ms");
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
