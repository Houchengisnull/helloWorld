package org.hc.learning.jvm.reference;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 软引用 内存溢出前回收
 */
public class SoftReferenceTest {

    public static void main(String[] args) {
        User user = new User();
        user.setId("1");
        user.setName("Houcheng");
        SoftReference<User> userCache = new SoftReference(user);
        user = null;

        System.out.println(userCache.get());
        System.gc();
        System.out.println(userCache.get() == null);

        List<byte[]> bytes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(userCache.get());
                bytes.add(new byte[1024 * 1024]);
            } catch (OutOfMemoryError e) {
                System.err.println(userCache.get());
            }
        }
    }

}
