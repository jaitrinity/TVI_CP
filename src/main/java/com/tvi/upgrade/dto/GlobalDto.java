package com.tvi.upgrade.dto;

public class GlobalDto {
	private String Circle,Network_Type;
	
	private String Operator,Request_for_Network_Type,Activity_Type,Request_Ref_No,Remarks,TOCO_Site_ID,Project_Name,RL_Type,
	Upgrade_Type;
	private RequestedEquipmentDto Requested_Equipment;
	public String getOperator() {
		return Operator;
	}
	public void setOperator(String operator) {
		Operator = operator;
	}
	public String getRequest_for_Network_Type() {
		return Request_for_Network_Type;
	}
	public void setRequest_for_Network_Type(String request_for_Network_Type) {
		Request_for_Network_Type = request_for_Network_Type;
	}
	public String getActivity_Type() {
		return Activity_Type;
	}
	public void setActivity_Type(String activity_Type) {
		Activity_Type = activity_Type;
	}
	public String getRequest_Ref_No() {
		return Request_Ref_No;
	}
	public void setRequest_Ref_No(String request_Ref_No) {
		Request_Ref_No = request_Ref_No;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getTOCO_Site_ID() {
		return TOCO_Site_ID;
	}
	public void setTOCO_Site_ID(String tOCO_Site_ID) {
		TOCO_Site_ID = tOCO_Site_ID;
	}
	public String getProject_Name() {
		return Project_Name;
	}
	public void setProject_Name(String project_Name) {
		Project_Name = project_Name;
	}
	public String getRL_Type() {
		return RL_Type;
	}
	public void setRL_Type(String rL_Type) {
		RL_Type = rL_Type;
	}
	public RequestedEquipmentDto getRequested_Equipment() {
		return Requested_Equipment;
	}
	public void setRequested_Equipment(RequestedEquipmentDto requested_Equipment) {
		Requested_Equipment = requested_Equipment;
	}
	public String getCircle() {
		return Circle;
	}
	public void setCircle(String circle) {
		Circle = circle;
	}
	public String getNetwork_Type() {
		return Network_Type;
	}
	public void setNetwork_Type(String network_Type) {
		Network_Type = network_Type;
	}
	public String getUpgrade_Type() {
		return Upgrade_Type;
	}
	public void setUpgrade_Type(String upgrade_Type) {
		Upgrade_Type = upgrade_Type;
	}
}
