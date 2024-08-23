package com.tvi.common.persistence.entity.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.tvi.constant.CommonFunction;
import com.tvi.constant.Constant;
import com.tvi.dto.AdditionalInformationDto;
import com.tvi.dto.BandFrequencyMHzDto;
import com.tvi.dto.BscRncCabinetsDto;
import com.tvi.dto.BtsCabinetDto;
import com.tvi.dto.BtsDto;
import com.tvi.dto.CommonDTO;
import com.tvi.dto.ComplainDTO;
import com.tvi.dto.DgDto;
import com.tvi.dto.FiberDto;
import com.tvi.dto.FiberNodeProvisioningDto;
import com.tvi.dto.FibreNodeDto;
import com.tvi.dto.McbDto;
import com.tvi.dto.MwAntennaDto;
import com.tvi.dto.MwDto;
import com.tvi.dto.OtherEquipmentDto;
import com.tvi.dto.OtherNodeDto;
import com.tvi.dto.PriorityDto;
import com.tvi.dto.ProductDetailDto;
import com.tvi.dto.RadioAntennaDto;
import com.tvi.dto.RejectSpDto;
import com.tvi.dto.RelocationDto;
import com.tvi.dto.RfaiAcceptReject;
import com.tvi.dto.SacfaDto;
import com.tvi.dto.SiteDetailDto;
import com.tvi.dto.SiteInfoDto;
import com.tvi.dto.SoSubmitDto;
import com.tvi.dto.SrRequestJsonDto;
import com.tvi.dto.SrSoWithdrawalDto;
import com.tvi.dto.StrageticDto;
import com.tvi.dto.TmaTmbDto;
import com.tvi.entity.EmployeeMasterEntity;
import com.tvi.entity.NbsMasterDetEntity;
import com.tvi.entity.NbsMasterHdrEntity;
import com.tvi.generic.common.GenericDaoImpl;
import com.tvi.request.AirFiberRequest;
import com.tvi.request.BBURequest;
import com.tvi.request.BTSRequest;
import com.tvi.request.EmployeeActionRequest;
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
import com.tvi.sharing.dto.CircleInformationDto;
import com.tvi.sharing.dto.SharingSrRequestJsonDto;
import com.tvi.upgrade.dto.RequestedEquipmentDto;
import com.tvi.upgrade.dto.UpgradeSrRequestJsonDto;

public class TviCommonDaoImpl extends GenericDaoImpl<Object> implements TviCommonDao  {

	@SuppressWarnings("unchecked")
	public List<Object[]> userLogin(LoginRequest jsonData) {
		String sql = "select NAME, ROLE, EMPLOYEE_ID, IS_HO_USER, CIRCLE_NAME, ORGANIZATION from EMPLOYEE_MASTER where "
				+ "EMPLOYEE_ID = ? and PASSWORD = BINARY(?) and IS_ACTIVE = ?";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter(1, jsonData.getUsername());
		query.setParameter(2, jsonData.getPassword());
		query.setParameter(3, "Y");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getMenuListByRoleName(LoginRequest jsonData) {
		String sql = "SELECT MENU_ID, MENU_NAME, ROUTER_LINK, ICON, IS_SUB_MENU FROM MENU_MASTER where find_in_set(?,Role) <> 0 and Is_Active = ? order by DISPLAY_ORDER ";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter(1, jsonData.getUserRole());
		query.setParameter(2, "Y");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getSubmenuByMenuId(String menuId, String userRole, String operator) {
		String sql = "SELECT SUB_MENU_ID, SUB_MENU_NAME, ROUTER_LINK, ICON FROM SUB_MENU_MASTER where MENU_ID = ? "
				+ "and find_in_set(?,Role) <> 0 "
				+ "and find_in_set(?,Organization) <> 0  "
				+ "and IS_ACTIVE = ?  order by DISPLAY_ORDER ";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter(1, Integer.parseInt(menuId));
		query.setParameter(2, userRole);
		query.setParameter(3, operator);
		query.setParameter(4, "Y");
		return query.getResultList();
	}

	public void insertHdrAndDet(SaveNBSRequest jsonData) {
		String t = "";
		String d = "";
		if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.New_Tenency)){
			t = ", `SITE_ADDRESS`, `DISTRICT`, `STATE`, `CLUTTER`, `SITE_TYPE`, `BTS_TYPE`, `Additional_Load`, `Total_Rated_Power_in_KW`, `NO_OF_RF_ANTENNA`, `NO_OF_MICROWAVE`, `NO_OF_BBU`, `NO_OF_RRU`, `NO_OF_BTS`, `NO_OF_MCB` ";
			d = ", ?";
			d += ", '"+jsonData.getDistrict()+"'";
			d += ", '"+jsonData.getState()+"'";
			d += ", '"+jsonData.getClutter()+"'";
			d += ", '"+jsonData.getSiteType()+"'";
			d += ", '"+jsonData.getBtsType()+"'";
			d += ", "+jsonData.getAdditionalLoad();
			d += ", "+jsonData.getTotalRatedPower();
			d += ", "+jsonData.getNoOfRFAntenna();
			d += ", "+jsonData.getNoOfMicrowave();
			d += ", "+jsonData.getNoOfBBU();
			d += ", "+jsonData.getNoOfRRU();
			d += ", "+jsonData.getNoOfBTS();
			d += ", "+jsonData.getNoOfMCB();
		}
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `Operator`, `SR_DATE`, `CIRCLE_NAME`, `TAB_NAME`, `TVI_SITE_ID`, `Site_Name`"+t+", `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID` , `LATITUDE_1`, `LONGITUDE_1`, `LATITUDE_2`, `LONGITUDE_2`, `AGL_REQUIRED`, "
				+ "`TECHNOLOGY`, `REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		Double latitude2 = jsonData.getLatitude2() == 0.0 ? null : jsonData.getLatitude2();
		Double longitude2 = jsonData.getLongitude2() == 0.0 ? null : jsonData.getLongitude2();
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', '"+jsonData.getOperator()+"', CURRENT_TIMESTAMP, '"+jsonData.getCircleName()+"', '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', ? "+d+", '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", "+latitude2+", "+longitude2+", "+jsonData.getAglRequired()+", "
				+ "'"+jsonData.getTechnology()+"', ?, 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		if(jsonData.getCurrentTab().equalsIgnoreCase(Constant.New_Tenency)){
			getEm().createNativeQuery(hdrTable + hdrValue)
			.setParameter(1, jsonData.getSiteName())
			.setParameter(2, jsonData.getSiteAddress())
			.setParameter(3, jsonData.getRemark())
			.executeUpdate();
		}
		else{
			getEm().createNativeQuery(hdrTable + hdrValue)
			.setParameter(1, jsonData.getSiteName())
			.setParameter(2, jsonData.getRemark())
			.executeUpdate();
		}
		if(jsonData.getNoOfMicrowave() != null && jsonData.getNoOfMicrowave() != 0){
			insertMwData(jsonData.getSrNumber(), jsonData.getMicrowaveList());
		}
		
		if(jsonData.getNoOfRRU() != null && jsonData.getNoOfRRU() != 0){
			insertRruData(jsonData.getSrNumber(), jsonData.getRruList());
		}
		
		if(jsonData.getNoOfBTS() != null && jsonData.getNoOfBTS() != 0){
			insertBtsData(jsonData.getSrNumber(), jsonData.getBtsList());
		}
		
		if(jsonData.getNoOfBBU() != null && jsonData.getNoOfBBU() != 0){
			insertBbuData(jsonData.getSrNumber(), jsonData.getBbuList());
		}
		
		if(jsonData.getNoOfRFAntenna() != null && jsonData.getNoOfRFAntenna() != 0){
			insertRfAntennaData(jsonData.getSrNumber(), jsonData.getRfAntennaList());
		}
		
