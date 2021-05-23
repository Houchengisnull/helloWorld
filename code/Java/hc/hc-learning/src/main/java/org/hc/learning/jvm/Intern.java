package org.hc.learning.jvm;

public class Intern {

    public static void main(String[] args) {
        String b = "靓仔";
        String a = b + "丞";
        /**
         * 一个字符串，内容与此字符串相同，但一定取自具有唯一字符串的池。
         */
        System.out.println(a.intern() == a);
        System.out.println(b.intern() == b);
    }

}
