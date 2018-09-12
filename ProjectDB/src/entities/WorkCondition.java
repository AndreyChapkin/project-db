package entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Work_Condition")
@NamedQuery(name = "AllWorkConditions", query = "select wc from WorkCondition wc")
public class WorkCondition implements Serializable {

	private static final long serialVersionUID = 1L;

	public WorkCondition() {
	}

	public WorkCondition(int workCode, String workDescrip) {
		this.workCode = workCode;
		this.workDescrip = workDescrip;
	}

	@Id
	@Column(name = "WORK_CODE")
	private int workCode;
	
	@Column(name = "WORK_DESCRIPTION")
	private String workDescrip;
	
	public int getWorkCode() {
		return workCode;
	}

	public void setWorkCode(int workCode) {
		this.workCode = workCode;
	}

	public String getWorkDescrip() {
		return workDescrip;
	}

	public void setWorkDescrip(String workDescrip) {
		this.workDescrip = workDescrip;
	}
	
	public void refresh(WorkCondition wc) {
		this.workDescrip = wc.getWorkDescrip();
	}
	
}
