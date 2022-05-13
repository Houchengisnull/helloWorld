[TOC]

# 基本概念

<u>在Java中，流指的是逻辑连续的数据</u>。如果我们流将看作河，那么河中的水就相当于流中的数据。

俗话说，水往低处走。那么在Java I/O流中数据，又从哪里来，哪里去呢？

首先我们要搞明白，在计算机科学中，I/O的意思是输入、输出。也可以说，I/O是计算机各组件通信的统称。顾名思义，Java I/O流的功能也就与计算机各组件输入/输出八九不离十了。

是的，Java I/O流也就是Java对数据及相关I/O操作的封装。需要注意的是，I/O流仅是Java中同步阻塞式I/O的封装。

| Language | I/O Model | Ecapsulated                            |
| -------- | --------- | -------------------------------------- |
| Java     | B I/O     | java.io(I/O流)                         |
| Java     | N I/O     | java.nio(Channel\ByteBuffer\Selectors) |
| Python   | B I/O     | I/O操作（I/O函数）                     |
| C/C++    | B I/O     | I/O操作（I/O函数）                     |
| GO       | B I/O     | bufio（ReadWriter）                    |

个人觉得，以流的概念来封装I/O操作，对于初学者来将固然生动形象，但非常脱离操作系统的实际工作方式。因为Java I/O流中的数据仅仅是逻辑上的连续，而不是物理或者时间上的连续。如果学习者计算机科学基础不够杂事，对其实际工作流程难以理解到位。

## 流的分类

- 按照流向
  - 输入流
  - 输出流
- 按照数据格式
  - 字节流
  - 字符流

## 核心功能

> 也许这就是封装的对于学习者不友好的地方，当你调用java.io中的`read()`/`write()`时，你不知道你的数据由实际交给了谁来处理，又度过了怎么样的经历。

### InputStream

#### read()

这是一个抽象方法，用于读取1个byte，并返回int。有趣的是，我们知道int有4个byte，而其返回值将忽略前3个byte，仅使用最后1个byte来保存数据。

在`FileInputStream`的实现中，

``` java
public int read() throw IOException {
    return read0();
}
```

其中，`read0()`是个native method，将从文件中读取一个byte。

### OutputStream

#### write(int  b)

同样是一个抽象方法，用于写入一个数据。与`InputStream.read()`方法一样，参数中的`int b`也只有最后1个byte有效。

# 常用I/O流

## ByteArray流

### ByteArrayInputStream

``` java
ByteArrayOutputStream output = new ByteArrayOutputStream();
ByteArrayInputStream input = new ByteArrayOutputStream(output.toByteArray());
```

### ByteArrayOutputStream

``` java
XSSFWorkbook xssfSheets = new XSSFWorkbook();
ByteArrayOutputStream output = new ByteArrayOutputStream();
xssfSheets.write(output);
InputStream input = new ByteArrayInputStream(output.toByteArray);
```

- OutputStream to ByteArrayOutputStream

  ``` 

# Pipes 管道

```java
PipedInputStream in = new PipedInputStream();
PipedOutputStream out = new PipedOutputStream(in);
new Thread(
  new Runnable(){
    public void run(){
      class1.putDataOnOutputStream(out);
    }
  }
).start();
class2.processDataFromInputStream(in);
```

> 支持边写边读

`PipedInputStream`中存储数据的数组大小默认为1024，且使用过程中不可扩充，当一次性写入的数据超过这个数，则会有个`AssertionError`抛出。当然，我们可以在初始化`PipedInputStream`的时候进行设置。

## Circular Buffers 循环缓存区

作为`PipedInputStream`和`PipedOutputStream`的一种替代方式，`CircularBuffer`有着更为简单的数据结构和使用方法，但是其并不是JDK自带的类需要额外引入

```xml
<!-- https://mvnrepository.com/artifact/org.ostermiller/utils -->
<dependency>
    <groupId>org.ostermiller</groupId>
    <artifactId>utils</artifactId>
    <version>1.07.00</version>
</dependency>
```



```java
CircularByteBuffer cbb = new CircularByteBuffer();
new Thread(
  new Runnable(){
    public void run(){
      class1.putDataOnOutputStream(cbb.getOutputStream());
    }
  }
).start();
class2.processDataFromInputStream(cbb.getInputStream());
```



## 参考

- [Java中3种OutputStream转InputStream的方法](https://www.jianshu.com/p/659a821d5118)
- [java中如何将OutputStream转换为InputStream](https://www.cnblogs.com/xiohao/p/5073832.html)

# FAQ

## 处理文件Base64

当读取文件的`byte[]`后，若中间需以`String`格式传输，那我们最好使用`Base64`来处理字节组数。

因为在编码转换的过程中，有可能导致字节数组与原来的字节数组不同。如果是文件的字节数组，这种情况会导致文件无效。