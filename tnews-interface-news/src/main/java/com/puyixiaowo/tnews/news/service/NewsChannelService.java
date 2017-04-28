package com.puyixiaowo.tnews.news.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.puyixiaowo.tnews.news.bean.NewsChannelBean;

/**
 * 新闻频道服务
 * @author huangfeihong
 * @date 2017年1月22日 下午11:33:20
 */
public interface NewsChannelService {
	
	long insertSelective(NewsChannelBean tnewsChannel);
	
	int deleteByPrimaryKey(Long id);
	
	int updateByPrimaryKeySelective(NewsChannelBean tnewsChannel);
	
	NewsChannelBean selectByPrimaryKey(Long id);
	
	List<NewsChannelBean> selectByExampleAndRowBounds(NewsChannelBean newsChannelBean, RowBounds rowBouds);

	int selectCountByExample(NewsChannelBean newsChannelBean);
	
	List<NewsChannelBean> selectByRowBounds(NewsChannelBean newBean, RowBounds rowBounds);
	
	int selectCount(NewsChannelBean newBean);
	
	List<NewsChannelBean> selectAll();

	void setApiChannel(Long newsChannelId, String apiChannelIds);

	void insertOrUpdateSelective(String json);

	List<NewsChannelBean> getAllNewsChannelFromRedis();

}
