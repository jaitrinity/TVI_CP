package com.tvi.response;

import java.util.List;

import com.tvi.dto.AirtelAuditDto;
import com.tvi.dto.AttachmentDto;
import com.tvi.dto.BscRncCabinetsDto;
import com.tvi.dto.BtsCabinetDto;
import com.tvi.dto.FibreNodeDto;
import com.tvi.dto.McbDto;
import com.tvi.dto.MwAntennaDto;
import com.tvi.dto.OtherNodeDto;
import com.tvi.dto.RadioAntennaDto;
import com.tvi.upgrade.dto.TmaTmbDto;

public class AirtelCommonResponse {

	private String buttonAfterStatus="",attachment="";
	private String Date_of_Proposal, Power_Rating, Site_Electrification_Distance, Tentative_EB_Availibility, 
	Additional_Charge, Address1, Head_Load_Charge, Electrification_Cost, Electrification_Line_Distance, 
	Electricity_Connection_HT_LT, Infra_Details, Site_Classification, Expected_Rent_to_Landlord, Non_Refundable_Deposit, 
	Estimated_Deployment_Time__in_days_, Additional_Capex, Standard_Rates, Fiber_Charges, Rental_Threshold, 
	Excess_Rent_beyond_Threshold, Tentative_Rental_Premium, Additional_Rent, IPFee, Field_Operation_Charges, 
	Security_Guard_Charges, Mobilization_Charges, Erection_Charges, Battery_backup_Hrs, Land_Lord_Charges_or_Rent_Charges,
	Wind_Speed, TowerHeight, Agl, Distance_from_P1_Lat_Long_in_meter, Rejection_Remarks, Difficult, Tower_Completion, 
	Shelter_Equipment_RoomReady, AirConditioner_Commissioned, DG_Commissioned, 
	Acceptance_Testing_Of_Site_Infrastructure_Completed_Status, EB_Status, Created_By, OFC_Duct_Laying_Done, 
	Access_To_Site_Available_Status, Engineer_Name, Engineer_Phone_Number, Notice_Form_Generation_Date, Tenure_In_Years, 
	SO_Accept_Reject, RFI_Accept_Reject, RFI_Reject_Remarks, RFI_Reject_Remarks_Others, Total_Rated_Power_In_KW, 
	Total_Rated_Power_In_Watt,TOCO_Site_Id,Financial_Site_Id,Expected_monthly_Rent_Approved,CAPEX_Amount_Approved,
	OPEX_Amount_Approved, Tentative_Billing_Amount_Approved, Target_Date, TargetDate_DD_MM_YYYY, Date;
	
	private String Is_it_Strategic, Shelter_Size, Length_Mtrs, Breadth_Mtrs, Height_AGL_Mtrs, DG_Redundancy, 
	Extra_Battery_Bank_Requirement, Extra_Battery_BackUp, Anyother_Specific_Requirements, Additional_Info_If_any, 
	Other_Additional_Info1, Other_Additional_Info2, Additional_Info_TargetDate_DD_MM_YYYY, Is_it_Relocaiton_SR, Source_Req_Ref_No, 
	Source_Indus_SiteId, Relocaiton_Reason;
	
	private String Withdrawal_Type, Withdrawal_Reason, Rejection_Category, Rejection_Reason, Accept_Reject, 
	Reject_SP_Remark,withdraw_remark,sharingPotential,spLatitude,spLongitude,clutter,activityType,projectName,rlType,
	UniqueRequestId,Remarks,Customer_Site_Id,Customer_Site_Name,State,Cell_Type,Site_Type,City,
	Search_Ring_Radious_Mtrs,Infill_NewTown,ShowCase_Non_Showcase,_3_11_Towns,Town,Request_for_Network_Type,
	Authority_Name,Preferred_Product_Type,Recommended_Product_Type_by_Acquisition,Customer_Product_Type,
	No_of_TMA_TMB,Weight_of_each_TMA_TMB,Combined_wt_of_TMA_TMB_Kgs,Height_at_which_needs_to_be_mounted_Mtrs,
	Other_Equipment,Fiber_Required,No_of_Fiber_Pairs,Is_Fiber_Node_Provisioning_Required,No_of_Pairs,
	Distance_Length_of_Fiber_in_Meter,SACFA_Number,Is_Diesel_Generator_DG_required,Product_Name,Request_Ref_No,
	BSC,BTS_Cabinett,Extend_Tenure,Fiber_Node_Provisioning,Micro_to_Macro_conversion,MW_Antennaa,Other_Nodes,
	Radio_Antennaa,Strategic_Conversion,Tower_Mounted_Booster,MW_IDUU,Pole,HubTag_Untag,Cluster,MSA_Type,
	Name_of_District_SSA,Reject_Remarks,Reject_Remarks_Others,Other_Equipment2;
	
