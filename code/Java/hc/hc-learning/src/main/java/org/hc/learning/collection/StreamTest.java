package org.hc.learning.collection;

import com.sun.tools.javac.util.Assert;
import org.hc.learning.test.TestCase;
import org.junit.Test;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hc.learning.pojo.Member;

public class StreamTest extends TestCase {

    /**
     * 通过Stream将List转换为Map
     */
    @Test
    public void map() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Stream<Map<Integer, String>> mapStream = list.stream().map(i -> getResult(i)); // 生成Map流
        List<Map<Integer, String>> collect = mapStream.collect(Collectors.toList());
        collect.stream().forEach(ele -> {
            System.out.println(ele.keySet());
        });
    }

    static Map<Integer, String> getResult(Integer i) {
        Map<Integer, String> map = new HashMap();
        map.put(i, String.valueOf(i));
        return map;
    }

    /**
     * 累加
     */
    @Test
    public void sum() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 10);
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        Assert.check(sum == 20);
    }

    /**
     * 对象转String
     */
    @Test
    public void obj2String() {
        List<Object> members = new ArrayList<>();
        members.add(Member.builder().name("Hugo").build());
        members.add(Member.builder().name("Hou").build());
        members.add(Member.builder().name("Cheng").build());

        List<String> collect = members.stream().map(o -> ((Member) o).getName()).collect(Collectors.toList());
    }

}
