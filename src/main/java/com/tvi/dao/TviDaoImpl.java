package com.tvi.dao;

import java.io.File;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.tvi.common.persistence.entity.dao.TviCommonDao;
import com.tvi.constant.CommonFunction;
import com.tvi.constant.Constant;
import com.tvi.constant.Response;
import com.tvi.constant.ReturnsCode;
import com.tvi.constant.SendMail;
import com.tvi.constant.SendSMS;
import com.tvi.dto.BulkDTO;
import com.tvi.dto.BulkSrDTO;
import com.tvi.dto.CommonDTO;
import com.tvi.dto.ComplainDTO;
import com.tvi.dto.NbsAuditDTO;
import com.tvi.dto.SystemParamDTO;
import com.tvi.entity.ComplainEntity;
import com.tvi.entity.EmployeeMasterEntity;
import com.tvi.entity.NbsAuditEntity;
import com.tvi.entity.NbsMasterDetEntity;
import com.tvi.entity.NbsMasterHdrEntity;
import com.tvi.entity.ResponseTableModel;
import com.tvi.request.BBURequest;
import com.tvi.request.BTSRequest;
import com.tvi.request.EmployeeActionRequest;
import com.tvi.request.GSMAntennaRequest;
import com.tvi.request.HPSCAntennaRequest;
import com.tvi.request.IP55Request;
import com.tvi.request.LoginRequest;
import com.tvi.request.MCBRequest;
import com.tvi.request.MassiveMIMORequest;
import com.tvi.request.MicrowaveRequest;
import com.tvi.request.ODSCRequest;
import com.tvi.request.PoleRequest;
import com.tvi.request.RFAntennaRequest;
import com.tvi.request.RRURequest;
import com.tvi.request.RRU_SwapRequest;
import com.tvi.request.SaveNBSRequest;
import com.tvi.response.EmployeeResponse;
import com.tvi.response.LayoutResponse;
import com.tvi.response.LoginResponse;
import com.tvi.response.MenuResponse;
import com.tvi.response.SubMenuResponse;

public class TviDaoImpl implements TviDao {

