package com.puyixiaowo.tnews.common.utils;

import org.apache.commons.lang.StringUtils;

public class ArrayUtils {
	
	/**
	 * 将英文逗号分隔的字符串转为Long类型数组;1,2,3,4=>[1,2,3,4]
	 * @param str
	 * @return
	 */
	public static Long[] parseToLongArray(String str) {
		if (StringUtils.isNotBlank(str)) {

			try {
				String[] strArr = str.split(",");
				Long[] result = new Long[strArr.length];
				for (int i = 0; i < strArr.length; i++)
					result[i] = Long.parseLong(strArr[i]);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new Long[0];
	}
}
