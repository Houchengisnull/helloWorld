package org.hc.learning.thread.forkjoin.learning.sort;


import org.hc.learning.thread.forkjoin.learning.sum.MakeArray;

/**
 * 类说明：简单插入排序（升序）
 */
public class InsertionSort {
    public static Integer[] sort(Integer[] array) {
        if (array.length == 0)
            return array;
        int currentValue;/*当前待排序数据，该元素之前的元素均已被排序过*/
        for (int i = 0; i < array.length - 1; i++) {
            int preIndex = i;/*已被排序数据的索引*/
            currentValue = array[preIndex + 1];

            /*在已被排序过数据中倒序寻找合适的位置，如果当前待排序数据比比较的元素要小，
            将比较的元素元素后移一位*/
            while (preIndex >= 0 && currentValue < array[preIndex]) {
                //将当前元素后移一位
                array[preIndex + 1] = array[preIndex];
                preIndex--;
            }
            /*while循环结束时，说明已经找到了当前待排序数据的合适位置，插入*/
            array[preIndex + 1] = currentValue;
        }
        return array;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("============================================");
        InsertionSort.sort(MakeArray.makeArray());
        System.out.println(" spend time:"+(System.currentTimeMillis()-start)+"ms");
    }
}