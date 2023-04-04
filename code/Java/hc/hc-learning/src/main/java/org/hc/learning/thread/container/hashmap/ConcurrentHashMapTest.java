package org.hc.learning.thread.container.hashmap;

import org.apache.lucene.util.RamUsageEstimator;
import org.hc.learning.test.TestCase;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest extends TestCase {

    private static ConcurrentHashMap map = new ConcurrentHashMap();

    @Test
    public void memory(){
        for (int i = 0; i < 10000; i++) {
            map.put(Integer.valueOf(i), new Object());
        }
        long l = RamUsageEstimator.sizeOf(map);
        String s = RamUsageEstimator.humanSizeOf(map);

        System.out.println("------ before clear -----");
        System.out.println(l/1024 + " KB");
        System.out.println(s);
        System.out.println(map.size());
        System.out.println("------ after clear -----");
        map.clear();
        long l1 = RamUsageEstimator.sizeOf(map);
        String s1 = RamUsageEstimator.humanSizeOf(map);
        System.out.println(l1/1024 + " KB");
        System.out.println(s1);
        System.out.println(map.size());
    }

}
