package com.tvi.dto;

public class ValidOtherEquipmentDto {
	private Integer typeNo;
	private String Feasibility;
	
	private String CustomerPunchedOrPlanning,Other_Equipment_Category,Other_Equipment_Type,Deletion_OR_Relocation,
	Target_Indus_Site_Id,Target_Request_RefNo;

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

	public String getCustomerPunchedOrPlanning() {
		return CustomerPunchedOrPlanning;
	}

	public void setCustomerPunchedOrPlanning(String customerPunchedOrPlanning) {
		CustomerPunchedOrPlanning = customerPunchedOrPlanning;
	}

	public String getOther_Equipment_Category() {
		return Other_Equipment_Category;
	}

	public void setOther_Equipment_Category(String other_Equipment_Category) {
		Other_Equipment_Category = other_Equipment_Category;
	}

	public String getOther_Equipment_Type() {
		return Other_Equipment_Type;
	}

	public void setOther_Equipment_Type(String other_Equipment_Type) {
		Other_Equipment_Type = other_Equipment_Type;
	}

	public String getDeletion_OR_Relocation() {
		return Deletion_OR_Relocation;
	}

	public void setDeletion_OR_Relocation(String deletion_OR_Relocation) {
		Deletion_OR_Relocation = deletion_OR_Relocation;
	}

	public String getTarget_Indus_Site_Id() {
		return Target_Indus_Site_Id;
	}

	public void setTarget_Indus_Site_Id(String target_Indus_Site_Id) {
		Target_Indus_Site_Id = target_Indus_Site_Id;
	}

	public String getTarget_Request_RefNo() {
		return Target_Request_RefNo;
	}

	public void setTarget_Request_RefNo(String target_Request_RefNo) {
		Target_Request_RefNo = target_Request_RefNo;
	}
	
	
}
