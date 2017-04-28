/**
 * 
 */
package com.puyixiaowo.tnews.manager.controller.tnews;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.puyixiaowo.tnews.bean.PageBean;
import com.puyixiaowo.tnews.manager.constants.RoutesNews;
import com.puyixiaowo.tnews.manager.controller.BaseController;
import com.puyixiaowo.tnews.news.bean.ApiChannelBean;
import com.puyixiaowo.tnews.news.service.ApiChannelService;

/**
 * 接口频道
 * @author huangfeihong
 * @date 2017年1月24日 下午9:48:48
 */
@Controller
@RequestMapping("/api/channel")
public class ApiChannelController extends BaseController{
	
	@Autowired
	private ApiChannelService apiChannelService;
	
	@RequiresPermissions("news:view")
	@RequestMapping("/{data}")
	@ResponseBody
	public Object newsChannel(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			ApiChannelBean apiChannelBean,
			@PathVariable Boolean data, String orders) {
		
		if (!data) {
			return new ModelAndView(RoutesNews.NEWS_CHANNEL_LIST);
		}
		
		PageBean pageBean = getPageBean(request);

		try {

			List<ApiChannelBean> list = apiChannelService.selectByExampleAndRowBounds(apiChannelBean, pageBean.getPageRowBounds());
			pageBean.setList(list);
			pageBean.setTotalCount(apiChannelService.selectCountByExample(apiChannelBean));
		} catch (Exception e) {
			pageBean.error(e.getMessage());
		}

		return pageBean.serialize();
	}
	
	/*
	 * 查询新闻频道数组
	 */
	@RequiresPermissions("news:view")
	@RequestMapping("/all/array")
	@ResponseBody
	public Object apiChannelAllArray(Model model, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		return apiChannelService.selectAll();
	}
	
}
