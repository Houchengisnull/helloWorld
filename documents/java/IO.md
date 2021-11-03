[TOC]



# 处理图片Base64

当获得一个文件的`byte[]`时，中间需要通过以字符串的形式传播，那我们需要以`Base64`作为加密字节流。若直接调用以下代码：

``` java
new String(bytes);
```

解析时生成的`bytes`与原`bytes`不一致，导致文件失效。

# InputStream与OutputStream相互转换

## ByteArray

### ByteArrayInputStream 

### ByteArrayOutputStream

``` java
XSSFWorkbook xssfSheets = new XSSFWorkbook();
ByteArrayOutputStream output = new ByteArrayOutputStream();
xssfSheets.write(output);
InputStream input = new ByteArrayInputStream(output.toByteArray);
```

> 简单，但无法边写边读，消耗内存依赖对象本身大小。

## Pipes 管道

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

### 注意

- `PipedInputStream`中存储数据的数组大小默认为1024，且使用过程中不可扩充，当一次性写入的数据超过这个数，则会有个`AssertionError`抛出。当然，我们可以在初始化`PipedInputStream`的时候进行设置。

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