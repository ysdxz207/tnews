package com.puyixiaowo.tnews.manager.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "manager_role")
public class RoleBean implements Serializable {
	@Id
	private Long id;
	private String roleName;
	private String code;
	private static final long serialVersionUID = 1L;

	//////////////////////
	@Transient
	private List<PermissionBean> permissions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName == null ? null : roleName.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<PermissionBean> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionBean> permissions) {
		this.permissions = permissions;
	}

}