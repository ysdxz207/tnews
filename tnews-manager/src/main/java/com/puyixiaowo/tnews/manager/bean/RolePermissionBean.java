package com.puyixiaowo.tnews.manager.bean;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "manager_role_permission")
public class RolePermissionBean implements Serializable {
	@Id
	private Long id;
	private Long roleId;
	private Long permissionId;
	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
}