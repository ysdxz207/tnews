package com.puyixiaowo.tnews.news.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.puyixiaowo.tnews.bean.Error;
import com.puyixiaowo.tnews.common.utils.ArrayUtils;
import com.puyixiaowo.tnews.common.utils.ListUtils;
import com.puyixiaowo.tnews.common.utils.RedisUtils;
import com.puyixiaowo.tnews.enums.RedisKeys;
import com.puyixiaowo.tnews.news.bean.ApiChannelBean;
import com.puyixiaowo.tnews.news.bean.NewsApiChannelBean;
import com.puyixiaowo.tnews.news.bean.NewsChannelBean;
import com.puyixiaowo.tnews.news.persistence.ApiChannelCustomMapper;
import com.puyixiaowo.tnews.news.persistence.NewsApiChannelCustomMapper;
import com.puyixiaowo.tnews.news.persistence.NewsChannelCustomMapper;
import com.puyixiaowo.tnews.news.service.NewsChannelService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("newsChannelService")
public class NewsChannelServiceImpl implements NewsChannelService {
	@Autowired
	private NewsChannelCustomMapper newsChannelCustomMapper;
	@Autowired
	private NewsApiChannelCustomMapper newsApiChannelCustomMapper;
	@Autowired
	private ApiChannelCustomMapper apiChannelCustomMapper;
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public long insertSelective(NewsChannelBean bean) {

		return newsChannelCustomMapper.insertSelective(bean);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		int row = newsChannelCustomMapper.deleteByPrimaryKey(id);
		updateRedis();
		return row;
	}

	@Override
	public int updateByPrimaryKeySelective(NewsChannelBean bean) {

		return newsChannelCustomMapper.updateByPrimaryKeySelective(bean);
	}

	@Override
	public NewsChannelBean selectByPrimaryKey(Long id) {
		NewsChannelBean newsChannelBean = newsChannelCustomMapper.selectByPrimaryKey(id);

		List<String> apiChannelNameList = new ArrayList<String>();
		List<Long> apiChannelIdList = new ArrayList<Long>();
		
		NewsApiChannelBean newsApiChannelBean = new NewsApiChannelBean();
		newsApiChannelBean.setNewsChannelId(newsChannelBean.getId());
		List<NewsApiChannelBean> newsApiChannelBeanList = newsApiChannelCustomMapper.select(newsApiChannelBean);

		for (NewsApiChannelBean bean : newsApiChannelBeanList) {

			ApiChannelBean apiChannelBean = new ApiChannelBean();
			apiChannelBean.setId(bean.getApiChannelId());

			apiChannelBean = apiChannelCustomMapper.selectOne(apiChannelBean);
			
			apiChannelIdList.add(apiChannelBean.getId());
			apiChannelNameList.add(apiChannelBean.getChannelName());
		}
		
		newsChannelBean.setApiChannelIds(StringUtils.join(apiChannelIdList, ","));
		newsChannelBean.setApiChannelNames(StringUtils.join(apiChannelNameList, ","));

		return newsChannelBean;
	}

	@Override
	public int selectCountByExample(NewsChannelBean newsChannelBean) {

		return newsChannelCustomMapper.selectCountByExample(buildExample(newsChannelBean));
	}

	@Override
	public List<NewsChannelBean> selectByExampleAndRowBounds(NewsChannelBean newsChannelBean, RowBounds rowBounds) {
		List<NewsChannelBean> list = newsChannelCustomMapper.selectByExampleAndRowBounds(buildExample(newsChannelBean),
				rowBounds);

		for (NewsChannelBean bean : list) {
			// 查询news_api_channel
			NewsApiChannelBean newsApiChannelBean = new NewsApiChannelBean();
			newsApiChannelBean.setNewsChannelId(bean.getId());
			List<NewsApiChannelBean> newsApiChannelBeanList = newsApiChannelCustomMapper.select(newsApiChannelBean);

			bean.setApiChannelIds(ListUtils.join(newsApiChannelBeanList, "apiChannelId"));
		}

		return list;
	}

	@Override
	public List<NewsChannelBean> selectByRowBounds(NewsChannelBean newBean, RowBounds rowBounds) {
		return newsChannelCustomMapper.selectByRowBounds(newBean, rowBounds);
	}

