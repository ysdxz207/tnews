package com.puyixiaowo.tnews.news.core;

import com.aliyun.odps.utils.StringUtils;
import com.puyixiaowo.tnews.common.utils.RedisUtils;
import com.puyixiaowo.tnews.core.MessageResolver;
import com.puyixiaowo.tnews.enums.RedisKeys;
import com.puyixiaowo.tnews.news.service.ApiChannelService;
import com.puyixiaowo.tnews.news.service.NewsChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * spring启动执行
 * @author ysdxz207
 *
 */
public class InitProcessor implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	private ApiChannelService apiChannelService;
	@Autowired
	private NewsChannelService newsChannelService;
	@Autowired
	private MessageResolver messageResolver;
	@Autowired
	private RedisUtils redisUtils;

	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		putApiChannel();
		initApiConfigs();
	}
	
	/**
	 * 查询apiChannel
	 */
	public void putApiChannel(){
		apiChannelService.getUsingChannelListFromRedis();
		newsChannelService.getAllNewsChannelFromRedis();
	}
	/*
	 * 初始化接口配置，并保存到redis
	 */
	public void initApiConfigs(){
		String timeStr = messageResolver.getMessage("specified.time");
		String timeTimesStr = messageResolver.getMessage("specified.time_times");

		long time = StringUtils.isBlank(timeStr) ? 1000 : Long.parseLong(timeStr);
		int time_times = StringUtils.isBlank(timeTimesStr) ? 2 : Integer.parseInt(timeTimesStr);

		redisUtils.set(RedisKeys.API_REQUEST_SPECIFIED_TIME.key, time);
		redisUtils.set(RedisKeys.API_REQUEST_SPECIFIED_TIME_TIMES.key, time_times);
	}
}
