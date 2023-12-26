package com.tvi.entity;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="Response_Table")
public class ResponseTableModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="RESPONSE_ID")
	private Integer responseId;
	
	@Column(name="METHOD_NAME")
	private String methodName;
	
	@Column(name="REQUEST_JSON")
	private Clob requestJson;
	
	@Column(name="RESPONSE_JSON")
	private Clob responseJson;
	
	@Column(name="CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	public Integer getResponseId() {
		return responseId;
	}

	public void setResponseId(Integer responseId) {
		this.responseId = responseId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Clob getRequestJson() {
		return requestJson;
	}

	public void setRequestJson(Clob requestJson) {
		this.requestJson = requestJson;
	}

	public Clob getResponseJson() {
		return responseJson;
	}

	public void setResponseJson(Clob responseJson) {
		this.responseJson = responseJson;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
	

}
