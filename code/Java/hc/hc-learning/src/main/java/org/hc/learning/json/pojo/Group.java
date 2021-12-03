package org.hc.learning.json.pojo;

import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class Group {
    /**
     * 组序号
     */
    private Integer index;
    /**
     * 组名称
     */
    private String name;
    /**
     * 成员
     */
    private List<Member> memberList;
}
