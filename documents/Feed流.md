[TOC]

- **参考**

- [什么是Feed流](http://www.sohu.com/a/231371811_114819)

- [Feed](https://baike.baidu.com/item/Feed/15181)

- [feed流推荐系统设计](https://blog.csdn.net/xwc35047/article/details/82996808)

# 什么是Feed流

>  feed是将用户主动订阅的若干消息源组合在一起形成内容聚合器，帮助用户持续地获取最新的订阅源内容。feed流即持续更新并呈现给用户内容的信息流。

最近常听朋友说中了抖音的毒，一有时间就刷抖音，根本停不下来。刷朋友圈、逛微博，以及现在很火的短视频，我们每天有大量时间消耗在“feed流”中，并且刷的不亦乐乎。跟同行交流或看相关的产品文章，也经常会提到“feed流”。那么，究竟什么是feed流，回想了一下，似乎对feed流没有明确的认知，所以梳理了一下feed相关的内容。

## 维基百科

> a web feed (or news feed) is a data format used for providing users with frequently updated content. Content distributors syndicate a web feed, thereby allowing users to subscribe a channel to it

- feed是一种给用户持续提供内容的数据形式
- 是由多个内容提供源组成的资源聚合器，由用户主动订阅消息源并且向用户提供内容

## 百度百科

### 本意

饲料、喂养、`广播`、`新闻`

#### 口语

随你便

### 引申

用来接收该信息来源更新的`接口`

## 总结

- **feed是将用户主动订阅的若干消息源组合在一起形成内容聚合器，帮助用户持续地获取最新的订阅源内容。**严格按照上述定义来说，我们通常说的搜索结果、排序列表都不能算作feed流。

- “Feed”，在RSS订阅过程中引申为用来接收该信息来源更新的接口。

每个人可能由于自己知识存储量与知识存储方向不同而对Feed有不同理解，作为一名`程序员`出身的`工程师`，更倾向于第二种理解。

个人以为`工程师`是众多职业更高级的一种形式与众多职业`升级形态`的一种抽象描绘，即`程序员`与`工程师`并不等价,尽管有些人依然声称自己为`程序员`，但他们实际已经是高级形态的`工程师`了。

## 用途

是一种用于网上新闻、博客和其他Web内容的数据交换规范，起源于网景通讯公司（Netscape）的推送技术（push technology），一种将用户订阅的内容传送给他们的通讯协同格式（Protocol）。

# RSS

## 定义

RSS订阅是站点用来和其他站点之间共享内容的一种简易方式，即`Really Simple Syndication（简易信息聚合）`。

## RSS文件

一个RSS文件就是一段规范的XML数据，该文件一般以rss，xml或者rdf作为后缀。发布一个RSS文件（一般称为RSS Feed）后，这个RSS Feed中包含的信息就能直接被其他站点调用，而且由于这些数据都是标准的XML格式，所以也能在其他的终端和服务中使用，如PDA、手机、[邮件列表](https://baike.baidu.com/item/%E9%82%AE%E4%BB%B6%E5%88%97%E8%A1%A8)等。
