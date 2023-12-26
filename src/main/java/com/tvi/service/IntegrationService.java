package com.tvi.service;

import java.sql.Clob;
import java.util.List;
import java.util.Map;

import com.tvi.constant.Response;
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
import com.tvi.sharing.dto.SharingSrRequestJsonDto;
import com.tvi.upgrade.dto.UpgradeSrRequestJsonDto;

public interface IntegrationService {
	
	void saveResponseInTable(Clob requestJson, Clob responseJson, String methodName);

	Response<SystemParamDTO> getNoOfList();
	
	Response<SrResponse> raiseSr(SrRequestJsonDto srDto);
	
	Response<SrResponse> raiseSharingSr(SharingSrRequestJsonDto srDto);
	
	Response<SrResponse> raiseUpgradeSr(UpgradeSrRequestJsonDto srDto);
	
	SrSoWindrawalResponse srSoWithdrawal(SrSoWithdrawalDto jsonData);
	
	RfaiAcceptRejectResponse rfaiAcceptReject(RfaiAcceptReject jsonData);
	
	RejectSpResponse rejectSp(RejectSpDto jsonData);
	
	SoSubmitResponse soSubmit(SoSubmitDto jsonData);
	
	Response<SaveNBSRequest> getAirtelDetails(AirtelCommonRequest jsonData);
	
	Response<AirtelCommonResponse> viewAirtelSrDetails(AirtelCommonRequest jsonData);
	
	Response<String> changeAirtelSrStatus(ChangeAirtelSrStatusRequest jsonData);
	
	List<SpReceivedResponse> spLapsed();

	Response<Map<String, String>> changeAirtelBulkSrStatus(BulkDTO jsonData);

	Response<ExportDataResponse> exportData(ExportDto jsonData);
	
	SpReceivedResponse spReceived_test(SpReceivedTest jsonData);
	
	SpReceivedResponse spReceivedSharing_test(SpReceivedTest jsonData);

	SpReceivedResponse spReceivedUpgrade_test(SpReceivedTest jsonData);

}
