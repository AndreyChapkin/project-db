package entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Mech_Degree")
@NamedQuery(name = "AllMechDegrees", query = "select mech from MechDegree mech")
public class MechDegree implements Serializable {

	private static final long serialVersionUID = 1L;

	public MechDegree() {
	}

	public MechDegree(int mechCode, String mechDescrip) {
		this.mechCode = mechCode;
		this.mechDescrip =mechDescrip;
	}

	@Id
	@Column(name = "MECH_CODE")
	private int mechCode;
	
	@Column(name = "MECH_DESCRIPTION")
	private String mechDescrip;
	
	public int getMechCode() {
		return mechCode;
	}

	public void setMechCode(int mechCode) {
		this.mechCode = mechCode;
	}

	public String getMechDescrip() {
		return mechDescrip;
	}

	public void setMechDescrip(String mechDescrip) {
		this.mechDescrip = mechDescrip;
	}
	
	public void refresh(MechDegree md) {
		this.mechDescrip = md.getMechDescrip();
	}
	
}
