package com.tvi.request;

import java.util.List;

import com.tvi.dto.AttachmentDto;
import com.tvi.dto.ValidBscRncDto;
import com.tvi.dto.ValidBtsDto;
import com.tvi.dto.ValidFibreNodeDto;
import com.tvi.dto.ValidMcbDto;
import com.tvi.dto.ValidMwAntennaDto;
import com.tvi.dto.ValidOtherEquipmentDto;
import com.tvi.dto.ValidOtherNodeDto;
import com.tvi.dto.ValidRadioAntennaDto;
import com.tvi.dto.ValidTmaTmbDto;

public class ChangeAirtelSrStatusRequest {
	private String loginEmpId,loginEmpRole,srNumber,spNumber,soNumber,circleName,
	currentStatus,afterStatus,remark,tabName;
	private List<ValidBtsDto> validBtsList;
	private List<ValidRadioAntennaDto> validRadioAntennaList;
	private List<ValidMwAntennaDto> validMwAntennaList;
	private List<ValidBscRncDto> validBscRncList;
	private List<ValidOtherNodeDto> validOtherNodeList;
	private List<ValidMcbDto> validMcbList;
	private List<ValidFibreNodeDto> validFibreNodeList;
	private List<ValidTmaTmbDto> validTmaTmbList;
	private List<ValidOtherEquipmentDto> validOtherEquipmentList;
	private List<AttachmentDto> attachedList;
	
	private String TOCO_Site_Id,viewTocoSiteId,Date_of_Proposal,Power_Rating,Site_Electrification_Distance,Tentative_EB_Availibility,
	Additional_Charge,Address1,Head_Load_Charge,Electrification_Cost,Electrification_Line_Distance,
	Electricity_Connection_HT_LT,Infra_Details,Site_Classification,Expected_Rent_to_Landlord,Non_Refundable_Deposit,
	Estimated_Deployment_Time__in_days_,Additional_Capex,Standard_Rates,Fiber_Charges,Rental_Threshold,
	Excess_Rent_beyond_Threshold,Tentative_Rental_Premium,Additional_Rent,IPFee,Field_Operation_Charges,
	Security_Guard_Charges,Mobilization_Charges,Erection_Charges,Battery_backup_Hrs,Land_Lord_Charges_or_Rent_Charges,
	Wind_Speed,TowerHeight,Agl,Distance_from_P1_Lat_Long_in_meter,Rejection_Remarks,Difficult,Tower_Completion,
	Shelter_Equipment_RoomReady,AirConditioner_Commissioned,DG_Commissioned,Acceptance_Testing_Of_Site_Infrastructure_Completed_Status,
	EB_Status,Created_By,OFC_Duct_Laying_Done,Access_To_Site_Available_Status,Engineer_Name,Engineer_Phone_Number,
	Notice_Form_Generation_Date,rfiDate,rfsDate,Recommended_Product_Type_by_Acquisition,sharingPotential,Latitude,Longitude,
	clutter;
	
	private String suggestedSiteAddress,suggestedDistrict,suggestedState,suggestedPincode,suggestedTownVillage,
	suggestedCity,suggestedLatitude,suggestedLongitude,suggestedDeviation,suggestedTowerType,suggestedBuildingHeight,
	suggestedTowerHeight,suggestedClutter,suggestedLandOwnerRent,suggestedElectrificationCharges,suggestedMcCharges;
	
	private String Association_AreyouWorkingInAnyBhartiGroup,Association_IfyesmentiontheBhartiUnitName,
	Association_NameOftheEmployee,Association_EmployeeId,Relative_AnyRelativesareWorkingWithBhartiGroup,
	Relative_IfyesmentiontheBhartiUnitName,Relative_NameOftheEmployee,Relative_EmployeeId,
	Relative_LandlordRelationshipwithEmployee,Relative_MobileNumberOfrelativeWithAirtel,
	Declaration;
	
