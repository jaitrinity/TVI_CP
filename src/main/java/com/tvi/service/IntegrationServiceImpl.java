package com.tvi.service;

import java.sql.Clob;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.tvi.constant.Response;
import com.tvi.constant.ReturnsCode;
import com.tvi.dao.IntegrationDao;
import com.tvi.dto.AdditionalInformationDto;
import com.tvi.dto.BandFrequencyMHzDto;
import com.tvi.dto.BscRncCabinetsDto;
import com.tvi.dto.BtsCabinetDto;
import com.tvi.dto.BtsDto;
import com.tvi.dto.BulkDTO;
import com.tvi.dto.DgDto;
import com.tvi.dto.ExportDto;
import com.tvi.dto.FiberDto;
import com.tvi.dto.FiberNodeProvisioningDto;
import com.tvi.dto.FibreNodeDto;
import com.tvi.dto.GlobalDto;
import com.tvi.dto.McbDto;
import com.tvi.dto.MwAntennaDto;
import com.tvi.dto.MwDto;
import com.tvi.dto.OtherEquipmentDto;
import com.tvi.dto.OtherNodeDto;
import com.tvi.dto.PriorityDetailsDto;
import com.tvi.dto.PriorityDto;
import com.tvi.dto.ProductDetailDto;
import com.tvi.dto.RadioAntennaDto;
import com.tvi.dto.RejectSpDto;
import com.tvi.dto.RelocationDto;
import com.tvi.dto.RfaiAcceptReject;
import com.tvi.dto.SacfaDto;
import com.tvi.dto.SiteDetailDto;
import com.tvi.dto.SoSubmitDto;
import com.tvi.dto.SrRequestJsonDto;
import com.tvi.dto.SrSoWithdrawalDto;
import com.tvi.dto.StrageticDto;
import com.tvi.dto.SystemParamDTO;
import com.tvi.dto.TmaTmbDto;
import com.tvi.request.AirtelCommonRequest;
import com.tvi.request.ChangeAirtelSrStatusRequest;
import com.tvi.request.SaveNBSRequest;
import com.tvi.request.SpReceivedTest;
import com.tvi.response.AirtelCommonResponse;
import com.tvi.response.ExportDataResponse;
import com.tvi.response.RejectSpResponse;
import com.tvi.response.RfaiAcceptRejectResponse;
import com.tvi.response.SoSubmitResponse;
import com.tvi.response.SpReceivedResponse;
import com.tvi.response.SrResponse;
import com.tvi.response.SrSoWindrawalResponse;
import com.tvi.sharing.dto.SharingSrRequestJsonDto;
import com.tvi.upgrade.dto.RequestedEquipmentDto;
import com.tvi.upgrade.dto.UpgradeSrRequestJsonDto;

public class IntegrationServiceImpl implements IntegrationService {

	@Autowired
	IntegrationDao intDeo;
	
	@Override
	public void saveResponseInTable(Clob requestJson, Clob responseJson, String methodName) {
		intDeo.saveResponseInTable(requestJson,responseJson,methodName);
	}

	@Override
	public Response<SystemParamDTO> getNoOfList() {
		return intDeo.getNoOfList();
	}

