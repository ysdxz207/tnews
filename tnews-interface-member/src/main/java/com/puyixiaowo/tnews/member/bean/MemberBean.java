package com.puyixiaowo.tnews.member.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.puyixiaowo.tnews.bean.BaseBean;
import com.puyixiaowo.tnews.common.utils.CustomDateTimeSerializer;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_member")
public class MemberBean extends BaseBean implements Serializable {
	@Id
	private Long id;
	private String username;
	private String password;
	private String mobile;
	private String email;
	private String nickname;
	@JSONField(serializeUsing = CustomDateTimeSerializer.class)
	private Date regTime;
	private Integer status;
	private String headImg;
	private String inviteCode;
	private String province;
	private String city;
	private Long credits;
	private Integer vip;
	private Integer sex;
	private String personalizedSignature;
	private String birthday;
	private static final long serialVersionUID = 1L;
	
	/////////////////////
	@Transient
	private String verificationCode;//验证码


	////////////////////
	public static final String NO_USERNAME = "NO_USERNAME";
	public static final String NO_PASSWORD = "NO_PASSWORD";
	public static final String NO_PROVINCE = "NO_PROVINCE";
	public static final String NO_CITY = "NO_CITY";
	public static final String LOGIN_FAILED = "LOGIN_FAILED";
	public static final String NO_VERIFICATION_CODE = "NO_VERIFICATION_CODE";
	public static final String NO_MEMBER_ID = "NO_MEMBER_ID";
	/**
	 * 该手机已注册
	 */
	public static final String EXISTS_USERNAME = "EXISTS_USERNAME";


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg == null ? null : headImg.trim();
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode == null ? null : inviteCode.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public Long getCredits() {
		return credits;
	}

	public void setCredits(Long credits) {
		this.credits = credits;
	}

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPersonalizedSignature() {
		return personalizedSignature;
	}

	public void setPersonalizedSignature(String personalizedSignature) {
		this.personalizedSignature = personalizedSignature == null ? null
				: personalizedSignature.trim();
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday == null ? null : birthday.trim();
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
	
}