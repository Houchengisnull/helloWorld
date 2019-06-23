package org.hc.learning.jvm.memoryleak;

import org.hc.learning.jvm.memoryleak.bean.Person;
import java.util.HashSet;
import java.util.Set;

/**
 * 验证结果并没有出现内存泄漏
 * 本人使用的时JDK8,也许在这个版本中这个bug已经被修复
 */
public class CollectionObjectChange {

    public static void main(String[] args)
    {
        Set<Person> set = new HashSet<Person>();
        Person p1 = new Person("唐僧","pwd1",25);
        Person p2 = new Person("孙悟空","pwd2",26);
        Person p3 = new Person("猪八戒","pwd3",27);
        set.add(p1);
        set.add(p2);
        set.add(p3);
        System.out.println("总共有:"+set.size()+" 个元素!"); //结果：总共有:3 个元素!
        for (Person person : set)
        {
            System.out.println(person);
        }
        p3.setAge(2); //修改p3的年龄,此时p3元素对应的hashcode值发生改变

        boolean removeResult = set.remove(p3);//此时remove不掉，造成内存泄漏
        if (removeResult) {
            System.out.println("remove P3成功");
        } else {
            System.out.println("remove P3s失败");
        }

        for (Person person : set)
        {
            System.out.println(person);
        }

        boolean addResult = set.add(p3);//重新添加，居然添加成功
        if (addResult) {
            System.out.println("add P3成功");
        } else {
            System.out.println("add P3s失败");
        }
        System.out.println("总共有:"+set.size()+" 个元素!"); //结果：总共有:4 个元素!
        for (Person person : set)
        {
            System.out.println(person);
        }
    }
}