	private String suggestedSiteAddress,suggestedDistrict,suggestedState,suggestedPincode,suggestedTownVillage,
	suggestedCity,suggestedLatitude,suggestedLongitude,suggestedDeviation,suggestedTowerType,suggestedBuildingHeight,
	suggestedTowerHeight,suggestedClutter,suggestedLandOwnerRent,suggestedElectrificationCharges,suggestedMcCharges;
	
	private String Association_AreyouWorkingInAnyBhartiGroup,Association_IfyesmentiontheBhartiUnitName,
	Association_NameOftheEmployee,Association_EmployeeId,Relative_AnyRelativesareWorkingWithBhartiGroup,
	Relative_IfyesmentiontheBhartiUnitName,Relative_NameOftheEmployee,Relative_EmployeeId,
	Relative_LandlordRelationshipwithEmployee,Relative_MobileNumberOfrelativeWithAirtel,
	Declaration;
	
	private List<BtsCabinetDto> BTS_Cabinet;
	private List<BtsCabinetDto> RRU_Cabinet;
	private List<RadioAntennaDto> Radio_Antenna;
	private List<MwAntennaDto> MW_Antenna;
	private List<BscRncCabinetsDto> BSC_RNC_Cabinets;
	private List<OtherNodeDto> Other_Node;
	private List<OtherNodeDto> MW_IDU;
	private List<McbDto> MCB;
	private List<FibreNodeDto> Fibre_Node;
	private List<TmaTmbDto> TMA_TMB;
	private List<AttachmentDto> attachedList;
	private List<AirtelAuditDto> auditList;
	
