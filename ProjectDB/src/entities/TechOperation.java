package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import entities.TimeNorm;
import entities.Equipment;
import entities.MechDegree;
import entities.WorkCondition;

@Entity
@Table(name = "Tech_Operation")
@NamedQuery(name = "AllTechOperations", query = "select to from TechOperation to")
public class TechOperation extends TechObject implements Serializable {

	private static final long serialVersionUID = 1L;

	public TechOperation() {
	}

	public TechOperation(User creator, TechObject assocObj, TechObject parentObj, Date createTime, Date editTime,
			int operNumber, int nspd, float prepareTime, float unitTime, float mainTime, float assistTime, String lcl,
			String programSign, int shopNumber, int areaNumber, int placeNumber, float unitTimeCoef, int prodBatchValue,
			int operNormUnit, int perfNumber, int workCategory, String appDocSign) {
		super(creator, assocObj, parentObj, createTime, editTime);
		this.operNumber = operNumber;
		this.nspd = nspd;
		this.prepareTime = prepareTime;
		this.unitTime = unitTime;
		this.mainTime = mainTime;
		this.assistTime = assistTime;
		this.lcl = lcl;
		this.programSign = programSign;
		this.shopNumber = shopNumber;
		this.areaNumber = areaNumber;
		this.placeNumber = placeNumber;
		this.unitTimeCoef = unitTimeCoef;
		this.prodBatchValue = prodBatchValue;
		this.operNormUnit = operNormUnit;
		this.perfNumber = perfNumber;
		this.workCategory = workCategory;
		this.appDocSign = appDocSign;
		this.techTransits = new ArrayList<TechTransit>();
		this.tools = new ArrayList<Tool>();
	}

	@Column(name = "OPER_NUMBER")
	private int operNumber;

	@Column(name = "NSPD")
	private int nspd;

	@Column(name = "PREPARE_TIME")
	private float prepareTime;

	@Column(name = "UNIT_TIME")
	private float unitTime;

	@Column(name = "MAIN_TIME")
	private float mainTime;

	@Column(name = "ASSIST_TIME")
	private float assistTime;

	@Column(name = "LCL")
	private String lcl;

	@Column(name = "PROGRAM_SIGN")
	private String programSign;

	@Column(name = "SHOP_NUMBER")
	private int shopNumber;

	@Column(name = "AREA_NUMBER")
	private int areaNumber;

	@Column(name = "PLACE_NUMBER")
	private int placeNumber;

	@Column(name = "UNIT_TIME_COEF")
	private float unitTimeCoef;

	@Column(name = "PROD_BATCH_VALUE")
	private int prodBatchValue;

	@Column(name = "OPER_NORM_UNIT")
	private int operNormUnit;

	@Column(name = "PERF_NUMBER")
	private int perfNumber;

