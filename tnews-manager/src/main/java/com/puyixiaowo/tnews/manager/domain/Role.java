package com.puyixiaowo.tnews.manager.domain;

import java.io.Serializable;

public class Role implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column manager_role.id
     *
     * @mbg.generated Mon Jan 16 14:21:39 CST 2017
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column manager_role.role_name
     *
     * @mbg.generated Mon Jan 16 14:21:39 CST 2017
     */
    private String roleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column manager_role.code
     *
     * @mbg.generated Mon Jan 16 14:21:39 CST 2017
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table manager_role
     *
     * @mbg.generated Mon Jan 16 14:21:39 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column manager_role.id
     *
     * @return the value of manager_role.id
     *
     * @mbg.generated Mon Jan 16 14:21:39 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column manager_role.id
     *
     * @param id the value for manager_role.id
     *
     * @mbg.generated Mon Jan 16 14:21:39 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column manager_role.role_name
     *
     * @return the value of manager_role.role_name
     *
     * @mbg.generated Mon Jan 16 14:21:39 CST 2017
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column manager_role.role_name
     *
     * @param roleName the value for manager_role.role_name
     *
     * @mbg.generated Mon Jan 16 14:21:39 CST 2017
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column manager_role.code
     *
     * @return the value of manager_role.code
     *
     * @mbg.generated Mon Jan 16 14:21:39 CST 2017
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column manager_role.code
     *
     * @param code the value for manager_role.code
     *
     * @mbg.generated Mon Jan 16 14:21:39 CST 2017
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}