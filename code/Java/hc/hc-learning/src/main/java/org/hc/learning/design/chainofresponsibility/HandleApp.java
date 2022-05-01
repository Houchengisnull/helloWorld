package org.hc.learning.design.chainofresponsibility;

import org.hc.learning.design.chainofresponsibility.handleimpl.DemandAnalysisHandle;
import org.hc.learning.design.chainofresponsibility.handleimpl.DevelopmentHandle;
import org.hc.learning.design.chainofresponsibility.handleimpl.DevelopmentRepresentHandle;
import org.hc.learning.design.chainofresponsibility.handleimpl.ProductManageHandle;

public class HandleApp {

    public static void main(String[] args) {
        // 需求
        String demand = "需求名称:一个五彩斑斓的黑";
        // 开发流程
        HandleChain developmentProcess = new HandleChain();
        developmentProcess.add(new DemandAnalysisHandle());
        developmentProcess.add(new ProductManageHandle());
        developmentProcess.add(new DevelopmentRepresentHandle());
        developmentProcess.add(new DevelopmentHandle());
        developmentProcess.executeChains(demand);
    }
}
