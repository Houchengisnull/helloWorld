package org.hc.learning.jvm.classloader;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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

    private List<String> getRelatedClasses(String filename) {
        List<String> classes = new ArrayList<>();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            String name = entries.nextElement().getName();
            if (name.startsWith(filename)) {
                classes.add(name);
            }
        }
        return classes;
    }

    public JarFile getJarFile() {
        return this.jarFile;
    }

    public static void printAllClass(JarFile jarFile) {
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            System.out.println(jarEntry.getName());
        }
    }

}
