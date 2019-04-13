package org.hc.learning.beanutils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Assert;
import org.junit.Test;

public class BeanUtilsTest {
	
	@Test
	public void beanInject() {
		String no = "1111";
		String idNo = "2222";
		Bean1 bean1 = new Bean1();
		try {
			BeanUtils.setProperty(bean1, "no", no);
			BeanUtils.setProperty(bean1, "idNo", idNo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(bean1.getNo(), no);
		Assert.assertEquals(bean1.getIdNo(), idNo);
	}
	
}
