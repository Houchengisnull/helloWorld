[toc]

> 该问题出现过两次——即在使用`Maven`打包时，依赖包并未放进`jar`中，导致可执行`jar`无法正常运行。
>
> 从前没有系统总结，导致解决该问题又花费太多时间。

# 打包

``` shell
-- 该生命周期包含resources、compile、testResources、testCompile、test、jar阶段
mvn package
```

# 打包插件

请根据实际需要选择合适插件

- `maven-jar-plugin`

  `maven`默认打包工具，不会将依赖包放进`jar`中

- `maven-shade-plugin`

  生成`executable jar`

- `maven-assembly-plugin`

  支持自定义打包

## 使用`maven-jar-plugin`

``` xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>2.4</version>
    <!-- 对要打的jar包进行配置 -->
    <configuration>
        <!-- Configuration of the archiver -->
        <archive>
            <!--生成的jar中，不要包含pom.xml和pom.properties这两个文件-->
            <addMavenDescriptor>false</addMavenDescriptor>

            <!-- Manifest specific configuration -->
            <manifest>
                <!--是否要把第三方jar放到manifest的classpath中-->
                <addClasspath>true</addClasspath>
                
                <!--生成的manifest中classpath的前缀，
                因为要把第三方jar放到lib目录下，
                所以classpath的前缀是lib/-->
                <classpathPrefix>lib/</classpathPrefix>
            </manifest>
        </archive>
        <!--过滤掉不希望包含在jar中的文件-->
        <excludes>
            <!-- 排除不需要的文件夹(路径是jar包内部的路径) -->
            <exclude>**/assembly/</exclude>
        </excludes>
    </configuration>
</plugin>

```

## 使用`maven-shade-plugin`

``` xml
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
                    <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>com.auto.Main</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```



# 参考

https://blog.csdn.net/zhaojianting/article/details/80324533