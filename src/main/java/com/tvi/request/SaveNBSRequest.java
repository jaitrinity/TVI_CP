package com.tvi.request;

import java.util.List;

import com.tvi.dto.ActionButtonDTO;
import com.tvi.dto.NbsAuditDTO;

public class SaveNBSRequest {
	private String loginEmpId,loginEmpRole,tabName,orgTabName,currentTab,operator="",productType,projectName,upgradeType;
	private String srNumber;
	private String spNumber="NA";
	private String soNumber="NA";
	private String srRaiseDate,spRaiseDate,soRaiseDate,rfiDate="",rfiAcceptedDate="",rfsDate="";
	private String tviSiteId,siteName,airtelSiteId,airtelLocatorId,opcoId,refNumberTvipl,siteId;
	private Double latitude1;
	private Double longitude1;
	private Double latitude2,suggestedLatitude,spLatitude;
	private Double longitude2,suggestedLongitude,spLongitude;
	private String circleName,siteAddress,state,class_A_District,class_B_District,class_C_District;
	private String technology,extensionType;
	private String district,ebAvailability,dgAvailability;
	private String btsType,siteType,clutter,frequencyUserByOpco;
	private String city,townVillage,cityVillage;
	private Integer pincode,noOfGSMAntenna,countOfRfFilter;
	private Double ebAvailabilityDistance,windSpeed,towerHeight,aglRequired,additionalLoad,loadOfU,fiberLength;
	private Integer noOfODSC;
	private List<ODSCRequest> odscList;
	private List<GSMAntennaRequest> gsmAntennaList;
	private Integer noOfMicrowave;
	private List<MicrowaveRequest> microwaveList;
	private Integer noOfRFAntenna;
	private List<RFAntennaRequest> rfAntennaList;
	private Integer noOfRFAntenna_Delete;
	private List<RFAntennaRequest> rfAntennaDeleteList;
	private Integer noOfRFAntenna_Add;
	private List<RFAntennaRequest> rfAntennaAddList;
	private Integer noOfBBU;
	private List<BBURequest> bbuList;
	private Integer noOfRRU;
	private List<RRURequest> rruList;
	private Integer noOfBTS;
	private List<BTSRequest> btsList;
	private Integer noOfMCB;
	private List<MCBRequest> mcbList;
	private Integer noOfMassiveMIMOAntenna;
	private List<MassiveMIMORequest> massiveMIMOAntennaList;
	private Integer noOfIP55;
	private List<IP55Request> ip55List;
	private Integer noOfRRU_Delete;
	private List<RRU_SwapRequest> rruDeleteList;
	private Integer noOfRRU_Add;
	private List<RRU_SwapRequest> rruAddList;
	private Integer noOfBBU_Delete;
	private List<BBURequest> bbuDeleteList;
	private Integer noOfBBU_Add;
	private List<BBURequest> bbuAddList;
	private Integer noOfMicrowave_Delete;
	private List<MicrowaveRequest> microwaveDeleteList;
	private Integer noOfMicrowave_Add;
	private List<MicrowaveRequest> microwaveAddList;
	private Integer noOfPole;
	private List<PoleRequest> noOfPoleList;
	private Integer noOfHPSCAntenna;
	private List<HPSCAntennaRequest> noOfHPSCAntennaList;
	private Integer noOfAirFiber;
	private List<AirFiberRequest> airFiberList;
	private Double floorLength,totalRatedPower;
	private Double floorWidth;
	private Double floorHeight;
	private Double btsPower,suggestedDeviation,powerRequired,powerRatingOfEquipment;
	private Double totalPowerConsumption,suggestedEbAvailablilityDistance,suggestedTowerHeight,suggestedCityPremium,poleHeight,additionalPowerRequired,
	powerConsumption,suggestedElectrificationCharges,suggestedMcCharges,totalWeightOnTower;
	private Integer rfAntennaMountHeight,mwAntennaMountHeight,noOfSpaceRequired,noOfUSpaceRequired,weightOfAdditionalAntenna,weightOfAntenna,
	areaOfAntenna,uSpaceForBBU,powerRequirement,powerThresholdInCaseOfMIMOExpansion,totalPowerRequired,noOfUBR_Antenna,mcbRequiredInAmp,
	acPower,noOfMcb,mcbAmp;
	private String fiberEntry,fiberTermination,existingAirtelConfigurationBeforeMIMO,smartSplitType,mwRack,
	floorSpaceOfODCabinet,acPowerLoad,odscModelType,typeOfAntenna,rowSpace,dgStatus,batteryBackup,suggestedTowerType;
	private Integer ratedPowerConsumption,towerWeight,rackSpaceForBBU,rackSpaceForMW,additionBTSFloorSpace,aglRequiredODSC,aglRequiredMW,additionalPoles,
	noOfSmallCell,areaPerSmallCell,depthPerSmallCell,heightOfHighestAntenna,weightOfSmallCell,ratedPower,uSpace,uSpace_ethernet,additionalLLRDueToAdditionalMIMO;
	private String cowType,serviceContractPeriod,powerSupply,airtelBackhaul,acDcBackup,additionalPowerBackup2Hrs,
	camuflauging,acbackupRequired2Hrs,additionalODSCRequired,existingLLROfTVISite,additionalLLRDueToAdditionalODSC,
	cumulativePANIndiaODCSSharingSOCount,conversionOfSharingODSCIntoMacroSite,sharingPotential;
	private String remark;
	private String status,pendingTo,pendingFor,srStatus;
	private String seafAttached,ddrAttached,surveyReportAttached,eSeafPortalUrl,ncsoAttached,googleSnapshotAttachment,nfaAttachment,boqAttachment,aggrementAttachment,rfiDocAttachment,
	atDocAttachment,hoAqAttachment,salesAttachment,hoLegalAttachment,hoProjectEbAttachment,hoProjectHeadAttachment,hoCooAttachment,
	rbhAttachment,acquisitionAttachment,hoSnMAttachment,hoRfPlanningAttachment,opcoAttachment,circleOperationHeadAttachment,circleLegalAttachment;
	private String suggestedSiteAddress,suggestedCity,suggestedTownVillage,suggestedDistrict,suggestedState,
	suggestedClutter,suggestedSiteType,suggestedDgAvailability,suggestedPropertyTax,suggestedMunicipalTax,cityClass,
	suggestedWindSpeed,suggestedLockTerm,suggestedLandOwnerRent,nbsProductType,additionalBB,headLoad,ebConnection;
	private Integer suggestedPincode,suggestedStandardIPFEE,suggestedLLR,suggestedLoading,cityPremiumPercentage;
	
