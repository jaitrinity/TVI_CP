package com.tvi.response;

public class ExportDataResponse {
	private	String columnTitle="SR Number,SP Number,SO Number,Site Id,TVI Site Id,Site Name,Latitude,Longitude,Product type,"
			+ "Project Name, Upgrade Type,"
			+ "SR Date,SP Date,SO Date,RFI date,RFI accepted date,Circle,Pending With,Status,Sharing Potential,"
			+ "Sharing Potential Remark";
	private String columnKey="srNumber,spNumber,soNumber,airtelSiteId,tviSiteId,siteName,latitude,longitude,orgTabName,"
			+ "projectName,upgradeType,"
			+ "srRaiseDate,spRaiseDate,soRaiseDate,rfiDate,rfiAcceptedDate,circleName,pendingTo,srStatus,sharingPotential,"
			+ "sharingPotentialRemark";
	
	private String srNumber,spNumber,soNumber,airtelSiteId,tviSiteId,siteName,latitude,longitude,orgTabName,
	projectName,upgradeType,
	srRaiseDate,spRaiseDate,soRaiseDate,rfiDate,rfiAcceptedDate,circleName,pendingTo,srStatus,sharingPotential,
	sharingPotentialRemark;
	
	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getColumnTitle() {
		return columnTitle;
	}

	public void setColumnTitle(String columnTitle) {
		this.columnTitle = columnTitle;
	}

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}

	public String getSpNumber() {
		return spNumber;
	}

	public void setSpNumber(String spNumber) {
		this.spNumber = spNumber;
	}

	public String getSoNumber() {
		return soNumber;
	}

	public void setSoNumber(String soNumber) {
		this.soNumber = soNumber;
	}

	public String getAirtelSiteId() {
		return airtelSiteId;
	}

	public void setAirtelSiteId(String airtelSiteId) {
		this.airtelSiteId = airtelSiteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getOrgTabName() {
		return orgTabName;
	}

	public void setOrgTabName(String orgTabName) {
		this.orgTabName = orgTabName;
	}

	public String getSrRaiseDate() {
		return srRaiseDate;
	}

	public void setSrRaiseDate(String srRaiseDate) {
		this.srRaiseDate = srRaiseDate;
	}

	public String getSpRaiseDate() {
		return spRaiseDate;
	}

	public void setSpRaiseDate(String spRaiseDate) {
		this.spRaiseDate = spRaiseDate;
	}

	public String getSoRaiseDate() {
		return soRaiseDate;
	}

	public void setSoRaiseDate(String soRaiseDate) {
		this.soRaiseDate = soRaiseDate;
	}

	public String getRfiDate() {
		return rfiDate;
	}

	public void setRfiDate(String rfiDate) {
		this.rfiDate = rfiDate;
	}

	public String getRfiAcceptedDate() {
		return rfiAcceptedDate;
	}

	public void setRfiAcceptedDate(String rfiAcceptedDate) {
		this.rfiAcceptedDate = rfiAcceptedDate;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getSrStatus() {
		return srStatus;
	}

	public void setSrStatus(String srStatus) {
		this.srStatus = srStatus;
	}

	public String getTviSiteId() {
		return tviSiteId;
	}

	public void setTviSiteId(String tviSiteId) {
		this.tviSiteId = tviSiteId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUpgradeType() {
		return upgradeType;
	}

	public void setUpgradeType(String upgradeType) {
		this.upgradeType = upgradeType;
	}

	public String getSharingPotential() {
		return sharingPotential;
	}

	public void setSharingPotential(String sharingPotential) {
		this.sharingPotential = sharingPotential;
	}

	public String getSharingPotentialRemark() {
		return sharingPotentialRemark;
	}

	public void setSharingPotentialRemark(String sharingPotentialRemark) {
		this.sharingPotentialRemark = sharingPotentialRemark;
	}

	public String getPendingTo() {
		return pendingTo;
	}

	public void setPendingTo(String pendingTo) {
		this.pendingTo = pendingTo;
	}
}
