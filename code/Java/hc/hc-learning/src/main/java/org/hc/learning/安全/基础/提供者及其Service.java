package org.hc.learning.安全.基础;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;

public class 提供者及其Service {
	public static void main(String[] args) {
		Provider[] providers = Security.getProviders();
        for(Provider p:providers){
            System.out.println("provider name:"+p.getName());
            for(Service s:p.getServices()){
                System.out.println("类型:"+s.getType()+"，算法："+s.getAlgorithm());
            }
            System.out.println("--------------------------");
        }
	}
}
