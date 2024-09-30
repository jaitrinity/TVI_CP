package com.tvi.controller;

import java.sql.Clob;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialClob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.tvi.constant.CommonFunction;
import com.tvi.constant.Response;
import com.tvi.constant.ReturnsCode;
import com.tvi.dto.AttachmentDto;
import com.tvi.dto.BulkDTO;
import com.tvi.dto.ExportDto;
import com.tvi.dto.RejectSpDto;
import com.tvi.dto.RfaiAcceptReject;
import com.tvi.dto.SoSubmitDto;
import com.tvi.dto.SrRequestJsonDto;
import com.tvi.dto.SrSoWithdrawalDto;
import com.tvi.dto.SystemParamDTO;
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
import com.tvi.service.IntegrationService;
import com.tvi.sharing.dto.SharingSrRequestJsonDto;
import com.tvi.upgrade.dto.UpgradeSrRequestJsonDto;

@CrossOrigin
@RestController
@RequestMapping(value = "/tvi")
public class IntegrationController {

	@Autowired
	IntegrationService intService;
	
	private void saveResponseInTable(Object request,Object response, String methodName){
		Clob requestJson = null;
		Clob responseJson = null;
		try {
			if(request != null){
				requestJson = new SerialClob(CommonFunction.printResponseJson(request).toCharArray());
			}
			responseJson = new SerialClob(CommonFunction.printResponseJson(response).toCharArray());
			intService.saveResponseInTable(requestJson,responseJson,methodName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value ="/getNoOfListTest",method = RequestMethod.GET)
	public Response<SystemParamDTO> getNoOfList(){
		return intService.getNoOfList();
	}
	
	@RequestMapping(value ="/raiseSr",method = RequestMethod.POST)
	public Response<SrResponse> raiseSr(@RequestBody String requestJson){
		Response<SrResponse> response = null;
		try {
			Gson gsonObj = new Gson();
			SrRequestJsonDto srDto = gsonObj.fromJson(requestJson, SrRequestJsonDto.class);
			response = intService.raiseSr(srDto);
			
			String json = gsonObj.toJson(srDto);
			saveResponseInTable(json, response, "raiseSr");
		} catch (Exception e) {
			response = new Response<SrResponse>();
			response.setResponseCode(ReturnsCode.BAD_REQUEST);
			response.setResponseDesc(e.getMessage());
			
			saveResponseInTable(requestJson, response, "raiseSr");
		}
		return response;
	}
	
	@RequestMapping(value ="/raiseSharingSr",method = RequestMethod.POST)
	public Response<SrResponse> raiseSharingSr(@RequestBody String requestJson){
		Response<SrResponse> response = null;
		try {
			Gson gsonObj = new Gson();
			SharingSrRequestJsonDto srDto = gsonObj.fromJson(requestJson, SharingSrRequestJsonDto.class);
			response = intService.raiseSharingSr(srDto);
			
			String json = gsonObj.toJson(srDto);
			saveResponseInTable(json, response, "raiseSharingSr");
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response<SrResponse>();
			response.setResponseCode(ReturnsCode.BAD_REQUEST);
			response.setResponseDesc(e.getMessage());
			
			saveResponseInTable(requestJson, response, "raiseSharingSr");
		}
		return response;
	}
	
	@RequestMapping(value ="/raiseUpgradeSr",method = RequestMethod.POST)
	public Response<SrResponse> raiseUpgradeSr(@RequestBody String requestJson){
		Response<SrResponse> response = null;
		try {
			Gson gsonObj = new Gson();
			UpgradeSrRequestJsonDto srDto = gsonObj.fromJson(requestJson, UpgradeSrRequestJsonDto.class);
			response = intService.raiseUpgradeSr(srDto);
			
			String json = gsonObj.toJson(srDto);
			saveResponseInTable(json, response, "raiseUpgradeSr");
		} catch (Exception e) {
			response = new Response<SrResponse>();
			response.setResponseCode(ReturnsCode.BAD_REQUEST);
			response.setResponseDesc(e.getMessage());
			
			saveResponseInTable(requestJson, response, "raiseUpgradeSr");
		}
		return response;
	}
	
	@RequestMapping(value ="/srSoWithdrawal",method = RequestMethod.POST)
	public SrSoWindrawalResponse srSoWithdrawal(@RequestBody SrSoWithdrawalDto jsonData){
		SrSoWindrawalResponse response = null;
		try {
			response = intService.srSoWithdrawal(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gsonObj = new Gson();
		String json = gsonObj.toJson(jsonData);
		saveResponseInTable(json, response, "srSoWithdrawal - "+jsonData.getSR_No()+" - "+jsonData.getStatus());
		return response;
	}
	
	@RequestMapping(value ="/rfaiAcceptReject",method = RequestMethod.POST)
	public RfaiAcceptRejectResponse rfaiAcceptReject(@RequestBody RfaiAcceptReject jsonData){
		RfaiAcceptRejectResponse response = null;
		try {
			response = intService.rfaiAcceptReject(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gsonObj = new Gson();
		String json = gsonObj.toJson(jsonData);
		saveResponseInTable(json, response, "rfaiAcceptReject - "+jsonData.getSR_No()+" - "+jsonData.getStatus());
		return response;
	}
	
	@RequestMapping(value ="/rejectSp",method = RequestMethod.POST)
	public RejectSpResponse rejectSp(@RequestBody RejectSpDto jsonData){
		RejectSpResponse response = null;
		try {
			response = intService.rejectSp(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gsonObj = new Gson();
		String json = gsonObj.toJson(jsonData);
		saveResponseInTable(json, response, "rejectSp - "+jsonData.getSR_No()+" - "+jsonData.getStatus());
		return response;
	}
	
	@RequestMapping(value ="/soSubmit",method = RequestMethod.POST)
	public SoSubmitResponse soSubmit(@RequestBody SoSubmitDto jsonData){
		SoSubmitResponse response = null;
		try {
			response = intService.soSubmit(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gsonObj = new Gson();
		String json = gsonObj.toJson(jsonData);
		saveResponseInTable(json, response, "soSubmit - "+jsonData.getSP_Ref_No()+" - "+jsonData.getStatus());
		return response;
	}
	
	@RequestMapping(value ="/getAirtelDetails",method = RequestMethod.POST)
	public Response<SaveNBSRequest> getAirtelDetails(@RequestBody AirtelCommonRequest jsonData){
		return intService.getAirtelDetails(jsonData);
	}
	
	@RequestMapping(value ="/viewAirtelSrDetails",method = RequestMethod.POST)
	public Response<AirtelCommonResponse> viewAirtelSrDetails(@RequestBody AirtelCommonRequest jsonData){
		return intService.viewAirtelSrDetails(jsonData);
	}
	
	@RequestMapping(value ="/changeAirtelSrStatus",method = RequestMethod.POST)
	public Response<String> changeAirtelSrStatus(@RequestBody ChangeAirtelSrStatusRequest jsonData){
		Response<String> response = null;
		try {
			List<AttachmentDto> attachedList = jsonData.getAttachedList();
			if(attachedList.size() !=0){
				for(AttachmentDto obj : attachedList){
					String imageStr = obj.getImageStr();
					String attName = obj.getAttName();
					if(!(imageStr.equalsIgnoreCase("") || attName.equalsIgnoreCase("Seaf Portal URL"))){
						imageStr = CommonFunction.convertBase64ToAllType(jsonData.getSrNumber(), imageStr, attName+"_"+jsonData.getAfterStatus());
						obj.setImageStr(imageStr);
					}
				}
			}
			response = intService.changeAirtelSrStatus(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(jsonData, response, "changeAirtelSrStatus - "+jsonData.getSrNumber()+" - "+jsonData.getAfterStatus());
		return response;
	}
	
	@RequestMapping(value ="/spLapsed",method = RequestMethod.GET)
	public List<SpReceivedResponse> spLapsed(){
		List<SpReceivedResponse> response = null;
		try {
			response = intService.spLapsed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(null, response, "spLapsed");
		return response;
	}
	
	@RequestMapping(value ="/changeAirtelBulkSrStatus",method = RequestMethod.POST)
	public Response<Map<String, String>> changeAirtelBulkSrStatus(@RequestBody BulkDTO jsonData){
		Response<Map<String, String>> response = new Response<>();
		try {
			response = intService.changeAirtelBulkSrStatus(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(jsonData, response, "changeAirtelBulkSrStatus");
		return response;
	}
	
	@RequestMapping(value ="/exportData",method = RequestMethod.POST)
	public Response<ExportDataResponse> exportData(@RequestBody ExportDto jsonData){
		Response<ExportDataResponse> response = new Response<>();
		try {
			response = intService.exportData(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(jsonData, response, "exportData");
		return response;
	}
	
	@RequestMapping(value ="/spReceived_test",method = RequestMethod.POST)
	public SpReceivedResponse spReceived_test(@RequestBody SpReceivedTest jsonData){
		SpReceivedResponse response = null;
		try {
			response = intService.spReceived_test(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(jsonData, response, "spReceived_test");
		return response;
	}
	
	@RequestMapping(value ="/spReceivedSharing_test",method = RequestMethod.POST)
	public SpReceivedResponse spReceivedSharing_test(@RequestBody SpReceivedTest jsonData){
		SpReceivedResponse response = null;
		try {
			response = intService.spReceivedSharing_test(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(jsonData, response, "spReceivedSharing_test");
		return response;
	}
	
	@RequestMapping(value ="/spReceivedUpgrade_test",method = RequestMethod.POST)
	public SpReceivedResponse spReceivedUpgrade_test(@RequestBody SpReceivedTest jsonData){
		SpReceivedResponse response = null;
		try {
			response = intService.spReceivedUpgrade_test(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveResponseInTable(jsonData, response, "spReceivedUpgrade_test");
		return response;
	}
}
