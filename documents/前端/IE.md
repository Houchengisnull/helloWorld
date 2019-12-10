[TOC]

# IE兼容性设置

`Compatibility View Mode`

## Question 无法加载完整页面

2019-05-19

某银行柜员系统仅能于`IE`浏览器上使用，且每次使用都要于`Compatibility View Mode`选项中设置`IP`，否则无法加载完整页面。

### 原因

> 在浏览器的发展历史中，出现过多家厂商争夺市场，导致同时存在两种标准；后W3C出面制定了HTML5，总算结束纷争。而某些网站却仍采用原有标准，为了兼容在IE8  IE9中变出现了兼容浏览模式，英文为Compatibility View Mode。

https://www.cnblogs.com/o--ok/archive/2012/11/01/IE8IE9-Compabilityview.html

# IE8

## IE8不记录`iframe`元素初次加载时历史记录

> 2019月12月10日
>
> 系统遇到一个小问题。某按钮调用`history.go(-1)`时返回页面不正确。

花了一个下午定位问题，由于框架`jeesite`代码风格不一致，导致在思考问题时思路一致不正确。

一直以为只是前端问题——`IE历史记录机制`有*bug*。

在看`Controller`代码后才发现其两次返回视图不同：其中一个使用`iframe`元素，而`IE8.0`中无法记录其历史记录，导致问题产生。

### 前端代码

``` html
<iframe src='/sys'></iframe>
```

