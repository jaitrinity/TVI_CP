package com.tvi.request;

public class BBURequest {
	private int id;
	private String bbuMake="",bbuModel="",bbuPositioning="";
	private Double bbuPowerConsumption=0.0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBbuMake() {
		return bbuMake;
	}
	public void setBbuMake(String bbuMake) {
		this.bbuMake = bbuMake;
	}
	public String getBbuModel() {
		return bbuModel;
	}
	public void setBbuModel(String bbuModel) {
		this.bbuModel = bbuModel;
	}
	public Double getBbuPowerConsumption() {
		return bbuPowerConsumption;
	}
	public void setBbuPowerConsumption(Double bbuPowerConsumption) {
		this.bbuPowerConsumption = bbuPowerConsumption;
	}
	public String getBbuPositioning() {
		return bbuPositioning;
	}
	public void setBbuPositioning(String bbuPositioning) {
		this.bbuPositioning = bbuPositioning;
	}
	
	
	
}
