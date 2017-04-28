package com.puyixiaowo.tnews.dictionary.domain;

import java.io.Serializable;

public class Dictionary implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dictionary.id
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dictionary.key_dic
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    private String keyDic;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dictionary.value_dic
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    private String valueDic;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dictionary.type_dic
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    private String typeDic;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dictionary.news_channel_id
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    private Long newsChannelId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dictionary.status
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_dictionary
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dictionary.id
     *
     * @return the value of t_dictionary.id
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dictionary.id
     *
     * @param id the value for t_dictionary.id
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dictionary.key_dic
     *
     * @return the value of t_dictionary.key_dic
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    public String getKeyDic() {
        return keyDic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dictionary.key_dic
     *
     * @param keyDic the value for t_dictionary.key_dic
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    public void setKeyDic(String keyDic) {
        this.keyDic = keyDic == null ? null : keyDic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dictionary.value_dic
     *
     * @return the value of t_dictionary.value_dic
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    public String getValueDic() {
        return valueDic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dictionary.value_dic
     *
     * @param valueDic the value for t_dictionary.value_dic
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    public void setValueDic(String valueDic) {
        this.valueDic = valueDic == null ? null : valueDic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dictionary.type_dic
     *
     * @return the value of t_dictionary.type_dic
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    public String getTypeDic() {
        return typeDic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dictionary.type_dic
     *
     * @param typeDic the value for t_dictionary.type_dic
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    public void setTypeDic(String typeDic) {
        this.typeDic = typeDic == null ? null : typeDic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dictionary.news_channel_id
     *
     * @return the value of t_dictionary.news_channel_id
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    public Long getNewsChannelId() {
        return newsChannelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dictionary.news_channel_id
     *
     * @param newsChannelId the value for t_dictionary.news_channel_id
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    public void setNewsChannelId(Long newsChannelId) {
        this.newsChannelId = newsChannelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dictionary.status
     *
     * @return the value of t_dictionary.status
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dictionary.status
     *
     * @param status the value for t_dictionary.status
     *
     * @mbg.generated Thu Feb 09 10:58:03 CST 2017
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}