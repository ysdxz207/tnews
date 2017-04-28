package com.puyixiaowo.tnews.manager.controller.rbac;

import com.puyixiaowo.tnews.manager.bean.UserRoleBean;
import com.puyixiaowo.tnews.manager.service.RoleService;
import com.puyixiaowo.tnews.manager.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.puyixiaowo.tnews.manager.bean.UserBean;
import com.puyixiaowo.tnews.manager.controller.BaseController;
import com.puyixiaowo.tnews.manager.shiro.SubjectManager;

/**
 * @author huangfeihong
 * @date 2017年1月7日 下午11:27:20
 */
@Controller
public class IndexController extends BaseController{

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	
	@RequestMapping("/")
	public String index(Model model){
		//查询当前用户信息
		UserBean user = SubjectManager.getToken();
		try {
			UserRoleBean userRoleBean = new UserRoleBean();
			userRoleBean.setUserId(user.getId());
			userRoleBean = userRoleService.selectOne(userRoleBean);
			user.setRoleName(roleService.selectByPrimaryKey(userRoleBean.getRoleId()).getRoleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("user",user);
		return "index";
	}
	@RequestMapping("/main")
	public String main(){
		
		return "base/main";
	}
}