	private List<NbsAuditDTO> nbsAuditList;
	
	private List<ODSCRequest> viewOdscList;
	private List<GSMAntennaRequest> viewGsmAntennaList;
	private List<MicrowaveRequest> viewMicrowaveList;
	private List<RFAntennaRequest> viewRfAntennaList;
	private List<RFAntennaRequest> viewRfAntennaDeleteList;
	private List<RFAntennaRequest> viewRfAntennaAddList;
	private List<BBURequest> viewBbuList;
	private List<RRURequest> viewRruList;
	private List<BTSRequest> viewBtsList;
	private List<MCBRequest> viewMcbList;
	private List<MassiveMIMORequest> viewMimoList;
	private List<IP55Request> viewIp55List;
	private List<RRU_SwapRequest> viewRruDeleteList;
	private List<RRU_SwapRequest> viewRruAddList;
	private List<BBURequest> viewBbuDeleteList;
	private List<BBURequest> viewBbuAddList;
	private List<MicrowaveRequest> viewMicrowaveDeleteList;
	private List<MicrowaveRequest> viewMicrowaveAddList;
	private List<PoleRequest> viewPoleList;
	private List<HPSCAntennaRequest> viewHPSCAntennaList;
	private List<ActionButtonDTO> actionButtonList;
	
