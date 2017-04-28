package com.puyixiaowo.tnews.dictionary.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.puyixiaowo.tnews.dictionary.bean.DictionaryBean;

/**
 * 字典服务
 * @author huangfeihong
 * @date 2017年2月2日 下午2:31:38
 */
public interface DictionaryService {
	
	long insertSelective(DictionaryBean dictionary);
	
	int deleteByPrimaryKey(Long id);
	
	int updateByPrimaryKeySelective(DictionaryBean dictionary);
	
	DictionaryBean selectByPrimaryKey(Long id);
	
	List<DictionaryBean> selectByExampleAndRowBounds(DictionaryBean dictionaryBean, RowBounds rowBouds);

	int selectCountByExample(DictionaryBean dictionaryBean);
	
	List<DictionaryBean> selectByRowBounds(DictionaryBean newBean, RowBounds rowBounds);
	
	int selectCount(DictionaryBean newBean);
	
	List<DictionaryBean> selectAll();

	void insertOrUpdateSelective(String json);

	void delete(String id);

	void updateRedisDictionary();

}
