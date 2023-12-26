package com.tvi.dto;

public class RfaiAcceptReject {
	private String TOCO_Site_ID,SR_No,Accept_Reject,Reject_Remarks,Reject_Remarks_Others;
	
	// Use for send email	
	private String srNumber,spNumber,soNumber,status,circle,tabName;

	public String getTOCO_Site_ID() {
		return TOCO_Site_ID;
	}

	public void setTOCO_Site_ID(String tOCO_Site_ID) {
		TOCO_Site_ID = tOCO_Site_ID;
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

	public String getReject_Remarks() {
		return Reject_Remarks;
	}

	public void setReject_Remarks(String reject_Remarks) {
		Reject_Remarks = reject_Remarks;
	}

	public String getReject_Remarks_Others() {
		return Reject_Remarks_Others;
	}

	public void setReject_Remarks_Others(String reject_Remarks_Others) {
		Reject_Remarks_Others = reject_Remarks_Others;
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
