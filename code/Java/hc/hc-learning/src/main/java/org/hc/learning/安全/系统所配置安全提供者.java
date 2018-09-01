package org.hc.learning.安全;

import java.security.Provider;
import java.security.Security;
import java.util.Map.Entry;
import java.util.Set;

public class 系统所配置安全提供者 {
	public static void main(String[] args){
		for (Provider p : Security.getProviders()) {
			// 打印所有安全提供者信息
			System.out.println(p);
			// 遍历提供者实体
			Set<Entry<Object, Object>> entrySet = p.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				// 打印提供者键值
				System.out.println("\t" + entry.getKey());
			}
		}
	}
}
