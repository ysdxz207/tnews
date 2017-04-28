/**
 * 
 */
package com.puyixiaowo.tnews.manager.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.puyixiaowo.tnews.common.utils.Md5Utils;
import com.puyixiaowo.tnews.manager.bean.UserBean;
import com.puyixiaowo.tnews.manager.bean.UserRoleBean;
import com.puyixiaowo.tnews.manager.persistence.UserCustomMapper;
import com.puyixiaowo.tnews.manager.persistence.UserRoleCustomMapper;
import com.puyixiaowo.tnews.manager.shiro.SubjectManager;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.*;

/**
 * @author huangfeihong
 * @date 2017年1月8日 下午5:53:00
 */
@Service
public class UserService {
	@Autowired
	private UserCustomMapper userCustomMapper;
	@Autowired
	private UserRoleCustomMapper userRoleCustomMapper;

	/*
	 * 添加
	 */
	public int insertSelective(UserBean userBean) throws RuntimeException {
		return userCustomMapper.insertSelective(userBean);
	}

	/*
	 * 修改
	 */
	public int updateByPrimaryKeySelective(UserBean userBean)
			throws RuntimeException {
		return userCustomMapper.updateByPrimaryKeySelective(userBean);
	}

	/*
	 * 删除
	 */
	public void delete(String id) throws RuntimeException {
		if (StringUtils.isBlank(id)) {
			return;
		}
		Example example = new Example(UserBean.class);
		Criteria c = example.createCriteria();
		c.andIn("id", Arrays.asList(id.split(",")));
		userCustomMapper.deleteByExample(example);
	}
	/*
	 * 根据ID查询信息
	 */
	public UserBean selectByPrimaryKey(Long id) {
		return userCustomMapper.selectByPrimaryKey(id);
	}

	/*
	 * 分页查询
	 */
	public List<UserBean> selectByRowBounds(UserBean userBean,
			RowBounds rowBounds) {
		
		return userCustomMapper.selectByRowBounds(userBean, rowBounds);
	}
	
	/*
	 * 分页查询
	 */
	public List<UserBean> selectByExampleAndRowBounds(Example example,
			RowBounds rowBounds) {
		
		return userCustomMapper.selectByExampleAndRowBounds(example, rowBounds);
	}
	
	/*
	 * 查询总记录数
	 */
	public int selectCount(UserBean userBean){
		return userCustomMapper.selectCount(userBean);
	}
	
	/*
	 * 查询总记录数
	 */
	public int selectCountByExample(Example example){
		return userCustomMapper.selectCountByExample(example);
	}
	/*
	 * 查询所有
	 */
	public List<UserBean> selectAll(){
		return userCustomMapper.selectAll();
	}
	
	/////////////////////////////////////
	/*
	 * 根据登录名获取
	 */
	public UserBean getUserByLoginName(UserBean userBean) {
		Example example = new Example(UserBean.class);
		Criteria c = example.createCriteria();
		c.andEqualTo(userBean);
		List<UserBean> list = userCustomMapper.selectByExample(example);
		if (list.size() == 1) {
			userBean = list.get(0);
		} else {
			userBean = null;
		}

		return userBean;
	}

	/*
	 * 更新最后登录时间
	 */
	public void updateLastLoginTime(UserBean userBean) throws RuntimeException {
		userBean.setLastLoginTime(new Date());

		Example example = new Example(UserBean.class);
		Criteria c = example.createCriteria();
		c.andEqualTo(userBean);

		userCustomMapper.updateByExampleSelective(userBean, example);
	}
	/*
	 * 添加或修改
	 */
	public void insertOrUpdateSelective(String json) throws RuntimeException{
		if (StringUtils.isBlank(json)) {
			return;
		}
		JSONArray arr = JSON.parseArray(json);
		for (int i = 0; i < arr.size(); i++) {
			UserBean userBean = arr.getJSONObject(i).toJavaObject(UserBean.class);
			if (userBean.getId() == null) {
				//判断是否已存在用户
				UserBean userBeanParams = new UserBean();
				userBeanParams.setLoginname(userBean.getLoginname());
				if (userCustomMapper.selectCount(userBeanParams) > 0) {
					throw new RuntimeException("用户已存在");
				}
				userBean.setCreateTime(new Date());
				userBean.setPassword(Md5Utils.md5(userBean.getPassword()));
				userCustomMapper.insertSelective(userBean);
			} else {
				userBean.setPassword(userBean.getPassword() == null?null:Md5Utils.md5(userBean.getPassword()));
				userCustomMapper.updateByPrimaryKeySelective(userBean);
			}
			//角色
			if (userBean.getRoleId() != null) {
				UserRoleBean userRoleBean = new UserRoleBean();
				userRoleBean.setUserId(userBean.getId());
				userRoleCustomMapper.delete(userRoleBean);
				userRoleBean.setRoleId(userBean.getRoleId());
				userRoleCustomMapper.insertSelective(userRoleBean);
			}

		}
	}

	/*
	 * 根据参数查询带角色的用户列表
	 */
	public List<UserBean> selectListWithRoleByParams(Map<String, Object> params) {
		UserBean currentUser = SubjectManager.getToken();
		List<UserBean> list = userCustomMapper.selectListWithRoleByParams(params);
		Iterator<UserBean> it = list.iterator();
		while(it.hasNext()) {
			UserBean bean = it.next();
			if ("admin".equals(bean.getLoginname()) || 
					currentUser.getLoginname().equals(bean.getLastLoginTime())) {
				it.remove();
			}
		}
		return list;
	}
	/*
	 * 
	 */
	public int selectCountByParams(Map<String, Object> params) {
		
		return userCustomMapper.selectCountByParams(params);
	}

}
