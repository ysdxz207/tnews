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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.puyixiaowo.tnews.manager.bean.PermissionBean;
import com.puyixiaowo.tnews.manager.persistence.PermissionCustomMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 权限
 * @author huangfeihong
 * @date 2017年1月16日
 */
@Service
public class PermissionService {
	@Autowired
	private PermissionCustomMapper permissionCustomMapper;

	/*
	 * 添加
	 */
	public int insertSelective(PermissionBean permissionBean) throws RuntimeException {
		return permissionCustomMapper.insertSelective(permissionBean);
	}

	/*
	 * 修改
	 */
	public int updateByPrimaryKeySelective(PermissionBean permissionBean)
			throws RuntimeException {
		return permissionCustomMapper.updateByPrimaryKeySelective(permissionBean);
	}

	/*
	 * 删除
	 */
	public int delete(String id) throws RuntimeException {
		if (StringUtils.isBlank(id)) {
			return 0;
		}
		Example example = new Example(PermissionBean.class);
		Criteria c = example.createCriteria();
		c.andIn("id", Arrays.asList(id.split(",")));
		return permissionCustomMapper.deleteByExample(example);
	}

	/*
	 * 根据ID查询信息
	 */
	public PermissionBean selectByPrimaryKey(Long id) {
		return permissionCustomMapper.selectByPrimaryKey(id);
	}

	/*
	 * 分页查询
	 */
	public List<PermissionBean> selectByRowBounds(PermissionBean permissionBean,
			RowBounds rowBounds) {

		return permissionCustomMapper.selectByRowBounds(permissionBean, rowBounds);
	}

	/*
	 * 分页查询
	 */
	public List<PermissionBean> selectByExampleAndRowBounds(Example example,
			RowBounds rowBounds) {
		
		return permissionCustomMapper.selectByExampleAndRowBounds(example, rowBounds);
	}

	/*
	 * 查询总记录数
	 */
	public int selectCount(PermissionBean permissionBean) {
		return permissionCustomMapper.selectCount(permissionBean);
	}

	/*
	 * 查询总记录数
	 */
	public int selectCountByExample(Example example) {
		return permissionCustomMapper.selectCountByExample(example);
	}
	
	/*
	 * 查询所有
	 */
	public List<PermissionBean> selectAll(){
		return permissionCustomMapper.selectAll();
	}

	/////////////////////////
	
	/*
	 * 添加或修改
	 */
	public void insertOrUpdateSelective(String json) throws RuntimeException{
		if (StringUtils.isBlank(json)) {
			return;
		}
		JSONArray arr = JSON.parseArray(json);
		for (int i = 0; i < arr.size(); i++) {
			PermissionBean permissionBean = arr.getJSONObject(i).toJavaObject(PermissionBean.class);
			if (permissionBean.getId() == null) {
				permissionCustomMapper.insertSelective(permissionBean);
			} else {
				permissionCustomMapper.updateByPrimaryKeySelective(permissionBean);
			}
		}
	}
}
