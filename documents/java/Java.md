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

# 克隆

克隆的目的是快速获取一个对象副本

- 浅克隆

  创建一个新对象，新对象属性和原对象完全一样，对于引用数据类型，仍指向原有属性所指向的对象的内存地址

- 深克隆

  创建一个新对象，引用数据类型也被克隆，不再指向原有对象地址

## 实现克隆

- 继承`Cloneable`接口
- 重写`clone()`
- 在`clone()`中调用`super.clone()`

> 在实现深克隆重写`clone()`方法时，对引用数据类型重复以上步骤，并调用该成员变量的`clone()`

``` java
    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        //注意以下代码
        Teacher teacher = (Teacher)super.clone();
        teacher.setStudent((Student)teacher.getStudent().clone());
        return teacher;
    }
```

- 参考

https://www.cnblogs.com/liqiangchn/p/9465186.html

https://blog.csdn.net/lovezhaohaimig/java/article/details/80372233

# Java容器

## List、Set、Map区别

- **List(对付顺序的好帮手)：** List接口存储一组不唯一（可以有多个元素引用相同的对象），有序的对象
- **Set(注重独一无二的性质):** 不允许重复的集合。不会有多个元素引用相同的对象。
- **Map(用Key来搜索的专家):** 使用键值对存储。Map会维护与Key有关联的值。两个Key可以引用相同的对象，但Key不能重复，典型的Key是String类型，但也可以是任何对象。

## 常见集合类

### Collection

#### List

| className  | description              |
| ---------- | ------------------------ |
| ArrayList  | 基于数组实现，非线程安全 |
| Vector     | 基于数组实现，线程安全   |
| LinkedList | 双向链表                 |

##### ArrayList与LinkedList区别

- 均不保证线程安全

- 底层数据结构不同，ArrayList由数组实现，LinkedList通过双向链表实现

- 插入和删除元素是否受到元素位置影响

  - ArrayList采用数组存储，所以插入和删除元素的时间复杂度受元素位置影响大
    - 末尾位置时间复杂度位O(1)
    - **i**位置位O(n-i)
  - LinkedList采用链表存储，所以插入与删除元素时间复杂度不受元素位置影响，近似O(1)

- ArrayList支持快速随机访问，LinkedList不支持

  > 快速访问即通过元素序号快速获取元素对象

- 内存空间占用

  - ArrayList的空 间浪费主要体现在在list列表的结尾会预留一定的容量空间

  - 而LinkedList的空间花费则体现在它的每一个元素都需要消耗比ArrayList更多的空间（因为要存放直接后继和直接前驱以及数据）。

#### Set

| className     | description                                          |
| ------------- | ---------------------------------------------------- |
| HashSet       | 无序、唯一。基于HashMap实现，底层采用HashMap保存元素 |
| LinkedHashSet | 继承自HashSet，内部通过LinkedHashMap实现             |
| TreeSet       | 有序、唯一。基于自平衡的红黑树实现                   |

### Map

| className         | description                                                  |
| ----------------- | ------------------------------------------------------------ |
| HashMap           | JDK1.8以后，当链表长度大于阈值（默认为8）时，将链表转化为红黑树，以减少搜索时间。(允许Key为null) |
| LinkedHashMap     | 继承自HashMap，在HashMap基础上增加了双向链表，使得键值对插入顺序得以保存 |
| Hashtable         | 数组+链表组成的，数组是 HashMap 的主体，链表则是主要为了解决哈希冲突而存在的 |
| TreeMap           | 红黑树                                                       |
| ConcurrentHashMap | 线程安全                                                     |

#### HashMap与Hashtable区别

- Hashtable继承自Dictionary；HashMap继承自AbstractMap（两者均实现Map接口）

- Hashtable多提供了elements()、contains()

- Hashtable不允许Key或Value为空，而HashMap允许为空。

- 初始容量大学和每次扩充容量大学不同

  - Hashtable初始大小为11，每次扩充变为原来的2n+1
  - HashMap初始大小为16，每次扩充为原来的2倍

- 计算Hash方式不同

  - HashMap通过hash()——通过`key.hashCode`右移16位再与之异或运算`^`
  - Hashtable直接通过key.hashCode()得到Hash值

- 解决Hash地址冲突方式不同

  在JDK8中，HashMap解决方式如下：

  - 若冲突数量小于8，以链表方式解决
  - 大于或等于8，将冲突Entry转换位红黑树进行存储
  - 而又当数量小于6时，则又转化位链表存储

  而Hashtable则一直以链表方式存储

# 类加载

## 类动态加载

动态主要体现为三种方式：

| 加载方式 | 加载类型 |
| -------- | -------- |
| new关键字         | 隐式加载         |
| Class.forName()         | 显式加载         |
| 自定义classloader加载         | 显式加载         |

当`JVM`启动时，会形成三个类加载器组成的初始类加载器层次结构

- `bootstrap classloader`

  引导类加载器，负责加载`JDK`核心类

- `extension classloader`

  扩展类加载器，负责加载`JRE`扩展目录——`$JAVA_HOME/jre/lib/ext`

- `system classloader`

  应用类加载器，负责加载`java -classpath`中指定的类路径或`jar`

## 类加载过程

在默认的`JVM`设计中，`classloader`加载`Class`的过程大致如下：

1、检测此`Class`是否载入过（即在`JVM`中是否有此`Class`），如果有到第8,如果没有到第2

2、如果父亲 `classloader`不存在（没有父亲 ，那一定是`bootstrap classloader`了），到第4

3、请求父亲 `classloader`载入，如果成功到第8，不成功到第5

4、请求`JVM`让`bootstrap classloader`去加载，如果成功到第8

5、寻找`Class`文件（从与此`classloader`相关的类路径中寻找）。如果找不到则到第7.

6、从文件中载入`Class`，到第8.

7、抛出`ClassNotFoundException`.

8、返回`Class`.

> 我们可以看到，`classloader`加载`Class`的顺序是先委托其parent来加载，直到所有的parent都不能加载才自己去加载。
>
> Java使用`classloader`原因，除了可以达到动态性之外，其实最重要的原因还有安全性，体现在下面两点：
>
> 1,核心的类不可能被仿照(这主要是因为`先parent委托机制`的作用)，比如我们不可能加载一个自己写的放在`classpath`下的`java.lang.String`类，因为`Java`总是`parent`优先，在系统目录下面的`String类`总是被优先加载。
>
> 2,我们可以指定，控制到特定的载入点，不会误用，比如我只要加载目录的ABC下面的类

## 类加载顺序

`paret first`，双亲委派机制。核心的类不可被仿照。

## 参考

https://www.xuebuyuan.com/1760563.html

http://blog.itpub.net/145274/viewspace-591473/

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

# System

## identityHashCode

重写`hashCode() `方法后，以JVM内存地址为基准计算得到的HashCode，与原`hashCode()`方法无异。

``` java
    /**
     * Returns the same hash code for the given object as
     * would be returned by the default method hashCode(),
     * whether or not the given object's class overrides
     * hashCode().
     * The hash code for the null reference is zero.
     *
     * @param x object for which the hashCode is to be calculated
     * @return  the hashCode
     * @since   JDK1.1
     */
    public static native int identityHashCode(Object x);
```



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

>  2019/08/30
>
> ​	本人第一次尝试时是可行的。当时忘做笔记。再次尝试是却失败了。网络真是相当离奇的环境。

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