package services;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import entities.Tool;
import entities.ToolGroup;

public class ToolService {
	
	private EntityManager em;
	
	public ToolService (EntityManager em){
		this.em = em;
	}
	
	public void add() throws SQLException, IOException{	
		
	}
	
	public void edit() throws SQLException, IOException {
		
	}
	
	public void delete() throws SQLException, IOException{	
		
	}
	
	public Tool find(int id) throws SQLException, IOException{	
		Tool tool = em.find(Tool.class, id);
		return tool;
	}
	
	public Tool findWithCode(String code) throws SQLException, IOException{	
		List<Tool> tools = em.createNamedQuery("ToolWithCode",Tool.class).setParameter("code", code).getResultList();
		if(tools != null && tools.size()>0)
			return tools.get(0);
		else return null;
	}
	
	public List<Tool> getAll() throws SQLException, IOException{	
			@SuppressWarnings("unchecked")
			List<Tool> resultList = em.createNamedQuery("AllTools").getResultList();
			return resultList;
	}
	
	public List<ToolGroup> getAllGroups(){	
		@SuppressWarnings("unchecked")
		List<ToolGroup> resultList = em.createNamedQuery("AllToolGroups").getResultList();
		return resultList;
	}
}