	@Override
	public int selectCount(NewsChannelBean newBean) {
		return newsChannelCustomMapper.selectCount(newBean);
	}

	/**
	 * 
	 * @param newsChannelBean
	 * @return
	 */
	private Object buildExample(NewsChannelBean newsChannelBean) {
		Example example = new Example(NewsChannelBean.class);
		Criteria c = example.createCriteria();
		// 参数处理
		if (StringUtils.isNotBlank(newsChannelBean.getChannelName())) {
			c.andLike("name", "%" + newsChannelBean.getChannelName() + "%");
		}
		if (StringUtils.isNotBlank(newsChannelBean.getCode())) {
			c.andLike("code", "%" + newsChannelBean.getCode() + "%");
		}
		if (newsChannelBean.getStatus() != null) {
			c.andEqualTo("status", newsChannelBean.getStatus());
		}

		example.setOrderByClause(newsChannelBean.getOrders());// 排序
		return example;
	}

	/**
	 * 
	 */
	@Override
	public List<NewsChannelBean> selectAll() {
		return newsChannelCustomMapper.selectAll();
	}

	/**
	 * 
	 */
	@Override
	public void setApiChannel(Long newsChannelId, String apiChannelIds) {
		if (StringUtils.isBlank(apiChannelIds)) {
			return;
		}
		Long[] ids = ArrayUtils.parseToLongArray(apiChannelIds);
		// 删除
		Example example = new Example(NewsApiChannelBean.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("newsChannelId", newsChannelId);
		newsApiChannelCustomMapper.deleteByExample(example);
		//
		for (Long apiChannelId : ids) {
			NewsApiChannelBean bean = new NewsApiChannelBean();
			bean.setNewsChannelId(newsChannelId);
			bean.setApiChannelId(apiChannelId);
			newsApiChannelCustomMapper.insertSelective(bean);
		}
		
	}

	/**
	 * 
	 */
	@Override
	public void insertOrUpdateSelective(String json) {
		if (StringUtils.isBlank(json)) {
			return;
		}
		JSONArray arr = JSON.parseArray(json);
		for (int i = 0; i < arr.size(); i++) {
			NewsChannelBean newsChannelBean = arr.getJSONObject(i).toJavaObject(NewsChannelBean.class);
			if (newsChannelBean.getId() == null) {
				//判断是否已存在用户
				NewsChannelBean newsChannelBeanParams = new NewsChannelBean();
				newsChannelBeanParams.setChannelName(newsChannelBean.getChannelName());
				if (newsChannelCustomMapper.selectCount(newsChannelBeanParams) > 0) {
					Error.throwError(NewsChannelBean.EXISTS_NEWS_CHANNEL, "新闻频道已存在");
				}
				newsChannelBean.setCreateTime(new Date());
				newsChannelCustomMapper.insertSelective(newsChannelBean);
			} else {
				newsChannelCustomMapper.updateByPrimaryKeySelective(newsChannelBean);
			}
		}
		
		updateRedis();
	}
	
	/**
	 * 从缓存中获取所有新闻频道
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NewsChannelBean> getAllNewsChannelFromRedis(){
		List<NewsChannelBean> list = null;
		try {
			list = (List<NewsChannelBean>)redisUtils.get(RedisKeys.NEWS_CHANNEL.key);
		} catch (Exception e) {
			Error.throwError("获取redis中接口频道失败:" + e.getMessage());
		}
		if (ListUtils.isEmpty(list)){
			NewsChannelBean newsChannelBean = new NewsChannelBean();
			newsChannelBean.setStatus(1);
			List<NewsChannelBean> newsChannelList = newsChannelCustomMapper.select(newsChannelBean);
			redisUtils.set(RedisKeys.NEWS_CHANNEL.key, newsChannelList);
			return newsChannelList;
		} else {
			return list;
		}
	}
	/**
	 * 更新redis
	 */
	private void updateRedis(){
		redisUtils.set(RedisKeys.NEWS_CHANNEL.key, null);
		getAllNewsChannelFromRedis();
	}
}
