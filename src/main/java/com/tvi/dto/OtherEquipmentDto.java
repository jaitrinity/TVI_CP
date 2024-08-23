package com.tvi.dto;

public class OtherEquipmentDto {
	/*private String Other_Equipment;

	public String getOther_Equipment() {
		return Other_Equipment;
	}

	public void setOther_Equipment(String other_Equipment) {
		Other_Equipment = other_Equipment;
	}*/
	
	private Integer typeNo;
	private String Action,Feasibility,CustomerPunchedOrPlanning,Deletion_OR_Relocation;
	
	private String Source_Request_RefNo,Other_Equipment_Category,Other_Equipment_Type,Equipment_to_be_relocated,
	Target_Indus_Site_Id,Target_Request_RefNo;

	public Integer getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(Integer typeNo) {
		this.typeNo = typeNo;
	}

	public String getAction() {
		return Action;
	}

	public void setAction(String action) {
		Action = action;
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

	public String getDeletion_OR_Relocation() {
		return Deletion_OR_Relocation;
	}

	public void setDeletion_OR_Relocation(String deletion_OR_Relocation) {
		Deletion_OR_Relocation = deletion_OR_Relocation;
	}

	public String getSource_Request_RefNo() {
		return Source_Request_RefNo;
	}

	public void setSource_Request_RefNo(String source_Request_RefNo) {
		Source_Request_RefNo = source_Request_RefNo;
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

	public String getEquipment_to_be_relocated() {
		return Equipment_to_be_relocated;
	}

	public void setEquipment_to_be_relocated(String equipment_to_be_relocated) {
		Equipment_to_be_relocated = equipment_to_be_relocated;
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
