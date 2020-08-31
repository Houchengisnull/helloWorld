[TOC]

# server.xml

## 解压war unpackWARs

``` xml
<Host name="localhost"  appBase="webapps" unpackWARs="true" autoDeploy="true">
```

# 异常

## Invalid character

> Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986.

`Tomcat`在7.0.73版本以后，添加了对`http head`的验证。

当前端申请的`url`包含一些字符时将出错，比如：`{`、`}`、`<`、`>`等待。

但我们依然有解决方案：

修改`tomcat`的`catalina.properties`文件

找到这一行：

``` properties
tomcat.util.http.parser.HttpParser.requestTargetAllow=|{}?&
```

> spring-boot 2.0.0内置tomcat版本在8.0以上