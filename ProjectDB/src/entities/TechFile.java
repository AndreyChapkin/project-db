package entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Tech_File")
@NamedQuery(name = "AllTechFiles", query = "select tf from TechFile tf")
public class TechFile implements Serializable {

	private static final long serialVersionUID = 1L;

	public TechFile() {
	}

	public TechFile(byte[] fileData, String fileName, String fileType) {
		this.fileData = fileData;
		this.fileName = fileName;
		this.fileType = fileType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FILE_ID")
	private int fileId;

	@Lob
	@Column(name = "DATA_FILE")
	private byte[] fileData;

	@Column(name = "FILE_NAME")
	private String fileName;

	@ManyToOne
	@JoinColumn(name = "DOC_ID")
	private Document document;
	
	@Column(name = "FILE_TYPE")
	private String fileType;

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] param) {
		this.fileData = param;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String param) {
		this.fileType = param;
	}
}
