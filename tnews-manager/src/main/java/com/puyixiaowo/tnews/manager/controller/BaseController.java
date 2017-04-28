package com.puyixiaowo.tnews.manager.controller;

import com.puyixiaowo.tnews.bean.PageBean;
import com.puyixiaowo.tnews.common.utils.CamelCaseUtils;
import com.puyixiaowo.tnews.common.utils.Constants;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

	/**
	 * 
	 * @Title: getIp
	 * @Description:获取访问者IP 
	 *                      在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效
	 *                      。
	 *                      本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP
	 *                      (用,分割)， 如果还不存在则调用Request .getRemoteAddr()。
	 * @param @param request
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	/**
	 * 放置对象到seesion中
	 * 
	 * @param request
	 * @param key
	 * @param value
	 */
	protected void setSession(HttpServletRequest request, String key,
			Object value) {
		getSession(request).setAttribute(key, value);
	}

	/**
	 * 获取seesion对象
	 * 
	 * @param request
	 * @return
	 */
	protected HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}

	/**
	 * 通过key获取session中存放的对象
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	protected Object getSession(HttpServletRequest request, String key) {
		return getSession(request).getAttribute(key);
	}

	/**
	 * 获取session的id
	 * 
	 * @param request
	 * @return
	 */
	protected String getSesssionId(HttpServletRequest request) {
		return getSession(request).getId();
	}

	/*
	 * 获取分页RowBouds
	 */
	protected PageBean getPageBean(HttpServletRequest request) {
		String pageCurrentStr = request.getParameter("pageCurrent");
		String pageSizeStr = request.getParameter("pageSize");
		int pageCurrent = 1;
		int pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (StringUtils.isNotBlank(pageCurrentStr)) {
			pageCurrent = Integer.valueOf(pageCurrentStr);
		}
		if (StringUtils.isNotBlank(pageSizeStr)) {
			pageSize = Integer.valueOf(pageSizeStr);
		}
		return new PageBean(pageCurrent, pageSize);
	}

	/*
	 * 判断是否是ajax请求
	 */
	public boolean isAjaxt(HttpServletRequest request) {
		String requestedWith = request.getHeader("X-Requested-With");
		return requestedWith != null && "XMLHttpRequest".equals(requestedWith);
	}

	public Map<String, Object> getParams(String orders, Object object) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();

		if (object != null) {
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				String getter = "get" + firstLetter + fieldName.substring(1);
				Method method = object.getClass().getMethod(getter
				);
				Object value = method.invoke(object);

				if (value == null) {
					continue;
				}
				if (value.getClass() == String.class && StringUtils.isBlank(value.toString())) {
					continue;
				}
				params.put(fieldName, value);
			}
		}
		
		params.put("orders", CamelCaseUtils.toUnderlineName(StringUtils
				.isNotBlank(orders) ? orders : null));
		return params;
	}
	
}
