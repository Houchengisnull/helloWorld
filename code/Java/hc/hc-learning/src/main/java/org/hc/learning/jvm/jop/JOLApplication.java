package org.hc.learning.jvm.jop;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 一款查看Java对象布局的工具
 */
public class JOLApplication {

    public static void main(String[] args) {
        // 20200806 上班好无聊
        Object map = generate();
        // 查看对象内部信息
        String innerMessage = ClassLayout.parseInstance(map).toPrintable();
        System.out.println(innerMessage);
        // 查看对象外部信息
        String outterMessage = GraphLayout.parseInstance(map).toPrintable();
        System.out.println(outterMessage);
        // 查看对象总空间大小
        long totalSize = GraphLayout.parseInstance(map).totalSize();
        System.out.println("total size : " + totalSize);
    }

    static Object generate() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", new Integer(1));
        map.put("b", "b");
        map.put("c", new Date());

        for (int i = 0; i < 10; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }
        return map;
    }

}