	@Override
	public Response<SrResponse> raiseSr(SrRequestJsonDto srDto) {
		Response<SrResponse> response = new Response<SrResponse>();
		
		String valid = "";
		valid += srDto.getRef_Number_TVIPL() == null ? "Ref_Number_TVIPL," : "";
		valid += srDto.getSiteId() == null ? "SiteId," : "";
		valid += srDto.getOperator() == null ? "Operator," : "";
		valid += srDto.getUniqueRequestId() == null ? "UniqueRequestId," : "";
		if(!valid.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc(""+valid+" key is missing");
			return response;
		}
		
		GlobalDto Global = srDto.getGlobal();
		if(Global == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Global json not found");
			return response;
		}
		else if(Global.getRemarks() == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Global - Remark key is missing");
			return response;
		}
		/*else if(Global.getRemarks().equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Global - Remark key is mandatory");
			return response;
		}*/
		PriorityDetailsDto Priority_Details = srDto.getPriority_Details();
		if(Priority_Details == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Priority_Details json not found");
			return response;
		}
		PriorityDto p1 = Priority_Details.getP1();
		PriorityDto p2 = Priority_Details.getP1();
		PriorityDto p3 = Priority_Details.getP1();
		String priData = "";
		if(p1 == null){
			priData = "Priority_Details : p1 json not found";
		}
		else if(p1.getSite_Address() == null){
			priData = "Priority_Details : p1 : Site_Address key is missing";
		}
		else if(p1.getTower_Type() == null){
			priData = "Priority_Details : p1 : Tower_Type key is missing";
		}
		else if(p1.getLatitude() == null){
			priData = "Priority_Details : p1 : Latitude key is missing";
		}
		else if(p1.getLongitude() == null){
			priData = "Priority_Details : p1 : Longitude key is missing";
		}
		else if(p1.getLandLord_Contact_Detail() == null){
			priData = "Priority_Details : p1 : LandLord_Contact_Detail key is missing";
		}
		else if(p2 == null){
			priData = "Priority_Details : p2 json not found";
		}
		else if(p2.getSite_Address() == null){
			priData = "Priority_Details : p2 : Site_Address key is missing";
		}
		else if(p2.getTower_Type() == null){
			priData = "Priority_Details : p2 : Tower_Type key is missing";
		}
		else if(p2.getLatitude() == null){
			priData = "Priority_Details : p2 : Latitude key is missing";
		}
		else if(p2.getLongitude() == null){
			priData = "Priority_Details : p2 : Longitude key is missing";
		}
		else if(p2.getLandLord_Contact_Detail() == null){
			priData = "Priority_Details : p2 : LandLord_Contact_Detail key is missing";
		}
		else if(p3 == null){
			priData = "Priority_Details : p3 json not found";
		}
		else if(p3.getSite_Address() == null){
			priData = "Priority_Details : p3 : Site_Address key is missing";
		}
		else if(p3.getTower_Type() == null){
			priData = "Priority_Details : p3 : Tower_Type key is missing";
		}
		else if(p3.getLatitude() == null){
			priData = "Priority_Details : p3 : Latitude key is missing";
		}
		else if(p3.getLongitude() == null){
			priData = "Priority_Details : p3 : Longitude key is missing";
		}
		else if(p3.getLandLord_Contact_Detail() == null){
			priData = "Priority_Details : p3 : LandLord_Contact_Detail key is missing";
		}
		if(!priData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc(priData);
			return response;
		}
		
		SiteDetailDto Site_Detail = srDto.getSite_Detail();
		if(Site_Detail == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Site_Detail json not found");
			return response;
		}
		String siteDetailData = "";
		siteDetailData += Site_Detail.getCustomer() == null ? "Customer," : "";
		siteDetailData += Site_Detail.getCustomer_Site_Id() == null ? "Customer_Site_Id," : "";
		siteDetailData += Site_Detail.getCustomer_Site_Name() == null ? "Customer_Site_Name," : "";
		siteDetailData += Site_Detail.getCircle() == null ? "Circle," : "";
		siteDetailData += Site_Detail.getState() == null ? "State," : "";
		siteDetailData += Site_Detail.getCell_Type() == null ? "Cell_Type," : "";
		siteDetailData += Site_Detail.getSite_Type() == null ? "Site_Type," : "";
		siteDetailData += Site_Detail.getCity() == null ? "City," : "";
		siteDetailData += Site_Detail.getSearch_Ring_Radious_Mtrs() == null ? "Search_Ring_Radious_Mtrs" : "";
		siteDetailData += Site_Detail.getInfill_NewTown() == null ? "Infill_NewTown," : "";
		siteDetailData += Site_Detail.getShowCase_Non_Showcase() == null ? "ShowCase_Non_Showcase," : "";
		siteDetailData += Site_Detail.get_3_11_Towns() == null ? "_3_11_Towns," : "";
		siteDetailData += Site_Detail.getTown() == null ? "Town," : "";
		siteDetailData += Site_Detail.getRequest_for_Network_Type() == null ? "Request_for_Network_Type," : "";
		siteDetailData += Site_Detail.getProject_Name() == null ? "Project_Name," : "";
		siteDetailData += Site_Detail.getAuthority_Name() == null ? "Authority_Name," : "";
		if(!siteDetailData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Site_Detail - "+siteDetailData+" key is missing");
			return response;
		}
		ProductDetailDto Product_Detail = srDto.getProduct_Detail();
		if(Product_Detail == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Product_Detail json not found");
			return response;
		}
		String proDetData = "";
		proDetData += Product_Detail.getCustomer_Product_Type() == null ? "Customer_Product_Type," : "";
		proDetData += Product_Detail.getPreferred_Product_Type() == null ? "Preferred_Product_Type," : "";
		if(!proDetData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Product_Detail - "+proDetData+" key is missing");
			return response;
		}
		
		BtsDto BTS = srDto.getBTS();
		if(BTS == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BTS json not found");
			return response;
		}
		String btsData = "";
		btsData += BTS.getConfig_type_1() == null ? "config_type_1," : "";
		btsData += BTS.getConfig_type_2() == null ? "config_type_2," : "";
		btsData += BTS.getConfig_type_3() == null ? "config_type_3," : "";
		btsData += BTS.getConfig_Carriers() == null ? "Config_Carriers," : "";
		List<BtsCabinetDto> BTS_Cabinet = BTS.getBTS_Cabinet();
		if(BTS_Cabinet == null){
			btsData += "BTS_Cabinet,";
		}
		/*else if(BTS_Cabinet.size() == 0){
			btsData = "BTS_Cabinet";
		}*/
		if(!btsData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BTS - "+btsData+" key is missing");
			return response;
		}
		String btsCabinetData = "";
		for(BtsCabinetDto btsCabi : BTS_Cabinet){
			btsCabinetData = btsCabi.getNetWork_Type() == null ? "NetWork_Type," : "";
			btsCabinetData += btsCabi.getBTS_Type() == null ? "BTS_Type," : "";
			btsCabinetData += btsCabi.getBand() == null ? "Band," : "";
			btsCabinetData += btsCabi.getManufacturer() == null ? "Manufacturer," : "";
			btsCabinetData += btsCabi.getMake_of_BTS() == null ? "Make_of_BTS," : "";
			btsCabinetData += btsCabi.getLength_Mtrs() == null ? "Length_Mtrs," : "";
			btsCabinetData += btsCabi.getWidth_Mtrs() == null ? "Width_Mtrs," : "";
			btsCabinetData += btsCabi.getHeight_Mtrs() == null ? "Height_Mtrs," : "";
			btsCabinetData += btsCabi.getBTS_Power_Rating_KW() == null ? "BTS_Power_Rating_KW," : "";
			btsCabinetData += btsCabi.getBTS_Location() == null ? "BTS_Location," : "";
			btsCabinetData += btsCabi.getBTS_Voltage() == null ? "BTS_Voltage," : "";
			btsCabinetData += btsCabi.getMain_Unit_incase_of_TT_Split_Version() == null ? "Main_Unit_incase_of_TT_Split_Version," : "";
			btsCabinetData += btsCabi.getSpace_Occupied_in_Us_incase_of_TT_Split_Version() == null ? "Space_Occupied_in_Us_incase_of_TT_Split_Version," : "";
			btsCabinetData += btsCabi.getRRU_Unit() == null ? "RRU_Unit," : "";
			btsCabinetData += btsCabi.getNo_of_RRU_Units_incase_of_TT_Split_Version() == null ? "No_of_RRU_Units_incase_of_TT_Split_Version," : "";
			btsCabinetData += btsCabi.getCombined_wt_of_RRU_Unit_incase_of_TT_Split_Version() == null ? "Combined_wt_of_RRU_Unit_incase_of_TT_Split_Version," : "";
			btsCabinetData += btsCabi.getAGL_of_RRU_unit_in_M() == null ? "AGL_of_RRU_unit_in_M," : "";
			btsCabinetData += btsCabi.getWeight_of_BTS_including_TMA_TMB_Kg() == null ? "Weight_of_BTS_including_TMA_TMB_Kg," : "";
			btsCabinetData += btsCabi.getBillable_Weigtht() == null ? "Billable_Weigtht," : "";
			if(!btsCabinetData.equalsIgnoreCase(""))
				break;
		}
		if(!btsCabinetData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BTS : BTS_Cabinet : "+btsCabinetData+" key is missing");
			return response;
		}
		TmaTmbDto TMA_TMB = srDto.getTMA_TMB();
		if(TMA_TMB == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("TMA_TMB json not found");
			return response;
		}
		String tmatmbData = "";
		tmatmbData = TMA_TMB.getNo_of_TMA_TMB() == null ? "No_of_TMA_TMB," : "";
		tmatmbData += TMA_TMB.getWeight_of_each_TMA_TMB() == null ? "Weight_of_each_TMA_TMB," : "";
		tmatmbData += TMA_TMB.getCombined_wt_of_TMA_TMB_Kgs() == null ? "Combined_wt_of_TMA_TMB_Kgs," : "";
		tmatmbData += TMA_TMB.getHeight_at_which_needs_to_be_mounted_Mtrs() == null ? "Height_at_which_needs_to_be_mounted_Mtrs" : "";
		if(!tmatmbData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("TMA_TMB : "+tmatmbData+" key is missing");
			return response;
		}
		
		List<RadioAntennaDto> Radio_Antenna = srDto.getRadio_Antenna();
		if(Radio_Antenna == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Radio_Antenna json not found");
			return response;
		}
		/*else if(Radio_Antenna.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Radio_Antenna json list not found");
			return response;
		}*/
		String radioAntData = "";
		for(RadioAntennaDto radiaAnt : Radio_Antenna){
			radioAntData = radiaAnt.getHeight_AGL_m() == null ? "Height_AGL_m," : "";
			radioAntData += radiaAnt.getAzimuth_Degree() == null ? "Azimuth_Degree," : "";
			radioAntData += radiaAnt.getLength_m() == null ? "Length_m," : "";
			radioAntData += radiaAnt.getWidth_m() == null ? "Width_m," : "";
			radioAntData += radiaAnt.getDepth_m() == null ? "Depth_m," : "";
			radioAntData += radiaAnt.getHeight_AGL_m() == null ? "Height_AGL_m," : "";
			radioAntData += radiaAnt.getNo_of_Ports() == null ? "No_of_Ports," : "";
			BandFrequencyMHzDto BandFrequencyMHz_FrequencyCombination = 
					radiaAnt.getBandFrequencyMHz_FrequencyCombination();
			if(BandFrequencyMHz_FrequencyCombination == null){
				radioAntData += "BandFrequencyMHz_FrequencyCombination,";
				break;
			}
			
			radioAntData = "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_1() == null ? "Frequency_Number_1," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_2() == null ? "Frequency_Number_2," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_3() == null ? "Frequency_Number_3," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_4() == null ? "Frequency_Number_4," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_5() == null ? "Frequency_Number_5," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_6() == null ? "Frequency_Number_6," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_7() == null ? "Frequency_Number_7," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_8() == null ? "Frequency_Number_8" : "";
			radioAntData += radiaAnt.getRadioAntenna_Type() == null ? "RadioAntenna_Type" : "";
			if(!radioAntData.equalsIgnoreCase(""))
				break;
		}
		if(!radioAntData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Radio_Antenna : "+radioAntData+" key is missing");
			return response;
		}
		MwDto MW = srDto.getMW();
		if(MW == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MW json not found");
			return response;
		}
		String mwAntData = "";
		mwAntData += MW.getAdditional_Transmission_Rack_Space_Power_Upgrade_Requirement() == null ? "Additional_Transmission_Rack_Space_Power_Upgrade_Requirement," : "";
		mwAntData += MW.getTotalIDUs_Magazines_tobe_Installed() == null ? "TotalIDUs_Magazines_tobe_Installed," : "";
		mwAntData += MW.getTransmission_Rack_Space_Required_in_Us() == null ? "Transmission_Rack_Space_Required_in_Us," : "";
		mwAntData += MW.getPower_Rating_in_Kw() == null ? "Power_Rating_in_Kw," : "";
		mwAntData += MW.getPower_Plant_Voltage() == null ? "Power_Plant_Voltage," : "";
		List<MwAntennaDto> MW_Antenna = MW.getMW_Antenna();
		if(MW_Antenna == null){
			mwAntData += "MW_Antenna,";
		}
		/*else if(MW_Antenna.size() == 0){
			mwAntData += "MW_Antenna json list not found";
		}*/
		if(!mwAntData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MW : "+mwAntData+" key is missing");
			return response;
		}
		for(MwAntennaDto mwAntObj : MW_Antenna){
			mwAntData += mwAntObj.getMWAntenna_i_WAN() == null ? "MWAntenna_i_WAN," : "";
			mwAntData += mwAntObj.getSize_of_MW() == null ? "Size_of_MW," : "";
			mwAntData += mwAntObj.getHeight_in_Mtrs() == null ? "Height_in_Mtrs," : "";
			mwAntData += mwAntObj.getAzimuth_Degree() == null ? "Azimuth_Degree" : "";
			if(!mwAntData.equalsIgnoreCase(""))
				break;
		}
		if(!mwAntData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MW : MW_Antenna : "+mwAntData+" key is missing");
			return response;
		}
		
		List<BscRncCabinetsDto> BSC_RNC_Cabinets = srDto.getBSC_RNC_Cabinets();
		if(BSC_RNC_Cabinets == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BSC_RNC_Cabinets json not found");
			return response;
		}
		/*else if(BSC_RNC_Cabinets.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BSC_RNC_Cabinets json list not found");
			return response;
		}*/
		String bscRncData = "";
		for(BscRncCabinetsDto bscRncObj : BSC_RNC_Cabinets){
			bscRncData += bscRncObj.getNetWork_Type() == null ? "NetWork_Type," : "";
			bscRncData += bscRncObj.getBSC_RNC_Type() == null ? "BSC_RNC_Type," : "";
			bscRncData += bscRncObj.getBSC_RNC_Manufacturer() == null ? "BSC_RNC_Manufacturer," : "";
			bscRncData += bscRncObj.getBSC_RNC_Make() == null ? "BSC_RNC_Make," : "";
			bscRncData += bscRncObj.getLength_Mtrs() == null ? "Length_Mtrs," : "";
			bscRncData += bscRncObj.getBreadth_Mtrs() == null ? "Breadth_Mtrs," : "";
			bscRncData += bscRncObj.getHeight_AGL() == null ? "Height_AGL," : "";
			bscRncData += bscRncObj.getBSC_RNC_Power_Rating() == null ? "BSC_RNC_Power_Rating" : "";
			if(!bscRncData.equalsIgnoreCase(""))
				break;
		}
		if(!bscRncData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BSC_RNC_Cabinets : "+bscRncData+" key is missing");
			return response;
		}
		
		List<OtherNodeDto> Other_Node = srDto.getOther_Node();
		if(Other_Node == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Node json not found");
			return response;
		}
		/*else if(Other_Node.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Node json list not found");
			return response;
		}*/
		String otherNodeData = "";
		for(OtherNodeDto otherNodeObj : Other_Node){
			otherNodeData += otherNodeObj.getNode_Type() == null ? "Node_Type," : "";
			otherNodeData += otherNodeObj.getNode_Location() == null ? "Node_Location," : "";
			otherNodeData += otherNodeObj.getNode_Manufacturer() == null ? "Node_Manufacturer," : "";
			otherNodeData += otherNodeObj.getNode_Model() == null ? "Node_Model," : "";
			otherNodeData += otherNodeObj.getLength_Mtrs() == null ? "Length_Mtrs," : "";
			otherNodeData += otherNodeObj.getBreadth_Mtrs() == null ? "Breadth_Mtrs," : "";
			otherNodeData += otherNodeObj.getHeight_Mtrs() == null ? "Height_Mtrs," : "";
			otherNodeData += otherNodeObj.getWeight_Kg() == null ? "Weight_Kg," : "";
			otherNodeData += otherNodeObj.getNode_Voltage() == null ? "Node_Voltage," : "";
			otherNodeData += otherNodeObj.getPower_Rating_in_Kw() == null ? "Power_Rating_in_Kw," : "";
			otherNodeData += otherNodeObj.getFullRack() == null ? "FullRack," : "";
			otherNodeData += otherNodeObj.getTx_Rack_Space_Required_In_Us() == null ? "Tx_Rack_Space_Required_In_Us," : "";
			otherNodeData += otherNodeObj.getRemarks() == null ? "Remarks," : "";
			if(!otherNodeData.equalsIgnoreCase(""))
				break;
		}
		if(!otherNodeData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Node : "+otherNodeData+" key is missing");
			return response;
		}
		AdditionalInformationDto Additional_Information = srDto.getAdditional_Information();
		if(Additional_Information == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Additional_Information json not found");
			return response;
		}
		StrageticDto Stragetic = Additional_Information.getStragetic();
		if(Stragetic == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Additional_Information : Stragetic json not found");
			return response;
		}
		String strageticData = "";
		strageticData += Stragetic.getIs_it_Strategic() == null ? "Is_it_Strategic" : "";
		strageticData += Stragetic.getShelter_Size() == null ? "Shelter_Size" : "";
		strageticData += Stragetic.getLength_Mtrs() == null ? "Length_Mtrs" : "";
		strageticData += Stragetic.getBreadth_Mtrs() == null ? "Breadth_Mtrs" : "";
		strageticData += Stragetic.getHeight_AGL_Mtrs() == null ? "Height_AGL_Mtrs" : "";
		strageticData += Stragetic.getDG_Redundancy() == null ? "DG_Redundancy" : "";
		strageticData += Stragetic.getExtra_Battery_Bank_Requirement() == null ? "Extra_Battery_Bank_Requirement" : "";
		strageticData += Stragetic.getExtra_Battery_BackUp() == null ? "Extra_Battery_BackUp" : "";
		strageticData += Stragetic.getAnyother_Specific_Requirements() == null ? "Anyother_Specific_Requirements" : "";
		if(!strageticData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Additional_Information : Stragetic : "+strageticData+" key is missing");
			return response;
		}
		
		RelocationDto Relocation = Additional_Information.getRelocation();
		if(Relocation == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Additional_Information : Relocation json not found");
			return response;
		}
		String relocationData = "";
		relocationData += Relocation.getIs_it_Relocaiton_SR() == null ? "Is_it_Relocaiton_SR," : "";
		relocationData += Relocation.getSource_Req_Ref_No() == null ? "Source_Req_Ref_No," : "";
		relocationData += Relocation.getSource_TOCO_SiteId() == null ? "Source_TOCO_SiteId," : "";
		relocationData += Relocation.getRelocaiton_Reason() == null ? "Relocaiton_Reason" : "";
		if(!relocationData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Additional_Information : Relocation : "+relocationData+" key is missing");
			return response;
		}
		
		McbDto MCB = srDto.getMCB();
		if(MCB == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MCB json not found");
			return response;
		}
		String mcbData = "";
		mcbData += MCB.getTotal_No_of_MCB_Required() == null ? "Total_No_of_MCB_Required," : "";
		mcbData += MCB.get_06A() == null ? "_06A," : "";
		mcbData += MCB.get_10A() == null ? "_10A," : "";
		mcbData += MCB.get_16A() == null ? "_16A," : "";
		mcbData += MCB.get_24A() == null ? "_24A," : "";
		mcbData += MCB.get_32A() == null ? "_32A," : "";
		mcbData += MCB.get_40A() == null ? "_40A," : "";
		mcbData += MCB.get_63A() == null ? "_63A," : "";
		mcbData += MCB.get_80A() == null ? "_80A" : "";
		if(!mcbData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MCB : "+mcbData+" key is missing");
			return response;
		}
//		OtherEquipmentDto Other_Equipment = srDto.getOther_Equipment();
		List<OtherEquipmentDto> Other_Equipment = srDto.getOther_Equipment();
		if(Other_Equipment == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Equipment json not found");
			return response;
		}
		/*else if(Other_Equipment.getOther_Equipment() == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Equipment : Other_Equipment key is missing");
			return response;
		}*/
		/*else if(Other_Equipment.getOther_Equipment().equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Equipment : Other_Equipment key is missing");
			return response;
		}*/
		FiberDto Fiber = srDto.getFiber();
		if(Fiber == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fiber json not found");
			return response;
		}
		String fiberData = "";
		fiberData += Fiber.getFiber_Required() == null ? "Fiber_Required," : "";
		fiberData += Fiber.getNo_of_Fiber_Pairs() == null ? "No_of_Fiber_Pairs," : "";
		if(!fiberData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fiber : "+fiberData+" key is missing");
			return response;
		}
		
		FiberNodeProvisioningDto Fiber_Node_Provisioning = srDto.getFiber_Node_Provisioning();
		if(Fiber_Node_Provisioning == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fiber_Node_Provisioning json not found");
			return response;
		}
		String fibNodeProData = "";
		fibNodeProData += Fiber_Node_Provisioning.getIs_Fiber_Node_Provisioning_Required() == null 
				? "Is_Fiber_Node_Provisioning_Required" : "";
		fibNodeProData += Fiber_Node_Provisioning.getNo_of_Pairs() == null 
				? "No_of_Pairs" : "";
		fibNodeProData += Fiber_Node_Provisioning.getDistance_Length_of_Fiber_in_Meter() == null 
				? "Distance_Length_of_Fiber_in_Meter" : "";
		if(!fibNodeProData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fiber_Node_Provisioning : "+fibNodeProData+" key is missing");
			return response;
		}
		
		List<FibreNodeDto> Fibre_Node = srDto.getFibre_Node();
		if(Fibre_Node == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fibre_Node json not found");
			return response;
		}
		/*else if(Fibre_Node.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fibre_Node json list not found");
			return response;
		}*/
		String fibNodeData = "";
		for(FibreNodeDto obj : Fibre_Node){
			fibNodeData += obj.getNode_Type() == null ? "Node_Type," : "";
			fibNodeData += obj.getNode_Location() == null ? "Node_Location," : "";
			fibNodeData += obj.getNode_Manufacturer() == null ? "Node_Manufacturer," : "";
			fibNodeData += obj.getNode_Model() == null ? "Node_Model," : "";
			fibNodeData += obj.getLength_Mtrs() == null ? "Length_Mtrs," : "";
			fibNodeData += obj.getBreadth_Mtrs() == null ? "Breadth_Mtrs," : "";
			fibNodeData += obj.getHeight_Mtrs() == null ? "Height_Mtrs," : "";
			fibNodeData += obj.getWeight_Kg() == null ? "Weight_Kg," : "";
			fibNodeData += obj.getNode_Voltage() == null ? "Node_Voltage," : "";
			fibNodeData += obj.getPower_Rating_in_Kw() == null ? "Power_Rating_in_Kw," : "";
			fibNodeData += obj.getFullRack() == null ? "FullRack," : "";
			fibNodeData += obj.getTx_Rack_Space_required_in_Us() == null ? "Tx_Rack_Space_required_in_Us," : "";
			fibNodeData += obj.getIs_Right_Of_Way_ROW_Required_Inside_The_TOCO_Premises() == null ? 
					"Is_Right_Of_Way_ROW_Required_Inside_The_TOCO_Premises," : "";
			fibNodeData += obj.getType_Of_Fiber_Laying() == null ? "Type_Of_Fiber_Laying," : "";
			fibNodeData += obj.getType_Of_FMS() == null ? "Type_Of_FMS," : "";
			fibNodeData += obj.getRemarks() == null ? "Remarks," : "";
			fibNodeData += obj.getFull_Rack() == null ? "Full_Rack" : "";
			if(!fibNodeData.equalsIgnoreCase(""))
				break;
		}
		
		if(!fibNodeData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fibre_Node : "+fibNodeData+" key is missing");
			return response;
		}
		SacfaDto SACFA = srDto.getSACFA();
		if(SACFA == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("SACFA json not found");
			return response;
		}
		else if(SACFA.getSACFA_Number() == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("SACFA : SACFA_Number key is missing");
			return response;
		}
		DgDto DG = srDto.getDG();
		if(DG == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("DG json not found");
			return response;
		}
		else if(DG.getIs_Diesel_Generator_DG_required() == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("DG : Is_Diesel_Generator_DG_required json not found");
			return response;
		}
		return intDeo.raiseSr(srDto);
	}
	
