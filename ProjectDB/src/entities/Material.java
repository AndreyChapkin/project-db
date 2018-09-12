package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Material")
@NamedQuery(name = "AllMaterials", query = "select mat from Material mat order by mat.matName, mat.matSort asc")
public class Material implements Serializable {

	private static final long serialVersionUID = 1L;

	public Material() {
	}

	public Material(int matCode, String matName, String matSort, String hardness,
			String matStandart) {
		this.matCode = matCode;
		this.matName = matName;
		this.matSort = matSort;
		this.hardness = hardness;
		this.matStandart = matStandart;
		this.typicBlank = new ArrayList<TypicBlank>();
	}

	@Id
	@Column(name = "MATERIAL_CODE")
	private int matCode;

	@Column(name = "MATERIAL_NAME")
	private String matName;

	@OneToMany(mappedBy = "material")
	private Collection<TypicBlank> typicBlank;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "MATERIAL_GROUP")
	private MaterialGroup matGroup;

	@Column(name = "MATERIAL_SORT")
	private String matSort;

	@Column(name = "MATERIAL_STANDART")
	private String matStandart;

	@Column(name = "HARDNESS")
	private String hardness;

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

	public int getMatCode() {
		return matCode;
	}

	public void setMatCode(int param) {
		this.matCode = param;
	}

	public Collection<TypicBlank> getTypicBlank() {
		return typicBlank;
	}

	public void setTypicBlank(Collection<TypicBlank> param) {
		this.typicBlank = param;
	}

	public String getMatSort() {
		return matSort;
	}

	public void setMatSort(String param) {
		this.matSort = param;
	}

	public String getMatStandart() {
		return matStandart;
	}

	public void setMatStandart(String param) {
		this.matStandart = param;
	}

	public String getHardness() {
		return hardness;
	}

	public void setHardness(String param) {
		this.hardness = param;
	}
	
	public void refresh(Material mat) {
		setMatSort(mat.getMatSort());
		setMatStandart(mat.getMatStandart());
	}
}
