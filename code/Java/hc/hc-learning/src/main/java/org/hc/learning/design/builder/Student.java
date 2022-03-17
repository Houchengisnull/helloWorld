package org.hc.learning.design.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Student {

    public Student(ForeignStudent foreignStudent) {
        this.name = foreignStudent.getName();
        this.country = foreignStudent.getCountry();
    }

    /**
     * 姓名
     */
    private String name;
    /**
     * 年级
     */
    private String grade;
    /**
     * 班级
     */
    private String classIndex;
    /**
     * 年纪
     */
    private Integer age;

    /**
     *
     */
     @Builder.Default
     private String country = "中国";
     private String language = "中文";
}
