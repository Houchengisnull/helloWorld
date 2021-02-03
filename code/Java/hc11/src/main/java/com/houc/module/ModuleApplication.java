package com.houc.module;

/**
 * 编译命令
 * 1.`javac -d target/hc src/main/java/module-info.java src/main/java/com/houc/module/ModuleApplication.java`
 * 2.`javac -mod mods
 *
 * 运行命令
 * 1.`java --module-path target -m hc/com.houc.module.ModuleApplication
 *      结果为"hc"
 * 直接运行
 * 1.`java -classpath target/hc com.houc.module.ModuleApplication`
 *      结果为空
 */
public class ModuleApplication {

    public static void main(String[] args) {
        Class<ModuleApplication> cls = ModuleApplication.class;
        String name = cls.getModule().getName();
        System.out.println(String.format("Module name :%s", name));
    }

}
