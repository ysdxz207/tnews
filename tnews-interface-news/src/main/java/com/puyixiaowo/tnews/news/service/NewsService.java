package com.puyixiaowo.tnews.news.service;

import com.puyixiaowo.tnews.news.bean.NewsBean;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 新闻实体服务接口
 * @author huangfeihong
 * @date 2016-11-06 15:48:26
 */
public interface NewsService {
	
	long insertSelective(NewsBean tnews);
	
	int deleteByPrimaryKey(Long id);
	
	int updateByPrimaryKeySelective(NewsBean tnews);
	
	NewsBean selectByPrimaryKey(Long id);
	
	List<NewsBean> selectByExampleAndRowBounds(NewsBean newsBean, RowBounds rowBouds);

	int selectCountByExample(NewsBean newsBean);
	
	List<NewsBean> selectByRowBounds(NewsBean newBean, RowBounds rowBounds);
	
	int selectCount(NewsBean newBean);
	
	int delete(String id);

	NewsBean getFromRedisById(Long id);

}
