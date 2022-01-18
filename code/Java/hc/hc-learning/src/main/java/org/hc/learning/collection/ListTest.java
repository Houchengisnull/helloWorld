package org.hc.learning.collection;

import lombok.extern.slf4j.Slf4j;
import org.hc.learning.test.TestCase;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ListTest extends TestCase {

    /**
     * 泛型强转
     */
    @Test
    public void convertListObjectTest(){
        List<Object> list = Arrays.asList("1", "2", "3");
        List<String> listString = (List<String>) (List) list;
    }

    /**
     * 
     */
    @Test
    public void joinTest(){
        List<String> list = Arrays.asList("AAA", "BBB", "CCC");
        String join = String.join(",", list);
        log.info("{}", join);
    }
}
