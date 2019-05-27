package org.hc.learning.jvm;

public class ConstantPool {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String string1 = "Hello";
		String string2 = string1 + "World";
		// String string3 = "HelloWorld";
		
		System.out.println(string2.intern() == string2);
		/*System.out.println(string2 == string3);
		System.out.println(string3.intern() == string2.intern());
		System.out.println(string3.intern() == string3);*/
		
		/*String s = new String("1");
		String s2 = "1";
		System.out.println(s == s2);
		System.out.println(s.intern() == s2);
		System.out.println(s == s2);*/
	}
}
