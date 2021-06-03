package org.hc.learning.jvm.memoryleak;

import java.util.LinkedList;
import java.util.List;

/**
 * 观察MinorGc 与Full GC的区别
 *  VM Args：-Xms30m -Xmx30m  -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 */
public class Oom {

    public static void main(String[] args) {
        List<Object> list = new LinkedList<>(); //在方法执行的过程中，它是GCRoots
        int i =0;
        while(true){
            try {
                i++;
                // if(i%10000==0) System.out.println("i="+i);
                list.add(new Object());
            } catch (OutOfMemoryError e){
                break;
            }
        }
    }

}
