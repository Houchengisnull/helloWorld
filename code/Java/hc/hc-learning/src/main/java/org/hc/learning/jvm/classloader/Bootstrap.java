package org.hc.learning.jvm.classloader;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

@Slf4j
public class Bootstrap {

    public static void main(String[] args) throws IOException {
        String jarPath = "C:\\Users\\admin\\Desktop\\helloWorld\\code\\Java\\hc\\hc-learning\\src\\main\\resources\\lib\\hutool-all-5.8.26.jar";
        MyJarClassLoader jarClassLoader = new MyJarClassLoader(new JarFile(new File(jarPath)));
        Class<?> clazz = jarClassLoader.loadClass("cn.hutool.core.date.DateUtil");
        System.out.println(clazz.getClassLoader());

        /*URL url = new URL("file:C:\\Users\\admin\\Desktop\\helloWorld\\code\\Java\\hc\\hc-learning\\src\\main\\resources\\lib\\hutool-all-5.8.26.jar");
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});

        Class<?> dateUtil = Class.forName("cn.hutool.core.date.DateUtil", true, urlClassLoader);
        System.out.println(dateUtil.getClassLoader());*/
    }
}
