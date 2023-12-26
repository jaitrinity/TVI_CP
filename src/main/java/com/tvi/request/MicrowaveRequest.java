package com.tvi.request;

public class MicrowaveRequest {
	private int id;
	private String make="",model="";
	private Double microwaveHeight=0.0,dia=0.0,microwaveAzimuth=0.0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getMicrowaveHeight() {
		return microwaveHeight;
	}
	public void setMicrowaveHeight(Double microwaveHeight) {
		this.microwaveHeight = microwaveHeight;
	}
	public Double getDia() {
		return dia;
	}
	public void setDia(Double dia) {
		this.dia = dia;
	}
	public Double getMicrowaveAzimuth() {
		return microwaveAzimuth;
	}
	public void setMicrowaveAzimuth(Double microwaveAzimuth) {
		this.microwaveAzimuth = microwaveAzimuth;
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
	
	
}
