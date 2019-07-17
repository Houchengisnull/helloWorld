package org.hc.learning.algorithm.sort;

import org.hc.learning.thread.forkjoin.learning.sum.MakeArray;

/**
 * 冒泡排序
 */
public class BubbleSort extends Sort<Integer>{

    private int count = 0 ;

    public BubbleSort(Integer[] array) {
        super(array);
    }

    /**
     * 10万数据耗时16703ms
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("冒泡排序");
        long l = System.currentTimeMillis();
        Integer[] array = MakeArray.makeArray();
        BubbleSort sort = new BubbleSort(array);
        sort.sort();
        // sort.output(array);
        System.out.println("交换" + sort.getCount() + "次");
        System.out.println("耗时" + (System.currentTimeMillis()-l) + "ms");
    }

    @Override
    void sort() {
        for (int i = 0; i < array.length; i++) {
            // 标记排序是否完成 缺省true
            boolean finished = true;
            for (int j = 0; j < array.length-1-i; j++) {
                if (array[j] < array[j+1]) {
                    swap(j, j+1);
                    finished = false;
                }
            }
            if (finished){
                /*System.out.println("执行" + (i+1) + "次数");*/
                break;
            }
        }
    }

    @Override
    void swap(int posFront, int posBack) {
        count++ ;
        int temp = array[posFront];
        array[posFront] = array[posBack];
        array[posBack] = temp;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
