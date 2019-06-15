[TOC]

# 安装

## 设置JAVA_HOME

由于`Maven`依赖本地JDK，需要设置环境变量`JAVA_HOME`指向本地JDK，注意不能直接指向本地JDK的bin目录。

# POM文件

## 生产可执行jar

于`pom.xml`中添加以下配置

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.1.1</version>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                    <configuration>
                        <transformers>
                            <transformer
                                    implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                <mainClass>com.auto.Main</mainClass>
                            </transformer>
                        </transformers>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

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



