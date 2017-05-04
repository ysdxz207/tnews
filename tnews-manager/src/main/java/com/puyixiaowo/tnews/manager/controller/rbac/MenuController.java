package com.puyixiaowo.tnews.manager.controller.rbac;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.puyixiaowo.tnews.bean.PageBean;
import com.puyixiaowo.tnews.bean.ResponseBean;
import com.puyixiaowo.tnews.common.utils.CamelCaseUtils;
import com.puyixiaowo.tnews.manager.bean.MenuBean;
import com.puyixiaowo.tnews.manager.constants.RoutesRBAC;
import com.puyixiaowo.tnews.manager.controller.BaseController;
import com.puyixiaowo.tnews.manager.service.MenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单管理
 * @author huangfeihong
 * @date 2017年1月11日
 */
@RequestMapping("/menu")
@Controller
public class MenuController extends BaseController{
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/menus/{type}")
	@ResponseBody
	public Object menus(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable Integer type){
		
		List<MenuBean> list = new ArrayList<MenuBean>();
		try {
			list = menuService.selectNestedByType(type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return buildMenus(list);
	}
	
	/**
	 * @param list
	 * @return
	 */
	private JSONArray buildMenus(List<MenuBean> list) {
		JSONArray result = new JSONArray();
		
		for (MenuBean menuBean : list) {
			JSONObject menus = new JSONObject();
			if (StringUtils.isNotBlank(menuBean.getCode())) {
				menus.put("id", menuBean.getCode());
			}
			if (StringUtils.isNotBlank(menuBean.getMenuName())) {
				menus.put("name", menuBean.getMenuName());
			}
			menus.put("target", "navtab");
			if (StringUtils.isNotBlank(menuBean.getHref())) {
				menus.put("url", menuBean.getHref());
			}
			List<MenuBean> menuList = menuBean.getMenus();
			if (menuList != null && menuList.size() > 0) {
				JSONArray children = buildMenus(menuList);
				menus.put("children", children);
			}
			result.add(menus);
		}
		return result;
	}
	
	@RequiresPermissions("menu:view")
	@RequestMapping("/{data}")
	@ResponseBody
	public Object menu(Model model, HttpServletRequest request, HttpServletResponse response, MenuBean menuBean,
			@PathVariable Boolean data, String orders) {
		
		if (!data) {
			return new ModelAndView(RoutesRBAC.MENU_LIST);
		}
		Example example = new Example(MenuBean.class);
		Criteria c = example.createCriteria();
		// 参数处理
		if (StringUtils.isNotBlank(menuBean.getMenuName())) {
			c.andLike("menuName", "%" + menuBean.getMenuName() + "%");
		}
		if (StringUtils.isNotBlank(menuBean.getIcon())) {
			c.andLike("icon", "%" + menuBean.getIcon() + "%");
		}
		if (StringUtils.isNotBlank(menuBean.getRemark())) {
			c.andLike("remark", "%" + menuBean.getRemark() + "%");
		}
		if (menuBean.getSort() != null) {
			c.andEqualTo("sort", menuBean.getSort());
		}
		if (menuBean.getType() != null) {
			c.andEqualTo("type", menuBean.getType());
		}
		if (menuBean.getPid() != null) {
			c.andEqualTo("pid", menuBean.getPid());
		}
		if (menuBean.getExpand() != null) {
			c.andEqualTo("isExpand", menuBean.getExpand());
		}
		if (menuBean.getStatus() != null) {
			c.andEqualTo("status", menuBean.getStatus());
		}
		example.setOrderByClause(CamelCaseUtils.toUnderlineName(StringUtils.isNotBlank(orders) ? orders : null));// 排序
		PageBean pageBean = getPageBean(request);

		try {

			List<MenuBean> list = menuService.selectByExampleAndRowBounds(example, pageBean.getPageRowBounds());
			pageBean.setList(list);
			pageBean.setTotalCount(menuService.selectCountByExample(example));
		} catch (Exception e) {
			pageBean.errorMessage(e.getMessage());
		}

		return pageBean.serialize();
	}

	@RequiresPermissions(value = { "menu:add", "menu:edit" }, logical = Logical.AND)
	@RequestMapping(value = "/edit/{data}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object edit(Model model, HttpServletRequest request, HttpServletResponse response,
			@PathVariable Boolean data, Long id, String json) {
		ResponseBean responseBean = new ResponseBean();
		if (data) {
			try {
				menuService.insertOrUpdateSelective(json);
			} catch (RuntimeException e) {
				responseBean.error(e);
			}
			return responseBean;
		}

		return new ModelAndView(RoutesRBAC.MENU_EDIT);
	}

	@RequiresPermissions("menu:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(Model model, HttpServletRequest request, HttpServletResponse response, String id) {

		ResponseBean responseBean = new ResponseBean();

		try {
			menuService.delete(id);
		} catch (RuntimeException e) {
			responseBean.error(e);
		}
		return responseBean;
	}
	
	/*
	 * 查询菜单数组
	 */
	@RequiresPermissions("menu:view")
	@RequestMapping("/all/array")
	@ResponseBody
	public Object menuAllArray(Model model, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		Example example = new Example(MenuBean.class);
		Criteria c = example.createCriteria();
		c.andNotEqualTo("pid", 0);
		return menuService.selectByExampleAndRowBounds(example, new RowBounds());
	}
}