		if(jsonData.getNoOfMCB() != null && jsonData.getNoOfMCB() != 0){
			insertMcbData(jsonData.getSrNumber(), jsonData.getMcbList());
		}
		
	}
	
	public void insertHdrAndDetOfSiteUpgrade(SaveNBSRequest jsonData) {
		String t = "";
		String d = "";
		
		t = ", `SITE_ADDRESS`, `DISTRICT`, `STATE`, `CLUTTER`, `SITE_TYPE`, `BTS_TYPE`, `Additional_Load`, `Total_Rated_Power_in_KW`, `NO_OF_RF_ANTENNA`, `NO_OF_RF_ANTENNA_Delete`, `NO_OF_RF_ANTENNA_Add`, `NO_OF_MICROWAVE`, `NO_OF_BBU`, `NO_OF_RRU`, `NO_OF_BTS`, `NO_OF_MCB`, `No_of_Massive_MIMO_Antenna`, `NO_OF_IP55`, `NO_OF_RRU_Delete`, `NO_OF_RRU_Add`, `NO_OF_BBU_Delete`, `NO_OF_BBU_Add`, `NO_OF_MICROWAVE_Delete`, `NO_OF_MICROWAVE_Add`, `NO_OF_U_SPACE_REQUIRED` ";
		d = ", ?";
		d += ", '"+jsonData.getDistrict()+"'";
		d += ", '"+jsonData.getState()+"'";
		d += ", '"+jsonData.getClutter()+"'";
		d += ", '"+jsonData.getSiteType()+"'";
		d += ", '"+jsonData.getBtsType()+"'";
		d += ", "+jsonData.getAdditionalLoad();
		d += ", "+jsonData.getTotalRatedPower();
		d += ", "+jsonData.getNoOfRFAntenna();
		d += ", "+jsonData.getNoOfRFAntenna_Delete();
		d += ", "+jsonData.getNoOfRFAntenna_Add();
		d += ", "+jsonData.getNoOfMicrowave();
		d += ", "+jsonData.getNoOfBBU();
		d += ", "+jsonData.getNoOfRRU();
		d += ", "+jsonData.getNoOfBTS();
		d += ", "+jsonData.getNoOfMCB();
		d += ", "+jsonData.getNoOfMassiveMIMOAntenna();
		d += ", "+jsonData.getNoOfIP55();
		d += ", "+jsonData.getNoOfRRU_Delete();
		d += ", "+jsonData.getNoOfRRU_Add();
		d += ", "+jsonData.getNoOfBBU_Delete();
		d += ", "+jsonData.getNoOfBBU_Add();
		d += ", "+jsonData.getNoOfMicrowave_Delete();
		d += ", "+jsonData.getNoOfMicrowave_Add();
		d += ", "+jsonData.getNoOfUSpaceRequired();
		
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `Operator`, `SR_DATE`, `CIRCLE_NAME`, `TAB_NAME`, `TVI_SITE_ID`, `Site_Name`"+t+", `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID` , `LATITUDE_1`, `LONGITUDE_1`, `LATITUDE_2`, `LONGITUDE_2`, `AGL_REQUIRED`, "
				+ "`TECHNOLOGY`, `ExtensionType`, `Count_Of_RF_Filter`, `REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		Double latitude2 = jsonData.getLatitude2() == 0.0 ? null : jsonData.getLatitude2();
		Double longitude2 = jsonData.getLongitude2() == 0.0 ? null : jsonData.getLongitude2();
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', '"+jsonData.getOperator()+"', CURRENT_TIMESTAMP, '"+jsonData.getCircleName()+"', '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', ? "+d+", '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", "+latitude2+", "+longitude2+", "+jsonData.getAglRequired()+", "
				+ "'"+jsonData.getTechnology()+"', '"+jsonData.getExtensionType()+"', "+jsonData.getCountOfRfFilter()+", ?, 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		getEm().createNativeQuery(hdrTable + hdrValue)
		.setParameter(1, jsonData.getSiteName())
		.setParameter(2, jsonData.getSiteAddress())
		.setParameter(3, jsonData.getRemark())
		.executeUpdate();
		
		if(jsonData.getNoOfMicrowave() != null && jsonData.getNoOfMicrowave() != 0){
			insertMwData(jsonData.getSrNumber(), jsonData.getMicrowaveList());
		}
		
		if(jsonData.getNoOfMicrowave_Delete() != null && jsonData.getNoOfMicrowave_Delete() != 0){
			insertMwDeleteData(jsonData.getSrNumber(), jsonData.getMicrowaveDeleteList());
		}
		
		if(jsonData.getNoOfMicrowave_Add() != null && jsonData.getNoOfMicrowave_Add() != 0){
			insertMwAddData(jsonData.getSrNumber(), jsonData.getMicrowaveAddList());
		}
		
		if(jsonData.getNoOfRRU() != null && jsonData.getNoOfRRU() != 0){
			insertRruData(jsonData.getSrNumber(), jsonData.getRruList());
		}
		
		if(jsonData.getNoOfBTS() != null && jsonData.getNoOfBTS() != 0){
			insertBtsData(jsonData.getSrNumber(), jsonData.getBtsList());
		}
		
		if(jsonData.getNoOfBBU() != null && jsonData.getNoOfBBU() != 0){
			insertBbuData(jsonData.getSrNumber(), jsonData.getBbuList());
		}
		
		if(jsonData.getNoOfBBU_Delete() != null && jsonData.getNoOfBBU_Delete() != 0){
			insertBbuDeleteData(jsonData.getSrNumber(), jsonData.getBbuDeleteList());
		}
		
		if(jsonData.getNoOfBBU_Add() != null && jsonData.getNoOfBBU_Add() != 0){
			insertBbuAddData(jsonData.getSrNumber(), jsonData.getBbuAddList());
		}
		
		if(jsonData.getNoOfRFAntenna() != null && jsonData.getNoOfRFAntenna() != 0){
			insertRfAntennaData(jsonData.getSrNumber(), jsonData.getRfAntennaList());
		}
		if(jsonData.getNoOfRFAntenna_Delete() != null && jsonData.getNoOfRFAntenna_Delete() != 0){
			insertRfAntennaDeleteData(jsonData.getSrNumber(), jsonData.getRfAntennaDeleteList());
		}
		if(jsonData.getNoOfRFAntenna_Add() != null && jsonData.getNoOfRFAntenna_Add() != 0){
			insertRfAntennaAddData(jsonData.getSrNumber(), jsonData.getRfAntennaAddList());
		}
		
		if(jsonData.getNoOfMCB() != null && jsonData.getNoOfMCB() != 0){
			insertMcbData(jsonData.getSrNumber(), jsonData.getMcbList());
		}
		
		if(jsonData.getNoOfMassiveMIMOAntenna() != null && jsonData.getNoOfMassiveMIMOAntenna() != 0){
			insertMassiveMimoAntennaData(jsonData.getSrNumber(), jsonData.getMassiveMIMOAntennaList());
		}
		
		if(jsonData.getNoOfIP55() != null && jsonData.getNoOfIP55() != 0){
			insertIp55Data(jsonData.getSrNumber(), jsonData.getIp55List());
		}
		
		if(jsonData.getNoOfRRU_Delete() != null && jsonData.getNoOfRRU_Delete() != 0){
			insertRruDeleteData(jsonData.getSrNumber(), jsonData.getRruDeleteList());
		}
		
		if(jsonData.getNoOfRRU_Add() != null && jsonData.getNoOfRRU_Add() != 0){
			insertRruAddData(jsonData.getSrNumber(), jsonData.getRruAddList());
		}
		
		
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllCircleName() {
		String sql = "SELECT `Number` FROM `No_Of_List` where `Type` = ? and `Is_Active` = ? "; 
		Query query = getEm().createNativeQuery(sql);
		query.setParameter(1, "allCircleList");
		query.setParameter(2, "Y");
		List<String> result = query.getResultList();
		List<String> circleList = new ArrayList<String>();
		if(result.size() !=0){
			String circleName = result.get(0);
			circleList = CommonFunction.convertCommaseparateStringToList(circleName);
		}
		return circleList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeMasterEntity> getAllEmployeeList() {
		String sql = "select em from EmployeeMasterEntity em ";
		Query query = getEm().createQuery(sql);
		return query.getResultList();
	}

	@Override
	public EmployeeMasterEntity getEmployeeDetails(EmployeeActionRequest jsonData) {
		return getEm().find(EmployeeMasterEntity.class, jsonData.getPrimaryId());
	}

	@Override
	public Object saveOrUpdateEmpData(EmployeeMasterEntity emEntity) {
		return save(emEntity);
	}
	
	@Override
	public Object saveOrUpdateEntityData(Object entity) {
		return save(entity);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<NbsMasterHdrEntity> getNbsHdrByCircleName(CommonDTO jsonData) {
		String sql = "select h from NbsMasterHdrEntity h where h.srNumber is not null ";
		if(!jsonData.getOperator().equalsIgnoreCase("TVI")){
			sql += " and h.operator = '"+jsonData.getOperator()+"' ";
		}
		
		if(jsonData.getIsHoUser().equalsIgnoreCase("N")){
			sql += " and h.circleName in (:circleName) ";
		}
		if(!jsonData.getFilterTviSiteId().equalsIgnoreCase("")){
			sql += " and h.tviSiteId like '"+jsonData.getFilterTviSiteId()+"' ";
		}
		if(!jsonData.getFilterSiteId().equalsIgnoreCase("")){
			sql += " and h.airtelSiteId like '"+jsonData.getFilterSiteId()+"' ";
		}
		
		if(!jsonData.getFilterSrNumber().equalsIgnoreCase("")){
			sql += " and (h.srNumber like '%"+jsonData.getFilterSrNumber()+"%' or "
					+ "h.spNumber like '%"+jsonData.getFilterSrNumber()+"%' or "
					+ "h.soNumber like '%"+jsonData.getFilterSrNumber()+"%') ";
		}
		
		if(!jsonData.getFilterCircleName().equalsIgnoreCase("")){
			sql += " and h.circleName = '"+jsonData.getFilterCircleName()+"' ";
		}
		if(!jsonData.getFilterProductType().equalsIgnoreCase("")){
			sql += " and h.tabName = '"+jsonData.getFilterProductType()+"' ";
		}
		if(jsonData.getFilterSrStatus().equalsIgnoreCase("SR")){
			if(!jsonData.getFilterStartDate().equalsIgnoreCase(""))
				sql += " and h.srDate >= '"+jsonData.getFilterStartDate()+"' and h.spDate is null ";
			else
				sql += " and h.srDate is not null and h.spDate is null ";
		}
		else if(jsonData.getFilterSrStatus().equalsIgnoreCase("SP")){
			if(!jsonData.getFilterStartDate().equalsIgnoreCase(""))
				sql += " and h.spDate >= '"+jsonData.getFilterStartDate()+"' and h.soDate is null ";
			else
				sql += " and h.spDate is not null and h.soDate is null ";
		}
		else if(jsonData.getFilterSrStatus().equalsIgnoreCase("SO")){
			if(!jsonData.getFilterStartDate().equalsIgnoreCase(""))
				sql += " and h.soDate >= '"+jsonData.getFilterStartDate()+"' and h.rfiDate is null ";
			else
				sql += " and h.soDate is not null and h.rfiDate is null ";
		}
		else if(jsonData.getFilterSrStatus().equalsIgnoreCase("RFI")){
			if(!jsonData.getFilterStartDate().equalsIgnoreCase(""))
				sql += " and h.rfiDate >= '"+jsonData.getFilterStartDate()+"' and h.rfiAcceptedDate is null ";
			else
				sql += " and h.rfiDate is not null and h.rfiAcceptedDate is null ";
		}
		else if(jsonData.getFilterSrStatus().equalsIgnoreCase("RFI Accepted")){
			if(!jsonData.getFilterStartDate().equalsIgnoreCase(""))
				sql += " and h.rfiAcceptedDate >= '"+jsonData.getFilterStartDate()+"' and h.rfsDate is null ";
			else
				sql += " and h.rfiAcceptedDate is not null and h.rfsDate is null ";
		}
		else if(jsonData.getFilterSrStatus().equalsIgnoreCase("RFS")){
			if(!jsonData.getFilterStartDate().equalsIgnoreCase(""))
				sql += " and h.rfsDate >= '"+jsonData.getFilterStartDate()+"' ";
			else
				sql += " and h.rfsDate is not null ";
		}
		else{
			if(!jsonData.getFilterStartDate().equalsIgnoreCase("")){
				sql += " and h.createDate >= '"+jsonData.getFilterStartDate()+"' ";
			}else{
				sql += " and h.createDate >= '2022-01-01' ";
			}
		}
		
		if(!jsonData.getFilterEndDate().equalsIgnoreCase("")){
			if(jsonData.getFilterSrStatus().equalsIgnoreCase("SR")){
				sql += " and h.srDate <= '"+jsonData.getFilterEndDate()+"' ";
			}
			else if(jsonData.getFilterSrStatus().equalsIgnoreCase("SP")){
				sql += " and h.spDate <= '"+jsonData.getFilterEndDate()+"' ";
			}
			else if(jsonData.getFilterSrStatus().equalsIgnoreCase("SO")){
				sql += " and h.soDate <= '"+jsonData.getFilterEndDate()+"' ";
			}
			else if(jsonData.getFilterSrStatus().equalsIgnoreCase("RFI")){
				sql += " and h.rfiDate <= '"+jsonData.getFilterEndDate()+"' ";
			}
			else if(jsonData.getFilterSrStatus().equalsIgnoreCase("RFI Accepted")){
				sql += " and h.rfiAcceptedDate <= '"+jsonData.getFilterEndDate()+"' ";
			}
			else if(jsonData.getFilterSrStatus().equalsIgnoreCase("RFS")){
				sql += " and h.rfsDate <= '"+jsonData.getFilterEndDate()+"' ";
			}
		}
		if(!jsonData.getFilterOperator().equalsIgnoreCase("")){
			sql += " and h.operator = '"+jsonData.getFilterOperator()+"' ";
		}
		sql += " order by h.createDate desc ";
		Query query = getEm().createQuery(sql);
		
		if(sql.contains(":circleName")){
			List<String> circleName = CommonFunction.convertCommaseparateStringToList(jsonData.getCircleName());
			query.setParameter("circleName", circleName);
		}
		
		List<NbsMasterHdrEntity> result = new ArrayList<>();
		if(jsonData.getTabName().equalsIgnoreCase("dashbord")){
			/* logic 1- start */
			Map<String, String> approvalFlow = new HashMap<String, String>();
			
			List<NbsMasterHdrEntity> tempResult = new ArrayList<>();
			List<NbsMasterHdrEntity> queryResult = query.getResultList();
			for(NbsMasterHdrEntity nb : queryResult){
				String comb = nb.getTabName()+":"+jsonData.getLoginEmpRole();
				String commSepaStatus = "";
				if(!approvalFlow.containsKey(comb)){
					commSepaStatus = getApprovalFlowStatus(jsonData.getLoginEmpRole(), nb.getTabName());
					if(!commSepaStatus.equalsIgnoreCase("")){
						approvalFlow.put(comb, commSepaStatus);
					}
					else{
						// for not require approval or not exist in `Approval_Flow` table
						approvalFlow.put(comb, "NA");
					}
				}
				else{
					commSepaStatus = approvalFlow.get(comb);
				}
				
				List<String> statusList = CommonFunction.convertCommaseparateStringToList(commSepaStatus);
				if(statusList.contains(nb.getStatus())){
					tempResult.add(nb);
				}
			}
			
			int maxRecord = tempResult.size();
			int pageNum = jsonData.getPageNum();
			int pageSize = jsonData.getRecordOnApage();
			jsonData.setMaxRecord(maxRecord);
			
			int startIndex = (pageNum-1) * pageSize;
			int endIndex = (startIndex + pageSize);
			
			for(int i=startIndex;i<endIndex;i++){
				if(i < maxRecord){
					result.add(tempResult.get(i));
				}
			}
			/* logic 1- end */
			
			/* logic 2- start */
			/*Map<String, String> approvalFlow = new HashMap<String, String>();
			String appFlowSql = "SELECT `TAB_NAME`, `Status` FROM `Approval_Flow` where "
					+ "`User_Role` = '"+jsonData.getLoginEmpRole()+"' and `Is_Active` = 'Y'";
		 	List<Object[]> appFlowResult = getAllTableData(appFlowSql);
		 	for(Object[] appFlow : appFlowResult){
		 		String tabName = String.valueOf(appFlow[0]);
		 		String status = String.valueOf(appFlow[1]);
		 		approvalFlow.put(tabName, status);
		 	}
		 	
		 	List<NbsMasterHdrEntity> tempResult = new ArrayList<>();
			List<NbsMasterHdrEntity> queryResult = query.getResultList();
		 	for(NbsMasterHdrEntity nb : queryResult){
				String comb = nb.getTabName();
				String commSepaStatus = approvalFlow.get(comb);
				if(commSepaStatus != null){
					//System.out.println(comb+" : "+commSepaStatus);
					List<String> statusList = CommonFunction.convertCommaseparateStringToList(commSepaStatus);
					if(statusList.contains(nb.getStatus())){
						tempResult.add(nb);
					}
				}
			}
			
			int maxRecord = tempResult.size();
			int pageNum = jsonData.getPageNum();
			int pageSize = jsonData.getRecordOnApage();
			jsonData.setMaxRecord(maxRecord);
			
			int startIndex = (pageNum-1) * pageSize;
			int endIndex = (startIndex + pageSize);
			
			for(int i=startIndex;i<endIndex;i++){
				if(i < maxRecord){
					result.add(tempResult.get(i));
				}
			}*/
			/* logic 2- end */
		}
		else{
			int maxRecord = query.getResultList().size();
			jsonData.setMaxRecord(maxRecord);
			int pageNum = jsonData.getPageNum();
			int pageSize = jsonData.getRecordOnApage();
			query.setFirstResult((pageNum-1) * pageSize);
			query.setMaxResults(pageSize);
			result = query.getResultList();
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NbsMasterDetEntity> getNbsDetailsBySrNumber(String srNumber) {
		String sql = "select d from NbsMasterDetEntity d where d.srNumber = ?";
		Query query = getEm().createQuery(sql);
		query.setParameter(1, srNumber);
		return query.getResultList();
	}

	@Override
	public NbsMasterHdrEntity getNbsHdrBySrNumber(String srNumber) {
		return getEm().find(NbsMasterHdrEntity.class, srNumber);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getNbsAuditBySrNumber(String srNumber) {
		String sql = "select DISTINCT d.EMP_ID, e.NAME, d.ACTION, d.REMARK, d.CREATE_DATE from NBS_AUDIT d join EMPLOYEE_MASTER e on d.EMP_ID = e.EMPLOYEE_ID "
				+ "where d.SR_NUMBER = ? order by d.CREATE_DATE desc";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter(1, srNumber);
		return query.getResultList();
	}

	@Override
	public void insertHdrAndDetOfODSCAnchor(SaveNBSRequest jsonData) {
		
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `Operator`, `SR_DATE`, `TAB_NAME`, `TVI_SITE_ID`, `Site_Name`, `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID` , `LATITUDE_1`, `LONGITUDE_1`, `LATITUDE_2`, `LONGITUDE_2`, `CIRCLE_NAME`, `TECHNOLOGY`, "
				+ "`AGL_required_for_ODSC`, `Airtel_Backhaul`, `AC_DC_Backup`, `Additional_power_back_up_of_2_Hrs`, `Camuflauging`,  "
				+ "`No_of_ODSC`, `NO_OF_RRU`, `NO_OF_BBU`, `NO_OF_MICROWAVE`, `No_Of_Poles`, `No_Of_HPSC_Antenna`, `U_Space`, `SITE_TYPE`, `Power_Rating_Of_Equipment`, `Total_Rated_Power_in_KW`, `REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		Double latitude2 = jsonData.getLatitude2() == 0.0 ? null : jsonData.getLatitude2();
		Double longitude2 = jsonData.getLongitude2() == 0.0 ? null : jsonData.getLongitude2();
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', '"+jsonData.getOperator()+"', CURRENT_TIMESTAMP, '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', ?, '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", "+latitude2+", "+longitude2+", '"+jsonData.getCircleName()+"', '"+jsonData.getTechnology()+"', "
				+ "'"+jsonData.getAglRequiredODSC()+"', '"+jsonData.getAirtelBackhaul()+"', '"+jsonData.getAcDcBackup()+"', '"+jsonData.getAdditionalPowerBackup2Hrs()+"', '"+jsonData.getCamuflauging()+"', "
				+ ""+jsonData.getNoOfODSC()+", "+jsonData.getNoOfRRU()+", "+jsonData.getNoOfBBU()+", "+jsonData.getNoOfMicrowave()+", "+jsonData.getNoOfPole()+", "+jsonData.getNoOfHPSCAntenna()+", "+jsonData.getuSpace()+", '"+jsonData.getSiteType()+"', "+jsonData.getPowerRatingOfEquipment()+", "+jsonData.getTotalRatedPower()+", ?, 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
	
		getEm().createNativeQuery(hdrTable + hdrValue)
		.setParameter(1, jsonData.getSiteName())
		.setParameter(2, jsonData.getRemark())
		.executeUpdate();
		
		if(jsonData.getNoOfODSC() !=null && jsonData.getNoOfODSC() != 0){
			insertOdscData(jsonData.getSrNumber(), jsonData.getOdscList());
		}
		
		if(jsonData.getNoOfMicrowave() !=null && jsonData.getNoOfMicrowave() != 0){
			insertMwData(jsonData.getSrNumber(), jsonData.getMicrowaveList());
		}
		if(jsonData.getNoOfBBU() !=null && jsonData.getNoOfBBU() != 0){
			insertBbuData(jsonData.getSrNumber(), jsonData.getBbuList());
		}
		if(jsonData.getNoOfRRU() !=null && jsonData.getNoOfRRU() != 0){
			insertRruData(jsonData.getSrNumber(), jsonData.getRruList());
		}
		if(jsonData.getNoOfPole() !=null && jsonData.getNoOfPole() !=0){
			insertPolesData(jsonData.getSrNumber(), jsonData.getNoOfPoleList());
		}
		if(jsonData.getNoOfHPSCAntenna() !=null && jsonData.getNoOfHPSCAntenna() !=0){
			insertHpscAntennaData(jsonData.getSrNumber(), jsonData.getNoOfHPSCAntennaList());
		}
		
		
	}

	/*@Override
	public void insertHdrAndDetOfCOW(SaveNBSRequest jsonData) {
		
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `SR_DATE`, `TAB_NAME`, `TVI_SITE_ID`, `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID` , `LATITUDE_1`, `LONGITUDE_1`, `LATITUDE_2`, `LONGITUDE_2`, `SITE_ADDRESS`, `CIRCLE_NAME`, `STATE`, `PINCODE`, `CLUTTER`, `DISTRICT`, `SITE_TYPE`,`TOWN_VILLAGE`, `TECHNOLOGY`, "
				+ "`Cow_Type`, `Service_Order_Contract_Period`, `Height_of_highest_Antenna`, `Rated_Power_Consumption`, `Weight_on_Tower`, `Rack_space_for_BBU`, `Rack_space_for_MW_IDU`, `Power_Supply`, `WEIGHT_OF_ADDITIONAL_ANTENNA`, `Additional_BTS_floor_Space`, `RF_ANTENNA_MOUNT_HEIGHT`, `MW_ANTENNA_MOUNT_HEIGHT`, "
				+ "`NO_OF_RF_ANTENNA`, `NO_OF_MICROWAVE`, `NO_OF_RRU`, `NO_OF_BBU`, `NO_OF_BTS`, `REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', CURRENT_TIMESTAMP, '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", "+jsonData.getLatitude2()+", "+jsonData.getLongitude2()+", '"+jsonData.getSiteAddress()+"', '"+jsonData.getCircleName()+"', '"+jsonData.getState()+"', '"+jsonData.getPincode()+"', '"+jsonData.getClutter()+"', '"+jsonData.getDistrict()+"', '"+jsonData.getSiteType()+"', '"+jsonData.getTownVillage()+"', '"+jsonData.getTechnology()+"', "
				+ "'"+jsonData.getCowType()+"', '"+jsonData.getServiceContractPeriod()+"', "+jsonData.getHeightOfHighestAntenna()+", "+jsonData.getRatedPowerConsumption()+", "+jsonData.getTowerWeight()+", "+jsonData.getRackSpaceForBBU()+", "+jsonData.getRackSpaceForMW()+", '"+jsonData.getPowerSupply()+"', "+jsonData.getWeightOfAdditionalAntenna()+", "+jsonData.getAdditionBTSFloorSpace()+", "+jsonData.getRfAntennaMountHeight()+", '"+jsonData.getMwAntennaMountHeight()+"', "
				+ ""+jsonData.getNoOfRFAntenna()+", "+jsonData.getNoOfMicrowave()+", "+jsonData.getNoOfRRU()+", "+jsonData.getNoOfBBU()+", "+jsonData.getNoOfBTS()+", '"+jsonData.getRemark()+"', 'NB01' , '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		getEm().createNativeQuery(hdrTable + hdrValue).executeUpdate();
		
		if(jsonData.getNoOfRFAntenna() != null && jsonData.getNoOfRFAntenna() != 0){
			insertRfAntennaData(jsonData.getSrNumber(), jsonData.getRfAntennaList());
		}
		
		if(jsonData.getNoOfMicrowave() != null && jsonData.getNoOfMicrowave() != 0){
			insertMwData(jsonData.getSrNumber(), jsonData.getMicrowaveList());
		}
		
		if(jsonData.getNoOfRRU() != null && jsonData.getNoOfRRU() != 0){
			insertRruData(jsonData.getSrNumber(), jsonData.getRruList());
		}
		
		if(jsonData.getNoOfBTS() != null && jsonData.getNoOfBTS() != 0){
			insertBtsData(jsonData.getSrNumber(), jsonData.getBtsList());
		}
		
		if(jsonData.getNoOfBBU() != null && jsonData.getNoOfBBU() != 0){
			insertBbuData(jsonData.getSrNumber(), jsonData.getBbuList());
		}
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTVISiteIdCircleName(String sql) {
		Query query = getEm().createNativeQuery(sql);
		return query.getResultList();
	}

	/*@Override
	public void insertHdrAndDetSharing(SaveNBSRequest jsonData) {
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `SR_DATE`, `TAB_NAME`, `TVI_SITE_ID`, `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID`, `LATITUDE_1`, `LONGITUDE_1`, `SITE_ADDRESS`, `CIRCLE_NAME`, `STATE`, `PINCODE`, `CLUTTER`, `DISTRICT`, `SITE_TYPE`, `TOWN_VILLAGE`, `TECHNOLOGY`, "
				+ "`EB_AVAILABILITY`, `DG_AVAILABILITY`,`Total_Rated_Power_in_KW`, `NO_OF_RF_ANTENNA`, `NO_OF_MICROWAVE`, `NO_OF_BBU`, "
				+ "`NO_OF_RRU`, `NO_OF_BTS`, `NO_OF_MCB`, `MW_RACK`, "
				+ "`NO_OF_U_SPACE_REQUIRED`, `REMARK`, `STATUS`,`CREATE_BY`, `CREATE_DATE`) ";
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', CURRENT_TIMESTAMP, '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", '"+jsonData.getSiteAddress()+"', '"+jsonData.getCircleName()+"', '"+jsonData.getState()+"', '"+jsonData.getPincode()+"', '"+jsonData.getClutter()+"', '"+jsonData.getDistrict()+"', '"+jsonData.getSiteType()+"', '"+jsonData.getTownVillage()+"', '"+jsonData.getTechnology()+"', "
				+ "'"+jsonData.getEbAvailability()+"', '"+jsonData.getDgAvailability()+"', "+jsonData.getTotalRatedPower()+", "+jsonData.getNoOfRFAntenna()+", "+jsonData.getNoOfMicrowave()+", "+jsonData.getNoOfBBU()+", "
				+ ""+jsonData.getNoOfRRU()+", "+jsonData.getNoOfBTS()+", "+jsonData.getNoOfMCB()+", '"+jsonData.getMwRack()+"', "
				+ ""+jsonData.getNoOfUSpaceRequired()+", '"+jsonData.getRemark()+"', 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		getEm().createNativeQuery(hdrTable + hdrValue).executeUpdate();
		
		if(jsonData.getNoOfMicrowave() != null && jsonData.getNoOfMicrowave() != 0){
			insertMwData(jsonData.getSrNumber(), jsonData.getMicrowaveList());
		}
		
		if(jsonData.getNoOfRRU() != null && jsonData.getNoOfRRU() != 0){
			insertRruData(jsonData.getSrNumber(), jsonData.getRruList());
		}
		
		if(jsonData.getNoOfBTS() != null && jsonData.getNoOfBTS() != 0){
			insertBtsData(jsonData.getSrNumber(), jsonData.getBtsList());
		}
		
		if(jsonData.getNoOfBBU() != null && jsonData.getNoOfBBU() != 0){
			insertBbuData(jsonData.getSrNumber(), jsonData.getBbuList());
		}
		
		if(jsonData.getNoOfMCB() != null && jsonData.getNoOfMCB() != 0){
			insertMcbData(jsonData.getSrNumber(), jsonData.getMcbList());
		}
	}*/

	@Override
	public void insertHdrAndDetODSCSharing(SaveNBSRequest jsonData) {
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `Operator`, `SR_DATE`, `TAB_NAME`, `TVI_SITE_ID`, `Site_Name`, `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID` , `LATITUDE_1`, `LONGITUDE_1`, `CIRCLE_NAME`, `TECHNOLOGY`, "
				+ "`AGL_required_for_ODSC`, `Airtel_Backhaul`, `AC_DC_Backup`, `Additional_power_back_up_of_2_Hrs`, `Camuflauging`,  "
				+ "`REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', '"+jsonData.getOperator()+"', CURRENT_TIMESTAMP, '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', ?, '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", '"+jsonData.getCircleName()+"', '"+jsonData.getTechnology()+"', "
				+ "'"+jsonData.getAglRequiredODSC()+"', '"+jsonData.getAirtelBackhaul()+"', '"+jsonData.getAcDcBackup()+"', '"+jsonData.getAdditionalPowerBackup2Hrs()+"', '"+jsonData.getCamuflauging()+"', "
				+ "?, 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
	
		getEm().createNativeQuery(hdrTable + hdrValue)
		.setParameter(1, jsonData.getSiteName())
		.setParameter(2, jsonData.getRemark())
		.executeUpdate();
		
	}

	/*@Override
	public void insertHdrAndDetODCSmartSplitSharing(SaveNBSRequest jsonData) {
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `SR_DATE`, `TAB_NAME`, `TVI_SITE_ID`, `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID`, `LATITUDE_1`, `LONGITUDE_1`, `SITE_ADDRESS`, `CIRCLE_NAME`, `STATE`, `PINCODE`, `CLUTTER`, `DISTRICT`, `SITE_TYPE`,`TOWN_VILLAGE`, `TECHNOLOGY`, "
				+ "`Smart_Split_Type`, `Floor_Space_Of_OD_Cabinate`, `AC_Power_Load`, `EB_AVAILABILITY`, `FIBER_TERMINATION`, `Total_Power_Required`, "
				+ "`REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', CURRENT_TIMESTAMP, '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", '"+jsonData.getSiteAddress()+"', '"+jsonData.getCircleName()+"', '"+jsonData.getState()+"', '"+jsonData.getPincode()+"', '"+jsonData.getClutter()+"', '"+jsonData.getDistrict()+"', '"+jsonData.getSiteType()+"', '"+jsonData.getTownVillage()+"', '"+jsonData.getTechnology()+"', "
				+ "'"+jsonData.getSmartSplitType()+"', '"+jsonData.getFloorSpaceOfODCabinet()+"', '"+jsonData.getAcPowerLoad()+"', '"+jsonData.getEbAvailability()+"', '"+jsonData.getFiberTermination()+"', "+jsonData.getTotalPowerRequired()+", "
				+ "'"+jsonData.getRemark()+"', 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		getEm().createNativeQuery(hdrTable + hdrValue).executeUpdate();
		
	}*/

	/*@Override
	public void insertHdrAndDetMassiveMIMOSharing(SaveNBSRequest jsonData) {
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `SR_DATE`, `TAB_NAME`, `TVI_SITE_ID`, `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID`, `LATITUDE_1`, `LONGITUDE_1`, `SITE_ADDRESS`, `CIRCLE_NAME`, `STATE`, `PINCODE`, `CLUTTER`, `DISTRICT`, `SITE_TYPE`,`TOWN_VILLAGE`, `TECHNOLOGY`, "
				+ "`Existing_Airtel_configuration_before_MIMO`, `No_of_Massive_MIMO_Antenna`, `U_space_for_BBU`, `Power_requirement`, `Power_Threshold_in_case_of_MIMO_Expansion`, `Existing_LLR_of_TVI_Site`, `Additional_LLR_due_to_Addition_of_MIMO`, "
				+ "`REMARK`, `STATUS`,`CREATE_BY`, `CREATE_DATE`) ";
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', CURRENT_TIMESTAMP, '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", '"+jsonData.getSiteAddress()+"', '"+jsonData.getCircleName()+"', '"+jsonData.getState()+"', '"+jsonData.getPincode()+"', '"+jsonData.getClutter()+"', '"+jsonData.getDistrict()+"', '"+jsonData.getSiteType()+"', '"+jsonData.getTownVillage()+"', '"+jsonData.getTechnology()+"', "
				+ "'"+jsonData.getExistingAirtelConfigurationBeforeMIMO()+"', "+jsonData.getNoOfMassiveMIMOAntenna()+", "+jsonData.getuSpaceForBBU()+", "+jsonData.getPowerRequirement()+", "+jsonData.getPowerThresholdInCaseOfMIMOExpansion()+", '"+jsonData.getExistingLLROfTVISite()+"', "+jsonData.getAdditionalLLRDueToAdditionalMIMO()+", "
				+ "'"+jsonData.getRemark()+"', 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		getEm().createNativeQuery(hdrTable + hdrValue).executeUpdate();
		
		if(jsonData.getNoOfMassiveMIMOAntenna() != null && jsonData.getNoOfMassiveMIMOAntenna() != 0){
			insertMassiveMimoAntennaData(jsonData.getSrNumber(), jsonData.getMassiveMIMOAntennaList());
		}
		
		
	}*/

	@Override
	public int updateBulkdataValue(String updateTable) {
		return getEm().createNativeQuery(updateTable).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getNoOfList(String sql) {
		Query query = getEm().createNativeQuery(sql);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllNbsStatus() {
		String sql = "SELECT `STATUS`,`DESCRIPTION`,`TAB_NAME`,`DESC_DETAILS` FROM `NBS_STATUS` ";
		Query query = getEm().createNativeQuery(sql);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	private String getApprovalFlowStatus(String role, String tabName){
		String sql = "SELECT `Status` FROM `Approval_Flow` where `User_Role` = '"+role+"' ";
		if(tabName != null){
			sql += " and `TAB_NAME` = '"+tabName+"' ";
		}
		sql += " and `Is_Active` = 'Y' ";
		Query query = getEm().createNativeQuery(sql);
		List<String> result = query.getResultList();
		String status = "";
		int i=0;
		for(String s : result){
			i++;
			status += s;
			if(i != result.size()){
				status += ",";
			}
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> sendOTPtoMobile(CommonDTO jsonData) {
		String sql = "SELECT `EMPLOYEE_ID` FROM `EMPLOYEE_MASTER` WHERE `EMPLOYEE_ID`= :empId and `MOBILE`= :mobile ";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("empId", jsonData.getLoginEmpId());
		query.setParameter("mobile", jsonData.getMobileNumber());
		return query.getResultList();
	}

	@Transactional
	@Override
	public int changePassword(CommonDTO jsonData) {
		String sql = "update `EMPLOYEE_MASTER` set `PASSWORD` = '"+jsonData.getNewPassword()+"' WHERE "
		+ " `EMPLOYEE_ID` = '"+jsonData.getLoginEmpId()+"' "
		+ " and `MOBILE` = '"+jsonData.getMobileNumber()+"' ";
		return getEm().createNativeQuery(sql).executeUpdate();
	}

	@Transactional
	@Override
	public void insertActiveDataInDet(CommonDTO jsonData) {
		if(jsonData.getNoOfMicrowave() != null && jsonData.getNoOfMicrowave() != 0){
			insertMwData(jsonData.getSrNumber(), jsonData.getMicrowaveList());
		}
		
		if(jsonData.getNoOfRRU() != null && jsonData.getNoOfRRU() != 0){
			insertRruData(jsonData.getSrNumber(), jsonData.getRruList());
		}
		
		if(jsonData.getNoOfBTS() != null && jsonData.getNoOfBTS() != 0){
			insertBtsData(jsonData.getSrNumber(), jsonData.getBtsList());
		}
		
		if(jsonData.getNoOfBBU() != null && jsonData.getNoOfBBU() != 0){
			insertBbuData(jsonData.getSrNumber(), jsonData.getBbuList());
		}
		
		if(jsonData.getNoOfRFAntenna() != null && jsonData.getNoOfRFAntenna() != 0){
			insertRfAntennaData(jsonData.getSrNumber(), jsonData.getRfAntennaList());
		}
		
		if(jsonData.getNoOfMCB() != null && jsonData.getNoOfMCB() != 0){
			insertMcbData(jsonData.getSrNumber(), jsonData.getMcbList());
		}
		
		if(jsonData.getNoOfODSC() != null && jsonData.getNoOfODSC() != 0){
			insertOdscData(jsonData.getSrNumber(), jsonData.getOdscList());
		}
				
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getNextLevelRole(String sql) {
		Query query = getEm().createNativeQuery(sql);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCircleOperationHeadOnCircle(String sql) {
		Query query = getEm().createNativeQuery(sql);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllTableData(String sql) {
		Query query = getEm().createNativeQuery(sql);
		return query.getResultList();
	}

	@Override
	public void insertHdrAndDetOfIwan(SaveNBSRequest jsonData) {
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `Operator`, `SR_DATE`, `CIRCLE_NAME`, `TAB_NAME`, `TVI_SITE_ID`, `Site_Name`, `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID` , `LATITUDE_1`, `LONGITUDE_1`, `AGL_REQUIRED`, "
				+ "`SITE_ADDRESS`, `DISTRICT`, `STATE`, `CLUTTER`, `SITE_TYPE`, "
				+ "`NO_OF_UBR_ANTENNA`, `WEIGHT_OF_ADDITIONAL_ANTENNA`, `Type_Of_Antenna`, `U_Space`, `Additional_Power_required`, `NO_OF_MCB`, `Additional_Load`, `Total_Rated_Power_in_KW`, `REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', '"+jsonData.getOperator()+"', CURRENT_TIMESTAMP, '"+jsonData.getCircleName()+"', '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', ?, '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", "+jsonData.getAglRequired()+", "
				+ "?, '"+jsonData.getDistrict()+"', '"+jsonData.getState()+"', '"+jsonData.getClutter()+"', '"+jsonData.getSiteType()+"', "
				+ ""+jsonData.getNoOfUBR_Antenna()+", "+jsonData.getWeightOfAntenna()+", '"+jsonData.getTypeOfAntenna()+"', '"+jsonData.getuSpace()+"', "+jsonData.getPowerRequired()+", "+jsonData.getNoOfMCB()+", "+jsonData.getAdditionalLoad()+", "+jsonData.getTotalRatedPower()+", ?, 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		getEm().createNativeQuery(hdrTable + hdrValue)
		.setParameter(1, jsonData.getSiteName())
		.setParameter(2, jsonData.getSiteAddress())
		.setParameter(3, jsonData.getRemark())
		.executeUpdate();
		
	}
	
	@Override
	public void insertHdrAndDetOfMcu(SaveNBSRequest jsonData) {
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `Operator`, `SR_DATE`, `CIRCLE_NAME`, `TAB_NAME`, `TVI_SITE_ID`, `Site_Name`, `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID` , `LATITUDE_1`, `LONGITUDE_1`, `AGL_REQUIRED`, "
				+ "`SITE_ADDRESS`, `DISTRICT`, `STATE`, `CLUTTER`, `SITE_TYPE`, "
				+ "`U_Space`, `Additional_Power_required`, `NO_OF_MCB`, `Additional_Load`, `Total_Rated_Power_in_KW`, `REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', '"+jsonData.getOperator()+"', CURRENT_TIMESTAMP, '"+jsonData.getCircleName()+"', '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', ?, '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", "+jsonData.getAglRequired()+", "
				+ "?, '"+jsonData.getDistrict()+"', '"+jsonData.getState()+"', '"+jsonData.getClutter()+"', '"+jsonData.getSiteType()+"', "
				+ ""+jsonData.getuSpace()+", "+jsonData.getPowerRequired()+", "+jsonData.getNoOfMCB()+", "+jsonData.getAdditionalLoad()+", "+jsonData.getTotalRatedPower()+", ?, 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		getEm().createNativeQuery(hdrTable + hdrValue)
		.setParameter(1, jsonData.getSiteName())
		.setParameter(2, jsonData.getSiteAddress())
		.setParameter(3, jsonData.getRemark())
		.executeUpdate();
		
	}

	@Override
	public void insertHdrAndDetOfFibreTermination(SaveNBSRequest jsonData) {
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `Operator`, `SR_DATE`, `CIRCLE_NAME`, `TAB_NAME`, `TVI_SITE_ID`, `Site_Name`, `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID` , `LATITUDE_1`, `LONGITUDE_1`, `AGL_REQUIRED`, "
				+ "`SITE_ADDRESS`, `DISTRICT`, `STATE`, `CLUTTER`, `SITE_TYPE`, "
				+ "`NO_OF_U_SPACE_REQUIRED`, `POWER_CONSUMPTION`, `NO_OF_MCB`, `Additional_Load`, `Total_Rated_Power_in_KW`, `REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', '"+jsonData.getOperator()+"', CURRENT_TIMESTAMP, '"+jsonData.getCircleName()+"', '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', ?, '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", "+jsonData.getAglRequired()+", "
				+ "?, '"+jsonData.getDistrict()+"', '"+jsonData.getState()+"', '"+jsonData.getClutter()+"', '"+jsonData.getSiteType()+"', "
				+ ""+jsonData.getNoOfUSpaceRequired()+", "+jsonData.getPowerConsumption()+", "+jsonData.getNoOfMCB()+", "+jsonData.getAdditionalLoad()+", "+jsonData.getTotalRatedPower()+", ?, 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		getEm().createNativeQuery(hdrTable + hdrValue)
		.setParameter(1, jsonData.getSiteName())
		.setParameter(2, jsonData.getSiteAddress())
		.setParameter(3, jsonData.getRemark())
		.executeUpdate();
		
	}

	@Override
	public void insertHdrAndDetOfTclRewin(SaveNBSRequest jsonData) {
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `Operator`, `SR_DATE`, `CIRCLE_NAME`, `TAB_NAME`, `TVI_SITE_ID`, `Site_Name`, `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID` , `LATITUDE_1`, `LONGITUDE_1`, `AGL_REQUIRED`, "
				+ "`SITE_ADDRESS`, `DISTRICT`, `STATE`, `CLUTTER`, `SITE_TYPE`, "
				+ "`NO_OF_UBR_ANTENNA`, `WEIGHT_OF_ADDITIONAL_ANTENNA`, `U_Space_For_E1_to_Ethernet_Converting_Unit`, `U_Space`, `Additional_Power_required`, `Additional_Load`, `Total_Rated_Power_in_KW`, `REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', '"+jsonData.getOperator()+"', CURRENT_TIMESTAMP, '"+jsonData.getCircleName()+"', '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', ?, '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", "+jsonData.getAglRequired()+", "
				+ "?, '"+jsonData.getDistrict()+"', '"+jsonData.getState()+"', '"+jsonData.getClutter()+"', '"+jsonData.getSiteType()+"', "
				+ ""+jsonData.getNoOfUBR_Antenna()+", "+jsonData.getWeightOfAntenna()+", "+jsonData.getuSpace_ethernet()+", '"+jsonData.getuSpace()+"', "+jsonData.getPowerRequired()+", "+jsonData.getAdditionalLoad()+", "+jsonData.getTotalRatedPower()+", ?, 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		getEm().createNativeQuery(hdrTable + hdrValue)
		.setParameter(1, jsonData.getSiteName())
		.setParameter(2, jsonData.getSiteAddress())
		.setParameter(3, jsonData.getRemark())
		.executeUpdate();
		
	}

	@Override
	public void insertHdrAndDetOfHexOlt(SaveNBSRequest jsonData) {
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `Operator`, `SR_DATE`, `CIRCLE_NAME`, `TAB_NAME`, `TVI_SITE_ID`, `Site_Name`, `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID` , `LATITUDE_1`, `LONGITUDE_1`, `AGL_REQUIRED`, "
				+ "`SITE_ADDRESS`, `DISTRICT`, `STATE`, `CLUTTER`, `SITE_TYPE`, "
				+ "`FLOOR_LENGTH`, `FIBER_ENTRY`, `NO_OF_MCB`, `MCB_Required_In_AMP`, `Additional_Power_required`, `Additional_Load`, `Total_Rated_Power_in_KW`, `REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', '"+jsonData.getOperator()+"', CURRENT_TIMESTAMP, '"+jsonData.getCircleName()+"', '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', ?, '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", "+jsonData.getAglRequired()+", "
				+ "?, '"+jsonData.getDistrict()+"', '"+jsonData.getState()+"', '"+jsonData.getClutter()+"', '"+jsonData.getSiteType()+"', "
				+ ""+jsonData.getFloorLength()+", '"+jsonData.getFiberEntry()+"', "+jsonData.getNoOfMCB()+", "+jsonData.getMcbRequiredInAmp()+", "+jsonData.getPowerRequired()+", "+jsonData.getAdditionalLoad()+", "+jsonData.getTotalRatedPower()+", ?, 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		getEm().createNativeQuery(hdrTable + hdrValue)
		.setParameter(1, jsonData.getSiteName())
		.setParameter(2, jsonData.getSiteAddress())
		.setParameter(3, jsonData.getRemark()).
		executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isExistSR(String sql) {
		Query query = getEm().createNativeQuery(sql);
		List<String> result = query.getResultList();
		if(result.size() != 0){
			return true;
		}
		return false;
	}

	@Override
	public void submitComplain(ComplainDTO jsonData) {
		String t = "INSERT INTO `Complain`(`SR_Number`, `Raise_Description`, `Raise_By`, `Raise_Date`, `Image`)";
		String d = "('"+jsonData.getSrNumber()+"', ?, '"+jsonData.getLoginEmpId()+"', CURDATE(), '"+jsonData.getImgStr()+"')";
		getEm().createNativeQuery(t+ " VALUES " +d).setParameter(1, jsonData.getDescription()).executeUpdate();
		
	}

	@Override
	public void insertHdrAndDetSmartSplit(SaveNBSRequest jsonData) {
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `Operator`, `SR_DATE`, `CIRCLE_NAME`, `TAB_NAME`, `TVI_SITE_ID`, `Site_Name`, `AIRTEL_SITE_ID`, `AIRTEL_LOCATOR_ID` , `LATITUDE_1`, `LONGITUDE_1`, `TECHNOLOGY`, "
				+ "`Floor_Space_Of_OD_Cabinate`, `Power_requirement`, `NO_OF_MCB`, `MCB_Required_In_AMP`, `DG_AVAILABILITY`, `AC_DC_Backup`, `REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', '"+jsonData.getOperator()+"', CURRENT_TIMESTAMP, '"+jsonData.getCircleName()+"', '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', ?, '"+jsonData.getAirtelSiteId()+"', '"+jsonData.getAirtelLocatorId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", '"+jsonData.getTechnology()+"', "
				+ "'"+jsonData.getRowSpace()+"', "+jsonData.getAcPower()+", "+jsonData.getNoOfMcb()+", "+jsonData.getMcbAmp()+", '"+jsonData.getDgStatus()+"', '"+jsonData.getBatteryBackup()+"', ?, 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		String sql = hdrTable + hdrValue;
		//System.out.println("insertHdrAndDetSmartSplit :: "+sql);
		getEm().createNativeQuery(sql)
		.setParameter(1, jsonData.getSiteName())
		.setParameter(2, jsonData.getRemark()).
		executeUpdate();
		
	}

	@Override
	public void editDataForHPSC(CommonDTO jsonData) {
		if(jsonData.isNoOfPolesEdit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.POLE+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfPole() !=null && jsonData.getNoOfPole() !=0){
				insertPolesData(jsonData.getSrNumber(), jsonData.getNoOfPoleList());
			}
		}
		if(jsonData.isNoOfHpscAntennaEdit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.HPSC_Antenna+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfHPSCAntenna() !=null && jsonData.getNoOfHPSCAntenna() !=0){
				insertHpscAntennaData(jsonData.getSrNumber(), jsonData.getNoOfHPSCAntennaList());
			}
		}
		if(jsonData.isNoOfBbuEdit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.BBU+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfBBU() !=null && jsonData.getNoOfBBU() != 0){
				insertBbuData(jsonData.getSrNumber(), jsonData.getBbuList());
			}
		}
		if(jsonData.isNoOfRruEdit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.RRU+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfRRU() !=null && jsonData.getNoOfRRU() != 0){
				insertRruData(jsonData.getSrNumber(), jsonData.getRruList());
			}
		}
	}

	@Override
	public void editAnyDetData(CommonDTO jsonData) {
		if(jsonData.isRFEdit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.RF_ANTENNA+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfRFAntenna() != null && jsonData.getNoOfRFAntenna() != 0){
				insertRfAntennaData(jsonData.getSrNumber(), jsonData.getRfAntennaList());
			}
		}
		if(jsonData.isRFDelete_Edit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.RF_ANTENNA_Delete+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			if(jsonData.getNoOfRFAntenna_Delete() != null && jsonData.getNoOfRFAntenna_Delete() != 0){
				insertRfAntennaDeleteData(jsonData.getSrNumber(), jsonData.getRfAntennaDeleteList());
			}
		}
		if(jsonData.isRFAdd_Edit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.RF_ANTENNA_Add+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			if(jsonData.getNoOfRFAntenna_Add() != null && jsonData.getNoOfRFAntenna_Add() != 0){
				insertRfAntennaAddData(jsonData.getSrNumber(), jsonData.getRfAntennaAddList());
			}
			
		}
		if(jsonData.isRRUAdd_Edit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.RRU_Add+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfRRU_Add() != null && jsonData.getNoOfRRU_Add() != 0){
				insertRruAddData(jsonData.getSrNumber(), jsonData.getRruAddList());
			}
		}
		if(jsonData.isRRUDelete_Edit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.RRU_Delete+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfRRU_Delete() != null && jsonData.getNoOfRRU_Delete() != 0){
				insertRruDeleteData(jsonData.getSrNumber(), jsonData.getRruDeleteList());
			}
		}
		if(jsonData.isMWEdit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.MICROWAVE+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfMicrowave() != null && jsonData.getNoOfMicrowave() != 0){
				insertMwData(jsonData.getSrNumber(), jsonData.getMicrowaveList());
			}
		}
		if(jsonData.isMWAdd_Edit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.MICROWAVE_Add+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfMicrowave_Add() != null && jsonData.getNoOfMicrowave_Add() != 0){
				insertMwAddData(jsonData.getSrNumber(), jsonData.getMicrowaveAddList());
			}
		}
		if(jsonData.isMWDelete_Edit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.MICROWAVE_Delete+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfMicrowave_Delete() != null && jsonData.getNoOfMicrowave_Delete() != 0){
				insertMwDeleteData(jsonData.getSrNumber(), jsonData.getMicrowaveDeleteList());
			}
		}
		if(jsonData.isBTSEdit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.BTS+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfBTS() != null && jsonData.getNoOfBTS() != 0){
				insertBtsData(jsonData.getSrNumber(), jsonData.getBtsList());
			}
		}
		if(jsonData.isNoOfBbuEdit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.BBU+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfBBU() != null && jsonData.getNoOfBBU() != 0){
				insertBbuData(jsonData.getSrNumber(), jsonData.getBbuList());
			}
		}
		if(jsonData.isBBUAdd_Edit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.BBU_Add+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfBBU_Add() != null && jsonData.getNoOfBBU_Add() != 0){
				insertBbuAddData(jsonData.getSrNumber(), jsonData.getBbuAddList());
			}
		}
		if(jsonData.isBBUDelete_Edit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.BBU_Delete+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfBBU_Delete() != null && jsonData.getNoOfBBU_Delete() != 0){
				insertBbuDeleteData(jsonData.getSrNumber(), jsonData.getBbuDeleteList());
			}
		}
		if(jsonData.isNoOfRruEdit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.RRU+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfRRU() != null && jsonData.getNoOfRRU() != 0){
				insertRruData(jsonData.getSrNumber(), jsonData.getRruList());
			}
		}
		if(jsonData.isRRUAdd_Edit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.RRU_Add+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfRRU_Add() != null && jsonData.getNoOfRRU_Add() != 0){
				insertRruAddData(jsonData.getSrNumber(), jsonData.getRruAddList());
			}
		}
		if(jsonData.isRRUDelete_Edit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.RRU_Delete+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfRRU_Delete() != null && jsonData.getNoOfRRU_Delete() != 0){
				insertRruDeleteData(jsonData.getSrNumber(), jsonData.getRruDeleteList());
			}
		}
		if(jsonData.isMCBEdit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.MCB+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfMCB() != null && jsonData.getNoOfMCB() != 0){
				insertMcbData(jsonData.getSrNumber(), jsonData.getMcbList());
			}
		}
		if(jsonData.isMimoEdit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.Massive_MIMO_ANTENNA+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfMassiveMIMOAntenna() != null && jsonData.getNoOfMassiveMIMOAntenna() != 0){
				insertMassiveMimoAntennaData(jsonData.getSrNumber(), jsonData.getMassiveMIMOAntennaList());
			}
		}
		if(jsonData.isIp55Edit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.IP55+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			
			if(jsonData.getNoOfIP55() != null && jsonData.getNoOfIP55() != 0){
				insertIp55Data(jsonData.getSrNumber(), jsonData.getIp55List());
			}
		}
		if(jsonData.isODSCEdit()){
			String sql = "UPDATE `NBS_MASTER_DET` set `SR_NUMBER` = concat(`SR_NUMBER`,'_updated'), `UPDATE_DATE` = CURRENT_TIMESTAMP "
					+ "where `SR_NUMBER` = '"+jsonData.getSrNumber()+"' and `TYPE` = '"+Constant.ODSC+"' ";
			getEm().createNativeQuery(sql).executeUpdate();
			if(jsonData.getNoOfODSC() != null && jsonData.getNoOfODSC() != 0){
				insertOdscData(jsonData.getSrNumber(), jsonData.getOdscList());
				
			}
		}
		
	}
	
	private void insertMwData(String srNumner, List<MicrowaveRequest> mwList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `MICROWAVE_Make`, `MICROWAVE_Model`, `MICROWAVE_HEIGHT`, `MICROWAVE_DIA`, `MICROWAVE_AZIMUTH`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(MicrowaveRequest r : mwList){
				detValue += "(NULL, '"+srNumner+"', "+r.getId()+", '"+Constant.MICROWAVE+"', '"+r.getMake()+"', '"+r.getModel()+"', "+r.getMicrowaveHeight()+", "+r.getDia()+", '"+r.getMicrowaveAzimuth()+"', CURRENT_TIMESTAMP)";
				if(i != mwList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertMwAddData(String srNumber, List<MicrowaveRequest> mwAddList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `MICROWAVE_Make`, `MICROWAVE_Model`, `MICROWAVE_HEIGHT`, `MICROWAVE_DIA`, `MICROWAVE_AZIMUTH`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(MicrowaveRequest r : mwAddList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.MICROWAVE_Add+"', '"+r.getMake()+"', '"+r.getModel()+"', "+r.getMicrowaveHeight()+", "+r.getDia()+", '"+r.getMicrowaveAzimuth()+"', CURRENT_TIMESTAMP)";
				if(i != mwAddList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertMwDeleteData(String srNumber, List<MicrowaveRequest> mwDeleteList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `MICROWAVE_Make`, `MICROWAVE_Model`, `MICROWAVE_HEIGHT`, `MICROWAVE_DIA`, `MICROWAVE_AZIMUTH`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(MicrowaveRequest r : mwDeleteList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.MICROWAVE_Delete+"', '"+r.getMake()+"', '"+r.getModel()+"', "+r.getMicrowaveHeight()+", "+r.getDia()+", '"+r.getMicrowaveAzimuth()+"', CURRENT_TIMESTAMP)";
				if(i != mwDeleteList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertRruData(String srNumber, List<RRURequest> rruList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `RRU_MAKE`, `RRU_MODEL`, `RRU_Frequency_Band`, `RRU_RATED_POWER_CONSUMPTION`, `RRU_WEIGHT`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(RRURequest r : rruList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.RRU+"', '"+r.getRruMake()+"', '"+r.getRruModel()+"', '"+r.getRruFreqBand()+"', "+r.getRruPowerConsumption()+", "+r.getRruWeight()+", CURRENT_TIMESTAMP)";
				if(i != rruList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertRruDeleteData(String srNumber, List<RRU_SwapRequest> rruDeleteList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `RRU_Delete_Make`, `RRU_Delete_Model`, `RRU_Delete_Frequency_Band`, `RRU_Delete_Power`, `RRU_Delete_Weight`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(RRU_SwapRequest r : rruDeleteList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.RRU_Delete+"', '"+r.getRruDeleteMake()+"', '"+r.getRruDeleteModel()+"', '"+r.getRruDeleteFreqBand()+"', "+r.getRruDeletePower()+", "+r.getRruDeleteWeight()+", CURRENT_TIMESTAMP)";
				if(i != rruDeleteList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertRruAddData(String srNumber, List<RRU_SwapRequest> rruAddList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `RRU_Add_Make`, `RRU_Add_Model`, `RRU_Add_Frequency_Band`, `RRU_Add_Power`, `RRU_Add_Weight`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(RRU_SwapRequest r : rruAddList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.RRU_Add+"', '"+r.getRruAddMake()+"', '"+r.getRruAddModel()+"', '"+r.getRruAddFreqBand()+"', "+r.getRruAddPower()+", "+r.getRruAddWeight()+", CURRENT_TIMESTAMP)";
				if(i != rruAddList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertBtsData(String srNumber, List<BTSRequest> btsList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `BTS_TYPE`, `BTS_MAKE`, `BTS_MODEL`, `BTS_FLOOR_SPACE`, `BTS_POWER`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(BTSRequest r : btsList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.BTS+"', '"+r.getBtsType()+"', '"+r.getBtsMake()+"', '"+r.getBtsModel()+"', "+r.getBtsFloorspace()+", "+r.getBtsPower()+", CURRENT_TIMESTAMP)";
				if(i != btsList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertBbuData(String srNumber, List<BBURequest> bbuList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `BBU_MAKE`, `BBU_MODEL`, `BBU_RATED_POWER_CONSUMPTION`, `BBU_Positioning`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(BBURequest r : bbuList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.BBU+"', '"+r.getBbuMake()+"', '"+r.getBbuModel()+"', "+r.getBbuPowerConsumption()+", '"+r.getBbuPositioning()+"', CURRENT_TIMESTAMP)";
				if(i != bbuList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertBbuDeleteData(String srNumber, List<BBURequest> bbuDeleteList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `BBU_MAKE`, `BBU_MODEL`, `BBU_RATED_POWER_CONSUMPTION`, `BBU_Positioning`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(BBURequest r : bbuDeleteList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.BBU_Delete+"', '"+r.getBbuMake()+"', '"+r.getBbuModel()+"', "+r.getBbuPowerConsumption()+", '"+r.getBbuPositioning()+"', CURRENT_TIMESTAMP)";
				if(i != bbuDeleteList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertBbuAddData(String srNumber, List<BBURequest> bbuAddList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `BBU_MAKE`, `BBU_MODEL`, `BBU_RATED_POWER_CONSUMPTION`, `BBU_Positioning`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(BBURequest r : bbuAddList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.BBU_Add+"', '"+r.getBbuMake()+"', '"+r.getBbuModel()+"', "+r.getBbuPowerConsumption()+", '"+r.getBbuPositioning()+"', CURRENT_TIMESTAMP)";
				if(i != bbuAddList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertRfAntennaData(String srNumber, List<RFAntennaRequest> rfAntennaList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `RF_SIZE`, `RF_WEIGHT`, `RF_AZIMUTH`, `RF_MAKE`, `RF_MODEL`, `RF_RATED_POWER`, `RF_RATED_POWER_CONSUMPTION`, `RF_GAIN`, `RF_BAND`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(RFAntennaRequest r : rfAntennaList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.RF_ANTENNA+"', "+r.getRfSize()+", "+r.getRfWeight()+", '"+r.getRfAzimuth()+"', '"+r.getRfMake()+"', '"+r.getRfModel()+"', "+r.getRfRatedPower()+", "+r.getRfRatedPowerConsumption()+", '"+r.getRfGain()+"', '"+r.getRfBand()+"', CURRENT_TIMESTAMP)";
				if(i != rfAntennaList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertRfAntennaDeleteData(String srNumber, List<RFAntennaRequest> rfAntennaDeleteList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `RF_SIZE`, `RF_WEIGHT`, `RF_AZIMUTH`, `RF_MAKE`, `RF_MODEL`, `RF_RATED_POWER`, `RF_RATED_POWER_CONSUMPTION`, `RF_GAIN`, `RF_BAND`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(RFAntennaRequest r : rfAntennaDeleteList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.RF_ANTENNA_Delete+"', "+r.getRfSize()+", "+r.getRfWeight()+", '"+r.getRfAzimuth()+"', '"+r.getRfMake()+"', '"+r.getRfModel()+"', "+r.getRfRatedPower()+", "+r.getRfRatedPowerConsumption()+", '"+r.getRfGain()+"', '"+r.getRfBand()+"', CURRENT_TIMESTAMP)";
				if(i != rfAntennaDeleteList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertRfAntennaAddData(String srNumber, List<RFAntennaRequest> rfAntennaAddList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `RF_SIZE`, `RF_WEIGHT`, `RF_AZIMUTH`, `RF_MAKE`, `RF_MODEL`, `RF_RATED_POWER`, `RF_RATED_POWER_CONSUMPTION`, `RF_GAIN`, `RF_BAND`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(RFAntennaRequest r : rfAntennaAddList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.RF_ANTENNA_Add+"', "+r.getRfSize()+", "+r.getRfWeight()+", '"+r.getRfAzimuth()+"', '"+r.getRfMake()+"', '"+r.getRfModel()+"', "+r.getRfRatedPower()+", "+r.getRfRatedPowerConsumption()+", '"+r.getRfGain()+"', '"+r.getRfBand()+"', CURRENT_TIMESTAMP)";
				if(i != rfAntennaAddList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertMcbData(String srNumber, List<MCBRequest> mcbList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `MCB_RATING`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(MCBRequest r : mcbList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.MCB+"', '"+r.getMcbRating()+"', CURRENT_TIMESTAMP)";
				if(i != mcbList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertIp55Data(String srNumber, List<IP55Request> ip55List){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `IP55_Power`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(IP55Request r : ip55List){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.IP55+"', "+r.getIp55Power()+", CURRENT_TIMESTAMP)";
				if(i != ip55List.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertOdscData(String srNumber, List<ODSCRequest> odscList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `ODSC_MAKE`, `ODSC_MODEL`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(ODSCRequest r : odscList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.ODSC+"', '"+r.getOdscMake()+"', '"+r.getOdscModel()+"', CURRENT_TIMESTAMP)";
				if(i != odscList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertMassiveMimoAntennaData(String srNumber, List<MassiveMIMORequest> mimoList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `MIMO_Antenna_Weight`, `MIMO_Antenna_Area`, `MIMO_Antenna_Make`, `MIMO_Antenna_Model`, `MIMO_Antenna_Power`, `MIMO_Antenna_Uspace`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(MassiveMIMORequest r : mimoList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.Massive_MIMO_ANTENNA+"', "+r.getMimoAntennaWeight()+", "+r.getMimoAntennaArea()+", '"+r.getMimoAntennaMake()+"', '"+r.getMimoAntennaModel()+"', "+r.getMimoPower()+", "+r.getMimoUspace()+", CURRENT_TIMESTAMP)";
				if(i != mimoList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertPolesData(String srNumber, List<PoleRequest> polesList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `Pole_Height`, `Pole_Weight`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(PoleRequest r : polesList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.POLE+"', "+r.getPoleHeight()+", "+r.getPoleWeight()+", CURRENT_TIMESTAMP)";
				if(i != polesList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertHpscAntennaData(String srNumber, List<HPSCAntennaRequest> hpscAntennaList){
		try {
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`DET_ID`, `SR_NUMBER`, `Type_No`, `TYPE`, `Type_of_HPSC_Antenna`, `CREATE_DATE`) ";
			String detValue = "";
			int i = 0;
			for(HPSCAntennaRequest r : hpscAntennaList){
				detValue += "(NULL, '"+srNumber+"', "+r.getId()+", '"+Constant.HPSC_Antenna+"', '"+r.getTypeOfHpscAntenna()+"', CURRENT_TIMESTAMP)";
				if(i != hpscAntennaList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertHdrAndDetTCU(SaveNBSRequest jsonData) {
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `Operator`, `SR_DATE`, `CIRCLE_NAME`, `TAB_NAME`, `TVI_SITE_ID`, `Site_Name`, `AIRTEL_SITE_ID`, `LATITUDE_1`, `LONGITUDE_1`, `AGL_REQUIRED`, "
				+ "`SITE_ADDRESS`, `DISTRICT`, `STATE`, `CLUTTER`, `SITE_TYPE`, "
				+ "`U_Space`, `Type_Of_Antenna`, `POWER_CONSUMPTION`, `Power_requirement`, `REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		
		String hdrValue = "VALUES ('"+jsonData.getSrNumber()+"', '"+jsonData.getOperator()+"', CURRENT_TIMESTAMP, '"+jsonData.getCircleName()+"', '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', ?, '"+jsonData.getAirtelSiteId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", "+jsonData.getAglRequired()+", "
				+ "?, '"+jsonData.getDistrict()+"', '"+jsonData.getState()+"', '"+jsonData.getClutter()+"', '"+jsonData.getSiteType()+"', "
				+ ""+jsonData.getuSpace()+", '"+jsonData.getTypeOfAntenna()+"', "+jsonData.getPowerConsumption()+", "+jsonData.getPowerRequirement()+", ?, 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		getEm().createNativeQuery(hdrTable + hdrValue)
		.setParameter(1, jsonData.getSiteName())
		.setParameter(2, jsonData.getSiteAddress())
		.setParameter(3, jsonData.getRemark()).
		executeUpdate();
	}
	
	private String getCircleCode(String circleName){
		String circleCode = "";
		String sql = "SELECT `CIRCLE_NAME`, `CIRCLE_CODE` FROM `CIRCLE_MASTER` where `CIRCLE_NAME` = '"+circleName+"' "
				+ "and IS_ACTIVE = 'Y'";
		List<Object[]> dataList = getAllTableData(sql);
		if(dataList.size() > 0){
			Object[] obj = dataList.get(0);
			circleCode = obj[1] == null ? "" : String.valueOf(obj[1]);
		}
		return circleCode;
	}
	
	@Override
	public void raiseSr(String srNumber, String tabName, SrRequestJsonDto srRequestJson) {
		Double totalRatedPowerInKW = 0.0,totalRatedPowerInWatt;
		SiteDetailDto siteDetail = srRequestJson.getSite_Detail();
		ProductDetailDto productDetail = srRequestJson.getProduct_Detail();
		TmaTmbDto tmatmb = srRequestJson.getTMA_TMB();
//		OtherEquipmentDto otherEquipment = srRequestJson.getOther_Equipment();
		FiberDto fiber = srRequestJson.getFiber();
		FiberNodeProvisioningDto fbp = srRequestJson.getFiber_Node_Provisioning();
		SacfaDto sacfa = srRequestJson.getSACFA();
		DgDto dg = srRequestJson.getDG();
		String circleName = siteDetail.getCircle();
		String circleCode = getCircleCode(circleName);
		siteDetail.setCircleCode(circleCode);
		String sql = "INSERT INTO `Airtel_SR`(`SR_Number`, `Ref_Number_TVIPL`, `SiteId`, `Operator`, `UniqueRequestId`, "
				+ "`Remarks`, "
				+ "`Customer`, `Customer_Site_Id`, `Customer_Site_Name`, "
				+ "`Circle`, `CircleCode`, `State`, `Cell_Type`, `Site_Type`, "
				+ "`City`, `Search_Ring_Radious_Mtrs`, `Infill_NewTown`, `ShowCase_Non_Showcase`, "
				+ "`_3_11_Towns`, `Town`, `Request_for_Network_Type`, "
				+ "`Project_Name`, `Authority_Name`, `Preferred_Product_Type`, "
				+ "`Customer_Product_Type`, `No_of_TMA_TMB`, `Weight_of_each_TMA_TMB`, "
				+ "`Combined_wt_of_TMA_TMB_Kgs`, `Height_at_which_needs_to_be_mounted_Mtrs`, "
				+ "`Fiber_Required`, `No_of_Fiber_Pairs`, "
				+ "`Is_Fiber_Node_Provisioning_Required`, `No_of_Pairs`, `Distance_Length_of_Fiber_in_Meter`, "
				+ "`SACFA_Number`, `Is_Diesel_Generator_DG_required`, `STATUS`, `SR_DATE`, `TAB_NAME`)";
		sql += " VALUES ";
		sql += " ('"+srNumber+"', '"+srRequestJson.getRef_Number_TVIPL()+"', '"+srRequestJson.getSiteId()+"', '"+srRequestJson.getOperator()+"', '"+srRequestJson.getUniqueRequestId()+"', '"+srRequestJson.getGlobal().getRemarks()+"', "
				+ "'"+siteDetail.getCustomer()+"', '"+siteDetail.getCustomer_Site_Id()+"', '"+siteDetail.getCustomer_Site_Name()+"', "
				+ "'"+circleName+"', '"+circleCode+"', '"+siteDetail.getState()+"', '"+siteDetail.getCell_Type()+"', '"+siteDetail.getSite_Type()+"', "
				+ "'"+siteDetail.getCity()+"', "+siteDetail.getSearch_Ring_Radious_Mtrs()+", '"+siteDetail.getInfill_NewTown()+"', '"+siteDetail.getShowCase_Non_Showcase()+"', "
				+ "'"+siteDetail.get_3_11_Towns()+"', '"+siteDetail.getTown()+"', '"+siteDetail.getRequest_for_Network_Type()+"', "
				+ "'"+siteDetail.getProject_Name()+"', '"+siteDetail.getAuthority_Name()+"', '"+productDetail.getPreferred_Product_Type()+"', "
				+ "'"+productDetail.getCustomer_Product_Type()+"', "+tmatmb.getNo_of_TMA_TMB()+", "+tmatmb.getWeight_of_each_TMA_TMB()+", "
				+ ""+tmatmb.getCombined_wt_of_TMA_TMB_Kgs()+", "+tmatmb.getHeight_at_which_needs_to_be_mounted_Mtrs()+", "
				+ "'"+fiber.getFiber_Required()+"', '"+fiber.getNo_of_Fiber_Pairs()+"', "
				+ "'"+fbp.getIs_Fiber_Node_Provisioning_Required()+"', "+fbp.getNo_of_Pairs()+", "+fbp.getDistance_Length_of_Fiber_in_Meter()+", "
				+ "'"+sacfa.getSACFA_Number()+"', '"+dg.getIs_Diesel_Generator_DG_required()+"', 'NB01', curdate(), '"+tabName+"') ";
		int i = getEm().createNativeQuery(sql).executeUpdate();
		if(i != 0){
			// Priority
			PriorityDto p1 = srRequestJson.getPriority_Details().getP1();
			PriorityDto p2 = srRequestJson.getPriority_Details().getP2();
			PriorityDto p3 = srRequestJson.getPriority_Details().getP3();
			sql = "INSERT INTO `Airtel_Priority_Details`(`SR_Number`, "
			+ "`P1_Site_Address`, `P1_Tower_Type`, `P1_Latitude`, `P1_Longitude`, `P1_LandLord_Contact_Detail`, "
			+ "`P2_Site_Address`, `P2_Tower_Type`, `P2_Latitude`, `P2_Longitude`, `P2_LandLord_Contact_Detail`, "
			+ "`P3_Site_Address`, `P3_Tower_Type`, `P3_Latitude`, `P3_Longitude`, `P3_LandLord_Contact_Detail`)";
			sql += " VALUES ";
			sql += " ('"+srNumber+"', "
					+ "'"+p1.getSite_Address()+"', '"+p1.getTower_Type()+"', '"+p1.getLatitude()+"', '"+p1.getLongitude()+"', '"+p1.getLandLord_Contact_Detail()+"', "
					+ "'"+p2.getSite_Address()+"', '"+p2.getTower_Type()+"', '"+p2.getLatitude()+"', '"+p2.getLongitude()+"', '"+p2.getLandLord_Contact_Detail()+"', "
					+ "'"+p3.getSite_Address()+"', '"+p3.getTower_Type()+"', '"+p3.getLatitude()+"', '"+p3.getLongitude()+"', '"+p3.getLandLord_Contact_Detail()+"')";
			i = getEm().createNativeQuery(sql).executeUpdate();
			
			// BTS
			BtsDto bts = srRequestJson.getBTS();
			sql = "INSERT INTO `Airtel_BTS`(`SR_Number`, `Type_No`, "
					+ "`Config_type_1`, `Config_type_2`, `Config_type_3`, `Config_Carriers`, "
					+ "`NetWork_Type`, `BTS_Type`, `Band`, `Manufacturer`, "
					+ "`Make_of_BTS`, `Length_Mtrs`, `Width_Mtrs`, `Height_Mtrs`, "
					+ "`BTS_Power_Rating_KW`, `BTS_Location`, `BTS_Voltage`, "
					+ "`Main_Unit_incase_of_TT_Split_Version`, `Space_Occupied_in_Us_incase_of_TT_Split_Version`, "
					+ "`RRU_Unit`, `No_of_RRU_Units_incase_of_TT_Split_Version`, "
					+ "`Combined_wt_of_RRU_Unit_incase_of_TT_Split_Version`, `AGL_of_RRU_unit_in_M`, "
					+ "`Weight_of_BTS_including_TMA_TMB_Kg`, `Billable_Weigtht`)";
			sql += " VALUES ";
			int typeNo = 1;
			for(BtsCabinetDto btsCabi : bts.getBTS_Cabinet()){
				totalRatedPowerInKW += btsCabi.getBTS_Power_Rating_KW();
				String dataSql = " ('"+srNumber+"', "+typeNo+", "
						+ ""+bts.getConfig_type_1()+", "+bts.getConfig_type_2()+", "+bts.getConfig_type_3()+", "+bts.getConfig_Carriers()+", "
						+ "'"+btsCabi.getNetWork_Type()+"', '"+btsCabi.getBTS_Type()+"', '"+btsCabi.getBand()+"', '"+btsCabi.getManufacturer()+"', "
						+ "'"+btsCabi.getMake_of_BTS()+"', "+btsCabi.getLength_Mtrs()+", "+btsCabi.getWidth_Mtrs()+", "+btsCabi.getHeight_Mtrs()+", "
						+ ""+btsCabi.getBTS_Power_Rating_KW()+", '"+btsCabi.getBTS_Location()+"', '"+btsCabi.getBTS_Voltage()+"', "
						+ "'"+btsCabi.getMain_Unit_incase_of_TT_Split_Version()+"', "+btsCabi.getSpace_Occupied_in_Us_incase_of_TT_Split_Version()+", "
						+ "'"+btsCabi.getRRU_Unit()+"', "+btsCabi.getNo_of_RRU_Units_incase_of_TT_Split_Version()+", "
						+ ""+btsCabi.getCombined_wt_of_RRU_Unit_incase_of_TT_Split_Version()+", "+btsCabi.getAGL_of_RRU_unit_in_M()+", "
						+ ""+btsCabi.getWeight_of_BTS_including_TMA_TMB_Kg()+", "+btsCabi.getBillable_Weigtht()+")";
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// Radio Antenna
			sql = "INSERT INTO `Airtel_Radio_Antenna`(`SR_Number`, `Type_No`, "
					+ "`RadioAntenna_i_WAN`, `Height_AGL_m`, `Azimuth_Degree`, "
					+ "`Length_m`, `Width_m`, `Depth_m`, `No_of_Ports`, `BandFrequencyMHz_FrequencyCombination`, "
					+ "`RadioAntenna_Type`, `Total_Ports`, `Max_Ports`, `Bands_List`)";
			sql += " VALUES ";
			typeNo = 1;
			for(RadioAntennaDto radioAntenna : srRequestJson.getRadio_Antenna()){
				BandFrequencyMHzDto bandFreq = radioAntenna.getBandFrequencyMHz_FrequencyCombination();
				String bandFreqM = bandFreq.getFrequency_Number_1()+","+bandFreq.getFrequency_Number_2()+","+bandFreq.getFrequency_Number_3()+","+bandFreq.getFrequency_Number_4()+","+
				bandFreq.getFrequency_Number_5()+","+bandFreq.getFrequency_Number_6()+","+bandFreq.getFrequency_Number_7()+","+bandFreq.getFrequency_Number_8()+",";
				
				String mp = "";
				String bl = "";
				String dataSql = "('"+srNumber+"', "+typeNo+", "
						+ "'"+radioAntenna.getRadioAntenna_i_WAN()+"', "+radioAntenna.getHeight_AGL_m()+", "+radioAntenna.getAzimuth_Degree()+", "
						+ ""+radioAntenna.getLength_m()+", "+radioAntenna.getWidth_m()+", "+radioAntenna.getDepth_m()+", '"+radioAntenna.getNo_of_Ports()+"', '"+bandFreqM+"', "
						+ "'"+radioAntenna.getRadioAntenna_Type()+"', 0, '"+mp+"', '"+bl+"')";
				
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// MW
			sql = "INSERT INTO `Airtel_MW`(`SR_Number`, `Type_No`, `Additional_Transmission_Rack_Space_Power_Upgrade_Requirement`, "
					+ "`TotalIDUs_Magazines_tobe_Installed`, `Transmission_Rack_Space_Required_in_Us`, "
					+ "`Power_Rating_in_Kw`, `Power_Plant_Voltage`, `MWAntenna_i_WAN`, "
					+ "`Size_of_MW`, `Height_in_Mtrs`, `Azimuth_Degree`)";
			sql += " VALUES ";
			MwDto mw = srRequestJson.getMW();
			totalRatedPowerInKW += mw.getPower_Rating_in_Kw();
			typeNo = 1;
			for(MwAntennaDto mwAnt : mw.getMW_Antenna()){
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+mw.getAdditional_Transmission_Rack_Space_Power_Upgrade_Requirement()+"', "
						+ "'"+mw.getTotalIDUs_Magazines_tobe_Installed()+"', "+mw.getTransmission_Rack_Space_Required_in_Us()+", "
						+ ""+mw.getPower_Rating_in_Kw()+", '"+mw.getPower_Plant_Voltage()+"', '"+mwAnt.getMWAntenna_i_WAN()+"', "
						+ ""+mwAnt.getSize_of_MW()+", "+mwAnt.getHeight_in_Mtrs()+", "+mwAnt.getAzimuth_Degree()+")";
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// BSC RNC
			sql = "INSERT INTO `Airtel_BSC_RNC_Cabinets`(`SR_Number`, `Type_No`, `NetWork_Type`, `BSC_RNC_Type`, `BSC_RNC_Manufacturer`, "
					+ "`BSC_RNC_Make`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_AGL`, `BSC_RNC_Power_Rating`)";
			sql += " VALUES ";
			typeNo = 1;
			for(BscRncCabinetsDto bsc : srRequestJson.getBSC_RNC_Cabinets()){
				totalRatedPowerInKW += bsc.getBSC_RNC_Power_Rating();
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+bsc.getNetWork_Type()+"', '"+bsc.getBSC_RNC_Type()+"', '"+bsc.getBSC_RNC_Manufacturer()+"', "
						+ "'"+bsc.getBSC_RNC_Make()+"', "+bsc.getLength_Mtrs()+", "+bsc.getBreadth_Mtrs()+", "+bsc.getHeight_AGL()+", "+bsc.getBSC_RNC_Power_Rating()+")";
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// Other Node
			sql = "INSERT INTO `Airtel_Other_Node`(`SR_Number`, `Type_No`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, "
					+ "`Node_Model`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, "
					+ "`Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, `FullRack`, "
					+ "`Tx_Rack_Space_Required_In_Us`, `Remarks`)";
			sql += " VALUES ";
			typeNo = 1;
			for(OtherNodeDto other : srRequestJson.getOther_Node()){
				totalRatedPowerInKW += other.getPower_Rating_in_Kw();
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+other.getNode_Type()+"', '"+other.getNode_Location()+"', '"+other.getNode_Manufacturer()+"', "
						+ "'"+other.getNode_Model()+"', "+other.getLength_Mtrs()+", "+other.getBreadth_Mtrs()+", "+other.getHeight_Mtrs()+", "
						+ ""+other.getWeight_Kg()+", '"+other.getNode_Voltage()+"', "+other.getPower_Rating_in_Kw()+", '"+other.getFullRack()+"', "
						+ ""+other.getTx_Rack_Space_Required_In_Us()+", '"+other.getRemarks()+"')";
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// AdditionalInfo
			AdditionalInformationDto addInfo = srRequestJson.getAdditional_Information();
			StrageticDto stra = addInfo.getStragetic();
		 	RelocationDto relo = addInfo.getRelocation();
		 	String anyOtherSpecReq = stra.getAnyother_Specific_Requirements();
		 	anyOtherSpecReq = anyOtherSpecReq == null ? "" :anyOtherSpecReq;
			sql = "INSERT INTO `Airtel_Additional_Information`(`SR_Number`, `Is_it_Strategic`, `Shelter_Size`, `Length_Mtrs`, "
					+ "`Breadth_Mtrs`, `Height_AGL_Mtrs`, `DG_Redundancy`, "
					+ "`Extra_Battery_Bank_Requirement`, `Extra_Battery_BackUp`, "
					+ "`Anyother_Specific_Requirements`, `Additional_Info_If_any`, "
					+ "`Other_Additional_Info1`, `Other_Additional_Info2`, `TargetDate_DD_MM_YYYY`, "
					+ "`Is_it_Relocaiton_SR`, `Source_Req_Ref_No`, `Source_Indus_SiteId`, "
					+ "`Relocaiton_Reason`)";
			sql += " VALUES ";
			sql += "('"+srNumber+"', '"+stra.getIs_it_Strategic()+"', '"+stra.getShelter_Size()+"', "+stra.getLength_Mtrs()+", "
					+ ""+stra.getBreadth_Mtrs()+", "+stra.getHeight_AGL_Mtrs()+", '"+stra.getDG_Redundancy()+"', "
					+ "'"+stra.getExtra_Battery_Bank_Requirement()+"', '"+stra.getExtra_Battery_BackUp()+"', "
					+ "'"+anyOtherSpecReq+"', '"+addInfo.getAdditional_Info_If_any()+"', "
					+ "'"+addInfo.getOther_Additional_Info1()+"', '"+addInfo.getOther_Additional_Info2()+"', '"+addInfo.getTargetDate_DD_MM_YYYY()+"', "
					+ "'"+relo.getIs_it_Relocaiton_SR()+"', '"+relo.getSource_Req_Ref_No()+"', '"+relo.getSource_TOCO_SiteId()+"', "
					+ "'"+relo.getRelocaiton_Reason()+"')";
			
			getEm().createNativeQuery(sql).executeUpdate();
			
			// MCB
			McbDto mcb = srRequestJson.getMCB();
			sql = "INSERT INTO `Airtel_MCB`(`SR_Number`, `Total_No_of_MCB_Required`, "
					+ "`_06A`, `_10A`, `_16A`, `_24A`, "
					+ "`_32A`, `_40A`, `_63A`, `_80A`)";
			sql += " VALUES ";
			sql += "('"+srNumber+"', "+mcb.getTotal_No_of_MCB_Required()+", "
					+ ""+mcb.get_06A()+", "+mcb.get_10A()+", "+mcb.get_16A()+", "+mcb.get_24A()+", "
					+ ""+mcb.get_32A()+", "+mcb.get_40A()+", "+mcb.get_63A()+", "+mcb.get_80A()+")";
			
			getEm().createNativeQuery(sql).executeUpdate();
			
			// Fibre Node
			sql = "INSERT INTO `Airtel_Fibre_Node`(`SR_Number`, `Type_No`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, "
					+ "`Node_Model`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, "
					+ "`Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, `FullRack`, "
					+ "`Tx_Rack_Space_required_in_Us`, `Is_Right_Of_Way_ROW_Required_Inside_The_Indus_Premises`, "
					+ "`Type_Of_Fiber_Laying`, `Type_Of_FMS`, `Remarks`, `Full_Rack`)";
			sql += " VALUES ";
			typeNo = 1;
			for(FibreNodeDto fiNo : srRequestJson.getFibre_Node()){
				totalRatedPowerInKW += fiNo.getPower_Rating_in_Kw();
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+fiNo.getNode_Type()+"', '"+fiNo.getNode_Location()+"', '"+fiNo.getNode_Manufacturer()+"', "
						+ "'"+fiNo.getNode_Model()+"', "+fiNo.getLength_Mtrs()+", "+fiNo.getBreadth_Mtrs()+", "+fiNo.getHeight_Mtrs()+", "
						+ ""+fiNo.getWeight_Kg()+", '"+fiNo.getNode_Voltage()+"', "+fiNo.getPower_Rating_in_Kw()+", '"+fiNo.getFullRack()+"', "
						+ ""+fiNo.getTx_Rack_Space_required_in_Us()+", '"+fiNo.getIs_Right_Of_Way_ROW_Required_Inside_The_TOCO_Premises()+"', "
						+ "'"+fiNo.getType_Of_Fiber_Laying()+"', '"+fiNo.getType_Of_FMS()+"', '"+fiNo.getRemarks()+"', '"+fiNo.getFull_Rack()+"')";
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// Other Equipment
			List<OtherEquipmentDto> otherEquipment = srRequestJson.getOther_Equipment();
			sql = "INSERT INTO `Airtel_Other_Equipment`(`SR_Number`, `Type_No`, `Source_Request_RefNo`, "
					+ "`Other_Equipment_Category`, `Other_Equipment_Type`, `Equipment_to_be_relocated`, "
					+ "`Target_Indus_Site_Id`, `Target_Request_RefNo`)";
			sql += " VALUES ";
			typeNo = 1;
			for(OtherEquipmentDto otEq : otherEquipment){
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+otEq.getSource_Request_RefNo()+"', '"+otEq.getOther_Equipment_Category()+"', '"+otEq.getOther_Equipment_Type()+"', "
						+ "'"+otEq.getEquipment_to_be_relocated()+"', '"+otEq.getTarget_Indus_Site_Id()+"', '"+otEq.getTarget_Request_RefNo()+"')";
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
						
			totalRatedPowerInWatt = totalRatedPowerInKW * 1000;
			String updatePowerRating = "UPDATE `Airtel_SR` set `Total_Rated_Power_In_KW` = "+totalRatedPowerInKW+", "
			+ "`Total_Rated_Power_In_Watt` = "+totalRatedPowerInWatt+" where `SR_Number` = '"+srNumber+"' ";
			int updateExe = getEm().createNativeQuery(updatePowerRating).executeUpdate();
			if(updateExe != 0){
				//System.out.println("Success");
			}
		}
		
	}
	
	@Override
	public void raiseSharingSr(String srNumber, SiteInfoDto siteObj, SharingSrRequestJsonDto srRequestJson) {
		Double totalRatedPowerInKW = 0.0,totalRatedPowerInWatt;
		com.tvi.sharing.dto.SiteDetailDto siteDetail = srRequestJson.getSite_Detail();
		com.tvi.sharing.dto.ProductDetailDto proDetails = srRequestJson.getProduct_Detail();
		com.tvi.sharing.dto.TmaTmbDto tmatmb = srRequestJson.getTMA_TMB();
//		com.tvi.sharing.dto.OtherEquipmentDto otherEquipment = srRequestJson.getOther_Equipment();
		com.tvi.sharing.dto.FiberDto fiber = srRequestJson.getFiber();
		com.tvi.sharing.dto.FiberNodeProvisioningDto fbp = srRequestJson.getFiber_Node_Provisioning();
		com.tvi.sharing.dto.SacfaDto sacfa = srRequestJson.getSACFA();
		com.tvi.sharing.dto.DgDto dg = srRequestJson.getDG();
		CircleInformationDto circleInfo = srRequestJson.getCircle_Information();
		
		String circleName = siteDetail.getCircle();
		String circleCode = getCircleCode(circleName);
		String sql = "INSERT INTO `Airtel_SR`(`SR_Number`, `Ref_Number_TVIPL`, `SiteId`, `Operator`, `UniqueRequestId`, `Remarks`, "
				+ "`TOCO_Site_Id`, `Customer`, `Customer_Site_Id`, `Customer_Site_Name`, "
				+ "`Circle`, `CircleCode`, `State`, `Cell_Type`, `Site_Type`, "
				+ "`City`, `Infill_NewTown`, `ShowCase_Non_Showcase`, "
				+ "`_3_11_Towns`, `Town`, `TargetDate_DD_MM_YYYY`, `Request_for_Network_Type`, "
				+ "`Project_Name`, `Authority_Name`, "
				+ "`Customer_Product_Type`, `No_of_TMA_TMB`, `Weight_of_each_TMA_TMB`, "
				+ "`Combined_wt_of_TMA_TMB_Kgs`, `Height_at_which_needs_to_be_mounted_Mtrs`, "
				+ "`Fiber_Required`, `No_of_Fiber_Pairs`, "
				+ "`Is_Fiber_Node_Provisioning_Required`, `No_of_Pairs`, `Distance_Length_of_Fiber_in_Meter`, "
				+ "`SACFA_Number`, `Is_Diesel_Generator_DG_required`, `STATUS`, `SR_DATE`, `TAB_NAME`, "
				+ "`Cluster`, `MSA_Type`, `Name_of_District_SSA`, `Product_Name`)";
		sql += " VALUES ";
		sql += " ('"+srNumber+"', '"+srRequestJson.getRef_Number_TVIPL()+"', '"+srRequestJson.getSiteId()+"', '"+srRequestJson.getOperator()+"', '"+srRequestJson.getUniqueRequestId()+"', '"+srRequestJson.getGlobal().getRemarks()+"', "
				+ "'"+siteDetail.getTOCO_Site_Id()+"', '"+siteDetail.getCustomer()+"', '"+siteDetail.getCustomer_Site_Id()+"', '"+siteDetail.getCustomer_Site_Name()+"', "
				+ "'"+circleName+"', '"+circleCode+"', '', '', '"+siteDetail.getShare_Type()+"', "
				+ "'"+siteDetail.getCity()+"', '"+siteDetail.getInfill_NewTown()+"', '"+siteDetail.getShowCase_Non_Showcase()+"', "
				+ "'"+siteDetail.get_3_11_Towns()+"', '"+siteDetail.getTown()+"', '"+siteDetail.getTargetDate_DD_MM_YYYY()+"', '"+siteDetail.getRequest_for_Network_Type()+"', "
				+ "'"+siteDetail.getProject_Name()+"', '"+siteDetail.getAuthority_Name()+"', "
				+ "'', "+tmatmb.getNo_of_TMA_TMB()+", "+tmatmb.getWeight_of_each_TMA_TMB()+", "
				+ ""+tmatmb.getCombined_wt_of_TMA_TMB_Kgs()+", "+tmatmb.getHeight_at_which_needs_to_be_mounted_Mtrs()+", "
				+ "'"+fiber.getFiber_Required()+"', '"+fiber.getNo_of_Fiber_Pairs()+"', "
				+ "'"+fbp.getIs_Fiber_Node_Provisioning_Required()+"', "+fbp.getNo_of_Pairs()+", "+fbp.getDistance_Length_of_Fiber_in_Meter()+", "
				+ "'"+sacfa.getSACFA_Number()+"', '"+dg.getIs_Diesel_Generator_DG_required()+"', 'NB01', curdate(), '"+Constant.New_Tenency+"', "
				+ "'"+circleInfo.getCluster()+"', '"+circleInfo.getMSA_Type()+"', '"+circleInfo.getName_of_District_SSA()+"', '"+proDetails.getProduct_Name()+"') ";
		int i = getEm().createNativeQuery(sql).executeUpdate();
		if(i != 0){
			// Priority
			sql = "INSERT INTO `Airtel_Priority_Details`(`SR_Number`, "
			+ "`P1_Site_Address`, `P1_Tower_Type`, `P1_Latitude`, `P1_Longitude`)";
			sql += " VALUES ";
			sql += " ('"+srNumber+"', "
					+ "?, ?, ?, ?)";
			i = getEm().createNativeQuery(sql)
					.setParameter(1, siteObj.getSiteAddess())
					.setParameter(2, siteObj.getTowerType())
					.setParameter(3, siteObj.getLatitude())
					.setParameter(4, siteObj.getLongitude()).executeUpdate();
			
			// BTS
			com.tvi.sharing.dto.BtsDto bts = srRequestJson.getBTS();
			sql = "INSERT INTO `Airtel_BTS`(`SR_Number`, `Type_No`, "
					+ "`Config_type_1`, `Config_type_2`, `Config_type_3`, `Config_Carriers`, "
					+ "`NetWork_Type`, `BTS_Type`, `Band`, `Manufacturer`, "
					+ "`Make_of_BTS`, `Length_Mtrs`, `Width_Mtrs`, `Height_Mtrs`, "
					+ "`BTS_Power_Rating_KW`, `BTS_Location`, `BTS_Voltage`, "
					+ "`Main_Unit_incase_of_TT_Split_Version`, `Space_Occupied_in_Us_incase_of_TT_Split_Version`, "
					+ "`RRU_Unit`, `No_of_RRU_Units_incase_of_TT_Split_Version`, "
					+ "`Combined_wt_of_RRU_Unit_incase_of_TT_Split_Version`, `AGL_of_RRU_unit_in_M`, "
					+ "`Weight_of_BTS_including_TMA_TMB_Kg`, `Billable_Weigtht`)";
			sql += " VALUES ";
			int typeNo = 1;
			for(com.tvi.sharing.dto.BtsCabinetDto btsCabi : bts.getBTS_Cabinet()){
				totalRatedPowerInKW += btsCabi.getBTS_Power_Rating_KW();
				String dataSql = " ('"+srNumber+"', "+typeNo+", "
						+ ""+bts.getConfig_type_1()+", "+bts.getConfig_type_2()+", "+bts.getConfig_type_3()+", "+bts.getConfig_Carriers()+", "
						+ "'"+btsCabi.getNetWork_Type()+"', '"+btsCabi.getBTS_Type()+"', '"+btsCabi.getBand()+"', '"+btsCabi.getManufacturer()+"', "
						+ "'"+btsCabi.getMake_of_BTS()+"', "+btsCabi.getLength_Mtrs()+", "+btsCabi.getWidth_Mtrs()+", "+btsCabi.getHeight_Mtrs()+", "
						+ ""+btsCabi.getBTS_Power_Rating_KW()+", '"+btsCabi.getBTS_Location()+"', '"+btsCabi.getBTS_Voltage()+"', "
						+ "'"+btsCabi.getMain_Unit_incase_of_TT_Split_Version()+"', "+btsCabi.getSpace_Occupied_in_Us_incase_of_TT_Split_Version()+", "
						+ "'"+btsCabi.getRRU_Unit()+"', "+btsCabi.getNo_of_RRU_Units_incase_of_TT_Split_Version()+", "
						+ ""+btsCabi.getCombined_wt_of_RRU_Unit_incase_of_TT_Split_Version()+", "+btsCabi.getAGL_of_RRU_unit_in_M()+", "
						+ ""+btsCabi.getWeight_of_BTS_including_TMA_TMB_Kg()+", "+btsCabi.getBillable_Weigtht()+")";
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// Radio Antenna
			sql = "INSERT INTO `Airtel_Radio_Antenna`(`SR_Number`, `Type_No`, "
					+ "`RadioAntenna_i_WAN`, `Height_AGL_m`, `Azimuth_Degree`, "
					+ "`Length_m`, `Width_m`, `Depth_m`, `No_of_Ports`, `BandFrequencyMHz_FrequencyCombination`, "
					+ "`RadioAntenna_Type`)";
			sql += " VALUES ";
			typeNo = 1;
			for(com.tvi.sharing.dto.RadioAntennaDto radioAntenna : srRequestJson.getRadio_Antenna()){
				com.tvi.sharing.dto.BandFrequencyMHzDto bandFreq = radioAntenna.getBandFrequencyMHz_FrequencyCombination();
				String bandFreqM = bandFreq.getFrequency_Number_1()+","+bandFreq.getFrequency_Number_2()+","+bandFreq.getFrequency_Number_3()+","+bandFreq.getFrequency_Number_4()+","+
				bandFreq.getFrequency_Number_5()+","+bandFreq.getFrequency_Number_6()+","+bandFreq.getFrequency_Number_7()+","+bandFreq.getFrequency_Number_8()+",";
				String dataSql = "('"+srNumber+"', "+typeNo+", "
						+ "'"+radioAntenna.getRadioAntenna_i_WAN()+"', "+radioAntenna.getHeight_AGL_m()+", "+radioAntenna.getAzimuth_Degree()+", "
						+ ""+radioAntenna.getLength_m()+", "+radioAntenna.getWidth_m()+", "+radioAntenna.getDepth_m()+", '"+radioAntenna.getNo_of_Ports()+"', '"+bandFreqM+"', "
						+ "'"+radioAntenna.getRadioAntenna_Type()+"')";
				
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// MW
			sql = "INSERT INTO `Airtel_MW`(`SR_Number`, `Type_No`, `Additional_Transmission_Rack_Space_Power_Upgrade_Requirement`, "
					+ "`TotalIDUs_Magazines_tobe_Installed`, `Transmission_Rack_Space_Required_in_Us`, "
					+ "`Power_Rating_in_Kw`, `Power_Plant_Voltage`, `MWAntenna_i_WAN`, "
					+ "`Size_of_MW`, `Height_in_Mtrs`, `Azimuth_Degree`)";
			sql += " VALUES ";
			com.tvi.sharing.dto.MwDto mw = srRequestJson.getMW();
			totalRatedPowerInKW += mw.getPower_Rating_in_Kw();
			typeNo = 1;
			for(com.tvi.sharing.dto.MwAntennaDto mwAnt : mw.getMW_Antenna()){
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+mw.getAdditional_Transmission_Rack_Space_Power_Upgrade_Requirement()+"', "
						+ "'"+mw.getTotalIDUs_Magazines_tobe_Installed()+"', "+mw.getTransmission_Rack_Space_Required_in_Us()+", "
						+ ""+mw.getPower_Rating_in_Kw()+", '"+mw.getPower_Plant_Voltage()+"', '"+mwAnt.getMWAntenna_i_WAN()+"', "
						+ ""+mwAnt.getSize_of_MW()+", "+mwAnt.getHeight_in_Mtrs()+", "+mwAnt.getAzimuth_Degree()+")";
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// BSC RNC
			sql = "INSERT INTO `Airtel_BSC_RNC_Cabinets`(`SR_Number`, `Type_No`, `NetWork_Type`, `BSC_RNC_Type`, `BSC_RNC_Manufacturer`, "
					+ "`BSC_RNC_Make`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_AGL`, `BSC_RNC_Power_Rating`)";
			sql += " VALUES ";
			typeNo = 1;
			for(com.tvi.sharing.dto.BscRncCabinetsDto bsc : srRequestJson.getBSC_RNC_Cabinets()){
				totalRatedPowerInKW += mw.getPower_Rating_in_Kw();
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+bsc.getNetWork_Type()+"', '"+bsc.getBSC_RNC_Type()+"', '"+bsc.getBSC_RNC_Manufacturer()+"', "
						+ "'"+bsc.getBSC_RNC_Make()+"', "+bsc.getLength_Mtrs()+", "+bsc.getBreadth_Mtrs()+", "+bsc.getHeight_AGL()+", "+bsc.getBSC_RNC_Power_Rating()+")";
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// Other Node
			sql = "INSERT INTO `Airtel_Other_Node`(`SR_Number`, `Type_No`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, "
					+ "`Node_Model`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, "
					+ "`Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, `FullRack`, "
					+ "`Tx_Rack_Space_Required_In_Us`, `Remarks`)";
			sql += " VALUES ";
			typeNo = 1;
			for(com.tvi.sharing.dto.OtherNodeDto other : srRequestJson.getOther_Node()){
				totalRatedPowerInKW += other.getPower_Rating_in_Kw();
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+other.getNode_Type()+"', '"+other.getNode_Location()+"', '"+other.getNode_Manufacturer()+"', "
						+ "'"+other.getNode_Model()+"', "+other.getLength_Mtrs()+", "+other.getBreadth_Mtrs()+", "+other.getHeight_Mtrs()+", "
						+ ""+other.getWeight_Kg()+", '"+other.getNode_Voltage()+"', "+other.getPower_Rating_in_Kw()+", '"+other.getFullRack()+"', "
						+ ""+other.getTx_Rack_Space_Required_In_Us()+", '"+other.getRemarks()+"')";
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// AdditionalInfo
			com.tvi.sharing.dto.AdditionalInformationDto addInfo = srRequestJson.getAdditional_Information();
			com.tvi.sharing.dto.StrageticDto stra = addInfo.getStragetic();
			String anyOtherSpecReq = stra.getAnyother_Specific_Requirements();
			anyOtherSpecReq = anyOtherSpecReq == null ? "" : anyOtherSpecReq;
			sql = "INSERT INTO `Airtel_Additional_Information`(`SR_Number`, `Is_it_Strategic`, `Shelter_Size`, `Length_Mtrs`, "
					+ "`Breadth_Mtrs`, `Height_AGL_Mtrs`, `DG_Redundancy`, "
					+ "`Extra_Battery_Bank_Requirement`, `Extra_Battery_BackUp`, "
					+ "`Anyother_Specific_Requirements`, "
					+ "`Other_Additional_Info1`, `Other_Additional_Info2`)";
			sql += " VALUES ";
			sql += "('"+srNumber+"', '"+stra.getIs_it_Strategic()+"', '"+stra.getShelter_Size()+"', "+stra.getLength_Mtrs()+", "
					+ ""+stra.getBreadth_Mtrs()+", "+stra.getHeight_AGL_Mtrs()+", '"+stra.getDG_Redundancy()+"', "
					+ "'"+stra.getExtra_Battery_Bank_Requirement()+"', '"+stra.getExtra_Battery_BackUp()+"', "
					+ "'"+anyOtherSpecReq+"', "
					+ "'"+addInfo.getOther_Additional_Info1()+"', '"+addInfo.getOther_Additional_Info2()+"')";
			
			getEm().createNativeQuery(sql).executeUpdate();
			
			// MCB
			com.tvi.sharing.dto.McbDto mcb = srRequestJson.getMCB();
			sql = "INSERT INTO `Airtel_MCB`(`SR_Number`, `Total_No_of_MCB_Required`, "
					+ "`_06A`, `_10A`, `_16A`, `_24A`, "
					+ "`_32A`, `_40A`, `_63A`, `_80A`)";
			sql += " VALUES ";
			sql += "('"+srNumber+"', "+mcb.getTotal_No_of_MCB_Required()+", "
					+ ""+mcb.get_06A()+", "+mcb.get_10A()+", "+mcb.get_16A()+", "+mcb.get_24A()+", "
					+ ""+mcb.get_32A()+", "+mcb.get_40A()+", "+mcb.get_63A()+", "+mcb.get_80A()+")";
			
			getEm().createNativeQuery(sql).executeUpdate();
			
			// Fibre Node
			sql = "INSERT INTO `Airtel_Fibre_Node`(`SR_Number`, `Type_No`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, "
					+ "`Node_Model`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, "
					+ "`Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, `FullRack`, "
					+ "`Tx_Rack_Space_required_in_Us`, `Is_Right_Of_Way_ROW_Required_Inside_The_Indus_Premises`, "
					+ "`Type_Of_Fiber_Laying`, `Type_Of_FMS`, `Remarks`, `Full_Rack`)";
			sql += " VALUES ";
			typeNo = 1;
			for(com.tvi.sharing.dto.FibreNodeDto fiNo : srRequestJson.getFibre_Node()){
				totalRatedPowerInKW += fiNo.getPower_Rating_in_Kw();
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+fiNo.getNode_Type()+"', '"+fiNo.getNode_Location()+"', '"+fiNo.getNode_Manufacturer()+"', "
						+ "'"+fiNo.getNode_Model()+"', "+fiNo.getLength_Mtrs()+", "+fiNo.getBreadth_Mtrs()+", "+fiNo.getHeight_Mtrs()+", "
						+ ""+fiNo.getWeight_Kg()+", '"+fiNo.getNode_Voltage()+"', "+fiNo.getPower_Rating_in_Kw()+", '"+fiNo.getFullRack()+"', "
						+ ""+fiNo.getTx_Rack_Space_required_in_Us()+", '"+fiNo.getIs_Right_Of_Way_ROW_Required_Inside_The_TOCO_Premises()+"', "
						+ "'"+fiNo.getType_Of_Fiber_Laying()+"', '"+fiNo.getType_Of_FMS()+"', '"+fiNo.getRemarks()+"', '"+fiNo.getFull_Rack()+"')";
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// Other Equipment
			List<com.tvi.sharing.dto.OtherEquipmentDto> otherEquipment = srRequestJson.getOther_Equipment();
			sql = "INSERT INTO `Airtel_Other_Equipment`(`SR_Number`, `Type_No`, `Action`, `Source_Request_RefNo`, "
					+ "`Other_Equipment_Category`, `Other_Equipment_Type`, `Equipment_to_be_relocated`, "
					+ "`Target_Indus_Site_Id`, `Target_Request_RefNo`)";
			sql += " VALUES ";
			typeNo = 1;
			for(com.tvi.sharing.dto.OtherEquipmentDto otEq : otherEquipment){
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+otEq.getAction()+"', '"+otEq.getSource_Request_RefNo()+"', '"+otEq.getOther_Equipment_Category()+"', '"+otEq.getOther_Equipment_Type()+"', "
						+ "'"+otEq.getEquipment_to_be_relocated()+"', '"+otEq.getTarget_Indus_Site_Id()+"', '"+otEq.getTarget_Request_RefNo()+"')";
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			totalRatedPowerInWatt = totalRatedPowerInKW * 1000;
			String updatePowerRating = "UPDATE `Airtel_SR` set `Total_Rated_Power_In_KW` = "+totalRatedPowerInKW+", "
			+ "`Total_Rated_Power_In_Watt` = "+totalRatedPowerInWatt+" where `SR_Number` = '"+srNumber+"' ";
			int updateExe = getEm().createNativeQuery(updatePowerRating).executeUpdate();
			if(updateExe != 0){
				//System.out.println("Success");
			}
		}
	
	}
	
	@Override
	public void raiseUpgradeSr(String srNumber, SiteInfoDto siteObj, UpgradeSrRequestJsonDto srRequestJson) {
		Double totalRatedPowerInKW = 0.0,totalRatedPowerInWatt;
		com.tvi.upgrade.dto.GlobalDto global = srRequestJson.getGlobal();
		RequestedEquipmentDto reqEquip = global.getRequested_Equipment();
		com.tvi.upgrade.dto.FiberNodeProvisioningDto fbp = srRequestJson.getFiber_Node_Provisioning();
		com.tvi.upgrade.dto.SacfaDto sacfa = srRequestJson.getSACFA();
		String sql = "INSERT INTO `Airtel_SR`(`SR_Number`, `Ref_Number_TVIPL`, `SiteId`, `Operator`, `Remarks`, `Activity_Type`, "
				+ "`TOCO_Site_Id`, `Request_Ref_No`, `RL_Type`, `Upgrade_Type`, `BSC`, "
				+ "`BTS_Cabinet`, `Extend_Tenure`, `Fiber_Node_Provisioning`, `Micro_to_Macro_conversion`, "
				+ "`MW_Antenna`, `Other_Nodes`, `Radio_Antenna`, `Strategic_Conversion`, "
				+ "`Tower_Mounted_Booster`, `MW_IDU`, `Pole`, `HubTag_Untag`, `Other_Equipment2`, `Request_for_Network_Type`, "
				+ "`Project_Name`, "
				+ "`Is_Fiber_Node_Provisioning_Required`, `No_of_Pairs`, `Distance_Length_of_Fiber_in_Meter`, "
				+ "`SACFA_Number`, `STATUS`, `SR_DATE`, `TAB_NAME`, `Customer_Site_Name`, "
				+ "`Circle`, `CircleCode`)";
		sql += " VALUES ";
		sql += " ('"+srNumber+"', '"+srRequestJson.getRef_Number_TVIPL()+"', '"+srRequestJson.getSiteId()+"', '"+global.getOperator()+"', '"+global.getRemarks()+"', '"+global.getActivity_Type()+"', "
				+ "'"+global.getTOCO_Site_ID()+"', '"+global.getRequest_Ref_No()+"', '"+global.getRL_Type()+"', '"+global.getUpgrade_Type()+"', '"+reqEquip.getBSC()+"', "
				+ "'"+reqEquip.getBTS_Cabinet()+"', '"+reqEquip.getExtend_Tenure()+"', '"+reqEquip.getFiber_Node_Provisioning()+"', '"+reqEquip.getMicro_to_Macro_conversion()+"', "
				+ "'"+reqEquip.getMW_Antenna()+"', '"+reqEquip.getOther_Nodes()+"', '"+reqEquip.getRadio_Antenna()+"', '"+reqEquip.getStrategic_Conversion()+"', "
				+ "'"+reqEquip.getTower_Mounted_Booster()+"', '"+reqEquip.getMW_IDU()+"', '"+reqEquip.getPole()+"', '"+reqEquip.getHubTag_Untag()+"', '"+reqEquip.getOther_Equipment()+"', '"+global.getRequest_for_Network_Type()+"', "
				+ "'"+global.getProject_Name()+"', "
				+ "'"+fbp.getIs_Fiber_Node_Provisioning_Required()+"', "+fbp.getNo_of_Pairs()+", "+fbp.getDistance_Length_of_Fiber_in_Meter()+", "
				+ "'"+sacfa.getSACFA_Number()+"', 'NB01', curdate(), '"+Constant.Site_Upgrade+"', '"+siteObj.getSiteName()+"', "
				+ "'"+siteObj.getCircle()+"', '"+siteObj.getCircle()+"') ";
		int i = getEm().createNativeQuery(sql).executeUpdate();
		if(i != 0){
			// Priority
			sql = "INSERT INTO `Airtel_Priority_Details`(`SR_Number`, "
					+ "`P1_Site_Address`, `P1_Tower_Type`, `P1_Latitude`, `P1_Longitude`)";
					sql += " VALUES ";
					sql += " ('"+srNumber+"', "
							+ "?, ?, ?, ?)";
			i = getEm().createNativeQuery(sql)
					.setParameter(1, siteObj.getSiteAddess())
					.setParameter(2, siteObj.getTowerType())
					.setParameter(3, siteObj.getLatitude())
					.setParameter(4, siteObj.getLongitude()).executeUpdate();
			
			// BTS
			com.tvi.upgrade.dto.BtsDto bts = srRequestJson.getBTS();
			sql = "INSERT INTO `Airtel_BTS`(`SR_Number`, `Type_No`, `Customer_Punched_Or_Planning`, "
					+ "`Config_type_1`, `Config_type_2`, `Config_type_3`, `Config_Carriers`, "
					+ " `Action`, `Source`, `NetWork_Type`, `BTS_Type`, `Band`, `Manufacturer`, "
					+ "`Make_of_BTS`, `Length_Mtrs`, `Width_Mtrs`, `Height_Mtrs`, "
					+ "`BTS_Power_Rating_KW`, `BTS_Location`, `BTS_Voltage`, "
					+ "`Main_Unit_incase_of_TT_Split_Version`, `Space_Occupied_in_Us_incase_of_TT_Split_Version`, "
					+ "`RRU_Unit`, `No_of_RRU_Units_incase_of_TT_Split_Version`, "
					+ "`Combined_wt_of_RRU_Unit_incase_of_TT_Split_Version`, `AGL_of_RRU_unit_in_M`, "
					+ "`Weight_of_BTS_including_TMA_TMB_Kg`, `Billable_Weigtht`)";
			sql += " VALUES ";
			int typeNo = 1;
			for(com.tvi.upgrade.dto.BtsCabinetDto btsCabi : bts.getBTS_Cabinet()){
//				totalRatedPowerInKW += btsCabi.getBTS_Power_Rating_KW();
				String action = btsCabi.getAction();
				if(action.equalsIgnoreCase("Add")){
					totalRatedPowerInKW += btsCabi.getBTS_Power_Rating_KW();
				}
				String dataSql = " ('"+srNumber+"', "+typeNo+", '"+btsCabi.getCustomer_Punched_Or_Planning()+"', "
						+ ""+bts.getConfig_type_1()+", "+bts.getConfig_type_2()+", "+bts.getConfig_type_3()+", "+bts.getConfig_Carriers()+", "
						+ "'"+btsCabi.getAction()+"', '"+btsCabi.getSource()+"', '"+btsCabi.getNetWork_Type()+"', '"+btsCabi.getBTS_Type()+"', '"+btsCabi.getBand()+"', '"+btsCabi.getManufacturer()+"', "
						+ "'"+btsCabi.getMake_of_BTS()+"', "+btsCabi.getLength_Mtrs()+", "+btsCabi.getWidth_Mtrs()+", "+btsCabi.getHeight_Mtrs()+", "
						+ ""+btsCabi.getBTS_Power_Rating_KW()+", '"+btsCabi.getBTS_Location()+"', '"+btsCabi.getBTS_Voltage()+"', "
						+ "'"+btsCabi.getMain_Unit_incase_of_TT_Split_Version()+"', "+btsCabi.getSpace_Occupied_in_Us_incase_of_TT_Split_Version()+", "
						+ "'"+btsCabi.getRRU_Unit()+"', "+btsCabi.getNo_Of_RRU_Units_incase_of_TT_Split_Version()+", "
						+ ""+btsCabi.getCombined_wt_Of_RRU_Unit_incase_of_TT_Split_Version()+", "+btsCabi.getAGL_of_RRU_unit_in_M()+", "
						+ ""+btsCabi.getWeight_Of_BTS_including_TMA_TMB_Kg()+", "+btsCabi.getBillable_Weigtht()+")";
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// Radio Antenna
			sql = "INSERT INTO `Airtel_Radio_Antenna`(`SR_Number`, `Type_No`, `Customer_Punched_Or_Planning`, `Sector_Details`, `Action`, "
					+ "`RadioAntenna_i_WAN`, `Height_AGL_m`, `Azimuth_Degree`, "
					+ "`Length_m`, `Width_m`, `Depth_m`, `No_of_Ports`, `BandFrequencyMHz_FrequencyCombination`, "
					+ "`RadioAntenna_Type`)";
			sql += " VALUES ";
			typeNo = 1;
			for(com.tvi.upgrade.dto.RadioAntennaDto radioAntenna : srRequestJson.getRadio_Antenna()){
				com.tvi.upgrade.dto.BandFrequencyMHzDto bandFreq = radioAntenna.getBandFrequencyMHz_FrequencyCombination();
				String bandFreqM = bandFreq.getFrequency_Number_1()+","+bandFreq.getFrequency_Number_2()+","+bandFreq.getFrequency_Number_3()+","+bandFreq.getFrequency_Number_4()+","+
				bandFreq.getFrequency_Number_5()+","+bandFreq.getFrequency_Number_6()+","+bandFreq.getFrequency_Number_7()+","+bandFreq.getFrequency_Number_8()+",";
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+radioAntenna.getCustomer_Punched_Or_Planning()+"', '"+radioAntenna.getSector_Details()+"', '"+radioAntenna.getAction()+"', "
						+ "'"+radioAntenna.getRadioAntenna_i_WAN()+"', "+radioAntenna.getHeight_AGL_m()+", "+radioAntenna.getAzimuth_Degree()+", "
						+ ""+radioAntenna.getLength_m()+", "+radioAntenna.getWidth_m()+", "+radioAntenna.getDepth_m()+", '"+radioAntenna.getNo_of_Ports()+"', '"+bandFreqM+"', "
						+ "'"+radioAntenna.getRadioAntenna_Type()+"')";
				
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// MW
			sql = "INSERT INTO `Airtel_MW`(`SR_Number`, `Type_No`, `Customer_Punched_Or_Planning`, `Sector_Details`, `Action`, `Source`, "
					+ "`Additional_Transmission_Rack_Space_Power_Upgrade_Requirement`, "
					+ "`TotalIDUs_Magazines_tobe_Installed`, `Transmission_Rack_Space_Required_in_Us`, "
					+ "`Power_Rating_in_Kw`, `Power_Plant_Voltage`, `MWAntenna_i_WAN`, "
					+ "`Size_of_MW`, `Height_in_Mtrs`, `Azimuth_Degree`)";
			sql += " VALUES ";
			com.tvi.upgrade.dto.MwDto mw = srRequestJson.getMW();
			totalRatedPowerInKW += mw.getPower_Rating_in_Kw();
			typeNo = 1;
			for(com.tvi.upgrade.dto.MwAntennaDto mwAnt : mw.getMW_Antenna()){
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+mwAnt.getCustomer_Punched_Or_Planning()+"', '"+mwAnt.getSector_Details()+"', '"+mwAnt.getAction()+"', '"+mwAnt.getSource()+"', "
						+ "'"+mw.getAdditional_Transmission_Rack_Space_Power_Upgrade_Requirement()+"', "
						+ "'"+mw.getTotalIDUs_Magazines_tobe_Installed()+"', "+mw.getTransmission_Rack_Space_Required_in_Us()+", "
						+ ""+mw.getPower_Rating_in_Kw()+", '"+mw.getPower_Plant_Voltage()+"', '"+mwAnt.getMWAntenna_i_WAN()+"', "
						+ ""+mwAnt.getSize_of_MW()+", "+mwAnt.getHeight_in_Mtrs()+", "+mwAnt.getAzimuth_Degree()+")";
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// BSC RNC
			sql = "INSERT INTO `Airtel_BSC_RNC_Cabinets`(`SR_Number`, `Type_No`, `Customer_Punched_Or_Planning`, `Action`, `Source`, "
					+ "`NetWork_Type`, `BSC_RNC_Type`, `BSC_RNC_Manufacturer`, "
					+ "`BSC_RNC_Make`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_AGL`, `BSC_RNC_Power_Rating`, `NodeType`)";
			sql += " VALUES ";
			typeNo = 1;
			for(com.tvi.upgrade.dto.BscRncCabinetsDto bsc : srRequestJson.getBSC_RNC_Cabinets()){
//				totalRatedPowerInKW += bsc.getBSC_RNC_Power_Rating();
				String action = bsc.getAction();
				if(action.equalsIgnoreCase("Add")){
					totalRatedPowerInKW += bsc.getBSC_RNC_Power_Rating();
				}
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+bsc.getCustomer_Punched_Or_Planning()+"', '"+bsc.getAction()+"', '"+bsc.getSource()+"', "
						+ "'"+bsc.getNetWork_Type()+"', '"+bsc.getBSC_RNC_Type()+"', '"+bsc.getBSC_RNC_Manufacturer()+"', "
						+ "'"+bsc.getBSC_RNC_Make()+"', "+bsc.getLength_Mtrs()+", "+bsc.getBreadth_Mtrs()+", "+bsc.getHeight_AGL()+", "
						+ ""+bsc.getBSC_RNC_Power_Rating()+", '"+bsc.getNodeType()+"')";
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// Other Node
			sql = "INSERT INTO `Airtel_Other_Node`(`SR_Number`, `Type_No`, `Customer_Punched_Or_Planning`, `Action`, `Node_Type`, `Node_Location`, `Node_Manufacturer`, "
					+ "`Node_Model`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, "
					+ "`Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, `FullRack`, "
					+ "`Tx_Rack_Space_Required_In_Us`, `Remarks`)";
			sql += " VALUES ";
			typeNo = 1;
			for(com.tvi.upgrade.dto.OtherNodeDto other : srRequestJson.getOther_Node()){
//				totalRatedPowerInKW += other.getPower_Rating_in_Kw();
				String action = other.getAction();
				if(action.equalsIgnoreCase("Add")){
					totalRatedPowerInKW += other.getPower_Rating_in_Kw();
				}
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+other.getCustomer_Punched_Or_Planning()+"', '"+other.getAction()+"', '"+other.getNode_Type()+"', '"+other.getNode_Location()+"', '"+other.getNode_Manufacturer()+"', "
						+ "'"+other.getNode_Model()+"', "+other.getLength_Mtrs()+", "+other.getBreadth_Mtrs()+", "+other.getHeight_Mtrs()+", "
						+ ""+other.getWeight_Kg()+", '"+other.getNode_Voltage()+"', "+other.getPower_Rating_in_Kw()+", '"+other.getFullRack()+"', "
						+ ""+other.getTx_Rack_Space_Required_In_Us()+", '"+other.getRemarks()+"')";
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// AdditionalInfo
			com.tvi.upgrade.dto.StrategicConversionDto strCon = srRequestJson.getStrategic_Conversion();
			com.tvi.upgrade.dto.AdditionalInformationDto addInfo = srRequestJson.getAdditional_Information();
			String anyOtherSpecReq = strCon.getAnyother_Specific_Requirements();
			anyOtherSpecReq = anyOtherSpecReq == null ? "" : anyOtherSpecReq;
			sql = "INSERT INTO `Airtel_Additional_Information`(`SR_Number`, `Is_it_Strategic`, `Shelter_Size`, `Length_Mtrs`, "
					+ "`Breadth_Mtrs`, `Height_AGL_Mtrs`, `DG_Redundancy`, "
					+ "`Extra_Battery_Bank_Requirement`, `Extra_Battery_BackUp`, "
					+ "`Anyother_Specific_Requirements`, "
					+ "`Other_Additional_Info1`, `Other_Additional_Info2`)";
			sql += " VALUES ";
			sql += "('"+srNumber+"', '"+strCon.getIs_it_Strategic()+"', '"+strCon.getShelter_Size()+"', "+strCon.getLength_Mtrs()+", "
					+ ""+strCon.getBreadth_Mtrs()+", "+strCon.getHeight_AGL_Mtrs()+", '"+strCon.getDG_Redundancy()+"', "
					+ "'"+strCon.getExtra_Battery_Bank_Requirement()+"', '"+strCon.getExtra_Battery_BackUp()+"', "
					+ "'"+anyOtherSpecReq+"', "
					+ "'"+addInfo.getOther_Additional_Info1()+"', '"+addInfo.getOther_Additional_Info2()+"')";
			
			getEm().createNativeQuery(sql).executeUpdate();
			
			// MCB
			com.tvi.upgrade.dto.McbDto mcb = srRequestJson.getMCB();
			sql = "INSERT INTO `Airtel_MCB`(`SR_Number`, `Total_No_of_MCB_Required`, "
					+ "`_06A`, `_10A`, `_16A`, `_24A`, "
					+ "`_32A`, `_40A`, `_63A`, `_80A`)";
			sql += " VALUES ";
			sql += "('"+srNumber+"', "+mcb.getTotal_No_of_MCB_Required()+", "
					+ ""+mcb.get_06A()+", "+mcb.get_10A()+", "+mcb.get_16A()+", "+mcb.get_24A()+", "
					+ ""+mcb.get_32A()+", "+mcb.get_40A()+", "+mcb.get_63A()+", "+mcb.get_80A()+")";
			
			getEm().createNativeQuery(sql).executeUpdate();
			
			// Fibre Node
			sql = "INSERT INTO `Airtel_Fibre_Node`(`SR_Number`, `Type_No`, `Customer_Punched_Or_Planning`, `Action`, `Source`, "
					+ "`Node_Type`, `Node_Location`, `Node_Manufacturer`, "
					+ "`Node_Model`, `Length_Mtrs`, `Breadth_Mtrs`, `Height_Mtrs`, "
					+ "`Weight_Kg`, `Node_Voltage`, `Power_Rating_in_Kw`, `FullRack`, "
					+ "`Tx_Rack_Space_required_in_Us`, `Is_Right_Of_Way_ROW_Required_Inside_The_Indus_Premises`, "
					+ "`Type_Of_Fiber_Laying`, `Type_Of_FMS`, `Remarks`)";
			sql += " VALUES ";
			typeNo = 1;
			for(com.tvi.upgrade.dto.FibreNodeDto fiNo : srRequestJson.getFibre_Node()){
//				totalRatedPowerInKW += fiNo.getPower_Rating_in_Kw();
				String action = fiNo.getAction();
				if(action.equalsIgnoreCase("Add")){
					totalRatedPowerInKW += fiNo.getPower_Rating_in_Kw();
				}
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+fiNo.getCustomer_Punched_Or_Planning()+"', '"+fiNo.getAction()+"', '"+fiNo.getSource()+"', "
						+ "'"+fiNo.getNode_Type()+"', '"+fiNo.getNode_Location()+"', '"+fiNo.getNode_Manufacturer()+"', "
						+ "'"+fiNo.getNode_Model()+"', "+fiNo.getLength_Mtrs()+", "+fiNo.getBreadth_Mtrs()+", "+fiNo.getHeight_Mtrs()+", "
						+ ""+fiNo.getWeight_Kg()+", '"+fiNo.getNode_Voltage()+"', "+fiNo.getPower_Rating_in_Kw()+", '"+fiNo.getFullRack()+"', "
						+ ""+fiNo.getTx_Rack_Space_required_in_Us()+", '"+fiNo.getIs_Right_of_Way_ROW_required_inside_the_Indus_premises()+"', "
						+ "'"+fiNo.getType_of_Fiber_Laying()+"', '"+fiNo.getType_of_FMS()+"', '"+fiNo.getRemarks()+"')";
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// TMA_TMB
			sql = "INSERT INTO `Airtel_TMA_TMB`(`SR_Number`, `Type_No`, `Action`, `Source`, "
					+ "`No_of_TMA_TMB`, `Weight_of_each_TMA_TMB`, `Combined_wt_of_TMA_TMB_Kgs`, "
					+ "`Height_at_which_needs_to_be_mounted_Mtrs`)";
			sql += " VALUES ";
			typeNo = 1;
			for(com.tvi.upgrade.dto.TmaTmbDto tmaTmb : srRequestJson.getTMA_TMB()){
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+tmaTmb.getAction()+"', '"+tmaTmb.getSource()+"', "
					+ "'"+tmaTmb.getNo_of_TMA_TMB()+"', '"+tmaTmb.getWeight_of_each_TMA_TMB()+"', '"+tmaTmb.getCombined_wt_of_TMA_TMB_Kgs()+"', "
					+ "'"+tmaTmb.getHeight_at_which_needs_to_be_mounted_Mtrs()+"')";
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			// Other Equipment
			List<com.tvi.upgrade.dto.OtherEquipmentDto> otherEquipment = srRequestJson.getOther_Equipment();
			sql = "INSERT INTO `Airtel_Other_Equipment`(`SR_Number`, `Type_No`, `Action`, `Source_Request_RefNo`, "
					+ "`Other_Equipment_Category`, `Other_Equipment_Type`, `Equipment_to_be_relocated`, "
					+ "`Target_Indus_Site_Id`, `Target_Request_RefNo`)";
			sql += " VALUES ";
			typeNo = 1;
			for(com.tvi.upgrade.dto.OtherEquipmentDto otEq : otherEquipment){
				String dataSql = "('"+srNumber+"', "+typeNo+", '"+otEq.getAction()+"', '"+otEq.getSource_Request_RefNo()+"', '"+otEq.getOther_Equipment_Category()+"', '"+otEq.getOther_Equipment_Type()+"', "
						+ "'"+otEq.getEquipment_to_be_relocated()+"', '"+otEq.getTarget_Indus_Site_Id()+"', '"+otEq.getTarget_Request_RefNo()+"')";
				
				int exe = getEm().createNativeQuery(sql + dataSql).executeUpdate();
				if(exe != 0){
					typeNo++;
				}
			}
			
			totalRatedPowerInWatt = totalRatedPowerInKW * 1000;
			String updatePowerRating = "UPDATE `Airtel_SR` set `Total_Rated_Power_In_KW` = "+totalRatedPowerInKW+", "
			+ "`Total_Rated_Power_In_Watt` = "+totalRatedPowerInWatt+" where `SR_Number` = '"+srNumber+"' ";
			int updateExe = getEm().createNativeQuery(updatePowerRating).executeUpdate();
			if(updateExe != 0){
				//System.out.println("Success");
			}
		}
		
	}
	
	@Override
	public boolean srSoWithdrawal(SrSoWithdrawalDto jsonData) {
		String srNumber = jsonData.getSR_No();
		String wt = jsonData.getWithdrawal_Type();
		String status = "";
		if(wt.equalsIgnoreCase("SR Withdrawal"))
			status = "NB102";
		else if(wt.equalsIgnoreCase("SO Withdrawal"))
			status = "NB106";
		
		String sql = "UPDATE `Airtel_SR` set `Withdrawal_Type` = ?, `Withdrawal_Reason` = ?, `withdraw_remark` = ?, `STATUS` = '"+status+"' where `SR_Number` = ?";
		int exe = getEm().createNativeQuery(sql)
				.setParameter(1, jsonData.getWithdrawal_Type())
				.setParameter(2, jsonData.getWithdrawal_Reason())
				.setParameter(3, jsonData.getWithdraw_remark())
				.setParameter(4, srNumber)
				.executeUpdate();
		if(exe != 0){
			sql = "SELECT `SR_Number`, `SP_Number`, `SO_Number`, `TAB_NAME`, `CircleCode`  FROM `Airtel_SR` where `SR_Number` = '"+srNumber+"'";
			List<Object[]> dataList = getAllTableData(sql);
			if(dataList.size() !=0){
				Object [] obj = dataList.get(0);
				srNumber = String.valueOf(obj[0]);
				String spNumber = obj[1] == null ? "NA" : String.valueOf(obj[1]);
				String soNumber = obj[2] == null ? "NA" : String.valueOf(obj[2]);
				String tabName = String.valueOf(obj[3]);
				String circleCode =  String.valueOf(obj[4]);
				
				String insAudSql = "INSERT INTO `Airtel_SR_Audit`(`SR_NUMBER`, `EMP_ID`, `ACTION`, `REMARK`) "
						+ "VALUES "
						+ "('"+srNumber+"', '"+circleCode+"', '"+status+"', '"+wt+"')";
				int i = getEm().createNativeQuery(insAudSql).executeUpdate();
				if(i != 0){
					jsonData.setSrNumber(srNumber);
					jsonData.setSpNumber(spNumber);
					jsonData.setSoNumber(soNumber);
					jsonData.setStatus(status);
					jsonData.setTabName(tabName);
					jsonData.setCircle(circleCode);
				}
			}
			return true;
		}
		return false;
		
	}
	
	@Override
	public boolean rfaiAcceptReject(RfaiAcceptReject jsonData) {
		String accRej = jsonData.getAccept_Reject();
		String tabName = "NA";
		String auditStatus = "";
		String st = "";
		String srNumber = jsonData.getSR_No();
		if(accRej.equalsIgnoreCase("Accept")){
			if(srNumber.indexOf("NB_")>-1){
//				st += ",`Status` = 'NB18', `RFI_ACCEPTED_DATE` = curdate()";
				auditStatus = "NB18";
				tabName = Constant.CreateNBS;
			}
			else if(srNumber.indexOf("HPSC_")>-1){
//				st += ",`Status` = 'NB18', `RFI_ACCEPTED_DATE` = curdate()";
				auditStatus = "NB18";
				tabName = Constant.HPSC;
			}
			else if(srNumber.indexOf("SH_")>-1 || srNumber.indexOf("NT_")>-1){
//				st += ",`Status` = 'NB09', `RFI_ACCEPTED_DATE` = curdate()";
				auditStatus = "NB09";
				tabName = Constant.New_Tenency;
			}
			else if(srNumber.indexOf("SU_")>-1 || srNumber.indexOf("UP_")>-1){
//				st += ",`Status` = 'NB07', `RFI_ACCEPTED_DATE` = curdate()";
				auditStatus = "NB07";
				tabName = Constant.Site_Upgrade;
			}
			st += ",`Status` = '"+auditStatus+"', `RFI_ACCEPTED_DATE` = curdate()";
		}
		else{
			if(srNumber.indexOf("NB_")>-1){
//				auditStatus = "NB108";
				auditStatus = "RNB15";
				tabName = Constant.CreateNBS;
			}
			else if(srNumber.indexOf("HPSC_")>-1){
//				auditStatus = "NB108";
				auditStatus = "RNB15";
				tabName = Constant.HPSC;
			}
			else if(srNumber.indexOf("SH_")>-1 || srNumber.indexOf("NT_")>-1){
//				auditStatus = "NB108";
				auditStatus = "RNB06";
				tabName = Constant.New_Tenency;
			}
			else if(srNumber.indexOf("SU_")>-1 || srNumber.indexOf("UP_")>-1){
//				auditStatus = "NB108";
				auditStatus = "RNB04";
				tabName = Constant.Site_Upgrade;
			}
			st += ", `Status`='"+auditStatus+"', `RFI_DATE` = null, `RFI_ACCEPTED_DATE` = null, `Tower_Completion` = null, "
					+ "`Shelter_Equipment_RoomReady` = null, `AirConditioner_Commissioned` = null, "
					+ "`DG_Commissioned` = null, `Acceptance_Testing_Of_Site_Infrastructure_Completed_Status` = null, "
					+ "`EB_Status` = null, `Created_By` = null, `OFC_Duct_Laying_Done` = null, "
					+ "`Access_To_Site_Available_Status` = null, `Engineer_Name` = null, `Engineer_Phone_Number` = null, "
					+ "`Notice_Form_Generation_Date` = null";		
			
		}
		String sql = "UPDATE `Airtel_SR` set `RFI_Accept_Reject` = ?, `RFI_Reject_Remarks` = ?, "
				+ "`RFI_Reject_Remarks_Others` = ? "+st+" where `SR_Number` = ?";
		int exe = getEm().createNativeQuery(sql)
				.setParameter(1, accRej)
				.setParameter(2, jsonData.getReject_Remarks())
				.setParameter(3, jsonData.getReject_Remarks_Others())
				.setParameter(4, srNumber)
				.executeUpdate();
		if(exe != 0){
			sql = "SELECT `SR_Number`, `SP_Number`, `SO_Number`, `CircleCode`  FROM `Airtel_SR` where `SR_Number` = '"+srNumber+"'";
			List<Object[]> dataList = getAllTableData(sql);
			if(dataList.size() !=0){
				Object [] obj = dataList.get(0);
				srNumber = String.valueOf(obj[0]);
				String spNumber = obj[1] == null ? "NA" : String.valueOf(obj[1]);
				String soNumber = obj[2] == null ? "NA" : String.valueOf(obj[2]);
				String circleCode =  String.valueOf(obj[3]);
				String insAudSql = "INSERT INTO `Airtel_SR_Audit`(`SR_NUMBER`, `EMP_ID`, `ACTION`, `REMARK`) "
						+ "VALUES "
						+ "('"+srNumber+"', '"+circleCode+"', '"+auditStatus+"', 'RFAI "+accRej+"')";
				int i = getEm().createNativeQuery(insAudSql).executeUpdate();
				if(i != 0){
					jsonData.setSrNumber(srNumber);
					jsonData.setSpNumber(spNumber);
					jsonData.setSoNumber(soNumber);
					jsonData.setStatus(auditStatus);
					jsonData.setTabName(tabName);
					jsonData.setCircle(circleCode);
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean rejectSp(RejectSpDto jsonData) {
		String accRej = jsonData.getAccept_Reject();
		String srNumber = jsonData.getSR_No();
		String tabName = "NA";
		String auditStatus = "";
		String st = "";
		if(accRej.equalsIgnoreCase("Accept")){
			if(srNumber.indexOf("NB_")>-1){
				st = ", `STATUS` = 'NB103'";
				auditStatus = "NB103";
				tabName = Constant.CreateNBS;
			}
			else if(srNumber.indexOf("HPSC_")>-1){
				st = ", `STATUS` = 'NB103'";
				auditStatus = "NB103";
				tabName = Constant.HPSC;
			}
			else if(srNumber.indexOf("SH_")>-1 || srNumber.indexOf("NT_")>-1){
				st = ", `STATUS` = 'NB103'";
				auditStatus = "NB103";
				tabName = Constant.New_Tenency;
			}
			else if(srNumber.indexOf("SU_")>-1 || srNumber.indexOf("UP_")>-1){
				st = ", `STATUS` = 'NB103'";
				auditStatus = "NB103";
				tabName = Constant.Site_Upgrade;
			}
		}
		else{
			if(srNumber.indexOf("NB_")>-1){
//				st = ", `STATUS` = 'NB104', `SP_Number` = null, `TOCO_Site_Id` = null, `SP_DATE` = null";
				st = ", `STATUS` = 'NB104'";
				auditStatus = "NB104";
				tabName = Constant.CreateNBS;
			}
			else if(srNumber.indexOf("HPSC_")>-1){
//				st = ", `STATUS` = 'NB104', `SP_Number` = null, `TOCO_Site_Id` = null, `SP_DATE` = null";
				st = ", `STATUS` = 'NB104'";
				auditStatus = "NB104";
				tabName = Constant.HPSC;
			}
			else if(srNumber.indexOf("SH_")>-1 || srNumber.indexOf("NT_")>-1){
//				st = ", `STATUS` = 'NB104', `SP_Number` = null, `SP_DATE` = null";
				st = ", `STATUS` = 'NB104'";
				auditStatus = "NB104";
				tabName = Constant.New_Tenency;
			}
			else if(srNumber.indexOf("SU_")>-1 || srNumber.indexOf("UP_")>-1){
//				st = ", `STATUS` = 'NB104', `SP_Number` = null, `SP_DATE` = null";
				st = ", `STATUS` = 'NB104'";
				auditStatus = "NB104";
				tabName = Constant.Site_Upgrade;
			}
			
		}

		String sql = "UPDATE `Airtel_SR` set `Rejection_Category` = ?, `Rejection_Reason` = ?, `Accept_Reject` = ?, "
				+ "`Reject_SP_Remark` = ?"+st+" where `SR_Number` = ?";
		
		int exe = getEm().createNativeQuery(sql)
				.setParameter(1, jsonData.getRejection_Category())
				.setParameter(2, jsonData.getRejection_Reason())
				.setParameter(3, accRej)
				.setParameter(4, jsonData.getRemarks())
				.setParameter(5, jsonData.getSR_No())
				.executeUpdate();
		if(exe != 0){
			/*String delSql = "DELETE FROM `Airtel_BTS` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP';";
			getEm().createNativeQuery(delSql).executeUpdate();
			
			delSql = "DELETE FROM `Airtel_Radio_Antenna` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP';";
			getEm().createNativeQuery(delSql).executeUpdate();
			
			delSql = "DELETE FROM `Airtel_BSC_RNC_Cabinets` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP';";
			getEm().createNativeQuery(delSql).executeUpdate();
			
			delSql = "DELETE FROM `Airtel_Fibre_Node` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP';";
			getEm().createNativeQuery(delSql).executeUpdate();
			
			delSql = "DELETE FROM `Airtel_MCB` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP';";
			getEm().createNativeQuery(delSql).executeUpdate();
			
			delSql = "DELETE FROM `Airtel_MW` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP';";
			getEm().createNativeQuery(delSql).executeUpdate();
			
			delSql = "DELETE FROM `Airtel_Other_Node` where `SR_Number` = '"+srNumber+"' and `InsertType` = 'SP';";
			getEm().createNativeQuery(delSql).executeUpdate();*/
			
			sql = "SELECT `SR_Number`, `SP_Number`, `SO_Number`, `CircleCode`  FROM `Airtel_SR` where `SR_Number` = '"+srNumber+"'";
			List<Object[]> dataList = getAllTableData(sql);
			if(dataList.size() !=0){
				Object [] obj = dataList.get(0);
				srNumber = String.valueOf(obj[0]);
				String spNumber = obj[1] == null ? "NA" : String.valueOf(obj[1]);
				String soNumber = obj[2] == null ? "NA" : String.valueOf(obj[2]);
				String circleCode =  String.valueOf(obj[3]);
				String insAudSql = "INSERT INTO `Airtel_SR_Audit`(`SR_NUMBER`, `EMP_ID`, `ACTION`, `REMARK`) "
						+ "VALUES "
						+ "('"+srNumber+"', '"+circleCode+"', '"+auditStatus+"', 'SP "+accRej+"')";
				int i = getEm().createNativeQuery(insAudSql).executeUpdate();
				if(i != 0){
					jsonData.setSrNumber(srNumber);
					jsonData.setSpNumber(spNumber);
					jsonData.setSoNumber(soNumber);
					jsonData.setStatus(auditStatus);
					jsonData.setTabName(tabName);
					jsonData.setCircle(circleCode);
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean soSubmit(SoSubmitDto jsonData) {
		String spNumber = jsonData.getSP_Ref_No();
		String tabName = "NA";
		String accRej = jsonData.getAccept_Reject();
		String status = "";
		if(spNumber.indexOf("NB_")>-1){
			tabName = Constant.CreateNBS;
			if(accRej.equalsIgnoreCase("Accept")){
				status = "NB10";
			}
			else{
				status = "NB110";
			}
		}
		else if(spNumber.indexOf("HPSC_")>-1){
			tabName = Constant.HPSC;
			if(accRej.equalsIgnoreCase("Accept")){
				status = "NB10";
			}
			else{
				status = "NB110";
			}
		}
		else if(spNumber.indexOf("SH_")>-1 || spNumber.indexOf("NT_")>-1){
			tabName = Constant.New_Tenency;
			if(accRej.equalsIgnoreCase("Accept")){
				status = "NB05";
			}
			else{
				status = "NB110";
			}
		}
		else if(spNumber.indexOf("SU_")>-1 || spNumber.indexOf("UP_")>-1){
			tabName = Constant.Site_Upgrade;
			if(accRej.equalsIgnoreCase("Accept")){
				status = "NB03";
			}
			else{
				status = "NB110";
			}
		}
		
		String sql = "UPDATE `Airtel_SR` set `Financial_Site_Id` = ?, `Expected_monthly_Rent_Approved` = ?, "
				+ "`CAPEX_Amount_Approved` = ?, `OPEX_Amount_Approved` = ?, `Tentative_Billing_Amount_Approved` = ?,"
				+ "`Target_Date` = ?, `Date` = ?, `SO_Number` = ?, `Tenure_In_Years` = ?, `SO_DATE` = curdate(), "
				+ "`SO_Accept_Reject` = ?, `STATUS`='"+status+"'  "
				+ "where `SP_Number` = ?";
		if(status.equalsIgnoreCase("NB110")){
			sql = "UPDATE `Airtel_SR` set `Financial_Site_Id` = ?, `Expected_monthly_Rent_Approved` = ?, "
					+ "`CAPEX_Amount_Approved` = ?, `OPEX_Amount_Approved` = ?, `Tentative_Billing_Amount_Approved` = ?,"
					+ "`Target_Date` = ?, `Date` = ?, `SO_Number` = ?, `Tenure_In_Years` = ?, "
					+ "`SO_Accept_Reject` = ?, `STATUS`='"+status+"' "
					+ "where `SP_Number` = ?";
		}
		int exe = getEm().createNativeQuery(sql)
				.setParameter(1, jsonData.getFinancial_Site_Id())
				.setParameter(2, jsonData.getExpected_monthly_Rent_Approved())
				.setParameter(3, jsonData.getCAPEX_Amount_Approved())
				.setParameter(4, jsonData.getOPEX_Amount_Approved())
				.setParameter(5, jsonData.getTentative_Billing_Amount_Approved())
				.setParameter(6, jsonData.getTarget_Date())
				.setParameter(7, jsonData.getDate())
				.setParameter(8, jsonData.getCustomer_SO_No())
				.setParameter(9, jsonData.getTenure_In_Years())
				.setParameter(10, accRej)
				.setParameter(11, spNumber)
				.executeUpdate();
		if(exe != 0){
			sql = "SELECT `SR_Number`, `SO_Number`, `CircleCode`  FROM `Airtel_SR` where `SP_Number` = '"+spNumber+"'";
			List<Object[]> dataList = getAllTableData(sql);
			if(dataList.size() !=0){
				Object [] obj = dataList.get(0);
				String srNumber =  String.valueOf(obj[0]);
				String soNumber = String.valueOf(obj[1]);
				String circleCode =  String.valueOf(obj[2]);
				String insAudSql = "INSERT INTO `Airtel_SR_Audit`(`SR_NUMBER`, `EMP_ID`, `ACTION`, `REMARK`) "
						+ "VALUES "
						+ "('"+srNumber+"', '"+circleCode+"', '"+status+"', 'SO "+accRej+"')";
				int i = getEm().createNativeQuery(insAudSql).executeUpdate();
				if(i != 0){
					jsonData.setSrNumber(srNumber);
					jsonData.setSpNumber(spNumber);
					jsonData.setSoNumber(soNumber);
					jsonData.setStatus(status);
					jsonData.setCircle(circleCode);
					jsonData.setTabName(tabName);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void insertHdrAndDetAF(SaveNBSRequest jsonData) {
		
		String hdrTable = "INSERT INTO `NBS_MASTER_HDR` (`SR_NUMBER`, `Operator`, `SR_DATE`, `CIRCLE_NAME`, `TAB_NAME`, `TVI_SITE_ID`, `Site_Name`, `AIRTEL_SITE_ID`, `LATITUDE_1`, `LONGITUDE_1`, `AGL_REQUIRED`, "
				+ "`SITE_ADDRESS`, `DISTRICT`, `STATE`, `CLUTTER`, `SITE_TYPE`, "
				+ "`NO_OF_RF_ANTENNA`, `Total_Rated_Power_in_KW`, `REMARK`, `STATUS`, `CREATE_BY`, `CREATE_DATE`) ";
		
		String srNumber = jsonData.getSrNumber();
		String hdrValue = "VALUES ('"+srNumber+"', '"+jsonData.getOperator()+"', CURRENT_TIMESTAMP, '"+jsonData.getCircleName()+"', '"+jsonData.getCurrentTab()+"', '"+jsonData.getTviSiteId()+"', ?, '"+jsonData.getAirtelSiteId()+"', "+jsonData.getLatitude1()+", "+jsonData.getLongitude1()+", "+jsonData.getAglRequired()+", "
				+ "?, '"+jsonData.getDistrict()+"', '"+jsonData.getState()+"', '"+jsonData.getClutter()+"', '"+jsonData.getSiteType()+"', "
				+ ""+jsonData.getNoOfAirFiber()+", "+jsonData.getTotalRatedPower()+", ?, 'NB01', '"+jsonData.getLoginEmpId()+"' , CURRENT_TIMESTAMP) ";
		getEm().createNativeQuery(hdrTable + hdrValue)
		.setParameter(1, jsonData.getSiteName())
		.setParameter(2, jsonData.getSiteAddress())
		.setParameter(3, jsonData.getRemark()).
		executeUpdate();
		
		if(jsonData.getNoOfAirFiber() != null && jsonData.getNoOfAirFiber() != 0){
			String detTable = "INSERT INTO `NBS_MASTER_DET` (`SR_NUMBER`, `Type_No`, `TYPE`, `AF_Make`, "
					+ "`AF_Model`, `AF_Dimensions`, `AF_Weight`, `AF_Band`, `AF_Technology`, `AF_Load`, `AF_Mcb`,"
					+ "`AF_EquipPlace`, `CREATE_DATE`) ";
			
			List<AirFiberRequest> dataList = jsonData.getAirFiberList();
			String detValue = "";
			int i = 0;
			for(AirFiberRequest r : dataList){
				detValue += "('"+srNumber+"', "+r.getId()+", '"+Constant.Air_Fiber+"', '"+r.getMake()+"', "
						+ "'"+r.getModel()+"', '"+r.getDimensions()+"', "+r.getWeight()+", '"+r.getBand()+"', '"+r.getTechnology()+"', "
						+ ""+r.getLoad()+", '"+r.getMcb()+"', '"+r.getEquipPlacement()+"',  CURRENT_TIMESTAMP)";
				if(i != dataList.size()-1){
					detValue += ",";
				}
				i++;
			}
			if(!detValue.equalsIgnoreCase(""))
				getEm().createNativeQuery(detTable + " VALUES " + detValue).executeUpdate();
		}
	}
	
	/*@Override
	public boolean soSubmit(SoSubmitDto jsonData) {
		String spNumber = jsonData.getSP_Ref_No();
		String tabName = "NA";
		String accRej = jsonData.getAccept_Reject();
		String st = "";
		String auditStatus = "";
		if(spNumber.indexOf("NB_")>-1){
			tabName = Constant.CreateNBS;
			if(accRej.equalsIgnoreCase("Accept")){
				st = ", `STATUS` = 'NB10'";
				auditStatus = "NB10";
			}
		}
		else if(spNumber.indexOf("HPSC_")>-1){
			tabName = Constant.HPSC;
			if(accRej.equalsIgnoreCase("Accept")){
				st = ", `STATUS` = 'NB10'";
				auditStatus = "NB10";
			}
		}
		else if(spNumber.indexOf("SH_")>-1 || spNumber.indexOf("NT_")>-1){
			tabName = Constant.New_Tenency;
			if(accRej.equalsIgnoreCase("Accept")){
				st = ", `STATUS` = 'NB05'";
				auditStatus = "NB05";
			}
		}
		else if(spNumber.indexOf("SU_")>-1 || spNumber.indexOf("UP_")>-1){
			tabName = Constant.Site_Upgrade;
			if(accRej.equalsIgnoreCase("Accept")){
				st = ", `STATUS` = 'NB03'";
				auditStatus = "NB03";
			}
		}
		
		String sql = "UPDATE `Airtel_SR` set `Financial_Site_Id` = ?, `Expected_monthly_Rent_Approved` = ?, "
				+ "`CAPEX_Amount_Approved` = ?, `OPEX_Amount_Approved` = ?, `Tentative_Billing_Amount_Approved` = ?,"
				+ "`Target_Date` = ?, `Date` = ?, `SO_Number` = ?, `Tenure_In_Years` = ?, `SO_DATE` = curdate(), "
				+ "`SO_Accept_Reject` = ? "+st+" "
				+ "where `SP_Number` = ?";
		int exe = getEm().createNativeQuery(sql)
				.setParameter(1, jsonData.getFinancial_Site_Id())
				.setParameter(2, jsonData.getExpected_monthly_Rent_Approved())
				.setParameter(3, jsonData.getCAPEX_Amount_Approved())
				.setParameter(4, jsonData.getOPEX_Amount_Approved())
				.setParameter(5, jsonData.getTentative_Billing_Amount_Approved())
				.setParameter(6, jsonData.getTarget_Date())
				.setParameter(7, jsonData.getDate())
				.setParameter(8, jsonData.getCustomer_SO_No())
				.setParameter(9, jsonData.getTenure_In_Years())
				.setParameter(10, accRej)
				.setParameter(11, spNumber)
				.executeUpdate();
		if(exe != 0){
			sql = "SELECT `SR_Number`, `SO_Number`, `CircleCode`  FROM `Airtel_SR` where `SP_Number` = '"+spNumber+"'";
			List<Object[]> dataList = getAllTableData(sql);
			if(dataList.size() !=0){
				Object [] obj = dataList.get(0);
				String srNumber =  String.valueOf(obj[0]);
				String soNumber = String.valueOf(obj[1]);
				String circleCode =  String.valueOf(obj[2]);
				String insAudSql = "INSERT INTO `Airtel_SR_Audit`(`SR_NUMBER`, `EMP_ID`, `ACTION`, `REMARK`) "
						+ "VALUES "
						+ "('"+srNumber+"', '"+circleCode+"', '"+auditStatus+"', 'SO "+accRej+"')";
				int i = getEm().createNativeQuery(insAudSql).executeUpdate();
				if(i != 0){
					jsonData.setSrNumber(srNumber);
					jsonData.setSpNumber(spNumber);
					jsonData.setSoNumber(soNumber);
					jsonData.setStatus(auditStatus);
					jsonData.setCircle(circleCode);
					jsonData.setTabName(tabName);
				}
			}
			return true;
		}
		return false;
	}*/

}
