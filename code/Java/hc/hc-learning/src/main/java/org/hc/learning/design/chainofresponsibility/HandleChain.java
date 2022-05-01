package org.hc.learning.design.chainofresponsibility;

import java.util.ArrayList;

public class HandleChain {

    private ArrayList<Handle> chains;
    private String demand;

    public HandleChain(){
        chains = new ArrayList();
    }

    public void add(Handle handle){
        this.chains.add(handle);
    }

    public void executeChains(String demand) {
        if (!chains.isEmpty()) {
            for (Handle chain : chains) {
                boolean execute = chain.execute(demand);
                if (!execute) {
                    break;
                }
            }
        }
    }

}
