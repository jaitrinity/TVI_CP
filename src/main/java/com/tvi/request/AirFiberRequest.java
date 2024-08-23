package com.tvi.request;

public class AirFiberRequest {
	private int id;
	private String make,model,dimensions,band,technology,mcb,equipPlacement;
	private Double weight,load;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	public String getBand() {
		return band;
	}
	public void setBand(String band) {
		this.band = band;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getMcb() {
		return mcb;
	}
	public void setMcb(String mcb) {
		this.mcb = mcb;
	}
	public String getEquipPlacement() {
		return equipPlacement;
	}
	public void setEquipPlacement(String equipPlacement) {
		this.equipPlacement = equipPlacement;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getLoad() {
		return load;
	}
	public void setLoad(Double load) {
		this.load = load;
	}
	
	
}
