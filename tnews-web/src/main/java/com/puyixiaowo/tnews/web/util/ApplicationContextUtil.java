/**
 * 
 */
package com.puyixiaowo.tnews.web.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author huangfeihong
 * @date 2016年12月8日 下午7:42:42
 */
public class ApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		ApplicationContextUtil.context = context;
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		try {
			return context.getBean(name, clazz);
		} catch (Exception e) {
			return null;
		}
	}

}