	@Autowired
	TviCommonDao tviCommonDao;
	
	ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource("log4j.xml").getFile());
	final static Logger logger = Logger.getLogger(TviDaoImpl.class);

	public Response<LoginResponse> userLogin(LoginRequest jsonData) {
		Response<LoginResponse> response = new Response<LoginResponse>();
		List<LoginResponse> list = new ArrayList<LoginResponse>();
		LoginResponse data = null;
		try {
			List<Object[]> result = tviCommonDao.userLogin(jsonData);
			if(result.size()!=0){
				for(Object[] obj : result){
					data = new LoginResponse();
					data.setEmpName(String.valueOf(obj[0]));
					data.setEmpRole(String.valueOf(obj[1]));
					data.setEmpId(String.valueOf(obj[2]));
					data.setIsHoUser(String.valueOf(obj[3]));
					if(data.getIsHoUser().equalsIgnoreCase("N")){
						data.setCircleName(String.valueOf(obj[4]));
					}
					if(obj[5] != null && !String.valueOf(obj[5]).equalsIgnoreCase("")){
						data.setOperator(String.valueOf(obj[5]));
					}
					list.add(data);
				}
				response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
				response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			}
			else{
				response.setResponseCode(ReturnsCode.NO_RECORDS_FOUND_CODE);
				response.setResponseDesc(ReturnsCode.NO_RECORD_FOUND);
			}
			response.setWrappedList(list);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}

	public Response<LayoutResponse> getMenuListByRoleName(LoginRequest jsonData) {
		Response<LayoutResponse> response = new Response<LayoutResponse>();
		List<LayoutResponse> list = new ArrayList<LayoutResponse>();
		LayoutResponse data = null;
		
		List<MenuResponse> menuList = null;
		MenuResponse menuData = null;
		
		List<SubMenuResponse> submenuList = null;
		SubMenuResponse submenuData = null;
		try {
			List<Object [] > menuResult = tviCommonDao.getMenuListByRoleName(jsonData);
			if(menuResult.size() != 0){
				data = new LayoutResponse();
				menuList = new ArrayList<MenuResponse>();
				for(Object [] menuObj :menuResult){
					menuData = new MenuResponse();
					menuData.setMenuName(String.valueOf(menuObj[1]));
					menuData.setRouterLink(String.valueOf(menuObj[2]));
					menuData.setIcon(String.valueOf(menuObj[3]));
					menuData.setIsSubMenu(String.valueOf(menuObj[4]));
					if(menuData.getIsSubMenu().equalsIgnoreCase("Y")){
						submenuList = new ArrayList<SubMenuResponse>();
						String menuId = String.valueOf(menuObj[0]);
						List<Object [] > subMenuResult = tviCommonDao.getSubmenuByMenuId(menuId,jsonData.getUserRole(),jsonData.getOperator());
						if(subMenuResult.size()!=0){
							for(Object[] submenuObj : subMenuResult){
								submenuData = new SubMenuResponse();
								submenuData.setSubmenuName(String.valueOf(submenuObj[1]));
								submenuData.setRouterLink(String.valueOf(submenuObj[2]));
								submenuData.setIcon(String.valueOf(submenuObj[3]));
								submenuList.add(submenuData);
							}
						}
						menuData.setSubmenuList(submenuList);
					}
					menuList.add(menuData);
				}
				data.setMenuList(menuList);
				list.add(data);
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

	@Transactional
	public Response<Map<String, String>> saveNBSDetails(SaveNBSRequest jsonData) {
		logger.error("========== saveNBSDetails =====Start======== ");
		Response<Map<String, String>> response = new Response<Map<String,String>>();
		Map<String, String> errorsMap = new HashMap<String, String>();
		try {
			Date d = new Date();
			String srNumber = "";
			if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS)){
				String sql = "SELECT `SR_NUMBER` FROM `NBS_MASTER_HDR` where `TAB_NAME` = '"+Constant.CreateNBS+"' and `LATITUDE_1` = '"+jsonData.getLatitude1()+"' and `LONGITUDE_1` = '"+jsonData.getLongitude1()+"' and `STATUS` not in ('NB97','NB98','NB99','NB100') ";
				boolean isExistByLatong = tviCommonDao.isExistSR(sql);
				if(isExistByLatong){
					response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
					response.setResponseDesc(ReturnsCode.ALREADY_EXIST+" SR on `Latitude 1` = "+jsonData.getLatitude1()+" and `Longitude 1` = "+jsonData.getLongitude1());
					return response;
				}
				String sql2 = "SELECT `SR_NUMBER` FROM `NBS_MASTER_HDR` where `TAB_NAME` = '"+Constant.CreateNBS+"' and `AIRTEL_SITE_ID` = '"+jsonData.getAirtelSiteId()+"' and `STATUS` not in ('NB97','NB98','NB99','NB100') ";
				boolean isExistBySiteId = tviCommonDao.isExistSR(sql2);
				if(isExistBySiteId){
					response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
					response.setResponseDesc(ReturnsCode.ALREADY_EXIST+" SR on `AIRTEL_SITE_ID` = "+jsonData.getAirtelSiteId());
					return response;
				}
				
				srNumber = "NB_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDet(jsonData);
			}
			else if (jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor)){
				srNumber = "OD_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetOfODSCAnchor(jsonData);
			}
			else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.New_Tenency)){
				srNumber = "NT_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDet(jsonData);
			}
			else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.Site_Upgrade)){
				srNumber = "SU_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetOfSiteUpgrade(jsonData);
			}
			else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.I_WAN)){
				srNumber = "IWAN_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetOfIwan(jsonData);
			}
			else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC)){
				srNumber = "HPSC_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetOfODSCAnchor(jsonData);
			}
			else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.MCU)){
				srNumber = "MCU_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetOfMcu(jsonData);
			}
			else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.UBR)){
				srNumber = "UBR_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetOfIwan(jsonData);
			}
			else if (jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing)){
				srNumber = "OD_Share_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetODSCSharing(jsonData);
			}
			else if (jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)){
				srNumber = "HPSC_Share_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetODSCSharing(jsonData);
			}
			
			else if (jsonData.getCurrentTab().equalsIgnoreCase(Constant.Fibre_Termination)){
				srNumber = "FT_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetOfFibreTermination(jsonData);
			}
			else if (jsonData.getCurrentTab().equalsIgnoreCase(Constant.TCL_Redwin)){
				srNumber = "Redwin_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetOfTclRewin(jsonData);
			}
			else if (jsonData.getCurrentTab().equalsIgnoreCase(Constant.HEX_OLT)){
				srNumber = "HEX_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetOfHexOlt(jsonData);
			}
			else if (jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split)){
				srNumber = "SS_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetSmartSplit(jsonData);
			}
			else if (jsonData.getCurrentTab().equalsIgnoreCase(Constant.TCU)){
				srNumber = "TCU_SR"+d.getTime();
				jsonData.setSrNumber(srNumber);
				tviCommonDao.insertHdrAndDetTCU(jsonData);
			}
						
			prepareForSendMail(srNumber, "NA", "NA", jsonData.getCircleName(), jsonData.getOperator(), "NB01", jsonData.getCurrentTab(),errorsMap);
			
			NbsAuditEntity audEnt = new NbsAuditEntity();
			audEnt.setSrNumber(jsonData.getSrNumber());
			audEnt.setEmpId(jsonData.getLoginEmpId());
			audEnt.setRamark(jsonData.getRemark());
			audEnt.setAction("NB01");
			audEnt.setCreateDate(new Date());
			tviCommonDao.saveOrUpdateEntityData(audEnt);
			
			response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
			response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			response.setErrorsMap(errorsMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
			logger.error("========== saveNBSDetails ======End======= "+e);
		}
		return response;
	}


	@Override
	public Response<SystemParamDTO> getAllCircleName() {
		Response<SystemParamDTO> response = new Response<SystemParamDTO>();
		List<SystemParamDTO> list = new ArrayList<SystemParamDTO>();
		SystemParamDTO data = null;
		try {
			List<String> result = tviCommonDao.getAllCircleName();
			for(String r : result){
				data = new SystemParamDTO();
				data.setParamCode(r);
				data.setParamDesc(r+" ");
				list.add(data);
			}
			response.setWrappedList(list);
			response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
			response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			
		}  catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}

	@Override
	public Response<EmployeeResponse> getAllEmployeeList() {
		Response<EmployeeResponse> response = new Response<EmployeeResponse>();
		List<EmployeeResponse> list = new ArrayList<EmployeeResponse>();
		EmployeeResponse data = null;
		
		try {
			List<EmployeeMasterEntity> result = tviCommonDao.getAllEmployeeList();
			if(result.size() != 0){
				for(EmployeeMasterEntity em : result){
					data = new EmployeeResponse();
					data.setPrimaryId(em.getId());
					data.setEmpId(em.getEmployeeId());
					data.setEmpName(em.getName());
					data.setEmpRole(em.getRole());
					data.setEmailId(em.getEmailId());
					data.setMobile(em.getMobile());
					data.setCircleName(em.getCircleName());
					data.setIsHoUser(em.getIsHoUser());
					data.setIsActive(em.getIsActive());
					if(data.getIsActive().equalsIgnoreCase("N")){
						data.setActiveStatus("Deactivate");
					}
					list.add(data);
				}
				response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
				response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			}
			else{
				response.setResponseCode(ReturnsCode.NO_RECORDS_FOUND_CODE);
				response.setResponseDesc(ReturnsCode.NO_RECORD_FOUND);
			}
			
			response.setWrappedList(list);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}

	@Transactional
	@Override
	public Response<Map<String, String>> activeOrDeactiveEmployee(EmployeeActionRequest jsonData) {
		Response<Map<String, String>> response = new Response<Map<String,String>>();
		try {
			EmployeeMasterEntity emEntity = tviCommonDao.getEmployeeDetails(jsonData);
			if(emEntity != null){
				emEntity.setIsActive(jsonData.getSearchType());
				Object obj = tviCommonDao.saveOrUpdateEmpData(emEntity);
				System.out.println(obj);
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

	@Transactional
	@Override
	public Response<SaveNBSRequest> getNbsDetails(CommonDTO jsonData) {
		Response<SaveNBSRequest> response = new Response<SaveNBSRequest>();
		List<SaveNBSRequest> list = new ArrayList<SaveNBSRequest>();
		String pagination = "";
		SaveNBSRequest data = null;
		
		try {
			Map<String, String> nbsStatusMap = new HashMap<String, String>();
			Map<String, String> odscAnchorStatusMap = new HashMap<String, String>();
			Map<String, String> newTenencyStatusMap = new HashMap<String, String>();
			Map<String, String> siteUpgradeStatusMap = new HashMap<String, String>();
			Map<String, String> iWanStatusMap = new HashMap<String, String>();
			Map<String, String> hpscStatusMap = new HashMap<String, String>();
			Map<String, String> mcuStatusMap = new HashMap<String, String>();
			Map<String, String> fiberTerminationStatusMap = new HashMap<String, String>();
			Map<String, String> ubrStatusMap = new HashMap<String, String>();
			Map<String, String> odscSharingStatusMap = new HashMap<String, String>();
			Map<String, String> hpscSharingStatusMap = new HashMap<String, String>();
			Map<String, String> tclRedwinStatusMap = new HashMap<String, String>();
			Map<String, String> hexOltStatusMap = new HashMap<String, String>();
			Map<String, String> smartSplitStatusMap = new HashMap<String, String>();
			Map<String, String> tcuStatusMap = new HashMap<String, String>();
			List<Object[]> result = tviCommonDao.getAllNbsStatus();
			for(Object [] obj : result){
				String status = String.valueOf(obj[0]);
				String description = String.valueOf(obj[1]);
				String tabName = String.valueOf(obj[2]);
				String decsDetails = String.valueOf(obj[3]);
				if(tabName.equalsIgnoreCase(Constant.CreateNBS)){
					nbsStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.ODSC_Anchor)){
					odscAnchorStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.New_Tenency)){
					newTenencyStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.Site_Upgrade)){
					siteUpgradeStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.I_WAN)){
					iWanStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.HPSC)){
					hpscStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.MCU)){
					mcuStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.Fibre_Termination)){
					fiberTerminationStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.UBR)){
					ubrStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.ODSC_Sharing)){
					odscSharingStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.HPSC_Sharing)){
					hpscSharingStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.TCL_Redwin)){
					tclRedwinStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.HEX_OLT)){
					hexOltStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.Smart_Split)){
					smartSplitStatusMap.put(status, description+":"+decsDetails);
				}
				else if(tabName.equalsIgnoreCase(Constant.TCU)){
					tcuStatusMap.put(status, description+":"+decsDetails);
				}
			}
			//Map<String, String> classCircleMap = new HashMap<String, String>();
			
			String dateFormate = Constant.DATE_IN_DDMMYYYY_FORMATE;
			int maxRecord = 0;
			List<NbsMasterHdrEntity> nbsHdr = tviCommonDao.getNbsHdrByCircleName(jsonData);
			maxRecord += jsonData.getMaxRecord();
			if(nbsHdr.size() != 0){
				for(NbsMasterHdrEntity h : nbsHdr){
					data = new SaveNBSRequest();
					data.setLoginEmpRole(jsonData.getLoginEmpRole());
					data.setSrNumber(h.getSrNumber());
					data.setOperator(h.getOperator());
					data.setSrRaiseDate(CommonFunction.convertDateFormatAsGiven(h.getSrDate(), dateFormate));
					if(h.getSpNumber()!=null)
						data.setSpNumber(h.getSpNumber());
					
					if(h.getSoNumber()!=null)
						data.setSoNumber(h.getSoNumber());
					
					if(h.getRfiDate() !=null)
						data.setRfiDate(CommonFunction.convertDateFormatAsGiven(h.getRfiDate(), dateFormate));
					
					if(h.getRfiAcceptedDate() !=null)
						data.setRfiAcceptedDate(CommonFunction.convertDateFormatAsGiven(h.getRfiAcceptedDate(), dateFormate));
					
					if(h.getRfsDate() !=null)
						data.setRfsDate(CommonFunction.convertDateFormatAsGiven(h.getRfsDate(), dateFormate));
					
					if(h.getSpDate() !=null)
						data.setSpRaiseDate(CommonFunction.convertDateFormatAsGiven(h.getSpDate(), dateFormate));
					
					if(h.getSoDate() !=null)
						data.setSoRaiseDate(CommonFunction.convertDateFormatAsGiven(h.getSoDate(), dateFormate));
					
					data.setCurrentTab(h.getTabName());
					if(data.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS))
						data.setProductType("Macro Anchor");
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor))
						data.setProductType("ODSC Anchor");
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.New_Tenency))
						data.setProductType("New Tenency");
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.Site_Upgrade))
						data.setProductType("Site Upgrade");
					else
						data.setProductType(data.getCurrentTab());
					data.setCircleName(h.getCircleName());
					data.setTviSiteId(h.getTviSiteId());
					data.setSiteName(h.getSiteName());
					data.setAirtelSiteId(h.getAirtelSiteId());
					data.setAirtelLocatorId(h.getAirtelLocatorId());
					data.setTechnology(h.getTechnology());
					data.setExtensionType(h.getExtensionType());
					data.setSiteAddress(h.getSiteAddress());
					//data.setCity(h.getCity());
					//data.setTownVillage(h.getTownVillage());
					data.setDistrict(h.getDistrict());
					data.setState(h.getState());
					//data.setPincode(h.getPincode());
					data.setClutter(h.getClutter());
					data.setWindSpeed(h.getWindSpeed());
					data.setSiteType(h.getSiteType());
					//data.setTowerHeight(h.getTowerHeight());
					data.setAglRequired(h.getAglRequired());
					data.setFrequencyUserByOpco(h.getFrequencyUserByOpco());
					//data.setOpcoId(h.getOpcoId());
					data.setLatitude1(h.getLatitude1());
					data.setLongitude1(h.getLongitude1());
					data.setLatitude2(h.getLatitude2());
					data.setLongitude2(h.getLongitude2());
					data.setSuggestedLatitude(h.getSuggestedLatitude());
					data.setSuggestedLongitude(h.getSuggestedLongitude());
					//data.setEbAvailability(h.getEbAvailability());
					data.setEbAvailabilityDistance(h.getEbAvailabilityDistance());
					data.setDgAvailability(h.getDgAvailability());
					data.setNoOfRFAntenna(h.getNoOfRFAntenna());
					data.setNoOfRFAntenna_Delete(h.getNoOfRFAntenna_Delete());
					data.setNoOfRFAntenna_Add(h.getNoOfRFAntenna_Add());
					//data.setNoOfGSMAntenna(h.getNoOfGsmAntenna());
					data.setNoOfMicrowave(h.getNoOfMicrowave());
					data.setBtsType(h.getBtsType());
					data.setNoOfBBU(h.getNoOfBbu());
					data.setNoOfRRU(h.getNoOfRru());
					data.setNoOfBTS(h.getNoOfBts());
					data.setNoOfMCB(h.getNO_OF_MCB());
					data.setNoOfIP55(h.getNO_OF_IP55());
					data.setNoOfRRU_Delete(h.getNO_OF_RRU_Delete());
					data.setNoOfRRU_Add(h.getNO_OF_RRU_Add());
					data.setNoOfBBU_Delete(h.getNoOfBbuDelete());
					data.setNoOfBBU_Add(h.getNoOfBbuAdd());
					data.setNoOfMicrowave_Delete(h.getNoOfMicrowaveDelete());
					data.setNoOfMicrowave_Add(h.getNoOfMicrowaveAdd());
					data.setMwRack(h.getMW_RACK());
					data.setNoOfUBR_Antenna(h.getNoOfUBR_Antenna());
					data.setFiberLength(h.getFiberLength());
					data.setAdditionalLoad(h.getAdditionalLoad());
					data.setFloorLength(h.getFloorLength());
					//data.setFloorWidth(h.getFloorWidth());
					//data.setFloorHeight(h.getFloorHeight());
					data.setTypeOfAntenna(h.getTypeOfAntenna());
					//data.setBtsPower(h.getBtsPower());
					//data.setTotalPowerConsumption(h.getTotalPowerConsumption());
					//data.setNoOfSpaceRequired(h.getNoOfSpaceRequired());
					data.setPowerConsumption(h.getPowerConsumption());
					data.setNoOfUSpaceRequired(h.getNoOfUSpaceRequired());
					data.setLoadOfU(h.getLoadOfU());
					data.setCountOfRfFilter(h.getCountOfRfFilter());
					data.setFiberEntry(h.getFiberEntry());
					data.setFiberTermination(h.getFiberTermination());
					data.setWeightOfAdditionalAntenna(h.getWeightOfAdditionalAntenna());
					//data.setRfAntennaMountHeight(h.getRfAntennaMountHeight());
					//data.setMwAntennaMountHeight(h.getMwAntennaMountHeight());
					//data.setExistingAirtelConfigurationBeforeMIMO(h.getExistingAirtelConfigurationBeforeMIMO());
					data.setNoOfMassiveMIMOAntenna(h.getNoOfMassiveMIMOAntenna());
					data.setPowerRequirement(h.getPowerRequirement());
					//data.setPowerThresholdInCaseOfMIMOExpansion(h.getPowerThresholdInCaseOfMIMOExpansion());
					//data.setAdditionalLLRDueToAdditionalMIMO(h.getAdditionalLLRDueToAdditionalMIMO());
					//data.setSmartSplitType(h.getSmartSplitType());
					data.setFloorSpaceOfODCabinet(h.getFloorSpaceOfODCabinet());
					//data.setAcPowerLoad(h.getAcPowerLoad());
					//data.setTotalPowerRequired(h.getTotalPowerRequired());
					//data.setCowType(h.getCowType());
					//data.setServiceContractPeriod(h.getServiceContractPeriod());
					//data.setRatedPowerConsumption(h.getRatedPowerConsumption());
					//data.setTowerWeight(h.getTowerWeight());
					//data.setRackSpaceForBBU(h.getRackSpaceForBBU());
					//data.setRackSpaceForMW(h.getRackSpaceForMW());
					data.setPoleHeight(h.getPoleHeight());
					//data.setAdditionalPoles(h.getAdditionalPoles());
					data.setAirtelBackhaul(h.getAirtelBackhaul());
					data.setAcDcBackup(h.getAcDcBackup());
					data.setAdditionalPowerBackup2Hrs(h.getAdditionalPowerBackup2Hrs());
					data.setTotalRatedPower(h.getTotalRatedPower());
					data.setCamuflauging(h.getCamuflauging());
					//data.setNoOfSmallCell(h.getNoOfSmallCell());
					//data.setAreaPerSmallCell(h.getAreaPerSmallCell());
					//data.setDepthPerSmallCell(h.getDepthPerSmallCell());
					//data.setHeightOfHighestAntenna(h.getHeightOfHighestAntenna());
					//data.setWeightOfSmallCell(h.getWeightOfSmallCell());
					//data.setRatedPower(h.getRatedPower());
					data.setuSpace(h.getuSpace());
					data.setuSpace_ethernet(h.getuSpace_ethernet());
					//data.setAcbackupRequired2Hrs(h.getAcbackupRequired2Hrs());
					//data.setAdditionalODSCRequired(h.getAdditionalODSCRequired());
					data.setAdditionalPowerRequired(h.getAdditionalPowerRequired());
					//data.setExistingLLROfTVISite(h.getExistingLLROfTVISite());
					//data.setAdditionalLLRDueToAdditionalODSC(h.getAdditionalLLRDueToAdditionalODSC());
					//data.setCumulativePANIndiaODCSSharingSOCount(h.getCumulativePANIndiaODCSSharingSOCount());
					data.setConversionOfSharingODSCIntoMacroSite(h.getConversionOfSharingODSCIntoMacroSite());
					data.setNoOfODSC(h.getNoOfODSC());
					data.setAglRequiredODSC(h.getAglRequiredODSC());
					data.setAirtelBackhaul(h.getAirtelBackhaul());
					data.setAcDcBackup(h.getAcDcBackup());
					data.setAdditionalPowerBackup2Hrs(h.getAdditionalPowerBackup2Hrs());
					data.setCamuflauging(h.getCamuflauging());
					data.setMcbRequiredInAmp(h.getMCB_Required_In_AMP());
					data.setRowSpace(h.getFloorSpaceOfODCabinet());
					data.setAcPower(h.getPowerRequirement());
					data.setNoOfMcb(h.getNO_OF_MCB());
					data.setNoOfPole(h.getNoOfPoles());
					data.setNoOfHPSCAntenna(h.getNoOfHpscAntenna());
					data.setMcbAmp(h.getMCB_Required_In_AMP());
					data.setBatteryBackup(h.getAcDcBackup());
					data.setDgStatus(h.getDgAvailability());
					data.setPowerRatingOfEquipment(h.getPowerRatingOfEquipment());
					data.setNbsProductType(h.getProductType());
					data.setAdditionalBB(h.getAdditionalBB());
					data.setHeadLoad(h.getHeadLoad());
					data.setEbConnection(h.getEbConnection());
					data.setTotalWeightOnTower(h.getTotalWeightOnTower());
					data.setRemark(h.getRemark());
					data.setStatus(h.getStatus());
					
					if(data.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB23") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = nbsStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
						
					}
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB23") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = odscAnchorStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
					}
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB23") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = odscSharingStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
					}
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.New_Tenency)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB14") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = newTenencyStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
					}
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.Site_Upgrade)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB12") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = siteUpgradeStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
					}
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.I_WAN)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB12") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = iWanStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
					}
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.HPSC)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB23") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = hpscStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
						
					}
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB23") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = hpscSharingStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
						
					}
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.MCU)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB12") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = mcuStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
					}
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.Fibre_Termination)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB12") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = fiberTerminationStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
					}
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.UBR)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB12") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = ubrStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
					}
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.TCL_Redwin)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB12") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = tclRedwinStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
					}
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.HEX_OLT)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB12") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = hexOltStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
					}
					
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB23") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = smartSplitStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
					}
					
					else if(data.getCurrentTab().equalsIgnoreCase(Constant.TCU)){
						if(jsonData.getLoginEmpRole().equalsIgnoreCase("OPCO") && !(data.getStatus().equalsIgnoreCase("NB12") || data.getStatus().equalsIgnoreCase("NB100"))){ 
							data.setPendingTo("WIP");
							data.setPendingFor("WIP");
						}
						else{
							String status = tcuStatusMap.get(h.getStatus());
							data.setPendingTo(status.split(":")[0]);
							data.setPendingFor(status.split(":")[1]);
						}
					}
					
					if(h.geteSeafPortalUrl() == null || h.geteSeafPortalUrl().equalsIgnoreCase("")){
						data.seteSeafPortalUrl("NA");
					}
					else{
						data.seteSeafPortalUrl(h.geteSeafPortalUrl());
					}
					
					if(h.getSeafAttached() == null || h.getSeafAttached().equalsIgnoreCase("")){
						data.setSeafAttached("NA");
					}
					else{
						data.setSeafAttached(h.getSeafAttached());
					}
					if(h.getDdrAttached() == null || h.getDdrAttached().equalsIgnoreCase("")){
						data.setDdrAttached("NA");
					}
					else{
						data.setDdrAttached(h.getDdrAttached());
					}
					if(h.getSurveyReportAttached() == null || h.getSurveyReportAttached().equalsIgnoreCase("")){
						data.setSurveyReportAttached("NA");
					}
					else{
						data.setSurveyReportAttached(h.getSurveyReportAttached());
					}
					if(h.getGoogleSnapshotAttachment() == null || h.getGoogleSnapshotAttachment().equalsIgnoreCase("")){
						data.setGoogleSnapshotAttachment("NA");
					}
					else{
						data.setGoogleSnapshotAttachment(h.getGoogleSnapshotAttachment());
					}
					if(h.getNfaAttachment() == null || h.getNfaAttachment().equalsIgnoreCase("")){
						data.setNfaAttachment("NA");
					}
					else{
						data.setNfaAttachment(h.getNfaAttachment());
					}
					if(h.getNcsoAttached() == null || h.getNcsoAttached().equalsIgnoreCase("")){
						data.setNcsoAttached("NA");
					}
					else{
						data.setNcsoAttached(h.getNcsoAttached());
					}
					if(h.getBoqAttachment() == null || h.getBoqAttachment().equalsIgnoreCase("")){
						data.setBoqAttachment("NA");
					}
					else{
						data.setBoqAttachment(h.getBoqAttachment());
					}
					if(h.getAggrementAttached() == null || h.getAggrementAttached().equalsIgnoreCase("")){
						data.setAggrementAttachment("NA");
					}
					else{
						data.setAggrementAttachment(h.getAggrementAttached());
					}
					/*if(h.getHoAqAttachment() == null || h.getHoAqAttachment().equalsIgnoreCase("")){
						data.setHoAqAttachment("NA");
					}
					else{
						data.setHoAqAttachment(h.getHoAqAttachment());
					}*/
					if(h.getAcquisitionAttachment() == null || h.getAcquisitionAttachment().equalsIgnoreCase("")){
						data.setAcquisitionAttachment("NA");
					}
					else{
						data.setAcquisitionAttachment(h.getAcquisitionAttachment());
					}
					if(h.getSalesAttachment() == null || h.getSalesAttachment().equalsIgnoreCase("")){
						data.setSalesAttachment("NA");
					}
					else{
						data.setSalesAttachment(h.getSalesAttachment());
					}
					if(h.getHoLegalAttachment() == null || h.getHoLegalAttachment().equalsIgnoreCase("")){
						data.setHoLegalAttachment("NA");
					}
					else{
						data.setHoLegalAttachment(h.getHoLegalAttachment());
					}
					/*if(h.getHoProjectHeadAttachment() == null || h.getHoProjectHeadAttachment().equalsIgnoreCase("")){
						data.setHoProjectHeadAttachment("NA");
					}
					else{
						data.setHoProjectHeadAttachment(h.getHoProjectHeadAttachment());
					}*/
					if(h.getHoProjectEbAttachment() == null || h.getHoProjectEbAttachment().equalsIgnoreCase("")){
						data.setHoProjectEbAttachment("NA");
					}
					else{
						data.setHoProjectEbAttachment(h.getHoProjectEbAttachment());
					}
					/*if(h.getHoCooAttachment() == null || h.getHoCooAttachment().equalsIgnoreCase("")){
						data.setHoCooAttachment("NA");
					}
					else{
						data.setHoCooAttachment(h.getHoCooAttachment());
					}*/
					if(h.getRfiDocAttachment() == null || h.getRfiDocAttachment().equalsIgnoreCase("")){
						data.setRfiDocAttachment("NA");
					}
					else{
						data.setRfiDocAttachment(h.getRfiDocAttachment());
					}
					if(h.getAtDocAttachment() == null || h.getAtDocAttachment().equalsIgnoreCase("")){
						data.setAtDocAttachment("NA");
					}
					else{
						data.setAtDocAttachment(h.getAtDocAttachment());
					}
					if(h.getRbhAttachment() == null || h.getRbhAttachment().equalsIgnoreCase("")){
						data.setRbhAttachment("NA");
					}
					else{
						data.setRbhAttachment(h.getRbhAttachment());
					}
					
					if(h.getHoSnMAttachment() == null || h.getHoSnMAttachment().equalsIgnoreCase("")){
						data.setHoSnMAttachment("NA");
					}
					else{
						data.setHoSnMAttachment(h.getHoSnMAttachment());
					}
					
					if(h.getHoRfPlanningAttachment() == null || h.getHoRfPlanningAttachment().equalsIgnoreCase("")){
						data.setHoRfPlanningAttachment("NA");
					}
					else{
						data.setHoRfPlanningAttachment(h.getHoRfPlanningAttachment());
					}
					
					if(h.getOpcoAttachment() == null || h.getOpcoAttachment().equalsIgnoreCase("")){
						data.setOpcoAttachment("NA");
					}
					else{
						data.setOpcoAttachment(h.getOpcoAttachment());
					}
					/*if(h.getCircleOperationHeadAttachment() == null || h.getCircleOperationHeadAttachment().equalsIgnoreCase("")){
						data.setCircleOperationHeadAttachment("NA");
					}
					else{
						data.setCircleOperationHeadAttachment(h.getCircleOperationHeadAttachment());
					}*/
					if(h.getCircleLegalAttachment() == null || h.getCircleLegalAttachment().equalsIgnoreCase("")){
						data.setCircleLegalAttachment("NA");
					}
					else{
						data.setCircleLegalAttachment(h.getCircleLegalAttachment());
					}
					
					data.setSuggestedSiteAddress(h.getSuggestedSiteAddress());
					data.setSuggestedCity(h.getSuggestedCity());
					data.setSuggestedTownVillage(h.getSuggestedTownVillage());
					data.setSuggestedDistrict(h.getSuggestedDistrict());
					data.setSuggestedState(h.getSuggestedState());
					data.setSuggestedPincode(h.getSuggestedPincode());
					data.setSuggestedClutter(h.getSuggestedClutter());
					data.setSuggestedSiteType(h.getSuggestedSiteType());
					data.setSuggestedStandardIPFEE(h.getSuggestedStandardIPFEE());
					data.setSuggestedLLR(h.getSuggestedLLR());
					data.setSuggestedCityPremium(h.getSuggestedCityPremium());
					data.setSuggestedLoading(h.getSuggestedLoading());
					data.setSuggestedEbAvailablilityDistance(h.getSuggestedEbAvailablilityDistance());
					data.setSuggestedTowerHeight(h.getSuggestedTowerHeight());
					data.setSuggestedDgAvailability(h.getSuggestedDgAvailability());
					data.setSuggestedPropertyTax(h.getSuggestedPropertyTax());
					data.setSuggestedMunicipalTax(h.getSuggestedMunicipalTax());
					data.setSuggestedTowerType(h.getSuggestedTowerType());
					data.setCityClass(h.getCityClass());
					data.setCityPremiumPercentage(h.getCityPremiumPercentage());
					data.setSuggestedWindSpeed(h.getSuggestedWindSpeed());
					data.setSuggestedLockTerm(h.getSuggestedLockTerm());
					data.setSuggestedDeviation(h.getSuggestedDeviation());
					data.setSuggestedLandOwnerRent(h.getSuggestedLandOwnerRent());
					data.setOdscModelType(h.getOdscModelType());
					data.setSuggestedElectrificationCharges(h.getElectrificationCharges());
					data.setSuggestedMcCharges(h.getMcCharges());
					data.setSharingPotential(h.getSharingPotential());
					//prepareNbsDetAndAuditData(data);
					//prepareNbsDetForEdit(data);
					list.add(data);
				}
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
	
	@Override
	public Response<SaveNBSRequest> viewSrDetails(CommonDTO jsonData) {
		Response<SaveNBSRequest> response = new Response<SaveNBSRequest>();
		List<SaveNBSRequest> list = new ArrayList<SaveNBSRequest>();
		try {
			SaveNBSRequest data = new SaveNBSRequest();
			data.setSrNumber(jsonData.getSrNumber());
			data.setLoginEmpRole(jsonData.getLoginEmpRole());
			prepareNbsDetAndAuditData(data);
			prepareNbsDetForEdit(data);
			list.add(data);
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

	@Transactional
	private void prepareNbsDetAndAuditData(SaveNBSRequest data) {
		try {
			List<ODSCRequest> odscList = new ArrayList<>();
			List<RFAntennaRequest> rfAntennaList = new ArrayList<>();
			List<RFAntennaRequest> rfAntennaDeleteList = new ArrayList<>();
			List<RFAntennaRequest> rfAntennaAddList = new ArrayList<>();
			List<GSMAntennaRequest> gsmAntennaList = new ArrayList<>();
			List<MicrowaveRequest> microwaveList = new ArrayList<>();
			List<MicrowaveRequest> microwaveDeleteList = new ArrayList<>();
			List<MicrowaveRequest> microwaveAddList = new ArrayList<>();
			List<BBURequest> bbuList = new ArrayList<>();
			List<RRURequest> rruList = new ArrayList<>();
			List<BTSRequest> btsList = new ArrayList<>();
			List<MCBRequest> mcbList = new ArrayList<>();
			List<MassiveMIMORequest> mimoList = new ArrayList<>();
			List<IP55Request> ip55List = new ArrayList<>();
			List<RRU_SwapRequest> rruDeleteList = new ArrayList<>();
			List<RRU_SwapRequest> rruAddList = new ArrayList<>();
			List<BBURequest> bbuDeleteList = new ArrayList<>();
			List<BBURequest> bbuAddList = new ArrayList<>();
			List<PoleRequest> noOfPoleList = new ArrayList<>();
			List<HPSCAntennaRequest> noOfHPSCAntennaList = new ArrayList<>();
			
			ODSCRequest odscData = null;
			RFAntennaRequest rfAntennaData = null;
			RFAntennaRequest rfAntennaDeleteData = null;
			RFAntennaRequest rfAntennaAddData = null;
			GSMAntennaRequest gsmAntennaData = null; 
			MicrowaveRequest microwaveData = null; 
			MicrowaveRequest microwaveDeleteData = null;
			MicrowaveRequest microwaveAddData = null; 
			RRURequest rruData = null;
			BTSRequest btsData = null;
			MCBRequest mcbData = null;
			MassiveMIMORequest mimoData = null;
			BBURequest bbuData = null;
			IP55Request ip55Data = null;
			RRU_SwapRequest rruDeleteData = null;
			RRU_SwapRequest rruAddData = null;
			BBURequest bbuDeleteData = null;
			BBURequest bbuAddData = null;
			PoleRequest poleData = null;
			HPSCAntennaRequest hpscAntennaData = null;
			
			List<NbsMasterDetEntity> nbsDet = tviCommonDao.getNbsDetailsBySrNumber(data.getSrNumber());
			for(NbsMasterDetEntity d : nbsDet){
				if(d.getType().equalsIgnoreCase(Constant.ODSC)){
					odscData = new ODSCRequest();
					odscData.setOdscMake(d.getOdscMake());
					odscData.setOdscModel(d.getOdscModel());
					odscList.add(odscData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.RF_ANTENNA)){
					rfAntennaData = new RFAntennaRequest();
					rfAntennaData.setRfSize(d.getRfSize());
					rfAntennaData.setRfWeight(d.getRfWeight());
					rfAntennaData.setRfMake(d.getRfMake());
					rfAntennaData.setRfModel(d.getRfModel());
					rfAntennaData.setRfAzimuth(d.getRfAzimuth());
					rfAntennaData.setRfGain(d.getRfGain());
					rfAntennaData.setRfBand(d.getRfBand());
					rfAntennaList.add(rfAntennaData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.RF_ANTENNA_Delete)){
					rfAntennaDeleteData = new RFAntennaRequest();
					rfAntennaDeleteData.setRfSize(d.getRfSize());
					rfAntennaDeleteData.setRfWeight(d.getRfWeight());
					rfAntennaDeleteData.setRfMake(d.getRfMake());
					rfAntennaDeleteData.setRfModel(d.getRfModel());
					rfAntennaDeleteData.setRfAzimuth(d.getRfAzimuth());
					rfAntennaDeleteData.setRfGain(d.getRfGain());
					rfAntennaDeleteData.setRfBand(d.getRfBand());
					rfAntennaDeleteList.add(rfAntennaDeleteData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.RF_ANTENNA_Add)){
					rfAntennaAddData = new RFAntennaRequest();
					rfAntennaAddData.setRfSize(d.getRfSize());
					rfAntennaAddData.setRfWeight(d.getRfWeight());
					rfAntennaAddData.setRfMake(d.getRfMake());
					rfAntennaAddData.setRfModel(d.getRfModel());
					rfAntennaAddData.setRfAzimuth(d.getRfAzimuth());
					rfAntennaAddData.setRfGain(d.getRfGain());
					rfAntennaAddData.setRfBand(d.getRfBand());
					rfAntennaAddList.add(rfAntennaAddData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.GSM_ANTENNA)){
					gsmAntennaData = new GSMAntennaRequest();
					gsmAntennaData.setGsmAntennaHeight(d.getGsmAntennaHeight());
					gsmAntennaList.add(gsmAntennaData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.MICROWAVE)){
					microwaveData = new MicrowaveRequest();
					microwaveData.setMake(d.getMicrowaveMake());
					microwaveData.setModel(d.getMicrowaveModel());
					microwaveData.setMicrowaveHeight(d.getMicrowaveHeight());
					microwaveData.setDia(d.getMicrowaveDia());
					microwaveData.setMicrowaveAzimuth(d.getMicrowaveAzimuth());
					microwaveList.add(microwaveData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.MICROWAVE_Delete)){
					microwaveDeleteData = new MicrowaveRequest();
					microwaveDeleteData.setMake(d.getMicrowaveMake());
					microwaveDeleteData.setModel(d.getMicrowaveModel());
					microwaveDeleteData.setMicrowaveHeight(d.getMicrowaveHeight());
					microwaveDeleteData.setDia(d.getMicrowaveDia());
					microwaveDeleteData.setMicrowaveAzimuth(d.getMicrowaveAzimuth());
					microwaveDeleteList.add(microwaveDeleteData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.MICROWAVE_Add)){
					microwaveAddData = new MicrowaveRequest();
					microwaveAddData.setMake(d.getMicrowaveMake());
					microwaveAddData.setModel(d.getMicrowaveModel());
					microwaveAddData.setMicrowaveHeight(d.getMicrowaveHeight());
					microwaveAddData.setDia(d.getMicrowaveDia());
					microwaveAddData.setMicrowaveAzimuth(d.getMicrowaveAzimuth());
					microwaveAddList.add(microwaveAddData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.BBU)){
					bbuData = new BBURequest();
					bbuData.setBbuMake(d.getBbuMake());
					bbuData.setBbuModel(d.getBbuModel());
					bbuData.setBbuPowerConsumption(d.getBbuPowerConsumption());
					bbuData.setBbuPositioning(d.getBbuPositioning());
					bbuList.add(bbuData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.BBU_Delete)){
					bbuDeleteData = new BBURequest();
					bbuDeleteData.setBbuMake(d.getBbuMake());
					bbuDeleteData.setBbuModel(d.getBbuModel());
					bbuDeleteData.setBbuPowerConsumption(d.getBbuPowerConsumption());
					bbuDeleteData.setBbuPositioning(d.getBbuPositioning());
					bbuDeleteList.add(bbuDeleteData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.BBU_Add)){
					bbuAddData = new BBURequest();
					bbuAddData.setBbuMake(d.getBbuMake());
					bbuAddData.setBbuModel(d.getBbuModel());
					bbuAddData.setBbuPowerConsumption(d.getBbuPowerConsumption());
					bbuAddData.setBbuPositioning(d.getBbuPositioning());
					bbuAddList.add(bbuAddData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.RRU)){
					rruData = new RRURequest();
					rruData.setRruMake(d.getRruMake());
					rruData.setRruModel(d.getRruModel());
					rruData.setRruFreqBand(d.getRruFreqBand());
					rruData.setRruWeight(d.getRruWeight());
					rruData.setRruPowerConsumption(d.getRruPowerConsumption());
					rruList.add(rruData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.BTS)){
					btsData = new BTSRequest();
					//btsData.setBtsType(d.getBtsType());
					btsData.setBtsModel(d.getBtsModel());
					btsData.setBtsMake(d.getBtsMake());
					btsData.setBtsFloorspace(d.getBtsFloorspace());
					btsData.setBtsPower(d.getBtsPower());
					btsList.add(btsData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.MCB)){
					mcbData = new MCBRequest();
					mcbData.setMcbRating(d.getMcbRating());
					mcbList.add(mcbData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.Massive_MIMO_ANTENNA)){
					mimoData = new MassiveMIMORequest();
					mimoData.setMimoAntennaWeight(d.getMimoWeight());
					mimoData.setMimoAntennaArea(d.getMimoArea());
					mimoData.setMimoAntennaMake(d.getMimoMake());
					mimoData.setMimoAntennaModel(d.getMimoModel());
					mimoData.setMimoPower(d.getMimoPower());
					mimoData.setMimoUspace(d.getMimoUspace());
					mimoList.add(mimoData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.IP55)){
					ip55Data = new IP55Request();
					ip55Data.setIp55Power(d.getIp55Power());
					ip55List.add(ip55Data);
				}
				else if(d.getType().equalsIgnoreCase(Constant.RRU_Delete)){
					rruDeleteData = new RRU_SwapRequest();
					rruDeleteData.setRruDeleteMake(d.getRruDeleteMake());
					rruDeleteData.setRruDeleteModel(d.getRruDeleteModel());
					rruDeleteData.setRruDeleteFreqBand(d.getRruDeleteFreqBand());
					rruDeleteData.setRruDeletePower(d.getRruDeletePower());
					rruDeleteData.setRruDeleteWeight(d.getRruDeleteWeight());
					rruDeleteList.add(rruDeleteData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.RRU_Add)){
					rruAddData = new RRU_SwapRequest();
					rruAddData.setRruAddMake(d.getRruAddMake());
					rruAddData.setRruAddModel(d.getRruAddModel());
					rruAddData.setRruAddFreqBand(d.getRruAddFreqBand());
					rruAddData.setRruAddPower(d.getRruAddPower());
					rruAddData.setRruAddWeight(d.getRruAddWeight());
					rruAddList.add(rruAddData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.POLE)){
					poleData = new PoleRequest();
					poleData.setPoleHeight(d.getPoleHeight());
					poleData.setPoleWeight(d.getPoleWeight());
					noOfPoleList.add(poleData);
				}
				else if(d.getType().equalsIgnoreCase(Constant.HPSC_Antenna)){
					hpscAntennaData = new HPSCAntennaRequest();
					hpscAntennaData.setTypeOfHpscAntenna(d.getTypeofHpscAntenna());
					noOfHPSCAntennaList.add(hpscAntennaData);
				}
			}
			
			List<NbsAuditDTO> auditList = new ArrayList<>();
			NbsAuditDTO auditData = null;
			
			List<Object[]> auditResult = tviCommonDao.getNbsAuditBySrNumber(data.getSrNumber());
			for(Object[] nbsAudit : auditResult){
				auditData = new NbsAuditDTO();
				auditData.setEmpId(String.valueOf(nbsAudit[0]));
				auditData.setEmpName(String.valueOf(nbsAudit[1]));
				auditData.setAction(String.valueOf(nbsAudit[2]));
				auditData.setRemark(String.valueOf(nbsAudit[3]));
				auditData.setAuditDate(String.valueOf(nbsAudit[4]).replace(".0", ""));
				if(!data.getLoginEmpRole().equalsIgnoreCase("OPCO")){
					auditList.add(auditData);
				}
				else{
					if(auditData.getAction().equalsIgnoreCase("NB01") || auditData.getAction().equalsIgnoreCase("NB09") || 
							auditData.getAction().equalsIgnoreCase("NB10") || auditData.getAction().equalsIgnoreCase("NB17") || 
							auditData.getAction().equalsIgnoreCase("NB18") || auditData.getAction().equalsIgnoreCase("NB19") || 
							auditData.getAction().equalsIgnoreCase("NB99") || auditData.getAction().equalsIgnoreCase("NB100"))
					auditList.add(auditData);
				}
				
			}
			data.setOdscList(odscList);
			data.setRfAntennaList(rfAntennaList);
			data.setRfAntennaDeleteList(rfAntennaDeleteList);
			data.setRfAntennaAddList(rfAntennaAddList);
			data.setGsmAntennaList(gsmAntennaList);
			data.setMicrowaveList(microwaveList);
			data.setMicrowaveDeleteList(microwaveDeleteList);
			data.setMicrowaveAddList(microwaveAddList);
			data.setBbuList(bbuList);
			data.setBbuDeleteList(bbuDeleteList);
			data.setBbuAddList(bbuAddList);
			data.setRruList(rruList);
			data.setBtsList(btsList);
			data.setMassiveMIMOAntennaList(mimoList);
			data.setMcbList(mcbList);
			data.setIp55List(ip55List);
			data.setRruDeleteList(rruDeleteList);
			data.setRruAddList(rruAddList);
			data.setNoOfPoleList(noOfPoleList);
			data.setNoOfHPSCAntennaList(noOfHPSCAntennaList);
			data.setNbsAuditList(auditList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Transactional
	private void prepareNbsDetForEdit(SaveNBSRequest data) {
		try {
			List<ODSCRequest> viewOdscList = new ArrayList<>();
			for(ODSCRequest obj : data.getOdscList()){
				viewOdscList.add(obj);
			}
			List<RFAntennaRequest> viewRfAntennaList =  new ArrayList<>();
			for(RFAntennaRequest obj : data.getRfAntennaList()){
				viewRfAntennaList.add(obj);
			}
			List<GSMAntennaRequest> viewGsmAntennaList =  new ArrayList<>();
			for(GSMAntennaRequest obj : data.getGsmAntennaList()){
				viewGsmAntennaList.add(obj);
			}
			List<RFAntennaRequest> viewRfAntennaDeleteList =  new ArrayList<>();
			for(RFAntennaRequest obj : data.getRfAntennaDeleteList()){
				viewRfAntennaDeleteList.add(obj);
			}
			List<RFAntennaRequest> viewRfAntennaAddList =  new ArrayList<>();
			for(RFAntennaRequest obj : data.getRfAntennaAddList()){
				viewRfAntennaAddList.add(obj);
			}
			List<MicrowaveRequest> viewMicrowaveList = new ArrayList<>();
			for(MicrowaveRequest obj : data.getMicrowaveList()){
				viewMicrowaveList.add(obj);
			}
			List<MicrowaveRequest> viewMicrowaveDeleteList =  new ArrayList<>();
			for(MicrowaveRequest obj : data.getMicrowaveDeleteList()){
				viewMicrowaveDeleteList.add(obj);
			}
			List<MicrowaveRequest> viewMicrowaveAddList =  new ArrayList<>();
			for(MicrowaveRequest obj : data.getMicrowaveAddList()){
				viewMicrowaveAddList.add(obj);
			}
			List<BBURequest> viewBbuList =  new ArrayList<>();
			for(BBURequest obj : data.getBbuList()){
				viewBbuList.add(obj);
			}
			List<BBURequest> viewBbuDeleteList = new ArrayList<>();
			for(BBURequest obj : data.getBbuDeleteList()){
				viewBbuDeleteList.add(obj);
			}
			List<BBURequest> viewBbuAddList =  new ArrayList<>();
			for(BBURequest obj : data.getBbuAddList()){
				viewBbuAddList.add(obj);
			}
			List<RRURequest> viewRruList =  new ArrayList<>();
			for(RRURequest obj : data.getRruList()){
				viewRruList.add(obj);
			}
			List<RRU_SwapRequest> viewRruDeleteList = new ArrayList<>();
			for(RRU_SwapRequest obj : data.getRruDeleteList()){
				viewRruDeleteList.add(obj);
			}
			List<RRU_SwapRequest> viewRruAddList =  new ArrayList<>();
			for(RRU_SwapRequest obj : data.getRruAddList()){
				viewRruAddList.add(obj);
			}
			List<BTSRequest> viewBtsList =  new ArrayList<>();
			for(BTSRequest obj : data.getBtsList()){
				viewBtsList.add(obj);
			}
			List<MCBRequest> viewMcbList = new ArrayList<>();
			for(MCBRequest obj : data.getMcbList()){
				viewMcbList.add(obj);
			}
			List<MassiveMIMORequest> viewMimoList =  new ArrayList<>();
			for(MassiveMIMORequest obj : data.getMassiveMIMOAntennaList()){
				viewMimoList.add(obj);
			}
			List<IP55Request> viewIp55List = new ArrayList<>();
			for(IP55Request obj : data.getIp55List()){
				viewIp55List.add(obj);
			}
			List<PoleRequest> viewPoleList = new ArrayList<>();
			for(PoleRequest obj : data.getNoOfPoleList()){
				viewPoleList.add(obj);
			}
			List<HPSCAntennaRequest> viewHPSCAntennaList =  new ArrayList<>();
			for(HPSCAntennaRequest obj : data.getNoOfHPSCAntennaList()){
				viewHPSCAntennaList.add(obj);
			}
			int maxData = 30;
			int listSize = viewOdscList.size();
			ODSCRequest odscData = new ODSCRequest();
			for(int i=(listSize);i<maxData;i++){
				viewOdscList.add(odscData);
			}
			listSize = viewRfAntennaList.size();
			RFAntennaRequest rfAntennaData = new RFAntennaRequest();
			for(int i=(listSize);i<maxData;i++){
				viewRfAntennaList.add(rfAntennaData);
			}
			listSize = viewRfAntennaAddList.size();
			RFAntennaRequest rfAntennaAddData = new RFAntennaRequest();
			for(int i=(listSize);i<maxData;i++){
				viewRfAntennaAddList.add(rfAntennaAddData);
			}
			listSize = viewRfAntennaDeleteList.size();
			RFAntennaRequest rfAntennaDeleteData = new RFAntennaRequest();
			for(int i=(listSize);i<maxData;i++){
				viewRfAntennaDeleteList.add(rfAntennaDeleteData);
			}
			listSize = viewGsmAntennaList.size();
			GSMAntennaRequest gsmAntennaData = new GSMAntennaRequest(); 
			for(int i=(listSize);i<maxData;i++){
				viewGsmAntennaList.add(gsmAntennaData);
			}
			listSize = viewMicrowaveList.size();
			MicrowaveRequest microwaveData = new MicrowaveRequest();
			for(int i=(listSize);i<maxData;i++){
				viewMicrowaveList.add(microwaveData);
			}
			listSize = viewMicrowaveAddList.size();
			MicrowaveRequest microwaveAddData = new MicrowaveRequest(); 
			for(int i=(listSize);i<maxData;i++){
				viewMicrowaveAddList.add(microwaveAddData);
			}
			listSize = viewMicrowaveDeleteList.size();
			MicrowaveRequest microwaveDeleteData = new MicrowaveRequest();
			for(int i=(listSize);i<maxData;i++){
				viewMicrowaveDeleteList.add(microwaveDeleteData);
			}
			listSize = viewBbuList.size();
			BBURequest bbuData = new BBURequest();
			for(int i=(listSize);i<maxData;i++){
				viewBbuList.add(bbuData);
			}
			listSize = viewBbuAddList.size();
			BBURequest bbuAddData = new BBURequest();
			for(int i=(listSize);i<maxData;i++){
				viewBbuAddList.add(bbuAddData);
			}
			listSize = viewBbuDeleteList.size();
			BBURequest bbuDeleteData = new BBURequest();
			for(int i=(listSize);i<maxData;i++){
				viewBbuDeleteList.add(bbuDeleteData);
			}
			listSize = viewRruList.size();
			RRURequest rruData = new RRURequest();
			for(int i=(listSize);i<maxData;i++){
				viewRruList.add(rruData);
			}
			listSize = viewRruAddList.size();
			RRU_SwapRequest rruAddData = new RRU_SwapRequest();
			for(int i=(listSize);i<maxData;i++){
				viewRruAddList.add(rruAddData);
			}
			listSize = viewRruDeleteList.size();
			RRU_SwapRequest rruDeleteData = new RRU_SwapRequest();
			for(int i=(listSize);i<maxData;i++){
				viewRruDeleteList.add(rruDeleteData);
			}
			listSize = viewBtsList.size();
			BTSRequest btsData = new BTSRequest();
			for(int i=(listSize);i<maxData;i++){
				viewBtsList.add(btsData);
			}
			listSize = viewMcbList.size();
			MCBRequest mcbData = new MCBRequest();
			for(int i=(listSize);i<maxData;i++){
				viewMcbList.add(mcbData);
			}
			listSize = viewMimoList.size();
			MassiveMIMORequest mimoData = new MassiveMIMORequest();
			for(int i=(listSize);i<maxData;i++){
				viewMimoList.add(mimoData);
			}
			listSize = viewIp55List.size();
			IP55Request ip55Data = new IP55Request();
			for(int i=(listSize);i<maxData;i++){
				viewIp55List.add(ip55Data);
			}
			listSize = viewPoleList.size();
			PoleRequest poleData = new PoleRequest();
			for(int i=(listSize);i<maxData;i++){
				viewPoleList.add(poleData);
			}
			listSize = viewHPSCAntennaList.size();
			HPSCAntennaRequest hpscAntennaData = new HPSCAntennaRequest();
			for(int i=(listSize);i<maxData;i++){
				viewHPSCAntennaList.add(hpscAntennaData);
			}

			
			data.setViewOdscList(viewOdscList);
			data.setViewRfAntennaList(viewRfAntennaList);
			data.setViewRfAntennaDeleteList(viewRfAntennaDeleteList);
			data.setViewRfAntennaAddList(viewRfAntennaAddList);
			data.setViewGsmAntennaList(viewGsmAntennaList);
			data.setViewMicrowaveList(viewMicrowaveList);
			data.setViewMicrowaveDeleteList(viewMicrowaveDeleteList);
			data.setViewMicrowaveAddList(viewMicrowaveAddList);
			data.setViewBbuList(viewBbuList);
			data.setViewBbuDeleteList(viewBbuDeleteList);
			data.setViewBbuAddList(viewBbuAddList);
			data.setViewRruList(viewRruList);
			data.setViewBtsList(viewBtsList);
			data.setViewMimoList(viewMimoList);
			data.setViewMcbList(viewMcbList);
			data.setViewIp55List(viewIp55List);
			data.setViewRruDeleteList(viewRruDeleteList);
			data.setViewRruAddList(viewRruAddList);
			data.setViewPoleList(viewPoleList);
			data.setViewHPSCAntennaList(viewHPSCAntennaList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Transactional
	@Override
	public Response<Map<String, String>> changeSrStatus(CommonDTO jsonData) {

		Response<Map<String, String>> response = new Response<Map<String, String>>();
		Map<String, String> errorsMap = new HashMap<String, String>();
		try {
			NbsMasterHdrEntity nbsHdr = tviCommonDao.getNbsHdrBySrNumber(jsonData.getSrNumber());
			if(nbsHdr != null){
				if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) && jsonData.getStatus().equalsIgnoreCase("NB03")){
					nbsHdr.setSharingPotential(jsonData.getSharingPotential());
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) && jsonData.getStatus().equalsIgnoreCase("NB04")){
					/*boolean checkCircleOperationHead = checkCircleOperationHead(jsonData.getCircleName());
					if(checkCircleOperationHead){ jsonData.setStatus("NB31"); nbsHdr.setStatus("NB31");}*/
					nbsHdr.seteSeafPortalUrl(jsonData.geteSeafPortalUrl());
					if(jsonData.getSeafAttachedStr() != null && jsonData.getSeafAttachedStr() != "")
						nbsHdr.setSeafAttached(jsonData.getSeafAttachedStr());
					if(jsonData.getDdrAttachedStr() != null && jsonData.getDdrAttachedStr() != "")
						nbsHdr.setDdrAttached(jsonData.getDdrAttachedStr());
					if(jsonData.getSurveyReportAttachedStr() != null && jsonData.getSurveyReportAttachedStr() != "")
						nbsHdr.setSurveyReportAttached(jsonData.getSurveyReportAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && 
						(jsonData.getStatus().equalsIgnoreCase("NB04") || jsonData.getStatus().equalsIgnoreCase("NB06"))){
					nbsHdr.setSuggestedSiteAddress(jsonData.getSuggestedSiteAddress());
					nbsHdr.setSuggestedDistrict(jsonData.getSuggestedDistrict());
					nbsHdr.setSuggestedState(jsonData.getSuggestedState());
					nbsHdr.setSuggestedPincode(jsonData.getSuggestedPincode());
					nbsHdr.setSuggestedLatitude(jsonData.getSuggestedLatitude());
					nbsHdr.setSuggestedLongitude(jsonData.getSuggestedLongitude());
					nbsHdr.setSuggestedCity(jsonData.getSuggestedCity());
					nbsHdr.setSuggestedTownVillage(jsonData.getSuggestedTownVillage());
					nbsHdr.setSuggestedClutter(jsonData.getSuggestedClutter());
					nbsHdr.setSuggestedTowerHeight(jsonData.getSuggestedTowerHeight());
					nbsHdr.setSuggestedDeviation(jsonData.getSuggestedDeviation());
					nbsHdr.setPoleHeight(jsonData.getSuggestedBuildingHeight());
					nbsHdr.setSuggestedTowerType(jsonData.getSuggestedTowerType());
					nbsHdr.setSuggestedLandOwnerRent(jsonData.getSuggestedLandOwnerRent());
					nbsHdr.setElectrificationCharges(jsonData.getSuggestedElectrificationCharges());
					nbsHdr.setMcCharges(jsonData.getSuggestedMcCharges());
					if(jsonData.getAnyAttachedStr() != null && jsonData.getAnyAttachedStr() !=""){
						if(jsonData.getStatus().equalsIgnoreCase("NB04"))
							nbsHdr.setAcquisitionAttachment(jsonData.getAnyAttachedStr());
						if(jsonData.getStatus().equalsIgnoreCase("NB06"))
							nbsHdr.setHoRfPlanningAttachment(jsonData.getAnyAttachedStr());
					}	
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) && jsonData.getStatus().equalsIgnoreCase("NB06")){
					if(jsonData.getGoogleSnapshotStr() !=null && !jsonData.getGoogleSnapshotStr().equalsIgnoreCase(""))
						nbsHdr.setGoogleSnapshotAttachment(jsonData.getGoogleSnapshotStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && 
						jsonData.getStatus().equalsIgnoreCase("RNB08")){
					nbsHdr.setNfaAttachment(jsonData.getNfaAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS)) && 
						jsonData.getStatus().equalsIgnoreCase("NB015")){
					nbsHdr.setHoLegalAttachment(jsonData.getAnyAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split)) && 
						jsonData.getStatus().equalsIgnoreCase("NB15")){
					nbsHdr.setBoqAttachment(jsonData.getBoqAttachedStr());
				}
				else if(jsonData.getStatus().equalsIgnoreCase("NB98") &&
						(jsonData.getNcsoAttachedStr() != null && !jsonData.getNcsoAttachedStr().equalsIgnoreCase(""))){
					nbsHdr.setNcsoAttached(jsonData.getNcsoAttachedStr());
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) && jsonData.getStatus().equalsIgnoreCase("NBB12") && 
						(jsonData.getAgreementStr() != null && !jsonData.getAgreementStr().equalsIgnoreCase(""))){
					nbsHdr.setAggrementAttached(jsonData.getAgreementStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && jsonData.getStatus().equalsIgnoreCase("NBB12")){
					if(jsonData.getAgreementStr() != null && !jsonData.getAgreementStr().equalsIgnoreCase(""))
						nbsHdr.setAggrementAttached(jsonData.getAgreementStr());
					if(jsonData.getDdrAttachedStr() != null && !jsonData.getDdrAttachedStr().equalsIgnoreCase(""))
						nbsHdr.setDdrAttached(jsonData.getDdrAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && 
						jsonData.getStatus().equalsIgnoreCase("NB12")){
					
					if(jsonData.getAnyAttachedStr() != null && jsonData.getAnyAttachedStr() !=""){
						String attachment = nbsHdr.getCircleLegalAttachment();
						if(attachment !=null && !attachment.equalsIgnoreCase(""))
							attachment += ","+jsonData.getAnyAttachedStr();
						else
							attachment = jsonData.getAnyAttachedStr();
						nbsHdr.setCircleLegalAttachment(attachment);
					}
						
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) && jsonData.getStatus().equalsIgnoreCase("NB08")){
					nbsHdr.setAirtelLocatorId(jsonData.getAirtelLocatorId());
					nbsHdr.setSuggestedLatitude(jsonData.getSuggestedLatitude());
					nbsHdr.setSuggestedLongitude(jsonData.getSuggestedLongitude());
					nbsHdr.setSuggestedSiteAddress(jsonData.getSuggestedSiteAddress());
					nbsHdr.setSuggestedCity(jsonData.getSuggestedCity());
					nbsHdr.setSuggestedTownVillage(jsonData.getSuggestedTownVillage());
					nbsHdr.setSuggestedDistrict(jsonData.getSuggestedDistrict());
					nbsHdr.setSuggestedState(jsonData.getSuggestedState());
					nbsHdr.setSuggestedPincode(jsonData.getSuggestedPincode());
					nbsHdr.setSuggestedClutter(jsonData.getSuggestedClutter());
					nbsHdr.setSuggestedSiteType(jsonData.getSuggestedSiteType());
					if(nbsHdr.getSuggestedSiteType().equalsIgnoreCase("RTP") || nbsHdr.getSuggestedSiteType().equalsIgnoreCase("RTT"))
						nbsHdr.setPoleHeight(jsonData.getSuggestedBuildingHeight());
					nbsHdr.setSuggestedEbAvailablilityDistance(jsonData.getSuggestedEbAvailablilityDistance());
					nbsHdr.setSuggestedTowerHeight(jsonData.getSuggestedTowerHeight());
					nbsHdr.setSuggestedDgAvailability(jsonData.getSuggestedDgAvailability());
					nbsHdr.setWindSpeed(jsonData.getWindSpeed());
					nbsHdr.setFrequencyUserByOpco(jsonData.getFrequencyUserByOpco());
					nbsHdr.setEbConnection(jsonData.getEbConnection());
					nbsHdr.setSpDate(new Date());
					if(jsonData.getAnyAttachedStr() != null && jsonData.getAnyAttachedStr() !="")
						nbsHdr.setHoRfPlanningAttachment(jsonData.getAnyAttachedStr());
					String spNumber = jsonData.getSrNumber().replace("SR", "SP");
					nbsHdr.setSpNumber(spNumber);
					jsonData.setSpNumber(spNumber);
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && jsonData.getStatus().equalsIgnoreCase("NB08")){
					nbsHdr.setSpDate(new Date());
					String spNumber = jsonData.getSrNumber().replace("SR", "SP");
					nbsHdr.setSpNumber(spNumber);
					jsonData.setSpNumber(spNumber);
					if(jsonData.getAnyAttachedStr() != null && jsonData.getAnyAttachedStr() !="")
						nbsHdr.setHoSnMAttachment(jsonData.getAnyAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.New_Tenency) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.TCL_Redwin)) && jsonData.getStatus().equalsIgnoreCase("NB03")){
					nbsHdr.setSpDate(new Date());
					String spNumber = jsonData.getSrNumber().replace("SR", "SP");
					nbsHdr.setSpNumber(spNumber);
					jsonData.setSpNumber(spNumber);
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.New_Tenency) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.TCL_Redwin)) && jsonData.getStatus().equalsIgnoreCase("NB05")){
					nbsHdr.setSoDate(new Date());
					String soNumber = jsonData.getSrNumber().replace("SR", "SO");
					nbsHdr.setSoNumber(soNumber);
					jsonData.setSoNumber(soNumber);
					if(jsonData.getAnyAttachedStr() != null || !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))
						nbsHdr.setOpcoAttachment(jsonData.getAnyAttachedStr());
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) && jsonData.getStatus().equalsIgnoreCase("NB09")){
					nbsHdr.setSuggestedStandardIPFEE(jsonData.getSuggestedStandardIPFEE());
					nbsHdr.setSuggestedLLR(jsonData.getSuggestedLLR());
					nbsHdr.setSuggestedCityPremium(jsonData.getSuggestedCityPremium());
					nbsHdr.setCityClass(jsonData.getCityClass());
					if(jsonData.getOperator().equals(Constant.Airtel))
						nbsHdr.setCityPremiumPercentage(jsonData.getAirtelCityPremiumPercentage());
					else if(jsonData.getOperator().equals(Constant.RJIO)){
						nbsHdr.setCityPremiumPercentage(jsonData.getCityPremiumPercentage());
						nbsHdr.setSuggestedWindSpeed(jsonData.getSuggestedWindSpeed());
						nbsHdr.setSuggestedLockTerm(jsonData.getSuggestedLockTerm());
					}
					
					nbsHdr.setSuggestedLoading(jsonData.getSuggestedLoading());
					nbsHdr.setSuggestedPropertyTax(jsonData.getSuggestedPropertyTax());
					nbsHdr.setSuggestedMunicipalTax(jsonData.getSuggestedMunicipalTax());
					nbsHdr.setProductType(jsonData.getNbsProductType());
					nbsHdr.setAdditionalBB(jsonData.getAdditionalBB());
					nbsHdr.setHeadLoad(jsonData.getHeadLoad());
					nbsHdr.setTotalWeightOnTower(jsonData.getTotalWeightOnTower());
					if(jsonData.getAnyAttachedStr() != null && jsonData.getAnyAttachedStr() !="")
						nbsHdr.setSalesAttachment(jsonData.getAnyAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && jsonData.getStatus().equalsIgnoreCase("NB09")){
					nbsHdr.setOdscModelType(jsonData.getOdscModelType());
					if(jsonData.getAnyAttachedStr() != null && jsonData.getAnyAttachedStr() !="")
						nbsHdr.setSalesAttachment(jsonData.getAnyAttachedStr());
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) && jsonData.getStatus().equalsIgnoreCase("NB10")){
					nbsHdr.setNoOfRFAntenna(jsonData.getNoOfRFAntenna());
					nbsHdr.setNoOfMicrowave(jsonData.getNoOfMicrowave());
					nbsHdr.setNoOfBts(jsonData.getNoOfBTS());
					nbsHdr.setNoOfBbu(jsonData.getNoOfBBU());
					nbsHdr.setNoOfRru(jsonData.getNoOfRRU());
					nbsHdr.setNO_OF_MCB(jsonData.getNoOfMCB());
					nbsHdr.setTotalRatedPower(jsonData.getTotalRatedPower());
					nbsHdr.setBtsType(jsonData.getBtsType());
					nbsHdr.setAdditionalLoad(jsonData.getAdditionalLoad());
					nbsHdr.setMW_RACK(jsonData.getMwRack());
					nbsHdr.setNoOfUSpaceRequired(jsonData.getNoOfUSpaceRequired());
					nbsHdr.setFiberTermination(jsonData.getFiberTermination());
					nbsHdr.setLoadOfU(jsonData.getLoadOfU());
					nbsHdr.setSoDate(new Date());
					if(jsonData.getAnyAttachedStr() != null && jsonData.getAnyAttachedStr() !="")
						nbsHdr.setOpcoAttachment(jsonData.getAnyAttachedStr());
					tviCommonDao.insertActiveDataInDet(jsonData);
					String soNumber = jsonData.getSrNumber().replace("SR", "SO");
					nbsHdr.setSoNumber(soNumber);
					jsonData.setSoNumber(soNumber);
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && jsonData.getStatus().equalsIgnoreCase("NB10")){
					nbsHdr.setNoOfODSC(jsonData.getNoOfODSC());
					nbsHdr.setNoOfMicrowave(jsonData.getNoOfMicrowave());
					nbsHdr.setFiberLength(jsonData.getFiberLength());
					nbsHdr.setSoDate(new Date());
					if(jsonData.getAnyAttachedStr() != null && jsonData.getAnyAttachedStr() !="")
						nbsHdr.setOpcoAttachment(jsonData.getAnyAttachedStr());
					tviCommonDao.insertActiveDataInDet(jsonData);
					String soNumber = jsonData.getSrNumber().replace("SR", "SO");
					nbsHdr.setSoNumber(soNumber);
					jsonData.setSoNumber(soNumber);
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.New_Tenency) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.TCL_Redwin)) && jsonData.getStatus().equalsIgnoreCase("NB07")){
					/*String strRfiDate = jsonData.getRfiDate();
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(strRfiDate));*/
					nbsHdr.setRfiDate(new Date());
					nbsHdr.setRfiDocAttachment(jsonData.getRfiAttachedStr());
					
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS)) && 
						jsonData.getStatus().equalsIgnoreCase("NB15")){
					String tviSiteId = jsonData.getTviSiteId();
					String sql = "SELECT `TVISiteID` FROM `Site_Master` where `TVISiteID` = '"+tviSiteId+"' ";
					boolean isExist = tviCommonDao.isExistSR(sql);
					if(isExist){
						response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
						response.setResponseDesc(ReturnsCode.ALREADY_EXIST+" TVI Site Id  = "+tviSiteId+" in Site Master.");
						return response;
					}
					nbsHdr.setTviSiteId(tviSiteId);
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS)) && 
						jsonData.getStatus().equalsIgnoreCase("NB16")){
					/*String strRfiDate = jsonData.getRfiDate();
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(strRfiDate));*/
					nbsHdr.setRfiDate(new Date());
					nbsHdr.setRfiDocAttachment(jsonData.getRfiAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && 
						jsonData.getStatus().equalsIgnoreCase("NB16")){
					String tviSiteId = jsonData.getTviSiteId();
					String sql = "SELECT `TVISiteID` FROM `Site_Master` where `TVISiteID` = '"+tviSiteId+"' ";
					boolean isExist = tviCommonDao.isExistSR(sql);
					if(isExist){
						response.setResponseCode(ReturnsCode.ALREADY_EXIST_CODE);
						response.setResponseDesc(ReturnsCode.ALREADY_EXIST+" TVI Site Id  = "+tviSiteId+" in Site Master.");
						return response;
					}
					/*String strRfiDate = jsonData.getRfiDate();
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(strRfiDate));*/
					nbsHdr.setRfiDate(new Date());
					nbsHdr.setTviSiteId(tviSiteId);
					nbsHdr.setRfiDocAttachment(jsonData.getRfiAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.New_Tenency) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.TCL_Redwin)) && jsonData.getStatus().equalsIgnoreCase("NB09")){
					String rfiAcceptedDate = jsonData.getRfiAcceptedDate();
					if(rfiAcceptedDate != null)
						nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(rfiAcceptedDate));
					nbsHdr.setAirtelLocatorId(jsonData.getAirtelLocatorId());
					nbsHdr.setAirtelSiteId(jsonData.getAirtelSiteId());
					nbsHdr.setAtDocAttachment(jsonData.getAtAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && jsonData.getStatus().equalsIgnoreCase("NB18")){
					String rfiAcceptedDate = jsonData.getRfiAcceptedDate();
					if(rfiAcceptedDate != null)
						nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(rfiAcceptedDate));
					nbsHdr.setAirtelLocatorId(jsonData.getAirtelLocatorId());
					nbsHdr.setAirtelSiteId(jsonData.getAirtelSiteId());
					nbsHdr.setAtDocAttachment(jsonData.getAtAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && jsonData.getStatus().equalsIgnoreCase("NB19")){
					String strRfsDate = jsonData.getRfsDate();
					if(strRfsDate != null)
					nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(strRfsDate));
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.New_Tenency) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.TCL_Redwin)) && jsonData.getStatus().equalsIgnoreCase("NB10")){
					String strRfsDate = jsonData.getRfsDate();
					if(strRfsDate != null)
					nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(strRfsDate));
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && 
						jsonData.getStatus().equalsIgnoreCase("NB05") &&
						(jsonData.getAnyAttachedStr() != null && !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
					nbsHdr.setRbhAttachment(jsonData.getAnyAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && 
						jsonData.getStatus().equalsIgnoreCase("NB07") &&
						(jsonData.getAnyAttachedStr() != null && !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
					nbsHdr.setHoSnMAttachment(jsonData.getAnyAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && 
						jsonData.getStatus().equalsIgnoreCase("NB11") &&
						(jsonData.getAnyAttachedStr() != null && !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
					String salesAttachment = nbsHdr.getSalesAttachment();
					if(salesAttachment !=null && !salesAttachment.equalsIgnoreCase(""))
						salesAttachment += ","+jsonData.getAnyAttachedStr();
					else
						salesAttachment = jsonData.getAnyAttachedStr();
					nbsHdr.setSalesAttachment(salesAttachment);
				}
				
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && 
						jsonData.getStatus().equalsIgnoreCase("NB13") &&
						(jsonData.getAnyAttachedStr() != null && !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
					nbsHdr.setHoLegalAttachment(jsonData.getAnyAttachedStr());
				}
				else if((jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split) ||
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)) && 
						jsonData.getStatus().equalsIgnoreCase("NB14") &&
						(jsonData.getAnyAttachedStr() != null && !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
					nbsHdr.setHoProjectEbAttachment(jsonData.getAnyAttachedStr());
				}
				/*else if(jsonData.getStatus().equalsIgnoreCase("NB32") &&
						(jsonData.getAnyAttachedStr() != null || !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
					nbsHdr.setCircleOperationHeadAttachment(jsonData.getAnyAttachedStr());
				}*/
				
				else if(jsonData.getStatus().equalsIgnoreCase("NB100") && 
						(jsonData.getAnyAttachedStr() != null && !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
					String salesAttachment = nbsHdr.getSalesAttachment();
					if(salesAttachment !=null && !salesAttachment.equalsIgnoreCase(""))
						salesAttachment += ","+jsonData.getAnyAttachedStr();
					else
						salesAttachment = jsonData.getAnyAttachedStr();
					nbsHdr.setSalesAttachment(salesAttachment);
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC)){
					changeDataForHpsc(nbsHdr,jsonData);
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.CreateNBS)){
					changeDataForCreateNbs(nbsHdr,jsonData);
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.New_Tenency)){
					changeDataForNewTenency(nbsHdr,jsonData);
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.Site_Upgrade)){
					changeDataForSiteUpgrade(nbsHdr,jsonData);
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.I_WAN) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.UBR)){
					changeDataForIwan(nbsHdr,jsonData);
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.MCU)){
					changeDataForMcu(nbsHdr,jsonData);
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.Fibre_Termination)){
					changeDataForFibreTermination(nbsHdr,jsonData);
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Anchor) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.ODSC_Sharing) || 
						jsonData.getCurrentTab().equalsIgnoreCase(Constant.HPSC_Sharing)){
					changeDataForOdscAnchor(nbsHdr,jsonData);
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.TCL_Redwin)){
					changeDataForTclRedwin(nbsHdr,jsonData);
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.HEX_OLT)){
					changeDataForHexOlt(nbsHdr,jsonData);
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.Smart_Split)){
					changeDataForSmartSplit(nbsHdr,jsonData);
				}
				else if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.TCU)){
					changeDataForTcu(nbsHdr,jsonData);
				}
				
				nbsHdr.setStatus(jsonData.getStatus());
				nbsHdr.setUpdateDate(new Date());
				tviCommonDao.saveOrUpdateEntityData(nbsHdr);
				
				prepareForSendMail(jsonData.getSrNumber(), jsonData.getSpNumber(), jsonData.getSoNumber(), jsonData.getCircleName(), jsonData.getOperator(), jsonData.getStatus(), jsonData.getCurrentTab(),errorsMap);
				
				NbsAuditEntity audEnt = new NbsAuditEntity();
				audEnt.setSrNumber(jsonData.getSrNumber());
				audEnt.setEmpId(jsonData.getLoginEmpId());
				audEnt.setRamark(jsonData.getRemark());
				audEnt.setAction(jsonData.getStatus());
				audEnt.setCreateDate(new Date());
				tviCommonDao.saveOrUpdateEntityData(audEnt);
			}
			response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
			response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			response.setErrorsMap(errorsMap);
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
			logger.error("========== changeSrStatus ============= "+e.toString());
		}
		return response;
	
	}
	
	private void changeDataForTcu(NbsMasterHdrEntity nbsHdr, CommonDTO jsonData) {
		try {
			if(jsonData.getStatus().equalsIgnoreCase("NB02")){
				nbsHdr.setSpDate(new Date());
				String spNumber = jsonData.getSrNumber().replace("SR", "SP");
				nbsHdr.setSpNumber(spNumber);
				jsonData.setSpNumber(spNumber);
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB03")){
				nbsHdr.setSoDate(new Date());
				String soNumber = jsonData.getSrNumber().replace("SR", "SO");
				nbsHdr.setSoNumber(soNumber);
				jsonData.setSoNumber(soNumber);
				if(jsonData.getAnyAttachedStr() != null || !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))
					nbsHdr.setOpcoAttachment(jsonData.getAnyAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB05")){
				/*String strRfiDate = jsonData.getRfiDate();
				nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(strRfiDate));*/
				nbsHdr.setRfiDate(new Date());
				nbsHdr.setRfiDocAttachment(jsonData.getRfiAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB07")){
				String rfiAcceptedDate = jsonData.getRfiAcceptedDate();
				if(rfiAcceptedDate != null)
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(rfiAcceptedDate));
				nbsHdr.setAirtelLocatorId(jsonData.getAirtelLocatorId());
				nbsHdr.setAirtelSiteId(jsonData.getAirtelSiteId());
				nbsHdr.setAtDocAttachment(jsonData.getAtAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB08")){
				String strRfsDate = jsonData.getRfsDate();
				if(strRfsDate != null)
				nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(strRfsDate));
			}
			else if((jsonData.getStatus().equalsIgnoreCase("RNB04") || jsonData.getStatus().equalsIgnoreCase("NB100")) && 
					(jsonData.getAnyAttachedStr() != null && !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
				String salesAttachment = nbsHdr.getSalesAttachment();
				if(salesAttachment !=null && !salesAttachment.equalsIgnoreCase(""))
					salesAttachment += ","+jsonData.getAnyAttachedStr();
				else
					salesAttachment = jsonData.getAnyAttachedStr();
				nbsHdr.setSalesAttachment(salesAttachment);	
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB11")){
				String dates = jsonData.getRfiDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfiAcceptedDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfsDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(dates));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void changeDataForSmartSplit(NbsMasterHdrEntity nbsHdr, CommonDTO jsonData) {
		try {
			if(jsonData.isODSCEdit())
				nbsHdr.setNoOfODSC(jsonData.getNoOfODSC());
			
			String dates = jsonData.getRfiDateEditable();
			if(dates != null && !dates.equalsIgnoreCase(""))
				nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(dates));
			dates = jsonData.getRfiAcceptedDateEditable();
			if(dates != null && !dates.equalsIgnoreCase(""))
				nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(dates));
			dates = jsonData.getRfsDateEditable();
			if(dates != null && !dates.equalsIgnoreCase(""))
				nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(dates));
			tviCommonDao.editAnyDetData(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void changeDataForHexOlt(NbsMasterHdrEntity nbsHdr, CommonDTO jsonData) {
		try {
			if(jsonData.getStatus().equalsIgnoreCase("NB02")){
				nbsHdr.setSpDate(new Date());
				String spNumber = jsonData.getSrNumber().replace("SR", "SP");
				nbsHdr.setSpNumber(spNumber);
				jsonData.setSpNumber(spNumber);
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB03")){
				nbsHdr.setSoDate(new Date());
				String soNumber = jsonData.getSrNumber().replace("SR", "SO");
				nbsHdr.setSoNumber(soNumber);
				jsonData.setSoNumber(soNumber);
				if(jsonData.getAnyAttachedStr() != null || !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))
					nbsHdr.setOpcoAttachment(jsonData.getAnyAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB05")){
				/*String strRfiDate = jsonData.getRfiDate();
				nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(strRfiDate));*/
				nbsHdr.setRfiDate(new Date());
				nbsHdr.setRfiDocAttachment(jsonData.getRfiAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB07")){
				String rfiAcceptedDate = jsonData.getRfiAcceptedDate();
				if(rfiAcceptedDate != null)
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(rfiAcceptedDate));
				nbsHdr.setAirtelLocatorId(jsonData.getAirtelLocatorId());
				nbsHdr.setAirtelSiteId(jsonData.getAirtelSiteId());
				nbsHdr.setAtDocAttachment(jsonData.getAtAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB08")){
				String strRfsDate = jsonData.getRfsDate();
				if(strRfsDate != null)
				nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(strRfsDate));
			}
			else if((jsonData.getStatus().equalsIgnoreCase("RNB04") || jsonData.getStatus().equalsIgnoreCase("NB100")) && 
					(jsonData.getAnyAttachedStr() != null && !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
				String salesAttachment = nbsHdr.getSalesAttachment();
				if(salesAttachment !=null && !salesAttachment.equalsIgnoreCase(""))
					salesAttachment += ","+jsonData.getAnyAttachedStr();
				else
					salesAttachment = jsonData.getAnyAttachedStr();
				nbsHdr.setSalesAttachment(salesAttachment);	
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB11")){
				nbsHdr.setFloorLength(jsonData.getFloorLength());
				nbsHdr.setFiberEntry(jsonData.getFiberEntry());
				nbsHdr.setNO_OF_MCB(jsonData.getNoOfMCB());
				nbsHdr.setMCB_Required_In_AMP(jsonData.getMcbRequiredInAmp());
				nbsHdr.setAdditionalPowerRequired(jsonData.getPowerRequired());
				nbsHdr.setTotalRatedPower(jsonData.getTotalRatedPower());
				nbsHdr.setAdditionalLoad(jsonData.getAdditionalLoad());
				
				String dates = jsonData.getRfiDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfiAcceptedDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfsDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(dates));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void changeDataForTclRedwin(NbsMasterHdrEntity nbsHdr, CommonDTO jsonData) {
		try {
			if(jsonData.getStatus().equalsIgnoreCase("NB13")){
				nbsHdr.setNoOfUBR_Antenna(jsonData.getNoOfUBR_Antenna());
				nbsHdr.setWeightOfAdditionalAntenna(jsonData.getWeightOfAntenna());
				nbsHdr.setuSpace_ethernet(jsonData.getuSpace_ethernet());
				nbsHdr.setuSpace(jsonData.getuSpaceTcl());
				nbsHdr.setAdditionalPowerRequired(jsonData.getPowerRequired());
				nbsHdr.setTotalRatedPower(jsonData.getTotalRatedPower());
				nbsHdr.setAdditionalLoad(jsonData.getAdditionalLoad());
				
				String dates = jsonData.getRfiDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfiAcceptedDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfsDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(dates));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void changeDataForOdscAnchor(NbsMasterHdrEntity nbsHdr, CommonDTO jsonData) {
		try {
			if(jsonData.getStatus().equalsIgnoreCase("NB22")){
				if(jsonData.isODSCEdit())
					nbsHdr.setNoOfODSC(jsonData.getNoOfODSC());
				if(jsonData.isMWEdit())
					nbsHdr.setNoOfMicrowave(jsonData.getNoOfMicrowave());
				
				String dates = jsonData.getRfiDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfiAcceptedDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfsDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(dates));
				tviCommonDao.editAnyDetData(jsonData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void changeDataForFibreTermination(NbsMasterHdrEntity nbsHdr, CommonDTO jsonData) {
		try {
			if(jsonData.getStatus().equalsIgnoreCase("NB02")){
				nbsHdr.setSpDate(new Date());
				String spNumber = jsonData.getSrNumber().replace("SR", "SP");
				nbsHdr.setSpNumber(spNumber);
				jsonData.setSpNumber(spNumber);
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB03")){
				nbsHdr.setSoDate(new Date());
				String soNumber = jsonData.getSrNumber().replace("SR", "SO");
				nbsHdr.setSoNumber(soNumber);
				jsonData.setSoNumber(soNumber);
				if(jsonData.getAnyAttachedStr() != null || !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))
					nbsHdr.setOpcoAttachment(jsonData.getAnyAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB05")){
				/*String strRfiDate = jsonData.getRfiDate();
				nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(strRfiDate));*/
				nbsHdr.setRfiDate(new Date());
				nbsHdr.setRfiDocAttachment(jsonData.getRfiAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB07")){
				String rfiAcceptedDate = jsonData.getRfiAcceptedDate();
				if(rfiAcceptedDate != null)
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(rfiAcceptedDate));
				nbsHdr.setAirtelLocatorId(jsonData.getAirtelLocatorId());
				nbsHdr.setAirtelSiteId(jsonData.getAirtelSiteId());
				nbsHdr.setAtDocAttachment(jsonData.getAtAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB08")){
				String strRfsDate = jsonData.getRfsDate();
				if(strRfsDate != null)
				nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(strRfsDate));
			}
			else if((jsonData.getStatus().equalsIgnoreCase("RNB04") || jsonData.getStatus().equalsIgnoreCase("NB100")) && 
					(jsonData.getAnyAttachedStr() != null && !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
				String salesAttachment = nbsHdr.getSalesAttachment();
				if(salesAttachment !=null && !salesAttachment.equalsIgnoreCase(""))
					salesAttachment += ","+jsonData.getAnyAttachedStr();
				else
					salesAttachment = jsonData.getAnyAttachedStr();
				nbsHdr.setSalesAttachment(salesAttachment);
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB11")){
				nbsHdr.setNoOfUSpaceRequired(jsonData.getNoOfUSpaceRequired());
				nbsHdr.setPowerConsumption(jsonData.getPowerConsumption());
				nbsHdr.setNO_OF_MCB(jsonData.getNoOfMCB());
				nbsHdr.setTotalRatedPower(jsonData.getTotalRatedPower());
				nbsHdr.setAdditionalLoad(jsonData.getAdditionalLoad());
				
				String dates = jsonData.getRfiDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfiAcceptedDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfsDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(dates));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void changeDataForMcu(NbsMasterHdrEntity nbsHdr, CommonDTO jsonData) {
		try {
			if(jsonData.getStatus().equalsIgnoreCase("NB02")){
				nbsHdr.setSpDate(new Date());
				String spNumber = jsonData.getSrNumber().replace("SR", "SP");
				nbsHdr.setSpNumber(spNumber);
				jsonData.setSpNumber(spNumber);
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB03")){
				nbsHdr.setSoDate(new Date());
				String soNumber = jsonData.getSrNumber().replace("SR", "SO");
				nbsHdr.setSoNumber(soNumber);
				jsonData.setSoNumber(soNumber);
				if(jsonData.getAnyAttachedStr() != null || !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))
					nbsHdr.setOpcoAttachment(jsonData.getAnyAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB05")){
				/*String strRfiDate = jsonData.getRfiDate();
				nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(strRfiDate));*/
				nbsHdr.setRfiDate(new Date());
				nbsHdr.setRfiDocAttachment(jsonData.getRfiAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB07")){
				String rfiAcceptedDate = jsonData.getRfiAcceptedDate();
				if(rfiAcceptedDate != null)
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(rfiAcceptedDate));
				nbsHdr.setAirtelLocatorId(jsonData.getAirtelLocatorId());
				nbsHdr.setAirtelSiteId(jsonData.getAirtelSiteId());
				nbsHdr.setAtDocAttachment(jsonData.getAtAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB08")){
				String strRfsDate = jsonData.getRfsDate();
				if(strRfsDate != null)
				nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(strRfsDate));
			}
			else if((jsonData.getStatus().equalsIgnoreCase("RNB04") || jsonData.getStatus().equalsIgnoreCase("NB100")) && 
					(jsonData.getAnyAttachedStr() != null && !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
				String salesAttachment = nbsHdr.getSalesAttachment();
				if(salesAttachment !=null && !salesAttachment.equalsIgnoreCase(""))
					salesAttachment += ","+jsonData.getAnyAttachedStr();
				else
					salesAttachment = jsonData.getAnyAttachedStr();
				nbsHdr.setSalesAttachment(salesAttachment);	
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB11")){
				nbsHdr.setuSpace(jsonData.getuSpaceMcu());
				nbsHdr.setAdditionalPowerRequired(jsonData.getPowerRequired());
				nbsHdr.setNO_OF_MCB(jsonData.getNoOfMCB());
				nbsHdr.setTotalRatedPower(jsonData.getTotalRatedPower());
				nbsHdr.setAdditionalLoad(jsonData.getAdditionalLoad());
				
				String dates = jsonData.getRfiDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfiAcceptedDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfsDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(dates));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void changeDataForIwan(NbsMasterHdrEntity nbsHdr, CommonDTO jsonData) {
		try {
			if(jsonData.getStatus().equalsIgnoreCase("NB02")){
				nbsHdr.setSpDate(new Date());
				String spNumber = jsonData.getSrNumber().replace("SR", "SP");
				nbsHdr.setSpNumber(spNumber);
				jsonData.setSpNumber(spNumber);
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB03")){
				nbsHdr.setSoDate(new Date());
				String soNumber = jsonData.getSrNumber().replace("SR", "SO");
				nbsHdr.setSoNumber(soNumber);
				jsonData.setSoNumber(soNumber);
				if(jsonData.getAnyAttachedStr() != null || !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))
					nbsHdr.setOpcoAttachment(jsonData.getAnyAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB05")){
				/*String strRfiDate = jsonData.getRfiDate();
				nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(strRfiDate));*/
				nbsHdr.setRfiDate(new Date());
				nbsHdr.setRfiDocAttachment(jsonData.getRfiAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB07")){
				String rfiAcceptedDate = jsonData.getRfiAcceptedDate();
				if(rfiAcceptedDate != null)
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(rfiAcceptedDate));
				nbsHdr.setAirtelLocatorId(jsonData.getAirtelLocatorId());
				nbsHdr.setAirtelSiteId(jsonData.getAirtelSiteId());
				nbsHdr.setAtDocAttachment(jsonData.getAtAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB08")){
				String strRfsDate = jsonData.getRfsDate();
				if(strRfsDate != null)
				nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(strRfsDate));
			}
			else if((jsonData.getStatus().equalsIgnoreCase("RNB04") || jsonData.getStatus().equalsIgnoreCase("NB100")) && 
					(jsonData.getAnyAttachedStr() != null && !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
				String salesAttachment = nbsHdr.getSalesAttachment();
				if(salesAttachment !=null && !salesAttachment.equalsIgnoreCase(""))
					salesAttachment += ","+jsonData.getAnyAttachedStr();
				else
					salesAttachment = jsonData.getAnyAttachedStr();
				nbsHdr.setSalesAttachment(salesAttachment);	
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB11")){
				nbsHdr.setNoOfUBR_Antenna(jsonData.getNoOfUBR_Antenna());
				nbsHdr.setWeightOfAdditionalAntenna(jsonData.getWeightOfAntenna());
				nbsHdr.setTypeOfAntenna(jsonData.getTypeOfAntenna());
				nbsHdr.setuSpace(jsonData.getuSpaceIwan());
				nbsHdr.setAdditionalPowerRequired(jsonData.getPowerRequired());
				nbsHdr.setNO_OF_MCB(jsonData.getNoOfMCB());
				nbsHdr.setTotalRatedPower(jsonData.getTotalRatedPower());
				nbsHdr.setAdditionalLoad(jsonData.getAdditionalLoad());
				
				String dates = jsonData.getRfiDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfiAcceptedDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfsDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(dates));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void changeDataForSiteUpgrade(NbsMasterHdrEntity nbsHdr, CommonDTO jsonData) {
		try {
			if(jsonData.getStatus().equalsIgnoreCase("NB02")){
				nbsHdr.setSpDate(new Date());
				String spNumber = jsonData.getSrNumber().replace("SR", "SP");
				nbsHdr.setSpNumber(spNumber);
				jsonData.setSpNumber(spNumber);
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB03")){
				nbsHdr.setSoDate(new Date());
				String soNumber = jsonData.getSrNumber().replace("SR", "SO");
				nbsHdr.setSoNumber(soNumber);
				jsonData.setSoNumber(soNumber);
				if(jsonData.getAnyAttachedStr() != null || !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))
					nbsHdr.setOpcoAttachment(jsonData.getAnyAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB05")){
				/*String strRfiDate = jsonData.getRfiDate();
				nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(strRfiDate));*/
				nbsHdr.setRfiDate(new Date());
				nbsHdr.setRfiDocAttachment(jsonData.getRfiAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB07")){
				String rfiAcceptedDate = jsonData.getRfiAcceptedDate();
				if(rfiAcceptedDate != null)
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(rfiAcceptedDate));
				nbsHdr.setAirtelLocatorId(jsonData.getAirtelLocatorId());
				nbsHdr.setAirtelSiteId(jsonData.getAirtelSiteId());
				nbsHdr.setAtDocAttachment(jsonData.getAtAttachedStr());
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB08")){
				String strRfsDate = jsonData.getRfsDate();
				if(strRfsDate != null)
				nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(strRfsDate));
			}
			else if((jsonData.getStatus().equalsIgnoreCase("RNB04") || jsonData.getStatus().equalsIgnoreCase("NB100")) && 
					(jsonData.getAnyAttachedStr() != null && !jsonData.getAnyAttachedStr().equalsIgnoreCase(""))){
				String salesAttachment = nbsHdr.getSalesAttachment();
				if(salesAttachment !=null && !salesAttachment.equalsIgnoreCase(""))
					salesAttachment += ","+jsonData.getAnyAttachedStr();
				else
					salesAttachment = jsonData.getAnyAttachedStr();
				nbsHdr.setSalesAttachment(salesAttachment);
				
			}
			else if(jsonData.getStatus().equalsIgnoreCase("NB11")){
				nbsHdr.setTotalRatedPower(jsonData.getTotalRatedPower());
				nbsHdr.setAdditionalLoad(jsonData.getAdditionalLoad());
				if(jsonData.isRFEdit())
					nbsHdr.setNoOfRFAntenna(jsonData.getNoOfRFAntenna());
				if(jsonData.isRFAdd_Edit())
					nbsHdr.setNoOfRFAntenna_Add(jsonData.getNoOfRFAntenna_Add());
				if(jsonData.isRFDelete_Edit())	
					nbsHdr.setNoOfRFAntenna_Delete(jsonData.getNoOfRFAntenna_Delete());
				if(jsonData.isMWEdit())
					nbsHdr.setNoOfMicrowave(jsonData.getNoOfMicrowave());
				if(jsonData.isMWAdd_Edit())
					nbsHdr.setNoOfMicrowaveAdd(jsonData.getNoOfMicrowave_Add());
				if(jsonData.isMWDelete_Edit())
					nbsHdr.setNoOfMicrowaveDelete(jsonData.getNoOfMicrowave_Delete());
				if(jsonData.isBTSEdit()){
					nbsHdr.setBtsType(jsonData.getBtsType());
					nbsHdr.setNoOfBts(jsonData.getNoOfBTS());
				}
				if(jsonData.isNoOfBbuEdit())
					nbsHdr.setNoOfBbu(jsonData.getNoOfBBU());
				if(jsonData.isBBUAdd_Edit())
					nbsHdr.setNoOfBbuAdd(jsonData.getNoOfBBU_Add());
				if(jsonData.isBBUDelete_Edit())
					nbsHdr.setNoOfBbuDelete(jsonData.getNoOfBBU_Delete());
				if(jsonData.isNoOfRruEdit())
					nbsHdr.setNoOfRru(jsonData.getNoOfRRU());
				if(jsonData.isRRUAdd_Edit())
					nbsHdr.setNO_OF_RRU_Add(jsonData.getNoOfRRU_Add());
				if(jsonData.isRRUDelete_Edit())
					nbsHdr.setNO_OF_RRU_Delete(jsonData.getNoOfRRU_Delete());
				if(jsonData.isMimoEdit())
					nbsHdr.setNoOfMassiveMIMOAntenna(jsonData.getNoOfMassiveMIMOAntenna());
				if(jsonData.isIp55Edit())
					nbsHdr.setNO_OF_IP55(jsonData.getNoOfIP55());
				if(jsonData.isMCBEdit())
					nbsHdr.setNO_OF_MCB(jsonData.getNoOfMCB());
				
				String dates = jsonData.getRfiDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfiAcceptedDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfsDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(dates));
				tviCommonDao.editAnyDetData(jsonData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void changeDataForHpsc(NbsMasterHdrEntity nbsHdr, CommonDTO jsonData) {
		try {
			if(jsonData.getStatus().equalsIgnoreCase("NB17") || jsonData.getStatus().equalsIgnoreCase("NB22")){
				if(jsonData.isNoOfPolesEdit())
					nbsHdr.setNoOfPoles(jsonData.getNoOfPole());
				if(jsonData.isNoOfHpscAntennaEdit())
					nbsHdr.setNoOfHpscAntenna(jsonData.getNoOfHPSCAntenna());
				if(jsonData.isNoOfBbuEdit())
					nbsHdr.setNoOfBbu(jsonData.getNoOfBBU());
				if(jsonData.isNoOfRruEdit())
					nbsHdr.setNoOfRru(jsonData.getNoOfRRU());
				
				if(jsonData.getStatus().equalsIgnoreCase("NB22")){
					String dates = jsonData.getRfiDateEditable();
					if(dates != null && !dates.equalsIgnoreCase(""))
						nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(dates));
					dates = jsonData.getRfiAcceptedDateEditable();
					if(dates != null && !dates.equalsIgnoreCase(""))
						nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(dates));
					dates = jsonData.getRfsDateEditable();
					if(dates != null && !dates.equalsIgnoreCase(""))
						nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(dates));
				}
				nbsHdr.setPowerRatingOfEquipment(jsonData.getPowerRatingOfEquipment());
				nbsHdr.setuSpace(jsonData.getuSpace());
				nbsHdr.setSiteType(jsonData.getSiteType());
				tviCommonDao.editDataForHPSC(jsonData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void changeDataForCreateNbs(NbsMasterHdrEntity nbsHdr, CommonDTO jsonData) {
		try {
			if(jsonData.getStatus().equalsIgnoreCase("NB22")){
				nbsHdr.setTotalRatedPower(jsonData.getTotalRatedPower());
				nbsHdr.setBtsType(jsonData.getBtsType());
				nbsHdr.setAdditionalLoad(jsonData.getAdditionalLoad());
				if(jsonData.isRFEdit())
					nbsHdr.setNoOfRFAntenna(jsonData.getNoOfRFAntenna());
				if(jsonData.isMWEdit())
					nbsHdr.setNoOfMicrowave(jsonData.getNoOfMicrowave());
				if(jsonData.isBTSEdit())
					nbsHdr.setNoOfBts(jsonData.getNoOfBTS());
				if(jsonData.isNoOfBbuEdit())
					nbsHdr.setNoOfBbu(jsonData.getNoOfBBU());
				if(jsonData.isNoOfRruEdit())
					nbsHdr.setNoOfRru(jsonData.getNoOfRRU());
				if(jsonData.isMCBEdit())
					nbsHdr.setNO_OF_MCB(jsonData.getNoOfMCB());
				
				String dates = jsonData.getRfiDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfiAcceptedDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfsDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(dates));
				tviCommonDao.editAnyDetData(jsonData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void changeDataForNewTenency(NbsMasterHdrEntity nbsHdr, CommonDTO jsonData) {
		try {
			if(jsonData.getStatus().equalsIgnoreCase("NB13")){
				nbsHdr.setTotalRatedPower(jsonData.getTotalRatedPower());
				nbsHdr.setBtsType(jsonData.getBtsType());
				nbsHdr.setAdditionalLoad(jsonData.getAdditionalLoad());
				if(jsonData.isRFEdit())
					nbsHdr.setNoOfRFAntenna(jsonData.getNoOfRFAntenna());
				if(jsonData.isMWEdit())
					nbsHdr.setNoOfMicrowave(jsonData.getNoOfMicrowave());
				if(jsonData.isBTSEdit())
					nbsHdr.setNoOfBts(jsonData.getNoOfBTS());
				if(jsonData.isNoOfBbuEdit())
					nbsHdr.setNoOfBbu(jsonData.getNoOfBBU());
				if(jsonData.isNoOfRruEdit())
					nbsHdr.setNoOfRru(jsonData.getNoOfRRU());
				if(jsonData.isMCBEdit())
					nbsHdr.setNO_OF_MCB(jsonData.getNoOfMCB());
				
				String dates = jsonData.getRfiDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfiAcceptedDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfiAcceptedDate(CommonFunction.convertStrDateToUtilDate(dates));
				dates = jsonData.getRfsDateEditable();
				if(dates != null && !dates.equalsIgnoreCase(""))
					nbsHdr.setRfsDate(CommonFunction.convertStrDateToUtilDate(dates));
				tviCommonDao.editAnyDetData(jsonData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/*private boolean checkCircleOperationHead(String circleName) {
		try {
			String sql = "SELECT `EMPLOYEE_ID` FROM `EMPLOYEE_MASTER` WHERE find_in_set('"+circleName+"',`CIRCLE_NAME`) <> 0 "
					+ "and ROLE = 'Circle_Operation_Head' and IS_ACTIVE = 'Y'";
			List<String> result = tviCommonDao.getCircleOperationHeadOnCircle(sql);
			if(result.size() !=0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}*/
	
	

	private void prepareForSendMail(String srNumber,String spNumber,String soNumber, String circleName, String operatorName, String status, String currentTab, Map<String, String> errorsMap) {
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
			
			String technology = "";
			String upgradeType = "";
			if(currentTab.equals(Constant.Site_Upgrade)){
				String upgradeSql = "SELECT `TECHNOLOGY`, `ExtensionType` FROM `NBS_MASTER_HDR` where `SR_NUMBER` = '"+srNumber+"' ";
				List<Object[]> upgradeResult = tviCommonDao.getNextLevelRole(upgradeSql);
				int upgradeRowCount = upgradeResult.size();
				if(upgradeRowCount == 1){
					Object[] upgObj = upgradeResult.get(0);
					technology = String.valueOf(upgObj[0]);
					upgradeType = upgObj[1] == null ? "" : String.valueOf(upgObj[1]);
				}
				
			}
			
			String nbStatus = "";
			String statusSql = "SELECT `DESC_DETAILS`, `DESCRIPTION` FROM `NBS_STATUS`  where `STATUS` = '"+status+"' and `TAB_NAME` = '"+currentTab+"' ";
			List<Object[]> statusResult = tviCommonDao.getNextLevelRole(statusSql);
			int statusRowCount = statusResult.size();
			if(statusRowCount == 1){
				Object[] staObj = statusResult.get(0);
				nbStatus = String.valueOf(staObj[0]);
			}
			
			
			String sql = "SELECT `User_Role`, `Is_HO_User`, `Action_On_Role` FROM `Notification_Flow` where find_in_set('"+status+"',`Status`) <> 0 and `TAB_NAME` = '"+currentTab+"' ";
			List<Object[]> result = tviCommonDao.getNextLevelRole(sql);
			int rowCount = result.size();
			String pendingRole = "";
			for(Object[] objj : result){
				String userRole = String.valueOf(objj[0]);
				String isHoUser = String.valueOf(objj[1]);
				String actionOnRole = String.valueOf(objj[2]);
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
						String name = obj[0] != null && String.valueOf(obj[0]).equalsIgnoreCase("") ? "" : String.valueOf(obj[0]);
						String mobile = obj[1] != null && String.valueOf(obj[1]).equalsIgnoreCase("") ? "" : String.valueOf(obj[1]);
						String email = obj[2] != null && String.valueOf(obj[2]).equalsIgnoreCase("") ? "" : String.valueOf(obj[2]);
						
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
							if(rowCount > 1){
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
							if(currentTab.equals(Constant.Site_Upgrade)){
								msg.append("Technology : "+technology+"<br>");
								msg.append("Upgrade Type : "+upgradeType+"<br>");
							}
							
							sms.append("SR Detail: ");
							sms.append("SR No : "+srNumber+" ");
							sms.append("Status : "+nbStatus+" ");
							if(currentTab.equals(Constant.Site_Upgrade)){
								sms.append("Technology : "+technology+" ");
								sms.append("Upgrade Type : "+upgradeType+" ");
							}
							
							if(send_sms_4_SR.equalsIgnoreCase("Yes"))
								isSendSMS = true;
						}
						// Y Y N
						else if(!srNumber.equalsIgnoreCase("NA") && !spNumber.equalsIgnoreCase("NA") && soNumber.equalsIgnoreCase("NA")){
							subject = spNumber;
							if(rowCount > 1){
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
							if(currentTab.equals(Constant.Site_Upgrade)){
								msg.append("Technology : "+technology+"<br>");
								msg.append("Upgrade Type : "+upgradeType+"<br>");
							}
							
							sms.append("SP Detail: ");
							sms.append("SP No : "+spNumber+" ");
							sms.append("Status : "+nbStatus+" ");
							if(currentTab.equals(Constant.Site_Upgrade)){
								sms.append("Technology : "+technology+" ");
								sms.append("Upgrade Type : "+upgradeType+" ");
							}
							
							if(send_sms_4_SP.equalsIgnoreCase("Yes"))
								isSendSMS = true;
						}
						// Y Y Y
						else if(!srNumber.equalsIgnoreCase("NA") && !spNumber.equalsIgnoreCase("NA") && !soNumber.equalsIgnoreCase("NA")){
							subject = soNumber;
							if(rowCount > 1){
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
							if(currentTab.equals(Constant.Site_Upgrade)){
								msg.append("Technology : "+technology+"<br>");
								msg.append("Upgrade Type : "+upgradeType+"<br>");
							}
							
							sms.append("SO Detail: ");
							sms.append("SO No : "+soNumber+" ");
							sms.append("Status : "+nbStatus+" ");
							if(currentTab.equals(Constant.Site_Upgrade)){
								sms.append("Technology : "+technology+" ");
								sms.append("Upgrade Type : "+upgradeType+" ");
							}
							
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

	@Override
	public Response<CommonDTO> getTVISiteIdCircleName(CommonDTO jsonData) {
		Response<CommonDTO> response = new Response<CommonDTO>();
		List<CommonDTO> listData = new ArrayList<>();
		CommonDTO data = null;
		try {
			String sql = "SELECT `TVISiteID`, `District`, `GST_State`, `Latitude`, `Longitude` FROM `Site_Master` where `Circle` = '"+jsonData.getCircleName()+"' and `Anchor_Site_Built_for` = 'Airtel' ";
			List<Object[]> result =  tviCommonDao.getTVISiteIdCircleName(sql);
			for(Object[] obj : result){
				String tviSiteId = String.valueOf(obj[0]);
				String district = String.valueOf(obj[1]);
				String state = String.valueOf(obj[2]);
				String latitude = String.valueOf(obj[3]);
				String longitude = String.valueOf(obj[4]);
				data = new CommonDTO();
				data.setParamCode(tviSiteId);
				data.setParamDesc(tviSiteId+" ");
				data.setDistrict(district);
				data.setState(state);
				data.setLatitude(latitude);
				data.setLongitude(longitude);
				listData.add(data);
			}
			response.setWrappedList(listData);
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
	public Response<Map<String, String>> changeBulkSrStatus(BulkDTO jsonData) {
		Response<Map<String, String>> response = new Response<>();
		Map<String, String> errorsMap = new HashMap<String, String>();
		try {
			NbsAuditEntity audEnt = null;
			for(BulkSrDTO b : jsonData.getCheckCheckedList()){
				String col = "";
				if(b.getTabName().equalsIgnoreCase(Constant.CreateNBS)){
					col = "Sharing_Potential = '"+jsonData.getBulkSharingPotential()+"', ";
				}
				String updateTable = "update NBS_MASTER_HDR set "+col+" STATUS = '"+jsonData.getBulkAction()+"', REMARK = '"+jsonData.getBulkRemark()+"', UPDATE_DATE=CURRENT_TIMESTAMP where SR_NUMBER = '"+b.getSrNumber()+"' ";
				int c = tviCommonDao.updateBulkdataValue(updateTable);
				if(c != 0){
					audEnt = new NbsAuditEntity();
					audEnt.setSrNumber(b.getSrNumber());
					audEnt.setEmpId(jsonData.getLoginEmpId());
					audEnt.setRamark(jsonData.getBulkRemark());
					audEnt.setAction(jsonData.getBulkAction());
					audEnt.setCreateDate(new Date());
					tviCommonDao.saveOrUpdateEntityData(audEnt);
				}
				
				prepareForSendMail(b.getSrNumber(), "NA", "NA", b.getCircleName(), b.getOperator(), jsonData.getBulkAction(), b.getTabName(), errorsMap);
					
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
	public Response<SystemParamDTO> getNoOfList() {
		Response<SystemParamDTO> response = new Response<SystemParamDTO>();
		List<SystemParamDTO> list = new ArrayList<SystemParamDTO>();
		SystemParamDTO data = null;
		try {
			String sql = "select Type,Number from No_Of_List where Is_Active = 'Y' ";
			List<Object[]> resultList = tviCommonDao.getNoOfList(sql);
			for(Object obj [] : resultList){
				data = new SystemParamDTO();
				data.setParamCode(String.valueOf(obj[0]));
				data.setParamDesc(String.valueOf(obj[1]));
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
	public Response<Map<String, String>> changeSrStatusAfterEdit(SaveNBSRequest jsonData) {
		Response<Map<String, String>> response = new Response<Map<String, String>>();
		try {
			NbsMasterHdrEntity nbsHdr = tviCommonDao.getNbsHdrBySrNumber(jsonData.getSrNumber());
			if(nbsHdr != null){
				nbsHdr.setStatus(jsonData.getStatus());
				nbsHdr.setRemark(jsonData.getRemark());
				if(jsonData.getStatus().equalsIgnoreCase("NB02")){
					String srNumber = jsonData.getSrNumber();
					nbsHdr.setSpNumber(srNumber.replace("SR", "SP"));
				}
				nbsHdr.setUpdateDate(new Date());
				tviCommonDao.saveOrUpdateEntityData(nbsHdr);
				
				
				NbsAuditEntity audEnt = new NbsAuditEntity();
				audEnt.setSrNumber(jsonData.getSrNumber());
				audEnt.setEmpId(jsonData.getLoginEmpId());
				audEnt.setRamark(jsonData.getRemark());
				audEnt.setAction(jsonData.getStatus());
				audEnt.setCreateDate(new Date());
				tviCommonDao.saveOrUpdateEntityData(audEnt);
			}
			response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
			response.setResponseDesc(ReturnsCode.SUCCESSFUL);
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		
		return response;
	}

	@Override
	public Response<Map<String, String>> sendMail() {
		Response<Map<String, String>> response = new Response<Map<String, String>>();
		try {
			String to = "jai.prakash@trinityapplab.co.in,hunny@trinityapplab.co.in",
					cc="pushkar.tyagi@trinityapplab.co.in",
					subject="Demo",
					msg="<h1>Hello this demo mail</h1> "
						+ "<table> "
						+ "<tr> <th>Name</th> <th>Venue</th> </tr>"
						+ "<tr> <td>Jai</td> <td>Ghaziabad</td> </tr>"
						+ "<tr> <td>Hunny</td> <td>Delhi</td> </tr>"
						+ "</table>";
			String mailResponse = SendMail.sendMail("Testing", to, cc, "", subject, msg,null);
			if(mailResponse !=null){
				response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
				response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			}
			else{
				response.setResponseCode(ReturnsCode.GENERIC_ERROR);
				response.setResponseDesc("Mail not send..");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}

	@Override
	public Response<String> sendOTPtoMobile(CommonDTO jsonData) {
		Response<String> response = new Response<String>();
		List<String> wrappedList = new ArrayList<String>();
		try {
			List<String> result =  tviCommonDao.sendOTPtoMobile(jsonData);
			if(result.size() != 0){
				String otp = CommonFunction.generateOtp();
				String msg = "Your one time password (OTP) is "+otp+" for CP application.";
				int isSend = SendSMS.sendSms(msg, jsonData.getMobileNumber());
				System.out.println(isSend);
				wrappedList.add(otp);
				response.setWrappedList(wrappedList);
				response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
				response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			}
			else{
				response.setResponseCode(ReturnsCode.NO_RECORDS_FOUND_CODE);
				response.setResponseDesc(ReturnsCode.NO_RECORD_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_DATABASE_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}

	@Override
	public Response<Map<String, String>> changePassword(CommonDTO jsonData) {
		Response<Map<String, String>> response = new Response<Map<String, String>>();
		try {
			int count = tviCommonDao.changePassword(jsonData);
			if(count != 0){
				response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
				response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			}
			else{
				response.setResponseCode(ReturnsCode.NO_RECORDS_FOUND_CODE);
				response.setResponseDesc(ReturnsCode.NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_DATABASE_ERROR);
			response.setResponseDesc(e.toString());
		}
		return response;
	}

	@Transactional
	@Override
	public void saveResponseInTable(Clob requestJson, Clob responseJson, String methodName) {
		try {
			ResponseTableModel res = new ResponseTableModel();
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
	public Response<Map<String, String>> sendMailMyPhp(String srNumber, String circleName, String operatorName,
			String currentTab) {
		Response<Map<String, String>> response = new Response<>();
		Map<String, String> errorsMap = new HashMap<String, String>();
		try {
			prepareForSendMail(srNumber, "NA", "NA", circleName, operatorName, "NB01", currentTab,errorsMap);
			response.setErrorsMap(errorsMap);
			response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
			response.setResponseDesc(ReturnsCode.SUCCESSFUL);
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_DATABASE_ERROR);
			response.setResponseDesc(e.toString());
		}
		
		return response;
	}

	@Transactional
	@Override
	public Response<Map<String, String>> submitComplain(ComplainDTO jsonData) {
		Response<Map<String, String>> response = new Response<>();
		Map<String, String> errorsMap = new HashMap<String, String>();
		try {
			ComplainEntity complain = new ComplainEntity();
			complain.setSrNumber(jsonData.getSrNumber());
			complain.setRaiseDescription(jsonData.getDescription());
			complain.setRaiseBy(jsonData.getLoginEmpId());
			complain.setRaiseDate(new Date());
			complain.setImage(jsonData.getImgStr());
			complain.setStatus("Open");
			ComplainEntity com = (ComplainEntity) tviCommonDao.saveOrUpdateEntityData(complain);
			if(com != null){
				Integer cId = com.getComplainId();
				String raiseBy = com.getRaiseBy();
				String desc = com.getRaiseDescription();
				String img = com.getImage();
				StringBuilder msg = new StringBuilder();
				msg.append("Dear Sir,<br>");
				msg.append("FYI<br>");
				msg.append("Raise By : "+raiseBy+".<br>");
				msg.append("Description : "+desc+".<br>");
				msg.append("Img : "+img+".<br>");
				msg.append("Please solve this.");
				SendMail.sendMail("Trinity Helpdesk", "jai.prakash@trinityapplab.co.in", "", "", "TVI Complaint - "+cId, msg.toString(), null);
			}
			response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
			response.setResponseDesc(ReturnsCode.SUCCESSFUL);
			response.setErrorsMap(errorsMap);
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_DATABASE_ERROR);
			response.setResponseDesc(e.toString());
			response.setErrorsMap(errorsMap);
		}
		return response;
	}

	@Override
	public Response<Map<String, String>> sendComplaintMailPhp(String emailFrom, String emailId, String ccEmailId, String bccEmailId, 
			String subject, String msg) {
		Response<Map<String, String>> response = new Response<>();
		Map<String, String> errorsMap = new HashMap<String, String>();
		try {
			String status = SendMail.sendMail(emailFrom, emailId, ccEmailId, bccEmailId, subject, msg.toString(), null);
			if(status != null){
				response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
				response.setResponseDesc(ReturnsCode.SUCCESSFUL);
				errorsMap.put("MAIL_STATUS", "SUCCESS");
				response.setErrorsMap(errorsMap);
			}
			else{
				response.setResponseCode(ReturnsCode.OPERATION_SUCCESSFUL);
				response.setResponseDesc(ReturnsCode.SUCCESSFUL);
				errorsMap.put("MAIL_STATUS", "FAIL");
				response.setErrorsMap(errorsMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ReturnsCode.GENERIC_DATABASE_ERROR);
			response.setResponseDesc(e.toString());
			response.setErrorsMap(errorsMap);
		}
		return response;
	}

	

}
