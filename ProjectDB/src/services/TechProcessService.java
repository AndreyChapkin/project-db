package services;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import entities.*;

public class TechProcessService {
	
	private EntityManager em;
	
	public TechProcessService (EntityManager em){
		this.em = em;
	}
	
	public void add(TechProcess parentTechProc,TechProcess techProc, Product prod, Document doc, List<TechOperation> techOpers,
	TypicBlank typBlank, Material material, List<TypicOperation> typOpers, List<TimeNorm> timeNorms,
	List<Profession> profs, List<MechDegree> mechDegrees, List<WorkCondition> workCondits,
	List<Equipment> equips) throws SQLException, IOException{	
		
		if(parentTechProc == null) {
			Product tpProd = em.merge(prod);
			techProc.setAssocObj(tpProd);
			tpProd.getLinkedObjs().add(techProc);
		}
		else {
			parentTechProc = em.find(TechProcess.class, parentTechProc.getObjId());
			techProc.setAssocObj(parentTechProc.getAssocObj());
			techProc.setParentObj(parentTechProc);
			parentTechProc.getChildObjs().add(techProc);
		}
		
		techProc.getLinkedObjs().add(doc);
		doc.setAssocObj(techProc);
		
		if(typBlank.getBlankCode() != 0) {
		TypicBlank tb = em.find(TypicBlank.class, typBlank.getBlankCode());
		if (tb == null) techProc.setTypicBlank(typBlank);
		else techProc.setTypicBlank(tb);
		} else techProc.setTypicBlank(null);
		
		if(material.getMatCode() != 0) {
		Material m = em.find(Material.class, material.getMatCode());
		if (m == null) {
			techProc.getTypicBlank().setMaterial(material);
			material.getTypicBlank().add(techProc.getTypicBlank());
		}
		else {
			if(techProc.getTypicBlank() != null) {
				techProc.getTypicBlank().setMaterial(m);
				m.getTypicBlank().add(techProc.getTypicBlank());
			}
		}
		}
		
		TechOperation techOper = null;
		TypicOperation to = null;
		TimeNorm tn = null;
		Profession p = null;
		MechDegree md = null;
		WorkCondition wc = null; 
		Equipment eq = null;
		for(int i=0;i<techOpers.size();i++) {
			
			techOper = techOpers.get(i);
			
			if(typOpers.get(i).getOperCode()!=0 && profs.get(i).getProfCode()!=0) {
				typOpers.get(i).getProfessions().add(profs.get(i));
				profs.get(i).getTypicOperations().add(typOpers.get(i));
			}
			
			if(typOpers.get(i).getOperCode()!=0 && equips.get(i).getEqCode()!=0) {
				typOpers.get(i).getEquipments().add(equips.get(i));
				equips.get(i).getTypicOperations().add(typOpers.get(i));
			}
			
			if(typOpers.get(i).getOperCode() != 0) {
			to = em.find(TypicOperation.class, typOpers.get(i).getOperCode());
			if (to == null) techOper.setTypicOperation(typOpers.get(i));
			else techOper.setTypicOperation(to);
			}
			
			if(!timeNorms.get(i).getNormSign().equals("")) {
			tn = em.find(TimeNorm.class, timeNorms.get(i).getNormSign());
			if (tn == null) techOper.setTimeNorm(timeNorms.get(i));
			else techOper.setTimeNorm(tn);
			}
			
			if(profs.get(i).getProfCode() != 0) {
			p = em.find(Profession.class, profs.get(i).getProfCode());
			if (p == null) techOper.setProfession(profs.get(i));
			else techOper.setProfession(p);
			}
			
			if(mechDegrees.get(i).getMechCode() != 0) {
			md = em.find(MechDegree.class, mechDegrees.get(i).getMechCode());
			if (md == null) techOper.setMechDegree(mechDegrees.get(i));
			else techOper.setMechDegree(md);
			}
			
			if( workCondits.get(i).getWorkCode() != 0) {
			wc = em.find(WorkCondition.class, workCondits.get(i).getWorkCode());
			if (wc == null) techOper.setWorkCondition(workCondits.get(i));
			else techOper.setWorkCondition(wc);
			}
			
			if(equips.get(i).getEqCode() != 0) {
			eq = em.find(Equipment.class, equips.get(i).getEqCode());
			if (eq == null) techOper.setEquipment(equips.get(i));
			else techOper.setEquipment(eq);
			}
			
			techProc.getLinkedObjs().add(techOper);
			techOper.setAssocObj(techProc);
			em.persist(techOper);
		}
		em.persist(techProc);
	}
	
