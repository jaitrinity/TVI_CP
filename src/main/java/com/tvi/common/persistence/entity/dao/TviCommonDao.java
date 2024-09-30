package com.tvi.common.persistence.entity.dao;

import java.util.List;

import com.tvi.dto.CommonDTO;
import com.tvi.dto.ComplainDTO;
import com.tvi.dto.RejectSpDto;
import com.tvi.dto.RfaiAcceptReject;
import com.tvi.dto.SiteInfoDto;
import com.tvi.dto.SoSubmitDto;
import com.tvi.dto.SrRequestJsonDto;
import com.tvi.dto.SrSoWithdrawalDto;
import com.tvi.entity.EmployeeMasterEntity;
import com.tvi.entity.NbsMasterDetEntity;
import com.tvi.entity.NbsMasterHdrEntity;
import com.tvi.request.EmployeeActionRequest;
import com.tvi.request.LoginRequest;
import com.tvi.request.SaveNBSRequest;
import com.tvi.sharing.dto.SharingSrRequestJsonDto;
import com.tvi.upgrade.dto.UpgradeSrRequestJsonDto;

public interface TviCommonDao {

	List<Object[]> userLogin(LoginRequest jsonData);

	List<Object[]> getMenuListByRoleName(LoginRequest jsonData);

	List<Object[]> getSubmenuByMenuId(String menuId, String userRole, String operator);

	void insertHdrAndDet(SaveNBSRequest jsonData);
	
	void insertHdrAndDetOfSiteUpgrade(SaveNBSRequest jsonData);

	List<String> getAllCircleName();

	List<EmployeeMasterEntity> getAllEmployeeList();

	EmployeeMasterEntity getEmployeeDetails(EmployeeActionRequest jsonData);

	Object saveOrUpdateEmpData(EmployeeMasterEntity emEntity);
	
	public Object saveOrUpdateEntityData(Object entity);

	List<NbsMasterHdrEntity> getNbsHdrByCircleName(CommonDTO jsonData);

	List<NbsMasterDetEntity> getNbsDetailsBySrNumber(String srNumber);

	NbsMasterHdrEntity getNbsHdrBySrNumber(String srNumber);

	List<Object[]> getNbsAuditBySrNumber(String srNumber);

	void insertHdrAndDetOfODSCAnchor(SaveNBSRequest jsonData);

	List<Object[]> getTVISiteIdCircleName(String sql);

	void insertHdrAndDetODSCSharing(SaveNBSRequest jsonData);

	int updateBulkdataValue(String updateTable);

	List<Object[]> getNoOfList(String sql);

	List<Object[]> getAllNbsStatus();

	List<String> sendOTPtoMobile(CommonDTO jsonData);

	int changePassword(CommonDTO jsonData);

	void insertActiveDataInDet(CommonDTO jsonData);

	List<Object[]> getNextLevelRole(String sql);

	List<String> getCircleOperationHeadOnCircle(String sql);

	List<Object[]> getAllTableData(String sql);

	void insertHdrAndDetOfIwan(SaveNBSRequest jsonData);
	
	void insertHdrAndDetOfMcu(SaveNBSRequest jsonData);

	void insertHdrAndDetOfFibreTermination(SaveNBSRequest jsonData);

	void insertHdrAndDetOfTclRewin(SaveNBSRequest jsonData);

	void insertHdrAndDetOfHexOlt(SaveNBSRequest jsonData);

	boolean isExistSR(String sql);

	void submitComplain(ComplainDTO jsonData);

	void insertHdrAndDetSmartSplit(SaveNBSRequest jsonData);

	void editDataForHPSC(CommonDTO jsonData);
	
	void editAnyDetData(CommonDTO jsonData);

	void insertHdrAndDetTCU(SaveNBSRequest jsonData);
	
	//
	
	public void raiseSr(String srNumber, String tabName, SrRequestJsonDto srRequestJson);
	
	public void raiseSharingSr(String srNumber, SiteInfoDto siteObj, SharingSrRequestJsonDto srRequestJson);
	
	public void raiseUpgradeSr(String srNumber, SiteInfoDto siteObj, UpgradeSrRequestJsonDto srRequestJson);
	
	public boolean srSoWithdrawal(SrSoWithdrawalDto jsonData);
	
	public boolean rfaiAcceptReject(RfaiAcceptReject jsonData);
	
	public boolean rejectSp(RejectSpDto jsonData);
	
	public boolean soSubmit(SoSubmitDto jsonData);

	void insertHdrAndDetAF(SaveNBSRequest jsonData);

}
