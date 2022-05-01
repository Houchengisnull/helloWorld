package org.hc.learning.design.chainofresponsibility.handleimpl;

import org.hc.learning.design.chainofresponsibility.Handle;

public class DemandAnalysisHandle implements Handle {
    @Override
    public boolean execute(String demand) {
        System.out.println("[需求分析] 开始处理:" + demand);
        System.out.println("[需求分析] 处理结果: 可实现");
        return true;
    }
}
