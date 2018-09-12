package services;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import entities.Message;
import entities.User;

public class MessageService {
	
	private EntityManager em;
	
	public MessageService (EntityManager em){
		this.em = em;
	}
	
	public void add(Message mes,User recipient,User sender) throws SQLException, IOException{
			recipient.getInMessages().add(0, mes);
			sender.getOutMessages().add(0, mes);
			em.persist(mes);	
	}
	
	public void delete(int id){
		Message mes = em.find(Message.class, id);
		mes.getRecipient().getInMessages().remove(mes);
	}
	
	public void deleteOut(int id){
		Message mes = em.find(Message.class, id);
		mes.getSender().getOutMessages().remove(mes);
	}
	
	public Message findMessage(int id){	
		Message mes = em.find(Message.class, id);
		return mes;
	}
	
	public List<Message> getAllMessages(){	
			@SuppressWarnings("unchecked")
			List<Message> resultList = em.createNamedQuery("AllMessages").getResultList();
			return resultList;
	}
}
