[TOC]

# stash

`stash` 备份当前工作区到Git栈

## 用法一 备份本地修改

``` bash
git stash 
git pull
git stash pop # 从Git栈中取出上次修改内容
```

## 用法二 放弃本地修改

``` bash
git reset
git pull
```



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