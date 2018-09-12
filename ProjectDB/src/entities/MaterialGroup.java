package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Collection;
import java.util.ArrayList;

@Entity
@Table(name = "Material_Group")
@NamedQuery(name = "AllMaterialGroups", query = "select mg from MaterialGroup mg")
public class MaterialGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	public MaterialGroup() {
	}

	public MaterialGroup(String matGroupName) {
		this.matGroupName = matGroupName;
		this.materials = new ArrayList<Material>();
	}

	@Id
	@Column(name = "MAT_GROUP_NAME")
	private String matGroupName;
	
	@OneToMany(mappedBy = "matGroup")
	private Collection<Material> materials;
	
	public String getMatGroupName() {
		return matGroupName;
	}

	public void setMatGroupName(String param) {
		this.matGroupName = param;
	}

	public Collection<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(Collection<Material> materials) {
		this.materials = materials;
	}
}
