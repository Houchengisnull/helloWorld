[TOC]

# 从`win xp`至`win 7`

- 无法正常运行时，尝试将正确的`VBA6.dll`（比如正常运行的`xp`系统）复制至`VB98`目录

# ADO.recordSet

> 2019-09-09
>
> ​	上周发现一个问题：`DB2`某类型为`Decimal`的字段设置了`Scale`为4，导致5位小数数据保存时自动四舍五入。导致两系统数据尾数不一。

## NumericScale

保留多少位小数

## Precision

有效数字

# 数据源

## MSSQL

### MSSQL设置数据源端口

https://blog.csdn.net/wf824284257/article/details/53385641

# Runtime Error

## 3706

> 猜测为未找到相应插件。在安装`Visual Basic 6.0`后成功

### 参考

https://bbs.csdn.net/topics/10346100

## VB IDE :"mscomctl.ocx" Couldn't not be loaded

> 背景
>
> ​	程序从`win xp`迁移至`win7`或`win10`

### 解决方案

- 注册`mscomctl.ocx`

- 修改`*.vbp`文件

  将对该组件的应用版本修改为*2.0*

  ``` vb
  Object={831FDD16-0C5C-11D2-A9FC-0000F8754DA1}#2.0#0; MSCOMCTL.OCX
  ```

### 参考

https://www.e-learn.cn/content/wangluowenzhang/549358