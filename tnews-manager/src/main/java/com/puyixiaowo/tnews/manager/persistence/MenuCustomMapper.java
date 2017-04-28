package com.puyixiaowo.tnews.manager.persistence;

import com.puyixiaowo.tnews.manager.bean.MenuBean;
import com.puyixiaowo.tnews.manager.bean.other.MenuPermissionBean;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface MenuCustomMapper extends Mapper<MenuBean> {

	List<MenuBean> selectNestedByType(Map<String, Object> params);

	List<MenuPermissionBean> selectValidMenuPermissions(@Param("roleId") Long roleId);
}
