[TOC]

# 常见术语

当你渐渐深入时，你会注意到这些人或单位或用语常常出现在眼前，为了更好的学习`Java`特性，知道他们是干嘛的想干嘛也许也有用处。

## JSR

全称`Java Specification Requests`，意思是`Java规范提案`，是指向`JCP`提出新增标准化技术规范的正式请求。

## JCP

全称`Java Community Process`，是一个开放的国际组织，其职能是发展和更新。其维护的规范包括`J2ME`、`J2SE`、`J2EE`、`XML`等。

由`SUN`在1995年创立，由一个非正式的`Process`演进成数百名来自世界各地的成员共同监督`Java`发展的正式`Process`。

这里我不知道该如何表明`Process`的意思，觉得不论是组织还是程序都有歧义，所以用`Process`代替。

> 实际上在学习`@PostConstruct`有见过，特地总结在`Java`笔记里。

## JEP

- [JEP 索引](http://openjdk.java.net/jeps/0)

`JEP`，全称`JDK Enhancement-Proposal & Roadmap Process`。该组织主要负责`JDK`的维护，而`JCP`负责`Java API`的维护。

# 位运算

| 名称       | 符号 | 作用                         |
| ---------- | ---- | ---------------------------- |
| 位与       | &    | `1&1=1`、`0&0=0`、`1&0=0`    |
| 位或       | \|   | `1|1=1`、`1|0=1`、`0|0=0`    |
| 位非       | ~\|! | `~1=0`、`~0=1`               |
| 位异或     | ^    | `1^1=0`、`1^0=1`、`0^0=0`    |
| 有符号右移 | >>   | 若正数，高位补0，负数高位补1 |
| 有符号左移 | <<   | 丢弃最高位，低位补0          |
| 无符号右移 | >>>  | 不论正负，高位补0            |

- 取模性质

  取模`a%(2^n)`等价于`a&(2^n-1)`，所以在`HashMap`里的数组个数一定是2的乘方数。计算`key`值在哪个元素中的时候，就用位运算快速定位。

  由于位运算比乘法效率更高，所以往往使用位运算替代取模操作。

## 应用

- Java中的类修饰符、成员变量修饰符、方法修饰符的判断

  比如`Class`类中的`isAnnotation()`方法

  ```java
  /**
  * 判断是否为注解
  */
  public boolean isAnnotation() {
      return (getModifiers() & ANNOTATION) != 0;
  }
  ```

- `HashMap`和`ConcurrentHashMap`

- 权限控制或商品属性

  见`org.hc.learning.bitwise.Permission`

- 简单可逆加密

  比如异或运算`1^1=0`，`0^1=1`

### 位或运算

比如在NIO中，需要向selector注册两个事件。

``` java
channel.register(selector, SelectionKey.OP_WRITE|SelectionKey.OP_READ, buffer);
```

channel向selector注册了`SelectionKey.OP_WRITE|SelectionKey.OP_READ`，我们看源码这两个参数分别是：

``` java
public static final int OP_READ = 1 << 0;
public static final int OP_WRITE = 1 << 2;
```

在处理位运算的时候，要把`int`想象成8个都是0的格子，`1<<0`与`1<<2`分别是将1左移0位/2位。

- **OP_READ**	把1放到`0000 0000`的第0个位置，那么就是`0000 0001`。
- **OP_WRITE**	把1放到`0000 0000`的第2个位置，那么就是`0000 0100`。

至于这个或运算，就是两者结合而已，得到结果`0000 0101 `。

实际上，就是将每个格子当作一个开关，检查这个格子是否是1，1意味着打开了这个开关。

## 位运算的优劣势

- **优势:**	节省代码量，效率高，属性变动影响小
- **劣势:**	不直观

# 枚举

- 参考

  <a href='https://blog.csdn.net/echizao1839/article/details/80890490'>Java enum常见的用法</a>
  
  <a href='http://www.imooc.com/article/302066'>恕我直言，我怀疑你没怎么用过枚举</a>
  
  ## 使用枚举的原因
  
  在开发过程中，我们常常需要定义常量。譬如：
  
  ``` java
  @Data
  public class LikeLevel {
      public static final int LIKE = 1; // 喜欢
      public static final int LOVE = 2; // 爱
      public static final int VERY_LIKE = 3; // 非常喜欢
  }
  ```
  
  在调用时，常常
  
  ``` java
  public void doWhatByLevel(int like) {
      if (like == Like) {
          doLike();
      }
      if (like == LOVE) {
          makeLove();
      }
  }
  ```
  
  的确使用`int`也能正常使用，但是失去了对传入参数约束的作用。我们可以直接将`1`、`2`以及我们没有定义的值`99`传入，那么代码的行为可能会超出我们的预料。
  
  为了更好的约束程序员传入的变量。
  
  我们最好通过枚举来定义。
  
  ``` java
  public enum LikeEnum {
      Like, Love, VeryLike
  }
  ```

## 常量定义

``` java
public enum WeekDay{
    SUN,MON,TUE,WED,THT,FRI,SAT
}
```

## switch

枚举类型可用于`switch`

``` java
WeekDay weekday = WeekDay.SUN;
switch(weekday) {
        CASE SUN:break;
        CASE MON:break;
        CASE TUE:break;
        CASE WED:break;
        CASE THT:break;
        CASE FRI:break;
        cASE SAT:break;
}
```

## 定义成员变量

枚举依然是一个类，也可以定义成员方法甚至是实现接口

``` java
public enum Color{
    RED("red", 0), GREEN("green",1);
    
    private String name;
    private int index;
    
    public Color(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public String getName() {  
        return name;  
    }  

    public void setName(String name) {  
        this.name = name;  
    }  

    public int getIndex() {  
        return index;  
    }  

    public void setIndex(int index) {  
        this.index = index;  
    }
       
}
```

## 消除if-else

在第一个例子中，`doWhatByLevel(int)`使用`if-else`来完成不同的参数完成不同的操作的功能。

``` java
public void doWhatByLevel(int like) {
    if (like == Like) {
        doLike();
    }
    if (like == LOVE) {
        makeLove();
    }
}
```

实际上，通过***枚举+接口***，我们可以令我们的代码更加优雅。

``` java
public interface LikeAction {
	void do();
}

public enum LikeEnum implements LikeAction{
    Like {
        @Override
        public void do() {
            // 表白
        }
    },
    
    Love {
        @Override
        public void do() {
            // 做点什么吧
        }
    },
    
    VeryLike {
        @Override 
        public void do () {
            // 散花
        }
    }
}
```

这样子的话，我们可以将`doWhatByLevel(LikeEnum)`的代码去掉`if-else`，看官请看。是不是优雅多了。

``` java
public void doWhatLevel(LikeEnum likeLevel) {
    likeLevel.do();
}
```

# 面向对象

## private

2019-6-22

今天看到一个问题——private修饰的方法可以通过反射访问，那么private的意义是什么？

> 你遇到过哪些质量很高的 Java 面试？ - ZO01的回答 - 知乎
> https://www.zhihu.com/question/60949531/answer/579002882

ZO01设置了禁止转载，就不将他的回答copy上去了。个人以为确实是个直击灵魂的问题。

## 匿名内部类

### 一个冷知识:匿名内部类也会生成class文件

比如在`Hello.java`中有一个匿名内部类，那么编译后除了生成`Hello.class`，还会生成`Hello$1.class`。

# String

String是我们最常用的类之一，媲美基本数据类型。它具有两个特点：

- 驻留机制
- 不可变

## 驻留机制

字符串驻留[^String Interning]，是许多语言会采用的一种机制。不论在什么编程语言中，字符串都是最常用的数据类型。

在我们大多数编程场景中，使用String的频率往往超过使用long、float、double类型，即使是int与byte可能也无法比拟。

但相比于基本数据类型，String是一个很耗费内存的数据类型，为了减少反复分配内存的开销，JVM通常将String缓存再常量池[^Constant Pool]中。

- **intern()**

  在String API中，有一个`intern()`方法，它的作用是首先比较常量池是否有该字符串的值，如果有则返回常量池中的引用，如果没有则将字符串对象放入常量池，并返回在常量池汇中的内存地址。

## 不可变

不可变是指你无法修改一个String对象的值，除非你极端的使用反射机制。

``` java
 String text = "Hello";
Field value = String.class.getDeclaredField("value");
value.setAccessible(true);
value.set(text, value.get("Hello world"));
```

- 不可变的实现

  1. 将其内部的value变量声明为`private final`，以保证String不提供修改字符串的方法；
  2. 将其类型声明为`final`，以保证不存在子类可以通过继承重写其方法;

  **归根到底，String慎重的封装令使用者无法修改String的值。**

- 原因

  安全性。我们知道JVM在加载class文件中的常量到常量池中，这个过程是线程安全的。
  
  但是如果字符串是可变的，在一个线程从常量池中读取字符串后，然后对其修改，这就牵扯到了线程安全问题。为了降低开发复杂度、减少同步带来的性能开销，将String设计为不可变，可以说是一种最佳实践了。

# 克隆

- **参考**
- [浅谈Java中的深克隆和浅克隆（阿里面试）](https://www.cnblogs.com/liqiangchn/p/9465186.html)

克隆的目的是快速获取一个对象副本

| 克隆方式 | 描述                                                         |
| -------- | ------------------------------------------------------------ |
| 浅克隆   | 创建一个新对象，新对象属性和原对象完全一样，对于引用数据类型，仍指向原有属性所指向的对象的内存地址。 |
| 深克隆   | 创建一个新对象，引用数据类型也被克隆，不再指向原有对象地址。 |

## 实现深克隆

- **通过继承克隆接口**

  继承并重写`Cloneable`接口，并调用`super.clone()`，并对引用数据类型重复上述操作。

  ``` java
  @Override
  public Object clone() throws CloneNotSupportedException {
      //注意以下代码
      Teacher teacher = (Teacher)super.clone();
      teacher.setStudent((Student)teacher.getStudent().clone());
      return teacher;
  }
  ```

- **工具**

  1. 通过`ObjectInputStream`，`ObjectOutputStream`实现对象的深克隆；
  2. 借助`Spring`、`Hutool`的`BeanUtils`；

# Class Loader

## 加载方式

| 加载方式              | 加载类型 |
| --------------------- | -------- |
| new关键字             | 隐式加载 |
| Class.forName()       | 显式加载 |
| 自定义classloader加载 | 显式加载 |

## 类加载器

- **bootstrap classloader**

  引导类加载器(启动类加载器)，负责加载`JDK`核心类，由C++实现。

- **extension classloader**

  扩展类加载器，负责加载`JRE`扩展目录——`$JAVA_HOME/jre/lib/ext`

- **system classloader**

  应用类加载器，负责加载`java -classpath`中指定的类路径或`jar`

## 类加载过程

- 加载

  加载过程：通过全类名读取.class文件的二进制字节流，将字节流（代表的静态存储结构）转换为方法区的运行时数据结构，在内存中生成代表该类的对象。

- 链接

  - 验证
  - 准备
  - 初始化

- 初始化

### 动态加载

动态加载指的是在类被具体使用的时候，才去加载。

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
> 1.核心的类不可能被仿照(这主要是因为`先parent委托机制`的作用)，比如我们不可能加载一个自己写的放在`classpath`下的`java.lang.String`类，因为`Java`总是`parent`优先，在系统目录下面的`String类`总是被优先加载。
>
> 2.我们可以指定，控制到特定的载入点，不会误用，比如我只要加载目录的ABC下面的类

## 类加载顺序

`parent first`，双亲委派机制。核心的类不可被仿照。

## 双亲委派模型

如果一个类加载器收到了类加载请求，它首先委派给父加载器去完成，只有当父加载器无法完成时，子加载器采取自己加载。

- 打破双亲委派模型需要重新ClassLoader的loadClass()方法

# 引用 Reference

## 软引用

当要发生`OOM`前，回收软引用。

``` java
/**
* -Xms10m -Xmx10m -XX:+PrintGCDetails
*/
User user = new User();
user.setId("1");
user.setName("Houcheng");
SoftReference<User> userCache = new SoftReference(user);
user = null;

System.out.println(userCache.get());
System.gc();
System.out.println(userCache.get() == null);

List<byte[]> bytes = new ArrayList<>();
for (int i = 0; i < 10; i++) {
    try {
        System.out.println(userCache.get());
        bytes.add(new byte[1024 * 1024]);
    } catch (OutOfMemoryError e) {
        System.err.println(userCache.get());
    }
}
```

### 软引用应用场景

缓存

## 弱引用

**弱引用的概念**

> `JVM`发生`GC`时，一定回收弱引用。

强引用在置空时才可以被`JVM`的`GC`回收

``` java
Object obj = new Object();
obj = null;
```

但手动置空对象对于程序员来说，**繁琐且违背自动回收的思想。**平常情况下，手动置空不需要程序员来做，因为在Java中，对于调用它的方法执行完毕后，指向它的引用会从栈帧中“pop up”。下次`GC`时将回收它。

### 弱引用的应用场景

- **ThreadLocal**
- **WeakHashMap**

<hr>

- **Cache**

  但也有例外，缓存对象正是程序员运行需要的，那么只要程序运行，Cache对象的引用就不会被回收。随着Cache中的Reference越来越多，`GC`无法回收的对象也会越来越多。

  由于无法被自动回收，那就需要程序员手动释放这些引用占据的内存，而这却违背了`GC`本质——自动回收可回收的对象。

  于是就有`WeakReference`。

- **Example**

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

- **参考**

[WeakReference 学习和使用]([WeakReference 学习和使用_眼界决定境界-CSDN博客_weakreference使用](https://blog.csdn.net/qq_33663983/article/details/78349641))

## 虚引用

- 虚引用唯一的应用场景

能令对象在被垃圾回收时收到一个系统通知。

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

# Object

## finialize

垃圾回收器准备回收时，先调用`finalize()`。

> 知乎 [Pandora](https://www.zhihu.com/people/pandora-5-21)
>
> 主要是对一些非Java method开辟的内存，如jni调用native method，做资源释放前置处理，当然方法体内处理方法还是调用c/c++的析构函数

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

# 格式化

作为一名`Java`程序员，一定经常需要对各种数据类型进行格式化。以下来温故以下`Java`中的`Format API`。

## Format

我们最常用的`SimpleDateFormat`和`DecimalFormat`都是抽象类`Format`的基类。

采用`Template patter`，如果我们需要实现自己的格式化类，我们需要继承`Format`并实现

- **StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos)**
- **parseObject(String source, ParsePosition pos)**

在实际开发中，我们无需实现自己的格式化类。我们只要知道常规用法就好了，所以也不对`Format`进行深入分析了。

## DecimalFormat 数字格式化

- 参考

  https://www.jianshu.com/p/b3699d73142e

| 符号 | 位置 | 本地化 | 含义                             |
| ---- | ---- | ------ | -------------------------------- |
| 0    | 数字 | true   | 阿拉伯数字                       |
| #    | 数字 | true   | 阿拉伯数字，如果不存在则显示为空 |
| E    | 数字 | true   | 分割科学计数法法的尾数和指数     |

当然除了以上有意义的符号，可以在`pattern`中添加任意字符串来定义格式。

### `0`和`#`

这应该是我们最常用的符号

``` java
 /**
  * 上面的代码就是网上很经典的案例，下面我们来分析另外的一个值
  */      
pi=12.34567;
//取一位整数
System.out.println(new DecimalFormat("0").format(pi));//12
//取一位整数和两位小数
System.out.println(new DecimalFormat("0.00").format(pi));//12.35
//取两位整数和三位小数，整数不足部分以0填补。
System.out.println(new DecimalFormat("00.000").format(pi));// 12.346
//取所有整数部分
System.out.println(new DecimalFormat("#").format(pi));//12
//以百分比方式计数，并取两位小数
System.out.println(new DecimalFormat("#.##%").format(pi));//1234.57%

/**
 * 扩展，如果是其他的数字会是下面的效果
 */
pi=12.34;
//整数
System.out.println(new DecimalFormat("6").format(pi));//612
System.out.println(new DecimalFormat("60").format(pi));//612
System.out.println(new DecimalFormat("06").format(pi));//126
System.out.println(new DecimalFormat("00600").format(pi));//00126
System.out.println(new DecimalFormat("#####60000").format(pi));//00126
//小数
System.out.println(new DecimalFormat(".6").format(pi));//12.6
System.out.println(new DecimalFormat(".06").format(pi));//12.36
System.out.println(new DecimalFormat(".60").format(pi));//12.36
System.out.println(new DecimalFormat(".0600").format(pi));//12.3406
System.out.println(new DecimalFormat(".6000").format(pi));//12.3406
System.out.println(new DecimalFormat(".600000##").format(pi));//12.340006
```

那么在使用数字时，即`0`或其他数字，在`source`位数不满时，填充`pattern`中的数字。

### 科学计数法 E

``` java
pi = 123456789.3456;
System.out.println(new DecimalFormat("0E0").format(pi));//1E8
System.out.println(new DecimalFormat("0E00").format(pi));//1E08
System.out.println(new DecimalFormat("#E0").format(pi));//.1E9
System.out.println(new DecimalFormat("##E0").format(pi));//1.2E8
System.out.println(new DecimalFormat("###E0").format(pi));//123E6
System.out.println(new DecimalFormat("####E0").format(pi));//1.235E8
System.out.println(new DecimalFormat("#####E0").format(pi));//1234.6E5
System.out.println(new DecimalFormat("######E0").format(pi));//123.457E6
System.out.println(new DecimalFormat("#######E0").format(pi));//12.34568E7
System.out.println(new DecimalFormat("########E0").format(pi));//1.2345679E8
System.out.println(new DecimalFormat("#########E0").format(pi));//123456789E0
System.out.println(new DecimalFormat("##########E0").format(pi));//123456789.3E0
```

## SimpleDateFormat 日期格式化

### 定义到毫秒

其中大写的`S`表示毫秒。

``` java
new SimpleDateFormat("yyyyMMddhhmmssSSSS");
```

### 线程安全问题

- 参考

  https://www.cnblogs.com/zemliu/p/3290585.html

  https://blog.csdn.net/qq_42361748/article/details/88661408

在《阿里巴巴开发手册》中，我们使用`SimpleDateFormat`避免频繁创建对象实例，会将它定义为一个静态变量。

但`SimpleDateFormat`内部有个`Calendar`对象引用。

``` java
protected Calendar calendar;
```

我们在看`SimpleDateFormat`的`Date parse(Object source)`

``` java
Date parse() {

  calendar.clear(); // 清理calendar

  ... // 执行一些操作, 设置 calendar 的日期什么的

  calendar.getTime(); // 获取calendar的时间

}
```

对并发编程有一定了解的同学很容易发现这会导致线程安全问题。

所以`SimpleDateFormat`的开发者建议我们为每一个线程创建一个实例。

解决办法有很多，比如每次创建一个`SimpleDateFormat instance`、`synchronized`、`ThreadLocal`，我们需要根据实际情况选择合适的解决方案。

# 时间

## JDK8

在JDK1.8中引入了一些新的格式化工具，今天为大家介绍个人在工作中有用到的。

### DateTimeFormatter

`JDK8`引入了新的`DateTimeFormatter`，它是一个**线程安全**的时间日期格式化类。

在只有一个`SimpleDateFormat`实例的情况下，它将在多线程环境下共用同一个成员变量——`Calendar`对象，所以在多线程环境下会出现一些奇怪的问题。

- 我们可以为方法中创建`SimpleDateFormat`对象，确保其为线程私有；
- 也可以结合`threadLocal`，令每个线程持有自己的`SimpleDateFormat`对象；
- 最后我们可以直接使用`JDK1.8`中的`DateTimeFormatter`。

但注意`DateTimeFormatter`对格式的处理在毫秒后与`SimpleDateFormatter`有些许不同，在实际开发中我们需要确保我们的结果为业务所需。

``` java
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
LocalDateTime now = LocalDateTime.now();
System.out.println(formatter.format(now));
```

### LocalDateTime

在使用`DateTimeFormatter`来格式化时间时，我们同样需要使用与其配套的一系列时间工具。

比如`LocalDateTime`，它可以直接根据操作系统获取当地时间。

首先它通过静态方法`now()`来获取时间。

``` java
public static LocalDateTime now(Clock clock) {
    Objects.requireNonNull(clock, "clock");
    final Instant now = clock.instant();  // called once
    ZoneOffset offset = clock.getZone().getRules().getOffset(now);
    return ofEpochSecond(now.getEpochSecond(), now.getNano(), offset);
}
```

它将返回一个不可变对象。

当通过`now()`返回对象后，其不再随时间而改变

``` java
System.out.println("------------ 查看LocalDateTime对象是否随时间变化 ------------");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime last = LocalDateTime.now();
        String lastValue = formatter.format(last);
        System.out.println("last第一次格式化:" + lastValue);
        // 线程沉睡一秒
        Thread.sleep(1000);
        // 当通过now()获取对象实例后, 不随时间变化
        String lastValueSecond = formatter.format(last);
        System.out.println("last第二次格式化:" + lastValueSecond);
        LocalDateTime now = LocalDateTime.now();
        String nowValue = formatter.format(now);
        System.out.println("now 第一次格式化:" + nowValue);
        System.out.println("LocalDateTime对象是否随时间变化:" + nowValue.equals(lastValue));
```

- 多线程环境下更新`lastAccess`

```java
import lombok.SneakyThrows;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalDateTimeInConcurrency {

    static LocalDateTime lastAccess = LocalDateTime.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss'Z'");
    static final Integer threadNum = 5;

    public static void main(String[] args ) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        for (Integer i = 0; i < threadNum; i++) {
            executorService.execute(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    while (true) {
                        // synchronized (lastAccess) { // 由于lastAccess对象的引用不断在改变，所以依然会出现线程安全问题
                        synchronized (LocalDateTimeInConcurrency.class) {
                            String format = lastAccess.format(formatter);
                            System.out.println(Thread.currentThread().getName() + " get last Access:" + format);
                            lastAccess = LocalDateTime.now();
                            Thread.sleep(1000);
                        }
                    }
                }
            });
        }
    }
}
```

### ZonedDateTime

而`ZonedDateTime`是个可以设置时区的时间工具，如下我们可以获取系统时区。用法与`LocalDateTime`类似。

``` java
/** 通过Instant创建ZonedDateTime对象
*/
Instant now = Instant.now();
ZonedDateTime zondeDateTime = now.atZone(ZonedId.systemDefault());
System.out.println(zonedDateTime);
System.out.println(zonedDateTime.format(formatter));
```

### Instant

获取时间戳，与`System.currentTimeMillis()`效果相同。

``` java
System.out.println("------------ Instant获取时间戳");
System.out.println("Instant.now().toEpochMilli()" + Instant.now().toEpochMilli());
System.out.println("System.currentTimeMillis:" + System.currentTimeMillis());
```

### 转化

``` mermaid
graph LR
	String -- DateTimeFormatter--> LocalDateTime
	String -- DateTimeFormatter.withZone--> ZonedDateTime
	LocalDateTime -- atZone--> ZonedDateTime
	ZonedDateTime -- toLocalDate--> LocalDateTime
	Instant -- ofInstant --> ZonedDateTime
	ZonedDateTime -- toInstant --> Instant 
	LocalDateTime -- ZonedOffset--> Instant
	Instant -- ZonedOffset --> LocalDateTime
```

# JDBC

## 注册驱动

### JDBC4.0

在`JDK6/JDBC4.0`之后，注册数据库驱动无需再通过

``` java
Class.forName("com.mysql.jdbc.Driver");
```

## Blob

在数据库中可以使用`Blob`来存储字节数组。但通常的做法是存储图片的`url`或者`uri`，查看图片时后端返回结果后，浏览器对图片再次请求。

- **读取**

  通常情况下，我们要将`Blob`转换成`byte[]`再进行处理：

  ``` java
  private byte[] readBlob(Blob blob) throws IOException, SQLException{
      InputStream input = blob.getBinaryStream();
      int i = 0;
      byte[] buffer = new byte[1024];
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      while((i = input.read(buffer)) > 0) {
          output.write(buffer, 0, i);
      }
      byte[] bytes = output.toByteArray();
      input.close();
      output.close();
      return bytes;
  }
  ```

## 异常

- `A ResourcePool could not acquire a resource from its primary factory or source`

  在部署公司某系统连接`oracle`时，`c3p0`连接池由于神秘原因无法从数据库服务器中获取连接，导致出现该错误。

  经过百度与同事帮助，分析原因为`JDK`、`oracle`、`c3p0连接池`及相关数据库连接驱动(`ojdbc6`、`ojdbc7`)之间存在差异造成的。

  最后，听取同事建议，将数据库连接池切换为`Druid`后，项目成功运行。

# JAR

## META-INF

### MANIFEST.MF

在`Jar`中，有一个`META-INF`目录，该目录中有一个`MANIFEST.MF`文件。该文件主要用于描述`Jar`的信息。

以下为`struct.jar`的`MANIFEST.MF`内容:

``` text
## 版本信息
Manifest-Version: 1.0
## 创建者
Created-By: Apache Ant 1.5.1
Extension-Name: Struts Framework
Specification-Title: Struts Framework
Specification-Vendor: Apache Software Foundation
Specification-Version: 1.1
Implementation-Title: Struts Framework
Implementation-Vendor: Apache Software Foundation
Implementation-Vendor-Id: org.apache
Implementation-Version: 1.1
## 依赖Jar 应用程序或类加载器通过该值构建内部类搜索路径
Class-Path:  commons-beanutils.jar commons-collections.jar commons-dig
 ester.jar commons-logging.jar commons-validator.jar jakarta-oro.jar s
 truts-legacy.jar
```

其他关键信息

- **Main-Class:**	定义`Jar`的主类

#### 签名

在`Jar`中可以定义签名，以下为`mail.jar`：

``` text
Name: javax/mail/Address.class
Digest-Algorithms: SHA MD5 
SHA-Digest: AjR7RqnN//cdYGouxbd06mSVfI4=
MD5-Digest: ZnTIQ2aQAtSNIOWXI1pQpw==
```

这段内容中定义类签名的类名，计算摘要的算法名以及摘要。通过摘要我们可以确定代码是否被纂改过。

# 代理模式

## JDK动态代理

> “代理无处不在，例如在公司要上外网，要在浏览器里设置一个HTTP代理...”
>
> ——《架构探险》

- 原因

  `Java`不支持动态性，无法在运行时修改一个类。

- 涉及技术

  类加载、动态代理等。

- 应用场景

  热部署、`AOP`。

- 意义

  例子：

  在某些函数（方法）调用前后加上日志目录；

  给某些函数（方法）加上事务支持；

  给某些函数（方法）加上权限控制；

  在笔者看来，仿佛编程分为`纵向编程`与`横向编程`。

  我们从事的关于业务逻辑上的代码编写便是纵向编程。而这些需要依赖“动态技术”的，<u>在不修改源代码情况下令程序流程发生改变的代码编写就是横向编程。</u>

  也就是为了保证程序的**开闭原则**。

### UML

> - **2018-09-01**
>
>   关于为什么JDK动态代理需要接口：
>
>   在给一个VB项目添加非功能性需求时，比如捕捉异常，断线重连......一下就想到使用代理模式，我在调用一个对象前首先需要代理对象拥有和被代理对象一样的方法，那么当然第一就想到`inteface`啦，然而VB好像没有`interface`......

``` mermaid
classDiagram

IHelloWorld <-- HelloWorld : implements
IHelloWorld : sayHello()
LoggerHandler *-- Logger : call
LoggerHandler *-- HelloWorld : call
HelloWorld : sayHello()
InvocationHandler <-- LoggerHandler : implements
InvocationHandler : invoke()

```

- **过程解析**
  1. 将被代理对象`HelloWorld`作为参数传给`LoggerHandler`；
  2. 在运行时动态生成一个实现`IHelloWorld`的类`class`；
  3. 通过类加载器动态将该类加载到`JVM方法区`；
  4. 再通过该类生成一个代理对象；
  5. 在调用代理对象`sayHello()`时，通过`LoggerHandler`的`invoke()`来调用其`sayHello()`；

### InvocationHandler

动态生成的代理对象在调用方法时，将调用该接口中的`invoke`方法。

- **LoggerHandler**

``` java
/**
 * 动态代理 例子
 * @author Administrator
 *
 */
public class LoggerHandler implements InvocationHandler{
	
	/**
	 * 被代理的对象
	 */
	private Object target;
	
	public LoggerHandler( Object target ) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("startLog()");
		Object result = method.invoke(target, args);
		System.out.println("endLog()");
		return result;
	}

}
```

- **调用过程**

``` java
import java.lang.reflect.Proxy;

public class Main {

	public static void main(String[] args) {
		IHelloWorld helloWorld = new HelloWorld();
		LoggerHandler handler = new LoggerHandler(helloWorld);
		/**
		 * 动态生成一个IHelloWorld对象 proxy
		 */
		IHelloWorld proxy = (IHelloWorld) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(),	// 获得当前类加载器
				helloWorld.getClass().getInterfaces(),
				handler);
		proxy.sayHello();
	}

}
```

### 局限

- 被代理的类必须实现接口

## 其他

- 参考
- [妈蛋！空降的老大叫写得代码全部都得要Mock，看来得996了 - 不加班程序员的文章 - 知乎](https://zhuanlan.zhihu.com/p/357610982)

除了`JDK动态代理`外，代理模式还有其他的实现方式：

- 自定义类加载器
- 动态修改字节码

市场上许多`Mock工具`运用了代理模式。其中像`PowerMock`通过自定义类加载器实现，像`JMockit`、`TestableMock(阿里系)`则通过修改类的字节码实现。

两种方式都将修改类的字节码，但是`自定义类加载器`通过完全接管类的加载过程来实现，而`动态修改字节码`则在类加载完成后对字节码文件进行“二次修改”。

> - **Mock工具**
>
>   模拟工具

### CGLib

像`Spring`、`Hibernate`均使用`CGLib`来实现其切面编程。

- 参考
- [深入理解CGLIB动态代理机制](https://www.jianshu.com/p/9a61af393e41?from=timeline&isappinstalled=0)

> 使用CGLIB创建代理速度较慢，但创建代理后运行速度非常快，JDK动态代理正好相反。

#### 局限

- 被代理的类不能被`final`声明
- 被代理的方法不能被`final`声明



# FAQ

## Java高编译低运行问题

- 参考
- [关于JDK1.7中keyset错误！NoSuchMethodErrorjava.util.concurrent.ConcurrentHashMap.keySet](https://developer.aliyun.com/ask/250705?spm=a2c6h.13524658)
- [Java高编译低运行错误(ConcurrentHashMap.keySet)](https://www.cnblogs.com/superhedantou/p/5693113.html)

在`JDK8`中`ConcurrentHashMap`，新增内部类`KeySetView`，在低版本环境中运行时会抛出该问题。

## StackOverflowError：正则表达式栈溢出错误

- [StackOverflowError：正则表达式栈溢出错误](https://www.jianshu.com/p/87d0175e1aed)

今天遇到了这个错误，但是这只是表面现象，根本原因：**是我将图片的字节数组通过`JSONObject`包装后，再使用日志框架打印出来。**