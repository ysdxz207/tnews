/**
 *
 */
package com.puyixiaowo.tnews.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.puyixiaowo.tnews.common.utils.Constants;

import java.io.Serializable;

/**
 * @author huangfeihong
 * @date 2016年12月6日 下午9:24:25
 */
public class ResponseBean implements Serializable {

	private static final long serialVersionUID = -5266170746828998914L;
	private int statusCode = Constants.RESPONSE_STATUS_CODE_SUCCESS;
	private String errorCode = null;
	private String message = Constants.RESPONSE_SUCCESS_MESSAGE;
	private Object data;

	//////////
	private boolean closeCurrent = true;//默认关闭当前对话框
	private String tabid;
	private String forward;
	private String forwardConfirm;


	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isCloseCurrent() {
		return closeCurrent;
	}

	public void setCloseCurrent(boolean closeCurrent) {
		this.closeCurrent = closeCurrent;
	}

	public String getTabid() {
		return tabid;
	}

	public void setTabid(String tabid) {
		this.tabid = tabid;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getForwardConfirm() {
		return forwardConfirm;
	}

	public void setForwardConfirm(String forwardConfirm) {
		this.forwardConfirm = forwardConfirm;
	}

	//////////////////////////////

	/**
	 * 序列化
	 *
	 * @param data
	 */
	public void setSerializeData(Object data) {
		this.data = JSONObject.parse(JSON.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
	}

	public ResponseBean error(String message) {
		error("ERROR", message);
		return this;
	}

	public ResponseBean error(String errorCode, String message) {
		this.statusCode = Constants.RESPONSE_STATUS_CODE_ERROR;
		this.errorCode = errorCode;
		this.message = message;
		return this;
	}

	public ResponseBean error(Exception e) {
		error(e.getMessage(), e.getCause().getMessage());
		return this;
	}

	/**
	 * 序列化
	 *
	 * @return
	 */
	public String serialize() {
		return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
	}

}
