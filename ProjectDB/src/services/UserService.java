package services;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import entities.User;
import entities.TechObject;
import entities.Tool;

public class UserService {
	
	private EntityManager em;
	
	public UserService (EntityManager em){
		this.em = em;
	}
	
	public void add (User user) throws SQLException, IOException{
			em.persist(user);	
	}
	
	public void delete(int id){	
		User user = em.getReference(User.class, id);
		for(TechObject obj:user.getCreatedObjects()) {
			obj.setCreator(null); 
		}
		em.remove(user);
	}
	
	public User find(int id){	
		User user = em.find(User.class, id);
		return user;
	}
	
	public User findWithLogin(String login) throws SQLException, IOException{	
		
		@SuppressWarnings("unchecked")
		List<User> users = em.createNamedQuery("UserWithSurname").setParameter("surname", login).getResultList();
		if(users != null && users.size()>0)
			return users.get(0);
		else return null;
	}
	
	public List<User> getAll(){	
			@SuppressWarnings("unchecked")
			List<User> resultList = em.createNamedQuery("AllUsers").getResultList();
			return resultList;
	}
}
