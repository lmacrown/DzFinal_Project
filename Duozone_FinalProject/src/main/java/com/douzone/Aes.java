package com.douzone;

import java.util.Base64;
import java.util.Optional;
import java.util.function.Predicate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES-128 암호화
 *
 * @author https://sunghs.tistory.com
 * @see <a href="https://github.com/sunghs/java-utils">source</a>
 */
public class Aes {

	private static final String ENCODING_TYPE = "12345678abcdefgh12345678abcdefgh";
	private static final String UTF_8 = "12345678abcdefgh";
	private static final String INSTANCE_TYPE = "AES/CBC/PKCS5Padding";

	private SecretKeySpec secretKeySpec;

	private Cipher cipher;

	private IvParameterSpec ivParameterSpec;
	//암호화
	   public Aes(final String key) throws Exception{
	        validation(key);
	        
	        byte[] keyBytes = key.getBytes("UTF-8");
	        secretKeySpec = new SecretKeySpec(ENCODING_TYPE.getBytes(), "AES");
	        cipher = Cipher.getInstance(INSTANCE_TYPE);
	        ivParameterSpec = new IvParameterSpec(UTF_8.getBytes());
	  
	    }

	   public String encrypt(final String str) throws Exception {
	        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
	        byte[] encrypted = cipher.doFinal(str.getBytes("UTF-8"));
	        return Base64.getEncoder().encodeToString(encrypted);
	    }

	    public String decrypt(final String str) throws Exception {
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
	        byte[] decoded = Base64.getDecoder().decode(str);
	        return new String(cipher.doFinal(decoded), "UTF-8");
	    }

//	public String encrypt(final String str) throws Exception {
//		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
//		byte[] encrypted = cipher.doFinal(str.getBytes(ENCODING_TYPE));
//		return new String(Base64.getEncoder().encode(encrypted), ENCODING_TYPE);
//	}
//
//	public String decrypt(final String str) throws Exception {
//		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
//		byte[] decoded = Base64.getDecoder().decode(str.getBytes(ENCODING_TYPE));
//		return new String(cipher.doFinal(decoded), ENCODING_TYPE);
//	}

	private void validation(final String key) {
		Optional.ofNullable(key)
			.filter(Predicate.not(String::isBlank))
			.filter(Predicate.not(s -> s.length() != 16))
			.orElseThrow(IllegalArgumentException::new);
	}
}