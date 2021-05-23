package org.hc.learning.jvm.reference;

import java.lang.ref.WeakReference;

/**
 * 弱引用 发生GC必回收
 */
public class WeakReferenceTest {

    public static void main(String[] args) {
        User user = new User();
        user.setId("001");
        user.setName("Houcheng");
        WeakReference<User> cache = new WeakReference<User>(user);
        user = null;

        System.out.println(cache.get());
        System.gc();
        System.out.println(cache.get());
    }
}
