package com.tvi.upgrade.dto;

import java.util.List;

public class BtsDto {
	private Double config_type_1,config_type_2,config_type_3,Config_Carriers;
	private List<BtsCabinetDto> BTS_Cabinet;
	public Double getConfig_type_1() {
		return config_type_1;
	}
	public void setConfig_type_1(Double config_type_1) {
		this.config_type_1 = config_type_1;
	}
	public Double getConfig_type_2() {
		return config_type_2;
	}
	public void setConfig_type_2(Double config_type_2) {
		this.config_type_2 = config_type_2;
	}
	public Double getConfig_type_3() {
		return config_type_3;
	}
	public void setConfig_type_3(Double config_type_3) {
		this.config_type_3 = config_type_3;
	}
	public Double getConfig_Carriers() {
		return Config_Carriers;
	}
	public void setConfig_Carriers(Double config_Carriers) {
		Config_Carriers = config_Carriers;
	}
	public List<BtsCabinetDto> getBTS_Cabinet() {
		return BTS_Cabinet;
	}
	public void setBTS_Cabinet(List<BtsCabinetDto> bTS_Cabinet) {
		BTS_Cabinet = bTS_Cabinet;
	}
	
	
}