	@Column(name = "WORK_CATEGORY")
	private int workCategory;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "TYPIC_OPERATION_CODE")
	private TypicOperation typicOperation;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "TIME_NORM")
	private TimeNorm timeNorm;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "EQUIPMENT_CODE")
	private Equipment equipment;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "MECH_DEGREE")
	private MechDegree mechDegree;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "WORK_CONDITION")
	private WorkCondition workCondition;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PROFESSION")
	private Profession profession;

	@OneToMany(mappedBy = "techOperation", cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderColumn(name = "TECH_TRANSIT_INDEX")
	private List<TechTransit> techTransits;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "Device_For_Operation", joinColumns = @JoinColumn(name = "OPER_ID"), inverseJoinColumns = @JoinColumn(name = "TOOL_ID"))
	private Collection<Tool> tools;
	
	@Column(name = "APP_DOC_SIGN")
	private String appDocSign;

	public int getOperNumber() {
		return operNumber;
	}

	public void setOperNumber(int param) {
		this.operNumber = param;
	}

	public int getNspd() {
		return nspd;
	}

	public void setNspd(int param) {
		this.nspd = param;
	}

	public float getPrepareTime() {
		return prepareTime;
	}

	public void setPrepareTime(float param) {
		this.prepareTime = param;
	}

	public float getUnitTime() {
		return unitTime;
	}

	public void setUnitTime(float param) {
		this.unitTime = param;
	}

	public float getMainTime() {
		return mainTime;
	}

	public void setMainTime(float param) {
		this.mainTime = param;
	}

	public float getAssistTime() {
		return assistTime;
	}

	public void setAssistTime(float param) {
		this.assistTime = param;
	}

	public String getLcl() {
		return lcl;
	}

	public void setLcl(String param) {
		this.lcl = param;
	}

	public String getProgramSign() {
		return programSign;
	}

	public void setProgramSign(String param) {
		this.programSign = param;
	}

	public int getShopNumber() {
		return shopNumber;
	}

	public void setShopNumber(int param) {
		this.shopNumber = param;
	}

	public int getAreaNumber() {
		return areaNumber;
	}

	public void setAreaNumber(int param) {
		this.areaNumber = param;
	}

	public int getPlaceNumber() {
		return placeNumber;
	}

	public void setPlaceNumber(int param) {
		this.placeNumber = param;
	}

	public float getUnitTimeCoef() {
		return unitTimeCoef;
	}

	public void setUnitTimeCoef(float param) {
		this.unitTimeCoef = param;
	}

	public int getProdBatchValue() {
		return prodBatchValue;
	}

	public void setProdBatchValue(int param) {
		this.prodBatchValue = param;
	}

	public int getOperNormUnit() {
		return operNormUnit;
	}

	public void setOperNormUnit(int param) {
		this.operNormUnit = param;
	}

	public int getPerfNumber() {
		return perfNumber;
	}

	public void setPerfNumber(int param) {
		this.perfNumber = param;
	}

	public int getWorkCategory() {
		return workCategory;
	}

	public void setWorkCategory(int param) {
		this.workCategory = param;
	}

	public TypicOperation getTypicOperation() {
		return typicOperation;
	}

	public void setTypicOperation(TypicOperation param) {
		this.typicOperation = param;
	}

	public TimeNorm getTimeNorm() {
		return timeNorm;
	}

	public void setTimeNorm(TimeNorm param) {
		this.timeNorm = param;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment param) {
		this.equipment = param;
	}

	public MechDegree getMechDegree() {
		return mechDegree;
	}

	public void setMechDegree(MechDegree param) {
		this.mechDegree = param;
	}

	public WorkCondition getWorkCondition() {
		return workCondition;
	}

	public void setWorkCondition(WorkCondition param) {
		this.workCondition = param;
	}

	public List<TechTransit> getTechTransits() {
		return techTransits;
	}

	public void setTechTransits(List<TechTransit> techTransits) {
		this.techTransits = techTransits;
	}

	public Collection<Tool> getTools() {
		return tools;
	}

	public void setTools(Collection<Tool> tools) {
		this.tools = tools;
	}

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}
	
	public String getAppDocSign() {
		return appDocSign;
	}

	public void setAppDocSign(String param) {
		this.appDocSign = param;
	}
	
	public void refresh(TechOperation to) {
		this.typicOperation = to.getTypicOperation();
		this.timeNorm = to.getTimeNorm();
		this.equipment = to.getEquipment();
		this.mechDegree = to.getMechDegree();
		this.workCondition = to.getWorkCondition();
		this.profession = to.getProfession();
		this.operNumber = to.getOperNumber();
		this.nspd = to.getNspd();
		this.prepareTime = to.getPrepareTime();
		this.unitTime = to.getUnitTime();
		this.shopNumber = to.getShopNumber();
		this.areaNumber = to.getAreaNumber();
		this.placeNumber = to.getPlaceNumber();
		this.unitTimeCoef = to.getUnitTimeCoef();
		this.prodBatchValue = to.getProdBatchValue();
		this.operNormUnit = to.getOperNormUnit();
		this.perfNumber = to.getPerfNumber();
		this.workCategory = to.getWorkCategory();
		this.appDocSign = to.getAppDocSign();
		this.setEditTime(to.getEditTime());
	}

	public TechOperation clone() {
		TechOperation cloneOper = new TechOperation( null, null, null, new Date(), new Date(), 0, 0, 0, 0, 0, 0, null,
																								null, 0, 0, 0, 0, 0, 0, 0, 0, null);
		cloneOper.setOperNumber(this.operNumber);
		cloneOper.setNspd(this.nspd);
		cloneOper.setPrepareTime(this.prepareTime);
		cloneOper.setUnitTime(this.unitTime);
		cloneOper.setMainTime(this.mainTime);
		cloneOper.setAssistTime(this.assistTime);
		cloneOper.setLcl(this.lcl);
		cloneOper.setProgramSign(this.programSign);
		cloneOper.setShopNumber(this.shopNumber);
		cloneOper.setAreaNumber(this.areaNumber);
		cloneOper.setPlaceNumber(this.placeNumber);
		cloneOper.setUnitTimeCoef(this.unitTimeCoef);
		cloneOper.setProdBatchValue(this.prodBatchValue);
		cloneOper.setOperNormUnit(this.operNormUnit);
		cloneOper.setPerfNumber(this.perfNumber);
		cloneOper.setWorkCategory(this.workCategory);
		cloneOper.setAppDocSign(this.appDocSign);
		
		return cloneOper;
	}
}
