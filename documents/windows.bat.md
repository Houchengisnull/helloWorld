[TOC]

- 参考

  https://www.cnblogs.com/zhaoqingqing/p/4620402.html

# 注释

`::`

# 查看帮助信息

`/?`

比如向查看`type`命令的帮助信息，则只需

``` bat
type /?
```

# 暂停

执行完毕后`cmd`不退出

`pause`

# 查看端口被占用

``` bash
netstat -aon|findstr #{pid}
```

# 终止进程 taskkill

``` bash
taskkill -f -pid #{pid}
```

# Runas

允许用户用其他权限运行指定的工具和程序

``` bash
# runas /profile /user:ourcomputer\ftpuser "C:Program Files\ftp.exe"
# runas /profile /user:mymachine\ftpuser "C:Program Files\ftp.exe \"${params}\"
```

## 参数

| 参数 | 作用 |
| ---- | ---- |
|`/profile` |  加载用户的配置文件。`/profile`为默认值 |
|`/no profile` | 指定不加载用户配置文件 |
|`/user:username` |  指定在其下运行程序的用户账户名称 |
|`/savecred` | 记住密码 |

# 查找文件绝对路径 where

适用查找大部分常规软件的安装路径

``` bat
$ where python
C:\Users\admin\AppData\Local\Programs\Python\Python38\python.exe
```

# 调用外部程序 start

`start`

- 启动`cmd`

  ``` bat
  start
  ```

- 打开文件夹

  ``` bat
  # 打开当前文件夹
  start .
  # 打开文件夹
  start %path%
  ```

# 文件

## 复制 copy

``` bat
copy A.txt B.txt
```

