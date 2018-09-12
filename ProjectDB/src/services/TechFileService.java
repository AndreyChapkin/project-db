package services;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import entities.TechFile;

public class TechFileService {
	
	private EntityManager em;
	
	public TechFileService (EntityManager em){
		this.em = em;
	}
	
	public void addTechFile(TechFile techFile) throws SQLException, IOException{
			em.persist(techFile);	
	}
	
	public void deleteTechFile(int id){	
		TechFile techFile = em.find(TechFile.class, id);
		em.remove(techFile);
	}
	
	public TechFile findTechFile(int id){	
		TechFile techFile = em.find(TechFile.class, id);
		return techFile;
	}
	
	public List<TechFile> getAllTechFiles(){	
			@SuppressWarnings("unchecked")
			List<TechFile> resultList = em.createNamedQuery("AllTechFiles").getResultList();
			return resultList;
	}
}
