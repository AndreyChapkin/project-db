package entities;

import java.io.Serializable;

import javax.persistence.*;
import entities.Equipment;
import java.util.Collection;
import java.util.ArrayList;

@Entity
@Table(name = "Equipment_Group")
@NamedQuery(name = "AllEquipmentGroups", query = "select eqg from EquipmentGroup eqg")
public class EquipmentGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	public EquipmentGroup() {
	}

	public EquipmentGroup(String eqGroupName) {
		this.eqGroupName = eqGroupName;
		this.equipments = new ArrayList<Equipment>();
	}

	@Id
	@Column(name = "EQUIPMENT_GROUP_NAME")
	private String eqGroupName;
	
	@OneToMany(mappedBy = "equipmentGroup")
	private Collection<Equipment> equipments;
	
	public String getEqGroupName() {
		return eqGroupName;
	}

	public void setEqGroupName(String eqGroupName) {
		this.eqGroupName = eqGroupName;
	}

	public Collection<Equipment> getEquipments() {
	    return equipments;
	}

	public void setEquipments(Collection<Equipment> param) {
	    this.equipments = param;
	}
}

