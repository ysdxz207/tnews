package com.puyixiaowo.tnews.common.utils;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


public class JsonUtils {

	//private final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	public static boolean isBadJsonObject(String json) {
		return !isGoodJsonObject(json);
	}

	
	public static boolean isGoodJsonObject(String json) {
		if (StringUtils.isBlank(json)){
			return false;
		}
		try {
			JSONObject.parse(json);
		} catch (JSONException ex) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否丢失最后大括号
	 * @param responseValue
	 * @return
	 */
	public static boolean isLostJsonEnd(String responseValue) {
		return !responseValue.endsWith("}");
	}


	public static void makeJsonEnd(String responseValue) {
		if (isLostJsonEnd(responseValue)){
			System.out.println("do make json end");
			System.out.println(responseValue);
			responseValue = responseValue + "}";
		}
	}
	public static void main(String[] args) {
		String aa = null;
		System.out.println(isBadJsonObject(aa));
	}
}