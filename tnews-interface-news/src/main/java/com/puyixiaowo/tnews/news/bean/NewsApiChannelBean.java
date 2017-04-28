package com.puyixiaowo.tnews.news.bean;
import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import com.puyixiaowo.tnews.bean.BaseBean;

@Table(name="t_news_api_channel")
public class NewsApiChannelBean extends BaseBean implements Serializable {
	@Id
    private Long id;
    private Long apiChannelId;
    private Long newsChannelId;
    private static final long serialVersionUID = 1L;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getApiChannelId() {
        return apiChannelId;
    }
    public void setApiChannelId(Long apiChannelId) {
        this.apiChannelId = apiChannelId;
    }
    public Long getNewsChannelId() {
        return newsChannelId;
    }
    public void setNewsChannelId(Long newsChannelId) {
        this.newsChannelId = newsChannelId;
    }
}