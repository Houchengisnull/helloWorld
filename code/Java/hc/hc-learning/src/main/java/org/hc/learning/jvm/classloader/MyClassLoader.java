package org.hc.learning.jvm.classloader;

import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader loader = new MyClassLoader();
        Object instance = loader.loadClass("org.hc.learning.jvm.classloader.MyClassLoader").newInstance();
        System.out.println("当前类加载器:" + MyClassLoader.class.getClassLoader());
        System.out.println("新类加载器:" + loader);
        // 打破双亲委派结果为false
        System.out.println(instance instanceof MyClassLoader);
    }

    /**
     * 打破双亲委派
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // 读取class文件
        String filename = name.substring(name.lastIndexOf("." ) + 1) + ".class";
        InputStream input = getClass().getResourceAsStream(filename);
        if (input == null) {
            return super.loadClass(name);
        }
        try {
            byte[] bytes = new byte[input.available()];
            input.read(bytes);
            return defineClass(name, bytes,0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }
}
