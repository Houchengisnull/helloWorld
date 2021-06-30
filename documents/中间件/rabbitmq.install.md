[toc]

# 安装Erlang

> `Erlang`是一种通用的面向并发的编程语音。

`Rabbit MQ`由`Erlang`实现，对`Erlang`运行环境有依赖。

配置后环境变量后，可通过以下方式检查是否安装成功

``` bat
erl
```

# 安装Rabbit MQ

## 配置环境变量

将`%Rabbit MQ%\sbin`配置在`path`下，

可通过`rabbitmqctrl status`检查是否安装成功。

如果开启MQ服务识别，出现`BOOT FAILED`

需要检查是否将`Rabbit MQ`安装在含中文字符或空格的路径下。

## 安装管理插件

``` bat
rabbitmq-plugins enable rabbitmq_management
```

- 出现`Applying plugin configuration to rabbit@XXX failed`

  首先，停止MQ服务后进行重装，

  ``` bat
  rabbitmq-service stop
  rabbitmq-service remove
  rabbitmq-service install
  rabbitmq-service start
  # 重新安装插件
  rabbitmq-plugins enable rabbitmq_management
  ```

- 默认端口：`15672`

- 默认用户：`guest`

- 默认密码：`guest`

## FAQ

- [安装RabbitMQ出现init terminating in do_boot的解决方法 - 简书 (jianshu.com)](https://www.jianshu.com/p/264717dcbd1f)

- [windows 安装 rabbitmq ERLANG_HOME not set correctly](https://blog.csdn.net/weixin_34279184/article/details/92173659)

  最初以为`erlang`安装有问题，于是卸载了重装。最后发现问题出在`rabbitmqctrl.bat`。

  ``` bat
  "!ERLANG_HOME!\bin\erl.exe" ^
          -pa "!RABBITMQ_EBIN_ROOT!" ^
          -noinput -hidden ^
          -s rabbit_prelaunch ^
          !RABBITMQ_NAME_TYPE! rabbitmqprelaunch!RANDOM!!TIME:~9! ^
          -extra "!RABBITMQ_NODENAME!"
  ```

  其中要求环境变量`ERLANG_HOME`为`bin`目录的父路径。

# 查看状态

进入`rabbitmq`的安装目录：

``` shell
rabbitmqctl status
```



