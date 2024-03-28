package org.hc.learning.jvm.classloader;

import com.google.common.reflect.ClassPath;
import lombok.SneakyThrows;
import java.util.Set;

public class GetAllClassesUtil{

    @SneakyThrows
    public static void main(String[] args) {
        ClassPath clazzPath = ClassPath.from(ClassLoader.getSystemClassLoader());
        Set<ClassPath.ClassInfo> classes = clazzPath.getAllClasses();
        for (ClassPath.ClassInfo clazz : classes) {
            System.out.println(clazz.getResourceName());
        }
    }

}
