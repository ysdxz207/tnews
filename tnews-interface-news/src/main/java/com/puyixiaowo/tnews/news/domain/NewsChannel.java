package com.puyixiaowo.tnews.news.domain;

import java.io.Serializable;
import java.util.Date;

public class NewsChannel implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_news_channel.id
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_news_channel.channel_name
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    private String channelName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_news_channel.code
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    private String code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_news_channel.create_time
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_news_channel.update_time
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_news_channel.status
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_news_channel
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_news_channel.id
     *
     * @return the value of t_news_channel.id
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_news_channel.id
     *
     * @param id the value for t_news_channel.id
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_news_channel.channel_name
     *
     * @return the value of t_news_channel.channel_name
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_news_channel.channel_name
     *
     * @param channelName the value for t_news_channel.channel_name
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_news_channel.code
     *
     * @return the value of t_news_channel.code
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_news_channel.code
     *
     * @param code the value for t_news_channel.code
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_news_channel.create_time
     *
     * @return the value of t_news_channel.create_time
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_news_channel.create_time
     *
     * @param createTime the value for t_news_channel.create_time
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_news_channel.update_time
     *
     * @return the value of t_news_channel.update_time
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_news_channel.update_time
     *
     * @param updateTime the value for t_news_channel.update_time
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_news_channel.status
     *
     * @return the value of t_news_channel.status
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_news_channel.status
     *
     * @param status the value for t_news_channel.status
     *
     * @mbg.generated Thu Feb 09 10:39:56 CST 2017
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}