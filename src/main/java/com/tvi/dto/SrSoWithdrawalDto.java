package com.tvi.dto;

public class SrSoWithdrawalDto {
	private String TOCO_Site_ID,SR_No,Withdrawal_Type,Withdrawal_Reason,withdraw_remark;
	
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

	public String getWithdrawal_Type() {
		return Withdrawal_Type;
	}

	public void setWithdrawal_Type(String withdrawal_Type) {
		Withdrawal_Type = withdrawal_Type;
	}

	public String getWithdrawal_Reason() {
		return Withdrawal_Reason;
	}

	public void setWithdrawal_Reason(String withdrawal_Reason) {
		Withdrawal_Reason = withdrawal_Reason;
	}

	public String getWithdraw_remark() {
		return withdraw_remark;
	}

	public void setWithdraw_remark(String withdraw_remark) {
		this.withdraw_remark = withdraw_remark;
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
