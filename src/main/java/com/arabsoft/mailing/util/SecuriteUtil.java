package com.arabsoft.mailing.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecuriteUtil {
	private static final String SHA_512 = "SHA-512";
	private static final String UTF_8 = "UTF-8";

	public static String getSHA512SecurePassword(String passwordToHash)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {

		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance(SHA_512);
			byte[] bytes = md.digest(passwordToHash.getBytes(UTF_8));
			StringBuilder sb = new StringBuilder();
			for (byte aByte : bytes) {
				sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw e;
		}
		return generatedPassword;
	}
}
