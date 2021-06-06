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

[使用IntelliJ IDEA 配置Maven（入门）](https://www.cnblogs.com/sigm/p/6035155.html)

## 在IDEA中使用Maven构建Web应用

[idea使用maven构建web应用](https://blog.csdn.net/u012826756/article/details/79478973)

# 手动导入jar

依赖插件

[maven install 插件介绍](https://blog.csdn.net/tyrroo/article/details/77017190)

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

# debug

``` bat
mvn -x package
```

增加`-x`参数将进入`debug`模式进行打印。

# Archetype 原型

> - **2021.1.26**
>
>   今天学习`javafx`的时候，遗漏了官网中需要修改`archetype artifactId`的要求。导致反复出现构建失败。类似这种错误。
>
>   <a href='https://stackoverflow.com/questions/59696029/the-desired-archetype-does-not-exist'>The desired archetype does not exist</a>
>   
>   尽管这篇文章中构建失败的原因和我不太一样，但错误信息是一样的。
>   
>   虽然花费了不少时间，但是有所收获。在漫漫编程路上发现越来越多的分岔路。😢😢😢😢😢😢😢😢

- **作用**	生成`Maven`项目骨架（项目的目录结构和`pom.xml`）

  只要给对应的`Archetype`插件提供基本的信息：

  1. `groupId`
  2. `artifactId`
  3. `version`

  就可以快速生成项目的原型。需要注意的是，在使用`IDE`时，实际上的`artifactId`并不一定是默认的`artifactId`。

  比如在学习`javafx`的过程中，`IDEA`默认填写的是`javafx-maven-archetypes`，但根据官网上的描绘应该是`javafx-archetype-fxml`或者`javafx-archetype-simple`。

- **命令行使用**

  ``` shell
  mvn archetype:generate
  ```

  其中`archtype`是`maven-archetype-plugin`的缩写；

  `generate`是我们创建maven项目的目标名称，对应`maven-archetype-quickstart`、`javafx-archetype-fxml`、`javafx-archetype-simple`等。

另外在中央仓库中有许多`Archetype`插件，比如：

- **maven-archtype-quickstart**

  当我们直接在命令行输入`mvn archetype:generate`时，没有指定使用哪个原型，那么默认便是`quickstart`。

- **maven-archetype-webapp**

  快速创建一个`Web`应用

- **AppFuse Archetype**

  一个集成了很多开源工具的项目。

<hr>

- **参考**

  <a href='http://c.biancheng.net/view/5298.html'>Archetype插件的介绍和使用</a>





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

