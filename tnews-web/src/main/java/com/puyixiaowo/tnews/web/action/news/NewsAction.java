/**
 * 
 */
package com.puyixiaowo.tnews.web.action.news;

import com.puyixiaowo.tnews.bean.PageBean;
import com.puyixiaowo.tnews.bean.RequestBean;
import com.puyixiaowo.tnews.bean.ResponseBean;
import com.puyixiaowo.tnews.news.bean.NewsBean;
import com.puyixiaowo.tnews.news.service.NewsService;
import com.puyixiaowo.tnews.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huangfeihong
 * @date 2016年12月8日 下午8:33:04
 */
@Controller("newsAction")
public class NewsAction extends BaseAction {

	@Autowired
	private NewsService newsService;

	/*
	 * 获取新闻列表
	 */
	public String getNewsList(HttpServletRequest request,
			HttpServletResponse response, RequestBean requestBean) {
		
		PageBean pageBean = new PageBean();

		try {
			pageBean = requestBean.toPageBean();
			NewsBean newsBean = requestBean.toEntity(NewsBean.class, true);
			newsBean.setStatus(1);//有效新闻
			pageBean.setList(newsService.selectByRowBounds(
					newsBean,
					pageBean.getPageRowBounds()));
			pageBean.setTotalCount(newsService.selectCount(newsBean));
		} catch (Exception e) {
			pageBean.error(e);
		}
		return pageBean.serialize();
	}
	
	/*
	 * 获取新闻详情
	 */
	public String getNewsDetail(HttpServletRequest request,
			HttpServletResponse response, RequestBean requestBean) {
		
		ResponseBean responseBean = new ResponseBean();

		try {
			responseBean.setData(newsService.getFromRedisById(requestBean.getLong("newsId")));
		} catch (Exception e) {
			responseBean.error(e);
		}
		return responseBean.serialize();
	}
}
