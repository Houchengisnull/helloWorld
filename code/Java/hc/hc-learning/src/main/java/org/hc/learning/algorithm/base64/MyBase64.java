package org.hc.learning.algorithm.base64;

import org.bouncycastle.util.encoders.Base64;

/**
 * https://www.liaoxuefeng.com/wiki/897692888725344/949441536192576
 * 2020.11.18 已经看不懂两年前具体的代码实现了，今天复习一下Base64的思想
 */
public class MyBase64 {
	
	private final static String mapper = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	/**
	 * 实现思路:
	 * 先将 数组data 中所以数据复制到  clone 并且补充足够的长度给 clone
	 * 然后对 clone 中的数据进行加密
	 * @param data
	 * @return
	 */
	public static String encode(byte[] data){
		// 声明
		int len = data.length / 3 * 4 ;
		int mod = data.length % 3 ;	// 余数
		if( mod %3 != 0){
			len += 4;
		}
		int lenBytes = len/ 4 * 3;
		byte[] clone = new byte[ lenBytes ];
		char[] chars = new char[ len ];
		// 复制
		for( int i=0 ; i<lenBytes ; i++ ){
			if( i < data.length ){
				clone[i] = data[i];
			}else{
				clone[i] = 0 ;
			}
		}
		// 加密核心
		int count = 0 ;
		for( int i=0 ; i<clone.length ; i+=3 ){
			chars[count++] = mapper.charAt( ( clone[i] >>2 ) % 64 );
			chars[count++] = mapper.charAt( ( clone[i] <<6 >>2 ) %64 + ( clone[i+1] >>4 ) %64 );   // 不能将 %64提取,移位操作非常规运算
			chars[count++] = mapper.charAt( ( clone[i+1] <<4 >>2 ) %64 + ( clone[i+2] >>6 ) %64 );
			chars[count++] = mapper.charAt( ( clone[i+2] <<2 >>2 ) %64 );
		}
		return mod != 0 ? new String(chars, 0 , chars.length- ( 3 - mod)) : new String(chars) ;
	}
	
	public static String decode( byte[] data ){
		char c ;
		byte[] ori = new byte[ data.length + 2 ];		// 原数据
		char[] chars = new char[ (data.length +2 ) /3 *4 ];
		for ( int i=0 ; i<ori.length ; i++ ){  			// 将加密数据 首先解析 为 对应表中的数字信息
			if ( i < data.length ){
				c = (char) data[i];
				ori[i] = (byte) mapper.indexOf( c );
			}else{
				ori[i] = 0;
			}
		}
		// 在通过这些数字信息 解析 为 原信息
		int count = 0 ;
		for( int i=0 ; i<ori.length ; i+=4){
			chars[count++] = (char) ((ori[i] * 4) + (ori[i+1] * 4 / 64)) ;
			chars[count++] = (char) ((ori[i+1] % 8 * 16) + (ori[i+2] * 4 / 16));
			chars[count++] = (char) ((ori[i+2] % 2 * 64) + (ori[i+3]));
		}
		return new String( chars );
	}
	
	public static void main(String[] args) {
		byte[] data = "Helloworld111".getBytes();
		byte[] encode = Base64.encode( data );
		for( byte b : data ){
			System.out.print( b + " " );
		}
		System.out.println();
		System.out.println( new String(encode) );
		String dataEncode = encode( data );
		System.out.println( dataEncode );
		String decode = decode( dataEncode.getBytes() );
		System.out.println( decode );
	}
}
