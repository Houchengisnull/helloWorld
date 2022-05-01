package org.hc.learning.design.chainofresponsibility.handleimpl;

import org.hc.learning.design.chainofresponsibility.Handle;

public class DevelopmentHandle implements Handle {
    @Override
    public boolean execute(String demand) {
        System.out.println("[程序员] 开始处理:" + demand);
        System.out.println("[程序员] 处理结果: 驳回");
        return false;
    }
}
