package com.tvi.controller;

import java.sql.Clob;
import java.util.Date;
import java.util.Map;

import javax.sql.rowset.serial.SerialClob;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tvi.constant.CommonFunction;
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
import com.tvi.service.TviService;

@CrossOrigin
@RestController
@RequestMapping(value = "/tvi")
public class TVIController {
	@Autowired
	TviService tviService;
	
	private void saveResponseInTable(Object request,Object response, String methodName){
		Clob requestJson = null;
		Clob responseJson = null;
		try {
			if(request != null){
				requestJson = new SerialClob(CommonFunction.printResponseJson(request).toCharArray());
			}
			responseJson = new SerialClob(CommonFunction.printResponseJson(response).toCharArray());
			tviService.saveResponseInTable(requestJson,responseJson,methodName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value ="/getNoOfList",method = RequestMethod.GET)
	public Response<SystemParamDTO> getNoOfList(){
		return tviService.getNoOfList();
	}
	
	@RequestMapping(value ="/authenticate",method = RequestMethod.POST)
	public Response<LoginResponse> authenticate(@RequestBody LoginRequest jsonData){
		Response<LoginResponse> response = tviService.userLogin(jsonData);
		return response;
	}
	
	@RequestMapping(value ="/getMenuListByRoleName",method = RequestMethod.POST)
	public Response<LayoutResponse> getMenuListByRoleName(@RequestBody LoginRequest jsonData){
		return tviService.getMenuListByRoleName(jsonData);
	}
	
	@RequestMapping(value ="/saveNBSDetails",method = RequestMethod.POST)
	public Response<Map<String, String>> saveNBSDetails(@RequestBody SaveNBSRequest jsonData){
		Response<Map<String, String>> response = null;
		try {
			response = tviService.saveNBSDetails(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(jsonData, response, "saveNBSDetails - "+jsonData.getSrNumber());
		return response;
	}
	
	@RequestMapping(value ="/getAllCircleName",method = RequestMethod.GET)
	public Response<SystemParamDTO> getAllCircleName(){
		return tviService.getAllCircleName();
	}
	
	@RequestMapping(value ="/getAllEmployeeList",method = RequestMethod.GET)
	public Response<EmployeeResponse> getAllEmployeeList(){
		return tviService.getAllEmployeeList();
	}
	
	@RequestMapping(value ="/activeOrDeactiveEmployee",method = RequestMethod.POST)
	public Response<Map<String, String>> activeOrDeactiveEmployee(@RequestBody EmployeeActionRequest jsonData){
		return tviService.activeOrDeactiveEmployee(jsonData);
	}
		
	@RequestMapping(value ="/getNbsDetails",method = RequestMethod.POST)
	public Response<SaveNBSRequest> getNbsDetails(@RequestBody CommonDTO jsonData){
		return tviService.getNbsDetails(jsonData);
	}
	
	@RequestMapping(value ="/viewSrDetails",method = RequestMethod.POST)
	public Response<SaveNBSRequest> viewSrDetails(@RequestBody CommonDTO jsonData){
		return tviService.viewSrDetails(jsonData);
	}
	
	@RequestMapping(value ="/changeSrStatus",method = RequestMethod.POST)
	public Response<Map<String, String>> changeSrStatus(@RequestBody CommonDTO jsonData){
		Response<Map<String, String>> response = null;
		try {
			if(jsonData.getNcsoAttachedStr()!= null && !jsonData.getNcsoAttachedStr().equalsIgnoreCase("")){
				String u = "";
				if(jsonData.isNcsoLink())
					u = jsonData.getNcsoAttachedStr();
				else
					u = CommonFunction.convertBase64ToAllType(jsonData.getSrNumber(), jsonData.getNcsoAttachedStr(), "NCSO");
				jsonData.setNcsoAttachedStr(u);
			}
			if(jsonData.getGoogleSnapshotStr() != null && !jsonData.getGoogleSnapshotStr().equalsIgnoreCase("")){
				String u = "";
				if(jsonData.isGoogleSnapshotLink())
					u = jsonData.getGoogleSnapshotStr();
				else
					u = CommonFunction.convertBase64ToAllType(jsonData.getSrNumber(), jsonData.getGoogleSnapshotStr(), "GOOGLE_SNAPSHOT");
				jsonData.setGoogleSnapshotStr(u);
			}
			if(jsonData.getNfaAttachedStr() != null && !jsonData.getNfaAttachedStr().equalsIgnoreCase("")){
				String u = CommonFunction.convertBase64ToAllType(jsonData.getSrNumber(), jsonData.getNfaAttachedStr(), "NFA");
				jsonData.setNfaAttachedStr(u);
			}
			if(jsonData.getSeafAttachedStr()!= null && !jsonData.getSeafAttachedStr().equalsIgnoreCase("")){
				String u = "";
				if(jsonData.isSeafLink())
					u = jsonData.getSeafAttachedStr();
				else
					u = CommonFunction.convertBase64ToAllType(jsonData.getSrNumber(), jsonData.getSeafAttachedStr(), "SEAF");
				jsonData.setSeafAttachedStr(u);
			}
			if(jsonData.getBoqAttachedStr()!= null && !jsonData.getBoqAttachedStr().equalsIgnoreCase("")){
				String u = "";
				if(jsonData.isBoqLink())
					u = jsonData.getBoqAttachedStr();
				else
					u = CommonFunction.convertBase64ToAllType(jsonData.getSrNumber(), jsonData.getBoqAttachedStr(), "BOQ");
				jsonData.setBoqAttachedStr(u);
			}
			if(jsonData.getAnyAttachedStr()!= null && !jsonData.getAnyAttachedStr().equalsIgnoreCase("")){
				String u = "";
				if(jsonData.isAnyLink())
					u = jsonData.getAnyAttachedStr();
				else
					u = CommonFunction.convertBase64ToAllType(jsonData.getSrNumber(), jsonData.getAnyAttachedStr(), "ANY_"+jsonData.getStatus());
				jsonData.setAnyAttachedStr(u);
			}
			if(jsonData.getAgreementStr()!= null && !jsonData.getAgreementStr().equalsIgnoreCase("")){
				String u = "";
				if(jsonData.isAggrementLink())
					u = jsonData.getAgreementStr();
				else
					u = CommonFunction.convertBase64ToAllType(jsonData.getSrNumber(), jsonData.getAgreementStr(), "Agreement");
				jsonData.setAgreementStr(u);
			}
			if(jsonData.getRfiAttachedStr()!= null && !jsonData.getRfiAttachedStr().equalsIgnoreCase("")){
				String u = "";
				if(jsonData.isRfiDocLink())
					u = jsonData.getRfiAttachedStr();
				else
					u = CommonFunction.convertBase64ToAllType(jsonData.getSrNumber(), jsonData.getRfiAttachedStr(), "RFI_Doc");
				jsonData.setRfiAttachedStr(u);
			}
			if(jsonData.getAtAttachedStr()!= null && !jsonData.getAtAttachedStr().equalsIgnoreCase("")){
				String u = "";
				if(jsonData.isAtDocLink())
					u = jsonData.getAtAttachedStr();
				else
					u = CommonFunction.convertBase64ToAllType(jsonData.getSrNumber(), jsonData.getAtAttachedStr(), "AT_Doc");
				jsonData.setAtAttachedStr(u);
			}
			if(jsonData.getDdrAttachedStr()!= null && !jsonData.getDdrAttachedStr().equalsIgnoreCase("")){
				String u = "";
				if(jsonData.isDdrLink())
					u = jsonData.getDdrAttachedStr();
				else
					u = CommonFunction.convertBase64ToAllType(jsonData.getSrNumber(), jsonData.getDdrAttachedStr(), "DDR");
				jsonData.setDdrAttachedStr(u);
			}
			if(jsonData.getSurveyReportAttachedStr()!= null && !jsonData.getSurveyReportAttachedStr().equalsIgnoreCase("")){
				String u = "";
				if(jsonData.isSurveyReportLink())
					u = jsonData.getSurveyReportAttachedStr();
				else
					u = CommonFunction.convertBase64ToAllType(jsonData.getSrNumber(), jsonData.getSurveyReportAttachedStr(), "Survey_Report");
				jsonData.setSurveyReportAttachedStr(u);;
			}
			
			response = tviService.changeSrStatus(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(jsonData, response, "changeSrStatus - "+jsonData.getSrNumber()+" - "+jsonData.getStatus());
		return response;
	}
	
	@RequestMapping(value ="/getTVISiteIdCircleName",method = RequestMethod.POST)
	public Response<CommonDTO> getTVISiteIdCircleName(@RequestBody CommonDTO jsonData){
		return tviService.getTVISiteIdCircleName(jsonData);
	}
	
	@RequestMapping(value ="/changeBulkSrStatus",method = RequestMethod.POST)
	public Response<Map<String, String>> changeBulkSrStatus(@RequestBody BulkDTO jsonData){
		Response<Map<String, String>> response = new Response<>();
		try {
			response = tviService.changeBulkSrStatus(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(jsonData, response, "changeBulkSrStatus");
		return response;
	}
	
	@RequestMapping(value ="/sendMail",method = RequestMethod.GET)
	public Response<Map<String, String>> sendMail(){
		return tviService.sendMail();
	}
	
	@RequestMapping(value ="/sendOTPtoMobile",method = RequestMethod.POST)
	public Response<String> sendOTPtoMobile(@RequestBody CommonDTO jsonData){
		Response<String> response = null;
		try {
			response = tviService.sendOTPtoMobile(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//saveResponseInTable(jsonData, response, "sendOTPtoMobile");
		return response;
	}
	
	@RequestMapping(value ="/changePassword",method = RequestMethod.POST)
	public Response<Map<String, String>> changePassword(@RequestBody CommonDTO jsonData){
		Response<Map<String, String>> response = null;
		try {
			response = tviService.changePassword(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//saveResponseInTable(jsonData, response, "changePassword");
		return response;
	}
	
	@RequestMapping(value ="/sendMailMyPhp",method = RequestMethod.GET)
	public Response<Map<String, String>> sendMailMyPhp(@QueryParam("srNumber") String srNumber, 
			@QueryParam("circleName") String circleName, 
			@QueryParam("operatorName") String operatorName, 
			@QueryParam("currentTab") String currentTab ){
		Response<Map<String, String>> response = null;
		try {
			response = tviService.sendMailMyPhp(srNumber,circleName,operatorName,currentTab);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(null, response, "sendMailMyPhp");
		return response;
	}
	
	@RequestMapping(value ="/submitComplain",method = RequestMethod.POST)
	public Response<Map<String, String>> submitComplain(@RequestBody ComplainDTO jsonData){
		Response<Map<String, String>> response = null;
		try {
			if(jsonData.getImgStr()!= null && !jsonData.getImgStr().equalsIgnoreCase("")){
				Date d = new Date();
				String u = CommonFunction.convertBase64ToAllType(""+d.getTime(), jsonData.getImgStr(), "Complain");
				jsonData.setImgStr(u);
			}
			response = tviService.submitComplain(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(jsonData, response, "submitComplain");
		return response;
	}
	
	@RequestMapping(value ="/sendCompaintMailPhp",method = RequestMethod.GET)
	public Response<Map<String, String>> sendCompaintMailPhp(
			@QueryParam("emailFrom") String emailFrom,
			@QueryParam("emailId") String emailId,
			@QueryParam("ccEmailId") String ccEmailId,
			@QueryParam("bccEmailId") String bccEmailId,
			@QueryParam("subject") String subject,
			@QueryParam("msg") String msg){
		Response<Map<String, String>> response = null;
		try {
			response = tviService.sendComplaintMailPhp(emailFrom, emailId, ccEmailId, bccEmailId, subject, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(null, response, "sendCompaintMailPhp");
		return response;
	}

}
