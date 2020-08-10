package org.hc.learning.datastruct;

import java.util.LinkedList;

public class ListApplication {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<Integer>() {
            {
                push(1);
                push(2);
                push(3);
                push(4);
            }
        };

        System.out.println("First " + list.getFirst());
        System.out.println("Last " + list.getLast());
    }

}
