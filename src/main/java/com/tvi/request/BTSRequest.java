package com.tvi.request;

public class BTSRequest {
	private int id;
	private String btsType="",btsMake="",btsModel="";
	private Double btsFloorspace=0.0,btsPower=0.0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBtsType() {
		return btsType;
	}
	public void setBtsType(String btsType) {
		this.btsType = btsType;
	}
	public String getBtsMake() {
		return btsMake;
	}
	public void setBtsMake(String btsMake) {
		this.btsMake = btsMake;
	}
	public String getBtsModel() {
		return btsModel;
	}
	public void setBtsModel(String btsModel) {
		this.btsModel = btsModel;
	}
	
	public Double getBtsFloorspace() {
		return btsFloorspace;
	}
	public void setBtsFloorspace(Double btsFloorspace) {
		this.btsFloorspace = btsFloorspace;
	}
	public Double getBtsPower() {
		return btsPower;
	}
	public void setBtsPower(Double btsPower) {
		this.btsPower = btsPower;
	}
	
	
	
	
}