	@Override
	public Response<SrResponse> raiseSharingSr(SharingSrRequestJsonDto srDto) {
		Response<SrResponse> response = new Response<SrResponse>();
		
		String valid = "";
		valid += srDto.getRef_Number_TVIPL() == null ? "Ref_Number_TVIPL," : "";
		valid += srDto.getSiteId() == null ? "SiteId," : "";
		valid += srDto.getOperator() == null ? "Operator," : "";
		valid += srDto.getUniqueRequestId() == null ? "UniqueRequestId," : "";
		if(!valid.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc(""+valid+" key is missing");
			return response;
		}
		
		com.tvi.sharing.dto.GlobalDto Global = srDto.getGlobal();
		if(Global == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Global json not found");
			return response;
		}
		else if(Global.getRemarks() == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Global - Remark key is missing");
			return response;
		}
		com.tvi.sharing.dto.SiteDetailDto Site_Detail = srDto.getSite_Detail();
		if(Site_Detail == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Site_Detail json not found");
			return response;
		}
		String siteDetailData = "";
		siteDetailData += Site_Detail.getTOCO_Site_Id() == null ? "TOCO_Site_Id," : "";
		siteDetailData += Site_Detail.getCustomer() == null ? "Customer," : "";
		siteDetailData += Site_Detail.getCustomer_Site_Id() == null ? "Customer_Site_Id," : "";
		siteDetailData += Site_Detail.getCustomer_Site_Name() == null ? "Customer_Site_Name," : "";
		siteDetailData += Site_Detail.getCircle() == null ? "Circle," : "";
		siteDetailData += Site_Detail.getShare_Type() == null ? "Share_Type," : "";
		siteDetailData += Site_Detail.getCity() == null ? "City," : "";
		siteDetailData += Site_Detail.getInfill_NewTown() == null ? "Infill_NewTown," : "";
		siteDetailData += Site_Detail.getShowCase_Non_Showcase() == null ? "ShowCase_Non_Showcase," : "";
		siteDetailData += Site_Detail.get_3_11_Towns() == null ? "_3_11_Towns," : "";
		siteDetailData += Site_Detail.getTown() == null ? "Town," : "";
		siteDetailData += Site_Detail.getTargetDate_DD_MM_YYYY() == null ? "TargetDate_DD_MM_YYYY," : "";
		siteDetailData += Site_Detail.getRequest_for_Network_Type() == null ? "Request_for_Network_Type," : "";
		siteDetailData += Site_Detail.getProject_Name() == null ? "Project_Name," : "";
		siteDetailData += Site_Detail.getAuthority_Name() == null ? "Authority_Name," : "";
		if(!siteDetailData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Site_Detail - "+siteDetailData+" key is missing");
			return response;
		}
		com.tvi.sharing.dto.ProductDetailDto Product_Detail = srDto.getProduct_Detail();
		if(Product_Detail == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Product_Detail json not found");
			return response;
		}
		else if(Product_Detail.getProduct_Name() == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Product_Detail : Product_Name key is missing");
			return response;
		}
		List<com.tvi.sharing.dto.RadioAntennaDto> Radio_Antenna = srDto.getRadio_Antenna();
		if(Radio_Antenna == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Radio_Antenna json not found");
			return response;
		}
		/*else if(Radio_Antenna.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Radio_Antenna json list not found");
			return response;
		}*/
		String radioAntData = "";
		for(com.tvi.sharing.dto.RadioAntennaDto radiaAnt : Radio_Antenna){
			radioAntData = radiaAnt.getHeight_AGL_m() == null ? "Height_AGL_m," : "";
			radioAntData += radiaAnt.getAzimuth_Degree() == null ? "Azimuth_Degree," : "";
			radioAntData += radiaAnt.getLength_m() == null ? "Length_m," : "";
			radioAntData += radiaAnt.getWidth_m() == null ? "Width_m," : "";
			radioAntData += radiaAnt.getDepth_m() == null ? "Depth_m," : "";
			radioAntData += radiaAnt.getHeight_AGL_m() == null ? "Height_AGL_m," : "";
			radioAntData += radiaAnt.getNo_of_Ports() == null ? "No_of_Ports," : "";
			
			com.tvi.sharing.dto.BandFrequencyMHzDto BandFrequencyMHz_FrequencyCombination = 
					radiaAnt.getBandFrequencyMHz_FrequencyCombination();
			if(BandFrequencyMHz_FrequencyCombination == null){
				radioAntData += "BandFrequencyMHz_FrequencyCombination,";
				break;
			}
			radioAntData = "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_1() == null ? "Frequency_Number_1," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_2() == null ? "Frequency_Number_2," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_3() == null ? "Frequency_Number_3," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_4() == null ? "Frequency_Number_4," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_5() == null ? "Frequency_Number_5," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_6() == null ? "Frequency_Number_6," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_7() == null ? "Frequency_Number_7," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_8() == null ? "Frequency_Number_8" : "";
			radioAntData += radiaAnt.getRadioAntenna_Type() == null ? "RadioAntenna_Type" : "";
			
			if(!radioAntData.equalsIgnoreCase(""))
				break;
		}
		if(!radioAntData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Radio_Antenna : "+radioAntData+" key is missing");
			return response;
		}
		
		List<com.tvi.sharing.dto.BscRncCabinetsDto> BSC_RNC_Cabinets = srDto.getBSC_RNC_Cabinets();
		if(BSC_RNC_Cabinets == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BSC_RNC_Cabinets json not found");
			return response;
		}
		/*else if(BSC_RNC_Cabinets.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BSC_RNC_Cabinets json list not found");
			return response;
		}*/
		String bscRncData = "";
		for(com.tvi.sharing.dto.BscRncCabinetsDto bscRncObj : BSC_RNC_Cabinets){
			bscRncData += bscRncObj.getNetWork_Type() == null ? "NetWork_Type," : "";
			bscRncData += bscRncObj.getBSC_RNC_Type() == null ? "BSC_RNC_Type," : "";
			bscRncData += bscRncObj.getBSC_RNC_Manufacturer() == null ? "BSC_RNC_Manufacturer," : "";
			bscRncData += bscRncObj.getBSC_RNC_Make() == null ? "BSC_RNC_Make," : "";
			bscRncData += bscRncObj.getLength_Mtrs() == null ? "Length_Mtrs," : "";
			bscRncData += bscRncObj.getBreadth_Mtrs() == null ? "Breadth_Mtrs," : "";
			bscRncData += bscRncObj.getHeight_AGL() == null ? "Height_AGL," : "";
			bscRncData += bscRncObj.getBSC_RNC_Power_Rating() == null ? "BSC_RNC_Power_Rating" : "";
			if(!bscRncData.equalsIgnoreCase(""))
				break;
		}
		if(!bscRncData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BSC_RNC_Cabinets : "+bscRncData+" key is missing");
			return response;
		}
		com.tvi.sharing.dto.MwDto MW = srDto.getMW();
		if(MW == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MW json not found");
			return response;
		}
		String mwAntData = "";
		mwAntData += MW.getAdditional_Transmission_Rack_Space_Power_Upgrade_Requirement() == null ? "Additional_Transmission_Rack_Space_Power_Upgrade_Requirement," : "";
		mwAntData += MW.getTotalIDUs_Magazines_tobe_Installed() == null ? "TotalIDUs_Magazines_tobe_Installed," : "";
		mwAntData += MW.getTransmission_Rack_Space_Required_in_Us() == null ? "Transmission_Rack_Space_Required_in_Us," : "";
		mwAntData += MW.getPower_Rating_in_Kw() == null ? "Power_Rating_in_Kw," : "";
		mwAntData += MW.getPower_Plant_Voltage() == null ? "Power_Plant_Voltage," : "";
		List<com.tvi.sharing.dto.MwAntennaDto> MW_Antenna = MW.getMW_Antenna();
		if(MW_Antenna == null){
			mwAntData += "MW_Antenna,";
		}
		/*else if(MW_Antenna.size() == 0){
			mwAntData += "MW_Antenna json list not found";
		}*/
		if(!mwAntData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MW : "+mwAntData+" key is missing");
			return response;
		}
		for(com.tvi.sharing.dto.MwAntennaDto mwAntObj : MW_Antenna){
			mwAntData += mwAntObj.getMWAntenna_i_WAN() == null ? "MWAntenna_i_WAN," : "";
			mwAntData += mwAntObj.getSize_of_MW() == null ? "Size_of_MW," : "";
			mwAntData += mwAntObj.getHeight_in_Mtrs() == null ? "Height_in_Mtrs," : "";
			mwAntData += mwAntObj.getAzimuth_Degree() == null ? "Azimuth_Degree" : "";
			if(!mwAntData.equalsIgnoreCase(""))
				break;
		}
		if(!mwAntData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MW : MW_Antenna : "+mwAntData+" key is missing");
			return response;
		}
		
		List<com.tvi.sharing.dto.OtherNodeDto> Other_Node = srDto.getOther_Node();
		if(Other_Node == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Node json not found");
			return response;
		}
		/*else if(Other_Node.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Node json list not found");
			return response;
		}*/
		String otherNodeData = "";
		for(com.tvi.sharing.dto.OtherNodeDto otherNodeObj : Other_Node){
			otherNodeData += otherNodeObj.getNode_Type() == null ? "Node_Type," : "";
			otherNodeData += otherNodeObj.getNode_Location() == null ? "Node_Location," : "";
			otherNodeData += otherNodeObj.getNode_Manufacturer() == null ? "Node_Manufacturer," : "";
			otherNodeData += otherNodeObj.getNode_Model() == null ? "Node_Model," : "";
			otherNodeData += otherNodeObj.getLength_Mtrs() == null ? "Length_Mtrs," : "";
			otherNodeData += otherNodeObj.getBreadth_Mtrs() == null ? "Breadth_Mtrs," : "";
			otherNodeData += otherNodeObj.getHeight_Mtrs() == null ? "Height_Mtrs," : "";
			otherNodeData += otherNodeObj.getWeight_Kg() == null ? "Weight_Kg," : "";
			otherNodeData += otherNodeObj.getNode_Voltage() == null ? "Node_Voltage," : "";
			otherNodeData += otherNodeObj.getPower_Rating_in_Kw() == null ? "Power_Rating_in_Kw," : "";
			otherNodeData += otherNodeObj.getFullRack() == null ? "FullRack," : "";
			otherNodeData += otherNodeObj.getTx_Rack_Space_Required_In_Us() == null ? "Tx_Rack_Space_Required_In_Us," : "";
			otherNodeData += otherNodeObj.getRemarks() == null ? "Remarks," : "";
			if(!otherNodeData.equalsIgnoreCase(""))
				break;
		}
		if(!otherNodeData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Node : "+otherNodeData+" key is missing");
			return response;
		}
		com.tvi.sharing.dto.TmaTmbDto TMA_TMB = srDto.getTMA_TMB();
		if(TMA_TMB == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("TMA_TMB json not found");
			return response;
		}
		String tmatmbData = "";
		tmatmbData = TMA_TMB.getNo_of_TMA_TMB() == null ? "No_of_TMA_TMB," : "";
		tmatmbData += TMA_TMB.getWeight_of_each_TMA_TMB() == null ? "Weight_of_each_TMA_TMB," : "";
		tmatmbData += TMA_TMB.getCombined_wt_of_TMA_TMB_Kgs() == null ? "Combined_wt_of_TMA_TMB_Kgs," : "";
		tmatmbData += TMA_TMB.getHeight_at_which_needs_to_be_mounted_Mtrs() == null ? "Height_at_which_needs_to_be_mounted_Mtrs" : "";
		if(!tmatmbData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("TMA_TMB : "+tmatmbData+" key is missing");
			return response;
		}
		com.tvi.sharing.dto.BtsDto BTS = srDto.getBTS();
		if(BTS == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BTS json not found");
			return response;
		}
		String btsData = "";
		btsData += BTS.getConfig_type_1() == null ? "config_type_1," : "";
		btsData += BTS.getConfig_type_2() == null ? "config_type_2," : "";
		btsData += BTS.getConfig_type_3() == null ? "config_type_3," : "";
		btsData += BTS.getConfig_Carriers() == null ? "Config_Carriers," : "";
		List<com.tvi.sharing.dto.BtsCabinetDto> BTS_Cabinet = BTS.getBTS_Cabinet();
		if(BTS_Cabinet == null){
			btsData += "BTS_Cabinet,";
		}
		/*else if(BTS_Cabinet.size() == 0){
			btsData = "BTS_Cabinet";
		}*/
		if(!btsData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BTS - "+btsData+" key is missing");
			return response;
		}
		String btsCabinetData = "";
		for(com.tvi.sharing.dto.BtsCabinetDto btsCabi : BTS_Cabinet){
			btsCabinetData = btsCabi.getNetWork_Type() == null ? "NetWork_Type," : "";
			btsCabinetData += btsCabi.getBTS_Type() == null ? "BTS_Type," : "";
			btsCabinetData += btsCabi.getBand() == null ? "Band," : "";
			btsCabinetData += btsCabi.getManufacturer() == null ? "Manufacturer," : "";
			btsCabinetData += btsCabi.getMake_of_BTS() == null ? "Make_of_BTS," : "";
			btsCabinetData += btsCabi.getLength_Mtrs() == null ? "Length_Mtrs," : "";
			btsCabinetData += btsCabi.getWidth_Mtrs() == null ? "Width_Mtrs," : "";
			btsCabinetData += btsCabi.getHeight_Mtrs() == null ? "Height_Mtrs," : "";
			btsCabinetData += btsCabi.getBTS_Power_Rating_KW() == null ? "BTS_Power_Rating_KW," : "";
			btsCabinetData += btsCabi.getBTS_Location() == null ? "BTS_Location," : "";
			btsCabinetData += btsCabi.getBTS_Voltage() == null ? "BTS_Voltage," : "";
			btsCabinetData += btsCabi.getMain_Unit_incase_of_TT_Split_Version() == null ? "Main_Unit_incase_of_TT_Split_Version," : "";
			btsCabinetData += btsCabi.getSpace_Occupied_in_Us_incase_of_TT_Split_Version() == null ? "Space_Occupied_in_Us_incase_of_TT_Split_Version," : "";
			btsCabinetData += btsCabi.getRRU_Unit() == null ? "RRU_Unit," : "";
			btsCabinetData += btsCabi.getNo_of_RRU_Units_incase_of_TT_Split_Version() == null ? "No_of_RRU_Units_incase_of_TT_Split_Version," : "";
			btsCabinetData += btsCabi.getCombined_wt_of_RRU_Unit_incase_of_TT_Split_Version() == null ? "Combined_wt_of_RRU_Unit_incase_of_TT_Split_Version," : "";
			btsCabinetData += btsCabi.getAGL_of_RRU_unit_in_M() == null ? "AGL_of_RRU_unit_in_M," : "";
			btsCabinetData += btsCabi.getWeight_of_BTS_including_TMA_TMB_Kg() == null ? "Weight_of_BTS_including_TMA_TMB_Kg," : "";
			btsCabinetData += btsCabi.getBillable_Weigtht() == null ? "Billable_Weigtht," : "";
			if(!btsCabinetData.equalsIgnoreCase(""))
				break;
		}
		if(!btsCabinetData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BTS : BTS_Cabinet : "+btsCabinetData+" key is missing");
			return response;
		}
		com.tvi.sharing.dto.AdditionalInformationDto Additional_Information = srDto.getAdditional_Information();
		if(Additional_Information == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Additional_Information json not found");
			return response;
		}
		com.tvi.sharing.dto.StrageticDto Stragetic = Additional_Information.getStragetic();
		if(Stragetic == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Additional_Information : Stragetic json not found");
			return response;
		}
		String strageticData = "";
		strageticData += Stragetic.getIs_it_Strategic() == null ? "Is_it_Strategic" : "";
		strageticData += Stragetic.getShelter_Size() == null ? "Shelter_Size" : "";
		strageticData += Stragetic.getLength_Mtrs() == null ? "Length_Mtrs" : "";
		strageticData += Stragetic.getBreadth_Mtrs() == null ? "Breadth_Mtrs" : "";
		strageticData += Stragetic.getHeight_AGL_Mtrs() == null ? "Height_AGL_Mtrs" : "";
		strageticData += Stragetic.getDG_Redundancy() == null ? "DG_Redundancy" : "";
		strageticData += Stragetic.getExtra_Battery_Bank_Requirement() == null ? "Extra_Battery_Bank_Requirement" : "";
		strageticData += Stragetic.getExtra_Battery_BackUp() == null ? "Extra_Battery_BackUp" : "";
		strageticData += Stragetic.getAny_other_specific_requirements() == null ? "Any_other_specific_requirements" : "";
		if(!strageticData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Additional_Information : Stragetic : "+strageticData+" key is missing");
			return response;
		}
		
		com.tvi.sharing.dto.McbDto MCB = srDto.getMCB();
		if(MCB == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MCB json not found");
			return response;
		}
		String mcbData = "";
		mcbData += MCB.getTotal_No_of_MCB_Required() == null ? "Total_No_of_MCB_Required," : "";
		mcbData += MCB.get_06A() == null ? "_06A," : "";
		mcbData += MCB.get_10A() == null ? "_10A," : "";
		mcbData += MCB.get_16A() == null ? "_16A," : "";
		mcbData += MCB.get_24A() == null ? "_24A," : "";
		mcbData += MCB.get_32A() == null ? "_32A," : "";
		mcbData += MCB.get_40A() == null ? "_40A," : "";
		mcbData += MCB.get_63A() == null ? "_63A," : "";
		mcbData += MCB.get_80A() == null ? "_80A" : "";
		if(!mcbData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MCB : "+mcbData+" key is missing");
			return response;
		}
//		com.tvi.sharing.dto.OtherEquipmentDto Other_Equipment = srDto.getOther_Equipment();
//		if(Other_Equipment == null){
//			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
//			response.setResponseDesc("Other_Equipment json not found");
//			return response;
//		}
//		else if(Other_Equipment.getOther_Equipment() == null){
//			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
//			response.setResponseDesc("Other_Equipment : Other_Equipment key is missing");
//			return response;
//		}
		List<com.tvi.sharing.dto.OtherEquipmentDto> Other_Equipment = srDto.getOther_Equipment();
		if(Other_Equipment == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Equipment json not found");
			return response;
		}
		com.tvi.sharing.dto.FiberDto Fiber = srDto.getFiber();
		if(Fiber == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fiber json not found");
			return response;
		}
		String fiberData = "";
		fiberData += Fiber.getFiber_Required() == null ? "Fiber_Required," : "";
		fiberData += Fiber.getNo_of_Fiber_Pairs() == null ? "No_of_Fiber_Pairs," : "";
		if(!fiberData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fiber : "+fiberData+" key is missing");
			return response;
		}
		com.tvi.sharing.dto.FiberNodeProvisioningDto Fiber_Node_Provisioning = srDto.getFiber_Node_Provisioning();
		if(Fiber_Node_Provisioning == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fiber_Node_Provisioning json not found");
			return response;
		}
		String fibNodeProData = "";
		fibNodeProData += Fiber_Node_Provisioning.getIs_Fiber_Node_Provisioning_Required() == null 
				? "Is_Fiber_Node_Provisioning_Required" : "";
		fibNodeProData += Fiber_Node_Provisioning.getNo_of_Pairs() == null 
				? "No_of_Pairs" : "";
		fibNodeProData += Fiber_Node_Provisioning.getDistance_Length_of_Fiber_in_Meter() == null 
				? "Distance_Length_of_Fiber_in_Meter" : "";
		if(!fibNodeProData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fiber_Node_Provisioning : "+fibNodeProData+" key is missing");
			return response;
		}
		List<com.tvi.sharing.dto.FibreNodeDto> Fibre_Node = srDto.getFibre_Node();
		if(Fibre_Node == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fibre_Node json not found");
			return response;
		}
		/*else if(Fibre_Node.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fibre_Node json list not found");
			return response;
		}*/
		String fibNodeData = "";
		for(com.tvi.sharing.dto.FibreNodeDto obj : Fibre_Node){
			fibNodeData += obj.getNode_Type() == null ? "Node_Type," : "";
			fibNodeData += obj.getNode_Location() == null ? "Node_Location," : "";
			fibNodeData += obj.getNode_Manufacturer() == null ? "Node_Manufacturer," : "";
			fibNodeData += obj.getNode_Model() == null ? "Node_Model," : "";
			fibNodeData += obj.getLength_Mtrs() == null ? "Length_Mtrs," : "";
			fibNodeData += obj.getBreadth_Mtrs() == null ? "Breadth_Mtrs," : "";
			fibNodeData += obj.getHeight_Mtrs() == null ? "Height_Mtrs," : "";
			fibNodeData += obj.getWeight_Kg() == null ? "Weight_Kg," : "";
			fibNodeData += obj.getNode_Voltage() == null ? "Node_Voltage," : "";
			fibNodeData += obj.getPower_Rating_in_Kw() == null ? "Power_Rating_in_Kw," : "";
			fibNodeData += obj.getFullRack() == null ? "FullRack," : "";
			fibNodeData += obj.getTx_Rack_Space_required_in_Us() == null ? "Tx_Rack_Space_required_in_Us," : "";
			fibNodeData += obj.getIs_Right_Of_Way_ROW_Required_Inside_The_TOCO_Premises() == null ? 
					"Is_Right_Of_Way_ROW_Required_Inside_The_TOCO_Premises," : "";
			fibNodeData += obj.getType_Of_Fiber_Laying() == null ? "Type_Of_Fiber_Laying," : "";
			fibNodeData += obj.getType_Of_FMS() == null ? "Type_Of_FMS," : "";
			fibNodeData += obj.getRemarks() == null ? "Remarks," : "";
			fibNodeData += obj.getFull_Rack() == null ? "Full_Rack" : "";
			if(!fibNodeData.equalsIgnoreCase(""))
				break;
		}
		
		if(!fibNodeData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fibre_Node : "+fibNodeData+" key is missing");
			return response;
		}
		com.tvi.sharing.dto.CircleInformationDto Circle_Information = srDto.getCircle_Information();
		if(Circle_Information == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Circle_Information json not found");
			return response;
		}
		String circleInfoData = "";
		circleInfoData += Circle_Information.getName_of_District_SSA() == null ? "Name_of_District_SSA," : "";
		circleInfoData += Circle_Information.getCluster() == null ? "Cluster," : "";
		circleInfoData += Circle_Information.getMSA_Type() == null ? "MSA_Type," : "";
		if(!circleInfoData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Circle_Information : "+circleInfoData+" key is missing");
			return response;
		}
		
		com.tvi.sharing.dto.SacfaDto SACFA = srDto.getSACFA();
		if(SACFA == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("SACFA json not found");
			return response;
		}
		else if(SACFA.getSACFA_Number() == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("SACFA : SACFA_Number key is missing");
			return response;
		}
		com.tvi.sharing.dto.DgDto DG = srDto.getDG();
		if(DG == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("DG json not found");
			return response;
		}
		else if(DG.getIs_Diesel_Generator_DG_required() == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("DG : Is_Diesel_Generator_DG_required json not found");
			return response;
		}
		return intDeo.raiseSharingSr(srDto);
	}
	
