package org.hc.learning.design.builder;

import com.alibaba.fastjson.JSON;

/**
 * 构造器模式
 * 适合解决构造器参数过多
 */
public class BuilderApp {

    public static void main(String[] args) {
        ForeignStudent foreignStudent = new ForeignStudent("Hugo", "Chinese");
        Student student = new Student(foreignStudent);
        Student studentGradeTen = student.builder().grade("10").build();
        System.out.println(JSON.toJSONString(foreignStudent));
        System.out.println(JSON.toJSONString(student));
        System.out.println(JSON.toJSONString(studentGradeTen));
    }

}