	public List<BtsCabinetDto> getBTS_Cabinet() {
		return BTS_Cabinet;
	}
	public void setBTS_Cabinet(List<BtsCabinetDto> bTS_Cabinet) {
		BTS_Cabinet = bTS_Cabinet;
	}
	public List<RadioAntennaDto> getRadio_Antenna() {
		return Radio_Antenna;
	}
	public void setRadio_Antenna(List<RadioAntennaDto> radio_Antenna) {
		Radio_Antenna = radio_Antenna;
	}
	public List<MwAntennaDto> getMW_Antenna() {
		return MW_Antenna;
	}
	public void setMW_Antenna(List<MwAntennaDto> mW_Antenna) {
		MW_Antenna = mW_Antenna;
	}
	public List<BscRncCabinetsDto> getBSC_RNC_Cabinets() {
		return BSC_RNC_Cabinets;
	}
	public void setBSC_RNC_Cabinets(List<BscRncCabinetsDto> bSC_RNC_Cabinets) {
		BSC_RNC_Cabinets = bSC_RNC_Cabinets;
	}
	public List<OtherNodeDto> getOther_Node() {
		return Other_Node;
	}
	public void setOther_Node(List<OtherNodeDto> other_Node) {
		Other_Node = other_Node;
	}
	public List<FibreNodeDto> getFibre_Node() {
		return Fibre_Node;
	}
	public void setFibre_Node(List<FibreNodeDto> fibre_Node) {
		Fibre_Node = fibre_Node;
	}
	public String getButtonAfterStatus() {
		return buttonAfterStatus;
	}
	public void setButtonAfterStatus(String buttonAfterStatus) {
		this.buttonAfterStatus = buttonAfterStatus;
	}
	public List<McbDto> getMCB() {
		return MCB;
	}
	public void setMCB(List<McbDto> mCB) {
		MCB = mCB;
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
	public String getTenure_In_Years() {
		return Tenure_In_Years;
	}
	public void setTenure_In_Years(String tenure_In_Years) {
		Tenure_In_Years = tenure_In_Years;
	}
	public String getSO_Accept_Reject() {
		return SO_Accept_Reject;
	}
	public void setSO_Accept_Reject(String sO_Accept_Reject) {
		SO_Accept_Reject = sO_Accept_Reject;
	}
	public String getRFI_Accept_Reject() {
		return RFI_Accept_Reject;
	}
	public void setRFI_Accept_Reject(String rFI_Accept_Reject) {
		RFI_Accept_Reject = rFI_Accept_Reject;
	}
	public String getRFI_Reject_Remarks() {
		return RFI_Reject_Remarks;
	}
	public void setRFI_Reject_Remarks(String rFI_Reject_Remarks) {
		RFI_Reject_Remarks = rFI_Reject_Remarks;
	}
	public String getRFI_Reject_Remarks_Others() {
		return RFI_Reject_Remarks_Others;
	}
	public void setRFI_Reject_Remarks_Others(String rFI_Reject_Remarks_Others) {
		RFI_Reject_Remarks_Others = rFI_Reject_Remarks_Others;
	}
	public String getTotal_Rated_Power_In_KW() {
		return Total_Rated_Power_In_KW;
	}
	public void setTotal_Rated_Power_In_KW(String total_Rated_Power_In_KW) {
		Total_Rated_Power_In_KW = total_Rated_Power_In_KW;
	}
	public String getTotal_Rated_Power_In_Watt() {
		return Total_Rated_Power_In_Watt;
	}
	public void setTotal_Rated_Power_In_Watt(String total_Rated_Power_In_Watt) {
		Total_Rated_Power_In_Watt = total_Rated_Power_In_Watt;
	}
	public String getTOCO_Site_Id() {
		return TOCO_Site_Id;
	}
	public void setTOCO_Site_Id(String tOCO_Site_Id) {
		TOCO_Site_Id = tOCO_Site_Id;
	}
	public String getFinancial_Site_Id() {
		return Financial_Site_Id;
	}
	public void setFinancial_Site_Id(String financial_Site_Id) {
		Financial_Site_Id = financial_Site_Id;
	}
	public String getExpected_monthly_Rent_Approved() {
		return Expected_monthly_Rent_Approved;
	}
	public void setExpected_monthly_Rent_Approved(String expected_monthly_Rent_Approved) {
		Expected_monthly_Rent_Approved = expected_monthly_Rent_Approved;
	}
	public String getCAPEX_Amount_Approved() {
		return CAPEX_Amount_Approved;
	}
	public void setCAPEX_Amount_Approved(String cAPEX_Amount_Approved) {
		CAPEX_Amount_Approved = cAPEX_Amount_Approved;
	}
	public String getOPEX_Amount_Approved() {
		return OPEX_Amount_Approved;
	}
	public void setOPEX_Amount_Approved(String oPEX_Amount_Approved) {
		OPEX_Amount_Approved = oPEX_Amount_Approved;
	}
	public String getTentative_Billing_Amount_Approved() {
		return Tentative_Billing_Amount_Approved;
	}
	public void setTentative_Billing_Amount_Approved(String tentative_Billing_Amount_Approved) {
		Tentative_Billing_Amount_Approved = tentative_Billing_Amount_Approved;
	}
	public String getTarget_Date() {
		return Target_Date;
	}
	public void setTarget_Date(String target_Date) {
		Target_Date = target_Date;
	}
	public String getTargetDate_DD_MM_YYYY() {
		return TargetDate_DD_MM_YYYY;
	}
	public void setTargetDate_DD_MM_YYYY(String targetDate_DD_MM_YYYY) {
		TargetDate_DD_MM_YYYY = targetDate_DD_MM_YYYY;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getIs_it_Strategic() {
		return Is_it_Strategic;
	}
	public void setIs_it_Strategic(String is_it_Strategic) {
		Is_it_Strategic = is_it_Strategic;
	}
	public String getShelter_Size() {
		return Shelter_Size;
	}
	public void setShelter_Size(String shelter_Size) {
		Shelter_Size = shelter_Size;
	}
	public String getLength_Mtrs() {
		return Length_Mtrs;
	}
	public void setLength_Mtrs(String length_Mtrs) {
		Length_Mtrs = length_Mtrs;
	}
	public String getBreadth_Mtrs() {
		return Breadth_Mtrs;
	}
	public void setBreadth_Mtrs(String breadth_Mtrs) {
		Breadth_Mtrs = breadth_Mtrs;
	}
	public String getHeight_AGL_Mtrs() {
		return Height_AGL_Mtrs;
	}
	public void setHeight_AGL_Mtrs(String height_AGL_Mtrs) {
		Height_AGL_Mtrs = height_AGL_Mtrs;
	}
	public String getDG_Redundancy() {
		return DG_Redundancy;
	}
	public void setDG_Redundancy(String dG_Redundancy) {
		DG_Redundancy = dG_Redundancy;
	}
	public String getExtra_Battery_Bank_Requirement() {
		return Extra_Battery_Bank_Requirement;
	}
	public void setExtra_Battery_Bank_Requirement(String extra_Battery_Bank_Requirement) {
		Extra_Battery_Bank_Requirement = extra_Battery_Bank_Requirement;
	}
	public String getExtra_Battery_BackUp() {
		return Extra_Battery_BackUp;
	}
	public void setExtra_Battery_BackUp(String extra_Battery_BackUp) {
		Extra_Battery_BackUp = extra_Battery_BackUp;
	}
	public String getAnyother_Specific_Requirements() {
		return Anyother_Specific_Requirements;
	}
	public void setAnyother_Specific_Requirements(String anyother_Specific_Requirements) {
		Anyother_Specific_Requirements = anyother_Specific_Requirements;
	}
	public String getAdditional_Info_If_any() {
		return Additional_Info_If_any;
	}
	public void setAdditional_Info_If_any(String additional_Info_If_any) {
		Additional_Info_If_any = additional_Info_If_any;
	}
	public String getOther_Additional_Info1() {
		return Other_Additional_Info1;
	}
	public void setOther_Additional_Info1(String other_Additional_Info1) {
		Other_Additional_Info1 = other_Additional_Info1;
	}
	public String getOther_Additional_Info2() {
		return Other_Additional_Info2;
	}
	public void setOther_Additional_Info2(String other_Additional_Info2) {
		Other_Additional_Info2 = other_Additional_Info2;
	}
	public String getAdditional_Info_TargetDate_DD_MM_YYYY() {
		return Additional_Info_TargetDate_DD_MM_YYYY;
	}
	public void setAdditional_Info_TargetDate_DD_MM_YYYY(String additional_Info_TargetDate_DD_MM_YYYY) {
		Additional_Info_TargetDate_DD_MM_YYYY = additional_Info_TargetDate_DD_MM_YYYY;
	}
	public String getIs_it_Relocaiton_SR() {
		return Is_it_Relocaiton_SR;
	}
	public void setIs_it_Relocaiton_SR(String is_it_Relocaiton_SR) {
		Is_it_Relocaiton_SR = is_it_Relocaiton_SR;
	}
	public String getSource_Req_Ref_No() {
		return Source_Req_Ref_No;
	}
	public void setSource_Req_Ref_No(String source_Req_Ref_No) {
		Source_Req_Ref_No = source_Req_Ref_No;
	}
	public String getSource_Indus_SiteId() {
		return Source_Indus_SiteId;
	}
	public void setSource_Indus_SiteId(String source_Indus_SiteId) {
		Source_Indus_SiteId = source_Indus_SiteId;
	}
	public String getRelocaiton_Reason() {
		return Relocaiton_Reason;
	}
	public void setRelocaiton_Reason(String relocaiton_Reason) {
		Relocaiton_Reason = relocaiton_Reason;
	}
	public List<BtsCabinetDto> getRRU_Cabinet() {
		return RRU_Cabinet;
	}
	public void setRRU_Cabinet(List<BtsCabinetDto> rRU_Cabinet) {
		RRU_Cabinet = rRU_Cabinet;
	}
	public List<OtherNodeDto> getMW_IDU() {
		return MW_IDU;
	}
	public void setMW_IDU(List<OtherNodeDto> mW_IDU) {
		MW_IDU = mW_IDU;
	}
	public String getWithdrawal_Type() {
		return Withdrawal_Type;
	}
	public void setWithdrawal_Type(String withdrawal_Type) {
		Withdrawal_Type = withdrawal_Type;
	}
	public String getWithdrawal_Reason() {
		return Withdrawal_Reason;
	}
	public void setWithdrawal_Reason(String withdrawal_Reason) {
		Withdrawal_Reason = withdrawal_Reason;
	}
	public String getRejection_Category() {
		return Rejection_Category;
	}
	public void setRejection_Category(String rejection_Category) {
		Rejection_Category = rejection_Category;
	}
	public String getRejection_Reason() {
		return Rejection_Reason;
	}
	public void setRejection_Reason(String rejection_Reason) {
		Rejection_Reason = rejection_Reason;
	}
	public String getAccept_Reject() {
		return Accept_Reject;
	}
	public void setAccept_Reject(String accept_Reject) {
		Accept_Reject = accept_Reject;
	}
	public String getReject_SP_Remark() {
		return Reject_SP_Remark;
	}
	public void setReject_SP_Remark(String reject_SP_Remark) {
		Reject_SP_Remark = reject_SP_Remark;
	}
	public String getWithdraw_remark() {
		return withdraw_remark;
	}
	public void setWithdraw_remark(String withdraw_remark) {
		this.withdraw_remark = withdraw_remark;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public List<AttachmentDto> getAttachedList() {
		return attachedList;
	}
	public void setAttachedList(List<AttachmentDto> attachedList) {
		this.attachedList = attachedList;
	}
	public List<AirtelAuditDto> getAuditList() {
		return auditList;
	}
	public void setAuditList(List<AirtelAuditDto> auditList) {
		this.auditList = auditList;
	}
	public String getSharingPotential() {
		return sharingPotential;
	}
	public void setSharingPotential(String sharingPotential) {
		this.sharingPotential = sharingPotential;
	}
	public String getSpLatitude() {
		return spLatitude;
	}
	public void setSpLatitude(String spLatitude) {
		this.spLatitude = spLatitude;
	}
	public String getSpLongitude() {
		return spLongitude;
	}
	public void setSpLongitude(String spLongitude) {
		this.spLongitude = spLongitude;
	}
	public String getClutter() {
		return clutter;
	}
	public void setClutter(String clutter) {
		this.clutter = clutter;
	}
	public List<TmaTmbDto> getTMA_TMB() {
		return TMA_TMB;
	}
	public void setTMA_TMB(List<TmaTmbDto> tMA_TMB) {
		TMA_TMB = tMA_TMB;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getRlType() {
		return rlType;
	}
	public void setRlType(String rlType) {
		this.rlType = rlType;
	}
	public String getUniqueRequestId() {
		return UniqueRequestId;
	}
	public void setUniqueRequestId(String uniqueRequestId) {
		UniqueRequestId = uniqueRequestId;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getCustomer_Site_Id() {
		return Customer_Site_Id;
	}
	public void setCustomer_Site_Id(String customer_Site_Id) {
		Customer_Site_Id = customer_Site_Id;
	}
	public String getCustomer_Site_Name() {
		return Customer_Site_Name;
	}
	public void setCustomer_Site_Name(String customer_Site_Name) {
		Customer_Site_Name = customer_Site_Name;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getCell_Type() {
		return Cell_Type;
	}
	public void setCell_Type(String cell_Type) {
		Cell_Type = cell_Type;
	}
	public String getSite_Type() {
		return Site_Type;
	}
	public void setSite_Type(String site_Type) {
		Site_Type = site_Type;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getSearch_Ring_Radious_Mtrs() {
		return Search_Ring_Radious_Mtrs;
	}
	public void setSearch_Ring_Radious_Mtrs(String search_Ring_Radious_Mtrs) {
		Search_Ring_Radious_Mtrs = search_Ring_Radious_Mtrs;
	}
	public String getInfill_NewTown() {
		return Infill_NewTown;
	}
	public void setInfill_NewTown(String infill_NewTown) {
		Infill_NewTown = infill_NewTown;
	}
	public String getShowCase_Non_Showcase() {
		return ShowCase_Non_Showcase;
	}
	public void setShowCase_Non_Showcase(String showCase_Non_Showcase) {
		ShowCase_Non_Showcase = showCase_Non_Showcase;
	}
	public String get_3_11_Towns() {
		return _3_11_Towns;
	}
	public void set_3_11_Towns(String _3_11_Towns) {
		this._3_11_Towns = _3_11_Towns;
	}
	public String getTown() {
		return Town;
	}
	public void setTown(String town) {
		Town = town;
	}
	public String getRequest_for_Network_Type() {
		return Request_for_Network_Type;
	}
	public void setRequest_for_Network_Type(String request_for_Network_Type) {
		Request_for_Network_Type = request_for_Network_Type;
	}
	public String getAuthority_Name() {
		return Authority_Name;
	}
	public void setAuthority_Name(String authority_Name) {
		Authority_Name = authority_Name;
	}
	public String getPreferred_Product_Type() {
		return Preferred_Product_Type;
	}
	public void setPreferred_Product_Type(String preferred_Product_Type) {
		Preferred_Product_Type = preferred_Product_Type;
	}
	public String getRecommended_Product_Type_by_Acquisition() {
		return Recommended_Product_Type_by_Acquisition;
	}
	public void setRecommended_Product_Type_by_Acquisition(String recommended_Product_Type_by_Acquisition) {
		Recommended_Product_Type_by_Acquisition = recommended_Product_Type_by_Acquisition;
	}
	public String getCustomer_Product_Type() {
		return Customer_Product_Type;
	}
	public void setCustomer_Product_Type(String customer_Product_Type) {
		Customer_Product_Type = customer_Product_Type;
	}
	public String getNo_of_TMA_TMB() {
		return No_of_TMA_TMB;
	}
	public void setNo_of_TMA_TMB(String no_of_TMA_TMB) {
		No_of_TMA_TMB = no_of_TMA_TMB;
	}
	public String getWeight_of_each_TMA_TMB() {
		return Weight_of_each_TMA_TMB;
	}
	public void setWeight_of_each_TMA_TMB(String weight_of_each_TMA_TMB) {
		Weight_of_each_TMA_TMB = weight_of_each_TMA_TMB;
	}
	public String getCombined_wt_of_TMA_TMB_Kgs() {
		return Combined_wt_of_TMA_TMB_Kgs;
	}
	public void setCombined_wt_of_TMA_TMB_Kgs(String combined_wt_of_TMA_TMB_Kgs) {
		Combined_wt_of_TMA_TMB_Kgs = combined_wt_of_TMA_TMB_Kgs;
	}
	public String getHeight_at_which_needs_to_be_mounted_Mtrs() {
		return Height_at_which_needs_to_be_mounted_Mtrs;
	}
	public void setHeight_at_which_needs_to_be_mounted_Mtrs(String height_at_which_needs_to_be_mounted_Mtrs) {
		Height_at_which_needs_to_be_mounted_Mtrs = height_at_which_needs_to_be_mounted_Mtrs;
	}
	public String getOther_Equipment() {
		return Other_Equipment;
	}
	public void setOther_Equipment(String other_Equipment) {
		Other_Equipment = other_Equipment;
	}
	public String getFiber_Required() {
		return Fiber_Required;
	}
	public void setFiber_Required(String fiber_Required) {
		Fiber_Required = fiber_Required;
	}
	public String getNo_of_Fiber_Pairs() {
		return No_of_Fiber_Pairs;
	}
	public void setNo_of_Fiber_Pairs(String no_of_Fiber_Pairs) {
		No_of_Fiber_Pairs = no_of_Fiber_Pairs;
	}
	public String getIs_Fiber_Node_Provisioning_Required() {
		return Is_Fiber_Node_Provisioning_Required;
	}
	public void setIs_Fiber_Node_Provisioning_Required(String is_Fiber_Node_Provisioning_Required) {
		Is_Fiber_Node_Provisioning_Required = is_Fiber_Node_Provisioning_Required;
	}
	public String getNo_of_Pairs() {
		return No_of_Pairs;
	}
	public void setNo_of_Pairs(String no_of_Pairs) {
		No_of_Pairs = no_of_Pairs;
	}
	public String getDistance_Length_of_Fiber_in_Meter() {
		return Distance_Length_of_Fiber_in_Meter;
	}
	public void setDistance_Length_of_Fiber_in_Meter(String distance_Length_of_Fiber_in_Meter) {
		Distance_Length_of_Fiber_in_Meter = distance_Length_of_Fiber_in_Meter;
	}
	public String getSACFA_Number() {
		return SACFA_Number;
	}
	public void setSACFA_Number(String sACFA_Number) {
		SACFA_Number = sACFA_Number;
	}
	public String getIs_Diesel_Generator_DG_required() {
		return Is_Diesel_Generator_DG_required;
	}
	public void setIs_Diesel_Generator_DG_required(String is_Diesel_Generator_DG_required) {
		Is_Diesel_Generator_DG_required = is_Diesel_Generator_DG_required;
	}
	public String getProduct_Name() {
		return Product_Name;
	}
	public void setProduct_Name(String product_Name) {
		Product_Name = product_Name;
	}
	public String getRequest_Ref_No() {
		return Request_Ref_No;
	}
	public void setRequest_Ref_No(String request_Ref_No) {
		Request_Ref_No = request_Ref_No;
	}
	public String getBSC() {
		return BSC;
	}
	public void setBSC(String bSC) {
		BSC = bSC;
	}
	public String getBTS_Cabinett() {
		return BTS_Cabinett;
	}
	public void setBTS_Cabinett(String bTS_Cabinett) {
		BTS_Cabinett = bTS_Cabinett;
	}
	public String getExtend_Tenure() {
		return Extend_Tenure;
	}
	public void setExtend_Tenure(String extend_Tenure) {
		Extend_Tenure = extend_Tenure;
	}
	public String getFiber_Node_Provisioning() {
		return Fiber_Node_Provisioning;
	}
	public void setFiber_Node_Provisioning(String fiber_Node_Provisioning) {
		Fiber_Node_Provisioning = fiber_Node_Provisioning;
	}
	public String getMicro_to_Macro_conversion() {
		return Micro_to_Macro_conversion;
	}
	public void setMicro_to_Macro_conversion(String micro_to_Macro_conversion) {
		Micro_to_Macro_conversion = micro_to_Macro_conversion;
	}
	public String getMW_Antennaa() {
		return MW_Antennaa;
	}
	public void setMW_Antennaa(String mW_Antennaa) {
		MW_Antennaa = mW_Antennaa;
	}
	public String getOther_Nodes() {
		return Other_Nodes;
	}
	public void setOther_Nodes(String other_Nodes) {
		Other_Nodes = other_Nodes;
	}
	public String getRadio_Antennaa() {
		return Radio_Antennaa;
	}
	public void setRadio_Antennaa(String radio_Antennaa) {
		Radio_Antennaa = radio_Antennaa;
	}
	public String getStrategic_Conversion() {
		return Strategic_Conversion;
	}
	public void setStrategic_Conversion(String strategic_Conversion) {
		Strategic_Conversion = strategic_Conversion;
	}
	public String getTower_Mounted_Booster() {
		return Tower_Mounted_Booster;
	}
	public void setTower_Mounted_Booster(String tower_Mounted_Booster) {
		Tower_Mounted_Booster = tower_Mounted_Booster;
	}
	public String getMW_IDUU() {
		return MW_IDUU;
	}
	public void setMW_IDUU(String mW_IDUU) {
		MW_IDUU = mW_IDUU;
	}
	public String getPole() {
		return Pole;
	}
	public void setPole(String pole) {
		Pole = pole;
	}
	public String getHubTag_Untag() {
		return HubTag_Untag;
	}
	public void setHubTag_Untag(String hubTag_Untag) {
		HubTag_Untag = hubTag_Untag;
	}
	public String getCluster() {
		return Cluster;
	}
	public void setCluster(String cluster) {
		Cluster = cluster;
	}
	public String getMSA_Type() {
		return MSA_Type;
	}
	public void setMSA_Type(String mSA_Type) {
		MSA_Type = mSA_Type;
	}
	public String getName_of_District_SSA() {
		return Name_of_District_SSA;
	}
	public void setName_of_District_SSA(String name_of_District_SSA) {
		Name_of_District_SSA = name_of_District_SSA;
	}
	public String getReject_Remarks() {
		return Reject_Remarks;
	}
	public void setReject_Remarks(String reject_Remarks) {
		Reject_Remarks = reject_Remarks;
	}
	public String getReject_Remarks_Others() {
		return Reject_Remarks_Others;
	}
	public void setReject_Remarks_Others(String reject_Remarks_Others) {
		Reject_Remarks_Others = reject_Remarks_Others;
	}
	public String getOther_Equipment2() {
		return Other_Equipment2;
	}
	public void setOther_Equipment2(String other_Equipment2) {
		Other_Equipment2 = other_Equipment2;
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
	
}
