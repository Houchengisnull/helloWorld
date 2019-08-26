[TOC]

# logback

官网地址：https://logback.qos.ch/index.html

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