package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Tool")
@NamedQueries(
		{@NamedQuery(name = "AllTools", query = "select t from Tool t order by t.toolName asc"),
		 @NamedQuery(name = "ToolWithCode", query = "select t from Tool t where t.toolCode = :code")}
)
public class Tool extends TechObject implements Serializable {

	private static final long serialVersionUID = 1L;

	public Tool() {
	}

	public Tool(User creator,TechObject  assocObj,
	TechObject  parentObj, Date createTime, Date editTime,
	String toolName, String toolType, String toolStandart, String toolCode) {
		super(creator, assocObj, parentObj, createTime, editTime);
		this.toolName = toolName;
		this.toolType = toolType;
		this.toolStandart = toolStandart;
		this.toolCode = toolCode;
	}

	@Column(name = "TOOL_NAME")
	private String toolName;

	@Column(name = "TOOL_TYPE")
	private String toolType;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "TOOL_GROUP")
	private ToolGroup toolGroup;

	@Column(name = "TOOL_STANDART")
	private String toolStandart;

	@Column(name = "TOOL_CODE")
	private String toolCode;

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getToolType() {
		return toolType;
	}

	public void setToolType(String param) {
		this.toolType = param;
	}

	public ToolGroup getToolGroup() {
		return toolGroup;
	}

	public void setToolGroup(ToolGroup toolGroup) {
		this.toolGroup = toolGroup;
	}

	public String getToolStandart() {
		return toolStandart;
	}

	public void setToolStandart(String param) {
		this.toolStandart = param;
	}

	public String getToolCode() {
		return toolCode;
	}

	public void setToolCode(String param) {
		this.toolCode = param;
	}
	
	public void refresh(Tool t) {
		this.toolName = t.getToolName();
		this.toolStandart = t.getToolStandart();
		this.setEditTime(t.getEditTime());
	}
}