	@Override
	public Response<SrResponse> raiseUpgradeSr(UpgradeSrRequestJsonDto srDto) {
		Response<SrResponse> response = new Response<SrResponse>();
		
		String valid = "";
		valid += srDto.getRef_Number_TVIPL() == null ? "Ref_Number_TVIPL," : "";
		valid += srDto.getSiteId() == null ? "SiteId," : "";
		if(!valid.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc(""+valid+" key is missing");
			return response;
		}
	
		com.tvi.upgrade.dto.GlobalDto Global = srDto.getGlobal();
		if(Global == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Global json not found");
			return response;
		}
		String globalData = "";
		globalData += Global.getOperator() == null ? "Operator," : "";
		globalData += Global.getRequest_for_Network_Type() == null ? "Request_for_Network_Type," : "";
		RequestedEquipmentDto reqEqui = Global.getRequested_Equipment();
		globalData += reqEqui == null ? "Requested_Equipment," : "";
		globalData += reqEqui.getMW_Antenna() == null ? "MW_Antenna," : "";
		globalData += reqEqui.getMW_IDU() == null ? "MW_IDU," : "";
		globalData += reqEqui.getRadio_Antenna() == null ? "Radio_Antenna," : "";
		globalData += reqEqui.getTower_Mounted_Booster() == null ? "Tower_Mounted_Booster," : "";
		globalData += reqEqui.getBTS_Cabinet() == null ? "BTS_Cabinet," : "";
		globalData += reqEqui.getFiber_Node_Provisioning() == null ? "Fiber_Node_Provisioning," : "";
		globalData += reqEqui.getMicro_to_Macro_conversion() == null ? "Micro_to_Macro_conversion," : "";
		globalData += reqEqui.getOther_Nodes() == null ? "Other_Nodes," : "";
		globalData += reqEqui.getBSC() == null ? "BSC," : "";
		globalData += reqEqui.getStrategic_Conversion() == null ? "Strategic_Conversion," : "";
		globalData += reqEqui.getOther_Equipment() == null ? "Other_Equipment," : "";
		globalData += reqEqui.getExtend_Tenure() == null ? "Extend_Tenure," : "";
		globalData += reqEqui.getPole() == null ? "Pole," : "";
		globalData += reqEqui.getHubTag_Untag() == null ? "HubTag_Untag," : "";
		globalData += Global.getActivity_Type() == null ? "Activity_Type," : "";
		globalData += Global.getRequest_Ref_No() == null ? "Request_Ref_No," : "";
		globalData += Global.getRemarks() == null ? "Remarks," : "";
		globalData += Global.getTOCO_Site_ID() == null ? "TOCO_Site_ID," : "";
		globalData += Global.getProject_Name() == null ? "Project_Name," : "";
		globalData += Global.getRL_Type() == null ? "RL_Type," : "";
		globalData += Global.getUpgrade_Type() == null ? "Upgrade_Type," : "";
		if(!globalData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Global : "+globalData+" key is missing");
			return response;
		}
		
		List<com.tvi.upgrade.dto.RadioAntennaDto> Radio_Antenna = srDto.getRadio_Antenna();
		if(Radio_Antenna == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Radio_Antenna json not found");
			return response;
		}
		/*else if(Radio_Antenna.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Radio_Antenna json list not found");
			return response;
		}*/
		String radioAntData = "";
		for(com.tvi.upgrade.dto.RadioAntennaDto radiaAnt : Radio_Antenna){
			radioAntData = radiaAnt.getAction() == null ? "Action," : "";
			radioAntData += radiaAnt.getRadioAntenna_i_WAN() == null ? "RadioAntenna_i_WAN," : "";
			radioAntData += radiaAnt.getHeight_AGL_m() == null ? "Height_AGL_m," : "";
			radioAntData += radiaAnt.getAzimuth_Degree() == null ? "Azimuth_Degree," : "";
			radioAntData += radiaAnt.getLength_m() == null ? "Length_m," : "";
			radioAntData += radiaAnt.getWidth_m() == null ? "Width_m," : "";
			radioAntData += radiaAnt.getDepth_m() == null ? "Depth_m," : "";
			radioAntData += radiaAnt.getNo_of_Ports() == null ? "No_of_Ports," : "";
			com.tvi.upgrade.dto.BandFrequencyMHzDto BandFrequencyMHz_FrequencyCombination = 
					radiaAnt.getBandFrequencyMHz_FrequencyCombination();
			if(BandFrequencyMHz_FrequencyCombination == null){
				radioAntData += "BandFrequencyMHz_FrequencyCombination,";
				break;
			}
			
			radioAntData = "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_1() == null ? "Frequency_Number_1," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_2() == null ? "Frequency_Number_2," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_3() == null ? "Frequency_Number_3," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_4() == null ? "Frequency_Number_4," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_5() == null ? "Frequency_Number_5," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_6() == null ? "Frequency_Number_6," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_7() == null ? "Frequency_Number_7," : "";
			radioAntData += BandFrequencyMHz_FrequencyCombination.getFrequency_Number_8() == null ? "Frequency_Number_8" : "";
			radioAntData += radiaAnt.getRadioAntenna_Type() == null ? "RadioAntenna_Type" : "";
			if(!radioAntData.equalsIgnoreCase(""))
				break;
		}
		if(!radioAntData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Radio_Antenna : "+radioAntData+" key is missing");
			return response;
		}
		List<com.tvi.upgrade.dto.BscRncCabinetsDto> BSC_RNC_Cabinets = srDto.getBSC_RNC_Cabinets();
		if(BSC_RNC_Cabinets == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BSC_RNC_Cabinets json not found");
			return response;
		}
		/*else if(BSC_RNC_Cabinets.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BSC_RNC_Cabinets json list not found");
			return response;
		}*/
		String bscRncData = "";
		for(com.tvi.upgrade.dto.BscRncCabinetsDto bscRncObj : BSC_RNC_Cabinets){
			bscRncData += bscRncObj.getAction() == null ? "Action," : "";
			bscRncData += bscRncObj.getNetWork_Type() == null ? "NetWork_Type," : "";
			bscRncData += bscRncObj.getBSC_RNC_Type() == null ? "BSC_RNC_Type," : "";
			bscRncData += bscRncObj.getBSC_RNC_Manufacturer() == null ? "BSC_RNC_Manufacturer," : "";
			bscRncData += bscRncObj.getBSC_RNC_Make() == null ? "BSC_RNC_Make," : "";
			bscRncData += bscRncObj.getLength_Mtrs() == null ? "Length_Mtrs," : "";
			bscRncData += bscRncObj.getBreadth_Mtrs() == null ? "Breadth_Mtrs," : "";
			bscRncData += bscRncObj.getHeight_AGL() == null ? "Height_AGL," : "";
			bscRncData += bscRncObj.getBSC_RNC_Power_Rating() == null ? "BSC_RNC_Power_Rating," : "";
			bscRncData += bscRncObj.getNodeType() == null ? "NodeType" : "";
			if(!bscRncData.equalsIgnoreCase(""))
				break;
		}
		if(!bscRncData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BSC_RNC_Cabinets : "+bscRncData+" key is missing");
			return response;
		}
		
		com.tvi.upgrade.dto.MwDto MW = srDto.getMW();
		if(MW == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MW json not found");
			return response;
		}
		String mwAntData = "";
		mwAntData += MW.getAdditional_Transmission_Rack_Space_Power_Upgrade_Requirement() == null ? "Additional_Transmission_Rack_Space_Power_Upgrade_Requirement," : "";
		mwAntData += MW.getTotalIDUs_Magazines_tobe_Installed() == null ? "TotalIDUs_Magazines_tobe_Installed," : "";
		mwAntData += MW.getTransmission_Rack_Space_Required_in_Us() == null ? "Transmission_Rack_Space_Required_in_Us," : "";
		mwAntData += MW.getPower_Rating_in_Kw() == null ? "Power_Rating_in_Kw," : "";
		mwAntData += MW.getPower_Plant_Voltage() == null ? "Power_Plant_Voltage," : "";
		List<com.tvi.upgrade.dto.MwAntennaDto> MW_Antenna = MW.getMW_Antenna();
		if(MW_Antenna == null){
			mwAntData += "MW_Antenna,";
		}
		/*else if(MW_Antenna.size() == 0){
			mwAntData += "MW_Antenna json list not found";
		}*/
		if(!mwAntData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MW : "+mwAntData+" key is missing");
			return response;
		}
		for(com.tvi.upgrade.dto.MwAntennaDto mwAntObj : MW_Antenna){
			mwAntData += mwAntObj.getAction() == null ? "Action," : "";
			mwAntData += mwAntObj.getSource() == null ? "Source," : "";
			mwAntData += mwAntObj.getMWAntenna_i_WAN() == null ? "MWAntenna_i_WAN," : "";
			mwAntData += mwAntObj.getSize_of_MW() == null ? "Size_of_MW," : "";
			mwAntData += mwAntObj.getHeight_in_Mtrs() == null ? "Height_in_Mtrs," : "";
			mwAntData += mwAntObj.getAzimuth_Degree() == null ? "Azimuth_Degree" : "";
			if(!mwAntData.equalsIgnoreCase(""))
				break;
		}
		if(!mwAntData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MW : MW_Antenna : "+mwAntData+" key is missing");
			return response;
		}
		com.tvi.upgrade.dto.FiberNodeProvisioningDto Fiber_Node_Provisioning = srDto.getFiber_Node_Provisioning();
		if(Fiber_Node_Provisioning == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fiber_Node_Provisioning json not found");
			return response;
		}
		String fibNodeProData = "";
		fibNodeProData += Fiber_Node_Provisioning.getIs_Fiber_Node_Provisioning_Required() == null 
				? "Is_Fiber_Node_Provisioning_Required" : "";
		fibNodeProData += Fiber_Node_Provisioning.getNo_of_Pairs() == null 
				? "No_of_Pairs" : "";
		fibNodeProData += Fiber_Node_Provisioning.getDistance_Length_of_Fiber_in_Meter() == null 
				? "Distance_Length_of_Fiber_in_Meter" : "";
		if(!fibNodeProData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fiber_Node_Provisioning : "+fibNodeProData+" key is missing");
			return response;
		}
		
		List<com.tvi.upgrade.dto.FibreNodeDto> Fibre_Node = srDto.getFibre_Node();
		if(Fibre_Node == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fibre_Node json not found");
			return response;
		}
		/*else if(Fibre_Node.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fibre_Node json list not found");
			return response;
		}*/
		String fibNodeData = "";
		for(com.tvi.upgrade.dto.FibreNodeDto obj : Fibre_Node){
			fibNodeData += obj.getRemarks() == null ? "Remarks," : "";
			fibNodeData += obj.getAction() == null ? "Action," : "";
			fibNodeData += obj.getSource() == null ? "Source," : "";
			fibNodeData += obj.getNode_Type() == null ? "Node_Type," : "";
			fibNodeData += obj.getNode_Location() == null ? "Node_Location," : "";
			fibNodeData += obj.getNode_Manufacturer() == null ? "Node_Manufacturer," : "";
			fibNodeData += obj.getNode_Model() == null ? "Node_Model," : "";
			fibNodeData += obj.getLength_Mtrs() == null ? "Length_Mtrs," : "";
			fibNodeData += obj.getBreadth_Mtrs() == null ? "Breadth_Mtrs," : "";
			fibNodeData += obj.getHeight_Mtrs() == null ? "Height_Mtrs," : "";
			fibNodeData += obj.getWeight_Kg() == null ? "Weight_Kg," : "";
			fibNodeData += obj.getNode_Voltage() == null ? "Node_Voltage," : "";
			fibNodeData += obj.getPower_Rating_in_Kw() == null ? "Power_Rating_in_Kw," : "";
			fibNodeData += obj.getFullRack() == null ? "FullRack," : "";
			fibNodeData += obj.getTx_Rack_Space_required_in_Us() == null ? "Tx_Rack_Space_required_in_Us," : "";
			fibNodeData += obj.getIs_Right_of_Way_ROW_required_inside_the_Indus_premises() == null ? 
					"Is_Right_of_Way_ROW_required_inside_the_Indus_premises," : "";
			fibNodeData += obj.getType_of_Fiber_Laying() == null ? "Type_of_Fiber_Laying," : "";
			fibNodeData += obj.getType_of_FMS() == null ? "Type_of_FMS," : "";
			if(!fibNodeData.equalsIgnoreCase(""))
				break;
		}
		if(!fibNodeData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Fibre_Node : "+fibNodeData+" key is missing");
			return response;
		}
		com.tvi.upgrade.dto.StrategicConversionDto Strategic_Conversion = srDto.getStrategic_Conversion();
		if(Strategic_Conversion == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Strategic_Conversion json not found");
			return response;
		}
		String strageticData = "";
		strageticData += Strategic_Conversion.getIs_it_Strategic() == null ? "Is_it_Strategic" : "";
		strageticData += Strategic_Conversion.getShelter_Size() == null ? "Shelter_Size" : "";
		strageticData += Strategic_Conversion.getLength_Mtrs() == null ? "Length_Mtrs" : "";
		strageticData += Strategic_Conversion.getBreadth_Mtrs() == null ? "Breadth_Mtrs" : "";
		strageticData += Strategic_Conversion.getHeight_AGL_Mtrs() == null ? "Height_AGL_Mtrs" : "";
		strageticData += Strategic_Conversion.getDG_Redundancy() == null ? "DG_Redundancy" : "";
		strageticData += Strategic_Conversion.getExtra_Battery_Bank_Requirement() == null ? "Extra_Battery_Bank_Requirement" : "";
		strageticData += Strategic_Conversion.getExtra_Battery_BackUp() == null ? "Extra_Battery_BackUp" : "";
		strageticData += Strategic_Conversion.getAnyother_Specific_Requirements() == null ? "Anyother_Specific_Requirements" : "";
		if(!strageticData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Strategic_Conversion : "+strageticData+" key is missing");
			return response;
		}
		
		List<com.tvi.upgrade.dto.OtherNodeDto> Other_Node = srDto.getOther_Node();
		if(Other_Node == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Node json not found");
			return response;
		}
		/*else if(Other_Node.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Node json list not found");
			return response;
		}*/
		String otherNodeData = "";
		for(com.tvi.upgrade.dto.OtherNodeDto otherNodeObj : Other_Node){
			otherNodeData += otherNodeObj.getAction() == null ? "Action," : "";
			otherNodeData += otherNodeObj.getNode_Type() == null ? "Node_Type," : "";
			otherNodeData += otherNodeObj.getNode_Location() == null ? "Node_Location," : "";
			otherNodeData += otherNodeObj.getNode_Manufacturer() == null ? "Node_Manufacturer," : "";
			otherNodeData += otherNodeObj.getNode_Model() == null ? "Node_Model," : "";
			otherNodeData += otherNodeObj.getLength_Mtrs() == null ? "Length_Mtrs," : "";
			otherNodeData += otherNodeObj.getBreadth_Mtrs() == null ? "Breadth_Mtrs," : "";
			otherNodeData += otherNodeObj.getHeight_Mtrs() == null ? "Height_Mtrs," : "";
			otherNodeData += otherNodeObj.getWeight_Kg() == null ? "Weight_Kg," : "";
			otherNodeData += otherNodeObj.getNode_Voltage() == null ? "Node_Voltage," : "";
			otherNodeData += otherNodeObj.getPower_Rating_in_Kw() == null ? "Power_Rating_in_Kw," : "";
			otherNodeData += otherNodeObj.getFullRack() == null ? "FullRack," : "";
			otherNodeData += otherNodeObj.getTx_Rack_Space_Required_In_Us() == null ? "Tx_Rack_Space_Required_In_Us," : "";
			otherNodeData += otherNodeObj.getRemarks() == null ? "Remarks," : "";
			if(!otherNodeData.equalsIgnoreCase(""))
				break;
		}
		if(!otherNodeData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Node : "+otherNodeData+" key is missing");
			return response;
		}
		List<com.tvi.upgrade.dto.TmaTmbDto> TMA_TMB = srDto.getTMA_TMB();
		if(TMA_TMB == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("TMA_TMB json not found");
			return response;
		}
		/*else if(TMA_TMB.size() == 0){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("TMA_TMB json list not found");
			return response;
		}*/
		String tmaTmbData = "";
		for(com.tvi.upgrade.dto.TmaTmbDto dto : TMA_TMB){
			tmaTmbData += dto.getAction() == null ? "Action," : "";
			tmaTmbData += dto.getSource() == null ? "Source," : "";
			tmaTmbData += dto.getNo_of_TMA_TMB() == null ? "No_of_TMA_TMB," : "";
			tmaTmbData += dto.getWeight_of_each_TMA_TMB() == null ? "Weight_of_each_TMA_TMB," : "";
			tmaTmbData += dto.getCombined_wt_of_TMA_TMB_Kgs() == null ? "Combined_wt_of_TMA_TMB_Kgs," : "";
			tmaTmbData += dto.getHeight_at_which_needs_to_be_mounted_Mtrs() == null ? "Height_at_which_needs_to_be_mounted_Mtrs," : "";
			if(!tmaTmbData.equalsIgnoreCase(""))
				break;
		}
		if(!tmaTmbData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("TMA_TMB : "+tmaTmbData+" key is missing");
			return response;
		}
		com.tvi.upgrade.dto.BtsDto BTS = srDto.getBTS();
		if(BTS == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BTS json not found");
			return response;
		}
		String btsData = "";
		btsData += BTS.getConfig_type_1() == null ? "config_type_1," : "";
		btsData += BTS.getConfig_type_2() == null ? "config_type_2," : "";
		btsData += BTS.getConfig_type_3() == null ? "config_type_3," : "";
		btsData += BTS.getConfig_Carriers() == null ? "Config_Carriers," : "";
		List<com.tvi.upgrade.dto.BtsCabinetDto> BTS_Cabinet = BTS.getBTS_Cabinet();
		if(BTS_Cabinet == null){
			btsData += "BTS_Cabinet,";
		}
		/*else if(BTS_Cabinet.size() == 0){
			btsData = "BTS_Cabinet";
		}*/
		if(!btsData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BTS - "+btsData+" key is missing");
			return response;
		}
		String btsCabinetData = "";
		for(com.tvi.upgrade.dto.BtsCabinetDto btsCabi : BTS_Cabinet){
			btsCabinetData += btsCabi.getAction() == null ? "Action," : "";
			btsCabinetData += btsCabi.getNetWork_Type() == null ? "NetWork_Type," : "";
			btsCabinetData += btsCabi.getBTS_Type() == null ? "BTS_Type," : "";
			btsCabinetData += btsCabi.getBand() == null ? "Band," : "";
			btsCabinetData += btsCabi.getManufacturer() == null ? "Manufacturer," : "";
			btsCabinetData += btsCabi.getMake_of_BTS() == null ? "Make_of_BTS," : "";
			btsCabinetData += btsCabi.getLength_Mtrs() == null ? "Length_Mtrs," : "";
			btsCabinetData += btsCabi.getWidth_Mtrs() == null ? "Width_Mtrs," : "";
			btsCabinetData += btsCabi.getHeight_Mtrs() == null ? "Height_Mtrs," : "";
			btsCabinetData += btsCabi.getBTS_Power_Rating_KW() == null ? "BTS_Power_Rating_KW," : "";
			btsCabinetData += btsCabi.getBTS_Location() == null ? "BTS_Location," : "";
			btsCabinetData += btsCabi.getBTS_Voltage() == null ? "BTS_Voltage," : "";
			btsCabinetData += btsCabi.getMain_Unit_incase_of_TT_Split_Version() == null ? "Main_Unit_incase_of_TT_Split_Version," : "";
			btsCabinetData += btsCabi.getSpace_Occupied_in_Us_incase_of_TT_Split_Version() == null ? "Space_Occupied_in_Us_incase_of_TT_Split_Version," : "";
			btsCabinetData += btsCabi.getRRU_Unit() == null ? "RRU_Unit," : "";
			btsCabinetData += btsCabi.getNo_Of_RRU_Units_incase_of_TT_Split_Version() == null ? "No_Of_RRU_Units_incase_of_TT_Split_Version," : "";
			btsCabinetData += btsCabi.getCombined_wt_Of_RRU_Unit_incase_of_TT_Split_Version() == null ? "Combined_wt_Of_RRU_Unit_incase_of_TT_Split_Version," : "";
			btsCabinetData += btsCabi.getAGL_of_RRU_unit_in_M() == null ? "AGL_of_RRU_unit_in_M," : "";
			btsCabinetData += btsCabi.getWeight_Of_BTS_including_TMA_TMB_Kg() == null ? "Weight_Of_BTS_including_TMA_TMB_Kg," : "";
			btsCabinetData += btsCabi.getBillable_Weigtht() == null ? "Billable_Weigtht," : "";
			if(!btsCabinetData.equalsIgnoreCase(""))
				break;
		}
		if(!btsCabinetData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("BTS : BTS_Cabinet : "+btsCabinetData+" key is missing");
			return response;
		}
		com.tvi.upgrade.dto.AdditionalInformationDto Additional_Information = srDto.getAdditional_Information();
		if(Additional_Information == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Additional_Information json not found");
			return response;
		}
		String addInfoData = "";
		addInfoData += Additional_Information.getOther_Additional_Info1() == null ? "Other_Additional_Info1" : "";
		addInfoData += Additional_Information.getOther_Additional_Info2() == null ? "Other_Additional_Info2" : "";
		if(!addInfoData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Additional_Information : "+addInfoData+" key is missing");
			return response;
		}
		com.tvi.upgrade.dto.McbDto MCB = srDto.getMCB();
		if(MCB == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MCB json not found");
			return response;
		}
		String mcbData = "";
		mcbData += MCB.getTotal_No_of_MCB_Required() == null ? "Total_No_of_MCB_Required," : "";
		mcbData += MCB.get_06A() == null ? "_06A," : "";
		mcbData += MCB.get_10A() == null ? "_10A," : "";
		mcbData += MCB.get_16A() == null ? "_16A," : "";
		mcbData += MCB.get_24A() == null ? "_24A," : "";
		mcbData += MCB.get_32A() == null ? "_32A," : "";
		mcbData += MCB.get_40A() == null ? "_40A," : "";
		mcbData += MCB.get_63A() == null ? "_63A," : "";
		mcbData += MCB.get_80A() == null ? "_80A" : "";
		if(!mcbData.equalsIgnoreCase("")){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("MCB : "+mcbData+" key is missing");
			return response;
		}
		/*com.tvi.upgrade.dto.OtherEquipmentDto Other_Equipment = srDto.getOther_Equipment();
		if(Other_Equipment == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Equipment json not found");
			return response;
		}
		else if(Other_Equipment.getOther_Equipment() == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Equipment : Other_Equipment key is missing");
			return response;
		}*/
		List<com.tvi.upgrade.dto.OtherEquipmentDto> Other_Equipment = srDto.getOther_Equipment();
		if(Other_Equipment == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("Other_Equipment json not found");
			return response;
		}
		com.tvi.upgrade.dto.SacfaDto SACFA = srDto.getSACFA();
		if(SACFA == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("SACFA json not found");
			return response;
		}
		else if(SACFA.getSACFA_Number() == null){
			response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
			response.setResponseDesc("SACFA : SACFA_Number key is missing");
			return response;
		}
		return intDeo.raiseUpgradeSr(srDto);
	}
	
