package org.hc.learning.collection;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LambdaTest {

    private List<String> list;

    @Before
    public void buildArrayList() {
        list = new ArrayList<>();
        list.add("Hello");
        list.add("world");
        list.add("one");
        list.add("two");
        list.add("three");
    }

    @Test
    public void forEach() {
        list.forEach(ele -> System.out.println(ele));
    }

    @Test
    public void removeIf() {
        list.removeIf(ele -> ele.equals("two"));
        list.forEach(ele -> System.out.println(ele));
    }

    @Test
    public void removeIfWithArrays() {
        List<String> arrayList = Arrays.asList(new String[]{"one", "two", "three"});
        arrayList.removeIf(ele -> ele.equals("two"));
    }

}
