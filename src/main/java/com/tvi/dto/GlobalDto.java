package com.tvi.dto;

public class GlobalDto {
	// At time SP Received
	private String Request_Ref_No,Circle,Network_Type;
	
	private String Remarks;
	

	public String getRequest_Ref_No() {
		return Request_Ref_No;
	}

	public void setRequest_Ref_No(String request_Ref_No) {
		Request_Ref_No = request_Ref_No;
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

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	
	
}
