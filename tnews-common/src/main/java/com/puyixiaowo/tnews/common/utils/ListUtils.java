package com.puyixiaowo.tnews.common.utils;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListUtils {

	/**
	 * 判断list中是否有重复元素
	 * 
	 * @param list
	 * @return
	 */
	public static boolean hasRepeat(List<? extends Object> list) {
		if (null == list)
			return false;
		return list.size() == new HashSet<Object>(list).size();
	}

	/**
	 * 判断list中元素是否完全相同
	 * 
	 * @param list
	 * @return
	 */
	public static boolean hasSame(List<? extends Object> list) {
		if (null == list)
			return false;
		return 1 == new HashSet<Object>(list).size();
	}
	
	/**
	 * 
	 * @param list
	 * @param property
	 * 			属性名
	 * @return
	 */
	public static String join(List<?> list, String property) {
		
		String str = "";
		try {
			String m = property.substring(0, 1).toUpperCase() + property.substring(1);
			List<String> strList = new ArrayList<>();
			for (Object object : list) {
				Method method = object.getClass().getMethod("get" + m);
				Object o = method.invoke(object);
				if (o != null) {
					String result = o.toString();
					strList.add(result);
				}
			}
			str = StringUtils.join(strList, ",");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}

	public static boolean isEmpty(List<?> list) {
		return list == null || list.size() == 0;
	}

	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}

}
