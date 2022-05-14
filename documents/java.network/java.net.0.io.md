[toc]

> 不论是那种网络IO模型，都是只其accept连接的方式，底层通信依然是socket。

# I/O in Java

作为Java开发者，我们最为常见的I/O模型是：

| I/O Model     | Class | Support Version |
| ------------- | ----- | --------------- |
| 同步阻塞式I/O | BI/O  |                 |
| I/O多路复用   | NI/O  | Java 1.4        |
| 异步I/O       | AI/O  | Java 1.7        |

# BIO

`blocking io`。

在Java中，BIO[^bio]是面向流的IO，其每次从流中读一个或多个字节，直至读取所有字节。而且流是阻塞的，当一个线程读取或写入内容时，线程在此期间不能做其他事情。

[^bio]: java.io.*

# NIO

`no-blocking io`/`new io`。

在JDK1.4后引入，提供高速、面向块的I/O。

在NIO中，线程向某一通道发送请求读取数据，但是仅能读取目前可用的数据，若没有数据则获取不到任何数据。在这一过程中，线程不会被阻塞，而不是直至数据被允许读取。

- NIO与BIO的区别

  | NIO                                        | BIO                    |
  | ------------------------------------------ | ---------------------- |
  | 面向块(缓冲区)                             | 面向流                 |
  | 非阻塞                                     | 阻塞                   |
  | 通过selecors可以令一个线程监视多个输入通道 | 一个线程监视一个Socket |

另外，JDK中的NIO是一种全双工模式，就像TCP中的endpoint可以同时发送和接收网络包，它既可以读取，又可以写入数据。

## 重要概念

- Selector
- Channel
- Buffer
- SelectionKey

### Selector

选择器，线程将向Selector对象注册关注的Channel与IO事件。

``` java
Selector selector = Selector.open();
ServerSocketChannel channel = ServerSocketChannel.open();
channel.bind(new InetSocketAddress(9999));
// 通道向selector注册accept事件
channel.register(selector, SelectionKey.OP_ACCEPT);
```

### Channel

线程通过通道与操作系统进行交互（读取数据与写入数据）。

在Java的NIO编程中：

- SelectableChannel是Channel的父类
- ServerSocketChannel是Server的监听通道。通过ServerSocketChannel，Server才能向操作系统注册支持多路复用IO的端口监听。支持TCP与UDP。
- SocketChannel是Client对应的通道。
- 通道中的数据总是先读到或者写入Buffer中。

### Buffer

