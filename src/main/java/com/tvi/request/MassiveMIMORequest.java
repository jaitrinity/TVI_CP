package com.tvi.request;

public class MassiveMIMORequest {
	private int id;
	private Double mimoAntennaWeight=0.0,mimoAntennaArea=0.0,mimoPower=0.0,mimoUspace=0.0;
	private String mimoAntennaMake="",mimoAntennaModel="";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Double getMimoAntennaWeight() {
		return mimoAntennaWeight;
	}
	public void setMimoAntennaWeight(Double mimoAntennaWeight) {
		this.mimoAntennaWeight = mimoAntennaWeight;
	}
	public Double getMimoAntennaArea() {
		return mimoAntennaArea;
	}
	public void setMimoAntennaArea(Double mimoAntennaArea) {
		this.mimoAntennaArea = mimoAntennaArea;
	}
	public String getMimoAntennaMake() {
		return mimoAntennaMake;
	}
	public void setMimoAntennaMake(String mimoAntennaMake) {
		this.mimoAntennaMake = mimoAntennaMake;
	}
	public String getMimoAntennaModel() {
		return mimoAntennaModel;
	}
	public void setMimoAntennaModel(String mimoAntennaModel) {
		this.mimoAntennaModel = mimoAntennaModel;
	}
	public Double getMimoPower() {
		return mimoPower;
	}
	public void setMimoPower(Double mimoPower) {
		this.mimoPower = mimoPower;
	}
	public Double getMimoUspace() {
		return mimoUspace;
	}
	public void setMimoUspace(Double mimoUspace) {
		this.mimoUspace = mimoUspace;
	}

	
}
