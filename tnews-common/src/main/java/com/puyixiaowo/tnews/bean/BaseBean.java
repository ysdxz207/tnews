/**
 * 
 */
package com.puyixiaowo.tnews.bean;

import com.puyixiaowo.tnews.common.utils.CamelCaseUtils;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author huangfeihong
 * @date 2017年1月22日 下午10:57:33
 */
public class BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3343330108451606790L;
	
	@Transient
	private String orders;//排序
	
	///////
	public String getOrders() {
		return CamelCaseUtils.toUnderlineName(StringUtils.isNotBlank(orders) ? orders : null);
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}
	
	
}
