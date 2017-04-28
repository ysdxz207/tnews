/**
 * 
 */
package com.puyixiaowo.tnews.web.action.news;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.puyixiaowo.tnews.bean.PageBean;
import com.puyixiaowo.tnews.bean.RequestBean;
import com.puyixiaowo.tnews.news.bean.NewsChannelBean;
import com.puyixiaowo.tnews.news.service.NewsChannelService;
import com.puyixiaowo.tnews.web.action.BaseAction;

/**
 * 新闻频道
 * @author huangfeihong
 * @date 2017年2月2日 上午11:04:28
 */
@Controller("newsChannelAction")
public class NewsChannelAction extends BaseAction {

	@Autowired
	private NewsChannelService newsChannelService;

	/*
	 * 获取新闻频道列表
	 */
	public String getNewsChannelList(HttpServletRequest request,
			HttpServletResponse response, RequestBean requestBean) {
		
		PageBean pageBean = new PageBean();

		try {
			pageBean = requestBean.toPageBean();
			NewsChannelBean newsChannelBean = requestBean.toEntity(NewsChannelBean.class);
			
			pageBean.setList(newsChannelService.selectByRowBounds(
					newsChannelBean,
					pageBean.getPageRowBounds()));
			pageBean.setTotalCount(newsChannelService.selectCount(newsChannelBean));
		} catch (Exception e) {
			pageBean.error("获取新闻频道列表失败:" + e.getMessage());
		}
		return pageBean.serialize();
	}
	
}
