package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import entities.TypicBlank;
import java.util.Collection;

@Entity
@Table(name = "Blank_Group")
@NamedQuery(name = "AllBlankGroups", query = "select bg from BlankGroup bg")
public class BlankGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	public BlankGroup() {
	}

	public BlankGroup(String blankGroupName) {
		this.blankGroupName = blankGroupName;
		this.typicBlank = new ArrayList<TypicBlank>();
	}

	@Id
	@Column(name = "BLANK_GROUP_NAME")
	private String blankGroupName;
	
	@OneToMany(mappedBy = "blankGroup")
	private Collection<TypicBlank> typicBlank;

	public Collection<TypicBlank> getTypicBlank() {
	    return typicBlank;
	}

	public void setTypicBlank(Collection<TypicBlank> param) {
	    this.typicBlank = param;
	}

}
