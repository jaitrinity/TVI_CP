package com.tvi.dto;

public class ValidBscRncDto {
	private Integer typeNo;
	private String Feasibility;
	
	private String NetWork_Type,BSC_RNC_Type,BSC_RNC_Manufacturer,BSC_RNC_Make,Length_Mtrs,Breadth_Mtrs,Height_AGL,
	BSC_RNC_Power_Rating,Customer_Punched_Or_Planning="Planning",Action="Add";
	public Integer getTypeNo() {
		return typeNo;
	}
	public void setTypeNo(Integer typeNo) {
		this.typeNo = typeNo;
	}
	
	public String getFeasibility() {
		return Feasibility;
	}
	public void setFeasibility(String feasibility) {
		Feasibility = feasibility;
	}
	public String getNetWork_Type() {
		return NetWork_Type;
	}
	public void setNetWork_Type(String netWork_Type) {
		NetWork_Type = netWork_Type;
	}
	public String getBSC_RNC_Type() {
		return BSC_RNC_Type;
	}
	public void setBSC_RNC_Type(String bSC_RNC_Type) {
		BSC_RNC_Type = bSC_RNC_Type;
	}
	public String getBSC_RNC_Manufacturer() {
		return BSC_RNC_Manufacturer;
	}
	public void setBSC_RNC_Manufacturer(String bSC_RNC_Manufacturer) {
		BSC_RNC_Manufacturer = bSC_RNC_Manufacturer;
	}
	public String getBSC_RNC_Make() {
		return BSC_RNC_Make;
	}
	public void setBSC_RNC_Make(String bSC_RNC_Make) {
		BSC_RNC_Make = bSC_RNC_Make;
	}
	public String getLength_Mtrs() {
		return Length_Mtrs;
	}
	public void setLength_Mtrs(String length_Mtrs) {
		Length_Mtrs = length_Mtrs;
	}
	public String getBreadth_Mtrs() {
		return Breadth_Mtrs;
	}
	public void setBreadth_Mtrs(String breadth_Mtrs) {
		Breadth_Mtrs = breadth_Mtrs;
	}
	public String getHeight_AGL() {
		return Height_AGL;
	}
	public void setHeight_AGL(String height_AGL) {
		Height_AGL = height_AGL;
	}
	public String getBSC_RNC_Power_Rating() {
		return BSC_RNC_Power_Rating;
	}
	public void setBSC_RNC_Power_Rating(String bSC_RNC_Power_Rating) {
		BSC_RNC_Power_Rating = bSC_RNC_Power_Rating;
	}
	public String getCustomer_Punched_Or_Planning() {
		return Customer_Punched_Or_Planning;
	}
	public void setCustomer_Punched_Or_Planning(String customer_Punched_Or_Planning) {
		Customer_Punched_Or_Planning = customer_Punched_Or_Planning;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	
}
