package entities;

import java.io.Serializable;

import javax.persistence.*;
import entities.TypicOperation;
import java.util.Collection;
import java.util.ArrayList;

@Entity
@Table(name = "Operation_Group")
@NamedQuery(name = "AllOperationGroups", query = "select og from OperationGroup og")
public class OperationGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public OperationGroup(){}
	
	public OperationGroup(String operGroupName) {
		this.operGroupName = operGroupName;
		this.typicOperations = new ArrayList<TypicOperation>();
	}

	@Id
	@Column(name = "OPERATION_GROUP_NAME")
	private String operGroupName;
	
	@OneToMany(mappedBy = "operationGroup")
	private Collection<TypicOperation> typicOperations;
	
	public String getOperGroupName() {
		return operGroupName;
	}

	public void setOperGroupName(String operGroupName) {
		this.operGroupName = operGroupName;
	}

	public Collection<TypicOperation> getTypicOperations() {
	    return typicOperations;
	}

	public void setTypicOperations(Collection<TypicOperation> param) {
	    this.typicOperations = param;
	}
}
