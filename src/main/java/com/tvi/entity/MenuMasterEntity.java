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
@Table(name="MENU_MASTER")
public class MenuMasterEntity implements Serializable {

	private static final long serialVersionUID = -1753654117836157596L;
	
	@Id
	@GeneratedValue
	@Column(name="MENU_ID")
	private Integer menuId;
	
	@Column(name="MENU_NAME")
	private String menuName;
	
	@Column(name="ROLE")
	private String role;
	
	@Column(name="ROUTER_LINK")
	private String routerLink;
	
	@Column(name="DISPLAY_ORDER")
	private Integer displayOrder;
	
	@Column(name="ICON")
	private String icon;
	
	@Column(name="IS_SUB_MENU")
	private String isSubmenu;
	
	@Column(name="IS_ACTIVE")
	private String isActive;
	
	@Column(name="CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRouterLink() {
		return routerLink;
	}

	public void setRouterLink(String routerLink) {
		this.routerLink = routerLink;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIsSubmenu() {
		return isSubmenu;
	}

	public void setIsSubmenu(String isSubmenu) {
		this.isSubmenu = isSubmenu;
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
