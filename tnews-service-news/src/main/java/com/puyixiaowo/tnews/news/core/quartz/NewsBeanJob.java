package com.puyixiaowo.tnews.news.core.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.puyixiaowo.tnews.common.utils.RedisUtils;
import com.puyixiaowo.tnews.enums.RedisKeys;
import com.puyixiaowo.tnews.news.service.NewsApiService;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class NewsBeanJob {
	
	@Autowired
	private NewsApiService newsApiService;
	@Autowired
	private RedisUtils redisUtils;
	
	
	public void doSyncNews() {
		Object switchAutoObj = redisUtils.get(RedisKeys.SWITCH_API_AUTO.key);
		boolean auto = Boolean.valueOf(switchAutoObj == null ? "true" : switchAutoObj.toString());
		if (auto) {
			newsApiService.syncNews();
		}
	}
	
}
