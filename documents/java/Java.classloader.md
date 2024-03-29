[toc]

# ClassLoader

类加载器用于将字节码的二进制字节流，转换为方法区的运行时数据结构，在内存中生成代表该类的对象。

 而具体执行的方法是ClassLoader中的defineClass(..)。

## JDK ClassLoader

JDK已经实现了几种ClassLoader：

- **bootstrap classloader**

  负责加载JDK核心类，例如：java.lang.*

- **extension classloader**

  扩展类加载器，负责加载`JRE`扩展目录——`$JAVA_HOME/jre/lib/ext`

- **system classloader**

  应用类加载器，负责加载`java -classpath`中指定的类路径或`jar`

### 类加载过程

- loading
- Verification
- Preparation
- Resolution
- Initialization

除了loading外，其余四步都由JVM负责。反正，JVM会报错，如果你加载的类由有问题。

此外，`Verification`、`Preparation`、`Resolution`都是`Linking`。

> 动态加载：JVM会在使用的时候加载。

### 加载方式

| 加载方式              | 加载类型 |
| --------------------- | -------- |
| new关键字             | 隐式加载 |
| Class.forName()       | 显式加载 |
| 自定义classloader加载 | 显式加载 |

## 双亲委派模型

### 概念

双亲委派模型很简单：

- **Parent class fisrt**

  父类优先。

- **Who is parent class**

  在Java中提及父类，我们总是第一个想到继承，但是在ClassLoader中，父类与子类是一种组合关系。

  我认为在其他语言里：更可能叫做链表，反正就是结构体中的一个同类型字段。

### 实现

总之，双亲委派的概念没有那么复杂。

如果需要看看具体的源码，只需要关注ClassLoader与URLClassLoader的findClass(..)和loadClass(..)。

## 自定义类加载器

实际业务场景非常少，大多数情况工具都会帮你处理好，比如Tomcat或者是jdbc。而我唯一遇到的情况是Kafka的包冲突，需要通过类加载器实现类隔离，不得不使用自定义类加载器。

你可以选择记住这篇文章等有需要的时候再来看。

- **加载外部Jar**

  ``` java
  import lombok.SneakyThrows;
  import lombok.extern.slf4j.Slf4j;
  import java.io.ByteArrayOutputStream;
  import java.io.InputStream;
  import java.util.Enumeration;
  import java.util.jar.JarEntry;
  import java.util.jar.JarFile;
  
  @Slf4j
  public class MyJarClassLoader extends ClassLoader {
  
      private JarFile jarFile;
      private ClassLoader parent;
  
      public MyJarClassLoader(JarFile jarFile) {
          super(Thread.currentThread().getContextClassLoader());
          this.jarFile = jarFile;
          this.parent = Thread.currentThread().getContextClassLoader();
      }
  
      @SneakyThrows
      private byte[] getClassData(InputStream input) {
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          byte[] buffer = new byte[4096];
          int len = 0;
          while ((len = input.read(buffer)) != -1) {
              out.write(buffer, 0, len);
          }
          return out.toByteArray();
      }
  
      /**
       *
       * @param name 类全限定名
       * @return
       */
      @SneakyThrows
      @Override
      public Class<?> loadClass(String name) {
          Class clazz = null;
          if (null != jarFile) {
              String fullname = name.replaceAll("\\.", "\\/").concat(".class");
              JarEntry jarEntry = jarFile.getJarEntry(fullname);
              if (null != jarEntry) {
                  InputStream input = jarFile.getInputStream(jarEntry);
                  byte[] bytes = getClassData(input);
  
                  // 格式 cn.hutool.core.date.DateTime
                  clazz = defineClass(name, bytes,0, bytes.length);;
              }
              if (clazz == null) {
                  clazz = parent.loadClass(name);
              }
          }
          return clazz;
      }
      
      public static void printAllClass(JarFile jarFile) {
          Enumeration<JarEntry> entries = jarFile.entries();
          while (entries.hasMoreElements()) {
              JarEntry jarEntry = entries.nextElement();
              System.out.println(jarEntry.getName());
          }
      }
  }
  ```

总之，你要打破双亲委派模型的话，必须重新loadClass()。如果不需要，意味着你只是想加载外部（比如网络）上的.class，你只需要重新findClass(..)。

# 参考

https://dengchengchao.com/?p=1156