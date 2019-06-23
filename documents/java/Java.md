[TOC]

# 面向对象

## private

2019-6-22

今天看到一个问题——private修饰的方法可以通过反射访问，那么private的意义是什么？

> 你遇到过哪些质量很高的 Java 面试？ - ZO01的回答 - 知乎
> https://www.zhihu.com/question/60949531/answer/579002882

ZO01设置了禁止转载，就不将他的回答copy上去了。个人以为确实是个直击灵魂的问题。

# Java中常见的内存泄漏场景

## 静态集合类引起内存泄漏

``` java
import java.util.Vector;

/**
 * 静态集合类引起的memory leak
 */
public class StaticCollection {
    static Vector<Object> vector = new Vector<>(10);
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Object o = new Object();
            vector.add(o);
            o = null;
        }
    }
}
```

在以上代码中，通过`o = null;`释放引用，但`vector`依然持有该对象引用。

## 集合中对象属性被修改

``` java

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
```

原本在`p3`修改年龄后，`hashCode`随之改变而无法remove失败造成内存泄漏。但可能在JDK8修复了这个Bug。

## 监听器

## 连接

网络连接（Socket）和IO连接除非显式调用`close`，否则不会被垃圾回收。

- 在数据库连接中

  ResultSet和Statement对象可以不进行显式回收，但Connection一定要显式回收，因为Connection在任何时候都无法自动回收，而Connection一旦回收，ResultSet和Statement会被设为null。

  但在使用连接池时，除了要显式关闭连接，还要显式关闭ResultSet和Statement。否则大量Statement对象无法释放。

## 单例对象持有外部对象引用

单例对象在被初始化后，往往在整个JVM周期中存在。如果这个单例对象持有外部对象引用，该外部对象不能被JVM内存回收。

## 总结

以上情况归根到底，是**强引用对象不能被垃圾回收**。

https://www.cnblogs.com/panxuejun/p/5883044.html

