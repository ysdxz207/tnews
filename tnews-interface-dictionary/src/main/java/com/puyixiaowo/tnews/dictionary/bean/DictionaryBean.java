package com.puyixiaowo.tnews.dictionary.bean;

import com.puyixiaowo.tnews.bean.BaseBean;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_dictionary")
public class DictionaryBean extends BaseBean implements Serializable {
	@Id
	private Long id;
	private String keyDic;
	private String valueDic;
	private String typeDic;
	private Long newsChannelId;
	private Integer status;
	private static final long serialVersionUID = 1L;


	///////////////////
	public static final String EXISTS_DICTIONARY = "EXISTS_DICTIONARY";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyDic() {
		return keyDic;
	}

	public void setKeyDic(String keyDic) {
		this.keyDic = keyDic;
	}

	public String getValueDic() {
		return valueDic;
	}

	public void setValueDic(String valueDic) {
		this.valueDic = valueDic;
	}

	public String getTypeDic() {
		return typeDic;
	}

	public void setTypeDic(String typeDic) {
		this.typeDic = typeDic;
	}

	public Long getNewsChannelId() {
		return newsChannelId;
	}

	public void setNewsChannelId(Long newsChannelId) {
		this.newsChannelId = newsChannelId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}