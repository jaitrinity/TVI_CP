package com.tvi.dto;

public class AdditionalInformationDto {
	private StrageticDto Stragetic;
	private String Additional_Info_If_any,Other_Additional_Info1,Other_Additional_Info2,TargetDate_DD_MM_YYYY;
	private RelocationDto Relocation;
	public StrageticDto getStragetic() {
		return Stragetic;
	}
	public void setStragetic(StrageticDto stragetic) {
		Stragetic = stragetic;
	}
	public String getAdditional_Info_If_any() {
		return Additional_Info_If_any;
	}
	public void setAdditional_Info_If_any(String additional_Info_If_any) {
		Additional_Info_If_any = additional_Info_If_any;
	}
	public String getOther_Additional_Info1() {
		return Other_Additional_Info1;
	}
	public void setOther_Additional_Info1(String other_Additional_Info1) {
		Other_Additional_Info1 = other_Additional_Info1;
	}
	public String getOther_Additional_Info2() {
		return Other_Additional_Info2;
	}
	public void setOther_Additional_Info2(String other_Additional_Info2) {
		Other_Additional_Info2 = other_Additional_Info2;
	}
	public String getTargetDate_DD_MM_YYYY() {
		return TargetDate_DD_MM_YYYY;
	}
	public void setTargetDate_DD_MM_YYYY(String targetDate_DD_MM_YYYY) {
		TargetDate_DD_MM_YYYY = targetDate_DD_MM_YYYY;
	}
	public RelocationDto getRelocation() {
		return Relocation;
	}
	public void setRelocation(RelocationDto relocation) {
		Relocation = relocation;
	}
	
	
}
