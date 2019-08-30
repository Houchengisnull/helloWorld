[TOC]

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