/**
 * 
 */
package com.puyixiaowo.tnews.manager.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.puyixiaowo.tnews.common.utils.ArrayUtils;
import com.puyixiaowo.tnews.manager.bean.RoleBean;
import com.puyixiaowo.tnews.manager.bean.RolePermissionBean;
import com.puyixiaowo.tnews.manager.persistence.RoleCustomMapper;
import com.puyixiaowo.tnews.manager.persistence.RolePermissionCustomMapper;

/**
 * @author huangfeihong
 * @date 2017年1月8日 下午5:53:00
 */
@Service
public class RoleService {
	@Autowired
	private RoleCustomMapper roleCustomMapper;
	@Autowired
	private RolePermissionCustomMapper rolePermissionCustomMapper;
	
	
	/*
	 * 添加
	 */
	public int insertSelective(RoleBean roleBean) throws RuntimeException {
		return roleCustomMapper.insertSelective(roleBean);
	}

	/*
	 * 修改
	 */
	public int updateByPrimaryKeySelective(RoleBean roleBean)
			throws RuntimeException {
		return roleCustomMapper.updateByPrimaryKeySelective(roleBean);
	}

	/*
	 * 删除
	 */
	public void delete(String id) throws RuntimeException {
		if (StringUtils.isBlank(id)) {
			return;
		}
		Example example = new Example(RoleBean.class);
		Criteria c = example.createCriteria();
		c.andIn("id", Arrays.asList(id.split(",")));
		roleCustomMapper.deleteByExample(example);
	}
	/*
	 * 根据ID查询
	 */
	public RoleBean selectByPrimaryKey(Long id) {
		return roleCustomMapper.selectByPrimaryKey(id);
	}

	/*
	 * 分页查询
	 */
	public List<RoleBean> selectByRowBounds(RoleBean roleBean,
			RowBounds rowBounds) {
		
		return roleCustomMapper.selectByRowBounds(roleBean, rowBounds);
	}
	
	/*
	 * 分页查询
	 */
	public List<RoleBean> selectByExampleAndRowBounds(Example example,
			RowBounds rowBounds) {
		
		return roleCustomMapper.selectByExampleAndRowBounds(example, rowBounds);
	}
	
	/*
	 * 查询总记录数
	 */
	public int selectCount(RoleBean roleBean){
		return roleCustomMapper.selectCount(roleBean);
	}
	
	/*
	 * 查询总记录数
	 */
	public int selectCountByExample(Example example){
		return roleCustomMapper.selectCountByExample(example);
	}
	
	/*
	 * 查询所有
	 */
	public List<RoleBean> selectAll(){
		return roleCustomMapper.selectAll();
	}
	////////////////////////////////////////

	/*
	 * 添加或修改
	 */
	public void insertOrUpdateSelective(String json) {
		if (StringUtils.isBlank(json)) {
			return;
		}
		JSONArray arr = JSON.parseArray(json);
		for (int i = 0; i < arr.size(); i++) {
			RoleBean roleBean = arr.getJSONObject(i).toJavaObject(RoleBean.class);
			if (roleBean.getId() == null) {
				roleCustomMapper.insertSelective(roleBean);
			} else {
				roleCustomMapper.updateByPrimaryKeySelective(roleBean);
			}
		}
	}

	/*
	 * 赋予权限
	 */
	public void setPermission(Long roleId, String permissionIds) {
		if (StringUtils.isBlank(permissionIds)) {
			return;
		}
		Long [] ids = ArrayUtils.parseToLongArray(permissionIds);
		//删除角色权限
		Example example = new Example(RolePermissionBean.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("roleId", roleId);
		rolePermissionCustomMapper.deleteByExample(example);
		//添加角色权限
		for (Long permissionId : ids) {
			RolePermissionBean bean = new RolePermissionBean();
			bean.setRoleId(roleId);
			bean.setPermissionId(permissionId);
			rolePermissionCustomMapper.insertSelective(bean);
		}
		
	}
}
