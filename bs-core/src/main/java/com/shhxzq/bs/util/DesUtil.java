package com.shhxzq.bs.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 中信联名卡加密
 */
public class DesUtil {
	/**
	 * 
	 * @param sText
	 * @param base64Key
	 * @return 
	 * @throws Exception
	 */
	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	public static byte[] encryptDES(String encryptString, String encryptKey)

	throws Exception {

		IvParameterSpec zeroIv = new IvParameterSpec(iv);

		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");

		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);

		byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));

		return encryptedData;

	}

	public static String encryptDESwithBase64(String encryptString, String encryptKey) throws Exception

	{
		if(StringUtils.isEmpty(encryptString)){
			return null;
		}
		BASE64Encoder enc = new BASE64Encoder();
		return enc.encode(encryptDES(encryptString, encryptKey));

	}

	public static String decryptDES(byte[] encryptedData, String decryptKey)

	throws Exception {

		IvParameterSpec zeroIv = new IvParameterSpec(iv);

		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("UTF-8"), "DES");

		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);

		byte decryptedData[] = cipher.doFinal(encryptedData);

		String decryptedString = new String(decryptedData, "UTF-8");

		return decryptedString;

	}

	public static String decryptDESwithBase64(String encryptedString, String decryptKey) throws Exception

	{
		BASE64Decoder dec = new BASE64Decoder();
		byte[] encryptedData = dec.decodeBuffer(encryptedString);
		return decryptDES(encryptedData, decryptKey);

	}

	public static void main(String[] args) throws Exception {
		try {
			//加密
			System.out.println(encryptDESwithBase64("加密数据", "qwertyui"));
			//解密
			System.out.println(decryptDESwithBase64("WJltGDDI290bxJp2fHud2w==", "qwertyui"));
		} catch (Exception e) {
			System.out.println("解密异常:" + e);
		}
	}
}
