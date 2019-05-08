[TOC]

# target

## javac

### 指定编译产生字节码版本

``` xml
<target name="hello">
	......
    <javac source="1.6" target="1.6" srcdir="${javaSrc}" destdir="${build.classes}" encoding="UTF-8" debuglevel="line,vars,source">
    	<classpath refid="hello.classpath"/>
    </javac>
    ......
</target>
```

- source

- target

- executable

  ``` xml
  executable="C:/j2sdk1.4.2_05/bin/javac"
  ```

编译产生字节码版本，真正起作用的是target属性。

### 参考

https://blog.csdn.net/chenzhengfeng/article/details/77479105