package org.hc.learning.jvm.memoryleak;

import java.util.Vector;

/**
 * 静态集合类引起的memory leak
 */
public class StaticCollection {
    static Vector<Object> vector = new Vector<>(10);
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Object o = new Object();
            vector.add(o);
            o = null;
        }
    }
}
