package entities;

import java.io.Serializable;

import javax.persistence.*;
import java.util.Collection;
import java.util.ArrayList;

import entities.Profession;
import entities.OperationGroup;
import entities.Equipment;

@Entity
@Table(name = "Typic_Operation")
@NamedQuery(name = "AllTypicOperations", query = "select typO from TypicOperation typO order by typO.operName asc")
public class TypicOperation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public TypicOperation(){}
	
	public TypicOperation(int operCode, String operName) {
		this.operCode = operCode;
		this.operName = operName;
		this.professions = new ArrayList<Profession>();
		this.equipments = new ArrayList<Equipment>();
		this.tools = new ArrayList<Tool>();
	}

	@Id
	@Column(name = "OPERATION_CODE")
	private int operCode;
	
	@Column(name = "OPERATION_NAME")
	private String operName;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "OPERATION_GROUP")
	private OperationGroup operationGroup;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "Profession_And_Operation",
				joinColumns = @JoinColumn(name = "OPERATION_CODE"),
				inverseJoinColumns = @JoinColumn(name = "PROFESSION_CODE")
	)
	private Collection<Profession> professions;

	@ManyToMany
	@JoinTable(name = "Operation_And_Equipment",
				joinColumns = @JoinColumn(name = "OPERATION_CODE"),
				inverseJoinColumns = @JoinColumn(name = "EQUIPMENT_CODE")
	)
	private Collection<Equipment> equipments;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "Device_For_Typic_Operation",
				joinColumns = @JoinColumn(name = "TYPIC_OPER_CODE"),
				inverseJoinColumns = @JoinColumn(name = "TOOL_ID")
	)
	private Collection<Tool> tools;
	
	public int getOperCode() {
		return operCode;
	}

	public void setOperCode(int operCode) {
		this.operCode = operCode;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public OperationGroup getOperationGroup() {
	    return operationGroup;
	}

	public void setOperationGroup(OperationGroup param) {
	    this.operationGroup = param;
	}

	public Collection<Profession> getProfessions() {
	    return professions;
	}

	public void setProfessions(Collection<Profession> param) {
	    this.professions = param;
	}

	public Collection<Equipment> getEquipments() {
	    return equipments;
	}

	public void setEquipments(Collection<Equipment> param) {
	    this.equipments = param;
	}

	public Collection<Tool> getTools() {
		return tools;
	}

	public void setTools(Collection<Tool> tools) {
		this.tools = tools;
	}
	
	
	public void refresh(TypicOperation to) {
		setOperName(to.getOperName());
	}
}
