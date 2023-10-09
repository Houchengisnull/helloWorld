package org.hc.learning.jvm.classloader;

import org.hc.tool.print.Prints;
import sun.misc.Launcher;

import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderApp {

    public static void main(String[] args) {
        // 启动类加载器
        System.out.println("启动类加载器:");
        for (URL url : Launcher.getBootstrapClassPath().getURLs()) {
            System.out.println(url);
        }
        Prints.printSplitLine();

        // 扩展类加载器
        System.out.println("扩展加载器:");
        URLClassLoader ext = (URLClassLoader) ClassLoader.getSystemClassLoader().getParent();
        for (URL url : ext.getURLs()) {
            System.out.println(url);
        }
        Prints.printSplitLine();

        // 应用类加载器
        System.out.println("系统加载器:");
        URLClassLoader system = (URLClassLoader) ClassLoader.getSystemClassLoader();
        for (URL url : system.getURLs()){
            System.out.println(url);
        }
        Prints.printSplitLine();

    }

}
