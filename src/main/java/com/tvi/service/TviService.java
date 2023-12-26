package com.tvi.service;

import java.sql.Clob;
import java.util.Map;

import com.tvi.constant.Response;
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

public interface TviService {

	Response<LoginResponse> userLogin(LoginRequest jsonData);

	Response<LayoutResponse> getMenuListByRoleName(LoginRequest jsonData);

	Response<Map<String, String>> saveNBSDetails(SaveNBSRequest jsonData);

	Response<SystemParamDTO> getAllCircleName();

	Response<EmployeeResponse> getAllEmployeeList();

	Response<Map<String, String>> activeOrDeactiveEmployee(EmployeeActionRequest jsonData);

	Response<SaveNBSRequest> getNbsDetails(CommonDTO jsonData);
	
	Response<SaveNBSRequest> viewSrDetails(CommonDTO jsonData);

	Response<Map<String, String>> changeSrStatus(CommonDTO jsonData);

	Response<CommonDTO> getTVISiteIdCircleName(CommonDTO jsonData);

	Response<Map<String, String>> changeBulkSrStatus(BulkDTO jsonData);

	Response<SystemParamDTO> getNoOfList();

	Response<Map<String, String>> changeSrStatusAfterEdit(SaveNBSRequest jsonData);

	Response<Map<String, String>> sendMail();

	Response<String> sendOTPtoMobile(CommonDTO jsonData);

	Response<Map<String, String>> changePassword(CommonDTO jsonData);

	void saveResponseInTable(Clob requestJson, Clob responseJson, String methodName);

	Response<Map<String, String>> sendMailMyPhp(String srNumber, String circleName, String operatorName,
			String currentTab);

	Response<Map<String, String>> submitComplain(ComplainDTO jsonData);

	Response<Map<String, String>> sendComplaintMailPhp(String emailFrom, String emailId, String ccEmailId, String bccEmailId, 
			String subject, String msg);

}