	@Override
	public SrSoWindrawalResponse srSoWithdrawal(SrSoWithdrawalDto jsonData) {
		return intDeo.srSoWithdrawal(jsonData);
	}
	
	@Override
	public RfaiAcceptRejectResponse rfaiAcceptReject(RfaiAcceptReject jsonData) {
		return intDeo.rfaiAcceptReject(jsonData);
	}
	
	@Override
	public RejectSpResponse rejectSp(RejectSpDto jsonData) {
		return intDeo.rejectSp(jsonData);
	}
	
	@Override
	public SoSubmitResponse soSubmit(SoSubmitDto jsonData) {
		return intDeo.soSubmit(jsonData);
	}
	
	@Override
	public Response<SaveNBSRequest> getAirtelDetails(AirtelCommonRequest jsonData) {
		return intDeo.getAirtelDetails(jsonData);
	}
	
	@Override
	public Response<AirtelCommonResponse> viewAirtelSrDetails(AirtelCommonRequest jsonData) {
		return intDeo.viewAirtelSrDetails(jsonData);
	}
	
	@Override
	public Response<String> changeAirtelSrStatus(ChangeAirtelSrStatusRequest jsonData) {
		return intDeo.changeAirtelSrStatus(jsonData);
	}
	
	@Override
	public List<SpReceivedResponse> spLapsed() {
		return intDeo.spLapsed();
	}

	@Override
	public Response<Map<String, String>> changeAirtelBulkSrStatus(BulkDTO jsonData) {
		return intDeo.changeAirtelBulkSrStatus(jsonData);
	}

	@Override
	public Response<ExportDataResponse> exportData(ExportDto jsonData) {
		return intDeo.exportData(jsonData);
	}
	
	@Override
	public SpReceivedResponse spReceived_test(SpReceivedTest jsonData) {
		return intDeo.spReceived_test(jsonData);
	}
	
	@Override
	public SpReceivedResponse spReceivedSharing_test(SpReceivedTest jsonData) {
		return intDeo.spReceivedSharing_test(jsonData);
	}

	@Override
	public SpReceivedResponse spReceivedUpgrade_test(SpReceivedTest jsonData) {
		return intDeo.spReceivedUpgrade_test(jsonData);
	}
}
