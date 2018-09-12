package services;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import entities.TechObject;

public class TechObjectService {
	
	private EntityManager em;
	
	public TechObjectService (EntityManager em){
		this.em = em;
	}
	
	public void addObject(TechObject obj) throws SQLException, IOException{
			em.persist(obj);	
	}
	
	public void deleteObject(int id){	
		TechObject obj = em.getReference(TechObject.class, id);
		if (obj.getCreator()!=null) obj.getCreator().getCreatedObjects().remove(obj);
		if (obj.getAssocObj()!=null) obj.getAssocObj().getLinkedObjs().remove(obj);
		for (TechObject linkedObj:obj.getLinkedObjs()) {
			linkedObj.setAssocObj(null);
		}
		em.remove(obj);
	}
	
	public TechObject findObject(int id){	
		TechObject obj = em.find(TechObject.class, id);
		return obj;
	}
	
	public List<TechObject> getAllObjects(){	
			@SuppressWarnings("unchecked")
			List<TechObject> resultList = em.createNamedQuery("AllObjects").getResultList();
			return resultList;
	}
}
