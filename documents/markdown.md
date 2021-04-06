[toc]

# 画图

## 基本图形

```mermaid
graph TD
    id[带文本的矩形]
    id4(带文本的圆角矩形)
    id3>带文本的不对称的矩形]
    id1{带文本的菱形}
    id2((带文本的圆形))
```



## 线条

``` mermaid
graph LR
    A[A] --> B[B] 
    A1[A] --- B1[B] 
    A4[A] -.- B4[B] 
    A5[A] -.-> B5[B] 
    A7[A] ==> B7[B] 
    A2[A] -- 描述 --- B2[B] 
    A3[A] -- 描述 --> B3[B] 
    A6[A] -. 描述 .-> B6[B] 
    A8[A] == 描述 ==> B8[B] 
```

## 子流程

```mermaid
graph TB
    c1-->a2
    subgraph one
    a1-->a2
    end
    subgraph two
    b1-->b2
    end
    subgraph three
    c1-->c2
    end
```



- **参考**

  <a href='https://www.jianshu.com/p/b421cc723da5'>如何在Markdown中画流程图</a>

# 时序图

- **参考**
  - [Markdown 进阶技能：用代码画时序图](https://zhuanlan.zhihu.com/p/70261692)	`mermaid`用法
  - [MarkDown 时序图](https://www.jianshu.com/p/8f8e7fd20054)	`sequence`用法

<hr>

- **实线**	代表请求

- **虚线**	代表返回

- **末尾[X]**	异步消息，无需等待

  ``` mermaid
  sequenceDiagram
      participant 老板A
      participant 员工A
  
      老板A ->> 员工A : “在这里我们都是兄弟！”
      老板A -x 员工A : 画个饼
      员工A -->> 老板A : 鼓掌
  ```

  

## sequence

``` sequence
participant 老板
participant 员工

老板 - 员工 : 在不在
员工 -- 老板 : 在的怎么了?
Note left of 老板: 是不是又在摸鱼?
老板 - 员工 : 在干嘛?
员工 -- 老板 : 在认真工作!
Note right of 员工 : 怀疑我?

```

## mermaid

`mermaid`相比`sequence`功能更加丰富。

``` mermaid
sequenceDiagram
    小程序 ->> 小程序 : wx.login()获取code
    小程序 ->> + 服务器 : wx.request()发送code
    服务器 ->> + 微信服务器 : code+appid+secret
    微信服务器 -->> - 服务器 : openid
    服务器 ->> 服务器 : 根据openid确定用户并生成token
    服务器 -->> - 小程序 : token
```

### 激活框

``` mermaid
sequenceDiagram
    老板B ->> + 员工B : “你们要669！”
    员工B -->> - 老板B : 鼓掌
    
    老板B ->> + 员工B : “悔创本司！”
    员工B -->> - 老板B : 鼓掌
```

### 注解

``` mermaid
sequenceDiagram
    Note left of 老板A : 对脸不感兴趣
    Note right of 老板B : 对钱不感兴趣
    Note over 老板A,老板B : 对996感兴趣
```

### 循环

``` mermaid
sequenceDiagram
    网友 ->> + X宝 : 网购钟意的商品
    X宝 -->> - 网友 : 下单成功
    
    loop 一天七次
        网友 ->> + X宝 : 查看配送进度
        X宝 -->> - 网友 : 配送中
    end
```

### 选择

``` mermaid
sequenceDiagram    
    土豪 ->> 取款机 : 查询余额
    取款机 -->> 土豪 : 余额
    
    alt 余额 > 5000
        土豪 ->> 取款机 : 取上限值 5000 块
    else 5000 < 余额 < 100
        土豪 ->> 取款机 : 有多少取多少
    else 余额 < 100
        土豪 ->> 取款机 : 退卡
    end
    
    取款机 -->> 土豪 : 退卡
```

### 可选

``` mermaid
sequenceDiagram
    老板C ->> 员工C : 开始实行996
    
    opt 永不可能
        员工C -->> 老板C : 拒绝
    end
```

### 并行

``` mermaid
sequenceDiagram
    老板C ->> 员工C : 开始实行996
    
    par 并行
        员工C ->> 员工C : 刷微博
    and
        员工C ->> 员工C : 工作
    and
        员工C ->> 员工C : 刷朋友圈
    end
    
    员工C -->> 老板C : 9点下班
```

# UML

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

