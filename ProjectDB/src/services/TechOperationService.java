package services;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import entities.*;

public class TechOperationService {
	
	private EntityManager em;
	
	public TechOperationService (EntityManager em){
		this.em = em;
	}
	
	public void add(TechProcess techProc, Product prod, Document doc, List<TechOperation> techOpers,
	TypicBlank typBlank, Material material, List<TypicOperation> typOpers, List<TimeNorm> timeNorms,
	List<Profession> profs, List<MechDegree> mechDegrees, List<WorkCondition> workCondits,
	List<Equipment> equips) throws SQLException, IOException{	
		
		Product tpProd = em.merge(prod);
		techProc.setAssocObj(tpProd);
		tpProd.getLinkedObjs().add(techProc);
		
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
			techProc.getTypicBlank().setMaterial(m);
			m.getTypicBlank().add(techProc.getTypicBlank());
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
			
			//ÎÁÐÀÇÅÖ
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
		em.remove(techProc);
	}
	
	public void deleteOperCard(int id) throws SQLException, IOException{	

		TechOperation techOper = em.find(TechOperation.class, id);
		Document document = em.find(Document.class,techOper.getLinkedObjs().get(0).getObjId());
		techOper.getLinkedObjs().remove(0);
		em.remove(document);
		for(TechTransit tt:techOper.getTechTransits()) {
			tt = em.find(TechTransit.class, tt.getTranId());
			tt.setTools(null);
			em.remove(tt);
		}
		techOper.setTechTransits(new ArrayList<TechTransit>());
		if(techOper.getParentObj() != null) {
			if(techOper.getAssocObj() != null) techOper.getAssocObj().getLinkedObjs().remove(techOper);
			if(techOper.getParentObj() != null) techOper.getParentObj().getChildObjs().remove(techOper);
			if(techOper.getCreator() != null) techOper.getCreator().getCreatedObjects().remove(techOper);
			if(techOper.getEditor() !=null) techOper.getEditor().getEditedObjects().remove(techOper);
			em.remove(techOper);
		}
	}
	
	public void deleteTransit(TechOperation techOper, int removePos) throws SQLException, IOException{	
		
		TechTransit techTran = (TechTransit) techOper.getTechTransits().get(removePos);
		techTran.setTools(null);
		techTran.setTechOperation(null);
		techOper.getTechTransits().remove(techTran);
		em.merge(techOper);
	}
	
	public void addOperCard(TechOperation parentTechOper, TechOperation techOper,Document document,List<TechTransit> techTransits,String hardness,String[] toolsPiles) throws SQLException, IOException{
	
		TechProcess tp = (TechProcess) techOper.getAssocObj();
		if(tp.getTypicBlank() != null && tp.getTypicBlank().getMaterial()!= null)
			tp.getTypicBlank().getMaterial().setHardness(hardness);
        String[] oneToolData = new String[3];
        Tool oldTool;
		for(int i=0;i<toolsPiles.length;i++) {
			String[] tools = toolsPiles[i].trim().split(";");
	        for(String s:tools){
	        	//toolCode, toolName è toolStandart
	        	oneToolData = s.trim().split(",");
	            if(oneToolData.length == 3) {
					oldTool = new ToolService(em).findWithCode(oneToolData[0].trim());
					Tool newTool = new Tool(null, null, null, new Date(), new Date(),oneToolData[1].trim(), null,
							oneToolData[2].trim(), oneToolData[0].trim());
					if(oldTool == null) {
						em.persist(newTool);
						techTransits.get(i).getTools().add(newTool);
					}
					else {
						oldTool.refresh(newTool);
						techTransits.get(i).getTools().add(oldTool);
					}
	            }
	        }
		}
		
		if(parentTechOper == null) {
			techOper.getLinkedObjs().add(document);
			document.setAssocObj(techOper);
			em.persist(document);
			techOper.setTechTransits(techTransits);
			for(TechTransit tt:techTransits) {
				tt.setTechOperation(techOper);
				em.persist(tt);
			}
			em.merge(techOper);
		}
		else {
			TechOperation childTechOper = techOper.clone();
			
			childTechOper.getLinkedObjs().add(document);
			document.setAssocObj(childTechOper);
			em.persist(document);
			
			childTechOper.setTechTransits(techTransits);
			for(TechTransit tt:techTransits) {
				tt.setTechOperation(childTechOper);
				em.persist(tt);
			}

			if(parentTechOper.getTypicOperation() !=null) {
				TypicOperation oldTO = em.find(TypicOperation.class, parentTechOper.getTypicOperation().getOperCode());
				childTechOper.setTypicOperation(oldTO);
			}
			if(parentTechOper.getTimeNorm() != null) { 
				TimeNorm oldTN = em.find(TimeNorm.class, parentTechOper.getTimeNorm().getNormSign());
				childTechOper.setTimeNorm(oldTN);
			}
			if(parentTechOper.getProfession() != null) {
				Profession oldPr = em.find(Profession.class, parentTechOper.getProfession().getProfCode());
				childTechOper.setProfession(oldPr);
			}
			if(parentTechOper.getMechDegree() != null) {
				MechDegree oldMD = em.find(MechDegree.class, parentTechOper.getMechDegree().getMechCode());
				childTechOper.setMechDegree(oldMD);
			}
			if(parentTechOper.getWorkCondition() != null) {
				WorkCondition oldWC = em.find(WorkCondition.class, parentTechOper.getWorkCondition().getWorkCode());
				childTechOper.setWorkCondition(oldWC);
			}
			if(parentTechOper.getEquipment() != null) {
				Equipment oldEq = em.find(Equipment.class, parentTechOper.getEquipment().getEqCode());
				childTechOper.setEquipment(oldEq);
			}
			TechProcess oldTP = em.find(TechProcess.class, parentTechOper.getAssocObj().getObjId());
			childTechOper.setAssocObj(oldTP);
			
			childTechOper.setParentObj(parentTechOper);
			parentTechOper.getChildObjs().add(childTechOper);
			em.persist(childTechOper);
			em.merge(parentTechOper);
		}
	}
	
