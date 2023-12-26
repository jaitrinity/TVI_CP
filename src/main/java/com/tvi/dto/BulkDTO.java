package com.tvi.dto;

import java.util.List;

public class BulkDTO {
	private String loginEmpId,loginEmpRole,bulkAction,bulkRemark,bulkSharingPotential;
	private List<BulkSrDTO> checkCheckedList;
	
	public String getLoginEmpId() {
		return loginEmpId;
	}
	public void setLoginEmpId(String loginEmpId) {
		this.loginEmpId = loginEmpId;
	}
	public String getLoginEmpRole() {
		return loginEmpRole;
	}
	public void setLoginEmpRole(String loginEmpRole) {
		this.loginEmpRole = loginEmpRole;
	}
	public String getBulkAction() {
		return bulkAction;
	}
	public void setBulkAction(String bulkAction) {
		this.bulkAction = bulkAction;
	}
	public String getBulkRemark() {
		return bulkRemark;
	}
	public void setBulkRemark(String bulkRemark) {
		this.bulkRemark = bulkRemark;
	}
	public String getBulkSharingPotential() {
		return bulkSharingPotential;
	}
	public void setBulkSharingPotential(String bulkSharingPotential) {
		this.bulkSharingPotential = bulkSharingPotential;
	}
	public List<BulkSrDTO> getCheckCheckedList() {
		return checkCheckedList;
	}
	public void setCheckCheckedList(List<BulkSrDTO> checkCheckedList) {
		this.checkCheckedList = checkCheckedList;
	}
	
	

}
