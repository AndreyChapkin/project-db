package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Collection;
import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "Document")
@NamedQuery(name = "AllDocuments", query = "select d from Document d order by d.createTime desc")
public class Document extends TechObject implements Serializable {

	private static final long serialVersionUID = 1L;

	public Document() {
	}

	public Document(User creator, TechObject  assocObj, TechObject  parentObj, 
	Date createTime, Date editTime, String docName, String docType, String docLetter, String docSign) {
		super(creator, assocObj, parentObj, createTime, editTime);
		this.docName = docName;
		this.docType = docType;
		this.docLetter = docLetter;
		this.docSign = docSign;
		this.techFiles = new ArrayList<TechFile>();
	}

	@Column(name = "DOC_NAME")
	private String docName;

	@Column(name = "DOC_TYPE")
	private String docType;

	@Column(name = "DOC_LETTER")
	private String docLetter;
	
	@Column(name = "DOC_SIGN")
	private String docSign;
	
	@OneToMany(mappedBy = "document", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private Collection<TechFile> techFiles;

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String param) {
		this.docType = param;
	}

	public String getDocLetter() {
		return docLetter;
	}

	public void setDocLetter(String param) {
		this.docLetter = param;
	}

	public String getDocSign() {
		return docSign;
	}

	public void setDocSign(String param) {
		this.docSign = param;
	}

	public Collection<TechFile> getTechFiles() {
		return techFiles;
	}

	public void setTechFiles(Collection<TechFile> techFiles) {
		this.techFiles = techFiles;
	}
	
	public void refresh(Document d) {
		this.docName = d.getDocName();
		this.docLetter = d.getDocLetter();
		this.docSign = d.getDocSign();
		this.docType = d.getDocType();
		this.setEditTime(d.getEditTime());
	}
}
