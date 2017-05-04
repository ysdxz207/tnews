package com.puyixiaowo.tnews.news.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.puyixiaowo.tnews.bean.RequestControlBean;
import com.puyixiaowo.tnews.common.utils.*;
import com.puyixiaowo.tnews.core.MessageResolver;
import com.puyixiaowo.tnews.enums.RedisKeys;
import com.puyixiaowo.tnews.news.bean.ApiChannelBean;
import com.puyixiaowo.tnews.news.bean.NewsApiChannelBean;
import com.puyixiaowo.tnews.news.bean.NewsBean;
import com.puyixiaowo.tnews.news.bean.NewsChannelBean;
import com.puyixiaowo.tnews.news.persistence.ApiChannelCustomMapper;
import com.puyixiaowo.tnews.news.persistence.NewsApiChannelCustomMapper;
import com.puyixiaowo.tnews.news.persistence.NewsCustomMapper;
import com.puyixiaowo.tnews.news.service.NewsApiService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.*;

@Service("newsApiService")
public class NewsApiServiceImpl implements NewsApiService {

	@Autowired
	private MessageResolver messageResolver;
	@Autowired
	private ApiChannelCustomMapper apiChannelCustomMapper;
	@Autowired
	private NewsApiChannelCustomMapper newsApiChannelCustomMapper;
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private NewsFilterService newsFilterService;
	@Autowired
	private NewsCustomMapper newsCustomMapper;

	private final static Logger logger = LoggerFactory
			.getLogger(NewsApiServiceImpl.class);

