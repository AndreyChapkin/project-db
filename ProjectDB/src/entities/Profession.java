package entities;

import java.io.Serializable;

import javax.persistence.*;
import entities.TypicOperation;
import java.util.Collection;
import java.util.ArrayList;

@Entity
@Table(name = "Profession")
@NamedQuery(name = "AllProfessions", query = "select prof from Profession prof")
public class Profession implements Serializable {

	private static final long serialVersionUID = 1L;

	public Profession() {
	}

	public Profession(int profCode, String profName) {
		this.profCode = profCode;
		this.profName = profName;
		this.typicOperations = new ArrayList<TypicOperation>();
	}

	@Id
	@Column(name = "PROFESSION_CODE")
	private int profCode;
	
	@Column(name = "PROFESSION_NAME")
	private String profName;

	@ManyToMany(mappedBy = "professions")
	private Collection<TypicOperation> typicOperations;

	public int getProfCode() {
		return profCode;
	}

	public void setProfCode(int profCode) {
		this.profCode = profCode;
	}

	public String getProfName() {
		return profName;
	}

	public void setProfName(String param) {
		this.profName = param;
	}

	public Collection<TypicOperation> getTypicOperations() {
	    return typicOperations;
	}

	public void setTypicOperations(Collection<TypicOperation> param) {
	    this.typicOperations = param;
	}
	
	public void refresh(Profession prof) {
		this.profName = prof.getProfName();
	}
}
