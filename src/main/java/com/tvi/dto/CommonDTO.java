package com.tvi.dto;

import java.util.List;

import com.tvi.request.BBURequest;
import com.tvi.request.BTSRequest;
import com.tvi.request.HPSCAntennaRequest;
import com.tvi.request.IP55Request;
import com.tvi.request.MCBRequest;
import com.tvi.request.MassiveMIMORequest;
import com.tvi.request.MicrowaveRequest;
import com.tvi.request.ODSCRequest;
import com.tvi.request.PoleRequest;
import com.tvi.request.RFAntennaRequest;
import com.tvi.request.RRURequest;
import com.tvi.request.RRU_SwapRequest;

public class CommonDTO {
	private int pageNum, maxRecord, recordOnApage;
	private String filterSrNumber="",filterCircleName="",filterTviSiteId="",filterSiteId="",filterProductType="",
			filterSrStatus="",filterStartDate="",filterEndDate="",filterOperator="",mobileNumber,newPassword,operator="",
			currentTab,currentStatus="";
	private String loginEmpId,loginEmpRole,isHoUser,circleName,paramCode,paramDesc,district,state,tabName,action;
	private Double ebAvailabilityDistance,suggestedLatitude,suggestedLongitude,suggestedCityPremium,
	suggestedEbAvailablilityDistance,suggestedTowerHeight,suggestedBuildingHeight,totalRatedPower,additionalLoad,windSpeed,loadOfU,
	suggestedDeviation,fiberLength,suggestedElectrificationCharges,suggestedMcCharges,powerRatingOfEquipment,totalWeightOnTower,
	powerRequired,powerConsumption,floorLength;
	private String airtelLocatorId,airtelSiteId,suggestedSiteAddress,suggestedCity,suggestedTownVillage,suggestedDistrict,suggestedState,
		suggestedClutter,suggestedSiteType,suggestedDgAvailability,siteType,fiberEntry;
	private Integer suggestedPincode,suggestedStandardIPFEE,suggestedLLR,suggestedLoading,noOfUSpaceRequired,airtelCityPremiumPercentage,
	cityPremiumPercentage,uSpace,uSpaceIwan,uSpaceMcu,uSpaceTcl,noOfUBR_Antenna,weightOfAntenna,uSpace_ethernet,mcbRequiredInAmp;
	private String srNumber,spNumber="",soNumber="",tviSiteId,status,remark,rfiDate,rfiAcceptedDate,rfsDate,seafAttachedStr="",ncsoAttachedStr="",frequencyUserByOpco,
			googleSnapshotStr="",nfaAttachedStr="",boqAttachedStr="",anyAttachedStr="",agreementStr="",rfiAttachedStr="",atAttachedStr="",
			ddrAttachedStr,surveyReportAttachedStr,eSeafPortalUrl,latitude,longitude,odscModelType,typeOfAntenna,rfiDateEditable,rfiAcceptedDateEditable,rfsDateEditable;
	private String suggestedPropertyTax,suggestedMunicipalTax,mwRack,fiberTermination,btsType,cityClass,suggestedWindSpeed,
	suggestedLockTerm,suggestedLandOwnerRent,suggestedTowerType,sharingPotential,nbsProductType,additionalBB,headLoad,ebConnection;
	private boolean isNoOfPolesEdit,isNoOfHpscAntennaEdit,isNoOfBbuEdit,isBBUAdd_Edit,isBBUDelete_Edit,isNoOfRruEdit,isRRUAdd_Edit,
	isRRUDelete_Edit,isRFEdit,isRFAdd_Edit,isRFDelete_Edit,isMWEdit,isMWAdd_Edit,isMWDelete_Edit,isBTSEdit,isMCBEdit,
	isMimoEdit,isIp55Edit,isODSCEdit;
	
	private boolean isAtDocLink,isSurveyReportLink,isRfiDocLink,isNcsoLink,isGoogleSnapshotLink,isBoqLink,isAggrementLink,isAnyLink,
	isDdrLink,isSeafLink;
	
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
	private Integer noOfODSC;
	private List<ODSCRequest> odscList;
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

