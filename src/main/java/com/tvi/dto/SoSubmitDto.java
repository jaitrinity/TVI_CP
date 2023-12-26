package com.tvi.dto;

public class SoSubmitDto {
	private String Operator,Accept_Reject,Financial_Site_Id,Expected_monthly_Rent_Approved,CAPEX_Amount_Approved,
	OPEX_Amount_Approved,Tentative_Billing_Amount_Approved,Target_Date,SP_Ref_No,Indus_Site_Id,Request_Ref_No,Date,
	Customer_SO_No,Tenure_In_Years;
	
	// Use for send email	
	private String srNumber,spNumber,soNumber,status,circle,tabName;

	public String getOperator() {
		return Operator;
	}

	public void setOperator(String operator) {
		Operator = operator;
	}

	public String getAccept_Reject() {
		return Accept_Reject;
	}

	public void setAccept_Reject(String accept_Reject) {
		Accept_Reject = accept_Reject;
	}

	public String getFinancial_Site_Id() {
		return Financial_Site_Id;
	}

	public void setFinancial_Site_Id(String financial_Site_Id) {
		Financial_Site_Id = financial_Site_Id;
	}

	public String getExpected_monthly_Rent_Approved() {
		return Expected_monthly_Rent_Approved;
	}

	public void setExpected_monthly_Rent_Approved(String expected_monthly_Rent_Approved) {
		Expected_monthly_Rent_Approved = expected_monthly_Rent_Approved;
	}

	public String getCAPEX_Amount_Approved() {
		return CAPEX_Amount_Approved;
	}

	public void setCAPEX_Amount_Approved(String cAPEX_Amount_Approved) {
		CAPEX_Amount_Approved = cAPEX_Amount_Approved;
	}

	public String getOPEX_Amount_Approved() {
		return OPEX_Amount_Approved;
	}

	public void setOPEX_Amount_Approved(String oPEX_Amount_Approved) {
		OPEX_Amount_Approved = oPEX_Amount_Approved;
	}

	public String getTentative_Billing_Amount_Approved() {
		return Tentative_Billing_Amount_Approved;
	}

	public void setTentative_Billing_Amount_Approved(String tentative_Billing_Amount_Approved) {
		Tentative_Billing_Amount_Approved = tentative_Billing_Amount_Approved;
	}

	public String getTarget_Date() {
		return Target_Date;
	}

	public void setTarget_Date(String target_Date) {
		Target_Date = target_Date;
	}

	public String getSP_Ref_No() {
		return SP_Ref_No;
	}

	public void setSP_Ref_No(String sP_Ref_No) {
		SP_Ref_No = sP_Ref_No;
	}

	public String getIndus_Site_Id() {
		return Indus_Site_Id;
	}

	public void setIndus_Site_Id(String indus_Site_Id) {
		Indus_Site_Id = indus_Site_Id;
	}

	public String getRequest_Ref_No() {
		return Request_Ref_No;
	}

	public void setRequest_Ref_No(String request_Ref_No) {
		Request_Ref_No = request_Ref_No;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getCustomer_SO_No() {
		return Customer_SO_No;
	}

	public void setCustomer_SO_No(String customer_SO_No) {
		Customer_SO_No = customer_SO_No;
	}

	public String getTenure_In_Years() {
		return Tenure_In_Years;
	}

	public void setTenure_In_Years(String tenure_In_Years) {
		Tenure_In_Years = tenure_In_Years;
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

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
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
}
