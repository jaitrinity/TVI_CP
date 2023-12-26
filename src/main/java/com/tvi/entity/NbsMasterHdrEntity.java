package com.tvi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="NBS_MASTER_HDR")
public class NbsMasterHdrEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SR_NUMBER")
	private String srNumber;
	
	@Column(name="SP_NUMBER")
	private String spNumber;
	
	@Column(name="SO_NUMBER")
	private String soNumber;
	
	@Column(name="Operator")
	private String operator;
	
	@Column(name="RFI_DATE")
	@Temporal(TemporalType.DATE)
	private Date rfiDate;
	
	@Column(name="RFI_Doc_Attachement")
	private String rfiDocAttachment;
	
	@Column(name="AT_Doc_Attachement")
	private String atDocAttachment;
	
	@Column(name="RFI_ACCEPTED_DATE")
	@Temporal(TemporalType.DATE)
	private Date rfiAcceptedDate;
	
	@Column(name="RFS_DATE")
	@Temporal(TemporalType.DATE)
	private Date rfsDate;
	
	@Column(name="SR_DATE")
	@Temporal(TemporalType.DATE)
	private Date srDate;
	
	@Column(name="SP_DATE")
	@Temporal(TemporalType.DATE)
	private Date spDate;
	
	@Column(name="SO_DATE")
	@Temporal(TemporalType.DATE)
	private Date soDate;

	@Column(name="TAB_NAME")
	private String tabName;
	
	/*@Column(name="OPCO_ID")
	private String opcoId;*/
	
	@Column(name="LATITUDE_1")
	private Double latitude1;
	
	@Column(name="LONGITUDE_1")
	private Double longitude1;

	@Column(name="LATITUDE_2")
	private Double latitude2;
	
	@Column(name="LONGITUDE_2")
	private Double longitude2;
	
	@Column(name="SUGGESTED_LATITUDE")
	private Double suggestedLatitude;
	
	@Column(name="SUGGESTED_LONGITUDE")
	private Double suggestedLongitude;
	
	@Column(name="CIRCLE_NAME")
	private String circleName;
	
	@Column(name="TVI_SITE_ID")
	private String tviSiteId;
	
	@Column(name="Site_Name")
	private String siteName;
	
	@Column(name="AIRTEL_SITE_ID")
	private String airtelSiteId;
	
	@Column(name="SITE_ADDRESS")
	private String siteAddress;
	
	@Column(name="SUGGESTED_SITE_ADDRESS")
	private String suggestedSiteAddress;
	
	/*@Column(name="CITY")
	private String city;*/
	
	@Column(name="SUGGESTED_CITY")
	private String suggestedCity;
	
	@Column(name="AIRTEL_LOCATOR_ID")
	private String airtelLocatorId;
	
	@Column(name="STATE")
	private String state;
	
	@Column(name="SUGGESTED_STATE")
	private String suggestedState;
	
	@Column(name="DISTRICT")
	private String district;
	
	@Column(name="SUGGESTED_DISTRICT")
	private String suggestedDistrict;
	
	@Column(name="SITE_TYPE")
	private String siteType;
	
	@Column(name="BTS_TYPE")
	private String btsType;
	
	@Column(name="SUGGESTED_SITE_TYPE")
	private String suggestedSiteType;
	
	@Column(name="SUGGESTED_EB_AVAILABLILITY_DISTANCE")
	private Double suggestedEbAvailablilityDistance;
	
	@Column(name="Suggested_DG_Availability")
	private String suggestedDgAvailability;
	
	@Column(name="Suggested_Tower_Type")
	private String suggestedTowerType;
	
	@Column(name="SUGGESTED_TOWER_HEIGHT")
	private Double suggestedTowerHeight;
	
	@Column(name="Suggested_Standard_IPFEE")
	private Integer suggestedStandardIPFEE;
	
	@Column(name="Suggested_LLR")
	private Integer suggestedLLR;
	
	@Column(name="City_Premium_Percentage")
	private Integer cityPremiumPercentage;
	
	@Column(name="City_Class")
	private String cityClass;
	
	@Column(name="Suggested_City_Premium")
	private Double suggestedCityPremium;
	
	@Column(name="Suggested_Loading")
	private Integer suggestedLoading;
	
	/*@Column(name="CITY_VILLAGE")
	private String cityVillage;*/
	
	/*@Column(name="TOWN_VILLAGE")
	private String townVillage;*/
	
	@Column(name="SUGGESTED_TOWN_VILLAGE")
	private String suggestedTownVillage;
	
	@Column(name="Suggested_Municipal_Tax")
	private String suggestedMunicipalTax;
	
	@Column(name="Suggested_Property_Tax")
	private String suggestedPropertyTax;
	
	@Column(name="Suggested_Wind_Speed")
	private String suggestedWindSpeed;
	
	@Column(name="Suggested_Lock_Term")
	private String suggestedLockTerm;
	
	@Column(name="TECHNOLOGY")
	private String technology;
	
	@Column(name="ExtensionType")
	private String extensionType;
	
	/*@Column(name="TOWER_HEIGHT")
	private Double towerHeight;*/
	
	@Column(name="AGL_REQUIRED")
	private Double aglRequired;
	
	@Column(name="FREQUENCY_BAND_USED_BY_OPCO")
	private String frequencyUserByOpco;
	
	/*@Column(name="EB_AVAILABILITY")
	private String ebAvailability;*/
	
	@Column(name="EB_Availability_Distance")
	private Double ebAvailabilityDistance;
	
	@Column(name="DG_AVAILABILITY")
	private String dgAvailability;
	
	@Column(name="FIBER_TERMINATION")
	private String fiberTermination;
	
	/*@Column(name="PINCODE")
	private Integer pincode;*/
	
	@Column(name="SUGGESTED_PINCODE")
	private Integer suggestedPincode;
	
	@Column(name="WEIGHT_OF_ADDITIONAL_ANTENNA")
	private Integer weightOfAdditionalAntenna;
	
	/*@Column(name="RF_ANTENNA_MOUNT_HEIGHT")
	private Integer rfAntennaMountHeight;*/
	
	/*@Column(name="MW_ANTENNA_MOUNT_HEIGHT")
	private Integer mwAntennaMountHeight;*/
	
	@Column(name="NO_OF_RF_ANTENNA")
	private Integer noOfRFAntenna;
	
	@Column(name="NO_OF_RF_ANTENNA_Delete")
	private Integer noOfRFAntenna_Delete;
	
	@Column(name="NO_OF_RF_ANTENNA_Add")
	private Integer noOfRFAntenna_Add;
	
	@Column(name="CLUTTER")
	private String clutter;
	
	@Column(name="SUGGESTED_CLUTTER")
	private String suggestedClutter;
	
	@Column(name="WIND_SPEED")
	private Double windSpeed;
	
	@Column(name="NO_OF_MCB")
	private Integer NO_OF_MCB;
	
	@Column(name="MCB_Required_In_AMP")
	private Integer MCB_Required_In_AMP;
	
	@Column(name="NO_OF_IP55")
	private Integer NO_OF_IP55;
	
	@Column(name="NO_OF_RRU_Delete")
	private Integer NO_OF_RRU_Delete;
	
	@Column(name="NO_OF_RRU_Add")
	private Integer NO_OF_RRU_Add;
	
	@Column(name="MW_RACK")
	private String MW_RACK;
	
	/*@Column(name="NO_OF_GSM_ANTENNA")
	private Integer noOfGsmAntenna;*/
	
	@Column(name="NO_OF_MICROWAVE")
	private Integer noOfMicrowave;
	
	@Column(name="NO_OF_MICROWAVE_Delete")
	private Integer noOfMicrowaveDelete;
	
	@Column(name="NO_OF_MICROWAVE_Add")
	private Integer noOfMicrowaveAdd;
	
	@Column(name="Fiber_Length")
	private Double fiberLength;
	
	@Column(name="NO_OF_BBU")
	private Integer noOfBbu;
	
	@Column(name="NO_OF_BBU_Delete")
	private Integer noOfBbuDelete;
	
	@Column(name="NO_OF_BBU_Add")
	private Integer noOfBbuAdd;
	
	@Column(name="NO_OF_RRU")
	private Integer noOfRru;
	
	@Column(name="NO_OF_BTS")
	private Integer noOfBts;
	
	@Column(name="Additional_Load")
	private Double additionalLoad;
	
	@Column(name="FLOOR_LENGTH")
	private Double floorLength;
	
	/*@Column(name="FLOOR_WIDTH")
	private Double floorWidth;*/
	
	/*@Column(name="FLOOR_HEIGHT")
	private Double floorHeight;*/
	
	/*@Column(name="BTS_POWER")
	private Double btsPower;*/
	
	/*@Column(name="TOTAL_POWER_CONSUMPTION")
	private Double totalPowerConsumption;*/
	
	@Column(name="POWER_CONSUMPTION")
	private Double powerConsumption;
	
	/*@Column(name="NO_OF_SPACE_REQUIRED")
	private Integer noOfSpaceRequired;*/
	
	@Column(name="NO_OF_U_SPACE_REQUIRED")
	private Integer noOfUSpaceRequired;
	
	@Column(name="Load_Of_U")
	private Double loadOfU;
	
	/*@Column(name="Existing_Airtel_configuration_before_MIMO")
	private String existingAirtelConfigurationBeforeMIMO;*/
	
	@Column(name="No_of_Massive_MIMO_Antenna")
	private Integer noOfMassiveMIMOAntenna;
	
	/*@Column(name="U_space_for_BBU")
	private Integer uSpaceForBBU;*/
	
	@Column(name="Power_requirement")
	private Integer powerRequirement;
	
	/*@Column(name="Power_Threshold_in_case_of_MIMO_Expansion")
	private Integer powerThresholdInCaseOfMIMOExpansion;*/
	
	/*@Column(name="Additional_LLR_due_to_Addition_of_MIMO")
	private Integer additionalLLRDueToAdditionalMIMO;*/
	
	/*@Column(name="Smart_Split_Type")
	private String smartSplitType;*/
	
	@Column(name="Floor_Space_Of_OD_Cabinate")
	private String floorSpaceOfODCabinet;
	
	/*@Column(name="AC_Power_Load")
	private String acPowerLoad;*/
	
	/*@Column(name="Total_Power_Required")
	private Integer totalPowerRequired;*/
	
	/*@Column(name="Cow_Type")
	private String cowType;*/
	
	/*@Column(name="Service_Order_Contract_Period")
	private String serviceContractPeriod;*/
	
	/*@Column(name="Weight_on_Tower")
	private Integer towerWeight;*/
	
	/*@Column(name="Rated_Power_Consumption")
	private Integer ratedPowerConsumption;*/
	
	/*@Column(name="Rack_space_for_BBU")
	private Integer rackSpaceForBBU;*/
	
	/*@Column(name="Rack_space_for_MW_IDU")
	private Integer rackSpaceForMW;*/
	
	/*@Column(name="Power_Supply")
	private String powerSupply;*/
	
	/*@Column(name="Additional_BTS_floor_Space")
	private Integer additionBTSFloorSpace;*/
	
	@Column(name="AGL_required_for_ODSC")
	private Integer aglRequiredODSC;
	
	/*@Column(name="AGL_required_for_MW_UBR")
	private Integer aglRequiredMW;*/
	
	@Column(name="Pole_Height")
	private Double poleHeight;
	
	/*@Column(name="Additional_Poles")
	private Integer additionalPoles;*/
	
	@Column(name="Airtel_Backhaul")
	private String airtelBackhaul;
	
	@Column(name="AC_DC_Backup")
	private String acDcBackup;
	
	@Column(name="Additional_power_back_up_of_2_Hrs")
	private String additionalPowerBackup2Hrs;
	
	@Column(name="Total_Rated_Power_in_KW")
	private Double totalRatedPower;
	
	@Column(name="Camuflauging")
	private String camuflauging;
	
	/*@Column(name="No_of_Small_Cell")
	private Integer noOfSmallCell;*/
	
	/*@Column(name="Area_per_Small_Cell")
	private Integer areaPerSmallCell;*/
	
	/*@Column(name="Depth_per_Small_cell")
	private Integer depthPerSmallCell;*/
	
	/*@Column(name="Height_of_highest_Antenna")
	private Integer heightOfHighestAntenna;*/
	
	/*@Column(name="Weight_of_Small_cell")
	private Integer weightOfSmallCell;*/
	
	/*@Column(name="Rated_Power")
	private Integer ratedPower;*/
	
	@Column(name="U_Space")
	private Integer uSpace;
	
	@Column(name="U_Space_For_E1_to_Ethernet_Converting_Unit")
	private Integer uSpace_ethernet;
	
	/*@Column(name="AC_Backup_Required_2_Hrs")
	private String acbackupRequired2Hrs;*/
	
	/*@Column(name="Additional_ODSC_Required")
	private String additionalODSCRequired;*/
	
	@Column(name="Additional_Power_required")
	private Double additionalPowerRequired;
	
	/*@Column(name="Existing_LLR_of_TVI_Site")
	private String existingLLROfTVISite;*/
	
	/*@Column(name="Additional_LLR_due_to_Addition_of_ODSC")
	private String additionalLLRDueToAdditionalODSC;*/
	
	/*@Column(name="Cumulative_PAN_India_ODSC_Sharing_SO_count")
	private String cumulativePANIndiaODCSSharingSOCount;*/
	
	@Column(name="Conversion_of_Sharing_ODSC_into_Macro_Site")
	private String conversionOfSharingODSCIntoMacroSite;
	
	@Column(name="No_of_ODSC")
	private Integer noOfODSC;
	
	@Column(name="FIBER_ENTRY")
	private String fiberEntry;
	
	@Column(name="SEAF_Attached")
	private String seafAttached;
	
	@Column(name="DDR_Attached")
	private String ddrAttached;
	
	@Column(name="Survey_Report_Attached")
	private String surveyReportAttached;
	
	@Column(name="E_SEAF_Portal_URL")
	private String eSeafPortalUrl;
	
	@Column(name="NCSO_Attached")
	private String ncsoAttached;
	
	@Column(name="Aggrement_Attached")
	private String aggrementAttached;
	
	@Column(name="Google_Snapshot_Attachment")
	private String googleSnapshotAttachment;
	
	@Column(name="NFA_Attachment")
	private String nfaAttachment;
	
	@Column(name="BOQ_Attachment")
	private String boqAttachment;
	
	/*@Column(name="HO_AQ_Attachment")
	private String hoAqAttachment;*/
	
	@Column(name="Acquisition_Attachment")
	private String acquisitionAttachment;
	
	@Column(name="RBH_Attachment")
	private String rbhAttachment;
	
	@Column(name="HO_SnM_Attachment")
	private String hoSnMAttachment;
	
	@Column(name="HO_RF_Planning_Attachment")
	private String hoRfPlanningAttachment;
	
	@Column(name="OPCO_Attachment")
	private String opcoAttachment;
	
	/*@Column(name="Circle_Operation_Head_Attachment")
	private String circleOperationHeadAttachment;*/
	
	@Column(name="Sales_Attachment")
	private String salesAttachment;
	
	@Column(name="HO_Legal_Attachment")
	private String hoLegalAttachment;
	
	@Column(name="Circle_Legal_Attachment")
	private String circleLegalAttachment;
	
	@Column(name="HO_Project_EB_Attachment")
	private String hoProjectEbAttachment;
	
	/*@Column(name="HO_Project_Head_Attachment")
	private String hoProjectHeadAttachment;*/
	
	/*@Column(name="HO_COO_Attachment")
	private String hoCooAttachment;*/
	
	@Column(name="Suggested_Deviation")
	private Double suggestedDeviation;
	
	@Column(name="Electrification_Charges")
	private Double electrificationCharges;
	
	@Column(name="MC_Charges")
	private Double mcCharges;
	
	@Column(name="Sharing_Potential")
	private String sharingPotential;
	
	@Column(name="Suggested_Land_Owner_Rent")
	private String suggestedLandOwnerRent;
	
	@Column(name="Odsc_ModelType")
	private String odscModelType;
	
	@Column(name="NO_OF_UBR_ANTENNA")
	private Integer noOfUBR_Antenna;
	
	@Column(name="Count_Of_RF_Filter")
	private Integer countOfRfFilter;
	
	@Column(name="Type_Of_Antenna")
	private String typeOfAntenna;
	
	@Column(name="No_Of_Poles")
	private Integer noOfPoles;
	
	@Column(name="No_Of_HPSC_Antenna")
	private Integer noOfHpscAntenna;
	
	@Column(name="Power_Rating_Of_Equipment")
	private Double powerRatingOfEquipment;
	
	@Column(name="Product_Type")
	private String productType;
	
	@Column(name="Additional_Battery_Bank")
	private String additionalBB;
	
	@Column(name="Head_Load")
	private String headLoad;
	
	@Column(name="EB_Connection")
	private String ebConnection;
	
	@Column(name="Total_weight_on_tower")
	private Double totalWeightOnTower;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATE_BY")
	private String createBy;
	
	@Column(name="CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name="UPDATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}

	/*public String getOpcoId() {
		return opcoId;
	}

	public void setOpcoId(String opcoId) {
		this.opcoId = opcoId;
	}*/

	public Double getLatitude1() {
		return latitude1;
	}

	public void setLatitude1(Double latitude1) {
		this.latitude1 = latitude1;
	}

	public Double getLongitude1() {
		return longitude1;
	}

	public void setLongitude1(Double longitude1) {
		this.longitude1 = longitude1;
	}

	public Double getLatitude2() {
		return latitude2;
	}

	public void setLatitude2(Double latitude2) {
		this.latitude2 = latitude2;
	}

	public Double getLongitude2() {
		return longitude2;
	}

	public void setLongitude2(Double longitude2) {
		this.longitude2 = longitude2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	/*public String getCityVillage() {
		return cityVillage;
	}

	public void setCityVillage(String cityVillage) {
		this.cityVillage = cityVillage;
	}*/

	/*public Integer getNoOfGsmAntenna() {
		return noOfGsmAntenna;
	}

	public void setNoOfGsmAntenna(Integer noOfGsmAntenna) {
		this.noOfGsmAntenna = noOfGsmAntenna;
	}*/

	public Integer getNoOfMicrowave() {
		return noOfMicrowave;
	}

	public void setNoOfMicrowave(Integer noOfMicrowave) {
		this.noOfMicrowave = noOfMicrowave;
	}

	public Integer getNoOfBbu() {
		return noOfBbu;
	}

	public void setNoOfBbu(Integer noOfBbu) {
		this.noOfBbu = noOfBbu;
	}

	public Integer getNoOfRru() {
		return noOfRru;
	}

	public void setNoOfRru(Integer noOfRru) {
		this.noOfRru = noOfRru;
	}

	public Integer getNoOfBts() {
		return noOfBts;
	}

	public void setNoOfBts(Integer noOfBts) {
		this.noOfBts = noOfBts;
	}

	public Double getFloorLength() {
		return floorLength;
	}

	public void setFloorLength(Double floorLength) {
		this.floorLength = floorLength;
	}

	/*public Double getFloorWidth() {
		return floorWidth;
	}

	public void setFloorWidth(Double floorWidth) {
		this.floorWidth = floorWidth;
	}*/

	/*public Double getFloorHeight() {
		return floorHeight;
	}

	public void setFloorHeight(Double floorHeight) {
		this.floorHeight = floorHeight;
	}*/

	/*public Double getBtsPower() {
		return btsPower;
	}

	public void setBtsPower(Double btsPower) {
		this.btsPower = btsPower;
	}*/

	/*public Double getTotalPowerConsumption() {
		return totalPowerConsumption;
	}

	public void setTotalPowerConsumption(Double totalPowerConsumption) {
		this.totalPowerConsumption = totalPowerConsumption;
	}*/

	/*public Integer getNoOfSpaceRequired() {
		return noOfSpaceRequired;
	}

	public void setNoOfSpaceRequired(Integer noOfSpaceRequired) {
		this.noOfSpaceRequired = noOfSpaceRequired;
	}*/

	public String getFiberEntry() {
		return fiberEntry;
	}

	public void setFiberEntry(String fiberEntry) {
		this.fiberEntry = fiberEntry;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getSpNumber() {
		return spNumber;
	}

	public void setSpNumber(String spNumber) {
		this.spNumber = spNumber;
	}

	public String getSoNumber() {
		return soNumber;
	}

	public void setSoNumber(String soNumber) {
		this.soNumber = soNumber;
	}

	public Double getSuggestedLatitude() {
		return suggestedLatitude;
	}

	public void setSuggestedLatitude(Double suggestedLatitude) {
		this.suggestedLatitude = suggestedLatitude;
	}

	public Double getSuggestedLongitude() {
		return suggestedLongitude;
	}

	public void setSuggestedLongitude(Double suggestedLongitude) {
		this.suggestedLongitude = suggestedLongitude;
	}

	public Date getRfiDate() {
		return rfiDate;
	}

	public void setRfiDate(Date rfiDate) {
		this.rfiDate = rfiDate;
	}

	public Date getRfsDate() {
		return rfsDate;
	}

	public void setRfsDate(Date rfsDate) {
		this.rfsDate = rfsDate;
	}

	public String getTviSiteId() {
		return tviSiteId;
	}

	public void setTviSiteId(String tviSiteId) {
		this.tviSiteId = tviSiteId;
	}

	public String getAirtelSiteId() {
		return airtelSiteId;
	}

	public void setAirtelSiteId(String airtelSiteId) {
		this.airtelSiteId = airtelSiteId;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}

	public String getAirtelLocatorId() {
		return airtelLocatorId;
	}

	public void setAirtelLocatorId(String airtelLocatorId) {
		this.airtelLocatorId = airtelLocatorId;
	}

	/*public String getTownVillage() {
		return townVillage;
	}

	public void setTownVillage(String townVillage) {
		this.townVillage = townVillage;
	}*/

	public String getFrequencyUserByOpco() {
		return frequencyUserByOpco;
	}

	public void setFrequencyUserByOpco(String frequencyUserByOpco) {
		this.frequencyUserByOpco = frequencyUserByOpco;
	}

	/*public String getEbAvailability() {
		return ebAvailability;
	}

	public void setEbAvailability(String ebAvailability) {
		this.ebAvailability = ebAvailability;
	}*/

	public String getDgAvailability() {
		return dgAvailability;
	}

	public void setDgAvailability(String dgAvailability) {
		this.dgAvailability = dgAvailability;
	}

	public String getFiberTermination() {
		return fiberTermination;
	}

	public void setFiberTermination(String fiberTermination) {
		this.fiberTermination = fiberTermination;
	}

	/*public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}*/

	public Integer getWeightOfAdditionalAntenna() {
		return weightOfAdditionalAntenna;
	}

	public void setWeightOfAdditionalAntenna(Integer weightOfAdditionalAntenna) {
		this.weightOfAdditionalAntenna = weightOfAdditionalAntenna;
	}

	/*public Integer getRfAntennaMountHeight() {
		return rfAntennaMountHeight;
	}

	public void setRfAntennaMountHeight(Integer rfAntennaMountHeight) {
		this.rfAntennaMountHeight = rfAntennaMountHeight;
	}*/

	/*public Integer getMwAntennaMountHeight() {
		return mwAntennaMountHeight;
	}

	public void setMwAntennaMountHeight(Integer mwAntennaMountHeight) {
		this.mwAntennaMountHeight = mwAntennaMountHeight;
	}*/

	public Integer getNoOfRFAntenna() {
		return noOfRFAntenna;
	}

	public void setNoOfRFAntenna(Integer noOfRFAntenna) {
		this.noOfRFAntenna = noOfRFAntenna;
	}

	public String getClutter() {
		return clutter;
	}

	public void setClutter(String clutter) {
		this.clutter = clutter;
	}

	public Integer getNO_OF_MCB() {
		return NO_OF_MCB;
	}

	public void setNO_OF_MCB(Integer nO_OF_MCB) {
		NO_OF_MCB = nO_OF_MCB;
	}

	public String getMW_RACK() {
		return MW_RACK;
	}

	public void setMW_RACK(String mW_RACK) {
		MW_RACK = mW_RACK;
	}

	public Integer getNoOfUSpaceRequired() {
		return noOfUSpaceRequired;
	}

	public void setNoOfUSpaceRequired(Integer noOfUSpaceRequired) {
		this.noOfUSpaceRequired = noOfUSpaceRequired;
	}

	/*public String getExistingAirtelConfigurationBeforeMIMO() {
		return existingAirtelConfigurationBeforeMIMO;
	}

	public void setExistingAirtelConfigurationBeforeMIMO(String existingAirtelConfigurationBeforeMIMO) {
		this.existingAirtelConfigurationBeforeMIMO = existingAirtelConfigurationBeforeMIMO;
	}*/

	public Integer getNoOfMassiveMIMOAntenna() {
		return noOfMassiveMIMOAntenna;
	}

	public void setNoOfMassiveMIMOAntenna(Integer noOfMassiveMIMOAntenna) {
		this.noOfMassiveMIMOAntenna = noOfMassiveMIMOAntenna;
	}
	
	/*public Integer getuSpaceForBBU() {
		return uSpaceForBBU;
	}

	public void setuSpaceForBBU(Integer uSpaceForBBU) {
		this.uSpaceForBBU = uSpaceForBBU;
	}*/

	public Integer getPowerRequirement() {
		return powerRequirement;
	}

	public void setPowerRequirement(Integer powerRequirement) {
		this.powerRequirement = powerRequirement;
	}

	/*public Integer getPowerThresholdInCaseOfMIMOExpansion() {
		return powerThresholdInCaseOfMIMOExpansion;
	}

	public void setPowerThresholdInCaseOfMIMOExpansion(Integer powerThresholdInCaseOfMIMOExpansion) {
		this.powerThresholdInCaseOfMIMOExpansion = powerThresholdInCaseOfMIMOExpansion;
	}*/

	/*public Integer getAdditionalLLRDueToAdditionalMIMO() {
		return additionalLLRDueToAdditionalMIMO;
	}

	public void setAdditionalLLRDueToAdditionalMIMO(Integer additionalLLRDueToAdditionalMIMO) {
		this.additionalLLRDueToAdditionalMIMO = additionalLLRDueToAdditionalMIMO;
	}*/

	/*public String getSmartSplitType() {
		return smartSplitType;
	}

	public void setSmartSplitType(String smartSplitType) {
		this.smartSplitType = smartSplitType;
	}*/

	public String getFloorSpaceOfODCabinet() {
		return floorSpaceOfODCabinet;
	}

	public void setFloorSpaceOfODCabinet(String floorSpaceOfODCabinet) {
		this.floorSpaceOfODCabinet = floorSpaceOfODCabinet;
	}

	/*public String getAcPowerLoad() {
		return acPowerLoad;
	}

	public void setAcPowerLoad(String acPowerLoad) {
		this.acPowerLoad = acPowerLoad;
	}*/

	/*public Integer getTotalPowerRequired() {
		return totalPowerRequired;
	}

	public void setTotalPowerRequired(Integer totalPowerRequired) {
		this.totalPowerRequired = totalPowerRequired;
	}*/

	/*public String getCowType() {
		return cowType;
	}

	public void setCowType(String cowType) {
		this.cowType = cowType;
	}*/

	/*public String getServiceContractPeriod() {
		return serviceContractPeriod;
	}

	public void setServiceContractPeriod(String serviceContractPeriod) {
		this.serviceContractPeriod = serviceContractPeriod;
	}*/

	/*public Integer getTowerWeight() {
		return towerWeight;
	}

	public void setTowerWeight(Integer towerWeight) {
		this.towerWeight = towerWeight;
	}*/

	/*public Integer getRackSpaceForBBU() {
		return rackSpaceForBBU;
	}

	public void setRackSpaceForBBU(Integer rackSpaceForBBU) {
		this.rackSpaceForBBU = rackSpaceForBBU;
	}*/

	/*public Integer getRackSpaceForMW() {
		return rackSpaceForMW;
	}

	public void setRackSpaceForMW(Integer rackSpaceForMW) {
		this.rackSpaceForMW = rackSpaceForMW;
	}*/

	/*public String getPowerSupply() {
		return powerSupply;
	}

	public void setPowerSupply(String powerSupply) {
		this.powerSupply = powerSupply;
	}*/

	/*public Integer getAdditionBTSFloorSpace() {
		return additionBTSFloorSpace;
	}

	public void setAdditionBTSFloorSpace(Integer additionBTSFloorSpace) {
		this.additionBTSFloorSpace = additionBTSFloorSpace;
	}*/

	public Integer getAglRequiredODSC() {
		return aglRequiredODSC;
	}

	public void setAglRequiredODSC(Integer aglRequiredODSC) {
		this.aglRequiredODSC = aglRequiredODSC;
	}

	/*public Integer getAglRequiredMW() {
		return aglRequiredMW;
	}

	public void setAglRequiredMW(Integer aglRequiredMW) {
		this.aglRequiredMW = aglRequiredMW;
	}*/

	public Double getPoleHeight() {
		return poleHeight;
	}

	public void setPoleHeight(Double poleHeight) {
		this.poleHeight = poleHeight;
	}

	/*public Integer getAdditionalPoles() {
		return additionalPoles;
	}

	public void setAdditionalPoles(Integer additionalPoles) {
		this.additionalPoles = additionalPoles;
	}*/

	public String getAirtelBackhaul() {
		return airtelBackhaul;
	}

	public void setAirtelBackhaul(String airtelBackhaul) {
		this.airtelBackhaul = airtelBackhaul;
	}

	public String getAcDcBackup() {
		return acDcBackup;
	}

	public void setAcDcBackup(String acDcBackup) {
		this.acDcBackup = acDcBackup;
	}

	public String getAdditionalPowerBackup2Hrs() {
		return additionalPowerBackup2Hrs;
	}

	public void setAdditionalPowerBackup2Hrs(String additionalPowerBackup2Hrs) {
		this.additionalPowerBackup2Hrs = additionalPowerBackup2Hrs;
	}

	

	public Double getTotalRatedPower() {
		return totalRatedPower;
	}

	public void setTotalRatedPower(Double totalRatedPower) {
		this.totalRatedPower = totalRatedPower;
	}

	public String getCamuflauging() {
		return camuflauging;
	}

	public void setCamuflauging(String camuflauging) {
		this.camuflauging = camuflauging;
	}

	/*public Integer getNoOfSmallCell() {
		return noOfSmallCell;
	}

	public void setNoOfSmallCell(Integer noOfSmallCell) {
		this.noOfSmallCell = noOfSmallCell;
	}*/

	/*public Integer getAreaPerSmallCell() {
		return areaPerSmallCell;
	}

	public void setAreaPerSmallCell(Integer areaPerSmallCell) {
		this.areaPerSmallCell = areaPerSmallCell;
	}*/

	/*public Integer getDepthPerSmallCell() {
		return depthPerSmallCell;
	}

	public void setDepthPerSmallCell(Integer depthPerSmallCell) {
		this.depthPerSmallCell = depthPerSmallCell;
	}*/

	/*public Integer getHeightOfHighestAntenna() {
		return heightOfHighestAntenna;
	}

	public void setHeightOfHighestAntenna(Integer heightOfHighestAntenna) {
		this.heightOfHighestAntenna = heightOfHighestAntenna;
	}*/

	/*public Integer getWeightOfSmallCell() {
		return weightOfSmallCell;
	}

	public void setWeightOfSmallCell(Integer weightOfSmallCell) {
		this.weightOfSmallCell = weightOfSmallCell;
	}*/

	/*public Integer getRatedPower() {
		return ratedPower;
	}

	public void setRatedPower(Integer ratedPower) {
		this.ratedPower = ratedPower;
	}*/

	public Integer getuSpace() {
		return uSpace;
	}

	public void setuSpace(Integer uSpace) {
		this.uSpace = uSpace;
	}

	/*public String getAcbackupRequired2Hrs() {
		return acbackupRequired2Hrs;
	}

	public void setAcbackupRequired2Hrs(String acbackupRequired2Hrs) {
		this.acbackupRequired2Hrs = acbackupRequired2Hrs;
	}*/

	/*public String getAdditionalODSCRequired() {
		return additionalODSCRequired;
	}

	public void setAdditionalODSCRequired(String additionalODSCRequired) {
		this.additionalODSCRequired = additionalODSCRequired;
	}*/

	public Double getAdditionalPowerRequired() {
		return additionalPowerRequired;
	}

	public void setAdditionalPowerRequired(Double additionalPowerRequired) {
		this.additionalPowerRequired = additionalPowerRequired;
	}

	/*public String getExistingLLROfTVISite() {
		return existingLLROfTVISite;
	}

	public void setExistingLLROfTVISite(String existingLLROfTVISite) {
		this.existingLLROfTVISite = existingLLROfTVISite;
	}*/

	/*public String getAdditionalLLRDueToAdditionalODSC() {
		return additionalLLRDueToAdditionalODSC;
	}

	public void setAdditionalLLRDueToAdditionalODSC(String additionalLLRDueToAdditionalODSC) {
		this.additionalLLRDueToAdditionalODSC = additionalLLRDueToAdditionalODSC;
	}*/

	/*public String getCumulativePANIndiaODCSSharingSOCount() {
		return cumulativePANIndiaODCSSharingSOCount;
	}

	public void setCumulativePANIndiaODCSSharingSOCount(String cumulativePANIndiaODCSSharingSOCount) {
		this.cumulativePANIndiaODCSSharingSOCount = cumulativePANIndiaODCSSharingSOCount;
	}*/

	public String getConversionOfSharingODSCIntoMacroSite() {
		return conversionOfSharingODSCIntoMacroSite;
	}

	public void setConversionOfSharingODSCIntoMacroSite(String conversionOfSharingODSCIntoMacroSite) {
		this.conversionOfSharingODSCIntoMacroSite = conversionOfSharingODSCIntoMacroSite;
	}

	public Integer getNoOfODSC() {
		return noOfODSC;
	}

	public void setNoOfODSC(Integer noOfODSC) {
		this.noOfODSC = noOfODSC;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	/*public Integer getRatedPowerConsumption() {
		return ratedPowerConsumption;
	}

	public void setRatedPowerConsumption(Integer ratedPowerConsumption) {
		this.ratedPowerConsumption = ratedPowerConsumption;
	}*/

	public String getSeafAttached() {
		return seafAttached;
	}

	public void setSeafAttached(String seafAttached) {
		this.seafAttached = seafAttached;
	}

	public String getNcsoAttached() {
		return ncsoAttached;
	}

	public void setNcsoAttached(String ncsoAttached) {
		this.ncsoAttached = ncsoAttached;
	}

	public String getGoogleSnapshotAttachment() {
		return googleSnapshotAttachment;
	}

	public void setGoogleSnapshotAttachment(String googleSnapshotAttachment) {
		this.googleSnapshotAttachment = googleSnapshotAttachment;
	}

	public String getNfaAttachment() {
		return nfaAttachment;
	}

	public void setNfaAttachment(String nfaAttachment) {
		this.nfaAttachment = nfaAttachment;
	}

	public String getBoqAttachment() {
		return boqAttachment;
	}

	public void setBoqAttachment(String boqAttachment) {
		this.boqAttachment = boqAttachment;
	}

	/*public String getHoAqAttachment() {
		return hoAqAttachment;
	}

	public void setHoAqAttachment(String hoAqAttachment) {
		this.hoAqAttachment = hoAqAttachment;
	}*/

	public String getSalesAttachment() {
		return salesAttachment;
	}

	public void setSalesAttachment(String salesAttachment) {
		this.salesAttachment = salesAttachment;
	}

	public String getHoLegalAttachment() {
		return hoLegalAttachment;
	}

	public void setHoLegalAttachment(String hoLegalAttachment) {
		this.hoLegalAttachment = hoLegalAttachment;
	}

	public String getHoProjectEbAttachment() {
		return hoProjectEbAttachment;
	}

	public void setHoProjectEbAttachment(String hoProjectEbAttachment) {
		this.hoProjectEbAttachment = hoProjectEbAttachment;
	}

	/*public String getHoProjectHeadAttachment() {
		return hoProjectHeadAttachment;
	}

	public void setHoProjectHeadAttachment(String hoProjectHeadAttachment) {
		this.hoProjectHeadAttachment = hoProjectHeadAttachment;
	}*/

	/*public String getHoCooAttachment() {
		return hoCooAttachment;
	}

	public void setHoCooAttachment(String hoCooAttachment) {
		this.hoCooAttachment = hoCooAttachment;
	}*/

	public Date getRfiAcceptedDate() {
		return rfiAcceptedDate;
	}

	public void setRfiAcceptedDate(Date rfiAcceptedDate) {
		this.rfiAcceptedDate = rfiAcceptedDate;
	}

	public String getSuggestedSiteAddress() {
		return suggestedSiteAddress;
	}

	public void setSuggestedSiteAddress(String suggestedSiteAddress) {
		this.suggestedSiteAddress = suggestedSiteAddress;
	}

	public String getSuggestedCity() {
		return suggestedCity;
	}

	public void setSuggestedCity(String suggestedCity) {
		this.suggestedCity = suggestedCity;
	}

	/*public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}*/

	public String getSuggestedState() {
		return suggestedState;
	}

	public void setSuggestedState(String suggestedState) {
		this.suggestedState = suggestedState;
	}

	public String getSuggestedDistrict() {
		return suggestedDistrict;
	}

	public void setSuggestedDistrict(String suggestedDistrict) {
		this.suggestedDistrict = suggestedDistrict;
	}

	public String getSuggestedSiteType() {
		return suggestedSiteType;
	}

	public void setSuggestedSiteType(String suggestedSiteType) {
		this.suggestedSiteType = suggestedSiteType;
	}

	public String getSuggestedTownVillage() {
		return suggestedTownVillage;
	}

	public void setSuggestedTownVillage(String suggestedTownVillage) {
		this.suggestedTownVillage = suggestedTownVillage;
	}

	public Integer getSuggestedPincode() {
		return suggestedPincode;
	}

	public void setSuggestedPincode(Integer suggestedPincode) {
		this.suggestedPincode = suggestedPincode;
	}

	public String getSuggestedClutter() {
		return suggestedClutter;
	}

	public void setSuggestedClutter(String suggestedClutter) {
		this.suggestedClutter = suggestedClutter;
	}

	/*public Double getTowerHeight() {
		return towerHeight;
	}

	public void setTowerHeight(Double towerHeight) {
		this.towerHeight = towerHeight;
	}*/

	public Double getAglRequired() {
		return aglRequired;
	}

	public void setAglRequired(Double aglRequired) {
		this.aglRequired = aglRequired;
	}

	public Double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Double getAdditionalLoad() {
		return additionalLoad;
	}

	public void setAdditionalLoad(Double additionalLoad) {
		this.additionalLoad = additionalLoad;
	}

	public Integer getSuggestedStandardIPFEE() {
		return suggestedStandardIPFEE;
	}

	public void setSuggestedStandardIPFEE(Integer suggestedStandardIPFEE) {
		this.suggestedStandardIPFEE = suggestedStandardIPFEE;
	}

	public Integer getSuggestedLLR() {
		return suggestedLLR;
	}

	public void setSuggestedLLR(Integer suggestedLLR) {
		this.suggestedLLR = suggestedLLR;
	}

	public Double getSuggestedCityPremium() {
		return suggestedCityPremium;
	}

	public void setSuggestedCityPremium(Double suggestedCityPremium) {
		this.suggestedCityPremium = suggestedCityPremium;
	}

	public Integer getSuggestedLoading() {
		return suggestedLoading;
	}

	public void setSuggestedLoading(Integer suggestedLoading) {
		this.suggestedLoading = suggestedLoading;
	}

	public Double getEbAvailabilityDistance() {
		return ebAvailabilityDistance;
	}

	public void setEbAvailabilityDistance(Double ebAvailabilityDistance) {
		this.ebAvailabilityDistance = ebAvailabilityDistance;
	}

	public String getBtsType() {
		return btsType;
	}

	public void setBtsType(String btsType) {
		this.btsType = btsType;
	}

	public Double getSuggestedEbAvailablilityDistance() {
		return suggestedEbAvailablilityDistance;
	}

	public void setSuggestedEbAvailablilityDistance(Double suggestedEbAvailablilityDistance) {
		this.suggestedEbAvailablilityDistance = suggestedEbAvailablilityDistance;
	}

	public Double getSuggestedTowerHeight() {
		return suggestedTowerHeight;
	}

	public void setSuggestedTowerHeight(Double suggestedTowerHeight) {
		this.suggestedTowerHeight = suggestedTowerHeight;
	}

	public String getSuggestedMunicipalTax() {
		return suggestedMunicipalTax;
	}

	public void setSuggestedMunicipalTax(String suggestedMunicipalTax) {
		this.suggestedMunicipalTax = suggestedMunicipalTax;
	}

	public String getSuggestedPropertyTax() {
		return suggestedPropertyTax;
	}

	public void setSuggestedPropertyTax(String suggestedPropertyTax) {
		this.suggestedPropertyTax = suggestedPropertyTax;
	}

	public String getSuggestedDgAvailability() {
		return suggestedDgAvailability;
	}

	public void setSuggestedDgAvailability(String suggestedDgAvailability) {
		this.suggestedDgAvailability = suggestedDgAvailability;
	}

	public String getAggrementAttached() {
		return aggrementAttached;
	}

	public void setAggrementAttached(String aggrementAttached) {
		this.aggrementAttached = aggrementAttached;
	}

	public Date getSpDate() {
		return spDate;
	}

	public void setSpDate(Date spDate) {
		this.spDate = spDate;
	}

	public Date getSoDate() {
		return soDate;
	}

	public void setSoDate(Date soDate) {
		this.soDate = soDate;
	}

	public Date getSrDate() {
		return srDate;
	}

	public void setSrDate(Date srDate) {
		this.srDate = srDate;
	}

	public Double getLoadOfU() {
		return loadOfU;
	}

	public void setLoadOfU(Double loadOfU) {
		this.loadOfU = loadOfU;
	}

	public String getRfiDocAttachment() {
		return rfiDocAttachment;
	}

	public void setRfiDocAttachment(String rfiDocAttachment) {
		this.rfiDocAttachment = rfiDocAttachment;
	}

	public String getAtDocAttachment() {
		return atDocAttachment;
	}

	public void setAtDocAttachment(String atDocAttachment) {
		this.atDocAttachment = atDocAttachment;
	}

	public String getRbhAttachment() {
		return rbhAttachment;
	}

	public void setRbhAttachment(String rbhAttachment) {
		this.rbhAttachment = rbhAttachment;
	}

	public String getHoRfPlanningAttachment() {
		return hoRfPlanningAttachment;
	}

	public void setHoRfPlanningAttachment(String hoRfPlanningAttachment) {
		this.hoRfPlanningAttachment = hoRfPlanningAttachment;
	}

	public String getHoSnMAttachment() {
		return hoSnMAttachment;
	}

	public void setHoSnMAttachment(String hoSnMAttachment) {
		this.hoSnMAttachment = hoSnMAttachment;
	}

	public String getOpcoAttachment() {
		return opcoAttachment;
	}

	public void setOpcoAttachment(String opcoAttachment) {
		this.opcoAttachment = opcoAttachment;
	}

	public String getDdrAttached() {
		return ddrAttached;
	}

	public void setDdrAttached(String ddrAttached) {
		this.ddrAttached = ddrAttached;
	}

	public String getSurveyReportAttached() {
		return surveyReportAttached;
	}

	public void setSurveyReportAttached(String surveyReportAttached) {
		this.surveyReportAttached = surveyReportAttached;
	}

	/*public String getCircleOperationHeadAttachment() {
		return circleOperationHeadAttachment;
	}

	public void setCircleOperationHeadAttachment(String circleOperationHeadAttachment) {
		this.circleOperationHeadAttachment = circleOperationHeadAttachment;
	}*/

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getCityPremiumPercentage() {
		return cityPremiumPercentage;
	}

	public void setCityPremiumPercentage(Integer cityPremiumPercentage) {
		this.cityPremiumPercentage = cityPremiumPercentage;
	}

	public String getCityClass() {
		return cityClass;
	}

	public void setCityClass(String cityClass) {
		this.cityClass = cityClass;
	}

	public String getSuggestedWindSpeed() {
		return suggestedWindSpeed;
	}

	public void setSuggestedWindSpeed(String suggestedWindSpeed) {
		this.suggestedWindSpeed = suggestedWindSpeed;
	}

	public String getSuggestedLockTerm() {
		return suggestedLockTerm;
	}

	public void setSuggestedLockTerm(String suggestedLockTerm) {
		this.suggestedLockTerm = suggestedLockTerm;
	}

	public Double getSuggestedDeviation() {
		return suggestedDeviation;
	}

	public void setSuggestedDeviation(Double suggestedDeviation) {
		this.suggestedDeviation = suggestedDeviation;
	}

	public String getSuggestedLandOwnerRent() {
		return suggestedLandOwnerRent;
	}

	public void setSuggestedLandOwnerRent(String suggestedLandOwnerRent) {
		this.suggestedLandOwnerRent = suggestedLandOwnerRent;
	}

	public String getOdscModelType() {
		return odscModelType;
	}

	public void setOdscModelType(String odscModelType) {
		this.odscModelType = odscModelType;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getNO_OF_IP55() {
		return NO_OF_IP55;
	}

	public void setNO_OF_IP55(Integer nO_OF_IP55) {
		NO_OF_IP55 = nO_OF_IP55;
	}

	public Integer getNO_OF_RRU_Delete() {
		return NO_OF_RRU_Delete;
	}

	public void setNO_OF_RRU_Delete(Integer nO_OF_RRU_Delete) {
		NO_OF_RRU_Delete = nO_OF_RRU_Delete;
	}

	public Integer getNO_OF_RRU_Add() {
		return NO_OF_RRU_Add;
	}

	public void setNO_OF_RRU_Add(Integer nO_OF_RRU_Add) {
		NO_OF_RRU_Add = nO_OF_RRU_Add;
	}

	public String getExtensionType() {
		return extensionType;
	}

	public void setExtensionType(String extensionType) {
		this.extensionType = extensionType;
	}

	public Integer getNoOfUBR_Antenna() {
		return noOfUBR_Antenna;
	}

	public void setNoOfUBR_Antenna(Integer noOfUBR_Antenna) {
		this.noOfUBR_Antenna = noOfUBR_Antenna;
	}

	public String getTypeOfAntenna() {
		return typeOfAntenna;
	}

	public void setTypeOfAntenna(String typeOfAntenna) {
		this.typeOfAntenna = typeOfAntenna;
	}

	public String getAcquisitionAttachment() {
		return acquisitionAttachment;
	}

	public void setAcquisitionAttachment(String acquisitionAttachment) {
		this.acquisitionAttachment = acquisitionAttachment;
	}

	public Double getFiberLength() {
		return fiberLength;
	}

	public void setFiberLength(Double fiberLength) {
		this.fiberLength = fiberLength;
	}

	public Integer getNoOfBbuDelete() {
		return noOfBbuDelete;
	}

	public void setNoOfBbuDelete(Integer noOfBbuDelete) {
		this.noOfBbuDelete = noOfBbuDelete;
	}

	public Integer getNoOfBbuAdd() {
		return noOfBbuAdd;
	}

	public void setNoOfBbuAdd(Integer noOfBbuAdd) {
		this.noOfBbuAdd = noOfBbuAdd;
	}

	public Integer getNoOfMicrowaveDelete() {
		return noOfMicrowaveDelete;
	}

	public void setNoOfMicrowaveDelete(Integer noOfMicrowaveDelete) {
		this.noOfMicrowaveDelete = noOfMicrowaveDelete;
	}

	public Integer getNoOfMicrowaveAdd() {
		return noOfMicrowaveAdd;
	}

	public void setNoOfMicrowaveAdd(Integer noOfMicrowaveAdd) {
		this.noOfMicrowaveAdd = noOfMicrowaveAdd;
	}

	public Double getPowerConsumption() {
		return powerConsumption;
	}

	public void setPowerConsumption(Double powerConsumption) {
		this.powerConsumption = powerConsumption;
	}

	public Integer getuSpace_ethernet() {
		return uSpace_ethernet;
	}

	public void setuSpace_ethernet(Integer uSpace_ethernet) {
		this.uSpace_ethernet = uSpace_ethernet;
	}

	public Integer getMCB_Required_In_AMP() {
		return MCB_Required_In_AMP;
	}

	public void setMCB_Required_In_AMP(Integer mCB_Required_In_AMP) {
		MCB_Required_In_AMP = mCB_Required_In_AMP;
	}

	public Integer getCountOfRfFilter() {
		return countOfRfFilter;
	}

	public void setCountOfRfFilter(Integer countOfRfFilter) {
		this.countOfRfFilter = countOfRfFilter;
	}

	public Integer getNoOfRFAntenna_Delete() {
		return noOfRFAntenna_Delete;
	}

	public void setNoOfRFAntenna_Delete(Integer noOfRFAntenna_Delete) {
		this.noOfRFAntenna_Delete = noOfRFAntenna_Delete;
	}

	public Integer getNoOfRFAntenna_Add() {
		return noOfRFAntenna_Add;
	}

	public void setNoOfRFAntenna_Add(Integer noOfRFAntenna_Add) {
		this.noOfRFAntenna_Add = noOfRFAntenna_Add;
	}

	public Integer getNoOfPoles() {
		return noOfPoles;
	}

	public void setNoOfPoles(Integer noOfPoles) {
		this.noOfPoles = noOfPoles;
	}

	public Integer getNoOfHpscAntenna() {
		return noOfHpscAntenna;
	}

	public void setNoOfHpscAntenna(Integer noOfHpscAntenna) {
		this.noOfHpscAntenna = noOfHpscAntenna;
	}

	public Double getPowerRatingOfEquipment() {
		return powerRatingOfEquipment;
	}

	public void setPowerRatingOfEquipment(Double powerRatingOfEquipment) {
		this.powerRatingOfEquipment = powerRatingOfEquipment;
	}

	public Double getElectrificationCharges() {
		return electrificationCharges;
	}

	public void setElectrificationCharges(Double electrificationCharges) {
		this.electrificationCharges = electrificationCharges;
	}

	public Double getMcCharges() {
		return mcCharges;
	}

	public void setMcCharges(Double mcCharges) {
		this.mcCharges = mcCharges;
	}

	public String getSharingPotential() {
		return sharingPotential;
	}

	public void setSharingPotential(String sharingPotential) {
		this.sharingPotential = sharingPotential;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getAdditionalBB() {
		return additionalBB;
	}

	public void setAdditionalBB(String additionalBB) {
		this.additionalBB = additionalBB;
	}

	public String getHeadLoad() {
		return headLoad;
	}

	public void setHeadLoad(String headLoad) {
		this.headLoad = headLoad;
	}

	public String getEbConnection() {
		return ebConnection;
	}

	public void setEbConnection(String ebConnection) {
		this.ebConnection = ebConnection;
	}

	public Double getTotalWeightOnTower() {
		return totalWeightOnTower;
	}

	public void setTotalWeightOnTower(Double totalWeightOnTower) {
		this.totalWeightOnTower = totalWeightOnTower;
	}

	public String getSuggestedTowerType() {
		return suggestedTowerType;
	}

	public void setSuggestedTowerType(String suggestedTowerType) {
		this.suggestedTowerType = suggestedTowerType;
	}

	public String geteSeafPortalUrl() {
		return eSeafPortalUrl;
	}

	public void seteSeafPortalUrl(String eSeafPortalUrl) {
		this.eSeafPortalUrl = eSeafPortalUrl;
	}

	public String getCircleLegalAttachment() {
		return circleLegalAttachment;
	}

	public void setCircleLegalAttachment(String circleLegalAttachment) {
		this.circleLegalAttachment = circleLegalAttachment;
	}
	
	

}
