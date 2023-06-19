package org.hc.learning.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {

    private int page;
    private int size;
    private int total;
    private List<T> list;

}
