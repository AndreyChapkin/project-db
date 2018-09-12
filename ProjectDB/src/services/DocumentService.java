package services;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import entities.Document;
import entities.TechFile;
import entities.Product;

public class DocumentService {
	
	private EntityManager em;
	
	public DocumentService (EntityManager em){
		this.em = em;
	}
	
	public void add(Product prod, Document parentDoc, Document doc,List<TechFile> techFiles) throws SQLException, IOException{
			
			if(parentDoc != null) {
				doc.setParentObj(parentDoc);
				parentDoc.getChildObjs().add(doc);
				em.merge(parentDoc);
				
				doc.getTechFiles().addAll(parentDoc.getTechFiles());
			}
		
			prod.getLinkedObjs().add(doc);
			doc.setAssocObj(prod);
			
			doc.getTechFiles().addAll(techFiles);
			for(TechFile tf:techFiles) {
				tf.setDocument(doc);
			}

			em.persist(doc);
			em.merge(prod);
	}
	
	public void edit(Document oldDoc,Document doc,List<TechFile> techFiles) throws SQLException, IOException{
		
		oldDoc = em.find(Document.class, oldDoc.getObjId());
		oldDoc.refresh(doc);

		for(TechFile tf:techFiles) {
			oldDoc.getTechFiles().add(tf);
			tf.setDocument(oldDoc);
			em.persist(tf);
		}
}
	
	public void addDocumentFile(TechFile techFile, int docId) throws SQLException, IOException{
		
		Document doc = em.find(Document.class, docId);
		doc.getTechFiles().add(techFile);
		techFile.setDocument(doc);
		em.persist(techFile);
	}
	
	public void delete(int id){	
		Document doc = em.find(Document.class, id);
		if(doc.getAssocObj() != null) doc.getAssocObj().getLinkedObjs().remove(doc);
		if(doc.getParentObj() != null) doc.getParentObj().getChildObjs().remove(doc);
		if(doc.getCreator() != null) doc.getCreator().getCreatedObjects().remove(doc);
		if(doc.getEditor() !=null) doc.getEditor().getEditedObjects().remove(doc);
		em.remove(doc);
	}
	
	public Document find(int id){	
		Document doc = em.find(Document.class, id);
		return doc;
	}
	
	public List<Document> getAll(){	
			@SuppressWarnings("unchecked")
			List<Document> resultList = em.createNamedQuery("AllDocuments").getResultList();
			return resultList;
	}
}
