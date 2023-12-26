package com.tvi.request;

import java.util.List;

import com.tvi.dto.SystemParamDTO;

public class EmployeeActionRequest {
	private Integer primaryId;
	private String empId,
	searchType; // Y : Activate, N : Deactivate
	
	private String empName,mobile,emailId;
	private List<SystemParamDTO> circleName;
	
	public Integer getPrimaryId() {
		return primaryId;
	}
	public void setPrimaryId(Integer primaryId) {
		this.primaryId = primaryId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public List<SystemParamDTO> getCircleName() {
		return circleName;
	}
	public void setCircleName(List<SystemParamDTO> circleName) {
		this.circleName = circleName;
	}
	
	
	
}
