package org.hc.learning.algorithm.消息摘要;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

public class CRC文件校验 {
	
	@Test
	public void testByMessageDigest() {
		// 文件路径
		String path = "F:\\vs_community_CHS__149203781.1488612268.exe";
		// 构建文件输入流
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 初始化MessageDigest, 并指定MD5算法
		DigestInputStream dis = null;
		try {
			dis = new DigestInputStream(fis, MessageDigest.getInstance("MD5"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 流缓冲大小
		int buf = 1024;
		// 缓冲字节数组
		byte[] buffer = new byte[buf];
		// 当读到值大于-1就继续读
		int read = 0;
		try {
			read = dis.read(buffer, 0, buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(read > -1) {
			try {
				read = dis.read(buffer, 0, buf);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 关闭流
		try {
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 获得MessageDigest
		MessageDigest md = dis.getMessageDigest();
		// 摘要处理
		byte[] b = md.digest();
		String md5Hex = Hex.toHexString(b);
		System.out.println(md5Hex);
		assertEquals("比如软件下载官网提供的消息摘要",md5Hex);
	}
	
	@Test
	public void testByDigestUtils() throws Exception {
		// 文件路径
		String path = "F:\\vs_community_CHS__149203781.1488612268.exe";
		FileInputStream fis = new FileInputStream(new File(path));
		String md5Hex = DigestUtils.md5Hex(fis);
		System.out.println(md5Hex);
		fis.close();
		assertEquals("比如软件下载官网提供的消息摘要", md5Hex);
	}
}
