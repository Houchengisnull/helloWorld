[TOC]

# Optional

## 简述

`Optional` 类是一个可以为`null`的容器对象。如果值存在则`isPresent()`方法会返回`true`，调用`get()`方法会返回该对象。
`Optional`是个容器：它可以保存类型`T`的值，或者仅仅保存`null`。`Optional`提供很多有用的方法，这样我们就不用显式进行空值检测。
`Optional` 类的引入很好的解决空指针异常。

## 类方法  

| 序号 | 方法 & 描述                                                  |
| :--- | :----------------------------------------------------------- |
| 1    | **static <T> Optional<T> empty()**返回空的 Optional 实例。   |
| 2    | **boolean equals(Object obj)**判断其他对象是否等于 Optional。 |
| 3    | **Optional<T> filter(Predicate<? super <T> predicate)**如果值存在，并且这个值匹配给定的 predicate，返回一个Optional用以描述这个值，否则返回一个空的Optional。 |
| 4    | **<U> Optional<U> flatMap(Function<? super T,Optional<U>> mapper)**如果值存在，返回基于Optional包含的映射方法的值，否则返回一个空的Optional |
| 5    | **T get()**如果在这个Optional中包含这个值，返回值，否则抛出异常：NoSuchElementException |
| 6    | **int hashCode()**返回存在值的哈希码，如果值不存在 返回 0。  |
| 7    | **void ifPresent(Consumer<? super T> consumer)**如果值存在则使用该值调用 consumer , 否则不做任何事情。 |
| 8    | **boolean isPresent()**如果值存在则方法会返回true，否则返回 false。 |
| 9    | **<U>Optional<U> map(Function<? super T,? extends U> mapper)**如果有值，则对其执行调用映射函数得到返回值。如果返回值不为 null，则创建包含映射返回值的Optional作为map方法返回值，否则返回空Optional。 |
| 10   | **static <T> Optional<T> of(T value)**返回一个指定非null值的Optional。 |
| 11   | **static <T> Optional<T> ofNullable(T value)**如果为非空，返回 Optional 描述的指定值，否则返回空的 Optional。 |
| 12   | **T orElse(T other)**如果存在该值，返回值， 否则返回 other。 |
| 13   | **T orElseGet(Supplier<? extends T> other)**如果存在该值，返回值， 否则触发 other，并返回 other 调用的结果。 |
| 14   | **<X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)**如果存在该值，返回包含的值，否则抛出由 Supplier 继承的异常 |
| 15   | **String toString()**返回一个Optional的非空字符串，用来调试  |

- **of**

```java
//创建一个值为张三的String类型的Optional
Optional<String> ofOptional = Optional.of("张三");
//如果我们用of方法创建Optional对象时，所传入的值为null，则抛出NullPointerException如下图所示
Optional<String> nullOptional = Optional.of(null);
```

- **ofNullable**

```
//为指定的值创建Optional对象，不管所传入的值为null不为null，创建的时候都不会报错
Optional<String> nullOptional = Optional.ofNullable(null);
Optional<String> nullOptional = Optional.ofNullable("list");
```

- **empty**

```java
//创建一个空的String类型的Optional对象
Optional<String> emptyOptional = Optional.empty();
```

- **get**

如果我们创建的Optional对象中有值存在则返回此值，如果没有值存在，则会抛出 
NoSuchElementException异常。

```java
Optional<String> stringOptional = Optional.of("张三");
System.out.println(stringOptional.get());
```

- **orElse**

如果创建的Optional中有值存在，则返回此值，否则返回一个默认值

```java
Optional<String> stringOptional = Optional.of("张三");
System.out.println(stringOptional.orElse("zhangsan"));

Optional<String> emptyOptional = Optional.empty();
System.out.println(emptyOptional.orElse("李四"));
```

- **orElseGet**

```java
Optional<String> stringOptional = Optional.of("张三");
System.out.println(stringOptional.orElseGet(() -> "zhangsan"));

Optional<String> emptyOptional = Optional.empty();
System.out.println(emptyOptional.orElseGet(() -> "orElseGet"));

```

