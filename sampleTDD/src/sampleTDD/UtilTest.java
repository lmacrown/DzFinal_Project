package sampleTDD;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void test() {
		String str ="abc";
		//Assertions.assertEquals("abc", str);
		if(str.equals("abc"))
			System.out.println("같음");
		else 
			System.out.println("다름");
	}
	@Test
	void test2() {
		String str ="abcd";
		Assertions.assertEquals("abcd", str);
	}

}
