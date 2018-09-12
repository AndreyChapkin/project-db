package entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Typic_Blank")
@NamedQuery(name = "AllTypicBlanks", query = "select tb from TypicBlank tb order by tb.blankName, tb.profile asc")
public class TypicBlank implements Serializable {

	private static final long serialVersionUID = 1L;

	public TypicBlank() {
	}

	public TypicBlank(int blankCode, String blankName,
	String profile, String blankStandart, String typicSize) {
		this.blankCode = blankCode;
		this.blankName = blankName;
		this.profile = profile;
		this.blankStandart = blankStandart;
		this.typicSize = typicSize;
	}

	@Id
	@Column(name = "BLANK_CODE")
	private int blankCode;

	@Column(name = "BLANK_NAME")
	private String blankName;

	@Column(name = "PROFILE")
	private String profile;

	@Column(name = "BLANK_STANDART")
	private String blankStandart;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "BLANK_GROUP")
	private BlankGroup blankGroup;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "MATERIAL_ID")
	private Material material;

	@Column(name = "TYPIC_SIZE")
	private String typicSize;

	public int getBlankCode() {
		return blankCode;
	}

	public void setBlankCode(int blankId) {
		this.blankCode = blankId;
	}

	public String getBlankName() {
		return blankName;
	}

	public void setBlankName(String blankName) {
		this.blankName = blankName;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String param) {
		this.profile = param;
	}

	public String getBlankStandart() {
		return blankStandart;
	}

	public void setBlankStandart(String param) {
		this.blankStandart = param;
	}

	public BlankGroup getBlankGroup() {
		return blankGroup;
	}

	public void setBlankGroup(BlankGroup param) {
		this.blankGroup = param;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material param) {
		this.material = param;
	}

	public String getTypicSize() {
		return typicSize;
	}

	public void setTypicSize(String param) {
		this.typicSize = param;
	}
	
	public void refresh(TypicBlank tb) {
		setBlankName(tb.getBlankName());
		setProfile(tb.getProfile());
		setBlankStandart(tb.getBlankStandart());
		setTypicSize(tb.getTypicSize());
	}
}
