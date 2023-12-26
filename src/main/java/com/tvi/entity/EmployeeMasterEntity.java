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
@Table(name="EMPLOYEE_MASTER")
public class EmployeeMasterEntity implements Serializable {
	private static final long serialVersionUID = -4294329338764415915L;

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	
	@Column(name="EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="MOBILE")
	private String mobile;
	
	@Column(name="EMAIL_ID")
	private String emailId;
	
	@Column(name="ORGANIZATION")
	private String organization;
	
	@Column(name="ROLE")
	private String role;
	
	@Column(name="CIRCLE_NAME")
	private String circleName;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="IS_HO_USER")
	private String isHoUser;
	
	@Column(name="IS_ACTIVE")
	private String isActive;
	
	@Column(name="CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsHoUser() {
		return isHoUser;
	}

	public void setIsHoUser(String isHoUser) {
		this.isHoUser = isHoUser;
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
