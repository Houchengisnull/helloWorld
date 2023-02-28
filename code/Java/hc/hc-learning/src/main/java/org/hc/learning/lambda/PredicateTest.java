package org.hc.learning.lambda;

import org.hc.learning.test.TestCase;
import org.junit.Test;
import java.util.function.Predicate;

/**
 * 断言测试类
 */
public class PredicateTest extends TestCase {
    Predicate<Integer> predicate = n -> n > 0;
    Integer n = 10;

    @Test
    public void predicate() {
        System.out.println("predicate.test(n) :" + predicate.test(n));
        System.out.println("predicate.negate().test(n) :" + predicate.negate().test(n));
    }

}
