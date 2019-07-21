package org.hc.learning.algorithm.sort;

import org.hc.learning.thread.forkjoin.learning.sort.FkSort;
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

    @Test
    public void mergeSort(){
        MergeSort.main(null);
    }

    @Test
    public void mergeSortByMark() {
        FkSort.main(null);
    }
}
