package com.tvi.request;

public class PoleRequest {
	private int id;
	private Double poleHeight=0.0;
	private Double poleWeight=0.0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getPoleHeight() {
		return poleHeight;
	}
	public void setPoleHeight(Double poleHeight) {
		this.poleHeight = poleHeight;
	}
	public Double getPoleWeight() {
		return poleWeight;
	}
	public void setPoleWeight(Double poleWeight) {
		this.poleWeight = poleWeight;
	}
}
