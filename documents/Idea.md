[TOC]

# 破解

## 破解JAR

- 安装

  版本：2018.2.5

  下载地址：https://www.jetbrains.com/idea/download/previous.html

- `JetbrainsCrack-release-enc`

链接：https://pan.baidu.com/s/1X4W-q3jqPAj8dhJFT4zreg 
提取码：***dedr***

- `JetbrainsCrack-2.10-release-enc`

链接：https://pan.baidu.com/s/1TZNnEhqCnNAwxlJ4uAX4mg

提取码：**g069**

> `IDEA`与`JetbrainsCrack.jar`版本不一或导致`IDEA`频繁闪退

- 修改`idea.exe.vmoptions`和`idea64.exe.vmopitons`

  添加`-javaagent`参数

  ``` text
  -server
  -Xms128m
  -Xmx512m
  -XX:ReservedCodeCacheSize=240m
  -XX:+UseConcMarkSweepGC
  -XX:SoftRefLRUPolicyMSPerMB=50
  -ea
  -Dsun.io.useCanonCaches=false
  -Djava.net.preferIPv4Stack=true
  -Djdk.http.auth.tunneling.disabledSchemes=""
  -XX:+HeapDumpOnOutOfMemoryError
  -XX:-OmitStackTraceInFastThrow
  -javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2018.2.5\bin\JetbrainsCrack-release-enc.jar
  ```

