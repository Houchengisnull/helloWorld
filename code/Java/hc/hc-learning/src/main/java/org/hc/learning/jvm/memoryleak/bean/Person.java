package org.hc.learning.jvm.memoryleak.bean;

public class Person {
    private String name;
    private String pwd;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }



    public Person(String name, String pwd, int age) {
        this.name = name;
        this.pwd = pwd;
        this.age = age;
    }
}
