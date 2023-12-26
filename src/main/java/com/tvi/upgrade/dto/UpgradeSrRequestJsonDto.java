package com.tvi.upgrade.dto;

import java.util.List;

public class UpgradeSrRequestJsonDto {
	private String Ref_Number_TVIPL,SiteId;
	private GlobalDto Global;
	private List<RadioAntennaDto> Radio_Antenna;
	private List<BscRncCabinetsDto> BSC_RNC_Cabinets;
	private MwDto MW;
	private FiberNodeProvisioningDto Fiber_Node_Provisioning;
	private List<FibreNodeDto> Fibre_Node;
	private StrategicConversionDto Strategic_Conversion;
	private List<OtherNodeDto> Other_Node;
	private List<TmaTmbDto> TMA_TMB;
	private BtsDto BTS;
	private AdditionalInformationDto Additional_Information;
	private McbDto MCB;
	private OtherEquipmentDto Other_Equipment;
	private SacfaDto SACFA;
	
	public GlobalDto getGlobal() {
		return Global;
	}
	public void setGlobal(GlobalDto global) {
		Global = global;
	}
	public List<RadioAntennaDto> getRadio_Antenna() {
		return Radio_Antenna;
	}
	public void setRadio_Antenna(List<RadioAntennaDto> radio_Antenna) {
		Radio_Antenna = radio_Antenna;
	}
	public List<BscRncCabinetsDto> getBSC_RNC_Cabinets() {
		return BSC_RNC_Cabinets;
	}
	public void setBSC_RNC_Cabinets(List<BscRncCabinetsDto> bSC_RNC_Cabinets) {
		BSC_RNC_Cabinets = bSC_RNC_Cabinets;
	}
	public MwDto getMW() {
		return MW;
	}
	public void setMW(MwDto mW) {
		MW = mW;
	}
	public FiberNodeProvisioningDto getFiber_Node_Provisioning() {
		return Fiber_Node_Provisioning;
	}
	public void setFiber_Node_Provisioning(FiberNodeProvisioningDto fiber_Node_Provisioning) {
		Fiber_Node_Provisioning = fiber_Node_Provisioning;
	}
	
	public List<FibreNodeDto> getFibre_Node() {
		return Fibre_Node;
	}
	public void setFibre_Node(List<FibreNodeDto> fibre_Node) {
		Fibre_Node = fibre_Node;
	}
	public StrategicConversionDto getStrategic_Conversion() {
		return Strategic_Conversion;
	}
	public void setStrategic_Conversion(StrategicConversionDto strategic_Conversion) {
		Strategic_Conversion = strategic_Conversion;
	}
	public List<OtherNodeDto> getOther_Node() {
		return Other_Node;
	}
	public void setOther_Node(List<OtherNodeDto> other_Node) {
		Other_Node = other_Node;
	}
	
	public List<TmaTmbDto> getTMA_TMB() {
		return TMA_TMB;
	}
	public void setTMA_TMB(List<TmaTmbDto> tMA_TMB) {
		TMA_TMB = tMA_TMB;
	}
	public BtsDto getBTS() {
		return BTS;
	}
	public void setBTS(BtsDto bTS) {
		BTS = bTS;
	}
	public AdditionalInformationDto getAdditional_Information() {
		return Additional_Information;
	}
	public void setAdditional_Information(AdditionalInformationDto additional_Information) {
		Additional_Information = additional_Information;
	}
	public McbDto getMCB() {
		return MCB;
	}
	public void setMCB(McbDto mCB) {
		MCB = mCB;
	}
	public OtherEquipmentDto getOther_Equipment() {
		return Other_Equipment;
	}
	public void setOther_Equipment(OtherEquipmentDto other_Equipment) {
		Other_Equipment = other_Equipment;
	}
	public SacfaDto getSACFA() {
		return SACFA;
	}
	public void setSACFA(SacfaDto sACFA) {
		SACFA = sACFA;
	}
	public String getRef_Number_TVIPL() {
		return Ref_Number_TVIPL;
	}
	public void setRef_Number_TVIPL(String ref_Number_TVIPL) {
		Ref_Number_TVIPL = ref_Number_TVIPL;
	}
	public String getSiteId() {
		return SiteId;
	}
	public void setSiteId(String siteId) {
		SiteId = siteId;
	}
	
}
