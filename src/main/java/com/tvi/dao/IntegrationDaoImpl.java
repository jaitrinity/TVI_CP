package com.tvi.dao;

import java.io.File;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialClob;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.tvi.common.persistence.entity.dao.TviCommonDao;
import com.tvi.constant.CommonFunction;
import com.tvi.constant.Constant;
import com.tvi.constant.Response;
import com.tvi.constant.RestAPI;
import com.tvi.constant.ReturnsCode;
import com.tvi.constant.SendMail;
import com.tvi.constant.SendSMS;
import com.tvi.dto.AirtelAuditDto;
import com.tvi.dto.AttachmentDto;
import com.tvi.dto.BandFrequencyMHzDto;
import com.tvi.dto.BscRncCabinetsDto;
import com.tvi.dto.BtsCabinetDto;
import com.tvi.dto.BulkDTO;
import com.tvi.dto.BulkSrDTO;
import com.tvi.dto.DgDto;
import com.tvi.dto.ExportDto;
import com.tvi.dto.FibreNodeDto;
import com.tvi.dto.GlobalDto;
import com.tvi.dto.McbDto;
import com.tvi.dto.MwAntennaDto;
import com.tvi.dto.OtherEquipmentDto;
import com.tvi.dto.OtherNodeDto;
import com.tvi.dto.RadioAntennaDto;
import com.tvi.dto.RejectSpDto;
import com.tvi.dto.RfaiAcceptReject;
import com.tvi.dto.RfaiReceivedDto;
import com.tvi.dto.SiteDetailDto;
import com.tvi.dto.SiteInfoDto;
import com.tvi.dto.SoSubmitDto;
import com.tvi.dto.SpDetailsDto;
import com.tvi.dto.SpReceivedDto;
import com.tvi.dto.SrRequestJsonDto;
import com.tvi.dto.SrSoReject;
import com.tvi.dto.SrSoWithdrawalDto;
import com.tvi.dto.StrageticDto;
import com.tvi.dto.SystemParamDTO;
import com.tvi.dto.ValidBscRncDto;
import com.tvi.dto.ValidBtsDto;
import com.tvi.dto.ValidFibreNodeDto;
import com.tvi.dto.ValidMcbDto;
import com.tvi.dto.ValidMwAntennaDto;
import com.tvi.dto.ValidOtherEquipmentDto;
import com.tvi.dto.ValidOtherNodeDto;
import com.tvi.dto.ValidRadioAntennaDto;
import com.tvi.dto.ValidTmaTmbDto;
import com.tvi.entity.AirtelResponseTableModel;
import com.tvi.entity.AirtelSrAuditEntity;
import com.tvi.request.AirtelCommonRequest;
import com.tvi.request.ChangeAirtelSrStatusRequest;
import com.tvi.request.SaveNBSRequest;
import com.tvi.request.SpReceivedTest;
import com.tvi.response.AirtelApiResponse;
import com.tvi.response.AirtelCommonResponse;
import com.tvi.response.ExportDataResponse;
import com.tvi.response.RejectSpResponse;
import com.tvi.response.RfaiAcceptRejectResponse;
import com.tvi.response.RfaiReceivedResponse;
import com.tvi.response.SoSubmitResponse;
import com.tvi.response.SpReceivedResponse;
import com.tvi.response.SrResponse;
import com.tvi.response.SrSoWindrawalResponse;
import com.tvi.sharing.dto.SharingSrRequestJsonDto;
import com.tvi.sharing.dto.SpReceivedSharingDto;
import com.tvi.upgrade.dto.SpReceivedUpgradeDto;
import com.tvi.upgrade.dto.TmaTmbDto;
import com.tvi.upgrade.dto.UpgradeSrRequestJsonDto;

