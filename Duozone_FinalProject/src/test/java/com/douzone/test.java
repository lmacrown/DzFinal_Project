package com.douzone;

import org.junit.jupiter.api.Test;

public class test {
	@Test
	public void test() throws Exception {
	   Aes aes = new Aes("1234567812345678");
	   String enc = aes.encrypt("this is plain text");
	   String dec = aes.decrypt(enc);
	   System.out.println(enc);
	   System.out.println(dec);
	}
}
