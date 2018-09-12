package services;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import entities.Product;
import entities.ProductGroup;
import entities.MeasureUnit;

public class ProductService {
	
	private EntityManager em;
	
	public ProductService (EntityManager em){
		this.em = em;
	}
	
	public void add(Product prod, String groupName, String measUnitName, Product parentProd) throws SQLException, IOException{	
		ProductGroup prodGroup = em.find(ProductGroup.class, groupName);
		MeasureUnit measUnit = em.find(MeasureUnit.class, measUnitName);
		if (prodGroup == null) {
			prodGroup = new ProductGroup(groupName);
			em.persist(prodGroup);
		}
		if (measUnit == null) {
			measUnit = new MeasureUnit(measUnitName);
			em.persist(measUnit);
		}
		if (parentProd != null) {
			parentProd = em.merge(parentProd);
			parentProd.getChildObjs().add(prod);
			prod.setParentObj(parentProd);
		}
		prod.setProductGroup(prodGroup);
		prod.setMeasureUnit(measUnit);
		prodGroup.getProducts().add(prod);
		em.persist(prod);
	}
	
	public void edit(Product prod, Map<String,Object> prop,String prodGroupName,String measUnitName) throws SQLException, IOException {
		//Поиск группы и единицы измерения
		
		ProductGroup prodGroup = em.find(ProductGroup.class, prodGroupName);
		MeasureUnit measUnit = em.find(MeasureUnit.class, measUnitName);
		
		if (prodGroup == null) {
			prodGroup = new ProductGroup(prodGroupName);
			em.persist(prodGroup);
		}
		if (measUnit == null) {
			measUnit = new MeasureUnit(measUnitName);
			em.persist(measUnit);
		}
		if(prod != null) {
			prod = em.merge(prod);
			prod.setProdName((String) prop.get("prodName"));
			prod.setProdWeight((float) prop.get("prodWeight"));
			prod.setBatchValue((int) prop.get("batchValue"));
			prod.setProdSign((String)prop.get("prodSign"));
			prod.setEditTime(new Date());
			prod.setProductGroup(prodGroup);
			prodGroup.getProducts().add(prod);
			prod.setMeasureUnit(measUnit);
		}
	}
	
	public void delete(int id) throws SQLException, IOException{	
		Product prod = em.find(Product.class, id);
		
		/*if(prod.getLinkedObjs()!=null) {
			for(TechObject lp:prod.getLinkedObjs()) {
				lp.setAssocObj(null);
			}
		}*/
		if(prod.getAssocObj() != null) prod.getAssocObj().getLinkedObjs().remove(prod);
		if(prod.getParentObj() != null) prod.getParentObj().getChildObjs().remove(prod);
		if(prod.getProductGroup() != null)  prod.getProductGroup().getProducts().remove(prod);
		if(prod.getCreator() != null) prod.getCreator().getCreatedObjects().remove(prod);
		if(prod.getEditor() !=null) prod.getEditor().getEditedObjects().remove(prod);
		em.remove(prod);
	}
	
	public Product find(int id) throws SQLException, IOException{	
		Product prod = em.find(Product.class, id);
		return prod;
	}
	
	public List<Product> getAll() throws SQLException, IOException{	
			@SuppressWarnings("unchecked")
			List<Product> resultList = em.createNamedQuery("AllProducts").getResultList();
			return resultList;
	}
	
	public List<ProductGroup> getAllGroups(){	
		@SuppressWarnings("unchecked")
		List<ProductGroup> resultList = em.createNamedQuery("AllProdGroups").getResultList();
		return resultList;
	}
	
	public List<MeasureUnit> getAllMeasUnits(){	
		@SuppressWarnings("unchecked")
		List<MeasureUnit> resultList = em.createNamedQuery("AllMeasUnits").getResultList();
		return resultList;
	}
}
