package org.hc.learning.design.chainofresponsibility.handleimpl;

import org.hc.learning.design.chainofresponsibility.Handle;

public class ProductManageHandle implements Handle {
    @Override
    public boolean execute(String demand) {
        System.out.println("[PM分析] 开始处理:" + demand);
        System.out.println("[PM分析] 处理结果: ...");
        return true;
    }
}
