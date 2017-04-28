package com.puyixiaowo.tnews.manager.controller.dictionary;

import com.puyixiaowo.tnews.bean.PageBean;
import com.puyixiaowo.tnews.bean.PageRowBounds;
import com.puyixiaowo.tnews.bean.ResponseBean;
import com.puyixiaowo.tnews.dictionary.bean.DictionaryBean;
import com.puyixiaowo.tnews.dictionary.service.DictionaryService;
import com.puyixiaowo.tnews.manager.constants.RoutesDictionary;
import com.puyixiaowo.tnews.manager.constants.RoutesRBAC;
import com.puyixiaowo.tnews.manager.controller.BaseController;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 字典管理
 * @author huangfeihong
 * @date 2017年2月2日 下午1:45:46
 */
@RequestMapping("/dictionary")
@Controller
public class DictionaryController extends BaseController{
	
	@Autowired
	private DictionaryService dictionaryService;
	
	
	@RequiresPermissions("dictionary:view")
	@RequestMapping("/{data}")
	@ResponseBody
	public Object dictionary(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			DictionaryBean dictionaryBean,
			@PathVariable Boolean data, String orders) {
		
		if (!data) {
			return new ModelAndView(RoutesDictionary.DICTIONARY_LIST);
		}
		
		PageBean pageBean = getPageBean(request);

		try {

			List<DictionaryBean> list = dictionaryService.selectByExampleAndRowBounds(dictionaryBean, pageBean.getPageRowBounds());
			pageBean.setList(list);
			pageBean.setTotalCount(dictionaryService.selectCountByExample(dictionaryBean));
		} catch (RuntimeException e) {
			pageBean.error(e.getMessage());
		}

		return pageBean.serialize();
	}

	@RequiresPermissions(value = { "dictionary:add", "dictionary:edit" }, logical = Logical.AND)
	@RequestMapping(value = "/edit/{data}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object edit(Model model, HttpServletRequest request, HttpServletResponse response,
			@PathVariable Boolean data, Long id, String json) {
		ResponseBean responseBean = new ResponseBean();
		if (data) {
			try {
				dictionaryService.insertOrUpdateSelective(json);
			} catch (RuntimeException e) {
				responseBean.error(e.getMessage());
			}
			return responseBean;
		}

		return new ModelAndView(RoutesRBAC.PERMISSION_EDIT);
	}

	@RequiresPermissions("dictionary:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			String id) {

		ResponseBean responseBean = new ResponseBean();

		try {
			dictionaryService.delete(id);
		} catch (RuntimeException e) {
			responseBean.error(e.getMessage());
		}
		return responseBean;
	}
	
	/*
	 * 查询字典数组
	 */
	@RequiresPermissions("dictionary:view")
	@RequestMapping("/all/array")
	@ResponseBody
	public Object dictionaryAllArray(Model model, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		DictionaryBean dictionaryBean = new DictionaryBean();
		dictionaryBean.setTypeDic("dic_type");
		return dictionaryService.selectByExampleAndRowBounds(dictionaryBean, new PageRowBounds());
	}
}
