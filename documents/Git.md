[TOC]

# 用法

## 回滚单个文件

- 查看日志

``` bash
git log #{filename}
git log -number #{filename}
```

- 根据日志信息选择回退版本

``` bash
git reset #{verison} #{filename}
```

# 异常

## Couldn't find remote ref refs/heads/xxx [core]

https://blog.csdn.net/Tyro_java/article/details/76064584