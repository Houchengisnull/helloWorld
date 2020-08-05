package org.hc.learning.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Hackouc的反射工具类
 */
public class Reflections {

    /**
     * 调用实例私有|protected的成员方法
     * invoke protected/private method
     */
    public static <T> T invokeUnaccessible(Class<?> clazz, String methodName, Object instance, Class<T> returnType) {
        Method method = null;
        T obj = null;
        try {
            method = clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        method.setAccessible(true);
        try {
            obj = returnType.cast(method.invoke(instance));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * get protected/private/default field
     */
    public static <T> T getUnaccessible(Class<?> clazz, String fieldName, Object instance, Class<T> returnType){
        Field field = null;
        T obj = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        try {
            obj = returnType.cast(field.get(instance));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