public class IntegrationDaoImpl implements IntegrationDao{
	@Autowired
	TviCommonDao tviCommonDao;
	
	ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource("log4j.xml").getFile());
	final static Logger logger = Logger.getLogger(IntegrationDaoImpl.class);
	
	@Transactional
	@Override
	public void saveResponseInTable(Clob requestJson, Clob responseJson, String methodName) {
		try {
			AirtelResponseTableModel res = new AirtelResponseTableModel();
			res.setRequestJson(requestJson);
			res.setResponseJson(responseJson);
			res.setMethodName(methodName);
			res.setCreatedDate(new Date());
			tviCommonDao.saveOrUpdateEntityData(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response<SystemParamDTO> getNoOfList() {
		Response<SystemParamDTO> response = new Response<SystemParamDTO>();
		List<SystemParamDTO> list = new ArrayList<SystemParamDTO>();
		SystemParamDTO data = null;
		try {
			String sql = "select Type,Number from No_Of_List where `Type` = 'allCircleList' and Is_Active = 'Y' ";
			List<Object[]> resultList = tviCommonDao.getNoOfList(sql);
			for(Object obj [] : resultList){
				data = new SystemParamDTO();
				data.setParamCode(emptyString(obj[0]));
				data.setParamDesc(emptyString(obj[1]));
				list.add(data);
			}
			response.setWrappedList(list);
			response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
			response.setResponseDesc(ReturnsCode.SUCCESSFUL);
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}

	@Transactional
	@Override
	public Response<SrResponse> raiseSr(SrRequestJsonDto srDto) {
		Response<SrResponse> response = new Response<SrResponse>();
		Map<String, String> errorsMap = new HashMap<String, String>();
		List<SrResponse> srList = new ArrayList<SrResponse>();
		SrResponse sr = null;
		try {
			SrRequestJsonDto srRequestJson = srDto;
			
			// checking Ref_Number_TVIPL is available in `Airtel_SR` table...
			// ========= Start ========
			String refNumberTvipl = srDto.getRef_Number_TVIPL();
			String sql = "SELECT `SR_Number`, `UniqueRequestId`, `SR_DATE` FROM `Airtel_SR` where `TAB_NAME` in ('CreateNBS','HPSC') and `Ref_Number_TVIPL` = '"+refNumberTvipl+"'"
					+ " and `STATUS` not in ('NB97','NB98','NB99','NB100','NB101','NB102','NB104','NB105','NB106','NB107','NB108','NB110')";
			List<Object[]> alreadySrDataList = tviCommonDao.getAllTableData(sql);
			boolean isExist = alreadySrDataList.size() != 0 ? true : false;
			if(isExist){
				Object[] staObj = alreadySrDataList.get(0);
				response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
				response.setResponseDesc("SR already exist on Ref_Number_TVIPL("+refNumberTvipl+")");
				response.setSrNumber(emptyString(staObj[0]));
				response.setUniqueRequestId(emptyString(staObj[1]));
				response.setSrDate(emptyString(staObj[2]));
				return response;
			}
			// ========= End ========
			
			SiteDetailDto siteDetail = srRequestJson.getSite_Detail();
			// checking Customer_Site_Id is available in `Airtel_SR` table...
			// ========= Start ========
			String customerSiteId = siteDetail.getCustomer_Site_Id().trim();
			if(customerSiteId == null || customerSiteId.equalsIgnoreCase("")){
				response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
				response.setResponseDesc("Customer_Site_Id can't be blank");
				return response;
			}
			sql = "SELECT `SR_Number`, `UniqueRequestId`, `SR_DATE` FROM `Airtel_SR` where `TAB_NAME` in ('CreateNBS','HPSC') and `Customer_Site_Id` like '%"+customerSiteId+"%'"
					+ " and `STATUS` not in ('NB97','NB98','NB99','NB100','NB101','NB102','NB104','NB105','NB106','NB107','NB108','NB110')";
			alreadySrDataList = tviCommonDao.getAllTableData(sql);
			isExist = alreadySrDataList.size() != 0 ? true : false;
			if(isExist){
				Object[] staObj = alreadySrDataList.get(0);
				response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
				response.setResponseDesc("SR already exist on Customer_Site_Id("+customerSiteId+")");
				response.setSrNumber(emptyString(staObj[0]));
				response.setUniqueRequestId(emptyString(staObj[1]));
				response.setSrDate(emptyString(staObj[2]));
				return response;
			}
			// ========= End ========
			
			Date d = new Date();
			String tabName = Constant.CreateNBS;
			String srNumber = "NB_SR"+d.getTime();
			if(siteDetail.getSite_Type().equalsIgnoreCase(Constant.ULS) || 
					siteDetail.getSite_Type().equalsIgnoreCase(Constant.MICRO)){
				tabName = Constant.HPSC;
				srNumber = "HPSC_SR"+d.getTime();
			}
			sr = new SrResponse();
			sr.setSrNumber(srNumber);
			sr.setSrDate(CommonFunction.convertDateFormatAsGiven(d, "yyyy-MM-dd"));
			srList.add(sr);
			tviCommonDao.raiseSr(srNumber, tabName,srRequestJson);
			
			AirtelSrAuditEntity audEnt = new AirtelSrAuditEntity();
			audEnt.setSrNumber(srNumber);
			audEnt.setEmpId(siteDetail.getCircleCode());
			audEnt.setRamark("Raising New SR");
			audEnt.setAction("NB01");
			audEnt.setCreateDate(new Date());
			tviCommonDao.saveOrUpdateEntityData(audEnt);
			
			prepareForSendMail(srNumber, "NA", "NA", siteDetail.getCircleCode(), Constant.Airtel, "NB01", tabName, errorsMap);
			
			response.setWrappedList(srList);
			response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
			response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			response.setErrorsMap(errorsMap);
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}
	
	@Transactional
	@Override
	public Response<SrResponse> raiseSharingSr(SharingSrRequestJsonDto srDto) {
		Response<SrResponse> response = new Response<SrResponse>();
		Map<String, String> errorsMap = new HashMap<String, String>();
		List<SrResponse> srList = new ArrayList<SrResponse>();
		SrResponse sr = null;
		try {
			SharingSrRequestJsonDto srRequestJson = srDto;
			
			// checking Ref_Number_TVIPL is available in `Airtel_SR` table...
			// ========= Start ========
			String refNumberTvipl = srDto.getRef_Number_TVIPL();
			String sql = "SELECT `SR_Number`, `UniqueRequestId`, `SR_DATE` FROM `Airtel_SR` where `TAB_NAME` = 'New_Tenency' and `Ref_Number_TVIPL` = '"+refNumberTvipl+"'"
					+ " and `STATUS` not in ('NB97','NB98','NB99','NB100','NB101','NB102','NB104','NB105','NB106','NB107','NB108','NB110')";
			List<Object[]> alreadySrDataList = tviCommonDao.getAllTableData(sql);
			boolean isExist = alreadySrDataList.size() != 0 ? true : false;
			if(isExist){
				Object[] staObj = alreadySrDataList.get(0);
				response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
				response.setResponseDesc("SR already exist on Ref_Number_TVIPL("+refNumberTvipl+")");
				response.setSrNumber(emptyString(staObj[0]));
				response.setUniqueRequestId(emptyString(staObj[1]));
				response.setSrDate(emptyString(staObj[2]));
				return response;
			}
			// ========= End ========
			
			// checking `Customer_Site_Id` is available in `Airtel_SR` table...
			// ========= Start ========
			com.tvi.sharing.dto.SiteDetailDto siteDetail = srRequestJson.getSite_Detail();
			String customerSiteId = siteDetail.getCustomer_Site_Id().trim();
			if(customerSiteId == null || customerSiteId.equalsIgnoreCase("")){
				response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
				response.setResponseDesc("Customer_Site_Id can't be blank");
				return response;
			}
			sql = "SELECT `SR_Number`, `UniqueRequestId`, `SR_DATE` FROM `Airtel_SR` where `TAB_NAME` = 'New_Tenency' and `Customer_Site_Id` like '%"+customerSiteId+"%'"
					+ " and `STATUS` not in ('NB97','NB98','NB99','NB100','NB101','NB102','NB104','NB105','NB106','NB107','NB108','NB110')";
			alreadySrDataList = tviCommonDao.getAllTableData(sql);
			isExist = alreadySrDataList.size() != 0 ? true : false;
			if(isExist){
				Object[] staObj = alreadySrDataList.get(0);
				response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
				response.setResponseDesc("SR already exist on Customer_Site_Id("+customerSiteId+")");
				response.setSrNumber(emptyString(staObj[0]));
				response.setUniqueRequestId(emptyString(staObj[1]));
				response.setSrDate(emptyString(staObj[2]));
				return response;
			}
			// ========= End ========
			
			// checking TOCO_SITE_ID is available in Site_Master... 
			// ========= Start ========
			String tocoSiteId = siteDetail.getTOCO_Site_Id();
			if(tocoSiteId == null || tocoSiteId.equalsIgnoreCase("")){
				response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
				response.setResponseDesc("TOCO_Site_Id can't be blank");
				return response;
			}
			sql = "SELECT `Circle`, `Site Name`, `Latitude`, `Longitude`, `Address`, `TypeofSite`, `Airtel` FROM `Site_Master` "
					+ "where `TVISiteID` = '"+tocoSiteId+"' and (`Is_Deleted` is null or `Is_Deleted` = 'N')";
			List<Object[]> dataList = tviCommonDao.getAllTableData(sql);
			isExist = dataList.size() == 0 ? false : true;
			if(!isExist){
				response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
				response.setResponseDesc("TOCO Site Id("+tocoSiteId+") not found in Site Master");
				return response;
			}
			SiteInfoDto siteObj = null;
			String airtel = "";
			for(Object[] obj : dataList){
				siteObj = new SiteInfoDto();
				siteObj.setCircle(obj[0] == null ? "" : emptyString(obj[0]));
				siteObj.setSiteName(obj[1] == null ? "" : emptyString(obj[1]));
				siteObj.setLatitude(obj[2] == null ? "" : emptyString(obj[2]));
				siteObj.setLongitude(obj[3] == null ? "" : emptyString(obj[3]));
				siteObj.setSiteAddess(obj[4] == null ? "" : emptyString(obj[4]));
				siteObj.setTowerType(obj[5] == null ? "" : emptyString(obj[5]));
				airtel = obj[6] == null ? "N" : emptyString(obj[6]);
			}
			if(airtel.equalsIgnoreCase("Y")){
				response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
				response.setResponseDesc("Airtel already available in TOCO Site Id("+tocoSiteId+") ");
				return response;
			}
			// ========= End ========
			
			Date d = new Date();
			String srNumber = "NT_SR"+d.getTime();
			sr = new SrResponse();
			sr.setSrNumber(srNumber);
			sr.setSrDate(CommonFunction.convertDateFormatAsGiven(d, "yyyy-MM-dd"));
			srList.add(sr);
			tviCommonDao.raiseSharingSr(srNumber,siteObj,srRequestJson);
			
			AirtelSrAuditEntity audEnt = new AirtelSrAuditEntity();
			audEnt.setSrNumber(srNumber);
			audEnt.setEmpId(siteObj.getCircle());
			audEnt.setRamark("Raising Sharing SR");
			audEnt.setAction("NB01");
			audEnt.setCreateDate(new Date());
			tviCommonDao.saveOrUpdateEntityData(audEnt);
			
			prepareForSendMail(srNumber, "NA", "NA", siteObj.getCircle(), Constant.Airtel, "NB01", Constant.New_Tenency,errorsMap);
			
			response.setWrappedList(srList);
			response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
			response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			response.setErrorsMap(errorsMap);
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}
	
	@Transactional
	@Override
	public Response<SrResponse> raiseUpgradeSr(UpgradeSrRequestJsonDto srDto) {
		Response<SrResponse> response = new Response<SrResponse>();
		Map<String, String> errorsMap = new HashMap<String, String>();
		List<SrResponse> srList = new ArrayList<SrResponse>();
		SrResponse sr = null;
		try {
			UpgradeSrRequestJsonDto srRequestJson = srDto;
			
			// checking Ref_Number_TVIPL is available in `Airtel_SR` table...
			// ========= Start ========
			String refNumberTvipl = srDto.getRef_Number_TVIPL();
			String sql = "SELECT `SR_Number`, `UniqueRequestId`, `SR_DATE` FROM `Airtel_SR` where `TAB_NAME` = 'Site_Upgrade' and `Ref_Number_TVIPL` = '"+refNumberTvipl+"'"
					+ " and `STATUS` not in ('NB97','NB98','NB99','NB100','NB101','NB102','NB104','NB105','NB106','NB107','NB108','NB110')";
			List<Object[]> alreadySrDataList = tviCommonDao.getAllTableData(sql);
			boolean isExist = alreadySrDataList.size() != 0 ? true : false;
			if(isExist){
				Object[] staObj = alreadySrDataList.get(0);
				response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
				response.setResponseDesc("SR already exist on Ref_Number_TVIPL("+refNumberTvipl+")");
				response.setSrNumber(emptyString(staObj[0]));
				response.setUniqueRequestId(emptyString(staObj[1]));
				response.setSrDate(emptyString(staObj[2]));
				return response;
			}
			// ========= End ========
			
			// checking TOCO_SITE_ID is available in Site_Master...
			// ========= Start ========
			com.tvi.upgrade.dto.GlobalDto global = srRequestJson.getGlobal();
			String tocoSiteId = global.getTOCO_Site_ID();
			if(tocoSiteId == null || tocoSiteId.equalsIgnoreCase("")){
				response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
				response.setResponseDesc("TOCO_Site_Id can't be blank");
				return response;
			}
			sql = "SELECT `Circle`, `Site Name`, `Latitude`, `Longitude`, `Address`, `TypeofSite`, `Airtel` FROM `Site_Master` "
					+ "where `TVISiteID` = '"+tocoSiteId+"' and (`Is_Deleted` is null or `Is_Deleted` = 'N')";
			List<Object[]> dataList = tviCommonDao.getAllTableData(sql);
			isExist = dataList.size() == 0 ? false : true;
			if(!isExist){
				response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
				response.setResponseDesc("TOCO Site Id("+tocoSiteId+") not found in Site Master");
				return response;
			}
			SiteInfoDto siteObj = null;
			String airtel = "";
			for(Object[] obj : dataList){
				siteObj = new SiteInfoDto();
				siteObj.setCircle(obj[0] == null ? "" : emptyString(obj[0]));
				siteObj.setSiteName(obj[1] == null ? "" : emptyString(obj[1]));
				siteObj.setLatitude(obj[2] == null ? "" : emptyString(obj[2]));
				siteObj.setLongitude(obj[3] == null ? "" : emptyString(obj[3]));
				siteObj.setSiteAddess(obj[4] == null ? "" : emptyString(obj[4]));
				siteObj.setTowerType(obj[5] == null ? "" : emptyString(obj[5]));
				airtel = obj[6] == null ? "N" : emptyString(obj[6]);
			}
			if(airtel.equalsIgnoreCase("N")){
				response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
				response.setResponseDesc("Airtel not available in TOCO Site Id("+tocoSiteId+") ");
				return response;
			}
			// ========= End ========
			
			Date d = new Date();
			String srNumber = "SU_SR"+d.getTime();
			sr = new SrResponse();
			sr.setSrNumber(srNumber);
			sr.setSrDate(CommonFunction.convertDateFormatAsGiven(d, "yyyy-MM-dd"));
			srList.add(sr);
			tviCommonDao.raiseUpgradeSr(srNumber,siteObj,srRequestJson);
			
			AirtelSrAuditEntity audEnt = new AirtelSrAuditEntity();
			audEnt.setSrNumber(srNumber);
			audEnt.setEmpId(siteObj.getCircle());
			audEnt.setRamark("Raising Upgrade SR");
			audEnt.setAction("NB01");
			audEnt.setCreateDate(new Date());
			tviCommonDao.saveOrUpdateEntityData(audEnt);
			
			prepareForSendMail(srNumber, "NA", "NA", siteObj.getCircle(), Constant.Airtel, "NB01", Constant.Site_Upgrade,errorsMap);
			
			response.setWrappedList(srList);
			response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
			response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			response.setErrorsMap(errorsMap);
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}
	
	@Transactional
	@Override
	public SrSoWindrawalResponse srSoWithdrawal(SrSoWithdrawalDto jsonData) {
		SrSoWindrawalResponse response = new SrSoWindrawalResponse();
		try {
			String srNumber = jsonData.getSR_No();
			String sql = "SELECT `SR_Number`, `Withdrawal_Type` FROM `Airtel_SR` where `SR_Number` like '"+srNumber+"'";
			List<Object[]> dataList = tviCommonDao.getAllTableData(sql);
			String withType = "";
			boolean isAlreadyDone = false;
			for(Object [] dataObj : dataList){
				withType = dataObj[1] == null ? "" : emptyString(dataObj[1]);
				if(!withType.equalsIgnoreCase("")){
					isAlreadyDone = true;
				}
			}
			if(isAlreadyDone){
				response.setStatus("FAIL");
				response.setResponse_Message("Already "+withType);
			}
			else if(tviCommonDao.srSoWithdrawal(jsonData)){
				response.setStatus("SUCCESS");
				response.setResponse_Message(jsonData.getWithdrawal_Type()+" Successfully");
			}
			else{
				response.setStatus("FAIL");
				response.setResponse_Message(jsonData.getWithdrawal_Type()+" FAIL");
			}
			response.setRequest_Ref_No(jsonData.getSR_No());
			
			if(!isAlreadyDone){
				Map<String, String> errorsMap = new HashMap<String, String>();
				prepareForSendMail(jsonData.getSrNumber(), jsonData.getSpNumber(), jsonData.getSoNumber(), 
						jsonData.getCircle(), Constant.Airtel, jsonData.getStatus(), jsonData.getTabName(), errorsMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Transactional
	@Override
	public RfaiAcceptRejectResponse rfaiAcceptReject(RfaiAcceptReject jsonData) {
		RfaiAcceptRejectResponse response = new RfaiAcceptRejectResponse();
		try {
			String srNumber = jsonData.getSR_No();
			String sql = "SELECT `SR_Number`, `RFI_Accept_Reject` FROM `Airtel_SR` where `SR_Number` like '"+srNumber+"'";
			List<Object[]> dataList = tviCommonDao.getAllTableData(sql);
			String accRej = "";
			boolean isAlreadyDone = false;
			for(Object [] dataObj : dataList){
				accRej = dataObj[1] == null ? "" : emptyString(dataObj[1]);
				if(!accRej.equalsIgnoreCase("")){
					isAlreadyDone = true;
				}
			}
			if(isAlreadyDone){
				response.setStatus("FAIL");
				response.setRespose_Message("RFAI already "+accRej+" of "+srNumber);
			}
			else if(tviCommonDao.rfaiAcceptReject(jsonData)){
				response.setStatus("SUCCESS");
				if(jsonData.getAccept_Reject().equalsIgnoreCase("Accept"))
					response.setRespose_Message("RFAI Successfully Accepted");
				else
					response.setRespose_Message("RFAI Successfully Rejected");
			}
			else{
				response.setStatus("FAIL");
				response.setRespose_Message("FAIL to "+jsonData.getAccept_Reject()+" RFAI");
			}
			response.setSR_No(jsonData.getSR_No());
			
			if(!isAlreadyDone){
				Map<String, String> errorsMap = new HashMap<String, String>();
				prepareForSendMail(jsonData.getSrNumber(), jsonData.getSpNumber(), jsonData.getSoNumber(), 
						jsonData.getCircle(), Constant.Airtel, jsonData.getStatus(), jsonData.getTabName(), errorsMap);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Transactional
	@Override
	public RejectSpResponse rejectSp(RejectSpDto jsonData) {
		RejectSpResponse response = new RejectSpResponse();
		try {
			String srNumber = jsonData.getSR_No();
			String sql = "SELECT `SR_Number`, `Accept_Reject` FROM `Airtel_SR` where `SR_Number` = '"+srNumber+"'";
			List<Object[]> dataList = tviCommonDao.getAllTableData(sql);
			String accRej = "";
			boolean isAlreadyDone = false;
			for(Object [] dataObj : dataList){
				accRej = dataObj[1] == null ? "" : emptyString(dataObj[1]);
				if(!accRej.equalsIgnoreCase("")){
					isAlreadyDone = true;
				}
			}
			if(isAlreadyDone){
				response.setStatus("FAIL");
				response.setResponse_Message("SP already "+accRej+" of "+srNumber);
			}
			else if(tviCommonDao.rejectSp(jsonData)){
				response.setStatus("SUCCESS");
				if(jsonData.getAccept_Reject().equalsIgnoreCase("Accept"))
					response.setResponse_Message("SP Successfully Accepted");
				else
					response.setResponse_Message("SP Successfully Rejected");
			}
			else{
				response.setStatus("FAIL");
				response.setResponse_Message("FAIL to "+jsonData.getAccept_Reject()+" SP");
			}
			response.setSR_No(jsonData.getSR_No());
			
			if(!isAlreadyDone){
				Map<String, String> errorsMap = new HashMap<String, String>();
				prepareForSendMail(jsonData.getSrNumber(), jsonData.getSpNumber(), jsonData.getSoNumber(), 
						jsonData.getCircle(), Constant.Airtel, jsonData.getStatus(), jsonData.getTabName(), errorsMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Transactional
	@Override
	public SoSubmitResponse soSubmit(SoSubmitDto jsonData) {
		SoSubmitResponse response = new SoSubmitResponse();
		try {
			String spNumber = jsonData.getSP_Ref_No();
			String sql = "SELECT `SP_Number`, `SO_Accept_Reject` FROM `Airtel_SR` where `SP_Number` = '"+spNumber+"'";
			List<Object[]> dataList = tviCommonDao.getAllTableData(sql);
			String accRej = "";
			boolean isAlreadyDone = false;
			for(Object [] dataObj : dataList){
				accRej = dataObj[1] == null ? "" : emptyString(dataObj[1]);
				if(!accRej.equalsIgnoreCase("")){
					isAlreadyDone = true;
				}
			}
			if(isAlreadyDone){
				response.setStatus("FAIL");
				response.setResponse_Message("SO already "+accRej+" of "+spNumber);
			}
			else if(tviCommonDao.soSubmit(jsonData)){
				response.setStatus("SUCCESS");
				response.setResponse_Message("SO "+jsonData.getAccept_Reject());
			}
			else{
				response.setStatus("FAIL");
				response.setResponse_Message("SO "+jsonData.getAccept_Reject());
			}
			response.setAcceptReject(jsonData.getAccept_Reject());
			
			if(!isAlreadyDone){
				Map<String, String> errorsMap = new HashMap<String, String>();
				prepareForSendMail(jsonData.getSrNumber(), jsonData.getSpNumber(), jsonData.getSoNumber(), 
						jsonData.getCircle(), Constant.Airtel, jsonData.getStatus(), jsonData.getTabName(), errorsMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public Response<SaveNBSRequest> getAirtelDetails(AirtelCommonRequest jsonData) {
		Response<SaveNBSRequest> response = new Response<SaveNBSRequest>();
		List<SaveNBSRequest> list = new ArrayList<SaveNBSRequest>();
		String pagination = "";
		try {
			String filterSql = "";
			
			if(!jsonData.getFilterSrNumber().equalsIgnoreCase("")){
				filterSql = "and (sr.SR_Number like '"+jsonData.getFilterSrNumber()+"' "
						+ "or sr.SP_Number like '"+jsonData.getFilterSrNumber()+"'"
						+ "or sr.SO_Number like '"+jsonData.getFilterSrNumber()+"') ";
			}
			if(jsonData.getIsHoUser().equalsIgnoreCase("N")){
				String circleName = jsonData.getCircleName().replace(",","','");
				filterSql += " and sr.CircleCode in ('"+circleName+"') ";
			}
			if(!jsonData.getFilterCircleName().equalsIgnoreCase("")){
				filterSql += " and sr.CircleCode in ('"+jsonData.getFilterCircleName()+"') ";
			}
			if(!jsonData.getFilterTviSiteId().equalsIgnoreCase("")){
				filterSql += " and sr.TOCO_Site_Id like '"+jsonData.getFilterTviSiteId()+"' ";
			}
			if(!jsonData.getFilterSiteId().equalsIgnoreCase("")){
				filterSql += " and sr.Customer_Site_Id like '"+jsonData.getFilterSiteId()+"' ";
			}
			if(!jsonData.getFilterProductType().equalsIgnoreCase("")){
				filterSql += " and sr.TAB_NAME like '"+jsonData.getFilterProductType()+"' ";
			}
			if(!jsonData.getFilterStartDate().equalsIgnoreCase("")){
				filterSql += " and sr.SR_DATE >= '"+jsonData.getFilterStartDate()+"' ";
			}
			if(!jsonData.getFilterEndDate().equalsIgnoreCase("")){
				filterSql += " and sr.SR_DATE <= '"+jsonData.getFilterEndDate()+"' ";
			}
			
			String pendingSql = "";
			if(jsonData.isDashboard()){
				String penSql = "SELECT `TabName`, `CurrentStatus` FROM `StatusAction` "
						+ "WHERE `Role` = '"+jsonData.getLoginEmpRole()+"' and `IsActive` = 1";
				List<Object []> penSqlResult = tviCommonDao.getAllTableData(penSql);
				int penResSize = penSqlResult.size();
				if(penResSize > 0){
					pendingSql += "and (";
					int i=0;
					for(Object [] penObj : penSqlResult){
						String tabName = emptyString(penObj[0]);
						String currentStatus = emptyString(penObj[1]);
						currentStatus = currentStatus.replace(",", "','");
						pendingSql += " (sr.TAB_NAME = '"+tabName+"' and sr.STATUS in ('"+currentStatus+"')) ";
						if(i < penResSize-1){
							pendingSql += "OR";
						}
						i++;
					}
					pendingSql += ")";
				}	
			}
			String sql = "SELECT sr.SR_Number, sr.SP_Number, sr.SO_Number, sr.SR_DATE, sr.SP_DATE, sr.SO_DATE, sr.RFI_DATE, "
					+ "sr.RFI_ACCEPTED_DATE, sr.RFS_DATE, pd.P1_Site_Address, pd.P1_Tower_Type, pd.P1_Latitude, pd.P1_Longitude, "
					+ "pd.P1_LandLord_Contact_Detail, sr.TAB_NAME, sr.STATUS, s.AirtelStatus, sr.CircleCode, sr.Operator, "
					+ "sr.TOCO_Site_Id, sr.Customer_Site_Id, sr.Customer_Site_Name, sr.SP_Latitude, sr.SP_Longitude, "
					+ "tm.Org_Tab_Name, sr.Project_Name, sr.Upgrade_Type, sr.Ref_Number_TVIPL, sr.SiteId, s.DESCRIPTION "
					+ "FROM Airtel_SR sr "
					+ "left join Airtel_Priority_Details pd on sr.SR_Number = pd.SR_Number "
					+ "left join Airtel_Additional_Information ai on pd.SR_Number = ai.SR_Number "
					+ "left join NBS_STATUS s on sr.STATUS = s.STATUS and sr.TAB_NAME = s.TAB_NAME "
					+ "left join Tab_Master tm on sr.TAB_NAME = tm.Tab_Name "
					+ "where 1=1 "+pendingSql+" "+filterSql+" Order by sr.CREATE_DATE desc";
	
			List<Object []> tempResult = tviCommonDao.getAllTableData(sql);
			List<Object []> objList = new ArrayList<>();
			int maxRecord = tempResult.size();
			int pageNum = jsonData.getPageNum();
			int pageSize = jsonData.getRecordOnApage();
			int startIndex = (pageNum-1) * pageSize;
			int endIndex = (startIndex + pageSize);
			for(int i=startIndex;i<endIndex;i++){
				if(i < maxRecord){
					objList.add(tempResult.get(i));
				}
			}
			
			SaveNBSRequest snr = null;
			for(Object [] obj : objList){
				snr = new SaveNBSRequest();
				snr.setSrNumber(obj[0] == null ? "" : emptyString(obj[0]));
				snr.setSpNumber(obj[1] == null ? "NA" : emptyString(obj[1]));
				snr.setSoNumber(obj[2] == null ? "NA" : emptyString(obj[2]));
				snr.setSrRaiseDate(obj[3] == null ? "" : emptyString(obj[3]));
				snr.setSpRaiseDate(obj[4] == null ? "" : emptyString(obj[4]));
				snr.setSoRaiseDate(obj[5] == null ? "" : emptyString(obj[5]));
				snr.setRfiDate(obj[6] == null ? "" : emptyString(obj[6]));
				snr.setRfiAcceptedDate(obj[7] == null ? "" : emptyString(obj[7]));
				snr.setRfsDate(obj[8] == null ? "" : emptyString(obj[8]));
				snr.setSiteAddress(obj[9] == null ? "" : emptyString(obj[9]));
				snr.setSuggestedTowerType(obj[10] == null ? "" : emptyString(obj[10]));
				snr.setLatitude1(obj[11] == null ? 0.0 : Double.parseDouble(emptyNumeric(obj[11])));
				snr.setLongitude1(obj[12] == null ? 0.0 : Double.parseDouble(emptyNumeric(obj[12])));
				snr.setSuggestedLandOwnerRent(obj[13] == null ? "" : emptyString(obj[13]));
				snr.setTabName(emptyString(obj[14]));
				snr.setStatus(emptyString(obj[15]));
				snr.setSrStatus(emptyString(obj[16]));
				snr.setCircleName(emptyString(obj[17]));
				snr.setOperator(emptyString(obj[18]));
				snr.setTviSiteId(obj[19] == null ? "" : emptyString(obj[19]));
				snr.setAirtelSiteId(obj[20] == null ? "" : emptyString(obj[20]));
				snr.setSiteName(obj[21] == null ? "" : emptyString(obj[21]));
				snr.setSpLatitude(obj[22] == null ? 0.0 : Double.parseDouble(emptyNumeric(obj[22])));
				snr.setSpLongitude(obj[23] == null ? 0.0 : Double.parseDouble(emptyNumeric(obj[23])));
				snr.setOrgTabName(obj[24] == null ? "" : emptyString(obj[24]));
				snr.setProjectName(obj[25] == null ? "" : emptyString(obj[25]));
				snr.setUpgradeType(obj[26] == null ? "" : emptyString(obj[26]));
				snr.setRefNumberTvipl(obj[27] == null ? "" : emptyString(obj[27]));
				snr.setSiteId(obj[28] == null ? "" : emptyString(obj[28]));
				snr.setPendingTo(obj[29] == null ? "" : emptyString(obj[29]));
				list.add(snr);
			}
			pagination = createPagination(maxRecord,jsonData.getRecordOnApage());
			if(list.size() !=0){
				response.setWrappedList(list);
				response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
				response.setResponseDesc(ReturnsCode.SUCCESSFUL);
				response.setPagination(pagination);
			}
			
			else{
				response.setResponseCode(ReturnsCode.NO_RECORDS_FOUND_CODE);
				response.setResponseDesc(ReturnsCode.NO_RECORD_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}
	
	private String createPagination(int maxRecord, int recordOnApage) {
		String paggination = "";
		int division = maxRecord / recordOnApage;
		int reminder = maxRecord % recordOnApage;
		if(reminder !=0){
			division++;
		}
		for(int i=1;i<=division;i++){
			paggination += i;
			if(i != division) paggination+=",";
		}
		return paggination;
	}
	
	@Override
	public Response<AirtelCommonResponse> viewAirtelSrDetails(AirtelCommonRequest jsonData) {
		Response<AirtelCommonResponse> response = new Response<AirtelCommonResponse>();
		List<AirtelCommonResponse> list = new ArrayList<AirtelCommonResponse>();
		try {
			String sql = "SELECT `ButtonAfterStatus`, `Attachment`, `Role` FROM `StatusAction` where `TabName` = '"+jsonData.getTabName()+"' "
					+ "and `Role` = '"+jsonData.getLoginEmpRole()+"' "
					+ "and find_in_set('"+jsonData.getCurrentStatus()+"',`CurrentStatus`) <> 0 and `IsActive` = 1";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			AirtelCommonResponse airComm = new AirtelCommonResponse();
			for(Object [] dataObj : dataList){
				airComm.setButtonAfterStatus(dataObj[0] == null ? "" : emptyString(dataObj[0]));
				airComm.setAttachment(dataObj[1] == null ? "" : emptyString(dataObj[1]));
			}
			prepareAirSrBasicData(airComm,jsonData);
			prepareAirBtsCabinetData(airComm,jsonData);
			prepareAirRadioAntennaData(airComm,jsonData);
			prepareAirMwData(airComm,jsonData);
			prepareAirBscRncCabinetData(airComm,jsonData);
			prepareAirOtherNodeData(airComm,jsonData);
			prepareAirMcbData(airComm,jsonData);
			prepareAirFibreNodeData(airComm,jsonData);
			prepareAirAdditionalInfo(airComm,jsonData);
			prepareAirAttachmentData(airComm,jsonData);
			prepareAirAuditData(airComm,jsonData);
			prepareAirTmaTmbData(airComm,jsonData);
			prepareAirOtherEquipmentData(airComm,jsonData);
			list.add(airComm);
			
			if(list.size() !=0){
				response.setWrappedList(list);
				response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
				response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			}
			
			else{
				response.setResponseCode(ReturnsCode.NO_RECORDS_FOUND_CODE);
				response.setResponseDesc(ReturnsCode.NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}
	
	private void prepareAirOtherEquipmentData(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			String sql = "SELECT `Type_No`, `Feasibility`, `Action`, `Source_Request_RefNo`, "
					+ "`Other_Equipment_Category`, `Other_Equipment_Type`, `Equipment_to_be_relocated`, "
					+ "`Target_Indus_Site_Id`, `Target_Request_RefNo`, `CustomerPunchedOrPlanning`, "
					+ "`Deletion_OR_Relocation` FROM `Airtel_Other_Equipment` "
					+ "WHERE `SR_Number` = '"+jsonData.getSrNumber()+"' and `InsertType` in ('SR','SP')";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			List<OtherEquipmentDto> classList = new ArrayList<OtherEquipmentDto>();
			OtherEquipmentDto classObj = null;
			for(Object [] dataObj : dataList){
				classObj = new OtherEquipmentDto();
				classObj.setTypeNo(Integer.parseInt(emptyNumeric(dataObj[0])));
				classObj.setFeasibility(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				classObj.setAction(dataObj[2] == null ? "" : emptyString(dataObj[2]));
				classObj.setSource_Request_RefNo(dataObj[3] == null ? "" : emptyString(dataObj[3]));
				classObj.setOther_Equipment_Category(dataObj[4] == null ? "" : emptyString(dataObj[4]));
				classObj.setOther_Equipment_Type(dataObj[5] == null ? "" : emptyString(dataObj[5]));
				classObj.setEquipment_to_be_relocated(dataObj[6] == null ? "" : emptyString(dataObj[6]));
				classObj.setTarget_Indus_Site_Id(dataObj[7] == null ? "" : emptyString(dataObj[7]));
				classObj.setTarget_Request_RefNo(dataObj[8] == null ? "" : emptyString(dataObj[8]));
				classObj.setCustomerPunchedOrPlanning(dataObj[9] == null ? "" : emptyString(dataObj[9]));
				classObj.setDeletion_OR_Relocation(dataObj[10] == null ? "" : emptyString(dataObj[10]));
				classList.add(classObj);
			}
			airComm.setOther_Equipment(classList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void prepareAirTmaTmbData(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			String sql = "SELECT `Type_No`, `Feasibility`, `Action`, `Source`, `No_of_TMA_TMB`, "
					+ "`Weight_of_each_TMA_TMB`, `Combined_wt_of_TMA_TMB_Kgs`, "
					+ "`Height_at_which_needs_to_be_mounted_Mtrs`, `Customer_Punched_Or_Planning`, "
					+ "`Source_Request_Ref_No`, `Delete_Request_Ref_No` FROM `Airtel_TMA_TMB` "
					+ "WHERE `SR_Number` = '"+jsonData.getSrNumber()+"' and `InsertType` in ('SR','SP')";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			List<TmaTmbDto> classList = new ArrayList<TmaTmbDto>();
			TmaTmbDto classObj = null;
			for(Object [] dataObj : dataList){
				classObj = new TmaTmbDto();
				classObj.setTypeNo(Integer.parseInt(emptyNumeric(dataObj[0])));
				classObj.setFeasibility(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				classObj.setAction(dataObj[2] == null ? "" : emptyString(dataObj[2]));
				classObj.setSource(dataObj[3] == null ? "" : emptyString(dataObj[3]));
				classObj.setNo_of_TMA_TMB(dataObj[4] == null ? "" : emptyString(dataObj[4]));
				classObj.setWeight_of_each_TMA_TMB(dataObj[5] == null ? "" : emptyString(dataObj[5]));
				classObj.setCombined_wt_of_TMA_TMB_Kgs(dataObj[6] == null ? "" : emptyString(dataObj[6]));
				classObj.setHeight_at_which_needs_to_be_mounted_Mtrs(dataObj[7] == null ? "" : emptyString(dataObj[7]));
				classObj.setCustomer_Punched_Or_Planning(dataObj[8] == null ? "" : emptyString(dataObj[8]));
				classObj.setSource_Request_Ref_No(dataObj[9] == null ? "" : emptyString(dataObj[9]));
				classObj.setDelete_Request_Ref_No(dataObj[10] == null ? "" : emptyString(dataObj[10]));
				classList.add(classObj);
			}
			airComm.setTMA_TMB(classList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void prepareAirAuditData(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			List<AirtelAuditDto> auditList = new ArrayList<AirtelAuditDto>();
			String sql = "SELECT DISTINCT e.NAME, su.REMARK, su.CREATE_DATE FROM Airtel_SR_Audit su left join EMPLOYEE_MASTER e "
					+ "on su.EMP_ID = e.EMPLOYEE_ID where su.SR_NUMBER = '"+jsonData.getSrNumber()+"'";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			AirtelAuditDto obj = null;
			for(Object [] dataObj : dataList){
				obj = new AirtelAuditDto();
				obj.setEmpName(dataObj[0] == null ? "" : emptyString(dataObj[0]));
				obj.setRemark(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				obj.setAuditDate(dataObj[2] == null ? "" : emptyString(dataObj[2]));
				auditList.add(obj);
			}
			airComm.setAuditList(auditList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void prepareAirAttachmentData(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			List<AttachmentDto> attachedList = new ArrayList<AttachmentDto>();
			String sql = "SELECT "
					+ "(case when `AttachmentName` like 'Any%' then concat(`Role`,' Attachment') else `AttachmentName` end) as `AttachmentName`, `URL` "
					+ "FROM `Airtel_SR_Attachment` where `SR_Number` = '"+jsonData.getSrNumber()+"' and `IsActive` = 1";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			AttachmentDto obj = null;
			for(Object [] dataObj : dataList){
				obj = new AttachmentDto();
				obj.setAttName(dataObj[0] == null ? "" : emptyString(dataObj[0]));
				obj.setImageStr(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				attachedList.add(obj);
			}
			airComm.setAttachedList(attachedList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void prepareAirAdditionalInfo(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			String sql = "SELECT `Is_it_Strategic`, `Shelter_Size`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_AGL_Mtrs`, "
					+ "`DG_Redundancy`, `Extra_Battery_Bank_Requirement`, `Extra_Battery_BackUp`, "
					+ "`Anyother_Specific_Requirements`, `Additional_Info_If_any`, `Other_Additional_Info1`, "
					+ "`Other_Additional_Info2`, `TargetDate_DD_MM_YYYY`, `Is_it_Relocaiton_SR`, `Source_Req_Ref_No`, "
					+ "`Source_Indus_SiteId`, `Relocaiton_Reason` FROM `Airtel_Additional_Information` "
					+ "WHERE `SR_Number` = '"+jsonData.getSrNumber()+"'";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			for(Object [] dataObj : dataList){
				airComm.setIs_it_Strategic(dataObj[0] == null ? "" : emptyString(dataObj[0]));
				airComm.setShelter_Size(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				airComm.setLength_Mtrs(dataObj[2] == null ? "" : emptyString(dataObj[2]));
				airComm.setBreadth_Mtrs(dataObj[3] == null ? "" : emptyString(dataObj[3]));
				airComm.setHeight_AGL_Mtrs(dataObj[4] == null ? "" : emptyString(dataObj[4]));
				airComm.setDG_Redundancy(dataObj[5] == null ? "" : emptyString(dataObj[5]));
				airComm.setExtra_Battery_Bank_Requirement(dataObj[6] == null ? "" : emptyString(dataObj[6]));
				airComm.setExtra_Battery_BackUp(dataObj[7] == null ? "" : emptyString(dataObj[7]));
				airComm.setAnyother_Specific_Requirements(dataObj[8] == null ? "" : emptyString(dataObj[8]));
				airComm.setAdditional_Info_If_any(dataObj[9] == null ? "" : emptyString(dataObj[9]));
				airComm.setOther_Additional_Info1(dataObj[10] == null ? "" : emptyString(dataObj[10]));
				airComm.setOther_Additional_Info2(dataObj[11] == null ? "" : emptyString(dataObj[11]));
				airComm.setAdditional_Info_TargetDate_DD_MM_YYYY(dataObj[12] == null ? "" : emptyString(dataObj[12]));
				airComm.setIs_it_Relocaiton_SR(dataObj[13] == null ? "" : emptyString(dataObj[13]));
				airComm.setSource_Req_Ref_No(dataObj[14] == null ? "" : emptyString(dataObj[14]));
				airComm.setSource_Indus_SiteId(dataObj[15] == null ? "" : emptyString(dataObj[15]));
				airComm.setRelocaiton_Reason(dataObj[16] == null ? "" : emptyString(dataObj[16]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void prepareAirSrBasicData(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			String sql = "SELECT date_format(`Date_of_Proposal`,'%d-%m-%Y') as `Date_of_Proposal`, round(`Power_Rating`,3) as `Power_Rating`, `Site_Electrification_Distance`, `Tentative_EB_Availibility`, "
					+ "`Additional_Charge`, `Address1`, `Head_Load_Charge`, `Electrification_Cost`, "
					+ "`Electrification_Line_Distance`, `Electricity_Connection_HT_LT`, `Infra_Details`, "
					+ "`Site_Classification`, `Expected_Rent_to_Landlord`, `Non_Refundable_Deposit`, "
					+ "`Estimated_Deployment_Time__in_days_`, `Additional_Capex`, `Standard_Rates`, `Fiber_Charges`, "
					+ "`Rental_Threshold`, `Excess_Rent_beyond_Threshold`, `Tentative_Rental_Premium`, `Additional_Rent`, "
					+ "`IPFee`, `Field_Operation_Charges`, `Security_Guard_Charges`, `Mobilization_Charges`, "
					+ "`Erection_Charges`, `Battery_backup_Hrs`, `Land_Lord_Charges_or_Rent_Charges`, `Wind_Speed`, "
					+ "`TowerHeight`, `Agl`, `Distance_from_P1_Lat_Long_in_meter`, `Rejection_Remarks`, `Difficult`, "
					+ "`Tower_Completion`, `Shelter_Equipment_RoomReady`, `AirConditioner_Commissioned`, `DG_Commissioned`, "
					+ "`Acceptance_Testing_Of_Site_Infrastructure_Completed_Status`, `EB_Status`, `Created_By`, "
					+ "`OFC_Duct_Laying_Done`, `Access_To_Site_Available_Status`, `Engineer_Name`, `Engineer_Phone_Number`, "
					+ "`Notice_Form_Generation_Date`, `Tenure_In_Years`, `SO_Accept_Reject`, `RFI_Accept_Reject`, "
					+ "`RFI_Reject_Remarks`, `RFI_Reject_Remarks_Others`, round(`Total_Rated_Power_In_KW`,3) as `Total_Rated_Power_In_KW`, "
					+ "round(`Total_Rated_Power_In_Watt`,3) as `Total_Rated_Power_In_Watt`, `TOCO_Site_Id`, `Financial_Site_Id`, `Expected_monthly_Rent_Approved`, "
					+ "`CAPEX_Amount_Approved`, `OPEX_Amount_Approved`, `Tentative_Billing_Amount_Approved`, `Target_Date`, "
					+ "`TargetDate_DD_MM_YYYY`, `Date`, `Withdrawal_Type`, `Withdrawal_Reason`, `Rejection_Category`, "
					+ "`Rejection_Reason`, `Accept_Reject`, `Reject_SP_Remark`, `withdraw_remark`, `Sharing_Potential`, "
					+ "`SP_Latitude`, `SP_Longitude`, `Clutter`, `Activity_Type`, `Project_Name`, `RL_Type`, "
					+ "`UniqueRequestId`, `Remarks`, `Customer_Site_Id`, `Customer_Site_Name`, `State`, "
					+ "`Cell_Type`, `Site_Type`, `City`, `Search_Ring_Radious_Mtrs`, `Infill_NewTown`, "
					+ "`ShowCase_Non_Showcase`, `_3_11_Towns`, `Town`, `Request_for_Network_Type`, `Authority_Name`, "
					+ "`Preferred_Product_Type`,  `Recommended_Product_Type_by_Acquisition`, `Customer_Product_Type`, "
					+ "`No_of_TMA_TMB`, `Weight_of_each_TMA_TMB`, `Combined_wt_of_TMA_TMB_Kgs`, `Height_at_which_needs_to_be_mounted_Mtrs`, "
					+ "`Other_Equipment`, `Fiber_Required`, `No_of_Fiber_Pairs`, `Is_Fiber_Node_Provisioning_Required`, "
					+ "`No_of_Pairs`, `Distance_Length_of_Fiber_in_Meter`, `SACFA_Number`, `Is_Diesel_Generator_DG_required`, "
					+ "`Product_Name`, `Request_Ref_No`, `BSC`, `BTS_Cabinet`, `Extend_Tenure`, `Fiber_Node_Provisioning`, "
					+ "`Micro_to_Macro_conversion`, `MW_Antenna`, `Other_Nodes`, `Radio_Antenna`, `Strategic_Conversion`, "
					+ "`Tower_Mounted_Booster`, `MW_IDU`, `Pole`, `HubTag_Untag`, `Cluster`, `MSA_Type`, `Name_of_District_SSA`, "
					+ "`Reject_Remarks`, `Reject_Remarks_Others`, `Other_Equipment2`, `SuggesstedSiteAddress`, "
					+ "`SuggestedDistrict`, `SuggestedState`, `SuggestedPincode`, `SuggestedTownVillage`, "
					+ "`SuggestedCity`, `SuggestedLatitude`, `SuggestedLongitude`, `SuggestedDeviation`, "
					+ "`SuggestedTowerType`, `SuggestedBuildingHeight`, `SuggestedTowerHeight`, `SuggestedClutter`, "
					+ "`SuggestedLandOwnerRent`, `SuggestedElectrificationCharges`, `SuggestedMcCharges`, "
					+ "`Association_AreyouWorkingInAnyBhartiGroup`, `Association_IfyesmentiontheBhartiUnitName`, "
					+ "`Association_NameOftheEmployee`, `Association_EmployeeId`, "
					+ "`Relative_AnyRelativesareWorkingWithBhartiGroup`, `Relative_IfyesmentiontheBhartiUnitName`, "
					+ "`Relative_NameOftheEmployee`, `Relative_EmployeeId`, `Relative_LandlordRelationshipwithEmployee`, "
					+ "`Relative_MobileNumberOfrelativeWithAirtel`, `Declaration`  "
					+ "FROM `Airtel_SR` where `SR_Number` = '"+jsonData.getSrNumber()+"'";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			for(Object [] dataObj : dataList){
				airComm.setUniqueRequestId(dataObj[77] == null ? "" : emptyString(dataObj[77]));
				airComm.setRemarks(dataObj[78] == null ? "" : emptyString(dataObj[78]));
				airComm.setCustomer_Site_Id(dataObj[79] == null ? "" : emptyString(dataObj[79]));
				airComm.setCustomer_Site_Name(dataObj[80] == null ? "" : emptyString(dataObj[80]));
				airComm.setState(dataObj[81] == null ? "" : emptyString(dataObj[81]));
				airComm.setCell_Type(dataObj[82] == null ? "" : emptyString(dataObj[82]));
				airComm.setSite_Type(dataObj[83] == null ? "" : emptyString(dataObj[83]));
				airComm.setCity(dataObj[84] == null ? "" : emptyString(dataObj[84]));
				airComm.setSearch_Ring_Radious_Mtrs(dataObj[85] == null ? "" : emptyString(dataObj[85]));
				airComm.setInfill_NewTown(dataObj[86] == null ? "" : emptyString(dataObj[86]));
				airComm.setShowCase_Non_Showcase(dataObj[87] == null ? "" : emptyString(dataObj[87]));
				airComm.set_3_11_Towns(dataObj[88] == null ? "" : emptyString(dataObj[88]));
				airComm.setTown(dataObj[89] == null ? "" : emptyString(dataObj[89]));
				airComm.setRequest_for_Network_Type(dataObj[90] == null ? "" : emptyString(dataObj[90]));
				airComm.setAuthority_Name(dataObj[91] == null ? "" : emptyString(dataObj[91]));
				airComm.setPreferred_Product_Type(dataObj[92] == null ? "" : emptyString(dataObj[92]));
				airComm.setRecommended_Product_Type_by_Acquisition(dataObj[93] == null ? "" : emptyString(dataObj[93]));
				airComm.setCustomer_Product_Type(dataObj[94] == null ? "" : emptyString(dataObj[94]));
				airComm.setNo_of_TMA_TMB(dataObj[95] == null ? "" : emptyString(dataObj[95]));
				airComm.setWeight_of_each_TMA_TMB(dataObj[96] == null ? "" : emptyString(dataObj[96]));
				airComm.setCombined_wt_of_TMA_TMB_Kgs(dataObj[97] == null ? "" : emptyString(dataObj[97]));
				airComm.setHeight_at_which_needs_to_be_mounted_Mtrs(dataObj[98] == null ? "" : emptyString(dataObj[98]));
				//airComm.setOther_Equipment(dataObj[99] == null ? "" : emptyString(dataObj[99]));
				airComm.setFiber_Required(dataObj[100] == null ? "" : emptyString(dataObj[100]));
				airComm.setNo_of_Fiber_Pairs(dataObj[101] == null ? "" : emptyString(dataObj[101]));
				airComm.setIs_Fiber_Node_Provisioning_Required(dataObj[102] == null ? "" : emptyString(dataObj[102]));
				airComm.setNo_of_Pairs(dataObj[103] == null ? "" : emptyString(dataObj[103]));
				airComm.setDistance_Length_of_Fiber_in_Meter(dataObj[104] == null ? "" : emptyString(dataObj[104]));
				airComm.setSACFA_Number(dataObj[105] == null ? "" : emptyString(dataObj[105]));
				airComm.setIs_Diesel_Generator_DG_required(dataObj[106] == null ? "" : emptyString(dataObj[106]));
				airComm.setProduct_Name(dataObj[107] == null ? "" : emptyString(dataObj[107]));
				airComm.setRequest_Ref_No(dataObj[108] == null ? "" : emptyString(dataObj[108]));
				airComm.setBSC(dataObj[109] == null ? "" : emptyString(dataObj[109]));
				airComm.setBTS_Cabinett(dataObj[110] == null ? "" : emptyString(dataObj[110]));
				airComm.setExtend_Tenure(dataObj[111] == null ? "" : emptyString(dataObj[111]));
				airComm.setFiber_Node_Provisioning(dataObj[112] == null ? "" : emptyString(dataObj[112]));
				airComm.setMicro_to_Macro_conversion(dataObj[113] == null ? "" : emptyString(dataObj[113]));
				airComm.setMW_Antennaa(dataObj[114] == null ? "" : emptyString(dataObj[114]));
				airComm.setOther_Nodes(dataObj[115] == null ? "" : emptyString(dataObj[115]));
				airComm.setRadio_Antennaa(dataObj[116] == null ? "" : emptyString(dataObj[116]));
				airComm.setStrategic_Conversion(dataObj[117] == null ? "" : emptyString(dataObj[117]));
				airComm.setTower_Mounted_Booster(dataObj[118] == null ? "" : emptyString(dataObj[118]));
				airComm.setMW_IDUU(dataObj[119] == null ? "" : emptyString(dataObj[119]));
				airComm.setPole(dataObj[120] == null ? "" : emptyString(dataObj[120]));
				airComm.setHubTag_Untag(dataObj[121] == null ? "" : emptyString(dataObj[121]));
				airComm.setCluster(dataObj[122] == null ? "" : emptyString(dataObj[122]));
				airComm.setMSA_Type(dataObj[123] == null ? "" : emptyString(dataObj[123]));
				airComm.setName_of_District_SSA(dataObj[124] == null ? "" : emptyString(dataObj[124]));
				airComm.setReject_Remarks(dataObj[125] == null ? "" : emptyString(dataObj[125]));
				airComm.setReject_Remarks_Others(dataObj[126] == null ? "" : emptyString(dataObj[126]));
				airComm.setOther_Equipment2(dataObj[127] == null ? "" : emptyString(dataObj[127]));
				airComm.setSuggestedSiteAddress(dataObj[128] == null ? "" : emptyString(dataObj[128]));
				airComm.setSuggestedDistrict(dataObj[129] == null ? "" : emptyString(dataObj[129]));
				airComm.setSuggestedState(dataObj[130] == null ? "" : emptyString(dataObj[130]));
				airComm.setSuggestedPincode(dataObj[131] == null ? "" : emptyString(dataObj[131]));
				airComm.setSuggestedTownVillage(dataObj[132] == null ? "" : emptyString(dataObj[132]));
				airComm.setSuggestedCity(dataObj[133] == null ? "" : emptyString(dataObj[133]));
				airComm.setSuggestedLatitude(dataObj[134] == null ? "" : emptyString(dataObj[134]));
				airComm.setSuggestedLongitude(dataObj[135] == null ? "" : emptyString(dataObj[135]));
				airComm.setSuggestedDeviation(dataObj[136] == null ? "" : emptyString(dataObj[136]));
				airComm.setSuggestedTowerType(dataObj[137] == null ? "" : emptyString(dataObj[137]));
				airComm.setSuggestedBuildingHeight(dataObj[138] == null ? "" : emptyString(dataObj[138]));
				airComm.setSuggestedTowerHeight(dataObj[139] == null ? "" : emptyString(dataObj[139]));
				airComm.setSuggestedClutter(dataObj[140] == null ? "" : emptyString(dataObj[140]));
				airComm.setSuggestedLandOwnerRent(dataObj[141] == null ? "" : emptyString(dataObj[141]));
				airComm.setSuggestedElectrificationCharges(dataObj[142] == null ? "" : emptyString(dataObj[142]));
				airComm.setSuggestedMcCharges(dataObj[143] == null ? "" : emptyString(dataObj[143]));
				
				airComm.setAssociation_AreyouWorkingInAnyBhartiGroup(dataObj[144] == null ? "" : emptyString(dataObj[144]));
				airComm.setAssociation_IfyesmentiontheBhartiUnitName(dataObj[145] == null ? "" : emptyString(dataObj[145]));
				airComm.setAssociation_NameOftheEmployee(dataObj[146] == null ? "" : emptyString(dataObj[146]));
				airComm.setAssociation_EmployeeId(dataObj[147] == null ? "" : emptyString(dataObj[147]));
				airComm.setRelative_AnyRelativesareWorkingWithBhartiGroup(dataObj[148] == null ? "" : emptyString(dataObj[148]));
				airComm.setRelative_IfyesmentiontheBhartiUnitName(dataObj[149] == null ? "" : emptyString(dataObj[149]));
				airComm.setRelative_NameOftheEmployee(dataObj[150] == null ? "" : emptyString(dataObj[150]));
				airComm.setRelative_EmployeeId(dataObj[151] == null ? "" : emptyString(dataObj[151]));
				airComm.setRelative_LandlordRelationshipwithEmployee(dataObj[152] == null ? "" : emptyString(dataObj[152]));
				airComm.setRelative_MobileNumberOfrelativeWithAirtel(dataObj[153] == null ? "" : emptyString(dataObj[153]));
				airComm.setDeclaration(dataObj[154] == null ? "" : emptyString(dataObj[154]));
				
				airComm.setDate_of_Proposal(dataObj[0] == null ? "" : emptyString(dataObj[0]));
				airComm.setPower_Rating(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				airComm.setSite_Electrification_Distance(dataObj[2] == null ? "" : emptyString(dataObj[2]));
				airComm.setTentative_EB_Availibility(dataObj[3] == null ? "" : emptyString(dataObj[3]));
				airComm.setAdditional_Charge(dataObj[4] == null ? "" : emptyString(dataObj[4]));
				airComm.setAddress1(dataObj[5] == null ? "" : emptyString(dataObj[5]));
				airComm.setHead_Load_Charge(dataObj[6] == null ? "" : emptyString(dataObj[6]));
				airComm.setElectrification_Cost(dataObj[7] == null ? "" : emptyString(dataObj[7]));
				airComm.setElectrification_Line_Distance(dataObj[8] == null ? "" : emptyString(dataObj[8]));
				airComm.setElectricity_Connection_HT_LT(dataObj[9] == null ? "" : emptyString(dataObj[9]));
				airComm.setInfra_Details(dataObj[10] == null ? "" : emptyString(dataObj[10]));
				airComm.setSite_Classification(dataObj[11] == null ? "" : emptyString(dataObj[11]));
				airComm.setExpected_Rent_to_Landlord(dataObj[12] == null ? "" : emptyString(dataObj[12]));
				airComm.setNon_Refundable_Deposit(dataObj[13] == null ? "" : emptyString(dataObj[13]));
				airComm.setEstimated_Deployment_Time__in_days_(dataObj[14] == null ? "" : emptyString(dataObj[14]));
				airComm.setAdditional_Capex(dataObj[15] == null ? "" : emptyString(dataObj[15]));
				airComm.setStandard_Rates(dataObj[16] == null ? "" : emptyString(dataObj[16]));
				airComm.setFiber_Charges(dataObj[17] == null ? "" : emptyString(dataObj[17]));
				airComm.setRental_Threshold(dataObj[18] == null ? "" : emptyString(dataObj[18]));
				airComm.setExcess_Rent_beyond_Threshold(dataObj[19] == null ? "" : emptyString(dataObj[19]));
				airComm.setTentative_Rental_Premium(dataObj[20] == null ? "" : emptyString(dataObj[20]));
				airComm.setAdditional_Rent(dataObj[21] == null ? "" : emptyString(dataObj[21]));
				airComm.setIPFee(dataObj[22] == null ? "" : emptyString(dataObj[22]));
				airComm.setField_Operation_Charges(dataObj[23] == null ? "" : emptyString(dataObj[23]));
				airComm.setSecurity_Guard_Charges(dataObj[24] == null ? "" : emptyString(dataObj[24]));
				airComm.setMobilization_Charges(dataObj[25] == null ? "" : emptyString(dataObj[25]));
				airComm.setErection_Charges(dataObj[26] == null ? "" : emptyString(dataObj[26]));
				airComm.setBattery_backup_Hrs(dataObj[27] == null ? "" : emptyString(dataObj[27]));
				airComm.setLand_Lord_Charges_or_Rent_Charges(dataObj[28] == null ? "" : emptyString(dataObj[28]));
				airComm.setWind_Speed(dataObj[29] == null ? "" : emptyString(dataObj[29]));
				airComm.setTowerHeight(dataObj[30] == null ? "" : emptyString(dataObj[30]));
				airComm.setAgl(dataObj[31] == null ? "" : emptyString(dataObj[31]));
				airComm.setDistance_from_P1_Lat_Long_in_meter(dataObj[32] == null ? "" : emptyString(dataObj[32]));
				airComm.setRejection_Remarks(dataObj[33] == null ? "" : emptyString(dataObj[33]));
				airComm.setDifficult(dataObj[34] == null ? "" : emptyString(dataObj[34]));
				airComm.setTower_Completion(dataObj[35] == null ? "" : emptyString(dataObj[35]));
				airComm.setShelter_Equipment_RoomReady(dataObj[36] == null ? "" : emptyString(dataObj[36]));
				airComm.setAirConditioner_Commissioned(dataObj[37] == null ? "" : emptyString(dataObj[37]));
				airComm.setDG_Commissioned(dataObj[38] == null ? "" : emptyString(dataObj[38]));
				airComm.setAcceptance_Testing_Of_Site_Infrastructure_Completed_Status(dataObj[39] == null ? "" : emptyString(dataObj[39]));
				airComm.setEB_Status(dataObj[40] == null ? "" : emptyString(dataObj[40]));
				airComm.setCreated_By(dataObj[41] == null ? "" : emptyString(dataObj[41]));
				airComm.setOFC_Duct_Laying_Done(dataObj[42] == null ? "" : emptyString(dataObj[42]));
				airComm.setAccess_To_Site_Available_Status(dataObj[43] == null ? "" : emptyString(dataObj[43]));
				airComm.setEngineer_Name(dataObj[44] == null ? "" : emptyString(dataObj[44]));
				airComm.setEngineer_Phone_Number(dataObj[45] == null ? "" : emptyString(dataObj[45]));
				airComm.setNotice_Form_Generation_Date(dataObj[46] == null ? "" : emptyString(dataObj[46]));
				airComm.setTenure_In_Years(dataObj[47] == null ? "" : emptyString(dataObj[47]));
				airComm.setSO_Accept_Reject(dataObj[48] == null ? "" : emptyString(dataObj[48]));
				airComm.setRFI_Accept_Reject(dataObj[49] == null ? "" : emptyString(dataObj[49]));
				airComm.setRFI_Reject_Remarks(dataObj[50] == null ? "" : emptyString(dataObj[50]));
				airComm.setRFI_Reject_Remarks_Others(dataObj[51] == null ? "" : emptyString(dataObj[51]));
				airComm.setTotal_Rated_Power_In_KW(dataObj[52] == null ? "" : emptyString(dataObj[52]));
				airComm.setTotal_Rated_Power_In_Watt(dataObj[53] == null ? "" : emptyString(dataObj[53]));
				airComm.setTOCO_Site_Id(dataObj[54] == null ? "" : emptyString(dataObj[54]));
				airComm.setFinancial_Site_Id(dataObj[55] == null ? "" : emptyString(dataObj[55]));
				airComm.setExpected_monthly_Rent_Approved(dataObj[56] == null ? "" : emptyString(dataObj[56]));
				airComm.setCAPEX_Amount_Approved(dataObj[57] == null ? "" : emptyString(dataObj[57]));
				airComm.setOPEX_Amount_Approved(dataObj[58] == null ? "" : emptyString(dataObj[58]));
				airComm.setTentative_Billing_Amount_Approved(dataObj[59] == null ? "" : emptyString(dataObj[59]));
				airComm.setTarget_Date(dataObj[60] == null ? "" : emptyString(dataObj[60]));
				airComm.setTargetDate_DD_MM_YYYY(dataObj[61] == null ? "" : emptyString(dataObj[61]));
				airComm.setDate(dataObj[62] == null ? "" : emptyString(dataObj[62]));
				airComm.setWithdrawal_Type(dataObj[63] == null ? "" : emptyString(dataObj[63]));
				airComm.setWithdrawal_Reason(dataObj[64] == null ? "" : emptyString(dataObj[64]));
				airComm.setRejection_Category(dataObj[65] == null ? "" : emptyString(dataObj[65]));
				airComm.setRejection_Reason(dataObj[66] == null ? "" : emptyString(dataObj[66]));
				airComm.setAccept_Reject(dataObj[67] == null ? "" : emptyString(dataObj[67]));
				airComm.setReject_SP_Remark(dataObj[68] == null ? "" : emptyString(dataObj[68]));
				airComm.setWithdraw_remark(dataObj[69] == null ? "" : emptyString(dataObj[69]));
				airComm.setSharingPotential(dataObj[70] == null ? "" : emptyString(dataObj[70]));
				airComm.setSpLatitude(dataObj[71] == null ? "" : emptyString(dataObj[71]));
				airComm.setSpLongitude(dataObj[72] == null ? "" : emptyString(dataObj[72]));
				airComm.setClutter(dataObj[73] == null ? "" : emptyString(dataObj[73]));
				airComm.setActivityType(dataObj[74] == null ? "" : emptyString(dataObj[74]));
				airComm.setProjectName(dataObj[75] == null ? "" : emptyString(dataObj[75]));
				airComm.setRlType(dataObj[76] == null ? "" : emptyString(dataObj[76]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void prepareAirFibreNodeData(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			String sql = "SELECT `Type_No`, `Node_Type`, `Node_Location`, `Feasibility`, `Node_Manufacturer`, `Node_Model`, "
					+ "`Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, `Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, "
					+ "`FullRack`, `Tx_Rack_Space_required_in_Us`, `Is_Right_Of_Way_ROW_Required_Inside_The_Indus_Premises`, "
					+ "`Type_Of_Fiber_Laying`, `Type_Of_FMS`, `Remarks`, `Full_Rack`, `Action` FROM `Airtel_Fibre_Node` "
					+ "where `SR_Number` = '"+jsonData.getSrNumber()+"' and `InsertType` in ('SR','SP')";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			List<FibreNodeDto> classList = new ArrayList<FibreNodeDto>();
			FibreNodeDto classObj = null;
			for(Object [] dataObj : dataList){
				classObj = new FibreNodeDto();
				classObj.setTypeNo(Integer.parseInt(emptyNumeric(dataObj[0])));
				classObj.setNode_Type(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				classObj.setNode_Location(dataObj[2] == null ? "" : emptyString(dataObj[2]));
				classObj.setFeasibility(dataObj[3] == null ? "" : emptyString(dataObj[3]));
				classObj.setNode_Manufacturer(dataObj[4] == null ? "" : emptyString(dataObj[4]));
				classObj.setNode_Model(dataObj[5] == null ? "" : emptyString(dataObj[5]));
				classObj.setLength_Mtrs(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				classObj.setBreadth_Mtrs(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				classObj.setHeight_Mtrs(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				classObj.setWeight_Kg(dataObj[9] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[9])));
				classObj.setNode_Voltage(dataObj[10] == null ? "" : emptyString(dataObj[10]));
				classObj.setPower_Rating_in_Kw(dataObj[11] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[11])));
				classObj.setFullRack(dataObj[12] == null ? "" : emptyString(dataObj[12]));
				classObj.setTx_Rack_Space_required_in_Us(dataObj[13] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[13])));
				classObj.setIs_Right_Of_Way_ROW_Required_Inside_The_TOCO_Premises(dataObj[14] == null ? "" : emptyString(dataObj[14]));
				classObj.setType_Of_Fiber_Laying(dataObj[15] == null ? "" : emptyString(dataObj[15]));
				classObj.setType_Of_FMS(dataObj[16] == null ? "" : emptyString(dataObj[16]));
				classObj.setRemarks(dataObj[17] == null ? "" : emptyString(dataObj[17]));
				classObj.setFull_Rack(dataObj[18] == null ? "" : emptyString(dataObj[18]));
				classObj.setAction(dataObj[19] == null ? "" : emptyString(dataObj[19]));
				classList.add(classObj);
			}
			airComm.setFibre_Node(classList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void prepareAirMcbData(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			String sql = "SELECT `Total_No_of_MCB_Required`, `_06A`, `_10A`, `_16A`, `_24A`, `_32A`, `_40A`, `_63A`, "
					+ "`_80A`, `Feasibility` FROM `Airtel_MCB` "
					+ "where `SR_Number` = '"+jsonData.getSrNumber()+"' and `InsertType` in ('SR','SP')";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			List<McbDto> MCB = new ArrayList<McbDto>();
			McbDto classObj = null;
			for(Object [] dataObj : dataList){
				classObj = new McbDto();
				classObj.setTotal_No_of_MCB_Required(Double.parseDouble(emptyNumeric(dataObj[0])));
				classObj.set_06A(dataObj[1] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[1])));
				classObj.set_10A(dataObj[2] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[2])));
				classObj.set_16A(dataObj[3] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[3])));
				classObj.set_24A(dataObj[4] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[4])));
				classObj.set_32A(dataObj[5] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[5])));
				classObj.set_40A(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				classObj.set_63A(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				classObj.set_80A(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				classObj.setFeasibility(dataObj[9] == null ? "" : emptyString(dataObj[9]));
				MCB.add(classObj);
			}
			airComm.setMCB(MCB);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

	private void prepareAirOtherNodeData(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			String sql = "SELECT `Type_No`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, `Feasibility`, `Node_Model`, "
					+ "`Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, `Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, "
					+ "`FullRack`, `Tx_Rack_Space_Required_In_Us`, `Remarks`, `Action` FROM `Airtel_Other_Node` "
					+ "where `SR_Number` = '"+jsonData.getSrNumber()+"' and `InsertType` in ('SR','SP')";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			List<OtherNodeDto> classList = new ArrayList<OtherNodeDto>();
			List<OtherNodeDto> mwIduList = new ArrayList<OtherNodeDto>();
			OtherNodeDto classObj = null;
			for(Object [] dataObj : dataList){
				classObj = new OtherNodeDto();
				classObj.setTypeNo(Integer.parseInt(emptyNumeric(dataObj[0])));
				classObj.setNode_Type(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				classObj.setNode_Location(dataObj[2] == null ? "" : emptyString(dataObj[2]));
				classObj.setNode_Manufacturer(dataObj[3] == null ? "" : emptyString(dataObj[3]));
				classObj.setFeasibility(dataObj[4] == null ? "" : emptyString(dataObj[4]));
				classObj.setNode_Model(dataObj[5] == null ? "" : emptyString(dataObj[5]));
				classObj.setLength_Mtrs(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				classObj.setBreadth_Mtrs(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				classObj.setHeight_Mtrs(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				classObj.setWeight_Kg(dataObj[9] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[9])));
				classObj.setNode_Voltage(dataObj[10] == null ? "" : emptyString(dataObj[10]));
				classObj.setPower_Rating_in_Kw(dataObj[11] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[11])));
				classObj.setFullRack(dataObj[12] == null ? "" : emptyString(dataObj[12]));
				classObj.setTx_Rack_Space_Required_In_Us(dataObj[13] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[13])));
				classObj.setRemarks(emptyString(dataObj[14] == null ? "" : emptyString(dataObj[14])));
				classObj.setAction(emptyString(dataObj[15] == null ? "" : emptyString(dataObj[15])));
				String remark = dataObj[14] == null ? "" : emptyString(dataObj[14]);
				if(remark.equalsIgnoreCase("MW_Tx")){
					mwIduList.add(classObj);
				}
				else{
					classList.add(classObj);
				}
				
			}
			airComm.setOther_Node(classList);
			airComm.setMW_IDU(mwIduList);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

	private void prepareAirBscRncCabinetData(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			String sql = "SELECT `Type_No`, `NetWork_Type`, `BSC_RNC_Type`, `Feasibility`, `BSC_RNC_Manufacturer`, "
					+ "`BSC_RNC_Make`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_AGL`, `BSC_RNC_Power_Rating`, `Action` "
					+ "FROM `Airtel_BSC_RNC_Cabinets` "
					+ "where `SR_Number` = '"+jsonData.getSrNumber()+"' and `InsertType` in ('SR','SP')";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			List<BscRncCabinetsDto> classList = new ArrayList<BscRncCabinetsDto>();
			BscRncCabinetsDto classObj = null;
			for(Object [] dataObj : dataList){
				classObj = new BscRncCabinetsDto();
				classObj.setTypeNo(Integer.parseInt(emptyNumeric(dataObj[0])));
				classObj.setNetWork_Type(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				classObj.setBSC_RNC_Type(dataObj[2] == null ? "" : emptyString(dataObj[2]));
				classObj.setFeasibility(dataObj[3] == null ? "" : emptyString(dataObj[3]));
				classObj.setBSC_RNC_Manufacturer(dataObj[4] == null ? "" : emptyString(dataObj[4]));
				classObj.setBSC_RNC_Make(dataObj[5] == null ? "" : emptyString(dataObj[5]));
				classObj.setLength_Mtrs(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				classObj.setBreadth_Mtrs(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				classObj.setHeight_AGL(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				classObj.setBSC_RNC_Power_Rating(dataObj[9] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[9])));
				classObj.setAction(dataObj[10] == null ? "" : emptyString(dataObj[10]));
				classList.add(classObj);
			}
			airComm.setBSC_RNC_Cabinets(classList);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

	private void prepareAirMwData(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			String sql = "SELECT `Type_No`, `MWAntenna_i_WAN`, `Size_of_MW`, `Feasibility`, `Height_in_Mtrs`, "
					+ "`Azimuth_Degree`, `Action` FROM `Airtel_MW` "
					+ "where `SR_Number` = '"+jsonData.getSrNumber()+"' and `InsertType` in ('SR','SP')";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			List<MwAntennaDto> classList = new ArrayList<MwAntennaDto>();
			MwAntennaDto classObj = null;
			for(Object [] dataObj : dataList){
				classObj = new MwAntennaDto();
				classObj.setTypeNo(Integer.parseInt(emptyNumeric(dataObj[0])));
				classObj.setMWAntenna_i_WAN(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				classObj.setSize_of_MW(dataObj[2] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[2])));
				classObj.setFeasibility(dataObj[3] == null ? "" : emptyString(dataObj[3]));
				classObj.setHeight_in_Mtrs(dataObj[4] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[4])));
				classObj.setAzimuth_Degree(dataObj[5] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[5])));
				classObj.setAction(dataObj[6] == null ? "" : emptyString(dataObj[6]));
				classList.add(classObj);
			}
			airComm.setMW_Antenna(classList);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	private void prepareAirRadioAntennaData(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			String sql = "SELECT `Type_No`, `RadioAntenna_i_WAN`, `Height_AGL_m`, `Feasibility`, `Azimuth_Degree`, "
					+ "`Length_m`, `Width_m`, `Depth_m`, `No_of_Ports`, `RadioAntenna_Type`, "
					+ "`BandFrequencyMHz_FrequencyCombination`, `Action` FROM `Airtel_Radio_Antenna` "
					+ "where `SR_Number` = '"+jsonData.getSrNumber()+"' and `InsertType` in ('SR','SP')";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			List<RadioAntennaDto> classList = new ArrayList<RadioAntennaDto>();
			RadioAntennaDto classObj = null;
			for(Object [] dataObj : dataList){
				classObj = new RadioAntennaDto();
				classObj.setTypeNo(Integer.parseInt(emptyNumeric(dataObj[0])));
				classObj.setRadioAntenna_i_WAN(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				classObj.setHeight_AGL_m(dataObj[2] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[2])));
				classObj.setFeasibility(dataObj[3] == null ? "" : emptyString(dataObj[3]));
				classObj.setAzimuth_Degree(dataObj[4] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[4])));
				classObj.setLength_m(dataObj[5] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[5])));
				classObj.setWidth_m(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				classObj.setDepth_m(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				classObj.setNo_of_Ports(dataObj[8] == null ? "" : emptyString(dataObj[8]));
				classObj.setRadioAntenna_Type(dataObj[9] == null ? "" : emptyString(dataObj[9]));
				String bandFreq = dataObj[10] == null ? "" : emptyString(dataObj[10]);
				if(!bandFreq.equalsIgnoreCase("")){
					bandFreq = bandFreq.replace(",0.0", "");
				}
				classObj.setBandFrequencyMHz(bandFreq);
				classObj.setAction(dataObj[11] == null ? "" : emptyString(dataObj[11]));
				classList.add(classObj);
			}
			airComm.setRadio_Antenna(classList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void prepareAirBtsCabinetData(AirtelCommonResponse airComm, AirtelCommonRequest jsonData) {
		try {
			String sql = "SELECT `Type_No`, `NetWork_Type`, `BTS_Type`, `Feasibility`, `Band`, "
					+ "`Manufacturer`, `Make_of_BTS`, `Length_Mtrs`, `Width_Mtrs`, `Height_Mtrs`, `BTS_Power_Rating_KW`, "
					+ "`BTS_Location`, `BTS_Voltage`, `Main_Unit_incase_of_TT_Split_Version`, "
					+ "`Space_Occupied_in_Us_incase_of_TT_Split_Version`, `RRU_Unit`, "
					+ "`No_of_RRU_Units_incase_of_TT_Split_Version`, `Combined_wt_of_RRU_Unit_incase_of_TT_Split_Version`, "
					+ "`AGL_of_RRU_unit_in_M`, `Weight_of_BTS_including_TMA_TMB_Kg`, `Billable_Weigtht`, `Action` "
					+ "FROM `Airtel_BTS` where `SR_Number` = '"+jsonData.getSrNumber()+"' and `InsertType` in ('SR','SP')";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			List<BtsCabinetDto> classList = new ArrayList<BtsCabinetDto>();
			List<BtsCabinetDto> rruClassList = new ArrayList<BtsCabinetDto>();
			BtsCabinetDto classObj = null;
			for(Object [] dataObj : dataList){
				classObj = new BtsCabinetDto();
				classObj.setTypeNo(Integer.parseInt(emptyNumeric(dataObj[0])));
				classObj.setNetWork_Type(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				classObj.setBTS_Type(dataObj[2] == null ? "" : emptyString(dataObj[2]));
				classObj.setFeasibility(dataObj[3] == null ? "" : emptyString(dataObj[3]));
				classObj.setBand(dataObj[4] == null ? "" : emptyString(dataObj[4]));
				classObj.setManufacturer(dataObj[5] == null ? "" : emptyString(dataObj[5]));
				classObj.setMake_of_BTS(dataObj[6] == null ? "" : emptyString(dataObj[6]));
				classObj.setLength_Mtrs(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				classObj.setWidth_Mtrs(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				classObj.setHeight_Mtrs(dataObj[9] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[9])));
				classObj.setBTS_Power_Rating_KW(dataObj[10] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[10])));
				classObj.setBTS_Location(dataObj[11] == null ? "" : emptyString(dataObj[11]));
				classObj.setBTS_Voltage(dataObj[12] == null ? "" : emptyString(dataObj[12]));
				classObj.setMain_Unit_incase_of_TT_Split_Version(dataObj[13] == null ? "" : emptyString(dataObj[13]));
				classObj.setSpace_Occupied_in_Us_incase_of_TT_Split_Version(dataObj[14] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[14])));
				classObj.setRRU_Unit(dataObj[15] == null ? "" : emptyString(dataObj[15]));
				classObj.setNo_of_RRU_Units_incase_of_TT_Split_Version(dataObj[16] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[16])));
				classObj.setCombined_wt_of_RRU_Unit_incase_of_TT_Split_Version(dataObj[17] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[17])));
				classObj.setAGL_of_RRU_unit_in_M(dataObj[18] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[18])));
				classObj.setWeight_of_BTS_including_TMA_TMB_Kg(dataObj[19] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[19])));
				classObj.setBillable_Weigtht(dataObj[20] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[20])));
				classObj.setAction(dataObj[21] == null ? "" : emptyString(dataObj[21]));
				String btsType = dataObj[2] == null ? "" : emptyString(dataObj[2]);
				if(btsType.equalsIgnoreCase("Towertop")){
					rruClassList.add(classObj);
				}
				else{
					classList.add(classObj);
				}
				
			}
			airComm.setBTS_Cabinet(classList);
			airComm.setRRU_Cabinet(rruClassList);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Transactional
	@Override
	public Response<String> changeAirtelSrStatus(ChangeAirtelSrStatusRequest jsonData) {
		Response<String> response = new Response<>();
		Map<String, String> errorsMap = new HashMap<String, String>();
		try {
			String airtelApiResponse = "";
			String currentStatus = jsonData.getCurrentStatus();
			String afterStatus = jsonData.getAfterStatus();
			String srNumber = jsonData.getSrNumber();
			String sql = "UPDATE `Airtel_SR` set `STATUS` = '"+afterStatus+"' ";
			int i=0;
			if(jsonData.getTabName().equalsIgnoreCase(Constant.CreateNBS)){
				if(afterStatus.equalsIgnoreCase("NB03")){
					sql += ", `Sharing_Potential` = '"+jsonData.getSharingPotential()+"' ";
				}
				else if(afterStatus.equalsIgnoreCase("NB08")){
					/*Check TOCO/TVI site id site master.*/
					//------- Start ---------
					String tviSiteId = jsonData.getTOCO_Site_Id();
					String siteSql = "SELECT `TVISiteID` FROM `Site_Master` where `TVISiteID` = '"+tviSiteId+"' ";
					boolean isExist = tviCommonDao.isExistSR(siteSql);
					if(isExist){
						response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
						response.setResponseDesc(ReturnsCode.ALREADY_EXIST+" TOCO Site Id  = "+tviSiteId+" in Site Master.");
						return response;
					}
					// ------- End ---------
					
					String spNumber = srNumber.replace("SR", "SP");
					sql += ", `SP_Number` = '"+spNumber+"', `SP_DATE` = curdate(), "
							+ "`SP_Latitude` = '"+jsonData.getLatitude()+"', "
							+ "`SP_Longitude` = '"+jsonData.getLongitude()+"', `Clutter` = '"+jsonData.getClutter()+"', "
							+ "`TOCO_Site_Id` = '"+jsonData.getTOCO_Site_Id()+"', "
							+ "`Date_of_Proposal` = '"+jsonData.getDate_of_Proposal()+"', "
							+ "`Power_Rating` = '"+jsonData.getPower_Rating()+"', "
							+ "`Site_Electrification_Distance` = '"+jsonData.getSite_Electrification_Distance()+"', "
							+ "`Tentative_EB_Availibility` = '"+jsonData.getTentative_EB_Availibility()+"', "
							+ "`Address1` = '"+jsonData.getAddress1()+"', "
							+ "`Head_Load_Charge` = '"+jsonData.getHead_Load_Charge()+"', "
							+ "`Electrification_Cost` = '"+jsonData.getElectrification_Cost()+"', "
							+ "`Electrification_Line_Distance` = '"+jsonData.getElectrification_Line_Distance()+"', "
							+ "`Electricity_Connection_HT_LT` = '"+jsonData.getElectricity_Connection_HT_LT()+"', "
							+ "`Wind_Speed` = '"+jsonData.getWind_Speed()+"', "
							+ "`TowerHeight` = '"+jsonData.getTowerHeight()+"', "
							+ "`Agl` = '"+jsonData.getAgl()+"', "
							+ "`Distance_from_P1_Lat_Long_in_meter` = '"+jsonData.getDistance_from_P1_Lat_Long_in_meter()+"' ";
					insertBtsData(jsonData);
					insertRadioAntennaData(jsonData);
					insertMwAntennaData(jsonData);
					insertBscRncData(jsonData);
					insertOtherNodeData(jsonData);
					insertMcbData(jsonData);
					insertFibreNodeData(jsonData);
					insertOtherEquimentData(jsonData);
				}
				else if(afterStatus.equalsIgnoreCase("NB09")){
					sql += ", `Additional_Charge` = '"+jsonData.getAdditional_Charge()+"', "
						+ "`Infra_Details` = '"+jsonData.getInfra_Details()+"', "
						+ "`Site_Classification` = '"+jsonData.getSite_Classification()+"', "
						+ "`Expected_Rent_to_Landlord` = '"+jsonData.getExpected_Rent_to_Landlord()+"', "
						+ "`Non_Refundable_Deposit` = '"+jsonData.getNon_Refundable_Deposit()+"', "
						+ "`Estimated_Deployment_Time__in_days_` = '"+jsonData.getEstimated_Deployment_Time__in_days_()+"', "
						+ "`Additional_Capex` = '"+jsonData.getAdditional_Capex()+"', "
						+ "`Standard_Rates` = '"+jsonData.getStandard_Rates()+"', "
						+ "`Fiber_Charges` = '"+jsonData.getFiber_Charges()+"', "
						+ "`Rental_Threshold` = '"+jsonData.getRental_Threshold()+"', "
						+ "`Excess_Rent_beyond_Threshold` = '"+jsonData.getExcess_Rent_beyond_Threshold()+"', "
						+ "`Tentative_Rental_Premium` = '"+jsonData.getTentative_Rental_Premium()+"', "
						+ "`Additional_Rent` = '"+jsonData.getAdditional_Rent()+"', "
						+ "`IPFee` = '"+jsonData.getIPFee()+"', "
						+ "`Field_Operation_Charges` = '"+jsonData.getField_Operation_Charges()+"', "
						+ "`Security_Guard_Charges` = '"+jsonData.getSecurity_Guard_Charges()+"', "
						+ "`Mobilization_Charges` = '"+jsonData.getMobilization_Charges()+"', "
						+ "`Erection_Charges` = '"+jsonData.getErection_Charges()+"', "
						+ "`Battery_backup_Hrs` = '"+jsonData.getBattery_backup_Hrs()+"', "
						+ "`Land_Lord_Charges_or_Rent_Charges` = '"+jsonData.getLand_Lord_Charges_or_Rent_Charges()+"', "
						+ "`Recommended_Product_Type_by_Acquisition` = '"+jsonData.getRecommended_Product_Type_by_Acquisition()+"' ";
					
					sql += ", `Association_AreyouWorkingInAnyBhartiGroup` = '"+jsonData.getAssociation_AreyouWorkingInAnyBhartiGroup()+"', "
						+ "`Association_IfyesmentiontheBhartiUnitName` = '"+jsonData.getAssociation_IfyesmentiontheBhartiUnitName()+"', "
						+ "`Association_NameOftheEmployee` = '"+jsonData.getAssociation_NameOftheEmployee()+"', "
						+ "`Association_EmployeeId` = '"+jsonData.getAssociation_EmployeeId()+"', "
						+ "`Relative_AnyRelativesareWorkingWithBhartiGroup` = '"+jsonData.getRelative_AnyRelativesareWorkingWithBhartiGroup()+"', "
						+ "`Relative_IfyesmentiontheBhartiUnitName` = '"+jsonData.getRelative_IfyesmentiontheBhartiUnitName()+"', "
						+ "`Relative_NameOftheEmployee` = '"+jsonData.getRelative_NameOftheEmployee()+"', "
						+ "`Relative_EmployeeId` = '"+jsonData.getRelative_EmployeeId()+"', "
						+ "`Relative_LandlordRelationshipwithEmployee` = '"+jsonData.getRelative_LandlordRelationshipwithEmployee()+"', "
						+ "`Relative_MobileNumberOfrelativeWithAirtel` = '"+jsonData.getRelative_MobileNumberOfrelativeWithAirtel()+"', "
						+ "`Declaration` = '"+jsonData.getDeclaration()+"' ";
				}
				else if(afterStatus.equalsIgnoreCase("NB10")){
					String soNumber = srNumber.replace("SR", "SO");
					sql += ", `SO_Number` = '"+soNumber+"', `SO_DATE` = curdate() ";
				}
				else if(afterStatus.equalsIgnoreCase("NB15")){
					/*Check TOCO/TVI site id site master.*/
					//------- Start ---------
					String tviSiteId = jsonData.getViewTocoSiteId();
					String siteSql = "SELECT `TVISiteID` FROM `Site_Master` where `TVISiteID` = '"+tviSiteId+"' ";
					boolean isExist = tviCommonDao.isExistSR(siteSql);
					if(isExist){
						response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
						response.setResponseDesc(ReturnsCode.ALREADY_EXIST+" TOCO Site Id  = "+tviSiteId+" in Site Master.");
						return response;
					}
					// ------- End ---------
				}
				else if(afterStatus.equalsIgnoreCase("NB16")){
//					sql += ", `RFI_DATE` = '"+jsonData.getRfiDate()+"', "
					sql += ", `RFI_DATE` = curdate(), "
					+ "`Tower_Completion` = '"+jsonData.getTower_Completion()+"', "
					+ "`Shelter_Equipment_RoomReady` = '"+jsonData.getShelter_Equipment_RoomReady()+"', "
					+ "`AirConditioner_Commissioned` = '"+jsonData.getAirConditioner_Commissioned()+"', "
					+ "`DG_Commissioned` = '"+jsonData.getDG_Commissioned()+"', "
					+ "`Acceptance_Testing_Of_Site_Infrastructure_Completed_Status` = '"+jsonData.getAcceptance_Testing_Of_Site_Infrastructure_Completed_Status()+"', "
					+ "`EB_Status` = '"+jsonData.getEB_Status()+"', `Created_By` = '"+jsonData.getCreated_By()+"', "
					+ "`OFC_Duct_Laying_Done` = '"+jsonData.getOFC_Duct_Laying_Done()+"', "
					+ "`Access_To_Site_Available_Status` = '"+jsonData.getAccess_To_Site_Available_Status()+"', "
					+ "`Engineer_Name` = '"+jsonData.getEngineer_Name()+"', "
					+ "`Engineer_Phone_Number` = '"+jsonData.getEngineer_Phone_Number()+"', "
					+ "`Notice_Form_Generation_Date` = '"+jsonData.getNotice_Form_Generation_Date()+"' ";
				}
				else if(afterStatus.equalsIgnoreCase("NB19")){
					sql += ", `RFS_DATE` = '"+jsonData.getRfsDate()+"' ";
				}
				else if(afterStatus.equalsIgnoreCase("NB101")){
					SrSoReject srSo = new SrSoReject();
					srSo.setComments(jsonData.getRemark());
					srSo.setStatus("SR Rejected");
					srSo.setSR_Number(srNumber);
					SpReceivedResponse apiRes = srSoReject2(srSo, currentStatus);
					if(!apiRes.isStatus())
						airtelApiResponse = "updatesrdetailstatus :: "+apiRes.getMessage();
				}
				else if(afterStatus.equalsIgnoreCase("NB107")){
					SrSoReject srSo = new SrSoReject();
					srSo.setComments(jsonData.getRemark());
					srSo.setStatus("SO Rejected");
					srSo.setSR_Number(srNumber);
					SpReceivedResponse apiRes = srSoReject2(srSo, currentStatus);
					if(!apiRes.isStatus())
						airtelApiResponse = "updatesrdetailstatus :: "+apiRes.getMessage();
				}
				
				sql +=  "where `SR_Number` = '"+srNumber+"'";
				i = tviCommonDao.updateBulkdataValue(sql);
				if(i != 0){
					if(afterStatus.equalsIgnoreCase("NB09")){
						SpReceivedResponse apiRes = spReceived(srNumber, currentStatus);
						if(!apiRes.isStatus())
							airtelApiResponse = "sp_insert :: "+apiRes.getMessage();
					}
					else if(afterStatus.equalsIgnoreCase("NB17")){
						RfaiReceivedResponse apiRes = rfaiReceived(srNumber, currentStatus);
						if(!apiRes.isStatus())
							airtelApiResponse = "rfai_insert :: "+apiRes.getMessage();
					}
				}
			}
			else if(jsonData.getTabName().equalsIgnoreCase(Constant.New_Tenency)){
				if(afterStatus.equalsIgnoreCase("NB03")){
					String spNumber = srNumber.replace("SR", "SP");
					sql += ", `SP_Number` = '"+spNumber+"', `SP_DATE` = curdate(), "
							+ "`Date_of_Proposal` = '"+jsonData.getDate_of_Proposal()+"', "
							+ "`Power_Rating` = '"+jsonData.getPower_Rating()+"', "
							+ "`Site_Electrification_Distance` = '"+jsonData.getSite_Electrification_Distance()+"', "
							+ "`Tentative_EB_Availibility` = '"+jsonData.getTentative_EB_Availibility()+"', "
							+ "`Head_Load_Charge` = '"+jsonData.getHead_Load_Charge()+"', "
							+ "`Electrification_Cost` = '"+jsonData.getElectrification_Cost()+"', "
							+ "`Electrification_Line_Distance` = '"+jsonData.getElectrification_Line_Distance()+"', "
							+ "`Electricity_Connection_HT_LT` = '"+jsonData.getElectricity_Connection_HT_LT()+"', "
							+ "`Wind_Speed` = '"+jsonData.getWind_Speed()+"', "
							+ "`TowerHeight` = '"+jsonData.getTowerHeight()+"', "
							+ "`Agl` = '"+jsonData.getAgl()+"', "
							+ "`Distance_from_P1_Lat_Long_in_meter` = '"+jsonData.getDistance_from_P1_Lat_Long_in_meter()+"'";
					
					insertBtsData(jsonData);
					insertRadioAntennaData(jsonData);
					insertMwAntennaData(jsonData);
					insertBscRncData(jsonData);
					insertOtherNodeData(jsonData);
					insertMcbData(jsonData);
					insertFibreNodeData(jsonData);
					insertOtherEquimentData(jsonData);
				}
				else if(afterStatus.equalsIgnoreCase("NB04")){
					sql += ", `Additional_Charge` = '"+jsonData.getAdditional_Charge()+"', "
						+ "`Infra_Details` = '"+jsonData.getInfra_Details()+"', "
						+ "`Site_Classification` = '"+jsonData.getSite_Classification()+"', "
						+ "`Expected_Rent_to_Landlord` = '"+jsonData.getExpected_Rent_to_Landlord()+"', "
						+ "`Non_Refundable_Deposit` = '"+jsonData.getNon_Refundable_Deposit()+"', "
						+ "`Estimated_Deployment_Time__in_days_` = '"+jsonData.getEstimated_Deployment_Time__in_days_()+"', "
						+ "`Additional_Capex` = '"+jsonData.getAdditional_Capex()+"', "
						+ "`Standard_Rates` = '"+jsonData.getStandard_Rates()+"', "
						+ "`Fiber_Charges` = '"+jsonData.getFiber_Charges()+"', "
						+ "`Rental_Threshold` = '"+jsonData.getRental_Threshold()+"', "
						+ "`Excess_Rent_beyond_Threshold` = '"+jsonData.getExcess_Rent_beyond_Threshold()+"', "
						+ "`Tentative_Rental_Premium` = '"+jsonData.getTentative_Rental_Premium()+"', "
						+ "`Additional_Rent` = '"+jsonData.getAdditional_Rent()+"', "
						+ "`IPFee` = '"+jsonData.getIPFee()+"', "
						+ "`Field_Operation_Charges` = '"+jsonData.getField_Operation_Charges()+"', "
						+ "`Security_Guard_Charges` = '"+jsonData.getSecurity_Guard_Charges()+"', "
						+ "`Mobilization_Charges` = '"+jsonData.getMobilization_Charges()+"', "
						+ "`Erection_Charges` = '"+jsonData.getErection_Charges()+"', "
						+ "`Battery_backup_Hrs` = '"+jsonData.getBattery_backup_Hrs()+"', "
						+ "`Land_Lord_Charges_or_Rent_Charges` = '"+jsonData.getLand_Lord_Charges_or_Rent_Charges()+"', "
						+ "`Rejection_Remarks` = '"+jsonData.getRejection_Remarks()+"' ";
					
					sql += ", `Association_AreyouWorkingInAnyBhartiGroup` = '"+jsonData.getAssociation_AreyouWorkingInAnyBhartiGroup()+"', "
							+ "`Association_IfyesmentiontheBhartiUnitName` = '"+jsonData.getAssociation_IfyesmentiontheBhartiUnitName()+"', "
							+ "`Association_NameOftheEmployee` = '"+jsonData.getAssociation_NameOftheEmployee()+"', "
							+ "`Association_EmployeeId` = '"+jsonData.getAssociation_EmployeeId()+"', "
							+ "`Relative_AnyRelativesareWorkingWithBhartiGroup` = '"+jsonData.getRelative_AnyRelativesareWorkingWithBhartiGroup()+"', "
							+ "`Relative_IfyesmentiontheBhartiUnitName` = '"+jsonData.getRelative_IfyesmentiontheBhartiUnitName()+"', "
							+ "`Relative_NameOftheEmployee` = '"+jsonData.getRelative_NameOftheEmployee()+"', "
							+ "`Relative_EmployeeId` = '"+jsonData.getRelative_EmployeeId()+"', "
							+ "`Relative_LandlordRelationshipwithEmployee` = '"+jsonData.getRelative_LandlordRelationshipwithEmployee()+"', "
							+ "`Relative_MobileNumberOfrelativeWithAirtel` = '"+jsonData.getRelative_MobileNumberOfrelativeWithAirtel()+"', "
							+ "`Declaration` = '"+jsonData.getDeclaration()+"' ";
				}
				else if(afterStatus.equalsIgnoreCase("NB05")){
					String soNumber = srNumber.replace("SR", "SO");
					sql += ", `SO_Number` = '"+soNumber+"', `SO_DATE` = curdate() ";
				}
				
				else if(afterStatus.equalsIgnoreCase("NB07")){
//					sql += ", `RFI_DATE` = '"+jsonData.getRfiDate()+"', "
					sql += ", `RFI_DATE` = curdate(), "
					+ "`Tower_Completion` = '"+jsonData.getTower_Completion()+"', "
					+ "`Shelter_Equipment_RoomReady` = '"+jsonData.getShelter_Equipment_RoomReady()+"', "
					+ "`AirConditioner_Commissioned` = '"+jsonData.getAirConditioner_Commissioned()+"', "
					+ "`DG_Commissioned` = '"+jsonData.getDG_Commissioned()+"', "
					+ "`Acceptance_Testing_Of_Site_Infrastructure_Completed_Status` = '"+jsonData.getAcceptance_Testing_Of_Site_Infrastructure_Completed_Status()+"', "
					+ "`EB_Status` = '"+jsonData.getEB_Status()+"', `Created_By` = '"+jsonData.getCreated_By()+"', "
					+ "`OFC_Duct_Laying_Done` = '"+jsonData.getOFC_Duct_Laying_Done()+"', "
					+ "`Access_To_Site_Available_Status` = '"+jsonData.getAccess_To_Site_Available_Status()+"', "
					+ "`Engineer_Name` = '"+jsonData.getEngineer_Name()+"', "
					+ "`Engineer_Phone_Number` = '"+jsonData.getEngineer_Phone_Number()+"', "
					+ "`Notice_Form_Generation_Date` = '"+jsonData.getNotice_Form_Generation_Date()+"' ";
				}
				else if(afterStatus.equalsIgnoreCase("NB10")){
					sql += ", `RFS_DATE` = '"+jsonData.getRfsDate()+"' ";
				}
				else if(afterStatus.equalsIgnoreCase("NB101")){
					SrSoReject srSo = new SrSoReject();
					srSo.setComments(jsonData.getRemark());
					srSo.setStatus("SR Rejected");
					srSo.setSR_Number(srNumber);
					SpReceivedResponse apiRes = srSoReject2(srSo, currentStatus);
					if(!apiRes.isStatus())
						airtelApiResponse = "updatesrdetailstatus :: "+apiRes.getMessage();
				}
				else if(afterStatus.equalsIgnoreCase("NB107")){
					SrSoReject srSo = new SrSoReject();
					srSo.setComments(jsonData.getRemark());
					srSo.setStatus("SO Rejected");
					srSo.setSR_Number(srNumber);
					SpReceivedResponse apiRes = srSoReject2(srSo, currentStatus);
					if(!apiRes.isStatus())
						airtelApiResponse = "updatesrdetailstatus :: "+apiRes.getMessage();
				}
				
				sql +=  "where `SR_Number` = '"+srNumber+"'";
				i = tviCommonDao.updateBulkdataValue(sql);
				if(1 != 0){
					if(afterStatus.equalsIgnoreCase("NB04")){
						SpReceivedResponse apiRes = spReceivedSharing(srNumber, currentStatus);
						if(!apiRes.isStatus())
							airtelApiResponse = "sp_insert :: "+apiRes.getMessage();
					}
					else if(afterStatus.equalsIgnoreCase("NB08")){
						RfaiReceivedResponse apiRes = rfaiReceived(srNumber, currentStatus);
						if(!apiRes.isStatus())
							airtelApiResponse = "rfai_insert :: "+apiRes.getMessage();
					}
				}
			}
			else if(jsonData.getTabName().equalsIgnoreCase(Constant.Site_Upgrade)){
				if(afterStatus.equalsIgnoreCase("NB02")){
					String spNumber = srNumber.replace("SR", "SP");
					sql += ", `SP_Number` = '"+spNumber+"', `SP_DATE` = curdate(), "
							+ "`Date_of_Proposal` = '"+jsonData.getDate_of_Proposal()+"', "
							+ "`Power_Rating` = '"+jsonData.getPower_Rating()+"', "
							+ "`Site_Electrification_Distance` = '"+jsonData.getSite_Electrification_Distance()+"', "
							+ "`Tentative_EB_Availibility` = '"+jsonData.getTentative_EB_Availibility()+"', "
							+ "`Additional_Charge` = '"+jsonData.getAdditional_Charge()+"', "
							+ "`Head_Load_Charge` = '"+jsonData.getHead_Load_Charge()+"', "
							+ "`Electrification_Cost` = '"+jsonData.getElectrification_Cost()+"', "
							+ "`Electrification_Line_Distance` = '"+jsonData.getElectrification_Line_Distance()+"', "
							+ "`Electricity_Connection_HT_LT` = '"+jsonData.getElectricity_Connection_HT_LT()+"', "
							+ "`Infra_Details` = '"+jsonData.getInfra_Details()+"', "
							+ "`Site_Classification` = '"+jsonData.getSite_Classification()+"', "
							+ "`Expected_Rent_to_Landlord` = '"+jsonData.getExpected_Rent_to_Landlord()+"', "
							+ "`Non_Refundable_Deposit` = '"+jsonData.getNon_Refundable_Deposit()+"', "
							+ "`Estimated_Deployment_Time__in_days_` = '"+jsonData.getEstimated_Deployment_Time__in_days_()+"', "
							+ "`Additional_Capex` = '"+jsonData.getAdditional_Capex()+"', "
							+ "`Standard_Rates` = '"+jsonData.getStandard_Rates()+"', "
							+ "`Fiber_Charges` = '"+jsonData.getFiber_Charges()+"', "
							+ "`Rental_Threshold` = '"+jsonData.getRental_Threshold()+"', "
							+ "`Excess_Rent_beyond_Threshold` = '"+jsonData.getExcess_Rent_beyond_Threshold()+"', "
							+ "`Tentative_Rental_Premium` = '"+jsonData.getTentative_Rental_Premium()+"', "
							+ "`Additional_Rent` = '"+jsonData.getAdditional_Rent()+"', "
							+ "`IPFee` = '"+jsonData.getIPFee()+"', "
							+ "`Field_Operation_Charges` = '"+jsonData.getField_Operation_Charges()+"', "
							+ "`Security_Guard_Charges` = '"+jsonData.getSecurity_Guard_Charges()+"', "
							+ "`Mobilization_Charges` = '"+jsonData.getMobilization_Charges()+"', "
							+ "`Erection_Charges` = '"+jsonData.getErection_Charges()+"', "
							+ "`Battery_backup_Hrs` = '"+jsonData.getBattery_backup_Hrs()+"', "
							+ "`Land_Lord_Charges_or_Rent_Charges` = '"+jsonData.getLand_Lord_Charges_or_Rent_Charges()+"', "
							+ "`Wind_Speed` = '"+jsonData.getWind_Speed()+"', "
							+ "`TowerHeight` = '"+jsonData.getTowerHeight()+"', "
							+ "`Agl` = '"+jsonData.getAgl()+"', "
							+ "`Distance_from_P1_Lat_Long_in_meter` = '"+jsonData.getDistance_from_P1_Lat_Long_in_meter()+"',"
							+ "`Rejection_Remarks` = '"+jsonData.getRejection_Remarks()+"',"
							+ "`Difficult` = '"+jsonData.getDifficult()+"' ";
					
					sql += ", `Association_AreyouWorkingInAnyBhartiGroup` = '"+jsonData.getAssociation_AreyouWorkingInAnyBhartiGroup()+"', "
							+ "`Association_IfyesmentiontheBhartiUnitName` = '"+jsonData.getAssociation_IfyesmentiontheBhartiUnitName()+"', "
							+ "`Association_NameOftheEmployee` = '"+jsonData.getAssociation_NameOftheEmployee()+"', "
							+ "`Association_EmployeeId` = '"+jsonData.getAssociation_EmployeeId()+"', "
							+ "`Relative_AnyRelativesareWorkingWithBhartiGroup` = '"+jsonData.getRelative_AnyRelativesareWorkingWithBhartiGroup()+"', "
							+ "`Relative_IfyesmentiontheBhartiUnitName` = '"+jsonData.getRelative_IfyesmentiontheBhartiUnitName()+"', "
							+ "`Relative_NameOftheEmployee` = '"+jsonData.getRelative_NameOftheEmployee()+"', "
							+ "`Relative_EmployeeId` = '"+jsonData.getRelative_EmployeeId()+"', "
							+ "`Relative_LandlordRelationshipwithEmployee` = '"+jsonData.getRelative_LandlordRelationshipwithEmployee()+"', "
							+ "`Relative_MobileNumberOfrelativeWithAirtel` = '"+jsonData.getRelative_MobileNumberOfrelativeWithAirtel()+"', "
							+ "`Declaration` = '"+jsonData.getDeclaration()+"' ";
					
					insertBtsData(jsonData);
					insertRadioAntennaData(jsonData);
					insertMwAntennaData(jsonData);
					insertBscRncData(jsonData);
					insertOtherNodeData(jsonData);
					insertMcbData(jsonData);
					insertFibreNodeData(jsonData);
					insertTmaTmbData(jsonData);
					insertOtherEquimentData(jsonData);
				}
				else if(afterStatus.equalsIgnoreCase("NB03")){
					String soNumber = srNumber.replace("SR", "SO");
					sql += ", `SO_Number` = '"+soNumber+"', `SO_DATE` = curdate() ";
				}
				
				else if(afterStatus.equalsIgnoreCase("NB05")){
//					sql += ", `RFI_DATE` = '"+jsonData.getRfiDate()+"', "
					sql += ", `RFI_DATE` = curdate(), "
					+ "`Tower_Completion` = '"+jsonData.getTower_Completion()+"', "
					+ "`Shelter_Equipment_RoomReady` = '"+jsonData.getShelter_Equipment_RoomReady()+"', "
					+ "`AirConditioner_Commissioned` = '"+jsonData.getAirConditioner_Commissioned()+"', "
					+ "`DG_Commissioned` = '"+jsonData.getDG_Commissioned()+"', "
					+ "`Acceptance_Testing_Of_Site_Infrastructure_Completed_Status` = '"+jsonData.getAcceptance_Testing_Of_Site_Infrastructure_Completed_Status()+"', "
					+ "`EB_Status` = '"+jsonData.getEB_Status()+"', `Created_By` = '"+jsonData.getCreated_By()+"', "
					+ "`OFC_Duct_Laying_Done` = '"+jsonData.getOFC_Duct_Laying_Done()+"', "
					+ "`Access_To_Site_Available_Status` = '"+jsonData.getAccess_To_Site_Available_Status()+"', "
					+ "`Engineer_Name` = '"+jsonData.getEngineer_Name()+"', "
					+ "`Engineer_Phone_Number` = '"+jsonData.getEngineer_Phone_Number()+"', "
					+ "`Notice_Form_Generation_Date` = '"+jsonData.getNotice_Form_Generation_Date()+"' ";
				}
				else if(afterStatus.equalsIgnoreCase("NB08")){
					sql += ", `RFS_DATE` = '"+jsonData.getRfsDate()+"' ";
				}
				else if(afterStatus.equalsIgnoreCase("NB101")){
					SrSoReject srSo = new SrSoReject();
					srSo.setComments(jsonData.getRemark());
					srSo.setStatus("SR Rejected");
					srSo.setSR_Number(srNumber);
					SpReceivedResponse apiRes = srSoReject2(srSo, currentStatus);
					if(!apiRes.isStatus())
						airtelApiResponse = "updatesrdetailstatus :: "+apiRes.getMessage();
				}
				else if(afterStatus.equalsIgnoreCase("NB107")){
					SrSoReject srSo = new SrSoReject();
					srSo.setComments(jsonData.getRemark());
					srSo.setStatus("SO Rejected");
					srSo.setSR_Number(srNumber);
					SpReceivedResponse apiRes = srSoReject2(srSo, currentStatus);
					if(!apiRes.isStatus())
						airtelApiResponse = "updatesrdetailstatus :: "+apiRes.getMessage();
				}
				
				sql +=  "where `SR_Number` = '"+srNumber+"'";
				i = tviCommonDao.updateBulkdataValue(sql);
				if(1 != 0){
					if(afterStatus.equalsIgnoreCase("NB02")){
						SpReceivedResponse apiRes = spReceivedUpgrade(srNumber, currentStatus);
						if(!apiRes.isStatus())
							airtelApiResponse = "sp_insert :: "+apiRes.getMessage();
					}
					else if(afterStatus.equalsIgnoreCase("NB06")){
						RfaiReceivedResponse apiRes = rfaiReceived(srNumber, currentStatus);
						if(!apiRes.isStatus())
							airtelApiResponse = "rfai_insert :: "+apiRes.getMessage();
					}
				}
			}
			else if(jsonData.getTabName().equalsIgnoreCase(Constant.HPSC)){
				if(afterStatus.equalsIgnoreCase("NB04") || afterStatus.equalsIgnoreCase("NB06")){
					sql += ", `SuggesstedSiteAddress` = '"+jsonData.getSuggestedSiteAddress()+"', "
						+ " `SuggestedDistrict` = '"+jsonData.getSuggestedDistrict()+"', "
						+ " `SuggestedState` = '"+jsonData.getSuggestedState()+"', "
						+ " `SuggestedPincode` = '"+jsonData.getSuggestedPincode()+"', "
						+ " `SuggestedTownVillage` = '"+jsonData.getSuggestedTownVillage()+"', "
						+ " `SuggestedCity` = '"+jsonData.getSuggestedCity()+"', "
						+ " `SuggestedLatitude` = '"+jsonData.getSuggestedLatitude()+"', "
						+ " `SuggestedLongitude` = '"+jsonData.getSuggestedLongitude()+"', "
						+ " `SuggestedDeviation` = '"+jsonData.getSuggestedDeviation()+"', "
						+ " `SuggestedTowerType` = '"+jsonData.getSuggestedTowerType()+"', "
						+ " `SuggestedBuildingHeight` = '"+jsonData.getSuggestedBuildingHeight()+"', "
						+ " `SuggestedTowerHeight` = '"+jsonData.getSuggestedTowerHeight()+"', "
						+ " `SuggestedClutter` = '"+jsonData.getSuggestedClutter()+"', "
						+ " `SuggestedLandOwnerRent` = '"+jsonData.getSuggestedLandOwnerRent()+"', "
						+ " `SuggestedElectrificationCharges` = '"+jsonData.getSuggestedElectrificationCharges()+"', "
						+ "	`SuggestedMcCharges` = '"+jsonData.getSuggestedMcCharges()+"' ";
				}
				else if(afterStatus.equalsIgnoreCase("NB08")){
					String spNumber = srNumber.replace("SR", "SP");
					sql += ", `SP_Number` = '"+spNumber+"', `SP_DATE` = curdate() ";
	
				}
				else if(afterStatus.equalsIgnoreCase("NB09")){
					/*Check TOCO/TVI site id site master.*/
					//------- Start ---------
					String tviSiteId = jsonData.getTOCO_Site_Id();
					String siteSql = "SELECT `TVISiteID` FROM `Site_Master` where `TVISiteID` = '"+tviSiteId+"' ";
					boolean isExist = tviCommonDao.isExistSR(siteSql);
					if(isExist){
						response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
						response.setResponseDesc(ReturnsCode.ALREADY_EXIST+" TOCO Site Id  = "+tviSiteId+" in Site Master.");
						return response;
					}
					// ------- End ---------
					
					sql += ", `SP_Latitude` = '"+jsonData.getLatitude()+"', "
						+ "`SP_Longitude` = '"+jsonData.getLongitude()+"', `Clutter` = '"+jsonData.getClutter()+"', "
						+ "`TOCO_Site_Id` = '"+jsonData.getTOCO_Site_Id()+"', "
						+ "`Date_of_Proposal` = '"+jsonData.getDate_of_Proposal()+"', "
						+ "`Power_Rating` = '"+jsonData.getPower_Rating()+"', "
						+ "`Site_Electrification_Distance` = '"+jsonData.getSite_Electrification_Distance()+"', "
						+ "`Tentative_EB_Availibility` = '"+jsonData.getTentative_EB_Availibility()+"', "
						+ "`Address1` = '"+jsonData.getAddress1()+"', "
						+ "`Head_Load_Charge` = '"+jsonData.getHead_Load_Charge()+"', "
						+ "`Electrification_Cost` = '"+jsonData.getElectrification_Cost()+"', "
						+ "`Electrification_Line_Distance` = '"+jsonData.getElectrification_Line_Distance()+"', "
						+ "`Electricity_Connection_HT_LT` = '"+jsonData.getElectricity_Connection_HT_LT()+"', "
						+ "`Wind_Speed` = '"+jsonData.getWind_Speed()+"', "
						+ "`TowerHeight` = '"+jsonData.getTowerHeight()+"', "
						+ "`Agl` = '"+jsonData.getAgl()+"', "
						+ "`Distance_from_P1_Lat_Long_in_meter` = '"+jsonData.getDistance_from_P1_Lat_Long_in_meter()+"' ";
					
					sql += ", `Additional_Charge` = '"+jsonData.getAdditional_Charge()+"', "
						+ "`Infra_Details` = '"+jsonData.getInfra_Details()+"', "
						+ "`Site_Classification` = '"+jsonData.getSite_Classification()+"', "
						+ "`Expected_Rent_to_Landlord` = '"+jsonData.getExpected_Rent_to_Landlord()+"', "
						+ "`Non_Refundable_Deposit` = '"+jsonData.getNon_Refundable_Deposit()+"', "
						+ "`Estimated_Deployment_Time__in_days_` = '"+jsonData.getEstimated_Deployment_Time__in_days_()+"', "
						+ "`Additional_Capex` = '"+jsonData.getAdditional_Capex()+"', "
						+ "`Standard_Rates` = '"+jsonData.getStandard_Rates()+"', "
						+ "`Fiber_Charges` = '"+jsonData.getFiber_Charges()+"', "
						+ "`Rental_Threshold` = '"+jsonData.getRental_Threshold()+"', "
						+ "`Excess_Rent_beyond_Threshold` = '"+jsonData.getExcess_Rent_beyond_Threshold()+"', "
						+ "`Tentative_Rental_Premium` = '"+jsonData.getTentative_Rental_Premium()+"', "
						+ "`Additional_Rent` = '"+jsonData.getAdditional_Rent()+"', "
						+ "`IPFee` = '"+jsonData.getIPFee()+"', "
						+ "`Field_Operation_Charges` = '"+jsonData.getField_Operation_Charges()+"', "
						+ "`Security_Guard_Charges` = '"+jsonData.getSecurity_Guard_Charges()+"', "
						+ "`Mobilization_Charges` = '"+jsonData.getMobilization_Charges()+"', "
						+ "`Erection_Charges` = '"+jsonData.getErection_Charges()+"', "
						+ "`Battery_backup_Hrs` = '"+jsonData.getBattery_backup_Hrs()+"', "
						+ "`Land_Lord_Charges_or_Rent_Charges` = '"+jsonData.getLand_Lord_Charges_or_Rent_Charges()+"', "
						+ "`Recommended_Product_Type_by_Acquisition` = '"+jsonData.getRecommended_Product_Type_by_Acquisition()+"' ";
					
					sql += ", `Association_AreyouWorkingInAnyBhartiGroup` = '"+jsonData.getAssociation_AreyouWorkingInAnyBhartiGroup()+"', "
						+ "`Association_IfyesmentiontheBhartiUnitName` = '"+jsonData.getAssociation_IfyesmentiontheBhartiUnitName()+"', "
						+ "`Association_NameOftheEmployee` = '"+jsonData.getAssociation_NameOftheEmployee()+"', "
						+ "`Association_EmployeeId` = '"+jsonData.getAssociation_EmployeeId()+"', "
						+ "`Relative_AnyRelativesareWorkingWithBhartiGroup` = '"+jsonData.getRelative_AnyRelativesareWorkingWithBhartiGroup()+"', "
						+ "`Relative_IfyesmentiontheBhartiUnitName` = '"+jsonData.getRelative_IfyesmentiontheBhartiUnitName()+"', "
						+ "`Relative_NameOftheEmployee` = '"+jsonData.getRelative_NameOftheEmployee()+"', "
						+ "`Relative_EmployeeId` = '"+jsonData.getRelative_EmployeeId()+"', "
						+ "`Relative_LandlordRelationshipwithEmployee` = '"+jsonData.getRelative_LandlordRelationshipwithEmployee()+"', "
						+ "`Relative_MobileNumberOfrelativeWithAirtel` = '"+jsonData.getRelative_MobileNumberOfrelativeWithAirtel()+"', "
						+ "`Declaration` = '"+jsonData.getDeclaration()+"' ";
					
					insertBtsData(jsonData);
					insertRadioAntennaData(jsonData);
					insertMwAntennaData(jsonData);
					insertBscRncData(jsonData);
					insertOtherNodeData(jsonData);
					insertMcbData(jsonData);
					insertFibreNodeData(jsonData);
					insertOtherEquimentData(jsonData);
				}
				else if(afterStatus.equalsIgnoreCase("NB10")){
					String soNumber = srNumber.replace("SR", "SO");
					sql += ", `SO_Number` = '"+soNumber+"', `SO_DATE` = curdate() ";
				}
				else if(afterStatus.equalsIgnoreCase("NB15")){
					/*Check TOCO/TVI site id site master.*/
					//------- Start ---------
					String tviSiteId = jsonData.getViewTocoSiteId();
					String siteSql = "SELECT `TVISiteID` FROM `Site_Master` where `TVISiteID` = '"+tviSiteId+"' ";
					boolean isExist = tviCommonDao.isExistSR(siteSql);
					if(isExist){
						response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
						response.setResponseDesc(ReturnsCode.ALREADY_EXIST+" TOCO Site Id  = "+tviSiteId+" in Site Master.");
						return response;
					}
					// ------- End ---------
				}
				else if(afterStatus.equalsIgnoreCase("NB16")){
//					sql += ", `RFI_DATE` = '"+jsonData.getRfiDate()+"', "
					sql += ", `RFI_DATE` = curdate(), "
					+ "`Tower_Completion` = '"+jsonData.getTower_Completion()+"', "
					+ "`Shelter_Equipment_RoomReady` = '"+jsonData.getShelter_Equipment_RoomReady()+"', "
					+ "`AirConditioner_Commissioned` = '"+jsonData.getAirConditioner_Commissioned()+"', "
					+ "`DG_Commissioned` = '"+jsonData.getDG_Commissioned()+"', "
					+ "`Acceptance_Testing_Of_Site_Infrastructure_Completed_Status` = '"+jsonData.getAcceptance_Testing_Of_Site_Infrastructure_Completed_Status()+"', "
					+ "`EB_Status` = '"+jsonData.getEB_Status()+"', `Created_By` = '"+jsonData.getCreated_By()+"', "
					+ "`OFC_Duct_Laying_Done` = '"+jsonData.getOFC_Duct_Laying_Done()+"', "
					+ "`Access_To_Site_Available_Status` = '"+jsonData.getAccess_To_Site_Available_Status()+"', "
					+ "`Engineer_Name` = '"+jsonData.getEngineer_Name()+"', "
					+ "`Engineer_Phone_Number` = '"+jsonData.getEngineer_Phone_Number()+"', "
					+ "`Notice_Form_Generation_Date` = '"+jsonData.getNotice_Form_Generation_Date()+"' ";
				}
				else if(afterStatus.equalsIgnoreCase("NB19")){
					sql += ", `RFS_DATE` = '"+jsonData.getRfsDate()+"' ";
				}
				else if(afterStatus.equalsIgnoreCase("NB101")){
					SrSoReject srSo = new SrSoReject();
					srSo.setComments(jsonData.getRemark());
					srSo.setStatus("SR Rejected");
					srSo.setSR_Number(srNumber);
					SpReceivedResponse apiRes = srSoReject2(srSo, currentStatus);
					if(!apiRes.isStatus())
						airtelApiResponse = "updatesrdetailstatus :: "+apiRes.getMessage();
				}
				else if(afterStatus.equalsIgnoreCase("NB107")){
					SrSoReject srSo = new SrSoReject();
					srSo.setComments(jsonData.getRemark());
					srSo.setStatus("SO Rejected");
					srSo.setSR_Number(srNumber);
					SpReceivedResponse apiRes = srSoReject2(srSo, currentStatus);
					if(!apiRes.isStatus())
						airtelApiResponse = "updatesrdetailstatus :: "+apiRes.getMessage();
				}
				
				sql +=  "where `SR_Number` = '"+srNumber+"'";
				i = tviCommonDao.updateBulkdataValue(sql);
				if(i != 0){
					if(afterStatus.equalsIgnoreCase("NB09")){
						SpReceivedResponse apiRes = spReceived(srNumber, currentStatus);
						if(!apiRes.isStatus())
							airtelApiResponse = "sp_insert :: "+apiRes.getMessage();
					}
					else if(afterStatus.equalsIgnoreCase("NB17")){
						RfaiReceivedResponse apiRes = rfaiReceived(srNumber, currentStatus);
						if(!apiRes.isStatus())
							airtelApiResponse = "rfai_insert :: "+apiRes.getMessage();
					}
				}
			
			}
			insertAttachment(jsonData);
			if(i != 0){
				if(airtelApiResponse.equalsIgnoreCase("")){
					response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
					response.setResponseDesc(ReturnsCode.SUCCESSFUL);
				}
				else{
					response.setResponseCode(ReturnsCode.GENERIC_WEB_SERVICE_ERROR);
					response.setResponseDesc(airtelApiResponse);
					
					String upSql = "UPDATE `Airtel_SR` set `STATUS` = '"+currentStatus+"' where `SR_Number` = '"+srNumber+"'";
					tviCommonDao.updateBulkdataValue(upSql);
				}
			}
			else{
				response.setResponseCode(ReturnsCode.NO_RECORDS_FOUND_CODE);
				response.setResponseDesc(ReturnsCode.NO_RECORD_FOUND);
			}
			
			if(airtelApiResponse.equalsIgnoreCase("")){
				prepareForSendMail(jsonData.getSrNumber(), jsonData.getSpNumber(), jsonData.getSoNumber(), 
						jsonData.getCircleName(), Constant.Airtel, jsonData.getAfterStatus(), jsonData.getTabName(), errorsMap);
				
				AirtelSrAuditEntity audEnt = new AirtelSrAuditEntity();
				audEnt.setSrNumber(jsonData.getSrNumber());
				audEnt.setEmpId(jsonData.getLoginEmpId());
				audEnt.setRamark(jsonData.getRemark());
				audEnt.setAction(jsonData.getAfterStatus());
				audEnt.setCreateDate(new Date());
				tviCommonDao.saveOrUpdateEntityData(audEnt);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}
	
	private void insertOtherEquimentData(ChangeAirtelSrStatusRequest jsonData) {
		try {
			String srNumber = jsonData.getSrNumber();
			String delSql = "UPDATE `Airtel_Other_Equipment` set `InsertType`='SP_deleted'  where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			logger.info("Other_Equipment updated: "+delSql);
			tviCommonDao.updateBulkdataValue(delSql);
			
			List<ValidOtherEquipmentDto> validList = jsonData.getValidOtherEquipmentList();
			String sql = "INSERT INTO `Airtel_Other_Equipment`(`SR_Number`, `Type_No`, `Feasibility`, "
					+ "`CustomerPunchedOrPlanning`, `Other_Equipment_Category`, "
					+ "`Other_Equipment_Type`, `Deletion_OR_Relocation`, "
					+ "`Target_Indus_Site_Id`,`Target_Request_RefNo`, `InsertType`)";
			sql += " VALUES ";
			for(ValidOtherEquipmentDto obj : validList){
				String dataSql = "('"+jsonData.getSrNumber()+"', "+obj.getTypeNo()+", '"+obj.getFeasibility()+"', "
				+ "'"+obj.getCustomerPunchedOrPlanning()+"', '"+obj.getOther_Equipment_Category()+"', "
				+ "'"+obj.getOther_Equipment_Type()+"', '"+obj.getDeletion_OR_Relocation()+"', "
				+ "'"+obj.getTarget_Indus_Site_Id()+"', '"+obj.getTarget_Request_RefNo()+"',  'SP')";
				int exe = tviCommonDao.updateBulkdataValue(sql + dataSql);
				if(exe != 0){
					//System.out.println("TMA TMB updated");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void insertTmaTmbData(ChangeAirtelSrStatusRequest jsonData) {
		try {
			String srNumber = jsonData.getSrNumber();
			String delSql = "UPDATE `Airtel_TMA_TMB` set `InsertType`='SP_deleted'  where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			logger.info("TMA_TMB updated: "+delSql);
			tviCommonDao.updateBulkdataValue(delSql);
			
			List<ValidTmaTmbDto> validList = jsonData.getValidTmaTmbList();
			String sql = "INSERT INTO `Airtel_TMA_TMB`(`SR_Number`, `Type_No`, `Feasibility`, "
					+ "`Action`, `No_of_TMA_TMB`, `Weight_of_each_TMA_TMB`, "
					+ "`Combined_wt_of_TMA_TMB_Kgs`, `Height_at_which_needs_to_be_mounted_Mtrs`, "
					+ "`Customer_Punched_Or_Planning`,`Source_Request_Ref_No`, "
					+ "`Delete_Request_Ref_No`, `InsertType`)";
			sql += " VALUES ";
			for(ValidTmaTmbDto obj : validList){
				String dataSql = "('"+jsonData.getSrNumber()+"', "+obj.getTypeNo()+", '"+obj.getFeasibility()+"', "
				+ "'"+obj.getAction()+"', '"+obj.getNo_of_TMA_TMB()+"', '"+obj.getWeight_of_each_TMA_TMB()+"', "
				+ "'"+obj.getCombined_wt_of_TMA_TMB_Kgs()+"', '"+obj.getHeight_at_which_needs_to_be_mounted_Mtrs()+"', "
				+ "'"+obj.getCustomer_Punched_Or_Planning()+"', '"+obj.getSource_Request_Ref_No()+"', "
				+ "'"+obj.getDelete_Request_Ref_No()+"',  'SP')";
				int exe = tviCommonDao.updateBulkdataValue(sql + dataSql);
				if(exe != 0){
					//System.out.println("TMA TMB updated");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void insertAttachment(ChangeAirtelSrStatusRequest jsonData) {
		try {
			List<AttachmentDto> attachedList = jsonData.getAttachedList();
			if(attachedList.size() != 0){
				for(AttachmentDto obj : attachedList){
					String srNumber = jsonData.getSrNumber();
					String afterStatus = jsonData.getAfterStatus();
					String role = jsonData.getLoginEmpRole();
					String attName = obj.getAttName();
					String updateSql = "UPDATE `Airtel_SR_Attachment` set `IsActive` = 0 where "
							+ "`SR_Number` = '"+srNumber+"' and `Status` = '"+afterStatus+"' and "
							+ "`Role` = '"+role+"' and `AttachmentName` = '"+attName+"'";
					int updExe = tviCommonDao.updateBulkdataValue(updateSql);
					if(updExe != 0){
						//System.out.println("Attachment updated");
					}
					
					String insertSql = "INSERT INTO `Airtel_SR_Attachment`(`SR_Number`, `Status`, `Role`, `AttachmentName`, `URL`) VALUES ";
					insertSql += "('"+srNumber+"', '"+afterStatus+"', '"+role+"', '"+attName+"', '"+obj.getImageStr()+"')";
					int insExe = tviCommonDao.updateBulkdataValue(insertSql);
					if(insExe != 0){
						//System.out.println("Attachment inserted");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void insertBtsData(ChangeAirtelSrStatusRequest jsonData) {
		try {
			String srNumber = jsonData.getSrNumber();
			String delSql = "UPDATE `Airtel_BTS` set `InsertType` = 'SP_deleted' where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			logger.info("BTS updated: "+delSql);
			tviCommonDao.updateBulkdataValue(delSql);
			
			List<ValidBtsDto> validList = jsonData.getValidBtsList();
			String sql = "INSERT INTO `Airtel_BTS`(`SR_Number`, `Feasibility`, "
					+ "`Customer_Punched_Or_Planning`, `Action`, `Type_No`, "
					+ "`NetWork_Type`, `BTS_Type`, `Band`, `Manufacturer`, "
					+ "`Make_of_BTS`, `Length_Mtrs`, `Width_Mtrs`, `Height_Mtrs`, "
					+ "`BTS_Power_Rating_KW`, `BTS_Location`, `BTS_Voltage`, "
					+ "`Main_Unit_incase_of_TT_Split_Version`, `Space_Occupied_in_Us_incase_of_TT_Split_Version`, "
					+ "`RRU_Unit`, `No_of_RRU_Units_incase_of_TT_Split_Version`, "
					+ "`Combined_wt_of_RRU_Unit_incase_of_TT_Split_Version`, `AGL_of_RRU_unit_in_M`, "
					+ "`Weight_of_BTS_including_TMA_TMB_Kg`, `Billable_Weigtht`, `InsertType`)";
			sql += " VALUES ";
			for(ValidBtsDto obj : validList){
				String dataSql = " ('"+jsonData.getSrNumber()+"', '"+obj.getFeasibility()+"', "
						+ "'"+obj.getCustomer_Punched_Or_Planning()+"', '"+obj.getAction()+"', "+obj.getTypeNo()+", "
						+ "'"+obj.getNetWork_Type()+"', '"+obj.getBTS_Type()+"', '"+obj.getBand()+"', '"+obj.getManufacturer()+"', "
						+ "'"+obj.getMake_of_BTS()+"', "+obj.getLength_Mtrs()+", "+obj.getWidth_Mtrs()+", "+obj.getHeight_Mtrs()+", "
						+ ""+obj.getBTS_Power_Rating_KW()+", '"+obj.getBTS_Location()+"', '"+obj.getBTS_Voltage()+"', "
						+ "'"+obj.getMain_Unit_incase_of_TT_Split_Version()+"', "+obj.getSpace_Occupied_in_Us_incase_of_TT_Split_Version()+", "
						+ "'"+obj.getLocation_Of_RRU()+"', "+obj.getNo_of_RRU_Units_incase_of_TT_Split_Version()+", "
						+ ""+obj.getCombined_wt_of_RRU_Unit_incase_of_TT_Split_Version()+", "+obj.getAGL_of_RRU_unit_in_M()+", "
						+ ""+obj.getWeight_of_BTS_including_TMA_TMB_Kg()+", "+obj.getBillable_Weigtht()+", 'SP')";
				int exe = tviCommonDao.updateBulkdataValue(sql + dataSql);
				if(exe != 0){
					//System.out.println("BTS updated");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void insertRadioAntennaData(ChangeAirtelSrStatusRequest jsonData) {
		try {
			String srNumber = jsonData.getSrNumber();
			String delSql = "UPDATE `Airtel_Radio_Antenna` set `InsertType` = 'SP_deleted' where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			logger.info("Radio_Antenna updated: "+delSql);
			tviCommonDao.updateBulkdataValue(delSql);
			
			List<ValidRadioAntennaDto> validList = jsonData.getValidRadioAntennaList();
			String sql = "INSERT INTO `Airtel_Radio_Antenna`(`SR_Number`, `Feasibility`, "
					+ "`Customer_Punched_Or_Planning`, `Sector_Details`, `Action`, `Type_No`, "
					+ "`RadioAntenna_i_WAN`, `Height_AGL_m`, `Azimuth_Degree`, "
					+ "`Length_m`, `Width_m`, `Depth_m`, `No_of_Ports`, "
					+ "`RadioAntenna_Type`, `BandFrequencyMHz_FrequencyCombination`, `InsertType`)";
			sql += " VALUES ";
			for(ValidRadioAntennaDto obj : validList){
				String dataSql = "('"+jsonData.getSrNumber()+"', '"+obj.getFeasibility()+"', "
						+ "'"+obj.getCustomer_Punched_Or_Planning()+"', '"+obj.getSector_Details()+"', '"+obj.getAction()+"', "+obj.getTypeNo()+", "
						+ "'"+obj.getRadioAntenna_i_WAN()+"', "+obj.getHeight_AGL_m()+", "+obj.getAzimuth_Degree()+", "
						+ ""+obj.getLength_m()+", "+obj.getWidth_m()+", "+obj.getDepth_m()+", '"+obj.getNo_of_Ports()+"', "
						+ "'"+obj.getRadioAntenna_Type()+"', '"+obj.getBandFrequencyMHz_FrequencyCombination()+"', 'SP')";
				int i = tviCommonDao.updateBulkdataValue(sql + dataSql);
				if(i != 0){
					//System.out.println("Radio Antenna updated");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void insertMwAntennaData(ChangeAirtelSrStatusRequest jsonData) {
		try {
			String srNumber = jsonData.getSrNumber();
			String delSql = "UPDATE `Airtel_MW` set `InsertType` = 'SP_deleted' where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			logger.info("MW updated: "+delSql);
			tviCommonDao.updateBulkdataValue(delSql);
			
			String sql = "INSERT INTO `Airtel_MW`(`SR_Number`, `Feasibility`, "
					+ "`Customer_Punched_Or_Planning`, `Sector_Details`, `Action`, "
					+ "`Source`, "
					+ "`Type_No`, `MWAntenna_i_WAN`, "
					+ "`Size_of_MW`, `Height_in_Mtrs`, `Azimuth_Degree`, `InsertType`)";
			sql += " VALUES ";
			List<ValidMwAntennaDto> validList = jsonData.getValidMwAntennaList();
			for(ValidMwAntennaDto obj : validList){
				String dataSql = "('"+jsonData.getSrNumber()+"', '"+obj.getFeasibility()+"', "
						+ "'"+obj.getCustomer_Punched_Or_Planning()+"', '"+obj.getSector_Details()+"', '"+obj.getAction()+"', "
						+ "'"+obj.getSource()+"', "
						+ ""+obj.getTypeNo()+", '"+obj.getMWAntenna_i_WAN()+"', "
						+ ""+obj.getSize_of_MW()+", "+obj.getHeight_in_Mtrs()+", "+obj.getAzimuth_Degree()+", 'SP')";
				int i = tviCommonDao.updateBulkdataValue(sql + dataSql);
				if(i != 0){
					//System.out.println("MW Antenna updated");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void insertBscRncData(ChangeAirtelSrStatusRequest jsonData) {
		try {
			String srNumber = jsonData.getSrNumber();
			String delSql = "UPDATE `Airtel_BSC_RNC_Cabinets` set `InsertType` = 'SP_deleted' where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			logger.info("BSC_RNC_Cabinets updated: "+delSql);
			tviCommonDao.updateBulkdataValue(delSql);
			
			String sql = "INSERT INTO `Airtel_BSC_RNC_Cabinets`(`SR_Number`, `Feasibility`, "
					+ "`Customer_Punched_Or_Planning`, `Action`, `Type_No`, `NetWork_Type`, `BSC_RNC_Type`, `BSC_RNC_Manufacturer`, "
					+ "`BSC_RNC_Make`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_AGL`, `BSC_RNC_Power_Rating`, `InsertType`)";
			sql += " VALUES ";
			List<ValidBscRncDto> validList = jsonData.getValidBscRncList();
			for(ValidBscRncDto obj : validList){
				String dataSql = "('"+jsonData.getSrNumber()+"', '"+obj.getFeasibility()+"', "
						+ "'"+obj.getCustomer_Punched_Or_Planning()+"', '"+obj.getAction()+"', "+obj.getTypeNo()+", '"+obj.getNetWork_Type()+"', '"+obj.getBSC_RNC_Type()+"', '"+obj.getBSC_RNC_Manufacturer()+"', "
						+ "'"+obj.getBSC_RNC_Make()+"', "+obj.getLength_Mtrs()+", "+obj.getBreadth_Mtrs()+", "+obj.getHeight_AGL()+", "+obj.getBSC_RNC_Power_Rating()+", 'SP')";
				//System.out.println(sql + dataSql);
				
				int i = tviCommonDao.updateBulkdataValue(sql + dataSql);
				if(i != 0){
					//System.out.println("BSC RNC Cabinets updated");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void insertOtherNodeData(ChangeAirtelSrStatusRequest jsonData) {
		try {
			String srNumber = jsonData.getSrNumber();
			String delSql = "UPDATE `Airtel_Other_Node` set `InsertType` = 'SP_deleted' where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			logger.info("Other_Node updated: "+delSql);
			tviCommonDao.updateBulkdataValue(delSql);
			
			String sql = "INSERT INTO `Airtel_Other_Node`(`SR_Number`, `Feasibility`, "
					+ "`Customer_Punched_Or_Planning`, `Action`, `Type_No`, `Node_Type`, "
					+ "`Node_Location`, `Node_Manufacturer`, "
					+ "`Node_Model`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, "
					+ "`Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, `FullRack`, "
					+ "`Tx_Rack_Space_Required_In_Us`, `Remarks`, `InsertType`)";
			sql += " VALUES ";
			List<ValidOtherNodeDto> validList = jsonData.getValidOtherNodeList();
			for(ValidOtherNodeDto obj : validList){
				String dataSql = "('"+jsonData.getSrNumber()+"', '"+obj.getFeasibility()+"', "
						+ "'"+obj.getCustomer_Punched_Or_Planning()+"', '"+obj.getAction()+"', "+obj.getTypeNo()+", '"+obj.getNode_Type()+"', "
						+ "'"+obj.getNode_Location()+"', '"+obj.getNode_Manufacturer()+"', "
						+ "'"+obj.getNode_Model()+"', "+obj.getLength_Mtrs()+", "+obj.getBreadth_Mtrs()+", "+obj.getHeight_Mtrs()+", "
						+ ""+obj.getWeight_Kg()+", '"+obj.getNode_Voltage()+"', "+obj.getPower_Rating_in_Kw()+", '"+obj.getFullRack()+"', "
						+ ""+obj.getTx_Rack_Space_Required_In_Us()+", '"+obj.getRemarks()+"', 'SP')";
				
				int i = tviCommonDao.updateBulkdataValue(sql + dataSql);
				if(i != 0){
					//System.out.println("Other Node updated");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void insertMcbData(ChangeAirtelSrStatusRequest jsonData) {
		try {
			String srNumber = jsonData.getSrNumber();
			String delSql = "UPDATE `Airtel_MCB` set `InsertType` = 'SP_deleted' where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			logger.info("MCB updated: "+delSql);
			tviCommonDao.updateBulkdataValue(delSql);
			
			ValidMcbDto mcb = jsonData.getValidMcbList().get(0);
			String sql = "INSERT INTO `Airtel_MCB`(`SR_Number`, `Feasibility`, `Customer_Punched_Or_Planning`, "
					+ "`Total_No_of_MCB_Required`, `_06A`, `_10A`, `_16A`, "
					+ "`_24A`, `_32A`, `_40A`, `_63A`, `_80A`, `InsertType`)";
			sql += " VALUES ";
			sql += "('"+jsonData.getSrNumber()+"', '"+mcb.getFeasibility()+"', '"+mcb.getCustomer_Punched_Or_Planning()+"', "
					+ ""+mcb.getTotal_No_of_MCB_Required()+", "+mcb.get_06A()+", "+mcb.get_10A()+", "+mcb.get_16A()+", "
					+ ""+mcb.get_24A()+", "+mcb.get_32A()+", "+mcb.get_40A()+", "+mcb.get_63A()+", "+mcb.get_80A()+", 'SP')";
			
			int i = tviCommonDao.updateBulkdataValue(sql);
			if(i != 0){
				//System.out.println("MCB updated");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void insertFibreNodeData(ChangeAirtelSrStatusRequest jsonData) {
		try {
			String srNumber = jsonData.getSrNumber();
			String delSql = "UPDATE `Airtel_Fibre_Node` set `InsertType` = 'SP_deleted' where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			logger.info("Fibre_Node updated: "+delSql);
			tviCommonDao.updateBulkdataValue(delSql);
			
			String sql = "INSERT INTO `Airtel_Fibre_Node`(`SR_Number`, `Feasibility`, "
					+ "`Customer_Punched_Or_Planning`, `Action`, `Source`, "
					+ "`Type_No`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, "
					+ "`Node_Model`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, "
					+ "`Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, `FullRack`, "
					+ "`Tx_Rack_Space_required_in_Us`, `Is_Right_Of_Way_ROW_Required_Inside_The_Indus_Premises`, "
					+ "`Type_Of_Fiber_Laying`, `Type_Of_FMS`, `Remarks`, `Full_Rack`, `InsertType`)";
			sql += " VALUES ";
			List<ValidFibreNodeDto> validList = jsonData.getValidFibreNodeList();
			for(ValidFibreNodeDto obj : validList){
				String dataSql = "('"+jsonData.getSrNumber()+"', '"+obj.getFeasibility()+"', "
						+ "'"+obj.getCustomer_Punched_Or_Planning()+"', '"+obj.getAction()+"', '"+obj.getSource()+"', "
						+ ""+obj.getTypeNo()+", '"+obj.getNode_Type()+"', '"+obj.getNode_Location()+"', '"+obj.getNode_Manufacturer()+"', "
						+ "'"+obj.getNode_Model()+"', "+obj.getLength_Mtrs()+", "+obj.getBreadth_Mtrs()+", "+obj.getHeight_Mtrs()+", "
						+ ""+obj.getWeight_Kg()+", '"+obj.getNode_Voltage()+"', "+obj.getPower_Rating_in_Kw()+", '"+obj.getFullRack()+"', "
						+ ""+obj.getTx_Rack_Space_required_in_Us()+", '"+obj.getIs_Right_Of_Way_ROW_Required_Inside_The_TOCO_Premises()+"', "
						+ "'"+obj.getType_Of_Fiber_Laying()+"', '"+obj.getType_Of_FMS()+"', '"+obj.getRemarks()+"', '"+obj.getFull_Rack()+"', 'SP')";
				
				int i = tviCommonDao.updateBulkdataValue(sql + dataSql);
				if(i != 0){
					//System.out.println("Fibre Node updated");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private SpReceivedResponse srSoReject2(SrSoReject jsonData, String currentStatus) {
		SpReceivedResponse response = new SpReceivedResponse();
		try {
			Gson gsonObj = new Gson();
			String json = gsonObj.toJson(jsonData);
			System.out.println("srSoReject2 : JSON : "+json);
			logger.info("srSoReject2 : JSON : "+json);
			String apiURL = "https://ideploy.airtel.com/iDeployAPI/api/sr/updatesrdetailstatus";
			String apiResponse = RestAPI.updateSrDetailStatus(apiURL, json);
			System.out.println("srSoReject2 : apiResponse : "+apiResponse);
			logger.info("srSoReject2 : apiResponse : "+apiResponse);
			AirtelApiResponse airtelApi = gsonObj.fromJson(apiResponse, AirtelApiResponse.class);
			//System.out.println("Status : "+airtelApi.isStatus());
			//System.out.println("Messsage : "+airtelApi.getMessage());
			response.setStatus(airtelApi.isStatus());
			response.setMessage(airtelApi.getMessage());
			
			boolean status = airtelApi.isStatus();
			if(!status){
				String srNumber = jsonData.getSR_Number();
				String upSql = "UPDATE `Airtel_SR` set `STATUS` = '"+currentStatus+"' where `SR_Number` = '"+srNumber+"'";
				tviCommonDao.updateBulkdataValue(upSql);
			}
			
			Clob requestJson = new SerialClob(CommonFunction.printResponseJson(json).toCharArray());
			Clob responseJson = new SerialClob(CommonFunction.printResponseJson(apiResponse).toCharArray());
			saveResponseInTable(requestJson, responseJson, "srSoReject");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private SpReceivedResponse spReceived(String srNumber, String currentStatus){
		SpReceivedResponse response = new SpReceivedResponse();
		try {
			SpReceivedDto jsonData = getSrDetailsForSendToAirtel(srNumber);
			Gson gsonObj = new Gson();
			String json = gsonObj.toJson(jsonData);
			System.out.println("spReceived : JSON : "+json);
			logger.info("spReceived : JSON : "+json);
			String apiURL = "https://ideploy.airtel.com/iDeployAPI/api/common/sp_insert";
			String apiResponse = RestAPI.spInsert(apiURL, json);
			System.out.println("spReceived : apiResponse : "+apiResponse);
			logger.info("spReceived : apiResponse : "+apiResponse);
			AirtelApiResponse airtelApi = gsonObj.fromJson(apiResponse, AirtelApiResponse.class);
			//System.out.println("Status : "+airtelApi.isStatus());
			//System.out.println("Messsage : "+airtelApi.getMessage());
			response.setStatus(airtelApi.isStatus());
			response.setMessage(airtelApi.getMessage());
			
			boolean status = airtelApi.isStatus();
			if(!status){
				String upSql = "UPDATE `Airtel_SR` set `STATUS` = '"+currentStatus+"' where `SR_Number` = '"+srNumber+"'";
				tviCommonDao.updateBulkdataValue(upSql);
			}
			
			Clob requestJson = new SerialClob(CommonFunction.printResponseJson(json).toCharArray());
			Clob responseJson = new SerialClob(CommonFunction.printResponseJson(apiResponse).toCharArray());
			saveResponseInTable(requestJson, responseJson, "spReceived");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private SpReceivedDto getSrDetailsForSendToAirtel(String srNumber) {
		SpReceivedDto dto = new SpReceivedDto();
		try {
			String isDieselGeneratorDGrequired = "",circle="",networkType="",spLati="",spLong="",
			Ass_AreyouWorkingInAnyBhartiGroup="",Ass_IfyesmentiontheBhartiUnitName="",
			Ass_NameOftheEmployee="",Ass_EmployeeId="",Rel_AnyRelativesareWorkingWithBhartiGroup="",
			Rel_IfyesmentiontheBhartiUnitName="",Rel_NameOftheEmployee="",Rel_EmployeeId="",
			Rel_LandlordRelationshipwithEmployee="",Rel_MobileNumberOfrelativeWithAirtel="",Declaration="";
			String sql = "SELECT `SP_Number`, `Customer_Site_Id`, `Customer_Site_Name`, `Site_Type`, `Project_Name`, "
					+ "`City`, `Other_Equipment`, `Is_Diesel_Generator_DG_required`, `TOCO_Site_Id`, date_format(`Date_of_Proposal`,'%d-%m-%Y') as `Date_of_Proposal`, "
					+ "`Power_Rating`, `Site_Electrification_Distance`, `Tentative_EB_Availibility`, `Additional_Charge`, "
					+ "`Address1`, `Head_Load_Charge`, `Electrification_Cost`, `Electrification_Line_Distance`, "
					+ "`Electricity_Connection_HT_LT`, `Infra_Details`, `Site_Classification`, "
					+ "`Expected_Rent_to_Landlord`, `Non_Refundable_Deposit`, `Estimated_Deployment_Time__in_days_`, "
					+ "`Additional_Capex`, `Standard_Rates`, `Fiber_Charges`, `Rental_Threshold`, "
					+ "`Excess_Rent_beyond_Threshold`, `Tentative_Rental_Premium`, `Additional_Rent`, `IPFee`, "
					+ "`Field_Operation_Charges`, `Security_Guard_Charges`, `Mobilization_Charges`, `Erection_Charges`, "
					+ "`Battery_backup_Hrs`, `Land_Lord_Charges_or_Rent_Charges`, `Wind_Speed`, `TowerHeight`, "
					+ "`Agl`, `Distance_from_P1_Lat_Long_in_meter`, `Circle`, `Request_for_Network_Type`, `Remarks`, "
					+ "`Preferred_Product_Type`, `Recommended_Product_Type_by_Acquisition`, `SP_Latitude`, "
					+ "`SP_Longitude`, `Association_AreyouWorkingInAnyBhartiGroup`, "
					+ "`Association_IfyesmentiontheBhartiUnitName`, `Association_NameOftheEmployee`, "
					+ "`Association_EmployeeId`, `Relative_AnyRelativesareWorkingWithBhartiGroup`, "
					+ "`Relative_IfyesmentiontheBhartiUnitName`, `Relative_NameOftheEmployee`, `Relative_EmployeeId`, "
					+ "`Relative_LandlordRelationshipwithEmployee`, `Relative_MobileNumberOfrelativeWithAirtel`, "
					+ "`Declaration` FROM `Airtel_SR` where `SR_Number` = '"+srNumber+"'";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			SpDetailsDto SPDetails_NN = new SpDetailsDto();
			for(Object [] dataObj : dataList){
				SPDetails_NN.setSP_Ref_No(emptyString(dataObj[0]));
				SPDetails_NN.setCustomerSiteId(emptyString(dataObj[1]));
				SPDetails_NN.setCustomerSiteName(emptyString(dataObj[2]));
				SPDetails_NN.setSiteType(emptyString(dataObj[3]));
				SPDetails_NN.setProject_Name(emptyString(dataObj[4]));
				SPDetails_NN.setCity(emptyString(dataObj[5]));	
				//emptyString(dataObj[6]);
				isDieselGeneratorDGrequired = emptyString(dataObj[7]);
				SPDetails_NN.setTOCO_Site_Id(emptyString(dataObj[8]));
				SPDetails_NN.setDate_of_Proposal(dataObj[9] == null ? "" : emptyString(dataObj[9]));
				SPDetails_NN.setPower_Rating(dataObj[10] == null ? "" : emptyString(dataObj[10]));
				SPDetails_NN.setSite_Electrification_Distance(dataObj[11] == null ? "" : emptyString(dataObj[11]));
				SPDetails_NN.setTentative_EB_Availibility(dataObj[12] == null ? "" : emptyString(dataObj[12]));
				SPDetails_NN.setAdditional_Charge(dataObj[13] == null ? "" : emptyString(dataObj[13]));
				SPDetails_NN.setAddress1(dataObj[14] == null ? "" : emptyString(dataObj[14]));
				SPDetails_NN.setHead_Load_Charge(dataObj[15] == null ? "" : emptyString(dataObj[15]));
				SPDetails_NN.setElectrification_Cost(dataObj[16] == null ? "" : emptyString(dataObj[16]));
				SPDetails_NN.setElectrification_Line_Distance(dataObj[17] == null ? "" : emptyString(dataObj[17]));
				SPDetails_NN.setElectricity_Connection_HT_LT(dataObj[18] == null ? "" : emptyString(dataObj[18]));
				SPDetails_NN.setInfra_Details(dataObj[19] == null ? "" : emptyString(dataObj[19]));
				SPDetails_NN.setSite_Classification(dataObj[20] == null ? "" : emptyString(dataObj[20]));
				SPDetails_NN.setExpected_Rent_to_Landlord(dataObj[21] == null ? "" : emptyString(dataObj[21]));
				SPDetails_NN.setNon_Refundable_Deposit(dataObj[22] == null ? "" : emptyString(dataObj[22]));
				SPDetails_NN.setEstimated_Deployment_Time__in_days_(dataObj[23] == null ? "" : emptyString(dataObj[23]));
				SPDetails_NN.setAdditional_Capex(dataObj[24] == null ? "" : emptyString(dataObj[24]));
				SPDetails_NN.setStandard_Rates(dataObj[25] == null ? "" : emptyString(dataObj[25]));
				SPDetails_NN.setFiber_Charges(dataObj[26] == null ? "" : emptyString(dataObj[26]));
				SPDetails_NN.setRental_Threshold(dataObj[27] == null ? "" : emptyString(dataObj[27]));
				SPDetails_NN.setExcess_Rent_beyond_Threshold(dataObj[28] == null ? "" : emptyString(dataObj[28]));
				SPDetails_NN.setTentative_Rental_Premium(dataObj[29] == null ? "" : emptyString(dataObj[29]));
				SPDetails_NN.setAdditional_Rent(dataObj[30] == null ? "" : emptyString(dataObj[30]));
				SPDetails_NN.setIPFee(dataObj[31] == null ? "" : emptyString(dataObj[31]));
				SPDetails_NN.setField_Operation_Charges(dataObj[32] == null ? "" : emptyString(dataObj[32]));
				SPDetails_NN.setSecurity_Guard_Charges(dataObj[33] == null ? "" : emptyString(dataObj[33]));
				SPDetails_NN.setMobilization_Charges(dataObj[34] == null ? "" : emptyString(dataObj[34]));
				SPDetails_NN.setErection_Charges(dataObj[35] == null ? "" : emptyString(dataObj[35]));
				SPDetails_NN.setBattery_backup_Hrs(dataObj[36] == null ? "" : emptyString(dataObj[36]));
				SPDetails_NN.setLand_Lord_Charges_or_Rent_Charges(dataObj[37] == null ? "" : emptyString(dataObj[37]));
				SPDetails_NN.setWind_Speed(dataObj[38] == null ? "" : emptyString(dataObj[38]));
				SPDetails_NN.setTowerHeight(dataObj[39] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[39])));
				SPDetails_NN.setAgl(dataObj[40] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[40])));
				SPDetails_NN.setDistance_from_P1_Lat_Long_in_meter(dataObj[41] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[41])));
				circle = emptyString(dataObj[42]);
				networkType = emptyString(dataObj[43]);
				SPDetails_NN.setRemarks(dataObj[44] == null ? "" : emptyString(dataObj[44]));
				SPDetails_NN.setPreferred_Product_Type_by_Customer(dataObj[45] == null ? "" : emptyString(dataObj[45]));
				SPDetails_NN.setRecommended_Product_Type_by_Acquisition(dataObj[46] == null ? "" : emptyString(dataObj[46]));
				spLati = dataObj[47] == null ? "" : emptyString(dataObj[47]);
				SPDetails_NN.setLatitude(spLati);
				spLong = dataObj[48] == null ? "" : emptyString(dataObj[48]);
				SPDetails_NN.setLongitude(spLong);
				
				Ass_AreyouWorkingInAnyBhartiGroup = dataObj[49] == null ? "" : emptyString(dataObj[49]);
				Ass_IfyesmentiontheBhartiUnitName = dataObj[50] == null ? "" : emptyString(dataObj[50]);
				Ass_NameOftheEmployee = dataObj[51] == null ? "" : emptyString(dataObj[51]);
				Ass_EmployeeId = dataObj[52] == null ? "" : emptyString(dataObj[52]);
				Rel_AnyRelativesareWorkingWithBhartiGroup = dataObj[53] == null ? "" : emptyString(dataObj[53]);
				Rel_IfyesmentiontheBhartiUnitName = dataObj[54] == null ? "" : emptyString(dataObj[54]);
				Rel_NameOftheEmployee = dataObj[55] == null ? "" : emptyString(dataObj[55]);
				Rel_EmployeeId = dataObj[56] == null ? "" : emptyString(dataObj[56]);
				Rel_LandlordRelationshipwithEmployee = dataObj[57] == null ? "" : emptyString(dataObj[57]);
				Rel_MobileNumberOfrelativeWithAirtel = dataObj[58] == null ? "" : emptyString(dataObj[58]);
				Declaration = dataObj[59] == null ? "" : emptyString(dataObj[59]);
			}
			sql = "SELECT `P1_Latitude`, `P1_Longitude`, `P1_Tower_Type` FROM `Airtel_Priority_Details` "
					+ "where `SR_Number` = '"+srNumber+"'";
			dataList = tviCommonDao.getAllTableData(sql);
			for(Object [] dataObj : dataList){
				//SPDetails_NN.setLatitude(emptyString(dataObj[0]));
				//SPDetails_NN.setLongitude(emptyString(dataObj[1]));
				SPDetails_NN.setTowerType(emptyString(dataObj[2]));
			}
			dto.setSPDetails_NN(SPDetails_NN);
			
			List<RadioAntennaDto> Radio_Antenna = new ArrayList<RadioAntennaDto>();
			sql = "SELECT `Feasibility`, `RadioAntenna_i_WAN`, `RadioAntenna_Type`, `Height_AGL_m`, "
					+ "`Length_m`, `Width_m`, `Depth_m`, `No_of_Ports`, `Azimuth_Degree`, "
					+ "`BandFrequencyMHz_FrequencyCombination` FROM `Airtel_Radio_Antenna` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			RadioAntennaDto radioAnt = null;
			for(Object [] dataObj : dataList){
				radioAnt = new RadioAntennaDto();
				radioAnt.setCustomer_Punched_Or_Planning("Planning");
				radioAnt.setSector_Details("Sector 1");
				radioAnt.setFeasibility(emptyString(dataObj[0]));
				radioAnt.setRadioAntenna_i_WAN(emptyString(dataObj[1]));
				radioAnt.setRadioAntenna_Type(emptyString(dataObj[2]));
				radioAnt.setHeight_AGL_m(dataObj[3] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[3])));
				radioAnt.setLength_m(dataObj[4] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[4])));
				radioAnt.setWidth_m(dataObj[5] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[5])));
				radioAnt.setDepth_m(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				radioAnt.setNo_of_Ports(emptyString(dataObj[7]));
				radioAnt.setAzimuth_Degree(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				String freq = emptyString(dataObj[9]);
				String freqSplit [] = freq.split(",");
				BandFrequencyMHzDto bandFreq = new BandFrequencyMHzDto();
				for(int i=0;i<freqSplit.length;i++){
					if(i == 0)
						bandFreq.setFrequency_Number_1(Double.parseDouble(freqSplit[i]));
					if(i == 1)
						bandFreq.setFrequency_Number_2(Double.parseDouble(freqSplit[i]));
					if(i == 2)
						bandFreq.setFrequency_Number_3(Double.parseDouble(freqSplit[i]));
					if(i == 3)
						bandFreq.setFrequency_Number_4(Double.parseDouble(freqSplit[i]));
					if(i == 4)
						bandFreq.setFrequency_Number_5(Double.parseDouble(freqSplit[i]));
					if(i == 5)
						bandFreq.setFrequency_Number_6(Double.parseDouble(freqSplit[i]));
					if(i == 6)
						bandFreq.setFrequency_Number_7(Double.parseDouble(freqSplit[i]));
					if(i == 7)
						bandFreq.setFrequency_Number_8(Double.parseDouble(freqSplit[i]));
				}
				radioAnt.setBand_Frequency_MHz_Frequency_Combination(bandFreq);
				Radio_Antenna.add(radioAnt);
			}
			dto.setRadio_Antenna(Radio_Antenna);
			
			List<MwAntennaDto> MW = new ArrayList<MwAntennaDto>();
			sql = "SELECT `Feasibility`, `Height_in_Mtrs`, `MWAntenna_i_WAN`, `Size_of_MW`, `Azimuth_Degree` "
					+ " FROM `Airtel_MW` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			MwAntennaDto mwObj = null;
			for(Object [] dataObj : dataList){
				mwObj = new MwAntennaDto();
				mwObj.setCustomer_Punched_Or_Planning("Planning");
				mwObj.setSector_Details("Sector 1");
				mwObj.setFeasibility(emptyString(dataObj[0]));
				mwObj.setHeight_in_Mtrs(dataObj[1] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[1])));
				mwObj.setMWAntenna_i_WAN(emptyString(dataObj[2]));
				mwObj.setSize_of_MW(dataObj[3] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[3])));
				mwObj.setAzimuth_Degree(dataObj[4] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[4])));
				MW.add(mwObj);
			}
			dto.setMW(MW);
			
			List<BtsCabinetDto> BTS = new ArrayList<BtsCabinetDto>();
			sql = "SELECT `Feasibility`, `NetWork_Type`, `BTS_Type`, `Band`, `Manufacturer`, `Make_of_BTS`, "
					+ "`Length_Mtrs`, `Width_Mtrs`, `Height_Mtrs`, `BTS_Power_Rating_KW`, `BTS_Location`, "
					+ "`BTS_Voltage`, `Main_Unit_incase_of_TT_Split_Version`, "
					+ "`Space_Occupied_in_Us_incase_of_TT_Split_Version`, "
					+ "`RRU_Unit`, `No_of_RRU_Units_incase_of_TT_Split_Version`, "
					+ "`Combined_wt_of_RRU_Unit_incase_of_TT_Split_Version`, `AGL_of_RRU_unit_in_M`, "
					+ "`Weight_of_BTS_including_TMA_TMB_Kg`, `Billable_Weigtht` "
					+ "FROM `Airtel_BTS` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			BtsCabinetDto btsObj = null;
			for(Object [] dataObj : dataList){
				btsObj = new BtsCabinetDto();
				btsObj.setCustomer_Punched_Or_Planning("Planning");
				btsObj.setFeasibility(emptyString(dataObj[0]));
				btsObj.setNetWork_Type(emptyString(dataObj[1]));
				btsObj.setBTS_Type(emptyString(dataObj[2]));
				btsObj.setBand(emptyString(dataObj[3]));
				btsObj.setManufacturer(emptyString(dataObj[4]));
				btsObj.setMake_of_BTS(emptyString(dataObj[5]));
				btsObj.setLength_Mtrs(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				btsObj.setWidth_Mtrs(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				btsObj.setHeight_Mtrs(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				btsObj.setBTS_Power_Rating_KW(dataObj[9] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[9])));
				btsObj.setBTS_Location(emptyString(dataObj[10]));
				btsObj.setBTS_Voltage(emptyString(dataObj[11]));
				btsObj.setMain_Unit_in_case_of_TT_Split_Version(emptyString(dataObj[12]));
				btsObj.setSpace_Occupied_in_Us_in_case_of_TT_Split_Version(emptyString(dataObj[13]));
				btsObj.setLocation_Of_RRU(emptyString(dataObj[14]));
				btsObj.setNo_Of_RRU_Units_in_case_of_TT_Split_Version(dataObj[15] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[15])));
				btsObj.setCombined_wt_Of_RRU_Unit_in_case_of_TT_Split_Version(dataObj[16] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[16])));
				btsObj.setAGL_of_RRU_unit_in_M(dataObj[17] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[17])));
				btsObj.setWeight_Of_BTS_including_TMA_TMB_Kg(dataObj[18] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[18])));
				btsObj.setBillable_Weight_in_Kg(dataObj[19] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[19])));
				BTS.add(btsObj);
			}
			dto.setBTS(BTS);
			
			List<OtherNodeDto> Other_Node = new ArrayList<OtherNodeDto>();
			sql = "SELECT `Feasibility`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, `Node_Model`, "
					+ "`Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, `Weight_Kg`, `Node_Voltage`, "
					+ "`Power_Rating_in_Kw`, `FullRack`, `Tx_Rack_Space_Required_In_Us`, `Remarks` "
					+ "FROM `Airtel_Other_Node` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			OtherNodeDto otherObj = null;
			for(Object [] dataObj : dataList){
				otherObj = new OtherNodeDto();
				otherObj.setCustomer_Punched_Or_Planning("Planning");
				otherObj.setFeasibility(emptyString(dataObj[0]));
				otherObj.setNode_Type(emptyString(dataObj[1]));
				otherObj.setNode_Location(emptyString(dataObj[2]));
				otherObj.setNode_Manufacturer(emptyString(dataObj[3]));
				otherObj.setNode_Model(emptyString(dataObj[4]));
				otherObj.setLength_Mtrs(dataObj[5] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[5])));
				otherObj.setBreadth_Mtrs(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				otherObj.setHeight_Mtrs(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				otherObj.setWeight_Kg(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				otherObj.setNode_Voltage(emptyString(dataObj[9]));
				otherObj.setPower_Rating_in_Kw(dataObj[10] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[10])));
				otherObj.setFullRack(emptyString(dataObj[11]));
				otherObj.setTx_Rack_Space_Required_In_Us(dataObj[12] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[12])));
				otherObj.setRemarks(emptyString(dataObj[13]));
				Other_Node.add(otherObj);
			}
			dto.setOther_Node(Other_Node);
			
			List<BscRncCabinetsDto> BSC_RNC_Cabinets = new ArrayList<BscRncCabinetsDto>();
			sql = "SELECT `Feasibility`, `NetWork_Type`, `BSC_RNC_Type`, `BSC_RNC_Manufacturer`, `BSC_RNC_Make`, "
					+ "`Length_Mtrs`, `Breadth_Mtrs`, `Height_AGL`, `BSC_RNC_Power_Rating` "
					+ "FROM `Airtel_BSC_RNC_Cabinets` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			BscRncCabinetsDto bscRncObj = null;
			for(Object [] dataObj : dataList){
				bscRncObj = new BscRncCabinetsDto();
				bscRncObj.setCustomer_Punched_Or_Planning("Planning");
				bscRncObj.setFeasibility(emptyString(dataObj[0]));
				bscRncObj.setNetWork_Type(emptyString(dataObj[1]));
				bscRncObj.setBSC_RNC_Type(emptyString(dataObj[2]));
				bscRncObj.setBSC_RNC_Manufacturer(emptyString(dataObj[3]));
				bscRncObj.setBSC_RNC_Make(emptyString(dataObj[4]));
				bscRncObj.setLength_Mtrs(dataObj[5] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[5])));
				bscRncObj.setBreadth_Mtrs(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				bscRncObj.setHeight_AGL(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				bscRncObj.setBSC_RNC_Power_Rating(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				BSC_RNC_Cabinets.add(bscRncObj);
			}
			dto.setBSC_RNC_Cabinets(BSC_RNC_Cabinets);
			
			List<McbDto> MCB = new ArrayList<McbDto>();
			sql = "SELECT `Feasibility`, `Total_No_of_MCB_Required`, `_06A`, `_10A`, `_16A`, `_24A`, `_32A`, "
					+ "`_40A`, `_63A`, `_80A` FROM `Airtel_MCB` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			McbDto mcbDto = null;
			for(Object [] dataObj : dataList){
				mcbDto = new McbDto();
				mcbDto.setCustomer_Punched_Or_Planning("Planning");
				//mcbDto.setFeasibility(emptyString(dataObj[0]));
				mcbDto.setTotal_No_of_MCB_Required(Double.parseDouble(emptyNumeric(dataObj[1])));
				mcbDto.set_06A(dataObj[2] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[2])));
				mcbDto.set_10A(dataObj[3] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[3])));
				mcbDto.set_16A(dataObj[4] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[4])));
				mcbDto.set_24A(dataObj[5] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[5])));
				mcbDto.set_32A(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				mcbDto.set_40A(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				mcbDto.set_63A(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				mcbDto.set_80A(dataObj[9] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[9])));
				MCB.add(mcbDto);
			}
			dto.setMCB(MCB);
			
			List<FibreNodeDto> Fiber_Node = new ArrayList<FibreNodeDto>();
			sql = "SELECT `Feasibility`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, `Node_Model`, "
					+ "`Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, `Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, "
					+ "`FullRack`, `Tx_Rack_Space_required_in_Us`, `Is_Right_Of_Way_ROW_Required_Inside_The_Indus_Premises`, "
					+ "`Type_Of_Fiber_Laying`, `Type_Of_FMS`, `Remarks`, `Full_Rack` FROM `Airtel_Fibre_Node` "
					+ "where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			FibreNodeDto fiberObj = null;
			for(Object [] dataObj : dataList){
				fiberObj = new FibreNodeDto();
				fiberObj.setCustomer_Punched_Or_Planning("Planning");
				fiberObj.setFeasibility(emptyString(dataObj[0]));
				fiberObj.setNode_Type(emptyString(dataObj[1]));
				fiberObj.setNode_Location(emptyString(dataObj[2]));
				fiberObj.setNode_Manufacturer(emptyString(dataObj[3]));
				fiberObj.setNode_Model(emptyString(dataObj[4]));
				fiberObj.setLength_Mtrs(dataObj[5] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[5])));
				fiberObj.setBreadth_Mtrs(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				fiberObj.setHeight_mtrs(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				fiberObj.setWeight(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				fiberObj.setNode_Voltage(emptyString(dataObj[9]));
				fiberObj.setPower_Rating_in_Kw(dataObj[10] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[10])));
				fiberObj.setFullRack(emptyString(dataObj[11]));
				fiberObj.setTx_Rack_space_required_in_Us(dataObj[12] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[12])));
				fiberObj.setIs_Right_of_Way_ROW_required_inside_the_TOCO_premises(emptyString(dataObj[13]));
				fiberObj.setType_of_Fiber_Laying(emptyString(dataObj[14]));
				fiberObj.setType_of_FMS(emptyString(dataObj[15]));
				fiberObj.setRemarks(emptyString(dataObj[16]));
				fiberObj.setFull_Rack(emptyString(dataObj[17]));
				Fiber_Node.add(fiberObj);
			}
			dto.setFiber_Node(Fiber_Node);
			
			GlobalDto Global = new GlobalDto();
			Global.setRequest_Ref_No(srNumber);
			Global.setCircle(circle);
			Global.setNetwork_Type(networkType);
			dto.setGlobal(Global);
			
//			OtherEquipmentDto Other_Equipment = new OtherEquipmentDto();
//			Other_Equipment.setOther_Equipment(otherEquipment);
//			dto.setOther_Equipment(Other_Equipment);
			
			List<OtherEquipmentDto> Other_Equipment = new ArrayList<>();
			sql = "SELECT `Feasibility`, `Source_Request_RefNo`, `Other_Equipment_Category`, "
					+ "`Other_Equipment_Type`, `Equipment_to_be_relocated`, `Target_Indus_Site_Id`, "
					+ "`Target_Request_RefNo`, `CustomerPunchedOrPlanning`, `Deletion_OR_Relocation` "
					+ "FROM `Airtel_Other_Equipment` WHERE `SR_Number`='"+srNumber+"' and `InsertType`='SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			OtherEquipmentDto otEqObj = null;
			for(Object [] dataObj : dataList){
				otEqObj = new OtherEquipmentDto();
				otEqObj.setFeasibility(emptyString(dataObj[0]));
				otEqObj.setSource_Request_RefNo(emptyString(dataObj[1]));
				otEqObj.setOther_Equipment_Category(emptyString(dataObj[2]));
				otEqObj.setOther_Equipment_Type(emptyString(dataObj[3]));
				otEqObj.setEquipment_to_be_relocated(emptyString(dataObj[4]));
				otEqObj.setTarget_Indus_Site_Id(emptyString(dataObj[5]));
				otEqObj.setTarget_Request_RefNo(emptyString(dataObj[6]));
				otEqObj.setCustomerPunchedOrPlanning(emptyString(dataObj[7]));
				otEqObj.setDeletion_OR_Relocation(emptyString(dataObj[8]));
				Other_Equipment.add(otEqObj);
			}
			dto.setOther_Equipment(Other_Equipment);
			
			StrageticDto Strategic_Conversion = new StrageticDto();
			sql = "SELECT `Is_it_Strategic`, `Shelter_Size`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_AGL_Mtrs`, "
					+ "`DG_Redundancy`, `Extra_Battery_Bank_Requirement`, `Extra_Battery_BackUp`, "
					+ "`Anyother_Specific_Requirements` "
					+ "FROM `Airtel_Additional_Information` where `SR_Number` = '"+srNumber+"'";
			dataList = tviCommonDao.getAllTableData(sql);
			for(Object [] dataObj : dataList){
				Strategic_Conversion.setIs_it_Strategic(emptyString(dataObj[0]));
				Strategic_Conversion.setShelter_Size(emptyString(dataObj[1]));
				Strategic_Conversion.setLength_Mtrs(dataObj[2] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[2])));
				Strategic_Conversion.setBreadth_Mtrs(dataObj[3] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[3])));
				Strategic_Conversion.setHeight_AGL_Mtrs(dataObj[4] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[4])));
				Strategic_Conversion.setDG_Redundancy(emptyString(dataObj[5]));
				Strategic_Conversion.setExtra_Battery_Bank_Requirement(emptyString(dataObj[6]));
				Strategic_Conversion.setExtra_Battery_Back_Up(emptyString(dataObj[7]));
				Strategic_Conversion.setAny_other_specific_requirements(emptyString(dataObj[8]));
			}
			dto.setStrategic_Conversion(Strategic_Conversion);
			
			DgDto DG = new DgDto();
			DG.setIs_Diesel_Generator_DG_required(isDieselGeneratorDGrequired);
			dto.setDG(DG);
			
			dto.setAssociation_AreyouWorkingInAnyBhartiGroup(Ass_AreyouWorkingInAnyBhartiGroup);
			dto.setAssociation_IfyesmentiontheBhartiUnitName(Ass_IfyesmentiontheBhartiUnitName);
			dto.setAssociation_NameOftheEmployee(Ass_NameOftheEmployee);
			dto.setAssociation_EmployeeId(Ass_EmployeeId);
			dto.setRelative_AnyRelativesareWorkingWithBhartiGroup(Rel_AnyRelativesareWorkingWithBhartiGroup);
			dto.setRelative_IfyesmentiontheBhartiUnitName(Rel_IfyesmentiontheBhartiUnitName);
			dto.setRelative_NameOftheEmployee(Rel_NameOftheEmployee);
			dto.setRelative_EmployeeId(Rel_EmployeeId);
			dto.setRelative_LandlordRelationshipwithEmployee(Rel_LandlordRelationshipwithEmployee);
			dto.setRelative_MobileNumberOfrelativeWithAirtel(Rel_MobileNumberOfrelativeWithAirtel);
			dto.setDeclaration(Declaration);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	private SpReceivedResponse spReceivedSharing(String srNumber, String currentStatus) {
		SpReceivedResponse response = new SpReceivedResponse();
		try {
			SpReceivedSharingDto jsonData = getSharingSrDetailsForSendToAirtel(srNumber);
			Gson gsonObj = new Gson();
			String json = gsonObj.toJson(jsonData);
			System.out.println("spReceivedSharing : JSON : "+json);
			logger.info("spReceivedSharing : JSON : "+json);
			String apiURL = "https://ideploy.airtel.com/iDeployAPI/api/common/sp_insert";
			String apiResponse = RestAPI.spInsert(apiURL, json);
			System.out.println("spReceivedSharing : apiResponse : "+apiResponse);
			logger.info("spReceivedSharing : apiResponse : "+apiResponse);
			AirtelApiResponse airtelApi = gsonObj.fromJson(apiResponse, AirtelApiResponse.class);
			//System.out.println("Status : "+airtelApi.isStatus());
			//System.out.println("Messsage : "+airtelApi.getMessage());
			response.setStatus(airtelApi.isStatus());
			response.setMessage(airtelApi.getMessage());
			
			boolean status = airtelApi.isStatus();
			if(!status){
				String upSql = "UPDATE `Airtel_SR` set `STATUS` = '"+currentStatus+"' where `SR_Number` = '"+srNumber+"'";
				tviCommonDao.updateBulkdataValue(upSql);
			}
			
			Clob requestJson = new SerialClob(CommonFunction.printResponseJson(json).toCharArray());
			Clob responseJson = new SerialClob(CommonFunction.printResponseJson(apiResponse).toCharArray());
			saveResponseInTable(requestJson, responseJson, "spReceivedSharing");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private SpReceivedSharingDto getSharingSrDetailsForSendToAirtel(String srNumber) {
		SpReceivedSharingDto dto = new SpReceivedSharingDto();
		try {
			String isDieselGeneratorDGrequired = "",circle="",networkType="",
			Ass_AreyouWorkingInAnyBhartiGroup="",Ass_IfyesmentiontheBhartiUnitName="",
			Ass_NameOftheEmployee="",Ass_EmployeeId="",Rel_AnyRelativesareWorkingWithBhartiGroup="",
			Rel_IfyesmentiontheBhartiUnitName="",Rel_NameOftheEmployee="",Rel_EmployeeId="",
			Rel_LandlordRelationshipwithEmployee="",Rel_MobileNumberOfrelativeWithAirtel="",Declaration="";
			String sql = "SELECT `SP_Number`, `Customer_Site_Id`, `Customer_Site_Name`, `Site_Type`, `Project_Name`, "
					+ "`City`, `Other_Equipment`, `Is_Diesel_Generator_DG_required`, `TOCO_Site_Id`, "
					+ "date_format(`Date_of_Proposal`,'%d-%m-%Y') as `Date_of_Proposal`, `Power_Rating`, `Site_Electrification_Distance`, `Tentative_EB_Availibility`, "
					+ "`Additional_Charge`, `Head_Load_Charge`, `Electrification_Cost`, `Electrification_Line_Distance`, "
					+ "`Electricity_Connection_HT_LT`, `Infra_Details`, `Site_Classification`, `Expected_Rent_to_Landlord`, "
					+ "`Non_Refundable_Deposit`, `Estimated_Deployment_Time__in_days_`, `Additional_Capex`, `Standard_Rates`, "
					+ "`Fiber_Charges`, `Rental_Threshold`, `Excess_Rent_beyond_Threshold`, `Tentative_Rental_Premium`, "
					+ "`Additional_Rent`, `IPFee`, `Field_Operation_Charges`, `Security_Guard_Charges`, `Mobilization_Charges`, "
					+ "`Erection_Charges`, `Battery_backup_Hrs`, `Land_Lord_Charges_or_Rent_Charges`, `Wind_Speed`, `TowerHeight`, "
					+ "`Agl`, `Distance_from_P1_Lat_Long_in_meter`, `Rejection_Remarks`, `Circle`, `Request_for_Network_Type`, `Remarks`, "
					+ "`Association_AreyouWorkingInAnyBhartiGroup`, "
					+ "`Association_IfyesmentiontheBhartiUnitName`, `Association_NameOftheEmployee`, "
					+ "`Association_EmployeeId`, `Relative_AnyRelativesareWorkingWithBhartiGroup`, "
					+ "`Relative_IfyesmentiontheBhartiUnitName`, `Relative_NameOftheEmployee`, `Relative_EmployeeId`, "
					+ "`Relative_LandlordRelationshipwithEmployee`, `Relative_MobileNumberOfrelativeWithAirtel`, "
					+ "`Declaration` "
					+ "FROM `Airtel_SR` where `SR_Number` = '"+srNumber+"'";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.sharing.dto.SpDetailsDto SPDetails_NN = new com.tvi.sharing.dto.SpDetailsDto();
			for(Object [] dataObj : dataList){
				SPDetails_NN.setSP_Ref_No(emptyString(dataObj[0]));
				SPDetails_NN.setCustomerSiteId(emptyString(dataObj[1]));
				SPDetails_NN.setCustomerSiteName(emptyString(dataObj[2]));
				SPDetails_NN.setSiteType(emptyString(dataObj[3]));
				SPDetails_NN.setProject_Name(emptyString(dataObj[4]));
				SPDetails_NN.setCity(emptyString(dataObj[5]));	
//				emptyString(dataObj[6]);
				isDieselGeneratorDGrequired = emptyString(dataObj[7]);
				SPDetails_NN.setTOCO_Site_Id(emptyString(dataObj[8]));
				SPDetails_NN.setDate_of_Proposal(dataObj[9] == null ? "" : emptyString(dataObj[9]));
				SPDetails_NN.setPower_Rating(dataObj[10] == null ? "" : emptyString(dataObj[10]));
				SPDetails_NN.setSite_Electrification_Distance(dataObj[11] == null ? "" : emptyString(dataObj[11]));
				SPDetails_NN.setTentative_EB_Availibility(dataObj[12] == null ? "" : emptyString(dataObj[12]));
				SPDetails_NN.setAdditional_Charge(dataObj[13] == null ? "" : emptyString(dataObj[13]));
				SPDetails_NN.setHead_Load_Charge(dataObj[14] == null ? "" : emptyString(dataObj[14]));
				SPDetails_NN.setElectrification_Cost(dataObj[15] == null ? "" : emptyString(dataObj[15]));
				SPDetails_NN.setElectrification_Line_Distance(dataObj[16] == null ? "" : emptyString(dataObj[16]));
				SPDetails_NN.setElectricity_Connection_HT_LT(dataObj[17] == null ? "" : emptyString(dataObj[17]));
				SPDetails_NN.setInfra_Details(dataObj[18] == null ? "" : emptyString(dataObj[18]));
				SPDetails_NN.setSite_Classification(dataObj[19] == null ? "" : emptyString(dataObj[19]));
				SPDetails_NN.setExpected_Rent_to_Landlord(dataObj[20] == null ? "" : emptyString(dataObj[20]));
				SPDetails_NN.setNon_Refundable_Deposit(dataObj[21] == null ? "" : emptyString(dataObj[21]));
				SPDetails_NN.setEstimated_Deployment_Time__in_days_(dataObj[22] == null ? "" : emptyString(dataObj[22]));
				SPDetails_NN.setAdditional_Capex(emptyString(dataObj[23] == null ? "" : dataObj[23]));
				SPDetails_NN.setStandard_Rates(emptyString(dataObj[24] == null ? "" : dataObj[24]));
				SPDetails_NN.setFiber_Charges(emptyString(dataObj[25] == null ? "" : dataObj[25]));
				SPDetails_NN.setRental_Threshold(dataObj[26] == null ? "" : emptyString(dataObj[26]));
				SPDetails_NN.setExcess_Rent_beyond_Threshold(dataObj[27] == null ? "" : emptyString(dataObj[27]));
				SPDetails_NN.setTentative_Rental_Premium(dataObj[28] == null ? "" : emptyString(dataObj[28]));
				SPDetails_NN.setAdditional_Rent(dataObj[29] == null ? "" : emptyString(dataObj[29]));
				SPDetails_NN.setIPFee(dataObj[30] == null ? "" : emptyString(dataObj[30]));
				SPDetails_NN.setField_Operation_Charges(dataObj[31] == null ? "" : emptyString(dataObj[31]));
				SPDetails_NN.setSecurity_Guard_Charges(dataObj[32] == null ? "" : emptyString(dataObj[32]));
				SPDetails_NN.setMobilization_Charges(dataObj[33] == null ? "" : emptyString(dataObj[33]));
				SPDetails_NN.setErection_Charges(dataObj[34] == null ? "" : emptyString(dataObj[34]));
				SPDetails_NN.setBattery_backup_Hrs(dataObj[35] == null ? "" : emptyString(dataObj[35]));
				SPDetails_NN.setLand_Lord_Charges_or_Rent_Charges(dataObj[36] == null ? "" : emptyString(dataObj[36]));
				SPDetails_NN.setWind_Speed(dataObj[37] == null ? "" : emptyString(dataObj[37]));
				SPDetails_NN.setTowerHeight(dataObj[38] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[38])));
				SPDetails_NN.setAgl(dataObj[39] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[39])));
				SPDetails_NN.setDistance_from_P1_Lat_Long_in_meter(dataObj[40] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[40])));
				SPDetails_NN.setRejection_Remarks(dataObj[41] == null ? "" : emptyString(dataObj[41]));
				circle = dataObj[42] == null ? "" : emptyString(dataObj[42]);
				networkType = dataObj[43] == null ? "" : emptyString(dataObj[43]);
				SPDetails_NN.setRemarks(emptyString(dataObj[44]));
				
				Ass_AreyouWorkingInAnyBhartiGroup = dataObj[45] == null ? "" : emptyString(dataObj[45]);
				Ass_IfyesmentiontheBhartiUnitName = dataObj[46] == null ? "" : emptyString(dataObj[46]);
				Ass_NameOftheEmployee = dataObj[47] == null ? "" : emptyString(dataObj[47]);
				Ass_EmployeeId = dataObj[48] == null ? "" : emptyString(dataObj[48]);
				Rel_AnyRelativesareWorkingWithBhartiGroup = dataObj[49] == null ? "" : emptyString(dataObj[49]);
				Rel_IfyesmentiontheBhartiUnitName = dataObj[50] == null ? "" : emptyString(dataObj[50]);
				Rel_NameOftheEmployee = dataObj[51] == null ? "" : emptyString(dataObj[51]);
				Rel_EmployeeId = dataObj[52] == null ? "" : emptyString(dataObj[52]);
				Rel_LandlordRelationshipwithEmployee = dataObj[53] == null ? "" : emptyString(dataObj[53]);
				Rel_MobileNumberOfrelativeWithAirtel = dataObj[54] == null ? "" : emptyString(dataObj[54]);
				Declaration = dataObj[55] == null ? "" : emptyString(dataObj[55]);
			}
			sql = "SELECT `P1_Latitude`, `P1_Longitude`, `P1_Tower_Type` FROM `Airtel_Priority_Details` "
					+ "where `SR_Number` = '"+srNumber+"'";
			dataList = tviCommonDao.getAllTableData(sql);
			for(Object [] dataObj : dataList){
				SPDetails_NN.setLatitude(emptyString(dataObj[0]));
				SPDetails_NN.setLongitude(emptyString(dataObj[1]));
				SPDetails_NN.setTowerType(emptyString(dataObj[2]));
			}
			dto.setSPDetails_RES(SPDetails_NN);
			
			List<com.tvi.sharing.dto.RadioAntennaDto> Radio_Antenna = new ArrayList<com.tvi.sharing.dto.RadioAntennaDto>();
			sql = "SELECT `Feasibility`, `RadioAntenna_i_WAN`, `RadioAntenna_Type`, `Height_AGL_m`, "
					+ "`Length_m`, `Width_m`, `Depth_m`, `No_of_Ports`, `Azimuth_Degree`, "
					+ "`BandFrequencyMHz_FrequencyCombination` FROM `Airtel_Radio_Antenna` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.sharing.dto.RadioAntennaDto radioAnt = null;
			for(Object [] dataObj : dataList){
				radioAnt = new com.tvi.sharing.dto.RadioAntennaDto();
				radioAnt.setFeasibility(emptyString(dataObj[0]));
				radioAnt.setRadioAntenna_i_WAN(emptyString(dataObj[1]));
				radioAnt.setRadioAntenna_Type(emptyString(dataObj[2]));
				radioAnt.setHeight_AGL_m(dataObj[3] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[3])));
				radioAnt.setLength_m(dataObj[4] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[4])));
				radioAnt.setWidth_m(dataObj[5] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[5])));
				radioAnt.setDepth_m(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				radioAnt.setNo_of_Ports(emptyString(dataObj[7]));
				radioAnt.setAzimuth_Degree(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				String freq = emptyString(dataObj[9]);
				String freqSplit [] = freq.split(",");
				com.tvi.sharing.dto.BandFrequencyMHzDto bandFreq = new com.tvi.sharing.dto.BandFrequencyMHzDto();
				for(int i=0;i<freqSplit.length;i++){
					if(i == 0)
						bandFreq.setFrequency_Number_1(Double.parseDouble(freqSplit[i]));
					if(i == 1)
						bandFreq.setFrequency_Number_2(Double.parseDouble(freqSplit[i]));
					if(i == 2)
						bandFreq.setFrequency_Number_3(Double.parseDouble(freqSplit[i]));
					if(i == 3)
						bandFreq.setFrequency_Number_4(Double.parseDouble(freqSplit[i]));
					if(i == 4)
						bandFreq.setFrequency_Number_5(Double.parseDouble(freqSplit[i]));
					if(i == 5)
						bandFreq.setFrequency_Number_6(Double.parseDouble(freqSplit[i]));
					if(i == 6)
						bandFreq.setFrequency_Number_7(Double.parseDouble(freqSplit[i]));
					if(i == 7)
						bandFreq.setFrequency_Number_8(Double.parseDouble(freqSplit[i]));
				}
				radioAnt.setBand_Frequency_MHz_Frequency_Combination(bandFreq);
				Radio_Antenna.add(radioAnt);
			}
			dto.setRadio_Antenna(Radio_Antenna);
			
			List<com.tvi.sharing.dto.MwAntennaDto> MW = new ArrayList<com.tvi.sharing.dto.MwAntennaDto>();
			sql = "SELECT `Feasibility`, `Height_in_Mtrs`, `MWAntenna_i_WAN`, `Size_of_MW`, `Azimuth_Degree` "
					+ " FROM `Airtel_MW` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.sharing.dto.MwAntennaDto mwObj = null;
			for(Object [] dataObj : dataList){
				mwObj = new com.tvi.sharing.dto.MwAntennaDto();
				mwObj.setFeasibility(emptyString(dataObj[0]));
				mwObj.setHeight_in_Mtrs(dataObj[1] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[1])));
				mwObj.setMWAntenna_i_WAN(emptyString(dataObj[2]));
				mwObj.setSize_of_MW(dataObj[3] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[3])));
				mwObj.setAzimuth_Degree(dataObj[4] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[4])));
				MW.add(mwObj);
			}
			dto.setMW(MW);
			
			List<com.tvi.sharing.dto.BtsCabinetDto> BTS = new ArrayList<com.tvi.sharing.dto.BtsCabinetDto>();
			sql = "SELECT `Feasibility`, `NetWork_Type`, `BTS_Type`, `Band`, `Manufacturer`, `Make_of_BTS`, "
					+ "`Length_Mtrs`, `Width_Mtrs`, `Height_Mtrs`, `BTS_Power_Rating_KW`, `BTS_Location`, "
					+ "`BTS_Voltage`, `Main_Unit_incase_of_TT_Split_Version`, "
					+ "`Space_Occupied_in_Us_incase_of_TT_Split_Version`, "
					+ "`RRU_Unit`, `No_of_RRU_Units_incase_of_TT_Split_Version`, "
					+ "`Combined_wt_of_RRU_Unit_incase_of_TT_Split_Version`, `AGL_of_RRU_unit_in_M`, "
					+ "`Weight_of_BTS_including_TMA_TMB_Kg`, `Billable_Weigtht` "
					+ "FROM `Airtel_BTS` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.sharing.dto.BtsCabinetDto btsObj = null;
			for(Object [] dataObj : dataList){
				btsObj = new com.tvi.sharing.dto.BtsCabinetDto();
				btsObj.setFeasibility(emptyString(dataObj[0]));
				btsObj.setNetWork_Type(emptyString(dataObj[1]));
				btsObj.setBTS_Type(emptyString(dataObj[2]));
				btsObj.setBand(emptyString(dataObj[3]));
				btsObj.setManufacturer(emptyString(dataObj[4]));
				btsObj.setMake_of_BTS(emptyString(dataObj[5]));
				btsObj.setLength_Mtrs(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				btsObj.setWidth_Mtrs(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				btsObj.setHeight_Mtrs(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				btsObj.setBTS_Power_Rating_KW(dataObj[9] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[9])));
				btsObj.setBTS_Location(emptyString(dataObj[10]));
				btsObj.setBTS_Voltage(emptyString(dataObj[11]));
				btsObj.setMain_Unit_in_case_of_TT_Split_Version(emptyString(dataObj[12]));
				btsObj.setSpace_Occupied_in_Us_in_case_of_TT_Split_Version(dataObj[13] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[13])));
				btsObj.setLocation_Of_RRU(emptyString(dataObj[14]));
				btsObj.setNo_Of_RRU_Units_in_case_of_TT_Split_Version(dataObj[15] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[15])));
				btsObj.setCombined_wt_Of_RRU_Unit_in_case_of_TT_Split_Version(dataObj[16] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[16])));
				btsObj.setAGL_of_RRU_unit_in_M(dataObj[17] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[17])));
				btsObj.setWeight_Of_BTS_including_TMA_TMB_Kg(dataObj[18] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[18])));
				btsObj.setBillable_Weight_in_Kg(dataObj[19] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[19])));
				BTS.add(btsObj);
			}
			dto.setBTS(BTS);
			
			List<com.tvi.sharing.dto.OtherNodeDto> Other_Node = new ArrayList<com.tvi.sharing.dto.OtherNodeDto>();
			sql = "SELECT `Feasibility`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, `Node_Model`, "
					+ "`Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, `Weight_Kg`, `Node_Voltage`, "
					+ "`Power_Rating_in_Kw`, `FullRack`, `Tx_Rack_Space_Required_In_Us`, `Remarks` "
					+ "FROM `Airtel_Other_Node` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.sharing.dto.OtherNodeDto otherObj = null;
			for(Object [] dataObj : dataList){
				otherObj = new com.tvi.sharing.dto.OtherNodeDto();
				otherObj.setFeasibility(emptyString(dataObj[0]));
				otherObj.setNode_Type(emptyString(dataObj[1]));
				otherObj.setNode_Location(emptyString(dataObj[2]));
				otherObj.setNode_Manufacturer(emptyString(dataObj[3]));
				otherObj.setNode_Model(emptyString(dataObj[4]));
				otherObj.setLength_Mtrs(dataObj[5] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[5])));
				otherObj.setBreadth_Mtrs(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				otherObj.setHeight_Mtrs(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				otherObj.setWeight_Kg(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				otherObj.setNode_Voltage(emptyString(dataObj[9]));
				otherObj.setPower_Rating_in_Kw(dataObj[10] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[10])));
				otherObj.setFullRack(emptyString(dataObj[11]));
				otherObj.setTx_Rack_Space_Required_In_Us(dataObj[12] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[12])));
				otherObj.setRemarks(emptyString(dataObj[13]));
				Other_Node.add(otherObj);
			}
			dto.setOther_Node(Other_Node);
			
			List<com.tvi.sharing.dto.BscRncCabinetsDto> BSC_RNC_Cabinets = new ArrayList<com.tvi.sharing.dto.BscRncCabinetsDto>();
			sql = "SELECT `Feasibility`, `NetWork_Type`, `BSC_RNC_Type`, `BSC_RNC_Manufacturer`, `BSC_RNC_Make`, "
					+ "`Length_Mtrs`, `Breadth_Mtrs`, `Height_AGL`, `BSC_RNC_Power_Rating` "
					+ "FROM `Airtel_BSC_RNC_Cabinets` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.sharing.dto.BscRncCabinetsDto bscRncObj = null;
			for(Object [] dataObj : dataList){
				bscRncObj = new com.tvi.sharing.dto.BscRncCabinetsDto();
				bscRncObj.setFeasibility(emptyString(dataObj[0]));
				bscRncObj.setNetWork_Type(emptyString(dataObj[1]));
				bscRncObj.setBSC_RNC_Type(emptyString(dataObj[2]));
				bscRncObj.setBSC_RNC_Manufacturer(emptyString(dataObj[3]));
				bscRncObj.setBSC_RNC_Make(emptyString(dataObj[4]));
				bscRncObj.setLength_Mtrs(dataObj[5] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[5])));
				bscRncObj.setBreadth_Mtrs(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				bscRncObj.setHeight_AGL(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				bscRncObj.setBSC_RNC_Power_Rating(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				BSC_RNC_Cabinets.add(bscRncObj);
			}
			dto.setBSC_RNC_Cabinets(BSC_RNC_Cabinets);
			
			List<com.tvi.sharing.dto.McbDto> MCB = new ArrayList<com.tvi.sharing.dto.McbDto>();
			sql = "SELECT `Feasibility`, `Total_No_of_MCB_Required`, `_06A`, `_10A`, `_16A`, `_24A`, `_32A`, "
					+ "`_40A`, `_63A`, `_80A` FROM `Airtel_MCB` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.sharing.dto.McbDto mcbDto = null;
			for(Object [] dataObj : dataList){
				mcbDto = new com.tvi.sharing.dto.McbDto();
				mcbDto.setFeasibility(emptyString(dataObj[0]));
				mcbDto.setTotal_No_of_MCB_Required(Double.parseDouble(emptyNumeric(dataObj[1])));
				mcbDto.set_06A(dataObj[2] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[2])));
				mcbDto.set_16A(dataObj[4] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[4])));
				mcbDto.set_32A(dataObj[6] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[6])));
				mcbDto.set_40A(dataObj[7] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[7])));
				mcbDto.set_63A(dataObj[8] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[8])));
				mcbDto.set_80A(dataObj[9] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[9])));
				MCB.add(mcbDto);
			}
			dto.setMCB(MCB);
			
			List<com.tvi.sharing.dto.FibreNodeDto> Fiber_Node = new ArrayList<com.tvi.sharing.dto.FibreNodeDto>();
			sql = "SELECT `Feasibility`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, `Node_Model`, "
					+ "`Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, `Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, "
					+ "`FullRack`, `Tx_Rack_Space_required_in_Us`, `Is_Right_Of_Way_ROW_Required_Inside_The_Indus_Premises`, "
					+ "`Type_Of_Fiber_Laying`, `Type_Of_FMS`, `Remarks`, `Full_Rack` FROM `Airtel_Fibre_Node` "
					+ "where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.sharing.dto.FibreNodeDto fiberObj = null;
			for(Object [] dataObj : dataList){
				fiberObj = new com.tvi.sharing.dto.FibreNodeDto();
				fiberObj.setFeasibility(emptyString(dataObj[0]));
				fiberObj.setNode_Type(emptyString(dataObj[1]));
				fiberObj.setNode_Location(emptyString(dataObj[2]));
				fiberObj.setNode_Manufacturer(emptyString(dataObj[3]));
				fiberObj.setNode_Model(emptyString(dataObj[4]));
				fiberObj.setLength_Mtrs(Double.parseDouble(emptyNumeric(dataObj[5])));
				fiberObj.setBreadth_Mtrs(Double.parseDouble(emptyNumeric(dataObj[6])));
				fiberObj.setHeight_Mtrs(Double.parseDouble(emptyNumeric(dataObj[7])));
				fiberObj.setWeight_Kg(Double.parseDouble(emptyNumeric(dataObj[8])));
				fiberObj.setNode_Voltage(emptyString(dataObj[9]));
				fiberObj.setPower_Rating_in_Kw(Double.parseDouble(emptyNumeric(dataObj[10])));
				fiberObj.setFullRack(emptyString(dataObj[11]));
				fiberObj.setTx_Rack_space_required_in_Us(Double.parseDouble(emptyNumeric(dataObj[12])));
				fiberObj.setIs_Right_of_Way_ROW_required_inside_the_TOCO_premises(emptyString(dataObj[13]));
				fiberObj.setType_of_Fiber_Laying(emptyString(dataObj[14]));
				fiberObj.setType_of_FMS(emptyString(dataObj[15]));
				fiberObj.setRemarks(emptyString(dataObj[16]));
				//fiberObj.setFull_Rack(emptyString(dataObj[17]));
				Fiber_Node.add(fiberObj);
			}
			dto.setFiber_Node(Fiber_Node);
			
			com.tvi.sharing.dto.GlobalDto Global = new com.tvi.sharing.dto.GlobalDto();
			Global.setRequest_Ref_No(srNumber);
			Global.setCircle(circle);
			Global.setNetwork_Type(networkType);;
			dto.setGlobal(Global);
			
			/*com.tvi.sharing.dto.OtherEquipmentDto Other_Equipment = new com.tvi.sharing.dto.OtherEquipmentDto();
			Other_Equipment.setOther_Equipment(otherEquipment);
			dto.setOther_Equipment(Other_Equipment);*/
			
			List<com.tvi.sharing.dto.OtherEquipmentDto> Other_Equipment = new ArrayList<>();
			sql = "SELECT `Feasibility`, `Source_Request_RefNo`, `Other_Equipment_Category`, "
					+ "`Other_Equipment_Type`, `Equipment_to_be_relocated`, `Target_Indus_Site_Id`, "
					+ "`Target_Request_RefNo`, `CustomerPunchedOrPlanning`, `Deletion_OR_Relocation` "
					+ "FROM `Airtel_Other_Equipment` WHERE `SR_Number`='"+srNumber+"' and `InsertType`='SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.sharing.dto.OtherEquipmentDto otEqObj = null;
			for(Object [] dataObj : dataList){
				otEqObj = new com.tvi.sharing.dto.OtherEquipmentDto();
				otEqObj.setFeasibility(emptyString(dataObj[0]));
				otEqObj.setSource_Request_RefNo(emptyString(dataObj[1]));
				otEqObj.setOther_Equipment_Category(emptyString(dataObj[2]));
				otEqObj.setOther_Equipment_Type(emptyString(dataObj[3]));
				otEqObj.setEquipment_to_be_relocated(emptyString(dataObj[4]));
				otEqObj.setTarget_Indus_Site_Id(emptyString(dataObj[5]));
				otEqObj.setTarget_Request_RefNo(emptyString(dataObj[6]));
				otEqObj.setCustomerPunchedOrPlanning(emptyString(dataObj[7]));
				otEqObj.setDeletion_OR_Relocation(emptyString(dataObj[8]));
				Other_Equipment.add(otEqObj);
			}
			dto.setOther_Equipment(Other_Equipment);
			
			com.tvi.sharing.dto.StrageticDto Strategic_Conversion = new com.tvi.sharing.dto.StrageticDto();
			sql = "SELECT `Is_it_Strategic`, `Shelter_Size`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_AGL_Mtrs`, "
					+ "`DG_Redundancy`, `Extra_Battery_Bank_Requirement`, `Extra_Battery_BackUp` "
					+ "FROM `Airtel_Additional_Information` where `SR_Number` = '"+srNumber+"'";
			dataList = tviCommonDao.getAllTableData(sql);
			for(Object [] dataObj : dataList){
				Strategic_Conversion.setIs_it_Strategic(emptyString(dataObj[0]));
				Strategic_Conversion.setShelter_Size(emptyString(dataObj[1]));
				Strategic_Conversion.setLength_Mtrs(Double.parseDouble(emptyNumeric(dataObj[2])));
				Strategic_Conversion.setBreadth_Mtrs(Double.parseDouble(emptyNumeric(dataObj[3])));
				Strategic_Conversion.setHeight_AGL_Mtrs(Double.parseDouble(emptyNumeric(dataObj[4])));
				Strategic_Conversion.setDG_Redundancy(emptyString(dataObj[5]));
				Strategic_Conversion.setExtra_Battery_Bank_Requirement(emptyString(dataObj[6]));
				Strategic_Conversion.setExtra_Battery_Back_Up(emptyString(dataObj[7]));
			}
			dto.setStrategic_Conversion(Strategic_Conversion);
			com.tvi.sharing.dto.DgDto DG = new com.tvi.sharing.dto.DgDto();
			DG.setIs_Diesel_Generator_DG_required(isDieselGeneratorDGrequired);
			dto.setDG(DG);
			
			dto.setAssociation_AreyouWorkingInAnyBhartiGroup(Ass_AreyouWorkingInAnyBhartiGroup);
			dto.setAssociation_IfyesmentiontheBhartiUnitName(Ass_IfyesmentiontheBhartiUnitName);
			dto.setAssociation_NameOftheEmployee(Ass_NameOftheEmployee);
			dto.setAssociation_EmployeeId(Ass_EmployeeId);
			dto.setRelative_AnyRelativesareWorkingWithBhartiGroup(Rel_AnyRelativesareWorkingWithBhartiGroup);
			dto.setRelative_IfyesmentiontheBhartiUnitName(Rel_IfyesmentiontheBhartiUnitName);
			dto.setRelative_NameOftheEmployee(Rel_NameOftheEmployee);
			dto.setRelative_EmployeeId(Rel_EmployeeId);
			dto.setRelative_LandlordRelationshipwithEmployee(Rel_LandlordRelationshipwithEmployee);
			dto.setRelative_MobileNumberOfrelativeWithAirtel(Rel_MobileNumberOfrelativeWithAirtel);
			dto.setDeclaration(Declaration);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	private SpReceivedResponse spReceivedUpgrade(String srNumber, String currentStatus) {
		SpReceivedResponse response = new SpReceivedResponse();
		try {
			SpReceivedUpgradeDto jsonData = getUpgradeSrDetailsForSendToAirtel(srNumber);
			Gson gsonObj = new Gson();
			String json = gsonObj.toJson(jsonData);
			System.out.println("spReceivedUpgrade : JSON "+json);
			logger.info("spReceivedUpgrade : JSON : "+json);
			String apiURL = "https://ideploy.airtel.com/iDeployAPI/api/common/sp_insert";
			String apiResponse = RestAPI.spInsert(apiURL, json);
			System.out.println("spReceivedUpgrade : apiResponse : "+apiResponse);
			logger.info("spReceivedUpgrade : apiResponse : "+apiResponse);
			AirtelApiResponse airtelApi = gsonObj.fromJson(apiResponse, AirtelApiResponse.class);
			//System.out.println("Status : "+airtelApi.isStatus());
			//System.out.println("Messsage : "+airtelApi.getMessage());
			response.setStatus(airtelApi.isStatus());
			response.setMessage(airtelApi.getMessage());
			
			boolean status = airtelApi.isStatus();
			if(!status){
				String upSql = "UPDATE `Airtel_SR` set `STATUS` = '"+currentStatus+"' where `SR_Number` = '"+srNumber+"'";
				tviCommonDao.updateBulkdataValue(upSql);
			}
			
			Clob requestJson = new SerialClob(CommonFunction.printResponseJson(json).toCharArray());
			Clob responseJson = new SerialClob(CommonFunction.printResponseJson(apiResponse).toCharArray());
			saveResponseInTable(requestJson, responseJson, "spReceivedUpgrade");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private SpReceivedUpgradeDto getUpgradeSrDetailsForSendToAirtel(String srNumber) {
		SpReceivedUpgradeDto dto = new SpReceivedUpgradeDto();
		try {
			String circle="",networkType="",
				Ass_AreyouWorkingInAnyBhartiGroup="",Ass_IfyesmentiontheBhartiUnitName="",
				Ass_NameOftheEmployee="",Ass_EmployeeId="",Rel_AnyRelativesareWorkingWithBhartiGroup="",
				Rel_IfyesmentiontheBhartiUnitName="",Rel_NameOftheEmployee="",Rel_EmployeeId="",
				Rel_LandlordRelationshipwithEmployee="",Rel_MobileNumberOfrelativeWithAirtel="",Declaration="";
			String sql = "SELECT `SP_Number`, `Customer_Site_Id`, `Customer_Site_Name`, `Site_Type`, `Project_Name`, "
					+ "`City`, `Other_Equipment`, `Is_Diesel_Generator_DG_required`, `TOCO_Site_Id`, date_format(`Date_of_Proposal`,'%d-%m-%Y') as `Date_of_Proposal`, "
					+ "`Power_Rating`, `Site_Electrification_Distance`, `Tentative_EB_Availibility`, `Additional_Charge`, "
					+ "`Head_Load_Charge`, `Electrification_Cost`, `Electrification_Line_Distance`, "
					+ "`Electricity_Connection_HT_LT`, `Infra_Details`, `Site_Classification`, `Expected_Rent_to_Landlord`, "
					+ "`Non_Refundable_Deposit`, `Estimated_Deployment_Time__in_days_`, `Additional_Capex`, `Standard_Rates`, "
					+ "`Fiber_Charges`, `Rental_Threshold`, `Excess_Rent_beyond_Threshold`, `Tentative_Rental_Premium`, "
					+ "`Additional_Rent`, `IPFee`, `Field_Operation_Charges`, `Security_Guard_Charges`, `Mobilization_Charges`, "
					+ "`Erection_Charges`, `Land_Lord_Charges_or_Rent_Charges`, `Wind_Speed`, `TowerHeight`, "
					+ "`Agl`, `Distance_from_P1_Lat_Long_in_meter`, `Rejection_Remarks`, `Difficult`, `Circle`, "
					+ "`Request_for_Network_Type`, `Remarks`, `RL_Type`, "
					+ "`Association_AreyouWorkingInAnyBhartiGroup`, "
					+ "`Association_IfyesmentiontheBhartiUnitName`, `Association_NameOftheEmployee`, "
					+ "`Association_EmployeeId`, `Relative_AnyRelativesareWorkingWithBhartiGroup`, "
					+ "`Relative_IfyesmentiontheBhartiUnitName`, `Relative_NameOftheEmployee`, `Relative_EmployeeId`, "
					+ "`Relative_LandlordRelationshipwithEmployee`, `Relative_MobileNumberOfrelativeWithAirtel`, "
					+ "`Declaration` FROM `Airtel_SR` "
					+ "where `SR_Number` = '"+srNumber+"'";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.upgrade.dto.SpDetailsDto SPDetails_NN = new com.tvi.upgrade.dto.SpDetailsDto();
			for(Object [] dataObj : dataList){
				SPDetails_NN.setSP_Ref_No(emptyString(dataObj[0]));
				SPDetails_NN.setCustomerSiteId(emptyString(dataObj[1]));
				SPDetails_NN.setCustomerSiteName(emptyString(dataObj[2]));
				SPDetails_NN.setSiteType(emptyString(dataObj[3]));
				SPDetails_NN.setProject_Name(emptyString(dataObj[4]));
				//SPDetails_NN.setCity(emptyString(dataObj[5]));	
				//otherEquipment = emptyString(dataObj[6]);
				//isDieselGeneratorDGrequired = emptyString(dataObj[7]);
				SPDetails_NN.setTOCO_Site_Id(emptyString(dataObj[8]));
				SPDetails_NN.setDate_of_Proposal(dataObj[9] == null ? "" : emptyString(dataObj[9]));
				SPDetails_NN.setPower_Rating(dataObj[10] == null ? "" : emptyString(dataObj[10]));
				SPDetails_NN.setSite_Electrification_Distance(dataObj[11] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[11])));
				SPDetails_NN.setTentative_EB_Availibility(dataObj[12] == null ? "" : emptyString(dataObj[12]));
				SPDetails_NN.setAdditional_Charge(dataObj[13] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[13])));
				SPDetails_NN.setHead_Load_Charge(dataObj[14] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[14])));
				SPDetails_NN.setElectrification_Cost(dataObj[15] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[15])));
				SPDetails_NN.setElectrification_Line_Distance(dataObj[16] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[16])));
				SPDetails_NN.setElectricity_Connection_HT_LT(dataObj[17] == null ? "" : emptyString(dataObj[17]));
				SPDetails_NN.setInfra_Details(dataObj[18] == null ? "" : emptyString(dataObj[18]));
				SPDetails_NN.setSite_Classification(dataObj[19] == null ? "" : emptyString(dataObj[19]));
				SPDetails_NN.setExpected_Rent_to_Landlord(dataObj[20] == null ? "" : emptyString(dataObj[20]));
				SPDetails_NN.setNon_Refundable_Deposit(dataObj[21] == null ? "" : emptyString(dataObj[21]));
				SPDetails_NN.setEstimated_Deployment_Time__in_days_(dataObj[22] == null ? "" : emptyString(dataObj[22]));
				SPDetails_NN.setAdditional_Capex(dataObj[23] == null ? "" : emptyString(dataObj[23]));
				SPDetails_NN.setStandard_Rates(dataObj[24] == null ? "" : emptyString(dataObj[24]));
				SPDetails_NN.setFiber_Charges(dataObj[25] == null ? "" : emptyString(dataObj[25]));
				SPDetails_NN.setRental_Threshold(dataObj[26] == null ? "" : emptyString(dataObj[26]));
				SPDetails_NN.setExcess_Rent_beyond_Threshold(dataObj[27] == null ? "" : emptyString(dataObj[27]));
				SPDetails_NN.setTentative_Rental_Premium(dataObj[28] == null ? "" : emptyString(dataObj[28]));
				SPDetails_NN.setAdditional_Rent(dataObj[29] == null ? "" : emptyString(dataObj[29]));
				SPDetails_NN.setIPFee(dataObj[30] == null ? "" : emptyString(dataObj[30]));
				SPDetails_NN.setField_Operation_Charges(dataObj[31] == null ? "" : emptyString(dataObj[31]));
				SPDetails_NN.setSecurity_Guard_Charges(dataObj[32] == null ? "" : emptyString(dataObj[32]));
				SPDetails_NN.setMobilization_Charges(dataObj[33] == null ? "" : emptyString(dataObj[33]));
				SPDetails_NN.setErection_Charges(dataObj[34] == null ? "" : emptyString(dataObj[34]));
				SPDetails_NN.setLand_Lord_Charges_or_Rent_Charges(dataObj[35] == null ? "" : emptyString(dataObj[35]));
				SPDetails_NN.setWind_Speed(dataObj[36] == null ? "" : emptyString(dataObj[36]));
				SPDetails_NN.setTowerHeight(dataObj[37] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[37])));
				SPDetails_NN.setAgl(dataObj[38] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[38])));
				SPDetails_NN.setDistance_from_P1_Lat_Long_in_meter(dataObj[39] == null ? 0 : Double.parseDouble(emptyNumeric(dataObj[39])));
				SPDetails_NN.setRejection_Remarks(dataObj[40] == null ? "" : emptyString(dataObj[40]));
				SPDetails_NN.setDifficult(dataObj[41] == null ? "" :emptyString(dataObj[41]));
				circle = emptyString(dataObj[42]);
				networkType = emptyString(dataObj[43]);
				SPDetails_NN.setRemarks(emptyString(dataObj[44]));
				SPDetails_NN.setProductType(emptyString(dataObj[45]));
				
				Ass_AreyouWorkingInAnyBhartiGroup = dataObj[46] == null ? "" : emptyString(dataObj[46]);
				Ass_IfyesmentiontheBhartiUnitName = dataObj[47] == null ? "" : emptyString(dataObj[47]);
				Ass_NameOftheEmployee = dataObj[48] == null ? "" : emptyString(dataObj[48]);
				Ass_EmployeeId = dataObj[49] == null ? "" : emptyString(dataObj[49]);
				Rel_AnyRelativesareWorkingWithBhartiGroup = dataObj[50] == null ? "" : emptyString(dataObj[50]);
				Rel_IfyesmentiontheBhartiUnitName = dataObj[51] == null ? "" : emptyString(dataObj[51]);
				Rel_NameOftheEmployee = dataObj[52] == null ? "" : emptyString(dataObj[52]);
				Rel_EmployeeId = dataObj[53] == null ? "" : emptyString(dataObj[53]);
				Rel_LandlordRelationshipwithEmployee = dataObj[54] == null ? "" : emptyString(dataObj[54]);
				Rel_MobileNumberOfrelativeWithAirtel = dataObj[55] == null ? "" : emptyString(dataObj[55]);
				Declaration = dataObj[56] == null ? "" : emptyString(dataObj[56]);
			}
			sql = "SELECT `P1_Latitude`, `P1_Longitude`, `P1_Tower_Type` FROM `Airtel_Priority_Details` "
					+ "where `SR_Number` = '"+srNumber+"'";
			dataList = tviCommonDao.getAllTableData(sql);
			for(Object [] dataObj : dataList){
				SPDetails_NN.setLatitude(emptyString(dataObj[0]));
				SPDetails_NN.setLongitude(emptyString(dataObj[1]));
				SPDetails_NN.setTowerType(emptyString(dataObj[2]));
			}
			dto.setSPDetails_RL(SPDetails_NN);
			
			List<com.tvi.upgrade.dto.RadioAntennaDto> Radio_Antenna = new ArrayList<com.tvi.upgrade.dto.RadioAntennaDto>();
			sql = "SELECT `Feasibility`, `RadioAntenna_i_WAN`, `RadioAntenna_Type`, `Height_AGL_m`, "
					+ "`Length_m`, `Width_m`, `Depth_m`, `No_of_Ports`, `Azimuth_Degree`, "
					+ "`BandFrequencyMHz_FrequencyCombination`, `Action`, `Customer_Punched_Or_Planning`, "
					+ "`Sector_Details` "
					+ "FROM `Airtel_Radio_Antenna` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.upgrade.dto.RadioAntennaDto radioAnt = null;
			for(Object [] dataObj : dataList){
				radioAnt = new com.tvi.upgrade.dto.RadioAntennaDto();
				radioAnt.setAction(emptyString(dataObj[10]));
				radioAnt.setCustomer_Punched_Or_Planning(emptyString(dataObj[11]));
				radioAnt.setSector_Details(emptyString(dataObj[12]));
				
				radioAnt.setFeasibility(emptyString(dataObj[0]));
				radioAnt.setRadioAntenna_i_WAN(emptyString(dataObj[1]));
				radioAnt.setRadioAntenna_Type(emptyString(dataObj[2]));
				radioAnt.setHeight_AGL_m(Double.parseDouble(emptyNumeric(dataObj[3])));
				radioAnt.setLength_m(Double.parseDouble(emptyNumeric(dataObj[4])));
				radioAnt.setWidth_m(Double.parseDouble(emptyNumeric(dataObj[5])));
				radioAnt.setDepth_m(Double.parseDouble(emptyNumeric(dataObj[6])));
				radioAnt.setNo_of_Ports(emptyString(dataObj[7]));
				radioAnt.setAzimuth_Degree(Double.parseDouble(emptyNumeric(dataObj[8])));
				String freq = emptyString(dataObj[9]);
				String freqSplit [] = freq.split(",");
				com.tvi.upgrade.dto.BandFrequencyMHzDto bandFreq = new com.tvi.upgrade.dto.BandFrequencyMHzDto();
				for(int i=0;i<freqSplit.length;i++){
					if(i == 0)
						bandFreq.setFrequency_Number_1(Double.parseDouble(freqSplit[i]));
					if(i == 1)
						bandFreq.setFrequency_Number_2(Double.parseDouble(freqSplit[i]));
					if(i == 2)
						bandFreq.setFrequency_Number_3(Double.parseDouble(freqSplit[i]));
					if(i == 3)
						bandFreq.setFrequency_Number_4(Double.parseDouble(freqSplit[i]));
					if(i == 4)
						bandFreq.setFrequency_Number_5(Double.parseDouble(freqSplit[i]));
					if(i == 5)
						bandFreq.setFrequency_Number_6(Double.parseDouble(freqSplit[i]));
					if(i == 6)
						bandFreq.setFrequency_Number_7(Double.parseDouble(freqSplit[i]));
					if(i == 7)
						bandFreq.setFrequency_Number_8(Double.parseDouble(freqSplit[i]));
				}
				radioAnt.setBand_Frequency_MHz_Frequency_Combination(bandFreq);
				Radio_Antenna.add(radioAnt);
			}
			dto.setRadio_Antenna(Radio_Antenna);
			
			List<com.tvi.upgrade.dto.MwAntennaDto> MW = new ArrayList<com.tvi.upgrade.dto.MwAntennaDto>();
			sql = "SELECT `Feasibility`, `Height_in_Mtrs`, `MWAntenna_i_WAN`, `Size_of_MW`, "
					+ "`Azimuth_Degree`, `Action`, `Customer_Punched_Or_Planning`, `Sector_Details`, "
					+ "`Source` "
					+ " FROM `Airtel_MW` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.upgrade.dto.MwAntennaDto mwObj = null;
			for(Object [] dataObj : dataList){
				mwObj = new com.tvi.upgrade.dto.MwAntennaDto();
				mwObj.setFeasibility(emptyString(dataObj[0]));
				mwObj.setHeight_in_Mtrs(Double.parseDouble(emptyNumeric(dataObj[1])));
				mwObj.setMWAntenna_i_WAN(emptyString(dataObj[2]));
				mwObj.setSize_of_MW(Double.parseDouble(emptyNumeric(dataObj[3])));
				mwObj.setAzimuth_Degree(Double.parseDouble(emptyNumeric(dataObj[4])));
				mwObj.setAction(emptyString(dataObj[5]));
				mwObj.setCustomer_Punched_Or_Planning(emptyString(dataObj[6]));
				mwObj.setSector_Details(emptyString(dataObj[7]));
				mwObj.setSource(emptyString(dataObj[8]));
				MW.add(mwObj);
			}
			dto.setMW(MW);
			
			List<com.tvi.upgrade.dto.BtsCabinetDto> BTS = new ArrayList<com.tvi.upgrade.dto.BtsCabinetDto>();
			sql = "SELECT `Feasibility`, `NetWork_Type`, `BTS_Type`, `Band`, `Manufacturer`, `Make_of_BTS`, "
					+ "`Length_Mtrs`, `Width_Mtrs`, `Height_Mtrs`, `BTS_Power_Rating_KW`, `BTS_Location`, "
					+ "`BTS_Voltage`, `Main_Unit_incase_of_TT_Split_Version`, "
					+ "`Space_Occupied_in_Us_incase_of_TT_Split_Version`, "
					+ "`RRU_Unit`, `No_of_RRU_Units_incase_of_TT_Split_Version`, "
					+ "`Combined_wt_of_RRU_Unit_incase_of_TT_Split_Version`, `AGL_of_RRU_unit_in_M`, "
					+ "`Weight_of_BTS_including_TMA_TMB_Kg`, `Billable_Weigtht`, `Action`, "
					+ "`Customer_Punched_Or_Planning` "
					+ "FROM `Airtel_BTS` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.upgrade.dto.BtsCabinetDto btsObj = null;
			for(Object [] dataObj : dataList){
				btsObj = new com.tvi.upgrade.dto.BtsCabinetDto();
				btsObj.setFeasibility(emptyString(dataObj[0]));
				btsObj.setNetWork_Type(emptyString(dataObj[1]));
				btsObj.setBTS_Type(emptyString(dataObj[2]));
				btsObj.setBand(emptyString(dataObj[3]));
				btsObj.setManufacturer(emptyString(dataObj[4]));
				btsObj.setMake_of_BTS(emptyString(dataObj[5]));
				btsObj.setLength_Mtrs(Double.parseDouble(emptyNumeric(dataObj[6])));
				btsObj.setWidth_Mtrs(Double.parseDouble(emptyNumeric(dataObj[7])));
				btsObj.setHeight_Mtrs(Double.parseDouble(emptyNumeric(dataObj[8])));
				btsObj.setBTS_Power_Rating_KW(Double.parseDouble(emptyNumeric(dataObj[9])));
				btsObj.setBTS_Location(emptyString(dataObj[10]));
				btsObj.setBTS_Voltage(emptyString(dataObj[11]));
				btsObj.setMain_Unit_in_case_of_TT_Split_Version(emptyString(dataObj[12]));
				btsObj.setSpace_Occupied_in_Us_in_case_of_TT_Split_Version(emptyString(dataObj[13]));
				btsObj.setLocation_Of_RRU(emptyString(dataObj[14]));
				btsObj.setNo_Of_RRU_Units_in_case_of_TT_Split_Version(Double.parseDouble(emptyNumeric(dataObj[15])));
				btsObj.setCombined_wt_Of_RRU_Unit_in_case_of_TT_Split_Version(Double.parseDouble(emptyNumeric(dataObj[16])));
				btsObj.setAGL_of_RRU_unit_in_M(Double.parseDouble(emptyNumeric(dataObj[17])));
				btsObj.setWeight_Of_BTS_including_TMA_TMB_Kg(Double.parseDouble(emptyNumeric(dataObj[18])));
				btsObj.setBillable_Weight_in_Kg(Double.parseDouble(emptyNumeric(dataObj[19])));
				btsObj.setAction(emptyString(dataObj[20]));
				btsObj.setCustomer_Punched_Or_Planning(emptyString(dataObj[21]));
				BTS.add(btsObj);
			}
			dto.setBTS(BTS);
			
			List<com.tvi.upgrade.dto.OtherNodeDto> Other_Node = new ArrayList<com.tvi.upgrade.dto.OtherNodeDto>();
			sql = "SELECT `Feasibility`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, `Node_Model`, "
					+ "`Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, `Weight_Kg`, `Node_Voltage`, "
					+ "`Power_Rating_in_Kw`, `FullRack`, `Tx_Rack_Space_Required_In_Us`, `Remarks`, `Action`, "
					+ "`Customer_Punched_Or_Planning` "
					+ "FROM `Airtel_Other_Node` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.upgrade.dto.OtherNodeDto otherObj = null;
			for(Object [] dataObj : dataList){
				otherObj = new com.tvi.upgrade.dto.OtherNodeDto();
				otherObj.setFeasibility(emptyString(dataObj[0]));
				otherObj.setNode_Type(emptyString(dataObj[1]));
				otherObj.setNode_Location(emptyString(dataObj[2]));
				otherObj.setNode_Manufacturer(emptyString(dataObj[3]));
				otherObj.setNode_Model(emptyString(dataObj[4]));
				otherObj.setLength_Mtrs(Double.parseDouble(emptyNumeric(dataObj[5])));
				otherObj.setBreadth_Mtrs(Double.parseDouble(emptyNumeric(dataObj[6])));
				otherObj.setHeight_Mtrs(Double.parseDouble(emptyNumeric(dataObj[7])));
				otherObj.setWeight_Kg(Double.parseDouble(emptyNumeric(dataObj[8])));
				otherObj.setNode_Voltage(emptyString(dataObj[9]));
				otherObj.setPower_Rating_in_Kw(Double.parseDouble(emptyNumeric(dataObj[10])));
				otherObj.setFullRack(emptyString(dataObj[11]));
				otherObj.setTx_Rack_Space_Required_In_Us(Double.parseDouble(emptyNumeric(dataObj[12])));
				otherObj.setRemarks(emptyString(dataObj[13]));
				otherObj.setAction(emptyString(dataObj[14]));
				otherObj.setCustomer_Punched_Or_Planning(emptyString(dataObj[15]));
				Other_Node.add(otherObj);
			}
			dto.setOther_Node(Other_Node);
			
			List<com.tvi.upgrade.dto.BscRncCabinetsDto> BSC_RNC_Cabinets = new ArrayList<com.tvi.upgrade.dto.BscRncCabinetsDto>();
			sql = "SELECT `Feasibility`, `NetWork_Type`, `BSC_RNC_Type`, `BSC_RNC_Manufacturer`, `BSC_RNC_Make`, "
					+ "`Length_Mtrs`, `Breadth_Mtrs`, `Height_AGL`, `BSC_RNC_Power_Rating`, `Action`, "
					+ "`Customer_Punched_Or_Planning` "
					+ "FROM `Airtel_BSC_RNC_Cabinets` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.upgrade.dto.BscRncCabinetsDto bscRncObj = null;
			for(Object [] dataObj : dataList){
				bscRncObj = new com.tvi.upgrade.dto.BscRncCabinetsDto();
				bscRncObj.setFeasibility(emptyString(dataObj[0]));
				bscRncObj.setNetWork_Type(emptyString(dataObj[1]));
				bscRncObj.setBSC_RNC_Type(emptyString(dataObj[2]));
				bscRncObj.setBSC_RNC_Manufacturer(emptyString(dataObj[3]));
				bscRncObj.setBSC_RNC_Make(emptyString(dataObj[4]));
				bscRncObj.setLength_Mtrs(Double.parseDouble(emptyNumeric(dataObj[5])));
				bscRncObj.setBreadth_Mtrs(Double.parseDouble(emptyNumeric(dataObj[6])));
				bscRncObj.setHeight_AGL(Double.parseDouble(emptyNumeric(dataObj[7])));
				bscRncObj.setBSC_RNC_Power_Rating(Double.parseDouble(emptyNumeric(dataObj[8])));
				bscRncObj.setAction(emptyString(dataObj[9]));
				bscRncObj.setCustomer_Punched_Or_Planning(emptyString(dataObj[10]));
				BSC_RNC_Cabinets.add(bscRncObj);
			}
			dto.setBSC_RNC_Cabinets(BSC_RNC_Cabinets);
			
			List<com.tvi.upgrade.dto.McbDto> MCB = new ArrayList<com.tvi.upgrade.dto.McbDto>();
			sql = "SELECT `Feasibility`, `Total_No_of_MCB_Required`, `_06A`, `_10A`, `_16A`, `_24A`, `_32A`, "
					+ "`_40A`, `_63A`, `_80A`, `Customer_Punched_Or_Planning` FROM `Airtel_MCB` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.upgrade.dto.McbDto mcbDto = null;
			for(Object [] dataObj : dataList){
				mcbDto = new com.tvi.upgrade.dto.McbDto();
				mcbDto.setFeasibility(emptyString(dataObj[0]));
				mcbDto.setTotal_No_of_MCB_Required(Double.parseDouble(emptyNumeric(dataObj[1])));
				mcbDto.set_06A(Double.parseDouble(emptyNumeric(dataObj[2])));
				mcbDto.set_10A(Double.parseDouble(emptyNumeric(dataObj[3])));
				mcbDto.set_16A(Double.parseDouble(emptyNumeric(dataObj[4])));
				mcbDto.set_24A(Double.parseDouble(emptyNumeric(dataObj[5])));
				mcbDto.set_32A(Double.parseDouble(emptyNumeric(dataObj[6])));
				mcbDto.set_40A(Double.parseDouble(emptyNumeric(dataObj[7])));
				mcbDto.set_63A(Double.parseDouble(emptyNumeric(dataObj[8])));
				mcbDto.set_80A(Double.parseDouble(emptyNumeric(dataObj[9])));
				mcbDto.setCustomer_Punched_Or_Planning(emptyString(dataObj[10]));
				MCB.add(mcbDto);
			}
			dto.setMCB(MCB);
			
			List<com.tvi.upgrade.dto.FibreNodeDto> Fiber_Node = new ArrayList<com.tvi.upgrade.dto.FibreNodeDto>();
			sql = "SELECT `Feasibility`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, `Node_Model`, "
					+ "`Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, `Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, "
					+ "`FullRack`, `Tx_Rack_Space_required_in_Us`, `Is_Right_Of_Way_ROW_Required_Inside_The_Indus_Premises`, "
					+ "`Type_Of_Fiber_Laying`, `Type_Of_FMS`, `Remarks`, `Action`, "
					+ "`Customer_Punched_Or_Planning`, `Source` FROM `Airtel_Fibre_Node` "
					+ "where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.upgrade.dto.FibreNodeDto fiberObj = null;
			for(Object [] dataObj : dataList){
				fiberObj = new com.tvi.upgrade.dto.FibreNodeDto();
				fiberObj.setFeasibility(emptyString(dataObj[0]));
				fiberObj.setNode_Type(emptyString(dataObj[1]));
				fiberObj.setNode_Location(emptyString(dataObj[2]));
				fiberObj.setNode_Manufacturer(emptyString(dataObj[3]));
				fiberObj.setNode_Model(emptyString(dataObj[4]));
				fiberObj.setLength_Mtrs(Double.parseDouble(emptyNumeric(dataObj[5])));
				fiberObj.setBreadth_Mtrs(Double.parseDouble(emptyNumeric(dataObj[6])));
				fiberObj.setHeight_mtrs(Double.parseDouble(emptyNumeric(dataObj[7])));
				fiberObj.setWeight(Double.parseDouble(emptyNumeric(dataObj[8])));
				fiberObj.setNode_Voltage(emptyString(dataObj[9]));
				fiberObj.setPower_Rating_in_Kw(Double.parseDouble(emptyNumeric(dataObj[10])));
				fiberObj.setFullRack(emptyString(dataObj[11]));
				fiberObj.setTx_Rack_space_required_in_Us(Double.parseDouble(emptyNumeric(dataObj[12])));
				fiberObj.setIs_Right_of_Way_ROW_required_inside_the_TOCO_premises(emptyString(dataObj[13]));
				fiberObj.setType_of_Fiber_Laying(emptyString(dataObj[14]));
				fiberObj.setType_of_FMS(emptyString(dataObj[15]));
				fiberObj.setRemarks(emptyString(dataObj[16]));
				fiberObj.setAction(emptyString(dataObj[17]));
				fiberObj.setCustomer_Punched_Or_Planning(emptyString(dataObj[18]));
				fiberObj.setSource(emptyString(dataObj[19]));
				Fiber_Node.add(fiberObj);
			}
			dto.setFiber_Node(Fiber_Node);
			
			List<TmaTmbDto> TMA_TMB = new ArrayList<TmaTmbDto>();
			sql = "SELECT `Feasibility`, `Action`, `No_of_TMA_TMB`, `Weight_of_each_TMA_TMB`, "
					+ "`Combined_wt_of_TMA_TMB_Kgs`, `Height_at_which_needs_to_be_mounted_Mtrs`, "
					+ "`Customer_Punched_Or_Planning`, `Source_Request_Ref_No`, `Delete_Request_Ref_No` "
					+ "FROM `Airtel_TMA_TMB` WHERE `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			TmaTmbDto tmaTmbObj = null;
			for(Object [] dataObj : dataList){
				tmaTmbObj = new TmaTmbDto();
				tmaTmbObj.setFeasibility(emptyString(dataObj[0]));
				tmaTmbObj.setAction(emptyString(dataObj[1]));
				tmaTmbObj.setNo_of_TMA_TMB(emptyString(dataObj[2]));
				tmaTmbObj.setWeight_of_each_TMA_TMB(emptyString(dataObj[3]));
				tmaTmbObj.setCombined_wt_of_TMA_TMB_Kgs(emptyString(dataObj[4]));
				tmaTmbObj.setHeight_at_which_needs_to_be_mounted_Mtrs(emptyString(dataObj[5]));
				tmaTmbObj.setCustomer_Punched_Or_Planning(emptyString(dataObj[6]));
				tmaTmbObj.setSource_Request_Ref_No(emptyString(dataObj[7]));
				tmaTmbObj.setDelete_Request_Ref_No(emptyString(dataObj[8]));
				TMA_TMB.add(tmaTmbObj);
			}
			dto.setTMA_TMB(TMA_TMB);
			
			com.tvi.upgrade.dto.GlobalDto Global = new com.tvi.upgrade.dto.GlobalDto();
			Global.setRequest_Ref_No(srNumber);
			Global.setCircle(circle);
			Global.setNetwork_Type(networkType);
			dto.setGlobal(Global);
			
			/*com.tvi.upgrade.dto.OtherEquipmentDto Other_Equipment = new com.tvi.upgrade.dto.OtherEquipmentDto();
			Other_Equipment.setOther_Equipment(otherEquipment);
			dto.setOther_Equipment(Other_Equipment);*/
			
			List<com.tvi.upgrade.dto.OtherEquipmentDto> Other_Equipment = new ArrayList<>();
			sql = "SELECT `Feasibility`, `Source_Request_RefNo`, `Other_Equipment_Category`, "
					+ "`Other_Equipment_Type`, `Equipment_to_be_relocated`, `Target_Indus_Site_Id`, "
					+ "`Target_Request_RefNo`, `CustomerPunchedOrPlanning`, `Deletion_OR_Relocation` "
					+ "FROM `Airtel_Other_Equipment` WHERE `SR_Number`='"+srNumber+"' and `InsertType`='SP'";
			dataList = tviCommonDao.getAllTableData(sql);
			com.tvi.upgrade.dto.OtherEquipmentDto otEqObj = null;
			for(Object [] dataObj : dataList){
				otEqObj = new com.tvi.upgrade.dto.OtherEquipmentDto();
				otEqObj.setFeasibility(emptyString(dataObj[0]));
				otEqObj.setSource_Request_RefNo(emptyString(dataObj[1]));
				otEqObj.setOther_Equipment_Category(emptyString(dataObj[2]));
				otEqObj.setOther_Equipment_Type(emptyString(dataObj[3]));
				otEqObj.setEquipment_to_be_relocated(emptyString(dataObj[4]));
				otEqObj.setTarget_Indus_Site_Id(emptyString(dataObj[5]));
				otEqObj.setTarget_Request_RefNo(emptyString(dataObj[6]));
				otEqObj.setCustomerPunchedOrPlanning(emptyString(dataObj[7]));
				otEqObj.setDeletion_OR_Relocation(emptyString(dataObj[8]));
				Other_Equipment.add(otEqObj);
			}
			dto.setOther_Equipment(Other_Equipment);
			
			com.tvi.upgrade.dto.StrageticDto Strategic_Conversion = new com.tvi.upgrade.dto.StrageticDto();
			sql = "SELECT `Is_it_Strategic`, `Shelter_Size`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_AGL_Mtrs`, "
					+ "`DG_Redundancy`, `Extra_Battery_Bank_Requirement`, `Extra_Battery_BackUp` "
					+ "FROM `Airtel_Additional_Information` where `SR_Number` = '"+srNumber+"'";
			dataList = tviCommonDao.getAllTableData(sql);
			for(Object [] dataObj : dataList){
				Strategic_Conversion.setIs_it_Strategic(emptyString(dataObj[0]));
				Strategic_Conversion.setShelter_Size(emptyString(dataObj[1]));
				Strategic_Conversion.setLength_Mtrs(Double.parseDouble(emptyNumeric(dataObj[2])));
				Strategic_Conversion.setBreadth_Mtrs(Double.parseDouble(emptyNumeric(dataObj[3])));
				Strategic_Conversion.setHeight_AGL_Mtrs(Double.parseDouble(emptyNumeric(dataObj[4])));
				Strategic_Conversion.setDG_Redundancy(emptyString(dataObj[5]));
				Strategic_Conversion.setExtra_Battery_Bank_Requirement(emptyString(dataObj[6]));
				Strategic_Conversion.setExtra_Battery_Back_Up(emptyString(dataObj[7]));
			}
			dto.setStrategic_Conversion(Strategic_Conversion);
			
			dto.setAssociation_AreyouWorkingInAnyBhartiGroup(Ass_AreyouWorkingInAnyBhartiGroup);
			dto.setAssociation_IfyesmentiontheBhartiUnitName(Ass_IfyesmentiontheBhartiUnitName);
			dto.setAssociation_NameOftheEmployee(Ass_NameOftheEmployee);
			dto.setAssociation_EmployeeId(Ass_EmployeeId);
			dto.setRelative_AnyRelativesareWorkingWithBhartiGroup(Rel_AnyRelativesareWorkingWithBhartiGroup);
			dto.setRelative_IfyesmentiontheBhartiUnitName(Rel_IfyesmentiontheBhartiUnitName);
			dto.setRelative_NameOftheEmployee(Rel_NameOftheEmployee);
			dto.setRelative_EmployeeId(Rel_EmployeeId);
			dto.setRelative_LandlordRelationshipwithEmployee(Rel_LandlordRelationshipwithEmployee);
			dto.setRelative_MobileNumberOfrelativeWithAirtel(Rel_MobileNumberOfrelativeWithAirtel);
			dto.setDeclaration(Declaration);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	private RfaiReceivedResponse rfaiReceived(String srNumber, String currentStatus) {
		RfaiReceivedResponse response = new RfaiReceivedResponse();
		try {
			RfaiReceivedDto jsonData = getSrDetailsForRfaiReceived(srNumber);
			Gson gsonObj = new Gson();
			String json = gsonObj.toJson(jsonData);
			System.out.println("rfaiReceived : JSON "+json);
			logger.info("rfaiReceived : JSON : "+json);
			String apiURL = "https://ideploy.airtel.com/iDeployAPI/api/common/rfai_insert";
			String apiResponse = RestAPI.rfaiInsert(apiURL, json);
			System.out.println("rfaiReceived : apiResponse : "+apiResponse);
			logger.info("rfaiReceived : apiResponse : "+apiResponse);
			AirtelApiResponse airtelApi = gsonObj.fromJson(apiResponse, AirtelApiResponse.class);
			//System.out.println("Status : "+airtelApi.isStatus());
			//System.out.println("Messsage : "+airtelApi.getMessage());
			response.setStatus(airtelApi.isStatus());
			response.setMessage(airtelApi.getMessage());
			
			boolean status = airtelApi.isStatus();
			if(!status){
				String upSql = "UPDATE `Airtel_SR` set `STATUS` = '"+currentStatus+"' where `SR_Number` = '"+srNumber+"'";
				tviCommonDao.updateBulkdataValue(upSql);
			}
			
			Clob requestJson = new SerialClob(CommonFunction.printResponseJson(json).toCharArray());
			Clob responseJson = new SerialClob(CommonFunction.printResponseJson(apiResponse).toCharArray());
			saveResponseInTable(requestJson, responseJson, "rfaiReceived");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private RfaiReceivedDto getSrDetailsForRfaiReceived(String srNumber) {
		RfaiReceivedDto dto = new RfaiReceivedDto();
		try {
			dto.setRequest_Ref_No(srNumber);
			String sql = "SELECT `P1_Tower_Type`, `P1_Site_Address`, `P1_Latitude`, `P1_Longitude` "
					+ "FROM `Airtel_Priority_Details` where `SR_Number` = '"+srNumber+"'";
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			for(Object [] dataObj : dataList){
				dto.setTower_Type(dataObj[0] == null ? "" : emptyString(dataObj[0]));
				dto.setSite_Address(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				dto.setLatitude(dataObj[2] == null ? "" : emptyString(dataObj[2]));
				dto.setLongitude(dataObj[3] == null ? "" : emptyString(dataObj[3]));
			}
			sql = "SELECT `Preferred_Product_Type`, `Circle`, `Customer_Site_Id`, `Customer_Site_Name`, `Customer`, "
					+ "`Request_for_Network_Type`, `Cell_Type`, `TOCO_Site_Id`, `Product_Name`, `TAB_NAME`, "
					+ "`Tower_Completion`, `Shelter_Equipment_RoomReady`, `AirConditioner_Commissioned`, "
					+ "`DG_Commissioned`, `Acceptance_Testing_Of_Site_Infrastructure_Completed_Status`, "
					+ "`EB_Status`, `Created_By`, `OFC_Duct_Laying_Done`, `Access_To_Site_Available_Status`, "
					+ "`Engineer_Name`, `Engineer_Phone_Number`, "
					//+ "date_format(`Notice_Form_Generation_Date`,'%d/%m/%Y') as `Notice_Form_Generation_Date`, "
					+ "concat(`Notice_Form_Generation_Date`,'T00:00:00.000+05:30') as `Notice_Form_Generation_Date`, "
					+ "`Address1`, `RL_Type` FROM `Airtel_SR` where `SR_Number` = '"+srNumber+"'";
			dataList = tviCommonDao.getAllTableData(sql);
			for(Object [] dataObj : dataList){
				dto.setCircle(dataObj[1] == null ? "" : emptyString(dataObj[1]));
				dto.setCustomer_Site_ID(dataObj[2] == null ? "" : emptyString(dataObj[2]));
				dto.setCustomer_Site_Name(dataObj[3] == null ? "" : emptyString(dataObj[3]));
				dto.setCustomer(dataObj[4] == null ? "" : emptyString(dataObj[4]));
				dto.setNetwork_Type(dataObj[5] == null ? "" : emptyString(dataObj[5]));
				dto.setCell_Type(dataObj[6] == null ? "" : emptyString(dataObj[6]));
				if(dataObj[7] == null)
					dto.setTOCO_Site_Id("");
				else
					dto.setTOCO_Site_Id(emptyString(dataObj[7]));
				
				String tabName = emptyString(dataObj[9]);
				if(tabName.equalsIgnoreCase(Constant.CreateNBS)){
					dto.setProduct_Type(dataObj[0] == null ? "" : emptyString(dataObj[0]));
					dto.setSite_Address(dataObj[22] == null ? "" : emptyString(dataObj[22]));
				}
				else if(tabName.equalsIgnoreCase(Constant.New_Tenency)){
					dto.setProduct_Type(dataObj[8] == null ? "" : emptyString(dataObj[8]));
				}
				else if(tabName.equalsIgnoreCase(Constant.Site_Upgrade)){
					//dto.setProduct_Type(dataObj[0] == null ? "" : emptyString(dataObj[0]));
					dto.setProduct_Type(dataObj[23] == null ? "" : emptyString(dataObj[23]));
				}
				dto.setTower_Completion(dataObj[10] == null ? "" : emptyString(dataObj[10]));
				dto.setShelter_Equipment_RoomReady(dataObj[11] == null ? "" : emptyString(dataObj[11]));
				dto.setAirConditioner_Commissioned(dataObj[12] == null ? "" : emptyString(dataObj[12]));
				dto.setDG_Commissioned(dataObj[13] == null ? "" : emptyString(dataObj[13]));
				dto.setAcceptance_Testing_Of_Site_Infrastructure_Completed_Status(dataObj[14] == null ? "" : emptyString(dataObj[14]));
				dto.setEB_Status(dataObj[15] == null ? "" : emptyString(dataObj[15]));
				dto.setCreated_By(dataObj[16] == null ? "" : emptyString(dataObj[16]));
				dto.setOFC_Duct_Laying_Done(dataObj[17] == null ? "" : emptyString(dataObj[17]));
				dto.setAccess_To_Site_Available_Status(dataObj[18] == null ? "" : emptyString(dataObj[18]));
				dto.setEngineer_Name(dataObj[19] == null ? "" : emptyString(dataObj[19]));
				dto.setEngineer_Phone_Number(dataObj[20] == null ? "" : emptyString(dataObj[20]));
				dto.setNotice_Form_Generation_Date(dataObj[21] == null ? "" : emptyString(dataObj[21]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	
	@Transactional
	@Override
	public List<SpReceivedResponse> spLapsed() {
		List<SpReceivedResponse> response = new ArrayList<SpReceivedResponse>();
		try {
			String sql = "SELECT `SR_Number`, 'SP Lapsed' as `Status`, "
					+ "'SP lapsed due to not doing any action on SP.' as `Comments` FROM `Airtel_SR` "
					+ "where `Accept_Reject` is null and DATEDIFF(CURDATE(),`SP_DATE`) > 21 and `Withdrawal_Type` is null";
			
			List<Object []> dataList = tviCommonDao.getAllTableData(sql);
			SpReceivedResponse resObj = null;
			SrSoReject jsonData = null;
			for(Object [] dataObj : dataList){
				jsonData = new SrSoReject();
				String srNumber = emptyString(dataObj[0]);
				jsonData.setSR_Number(srNumber);
				jsonData.setStatus(emptyString(dataObj[1]));
				jsonData.setComments(emptyString(dataObj[2]));
				
				Gson gsonObj = new Gson();
				String json = gsonObj.toJson(jsonData);
				System.out.println("spLapsed : JSON : "+json);
				logger.info("spLapsed : JSON : "+json);
				String apiURL = "https://ideploy.airtel.com/iDeployAPI/api/sr/updatesrdetailstatus";
				String apiResponse = RestAPI.updateSrDetailStatus(apiURL, json);
				System.out.println("spLapsed : apiResponse : "+apiResponse);
				logger.info("spLapsed : apiResponse : "+apiResponse);
				AirtelApiResponse airtelApi = gsonObj.fromJson(apiResponse, AirtelApiResponse.class);
				//System.out.println("Status : "+airtelApi.isStatus());
				//System.out.println("Messsage : "+airtelApi.getMessage());
				resObj = new SpReceivedResponse();
				resObj.setStatus(airtelApi.isStatus());
				resObj.setMessage(airtelApi.getMessage());
				resObj.setRequestJson(json);
				resObj.setResponseJson(apiResponse);
				response.add(resObj);
				
				boolean status = resObj.isStatus();
				if(status){
					String upSql = "UPDATE `Airtel_SR` set `STATUS` = 'NB105', `Accept_Reject` = 'SP Lapsed' "
							+ "where `SR_Number` = '"+srNumber+"'";
					tviCommonDao.updateBulkdataValue(upSql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private void prepareForSendMail(String srNumber,String spNumber,String soNumber, String circleName, 
			String operatorName, String status, String currentTab, Map<String, String> errorsMap) {
		try {
			String fileName = "MyJsonFile.json";
			String str = CommonFunction.readTextFile(fileName);
			
			JSONObject jsonObject = new JSONObject(str);
			String mailFrom = jsonObject.getString("mailFrom");
			String portalURL = jsonObject.getString("portalURL");
			String ccMailId = jsonObject.getString("ccMailId");
			String mobileNo = jsonObject.getString("mobileNo");
			String regards = jsonObject.getString("regards");
			String send_sms_4_SR = jsonObject.getString("send_sms_4_SR");
			String send_sms_4_SP = jsonObject.getString("send_sms_4_SP");
			String send_sms_4_SO = jsonObject.getString("send_sms_4_SO");
			
			boolean isSendSMS = false;
			
			String successMail = "";
			String successSms = "";
			
			String nbStatus = "";
			String statusSql = "SELECT `DESC_DETAILS`, `DESCRIPTION` FROM `NBS_STATUS`  where `STATUS` = '"+status+"' and `TAB_NAME` = '"+currentTab+"' ";
			List<Object[]> statusResult = tviCommonDao.getNextLevelRole(statusSql);
			int statusRowCount = statusResult.size();
			if(statusRowCount == 1){
				Object[] staObj = statusResult.get(0);
				nbStatus = emptyString(staObj[0]);
			}
			
			
			String sql = "SELECT `User_Role`, `Is_HO_User`, `Action_On_Role` FROM `Notification_Flow` where find_in_set('"+status+"',`Status`) <> 0 and `TAB_NAME` = '"+currentTab+"' ";
			List<Object[]> result = tviCommonDao.getNextLevelRole(sql);
			int rowCount = result.size();
			String pendingRole = "";
			for(Object[] objj : result){
				String userRole = emptyString(objj[0]);
				String isHoUser = emptyString(objj[1]);
				String actionOnRole = emptyString(objj[2]);
				if(actionOnRole.equalsIgnoreCase("Y")){
					pendingRole = userRole;
				}
				
				sql = "select `NAME`, `MOBILE`, `EMAIL_ID` from `EMPLOYEE_MASTER` where `ROLE` = '"+userRole+"' ";
				if(userRole.equalsIgnoreCase("OPCO")){
					sql += " and `ORGANIZATION` = '"+operatorName+"' ";
				}
				if(isHoUser.equalsIgnoreCase("N")){
					sql += " and find_in_set('"+circleName+"',`CIRCLE_NAME`) <> 0 ";
				}
				sql += " and `IS_ACTIVE` = 'Y' ";
				
				List<Object[]> result1 = tviCommonDao.getNextLevelRole(sql);
				if(result1.size() !=0){
					for(Object[] obj : result1){
						String name = obj[0] != null && emptyString(obj[0]).equalsIgnoreCase("") ? "" : emptyString(obj[0]);
						String mobile = obj[1] != null && emptyString(obj[1]).equalsIgnoreCase("") ? "" : emptyString(obj[1]);
						String email = obj[2] != null && emptyString(obj[2]).equalsIgnoreCase("") ? "" : emptyString(obj[2]);
						
						String subject = "";
						StringBuilder msg = new StringBuilder();
						StringBuilder sms = new StringBuilder();
						msg.append("Dear "+name+",<br><br>");
						sms.append("Dear "+name+", ");
						if(rowCount > 1){
							msg.append("FYI<br><br>");
							sms.append("FYI ");
						}
						//Y N N
						if(!srNumber.equalsIgnoreCase("NA") && spNumber.equalsIgnoreCase("NA") && soNumber.equalsIgnoreCase("NA")){
							subject = srNumber;
							if(rowCount == 1){}
							else if(rowCount > 1){
								msg.append(srNumber+" is currently pending with "+pendingRole+".<br><br>");
								sms.append(srNumber+" is currently pending with "+pendingRole+". ");
							}
							else{
								msg.append(srNumber+" is pending for your action.<br><br>");
								sms.append(srNumber+" is pending for your action. ");
							}
							msg.append("<b>SR Detail:</b><br>");
							msg.append("SR No : "+srNumber+"<br>");
							msg.append("Status : "+nbStatus+"<br>");
							
							sms.append("SR Detail: ");
							sms.append("SR No : "+srNumber+" ");
							sms.append("Status : "+nbStatus+" ");
							
							if(send_sms_4_SR.equalsIgnoreCase("Yes"))
								isSendSMS = true;
						}
						// Y Y N
						else if(!srNumber.equalsIgnoreCase("NA") && !spNumber.equalsIgnoreCase("NA") && soNumber.equalsIgnoreCase("NA")){
							subject = spNumber;
							if(rowCount == 1){}
							else if(rowCount > 1){
								msg.append(spNumber+" is currently pending with "+pendingRole+".<br><br>");
								sms.append(spNumber+" is currently pending with "+pendingRole+". ");
							}
							else{
								msg.append(spNumber+" is pending for your action.<br><br>");
								sms.append(spNumber+" is pending for your action. ");
							}	
							msg.append("<b>SP Detail:</b><br>");
							msg.append("SP No : "+spNumber+"<br>");
							msg.append("Status : "+nbStatus+"<br>");
							
							sms.append("SP Detail: ");
							sms.append("SP No : "+spNumber+" ");
							sms.append("Status : "+nbStatus+" ");
							
							if(send_sms_4_SP.equalsIgnoreCase("Yes"))
								isSendSMS = true;
						}
						// Y Y Y
						else if(!srNumber.equalsIgnoreCase("NA") && !spNumber.equalsIgnoreCase("NA") && !soNumber.equalsIgnoreCase("NA")){
							subject = soNumber;
							if(rowCount == 1){}
							else if(rowCount > 1){
								msg.append(soNumber+" is currently pending with "+pendingRole+".<br><br>");
								sms.append(soNumber+" is currently pending with "+pendingRole+". ");
							}
							else{
								msg.append(soNumber+" is pending for your action.<br><br>");
								sms.append(soNumber+" is pending for your action. ");
							}
							msg.append("<b>SO Detail:</b><br>");
							msg.append("SO No : "+soNumber+"<br>");
							msg.append("Status : "+nbStatus+"<br>");
							
							sms.append("SO Detail: ");
							sms.append("SO No : "+soNumber+" ");
							sms.append("Status : "+nbStatus+" ");
							
							if(send_sms_4_SO.equalsIgnoreCase("Yes"))
								isSendSMS = true;
						}
						
						msg.append("Operator Name : "+operatorName+"<br>");
						msg.append("Circle : "+circleName+"<br><br>");
						msg.append("Click here : <a href='"+portalURL+"' target='blank'>Customer Portal</a> <br><br>");
						msg.append(regards);
						
						sms.append("Operator Name : "+operatorName+" ");
						sms.append("Circle : "+circleName+" ");
						sms.append("Click here : "+portalURL+" ");
						sms.append("- CP Team");
						
						String mailStatus = SendMail.sendMail(mailFrom, email, ccMailId, "", subject+" Pending for action", msg.toString(), null);
						if(mailStatus != null) successMail += email+",";
							
						if(isSendSMS){
							int smsStatus = SendSMS.sendSms(sms.toString(), mobile+","+mobileNo);
							if(smsStatus == 200) successSms += mobile+",";
						}
					}
				}
			}
			errorsMap.put("MAIL_STATUS_"+srNumber, successMail);
			errorsMap.put("SMS_STATUS_"+srNumber, successSms);
		}
		catch (Exception e) {
			e.printStackTrace();
			errorsMap.put("Error_In_prepareForSendMail", e.toString());
		}
	}

	@Transactional
	@Override
	public Response<Map<String, String>> changeAirtelBulkSrStatus(BulkDTO jsonData) {
		Response<Map<String, String>> response = new Response<>();
		Map<String, String> errorsMap = new HashMap<String, String>();
		try {
			AirtelSrAuditEntity audEnt = null;
			for(BulkSrDTO b : jsonData.getCheckCheckedList()){
				String updateTable = "update Airtel_SR set STATUS = '"+jsonData.getBulkAction()+"', `Sharing_Potential`='"+jsonData.getBulkSharingPotential()+"', UPDATE_DATE=CURRENT_TIMESTAMP where SR_Number = '"+b.getSrNumber()+"' ";
				int c = tviCommonDao.updateBulkdataValue(updateTable);
				if(c != 0){
					audEnt = new AirtelSrAuditEntity();
					audEnt.setSrNumber(b.getSrNumber());
					audEnt.setEmpId(jsonData.getLoginEmpId());
					audEnt.setRamark(jsonData.getBulkRemark());
					audEnt.setAction(jsonData.getBulkAction());
					audEnt.setCreateDate(new Date());
					tviCommonDao.saveOrUpdateEntityData(audEnt);
				}
				
				prepareForSendMail(b.getSrNumber(), "NA", "NA", b.getCircleName(), b.getOperator(), 
						jsonData.getBulkAction(), b.getTabName(), errorsMap);
					
			}
			response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
			response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			response.setErrorsMap(errorsMap);
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}

	@Override
	public Response<ExportDataResponse> exportData(ExportDto jsonData) {
		Response<ExportDataResponse> response = new Response<ExportDataResponse>();
		List<ExportDataResponse> wrappedList = new ArrayList<ExportDataResponse>();
		try {
			prepareExportData(wrappedList, jsonData);
			if(wrappedList.size() != 0){
				response.setWrappedList(wrappedList);
				response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
				response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			}
			else{
				response.setResponseCode(ReturnsCode.NO_RECORD_FOUND_CODE);
				response.setResponseDesc(ReturnsCode.NO_RECORD_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_DATABASE_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}
	
	private void prepareExportData(List<ExportDataResponse> wrappedList, ExportDto jsonData) {
		try {
			String filterSql = "";
			if(!jsonData.getFilterSrNumber().equalsIgnoreCase("")){
				filterSql = "and (sr.SR_Number like '"+jsonData.getFilterSrNumber()+"' "
						+ "or sr.SP_Number like '"+jsonData.getFilterSrNumber()+"'"
						+ "or sr.SO_Number like '"+jsonData.getFilterSrNumber()+"') ";
			}
			if(jsonData.getIsHoUser().equalsIgnoreCase("N")){
				String circleName = jsonData.getCircleName().replace(",","','");
				filterSql += " and sr.CircleCode in ('"+circleName+"') ";
			}
			if(!jsonData.getFilterCircleName().equalsIgnoreCase("")){
				filterSql += " and sr.CircleCode in ('"+jsonData.getFilterCircleName()+"') ";
			}
			if(!jsonData.getFilterTviSiteId().equalsIgnoreCase("")){
				filterSql += " and sr.TOCO_Site_Id like '"+jsonData.getFilterTviSiteId()+"' ";
			}
			if(!jsonData.getFilterSiteId().equalsIgnoreCase("")){
				filterSql += " and sr.Customer_Site_Id like '"+jsonData.getFilterSiteId()+"' ";
			}
			if(!jsonData.getFilterProductType().equalsIgnoreCase("")){
				filterSql += " and sr.TAB_NAME like '"+jsonData.getFilterProductType()+"' ";
			}
			if(!jsonData.getFilterStartDate().equalsIgnoreCase("")){
				filterSql += " and sr.SR_DATE >= '"+jsonData.getFilterStartDate()+"' ";
			}
			if(!jsonData.getFilterEndDate().equalsIgnoreCase("")){
				filterSql += " and sr.SR_DATE <= '"+jsonData.getFilterEndDate()+"' ";
			}
			
			String pendingSql = "";
			if(jsonData.isDashboard()){
				String penSql = "SELECT `TabName`, `CurrentStatus` FROM `StatusAction` "
						+ "WHERE `Role` = '"+jsonData.getLoginEmpRole()+"' and `IsActive` = 1";
				List<Object []> penSqlResult = tviCommonDao.getAllTableData(penSql);
				int penResSize = penSqlResult.size();
				if(penResSize > 0){
					pendingSql += "and (";
					int i=0;
					for(Object [] penObj : penSqlResult){
						String tabName = emptyString(penObj[0]);
						String currentStatus = emptyString(penObj[1]);
						currentStatus = currentStatus.replace(",", "','");
						pendingSql += " (sr.TAB_NAME = '"+tabName+"' and sr.STATUS in ('"+currentStatus+"')) ";
						if(i < penResSize-1){
							pendingSql += "OR";
						}
						i++;
					}
					pendingSql += ")";
				}	
			}
			String sql = "SELECT sr.SR_Number, sr.SP_Number, sr.SO_Number, sr.Customer_Site_Id, sr.Customer_Site_Name, "
					+ "(case when sr.SP_Latitude is null then pd.P1_Latitude else sr.SP_Latitude end) as Latitude, "
					+ "(case when sr.SP_Longitude is null then pd.P1_Longitude else sr.SP_Longitude end ) as Longitude,"
					+ "tm.Org_Tab_Name, "
					+ "sr.SR_DATE, sr.SP_DATE, sr.SO_DATE, sr.RFI_DATE, "
					+ "sr.RFI_ACCEPTED_DATE, sr.CircleCode, s.AirtelStatus, sr.TOCO_Site_Id, sr.Project_Name, "
					+ "sr.Upgrade_Type, sr.Sharing_Potential, aud.REMARK as Sharing_Potential_Remark, s.DESCRIPTION "
					+ "FROM Airtel_SR sr "
					+ "left join Airtel_Priority_Details pd on sr.SR_Number = pd.SR_Number "
					+ "left join Airtel_Additional_Information ai on pd.SR_Number = ai.SR_Number "
					+ "left join NBS_STATUS s on sr.STATUS = s.STATUS and sr.TAB_NAME = s.TAB_NAME "
					+ "left join Tab_Master tm on sr.TAB_NAME = tm.Tab_Name "
					+ "left join Airtel_SR_Audit aud on sr.SR_Number = aud.SR_NUMBER and aud.ACTION = 'NB03' and aud.SR_NUMBER like 'NB_SR%' "
					+ "where 1=1 "+pendingSql+" "+filterSql+" Order by sr.CREATE_DATE desc";
			List<Object []> result = tviCommonDao.getAllTableData(sql);
			ExportDataResponse data = null;
			if(result.size() !=0){
				for(Object [] obj : result){
					data = new ExportDataResponse();
					data.setSrNumber(obj[0] == null ? "" : emptyString(obj[0]));
					data.setSpNumber(obj[1] == null ? "" : emptyString(obj[1]));
					data.setSoNumber(obj[2] == null ? "" : emptyString(obj[2]));
					data.setAirtelSiteId(obj[3] == null ? "" : emptyString(obj[3]));
					data.setSiteName(obj[4] == null ? "" : emptyString(obj[4]));
					data.setLatitude(obj[5] == null ? "" : emptyString(obj[5]));
					data.setLongitude(obj[6] == null ? "" : emptyString(obj[6]));
					data.setOrgTabName(obj[7] == null ? "" : emptyString(obj[7]));
					data.setSrRaiseDate(obj[8] == null ? "" : emptyString(obj[8]));
					data.setSpRaiseDate(obj[9] == null ? "" : emptyString(obj[9]));
					data.setSoRaiseDate(obj[10] == null ? "" : emptyString(obj[10]));
					data.setRfiDate(obj[11] == null ? "" : emptyString(obj[11]));
					data.setRfiAcceptedDate(obj[12] == null ? "" : emptyString(obj[12]));
					data.setCircleName(obj[13] == null ? "" : emptyString(obj[13]));
					data.setSrStatus(obj[14] == null ? "" : emptyString(obj[14]));
					data.setTviSiteId(obj[15] == null ? "" : emptyString(obj[15]));
					data.setProjectName(obj[16] == null ? "" : emptyString(obj[16]));
					data.setUpgradeType(obj[17] == null ? "" : emptyString(obj[17]));
					data.setSharingPotential(obj[18] == null ? "" : emptyString(obj[18]));
					data.setSharingPotentialRemark(obj[19] == null ? "" : emptyString(obj[19]));
					data.setPendingTo(obj[20] == null ? "" : emptyString(obj[20]));
					wrappedList.add(data);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private String emptyString(Object obj){
		String value = String.valueOf(obj);
		if(value.equalsIgnoreCase("null"))
			return "";
		return value;
	}
	
	private String emptyNumeric(Object obj){
		String value = String.valueOf(obj);
		if(value.equalsIgnoreCase("null"))
			return "0";
		return value;
	}
	
	@Override
	public SpReceivedResponse spReceived_test(SpReceivedTest jsonData) {
		SpReceivedResponse response = spReceived(jsonData.getSrNumber(), jsonData.getCurrentStatus());
		return response;
	}
	
	@Override
	public SpReceivedResponse spReceivedSharing_test(SpReceivedTest jsonData) {
		SpReceivedResponse response = spReceivedSharing(jsonData.getSrNumber(), jsonData.getCurrentStatus());
		return response;
	}

	@Override
	public SpReceivedResponse spReceivedUpgrade_test(SpReceivedTest jsonData) {
		SpReceivedResponse response = spReceivedUpgrade(jsonData.getSrNumber(), jsonData.getCurrentStatus());
		return response;
	}
}
