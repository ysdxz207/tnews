package com.puyixiaowo.tnews.common.utils;

import java.util.Map;

/**
 * @author weishaoqiang
 * @date 2017-04-26 17:53
 */
public class MapUtils {

	public static String toUrlParams(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");
		}
		return s;
	}
}
