package org.hc.learning.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 成员类
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private Integer index;
    private String id;
    private String name;

}
