package com.tvi.response;


public class LoginResponse {
	private String empId;
	private String empName;
	private String empRole,isHoUser,operator="";
	private String circleName=""; // comma seperate value

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpRole() {
		return empRole;
	}

	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getIsHoUser() {
		return isHoUser;
	}

	public void setIsHoUser(String isHoUser) {
		this.isHoUser = isHoUser;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