	public void edit(TechProcess techProc, List<TechOperation> techOpers,
	TypicBlank typBlank, Material material, List<TypicOperation> typOpers, List<TimeNorm> timeNorms,
	List<Profession> profs, List<MechDegree> mechDegrees, List<WorkCondition> workCondits,
	List<Equipment> equips) throws SQLException, IOException {
		
		TypicBlank oldBlank = null;
		if(typBlank.getBlankCode() != 0) {
			oldBlank = em.find(TypicBlank.class, typBlank.getBlankCode());
			if (oldBlank == null) {
				em.persist(typBlank);
				techProc.setTypicBlank(typBlank);
			}
			else { 
				oldBlank.refresh(typBlank);
				techProc.setTypicBlank(oldBlank);
			}
		} else techProc.setTypicBlank(null);
		
		Material oldMaterial = null;
		if(material.getMatCode() != 0 && techProc.getTypicBlank() != null) {
			oldMaterial = em.find(Material.class, material.getMatCode());
			if (oldMaterial == null) techProc.getTypicBlank().setMaterial(material);
			else { 
				oldMaterial.refresh(material);
				techProc.getTypicBlank().setMaterial(oldMaterial);
			}
		} else 
			if (techProc.getTypicBlank() != null)
			techProc.getTypicBlank().setMaterial(null);
		
		TechOperation techOper = null;
		TechOperation oldTechOper = null;
		TypicOperation to = null;
		TimeNorm tn = null;
		Profession p = null;
		MechDegree md = null;
		WorkCondition wc = null; 
		Equipment eq = null;
			
		for(int i=0;i<techOpers.size();i++) {
	
			techOper = techOpers.get(i);
			oldTechOper = (TechOperation) techProc.getLinkedObjs().get(i+1);
			
			if(typOpers.get(i).getOperCode() != 0) {
				to = em.find(TypicOperation.class, typOpers.get(i).getOperCode());
				if (to == null) techOper.setTypicOperation(typOpers.get(i));
				else { 
					to.refresh(typOpers.get(i));
					techOper.setTypicOperation(to);
				}
			}
				
			if(!timeNorms.get(i).getNormSign().equals("")) {
				tn = em.find(TimeNorm.class, timeNorms.get(i).getNormSign());
				if (tn == null) techOper.setTimeNorm(timeNorms.get(i));
				else {
					tn.refresh(timeNorms.get(i));
					techOper.setTimeNorm(tn);
				}
			}
				
			if(profs.get(i).getProfCode() != 0) {
				p = em.find(Profession.class, profs.get(i).getProfCode());
				if (p == null) techOper.setProfession(profs.get(i));
				else { 
					p.refresh(profs.get(i));
					techOper.setProfession(p);
				}
			}
				
			if(mechDegrees.get(i).getMechCode() != 0) {
				md = em.find(MechDegree.class, mechDegrees.get(i).getMechCode());
				if (md == null) techOper.setMechDegree(mechDegrees.get(i));
				else {
					md.refresh(mechDegrees.get(i));
					techOper.setMechDegree(md);
				}
			}
				
			if( workCondits.get(i).getWorkCode() != 0) {
				wc = em.find(WorkCondition.class, workCondits.get(i).getWorkCode());
				if (wc == null) techOper.setWorkCondition(workCondits.get(i));
				else {
					wc.refresh(workCondits.get(i));
					techOper.setWorkCondition(wc);
				}
			}
				
			if(equips.get(i).getEqCode() != 0) {
				eq = em.find(Equipment.class, equips.get(i).getEqCode());
				if (eq == null) techOper.setEquipment(equips.get(i));
				else {
					eq.refresh(equips.get(i));
					techOper.setEquipment(eq);
				}
			}
			
			if(typOpers.get(i).getOperCode()!=0 && profs.get(i).getProfCode()!=0) {
				typOpers.get(i).getProfessions().add(profs.get(i));
				profs.get(i).getTypicOperations().add(typOpers.get(i));
			}
			
			if(typOpers.get(i).getOperCode()!=0 && equips.get(i).getEqCode()!=0) {
				typOpers.get(i).getEquipments().add(equips.get(i));
				equips.get(i).getTypicOperations().add(typOpers.get(i));
			}
			
			oldTechOper.refresh(techOper);
			em.merge(oldTechOper);		
			em.merge(techProc);
			
		}
	}
	
	public void delete(int id) throws SQLException, IOException{	
		
		TechProcess techProc = em.find(TechProcess.class, id);
		
		if(techProc.getAssocObj() != null) techProc.getAssocObj().getLinkedObjs().remove(techProc);
		if(techProc.getParentObj() != null) techProc.getParentObj().getChildObjs().remove(techProc);
		if(techProc.getCreator() != null) techProc.getCreator().getCreatedObjects().remove(techProc);
		if(techProc.getEditor() !=null) techProc.getEditor().getEditedObjects().remove(techProc);
		
		List<TechOperation> techOpers = new ArrayList<TechOperation>();
		for(int i=1;i<techProc.getLinkedObjs().size();i++) {
			techOpers.add((TechOperation)techProc.getLinkedObjs().get(i));
		}
		
		em.remove(techProc);
	}
	
	public void deleteOperation(TechProcess techProc, int removePos) throws SQLException, IOException{	
		
		TechOperation techOper = (TechOperation) techProc.getLinkedObjs().get(removePos);
		if(techOper.getParentObj() != null) techOper.getParentObj().getChildObjs().remove(techOper);
		if(techOper.getCreator() != null) techOper.getCreator().getCreatedObjects().remove(techOper);
		if(techOper.getEditor() !=null) techOper.getEditor().getEditedObjects().remove(techOper);
			
		if(techOper.getTechTransits() != null)
			for(TechTransit tt:techOper.getTechTransits()) {
				tt.setTools(null);
				em.merge(tt);
		}
		
		for(TechObject to:techOper.getLinkedObjs()) {
			if(to.getClass() == TechControl.class) {
				TechControl techCtrl = (TechControl) to;
				for(ParamControl pc:techCtrl.getParamControls()) {
					pc.setCtrlDevices(null);
					em.merge(pc);
				}
			}
		}
		
		techProc.getLinkedObjs().remove(techOper);
		em.merge(techProc);
	}
	
	public TechProcess find(int id) throws SQLException, IOException{	
		TechProcess techProc = em.find(TechProcess.class, id);
		return techProc;
	}
	
	public List<TechProcess> getAll() throws SQLException, IOException{	
			@SuppressWarnings("unchecked")
			List<TechProcess> resultList = em.createNamedQuery("AllTechProcesses").getResultList();
			return resultList;
	}
	
}
