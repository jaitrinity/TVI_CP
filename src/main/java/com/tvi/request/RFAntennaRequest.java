package com.tvi.request;

public class RFAntennaRequest {
	private int id;
	private String rfMake="",rfModel="",rfBand="";
	private Double rfSize=0.0,rfGain=0.0,rfWeight=0.0,rfRatedPower=0.0,rfAzimuth=0.0,rfRatedPowerConsumption=0.0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Double getRfSize() {
		return rfSize;
	}
	public void setRfSize(Double rfSize) {
		this.rfSize = rfSize;
	}
	public String getRfMake() {
		return rfMake;
	}
	public void setRfMake(String rfMake) {
		this.rfMake = rfMake;
	}
	public String getRfModel() {
		return rfModel;
	}
	public void setRfModel(String rfModel) {
		this.rfModel = rfModel;
	}
	
	public Double getRfGain() {
		return rfGain;
	}
	public void setRfGain(Double rfGain) {
		this.rfGain = rfGain;
	}
	public String getRfBand() {
		return rfBand;
	}
	public void setRfBand(String rfBand) {
		this.rfBand = rfBand;
	}
	public Double getRfWeight() {
		return rfWeight;
	}
	public void setRfWeight(Double rfWeight) {
		this.rfWeight = rfWeight;
	}
	public Double getRfRatedPower() {
		return rfRatedPower;
	}
	public void setRfRatedPower(Double rfRatedPower) {
		this.rfRatedPower = rfRatedPower;
	}
	public Double getRfAzimuth() {
		return rfAzimuth;
	}
	public void setRfAzimuth(Double rfAzimuth) {
		this.rfAzimuth = rfAzimuth;
	}
	public Double getRfRatedPowerConsumption() {
		return rfRatedPowerConsumption;
	}
	public void setRfRatedPowerConsumption(Double rfRatedPowerConsumption) {
		this.rfRatedPowerConsumption = rfRatedPowerConsumption;
	}

	
	
	
}
