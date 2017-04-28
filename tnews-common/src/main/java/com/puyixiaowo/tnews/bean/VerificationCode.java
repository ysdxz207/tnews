package com.puyixiaowo.tnews.bean;

import java.io.Serializable;

/**
 * 验证码实体
 * 
 * @author huangfeihong
 * @date 2017年2月24日
 */
public class VerificationCode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -600372892158196996L;

    public String mobile;
	public Integer type;

	// ///////////////////error code
	/**
	 * 没有传入手机号[mobile]
	 */
	public static final String NO_MOBILE = "NO_MOBILE";
	/**
	 * 没有传入验证码类型[type]
	 */
	public static final String NO_TYPE = "NO_TYPE";

	/**
	 * 发送短信失败
	 * @return
	 */
	public static final String SEND_SMS_ERROR = "SEND_SMS_ERROR";

	/**
	 * 验证码已失效
	 * @return
	 */
	public static final String EXPIRE_VERIFICATION_CODE = "EXPIRE_VERIFICATION_CODE";

	/**
	 *验证码错误
	 * @return
	 */
	public static final String WRONG_VERIFICATION_CODE = "WRONG_VERIFICATION_CODE";


	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