	public void editOperCard(TechOperation techOper,Document document,List<TechTransit> techTransits,String hardness,String[] toolsPiles) throws SQLException, IOException{

		TechOperation oldTechOper = em.find(TechOperation.class, techOper.getObjId());
		oldTechOper.setProgramSign(techOper.getProgramSign());
		oldTechOper.setMainTime(techOper.getMainTime());
		oldTechOper.setAssistTime(techOper.getAssistTime());
		oldTechOper.setLcl(techOper.getLcl());
		
		Document oldDocument = em.find(Document.class, oldTechOper.getLinkedObjs().get(0).getObjId());
		oldDocument.refresh(document);
		
		List<TechTransit> oldTechTransits = techOper.getTechTransits();
		
		TechProcess tp = (TechProcess) techOper.getAssocObj();
		if(tp.getTypicBlank() != null && tp.getTypicBlank().getMaterial()!= null) {
			Material oldMaterial = tp.getTypicBlank().getMaterial();
			oldMaterial.setHardness(hardness);
			em.merge(oldMaterial);
		}
        
		String[] oneToolData = new String[3];
        Tool oldTool;
        
        for(int i=0;i<toolsPiles.length;i++) {
        	oldTechTransits.get(i).setTools(new ArrayList<Tool>());
			String[] tools = toolsPiles[i].trim().split(";");
	        for(String s:tools){
	        	//toolCode, toolName è toolStandart
	        	oneToolData = s.trim().split(",");
	            if(oneToolData.length == 3) {
					oldTool = new ToolService(em).findWithCode(oneToolData[0].trim());
					Tool newTool = new Tool(null, null, null, new Date(), new Date(),oneToolData[1].trim(), null,
							oneToolData[2].trim(), oneToolData[0].trim());
					if(oldTool == null) {
						em.persist(newTool);
						oldTechTransits.get(i).getTools().add(newTool);
					}
					else {
						oldTool.refresh(newTool);
						oldTechTransits.get(i).getTools().add(oldTool);
					}
	            }
	        }
		}
        
		for(int i=0; i<techTransits.size();i++) {
			oldTechTransits.get(i).refresh(techTransits.get(i));
			em.merge(oldTechTransits.get(i));
		}
	}
	
