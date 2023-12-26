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
@Table(name="NBS_MASTER_DET")
public class NbsMasterDetEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="DET_ID")
	private Integer detId;
	
	@Column(name="SR_NUMBER")
	private String srNumber;
	
	@Column(name="Type_No")
	private Integer typeNo;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="GSM_ANTENNA_HEIGHT")
	private Double gsmAntennaHeight;
	
	@Column(name="MICROWAVE_Make")
	private String microwaveMake;
	
	@Column(name="MICROWAVE_Model")
	private String microwaveModel;
	
	@Column(name="MICROWAVE_HEIGHT")
	private Double microwaveHeight;
	
	@Column(name="MICROWAVE_DIA")
	private Double microwaveDia;
	
	@Column(name="MICROWAVE_AZIMUTH")
	private Double microwaveAzimuth;
	
	@Column(name="RRU_MAKE")
	private String rruMake;
	
	@Column(name="RRU_MODEL")
	private String rruModel;
	
	@Column(name="RRU_Frequency_Band")
	private String rruFreqBand;
	
	@Column(name="RRU_RATED_POWER_CONSUMPTION")
	private Double rruPowerConsumption;
	
	@Column(name="RRU_WEIGHT")
	private Double rruWeight;
	
	@Column(name="BTS_TYPE")
	private String btsType;
	
	@Column(name="BTS_MAKE")
	private String btsMake;
	
	@Column(name="BTS_MODEL")
	private String btsModel;
	
	@Column(name="BTS_FLOOR_SPACE")
	private Double btsFloorspace;
	
	@Column(name="BTS_POWER")
	private Double btsPower;
	
	@Column(name="BBU_MAKE")
	private String bbuMake;
	
	@Column(name="BBU_MODEL")
	private String bbuModel;
	
	@Column(name="BBU_RATED_POWER_CONSUMPTION")
	private Double bbuPowerConsumption;
	
	@Column(name="BBU_Positioning")
	private String bbuPositioning;
	
	@Column(name="RF_SIZE")
	private Double rfSize;
	
	@Column(name="RF_WEIGHT")
	private Double rfWeight;
	
	@Column(name="RF_AZIMUTH")
	private Double rfAzimuth;
	
	@Column(name="RF_MAKE")
	private String rfMake;
	
	@Column(name="RF_MODEL")
	private String rfModel;
	
	@Column(name="RF_RATED_POWER")
	private Double rfRatedPower;
	
	@Column(name="RF_RATED_POWER_CONSUMPTION")
	private Double rfRatedPowerConsumption;
	
	@Column(name="RF_GAIN")
	private Double rfGain;
	
	@Column(name="RF_BAND")
	private String rfBand;
	
	@Column(name="MCB_RATING")
	private String mcbRating;
	
	@Column(name="ODSC_MAKE")
	private String odscMake;
	
	@Column(name="ODSC_MODEL")
	private String odscModel;
	
	@Column(name="MIMO_Antenna_Weight")
	private Double mimoWeight;
	
	@Column(name="MIMO_Antenna_Area")
	private Double mimoArea;
	
	@Column(name="MIMO_Antenna_Make")
	private String mimoMake;
	
	@Column(name="MIMO_Antenna_Model")
	private String mimoModel;
	
	@Column(name="MIMO_Antenna_Power")
	private Double mimoPower;
	
	@Column(name="MIMO_Antenna_Uspace")
	private Double mimoUspace;	
	
	@Column(name="IP55_Power")
	private Double ip55Power;	
	
	@Column(name="RRU_Delete_Make")
	private String rruDeleteMake;
	
	@Column(name="RRU_Delete_Model")
	private String rruDeleteModel;
	
	@Column(name="RRU_Delete_Frequency_Band")
	private String rruDeleteFreqBand;
	
	@Column(name="RRU_Delete_Power")
	private Double rruDeletePower;	
	
	@Column(name="RRU_Delete_Weight")
	private Double rruDeleteWeight;
	
	@Column(name="RRU_Add_Make")
	private String rruAddMake;
	
	@Column(name="RRU_Add_Model")
	private String rruAddModel;
	
	@Column(name="RRU_Add_Frequency_Band")
	private String rruAddFreqBand;
	
	@Column(name="RRU_Add_Power")
	private Double rruAddPower;	
	
	@Column(name="RRU_Add_Weight")
	private Double rruAddWeight;
	
	@Column(name="Pole_Height")
	private Double poleHeight;	
	
	@Column(name="Pole_Weight")
	private Double poleWeight;
	
	@Column(name="Type_of_HPSC_Antenna")
	private String typeofHpscAntenna;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name="UPDATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	public Integer getDetId() {
		return detId;
	}

	public void setDetId(Integer detId) {
		this.detId = detId;
	}

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getGsmAntennaHeight() {
		return gsmAntennaHeight;
	}

	public void setGsmAntennaHeight(Double gsmAntennaHeight) {
		this.gsmAntennaHeight = gsmAntennaHeight;
	}

	public Double getMicrowaveHeight() {
		return microwaveHeight;
	}

	public void setMicrowaveHeight(Double microwaveHeight) {
		this.microwaveHeight = microwaveHeight;
	}

	public Double getMicrowaveDia() {
		return microwaveDia;
	}

	public void setMicrowaveDia(Double microwaveDia) {
		this.microwaveDia = microwaveDia;
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

	public String getBtsType() {
		return btsType;
	}

	public void setBtsType(String btsType) {
		this.btsType = btsType;
	}

	public String getBtsMake() {
		return btsMake;
	}

	public void setBtsMake(String btsMake) {
		this.btsMake = btsMake;
	}

	public String getBtsModel() {
		return btsModel;
	}

	public void setBtsModel(String btsModel) {
		this.btsModel = btsModel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Double getMicrowaveAzimuth() {
		return microwaveAzimuth;
	}

	public void setMicrowaveAzimuth(Double microwaveAzimuth) {
		this.microwaveAzimuth = microwaveAzimuth;
	}

	public String getRruModel() {
		return rruModel;
	}

	public void setRruModel(String rruModel) {
		this.rruModel = rruModel;
	}

	public Double getBtsFloorspace() {
		return btsFloorspace;
	}

	public void setBtsFloorspace(Double btsFloorspace) {
		this.btsFloorspace = btsFloorspace;
	}

	public Double getBtsPower() {
		return btsPower;
	}

	public void setBtsPower(Double btsPower) {
		this.btsPower = btsPower;
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


	public Double getRfRatedPowerConsumption() {
		return rfRatedPowerConsumption;
	}

	public void setRfRatedPowerConsumption(Double rfRatedPowerConsumption) {
		this.rfRatedPowerConsumption = rfRatedPowerConsumption;
	}
	
	

	public Double getRfWeight() {
		return rfWeight;
	}

	public void setRfWeight(Double rfWeight) {
		this.rfWeight = rfWeight;
	}

	public Double getRfAzimuth() {
		return rfAzimuth;
	}

	public void setRfAzimuth(Double rfAzimuth) {
		this.rfAzimuth = rfAzimuth;
	}

	public Double getRfRatedPower() {
		return rfRatedPower;
	}

	public void setRfRatedPower(Double rfRatedPower) {
		this.rfRatedPower = rfRatedPower;
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

	public String getMcbRating() {
		return mcbRating;
	}

	public void setMcbRating(String mcbRating) {
		this.mcbRating = mcbRating;
	}

	public String getOdscMake() {
		return odscMake;
	}

	public void setOdscMake(String odscMake) {
		this.odscMake = odscMake;
	}

	public String getOdscModel() {
		return odscModel;
	}

	public void setOdscModel(String odscModel) {
		this.odscModel = odscModel;
	}

	public Double getRruPowerConsumption() {
		return rruPowerConsumption;
	}

	public void setRruPowerConsumption(Double rruPowerConsumption) {
		this.rruPowerConsumption = rruPowerConsumption;
	}

	public Double getMimoWeight() {
		return mimoWeight;
	}

	public void setMimoWeight(Double mimoWeight) {
		this.mimoWeight = mimoWeight;
	}

	public Double getMimoArea() {
		return mimoArea;
	}

	public void setMimoArea(Double mimoArea) {
		this.mimoArea = mimoArea;
	}

	public String getMimoMake() {
		return mimoMake;
	}

	public void setMimoMake(String mimoMake) {
		this.mimoMake = mimoMake;
	}

	public String getMimoModel() {
		return mimoModel;
	}

	public void setMimoModel(String mimoModel) {
		this.mimoModel = mimoModel;
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

	public Double getIp55Power() {
		return ip55Power;
	}

	public void setIp55Power(Double ip55Power) {
		this.ip55Power = ip55Power;
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

	public String getRruDeleteModel() {
		return rruDeleteModel;
	}

	public void setRruDeleteModel(String rruDeleteModel) {
		this.rruDeleteModel = rruDeleteModel;
	}

	public Double getRruDeleteWeight() {
		return rruDeleteWeight;
	}

	public void setRruDeleteWeight(Double rruDeleteWeight) {
		this.rruDeleteWeight = rruDeleteWeight;
	}

	public String getRruAddMake() {
		return rruAddMake;
	}

	public void setRruAddMake(String rruAddMake) {
		this.rruAddMake = rruAddMake;
	}

	public String getRruAddModel() {
		return rruAddModel;
	}

	public void setRruAddModel(String rruAddModel) {
		this.rruAddModel = rruAddModel;
	}

	public Double getRruAddWeight() {
		return rruAddWeight;
	}

	public void setRruAddWeight(Double rruAddWeight) {
		this.rruAddWeight = rruAddWeight;
	}

	public Integer getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(Integer typeNo) {
		this.typeNo = typeNo;
	}

	public String getBbuPositioning() {
		return bbuPositioning;
	}

	public void setBbuPositioning(String bbuPositioning) {
		this.bbuPositioning = bbuPositioning;
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

	public String getTypeofHpscAntenna() {
		return typeofHpscAntenna;
	}

	public void setTypeofHpscAntenna(String typeofHpscAntenna) {
		this.typeofHpscAntenna = typeofHpscAntenna;
	}

	public String getRruFreqBand() {
		return rruFreqBand;
	}

	public void setRruFreqBand(String rruFreqBand) {
		this.rruFreqBand = rruFreqBand;
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

	public String getMicrowaveMake() {
		return microwaveMake;
	}

	public void setMicrowaveMake(String microwaveMake) {
		this.microwaveMake = microwaveMake;
	}

	public String getMicrowaveModel() {
		return microwaveModel;
	}

	public void setMicrowaveModel(String microwaveModel) {
		this.microwaveModel = microwaveModel;
	}	
	
	
}
