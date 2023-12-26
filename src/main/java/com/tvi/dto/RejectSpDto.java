package com.tvi.dto;

public class RejectSpDto {
	private String Rejection_Category,Rejection_Reason,SP_Ref_No,SR_No,Accept_Reject,Remarks;
	
	// Use for send email	
	private String srNumber,spNumber,soNumber,status,circle,tabName;

	public String getRejection_Category() {
		return Rejection_Category;
	}

	public void setRejection_Category(String rejection_Category) {
		Rejection_Category = rejection_Category;
	}

	public String getRejection_Reason() {
		return Rejection_Reason;
	}

	public void setRejection_Reason(String rejection_Reason) {
		Rejection_Reason = rejection_Reason;
	}

	public String getSP_Ref_No() {
		return SP_Ref_No;
	}

	public void setSP_Ref_No(String sP_Ref_No) {
		SP_Ref_No = sP_Ref_No;
	}

	public String getSR_No() {
		return SR_No;
	}

	public void setSR_No(String sR_No) {
		SR_No = sR_No;
	}

	public String getAccept_Reject() {
		return Accept_Reject;
	}

	public void setAccept_Reject(String accept_Reject) {
		Accept_Reject = accept_Reject;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}

	public String getSpNumber() {
		return spNumber;
	}

	public void setSpNumber(String spNumber) {
		this.spNumber = spNumber;
	}

	public String getSoNumber() {
		return soNumber;
	}

	public void setSoNumber(String soNumber) {
		this.soNumber = soNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	
}
