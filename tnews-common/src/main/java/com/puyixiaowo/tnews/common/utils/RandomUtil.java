package com.puyixiaowo.tnews.common.utils;

/**
 * 生成随机数工具
 * @author huangfeihong
 * @date 2017年2月24日
 */
public class RandomUtil {
	/**
	 * 生成六位随机数
	 * 
	 * @return
	 */
	public static String generateRandomSix() {

		return createRandom(true, 6);
	}

	/**
	 * 
	 * @param numberFlag
	 * 			纯数字
	 * @param length
	 * 			生成的长度
	 * @return
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890"
				: "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);
		return retStr;
	}

	public static void main(String[] args) {
		System.out.println(generateRandomSix());
	}
}
