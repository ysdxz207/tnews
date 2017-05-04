package com.puyixiaowo.tnews.manager.controller.rbac;

import com.aliyun.odps.utils.StringUtils;
import com.puyixiaowo.tnews.bean.PageBean;
import com.puyixiaowo.tnews.bean.ResponseBean;
import com.puyixiaowo.tnews.manager.bean.RoleBean;
import com.puyixiaowo.tnews.manager.bean.other.MenuPermissionBean;
import com.puyixiaowo.tnews.manager.constants.RoutesRBAC;
import com.puyixiaowo.tnews.manager.controller.BaseController;
import com.puyixiaowo.tnews.manager.service.MenuService;
import com.puyixiaowo.tnews.manager.service.RoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 角色管理
 * 
 * @author huangfeihong
 * @date 2017年1月12日
 */
@RequestMapping("/role")
@Controller
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	@RequiresPermissions("role:view")
	@RequestMapping("/{data}")
	@ResponseBody
	public Object role(Model model, HttpServletRequest request, HttpServletResponse response, RoleBean roleBean,
			@PathVariable Boolean data, String orders) {

		if (!data) {
			return new ModelAndView(RoutesRBAC.ROLE_LIST);
		}
		
		Example example = new Example(RoleBean.class);
		
		Criteria c = example.createCriteria();
		
		if (StringUtils.isNotBlank(roleBean.getRoleName())) {
			c.andLike("roleName", "%" + roleBean.getRoleName() + "%");
		}
		if (StringUtils.isNotBlank(roleBean.getCode())) {
			c.andLike("code", "%" + roleBean.getCode() + "%");
		}

		PageBean pageBean = getPageBean(request);

		try {
			pageBean.setList(roleService.selectByExampleAndRowBounds(example, pageBean.getPageRowBounds()));
			pageBean.setTotalCount(roleService.selectCountByExample(example));
		} catch (RuntimeException e) {
			pageBean.errorMessage(e.getMessage());
		}
		return pageBean.serialize();
	}

	@RequiresPermissions(value = { "role:add", "role:edit" }, logical = Logical.AND)
	@RequestMapping(value = "/edit/{data}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object edit(Model model, HttpServletRequest request, HttpServletResponse response,
			@PathVariable Boolean data, String json) {
		ResponseBean responseBean = new ResponseBean();
		if (data) {
			try {
				roleService.insertOrUpdateSelective(json);
			} catch (RuntimeException e) {
				responseBean.error(e);
			}
			return responseBean;
		}
		
		return new ModelAndView(RoutesRBAC.ROLE_EDIT);
	}

	@RequiresPermissions("role:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			String id) {

		ResponseBean responseBean = new ResponseBean();

		try {
			roleService.delete(id);
		} catch (RuntimeException e) {
			responseBean.error(e);
		}
		return responseBean;
	}
	
	/*
	 * 查询角色数组
	 */
	@RequiresPermissions("role:view")
	@RequestMapping("/all/array")
	@ResponseBody
	public Object roleAllArray(Model model, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		return roleService.selectAll();
	}
	
	@RequiresPermissions("role:setPermission")
	@RequestMapping(value = "/setPermission/{data}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object setPermission(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable Boolean data,
			@RequestParam(value="roleId", required=true) Long roleId,
			String permissionIds) {
		ResponseBean responseBean = new ResponseBean();
		if (data) {
			try {
				roleService.setPermission(roleId, permissionIds);
			} catch (RuntimeException e) {
				responseBean.error(e);
			}
			return responseBean;
		}
		//角色信息
		model.addAttribute("model", roleService.selectByPrimaryKey(roleId));
		
		//权限列表
		List<MenuPermissionBean> menuList = menuService.selectValidMenuPermissions(roleId);
		model.addAttribute("menuList", menuList);
	
		return new ModelAndView(RoutesRBAC.ROLE_PERMISSION_EDIT);
	}
}
