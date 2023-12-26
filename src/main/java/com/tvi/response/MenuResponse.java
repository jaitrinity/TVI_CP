package com.tvi.response;

import java.util.List;


public class MenuResponse {
	private String menuName;
	private String routerLink;
	private String icon;
	private String isSubMenu;
	private List<SubMenuResponse> submenuList;
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
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
	public List<SubMenuResponse> getSubmenuList() {
		return submenuList;
	}
	public void setSubmenuList(List<SubMenuResponse> submenuList) {
		this.submenuList = submenuList;
	}
	public String getIsSubMenu() {
		return isSubMenu;
	}
	public void setIsSubMenu(String isSubMenu) {
		this.isSubMenu = isSubMenu;
	}
	

}
