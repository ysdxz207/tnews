package com.puyixiaowo.tnews.member.domain;

import java.io.Serializable;
import java.util.Date;

public class Member implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.id
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.username
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.password
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.mobile
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private String mobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.email
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.nickname
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private String nickname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.reg_time
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private Date regTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.status
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.head_img
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private String headImg;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.invite_code
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private String inviteCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.province
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private String province;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.city
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private String city;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.credits
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private Long credits;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.vip
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private Integer vip;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.sex
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private Integer sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.personalized_signature
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private String personalizedSignature;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_member.birthday
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private String birthday;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_member
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.id
     *
     * @return the value of t_member.id
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.id
     *
     * @param id the value for t_member.id
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.username
     *
     * @return the value of t_member.username
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.username
     *
     * @param username the value for t_member.username
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.password
     *
     * @return the value of t_member.password
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.password
     *
     * @param password the value for t_member.password
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.mobile
     *
     * @return the value of t_member.mobile
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.mobile
     *
     * @param mobile the value for t_member.mobile
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.email
     *
     * @return the value of t_member.email
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.email
     *
     * @param email the value for t_member.email
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.nickname
     *
     * @return the value of t_member.nickname
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.nickname
     *
     * @param nickname the value for t_member.nickname
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.reg_time
     *
     * @return the value of t_member.reg_time
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.reg_time
     *
     * @param regTime the value for t_member.reg_time
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.status
     *
     * @return the value of t_member.status
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.status
     *
     * @param status the value for t_member.status
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.head_img
     *
     * @return the value of t_member.head_img
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.head_img
     *
     * @param headImg the value for t_member.head_img
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.invite_code
     *
     * @return the value of t_member.invite_code
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public String getInviteCode() {
        return inviteCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.invite_code
     *
     * @param inviteCode the value for t_member.invite_code
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.province
     *
     * @return the value of t_member.province
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public String getProvince() {
        return province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.province
     *
     * @param province the value for t_member.province
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.city
     *
     * @return the value of t_member.city
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public String getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.city
     *
     * @param city the value for t_member.city
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.credits
     *
     * @return the value of t_member.credits
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public Long getCredits() {
        return credits;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.credits
     *
     * @param credits the value for t_member.credits
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setCredits(Long credits) {
        this.credits = credits;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.vip
     *
     * @return the value of t_member.vip
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public Integer getVip() {
        return vip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.vip
     *
     * @param vip the value for t_member.vip
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setVip(Integer vip) {
        this.vip = vip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.sex
     *
     * @return the value of t_member.sex
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.sex
     *
     * @param sex the value for t_member.sex
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.personalized_signature
     *
     * @return the value of t_member.personalized_signature
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.personalized_signature
     *
     * @param personalizedSignature the value for t_member.personalized_signature
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature == null ? null : personalizedSignature.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.birthday
     *
     * @return the value of t_member.birthday
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.birthday
     *
     * @param birthday the value for t_member.birthday
     *
     * @mbg.generated Fri Feb 24 13:55:21 CST 2017
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }
}