package org.hc.learning.spring.aop.CGLib代理;

public class HelloWorldWithoutInterface {

    public void sayHello() {
        System.out.println("Hello world");
    }

    final void sayFinal() {
        System.out.println("Final");
    }
}
