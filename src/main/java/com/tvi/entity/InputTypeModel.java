package com.tvi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Input_Type")
public class InputTypeModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="Type_Id")
	private Integer typeId;
	
	@Column(name="Type")
	private String type;

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	

}
