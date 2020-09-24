[toc]

- 参考

  https://blog.csdn.net/zhm3023/article/details/82217222

  https://blog.csdn.net/qq_36505948/article/details/82734133

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

https://www.jianshu.com/p/264717dcbd1f

要注意不能把`Rabbit MQ`安装在含中文字符或空格的路径下。

## 安装管理插件

``` bat
rabbitmq-plugins enable rabbitmq_management`
```

- 出现`Applying plugin configuration to rabbit@XXX failed`

  首先，停止MQ服务后进行重装，

  ``` bat
  rabbitmq-service stop
  rabbitmq-service remove
  rabbitmq-service install
  rabbitmq-service start
  # 重新安装插件
  rabbitmq-plugins enablt rabbitmq_management
  ```

- 默认端口：15672
- 默认用户：guest
- 默认密码：guest

