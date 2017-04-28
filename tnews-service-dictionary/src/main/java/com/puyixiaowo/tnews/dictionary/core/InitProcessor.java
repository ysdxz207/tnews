package com.puyixiaowo.tnews.dictionary.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.puyixiaowo.tnews.dictionary.service.DictionaryService;

/**
 * spring启动执行
 * @author ysdxz207
 *
 */
public class InitProcessor implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		cachedDictionary();
	}
	
	/**
	 * 缓存新闻关键词字典
	 */
	public void cachedDictionary(){
		dictionaryService.updateRedisDictionary();
	}
}
