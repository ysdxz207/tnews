package com.puyixiaowo.tnews.common.utils;

import org.springframework.web.context.request.WebRequest;

public class AjaxUtils {

	private AjaxUtils() {
	}

	public static boolean isAjax(WebRequest webRequest) {
		String requestedWith = webRequest.getHeader("X-Requested-With");
		return requestedWith != null && "XMLHttpRequest".equals(requestedWith);
	}
}