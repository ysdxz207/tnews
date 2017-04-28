package com.puyixiaowo.tnews.web.action.member;

import com.puyixiaowo.tnews.bean.Error;
import com.puyixiaowo.tnews.bean.PageBean;
import com.puyixiaowo.tnews.bean.RequestBean;
import com.puyixiaowo.tnews.bean.ResponseBean;
import com.puyixiaowo.tnews.enums.FavoriteType;
import com.puyixiaowo.tnews.member.bean.MemberFavoriteBean;
import com.puyixiaowo.tnews.member.service.MemberFavoriteService;
import com.puyixiaowo.tnews.news.service.NewsService;
import com.puyixiaowo.tnews.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 *
 * @author huangfeihong
 * @date 2017年2月24日
 */
@Controller("memberFavoriteAction")
public class MemberFavoriteAction extends BaseAction {

	@Autowired
	private MemberFavoriteService memberFavoriteService;
	@Autowired
	private NewsService newsService;


	/*
	 * 用户收藏新闻等
	 */
	public String collectOrCancelFavorite(HttpServletRequest request,
										  HttpServletResponse response, RequestBean requestBean) {

		ResponseBean responseBean = new ResponseBean();
		try {
			MemberFavoriteBean memberFavoriteBean = requestBean.toEntity(MemberFavoriteBean.class);
			//判断收藏内容是否存在
			switch (FavoriteType.getType(memberFavoriteBean.getType())) {
				case NEWS:
					if (newsService.getFromRedisById(memberFavoriteBean.getFavoriteId()) == null) {
						Error.throwError(MemberFavoriteBean.NOT_EXISTS, "收藏内容不存在");
					}
			}
			memberFavoriteService.collectOrCancelFavorite(memberFavoriteBean);
		} catch (Exception e) {
			responseBean.error(e);
		}
		return responseBean.serialize();
	}

	/*
	 * 查询用户收藏
	 */
	public String getMemberFavoriteList(HttpServletRequest request,
										HttpServletResponse response, RequestBean requestBean) {

		PageBean pageBean = requestBean.toPageBean();
		try {
			MemberFavoriteBean memberFavoriteBean = requestBean.toEntity(MemberFavoriteBean.class);

			List<MemberFavoriteBean> memberFavoriteBeanList = memberFavoriteService.getMemberFavoriteList(memberFavoriteBean,
					pageBean.getPageRowBounds());

			List<Object> list = new ArrayList<>();

			for (MemberFavoriteBean bean :
					memberFavoriteBeanList) {
				switch (FavoriteType.getType(bean.getType())) {
					case NEWS:
						list.add(newsService.getFromRedisById(bean.getFavoriteId()));
						break;

				}

			}
			pageBean.setList(list);
			pageBean.setTotalCount(memberFavoriteService.selectCountByExample(memberFavoriteBean));
		} catch (Exception e) {
			pageBean.error(e);
		}
		return pageBean.serialize();
	}


}
