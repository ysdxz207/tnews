package com.puyixiaowo.tnews.manager.bean;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "manager_permission")
public class PermissionBean implements Serializable {
	@Id
	private Long id;
	private Long menuId;
	private String permissionName;
	private String permission;
	private static final long serialVersionUID = 1L;
	
	////////////

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName == null ? null : permissionName
				.trim();
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission == null ? null : permission.trim();
	}
	
}