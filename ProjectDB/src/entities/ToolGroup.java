package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Collection;
import java.util.ArrayList;

@Entity
@Table(name = "Tool_Group")
@NamedQuery(name = "AllToolGroups", query = "select tg from ToolGroup tg")
public class ToolGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	public ToolGroup() {
	}

	public ToolGroup(String toolGroupName) {
		this.toolGroupName = toolGroupName;
		this.tools = new ArrayList<Tool>();
	}

	@Id
	@Column(name = "TOOL_GROUP_NAME")
	private String toolGroupName;
	
	@OneToMany(mappedBy = "toolGroup")
	private Collection<Tool> tools;
	
	public String getToolGroupName() {
		return toolGroupName;
	}

	public void setToolGroupName(String param) {
		this.toolGroupName = param;
	}

	public Collection<Tool> getTools() {
		return tools;
	}

	public void setTools(Collection<Tool> tools) {
		this.tools = tools;
	}
}
