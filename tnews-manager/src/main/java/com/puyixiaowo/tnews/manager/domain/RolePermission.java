package com.puyixiaowo.tnews.manager.domain;

import java.io.Serializable;

public class RolePermission implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column manager_role_permission.id
     *
     * @mbg.generated Sun Jan 08 22:36:57 CST 2017
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column manager_role_permission.role_id
     *
     * @mbg.generated Sun Jan 08 22:36:57 CST 2017
     */
    private Long roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column manager_role_permission.permission_id
     *
     * @mbg.generated Sun Jan 08 22:36:57 CST 2017
     */
    private Long permissionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table manager_role_permission
     *
     * @mbg.generated Sun Jan 08 22:36:57 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column manager_role_permission.id
     *
     * @return the value of manager_role_permission.id
     *
     * @mbg.generated Sun Jan 08 22:36:57 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column manager_role_permission.id
     *
     * @param id the value for manager_role_permission.id
     *
     * @mbg.generated Sun Jan 08 22:36:57 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column manager_role_permission.role_id
     *
     * @return the value of manager_role_permission.role_id
     *
     * @mbg.generated Sun Jan 08 22:36:57 CST 2017
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column manager_role_permission.role_id
     *
     * @param roleId the value for manager_role_permission.role_id
     *
     * @mbg.generated Sun Jan 08 22:36:57 CST 2017
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column manager_role_permission.permission_id
     *
     * @return the value of manager_role_permission.permission_id
     *
     * @mbg.generated Sun Jan 08 22:36:57 CST 2017
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column manager_role_permission.permission_id
     *
     * @param permissionId the value for manager_role_permission.permission_id
     *
     * @mbg.generated Sun Jan 08 22:36:57 CST 2017
     */
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}