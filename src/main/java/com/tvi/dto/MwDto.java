package com.tvi.dto;

import java.util.List;

public class MwDto {
	private String Additional_Transmission_Rack_Space_Power_Upgrade_Requirement,Power_Plant_Voltage,
	TotalIDUs_Magazines_tobe_Installed;
	private Double Transmission_Rack_Space_Required_in_Us,
	Power_Rating_in_Kw;
	private List<MwAntennaDto> MW_Antenna;
	
	public String getAdditional_Transmission_Rack_Space_Power_Upgrade_Requirement() {
		return Additional_Transmission_Rack_Space_Power_Upgrade_Requirement;
	}
	public void setAdditional_Transmission_Rack_Space_Power_Upgrade_Requirement(
			String additional_Transmission_Rack_Space_Power_Upgrade_Requirement) {
		Additional_Transmission_Rack_Space_Power_Upgrade_Requirement = additional_Transmission_Rack_Space_Power_Upgrade_Requirement;
	}
	public String getPower_Plant_Voltage() {
		return Power_Plant_Voltage;
	}
	public void setPower_Plant_Voltage(String power_Plant_Voltage) {
		Power_Plant_Voltage = power_Plant_Voltage;
	}
	public String getTotalIDUs_Magazines_tobe_Installed() {
		return TotalIDUs_Magazines_tobe_Installed;
	}
	public void setTotalIDUs_Magazines_tobe_Installed(String totalIDUs_Magazines_tobe_Installed) {
		TotalIDUs_Magazines_tobe_Installed = totalIDUs_Magazines_tobe_Installed;
	}
	public Double getTransmission_Rack_Space_Required_in_Us() {
		return Transmission_Rack_Space_Required_in_Us;
	}
	public void setTransmission_Rack_Space_Required_in_Us(Double transmission_Rack_Space_Required_in_Us) {
		Transmission_Rack_Space_Required_in_Us = transmission_Rack_Space_Required_in_Us;
	}
	public Double getPower_Rating_in_Kw() {
		return Power_Rating_in_Kw;
	}
	public void setPower_Rating_in_Kw(Double power_Rating_in_Kw) {
		Power_Rating_in_Kw = power_Rating_in_Kw;
	}
	public List<MwAntennaDto> getMW_Antenna() {
		return MW_Antenna;
	}
	public void setMW_Antenna(List<MwAntennaDto> mW_Antenna) {
		MW_Antenna = mW_Antenna;
	}
	
	
}
