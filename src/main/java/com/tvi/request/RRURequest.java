package com.tvi.request;

public class RRURequest {
	private int id;
	private String rruMake="",rruModel="",rruFreqBand="";
	private Double rruPowerConsumption=0.0,rruWeight=0.0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRruMake() {
		return rruMake;
	}
	public void setRruMake(String rruMake) {
		this.rruMake = rruMake;
	}
	public Double getRruWeight() {
		return rruWeight;
	}
	public void setRruWeight(Double rruWeight) {
		this.rruWeight = rruWeight;
	}
	public String getRruModel() {
		return rruModel;
	}
	public void setRruModel(String rruModel) {
		this.rruModel = rruModel;
	}
	public Double getRruPowerConsumption() {
		return rruPowerConsumption;
	}
	public void setRruPowerConsumption(Double rruPowerConsumption) {
		this.rruPowerConsumption = rruPowerConsumption;
	}
	public String getRruFreqBand() {
		return rruFreqBand;
	}
	public void setRruFreqBand(String rruFreqBand) {
		this.rruFreqBand = rruFreqBand;
	}
	
	
}
