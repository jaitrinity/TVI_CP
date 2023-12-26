package com.tvi.request;

public class RRU_SwapRequest {
	private int id;
	private String rruDeleteMake="",rruAddMake="";
	private String rruDeleteModel="",rruAddModel="";
	private String rruDeleteFreqBand="",rruAddFreqBand="";
	private Double rruDeletePower=0.0,rruAddPower=0.0;
	private Double rruDeleteWeight=0.0,rruAddWeight=0.0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getRruDeletePower() {
		return rruDeletePower;
	}
	public void setRruDeletePower(Double rruDeletePower) {
		this.rruDeletePower = rruDeletePower;
	}
	public Double getRruAddPower() {
		return rruAddPower;
	}
	public void setRruAddPower(Double rruAddPower) {
		this.rruAddPower = rruAddPower;
	}
	public String getRruDeleteMake() {
		return rruDeleteMake;
	}
	public void setRruDeleteMake(String rruDeleteMake) {
		this.rruDeleteMake = rruDeleteMake;
	}
	public String getRruAddMake() {
		return rruAddMake;
	}
	public void setRruAddMake(String rruAddMake) {
		this.rruAddMake = rruAddMake;
	}
	public String getRruDeleteModel() {
		return rruDeleteModel;
	}
	public void setRruDeleteModel(String rruDeleteModel) {
		this.rruDeleteModel = rruDeleteModel;
	}
	public String getRruAddModel() {
		return rruAddModel;
	}
	public void setRruAddModel(String rruAddModel) {
		this.rruAddModel = rruAddModel;
	}
	public Double getRruDeleteWeight() {
		return rruDeleteWeight;
	}
	public void setRruDeleteWeight(Double rruDeleteWeight) {
		this.rruDeleteWeight = rruDeleteWeight;
	}
	public Double getRruAddWeight() {
		return rruAddWeight;
	}
	public void setRruAddWeight(Double rruAddWeight) {
		this.rruAddWeight = rruAddWeight;
	}
	public String getRruDeleteFreqBand() {
		return rruDeleteFreqBand;
	}
	public void setRruDeleteFreqBand(String rruDeleteFreqBand) {
		this.rruDeleteFreqBand = rruDeleteFreqBand;
	}
	public String getRruAddFreqBand() {
		return rruAddFreqBand;
	}
	public void setRruAddFreqBand(String rruAddFreqBand) {
		this.rruAddFreqBand = rruAddFreqBand;
	}
	
	
	
	
}
