[TOC]



# Runas

允许用户用其他权限运行指定的工具和程序

``` bash
# runas /profile /user:ourcomputer\ftpuser "C:Program Files\ftp.exe"
```

## 参数

- profile 加载用户的配置文件。`/profile`为默认值
- /no profile 指定不加载用户配置文件
- /env 指定当前使用网络环境
- /trustlevel 指定应用程序所在的授权级别
- /showtrustlevel 查看可用的信任级别
- /user:Administrator 指定在其下运行程序的用户账户名称