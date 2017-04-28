package com.puyixiaowo.tnews.news.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.puyixiaowo.tnews.bean.Error;
import com.puyixiaowo.tnews.common.utils.ListUtils;
import com.puyixiaowo.tnews.common.utils.RedisUtils;
import com.puyixiaowo.tnews.enums.RedisKeys;
import com.puyixiaowo.tnews.news.bean.NewsBean;
import com.puyixiaowo.tnews.news.core.thread.NewsByFilterThread;
import com.puyixiaowo.tnews.news.service.NewsApiService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

@Service("newsFilterService")
public class NewsFilterService {

	@Autowired
	public RedisUtils redisUtils;
	@Autowired
	public NewsApiService newsApiService;

	/**
	 * 根据新闻频道ID和新闻频道关键词验证新闻
	 * @param newsBean
	 * @return
	 */
	private boolean validateNews(NewsBean newsBean) throws RuntimeException{
		Long newsChannelId = newsBean.getNewsChannelId();
		// 未关联新闻频道的新闻
		if (newsChannelId == null) {
			return false;
		}
		
		boolean flag = false;

		String html = newsBean.getHtml();
		String title = newsBean.getTitle();

		if (StringUtils.isNotBlank(html)) {
			String[] dictionarysInclude = getDictionarys(
					RedisKeys.DICTIONARY_NEWS_KEYWORDS_INCLUDE.key,
					newsChannelId);
			String[] dictionarysExclude = getDictionarys(
					RedisKeys.DICTIONARY_NEWS_KEYWORDS_EXCLUDE.key,
					newsChannelId);

			if ((dictionarysInclude.length == 0 || dictionarysExclude.length == 0) || 
					(com.puyixiaowo.tnews.common.utils.StringUtils
					.stringContainsNumFromList(html, dictionarysInclude) >= 3
					&& com.puyixiaowo.tnews.common.utils.StringUtils
							.stringContainsNumFromList(title,
									dictionarysInclude) >= 1
					&& com.puyixiaowo.tnews.common.utils.StringUtils
							.stringContainsNumFromList(html, dictionarysExclude) == 0)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 根据新闻频道ID和redis缓存字典，获取新闻频道关键词
	 * 
	 * @return
	 */
	private String[] getDictionarys(String key, Long newsChannelId) {

		JSONArray dictionaryJsonArr = (JSONArray) redisUtils.get(key);

		if (ListUtils.isEmpty(dictionaryJsonArr)) {
			Error.throwError("redis缓存中无字典信息");
		}

		JSONObject dictionaryJsonObj = null;

		for (Object object : dictionaryJsonArr) {
			JSONObject obj = (JSONObject) object;
			Long id = obj.getLong("newsChannelId");

			if (id.longValue() == newsChannelId.longValue()) {
				dictionaryJsonObj = obj;
				break;
			}
		}

		// 字典中无当前新闻频道关联,不校验
		if (dictionaryJsonObj == null) {
			return new String[0];
		}

		String dictionaryStr = dictionaryJsonObj.getString("valueDic");
		return dictionaryStr.split(",");
	}
	
	/**
	 * 根据接口频道ID直接从接口获取校验新闻
	 * @param channelId
	 * @return
	 */
	public List<NewsBean> requestValidateNews(String channelId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("channelId", channelId);
		List<NewsBean> list = newsApiService.getNewsListByApi(params);
		Iterator<NewsBean> it = list.iterator();
		while (it.hasNext()) {
			NewsBean newsBean = it.next();
			if (!validateNews(newsBean)) {
				it.remove();
			}
		}
		return list;
	}

	/**
	 * 从接口获取通过字典校验的新闻,应比定时同步优先级高 规则：新闻标题包含两个关键词
	 * 
	 * @param newsChannelId
	 * @return
	 */
	public List<NewsBean> requestFilterValidateNews(Long newsChannelId) {
		List<NewsBean> result = new ArrayList<NewsBean>();

		String[] dictionarys = getDictionarys(
				RedisKeys.DICTIONARY_NEWS_KEYWORDS_INCLUDE.key, newsChannelId);


		// 创建一个通用池
		ForkJoinPool pool = ForkJoinPool.commonPool();

		// 提交可分解的CalTask任务
		Future<List<NewsBean>> future = pool.submit(new NewsByFilterThread(dictionarys, newsChannelId, 0, true));
		try {
			result = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		//不使用forkjoinpool
//		for (String dic :
//				dictionarys) {
//			List<NewsBean> list = requestKeyWordsFilterNews(dic, newsChannelId);
//			result.addAll(list);
//		}


		//排序
		Collections.sort(result, (arg0, arg1) -> arg0.getPubDate().compareTo(arg1.getPubDate()));
//		for (NewsBean newsBean :
//				result) {
//			System.out.println("====" + DateUtils.formatDateTime(newsBean.getPubDate()));
//		}
		//System.out.println("总数量：" + result.size());
		return result;
	}


	/**
	 * 根据关键词请求筛选新闻
	 * @param dic
	 * @param newsChannelId
	 * @return
	 */
	public List<NewsBean> requestKeyWordsFilterNews(String dic, Long newsChannelId){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("title", dic);
		params.put("maxResult", 20);
		// long start = System.currentTimeMillis();


		List<NewsBean> list = newsApiService.getNewsListByApi(params);
		Iterator<NewsBean> it = list.iterator();
		while (it.hasNext()) {
			NewsBean bean = it.next();
			bean.setNewsChannelId(newsChannelId);// 设为当前频道
			if (validateNews(bean)) {
				// 验证通过
				bean.setCreatorId(-1L);// 接口关键词筛选
			} else {
				it.remove();
			}
		}
		return list;
	}

}
