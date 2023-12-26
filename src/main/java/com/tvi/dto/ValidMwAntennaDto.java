package com.tvi.dto;

public class ValidMwAntennaDto {
	private Integer typeNo;
	private String Feasibility;
	
	private String MWAntenna_i_WAN,Size_of_MW,Height_in_Mtrs,Azimuth_Degree,
	Customer_Punched_Or_Planning="Planning",Sector_Details="Sector 1",Action="Add",Source="";
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
	public String getMWAntenna_i_WAN() {
		return MWAntenna_i_WAN;
	}
	public void setMWAntenna_i_WAN(String mWAntenna_i_WAN) {
		MWAntenna_i_WAN = mWAntenna_i_WAN;
	}
	public String getSize_of_MW() {
		return Size_of_MW;
	}
	public void setSize_of_MW(String size_of_MW) {
		Size_of_MW = size_of_MW;
	}
	public String getHeight_in_Mtrs() {
		return Height_in_Mtrs;
	}
	public void setHeight_in_Mtrs(String height_in_Mtrs) {
		Height_in_Mtrs = height_in_Mtrs;
	}
	public String getAzimuth_Degree() {
		return Azimuth_Degree;
	}
	public void setAzimuth_Degree(String azimuth_Degree) {
		Azimuth_Degree = azimuth_Degree;
	}
	public String getCustomer_Punched_Or_Planning() {
		return Customer_Punched_Or_Planning;
	}
	public void setCustomer_Punched_Or_Planning(String customer_Punched_Or_Planning) {
		Customer_Punched_Or_Planning = customer_Punched_Or_Planning;
	}
	public String getSector_Details() {
		return Sector_Details;
	}
	public void setSector_Details(String sector_Details) {
		Sector_Details = sector_Details;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	public String getSource() {
		return Source;
	}
	public void setSource(String source) {
		Source = source;
	}
	
	
}
