package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Tech_Process")
@NamedQueries({
	@NamedQuery(name = "AllTechProcesses", query = "select tp from TechProcess tp order by tp.createTime desc"),
	@NamedQuery(name = "AllTechProcessesForProduct", query = "select tp from TechProcess tp where tp.assocObj.objId like :prodId")
	})
public class TechProcess extends TechObject implements Serializable {

	private static final long serialVersionUID = 1L;

	public TechProcess() {
	}

	public TechProcess (User creator,TechObject  assocObj, TechObject  parentObj, Date createTime, Date editTime,
	String specInstruction, int normUnit, float materialConsump,
	float materialUseCoef, int detailCount, float blankSize, float blankWeight) {
		super(creator, assocObj, parentObj, createTime, editTime);
		this.setSpecInstruction(specInstruction);
		this.normUnit = normUnit;
		this.materialConsump = materialConsump;
		this.materialUseCoef = materialUseCoef;
		this.detailCount = detailCount;
		this.blankSize = blankSize;
		this.blankWeight = blankWeight;
	}

	@Column(name = "SPEC_INSTRUCTION")
	private String specInstruction;

	@Column(name = "NORM_UNIT")
	private int normUnit;

	@Column(name = "MATERIAL_CONSUMP")
	private float materialConsump;

	@Column(name = "MATERIAL_USE_COEF")
	private float materialUseCoef;

	@Column(name = "DETAIL_COUNT")
	private int detailCount;

	@Column(name = "BLANK_SIZE")
	private float blankSize;
	
	@Column(name = "BLANK_WEIGHT")
	private float blankWeight;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "TYPIC_BLANK_ID")
	private TypicBlank typicBlank;

	public int getNormUnit() {
		return normUnit;
	}

	public void setNormUnit(int param) {
		this.normUnit = param;
	}

	public float getMaterialConsump() {
		return materialConsump;
	}

	public void setMaterialConsump(float param) {
		this.materialConsump = param;
	}

	public float getMaterialUseCoef() {
		return materialUseCoef;
	}

	public void setMaterialUseCoef(float param) {
		this.materialUseCoef = param;
	}

	public int getDetailCount() {
		return detailCount;
	}

	public void setDetailCount(int param) {
		this.detailCount = param;
	}

	public float getBlankSize() {
		return blankSize;
	}

	public void setBlankSize(float param) {
		this.blankSize = param;
	}

	public float getBlankWeight() {
		return blankWeight;
	}

	public void setBlankWeight(float param) {
		this.blankWeight = param;
	}

	public TypicBlank getTypicBlank() {
	    return typicBlank;
	}

	public void setTypicBlank(TypicBlank param) {
	    this.typicBlank = param;
	}

	public String getSpecInstruction() {
		return specInstruction;
	}

	public void setSpecInstruction(String specInstruction) {
		this.specInstruction = specInstruction;
	}
}