- **orElseThrow**

如果创建的Optional中有值存在，则返回此值，否则抛出一个由指定的Supplier接口生成的异常

```java
Optional<String> stringOptional = Optional.of("张三");
System.out.println(stringOptional.orElseThrow(CustomException::new));

Optional<String> emptyOptional = Optional.empty();
System.out.println(emptyOptional.orElseThrow(CustomException::new));

private static class CustomException extends RuntimeException {
   private static final long serialVersionUID = -4399699891687593264L;

   public CustomException() {
       super("自定义异常");
   }

   public CustomException(String message) {
      super(message);
   }
}
```

- **filter**

```java
Optional<String> stringOptional = Optional.of("zhangsan");
System.out.println(stringOptional.filter(e -> e.length() > 5).orElse("王五"));
stringOptional = Optional.empty();
System.out.println(stringOptional.filter(e -> e.length() > 5).orElse("lisi"));
```

- **map**

```java
//map方法执行传入的lambda表达式参数对Optional实例的值进行修改,修改后的返回值仍然是一个Optional对象
Optional<String> stringOptional = Optional.of("zhangsan");
System.out.println(stringOptional.map(e -> e.toUpperCase()).orElse("失败"));

stringOptional = Optional.empty();
System.out.println(stringOptional.map(e -> e.toUpperCase()).orElse("失败"));
```

个人觉得该例子难理解`map()`方法实际用处，见以下代码  

```java
public static String getChampionName(Competition comp) throws IllegalArgumentException {
    return Optional.ofNullable(comp)
            .map(c->c.getResult())
            .map(r->r.getChampion())
            .map(u->u.getName())
            .orElseThrow(()->new IllegalArgumentException("The value of param comp isn't available."));
}
```

- **flagMap**

如果创建的Optional中的值存在，就对该值执行提供的Function函数调用，返回一个Optional类型的值，否 
则就返回一个空的Optional对象.flatMap与map（Funtion）方法类似，区别在于flatMap中的mapper返回 
值必须是Optional，map方法的mapping函数返回值可以是任何类型T。调用结束时，flatMap不会对结果用Optional封装。

```java 
//但flatMap方法中的lambda表达式返回值必须是Optionl实例
Optional<String> stringOptional = Optional.of("zhangsan");
System.out.println(stringOptional.flatMap(e -> Optional.of("lisi")).orElse("失败"));

stringOptional = Optional.empty();
System.out.println(stringOptional.flatMap(e -> Optional.empty()).orElse("失败"));

```

- **ifPresent**

```java
Optional<String> stringOptional = Optional.of("zhangsan");
stringOptional.ifPresent(e-> System.out.println("我被处理了。。。"+e));

```

<hr>


- **参考**

- [Java8 如何正确使用 Optional](http://www.importnew.com/26066.html)

- [理解、学习与使用 JAVA 中的 OPTIONAL](https://blog.csdn.net/zknxx/article/details/78586799)

# Lambda

## removeIf

- **参考**
- [Java集合中removeIf的使用](https://blog.csdn.net/qq_33829547/article/details/80277956)
- [lambda 表达式中removeif遇到的问题](https://blog.csdn.net/PANGPANGPANGJIA/article/details/113645383)

在JDK8中，`Collection`以及其子类新加入了`removeIf()`，一行代码快速移除符合条件的元素。

``` java
List<String> list = new ArrayList<>();

list.add("one");
list.add("two");
list.add("three");

list.removeIf(ele -> ele.equals("two"));
```

但是需要注意的是，我们使用`Arrays.asList(T... a)`方法创建一个`List`对象调用`removeIf`时会抛出`java.lang.UnsupportedOperationException`。这是因为`Arrays.asList`返回的是其内部私有类`ArrayList`，这个类继承自`AbstractList`，而它并不支持`add`与`remove`。

# Default关键字

