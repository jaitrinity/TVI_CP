package com.tvi.service;

import java.sql.Clob;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.tvi.constant.Response;
import com.tvi.dao.TviDao;
import com.tvi.dto.BulkDTO;
import com.tvi.dto.CommonDTO;
import com.tvi.dto.ComplainDTO;
import com.tvi.dto.SystemParamDTO;
import com.tvi.request.EmployeeActionRequest;
import com.tvi.request.LoginRequest;
import com.tvi.request.SaveNBSRequest;
import com.tvi.response.EmployeeResponse;
import com.tvi.response.LayoutResponse;
import com.tvi.response.LoginResponse;

public class TviServiceImpl implements TviService {
	@Autowired
	TviDao tviDao;

	public Response<LoginResponse> userLogin(LoginRequest jsonData) {
		return tviDao.userLogin(jsonData);
	}

	public Response<LayoutResponse> getMenuListByRoleName(LoginRequest jsonData) {
		return tviDao.getMenuListByRoleName(jsonData);
	}

	public Response<Map<String, String>> saveNBSDetails(SaveNBSRequest jsonData) {
		return tviDao.saveNBSDetails(jsonData);
	}

	@Override
	public Response<SystemParamDTO> getAllCircleName() {
		return tviDao.getAllCircleName();
	}

	@Override
	public Response<EmployeeResponse> getAllEmployeeList() {
		return tviDao.getAllEmployeeList();
	}

	@Override
	public Response<Map<String, String>> activeOrDeactiveEmployee(EmployeeActionRequest jsonData) {
		return tviDao.activeOrDeactiveEmployee(jsonData);
	}

	@Override
	public Response<SaveNBSRequest> getNbsDetails(CommonDTO jsonData) {
		return tviDao.getNbsDetails(jsonData);
	}
	
	@Override
	public Response<SaveNBSRequest> viewSrDetails(CommonDTO jsonData) {
		return tviDao.viewSrDetails(jsonData);
	}

	@Override
	public Response<Map<String, String>> changeSrStatus(CommonDTO jsonData) {
		return tviDao.changeSrStatus(jsonData);
	}

	@Override
	public Response<CommonDTO> getTVISiteIdCircleName(CommonDTO jsonData) {
		return tviDao.getTVISiteIdCircleName(jsonData);
	}

	@Override
	public Response<Map<String, String>> changeBulkSrStatus(BulkDTO jsonData) {
		return tviDao.changeBulkSrStatus(jsonData);
	}

	@Override
	public Response<SystemParamDTO> getNoOfList() {
		return tviDao.getNoOfList();
	}

	@Override
	public Response<Map<String, String>> changeSrStatusAfterEdit(SaveNBSRequest jsonData) {
		return tviDao.changeSrStatusAfterEdit(jsonData);
	}

	@Override
	public Response<Map<String, String>> sendMail() {
		return tviDao.sendMail();
	}

	@Override
	public Response<String> sendOTPtoMobile(CommonDTO jsonData) {
		return tviDao.sendOTPtoMobile(jsonData);
	}

	@Override
	public Response<Map<String, String>> changePassword(CommonDTO jsonData) {
		return tviDao.changePassword(jsonData);
	}

	@Override
	public void saveResponseInTable(Clob requestJson, Clob responseJson, String methodName) {
		tviDao.saveResponseInTable(requestJson,responseJson,methodName);
		
	}

	@Override
	public Response<Map<String, String>> sendMailMyPhp(String srNumber, String circleName, String operatorName,
			String currentTab) {
		return tviDao.sendMailMyPhp(srNumber,circleName,operatorName,currentTab);
	}

	@Override
	public Response<Map<String, String>> submitComplain(ComplainDTO jsonData) {
		return tviDao.submitComplain(jsonData);
	}

	@Override
	public Response<Map<String, String>> sendComplaintMailPhp(String emailFrom, String emailId, String ccEmailId, String bccEmailId, 
			String subject, String msg) {
		return tviDao.sendComplaintMailPhp(emailFrom, emailId, ccEmailId, bccEmailId, 
				subject, msg);
	}

}
