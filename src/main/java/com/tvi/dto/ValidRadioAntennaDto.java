package com.tvi.dto;

public class ValidRadioAntennaDto {
	private Integer typeNo;
	private String Feasibility;
	
	private String RadioAntenna_i_WAN,Height_AGL_m,Azimuth_Degree,Length_m,Width_m,Depth_m,No_of_Ports,RadioAntenna_Type,
	BandFrequencyMHz_FrequencyCombination,Customer_Punched_Or_Planning="Planning",Sector_Details="Sector 1",Action;
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
	public String getRadioAntenna_i_WAN() {
		return RadioAntenna_i_WAN;
	}
	public void setRadioAntenna_i_WAN(String radioAntenna_i_WAN) {
		RadioAntenna_i_WAN = radioAntenna_i_WAN;
	}
	public String getHeight_AGL_m() {
		return Height_AGL_m;
	}
	public void setHeight_AGL_m(String height_AGL_m) {
		Height_AGL_m = height_AGL_m;
	}
	public String getAzimuth_Degree() {
		return Azimuth_Degree;
	}
	public void setAzimuth_Degree(String azimuth_Degree) {
		Azimuth_Degree = azimuth_Degree;
	}
	public String getLength_m() {
		return Length_m;
	}
	public void setLength_m(String length_m) {
		Length_m = length_m;
	}
	public String getWidth_m() {
		return Width_m;
	}
	public void setWidth_m(String width_m) {
		Width_m = width_m;
	}
	public String getDepth_m() {
		return Depth_m;
	}
	public void setDepth_m(String depth_m) {
		Depth_m = depth_m;
	}
	public String getNo_of_Ports() {
		return No_of_Ports;
	}
	public void setNo_of_Ports(String no_of_Ports) {
		No_of_Ports = no_of_Ports;
	}
	public String getRadioAntenna_Type() {
		return RadioAntenna_Type;
	}
	public void setRadioAntenna_Type(String radioAntenna_Type) {
		RadioAntenna_Type = radioAntenna_Type;
	}
	public String getBandFrequencyMHz_FrequencyCombination() {
		return BandFrequencyMHz_FrequencyCombination;
	}
	public void setBandFrequencyMHz_FrequencyCombination(String bandFrequencyMHz_FrequencyCombination) {
		BandFrequencyMHz_FrequencyCombination = bandFrequencyMHz_FrequencyCombination;
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
}
