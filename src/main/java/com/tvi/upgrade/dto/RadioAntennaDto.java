package com.tvi.upgrade.dto;

public class RadioAntennaDto {
	private String Feasibility,Customer_Punched_Or_Planning,Sector_Details;
	
	private String Action,RadioAntenna_i_WAN,No_of_Ports,RadioAntenna_Type;
	private Double Height_AGL_m,Azimuth_Degree,Length_m,Width_m,Depth_m;
	private BandFrequencyMHzDto BandFrequencyMHz_FrequencyCombination;
	
	private BandFrequencyMHzDto Band_Frequency_MHz_Frequency_Combination;
	
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	public String getRadioAntenna_i_WAN() {
		return RadioAntenna_i_WAN;
	}
	public void setRadioAntenna_i_WAN(String radioAntenna_i_WAN) {
		RadioAntenna_i_WAN = radioAntenna_i_WAN;
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
	public Double getHeight_AGL_m() {
		return Height_AGL_m;
	}
	public void setHeight_AGL_m(Double height_AGL_m) {
		Height_AGL_m = height_AGL_m;
	}
	public Double getAzimuth_Degree() {
		return Azimuth_Degree;
	}
	public void setAzimuth_Degree(Double azimuth_Degree) {
		Azimuth_Degree = azimuth_Degree;
	}
	public Double getLength_m() {
		return Length_m;
	}
	public void setLength_m(Double length_m) {
		Length_m = length_m;
	}
	public Double getWidth_m() {
		return Width_m;
	}
	public void setWidth_m(Double width_m) {
		Width_m = width_m;
	}
	public Double getDepth_m() {
		return Depth_m;
	}
	public void setDepth_m(Double depth_m) {
		Depth_m = depth_m;
	}
	public BandFrequencyMHzDto getBandFrequencyMHz_FrequencyCombination() {
		return BandFrequencyMHz_FrequencyCombination;
	}
	public void setBandFrequencyMHz_FrequencyCombination(BandFrequencyMHzDto bandFrequencyMHz_FrequencyCombination) {
		BandFrequencyMHz_FrequencyCombination = bandFrequencyMHz_FrequencyCombination;
	}
	public String getFeasibility() {
		return Feasibility;
	}
	public void setFeasibility(String feasibility) {
		Feasibility = feasibility;
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
	public BandFrequencyMHzDto getBand_Frequency_MHz_Frequency_Combination() {
		return Band_Frequency_MHz_Frequency_Combination;
	}
	public void setBand_Frequency_MHz_Frequency_Combination(BandFrequencyMHzDto band_Frequency_MHz_Frequency_Combination) {
		Band_Frequency_MHz_Frequency_Combination = band_Frequency_MHz_Frequency_Combination;
	}
	
}
