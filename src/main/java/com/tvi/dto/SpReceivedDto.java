package com.tvi.dto;

import java.util.List;

public class SpReceivedDto {
	private SpDetailsDto SPDetails_NN;
	private List<RadioAntennaDto> Radio_Antenna;
	private List<MwAntennaDto> MW;
	private List<BtsCabinetDto> BTS;
	private List<OtherNodeDto> Other_Node;
	private List<BscRncCabinetsDto> BSC_RNC_Cabinets;
	private List<McbDto> MCB;
	private List<FibreNodeDto> Fiber_Node;
	private GlobalDto Global;
	//private OtherEquipmentDto Other_Equipment;
	private List<OtherEquipmentDto> Other_Equipment;
	private StrageticDto Strategic_Conversion;
	private DgDto DG;
	private String Association_AreyouWorkingInAnyBhartiGroup,Association_IfyesmentiontheBhartiUnitName,
	Association_NameOftheEmployee,Association_EmployeeId,Relative_AnyRelativesareWorkingWithBhartiGroup,
	Relative_IfyesmentiontheBhartiUnitName,Relative_NameOftheEmployee,Relative_EmployeeId,
	Relative_LandlordRelationshipwithEmployee,Relative_MobileNumberOfrelativeWithAirtel,Declaration;
	
	public SpDetailsDto getSPDetails_NN() {
		return SPDetails_NN;
	}
	public void setSPDetails_NN(SpDetailsDto sPDetails_NN) {
		SPDetails_NN = sPDetails_NN;
	}
	public List<RadioAntennaDto> getRadio_Antenna() {
		return Radio_Antenna;
	}
	public void setRadio_Antenna(List<RadioAntennaDto> radio_Antenna) {
		Radio_Antenna = radio_Antenna;
	}
	public List<MwAntennaDto> getMW() {
		return MW;
	}
	public void setMW(List<MwAntennaDto> mW) {
		MW = mW;
	}
	public List<BtsCabinetDto> getBTS() {
		return BTS;
	}
	public void setBTS(List<BtsCabinetDto> bTS) {
		BTS = bTS;
	}
	public List<OtherNodeDto> getOther_Node() {
		return Other_Node;
	}
	public void setOther_Node(List<OtherNodeDto> other_Node) {
		Other_Node = other_Node;
	}
	public List<BscRncCabinetsDto> getBSC_RNC_Cabinets() {
		return BSC_RNC_Cabinets;
	}
	public void setBSC_RNC_Cabinets(List<BscRncCabinetsDto> bSC_RNC_Cabinets) {
		BSC_RNC_Cabinets = bSC_RNC_Cabinets;
	}
	public List<McbDto> getMCB() {
		return MCB;
	}
	public void setMCB(List<McbDto> mCB) {
		MCB = mCB;
	}
	public List<FibreNodeDto> getFiber_Node() {
		return Fiber_Node;
	}
	public void setFiber_Node(List<FibreNodeDto> fiber_Node) {
		Fiber_Node = fiber_Node;
	}
	public GlobalDto getGlobal() {
		return Global;
	}
	public void setGlobal(GlobalDto global) {
		Global = global;
	}
	public List<OtherEquipmentDto> getOther_Equipment() {
		return Other_Equipment;
	}
	public void setOther_Equipment(List<OtherEquipmentDto> other_Equipment) {
		Other_Equipment = other_Equipment;
	}
	/*public OtherEquipmentDto getOther_Equipment() {
		return Other_Equipment;
	}
	public void setOther_Equipment(OtherEquipmentDto other_Equipment) {
		Other_Equipment = other_Equipment;
	}*/
	public StrageticDto getStrategic_Conversion() {
		return Strategic_Conversion;
	}
	public void setStrategic_Conversion(StrageticDto strategic_Conversion) {
		Strategic_Conversion = strategic_Conversion;
	}
	public DgDto getDG() {
		return DG;
	}
	public void setDG(DgDto dG) {
		DG = dG;
	}
	public String getAssociation_AreyouWorkingInAnyBhartiGroup() {
		return Association_AreyouWorkingInAnyBhartiGroup;
	}
	public void setAssociation_AreyouWorkingInAnyBhartiGroup(String association_AreyouWorkingInAnyBhartiGroup) {
		Association_AreyouWorkingInAnyBhartiGroup = association_AreyouWorkingInAnyBhartiGroup;
	}
	public String getAssociation_IfyesmentiontheBhartiUnitName() {
		return Association_IfyesmentiontheBhartiUnitName;
	}
	public void setAssociation_IfyesmentiontheBhartiUnitName(String association_IfyesmentiontheBhartiUnitName) {
		Association_IfyesmentiontheBhartiUnitName = association_IfyesmentiontheBhartiUnitName;
	}
	public String getAssociation_NameOftheEmployee() {
		return Association_NameOftheEmployee;
	}
	public void setAssociation_NameOftheEmployee(String association_NameOftheEmployee) {
		Association_NameOftheEmployee = association_NameOftheEmployee;
	}
	public String getAssociation_EmployeeId() {
		return Association_EmployeeId;
	}
	public void setAssociation_EmployeeId(String association_EmployeeId) {
		Association_EmployeeId = association_EmployeeId;
	}
	public String getRelative_AnyRelativesareWorkingWithBhartiGroup() {
		return Relative_AnyRelativesareWorkingWithBhartiGroup;
	}
	public void setRelative_AnyRelativesareWorkingWithBhartiGroup(String relative_AnyRelativesareWorkingWithBhartiGroup) {
		Relative_AnyRelativesareWorkingWithBhartiGroup = relative_AnyRelativesareWorkingWithBhartiGroup;
	}
	public String getRelative_IfyesmentiontheBhartiUnitName() {
		return Relative_IfyesmentiontheBhartiUnitName;
	}
	public void setRelative_IfyesmentiontheBhartiUnitName(String relative_IfyesmentiontheBhartiUnitName) {
		Relative_IfyesmentiontheBhartiUnitName = relative_IfyesmentiontheBhartiUnitName;
	}
	public String getRelative_NameOftheEmployee() {
		return Relative_NameOftheEmployee;
	}
	public void setRelative_NameOftheEmployee(String relative_NameOftheEmployee) {
		Relative_NameOftheEmployee = relative_NameOftheEmployee;
	}
	public String getRelative_EmployeeId() {
		return Relative_EmployeeId;
	}
	public void setRelative_EmployeeId(String relative_EmployeeId) {
		Relative_EmployeeId = relative_EmployeeId;
	}
	public String getRelative_LandlordRelationshipwithEmployee() {
		return Relative_LandlordRelationshipwithEmployee;
	}
	public void setRelative_LandlordRelationshipwithEmployee(String relative_LandlordRelationshipwithEmployee) {
		Relative_LandlordRelationshipwithEmployee = relative_LandlordRelationshipwithEmployee;
	}
	public String getRelative_MobileNumberOfrelativeWithAirtel() {
		return Relative_MobileNumberOfrelativeWithAirtel;
	}
	public void setRelative_MobileNumberOfrelativeWithAirtel(String relative_MobileNumberOfrelativeWithAirtel) {
		Relative_MobileNumberOfrelativeWithAirtel = relative_MobileNumberOfrelativeWithAirtel;
	}
	public String getDeclaration() {
		return Declaration;
	}
	public void setDeclaration(String declaration) {
		Declaration = declaration;
	}
	
	
	 
}
