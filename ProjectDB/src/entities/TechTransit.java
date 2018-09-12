package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Tech_Transit")
@NamedQuery(name = "AllTechTransits", query = "select tt from TechTransit tt")
public class TechTransit implements Serializable {

	private static final long serialVersionUID = 1L;

	public TechTransit() {
	}

	public TechTransit(int tranNumber, TechOperation techOper, String tranDescrip,
	int adjustPosNumber, float treatWidth, float strokeLength,
	int strokeCount, float cutDepth, float supValue, float rotSpeed,
	float cutSpeed, float tranMainTime, float tranAssistTime) {
		this.setTranNumber(tranNumber);
		this.techOperation = techOper;
		this.tranDescrip = tranDescrip;
		this.adjustPosNumber = adjustPosNumber;
		this.treatWidth = treatWidth;
		this.strokeLength = strokeLength;
		this.strokeCount = strokeCount;
		this.cutDepth = cutDepth;
		this.supValue = supValue;
		this.rotSpeed = rotSpeed;
		this.cutSpeed = cutSpeed;
		this.tranMainTime = tranMainTime;
		this.tranAssistTime = tranAssistTime;
		this.tools = new ArrayList<Tool>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRAN_ID")
	private int tranId;

	@ManyToOne
	@JoinColumn(name = "TECH_OPERATION_ID")
	private TechOperation techOperation;
	
	@Column(name = "TRAN_NUMBER")
	private int tranNumber;

	@Column(name = "TRAN_DESCRIPTION")
	private String tranDescrip;

	@Column(name = "ADJUST_POS_NUMBER")
	private int adjustPosNumber;

	@Column(name = "TREATED_WIDTH")
	private float treatWidth;

	@Column(name = "STROKE_LENGTH")
	private float strokeLength;

	@Column(name = "STROKE_COUNT")
	private int strokeCount;

	@Column(name = "CUTTING_DEPTH")
	private float cutDepth;

	@Column(name = "SUPPLY_VALUE")
	private float supValue;

	@Column(name = "ROTATING_SPEED")
	private float rotSpeed;

	@Column(name = "CUTTING_SPEED")
	private float cutSpeed;

	@Column(name = "TRAN_MAIN_TIME")
	private float tranMainTime;
	
	@Column(name = "TRAN_ASSIST_TIME")
	private float tranAssistTime;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "Tool_For_Transit", 
				joinColumns = @JoinColumn(name = "TRAN_ID", referencedColumnName = "TRAN_ID"),
				inverseJoinColumns = @JoinColumn(name = "TOOL_ID", referencedColumnName = "OBJ_ID"))
	private Collection<Tool> tools;

	public String getTranDescrip() {
		return tranDescrip;
	}

	public void setTranDescrip(String param) {
		this.tranDescrip = param;
	}

	public TechOperation getTechOperation() {
		return techOperation;
	}

	public void setTechOperation(TechOperation techOperation) {
		this.techOperation = techOperation;
	}

	public int getAdjustPosNumber() {
		return adjustPosNumber;
	}

	public void setAdjustPosNumber(int param) {
		this.adjustPosNumber = param;
	}

	public float getTreatWidth() {
		return treatWidth;
	}

	public void setTreatWidth(float param) {
		this.treatWidth = param;
	}

	public float getStrokeLength() {
		return strokeLength;
	}

	public void setStrokeLength(float param) {
		this.strokeLength = param;
	}

	public int getStrokeCount() {
		return strokeCount;
	}

	public void setStrokeCount(int param) {
		this.strokeCount = param;
	}

	public float getCutDepth() {
		return cutDepth;
	}

	public void setCutDepth(float param) {
		this.cutDepth = param;
	}

	public float getSupValue() {
		return supValue;
	}

	public void setSupValue(float param) {
		this.supValue = param;
	}

	public float getRotSpeed() {
		return rotSpeed;
	}

	public void setRotSpeed(float param) {
		this.rotSpeed = param;
	}

	public float getCutSpeed() {
		return cutSpeed;
	}

	public void setCutSpeed(float param) {
		this.cutSpeed = param;
	}

	public Collection<Tool> getTools() {
		return tools;
	}

	public void setTools(Collection<Tool> tools) {
		this.tools = tools;
	}

	public float getTranMainTime() {
		return tranMainTime;
	}

	public void setTranMainTime(float param) {
		this.tranMainTime = param;
	}

	public float getTranAssistTime() {
		return tranAssistTime;
	}

	public void setTranAssistTime(float param) {
		this.tranAssistTime = param;
	}

	public int getTranId() {
		return tranId;
	}

	public void setTranId(int tranId) {
		this.tranId = tranId;
	}

	public int getTranNumber() {
		return tranNumber;
	}

	public void setTranNumber(int tranNumber) {
		this.tranNumber = tranNumber;
	}
	
	public void refresh(TechTransit tt) {
		this.tranDescrip = tt.getTranDescrip();
		this.adjustPosNumber = tt.getAdjustPosNumber();
		this.treatWidth = tt.getTreatWidth();
		this.strokeLength = tt.getStrokeLength();
		this.strokeCount = tt.getStrokeCount();
		this.cutDepth = tt.getCutDepth();
		this.supValue = tt.getSupValue();
		this.rotSpeed = tt.getRotSpeed();
		this.cutSpeed = tt.getCutSpeed();
		this.tranMainTime = tt.getTranMainTime();
		this.tranAssistTime = tt.getTranAssistTime();
		this.tranNumber = tt.getTranNumber();
	}
}
