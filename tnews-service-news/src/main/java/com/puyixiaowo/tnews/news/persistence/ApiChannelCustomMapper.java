package com.puyixiaowo.tnews.news.persistence;

import java.util.List;

import com.puyixiaowo.tnews.news.bean.ApiChannelBean;

import tk.mybatis.mapper.common.Mapper;

public interface ApiChannelCustomMapper extends Mapper<ApiChannelBean> {

	List<ApiChannelBean> selectListByNewsChannelExists();
}