- 输入破解码

  ``` text
  BIG3CLIK6F-eyJsaWNlbnNlSWQiOiJCSUczQ0xJSzZGIiwibGljZW5zZWVOYW1lIjoibGFuIHl1IiwiYXNzaWduZWVOYW1lIjoiIiwiYXNzaWduZWVFbWFpbCI6IiIsImxpY2Vuc2VSZXN0cmljdGlvbiI6IkZvciBlZHVjYXRpb25hbCB1c2Ugb25seSIsImNoZWNrQ29uY3VycmVudFVzZSI6ZmFsc2UsInByb2R1Y3RzIjpbeyJjb2RlIjoiQUMiLCJwYWlkVXBUbyI6IjIwMTctMTEtMjMifSx7ImNvZGUiOiJETSIsInBhaWRVcFRvIjoiMjAxNy0xMS0yMyJ9LHsiY29kZSI6IklJIiwicGFpZFVwVG8iOiIyMDE3LTExLTIzIn0seyJjb2RlIjoiUlMwIiwicGFpZFVwVG8iOiIyMDE3LTExLTIzIn0seyJjb2RlIjoiV1MiLCJwYWlkVXBUbyI6IjIwMTctMTEtMjMifSx7ImNvZGUiOiJEUE4iLCJwYWlkVXBUbyI6IjIwMTctMTEtMjMifSx7ImNvZGUiOiJSQyIsInBhaWRVcFRvIjoiMjAxNy0xMS0yMyJ9LHsiY29kZSI6IlBTIiwicGFpZFVwVG8iOiIyMDE3LTExLTIzIn0seyJjb2RlIjoiREMiLCJwYWlkVXBUbyI6IjIwMTctMTEtMjMifSx7ImNvZGUiOiJEQiIsInBhaWRVcFRvIjoiMjAxNy0xMS0yMyJ9LHsiY29kZSI6IlJNIiwicGFpZFVwVG8iOiIyMDE3LTExLTIzIn0seyJjb2RlIjoiUEMiLCJwYWlkVXBUbyI6IjIwMTctMTEtMjMifSx7ImNvZGUiOiJDTCIsInBhaWRVcFRvIjoiMjAxNy0xMS0yMyJ9XSwiaGFzaCI6IjQ3NzU1MTcvMCIsImdyYWNlUGVyaW9kRGF5cyI6MCwiYXV0b1Byb2xvbmdhdGVkIjpmYWxzZSwiaXNBdXRvUHJvbG9uZ2F0ZWQiOmZhbHNlfQ==-iygsIMXTVeSyYkUxAqpHmymrgwN5InkOfeRhhPIPa88FO9FRuZosIBTY18tflChACznk3qferT7iMGKm7pumDTR4FbVVlK/3n1ER0eMKu2NcaXb7m10xT6kLW1Xb3LtuZEnuis5pYuEwT1zR7GskeNWdYZ0dAJpNDLFrqPyAPo5s1KLDHKpw+VfVd4uf7RMjOIzuJhAAYAG+amyivQt61I9aYiwpHQvUphvTwi0X0qL/oDJHAQbIv4Qwscyo4aYZJBKutYioZH9rgOP6Yw/sCltpoPWlJtDOcw/iEWYiCVG1pH9AWjCYXZ9AbbEBOWV71IQr5VWrsqFZ7cg7hLEJ3A==-MIIEPjCCAiagAwIBAgIBBTANBgkqhkiG9w0BAQsFADAYMRYwFAYDVQQDDA1KZXRQcm9maWxlIENBMB4XDTE1MTEwMjA4MjE0OFoXDTE4MTEwMTA4MjE0OFowETEPMA0GA1UEAwwGcHJvZDN5MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxcQkq+zdxlR2mmRYBPzGbUNdMN6OaXiXzxIWtMEkrJMO/5oUfQJbLLuMSMK0QHFmaI37WShyxZcfRCidwXjot4zmNBKnlyHodDij/78TmVqFl8nOeD5+07B8VEaIu7c3E1N+e1doC6wht4I4+IEmtsPAdoaj5WCQVQbrI8KeT8M9VcBIWX7fD0fhexfg3ZRt0xqwMcXGNp3DdJHiO0rCdU+Itv7EmtnSVq9jBG1usMSFvMowR25mju2JcPFp1+I4ZI+FqgR8gyG8oiNDyNEoAbsR3lOpI7grUYSvkB/xVy/VoklPCK2h0f0GJxFjnye8NT1PAywoyl7RmiAVRE/EKwIDAQABo4GZMIGWMAkGA1UdEwQCMAAwHQYDVR0OBBYEFGEpG9oZGcfLMGNBkY7SgHiMGgTcMEgGA1UdIwRBMD+AFKOetkhnQhI2Qb1t4Lm0oFKLl/GzoRykGjAYMRYwFAYDVQQDDA1KZXRQcm9maWxlIENBggkA0myxg7KDeeEwEwYDVR0lBAwwCgYIKwYBBQUHAwEwCwYDVR0PBAQDAgWgMA0GCSqGSIb3DQEBCwUAA4ICAQC9WZuYgQedSuOc5TOUSrRigMw4/+wuC5EtZBfvdl4HT/8vzMW/oUlIP4YCvA0XKyBaCJ2iX+ZCDKoPfiYXiaSiH+HxAPV6J79vvouxKrWg2XV6ShFtPLP+0gPdGq3x9R3+kJbmAm8w+FOdlWqAfJrLvpzMGNeDU14YGXiZ9bVzmIQbwrBA+c/F4tlK/DV07dsNExihqFoibnqDiVNTGombaU2dDup2gwKdL81ua8EIcGNExHe82kjF4zwfadHk3bQVvbfdAwxcDy4xBjs3L4raPLU3yenSzr/OEur1+jfOxnQSmEcMXKXgrAQ9U55gwjcOFKrgOxEdek/Sk1VfOjvS+nuM4eyEruFMfaZHzoQiuw4IqgGc45ohFH0UUyjYcuFxxDSU9lMCv8qdHKm+wnPRb0l9l5vXsCBDuhAGYD6ss+Ga+aDY6f/qXZuUCEUOH3QUNbbCUlviSz6+GiRnt1kA9N2Qachl+2yBfaqUqr8h7Z2gsx5LcIf5kYNsqJ0GavXTVyWh7PYiKX4bs354ZQLUwwa/cG++2+wNWP+HtBhVxMRNTdVhSm38AknZlD+PTAsWGu9GyLmhti2EnVwGybSD2Dxmhxk3IPCkhKAK+pl0eWYGZWG3tJ9mZ7SowcXLWDFAk0lRJnKGFMTggrWjV8GYpw5bq23VmIqqDLgkNzuoog==
  ```

  

