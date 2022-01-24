[toc]

- [Markdown 入门教程](https://www.wenjiangs.com/docs/markdown-introduction)

# 什么是Markdown

`Markdown`是现下程序员领域非常流行的文档格式。要我总结它的优点有三个：

- 简单
- 快速
- 实用

像`Github`、`码云`、`掘金`等技术网站已支持（并推荐）`Markdown`。

`Markdown`还可以转换成`word`、`PDF`等我们常用的办公软件格式。

# Markdown编辑器

## Typora

- **alt + /**	 查看源码

# 常见语法

## 目录

``` markdown
[toc]
```

> 有的编辑器可能不支持`[toc]`

## 标题

``` markdown
# 大标题
## 标题
### 小标题
```

## 换行

两个空格+回车。但是如果你结合了`Typora`(或者其他好用的编辑器)，你就可以直接换行了。

## 字体

*斜体文本*
_斜体文本_
**粗体文本**
__粗体文本__
***粗斜体文本***
___粗斜体文本___

## 线条

***

* * *

*****

- - -

----------

~~BAIDU.COM~~

<u>带下划线文本</u>

## 批注

大家好，[^小批注]的使用时这样子的。

[^小批注]: 我是批注的内容

# 列表

## 无序列表

* 第一项
  * 嘿嘿
  * 哈哈
* 第二项

> 实际上无序列表可以使用'-'、'*'、'+'

## 区块与代码

> 这是区块

- java

``` java
public class HelloWorld{
	public static void main(String[] args) {
        System.out.println("Hello world");
    }
}
```

- python

``` python
print('Hello world');
```

- sql

``` sql
-- 找出姓侯的学生
SELECT *
FROM student
WHERE name like '侯%'
```

# 插入超链接

- <a href='www.baidu.com'>百度</a>
- [谷歌](www.google.com)

## 业内跳转

[页内跳转](#页内跳转)

# 表格

| 表头   | 表头   |
| ------ | ------ |
| 单元格 | 单元格 |
| 单元格 | 单元格 |



# 绘图

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

- [mermaid 使用 （javascript 流程图 甘特图 序列图）](https://blog.csdn.net/Cribug8080/article/details/88595314)
- [mermaid工具](https://blog.csdn.net/liuxiao723846/article/details/83544588)

中文释义：美人鱼。

`node.js`实现的绘制流程图、甘特图、时序图的工具。且`mermaid`相比`sequence`功能更加丰富。

### 基本图形

```mermaid
graph TD
    id[带文本的矩形]
    id4(带文本的圆角矩形)
    id3>带文本的不对称的矩形]
    id1{带文本的菱形}
    id2((带文本的圆形))
```

### 线条

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

### 流程图

#### 子流程

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

### 时序图

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

---

``` mermaid
sequenceDiagram
    小程序 ->> 小程序 : wx.login()获取code
    小程序 ->> + 服务器 : wx.request()发送code
    服务器 ->> + 微信服务器 : code+appid+secret
    微信服务器 -->> - 服务器 : openid
    服务器 ->> 服务器 : 根据openid确定用户并生成token
    服务器 -->> - 小程序 : token
```

#### 激活框

``` mermaid
sequenceDiagram
    老板B ->> + 员工B : “你们要669！”
    员工B -->> - 老板B : 鼓掌
    
    老板B ->> + 员工B : “悔创本司！”
    员工B -->> - 老板B : 鼓掌
```

#### 注解

``` mermaid
sequenceDiagram
    Note left of 老板A : 对脸不感兴趣
    Note right of 老板B : 对钱不感兴趣
    Note over 老板A,老板B : 对996感兴趣
```

#### 循环

``` mermaid
sequenceDiagram
    网友 ->> + X宝 : 网购钟意的商品
    X宝 -->> - 网友 : 下单成功
    
    loop 一天七次
        网友 ->> + X宝 : 查看配送进度
        X宝 -->> - 网友 : 配送中
    end
```

#### 选择

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

#### 可选

``` mermaid
sequenceDiagram
    老板C ->> 员工C : 开始实行996
    
    opt 永不可能
        员工C -->> 老板C : 拒绝
    end
```

#### 并行

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

### UML

#### 类图

##### 类图的关系|线条

| Type   | Description |
| :----- | :---------- |
| `<|--` | 继承关系    |
| `*--`  | 组成关系    |
| `o--`  | 集合关系    |
| `-->`  | 关联关系    |
| `--`   | 实现连接    |
| `..>`  | 依赖关系    |
| `..|>` | 实现关系    |
| `..`   | 虚线连接    |

``` mermaid
classDiagram
  classA --|> classB : 继承
  classC --* classD : 组成
  classE --o classF : 集合
  classG --> classH : 关联
  classI -- classJ : 实线连接
  classK ..> classL : 依赖
  classM ..|> classN : 实现
  classO .. classP : 虚线连接
```

##### 类修饰符

```mermaid
classDiagram
class 形状 {
    <<interface>>
    定点数
    绘制()
}
```

##### 示例

- **示例1**

``` mermaid
classDiagram

IHelloWorld <-- HelloWorld : implements
IHelloWorld : sayHello()
LoggerHandler *-- Logger : call
LoggerHandler *-- HelloWorld : call
HelloWorld : sayHello()
HelloWorld : String who
InvocationHandler <-- LoggerHandler : implements
InvocationHandler : invoke()
```

- **示例2**

  ```mermaid
  classDiagram
  	鸟 --|> 动物 : 继承
    翅膀 "2" --> "1" 鸟 : 组合
    动物 ..> 氧气 : 依赖
    动物 ..> 水 : 依赖
    
  	class 动物 {
      <<interface>>
      +有生命
      +新陈代谢(氧气, 水)
      +繁殖()
  	}
  	
  	class 鸟 {
  		+羽毛
  		+有角质喙没有牙齿
  		+下蛋()
  	}
  	class 鸟 {
  		+羽毛
  		+有角质喙没有牙齿
  		+下蛋()
  	}
  ```

### 甘特图

``` mermaid
gantt
dateFormat  MM-DD
title BigPlan

section step 1
仔细观察 :active, p1, 10-28, 1d
悄悄观察 : p2, after p1, 1d
认真观察 : p3, after p2, 1d

section step 2
仔细制作 : p4, 11-01, 2d
认真制作 : p5, 11-02, 3d

```



