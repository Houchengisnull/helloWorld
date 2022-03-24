[TOC]

- 参考

  https://www.cnblogs.com/zhaoqingqing/p/4620402.html

# 注释

`::`|`rem`

# 输入参数

``` bat
run.bat helloWorld
```

- **run.bat**

``` bat
echo %1
```

## 转义字符

- **参考**
- [解放双手！ bat 批处理自动化运行程序](https://zhuanlan.zhihu.com/p/125033413)
- [^是批处理中的转义符](http://blog.sina.com.cn/s/blog_65d50aab0102xeu2.html)

`^`是批处理中的转义字符，将特殊字符转换为普通字符。例如：

``` bat
@echo off
set /a a=123
set /a b=456
if %a% geq %b% (
  echo %a%^>=%b%
) else (
  echo %a%^<%b% 
)
pause
```

# &，&&，|与||

- **&**

  表示任务在后台执行

- **&&**

  表示前一条任务执行成功时，才执行后一条命令。

  例如：

  ``` bat
  echo 'hello' && echo 'world'
  ```

- **|**

  表示管道，上一条命令的输出作为下一条命令参数。

- **||**

  表示上一条命令执行失败后，才执行下一条命令。

# 搜索 Find

``` bat
type hello.txt | find "world"
```

用于在文件中搜索特定字符串，通常也作为条件判断的铺垫。

# 循环

> 在循环中，我们常常需要用`!`取代`%`

- **参考**

  [for/F命令详解](https://www.cnblogs.com/hinata-sen/p/7443007.html)

  [BAT读取文件](https://blog.csdn.net/lengyuezuixue/article/details/81387141)

- **查看SVN中更新文件**

  ``` bat
  # 将svn最近10条记录保存到history.txt
  svn log -v -l 10 >> history.txt
  
  # 假设项目名为demo,代码在SVN上保存路径如下
  # demo
  # | - src
  # 		| - com.hello.main
  # | - test
  for /f "tokens=2" %%i in ('findstr "demo"')
  ```


# 字符串处理

## 字符串替换

- **常规**

  ``` bat
  set a=Helloworld
  set b=world
  echo %a:world= Hugo%
  ```

- **循环中的字符串替换**

  关键在于使用两个`!`而非`%`将整个表达式引用起来，`:`后为被替换的字符串，`=`后为替换的字符串。

  ``` bat
  set relative=com.houc
  
  for /f "tokens=2" %%i in ('findstr "com" svn.txt') do (
  	set l=%%i
  	echo !l:%relative%=!
  )
  ```

## 字符串拼接

使用`"`将表达式引用起来时，表明这个表达式在做`字符串拼接`的工作。在循环体中时，`%`会失效，需要用`!`。

``` bat
set a=1
set b=99
set "combine=%a%+%b%=100"
echo %combine%
```

# 系统

## 查看系统信息

- **参考**
- [查看Windows系统的开机、关机时间](https://blog.csdn.net/chao199512/article/details/86152449)

这个命令可以帮助我们查看`windows`系统的开机、关机的时间。有时候系统自动或人为的被重启（关键开机或关机后没有人重新启动程序），导致我们的程序停止运行且日志没报错，这个命令可以帮助我们确认系统是否被重启。

``` bat
systeminfo
```

除此之外，我们还可以通过`windows`系统的`事件查看器`或`net statistics workstation`来查看系统的开机、关机时间。

# 查看帮助信息

`/?`

比如向查看`type`命令的帮助信息，则只需

``` bat
type /?
```

# 暂停

执行完毕后`cmd`不退出

`pause`

# 查找进程

- **FI** 过滤器

``` bash
tasklist /FI "PID eq 18272"
```

# 查看端口被占用

``` bash
netstat -aon|findstr #{port}
```

# 终止进程 taskkill

``` bash
taskkill -f -pid #{pid}
```

# Runas

## 用其他用户/权限运行指定的工具和程序

``` bash
# runas /profile /user:ourcomputer\ftpuser "C:Program Files\ftp.exe"
# runas /profile /user:mymachine\ftpuser "C:Program Files\ftp.exe \"${params}\"
```

- 使用管理员用户启动`cmd`

  ``` bat
  runas /user:administrator cmd
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

# 退出

- **ctrl + c**
- **exit**

# 文件

## 查看目录树

``` bat
# 默认只显示目录名称
tree
tree /D
# 显示文件名
tree /F
```

## 查看文件

``` bat
type hello.txt
```

## 复制 copy

``` bat
copy A.txt B.txt
```