- 参考

https://blog.csdn.net/yl1712725180/article/details/80309862

https://blog.csdn.net/WinstonLau/article/details/83278653

## 验证服务器

1、安装验证服务器并启动；

2、修改`hosts`文件将破解服务网址修改为本地服务；

3、启动IDEA并输入破解码；

## 20.3 破解

- https://pan.baidu.com/s/1tq9yjkoLeR4wPtk243Z_Vg

  提取码：`s68r`

- 参考

- [IDEA 2020.3.2 破解版下载_安装教程（可激活至2099年,亲测有效）](https://www.exception.site/essay/how-to-free-use-idea-202021-by-resigter-code)

# Project Structure

## Project Setting

### Project

- Project name
- Project SDK
- Project language level  
限定项目编译检查时最低要求的JDK feature
- Project complied output  
项目默认编译输出根目录  
实际上, 每个模块可以设置独自的根目录(Module - (project) - Paths - Use Module compile output path)。
> 这个设置略鸡肋

### Modules

#### Source

- Mark as [ Source(代码) | Tests(单元测试) | Resources(静态资源) | Test Resoures(单元测试静态自由) | Excluded(排除) ]

#### Paths

设置模块编译输出路径
#### Dependencies

Scope 与maven编译范围类似
- complie 编译运行均有效
- Test 仅对测试类有效
- Runtime 仅运行时有效
- Provided 仅编译时有效; 对测试类编译运行均有效

#### 将项目设置为`Web`项目

- 进入`Module`
- 添加`Web模块`

https://jingyan.baidu.com/article/fdffd1f8938c93f3e98ca18e.html

## Libraries

### Facets

> When you select a framework (a facet) in the element selector pane, the settings for the framework are shown in the right-hand part of the dialog.     ——官方文档

### Artifacts 手工艺品

项目打包部署设置

> An artifact is an assembly of your project assets that you put together to test, deploy or distribute your software solution or its part. Examples are a collection of compiled Java classes or a Java application packaged in a Java archive, a Web application as a directory structure or a Web application archive, etc.     ——官方文档

- archive 归档
  至少在IBM中常常使用 archive 描述[jar|war]
- exploded 分解的

### 将`resource`放入`war`/`jar`中

1、添加`Directory Content`，地址指向`resource`

https://blog.csdn.net/yanguo110/article/details/80430200

# .idea与*.iml

> 2019-08-08
>
> 在工作中遇到一个环境配置的问题：
>
> - Maven配`pom.xml`提供依赖不完全满足项目
> - 复制他人可用项目时idea将根据Maven重置项目`.idea`和`*.iml`

`.idea`工程文件夹

`*.iml`模块配置信息

# Plugins

## Activiti 插件乱码

修改`idea`配置文件

- `$idea/bin/idea.exe.vmoptions`
- `$idea/bin/idea64.exe.vmoptions`

在文件末尾增加配置

``` txt
-Dfile.encoding=UTF-8
```

https://blog.csdn.net/jiankunking/article/details/73188603

## Install Python Plugins

- File - Settings - Plugins
- 点击<Button>Install JetBrains plugin</Button>

> 若出现`idea no python interpreter`，请设置项目`SDK`。
>
> - interpreter 解释器

- 建议直接使用系统`SDK`，而非交给Idea复制出一个`SDK`副本。

  下图`Inherit global site-packages`选项即表示继承系统`SDK`

  - inherit 继承

![1561804023049](https://github.com/Houchengisnull/helloWorld/blob/master/documents/images/idea/idea_pyhton_plugin.png)

## 参考

https://www.cnblogs.com/zlslch/p/7976893.html