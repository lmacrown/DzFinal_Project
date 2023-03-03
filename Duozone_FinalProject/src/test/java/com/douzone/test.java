package com.douzone;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class test {
	Map<String, Object> result = new HashMap();
	@Test
	public void AES() throws Exception {
	   Aes aes = new Aes("1234567812345678");
	   String enc = aes.encrypt("this is plain text");
	   String dec = aes.decrypt(enc);
	   System.out.println(enc);
	   System.out.println(dec);
	}
	
}
