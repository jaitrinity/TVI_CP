package com.tvi.dto;

public class OtherNodeDto {
	// At time SP Received
	private Integer typeNo;
	private String Feasibility,Customer_Punched_Or_Planning;
		
	private String Node_Type,Node_Location,Node_Manufacturer,Node_Model,Node_Voltage,FullRack,Remarks,Action;
	private Double Length_Mtrs,Breadth_Mtrs,Height_Mtrs,Weight_Kg,Power_Rating_in_Kw,Tx_Rack_Space_Required_In_Us;
	
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
	public String getNode_Type() {
		return Node_Type;
	}
	public void setNode_Type(String node_Type) {
		Node_Type = node_Type;
	}
	public String getNode_Location() {
		return Node_Location;
	}
	public void setNode_Location(String node_Location) {
		Node_Location = node_Location;
	}
	public String getNode_Manufacturer() {
		return Node_Manufacturer;
	}
	public void setNode_Manufacturer(String node_Manufacturer) {
		Node_Manufacturer = node_Manufacturer;
	}
	public String getNode_Model() {
		return Node_Model;
	}
	public void setNode_Model(String node_Model) {
		Node_Model = node_Model;
	}
	public String getNode_Voltage() {
		return Node_Voltage;
	}
	public void setNode_Voltage(String node_Voltage) {
		Node_Voltage = node_Voltage;
	}
	public String getFullRack() {
		return FullRack;
	}
	public void setFullRack(String fullRack) {
		FullRack = fullRack;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public Double getLength_Mtrs() {
		return Length_Mtrs;
	}
	public void setLength_Mtrs(Double length_Mtrs) {
		Length_Mtrs = length_Mtrs;
	}
	public Double getBreadth_Mtrs() {
		return Breadth_Mtrs;
	}
	public void setBreadth_Mtrs(Double breadth_Mtrs) {
		Breadth_Mtrs = breadth_Mtrs;
	}
	public Double getHeight_Mtrs() {
		return Height_Mtrs;
	}
	public void setHeight_Mtrs(Double height_Mtrs) {
		Height_Mtrs = height_Mtrs;
	}
	public Double getWeight_Kg() {
		return Weight_Kg;
	}
	public void setWeight_Kg(Double weight_Kg) {
		Weight_Kg = weight_Kg;
	}
	public Double getPower_Rating_in_Kw() {
		return Power_Rating_in_Kw;
	}
	public void setPower_Rating_in_Kw(Double power_Rating_in_Kw) {
		Power_Rating_in_Kw = power_Rating_in_Kw;
	}
	public Double getTx_Rack_Space_Required_In_Us() {
		return Tx_Rack_Space_Required_In_Us;
	}
	public void setTx_Rack_Space_Required_In_Us(Double tx_Rack_Space_Required_In_Us) {
		Tx_Rack_Space_Required_In_Us = tx_Rack_Space_Required_In_Us;
	}
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
	
	
}
