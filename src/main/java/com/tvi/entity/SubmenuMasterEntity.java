package com.tvi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="SUB_MENU_MASTER")
public class SubmenuMasterEntity implements Serializable {
	
	private static final long serialVersionUID = -3419799981247931335L;
	
	@Id
	@GeneratedValue
	@Column(name="SUB_MENU_ID")
	private Integer submenuId;
	
	@Column(name="MENU_ID")
	private Integer menuId;
	
	@Column(name="SUB_MENU_NAME")
	private String submenuName;
	
	@Column(name="ROLE")
	private String role;
	
	@Column(name="DISPLAY_ORDER")
	private Integer displayOrder;
	
	@Column(name="ROUTER_LINK")
	private String routerLink;
	
	@Column(name="ICON")
	private String icon;
	
	@Column(name="IS_ACTIVE")
	private String isActive;
	
	@Column(name="CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public Integer getSubmenuId() {
		return submenuId;
	}

	public void setSubmenuId(Integer submenuId) {
		this.submenuId = submenuId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getSubmenuName() {
		return submenuName;
	}

	public void setSubmenuName(String submenuName) {
		this.submenuName = submenuName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getRouterLink() {
		return routerLink;
	}

	public void setRouterLink(String routerLink) {
		this.routerLink = routerLink;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	

}
