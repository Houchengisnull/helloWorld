[TOC]

# systemprofile

> 2019-10-2
>
> 配置`jenkins`在连接远程机器时出错。后经同事提醒`jenkins`配置私钥目录为`C:\WINDOWS\system32\config\systemprofile`。

其为用户配置的模版文件夹。

# 编码

## 查看编码 chcp

## 切换编码

- `win + r`打开输入`regedit`打开注册表
- 定位到`HKEY_CURRENT_USER\Console\%SystemRoot%_system32_cmd.exe`
- 修改`CodePage`内容

| number | encode |
| ------ | ------ |
| 936    | GBK    |
| 65001    | UTF-8    |
| 950  | Big-5 |

# 快捷键

## 关闭计算机 Alt + Ctrl + Del

## 关闭窗口 Ctrl + w

- ctrl + w
- alt + F4

## 打开任务管理器 Ctrl + Shift + Esc

# 切换执行身份

- `shift` + 右键

# 账户用户控制

`控制面板` - `系统安全` - `更改用户账户控制` 将级别修改至`Never remind`。

# 注册表

用于存储系统和应用程序配置信息。出现于`win 3.0`，活跃于`win 95`至今。

- `*.vbr`使用`Clireg32.exe`注册
- `*.ocx`、`*.dll`使用`RegSvr32.exe`注册

> `win7`系统下应该没这两个exe，同时本人将`xp系统`中`clireg32`复制到`win7`后依然无法正常注册。

## 注册32位组件

> 背景
>
> ​	程序从`win xp`迁移到`win 7`

由于部份组件必须使用32位程序注册，需要使用`C:\Windows\SysWOW64\regsvr32`。

## 参考

https://blog.csdn.net/dongzhe8/article/details/80598128

# XPMode

## 整合硬盘

在`Win7`上右键`.vmcx(XPMode启动文件)`中点击`设定`。于`整合功能`中选中使用盘符

### 参考

https://jingyan.baidu.com/article/ff42efa91023a3c19e220217.html

# 配置`ODBC`数据源

1. 进入`odbc数据源管理器`

   ```shell
   # odbcad32
   ```

2. 于`使用者数据源名称`|`系统资料来源名称`下点击`新增`

3. 输入`数据源名称`后，点击`数据源别名`后`新增`按钮

4. 设置数据库服务

   在`数据库来源`中填写`username`与`password`，

   在`TCP/IP`中填写数据库名称，主机名称（`database server url`）及端口号

## 注册32位数据源

使用`C:\Windows\SysWOW64\odbcad32.exe`

> 补充一下，根据网上搜索曾经看到的资料，可以这样理解:在win64位系统下，system32下存放的是64位的相关文件，之所以称作system"32",是为了兼容之前的程序，打个比方，可能之前的程序，在读写关键配置文件时，是把路径写死的，XXXX/system32/XXX，这种比较Low的做法在我等Low级别程序员中很常见，为了这些软件不至于无法使用，根正苗红的关键文件不管是64还是32位系统，其路径仍在system32下;而sysWOW64,它的意思是64位系统下为了解决一些兼容性问题而存放的文件目录。打个比方，system32是好人，sysWOW64，是好人……是好人堆里挑出来的。。就这样理解吧!

## SQL Server

### 设置SQL Server端口

``` text 
Connect String=Provider=SQLOLEDB.1;
Persist Security Info=False;
User ID=#{username};
Password=#{password};
Initial Catalog=#{schema};
Data Source=#{host},#{port};
Network Library=Dbmssocn
```

## DB2

### SQL AllocHandle On SQL_HANDLE_ENV Failed

由于`DB2 8.2`引入新的安全特性防止用户使用数据库——必须属于`DB2ADMIN`或`DB2USERS`用户组。

https://www-01.ibm.com/support/docview.wss?uid=swg21229860

## 参考 

https://blog.csdn.net/hou09tian/article/details/78586432

https://blog.csdn.net/herobox/article/details/52402260

# 远程桌面连接

`mstsc`

# 查看资源被占用

`任务管理器 > 性能 > 资源管理器`

# Windows 10

## 系统配置

`MSConfig`

### 启动

点击`打开任务管理器`手动关闭开机启动项。

