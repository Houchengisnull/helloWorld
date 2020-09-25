[TOC]

# 安装

## 设置JAVA_HOME

由于`Maven`依赖本地JDK，需要设置环境变量`JAVA_HOME`指向本地JDK，注意不能直接指向本地JDK的bin目录。

# 约定配置

基于`约定优于配置`原则，`maven`使用共同的标准目录结构，见下表:

| 目录                               | 目的                                                         |
| :--------------------------------- | :----------------------------------------------------------- |
| ${basedir}                         | 存放pom.xml和所有的子目录                                    |
| ${basedir}/src/main/java           | 项目的java源代码                                             |
| ${basedir}/src/main/resources      | 项目的资源，比如说property文件，springmvc.xml                |
| ${basedir}/src/test/java           | 项目的测试类，比如说Junit代码                                |
| ${basedir}/src/test/resources      | 测试用的资源                                                 |
| ${basedir}/src/main/webapp/WEB-INF | web应用文件目录，web项目的信息，比如存放web.xml、本地图片、jsp视图页面 |
| ${basedir}/target                  | 打包输出目录                                                 |
| ${basedir}/target/classes          | 编译输出目录                                                 |
| ${basedir}/target/test-classes     | 测试编译输出目录                                             |
| Test.java                          | Maven只会自动运行符合该命名规则的测试类                      |
| ~/.m2/repository                   | Maven默认的本地仓库目录位置                                  |

# POM文件

## build

### plugin

#### 指定JDK版本

``` xml
<build>  
    <plugins>  
        <plugin>  
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>  
                <version>3.7</version>  
                <configuration>  
                    <source>1.8</source>  
                    <target>1.8</target>  
                </configuration>  
            </plugin>  
        </plugins>  
</build>
```

- source

  向下兼容最低版本

- target

  生成字节码使用JDK版本

# 在IDEA中使用Maven

https://www.cnblogs.com/sigm/p/6035155.html

## 在IDEA中使用Maven构建Web应用

https://blog.csdn.net/u012826756/article/details/79478973

# 手动导入jar

依赖插件

https://blog.csdn.net/tyrroo/article/details/77017190

``` xml
<dependency>
	<groupId>org.apache.maven.plugins</groupId>  
	<artifactId>maven-install-plugin</artifactId>  
	<version>2.5.1</version>  
</dependency>
```

# 查看依赖树

``` shell
mvn dependency:tree
```

# 解决依赖冲突

## spring-boot log4j日志未生成

- https://blog.csdn.net/libertine1993/article/details/80857483

该问题导致无法产生日志文件。

导致问题的`xml`如下：

``` xml
<dependency>
	<groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
<denpendency>
	<groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</denpendency>
```

将`spring-boot-starter-amqp`依赖移到后面去，日志文件便能正常生产。

``` xml
<denpendency>
	<groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</denpendency>
<dependency>
	<groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

通过`mvn dependency:tree`可知`spring-boot-starter-amqp`依赖`spring-boot-starter-logging`，而`spring-boot-starter-logging`的日志依赖是`logback-classic`。

也可以通过鼠标右键`Maven`点击`show Dependencies`查看项目的依赖情况。

而项目则使用`log4j`作为日志控件。

