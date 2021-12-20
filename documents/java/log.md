[TOC]

# logback

[官网地址](https://logback.qos.ch/index.html)

`logback`是由`log4j`创始人设计的另一个开源日志组件。其分为

- `logback-core`

- `logback-classic`

  其为`log4j`改良版本，实现`slf4j`框架。

- `logback-access`

  访问模块与`Servlet`容器集成，提供`http`访问日志功能。

## 与log4j相比

- 更快。（PS:明显感受启动速度更快）

- 测试充分

- 实现`slf4j api`

- 文档健全

- 自动重载配置

  ``` xml
  <!-- debug参数 是否打印logback内部日志信息 -->
  <configuration scan="true" scanPeriod="60 seconds" debug="false"> 
  	<!--其他配置省略-->
  </configuration>
  ```

  好处很多就不一一列举。

## 集成SpringMVC

> 项目原本使用`log4j`打印日志，但领导要求定时清理日志。经研究，个人认为`log4j`实现定时清理日志并集成进项目难度较大，故选择替换`log4j`为`logback`。

需要集成进`SpringMVC`，则需要`logback-ext-spring.jar`扩展包。于`web.xml`文件中添加如下配置：

``` xml
<!-- 按照加载顺序context-param\listener\filter\servlet，请讲该段配置放在web.xml最前（包括spring mvc监听器） -->
<context-param>
	<param-name>logbackConfigLocation</param-name>
	<param-value>file:/usr/project/${env}/conf/logback.xml</param-value>
</context-param>
<listener>
	<listener-class>ch.qos.logback.ext.spring.web.LogbackConfigListener</listener-class>
</listener>
```

尽管官网提供：https://logback.qos.ch/translator/ 用于将`log4j.properties`转换为`logback.xml`。但笔者将原项目的日志配置文件转换为`logback.xml`并不适用。

最后选择手动配置一遍，如下：

``` xml
<?xml version="1.0" encoding="UTF-8"?>

<configuration debug="true" scan="true" scanPeriod="60000">
	<property name="encoding" value="UTF-8" />
	<property name="appName" value="TFBWorkflow" />
    <property name="logDir" value="/usr/project/${env}/logs/" />
    <property name="logFileName" value="${appName}" />

    <appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <charset>${encoding}</charset>
            <pattern>%d{HH:mm:ss.SSS} [%thread] [%-5level] [%logger{36}] %msg%n</pattern>
        </encoder>
    </appender>

	<appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <File>${logDir}/${logFileName}.log</File>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
        <fileNamePattern>${logDir}/${logFileName}.%d{yyyy-MM-dd}.log</fileNamePattern>
        <maxHistory>120</maxHistory>
        </rollingPolicy>
        <encoder>
	        <pattern>%d{HH:mm:ss.SSS} [%thread] [%-5level] [%logger{36}] %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="error" class="ch.qos.logback.core.rolling.RollingFileAppender">
	    <File>${logDir}/${logFileName}_error.log</File>
        <encoder>
        	<pattern>%d [%t] %-5p [%c] - %m%n</pattern>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
        	<level>ERROR</level>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${logDir}/${logFileName}_error.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>120</maxHistory>
        </rollingPolicy>
    </appender>
	
    <!-- sample -->
	<logger name="org.springframework" level="ERROR"/>

    <root level="WARN">
        <appender-ref ref="stdout"/>
        <appender-ref ref="file"/>
        <appender-ref ref="error"/>
    </root>
</configuration>
```

## 参考

https://www.cnblogs.com/sandea/p/7116751.html

# log4j2

## 设置控制台编码

> 2020.08.31
>
> 前些日子做了个项目，起初并没有特别在意。直到强迫症发作一定要解决这个问题。
>
> 由于只有这个项目且只是在控制台中乱码，比如`System.out.println("你好")`不会乱码，但是`logger.info("你好")`会乱码。
>
> 在百度了许久时间才发现在`log4j2-spring.xml`有一个关于控制台编码的配置。其中`encoding`使用了`GBK`编码。
>
> 之所以搜索这么久其实除了百度的结果命中率低外，还因为自己关键字没有找对。没注意到使用的是`log4j2`，而不是`log4j`。或者我直接搜索`spring-boot`与`log4j2-spring`的日志配置效率会更高。
>
> 在解决该问题的时候，我实际上是参考一篇关于`log4j`的编码配置后，尝试修改`log4j2-spring.xml`解决的。又或者是乍一眼看到关于日志控制台编码的参数。
>
> 突然之间的灵光一现。

``` xml
<Console name="Console" target="SYSTEM_OUT">
  <PatternLayout charset="UTF-8" pattern="%-d{yyyy-MM-dd HH:mm:ss.SSS [%thread] %-5level %logger:(%line) %msg %n}"/>
</Console>
```

- **refer**
- [Log4j2 还是 Logback？2020 年Java 日志框架到底哪个性能好？](http://www.cainiaoxueyuan.com/bc/17731.html)

## Log4j2 2.*.0漏洞修复

- **参考**
- [Apache Log4j漏洞修复](https://cloud.tencent.com/developer/article/1917626)

> - 2021-12-20
> - 近期Log4j2爆出严重漏洞，影响版本范围[2.0.0, 2.15.0)

需要将`Log4j2`的版本升级到`2.15.0.RC2`，如果项目的`Maven`直接引入:

``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
    <version>2.2.0.RELEASE</version>
</dependency>
```

只需要将其注释并替换为:

``` xml
<dependency>
      <groupId>com.xx.framework</groupId>
      <artifactId>xx-log4j2-starter</artifactId>
      <exclusions>
          <dependency>
              <groupId>org.apache.logging.log4j</groupId>
              <artifactId>log4j-api</artifactId>
          </dependency>
          <exclusion>
              <groupId>org.apache.logging.log4j</groupId>
              <artifactId>log4j-core</artifactId>
          </exclusion>
          <exclusion>
              <groupId>org.apache.logging.log4j</groupId>
              <artifactId>log4j-jul</artifactId>
          </exclusion>
          <exclusion>
              <groupId>org.apache.logging.log4j</groupId>
              <artifactId>log4j-slf4j-impl</artifactId>
          </exclusion>
      </exclusions>
  </dependency>
```

