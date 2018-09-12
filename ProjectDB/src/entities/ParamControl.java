package entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Collection;
import java.util.ArrayList;

@Entity
@Table(name = "Param_Control")
@NamedQuery(name = "AllParamControls", query = "select pc from ParamControl pc")
public class ParamControl implements Serializable {

	private static final long serialVersionUID = 1L;

	public ParamControl() {
	}

	public ParamControl( TechControl techControl, String parameter,
	String valuePC, float timesRatio, int paramNumber) {
		this.techControl = techControl;
		this.parameter = parameter;
		this.valuePC = valuePC;
		this.timesRatio = timesRatio;
		this.paramNumber = paramNumber;
		this.ctrlDevices = new ArrayList<Tool>();
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PARAM_ID")
	private int paramId;
	
	@Column(name = "PARAM_NUMBER")
	private int paramNumber;
	
	@ManyToOne
	@JoinColumn(name = "TECH_CONTROL_ID")
	private TechControl techControl;

	@Column(name = "PARAMETER")
	private String parameter;
	
	@Column(name = "VALUE_AND_PC")
	private String valuePC;
	
	@Column(name = "TIMES_RATIO")
	private float timesRatio;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "Device_For_Control",
				joinColumns = @JoinColumn(name = "PARAM_ID", referencedColumnName ="PARAM_ID"),
				inverseJoinColumns = @JoinColumn(name = "DEVICE_ID", referencedColumnName = "OBJ_ID")
				
	)
	private Collection<Tool> ctrlDevices;

	public TechControl getTechControl() {
		return techControl;
	}

	public void setTechControl(TechControl techControl) {
		this.techControl = techControl;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String param) {
		this.parameter = param;
	}

	public String getValuePC() {
		return valuePC;
	}

	public void setValuePC(String param) {
		this.valuePC = param;
	}

	public float getTimesRatio() {
		return timesRatio;
	}

	public void setTimesRatio(float param) {
		this.timesRatio = param;
	}

	public int getParamId() {
		return paramId;
	}

	public void setParamId(int paramId) {
		this.paramId = paramId;
	}

	public int getParamNumber() {
		return paramNumber;
	}

	public void setParamNumber(int paramNumber) {
		this.paramNumber = paramNumber;
	}

	public Collection<Tool> getCtrlDevices() {
		return ctrlDevices;
	}

	public void setCtrlDevices(Collection<Tool> ctrlDevices) {
		this.ctrlDevices = ctrlDevices;
	}
	
	public void refresh(ParamControl pc) {
		this.paramNumber = pc.getParamNumber();
		this.parameter = pc.getParameter();
		this.valuePC = pc.getValuePC();
		this.timesRatio = pc.getTimesRatio();
	}
}
