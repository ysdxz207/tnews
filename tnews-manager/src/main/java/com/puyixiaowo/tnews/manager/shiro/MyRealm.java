package com.puyixiaowo.tnews.manager.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puyixiaowo.tnews.manager.bean.UserBean;
import com.puyixiaowo.tnews.manager.service.UserRoleService;
import com.puyixiaowo.tnews.manager.service.UserService;

@Service
public class MyRealm extends AuthorizingRealm {

	@Autowired
	UserService userService;
	@Autowired
	UserRoleService userRoleService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {
		UserBean user = (UserBean) principalCollection.fromRealm(getName())
				.iterator().next();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> permissons = userRoleService
				.selectPermissionByUserId(user.getId());
		if (permissons.size() == 1 && permissons.iterator().next() == null) {
			permissons = new HashSet<String>();
		}
		info.setStringPermissions(permissons);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {
		ShiroToken token = (ShiroToken) authenticationToken;
		String loginname = token.getUsername();
		UserBean u = new UserBean();
		u.setLoginname(loginname);
		UserBean user = userService.getUserByLoginName(u);
		if (null == user || !token.getPswd().equals(user.getPassword())) {
			throw new AccountException("帐号或密码不正确！");
		}
		if (1 != user.getStatus()) {
			throw new DisabledAccountException("账号已被禁止登录!");
		}
		userService.updateLastLoginTime(u);
		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}

}