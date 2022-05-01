package org.hc.learning.design.chainofresponsibility.handleimpl;

import org.hc.learning.design.chainofresponsibility.Handle;

public class DevelopmentRepresentHandle implements Handle {
    @Override
    public boolean execute(String demand) {
        System.out.println("[软件代表] 开始处理:" + demand);
        System.out.println("[软件代表] 处理结果: ???");
        return true;
    }
}
