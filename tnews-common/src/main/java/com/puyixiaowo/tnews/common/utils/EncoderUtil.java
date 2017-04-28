package com.puyixiaowo.tnews.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
/**
 * 加密工具
 */
public class EncoderUtil {
 
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
 
	/**
	 * encode string
	 *
	 * @param algorithm
	 * @param str
	 * @return String
	 */
	private static String encode(String algorithm, String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
 
	}
	
	/**
	 * @Des 得到相应的一个MD5加密后的字符串
	 * @param psd
	 * @param salt
	 * @return    MD5加密后的字符串
	 */
	private static String encodeByMd5(String str, String salt) {
	    try {
	        StringBuffer stingBuffer = new StringBuffer();
	        // 1.指定加密算法
	        MessageDigest digest = MessageDigest.getInstance("MD5");
	        // 2.将需要加密的字符串转化成byte类型的数据，然后进行哈希过程
	        byte[] bs = digest.digest((str + salt).getBytes());
	        // 3.遍历bs,让其生成32位字符串，固定写法

	        // 4.拼接字符串
	        for (byte b : bs) {
	            int i = b & 0xff;
	            String hexString = Integer.toHexString(i);
	            if (hexString.length() < 2) {
	                hexString = "0" + hexString;
	            }
	            stingBuffer.append(hexString);
	        }
	        return stingBuffer.toString();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
 
	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 *
	 * @param bytes
	 *            the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) { 			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
	/**
	 * 123edu密码加密算法
	 * @param password
	 * @return
	 */
	public static String encodePass(String password){
		return EncoderUtil.encodeByMd5(EncoderUtil.encode("SHA1", password), Constants.MD5_SALT);
	}
 
	public static void main(String[] args) {
		System.out.println("111111 MD5  :"
				+ EncoderUtil.encode("MD5", "111111"));
		System.out.println("111111 SHA1 :"
				+ EncoderUtil.encode("SHA1", "111111"));
		System.out.println("password:" + EncoderUtil.encodeByMd5(EncoderUtil.encode("SHA1", "123456"), Constants.MD5_SALT));
	}
 
}