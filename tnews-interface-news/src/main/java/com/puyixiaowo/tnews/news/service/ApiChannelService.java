package com.puyixiaowo.tnews.news.service;

import com.puyixiaowo.tnews.bean.PageRowBounds;
import com.puyixiaowo.tnews.news.bean.ApiChannelBean;

import java.util.List;

public interface ApiChannelService {

	
	List<ApiChannelBean> getUsingChannelListFromRedis();
	
	void insertApiChannel(ApiChannelBean tApiChannelBean);

	List<ApiChannelBean> selectAll();

	List<ApiChannelBean> selectByExampleAndRowBounds(ApiChannelBean apiChannelBean, PageRowBounds pageRowBounds);

	List<ApiChannelBean> selectByExample(ApiChannelBean apiChannelBean);

	int selectCountByExample(ApiChannelBean apiChannelBean);
}
