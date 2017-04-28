package com.puyixiaowo.tnews.news.service.impl;

import com.puyixiaowo.tnews.common.utils.RedisUtils;
import com.puyixiaowo.tnews.enums.RedisKeys;
import com.puyixiaowo.tnews.news.bean.NewsBean;
import com.puyixiaowo.tnews.news.persistence.NewsCustomMapper;
import com.puyixiaowo.tnews.news.service.NewsService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Arrays;
import java.util.List;

@Service("newsService")
public class NewsServiceImpl implements NewsService{
	@Autowired
	private NewsCustomMapper newsCustomMapper;
	@Autowired
	private RedisUtils redisUtils;
	
	
	@Override
	public long insertSelective(NewsBean bean) {
		
		return newsCustomMapper.insertSelective(bean);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		
		return newsCustomMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(NewsBean bean) {
		redisUtils.set(RedisKeys.NEWS_DETAIL.key + bean.getId(), bean);//更新redis中数据
		return newsCustomMapper.updateByPrimaryKeySelective(bean);
	}
	
	@Override
	public NewsBean selectByPrimaryKey(Long id) {
		NewsBean bean = newsCustomMapper.selectByPrimaryKey(id);
		if (bean != null) {
			redisUtils.set(RedisKeys.NEWS_DETAIL.key + id, bean);
		}
		return bean;
	}
	
	@Override
	public int selectCountByExample(NewsBean newsBean){
		
		return newsCustomMapper.selectCountByExample(buildExample(newsBean));
	}
	
	@Override
	public List<NewsBean> selectByExampleAndRowBounds(NewsBean newsBean, RowBounds rowBounds) {
		
		return newsCustomMapper.selectByExampleAndRowBounds(buildExample(newsBean), rowBounds);
	}
	
	@Override
	public List<NewsBean> selectByRowBounds(NewsBean newBean, RowBounds rowBounds) {
		return newsCustomMapper.selectByRowBounds(newBean, rowBounds);
	}
	
	@Override
	public int selectCount(NewsBean newBean){
		return newsCustomMapper.selectCount(newBean);
	}
	
	/**
	 * 
	 * @param newsBean
	 * @return
	 */
	private Object buildExample(NewsBean newsBean) {
		Example example = new Example(NewsBean.class);
		Criteria c = example.createCriteria();
		// 参数处理
		if (StringUtils.isNotBlank(newsBean.getTitle())) {
			c.andLike("title", "%" + newsBean.getTitle() + "%");
		}
		if (newsBean.getChannelId() != null) {
			c.andEqualTo("channelId", newsBean.getChannelId());
		}
		if (newsBean.getNewsChannelId() != null) {
			c.andEqualTo("newsChannelId", newsBean.getNewsChannelId());
		}
		if (StringUtils.isNotBlank(newsBean.getSource())) {
			c.andLike("source", "%" + newsBean.getSource() + "%");
		}
		if (newsBean.getCreatorId() != null) {
			c.andEqualTo("creatorId", newsBean.getCreatorId());
		}
		if (StringUtils.isNotBlank(newsBean.getChannelName())) {
			c.andLike("channelName", "%" + newsBean.getChannelName() + "%");
		}
		if (newsBean.getStatus() != null) {
			c.andEqualTo("status", newsBean.getStatus());
		}
		
		example.setOrderByClause(newsBean.getOrders());// 排序
		return example;
	}

	@Override
	public int delete(String id) {
		if (StringUtils.isBlank(id)) {
			return 0;
		}
		Example example = new Example(NewsBean.class);
		Criteria c = example.createCriteria();
		c.andIn("id", Arrays.asList(id.split(",")));

		for (String newsId :
				id.split(",")) {

			redisUtils.delete(RedisKeys.NEWS_DETAIL.key + newsId);//删除redis中数据
		}
		return newsCustomMapper.deleteByExample(example);
	}

	@Override
	public NewsBean getFromRedisById(Long id) {
		NewsBean bean = (NewsBean) redisUtils.get(RedisKeys.NEWS_DETAIL.key + id);
		if (bean == null) {
			bean = newsCustomMapper.selectByPrimaryKey(id);
			redisUtils.set(RedisKeys.NEWS_DETAIL.key + id, bean);
		}
		return bean;
	}


}
