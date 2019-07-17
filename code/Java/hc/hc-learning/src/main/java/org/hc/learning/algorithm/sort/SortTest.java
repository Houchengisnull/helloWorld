package org.hc.learning.algorithm.sort;

import org.hc.learning.thread.forkjoin.learning.sum.MakeArray;
import org.junit.Test;

public class SortTest {

    @Test
    public void insertionSort(){
        InsertionSort.main(null);
    }

    @Test
    public void bubbleSort(){
        BubbleSort.main(null);
    }

    @Test
    public void selectionSort(){
        SelectionSort.main(null);
    }
}