	public String getLoginEmpId() {
		return loginEmpId;
	}

	public void setLoginEmpId(String loginEmpId) {
		this.loginEmpId = loginEmpId;
	}

	public String getLoginEmpRole() {
		return loginEmpRole;
	}

	public void setLoginEmpRole(String loginEmpRole) {
		this.loginEmpRole = loginEmpRole;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getRfiDate() {
		return rfiDate;
	}

	public void setRfiDate(String rfiDate) {
		this.rfiDate = rfiDate;
	}

	public String getRfsDate() {
		return rfsDate;
	}

	public void setRfsDate(String rfsDate) {
		this.rfsDate = rfsDate;
	}

	public String getSeafAttachedStr() {
		return seafAttachedStr;
	}

	public void setSeafAttachedStr(String seafAttachedStr) {
		this.seafAttachedStr = seafAttachedStr;
	}

	public String getNcsoAttachedStr() {
		return ncsoAttachedStr;
	}

	public void setNcsoAttachedStr(String ncsoAttachedStr) {
		this.ncsoAttachedStr = ncsoAttachedStr;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getGoogleSnapshotStr() {
		return googleSnapshotStr;
	}

	public void setGoogleSnapshotStr(String googleSnapshotStr) {
		this.googleSnapshotStr = googleSnapshotStr;
	}

	public String getNfaAttachedStr() {
		return nfaAttachedStr;
	}

	public void setNfaAttachedStr(String nfaAttachedStr) {
		this.nfaAttachedStr = nfaAttachedStr;
	}

	public String getIsHoUser() {
		return isHoUser;
	}

	public void setIsHoUser(String isHoUser) {
		this.isHoUser = isHoUser;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getBoqAttachedStr() {
		return boqAttachedStr;
	}

	public void setBoqAttachedStr(String boqAttachedStr) {
		this.boqAttachedStr = boqAttachedStr;
	}

	public String getAnyAttachedStr() {
		return anyAttachedStr;
	}

	public void setAnyAttachedStr(String anyAttachedStr) {
		this.anyAttachedStr = anyAttachedStr;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFilterSrNumber() {
		return filterSrNumber;
	}

	public void setFilterSrNumber(String filterSrNumber) {
		this.filterSrNumber = filterSrNumber;
	}

	public String getFilterStartDate() {
		return filterStartDate;
	}

	public void setFilterStartDate(String filterStartDate) {
		this.filterStartDate = filterStartDate;
	}

	public String getFilterEndDate() {
		return filterEndDate;
	}

	public void setFilterEndDate(String filterEndDate) {
		this.filterEndDate = filterEndDate;
	}

	public String getRfiAcceptedDate() {
		return rfiAcceptedDate;
	}

	public void setRfiAcceptedDate(String rfiAcceptedDate) {
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

	public String getSuggestedSiteType() {
		return suggestedSiteType;
	}

	public void setSuggestedSiteType(String suggestedSiteType) {
		this.suggestedSiteType = suggestedSiteType;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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

	public String getSuggestedDgAvailability() {
		return suggestedDgAvailability;
	}

	public void setSuggestedDgAvailability(String suggestedDgAvailability) {
		this.suggestedDgAvailability = suggestedDgAvailability;
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

	public Integer getNoOfBBU() {
		return noOfBBU;
	}

	public void setNoOfBBU(Integer noOfBBU) {
		this.noOfBBU = noOfBBU;
	}

	public List<BBURequest> getBbuList() {
		return bbuList;
	}

	public void setBbuList(List<BBURequest> bbuList) {
		this.bbuList = bbuList;
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

	public List<BTSRequest> getBtsList() {
		return btsList;
	}

	public void setBtsList(List<BTSRequest> btsList) {
		this.btsList = btsList;
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

	public String getAgreementStr() {
		return agreementStr;
	}

	public void setAgreementStr(String agreementStr) {
		this.agreementStr = agreementStr;
	}

	public Double getTotalRatedPower() {
		return totalRatedPower;
	}

	public void setTotalRatedPower(Double totalRatedPower) {
		this.totalRatedPower = totalRatedPower;
	}

	public Double getAdditionalLoad() {
		return additionalLoad;
	}

	public void setAdditionalLoad(Double additionalLoad) {
		this.additionalLoad = additionalLoad;
	}

	public Double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Integer getNoOfUSpaceRequired() {
		return noOfUSpaceRequired;
	}

	public void setNoOfUSpaceRequired(Integer noOfUSpaceRequired) {
		this.noOfUSpaceRequired = noOfUSpaceRequired;
	}

	public String getFrequencyUserByOpco() {
		return frequencyUserByOpco;
	}

	public void setFrequencyUserByOpco(String frequencyUserByOpco) {
		this.frequencyUserByOpco = frequencyUserByOpco;
	}

	public String getMwRack() {
		return mwRack;
	}

	public void setMwRack(String mwRack) {
		this.mwRack = mwRack;
	}

	public String getFiberTermination() {
		return fiberTermination;
	}

	public void setFiberTermination(String fiberTermination) {
		this.fiberTermination = fiberTermination;
	}

	public String getBtsType() {
		return btsType;
	}

	public void setBtsType(String btsType) {
		this.btsType = btsType;
	}

	public Double getLoadOfU() {
		return loadOfU;
	}

	public void setLoadOfU(Double loadOfU) {
		this.loadOfU = loadOfU;
	}

	public String getRfiAttachedStr() {
		return rfiAttachedStr;
	}

	public void setRfiAttachedStr(String rfiAttachedStr) {
		this.rfiAttachedStr = rfiAttachedStr;
	}

	public String getAtAttachedStr() {
		return atAttachedStr;
	}

	public void setAtAttachedStr(String atAttachedStr) {
		this.atAttachedStr = atAttachedStr;
	}

	public String getDdrAttachedStr() {
		return ddrAttachedStr;
	}

	public void setDdrAttachedStr(String ddrAttachedStr) {
		this.ddrAttachedStr = ddrAttachedStr;
	}

	public String getSurveyReportAttachedStr() {
		return surveyReportAttachedStr;
	}

	public void setSurveyReportAttachedStr(String surveyReportAttachedStr) {
		this.surveyReportAttachedStr = surveyReportAttachedStr;
	}

	public String getAirtelLocatorId() {
		return airtelLocatorId;
	}

	public void setAirtelLocatorId(String airtelLocatorId) {
		this.airtelLocatorId = airtelLocatorId;
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

	public Integer getAirtelCityPremiumPercentage() {
		return airtelCityPremiumPercentage;
	}

	public void setAirtelCityPremiumPercentage(Integer airtelCityPremiumPercentage) {
		this.airtelCityPremiumPercentage = airtelCityPremiumPercentage;
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

	public String getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(String currentTab) {
		this.currentTab = currentTab;
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

	public String getSuggestedLandOwnerRent() {
		return suggestedLandOwnerRent;
	}

	public void setSuggestedLandOwnerRent(String suggestedLandOwnerRent) {
		this.suggestedLandOwnerRent = suggestedLandOwnerRent;
	}

	public Double getSuggestedBuildingHeight() {
		return suggestedBuildingHeight;
	}

	public void setSuggestedBuildingHeight(Double suggestedBuildingHeight) {
		this.suggestedBuildingHeight = suggestedBuildingHeight;
	}

	public String getSuggestedTowerType() {
		return suggestedTowerType;
	}

	public void setSuggestedTowerType(String suggestedTowerType) {
		this.suggestedTowerType = suggestedTowerType;
	}

	public Double getSuggestedDeviation() {
		return suggestedDeviation;
	}

	public void setSuggestedDeviation(Double suggestedDeviation) {
		this.suggestedDeviation = suggestedDeviation;
	}

	public String getOdscModelType() {
		return odscModelType;
	}

	public void setOdscModelType(String odscModelType) {
		this.odscModelType = odscModelType;
	}

	public String getFilterCircleName() {
		return filterCircleName;
	}

	public void setFilterCircleName(String filterCircleName) {
		this.filterCircleName = filterCircleName;
	}

	public String getFilterProductType() {
		return filterProductType;
	}

	public void setFilterProductType(String filterProductType) {
		this.filterProductType = filterProductType;
	}

	public String getTviSiteId() {
		return tviSiteId;
	}

	public void setTviSiteId(String tviSiteId) {
		this.tviSiteId = tviSiteId;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getRecordOnApage() {
		return recordOnApage;
	}

	public void setRecordOnApage(int recordOnApage) {
		this.recordOnApage = recordOnApage;
	}

	public int getMaxRecord() {
		return maxRecord;
	}

	public void setMaxRecord(int maxRecord) {
		this.maxRecord = maxRecord;
	}

	public String getAirtelSiteId() {
		return airtelSiteId;
	}

	public void setAirtelSiteId(String airtelSiteId) {
		this.airtelSiteId = airtelSiteId;
	}

	public Double getFiberLength() {
		return fiberLength;
	}

	public void setFiberLength(Double fiberLength) {
		this.fiberLength = fiberLength;
	}

	public String getFilterSrStatus() {
		return filterSrStatus;
	}

	public void setFilterSrStatus(String filterSrStatus) {
		this.filterSrStatus = filterSrStatus;
	}

	public String getFilterOperator() {
		return filterOperator;
	}

	public void setFilterOperator(String filterOperator) {
		this.filterOperator = filterOperator;
	}

	public String getFilterSiteId() {
		return filterSiteId;
	}

	public void setFilterSiteId(String filterSiteId) {
		this.filterSiteId = filterSiteId;
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

	public String getFilterTviSiteId() {
		return filterTviSiteId;
	}

	public void setFilterTviSiteId(String filterTviSiteId) {
		this.filterTviSiteId = filterTviSiteId;
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

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public Integer getuSpace() {
		return uSpace;
	}

	public void setuSpace(Integer uSpace) {
		this.uSpace = uSpace;
	}

	public boolean isNoOfPolesEdit() {
		return isNoOfPolesEdit;
	}

	public void setNoOfPolesEdit(boolean isNoOfPolesEdit) {
		this.isNoOfPolesEdit = isNoOfPolesEdit;
	}

	public boolean isNoOfHpscAntennaEdit() {
		return isNoOfHpscAntennaEdit;
	}

	public void setNoOfHpscAntennaEdit(boolean isNoOfHpscAntennaEdit) {
		this.isNoOfHpscAntennaEdit = isNoOfHpscAntennaEdit;
	}

	public boolean isNoOfBbuEdit() {
		return isNoOfBbuEdit;
	}

	public void setNoOfBbuEdit(boolean isNoOfBbuEdit) {
		this.isNoOfBbuEdit = isNoOfBbuEdit;
	}

	public boolean isNoOfRruEdit() {
		return isNoOfRruEdit;
	}

	public void setNoOfRruEdit(boolean isNoOfRruEdit) {
		this.isNoOfRruEdit = isNoOfRruEdit;
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

	public boolean isRFEdit() {
		return isRFEdit;
	}

	public void setRFEdit(boolean isRFEdit) {
		this.isRFEdit = isRFEdit;
	}

	public boolean isMWEdit() {
		return isMWEdit;
	}

	public void setMWEdit(boolean isMWEdit) {
		this.isMWEdit = isMWEdit;
	}

	public boolean isBTSEdit() {
		return isBTSEdit;
	}

	public void setBTSEdit(boolean isBTSEdit) {
		this.isBTSEdit = isBTSEdit;
	}

	public boolean isMCBEdit() {
		return isMCBEdit;
	}

	public void setMCBEdit(boolean isMCBEdit) {
		this.isMCBEdit = isMCBEdit;
	}

	public boolean isBBUAdd_Edit() {
		return isBBUAdd_Edit;
	}

	public void setBBUAdd_Edit(boolean isBBUAdd_Edit) {
		this.isBBUAdd_Edit = isBBUAdd_Edit;
	}

	public boolean isBBUDelete_Edit() {
		return isBBUDelete_Edit;
	}

	public void setBBUDelete_Edit(boolean isBBUDelete_Edit) {
		this.isBBUDelete_Edit = isBBUDelete_Edit;
	}

	public boolean isRRUAdd_Edit() {
		return isRRUAdd_Edit;
	}

	public void setRRUAdd_Edit(boolean isRRUAdd_Edit) {
		this.isRRUAdd_Edit = isRRUAdd_Edit;
	}

	public boolean isRRUDelete_Edit() {
		return isRRUDelete_Edit;
	}

	public void setRRUDelete_Edit(boolean isRRUDelete_Edit) {
		this.isRRUDelete_Edit = isRRUDelete_Edit;
	}

	public boolean isRFAdd_Edit() {
		return isRFAdd_Edit;
	}

	public void setRFAdd_Edit(boolean isRFAdd_Edit) {
		this.isRFAdd_Edit = isRFAdd_Edit;
	}

	public boolean isRFDelete_Edit() {
		return isRFDelete_Edit;
	}

	public void setRFDelete_Edit(boolean isRFDelete_Edit) {
		this.isRFDelete_Edit = isRFDelete_Edit;
	}

	public boolean isMWAdd_Edit() {
		return isMWAdd_Edit;
	}

	public void setMWAdd_Edit(boolean isMWAdd_Edit) {
		this.isMWAdd_Edit = isMWAdd_Edit;
	}

	public boolean isMWDelete_Edit() {
		return isMWDelete_Edit;
	}

	public void setMWDelete_Edit(boolean isMWDelete_Edit) {
		this.isMWDelete_Edit = isMWDelete_Edit;
	}

	public boolean isMimoEdit() {
		return isMimoEdit;
	}

	public void setMimoEdit(boolean isMimoEdit) {
		this.isMimoEdit = isMimoEdit;
	}

	public boolean isIp55Edit() {
		return isIp55Edit;
	}

	public void setIp55Edit(boolean isIp55Edit) {
		this.isIp55Edit = isIp55Edit;
	}

	public Integer getNoOfMassiveMIMOAntenna() {
		return noOfMassiveMIMOAntenna;
	}

	public void setNoOfMassiveMIMOAntenna(Integer noOfMassiveMIMOAntenna) {
		this.noOfMassiveMIMOAntenna = noOfMassiveMIMOAntenna;
	}

	public List<MassiveMIMORequest> getMassiveMIMOAntennaList() {
		return massiveMIMOAntennaList;
	}

	public void setMassiveMIMOAntennaList(List<MassiveMIMORequest> massiveMIMOAntennaList) {
		this.massiveMIMOAntennaList = massiveMIMOAntennaList;
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

	public Double getPowerRequired() {
		return powerRequired;
	}

	public void setPowerRequired(Double powerRequired) {
		this.powerRequired = powerRequired;
	}

	public Integer getuSpaceIwan() {
		return uSpaceIwan;
	}

	public void setuSpaceIwan(Integer uSpaceIwan) {
		this.uSpaceIwan = uSpaceIwan;
	}

	public Integer getNoOfUBR_Antenna() {
		return noOfUBR_Antenna;
	}

	public void setNoOfUBR_Antenna(Integer noOfUBR_Antenna) {
		this.noOfUBR_Antenna = noOfUBR_Antenna;
	}

	public Integer getWeightOfAntenna() {
		return weightOfAntenna;
	}

	public void setWeightOfAntenna(Integer weightOfAntenna) {
		this.weightOfAntenna = weightOfAntenna;
	}

	public String getTypeOfAntenna() {
		return typeOfAntenna;
	}

	public void setTypeOfAntenna(String typeOfAntenna) {
		this.typeOfAntenna = typeOfAntenna;
	}

	public Integer getuSpaceMcu() {
		return uSpaceMcu;
	}

	public void setuSpaceMcu(Integer uSpaceMcu) {
		this.uSpaceMcu = uSpaceMcu;
	}

	public Double getPowerConsumption() {
		return powerConsumption;
	}

	public void setPowerConsumption(Double powerConsumption) {
		this.powerConsumption = powerConsumption;
	}

	public boolean isODSCEdit() {
		return isODSCEdit;
	}

	public void setODSCEdit(boolean isODSCEdit) {
		this.isODSCEdit = isODSCEdit;
	}

	public Integer getuSpace_ethernet() {
		return uSpace_ethernet;
	}

	public void setuSpace_ethernet(Integer uSpace_ethernet) {
		this.uSpace_ethernet = uSpace_ethernet;
	}

	public Integer getuSpaceTcl() {
		return uSpaceTcl;
	}

	public void setuSpaceTcl(Integer uSpaceTcl) {
		this.uSpaceTcl = uSpaceTcl;
	}

	public Double getFloorLength() {
		return floorLength;
	}

	public void setFloorLength(Double floorLength) {
		this.floorLength = floorLength;
	}

	public String getFiberEntry() {
		return fiberEntry;
	}

	public void setFiberEntry(String fiberEntry) {
		this.fiberEntry = fiberEntry;
	}

	public Integer getMcbRequiredInAmp() {
		return mcbRequiredInAmp;
	}

	public void setMcbRequiredInAmp(Integer mcbRequiredInAmp) {
		this.mcbRequiredInAmp = mcbRequiredInAmp;
	}

	public boolean isAtDocLink() {
		return isAtDocLink;
	}

	public void setAtDocLink(boolean isAtDocLink) {
		this.isAtDocLink = isAtDocLink;
	}

	public boolean isAnyLink() {
		return isAnyLink;
	}

	public void setAnyLink(boolean isAnyLink) {
		this.isAnyLink = isAnyLink;
	}

	public boolean isAggrementLink() {
		return isAggrementLink;
	}

	public void setAggrementLink(boolean isAggrementLink) {
		this.isAggrementLink = isAggrementLink;
	}

	public boolean isBoqLink() {
		return isBoqLink;
	}

	public void setBoqLink(boolean isBoqLink) {
		this.isBoqLink = isBoqLink;
	}

	public boolean isGoogleSnapshotLink() {
		return isGoogleSnapshotLink;
	}

	public void setGoogleSnapshotLink(boolean isGoogleSnapshotLink) {
		this.isGoogleSnapshotLink = isGoogleSnapshotLink;
	}

	public boolean isNcsoLink() {
		return isNcsoLink;
	}

	public void setNcsoLink(boolean isNcsoLink) {
		this.isNcsoLink = isNcsoLink;
	}

	public boolean isRfiDocLink() {
		return isRfiDocLink;
	}

	public void setRfiDocLink(boolean isRfiDocLink) {
		this.isRfiDocLink = isRfiDocLink;
	}

	public boolean isSurveyReportLink() {
		return isSurveyReportLink;
	}

	public void setSurveyReportLink(boolean isSurveyReportLink) {
		this.isSurveyReportLink = isSurveyReportLink;
	}

	public boolean isDdrLink() {
		return isDdrLink;
	}

	public void setDdrLink(boolean isDdrLink) {
		this.isDdrLink = isDdrLink;
	}

	public boolean isSeafLink() {
		return isSeafLink;
	}

	public void setSeafLink(boolean isSeafLink) {
		this.isSeafLink = isSeafLink;
	}

	public String getRfiDateEditable() {
		return rfiDateEditable;
	}

	public void setRfiDateEditable(String rfiDateEditable) {
		this.rfiDateEditable = rfiDateEditable;
	}

	public String getRfiAcceptedDateEditable() {
		return rfiAcceptedDateEditable;
	}

	public void setRfiAcceptedDateEditable(String rfiAcceptedDateEditable) {
		this.rfiAcceptedDateEditable = rfiAcceptedDateEditable;
	}

	public String getRfsDateEditable() {
		return rfsDateEditable;
	}

	public void setRfsDateEditable(String rfsDateEditable) {
		this.rfsDateEditable = rfsDateEditable;
	}

	public String geteSeafPortalUrl() {
		return eSeafPortalUrl;
	}

	public void seteSeafPortalUrl(String eSeafPortalUrl) {
		this.eSeafPortalUrl = eSeafPortalUrl;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}	
	
	
	
}
