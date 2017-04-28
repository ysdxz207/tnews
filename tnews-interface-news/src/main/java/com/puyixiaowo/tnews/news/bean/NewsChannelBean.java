package com.puyixiaowo.tnews.news.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.puyixiaowo.tnews.bean.BaseBean;
import com.puyixiaowo.tnews.common.utils.CustomDateTimeSerializer;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name="t_news_channel")
public class NewsChannelBean extends BaseBean implements Serializable {
	@Id
	private Long id;
	private String channelName;
	private String code;
	@JSONField(serializeUsing = CustomDateTimeSerializer.class)
	private Date createTime;
	@JSONField(serializeUsing = CustomDateTimeSerializer.class)
	private Date updateTime;
	private Integer status;
	private static final long serialVersionUID = 1L;
	
	
	/////////////////
	@Transient
	private String apiChannelIds;//接口频道，逗号分隔
	@Transient
	private String apiChannelNames;//接口频道名,逗号分隔

	/////////////////
	public static final String EXISTS_NEWS_CHANNEL = "EXISTS_NEWS_CHANNEL";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getApiChannelIds() {
		return apiChannelIds;
	}

	public void setApiChannelIds(String apiChannelIds) {
		this.apiChannelIds = apiChannelIds;
	}

	public String getApiChannelNames() {
		return apiChannelNames;
	}

	public void setApiChannelNames(String apiChannelNames) {
		this.apiChannelNames = apiChannelNames;
	}
}