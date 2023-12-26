package com.tvi.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response<T> {
	private List<T> wrappedList = new ArrayList<T>();
	private Map<String, String> errorsMap = new HashMap<String, String>();
	private String responseCode;
	private String responseDesc;
	private String pagination;
	private String srNumber;
	private String uniqueRequestId;

	public Response() {
	}

	public Response(List<T> wrappedList) {
		this.wrappedList = wrappedList;
	}

	public Response(List<T> wrappedList, Map<String, String> errorsMap) {
		this.wrappedList = wrappedList;
		this.errorsMap = errorsMap;
	}

	public Response(List<T> wrappedList, Map<String, String> errorsMap, String responseCode, String responseDesc, 
			String pagination, String srNumber, String uniqueRequestId) {
		this.wrappedList = wrappedList;
		this.errorsMap = errorsMap;
		this.responseCode = responseCode;
		this.responseDesc = responseDesc;
		this.pagination = pagination;
		this.srNumber = srNumber;
		this.uniqueRequestId = uniqueRequestId;
	}

	public List<T> getWrappedList() {
		return this.wrappedList;
	}

	public void setWrappedList(List<T> wrappedList) {
		this.wrappedList = wrappedList;
	}

	public Map<String, String> getErrorsMap() {
		return this.errorsMap;
	}

	public void setErrorsMap(Map<String, String> errorsMap) {
		this.errorsMap = errorsMap;
	}

	public String getResponseCode() {
		return this.responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDesc() {
		return this.responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}
	
	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}

	public String getUniqueRequestId() {
		return uniqueRequestId;
	}

	public void setUniqueRequestId(String uniqueRequestId) {
		this.uniqueRequestId = uniqueRequestId;
	}
}
