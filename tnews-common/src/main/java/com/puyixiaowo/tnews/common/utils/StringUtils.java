package com.puyixiaowo.tnews.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StringUtils {
	
	
	public static int stringContainsNumFromList(String inputStr, String[] items) {
		int n = 0;
		List<String> ls = Arrays.asList(items);
		List<String> list = new ArrayList<String>(ls);

		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String str = it.next();
			if (inputStr.contains(str)) {
				it.remove();
				n++;
			}
		}
		return n;
	}
	/**
	 * 获取两个字符串的相似度
	 * @param str
	 * @param target
	 * @return
	 */
	public static float getSimilarityRatio(String str, String target) {
		return new Levenshtein().getSimilarityRatio(str, target);
	}
	
	public static void main(String[] args) {
		String inputStr = "我国哈哈了宇宙宇宙宇宙";
		String[] items = { "天文", "宇宙" };
		int n = stringContainsNumFromList(inputStr, items);
		System.out.println(n);
	}
}
