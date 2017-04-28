package com.puyixiaowo.tnews.news.service.impl;

import com.puyixiaowo.tnews.bean.Error;
import com.puyixiaowo.tnews.bean.PageRowBounds;
import com.puyixiaowo.tnews.common.utils.ListUtils;
import com.puyixiaowo.tnews.common.utils.RedisUtils;
import com.puyixiaowo.tnews.enums.RedisKeys;
import com.puyixiaowo.tnews.news.bean.ApiChannelBean;
import com.puyixiaowo.tnews.news.persistence.ApiChannelCustomMapper;
import com.puyixiaowo.tnews.news.service.ApiChannelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

@Service("apiChannelService")
public class ApiChannelServiceImpl implements ApiChannelService{

	@Autowired
	ApiChannelCustomMapper apiChannelCustomMapper;
	@Autowired
	RedisUtils redisUtils;
	
	/**
	 * 查询已被新闻频道关联的接口列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApiChannelBean> getUsingChannelListFromRedis(){
		List<ApiChannelBean> list = null;
		try {
			list = (List<ApiChannelBean>)redisUtils.get(RedisKeys.API_CHANNEL_USING.key);
		} catch (Exception e) {
			Error.throwError("获取redis中接口频道失败:" + e.getMessage());
		}
		if (ListUtils.isEmpty(list)){
			List<ApiChannelBean> apiChannelUsingList = apiChannelCustomMapper.selectListByNewsChannelExists();
			redisUtils.set(RedisKeys.API_CHANNEL_USING.key, apiChannelUsingList);
			return apiChannelUsingList;
		} else {
			return list;
		}
	}
	
	@Override
	public void insertApiChannel(ApiChannelBean apiChannelBean) {
		apiChannelCustomMapper.insertSelective(apiChannelBean);
	}

	@Override
	public List<ApiChannelBean> selectAll() {
		return apiChannelCustomMapper.selectAll();
	}

	@Override
	public List<ApiChannelBean> selectByExampleAndRowBounds(ApiChannelBean apiChannelBean,
			PageRowBounds pageRowBounds) {
		return apiChannelCustomMapper.selectByExampleAndRowBounds(buildExample(apiChannelBean), pageRowBounds);
	}

	@Override
	public int selectCountByExample(ApiChannelBean apiChannelBean) {
		return apiChannelCustomMapper.selectCountByExample(buildExample(apiChannelBean));
	}
	
	@Override
	public List<ApiChannelBean> selectByExample(ApiChannelBean apiChannelBean) {
		return apiChannelCustomMapper.selectByExample(buildExample(apiChannelBean));
	}
	
	private Object buildExample(ApiChannelBean apiChannelBean) {
		Example example = new Example(ApiChannelBean.class);
		Criteria c = example.createCriteria();
		// 参数处理
		if (StringUtils.isNotBlank(apiChannelBean.getChannelName())) {
			c.andLike("channelName", "%" + apiChannelBean.getChannelName() + "%");
		}
		if (apiChannelBean.getChannelId() != null) {
			c.andEqualTo("channelId", apiChannelBean.getChannelId());
		}
		if (apiChannelBean.getStatus() != null) {
			c.andEqualTo("status", apiChannelBean.getStatus());
		}
		
		example.setOrderByClause(apiChannelBean.getOrders());// 排序
		return example;
	}
}