	public List<NewsBean> getNewsListByApi(HashMap<String, Object> params) {
		List<NewsBean> list = new ArrayList<NewsBean>();

		params.put("needContent", 1);
		params.put("needHtml", 1);

		JSONObject jsonObject = null;
		try {
			jsonObject = requestApi(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return list;
		}

		JSONObject li = jsonObject.getJSONObject("showapi_res_body")
				.getJSONObject("pagebean");
		// Integer allPages = li.getIntValue("allPages");
		// Integer maxResult = li.getIntValue("maxResult");
		// Integer currentPage = li.getIntValue("currentPage");
		JSONArray contentList = li.getJSONArray("contentlist");

		for (int i = 0; i < contentList.size(); i++) {
			JSONObject contentObj = contentList.getJSONObject(i);

			// 处理图片内容
			// String newsContent = getNewsContent(contentObj);
			JSONArray imageurls = contentObj.getJSONArray("imageurls");
			// 封面图片
			JSONObject faceUrlObj = null;
			if (imageurls != null && imageurls.size() > 0) {
				faceUrlObj = imageurls.getJSONObject(0);
			}
			contentObj.remove("imageurls");

			// 处理html
			String html = getNewsHtml(contentObj);
			contentObj.remove("allList");
			contentObj.remove("html");

			if (!DateUtils.isValidDateTime(contentObj.getString("pubDate"))) {
				contentObj.put("pubDate", DateUtils.formatDateTime(new Date()));
			}
			try {
				contentObj.remove("id");//移除ID属性
				NewsBean newsBean = JSON.parseObject(contentObj.toJSONString(),
						NewsBean.class);
				if (faceUrlObj != null
						&& StringUtils.isNotEmpty(faceUrlObj.getString("url"))) {
					newsBean.setFaceUrl(faceUrlObj.getString("url"));
				} else {
					newsBean.setFaceUrl("");
				}

				// newsBean.setContentWithImgs(newsContent);

				String title = contentObj.getString("title");

				String desc = contentObj.getString("desc");
				if (StringUtils.isEmpty(title)) {
					newsBean.setTitle(desc);
				}
				newsBean.setHtml(html);
				newsBean.setDescription(desc);
				// 新闻频道
				ApiChannelBean apiChannelBean = new ApiChannelBean();
				apiChannelBean.setChannelId(newsBean.getChannelId());

				apiChannelBean = apiChannelCustomMapper
						.selectOne(apiChannelBean);

				NewsApiChannelBean newsApiChannelBean = new NewsApiChannelBean();
				newsApiChannelBean.setApiChannelId(apiChannelBean.getId());
				newsApiChannelBean = newsApiChannelCustomMapper
						.selectOne(newsApiChannelBean);
				if (null != newsApiChannelBean) {
					newsBean.setNewsChannelId(newsApiChannelBean
							.getNewsChannelId());
				}

				list.add(newsBean);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("", e);
			}
		}
		return list;
	}

	private String getNewsHtml(JSONObject contentObj) {
		String content = contentObj.getString("html");
		return content.replaceAll("'", "\"");
	}

	/**
	 * 处理新闻内容图片
	 */
	@SuppressWarnings("unused")
	private String getNewsContent(JSONObject contentObj) {
		JSONArray allList = contentObj.getJSONArray("allList");
		String content = contentObj.getString("html");
		if (allList == null) {
			return content;
		}
		StringBuffer sb = new StringBuffer();
		try {
			for (int j = 0; j < allList.size(); j++) {
				Object obj = allList.get(j);
				try {
					if (JsonUtils.isGoodJsonObject(obj.toString())) {
						sb.append("<p><img src=\""
								+ ((JSONObject) obj).getString("url")
								+ "\" /></p>");
					} else {
						sb.append("<p>" + obj.toString() + "</p>");
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("obj=" + obj.toString(), e);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		contentObj.remove("allList");
		return sb.toString();
	}

	public List<ApiChannelBean> getApiChannelByApi() {
		List<ApiChannelBean> list = new ArrayList<ApiChannelBean>();
		JSONObject jsonObject = null;
		try {
			jsonObject = requestApi(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// String err = (String) jsonObject.get("showapi_res_error");
		JSONArray channelList = jsonObject.getJSONObject("showapi_res_body")
				.getJSONArray("channelList");

		for (int i = 0; i < channelList.size(); i++) {
			JSONObject channel = channelList.getJSONObject(i);
			ApiChannelBean tApiChannelBean = JSON.parseObject(
					channel.toJSONString(), ApiChannelBean.class);
			tApiChannelBean.setChannelName(tApiChannelBean.getChannelName());
			list.add(tApiChannelBean);
		}
		return list;
	}

	@Override
	public void syncNews() {
		Object switchApiFilterObj = redisUtils
				.get(RedisKeys.SWITCH_API_FILTER.key);
		Object switchApiObj = redisUtils.get(RedisKeys.SWITCH_API.key);

		boolean switchApiFilter = Boolean
				.valueOf(switchApiFilterObj == null ? "true"
						: switchApiFilterObj.toString());
		boolean switchApi = Boolean.valueOf(switchApiObj == null ? "true"
				: switchApiObj.toString());

		if (switchApiFilter) {
			syncNewsFromApiByFilter();
		}
		if (switchApi) {
			syncNewsFromApi();
		}
	}

	@SuppressWarnings("unchecked")
	private void syncNewsFromApi() {
		System.out.println("----------start syncNewsFromApi-----------");
		List<ApiChannelBean> apiChannelUsingList = (List<ApiChannelBean>) redisUtils
				.get(RedisKeys.API_CHANNEL_USING.key);
		for (ApiChannelBean apiChannelBean : apiChannelUsingList) {

			List<NewsBean> list = new ArrayList<NewsBean>();
			long start = System.currentTimeMillis();
			try {
				list = newsFilterService.requestValidateNews(apiChannelBean.getChannelId());
			} catch (Exception e1) {
				logger.error("直接从接口获取新闻异常：" + e1.getMessage());
			}
			// newsFilterService.filterValidateNews(newsBean);
			int n = syncNews(list);
			long end = System.currentTimeMillis();
			System.out.println(DateUtils.formatDateTime(new Date()) + "["
					+ apiChannelBean.getChannelName() + "]频道同步了" + n
					+ "条新闻,消耗：" + (end - start) + "毫秒");
		}
		System.out.println("----------end syncNewsFromApi-----------");
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void syncNewsFromApiByFilter() {
		System.out
				.println("----------start syncNewsFromApiByFilter-----------");
		List<NewsChannelBean> newsChannelList = (List<NewsChannelBean>) redisUtils
				.get(RedisKeys.NEWS_CHANNEL.key);
		for (NewsChannelBean newsChannelBean : newsChannelList) {
//			NewsByFilterThread newsByFilterThread = new NewsByFilterThread(newsChannelBean);
//			Thread thread = new Thread(newsByFilterThread, newsChannelBean.getChannelName());
//			thread.start();
			List<NewsBean> list = new ArrayList<NewsBean>();
			
			long start = System.currentTimeMillis();
			try {
				list = newsFilterService.requestFilterValidateNews(newsChannelBean
						.getId());
			} catch (Exception e) {

				logger.error("根据字典关键词从接口获取新闻异常：" + e.getMessage());
			}
			
			long end = System.currentTimeMillis();
			int n = syncNews(list);
			System.out.println(DateUtils.formatDateTime(new Date()) + "["
					+ newsChannelBean.getChannelName() + "]频道同步了" + n
					+ "条新闻,消耗：" + (end - start) + "毫秒");
		}

		System.out.println("----------end syncNewsFromApiByFilter-----------");
	}

	private int syncNews(List<NewsBean> list) {
		// 去重数据
		// List<NewsBean> listWithoutDup = new ArrayList<>(new HashSet<>(list));

		int n = 0;
		try {
			for (NewsBean newsBean : list) {
				// NewsBean newsBeanExample = new NewsBean();
				// newsBeanExample.setTitle(newsBean.getTitle());
				// int count = newsCustomMapper.selectCount(newsBeanExample);
				// if (count > 0) {
				// continue;
				// }

				// 匹配相似度,大于0.3则不插入
				if (hasSimilarNews(newsBean.getTitle())) {
					continue;
				}
				newsBean.setCreateTime(new Date());

				newsCustomMapper.insertSelective(newsBean);
				n++;
			}
			// logger.info("---------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

	/**
	 * 是否有7天内相似的新闻
	 * 
	 * @param title
	 * @return
	 */
	private boolean hasSimilarNews(String title) {
		boolean flag = false;

		//首先查询是否有重复
		NewsBean newsBeanExample = new NewsBean();
		newsBeanExample.setTitle(title);
		int count = newsCustomMapper.selectCount(newsBeanExample);
		if (count > 0) {
			flag = true;
		} else {
			Example example = new Example(NewsBean.class);
			Criteria c = example.createCriteria();
			c.andGreaterThanOrEqualTo("pubDate", DateUtils.getDate(-7, true));// 7天内的数据
			List<NewsBean> newsList = newsCustomMapper.selectByExample(example);

			for (NewsBean bean : newsList) {
				float similarity = com.puyixiaowo.tnews.common.utils.StringUtils
						.getSimilarityRatio(title, bean.getTitle());
				if (similarity > 0.3) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/*
	 * 访问api
	 */
	private JSONObject requestApi(Map<String, Object> params) throws Exception {

		String apikey = messageResolver.getMessage("yiyuan.apikey");
		String appid = messageResolver.getMessage("yiyuan.appid");
		String apiurl = messageResolver.getMessage("yiyuan.apiurl");
		
		//访问请求控制
		requestControl(apiurl);

		String responseValue = ApiRequest.request(apiurl, appid, apikey, params);

		if (JsonUtils.isBadJsonObject(responseValue)) {
			logger.error("api请求返回json格式不正确：" + responseValue);
			throw new RuntimeException("api请求返回json格式不正确");
		}
		JSONObject json = JSON.parseObject(responseValue);
		if (json.getIntValue("showapi_res_code") != 0) {
			throw new RuntimeException("接口返回错误[" + MapUtils.toUrlParams(params) + "]：" + json.getString("showapi_res_error"));
		}
		return json;
	}

	/*
	 * 访问请求控制
	 */
	private void requestControl(String url) {

		long currentTime = System.currentTimeMillis();

		/*
		 * 访问次数控制
		 */
		long time = Long.parseLong(redisUtils.get(RedisKeys.API_REQUEST_SPECIFIED_TIME.key).toString());
		int time_times = Integer.parseInt(redisUtils.get(RedisKeys.API_REQUEST_SPECIFIED_TIME_TIMES.key).toString());
		RequestControlBean requestControlBean = (RequestControlBean)redisUtils.get(RedisKeys.API_REQUEST_URL_SPECIFIED_TIMES.key + url);

		if (requestControlBean != null &&
				currentTime - requestControlBean.getTime() <= time &&
				requestControlBean.getTimes() > time_times) {
			//在指定时间内且超出控制次数
			//System.err.println("接口访问已达最大限制，等待1秒");
			long start = System.currentTimeMillis();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			//System.out.println("等待时间："+(end-start));
			//清空链接控制
			redisUtils.delete(RedisKeys.API_REQUEST_URL_SPECIFIED_TIMES.key + url);
		} else if (requestControlBean != null &&
				currentTime - requestControlBean.getTime() <= time){
			//在指定时间内且未超出控制次数，则请求时间不变，增加一次次数
			requestControlBean.setTimes(requestControlBean.getTimes() + 1);
			redisUtils.set(RedisKeys.API_REQUEST_URL_SPECIFIED_TIMES.key + url, requestControlBean);
		} else {
			//其他情况设置次数为1
			requestControlBean = requestControlBean == null ? new RequestControlBean() : requestControlBean;
			requestControlBean.setTime(currentTime);
			requestControlBean.setTimes(1);
			redisUtils.set(RedisKeys.API_REQUEST_URL_SPECIFIED_TIMES.key + url, requestControlBean);
		}
		//System.out.println("请求api时间：" + DateUtils.formatDateTime(new Date()) + "[" + requestControlBean.getTimes() + "]");
	}
}
