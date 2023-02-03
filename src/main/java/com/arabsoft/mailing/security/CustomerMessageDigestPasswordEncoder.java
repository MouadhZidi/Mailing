package com.arabsoft.mailing.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.codec.Utf8;


import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomerMessageDigestPasswordEncoder implements PasswordEncoder {
	private MessageDigest digester;

	public CustomerMessageDigestPasswordEncoder(String algorithm) throws NoSuchAlgorithmException {
		this.digester = MessageDigest.getInstance(algorithm);
	}

	public String encode(CharSequence rawPassword) {
		return digest(rawPassword);
	}

	private String digest(CharSequence rawPassword) {
		String saltedPassword = rawPassword + "";

		byte[] digest = this.digester.digest(Utf8.encode(saltedPassword));
		String encoded = encode(digest);
		return encoded;
	}

	private String encode(byte[] digest) {
		 StringBuffer encodedPassword = new StringBuffer();
        for (byte b : digest) {
            encodedPassword.append(String.format("%02x", b & 0xff));
        }
        return encodedPassword.toString().toUpperCase();
	}

	
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String rawPasswordEncoded = digest(rawPassword);
		return encodedPassword.toString().equals(rawPasswordEncoded);
	}

}
