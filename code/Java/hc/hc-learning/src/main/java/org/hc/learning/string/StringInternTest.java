package org.hc.learning.string;

import cn.hutool.core.lang.Assert;
import org.hc.learning.test.TestCase;
import org.junit.Test;

public class StringInternTest extends TestCase {
    /**
     * 由于s2的值在编译器确定, 故s0==s2为TRUE
     * 反编译查看结果: ldc #2
     */
    @Test
    public void compilerTest() {
        String s0 = "hello";
        String s1 = "hello";
        String s2 = "he" + "llo";
        Assert.isTrue(s0 == s1);
        Assert.isTrue(s0 == s2);
    }

    @Test
    public void newInstanceTest() {
        String s0 = "hello";
        String s1 = new String("hello");
        String s2 = new String("he") + new String("llo");
        System.out.println(s0 == s1);
        System.out.println(s0 == s2);
        System.out.println(s1 == s2);
    }


    @Test
    public void internTest() {
        String s0 = "hello";
        String s1 = new String("hello");
        String s2 = new String("hello");
        System.out.println(s0 == s1);
        String intern = s1.intern();
        String intern1 = s2.intern();
        System.out.println(s0 == s1);
        System.out.println(s0 == intern);
        System.out.println(s0 == intern1);
    }

}
