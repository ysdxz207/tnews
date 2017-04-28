/**
 * 
 */
package com.puyixiaowo.tnews.manager.service;

import com.puyixiaowo.tnews.manager.bean.UserRoleBean;
import com.puyixiaowo.tnews.manager.persistence.UserRoleCustomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author huangfeihong
 * @date 2017年1月8日 下午5:53:00
 */
@Service
public class UserRoleService {
	
	@Autowired
	UserRoleCustomMapper userRoleCustomMapper;
	
	/**
	 * @param userId
	 * @return
	 */
	public Set<String> selectPermissionByUserId(Long userId) {
		
		return userRoleCustomMapper.selectPermissionByUserId(userId);
	}

	public UserRoleBean selectOne(UserRoleBean userRoleBean) {
		return userRoleCustomMapper.selectOne(userRoleBean);
	}
}
