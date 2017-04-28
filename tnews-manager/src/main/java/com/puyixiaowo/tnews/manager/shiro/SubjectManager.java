package com.puyixiaowo.tnews.manager.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.puyixiaowo.tnews.common.utils.Md5Utils;
import com.puyixiaowo.tnews.manager.bean.UserBean;


public class SubjectManager {

	/**
	 * 获取当前登录的用户User对象
	 * 
	 * @return
	 */
	public static UserBean getToken() {
		UserBean token = (UserBean) SecurityUtils.getSubject().getPrincipal();
		return token;
	}

	public static void login(String loginname, String password, Boolean rememberMe) {
		ShiroToken token = new ShiroToken(loginname, Md5Utils.md5(password));
		token.setRememberMe(rememberMe);
		SecurityUtils.getSubject().login(token);
	}
	
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	/**
	 * 获取session
	 * @return
	 */
	public static Session getSession(){
		return SecurityUtils.getSubject().getSession();
	}
}
