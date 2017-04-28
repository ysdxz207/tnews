/**
 * 
 */
package com.puyixiaowo.tnews.manager.controller.tnews;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.puyixiaowo.tnews.bean.PageBean;
import com.puyixiaowo.tnews.bean.ResponseBean;
import com.puyixiaowo.tnews.common.utils.RedisUtils;
import com.puyixiaowo.tnews.enums.RedisKeys;
import com.puyixiaowo.tnews.manager.constants.RoutesNews;
import com.puyixiaowo.tnews.manager.controller.BaseController;
import com.puyixiaowo.tnews.news.bean.NewsChannelBean;
import com.puyixiaowo.tnews.news.service.ApiChannelService;
import com.puyixiaowo.tnews.news.service.NewsChannelService;

/**
 * 新闻频道
 * @author huangfeihong
 * @date 2017年1月22日 下午11:31:25
 */
@Controller
@RequestMapping("/news/channel")
public class NewsChannelController extends BaseController{
	
	@Autowired
	private NewsChannelService newsChannelService;
	@Autowired
	private ApiChannelService apiChannelService;
	@Autowired
	private RedisUtils redisUtils;
	
	@RequiresPermissions("news:channel:view")
	@RequestMapping("/{data}")
	@ResponseBody
	public Object newsChannel(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			NewsChannelBean newsChannelBean,
			@PathVariable Boolean data, String orders) {
		
		if (!data) {
			return new ModelAndView(RoutesNews.NEWS_CHANNEL_LIST);
		}
		
		PageBean pageBean = getPageBean(request);

		try {

			List<NewsChannelBean> list = newsChannelService.selectByExampleAndRowBounds(newsChannelBean, pageBean.getPageRowBounds());
			pageBean.setList(list);
			pageBean.setTotalCount(newsChannelService.selectCountByExample(newsChannelBean));
		} catch (Exception e) {
			pageBean.error(e.getMessage());
		}
		
		return pageBean.serialize();
	}
	
	@RequiresPermissions(value = { "news:channel:add", "news:channel:edit" }, logical = Logical.AND)
	@RequestMapping(value = "/edit/{data}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object edit(Model model, HttpServletRequest request, HttpServletResponse response,
			@PathVariable Boolean data, Long id, String json) {
		ResponseBean responseBean = new ResponseBean();
		if (data) {
			try {
				newsChannelService.insertOrUpdateSelective(json);
			} catch (RuntimeException e) {
				responseBean.error(e.getMessage());
			}
			return responseBean;
		}

		return new ModelAndView(RoutesNews.NEWS_CHANNEL_EDIT);
	}
	
	/*
	 * 查询新闻频道数组
	 */
	@RequiresPermissions("news:view")
	@RequestMapping("/all/array")
	@ResponseBody
	public Object newsChannelAllArray(Model model, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		List<NewsChannelBean> list = newsChannelService.selectAll();
		NewsChannelBean newsChannelBean = new NewsChannelBean();
		newsChannelBean.setId(0L);
		newsChannelBean.setChannelName("无");
		list.add(0, newsChannelBean);
		return list;
	}
	
	/*
	 * 关联接口频道
	 */
	@RequiresPermissions("news:channel:edit")
	@RequestMapping("/setApiChannel/{data}")
	@ResponseBody
	public Object setApiChannel(Model model, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@PathVariable Boolean data,
			Long newsChannelId, String apiChannelIds) {
		
		if (!data) {
			if (newsChannelId != null) {
				NewsChannelBean newsChannelBean = newsChannelService.selectByPrimaryKey(newsChannelId);
				model.addAttribute("model", newsChannelBean);
			}
			return new ModelAndView(RoutesNews.NEWS_CHANNEL_SETAPICHANNEL);
		}
		
		ResponseBean responseBean = new ResponseBean();
		try {
			newsChannelService.setApiChannel(newsChannelId, apiChannelIds);
			redisUtils.set(RedisKeys.API_CHANNEL_USING.key, null);
			apiChannelService.getUsingChannelListFromRedis();
		} catch (Exception e) {
			responseBean.error(e.getMessage());
		}
		return responseBean.serialize();
	}
	
}
