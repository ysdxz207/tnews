/**
 * 
 */
package com.puyixiaowo.tnews.enums;

/**
 * @author huangfeihong
 * @date 2017年1月30日 下午12:46:38
 */
public enum RedisKeys {
	API_CHANNEL_USING("API_CHANNEL_USING_", "已被新闻频道关联的接口频道"),
	DICTIONARY_NEWS_KEYWORDS_INCLUDE("DICTIONARY_NEWS_KEYWORDS_INCLUDE_", "新闻包含关键词字典"),
	DICTIONARY_NEWS_KEYWORDS_EXCLUDE("DICTIONARY_NEWS_KEYWORDS_EXCLUDE_", "新闻排除关键词字典"),
	NEWS_CHANNEL("NEWS_CHANNEL_", "新闻频道"),
	SWITCH_API_FILTER("SWITCH_API_FILTER_", "是否开启接口过滤同步"),
	SWITCH_API("SWITCH_API_", "是否开启接口同步"),
	SWITCH_API_AUTO("SWITCH_API_AUTO_", "自动同步新闻"),
	SMS_MEMBER_REG("SMS_MEMBER_REG_", "用户注册验证码"),
	SMS_MEMBER_MODIFY_PASS("SMS_MEMBER_MODIFY_PASS_", "用户修改密码验证码"),
	NEWS_DETAIL("NEWS_DETAIL_", "新闻详情"),
	API_REQUEST_SPECIFIED_TIME("API_REQUEST_SPECIFIED_TIME", "接口指定时间内执行最大次数的时间"),
	API_REQUEST_SPECIFIED_TIME_TIMES("API_REQUEST_SPECIFIED_TIME_TIMES", "接口指定时间内执行最大次数的次数"),
	API_REQUEST_URL_SPECIFIED_TIMES("API_REQUEST_URL_SPECIFIED_TIMES_", "接口在指定时间内已访问次数");

	RedisKeys(String key, String description){
		this.key = key;
		this.description = description;
	}
	
	public String key;
	public String description;
}
