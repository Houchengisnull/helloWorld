package org.hc.learning.thread.forkjoin.learning.sort;


import org.hc.learning.thread.forkjoin.learning.sum.MakeArray;

import java.util.Arrays;

/**
 * 类说明：归并排序
 */
public class MergeSort {
    public static Integer[] sort(Integer[] array) {
        if(array.length<= MakeArray.THRESHOLD){
            return InsertionSort.sort(array);
        }else{
            /*切分数组，然后递归调用*/
            int mid = array.length / 2;
            Integer[] left = Arrays.copyOfRange(array, 0, mid);
            Integer[] right = Arrays.copyOfRange(array, mid, array.length);
            Integer[] lefts = sort(left);
            Integer[] rights = sort(right);
            System.out.print("mid:" + mid );
            return merge(lefts, rights);
        }
    }
    /**
     * 归并排序——将两段排序好的数组结合成一个排序数组
     * 以这种方式合并的效率为O（n） 有一种快速排序的味道
     * @param left
     * @param right
     * @return
     */
    public static Integer[] merge(Integer[] left, Integer[] right) {
        Integer[] result = new Integer[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= left.length)/*左边数组已经取完，完全取右边数组的值即可*/
                result[index] = right[j++];
            else if (j >= right.length)/*右边数组已经取完，完全取左边数组的值即可*/
                result[index] = left[i++];
            else if (left[i] > right[j])/*左边数组的元素值大于右边数组，取右边数组的值*/
                result[index] = right[j++];
            else/*右边数组的元素值大于左边数组，取左边数组的值*/
                result[index] = left[i++];
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("============================================");
        long start = System.currentTimeMillis();
        MergeSort.sort(MakeArray.makeArray());
        System.out.println(" spend time:"+(System.currentTimeMillis()-start)+"ms");
    }
}