package servlets.techProcess;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.ServletException;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import entities.*;
import services.*;

@WebServlet("/editTechProc")
public class EditTechProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EntityManagerFactory emf;
	private TechProcess techProc;
	
	@SuppressWarnings({"rawtypes","unchecked"})
    public void init() throws ServletException{	    	
		try {
	    	InitialContext ctx = new InitialContext();
	    	DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
	    	Map properties = new HashMap();
	    	properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
	    	emf = Persistence.createEntityManagerFactory("ProjectDB",properties);
	    }
	    catch(NamingException e) {throw new ServletException(e);}
	}
	    
	public void destroy() {
    	emf.close();
    }
	    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int techProcId = 0;
		String stechProcId = request.getParameter("techProcId");
		if (stechProcId!=null && !stechProcId.equals("")) {
			techProcId = Integer.parseInt(stechProcId.trim());
		}
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			techProc = new TechProcessService(em).find(techProcId);
			request.getServletContext().setAttribute("techProc", techProc);
			
			//Для справочных таблиц
			@SuppressWarnings("unchecked")
			List<Material> matList = em.createNamedQuery("AllMaterials").getResultList();
			for(Material m:matList) {
				em.refresh(m);
			}
			request.setAttribute("matList", matList);
			@SuppressWarnings("unchecked")
			List<TypicBlank> blankList = em.createNamedQuery("AllTypicBlanks").getResultList();
			for(TypicBlank tb:blankList) {
				em.refresh(tb);
			}
			request.setAttribute("blankList", blankList);
			@SuppressWarnings("unchecked")
			List<TypicOperation> typOperList = em.createNamedQuery("AllTypicOperations").getResultList();
			for(TypicOperation to:typOperList) {
				em.refresh(to);
			}
			request.setAttribute("typOperList", typOperList);
			@SuppressWarnings("unchecked")
			List<Equipment> equipList = em.createNamedQuery("AllEquipments").getResultList();
			for(Equipment eq:equipList) {
				em.refresh(eq);
			}
			request.setAttribute("equipList", equipList);
			
			request.setAttribute("techProc", techProc);
			em.getTransaction().commit();
		} catch (SQLException e) {e.printStackTrace();}
		finally {em.close();}
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/views/techProcess/editTechProcessView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");

		int techProcId = techProc.getObjId();
		
		techProc.setEditTime(new Date());
		
		//Документ-------
		Document doc = (Document) techProc.getLinkedObjs().get(0);
		String docName = request.getParameter("docName");
		doc.setDocName(docName);
		String docSign = request.getParameter("docSign");
		doc.setDocSign(docSign);
		String docLetter = request.getParameter("docLetter");
		doc.setDocLetter(docLetter);
		
		//Заготовка и материал
		String typicSize = request.getParameter("typicSize");
		String blankStandart = request.getParameter("blankStandart");
		int blankCode = 0;
		String sblankCode = request.getParameter("blankCode");
		if (sblankCode!=null && !sblankCode.equals("")) {
			blankCode = Integer.parseInt(sblankCode.trim());
		}
		String profile = request.getParameter("profile");
		//
		String matSort = request.getParameter("matSort");
		String matStandart = request.getParameter("matStandart");
		int matCode = 0;
		String smatCode = request.getParameter("matCode");
		if (smatCode!=null && !smatCode.equals("")) {
			matCode = Integer.parseInt(smatCode.trim());
		}
		//Шапка тех. процесса
		String specInstruction = request.getParameter("specInstruction");
		techProc.setSpecInstruction(specInstruction);
		int normUnit = 0;
		String snormUnit = request.getParameter("normUnit");
		if (snormUnit!=null && !snormUnit.equals("")) {
			normUnit = Integer.parseInt(snormUnit.trim());
		}
		techProc.setNormUnit(normUnit);
		
		float materialConsump = 0;
		String smaterialConsump = request.getParameter("materialConsump");
		if (smaterialConsump!=null && !smaterialConsump.equals("")) {
			materialConsump = Float.parseFloat(smaterialConsump.trim());
		}
		techProc.setMaterialConsump(materialConsump);
		
		float materialUseCoef = 0;
		String smaterialUseCoef = request.getParameter("materialUseCoef");
		if (smaterialUseCoef!=null && !smaterialUseCoef.equals("")) {
			materialUseCoef = Float.parseFloat(smaterialUseCoef.trim());
		}
		techProc.setMaterialUseCoef(materialUseCoef);
		
		float blankSize = 0;
		String sblankSize = request.getParameter("blankSize");
		if (sblankSize!=null && !sblankSize.equals("")) {
			blankSize = Float.parseFloat(sblankSize.trim());
		}
		techProc.setBlankSize(blankSize);
		
		int detailCount = 0;
		String sdetailCount = request.getParameter("detailCount");
		if (sdetailCount!=null && !sdetailCount.equals("")) {
			detailCount = Integer.parseInt(sdetailCount.trim());
		}
		techProc.setDetailCount(detailCount);
		
		float blankWeight = 0;
		String sblankWeight = request.getParameter("blankWeight");
		if (sblankWeight!=null && !sblankWeight.equals("")) {
			blankWeight = Float.parseFloat(sblankWeight.trim());
		}
		techProc.setBlankWeight(blankWeight);
		
		//Для списка тех. операций
		String[] sshopNumbers = request.getParameterValues("shopNumber");
		int[] shopNumbers = new int[sshopNumbers.length];
		for(int i=0; i<sshopNumbers.length; i++) {
				if (sshopNumbers[i]!=null && !sshopNumbers[i].equals("")) {
					shopNumbers[i] = Integer.parseInt(sshopNumbers[i].trim());
				}
		}
		String[] sareaNumbers = request.getParameterValues("areaNumber");
		int[] areaNumbers = new int[sareaNumbers.length];
		for(int i=0; i<sareaNumbers.length; i++) {
				if (sareaNumbers[i]!=null && !sareaNumbers[i].equals("")) {
					areaNumbers[i] = Integer.parseInt(sareaNumbers[i].trim());
				}
		}
		String[] splaceNumbers = request.getParameterValues("placeNumber");
		int[] placeNumbers = new int[splaceNumbers.length];
		for(int i=0; i<splaceNumbers.length; i++) {
				if (splaceNumbers[i]!=null && !splaceNumbers[i].equals("")) {
					placeNumbers[i] = Integer.parseInt(splaceNumbers[i].trim());
				}
		}
		String[] soperNumbers = request.getParameterValues("operNumber");
		int[] operNumbers = new int[soperNumbers.length];
		for(int i=0; i<soperNumbers.length; i++) {
				if (soperNumbers[i]!=null && !soperNumbers[i].equals("")) {
					operNumbers[i] = Integer.parseInt(soperNumbers[i].trim());
				}
		}
		String[] soperCodes = request.getParameterValues("operCode");
		int[] operCodes = new int[soperCodes.length];
		for(int i=0; i<soperCodes.length; i++) {
				if (soperCodes[i]!=null && !soperCodes[i].equals("")) {
					operCodes[i] = Integer.parseInt(soperCodes[i].trim());
				}
		}
		String[] operNames = request.getParameterValues("operName");
		String[] appDocSigns = request.getParameterValues("appDocSign");
		
		String[] seqCodes = request.getParameterValues("eqCode");
		int[] eqCodes = new int[seqCodes.length];
		for(int i=0; i<seqCodes.length; i++) {
				if (seqCodes[i]!=null && !seqCodes[i].equals("")) {
					eqCodes[i] = Integer.parseInt(seqCodes[i].trim());
				}
		}
		String[] eqNames = request.getParameterValues("eqName");
		
		String[] smechCodes = request.getParameterValues("mechCode");
		int[] mechCodes = new int[smechCodes.length];
		for(int i=0; i<smechCodes.length; i++) {
				if (smechCodes[i]!=null && !smechCodes[i].equals("")) {
					mechCodes[i] = Integer.parseInt(smechCodes[i].trim());
				}
		}
		String[] sprofCodes = request.getParameterValues("profCode");
		int[] profCodes = new int[sprofCodes.length];
		for(int i=0; i<sprofCodes.length; i++) {
				if (sprofCodes[i]!=null && !sprofCodes[i].equals("")) {
					profCodes[i] = Integer.parseInt(sprofCodes[i].trim());
				}
		}
		String[] sworkCategories = request.getParameterValues("workCategory");
		int[] workCategories = new int[sworkCategories.length];
		for(int i=0; i<sworkCategories.length; i++) {
				if (sworkCategories[i]!=null && !sworkCategories[i].equals("")) {
					workCategories[i] = Integer.parseInt(sworkCategories[i].trim());
				}
		}
		String[] sworkCodes = request.getParameterValues("workCode");
		int[] workCodes = new int[sworkCodes.length];
		for(int i=0; i<sworkCodes.length; i++) {
				if (sworkCodes[i]!=null && !sworkCodes[i].equals("")) {
					workCodes[i] = Integer.parseInt(sworkCodes[i].trim());
				}
		}
		String[] normSigns = request.getParameterValues("normSign");
		
		String[] sperfNumbers = request.getParameterValues("perfNumber");
		int[] perfNumbers = new int[sperfNumbers.length];
		for(int i=0; i<sperfNumbers.length; i++) {
				if (sperfNumbers[i]!=null && !sperfNumbers[i].equals("")) {
					perfNumbers[i] = Integer.parseInt(sperfNumbers[i].trim());
				}
		}
		String[] snspds = request.getParameterValues("nspd");
		int[] nspds = new int[snspds.length];
		for(int i=0; i<snspds.length; i++) {
				if (snspds[i]!=null && !snspds[i].equals("")) {
					nspds[i] = Integer.parseInt(snspds[i].trim());
				}
		}
		String[] soperNormUnits = request.getParameterValues("operNormUnit");
		int[] operNormUnits = new int[soperNormUnits.length];
		for(int i=0; i<soperNormUnits.length; i++) {
				if (soperNormUnits[i]!=null && !soperNormUnits[i].equals("")) {
					operNormUnits[i] = Integer.parseInt(soperNormUnits[i].trim());
				}
		}
		String[] sprodBatchValues = request.getParameterValues("prodBatchValue");
		int[] prodBatchValues = new int[sprodBatchValues.length];
		for(int i=0; i<sprodBatchValues.length; i++) {
				if (sprodBatchValues[i]!=null && !sprodBatchValues[i].equals("")) {
					prodBatchValues[i] = Integer.parseInt(sprodBatchValues[i].trim());
				}
		}
		String[] sunitTimeCoefs = request.getParameterValues("unitTimeCoef");
		float[] unitTimeCoefs = new float[sunitTimeCoefs.length];
		for(int i=0; i<sunitTimeCoefs.length; i++) {
				if (sunitTimeCoefs[i]!=null && !sunitTimeCoefs[i].equals("")) {
					unitTimeCoefs[i] = Float.parseFloat(sunitTimeCoefs[i].trim());
				}
		}
		String[] sprepareTimes = request.getParameterValues("prepareTime");
		float[] prepareTimes = new float[sprepareTimes.length];
		for(int i=0; i<sprepareTimes.length; i++) {
				if (sprepareTimes[i]!=null && !sprepareTimes[i].equals("")) {
					prepareTimes[i] = Float.parseFloat(sprepareTimes[i].trim());
				}
		}
		String[] sunitTimes = request.getParameterValues("unitTime");
		float[] unitTimes = new float[sunitTimes.length];
		for(int i=0; i<sunitTimes.length; i++) {
				if (sunitTimes[i]!=null && !sunitTimes[i].equals("")) {
					unitTimes[i] = Float.parseFloat(sunitTimes[i].trim());
				}
		}
		
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();

			List<TechOperation> techOperations = new ArrayList<TechOperation>();
			for(int i=0;i<shopNumbers.length;i++) {
				TechOperation to = new TechOperation(null, null, null, new Date(), new Date(), operNumbers[i],
						nspds[i], prepareTimes[i], unitTimes[i], 0, 0, null, null,
						shopNumbers[i], areaNumbers[i], placeNumbers[i], unitTimeCoefs[i], prodBatchValues[i],
						operNormUnits[i], perfNumbers[i], workCategories[i],appDocSigns[i]);
				techOperations.add(to);
			}
			TypicBlank tBlank = new TypicBlank(blankCode, null, profile, blankStandart, typicSize);
			Material tpMat = new Material( matCode, null, matSort, null, matStandart);
			
			List<TypicOperation> typicOperations = new ArrayList<TypicOperation>();
			for(int i=0;i<operCodes.length;i++) {
				TypicOperation to = new TypicOperation(operCodes[i], operNames[i]);
				typicOperations.add(to);
			}
			List<TimeNorm> timeNorms = new ArrayList<TimeNorm>();
			for(int i=0;i<normSigns.length;i++) {
				TimeNorm tn = new TimeNorm(normSigns[i], null);
				timeNorms.add(tn);
			}
			List<Profession> professions = new ArrayList<Profession>();
			for(int i=0;i<profCodes.length;i++) {
				Profession p = new Profession( profCodes[i], null);
				professions.add(p);
			}
			List<MechDegree> mechDegrees = new ArrayList<MechDegree>();
			for(int i=0;i<mechCodes.length;i++) {
				MechDegree md = new MechDegree( mechCodes[i], null);
				mechDegrees.add(md);
			}
			List<WorkCondition> workConditions = new ArrayList<WorkCondition>();
			for(int i=0;i<workCodes.length;i++) {
				WorkCondition wc = new  WorkCondition( workCodes[i], null);
				workConditions.add(wc);
			}
			List<Equipment> equipments = new ArrayList<Equipment>();
			for(int i=0;i<eqCodes.length;i++) {
				Equipment eq = new  Equipment(eqCodes[i], eqNames[i]);
				equipments.add(eq);
			}	
			
			new TechProcessService(em).edit(techProc, techOperations, tBlank, tpMat, 
					typicOperations, timeNorms, professions, mechDegrees, workConditions, equipments);
			
			em.getTransaction().commit();
		} 
		catch (SQLException e) {e.printStackTrace();}
		finally {em.close();}
		
		response.sendRedirect(request.getContextPath()+"/showTechProc?techProcId="+techProcId);
		}
}
