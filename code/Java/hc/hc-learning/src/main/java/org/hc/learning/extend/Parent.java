package org.hc.learning.extend;

public class Parent {

    private String treasure(){
        return "this is parent";
    }

    protected void show(){
        System.out.println(treasure());
    }

}
