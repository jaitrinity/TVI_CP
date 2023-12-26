package com.tvi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Complain")
public class ComplainEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="Complain_Id")
	private Integer complainId;
	
	@Column(name="SR_Number")
	private String srNumber;
	
	@Column(name="Raise_Description")
	private String raiseDescription;
	
	@Column(name="Raise_By")
	private String raiseBy;
	
	@Column(name="Raise_Date")
	@Temporal(TemporalType.DATE)
	private Date raiseDate;
	
	@Column(name="Image")
	private String image;
	
	@Column(name="Close_Description")
	private String closeDescription;

	@Column(name="Close_By")
	private String closeBy;
	
	@Column(name="Close_Date")
	@Temporal(TemporalType.DATE)
	private Date closeDate;
	
	@Column(name="Status")
	private String status;

	public Integer getComplainId() {
		return complainId;
	}

	public void setComplainId(Integer complainId) {
		this.complainId = complainId;
	}

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}

	public String getRaiseDescription() {
		return raiseDescription;
	}

	public void setRaiseDescription(String raiseDescription) {
		this.raiseDescription = raiseDescription;
	}

	public String getRaiseBy() {
		return raiseBy;
	}

	public void setRaiseBy(String raiseBy) {
		this.raiseBy = raiseBy;
	}

	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCloseDescription() {
		return closeDescription;
	}

	public void setCloseDescription(String closeDescription) {
		this.closeDescription = closeDescription;
	}

	public String getCloseBy() {
		return closeBy;
	}

	public void setCloseBy(String closeBy) {
		this.closeBy = closeBy;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getRaiseDate() {
		return raiseDate;
	}

	public void setRaiseDate(Date raiseDate) {
		this.raiseDate = raiseDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	
	
	
	
	
	

}
