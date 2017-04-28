package com.puyixiaowo.tnews.dictionary.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.puyixiaowo.tnews.bean.Error;
import com.puyixiaowo.tnews.common.utils.DateUtils;
import com.puyixiaowo.tnews.common.utils.RedisUtils;
import com.puyixiaowo.tnews.dictionary.bean.DictionaryBean;
import com.puyixiaowo.tnews.dictionary.persistence.DictionaryCustomMapper;
import com.puyixiaowo.tnews.enums.RedisKeys;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {
	@Autowired
	private DictionaryCustomMapper dictionaryCustomMapper;
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public long insertSelective(DictionaryBean bean) {

		return dictionaryCustomMapper.insertSelective(bean);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {

		return dictionaryCustomMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DictionaryBean bean) {

		return dictionaryCustomMapper.updateByPrimaryKeySelective(bean);
	}

	@Override
	public DictionaryBean selectByPrimaryKey(Long id) {

		return dictionaryCustomMapper.selectByPrimaryKey(id);
	}

	@Override
	public int selectCountByExample(DictionaryBean dictionaryBean) {

		return dictionaryCustomMapper
				.selectCountByExample(buildExample(dictionaryBean));
	}

	@Override
	public List<DictionaryBean> selectByExampleAndRowBounds(
			DictionaryBean dictionaryBean, RowBounds rowBounds) {

		return dictionaryCustomMapper.selectByExampleAndRowBounds(
				buildExample(dictionaryBean), rowBounds);
	}

	@Override
	public List<DictionaryBean> selectByRowBounds(DictionaryBean newBean,
			RowBounds rowBounds) {
		return dictionaryCustomMapper.selectByRowBounds(newBean, rowBounds);
	}

	@Override
	public int selectCount(DictionaryBean newBean) {
		return dictionaryCustomMapper.selectCount(newBean);
	}

	/**
	 * 
	 * @param dictionaryBean
	 * @return
	 */
	private Object buildExample(DictionaryBean dictionaryBean) {
		Example example = new Example(DictionaryBean.class);
		Criteria c = example.createCriteria();
		// 参数处理
		if (StringUtils.isNotBlank(dictionaryBean.getKeyDic())) {
			c.andLike("keyDic", "%" + dictionaryBean.getKeyDic() + "%");
		}
		if (StringUtils.isNotBlank(dictionaryBean.getValueDic())) {
			c.andLike("valueDic", "%" + dictionaryBean.getValueDic() + "%");
		}
		if (StringUtils.isNotBlank(dictionaryBean.getTypeDic())) {
			c.andLike("typeDic", "%" + dictionaryBean.getTypeDic() + "%");
		}
		if (dictionaryBean.getStatus() != null) {
			c.andEqualTo("status", dictionaryBean.getStatus());
		}

		example.setOrderByClause(dictionaryBean.getOrders());// 排序
		return example;
	}

	/**
	 * 
	 */
	@Override
	public List<DictionaryBean> selectAll() {
		return dictionaryCustomMapper.selectAll();
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
			DictionaryBean dictionaryBean = arr.getJSONObject(i).toJavaObject(
					DictionaryBean.class);
			if (dictionaryBean.getId() == null) {
				// 判断是否已存在
				Example example = new Example(DictionaryBean.class);
				Criteria c = example.createCriteria();
				c.andCondition("key_dic='" + dictionaryBean.getKeyDic() + 
						"' or value_dic='" + dictionaryBean.getValueDic() + "'");
				if (dictionaryCustomMapper.selectCountByExample(example) > 0) {
					Error.throwError(DictionaryBean.EXISTS_DICTIONARY, "字典已存在");
				}
				if (StringUtils.isBlank(dictionaryBean.getKeyDic())) {
					dictionaryBean.setKeyDic(DateUtils.formatNumDateTime(new Date()));
				}
				dictionaryCustomMapper.insertSelective(dictionaryBean);
			} else {
				dictionaryCustomMapper
						.updateByPrimaryKeySelective(dictionaryBean);
			}
		}
		
		//更新redis
		updateRedisDictionary();
	}

	/*
	 * 删除
	 */
	public void delete(String id) {
		if (StringUtils.isBlank(id)) {
			return;
		}
		Example example = new Example(DictionaryBean.class);
		Criteria c = example.createCriteria();
		c.andIn("id", Arrays.asList(id.split(",")));
		dictionaryCustomMapper.deleteByExample(example);
		//更新redis
		updateRedisDictionary();
	}

	@Override
	public void updateRedisDictionary() {
		List<DictionaryBean> listInclude = dictionaryCustomMapper.selectNewsChannelDictionarys("news_keywords_tianwen_include");
		redisUtils.set(RedisKeys.DICTIONARY_NEWS_KEYWORDS_INCLUDE.key, JSONArray.toJSON(listInclude));
		
		List<DictionaryBean> listExclude = dictionaryCustomMapper.selectNewsChannelDictionarys("news_keywords_tianwen_exclude");
		redisUtils.set(RedisKeys.DICTIONARY_NEWS_KEYWORDS_EXCLUDE.key, JSONArray.toJSON(listExclude));
	}
}
