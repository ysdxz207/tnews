package com.puyixiaowo.tnews.manager.controller.rbac;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.puyixiaowo.tnews.bean.PageBean;
import com.puyixiaowo.tnews.bean.ResponseBean;
import com.puyixiaowo.tnews.manager.bean.RoleBean;
import com.puyixiaowo.tnews.manager.bean.UserBean;
import com.puyixiaowo.tnews.manager.constants.RoutesRBAC;
import com.puyixiaowo.tnews.manager.controller.BaseController;
import com.puyixiaowo.tnews.manager.service.RoleService;
import com.puyixiaowo.tnews.manager.service.UserService;
import com.puyixiaowo.tnews.manager.shiro.SubjectManager;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author huangfeihong
 * @date 2017年1月11日
 */
@RequestMapping("/user")
@Controller
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@RequiresPermissions("user:view")
	@RequestMapping("/{data}")
	@ResponseBody
	public Object user(Model model,
					   HttpServletRequest request,
					   HttpServletResponse response,
					   UserBean userBean,
					   String orders,
					   @PathVariable Boolean data) {

		if (!data) {
			//查询角色列表
			List<RoleBean> roles = roleService.selectAll();
			model.addAttribute("roles", JSON.toJSONString(roles));
			return new ModelAndView(RoutesRBAC.USER_LIST);
		}

		PageBean pageBean = getPageBean(request);

		try {
			Map<String, Object> params = getParams(orders, userBean);
			List<UserBean> list = userService.selectListWithRoleByParams(params);
			pageBean.setList(list);
			pageBean.setTotalCount(userService.selectCountByParams(params));
		} catch (Exception e) {
			pageBean.errorMessage(e.getMessage());
		}
		return pageBean.serialize();
	}

	@RequiresPermissions(value = {"user:add", "user:edit"}, logical = Logical.AND)
	@RequestMapping(value = "/edit/{data}", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Object edit(Model model, HttpServletRequest request, HttpServletResponse response,
					   @PathVariable Boolean data,
					   Long id,
					   String json,
					   UserBean userBean,
					   @RequestParam(value="type", defaultValue = "info") String type) {
		ResponseBean responseBean = new ResponseBean();
		if (data) {
			try {
				if (StringUtils.isBlank(json) && userBean != null) {
					JSONArray arr = new JSONArray();
					arr.add(userBean);
					json = arr.toJSONString();
				}
				userService.insertOrUpdateSelective(json);
			} catch (RuntimeException e) {
				responseBean.error(e);
			}
			return responseBean;
		}

		model.addAttribute("model", userService.selectByPrimaryKey(id));

		String route = RoutesRBAC.USER_EDIT;
		if (id.intValue() == SubjectManager.getToken().getId()) {
			switch (type) {

				case "info":
					route = RoutesRBAC.USER_EDIT_CURRENT;
					break;
				case "pass":
					route = RoutesRBAC.USER_EDIT_PASS;
					break;
			}

		}
		return new ModelAndView(route);

	}

	@RequiresPermissions("user:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(Model model, HttpServletRequest request, HttpServletResponse response, String id) {

		ResponseBean responseBean = new ResponseBean();

		try {
			userService.delete(id);
		} catch (RuntimeException e) {
			responseBean.error(e);
		}
		return responseBean;
	}

	/*
	 * 查询用户数组
	 */
	@RequiresPermissions("user:view")
	@RequestMapping("/all/array")
	@ResponseBody
	public Object userAllArray(Model model,
							   HttpServletRequest request,
							   HttpServletResponse response) {
		List<UserBean> list = userService.selectAll();
		UserBean bean1 = new UserBean();
		bean1.setId(0L);
		bean1.setNickname("<font color=\"#38aff3\">无[接口直接导入]</font>");
		list.add(0, bean1);

		UserBean bean2 = new UserBean();
		bean2.setId(-1L);
		bean2.setNickname("<font color=\"#1AA260\">无[接口字典筛选]</font>");
		list.add(1, bean2);
		return list;
	}
}