	public void addCtrlCard(TechControl parentTechCtrl, TechOperation techOper,Document document, TechControl techCtrl, List<ParamControl> paramControls,String[] devicesPiles) throws SQLException, IOException{
		
		techOper.getLinkedObjs().add(techCtrl);
		techCtrl.setAssocObj(techOper);
		
		techCtrl.getLinkedObjs().add(document);
		document.setAssocObj(techCtrl);
		em.persist(document);
		em.persist(techCtrl);
		if(parentTechCtrl != null) {
			techCtrl.setParentObj(parentTechCtrl);
			parentTechCtrl.getChildObjs().add(techCtrl);
			em.merge(parentTechCtrl);
		}
		
		techCtrl.setParamControls(paramControls);
		for(ParamControl pc:paramControls) {
			pc.setTechControl(techCtrl);
			em.persist(pc);
		}
		
        String[] oneDeviceData = new String[3];
        Tool oldDevice;
		for(int i=0;i<devicesPiles.length;i++) {
			String[] devices = devicesPiles[i].trim().split("\n");
	        for(String s:devices){
	        	//toolCode, toolName è toolStandart
	        	oneDeviceData = s.trim().split(",");
	            if(oneDeviceData.length == 3) {
					oldDevice = new ToolService(em).findWithCode(oneDeviceData[0].trim());
					Tool newDevice = new Tool(null, null, null, new Date(), new Date(),oneDeviceData[1].trim(), null,
							oneDeviceData[2].trim(), oneDeviceData[0].trim());
					if(oldDevice == null) {
						em.persist(newDevice);
						paramControls.get(i).getCtrlDevices().add(newDevice);
					}
					else {
						oldDevice.refresh(newDevice);
						paramControls.get(i).getCtrlDevices().add(oldDevice);
					}
	            }
	        }
		}
		em.merge(techOper);
	}
	
public void editCtrlCard(TechOperation techOper,Document document, TechControl techCtrl, TechControl oldTechCtrl, List<ParamControl> paramControls,String[] devicesPiles) throws SQLException, IOException{
		
		TechOperation oldTechOper = em.find(TechOperation.class, techOper.getObjId());
		oldTechCtrl = em.find(TechControl.class, oldTechCtrl.getObjId());
		oldTechOper.setMainTime(techOper.getMainTime());
		oldTechOper.setAssistTime(techOper.getAssistTime());
		Document oldDocument = em.find(Document.class, oldTechCtrl.getLinkedObjs().get(0).getObjId());
		
		oldDocument.refresh(document);
		oldTechCtrl.refresh(techCtrl);
		List<ParamControl> oldParamCtrls = oldTechCtrl.getParamControls();
		
        String[] oneDeviceData = new String[3];
        Tool oldDevice;
		for(int i=0;i<devicesPiles.length;i++) {
			oldParamCtrls.get(i).setCtrlDevices(new ArrayList<Tool>());
			String[] devices = devicesPiles[i].trim().split("\n");
	        for(String s:devices){
	        	//toolCode, toolName è toolStandart
	        	oneDeviceData = s.trim().split(",");
	            if(oneDeviceData.length == 3) {
					oldDevice = new ToolService(em).findWithCode(oneDeviceData[0].trim());
					Tool newDevice = new Tool(null, null, null, new Date(), new Date(),oneDeviceData[1].trim(), null,
							oneDeviceData[2].trim(), oneDeviceData[0].trim());
					if(oldDevice == null) {
						em.persist(newDevice);
						oldParamCtrls.get(i).getCtrlDevices().add(newDevice);
					}
					else {
						oldDevice.refresh(newDevice);
						oldParamCtrls.get(i).getCtrlDevices().add(oldDevice);
					}
	            }
	        }
		}
		for(int i=0; i<oldParamCtrls.size();i++) {
			oldParamCtrls.get(i).refresh(paramControls.get(i));
			em.merge(paramControls.get(i));
		}

	}

	public void deleteCtrlCard(int id) throws SQLException, IOException{	
	
		TechControl techCtrl = em.find(TechControl.class, id);
		for(ParamControl pc:techCtrl.getParamControls()) {
			pc.setCtrlDevices(null);
			em.merge(pc);
		}
		if(techCtrl.getAssocObj() != null) techCtrl.getAssocObj().getLinkedObjs().remove(techCtrl);
		if(techCtrl.getParentObj() != null) techCtrl.getParentObj().getChildObjs().remove(techCtrl);
		if(techCtrl.getCreator() != null) techCtrl.getCreator().getCreatedObjects().remove(techCtrl);
		if(techCtrl.getEditor() !=null) techCtrl.getEditor().getEditedObjects().remove(techCtrl);
		em.remove(techCtrl);
	}

	public void deleteParamControl(TechControl techCtrl, int removePos) throws SQLException, IOException{	
		
		ParamControl paramCtrl = (ParamControl) techCtrl.getParamControls().get(removePos);
		paramCtrl.setCtrlDevices(null);
		paramCtrl.setTechControl(null);
		techCtrl.getParamControls().remove(paramCtrl);
		em.merge(paramCtrl);
		em.merge(techCtrl);
	}
	
	public TechOperation find(int id) throws SQLException, IOException{	
		TechOperation techOper = em.find(TechOperation.class, id);
		return techOper;
	}
	
	public List<TechOperation> getAll() throws SQLException, IOException{	
			@SuppressWarnings("unchecked")
			List<TechOperation> resultList = em.createNamedQuery("AllTechOperations").getResultList();
			return resultList;
	}
	
}
