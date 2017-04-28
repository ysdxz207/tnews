package com.puyixiaowo.tnews.manager.persistence;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.puyixiaowo.tnews.manager.bean.UserBean;

public interface UserCustomMapper extends Mapper<UserBean>{

	List<UserBean> selectListWithRoleByParams(Map<String, Object> params);

	int selectCountByParams(Map<String, Object> params);
}