	public String getOpcoId() {
		return opcoId;
	}
	public void setOpcoId(String opcoId) {
		this.opcoId = opcoId;
	}
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
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
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
	public String getCityVillage() {
		return cityVillage;
	}
	public void setCityVillage(String cityVillage) {
		this.cityVillage = cityVillage;
	}
	public Integer getNoOfGSMAntenna() {
		return noOfGSMAntenna;
	}
	public void setNoOfGSMAntenna(Integer noOfGSMAntenna) {
		this.noOfGSMAntenna = noOfGSMAntenna;
	}
	public List<GSMAntennaRequest> getGsmAntennaList() {
		return gsmAntennaList;
	}
	public void setGsmAntennaList(List<GSMAntennaRequest> gsmAntennaList) {
		this.gsmAntennaList = gsmAntennaList;
	}
	public Integer getNoOfMicrowave() {
		return noOfMicrowave;
	}
	public void setNoOfMicrowave(Integer noOfMicrowave) {
		this.noOfMicrowave = noOfMicrowave;
	}
	public List<MicrowaveRequest> getMicrowaveList() {
		return microwaveList;
	}
	public void setMicrowaveList(List<MicrowaveRequest> microwaveList) {
		this.microwaveList = microwaveList;
	}
	public Integer getNoOfBBU() {
		return noOfBBU;
	}
	public void setNoOfBBU(Integer noOfBBU) {
		this.noOfBBU = noOfBBU;
	}
	public Integer getNoOfRRU() {
		return noOfRRU;
	}
	public void setNoOfRRU(Integer noOfRRU) {
		this.noOfRRU = noOfRRU;
	}
	public List<RRURequest> getRruList() {
		return rruList;
	}
	public void setRruList(List<RRURequest> rruList) {
		this.rruList = rruList;
	}
	public Integer getNoOfBTS() {
		return noOfBTS;
	}
	public void setNoOfBTS(Integer noOfBTS) {
		this.noOfBTS = noOfBTS;
	}
	public Double getFloorLength() {
		return floorLength;
	}
	public void setFloorLength(Double floorLength) {
		this.floorLength = floorLength;
	}
	public Double getFloorWidth() {
		return floorWidth;
	}
	public void setFloorWidth(Double floorWidth) {
		this.floorWidth = floorWidth;
	}
	public Double getFloorHeight() {
		return floorHeight;
	}
	public void setFloorHeight(Double floorHeight) {
		this.floorHeight = floorHeight;
	}
	public Double getBtsPower() {
		return btsPower;
	}
	public void setBtsPower(Double btsPower) {
		this.btsPower = btsPower;
	}
	public Double getTotalPowerConsumption() {
		return totalPowerConsumption;
	}
	public void setTotalPowerConsumption(Double totalPowerConsumption) {
		this.totalPowerConsumption = totalPowerConsumption;
	}
	public Integer getNoOfSpaceRequired() {
		return noOfSpaceRequired;
	}
	public void setNoOfSpaceRequired(Integer noOfSpaceRequired) {
		this.noOfSpaceRequired = noOfSpaceRequired;
	}
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
	public String getSrNumber() {
		return srNumber;
	}
	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}
	public List<BTSRequest> getBtsList() {
		return btsList;
	}
	public void setBtsList(List<BTSRequest> btsList) {
		this.btsList = btsList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getLoginEmpId() {
		return loginEmpId;
	}
	public void setLoginEmpId(String loginEmpId) {
		this.loginEmpId = loginEmpId;
	}
	public List<NbsAuditDTO> getNbsAuditList() {
		return nbsAuditList;
	}
	public void setNbsAuditList(List<NbsAuditDTO> nbsAuditList) {
		this.nbsAuditList = nbsAuditList;
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
	public String getAirtelLocatorId() {
		return airtelLocatorId;
	}
	public void setAirtelLocatorId(String airtelLocatorId) {
		this.airtelLocatorId = airtelLocatorId;
	}
	public String getSiteAddress() {
		return siteAddress;
	}
	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}
	public String getEbAvailability() {
		return ebAvailability;
	}
	public void setEbAvailability(String ebAvailability) {
		this.ebAvailability = ebAvailability;
	}
	public String getDgAvailability() {
		return dgAvailability;
	}
	public void setDgAvailability(String dgAvailability) {
		this.dgAvailability = dgAvailability;
	}
	public String getClutter() {
		return clutter;
	}
	public void setClutter(String clutter) {
		this.clutter = clutter;
	}
	public String getFrequencyUserByOpco() {
		return frequencyUserByOpco;
	}
	public void setFrequencyUserByOpco(String frequencyUserByOpco) {
		this.frequencyUserByOpco = frequencyUserByOpco;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTownVillage() {
		return townVillage;
	}
	public void setTownVillage(String townVillage) {
		this.townVillage = townVillage;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	
	public Double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}
	public Double getTowerHeight() {
		return towerHeight;
	}
	public void setTowerHeight(Double towerHeight) {
		this.towerHeight = towerHeight;
	}
	public Double getAglRequired() {
		return aglRequired;
	}
	public void setAglRequired(Double aglRequired) {
		this.aglRequired = aglRequired;
	}
	public Integer getNoOfRFAntenna() {
		return noOfRFAntenna;
	}
	public void setNoOfRFAntenna(Integer noOfRFAntenna) {
		this.noOfRFAntenna = noOfRFAntenna;
	}
	public List<RFAntennaRequest> getRfAntennaList() {
		return rfAntennaList;
	}
	public void setRfAntennaList(List<RFAntennaRequest> rfAntennaList) {
		this.rfAntennaList = rfAntennaList;
	}
	public List<BBURequest> getBbuList() {
		return bbuList;
	}
	public void setBbuList(List<BBURequest> bbuList) {
		this.bbuList = bbuList;
	}
	public Integer getNoOfMCB() {
		return noOfMCB;
	}
	public void setNoOfMCB(Integer noOfMCB) {
		this.noOfMCB = noOfMCB;
	}
	public List<MCBRequest> getMcbList() {
		return mcbList;
	}
	public void setMcbList(List<MCBRequest> mcbList) {
		this.mcbList = mcbList;
	}
	
	public Integer getRfAntennaMountHeight() {
		return rfAntennaMountHeight;
	}
	public void setRfAntennaMountHeight(Integer rfAntennaMountHeight) {
		this.rfAntennaMountHeight = rfAntennaMountHeight;
	}
	public Integer getMwAntennaMountHeight() {
		return mwAntennaMountHeight;
	}
	public void setMwAntennaMountHeight(Integer mwAntennaMountHeight) {
		this.mwAntennaMountHeight = mwAntennaMountHeight;
	}
	
	public String getMwRack() {
		return mwRack;
	}
	public void setMwRack(String mwRack) {
		this.mwRack = mwRack;
	}
	public Integer getWeightOfAdditionalAntenna() {
		return weightOfAdditionalAntenna;
	}
	public void setWeightOfAdditionalAntenna(Integer weightOfAdditionalAntenna) {
		this.weightOfAdditionalAntenna = weightOfAdditionalAntenna;
	}
	public String getFiberTermination() {
		return fiberTermination;
	}
	public void setFiberTermination(String fiberTermination) {
		this.fiberTermination = fiberTermination;
	}
	public Integer getNoOfUSpaceRequired() {
		return noOfUSpaceRequired;
	}
	public void setNoOfUSpaceRequired(Integer noOfUSpaceRequired) {
		this.noOfUSpaceRequired = noOfUSpaceRequired;
	}
	public Integer getNoOfODSC() {
		return noOfODSC;
	}
	public void setNoOfODSC(Integer noOfODSC) {
		this.noOfODSC = noOfODSC;
	}
	public List<ODSCRequest> getOdscList() {
		return odscList;
	}
	public void setOdscList(List<ODSCRequest> odscList) {
		this.odscList = odscList;
	}
	public Integer getNoOfMassiveMIMOAntenna() {
		return noOfMassiveMIMOAntenna;
	}
	public void setNoOfMassiveMIMOAntenna(Integer noOfMassiveMIMOAntenna) {
		this.noOfMassiveMIMOAntenna = noOfMassiveMIMOAntenna;
	}
	public Integer getWeightOfAntenna() {
		return weightOfAntenna;
	}
	public void setWeightOfAntenna(Integer weightOfAntenna) {
		this.weightOfAntenna = weightOfAntenna;
	}
	public Integer getAreaOfAntenna() {
		return areaOfAntenna;
	}
	public void setAreaOfAntenna(Integer areaOfAntenna) {
		this.areaOfAntenna = areaOfAntenna;
	}
	public Integer getuSpaceForBBU() {
		return uSpaceForBBU;
	}
	public void setuSpaceForBBU(Integer uSpaceForBBU) {
		this.uSpaceForBBU = uSpaceForBBU;
	}
	public Integer getPowerRequirement() {
		return powerRequirement;
	}
	public void setPowerRequirement(Integer powerRequirement) {
		this.powerRequirement = powerRequirement;
	}
	public Integer getPowerThresholdInCaseOfMIMOExpansion() {
		return powerThresholdInCaseOfMIMOExpansion;
	}
	public void setPowerThresholdInCaseOfMIMOExpansion(Integer powerThresholdInCaseOfMIMOExpansion) {
		this.powerThresholdInCaseOfMIMOExpansion = powerThresholdInCaseOfMIMOExpansion;
	}
	public Integer getTotalPowerRequired() {
		return totalPowerRequired;
	}
	public void setTotalPowerRequired(Integer totalPowerRequired) {
		this.totalPowerRequired = totalPowerRequired;
	}
	public String getExistingAirtelConfigurationBeforeMIMO() {
		return existingAirtelConfigurationBeforeMIMO;
	}
	public void setExistingAirtelConfigurationBeforeMIMO(String existingAirtelConfigurationBeforeMIMO) {
		this.existingAirtelConfigurationBeforeMIMO = existingAirtelConfigurationBeforeMIMO;
	}
	
	public Integer getAdditionalLLRDueToAdditionalMIMO() {
		return additionalLLRDueToAdditionalMIMO;
	}
	public void setAdditionalLLRDueToAdditionalMIMO(Integer additionalLLRDueToAdditionalMIMO) {
		this.additionalLLRDueToAdditionalMIMO = additionalLLRDueToAdditionalMIMO;
	}
	public String getSmartSplitType() {
		return smartSplitType;
	}
	public void setSmartSplitType(String smartSplitType) {
		this.smartSplitType = smartSplitType;
	}
	public String getFloorSpaceOfODCabinet() {
		return floorSpaceOfODCabinet;
	}
	public void setFloorSpaceOfODCabinet(String floorSpaceOfODCabinet) {
		this.floorSpaceOfODCabinet = floorSpaceOfODCabinet;
	}
	public String getAcPowerLoad() {
		return acPowerLoad;
	}
	public void setAcPowerLoad(String acPowerLoad) {
		this.acPowerLoad = acPowerLoad;
	}
	public Integer getTowerWeight() {
		return towerWeight;
	}
	public void setTowerWeight(Integer towerWeight) {
		this.towerWeight = towerWeight;
	}
	public Integer getRackSpaceForBBU() {
		return rackSpaceForBBU;
	}
	public void setRackSpaceForBBU(Integer rackSpaceForBBU) {
		this.rackSpaceForBBU = rackSpaceForBBU;
	}
	public Integer getRackSpaceForMW() {
		return rackSpaceForMW;
	}
	public void setRackSpaceForMW(Integer rackSpaceForMW) {
		this.rackSpaceForMW = rackSpaceForMW;
	}
	public Integer getAdditionBTSFloorSpace() {
		return additionBTSFloorSpace;
	}
	public void setAdditionBTSFloorSpace(Integer additionBTSFloorSpace) {
		this.additionBTSFloorSpace = additionBTSFloorSpace;
	}
	public Integer getAglRequiredODSC() {
		return aglRequiredODSC;
	}
	public void setAglRequiredODSC(Integer aglRequiredODSC) {
		this.aglRequiredODSC = aglRequiredODSC;
	}
	public Integer getAglRequiredMW() {
		return aglRequiredMW;
	}
	public void setAglRequiredMW(Integer aglRequiredMW) {
		this.aglRequiredMW = aglRequiredMW;
	}
	public Integer getAdditionalPoles() {
		return additionalPoles;
	}
	public void setAdditionalPoles(Integer additionalPoles) {
		this.additionalPoles = additionalPoles;
	}
	
	public Double getTotalRatedPower() {
		return totalRatedPower;
	}
	public void setTotalRatedPower(Double totalRatedPower) {
		this.totalRatedPower = totalRatedPower;
	}
	public Integer getNoOfSmallCell() {
		return noOfSmallCell;
	}
	public void setNoOfSmallCell(Integer noOfSmallCell) {
		this.noOfSmallCell = noOfSmallCell;
	}
	public Integer getAreaPerSmallCell() {
		return areaPerSmallCell;
	}
	public void setAreaPerSmallCell(Integer areaPerSmallCell) {
		this.areaPerSmallCell = areaPerSmallCell;
	}
	public Integer getDepthPerSmallCell() {
		return depthPerSmallCell;
	}
	public void setDepthPerSmallCell(Integer depthPerSmallCell) {
		this.depthPerSmallCell = depthPerSmallCell;
	}
	public Integer getHeightOfHighestAntenna() {
		return heightOfHighestAntenna;
	}
	public void setHeightOfHighestAntenna(Integer heightOfHighestAntenna) {
		this.heightOfHighestAntenna = heightOfHighestAntenna;
	}
	public Integer getWeightOfSmallCell() {
		return weightOfSmallCell;
	}
	public void setWeightOfSmallCell(Integer weightOfSmallCell) {
		this.weightOfSmallCell = weightOfSmallCell;
	}
	public Integer getRatedPower() {
		return ratedPower;
	}
	public void setRatedPower(Integer ratedPower) {
		this.ratedPower = ratedPower;
	}
	public Integer getuSpace() {
		return uSpace;
	}
	public void setuSpace(Integer uSpace) {
		this.uSpace = uSpace;
	}
	public String getCowType() {
		return cowType;
	}
	public void setCowType(String cowType) {
		this.cowType = cowType;
	}
	public String getServiceContractPeriod() {
		return serviceContractPeriod;
	}
	public void setServiceContractPeriod(String serviceContractPeriod) {
		this.serviceContractPeriod = serviceContractPeriod;
	}
	public String getPowerSupply() {
		return powerSupply;
	}
	public void setPowerSupply(String powerSupply) {
		this.powerSupply = powerSupply;
	}
	public Double getPoleHeight() {
		return poleHeight;
	}
	public void setPoleHeight(Double poleHeight) {
		this.poleHeight = poleHeight;
	}
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
	public String getCamuflauging() {
		return camuflauging;
	}
	public void setCamuflauging(String camuflauging) {
		this.camuflauging = camuflauging;
	}
	public String getAcbackupRequired2Hrs() {
		return acbackupRequired2Hrs;
	}
	public void setAcbackupRequired2Hrs(String acbackupRequired2Hrs) {
		this.acbackupRequired2Hrs = acbackupRequired2Hrs;
	}
	public String getAdditionalODSCRequired() {
		return additionalODSCRequired;
	}
	public void setAdditionalODSCRequired(String additionalODSCRequired) {
		this.additionalODSCRequired = additionalODSCRequired;
	}
	public Double getAdditionalPowerRequired() {
		return additionalPowerRequired;
	}
	public void setAdditionalPowerRequired(Double additionalPowerRequired) {
		this.additionalPowerRequired = additionalPowerRequired;
	}
	public String getExistingLLROfTVISite() {
		return existingLLROfTVISite;
	}
	public void setExistingLLROfTVISite(String existingLLROfTVISite) {
		this.existingLLROfTVISite = existingLLROfTVISite;
	}
	public String getAdditionalLLRDueToAdditionalODSC() {
		return additionalLLRDueToAdditionalODSC;
	}
	public void setAdditionalLLRDueToAdditionalODSC(String additionalLLRDueToAdditionalODSC) {
		this.additionalLLRDueToAdditionalODSC = additionalLLRDueToAdditionalODSC;
	}
	public String getCumulativePANIndiaODCSSharingSOCount() {
		return cumulativePANIndiaODCSSharingSOCount;
	}
	public void setCumulativePANIndiaODCSSharingSOCount(String cumulativePANIndiaODCSSharingSOCount) {
		this.cumulativePANIndiaODCSSharingSOCount = cumulativePANIndiaODCSSharingSOCount;
	}
	public String getConversionOfSharingODSCIntoMacroSite() {
		return conversionOfSharingODSCIntoMacroSite;
	}
	public void setConversionOfSharingODSCIntoMacroSite(String conversionOfSharingODSCIntoMacroSite) {
		this.conversionOfSharingODSCIntoMacroSite = conversionOfSharingODSCIntoMacroSite;
	}
	public String getCurrentTab() {
		return currentTab;
	}
	public void setCurrentTab(String currentTab) {
		this.currentTab = currentTab;
	}
	public Integer getRatedPowerConsumption() {
		return ratedPowerConsumption;
	}
	public void setRatedPowerConsumption(Integer ratedPowerConsumption) {
		this.ratedPowerConsumption = ratedPowerConsumption;
	}
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
	public List<MassiveMIMORequest> getMassiveMIMOAntennaList() {
		return massiveMIMOAntennaList;
	}
	public void setMassiveMIMOAntennaList(List<MassiveMIMORequest> massiveMIMOAntennaList) {
		this.massiveMIMOAntennaList = massiveMIMOAntennaList;
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
	public String getPendingTo() {
		return pendingTo;
	}
	public void setPendingTo(String pendingTo) {
		this.pendingTo = pendingTo;
	}
	
	public String getPendingFor() {
		return pendingFor;
	}
	public void setPendingFor(String pendingFor) {
		this.pendingFor = pendingFor;
	}
	public String getBoqAttachment() {
		return boqAttachment;
	}
	public void setBoqAttachment(String boqAttachment) {
		this.boqAttachment = boqAttachment;
	}
	public String getHoAqAttachment() {
		return hoAqAttachment;
	}
	public void setHoAqAttachment(String hoAqAttachment) {
		this.hoAqAttachment = hoAqAttachment;
	}
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
	public String getHoProjectHeadAttachment() {
		return hoProjectHeadAttachment;
	}
	public void setHoProjectHeadAttachment(String hoProjectHeadAttachment) {
		this.hoProjectHeadAttachment = hoProjectHeadAttachment;
	}
	public String getHoCooAttachment() {
		return hoCooAttachment;
	}
	public void setHoCooAttachment(String hoCooAttachment) {
		this.hoCooAttachment = hoCooAttachment;
	}
	public String getRfiDate() {
		return rfiDate;
	}
	public void setRfiDate(String rfiDate) {
		this.rfiDate = rfiDate;
	}
	
	public String getRfiAcceptedDate() {
		return rfiAcceptedDate;
	}
	public void setRfiAcceptedDate(String rfiAcceptedDate) {
		this.rfiAcceptedDate = rfiAcceptedDate;
	}
	public String getRfsDate() {
		return rfsDate;
	}
	public void setRfsDate(String rfsDate) {
		this.rfsDate = rfsDate;
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
	public String getSuggestedTownVillage() {
		return suggestedTownVillage;
	}
	public void setSuggestedTownVillage(String suggestedTownVillage) {
		this.suggestedTownVillage = suggestedTownVillage;
	}
	public String getSuggestedDistrict() {
		return suggestedDistrict;
	}
	public void setSuggestedDistrict(String suggestedDistrict) {
		this.suggestedDistrict = suggestedDistrict;
	}
	public String getSuggestedState() {
		return suggestedState;
	}
	public void setSuggestedState(String suggestedState) {
		this.suggestedState = suggestedState;
	}
	public String getSuggestedClutter() {
		return suggestedClutter;
	}
	public void setSuggestedClutter(String suggestedClutter) {
		this.suggestedClutter = suggestedClutter;
	}
	public String getSuggestedSiteType() {
		return suggestedSiteType;
	}
	public void setSuggestedSiteType(String suggestedSiteType) {
		this.suggestedSiteType = suggestedSiteType;
	}
	public Integer getSuggestedPincode() {
		return suggestedPincode;
	}
	public void setSuggestedPincode(Integer suggestedPincode) {
		this.suggestedPincode = suggestedPincode;
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
	public String getSrRaiseDate() {
		return srRaiseDate;
	}
	public void setSrRaiseDate(String srRaiseDate) {
		this.srRaiseDate = srRaiseDate;
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
	public String getSuggestedDgAvailability() {
		return suggestedDgAvailability;
	}
	public void setSuggestedDgAvailability(String suggestedDgAvailability) {
		this.suggestedDgAvailability = suggestedDgAvailability;
	}
	public String getSuggestedPropertyTax() {
		return suggestedPropertyTax;
	}
	public void setSuggestedPropertyTax(String suggestedPropertyTax) {
		this.suggestedPropertyTax = suggestedPropertyTax;
	}
	public String getSuggestedMunicipalTax() {
		return suggestedMunicipalTax;
	}
	public void setSuggestedMunicipalTax(String suggestedMunicipalTax) {
		this.suggestedMunicipalTax = suggestedMunicipalTax;
	}
	public String getAggrementAttachment() {
		return aggrementAttachment;
	}
	public void setAggrementAttachment(String aggrementAttachment) {
		this.aggrementAttachment = aggrementAttachment;
	}
	public String getSpRaiseDate() {
		return spRaiseDate;
	}
	public void setSpRaiseDate(String spRaiseDate) {
		this.spRaiseDate = spRaiseDate;
	}
	public String getSoRaiseDate() {
		return soRaiseDate;
	}
	public void setSoRaiseDate(String soRaiseDate) {
		this.soRaiseDate = soRaiseDate;
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
	public String getRbhAttachment() {
		return rbhAttachment;
	}
	public void setRbhAttachment(String rbhAttachment) {
		this.rbhAttachment = rbhAttachment;
	}
	public String getHoSnMAttachment() {
		return hoSnMAttachment;
	}
	public void setHoSnMAttachment(String hoSnMAttachment) {
		this.hoSnMAttachment = hoSnMAttachment;
	}
	public String getHoRfPlanningAttachment() {
		return hoRfPlanningAttachment;
	}
	public void setHoRfPlanningAttachment(String hoRfPlanningAttachment) {
		this.hoRfPlanningAttachment = hoRfPlanningAttachment;
	}
	public String getOpcoAttachment() {
		return opcoAttachment;
	}
	public void setOpcoAttachment(String opcoAttachment) {
		this.opcoAttachment = opcoAttachment;
	}
	public String getLoginEmpRole() {
		return loginEmpRole;
	}
	public void setLoginEmpRole(String loginEmpRole) {
		this.loginEmpRole = loginEmpRole;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getClass_A_District() {
		return class_A_District;
	}
	public void setClass_A_District(String class_A_District) {
		this.class_A_District = class_A_District;
	}
	public String getClass_B_District() {
		return class_B_District;
	}
	public void setClass_B_District(String class_B_District) {
		this.class_B_District = class_B_District;
	}
	public String getClass_C_District() {
		return class_C_District;
	}
	public void setClass_C_District(String class_C_District) {
		this.class_C_District = class_C_District;
	}
	public String getCityClass() {
		return cityClass;
	}
	public void setCityClass(String cityClass) {
		this.cityClass = cityClass;
	}
	public Integer getCityPremiumPercentage() {
		return cityPremiumPercentage;
	}
	public void setCityPremiumPercentage(Integer cityPremiumPercentage) {
		this.cityPremiumPercentage = cityPremiumPercentage;
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
	public String getOdscModelType() {
		return odscModelType;
	}
	public void setOdscModelType(String odscModelType) {
		this.odscModelType = odscModelType;
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
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public Integer getNoOfIP55() {
		return noOfIP55;
	}
	public void setNoOfIP55(Integer noOfIP55) {
		this.noOfIP55 = noOfIP55;
	}
	public List<IP55Request> getIp55List() {
		return ip55List;
	}
	public void setIp55List(List<IP55Request> ip55List) {
		this.ip55List = ip55List;
	}
	public Integer getNoOfRRU_Delete() {
		return noOfRRU_Delete;
	}
	public void setNoOfRRU_Delete(Integer noOfRRU_Delete) {
		this.noOfRRU_Delete = noOfRRU_Delete;
	}
	public List<RRU_SwapRequest> getRruDeleteList() {
		return rruDeleteList;
	}
	public void setRruDeleteList(List<RRU_SwapRequest> rruDeleteList) {
		this.rruDeleteList = rruDeleteList;
	}
	public Integer getNoOfRRU_Add() {
		return noOfRRU_Add;
	}
	public void setNoOfRRU_Add(Integer noOfRRU_Add) {
		this.noOfRRU_Add = noOfRRU_Add;
	}
	public List<RRU_SwapRequest> getRruAddList() {
		return rruAddList;
	}
	public void setRruAddList(List<RRU_SwapRequest> rruAddList) {
		this.rruAddList = rruAddList;
	}
	public String getExtensionType() {
		return extensionType;
	}
	public void setExtensionType(String extensionType) {
		this.extensionType = extensionType;
	}
	public Double getPowerRequired() {
		return powerRequired;
	}
	public void setPowerRequired(Double powerRequired) {
		this.powerRequired = powerRequired;
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
	public String getCircleOperationHeadAttachment() {
		return circleOperationHeadAttachment;
	}
	public void setCircleOperationHeadAttachment(String circleOperationHeadAttachment) {
		this.circleOperationHeadAttachment = circleOperationHeadAttachment;
	}
	public Double getFiberLength() {
		return fiberLength;
	}
	public void setFiberLength(Double fiberLength) {
		this.fiberLength = fiberLength;
	}
	public Integer getNoOfBBU_Delete() {
		return noOfBBU_Delete;
	}
	public void setNoOfBBU_Delete(Integer noOfBBU_Delete) {
		this.noOfBBU_Delete = noOfBBU_Delete;
	}
	public List<BBURequest> getBbuDeleteList() {
		return bbuDeleteList;
	}
	public void setBbuDeleteList(List<BBURequest> bbuDeleteList) {
		this.bbuDeleteList = bbuDeleteList;
	}
	public Integer getNoOfBBU_Add() {
		return noOfBBU_Add;
	}
	public void setNoOfBBU_Add(Integer noOfBBU_Add) {
		this.noOfBBU_Add = noOfBBU_Add;
	}
	public List<BBURequest> getBbuAddList() {
		return bbuAddList;
	}
	public void setBbuAddList(List<BBURequest> bbuAddList) {
		this.bbuAddList = bbuAddList;
	}
	public Integer getNoOfMicrowave_Delete() {
		return noOfMicrowave_Delete;
	}
	public void setNoOfMicrowave_Delete(Integer noOfMicrowave_Delete) {
		this.noOfMicrowave_Delete = noOfMicrowave_Delete;
	}
	public List<MicrowaveRequest> getMicrowaveDeleteList() {
		return microwaveDeleteList;
	}
	public void setMicrowaveDeleteList(List<MicrowaveRequest> microwaveDeleteList) {
		this.microwaveDeleteList = microwaveDeleteList;
	}
	public Integer getNoOfMicrowave_Add() {
		return noOfMicrowave_Add;
	}
	public void setNoOfMicrowave_Add(Integer noOfMicrowave_Add) {
		this.noOfMicrowave_Add = noOfMicrowave_Add;
	}
	public List<MicrowaveRequest> getMicrowaveAddList() {
		return microwaveAddList;
	}
	public void setMicrowaveAddList(List<MicrowaveRequest> microwaveAddList) {
		this.microwaveAddList = microwaveAddList;
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
	public Integer getMcbRequiredInAmp() {
		return mcbRequiredInAmp;
	}
	public void setMcbRequiredInAmp(Integer mcbRequiredInAmp) {
		this.mcbRequiredInAmp = mcbRequiredInAmp;
	}
	public Integer getCountOfRfFilter() {
		return countOfRfFilter;
	}
	public void setCountOfRfFilter(Integer countOfRfFilter) {
		this.countOfRfFilter = countOfRfFilter;
	}
	public String getRowSpace() {
		return rowSpace;
	}
	public void setRowSpace(String rowSpace) {
		this.rowSpace = rowSpace;
	}
	public Integer getAcPower() {
		return acPower;
	}
	public void setAcPower(Integer acPower) {
		this.acPower = acPower;
	}
	public Integer getNoOfMcb() {
		return noOfMcb;
	}
	public void setNoOfMcb(Integer noOfMcb) {
		this.noOfMcb = noOfMcb;
	}
	public Integer getMcbAmp() {
		return mcbAmp;
	}
	public void setMcbAmp(Integer mcbAmp) {
		this.mcbAmp = mcbAmp;
	}
	public String getDgStatus() {
		return dgStatus;
	}
	public void setDgStatus(String dgStatus) {
		this.dgStatus = dgStatus;
	}
	public String getBatteryBackup() {
		return batteryBackup;
	}
	public void setBatteryBackup(String batteryBackup) {
		this.batteryBackup = batteryBackup;
	}
	public Integer getNoOfRFAntenna_Delete() {
		return noOfRFAntenna_Delete;
	}
	public void setNoOfRFAntenna_Delete(Integer noOfRFAntenna_Delete) {
		this.noOfRFAntenna_Delete = noOfRFAntenna_Delete;
	}
	public List<RFAntennaRequest> getRfAntennaDeleteList() {
		return rfAntennaDeleteList;
	}
	public void setRfAntennaDeleteList(List<RFAntennaRequest> rfAntennaDeleteList) {
		this.rfAntennaDeleteList = rfAntennaDeleteList;
	}
	public Integer getNoOfRFAntenna_Add() {
		return noOfRFAntenna_Add;
	}
	public void setNoOfRFAntenna_Add(Integer noOfRFAntenna_Add) {
		this.noOfRFAntenna_Add = noOfRFAntenna_Add;
	}
	public List<RFAntennaRequest> getRfAntennaAddList() {
		return rfAntennaAddList;
	}
	public void setRfAntennaAddList(List<RFAntennaRequest> rfAntennaAddList) {
		this.rfAntennaAddList = rfAntennaAddList;
	}
	public Integer getNoOfPole() {
		return noOfPole;
	}
	public void setNoOfPole(Integer noOfPole) {
		this.noOfPole = noOfPole;
	}
	public List<PoleRequest> getNoOfPoleList() {
		return noOfPoleList;
	}
	public void setNoOfPoleList(List<PoleRequest> noOfPoleList) {
		this.noOfPoleList = noOfPoleList;
	}
	public Integer getNoOfHPSCAntenna() {
		return noOfHPSCAntenna;
	}
	public void setNoOfHPSCAntenna(Integer noOfHPSCAntenna) {
		this.noOfHPSCAntenna = noOfHPSCAntenna;
	}
	public List<HPSCAntennaRequest> getNoOfHPSCAntennaList() {
		return noOfHPSCAntennaList;
	}
	public void setNoOfHPSCAntennaList(List<HPSCAntennaRequest> noOfHPSCAntennaList) {
		this.noOfHPSCAntennaList = noOfHPSCAntennaList;
	}
	public Double getPowerRatingOfEquipment() {
		return powerRatingOfEquipment;
	}
	public void setPowerRatingOfEquipment(Double powerRatingOfEquipment) {
		this.powerRatingOfEquipment = powerRatingOfEquipment;
	}
	public Double getSuggestedElectrificationCharges() {
		return suggestedElectrificationCharges;
	}
	public void setSuggestedElectrificationCharges(Double suggestedElectrificationCharges) {
		this.suggestedElectrificationCharges = suggestedElectrificationCharges;
	}
	public Double getSuggestedMcCharges() {
		return suggestedMcCharges;
	}
	public void setSuggestedMcCharges(Double suggestedMcCharges) {
		this.suggestedMcCharges = suggestedMcCharges;
	}
	public String getSharingPotential() {
		return sharingPotential;
	}
	public void setSharingPotential(String sharingPotential) {
		this.sharingPotential = sharingPotential;
	}
	public Double getTotalWeightOnTower() {
		return totalWeightOnTower;
	}
	public void setTotalWeightOnTower(Double totalWeightOnTower) {
		this.totalWeightOnTower = totalWeightOnTower;
	}
	public String getNbsProductType() {
		return nbsProductType;
	}
	public void setNbsProductType(String nbsProductType) {
		this.nbsProductType = nbsProductType;
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
	public List<ODSCRequest> getViewOdscList() {
		return viewOdscList;
	}
	public void setViewOdscList(List<ODSCRequest> viewOdscList) {
		this.viewOdscList = viewOdscList;
	}
	public List<GSMAntennaRequest> getViewGsmAntennaList() {
		return viewGsmAntennaList;
	}
	public void setViewGsmAntennaList(List<GSMAntennaRequest> viewGsmAntennaList) {
		this.viewGsmAntennaList = viewGsmAntennaList;
	}
	public List<MicrowaveRequest> getViewMicrowaveList() {
		return viewMicrowaveList;
	}
	public void setViewMicrowaveList(List<MicrowaveRequest> viewMicrowaveList) {
		this.viewMicrowaveList = viewMicrowaveList;
	}
	public List<RFAntennaRequest> getViewRfAntennaList() {
		return viewRfAntennaList;
	}
	public void setViewRfAntennaList(List<RFAntennaRequest> viewRfAntennaList) {
		this.viewRfAntennaList = viewRfAntennaList;
	}
	public List<RFAntennaRequest> getViewRfAntennaDeleteList() {
		return viewRfAntennaDeleteList;
	}
	public void setViewRfAntennaDeleteList(List<RFAntennaRequest> viewRfAntennaDeleteList) {
		this.viewRfAntennaDeleteList = viewRfAntennaDeleteList;
	}
	public List<RFAntennaRequest> getViewRfAntennaAddList() {
		return viewRfAntennaAddList;
	}
	public void setViewRfAntennaAddList(List<RFAntennaRequest> viewRfAntennaAddList) {
		this.viewRfAntennaAddList = viewRfAntennaAddList;
	}
	public List<BBURequest> getViewBbuList() {
		return viewBbuList;
	}
	public void setViewBbuList(List<BBURequest> viewBbuList) {
		this.viewBbuList = viewBbuList;
	}
	public List<RRURequest> getViewRruList() {
		return viewRruList;
	}
	public void setViewRruList(List<RRURequest> viewRruList) {
		this.viewRruList = viewRruList;
	}
	public List<BTSRequest> getViewBtsList() {
		return viewBtsList;
	}
	public void setViewBtsList(List<BTSRequest> viewBtsList) {
		this.viewBtsList = viewBtsList;
	}
	public List<MCBRequest> getViewMcbList() {
		return viewMcbList;
	}
	public void setViewMcbList(List<MCBRequest> viewMcbList) {
		this.viewMcbList = viewMcbList;
	}
	public List<MassiveMIMORequest> getViewMimoList() {
		return viewMimoList;
	}
	public void setViewMimoList(List<MassiveMIMORequest> viewMimoList) {
		this.viewMimoList = viewMimoList;
	}
	public List<IP55Request> getViewIp55List() {
		return viewIp55List;
	}
	public void setViewIp55List(List<IP55Request> viewIp55List) {
		this.viewIp55List = viewIp55List;
	}
	public List<RRU_SwapRequest> getViewRruDeleteList() {
		return viewRruDeleteList;
	}
	public void setViewRruDeleteList(List<RRU_SwapRequest> viewRruDeleteList) {
		this.viewRruDeleteList = viewRruDeleteList;
	}
	public List<RRU_SwapRequest> getViewRruAddList() {
		return viewRruAddList;
	}
	public void setViewRruAddList(List<RRU_SwapRequest> viewRruAddList) {
		this.viewRruAddList = viewRruAddList;
	}
	public List<BBURequest> getViewBbuDeleteList() {
		return viewBbuDeleteList;
	}
	public void setViewBbuDeleteList(List<BBURequest> viewBbuDeleteList) {
		this.viewBbuDeleteList = viewBbuDeleteList;
	}
	public List<BBURequest> getViewBbuAddList() {
		return viewBbuAddList;
	}
	public void setViewBbuAddList(List<BBURequest> viewBbuAddList) {
		this.viewBbuAddList = viewBbuAddList;
	}
	public List<MicrowaveRequest> getViewMicrowaveDeleteList() {
		return viewMicrowaveDeleteList;
	}
	public void setViewMicrowaveDeleteList(List<MicrowaveRequest> viewMicrowaveDeleteList) {
		this.viewMicrowaveDeleteList = viewMicrowaveDeleteList;
	}
	public List<MicrowaveRequest> getViewMicrowaveAddList() {
		return viewMicrowaveAddList;
	}
	public void setViewMicrowaveAddList(List<MicrowaveRequest> viewMicrowaveAddList) {
		this.viewMicrowaveAddList = viewMicrowaveAddList;
	}
	public List<PoleRequest> getViewPoleList() {
		return viewPoleList;
	}
	public void setViewPoleList(List<PoleRequest> viewPoleList) {
		this.viewPoleList = viewPoleList;
	}
	public List<HPSCAntennaRequest> getViewHPSCAntennaList() {
		return viewHPSCAntennaList;
	}
	public void setViewHPSCAntennaList(List<HPSCAntennaRequest> viewHPSCAntennaList) {
		this.viewHPSCAntennaList = viewHPSCAntennaList;
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
	public String getSrStatus() {
		return srStatus;
	}
	public void setSrStatus(String srStatus) {
		this.srStatus = srStatus;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public Double getSpLatitude() {
		return spLatitude;
	}
	public void setSpLatitude(Double spLatitude) {
		this.spLatitude = spLatitude;
	}
	public Double getSpLongitude() {
		return spLongitude;
	}
	public void setSpLongitude(Double spLongitude) {
		this.spLongitude = spLongitude;
	}
	public String getOrgTabName() {
		return orgTabName;
	}
	public void setOrgTabName(String orgTabName) {
		this.orgTabName = orgTabName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getUpgradeType() {
		return upgradeType;
	}
	public void setUpgradeType(String upgradeType) {
		this.upgradeType = upgradeType;
	}
	public String getRefNumberTvipl() {
		return refNumberTvipl;
	}
	public void setRefNumberTvipl(String refNumberTvipl) {
		this.refNumberTvipl = refNumberTvipl;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public List<ActionButtonDTO> getActionButtonList() {
		return actionButtonList;
	}
	public void setActionButtonList(List<ActionButtonDTO> actionButtonList) {
		this.actionButtonList = actionButtonList;
	}
	public Integer getNoOfAirFiber() {
		return noOfAirFiber;
	}
	public void setNoOfAirFiber(Integer noOfAirFiber) {
		this.noOfAirFiber = noOfAirFiber;
	}
	public List<AirFiberRequest> getAirFiberList() {
		return airFiberList;
	}
	public void setAirFiberList(List<AirFiberRequest> airFiberList) {
		this.airFiberList = airFiberList;
	}
	
	
}
