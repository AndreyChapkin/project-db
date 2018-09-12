package servlets.operCard;

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

@WebServlet("/editOperCard")
public class EditOperationCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EntityManagerFactory emf;
	
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
		
		int techOperId = 0;
		String stechOperId = request.getParameter("techOperId");
		if (stechOperId != null && !stechOperId.equals("")) {
			techOperId = Integer.parseInt(stechOperId.trim());
		}
		
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			TechOperation techOper = new TechOperationService(em).find(techOperId);
			
			//Для справочных таблиц
			@SuppressWarnings("unchecked")
			List<Tool> toolList = em.createNamedQuery("AllTools").getResultList();
			request.setAttribute("toolList", toolList);
			for(Tool t:toolList) {
				em.refresh(t);
			}
			request.getServletContext().setAttribute("techOper", techOper);
			em.getTransaction().commit();
		} catch (SQLException e) {e.printStackTrace();}
		finally{em.close();}
		request.getServletContext().getRequestDispatcher("/WEB-INF/views/operCard/editOperCardView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TechOperation techOper = (TechOperation) request.getServletContext().getAttribute("techOper");
		
		request.setCharacterEncoding("utf-8");
		
		//Для документа -------------------------------
		String docName = request.getParameter("docName");
		String docSign = request.getParameter("docSign");
		String docLetter = request.getParameter("docLetter");
		
		//Для материала -------------------------------
		String hardness = request.getParameter("hardness");
		
		//Для самой операции ----------------------------
		String programSign = request.getParameter("programSign");
		float mainTime = 0;
		String smainTime = request.getParameter("mainTime");
		if (smainTime!=null && !smainTime.equals("")) {
			mainTime = Float.parseFloat(smainTime.trim());
		}
		float assistTime = 0;
		String sassistTime = request.getParameter("assistTime");
		if (sassistTime!=null && !sassistTime.equals("")) {
			assistTime = Float.parseFloat(sassistTime.trim());
		}
		String lcl = request.getParameter("lcl");
		techOper.setProgramSign(programSign);
		techOper.setMainTime(mainTime);
		techOper.setAssistTime(assistTime);
		techOper.setLcl(lcl);
		
		//Для переходов ------------------------------
		String[] stranNumbers = request.getParameterValues("tranNumber");
		int[] tranNumbers = new int[stranNumbers.length];
		for(int i=0; i<stranNumbers.length; i++) {
				if (stranNumbers[i]!=null && !stranNumbers[i].equals("")) {
					tranNumbers[i] = Integer.parseInt(stranNumbers[i].trim());
				}
		}
		String[] tranDescrips = request.getParameterValues("tranDescrip");
		
		String[] stranMainTimes = request.getParameterValues("tranMainTime");
		float[] tranMainTimes = new float[stranMainTimes.length];
		for(int i=0; i<stranMainTimes.length; i++) {
				if (stranMainTimes[i]!=null && !stranMainTimes[i].equals("")) {
					tranMainTimes[i] = Float.parseFloat(stranMainTimes[i].trim());
				}
		}
		
		String[] stranAssistTimes = request.getParameterValues("tranAssistTime");
		float[] tranAssistTimes = new float[stranAssistTimes.length];
		for(int i=0; i<stranAssistTimes.length; i++) {
				if (stranAssistTimes[i]!=null && !stranAssistTimes[i].equals("")) {
					tranAssistTimes[i] = Float.parseFloat(stranAssistTimes[i].trim());
				}
		}

		// Оснастка ----------------------------------
		String[] toolsPiles = request.getParameterValues("toolsPile");
		//--------------------------------------------
		String[] sadjustPosNumbers = request.getParameterValues("adjustPosNumber");
		int[] adjustPosNumbers = new int[sadjustPosNumbers.length];
		for(int i=0; i<sadjustPosNumbers.length; i++) {
				if (sadjustPosNumbers[i]!=null && !sadjustPosNumbers[i].equals("")) {
					adjustPosNumbers[i] = Integer.parseInt(sadjustPosNumbers[i].trim());
				}
		}
		String[] streatWidths = request.getParameterValues("treatWidth");
		float[] treatWidths = new float[streatWidths.length];
		for(int i=0; i<streatWidths.length; i++) {
				if (streatWidths[i]!=null && !streatWidths[i].equals("")) {
					treatWidths[i] = Float.parseFloat(streatWidths[i].trim());
				}
		}
		String[] sstrokeLengths = request.getParameterValues("strokeLength");
		float[] strokeLengths = new float[sstrokeLengths.length];
		for(int i=0; i<sstrokeLengths.length; i++) {
				if (sstrokeLengths[i]!=null && !sstrokeLengths[i].equals("")) {
					strokeLengths[i] = Float.parseFloat(sstrokeLengths[i].trim());
				}
		}
		String[] scutDepths = request.getParameterValues("cutDepth");
		float[] cutDepths = new float[scutDepths.length];
		for(int i=0; i<scutDepths.length; i++) {
				if (scutDepths[i]!=null && !scutDepths[i].equals("")) {
					cutDepths[i] = Float.parseFloat(scutDepths[i].trim());
				}
		}
		String[] sstrokeCounts = request.getParameterValues("strokeCount");
		int[] strokeCounts = new int[sstrokeCounts.length];
		for(int i=0; i<sstrokeCounts.length; i++) {
				if (sstrokeCounts[i]!=null && !sstrokeCounts[i].equals("")) {
					strokeCounts[i] = Integer.parseInt(sstrokeCounts[i].trim());
				}
		}
		String[] ssupValues = request.getParameterValues("supValue");
		float[] supValues = new float[ssupValues.length];
		for(int i=0; i<ssupValues.length; i++) {
				if (ssupValues[i]!=null && !ssupValues[i].equals("")) {
					supValues[i] = Float.parseFloat(ssupValues[i].trim());
				}
		}
		String[] srotSpeeds = request.getParameterValues("rotSpeed");
		float[] rotSpeeds = new float[srotSpeeds.length];
		for(int i=0; i<srotSpeeds.length; i++) {
				if (srotSpeeds[i]!=null && !srotSpeeds[i].equals("")) {
					rotSpeeds[i] = Float.parseFloat(srotSpeeds[i].trim());
				}
		}
		String[] scutSpeeds = request.getParameterValues("cutSpeed");
		float[] cutSpeeds = new float[scutSpeeds.length];
		for(int i=0; i<scutSpeeds.length; i++) {
				if (scutSpeeds[i]!=null && !scutSpeeds[i].equals("")) {
					cutSpeeds[i] = Float.parseFloat(scutSpeeds[i].trim());
				}
		}
		//Формируем сущности
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Document document = new Document(null, null, null,new Date(), new Date(), docName, "Операционная карта", docLetter, docSign);
			List<TechTransit> techTransits = new ArrayList<TechTransit>();
			for(int i=0;i<tranNumbers.length;i++) {
				TechTransit techTran = new TechTransit(tranNumbers[i], techOper, tranDescrips[i], adjustPosNumbers[i], treatWidths[i],
				strokeLengths[i], strokeCounts[i], cutDepths[i], supValues[i], rotSpeeds[i], cutSpeeds[i], tranMainTimes[i], tranAssistTimes[i]);
				techTransits.add(techTran);
			}
			new TechOperationService(em).editOperCard(techOper, document, techTransits, hardness, toolsPiles);
			em.getTransaction().commit();
		} 
		catch (SQLException e) {e.printStackTrace();}
		finally {em.close();}
		response.sendRedirect(request.getContextPath()+"/showOperCard?techOperId="+techOper.getObjId());
		}
}
