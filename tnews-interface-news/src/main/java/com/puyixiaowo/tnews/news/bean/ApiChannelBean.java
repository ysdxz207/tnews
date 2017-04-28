package com.puyixiaowo.tnews.news.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

import com.puyixiaowo.tnews.bean.BaseBean;

@Table(name = "t_api_channel")
public class ApiChannelBean extends BaseBean implements Serializable {
	private Long id;
	private String channelId;
	private String channelName;
	private Date createTime;
	private Date updateTime;
	private Integer status;
	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId == null ? null : channelId.trim();
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName == null ? null : channelName.trim();
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
}