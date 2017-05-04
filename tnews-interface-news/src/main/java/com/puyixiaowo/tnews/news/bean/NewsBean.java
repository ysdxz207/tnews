package com.puyixiaowo.tnews.news.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.puyixiaowo.tnews.annotation.ReqNotEmpty;
import com.puyixiaowo.tnews.bean.BaseBean;
import com.puyixiaowo.tnews.common.utils.CustomDateTimeSerializer;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_news")
public class NewsBean extends BaseBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 57297363572586253L;

	@Id
	private Long id;
	private String nid;
	private String title;
	private String content;
	private String contentWithImgs;
	private String source;
	private String html;
	private String faceUrl;
	private String description;
	private String channelId;
	@ReqNotEmpty
	private Long newsChannelId;
	private String channelName;
	private String link;
	private String imageurls;
	@JSONField(serializeUsing = CustomDateTimeSerializer.class)
	private Date pubDate;
	@JSONField(serializeUsing = CustomDateTimeSerializer.class)
	private Date createTime;
	private String allList;
	private Long creatorId;
	private Integer status;
	private String faceUrlKeys;
	
	/////////////////////
	@Transient
	private String newsChannelName;//新闻频道名

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentWithImgs() {
		return contentWithImgs;
	}

	public void setContentWithImgs(String contentWithImgs) {
		this.contentWithImgs = contentWithImgs;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getFaceUrl() {
		return faceUrl;
	}

	public void setFaceUrl(String faceUrl) {
		this.faceUrl = faceUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Long getNewsChannelId() {
		return newsChannelId;
	}

	public void setNewsChannelId(Long newsChannelId) {
		this.newsChannelId = newsChannelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImageurls() {
		return imageurls;
	}

	public void setImageurls(String imageurls) {
		this.imageurls = imageurls;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAllList() {
		return allList;
	}

	public void setAllList(String allList) {
		this.allList = allList;
	}

	public String getNewsChannelName() {
		return newsChannelName;
	}

	public void setNewsChannelName(String newsChannelName) {
		this.newsChannelName = newsChannelName;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFaceUrlKeys() {
		return faceUrlKeys;
	}

	public void setFaceUrlKeys(String faceUrlKeys) {
		this.faceUrlKeys = faceUrlKeys;
	}
}
