package com.puyixiaowo.tnews.news.service;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import com.puyixiaowo.tnews.news.bean.ApiChannelBean;
import com.puyixiaowo.tnews.news.bean.NewsBean;

public interface NewsApiService {
	
	List<NewsBean> getNewsListByApi(HashMap<String, Object> params);

	List<ApiChannelBean> getApiChannelByApi() throws URISyntaxException;

	void syncNews();

}
