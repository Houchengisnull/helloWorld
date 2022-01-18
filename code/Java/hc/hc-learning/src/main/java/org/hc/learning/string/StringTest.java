package org.hc.learning.string;

import lombok.extern.slf4j.Slf4j;
import org.hc.learning.test.TestCase;
import org.junit.Test;

@Slf4j
public class StringTest extends TestCase {

    @Test
    public void equalRef(){
        String hello = new String("hello");
        String hello1 = "hello";
        log.debug("hello == hello1: {}", hello == hello1);
        log.debug("hello equal hello1: {}", hello.equals(hello1));
        log.debug("hello.hash:{}", hello.hashCode());
        log.debug("hello1.hash:{}", hello1.hashCode());
        // getClass().getName() + "@" + Integer.toHexString(hashCode());
        log.debug("hello:{}", String.class.getName()+"@"+Integer.toHexString(hello.hashCode()));
        log.debug("hello1:{}", String.class.getName()+"@"+Integer.toHexString(hello1.hashCode()));
    }

    @Test
    public void hash() {
        Object o = new Object();
        log.debug("{}:{}", o, o.hashCode());
    }

    @Test
    public void internTest(){
        String hello = new String("hello");
        System.out.println(hello.intern());
    }
}
