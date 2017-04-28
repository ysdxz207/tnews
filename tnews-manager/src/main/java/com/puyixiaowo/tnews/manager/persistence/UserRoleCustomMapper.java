package com.puyixiaowo.tnews.manager.persistence;

import java.util.Set;

import tk.mybatis.mapper.common.Mapper;

import com.puyixiaowo.tnews.manager.bean.UserRoleBean;

public interface UserRoleCustomMapper extends Mapper<UserRoleBean>{

	Set<String> selectPermissionByUserId(Long userId);
}
