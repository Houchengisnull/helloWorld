package org.hc.learning.algorithm.sort;

public abstract class Sort<T> {

    T[] array;
    public Sort(T[] array){
        this.array = array;
    }

    /**
     * 排序
     */
    abstract void sort();

    /**
     * 将array中位置posFront与posBack的元素交换
     * @param posFront 位置1
     * @param posBack 位置2
     */
    abstract void swap(int posFront, int posBack);

    public void output(T[] array) {
        for (int i = 0; i < array.length; i++) {
            if (i%10 == 0) {
                System.out.println();
            }
            System.out.print(array[i]);
            System.out.print("\t");
        }
        System.out.println();
    }
}
