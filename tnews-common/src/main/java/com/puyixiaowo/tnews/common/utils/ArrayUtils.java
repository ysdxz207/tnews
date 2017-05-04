package com.puyixiaowo.tnews.common.utils;

import java.util.stream.Stream;

public class ArrayUtils {
	
	/**
	 * 将英文逗号分隔的字符串转为Long类型数组;1,2,3,4=>[1,2,3,4]
	 * @param str
	 * @return
	 */
	public static long[] parseToLongArray(String str) {
		return Stream.of(str.split(",")).mapToLong(Long::valueOf).toArray();
	}
}
