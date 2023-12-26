package com.tvi.sharing.dto;

import java.util.List;

public class SharingSrRequestJsonDto {
	private String Ref_Number_TVIPL,SiteId,Operator,UniqueRequestId;
	private GlobalDto Global;
	private SiteDetailDto Site_Detail;
	private ProductDetailDto Product_Detail;
	private List<RadioAntennaDto> Radio_Antenna;
	private List<BscRncCabinetsDto> BSC_RNC_Cabinets;
	private MwDto MW;
	private List<OtherNodeDto> Other_Node;
	private TmaTmbDto TMA_TMB;
	private BtsDto BTS;
	private AdditionalInformationDto Additional_Information;
	private McbDto MCB;
	private OtherEquipmentDto Other_Equipment;
	private FiberDto Fiber;
	private FiberNodeProvisioningDto Fiber_Node_Provisioning;
	private List<FibreNodeDto> Fibre_Node;
	private CircleInformationDto Circle_Information;
	private SacfaDto SACFA;
	private DgDto DG;
	public String getOperator() {
		return Operator;
	}
	public void setOperator(String operator) {
		Operator = operator;
	}
	public String getUniqueRequestId() {
		return UniqueRequestId;
	}
	public void setUniqueRequestId(String uniqueRequestId) {
		UniqueRequestId = uniqueRequestId;
	}
	public GlobalDto getGlobal() {
		return Global;
	}
	public void setGlobal(GlobalDto global) {
		Global = global;
	}
	public SiteDetailDto getSite_Detail() {
		return Site_Detail;
	}
	public void setSite_Detail(SiteDetailDto site_Detail) {
		Site_Detail = site_Detail;
	}
	public ProductDetailDto getProduct_Detail() {
		return Product_Detail;
	}
	public void setProduct_Detail(ProductDetailDto product_Detail) {
		Product_Detail = product_Detail;
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
	public List<OtherNodeDto> getOther_Node() {
		return Other_Node;
	}
	public void setOther_Node(List<OtherNodeDto> other_Node) {
		Other_Node = other_Node;
	}
	public TmaTmbDto getTMA_TMB() {
		return TMA_TMB;
	}
	public void setTMA_TMB(TmaTmbDto tMA_TMB) {
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
	public FiberDto getFiber() {
		return Fiber;
	}
	public void setFiber(FiberDto fiber) {
		Fiber = fiber;
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
	public CircleInformationDto getCircle_Information() {
		return Circle_Information;
	}
	public void setCircle_Information(CircleInformationDto circle_Information) {
		Circle_Information = circle_Information;
	}
	public SacfaDto getSACFA() {
		return SACFA;
	}
	public void setSACFA(SacfaDto sACFA) {
		SACFA = sACFA;
	}
	public DgDto getDG() {
		return DG;
	}
	public void setDG(DgDto dG) {
		DG = dG;
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
