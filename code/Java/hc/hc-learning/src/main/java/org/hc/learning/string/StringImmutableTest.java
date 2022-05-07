package org.hc.learning.string;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hc.learning.test.TestCase;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashSet;

@Slf4j
public class StringImmutableTest extends TestCase {
    /**
     * 反射强制修改String
     */
    @SneakyThrows
    @Test
    public void reflect() {
        String text = "Hello";
        System.out.println(text);
        Field value = String.class.getDeclaredField("value");
        value.setAccessible(true);
        value.set(text, value.get("Hello world"));
        System.out.println(text);
    }

    @Test
    public void safety() {
        HashSet<StringBuilder> set = new HashSet<>();
        set.add(new StringBuilder("Hello"));
        set.add(new StringBuilder("Hello"));
        System.out.println(set.size());
    }
}
