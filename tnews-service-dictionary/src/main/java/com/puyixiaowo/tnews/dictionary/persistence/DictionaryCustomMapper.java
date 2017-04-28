package com.puyixiaowo.tnews.dictionary.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.puyixiaowo.tnews.dictionary.bean.DictionaryBean;

public interface DictionaryCustomMapper extends Mapper<DictionaryBean>{
	
	List<DictionaryBean> selectNewsChannelDictionarys(@Param(value = "typeDic") String typeDic);
	
}