	public String getSrNumber() {
		return srNumber;
	}
	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}
	public String getAfterStatus() {
		return afterStatus;
	}
	public void setAfterStatus(String afterStatus) {
		this.afterStatus = afterStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<ValidBtsDto> getValidBtsList() {
		return validBtsList;
	}
	public void setValidBtsList(List<ValidBtsDto> validBtsList) {
		this.validBtsList = validBtsList;
	}
	public List<ValidRadioAntennaDto> getValidRadioAntennaList() {
		return validRadioAntennaList;
	}
	public void setValidRadioAntennaList(List<ValidRadioAntennaDto> validRadioAntennaList) {
		this.validRadioAntennaList = validRadioAntennaList;
	}
	public List<ValidMwAntennaDto> getValidMwAntennaList() {
		return validMwAntennaList;
	}
	public void setValidMwAntennaList(List<ValidMwAntennaDto> validMwAntennaList) {
		this.validMwAntennaList = validMwAntennaList;
	}
	public List<ValidBscRncDto> getValidBscRncList() {
		return validBscRncList;
	}
	public void setValidBscRncList(List<ValidBscRncDto> validBscRncList) {
		this.validBscRncList = validBscRncList;
	}
	public List<ValidOtherNodeDto> getValidOtherNodeList() {
		return validOtherNodeList;
	}
	public void setValidOtherNodeList(List<ValidOtherNodeDto> validOtherNodeList) {
		this.validOtherNodeList = validOtherNodeList;
	}
	public List<ValidMcbDto> getValidMcbList() {
		return validMcbList;
	}
	public void setValidMcbList(List<ValidMcbDto> validMcbList) {
		this.validMcbList = validMcbList;
	}
	public List<ValidFibreNodeDto> getValidFibreNodeList() {
		return validFibreNodeList;
	}
	public void setValidFibreNodeList(List<ValidFibreNodeDto> validFibreNodeList) {
		this.validFibreNodeList = validFibreNodeList;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getTOCO_Site_Id() {
		return TOCO_Site_Id;
	}
	public void setTOCO_Site_Id(String tOCO_Site_Id) {
		TOCO_Site_Id = tOCO_Site_Id;
	}
	public String getDate_of_Proposal() {
		return Date_of_Proposal;
	}
	public void setDate_of_Proposal(String date_of_Proposal) {
		Date_of_Proposal = date_of_Proposal;
	}
	public String getPower_Rating() {
		return Power_Rating;
	}
	public void setPower_Rating(String power_Rating) {
		Power_Rating = power_Rating;
	}
	public String getSite_Electrification_Distance() {
		return Site_Electrification_Distance;
	}
	public void setSite_Electrification_Distance(String site_Electrification_Distance) {
		Site_Electrification_Distance = site_Electrification_Distance;
	}
	public String getTentative_EB_Availibility() {
		return Tentative_EB_Availibility;
	}
	public void setTentative_EB_Availibility(String tentative_EB_Availibility) {
		Tentative_EB_Availibility = tentative_EB_Availibility;
	}
	public String getAdditional_Charge() {
		return Additional_Charge;
	}
	public void setAdditional_Charge(String additional_Charge) {
		Additional_Charge = additional_Charge;
	}
	public String getAddress1() {
		return Address1;
	}
	public void setAddress1(String address1) {
		Address1 = address1;
	}
	public String getHead_Load_Charge() {
		return Head_Load_Charge;
	}
	public void setHead_Load_Charge(String head_Load_Charge) {
		Head_Load_Charge = head_Load_Charge;
	}
	public String getElectrification_Cost() {
		return Electrification_Cost;
	}
	public void setElectrification_Cost(String electrification_Cost) {
		Electrification_Cost = electrification_Cost;
	}
	public String getElectrification_Line_Distance() {
		return Electrification_Line_Distance;
	}
	public void setElectrification_Line_Distance(String electrification_Line_Distance) {
		Electrification_Line_Distance = electrification_Line_Distance;
	}
	public String getElectricity_Connection_HT_LT() {
		return Electricity_Connection_HT_LT;
	}
	public void setElectricity_Connection_HT_LT(String electricity_Connection_HT_LT) {
		Electricity_Connection_HT_LT = electricity_Connection_HT_LT;
	}
	public String getInfra_Details() {
		return Infra_Details;
	}
	public void setInfra_Details(String infra_Details) {
		Infra_Details = infra_Details;
	}
	public String getSite_Classification() {
		return Site_Classification;
	}
	public void setSite_Classification(String site_Classification) {
		Site_Classification = site_Classification;
	}
	public String getExpected_Rent_to_Landlord() {
		return Expected_Rent_to_Landlord;
	}
	public void setExpected_Rent_to_Landlord(String expected_Rent_to_Landlord) {
		Expected_Rent_to_Landlord = expected_Rent_to_Landlord;
	}
	public String getNon_Refundable_Deposit() {
		return Non_Refundable_Deposit;
	}
	public void setNon_Refundable_Deposit(String non_Refundable_Deposit) {
		Non_Refundable_Deposit = non_Refundable_Deposit;
	}
	public String getEstimated_Deployment_Time__in_days_() {
		return Estimated_Deployment_Time__in_days_;
	}
	public void setEstimated_Deployment_Time__in_days_(String estimated_Deployment_Time__in_days_) {
		Estimated_Deployment_Time__in_days_ = estimated_Deployment_Time__in_days_;
	}
	public String getAdditional_Capex() {
		return Additional_Capex;
	}
	public void setAdditional_Capex(String additional_Capex) {
		Additional_Capex = additional_Capex;
	}
	public String getStandard_Rates() {
		return Standard_Rates;
	}
	public void setStandard_Rates(String standard_Rates) {
		Standard_Rates = standard_Rates;
	}
	public String getFiber_Charges() {
		return Fiber_Charges;
	}
	public void setFiber_Charges(String fiber_Charges) {
		Fiber_Charges = fiber_Charges;
	}
	public String getRental_Threshold() {
		return Rental_Threshold;
	}
	public void setRental_Threshold(String rental_Threshold) {
		Rental_Threshold = rental_Threshold;
	}
	public String getExcess_Rent_beyond_Threshold() {
		return Excess_Rent_beyond_Threshold;
	}
	public void setExcess_Rent_beyond_Threshold(String excess_Rent_beyond_Threshold) {
		Excess_Rent_beyond_Threshold = excess_Rent_beyond_Threshold;
	}
	public String getTentative_Rental_Premium() {
		return Tentative_Rental_Premium;
	}
	public void setTentative_Rental_Premium(String tentative_Rental_Premium) {
		Tentative_Rental_Premium = tentative_Rental_Premium;
	}
	public String getAdditional_Rent() {
		return Additional_Rent;
	}
	public void setAdditional_Rent(String additional_Rent) {
		Additional_Rent = additional_Rent;
	}
	public String getIPFee() {
		return IPFee;
	}
	public void setIPFee(String iPFee) {
		IPFee = iPFee;
	}
	public String getField_Operation_Charges() {
		return Field_Operation_Charges;
	}
	public void setField_Operation_Charges(String field_Operation_Charges) {
		Field_Operation_Charges = field_Operation_Charges;
	}
	public String getSecurity_Guard_Charges() {
		return Security_Guard_Charges;
	}
	public void setSecurity_Guard_Charges(String security_Guard_Charges) {
		Security_Guard_Charges = security_Guard_Charges;
	}
	public String getMobilization_Charges() {
		return Mobilization_Charges;
	}
	public void setMobilization_Charges(String mobilization_Charges) {
		Mobilization_Charges = mobilization_Charges;
	}
	public String getErection_Charges() {
		return Erection_Charges;
	}
	public void setErection_Charges(String erection_Charges) {
		Erection_Charges = erection_Charges;
	}
	public String getBattery_backup_Hrs() {
		return Battery_backup_Hrs;
	}
	public void setBattery_backup_Hrs(String battery_backup_Hrs) {
		Battery_backup_Hrs = battery_backup_Hrs;
	}
	public String getLand_Lord_Charges_or_Rent_Charges() {
		return Land_Lord_Charges_or_Rent_Charges;
	}
	public void setLand_Lord_Charges_or_Rent_Charges(String land_Lord_Charges_or_Rent_Charges) {
		Land_Lord_Charges_or_Rent_Charges = land_Lord_Charges_or_Rent_Charges;
	}
	public String getWind_Speed() {
		return Wind_Speed;
	}
	public void setWind_Speed(String wind_Speed) {
		Wind_Speed = wind_Speed;
	}
	public String getTowerHeight() {
		return TowerHeight;
	}
	public void setTowerHeight(String towerHeight) {
		TowerHeight = towerHeight;
	}
	public String getAgl() {
		return Agl;
	}
	public void setAgl(String agl) {
		Agl = agl;
	}
	public String getDistance_from_P1_Lat_Long_in_meter() {
		return Distance_from_P1_Lat_Long_in_meter;
	}
	public void setDistance_from_P1_Lat_Long_in_meter(String distance_from_P1_Lat_Long_in_meter) {
		Distance_from_P1_Lat_Long_in_meter = distance_from_P1_Lat_Long_in_meter;
	}
	public String getRejection_Remarks() {
		return Rejection_Remarks;
	}
	public void setRejection_Remarks(String rejection_Remarks) {
		Rejection_Remarks = rejection_Remarks;
	}
	public String getDifficult() {
		return Difficult;
	}
	public void setDifficult(String difficult) {
		Difficult = difficult;
	}
	public String getTower_Completion() {
		return Tower_Completion;
	}
	public void setTower_Completion(String tower_Completion) {
		Tower_Completion = tower_Completion;
	}
	public String getShelter_Equipment_RoomReady() {
		return Shelter_Equipment_RoomReady;
	}
	public void setShelter_Equipment_RoomReady(String shelter_Equipment_RoomReady) {
		Shelter_Equipment_RoomReady = shelter_Equipment_RoomReady;
	}
	public String getAirConditioner_Commissioned() {
		return AirConditioner_Commissioned;
	}
	public void setAirConditioner_Commissioned(String airConditioner_Commissioned) {
		AirConditioner_Commissioned = airConditioner_Commissioned;
	}
	public String getDG_Commissioned() {
		return DG_Commissioned;
	}
	public void setDG_Commissioned(String dG_Commissioned) {
		DG_Commissioned = dG_Commissioned;
	}
	public String getAcceptance_Testing_Of_Site_Infrastructure_Completed_Status() {
		return Acceptance_Testing_Of_Site_Infrastructure_Completed_Status;
	}
	public void setAcceptance_Testing_Of_Site_Infrastructure_Completed_Status(
			String acceptance_Testing_Of_Site_Infrastructure_Completed_Status) {
		Acceptance_Testing_Of_Site_Infrastructure_Completed_Status = acceptance_Testing_Of_Site_Infrastructure_Completed_Status;
	}
	public String getEB_Status() {
		return EB_Status;
	}
	public void setEB_Status(String eB_Status) {
		EB_Status = eB_Status;
	}
	public String getCreated_By() {
		return Created_By;
	}
	public void setCreated_By(String created_By) {
		Created_By = created_By;
	}
	public String getOFC_Duct_Laying_Done() {
		return OFC_Duct_Laying_Done;
	}
	public void setOFC_Duct_Laying_Done(String oFC_Duct_Laying_Done) {
		OFC_Duct_Laying_Done = oFC_Duct_Laying_Done;
	}
	public String getAccess_To_Site_Available_Status() {
		return Access_To_Site_Available_Status;
	}
	public void setAccess_To_Site_Available_Status(String access_To_Site_Available_Status) {
		Access_To_Site_Available_Status = access_To_Site_Available_Status;
	}
	public String getEngineer_Name() {
		return Engineer_Name;
	}
	public void setEngineer_Name(String engineer_Name) {
		Engineer_Name = engineer_Name;
	}
	public String getEngineer_Phone_Number() {
		return Engineer_Phone_Number;
	}
	public void setEngineer_Phone_Number(String engineer_Phone_Number) {
		Engineer_Phone_Number = engineer_Phone_Number;
	}
	public String getNotice_Form_Generation_Date() {
		return Notice_Form_Generation_Date;
	}
	public void setNotice_Form_Generation_Date(String notice_Form_Generation_Date) {
		Notice_Form_Generation_Date = notice_Form_Generation_Date;
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
	public String getRecommended_Product_Type_by_Acquisition() {
		return Recommended_Product_Type_by_Acquisition;
	}
	public void setRecommended_Product_Type_by_Acquisition(String recommended_Product_Type_by_Acquisition) {
		Recommended_Product_Type_by_Acquisition = recommended_Product_Type_by_Acquisition;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
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
	public List<AttachmentDto> getAttachedList() {
		return attachedList;
	}
	public void setAttachedList(List<AttachmentDto> attachedList) {
		this.attachedList = attachedList;
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
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getSharingPotential() {
		return sharingPotential;
	}
	public void setSharingPotential(String sharingPotential) {
		this.sharingPotential = sharingPotential;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getClutter() {
		return clutter;
	}
	public void setClutter(String clutter) {
		this.clutter = clutter;
	}
	public List<ValidTmaTmbDto> getValidTmaTmbList() {
		return validTmaTmbList;
	}
	public void setValidTmaTmbList(List<ValidTmaTmbDto> validTmaTmbList) {
		this.validTmaTmbList = validTmaTmbList;
	}
	public String getSuggestedSiteAddress() {
		return suggestedSiteAddress;
	}
	public void setSuggestedSiteAddress(String suggestedSiteAddress) {
		this.suggestedSiteAddress = suggestedSiteAddress;
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
	public String getSuggestedPincode() {
		return suggestedPincode;
	}
	public void setSuggestedPincode(String suggestedPincode) {
		this.suggestedPincode = suggestedPincode;
	}
	public String getSuggestedTownVillage() {
		return suggestedTownVillage;
	}
	public void setSuggestedTownVillage(String suggestedTownVillage) {
		this.suggestedTownVillage = suggestedTownVillage;
	}
	public String getSuggestedCity() {
		return suggestedCity;
	}
	public void setSuggestedCity(String suggestedCity) {
		this.suggestedCity = suggestedCity;
	}
	public String getSuggestedLatitude() {
		return suggestedLatitude;
	}
	public void setSuggestedLatitude(String suggestedLatitude) {
		this.suggestedLatitude = suggestedLatitude;
	}
	public String getSuggestedLongitude() {
		return suggestedLongitude;
	}
	public void setSuggestedLongitude(String suggestedLongitude) {
		this.suggestedLongitude = suggestedLongitude;
	}
	public String getSuggestedDeviation() {
		return suggestedDeviation;
	}
	public void setSuggestedDeviation(String suggestedDeviation) {
		this.suggestedDeviation = suggestedDeviation;
	}
	public String getSuggestedTowerType() {
		return suggestedTowerType;
	}
	public void setSuggestedTowerType(String suggestedTowerType) {
		this.suggestedTowerType = suggestedTowerType;
	}
	public String getSuggestedBuildingHeight() {
		return suggestedBuildingHeight;
	}
	public void setSuggestedBuildingHeight(String suggestedBuildingHeight) {
		this.suggestedBuildingHeight = suggestedBuildingHeight;
	}
	public String getSuggestedTowerHeight() {
		return suggestedTowerHeight;
	}
	public void setSuggestedTowerHeight(String suggestedTowerHeight) {
		this.suggestedTowerHeight = suggestedTowerHeight;
	}
	public String getSuggestedClutter() {
		return suggestedClutter;
	}
	public void setSuggestedClutter(String suggestedClutter) {
		this.suggestedClutter = suggestedClutter;
	}
	public String getSuggestedLandOwnerRent() {
		return suggestedLandOwnerRent;
	}
	public void setSuggestedLandOwnerRent(String suggestedLandOwnerRent) {
		this.suggestedLandOwnerRent = suggestedLandOwnerRent;
	}
	public String getSuggestedElectrificationCharges() {
		return suggestedElectrificationCharges;
	}
	public void setSuggestedElectrificationCharges(String suggestedElectrificationCharges) {
		this.suggestedElectrificationCharges = suggestedElectrificationCharges;
	}
	public String getSuggestedMcCharges() {
		return suggestedMcCharges;
	}
	public void setSuggestedMcCharges(String suggestedMcCharges) {
		this.suggestedMcCharges = suggestedMcCharges;
	}
	public String getViewTocoSiteId() {
		return viewTocoSiteId;
	}
	public void setViewTocoSiteId(String viewTocoSiteId) {
		this.viewTocoSiteId = viewTocoSiteId;
	}
	public String getAssociation_AreyouWorkingInAnyBhartiGroup() {
		return Association_AreyouWorkingInAnyBhartiGroup;
	}
	public void setAssociation_AreyouWorkingInAnyBhartiGroup(String association_AreyouWorkingInAnyBhartiGroup) {
		Association_AreyouWorkingInAnyBhartiGroup = association_AreyouWorkingInAnyBhartiGroup;
	}
	public String getAssociation_IfyesmentiontheBhartiUnitName() {
		return Association_IfyesmentiontheBhartiUnitName;
	}
	public void setAssociation_IfyesmentiontheBhartiUnitName(String association_IfyesmentiontheBhartiUnitName) {
		Association_IfyesmentiontheBhartiUnitName = association_IfyesmentiontheBhartiUnitName;
	}
	public String getAssociation_NameOftheEmployee() {
		return Association_NameOftheEmployee;
	}
	public void setAssociation_NameOftheEmployee(String association_NameOftheEmployee) {
		Association_NameOftheEmployee = association_NameOftheEmployee;
	}
	public String getAssociation_EmployeeId() {
		return Association_EmployeeId;
	}
	public void setAssociation_EmployeeId(String association_EmployeeId) {
		Association_EmployeeId = association_EmployeeId;
	}
	public String getRelative_AnyRelativesareWorkingWithBhartiGroup() {
		return Relative_AnyRelativesareWorkingWithBhartiGroup;
	}
	public void setRelative_AnyRelativesareWorkingWithBhartiGroup(String relative_AnyRelativesareWorkingWithBhartiGroup) {
		Relative_AnyRelativesareWorkingWithBhartiGroup = relative_AnyRelativesareWorkingWithBhartiGroup;
	}
	public String getRelative_IfyesmentiontheBhartiUnitName() {
		return Relative_IfyesmentiontheBhartiUnitName;
	}
	public void setRelative_IfyesmentiontheBhartiUnitName(String relative_IfyesmentiontheBhartiUnitName) {
		Relative_IfyesmentiontheBhartiUnitName = relative_IfyesmentiontheBhartiUnitName;
	}
	public String getRelative_NameOftheEmployee() {
		return Relative_NameOftheEmployee;
	}
	public void setRelative_NameOftheEmployee(String relative_NameOftheEmployee) {
		Relative_NameOftheEmployee = relative_NameOftheEmployee;
	}
	public String getRelative_EmployeeId() {
		return Relative_EmployeeId;
	}
	public void setRelative_EmployeeId(String relative_EmployeeId) {
		Relative_EmployeeId = relative_EmployeeId;
	}
	public String getRelative_LandlordRelationshipwithEmployee() {
		return Relative_LandlordRelationshipwithEmployee;
	}
	public void setRelative_LandlordRelationshipwithEmployee(String relative_LandlordRelationshipwithEmployee) {
		Relative_LandlordRelationshipwithEmployee = relative_LandlordRelationshipwithEmployee;
	}
	public String getRelative_MobileNumberOfrelativeWithAirtel() {
		return Relative_MobileNumberOfrelativeWithAirtel;
	}
	public void setRelative_MobileNumberOfrelativeWithAirtel(String relative_MobileNumberOfrelativeWithAirtel) {
		Relative_MobileNumberOfrelativeWithAirtel = relative_MobileNumberOfrelativeWithAirtel;
	}
	public String getDeclaration() {
		return Declaration;
	}
	public void setDeclaration(String declaration) {
		Declaration = declaration;
	}
	public List<ValidOtherEquipmentDto> getValidOtherEquipmentList() {
		return validOtherEquipmentList;
	}
	public void setValidOtherEquipmentList(List<ValidOtherEquipmentDto> validOtherEquipmentList) {
		this.validOtherEquipmentList = validOtherEquipmentList;
	}
}
