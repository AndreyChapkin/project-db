package entities;

import java.io.Serializable;

import javax.persistence.*;
import entities.EquipmentGroup;
import entities.TypicOperation;
import java.util.Collection;
import java.util.ArrayList;

@Entity
@Table(name = "Equipment")
@NamedQuery(name = "AllEquipments", query = "select eq from Equipment eq order by eq.eqName asc")
public class Equipment implements Serializable {

	private static final long serialVersionUID = 1L;

	public Equipment() {
	}

	public Equipment(int eqCode, String eqName) {
		this.eqCode = eqCode;
		this.eqName = eqName;
		this.typicOperations = new ArrayList<TypicOperation>();
	}

	@Id
	@Column(name = "EQUIPMENT_CODE")
	private int eqCode;
	
	@Column(name = "EQUIPMENT_NAME")
	private String eqName;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "EQUIPMENT_GROUP")
	private EquipmentGroup equipmentGroup;

	@ManyToMany(mappedBy = "equipments")
	private Collection<TypicOperation> typicOperations;

	public int getEqCode() {
		return eqCode;
	}

	public void setEqCode(int eqCode) {
		this.eqCode = eqCode;
	}

	public String getEqName() {
		return eqName;
	}

	public void setEqName(String param) {
		this.eqName = param;
	}

	public EquipmentGroup getEquipmentGroup() {
	    return equipmentGroup;
	}

	public void setEquipmentGroup(EquipmentGroup param) {
	    this.equipmentGroup = param;
	}

	public Collection<TypicOperation> getTypicOperations() {
	    return typicOperations;
	}

	public void setTypicOperations(Collection<TypicOperation> param) {
	    this.typicOperations = param;
	}
	
	public void refresh(Equipment eq) {
		this.eqName = eq.getEqName();
	}
}
