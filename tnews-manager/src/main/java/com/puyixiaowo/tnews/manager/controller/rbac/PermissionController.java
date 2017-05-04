package com.puyixiaowo.tnews.manager.controller.rbac;

import com.puyixiaowo.tnews.bean.PageBean;
import com.puyixiaowo.tnews.bean.ResponseBean;
import com.puyixiaowo.tnews.common.utils.CamelCaseUtils;
import com.puyixiaowo.tnews.manager.bean.PermissionBean;
import com.puyixiaowo.tnews.manager.constants.RoutesRBAC;
import com.puyixiaowo.tnews.manager.controller.BaseController;
import com.puyixiaowo.tnews.manager.service.PermissionService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 权限管理
 * @author huangfeihong
 * @date 2017年1月16日
 */
@RequestMapping("/permission")
@Controller
public class PermissionController extends BaseController{
	
	@Autowired
	private PermissionService permissionService;
	
	
	@RequiresPermissions("permission:view")
	@RequestMapping("/{data}")
	@ResponseBody
	public Object permission(Model model, HttpServletRequest request, HttpServletResponse response, PermissionBean permissionBean,
			@PathVariable Boolean data, String orders) {
		
		if (!data) {
			return new ModelAndView(RoutesRBAC.PERMISSION_LIST);
		}
		Example example = new Example(PermissionBean.class);
		Criteria c = example.createCriteria();
		// 参数处理
		if (StringUtils.isNotBlank(permissionBean.getPermissionName())) {
			c.andLike("permissionName", "%" + permissionBean.getPermissionName() + "%");
		}
		if (permissionBean.getMenuId() != null) {
			c.andEqualTo("menuId", permissionBean.getMenuId());
		}
		if (StringUtils.isNotBlank(permissionBean.getPermission())) {
			c.andLike("permission", "%" + permissionBean.getPermission() + "%");
		}
		
		example.setOrderByClause(CamelCaseUtils.toUnderlineName(StringUtils.isNotBlank(orders) ? orders : null));// 排序
		PageBean pageBean = getPageBean(request);

		try {

			List<PermissionBean> list = permissionService.selectByExampleAndRowBounds(example, pageBean.getPageRowBounds());
			pageBean.setList(list);
			pageBean.setTotalCount(permissionService.selectCountByExample(example));
		} catch (RuntimeException e) {
			pageBean.errorMessage(e.getMessage());
		}

		return pageBean.serialize();
	}

	@RequiresPermissions(value = { "permission:add", "permission:edit" }, logical = Logical.AND)
	@RequestMapping(value = "/edit/{data}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object edit(Model model, HttpServletRequest request, HttpServletResponse response,
			@PathVariable Boolean data, Long id, String json) {
		ResponseBean responseBean = new ResponseBean();
		if (data) {
			try {
				permissionService.insertOrUpdateSelective(json);
			} catch (RuntimeException e) {
				responseBean.error(e);
			}
			return responseBean;
		}

		return new ModelAndView(RoutesRBAC.PERMISSION_EDIT);
	}

	@RequiresPermissions("permission:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(Model model, HttpServletRequest request, HttpServletResponse response, String id) {

		ResponseBean responseBean = new ResponseBean();

		try {
			permissionService.delete(id);
		} catch (RuntimeException e) {
			responseBean.error(e);
		}
		return responseBean;
	}
	
	/*
	 * 查询权限数组
	 */
	@RequiresPermissions("permission:view")
	@RequestMapping("/all/array")
	@ResponseBody
	public Object permissionAllArray(Model model, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		return permissionService.selectAll();
	}
}
