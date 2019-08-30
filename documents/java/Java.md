[TOC]

# command

## 查看JDK安装地址

``` shell
$ java -verbose
```

# 面向对象

## private

2019-6-22

今天看到一个问题——private修饰的方法可以通过反射访问，那么private的意义是什么？

> 你遇到过哪些质量很高的 Java 面试？ - ZO01的回答 - 知乎
> https://www.zhihu.com/question/60949531/answer/579002882

ZO01设置了禁止转载，就不将他的回答copy上去了。个人以为确实是个直击灵魂的问题。

# 弱引用的应用场景

- 弱引用的概念

  > JVM发生GC时，一定回收弱引用。

  

  强引用在置空时才可以被JVM的GC回收

  ``` java
  Object obj = new Object();
  obj = null;
  ```

  但手动置空对象对于程序员来说，**繁琐且违背自动回收的思想。**平常情况下，手动置空不需要程序员来做，因为在Java中，对于调用它的方法执行完毕后，指向它的引用会从栈帧中“pop up”。下次GC时将回收它。

- Cache

  但也有例外，缓存对象正是程序员运行需要的，那么只要程序运行，Cache对象的引用就不会被回收。随着Cache中的Reference越来越多，GC无法回收的对象也会越来越多。

  由于无法被自动回收，那就需要程序员手动释放这些引用占据的内存，而这却违背了GC本质——自动回收可回收的对象。

  于是就有`WeakReference`。

- Example

  ``` java
  
  import java.lang.ref.WeakReference;
  
  /**
   * 弱引用示例
   */
  public class WeakReferenceExample {
      public static void main(String[] args) {
          Person person = new Person("Hou", "Yamy", 23);
          // 当一个对象仅仅被weak reference指向, 而没有任何其他strong reference指向的时候, 如果GC运行, 那么这个对象就会被回收。
          WeakReference<Person> personWeakReference = new WeakReference<Person>(person);
  
          Person personClone = person;
          int count = 0;
          while (true) {
              if(personWeakReference.get() != null) {
                  count++;
                  System.out.println("person still be alive");
              } else {
                  System.out.println("person was collected");
                  break;
              }
          }
          // System.out.println(personClone.getName() + " is a cloneMan"); // 1
      }
  }
  ```

  若存在person指向强引用，但若之后不调用该强引用`personClone`，编译器将对它优化——即发生垃圾回收时依然能回收person。

  可对比**1处注释**通过`javap`反编译字节码结果 。

- 运行结果

  ``` text
  ...
  person still be alive
  person still be alive
  person still be alive
  person still be alive[GC (Allocation Failure)  65536K->1123K(251392K), 0.0009931 secs]
  
  person was collected
  
  ```

https://blog.csdn.net/qq_33663983/article/details/78349641

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

# Runtime

## 获取系统CPU核心数

``` java
Runtime.getRuntime().availableProcessors();
```

# Java Socket

## java.net.preferIPv4Stack

- `java`文档

  > If an application has a preference to only use IPv4 sockets, then this property can be set to true. The implication is that the application will not be able to communicate with IPv6 hosts.

优先使用`ipv4 栈`。// 优先使用`ipv4地址`

### 代码方式

有时当`ftp4j`等应用程序无法通过防火墙时，可设置该参数为true以令应用程序通过防火墙。

``` java
System.setProperty("java.net.preferIPv4Stack", "true");
```

### 参数方式

``` text
-Djava.net.preferIPv4Stack=true
```

### tomcat

可在`catalina.bat`或者`catalina.sh`中增加如下环境变量即可：

```shell
SET CATALINA_OPTS=-Djava.net.preferIPv4Stack=true
```

### 参考

https://blog.csdn.net/iteye_9130/article/details/82325170

https://blog.csdn.net/wodeyuer125/article/details/50502989

http://www.micmiu.com/lang/java/java-net-ipv4-ipv6/