[Buffer](#Buffer)

### SelectionKey

在Selector中说道：我们需要向Selector对象注册两件事：

- 关注的通道
- 通道关注的IO事件

而SelectionKey就是Java对NIO事件的定义集合：

|                           | OP_READ | OP_WRITE | OP_CONNECT | OP_ACCEPT |
| ------------------------- | ------- | -------- | ---------- | --------- |
| ServerSocketChannel       | N       | N        | N          | Y         |
| Server定义的SocketChannel | Y       | Y        | N          | N         |
| Client定义的SocketChannel | Y       | Y        | Y          | N         |

- 获取Key

  ``` java
  // 阻塞方法: 只有当至少一个注册的时间发生时才会继续执行
  selector.select();
  Set<SelectionKey> selectionKeys = selector.selectedKeys();
  Iterator<SelectionKey> iterator = selectionKeys.iterator();
  while (iterator.hasNext()) {
      SelectionKey next = iterator.next();
      // 必须显式remove, 否则事件无法被清理，始终保留在selectedKeys中
      iterator.remove(); 
      // ..
  
  }
  ```

- 取消Key

  ``` java
  key.cancel();
  ```

- 常见API

  ``` java
  // 获取Buffer对象
  key.attachment();
  ```

#### OP_READ

当操作系统读缓冲区有数据可读时就绪，仅当有就绪时才发起读操作。

#### OP_WRITE

当操作系统写缓冲区有空闲空间时就绪，所以没必要的话无需注册该事件。

但如果时写密集型的任务，比如文件下载，缓冲区很可能满，就需要注册该事件，同时写完数据后取消注册。

#### OP_CONNECT

当SocketChannel.connect()请求成功后就绪，该操作只给客户端使用。

#### OP_ACCEPT

当接收到一个客户端连接时就绪，该操作只给服务器使用。

# Buffer

Buffer用于与NIO通道的交互。

- capacity
- position
- limit

## 重要属性

### Capacity

Buffer实际上是Java对操作系统缓存区的封装。而缓存区实际就是操作系统用于读写数据的一片内存。那么它必然有容量的限制，这个就是Capacity。

### Position

Buffer保存数据采用数组的结构，那么在读写时就需要使用一个变量来记录当前读写的位置——Position。

当Buffer是写模式时，每写一个字节，position就会+1。初始为0，最大为capacity-1。

``` java
byte[] buffer = new byte[1024];
int capacity = buffer.length;
int limit = capacity-1;
for(int position = 0; position <= limit ; position++){
    // write
}
```

当Buffer切换到读模式时，position会被重置为0。

### Limit

上面一段伪代码演示了向Buffer写入数据的过程，但是在读取时，需要考虑一个问题。写入的数据不一定会填充完Buffer，那么我们还需要一个变量来记录写入的有效位置，才能在读取时读取到有效的数据。

``` java
byte[] buffer = new byte[1024];
int capacity = buffer.length;
int limit = capacity-1;

// write mode
for(int position = 0; position <= limit ; position++){
	// write data
    limit++;
}

// read mode
for(int position = 0; position < limit ; position++) {
    // read data
}
```

## Buffer的分配

``` java
ByteBuffer byteBuffer = ByteBuffer.allocate(48);
CharBuffer charBuffer = CharBuffer.allocate(1024);

/* 
wrap: 将byte数组或byte数组的一部分包装成ByteBuffer
*/
byte[] bytes = new byte[1024];
ByteBuffer wrapBuffer = ByteBuffer.wrap(bytes);
// ByteBuffer wrapBuffer = ByteBuffer.wrap(bytes, 0 ,512);
```

### 直接内存

- HeapByteBuffer
- DirectByteBuffer

当我们使用`ByteBuffer.allocate(1024)`对Buffer指定内存大小时，JVM会在Java Heap上开辟一块空间存放Buffer。

``` java
/**
* ByteBuffer.allocate(1024)源码
*/
public static ByteBuffer allocate(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException();
        return new HeapByteBuffer(capacity, capacity);
}
```

这个Buffer是一个`HeapByteBuffer`。

在`flush`时，JVM会将HeapByteBuffer中的数据拷贝到**直接内存**中，剩下的再交给操作系统进行调度。

> - 直接内存
>
>   由操作系统直接管理的内存

NIO使用Native函数库直接分配堆外内存，通过`DirectByteBuffer`对象作为这块内存的引用进行操作。

- 基本用法

  ``` java
  ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
  // 写入buffer
  byteBuffer.put("Hello world".getBytes());
  // 切换为读模式
  byteBuffer.flip();
  /* 这里是HeapByteBuffer, 所以在读模式下，会将数据拷贝到直接内存上(即读取)
  * 如果是DirectByteBuffer, 略过拷贝，操作系统直接读取即可
  */
  channel.write(byteBuffer);
  ```


#### 使用直接内存的原因

使用直接内存，减少了从JVM拷贝数据到直接内存的过程，提高运行效率。

相比使用堆内存：

- 减少垃圾回收工作
- 加快复制速度
- IO读写性能优于普通堆内存

但是它也会带来问题：

- 直接内存不受JVM控制，发生内存泄漏难以排查
- 直接内存不适合存储复杂对象
- 申请空间耗费更高性能

#### Direct Memory OOM

直接内存也有大小限制，故也会发生JVM。

## API

- **flip()**	将Buffer从写模式切换到读模式。
- **clear()**	清理整个缓冲区
- **compact**	清理已读取的缓冲区数据
- **mark()**	标记当前的position
- **reset()**	将position回退到标记位置
- **rewind()**	重置position
- **remaining()**[^remaining]	返回limit - position，即有序记录的长度
- limit()\limit(int)

[^remaining]: 剩下的
